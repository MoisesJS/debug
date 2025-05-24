package br.com.netservicos.novosms.emissao.core.facade.impl;

import static br.com.netservicos.core.bean.sn.SnSituacaoProcessamentoBean.Sigla.FINALIZADO;
import static br.com.netservicos.core.bean.sn.SnSituacaoProcessamentoBean.Sigla.PENDENTE;
import static br.com.netservicos.core.bean.sn.SnSituacaoProcessamentoBean.Sigla.PROCESSANDO;
import static br.com.netservicos.core.bean.sn.SnTipoLoteBean.Sigla.EMISSAO_PRINT_BOLETO_AVULSO;

import java.math.BigDecimal;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.ejb.CreateException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.CollectionUtils;

import br.com.netservicos.core.bean.sn.SnBoletoBean;
import br.com.netservicos.core.bean.sn.SnCidadeOperadoraBean;
import br.com.netservicos.core.bean.sn.SnControleArquivoBean;
import br.com.netservicos.core.bean.sn.SnLoteBean;
import br.com.netservicos.core.bean.sn.SnParametroBean;
import br.com.netservicos.core.bean.sn.SnSituacaoProcessamentoBean;
import br.com.netservicos.core.bean.sn.SnTipoCobrancaBean;
import br.com.netservicos.core.bean.sn.SnTipoLoteBean;
import br.com.netservicos.emissao.bean.TransporteDTO;
import br.com.netservicos.framework.core.bean.DynamicBean;
import br.com.netservicos.framework.core.bean.task.constants.TaskConstants;
import br.com.netservicos.framework.core.dao.BatchParameter;
import br.com.netservicos.framework.task.service.TaskService;
import br.com.netservicos.framework.util.attachments.messages.AttachmentMessageLevel;
import br.com.netservicos.framework.util.attachments.messages.BasicAttachmentMessage;
import br.com.netservicos.novosms.emissao.constants.PrintHouseConstants;
import br.com.netservicos.novosms.emissao.core.facade.ArquivoPrintHouseService;
import br.com.netservicos.novosms.emissao.core.facade.EmissaoService;
import br.com.netservicos.novosms.emissao.dto.ArquivoRemessaTransporteDTO;
import br.com.netservicos.novosms.emissao.dto.FaturaArquivoDTO;
import br.com.netservicos.novosms.emissao.dto.ParametroDTO;
import br.com.netservicos.novosms.emissao.exception.EmissaoBusinessResourceException;
import br.com.netservicos.novosms.emissao.resources.EmissaoResources;
import br.com.netservicos.novosms.geral.constants.GeralConstants;

/**
 * @author Wellintgon
 * @since 26/07/2007
 * @version 1.0
 * 
 * @ejb.bean name="EmissaoServiceEJB" type="Stateless" display-name="EmissaoServiceEJB"
 *           description="EmissaoServiceEJB Session EJB Stateless" view-type="both"
 *           jndi-name="netservicos/novosms/emissao/ejb/EmissaoServiceEJB"
 *           local-jndi-name="netservicos/novosms/emissao/ejb/local/EmissaoServiceEJB" transaction-type="Container"
 * 
 * @ejb.interface local-extends="javax.ejb.EJBLocalObject" extends="javax.ejb.EJBObject"
 * 
 * @ejb.home local-extends="javax.ejb.EJBLocalHome" extends="javax.ejb.EJBHome"
 */
public class EmissaoServiceEJBImpl extends PrintHouseQuerysServiceImpl implements EmissaoService{

	/** 
	 * 
	 */
	private static final long	serialVersionUID	                     = -6302278887000019324L;

	private static final Log	log	                                     = LogFactory.getLog(EmissaoServiceEJBImpl.class);

	private static final String	CID_CONTRATO	                         = "cidContrato";
	private static final String	ID_CONTROLE_ARQUIVO	                     = "idControleArquivo";
	private static final String	TASK_TRANSPORTE	                         = "EMISSAO_TRANSPORTE";
	private static final String	TASK_EMISSAO_ARQUIVO_RDCC	             = "EMISSAO_ARQUIVO_RDCC";
	private static final String	TASK_EMISSAO_ARQUIVO_CNAB	             = "EMISSAO_ARQUIVO_CNAB";
	private static final String	TASK_EMISSAO_ARQUIVO_PRINT_HOUSE	     = "EMISSAO_ARQUIVO_PRINT_HOUSE";
	private static final String TASK_EMISSAO_BAIXA_ONLINE_CC			 = "EMISSAO_BAIXA_CARTAO_CREDITO";
	private static final String	TASK_EMISSAO_TESTE_MENSAGENS	         = "TASK_EMISSAO_TESTE_MENSAGENS";
	private static final String	PARAM_LOTES	                             = "LOTES";
	private static final String	PARAM_CRITERIOS	                         = "CRITERIOS";
	private static final String	PARAM_DATA	                             = "DATA";
	private static final String	PARAM_LOTE	                             = "LOTE";
	private static final String	PARAM_TIPO_LOTE	                         = "TIPO_LOTE";
	private static final String	DYNA_PROPERTY_QTDE_TASK	                 = "qtdeTask";
	private static final String	DYNA_PROPERTY_ID_FILA_TASK	             = "idFilaInserida";
	private static final String	DYNA_PROPERTY_LST_TRANSPORTE_ARQUIVO	 = "lstTransporteArquivo";

	private static final String	SERVIDOR_ATIVO	                         = "SERVIDOR_FILA_ATIVO";
	private static final String	ROLE_VISUALIZAR_BANCO_EMISSAO	         = "VISUALIZAR_BANCO_EMISSAO";
	private static final String	ROLE_VISUALIZAR_OPERADORA_CARTAO_EMISSAO	= "VISUALIZAR_OPERADORA_CARTAO_EMISSAO";

	private static final String	PRSMS_LST_BOLETO_AVULSO	                 =
	                                                                             "{call PROD_JD.PGSMS_PRINT_BOLETO_AVULSO.PRSMS_GERA_LOTE_BOLETO_AVULSO(?,?,?)}";

	private static final String	ERRO_CRIACAO_LOTE_BOLETO_AVULSO	         = "erro.criacao.lote.boleto.avulso";
	private static final String	SEM_CRIACAO_LOTE_BOLETO_AVULSO	         = "netsms.criacao.lote.boleto.avulso.empty";

	/**
	 * @ejb.create-method view-type="both"
	 * @throws CreateException
	 */
	public void ejbCreate() throws CreateException{
		super.ejbCreate();
	}

	/**
	 * Inicia o processo de geração de Arquivo Print House para um Lote
	 * 
	 * @author Fabio Ikeda
	 * @number RF190, RF015_RF021
	 * @param idLote
	 * @param idFila
	 *            TODO
	 * @throws JNetException
	 * @semantics
	 * 
	 *            1) Buscar o lote correspondente ao idLote passado (getLoteDAO().selecionarLote(idLote))
	 * 
	 *            2) Aciona o método
	 *            arquivoPrintHouseService.gerarArquivoPrintHouse(lotesPendPrint,FatCobrancaConstants.
	 *            TipoLote.SIGLA_TIPO_LOTE_EMISSAO);
	 * 
	 *            3) Aciona o método netSMSService.dispararAtualizacao(lote);
	 * 
	 *            4) Inserir na fila a tarefa de Transporte de arquivo remessa usando como parâmetros o retorno de
	 *            arquivoPrintHouseService.gerarArquivoPrintHouse
	 * 
	 * @ejb.interface-method view-type = "both"
	 * 
	 */
	public List iniciarPrintHouse(Integer idLote){

		return null;

	}

	/**
	 * @author Fabio Ikeda
	 * @number RF190
	 * @param lotesPendPrint
	 * @param idFila
	 *            TODO
	 * @throws JNetException
	 * @since 1.4 RF190
	 * @ejb.interface-method view-type = "both"
	 * @ejb.transaction type="Required"
	 */
	public List iniciarGeracaoArquivoPrint(List<SnLoteBean> lotesPendPrint, String tipoLote){

		ArquivoPrintHouseService arquivoPrintHouseService = super.getService(ArquivoPrintHouseService.class);
		List<ArquivoRemessaTransporteDTO> transportesArquivo = (List<ArquivoRemessaTransporteDTO>) arquivoPrintHouseService.gerarArquivoPrintHouse(lotesPendPrint, tipoLote);

		// for (Iterator iter = lotesPendPrint.iterator(); iter.hasNext();) {
		// SnLoteBean lote = (SnLoteBean) iter.next();
		// netSMSService.dispararAtualizacao(lote, idFila);
		// }

		return transportesArquivo;// transportarArquivos(transportesArquivo, idFila);

	}

	/**
	 * Inicia o processo de geração de Arquivo PrintHouse
	 * 
	 * @author Fabio Ikeda
	 * @number RF190, RF015_RF021
	 * @param idOperadora
	 * @param dtVencimento
	 * @param idFila
	 *            TODO
	 * @throws JNetException
	 * @semantics
	 * 
	 *            1) Busca os lotes relacionados ao parametros passados e pendentes de impressao
	 *            (getLoteDAO().buscarLotes( Map ))
	 * 
	 *            2) Aciona o método arquivoPrintHouseService.gerarArquivoPrintHouse(lotesPendPrint,TIPO LOTE EMISSAO);
	 * 
	 *            3) Aciona o método netSMSService.dispararAtualizacao(lote);
	 * 
	 *            4) Inserir na fila a tarefa de Transporte de arquivo remessa usando como parâmetros o retorno de
	 *            arquivoPrintHouseService.gerarArquivoPrintHouse
	 * 
	 * @ejb.interface-method view-type = "both"
	 * @ejb.transaction type="Required"
	 * 
	 */
	public List iniciarPrintHouse(String cidContrato, Date dtVencimento, String tipoLote){

		List<ArquivoRemessaTransporteDTO> transportesArquivo = null;
		try {

			SnSituacaoProcessamentoBean situacaoProcessamentoPendente = getSituacaoProcessamento(SnSituacaoProcessamentoBean.Sigla.PENDENTE);
			// SnLoteBean loteFiltro = new SnLoteBean();
			SnTipoLoteBean tipoLoteBean = new SnTipoLoteBean();

			SnTipoCobrancaBean snTipoCobrancaBean = new SnTipoCobrancaBean();
			snTipoCobrancaBean.setIdTipoCobranca(2); // TODO ALTERAR

			SnCidadeOperadoraBean cidade = new SnCidadeOperadoraBean();
			cidade.setCidContrato(cidContrato);

			cidade = (SnCidadeOperadoraBean) search(SnCidadeOperadoraBean.LST_CIDADE_BY_ID, cidade).get(0);

			tipoLoteBean.setSgTipoLote(tipoLote);
			// loteFiltro.setSituacaoProcessamentoPrint(situacaoProcessamentoPendente);
			// loteFiltro.setSnTipoLoteBean(tipoLoteBean);
			// loteFiltro.setDtVcto(dtVencimento);
			// loteFiltro.setSnCidadeOperadoraBean(cidade);
			//
			// loteFiltro.setSnTipoCobrancaBean(snTipoCobrancaBean);

			// lotesPendPrint = getLoteDAO().buscarLotes(where);
			// TODO buscar lotes pendentes de impressão
			// List<SnLoteBean> lotesPendPrint = getDAO(getUserSession().getCurrentDbService()).select("buscarLotes",
			// loteFiltro, false);

			// List<SnLoteBean> lotesPendPrint = getDadosBeanByQuery("buscarLotesSemBanco", loteFiltro, false,
			// SnLoteBean.class);

			SnLoteBean loteFiltr = new SnLoteBean();
			loteFiltr.setDtVcto(dtVencimento);
			loteFiltr.setSnCidadeOperadoraBean(cidade);
			loteFiltr.setSituacaoProcessamento(situacaoProcessamentoPendente);
			loteFiltr.setSnTipoLoteBean(tipoLoteBean);
			loteFiltr.setSnTipoCobrancaBean(snTipoCobrancaBean);

			List<SnLoteBean> lotesPendPrint = super.search(SnLoteBean.OBTER_LOTES_PARAMETROS, loteFiltr, false);
			if (lotesPendPrint != null && lotesPendPrint.size() > 0) {
				transportesArquivo = iniciarGeracaoArquivoPrint(lotesPendPrint, tipoLote);
			}

		} catch (Exception e) {
			e.printStackTrace();

		}

		return transportesArquivo;

	}

	/**
	 * Inicia o processo de geração de Arquivo PrintHouse
	 * 
	 * @author Fabio Ikeda
	 * @number RF190, RF015_RF021
	 * @param idOperadora
	 * @param dtVencimento
	 * @param idFila
	 *            TODO
	 * @throws JNetException
	 * @semantics
	 * 
	 *            1) Busca os lotes relacionados ao parametros passados e pendentes de impressao
	 *            (getLoteDAO().buscarLotes( Map ))
	 * 
	 *            2) Aciona o método arquivoPrintHouseService.gerarArquivoPrintHouse(lotesPendPrint,TIPO LOTE EMISSAO);
	 * 
	 *            3) Aciona o método netSMSService.dispararAtualizacao(lote);
	 * 
	 *            4) Inserir na fila a tarefa de Transporte de arquivo remessa usando como parâmetros o retorno de
	 *            arquivoPrintHouseService.gerarArquivoPrintHouse
	 * 
	 * @ejb.interface-method view-type = "both"
	 * @ejb.transaction type="Required"
	 * 
	 */

	public List iniciarPrintHousePorLotes(String cidContrato, List<String> lotes, String tipoLote){

		List<ArquivoRemessaTransporteDTO> transportesArquivo = null;

		try {

			List<SnLoteBean> lotesPendPrint = new ArrayList<SnLoteBean>();
			for (String lote : lotes) {
				DynamicBean beanParam = new DynamicBean();
				beanParam.put("idLote", Integer.parseInt(lote));
				SnLoteBean lotePendente = (SnLoteBean) super.search("selecionaLoteById", beanParam, false).get(0);
				lotesPendPrint.add(lotePendente);
			}
			if (lotesPendPrint != null && lotesPendPrint.size() > 0) {
				transportesArquivo = iniciarGeracaoArquivoPrint(lotesPendPrint, tipoLote);
			}

		} catch (Exception e) {
			e.printStackTrace();

		}

		return transportesArquivo;

	}

	/**
	 * @number RF190
	 * @see br.com.netservicos.sms.fatcobranca.service.EmissaoService#iniciarPrintHouse2aViaReNF(java.lang.String,
	 *      Integer)
	 * @since 1.31 RF190
	 */
	/*
	 * public void iniciarPrintHouse2aViaReNF(String siglaTipoLote, Integer idFila) {
	 * 
	 * 
	 * ArquivoPrintHouseService arquivoPrintHouseService = super.getService(ArquivoPrintHouseService.class, true);
	 * List<ArquivoRemessaTransporteDTO> transportesArquivo = (List<ArquivoRemessaTransporteDTO>)
	 * arquivoPrintHouseService.gerarArquivoPrintHouse(lotesPendPrint,tipoLote);
	 * 
	 * try {
	 * 
	 * // transportesArquivo = arquivoPrintHouseService.gerarArquivoPrintHouse(siglaTipoLote);
	 * 
	 * } catch (Exception e) { throw e; } finally {
	 * 
	 * } }
	 */

	/**
	 * @ejb.interface-method view-type = "both"
	 * @ejb.transaction type="Required"
	 */
	public FaturaArquivoDTO gerarFaturaSegundaVia(Long idBoleto){

		ArquivoPrintHouseService arquivoPrintHouseService = super.getService(ArquivoPrintHouseService.class, true);
		FaturaArquivoDTO faturaDTO = (FaturaArquivoDTO) arquivoPrintHouseService.gerarFaturaSegundaVia(idBoleto);

		return faturaDTO;

	}

	protected SnSituacaoProcessamentoBean getSituacaoProcessamento(SnSituacaoProcessamentoBean.Sigla situacaoProcessamento) {

		SnSituacaoProcessamentoBean snSituacaoProcessamentoBean = new SnSituacaoProcessamentoBean();
		snSituacaoProcessamentoBean.setIdSituacaoProcessamento(situacaoProcessamento.getChave());
		snSituacaoProcessamentoBean.setSgSituacaoProcessamento(situacaoProcessamento.getSigla());

		snSituacaoProcessamentoBean = (SnSituacaoProcessamentoBean) find(snSituacaoProcessamentoBean);

		System.out.println(snSituacaoProcessamentoBean.getSgSituacaoProcessamento());

		// return this.getTabelaVirtualService().pesquisarChaveBusca(TabelaConstants.TABELA_SITUACAO_PROC,
		// chaveRegistro, null);
		return snSituacaoProcessamentoBean;
	}

	/**
	 * @author Fabio Ikeda
	 * @number RF190, RF015_RF021
	 * @param idOperadora
	 * @param dtVencimento
	 * @param idInstituicao
	 * @param idModalidade
	 * @param idFila
	 *            TODO
	 * @throws JNetException
	 * @number RF190
	 * @semantics
	 * 
	 *            1) Busca os lotes relacionados ao parametros passados e pendentes de impressao
	 *            (getLoteDAO().buscarLotes( Map ))
	 * 
	 *            2) Aciona o método arquivoPrintHouseService.gerarArquivoPrintHouse(lotesPendPrint,TIPO LOTE EMISSAO);
	 * 
	 *            3) Aciona o método netSMSService.dispararAtualizacao(lote);
	 * 
	 *            4) Inserir na fila a tarefa de Transporte de arquivo remessa usando como parâmetros o retorno de
	 *            arquivoPrintHouseService.gerarArquivoPrintHouse
	 * 
	 * @ejb.interface-method view-type = "both"
	 * 
	 */
	public List iniciarPrintHouse(Integer idOperadora, Date dtVencimento, Integer idInstituicao, Integer idModalidade,String  tipoLote){

		return null;
	}

	/**
	 * @version 1.0
	 * @author Fabio Ikeda
	 * @number RF190, RF017, RF015_RF021
	 * @param origem
	 * @param destino
	 * @throws JNetException
	 * @semantics Inicia o transporte do arquivo requisitado
	 * 
	 * @ejb.interface-method view-type = "both"
	 * 
	 */
	public List iniciarTransporteArquivos(TransporteDTO origem, TransporteDTO destino, String protocolo, Integer idFila, Integer idControleArquivo, Boolean apagarOrigem){

		// cahamada para o metodo de transporte
		return null;

	}

	/**
	 * Método responsável por buscar arquivos com criticas
	 * 
	 * @param bean
	 *            Bean contendo os filtros a serem utilizados para a busca
	 * @return boolean O resultado da busca utilizando o filtro parametrizado
	 * 
	 * @see br.com.netservicos.framework.core.bean.Bean
	 * 
	 * @author
	 * @since 04/07/2007
	 * 
	 * @ejb.interface-method view-type = "both"
	 * 
	 * 
	 */
	public Integer iniciarPrintHouseTest(){

		SnBoletoBean boleto = new SnBoletoBean();

		boleto.setIdBoleto(new Long(2781516));

		// SnCobrancaBean cob = new SnCobrancaBean();
		// cob.setIdCobranca(5140);

		// List result1 = super.search("listarTituloNotaFiscalOperadora", boleto, false);

		// List<ParametroDTO> result = super.getDadosBeanByQuery("listarTituloNotaFiscalOperadora", boleto,
		// false,ParametroDTO.class );

		List<ParametroDTO> result = super.getSelecionarPrefixo(boleto, ParametroDTO.class);

		// FilaServiceEJB fila = getService(FilaServiceEJB.class, false);

		// FilaBean bean = new FilaBean();
		// fila.insert(bean);

		if (result != null && result.size() > 0) {
			return result.size();
		} else {
			return 0;
		}

	}

	/**
	 * Metodo responsavel pela busca dos bancos das operadoras selecionadas
	 * 
	 * @author matorres
	 * @since 03/09/2007
	 * @param dynaBean
	 *            DynamicBean contendo parametros para a query (lstCidContrato)
	 * @return dynaBean
	 * 
	 * @ejb.interface-method view-type = "both"
	 */
	public DynamicBean obterBanco(DynamicBean dynaBean){

		List lstBanco = null;
		List lstOperadora = null;
		DynamicBean bean = new DynamicBean();

		Collection<String> colCidContrato = this.getCollectionFromStringArray((String) dynaBean.get("lstCidContrato"));

		if (colCidContrato != null && colCidContrato.size() > 0) {
			bean.set("lstCidContrato", colCidContrato);
			// if(this.context.isCallerInRole(ROLE_VISUALIZAR_BANCO_EMISSAO)) {
			lstBanco = (List) search("lstBancoPorCidadeOperadora", bean, false);
			// }
			// if(this.context.isCallerInRole(ROLE_VISUALIZAR_OPERADORA_CARTAO_EMISSAO)) {
			lstOperadora = (List) obterOperadoraCartao(dynaBean).get("lstSnOperadoraCartao");				

														
		}

		dynaBean.set("lstSnBanco", lstBanco);
		dynaBean.set("lstSnOperadoraCartao", lstOperadora);

		return dynaBean;

	}
	
	
	/**
	 * Metodo responsavel pela busca das operadoras de cartao conforme filtro
	 * 
	 * @author cassio
	 * @since 12/10/2021
	 * @param dynaBean
	 *            DynamicBean contendo parametros para a query (lstCidContrato)
	 * @return dynaBean
	 * 
	 * @ejb.interface-method view-type = "both"
	 */
	public DynamicBean obterOperadoraCartao(DynamicBean dynaBean){
		List lstOperadora = null;
		DynamicBean bean = new DynamicBean();
		
		Collection<String> colCidContrato = this.getCollectionFromStringArray((String) dynaBean.get("lstCidContrato"));
				
		if (colCidContrato != null && colCidContrato.size() > 0) {	
			
			bean.set("lstCidContrato", colCidContrato);
			
			lstOperadora = (List) search("lstOperadoraCartaoAutorizaCompra", bean, false);						
														
		}
		
		dynaBean.set("lstSnOperadoraCartao", lstOperadora);
		
		return dynaBean;
		
	}

	/**
	 * @author marcelo
	 * @since 30/08/2007
	 * @param dynaBean
	 *            DynamicBean contendo parametros para a query (dataInicial, dataFinal e lstCidContrato)
	 * @return dynaBean com a lista de lotes encontratos
	 * 
	 * @ejb.permission role-name="GERACAO_MANUAL_ARQUIVO_PRINT_CONSULTAR,GERACAO_MANUAL_ARQUIVO_PRINT_INSERIR"
	 * @ejb.interface-method view-type = "both"
	 */
	public DynamicBean buscarLotesParaEscolha(DynamicBean dynaBean){

		Collection<String> colCidContrato = this.getCollectionFromStringArray((String) dynaBean.get("lstCidContrato"));

		Date dtVencimentoInicio = (Date) dynaBean.get("dataInicial");
		Date dtVencimentoFim = (Date) dynaBean.get("dataFinal");
		String tipoLote = (String) dynaBean.get("tipoLote");

		DetachedCriteria criteria = DetachedCriteria.forClass(SnLoteBean.class);
		criteria.createAlias("snTipoLoteBean", "snTipoLoteBean");
		criteria.createAlias("situacaoProcessamento", "situacaoProcessamento");
		criteria.createAlias("snTipoCobrancaBean", "snTipoCobrancaBean");
		criteria.createAlias("snCidadeOperadoraBean", "snCidadeOperadoraBean");
		criteria.createAlias("snCriterioSegmentacaoBoletoBean", "snCriterioSegmentacaoBoletoBean");
		criteria.add(Restrictions.in("snCidadeOperadoraBean.cidContrato", colCidContrato));
		criteria.add(Restrictions.between("dtVcto", dtVencimentoInicio, dtVencimentoFim));
		if (tipoLote.equals("TODOS")) {
			criteria.add(Restrictions.in("snTipoLoteBean.sgTipoLote", new String[] {
			        SnTipoLoteBean.Sigla.EMISSAOPRINT.getChaveSigla()
			        , SnTipoLoteBean.Sigla.CPNF.getChaveSigla()
			        , SnTipoLoteBean.Sigla.SEGUNDA_VIA.getChaveSigla() }));
		}
		else {
			criteria.add(Restrictions.eq("snTipoLoteBean.sgTipoLote", tipoLote));
		}
		criteria.add(Restrictions.eq("situacaoProcessamento.sgSituacaoProcessamento", SnSituacaoProcessamentoBean.Sigla.PENDENTE.getSigla()));

		List<SnLoteBean> listaLote = (List<SnLoteBean>) getCurrentDAO().select(criteria);

		dynaBean.set("lstLote", listaLote);

		return dynaBean;

	}

	/**
	 * Metodo responsavel por buscar os lotes cnab para conforme filtro
	 * 
	 * @author matorres
	 * @since 04/09/2007
	 * @param dynaBean
	 *            DynamicBean contendo parametros para a query (dataInicial, dataFinal, lstCidContrato e lstBanco)
	 * @return dynaBean com a lista de lotes encontrados
	 * 
	 * @ejb.permission role-name="GERACAO_MANUAL_ARQUIVO_CNAB_CONSULTAR,GERACAO_MANUAL_ARQUIVO_CNAB_INSERIR"
	 * @ejb.interface-method view-type = "both"
	 */
	public DynamicBean buscarLotesCnab(DynamicBean dynaBean){

		Collection<String> colCidContrato = this.getCollectionFromStringArray((String) dynaBean.get("lstCidContrato"));
		Collection<Integer> colBanco = this.getCollectionFromStringArray((String)dynaBean.get("lstBanco"), Integer.class, 1); // 1 = tipo de instituicao financeira banco
		Collection<Integer> colOperadoraCartao = this.getCollectionFromStringArray((String)dynaBean.get("lstBanco"), Integer.class, 2); // 2 = tipo de instituicao financeira operadora cartao
																					   // financeira operadora cartao

		Date dtVencimentoInicio = (Date) dynaBean.get("dataInicial");
		Date dtVencimentoFim = (Date) dynaBean.get("dataFinal");

		DetachedCriteria criteria = DetachedCriteria.forClass(SnLoteBean.class);
		criteria.createAlias("snTipoLoteBean", "snTipoLoteBean");
		criteria.createAlias("situacaoProcessamento", "situacaoProcessamento");
		criteria.createAlias("snTipoCobrancaBean", "snTipoCobrancaBean");
		criteria.createAlias("snCidadeOperadoraBean", "snCidadeOperadoraBean");

		// if(this.context.isCallerInRole(ROLE_VISUALIZAR_BANCO_EMISSAO)) {
		criteria.setFetchMode("snBancoBean", FetchMode.JOIN);
		// }
		// if(this.context.isCallerInRole(ROLE_VISUALIZAR_OPERADORA_CARTAO_EMISSAO)) {
		criteria.setFetchMode("snOperadoraCartaoBean", FetchMode.JOIN);
		// }

		criteria.add(Restrictions.in("snCidadeOperadoraBean.cidContrato", colCidContrato));

		if ((colBanco != null && colBanco.size() > 0) && (colOperadoraCartao != null && colOperadoraCartao.size() > 0)) {
			criteria.add(Restrictions.or(Restrictions.in("snBancoBean.idBanco", colBanco),Restrictions.in("snOperadoraCartaoBean.idOperadoraCartao", colOperadoraCartao)));
		}
		else if (colBanco != null && colBanco.size() > 0) {
			criteria.add(Restrictions.in("snBancoBean.idBanco", colBanco));
		}
		else if (colOperadoraCartao != null && colOperadoraCartao.size() > 0) {
			criteria.add(Restrictions.in("snOperadoraCartaoBean.idOperadoraCartao", colOperadoraCartao));
		}

		criteria.add(Restrictions.between("dtVcto", dtVencimentoInicio, dtVencimentoFim));
		criteria.add(Restrictions.eq("snTipoLoteBean.sgTipoLote", SnTipoLoteBean.Sigla.EMISSAOCNAB.getChaveSigla()));
		criteria.add(Restrictions.eq("situacaoProcessamento.sgSituacaoProcessamento", SnSituacaoProcessamentoBean.Sigla.PENDENTE.getSigla()));

		List<SnLoteBean> listaLote = (List<SnLoteBean>) getCurrentDAO().select(criteria);

		dynaBean.set("lstLote", listaLote);

		return dynaBean;

	}
	
	/**
	 * Metodo responsavel por buscar os lotes baixa online conforme filtro
	 * 
	 * @author cassio
	 * @since 12/10/2021
	 * @param dynaBean
	 *            DynamicBean contendo parametros para a query (dataInicial, dataFinal, lstCidContrato e lstBanco)
	 * @return dynaBean com a lista de lotes encontrados 
	 * 
	 * @ejb.permission role-name="GERACAO_MANUAL_ARQUIVO_CNAB_CONSULTAR,GERACAO_MANUAL_ARQUIVO_CNAB_INSERIR"
	 * @ejb.interface-method view-type = "both"
	 */
	public DynamicBean buscarLotesCartaoOnline(DynamicBean dynaBean){

		Collection<String> colCidContrato = this.getCollectionFromStringArray((String) dynaBean.get("lstCidContrato"));			
		Collection<Integer> colOperadoraCartao = this.getCollectionFromStringArray((String)dynaBean.get("lstBanco"), Integer.class, 2); 																					 			

		Date dtVencimentoInicio = (Date) dynaBean.get("dataInicial");
		Date dtVencimentoFim = (Date) dynaBean.get("dataFinal");

		DetachedCriteria criteria = DetachedCriteria.forClass(SnLoteBean.class);
		
		criteria.createAlias("snTipoLoteBean", "snTipoLoteBean");
		criteria.createAlias("situacaoProcessamento", "situacaoProcessamento");
		criteria.createAlias("snTipoCobrancaBean", "snTipoCobrancaBean");
		criteria.createAlias("snCidadeOperadoraBean", "snCidadeOperadoraBean");

		criteria.setFetchMode("snOperadoraCartaoBean", FetchMode.JOIN);
				
		
		if (colOperadoraCartao != null && colOperadoraCartao.size() > 0) {
			criteria.add(Restrictions.in("snOperadoraCartaoBean.idOperadoraCartao", colOperadoraCartao));
		}

		criteria.add(Restrictions.in("snCidadeOperadoraBean.cidContrato", colCidContrato));
		criteria.add(Restrictions.between("dtVcto", dtVencimentoInicio, dtVencimentoFim));
		criteria.add(Restrictions.eq("snTipoLoteBean.sgTipoLote", SnTipoLoteBean.Sigla.EMISCCONL.getChaveSigla()));  
		criteria.add(Restrictions.eq("situacaoProcessamento.sgSituacaoProcessamento", SnSituacaoProcessamentoBean.Sigla.PENDENTE.getSigla()));

		List<SnLoteBean> listaLote = (List<SnLoteBean>) getCurrentDAO().select(criteria);		

		dynaBean.set("lstLote", listaLote);

		return dynaBean;

	}	

	/**
	 * Metodo responsavel pela gravacao dos lotes selecionados
	 * 
	 * @author matorres
	 * @since 30/08/2007
	 * @param dynaBean
	 *            DynamicBean contendo parametros para a query (lstLote)
	 * @return
	 * 
	 * @ejb.interface-method view-type = "both"
	 * @ejb.permission role-name="GERACAO_MANUAL_ARQUIVO_PRINT_INSERIR"
	 * @ejb.transaction type="Required"
	 */
	public DynamicBean persisteLotes(DynamicBean dynaBean){

		String[] loteArray = ((String) dynaBean.get("lstLoteGravar")).split("\\|");

		Map<String, Map<String, List<SnLoteBean>>> mapCidContrato = new HashMap<String, Map<String, List<SnLoteBean>>>();

		// Itera o array de lote e monta grupo por cidade do contrato e sigla do tipo de lote
		for (int i = 0; i < loteArray.length; i++) {

			String[] campos = loteArray[i].split("\\_");
			Integer idLote = Integer.parseInt(campos[0]);
			String cidContrato = campos[1];
			String sgTipoLote = campos[2];

			// Cria tipo de lote
			SnTipoLoteBean snTipoLoteBean = new SnTipoLoteBean();
			snTipoLoteBean.setSgTipoLote(sgTipoLote);

			// Cria lote
			SnLoteBean snLoteBean = new SnLoteBean();
			snLoteBean.setIdLote(idLote);
			snLoteBean.setSnTipoLoteBean(snTipoLoteBean);

			if (mapCidContrato.containsKey(cidContrato)) {

				Map<String, List<SnLoteBean>> mapSgTipoLote = mapCidContrato.get(cidContrato);

				if (mapSgTipoLote.containsKey(sgTipoLote)) {
					List<SnLoteBean> listLote = mapSgTipoLote.get(sgTipoLote);
					listLote.add(snLoteBean);
				}
				else {
					List<SnLoteBean> listLote = new ArrayList<SnLoteBean>();
					listLote.add(snLoteBean);
					mapSgTipoLote.put(sgTipoLote, listLote);
				}

			}
			else {

				List<SnLoteBean> listLote = new ArrayList<SnLoteBean>();
				listLote.add(snLoteBean);

				Map<String, List<SnLoteBean>> mapSgTipoLote = new HashMap<String, List<SnLoteBean>>();
				mapSgTipoLote.put(sgTipoLote, listLote);

				mapCidContrato.put(cidContrato, mapSgTipoLote);

			}

		}

		List<ArquivoRemessaTransporteDTO> lstTransporteArquivo = new ArrayList<ArquivoRemessaTransporteDTO>();

		String strIdFilaInserida = "";
		int i = 0;

		Iterator itCidContrato = mapCidContrato.keySet().iterator();

		// Itera as colecoes e cria tarefa de geracao dos arquivos por grupo de lotes
		// se segregação estiver desligada, e por lote se segregação estiver ligada
		while(itCidContrato.hasNext()) {

			String cidContrato = (String) itCidContrato.next();

			// Pesquisa cidade operadora para a cidade do contrato
			SnCidadeOperadoraBean snCidadeOperadoraBean = this.obterCidadeOperadora(cidContrato);

			boolean isTaskOn = this.usarTask(snCidadeOperadoraBean);

			Map<String, List<SnLoteBean>> mapSgTipoLote = (Map<String, List<SnLoteBean>>) mapCidContrato.get(cidContrato);

			Iterator<String> itSgTipoLote = mapSgTipoLote.keySet().iterator();

			while(itSgTipoLote.hasNext()) {

				String sgTipoLote = (String) itSgTipoLote.next();
				List<SnLoteBean> listLote = (List<SnLoteBean>) mapSgTipoLote.get(sgTipoLote);

				boolean isSegregado = this.usarControleSegregacao(snCidadeOperadoraBean);

				if (isTaskOn) {

					if (isSegregado) {

						// Insere a tarefa para geracao dos arquivos para cada lote
						for (SnLoteBean snLoteTarget : listLote) {

							List<SnLoteBean> listLoteUnico = new ArrayList<SnLoteBean>();
							listLoteUnico.add(snLoteTarget);

							// Insere a tafefa para geracao dos arquivos do grupo de lotes
							Long idFilaInserida = this.criarTarefaLotesPrintHouseTask(listLoteUnico, sgTipoLote);

							// Conta numero de tarefas criadas
							i++;

							strIdFilaInserida += idFilaInserida + ", ";

						}

					} else {

						// Insere a tafefa para geracao dos arquivos do grupo de lotes
						Long idFilaInserida = this.criarTarefaLotesPrintHouseTask(listLote, sgTipoLote);

						// Conta numero de tarefas criadas
						i++;

						strIdFilaInserida += idFilaInserida + ", ";

					}

				} else {

					String[] lstLote = new String[listLote.size()];
					for (int j = 0; j < listLote.size(); j++) {
						lstLote[j] = String.valueOf(listLote.get(j).getIdLote());
					}

					List<ArquivoRemessaTransporteDTO> lstArquivoRemessa = null;

					// Atualiza situacao do lote para processando em transacao separada
					ArquivoPrintHouseService arqPHService = super.getService(ArquivoPrintHouseService.class);
					arqPHService.atualizarSituacaoLote(listLote, SnSituacaoProcessamentoBean.Sigla.PROCESSANDO.getSigla());

					try {

						// Executa o processo de geracao de arquivo
						lstArquivoRemessa = this.gerarArquivoPrintHouseTask(lstLote, sgTipoLote);

						// Atualiza situacao do lote para finalizado em transacao separada
						arqPHService.atualizarSituacaoLote(listLote, SnSituacaoProcessamentoBean.Sigla.FINALIZADO.getSigla());

					}
					catch (Exception ex) {
						// Atualiza situacao do lote para pendente em transacao separada
						arqPHService.atualizarSituacaoLote(listLote, SnSituacaoProcessamentoBean.Sigla.PENDENTE.getSigla());
					}

					// Acumula os arquivos criados
					lstTransporteArquivo.addAll(lstArquivoRemessa);

				}

			}

		}

		// Remove ultimo caracter pipe
		if (strIdFilaInserida.length() > 0) {
			strIdFilaInserida = strIdFilaInserida.substring(0, strIdFilaInserida.length() - 2);
		}

		dynaBean.set(DYNA_PROPERTY_ID_FILA_TASK, strIdFilaInserida);
		dynaBean.set(DYNA_PROPERTY_LST_TRANSPORTE_ARQUIVO, lstTransporteArquivo);
		dynaBean.set(DYNA_PROPERTY_QTDE_TASK, new Integer(i));

		return dynaBean;

	}

	/**
	 * Metodo responsavel pela gravacao dos lotes selecionados
	 * 
	 * @author matorres
	 * @since 30/08/2007
	 * @param dynaBean
	 *            DynamicBean contendo parametros para a query (lstLote)
	 * @return
	 * 
	 * @ejb.interface-method view-type = "both"
	 * @ejb.permission role-name="GERACAO_MANUAL_ARQUIVO_PRINT_INSERIR"
	 * @ejb.transaction type="Required"
	 */
	public DynamicBean persisteCriterios(DynamicBean dynaBean){

		String[] loteArray = ((String) dynaBean.get("lstCriterios")).split("\\|");
		Date dtVencimento = (Date) dynaBean.get("dataInicial");
		SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
		String tipoLote = (String) dynaBean.get("tipoLote");

		String dtVencimentoStr = null;
		if (dtVencimento != null) {
			dtVencimentoStr = dateFormat.format(dtVencimento);
		}

		Map<String, Map<String, List<SnLoteBean>>> mapCidContrato = new HashMap<String, Map<String, List<SnLoteBean>>>();
		String idCriterios = "";
		String strIdFilaInserida = "";
		int k = 1;
		// Itera o array de lote e monta grupo por cidade do contrato e sigla do tipo de lote
		for (int i = 0; i < loteArray.length; i++) {

			String[] campos = loteArray[i].split("\\_");
			Integer idCriterio = Integer.parseInt(campos[0]);

			idCriterios += idCriterio + "|";
		}

		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put(PARAM_CRITERIOS, idCriterios);
		parameters.put(PARAM_DATA, dtVencimentoStr);
		parameters.put(PARAM_TIPO_LOTE, tipoLote);
		String obs = "";
		Long idFilaInserida = this.insertTask(parameters, TASK_EMISSAO_TESTE_MENSAGENS, obs);

		dynaBean.set(DYNA_PROPERTY_ID_FILA_TASK, idFilaInserida);
		dynaBean.set(DYNA_PROPERTY_QTDE_TASK, new Integer(k));

		return dynaBean;

	}

	/**
	 * Obtem se deve chamar incluir task ou chamar direto a emissao
	 * 
	 * @return
	 */
    private boolean usarTask(SnCidadeOperadoraBean snCidadeOperadoraBean){

        // Pesquisa parametro para ver se deve executar pela tela ou por task
        SnParametroBean snParametroBean = this.obterParametro(snCidadeOperadoraBean, SERVIDOR_ATIVO);

        //Define valor default
        String strUsarTask = null;
        BigDecimal vlrUsarTask = null;
        boolean isTask = true;

        if(snParametroBean != null){
            strUsarTask = snParametroBean.getVlrParametroStr();
            vlrUsarTask = snParametroBean.getVlrParametro();
        }               

        if(strUsarTask != null) {
            isTask = strUsarTask.equalsIgnoreCase("S");
        }else if (vlrUsarTask != null) {
            isTask = vlrUsarTask.intValue() == 1;
        }                       

        return isTask;
    }
	/**
	 * Obtem o flag de controle de segregacao
	 * 
	 * @param snCidadeOperadoraBean
	 * @return true ou false se o controle de segregacao esta ligado
	 */
	private boolean usarControleSegregacao(SnCidadeOperadoraBean snCidadeOperadoraBean){

		// Pesquisa parametro para ver se deve executar pela tela ou por task
		SnParametroBean snParametroBean = this.obterParametro(snCidadeOperadoraBean,
		                                                      PrintHouseConstants.Parametro.CONTROLE_SEGREGACAO_ARQUIVO_OPERADORA);

		if (snParametroBean == null
		        || snParametroBean.getVlrParametroStr() == null
		        || snParametroBean.getVlrParametroStr().equals("N")) {
			return Boolean.FALSE;
		} else {
			return Boolean.TRUE;
		}

	}

	/**
	 * Metodo responsavel pela gravacao dos lotes cnab selecionados
	 * 
	 * @author matorres
	 * @since 04/09/2007
	 * @param dynaBean
	 *            DynamicBean contendo parametros para a query (lstLoteGravar)
	 * @return dynaBean
	 * 
	 * @ejb.permission role-name="GERACAO_MANUAL_ARQUIVO_CNAB_INSERIR"
	 * @ejb.interface-method view-type = "both"
	 */
	public DynamicBean persisteLotesCnab(DynamicBean dynaBean){

		Map<String, List<SnLoteBean>> mapCidContrato = new HashMap<String, List<SnLoteBean>>();

		String[] loteArray = ((String) dynaBean.get("lstLoteGravar")).split("\\|");

		for (int i = 0; i < loteArray.length; i++) {

			String[] campos = loteArray[i].split("\\_");
			Integer idLote = Integer.parseInt(campos[0]);
			String cidContrato = campos[1];

			// Cria lote
			SnLoteBean snLoteBean = new SnLoteBean();
			snLoteBean.setIdLote(idLote);

			if (mapCidContrato.containsKey(cidContrato)) {

				List<SnLoteBean> listLote = mapCidContrato.get(cidContrato);
				listLote.add(snLoteBean);

			}
			else {

				List<SnLoteBean> listLote = new ArrayList<SnLoteBean>();
				listLote.add(snLoteBean);

				mapCidContrato.put(cidContrato, listLote);

			}

		}

		// Busca a situacao de processamento do lote Agendado
		SnSituacaoProcessamentoBean situacaoAgendado = this.obterSituacaoProcessamento(SnSituacaoProcessamentoBean.Sigla.AGENDADO.getSigla());

		List<ArquivoRemessaTransporteDTO> lstTransporteArquivo = new ArrayList<ArquivoRemessaTransporteDTO>();

		String strIdFilaInserida = "";
		int i = 0;

		Iterator itCidContrato = mapCidContrato.keySet().iterator();

		// Itera a colecao por cidade operadora
		while(itCidContrato.hasNext()) {

			String cidContrato = (String) itCidContrato.next();

			List<SnLoteBean> listLote = mapCidContrato.get(cidContrato);

			// Pesquisa cidade operadora para a cidade do contrato
			SnCidadeOperadoraBean snCidadeOperadoraBean = this.obterCidadeOperadora(cidContrato);

			boolean isTaskOn = this.usarTask(snCidadeOperadoraBean);

			// Itera a colecao de lotes de uma cidade para criar tarefa de geracao dos arquivos
			// ou chamar diretamente a geracao do arquivo se servidor de task nao esta ativo
			for (SnLoteBean snLoteBean : listLote) {

				if (isTaskOn) {

					// Insere a tafefa para geracao dos arquivos do grupo de lotes
					Long idFilaInserida = this.criarTarefaLoteCNABTask(snLoteBean);

					// Conta numero de tarefas criadas
					i++;

					strIdFilaInserida += idFilaInserida + ", ";

				} else {

					// Busca lote para poder efetuar update
					snLoteBean = (SnLoteBean) this.find(snLoteBean);
					snLoteBean.setSituacaoProcessamento(situacaoAgendado);

					// Atualiza situacao para agendado
					this.update(snLoteBean, getUserSession().getCurrentDbService());

					// Executa o processo de geracao de arquivo
					List<ArquivoRemessaTransporteDTO> lstArquivoRemessa = this.gerarArquivoCNABTask(snLoteBean.getIdLote());

					// Acumula os arquivos criados
					lstTransporteArquivo.addAll(lstArquivoRemessa);

				}

			}

		}

		// Remove ultimo caracter pipe
		if (strIdFilaInserida.length() > 0) {
			strIdFilaInserida = strIdFilaInserida.substring(0, strIdFilaInserida.length() - 2);
		}

		dynaBean.set(DYNA_PROPERTY_ID_FILA_TASK, strIdFilaInserida);
		dynaBean.set(DYNA_PROPERTY_QTDE_TASK, loteArray.length);
		dynaBean.set(DYNA_PROPERTY_LST_TRANSPORTE_ARQUIVO, lstTransporteArquivo);

		return dynaBean;

	}
	
	
	/**
	 * Metodo responsavel pela gravacao dos lotes baixa online cc selecionados
	 * 
	 * @author cassio
	 * @since 12/10/2021
	 * @param dynaBean
	 *            DynamicBean contendo parametros para a query (lstLoteGravar)
	 * @return dynaBean
	 * 
	 * @ejb.permission role-name="GERACAO_MANUAL_ARQUIVO_CNAB_INSERIR"
	 * @ejb.interface-method view-type = "both"
	 */
	public DynamicBean persisteLotesBaixaOnline(DynamicBean dynaBean){
		
		Map<String, List<SnLoteBean>> mapCidContrato = new HashMap<String, List<SnLoteBean>>();
							
		
		String[] loteArray = ((String) dynaBean.get("lstLoteGravar")).split("\\|");		
				

		for (int i = 0; i < loteArray.length; i++) {

			String[] campos = loteArray[i].split("\\_");
			Integer idLote = Integer.parseInt(campos[0]);
			String cidContrato = campos[1];

			// Cria lote
			SnLoteBean snLoteBean = new SnLoteBean();
			snLoteBean.setIdLote(idLote);

			if (mapCidContrato.containsKey(cidContrato)) {

				List<SnLoteBean> listLote = mapCidContrato.get(cidContrato);
				listLote.add(snLoteBean);
				
			} else {

				List<SnLoteBean> listLote = new ArrayList<SnLoteBean>();
				listLote.add(snLoteBean);

				mapCidContrato.put(cidContrato, listLote);

			}

		}				
	
		String strIdFilaInserida = "";
		int i = 0;

		Iterator itCidContrato = mapCidContrato.keySet().iterator();
		
	
			// Itera a colecao por cidade operadora
			while(itCidContrato.hasNext()) {
				
				String cidContrato = (String) itCidContrato.next();
				
				List<SnLoteBean> listLote = mapCidContrato.get(cidContrato);								 			
				
				try{								
					//Pesquisa cidade operadora para a cidade do contrato	
					SnCidadeOperadoraBean snCidadeOperadoraBean = this.obterCidadeOperadora(cidContrato);
		
					boolean isTaskOn = this.usarTask(snCidadeOperadoraBean);											
		
						// Insere tarefa para processamento das baixas por cidade
						// caso cidede não esteja parametrizado para geração, lança exceção. 		
					
						for (SnLoteBean snLoteBean: listLote){
							
							if (!isTaskOn) {																			
								throw new EmissaoBusinessResourceException(EmissaoResources.NULL_PARAMETER, 
								new Object[] { SERVIDOR_ATIVO, "Cidade: " + cidContrato});								
							}	
							
							
							// Insere a tafefa para processamento da baixa do lote				
							Long idFilaInserida = this.criarTarefaLotesBaixaOnline(snLoteBean);							
							
							// Conta numero de tarefas criadas
							i++;
			
							strIdFilaInserida += idFilaInserida.toString() + ", ";	
							
						}																																										
				
				}catch (EmissaoBusinessResourceException e){											
					
					log.error(new BasicAttachmentMessage("cidade [" + cidContrato + "]" + " não está parametrizada "
							+ "para utilizar servidor de fila.", AttachmentMessageLevel.ERROR), e);															
				}
	
			}
													
							
			// Remove ultimo caracter pipe
			if (strIdFilaInserida.length() > 0) {
				strIdFilaInserida = strIdFilaInserida.substring(0, strIdFilaInserida.length() - 2);
			}	
								
			dynaBean.set(DYNA_PROPERTY_ID_FILA_TASK, strIdFilaInserida);			
			dynaBean.set(DYNA_PROPERTY_QTDE_TASK, i);
						
				
		return dynaBean;
				
	}
			
	

	private Long criarTarefaLotesBaixaOnline(SnLoteBean snLoteBean) {
		
		Integer idLote = snLoteBean.getIdLote();
		
		// Busca a situacao de processamento do lote Agendado
		SnSituacaoProcessamentoBean situacaoAgendado = this.obterSituacaoProcessamento(SnSituacaoProcessamentoBean.Sigla.AGENDADO.getSigla());
				
			
		//Busca lote para efetuar update
		snLoteBean = (SnLoteBean) this.find(snLoteBean);
		snLoteBean.setSituacaoProcessamento(situacaoAgendado);
			
		//Realiza a atualização da situação
		this.update(snLoteBean, getUserSession().getCurrentDbService());
							
		
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put(PARAM_LOTES, idLote.toString());
		parameters.put(PARAM_TIPO_LOTE, SnTipoLoteBean.Sigla.EMISCCONL.getChaveSigla());				
		
		//Insere tarefa para processar o conjunto de lote e processar a baixa
		Long idFilaInserida = this.insertTask(parameters, TASK_EMISSAO_BAIXA_ONLINE_CC, "Lote = " + idLote.toString());			

		return idFilaInserida;		
	}

	/**
	 * Metodo responsavel pela busca dos lotes pendentes conforme tipo de lote e situacao do processamento
	 * 
	 * @author matorres
	 * @since 10/09/2007
	 * @param
	 * @return
	 * @ejb.interface-method view-type = "both"
	 */
	public List<SnLoteBean> selecionaLotePendente(String siglaSituacaoProcessamento, String siglaTipoLote){

		// Busca a situacao de processamento do lote Agendado
		SnSituacaoProcessamentoBean situacaoAgendado = this.obterSituacaoProcessamento(siglaSituacaoProcessamento);
		DynamicBean beanParam = new DynamicBean();
		beanParam.set("idSituacaoProcessamento", situacaoAgendado.getIdSituacaoProcessamento());

		if (EMISSAO_PRINT_BOLETO_AVULSO.getChaveSigla().equals(siglaTipoLote)) {
			beanParam.set("idsTipoLote", this.obterTiposLotes(siglaTipoLote));
			return super.search("selecionaLotePendenteByTiposLote", beanParam, false);
		}

		beanParam.set("idTipoLote", this.obterTipoLote(siglaTipoLote).getIdTipoLote());
		return super.search("selecionaLotePendente", beanParam, false);

	}

	private List obterTiposLotes(String siglaTipoLote){

		DetachedCriteria criteria = DetachedCriteria.forClass(SnTipoLoteBean.class);
		criteria.add(Restrictions.eq("sgTipoLote", siglaTipoLote));
        ////NSMSP_172250_NI_003
		criteria.addOrder(Order.asc("idTipoLote"));
		List lstSnTipoLoteBean = (List) getCurrentDAO().select(criteria);
		return lstSnTipoLoteBean;
	}

	/**
	 * Metodo responsavel pela execucao das tarefas CriarTarefaLotesPrintHousePrimViaTask,
	 * CriarTarefaLotesPrintHouseReemissaoNFTask e CriarTarefaLotesPrintHouseSegViaTask na qual cria uma tarefa
	 * GerarArquivoPrintHouseTask para cada conjunto de lotes agrupados.
	 * 
	 * @author matorres
	 * @since 10/09/2007
	 * @param listLote
	 *            Lista de lotes pendentes para processar e gerar arquivo print house.
	 * @param siglaTipoLote
	 *            Sigla do tipo de lote.
	 * @return
	 * 
	 * @ejb.interface-method view-type="both"
	 * @ejb.transaction type="Required"
	 */
	public Long criarTarefaLotesPrintHouseTask(List<SnLoteBean> listLote, String siglaTipoLote){

		String lotes = "";

		// Busca a situacao de processamento do lote Agendado
		SnSituacaoProcessamentoBean situacaoAgendado = this.obterSituacaoProcessamento(SnSituacaoProcessamentoBean.Sigla.AGENDADO.getSigla());

		for (SnLoteBean snLoteBean : listLote) {

			// Busca lote para poder efetuar update
			snLoteBean = (SnLoteBean) this.find(snLoteBean);
			snLoteBean.setSituacaoProcessamento(situacaoAgendado);

			// Atualiza situacao para agendado
			this.update(snLoteBean, getUserSession().getCurrentDbService());

			// Monta lista de lotes separada por pipe
			lotes += snLoteBean.getIdLote() + "|";

		}

		// Remove ultimo caractere pipe
		if (lotes.length() > 0) {
			lotes = lotes.substring(0, lotes.length() - 1);
		}

		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put(PARAM_LOTES, lotes);
		parameters.put(PARAM_TIPO_LOTE, siglaTipoLote);

		// Ajusta o tamanho da observação
		String obs = "Lotes = " + lotes;
		if (obs.length() >= 500) {
			obs = obs.substring(0, 480) + "..." + obs.substring(obs.length() - 16, obs.length());
		}

		// Insere a tarefa para processar o conjunto de lote e gerar os arquivos correspondentes
		Long idFilaInserida = this.insertTask(parameters, TASK_EMISSAO_ARQUIVO_PRINT_HOUSE, obs);

		return idFilaInserida;
	}

	/**
	 * Metodo responsavel pela execucao da tarefa GerarArquivoPrintHouseTask, na qual gera os arquivos printhouse,
	 * segunda via e copia da nota fiscal para os lotes recebidos como parametro.
	 * 
	 * @author matorres
	 * @since 08/09/2007
	 * @param lotes
	 *            String[] contendo a lista de lotes a processar.
	 * @param tipoLote
	 *            tipo do lote a ser processado.
	 * @return
	 * 
	 * @ejb.interface-method view-type="both"
	 * @ejb.transaction type="Required"
	 */
	public List<ArquivoRemessaTransporteDTO> gerarArquivoPrintHouseTask(String[] lotes, String tipoLote){

		DynamicBean beanParam = null;
		List<SnLoteBean> lotesPendentes = new ArrayList<SnLoteBean>();
		for (String lote : lotes) {
			beanParam = new DynamicBean();
			beanParam.put("idLote", Integer.parseInt(lote));
			SnLoteBean lotePendente = (SnLoteBean) super.search("selecionaLoteById", beanParam, false).get(0);
			lotesPendentes.add(lotePendente);
		}

		ArquivoPrintHouseService arquivoPrintHouseService = super.getService(ArquivoPrintHouseService.class);
		List<ArquivoRemessaTransporteDTO> arquivos = (List<ArquivoRemessaTransporteDTO>) arquivoPrintHouseService.gerarArquivoPrintHouse(lotesPendentes, tipoLote);

		return arquivos;

	}

	/**
	 * Metodo responsavel pela execucao da tarefa CriarTarefaLoteCNABTask, na qual cria uma tarefa GerarArquivoCNABTask
	 * para o lote informado.
	 * 
	 * @author matorres
	 * @since 10/09/2007
	 * @param snLoteBean
	 *            Lote para gerar arquivo
	 * @return
	 * 
	 * @ejb.interface-method view-type="both"
	 * @ejb.transaction type="Required"
	 */
	public Long criarTarefaLoteCNABTask(SnLoteBean snLoteBean){

		// Busca a situacao de processamento do lote Agendado
		SnSituacaoProcessamentoBean situacaoAgendado = this.obterSituacaoProcessamento(SnSituacaoProcessamentoBean.Sigla.AGENDADO.getSigla());

		// Busca lote para poder efetuar update
		snLoteBean = (SnLoteBean) this.find(snLoteBean);
		snLoteBean.setSituacaoProcessamento(situacaoAgendado);

		// Atualiza situacao para agendado
		this.update(snLoteBean, getUserSession().getCurrentDbService());

		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put(PARAM_LOTE, Integer.toString(snLoteBean.getIdLote()));

		// Insere a tarefa para processar o lote e gerar os arquivos correspondentes
		Long idFilaInserida = this.insertTask(parameters, TASK_EMISSAO_ARQUIVO_CNAB, "Lote = " + Integer.toString(snLoteBean.getIdLote()));

		return idFilaInserida;
	}

	/**
	 * Metodo responsavel pela execucao da tarefa CriarTarefaLoteRDCCTask, na qual cria uma tarefa GerarArquivoRDCCTask
	 * para o lote informado.
	 * 
	 * @author matorres
	 * @since 25/09/2007
	 * @return
	 * 
	 * @ejb.interface-method view-type="both"
	 * @ejb.transaction type="Required"
	 */
	public Long criarTarefaLoteRDCCTask(){

		// Busca a situacao de processamento do lote Agendado
		// SnSituacaoProcessamentoBean situacaoAgendado =
		// this.obterSituacaoProcessamento(SnSituacaoProcessamentoBean.Sigla.AGENDADO.getSigla());

		HashMap<String, String> parameters = new HashMap<String, String>();

		// Insere a tarefa para processar o lote e gerar os arquivos correspondentes
		Long idFilaInserida = this.insertTask(parameters, TASK_EMISSAO_ARQUIVO_RDCC, null);

		return idFilaInserida;

	}

	private SnSituacaoProcessamentoBean obterSituacaoProcessamento(String sigla){

		DynamicBean beanParam = new DynamicBean();
		beanParam.set("sgSituacaoProcessamento", sigla);

		// Busca a situacao de processamento do lote Agendado
		SnSituacaoProcessamentoBean situacaoAgendado = new SnSituacaoProcessamentoBean();
		situacaoAgendado = (SnSituacaoProcessamentoBean)super.search("lstSnSituacaoProcessamentoBySigla", beanParam, false).get(0);

		return situacaoAgendado;

	}

	private SnTipoLoteBean obterTipoLote(String sgTipoLote){

		DetachedCriteria criteria = DetachedCriteria.forClass(SnTipoLoteBean.class);
		criteria.add(Restrictions.eq("sgTipoLote", sgTipoLote));
        ////NSMSP_172250_NI_003
        criteria.addOrder(Order.asc("idTipoLote"));
		List<SnTipoLoteBean> lstSnTipoLoteBean = (List<SnTipoLoteBean>) getCurrentDAO().select(criteria);

		SnTipoLoteBean snTipoLoteBean = null;
		if (lstSnTipoLoteBean != null && lstSnTipoLoteBean.size() > 0) {
			snTipoLoteBean = lstSnTipoLoteBean.get(0);
		}

		return snTipoLoteBean;

	}

	/**
	 * Metodo responsavel pela execucao da tarefa GerarArquivoCNABTask, na qual gera os arquivos CNAB para o lote
	 * recebidos como parametro.
	 * 
	 * @author matorres
	 * @since 11/09/2007
	 * @param lote
	 *            Lote para processar.
	 * @return
	 * 
	 * @ejb.interface-method view-type="both"
	 * @ejb.transaction type="Required"
	 */
	public List<ArquivoRemessaTransporteDTO> gerarArquivoCNABTask(Integer lote){

		// Monta lista de parametros para a procedure
		BatchParameter[] parameters = new BatchParameter[2];
		parameters[0] = new BatchParameter(lote, Types.NUMERIC, false);
		parameters[1] = new BatchParameter(Types.NUMERIC, true);

		// Efetua chamada da procedure conforme parametros
		List list = this.getCurrentDAO().executeBatchTransactionSuspended("{call CNAB_REMESSA_NF.IniciaRemessa(?, ?)}", parameters);

		if (list != null) {

			Long idControleArquivo = Long.parseLong(list.get(1).toString());

			// Busca controle arquivo
			SnControleArquivoBean snControleArquivoBean = new SnControleArquivoBean();
			snControleArquivoBean.setIdControleArquivo(idControleArquivo);
			snControleArquivoBean = (SnControleArquivoBean) this.find(snControleArquivoBean);

			// Cria arquivo remessa transporte DTO com idControleArquivo e cidContrato
			ArquivoRemessaTransporteDTO transporteDTO = new ArquivoRemessaTransporteDTO();
			transporteDTO.setIdControleArquivo(idControleArquivo);
			transporteDTO.setCidContrato(snControleArquivoBean.getCidContrato().getCidContrato());

			List<ArquivoRemessaTransporteDTO> listArquivo = new ArrayList<ArquivoRemessaTransporteDTO>();
			listArquivo.add(transporteDTO);

			return listArquivo;

		}

		return null;

	}

	/**
	 * Metodo responsavel pela execucao da tarefa GerarArquivoRDCCTask, na qual gera os arquivos RDCC para o lote
	 * recebidos como parametro.
	 * 
	 * @author matorres
	 * @since 28/09/2007
	 * @return
	 * 
	 * @ejb.interface-method view-type="both"
	 * @ejb.transaction type="Required"
	 */
	public List<ArquivoRemessaTransporteDTO> gerarArquivoRDCCTask(){

		// Monta lista de parametros para a procedure
		BatchParameter[] parameters = new BatchParameter[] {
		        new BatchParameter(null, Types.NUMERIC, false)
		        , new BatchParameter(null, Types.NUMERIC, false)
		        , new BatchParameter(null, Types.VARCHAR, true)
		};

		// Efetua chamada da procedure conforme parametros
		List list = this.getCurrentDAO().executeBatchTransactionSuspended("{call CNAB_REMESSA_NF.IniciaRemessaRDCC(?, ?, ?)}", parameters);

		if (list != null && list.size() == 3 && list.get(2) != null) {

			String[] lstIdControleArquivo = list.get(2).toString().split("\\|");

			List<ArquivoRemessaTransporteDTO> listArquivo = new ArrayList<ArquivoRemessaTransporteDTO>();
			for (String idControleArquivo : lstIdControleArquivo) {

				if (idControleArquivo != null && !idControleArquivo.equals("")) {

					// Busca controle arquivo
					SnControleArquivoBean snControleArquivoBean = new SnControleArquivoBean();
					snControleArquivoBean.setIdControleArquivo(new Long(idControleArquivo));
					snControleArquivoBean = (SnControleArquivoBean) this.find(snControleArquivoBean);

					// Cria arquivo remessa transporte DTO com idControleArquivo e cidContrato
					ArquivoRemessaTransporteDTO transporteDTO = new ArquivoRemessaTransporteDTO();
					transporteDTO.setIdControleArquivo(new Long(idControleArquivo));
					transporteDTO.setCidContrato(snControleArquivoBean.getCidContrato().getCidContrato());

					listArquivo.add(transporteDTO);

				}
			}

			return listArquivo;

		}

		return null;

	}

	/**
	 * Metodo responsavel por criar a tarefa de transporte dos arquivos gerados.
	 * 
	 * @author matorres
	 * @since 08/09/2007
	 * @param transportesArquivo
	 *            Lista dos arquivos para transportar.
	 * @return
	 * 
	 * @ejb.interface-method view-type="both"
	 * @ejb.transaction type="Required"
	 */
	public void transportaArquivos(List<ArquivoRemessaTransporteDTO> transportesArquivo){

		if (transportesArquivo != null && transportesArquivo.size() > 0) {

			for (Iterator<ArquivoRemessaTransporteDTO> iterator = transportesArquivo.iterator(); iterator.hasNext();) {
				ArquivoRemessaTransporteDTO arquivoRemTransDTO = iterator.next();

				if (arquivoRemTransDTO != null) {

					Long idControleArquivo = arquivoRemTransDTO.getIdControleArquivo();
					String cidContrato = arquivoRemTransDTO.getCidContrato();

					if (idControleArquivo != null) {

						HashMap<String, String> parameters = new HashMap<String, String>();
						parameters.put(ID_CONTROLE_ARQUIVO, idControleArquivo.toString());
						parameters.put(CID_CONTRATO, cidContrato);

						String obs = "ID_CONTROLE_ARQUIVO = " + idControleArquivo.toString() + " - CID_CONTRATO = " + cidContrato;  

						Long idFilaInserida = this.insertTask(parameters, TASK_TRANSPORTE, obs);

					}

				}

			}

		}

	}

	/**
	 * Metodo responsavel por obter os dbServices disponiveis e ativos.
	 * 
	 * @author matorres
	 * @since 25/09/2007
	 * @return
	 * 
	 * @ejb.interface-method view-type="both"
	 * 
	 */
	public String[] getActiveDbServices(){

		// Obtem as bases disponiveis
		String[] dbServices = super.getUserSession().getSelectedDbServices();

		// Obtem apenas as bases ativas
		dbServices = super.getAvailableDBServices(dbServices);

		return dbServices;

	}

	/**
	 * @author matorres
	 * @since 10/09/2007
	 * 
	 */
	private Long insertTask(HashMap<String, String> parameters, String task, String observacao){

		String currentDBService = super.getUserSession().getCurrentDbService();
		String currentDBIdentifier = currentDBService.substring(GeralConstants.PREFIXO_DATABASE_SERVICE.length());
		Long idFila = (Long) super.getPrincipalProperties().get(TaskConstants.ID_FILA);

		TaskService taskService = super.getService(TaskService.class);
		Long idFilaInserida = taskService.submitTaskToFila(task, parameters, super.getUserSession().getUserId(), currentDBIdentifier, idFila, observacao);

		return idFilaInserida;

	}

	private Collection getCollectionFromStringArray(String stringWithPipe){
		return this.getCollectionFromStringArray(stringWithPipe, String.class, null);
	}

	private Collection getCollectionFromStringArray(String stringWithPipe, Class classType, Integer group){

		Collection col = null;

		if (stringWithPipe != null && !stringWithPipe.equals("")) {

			col = new ArrayList();

			String[] stringArray = stringWithPipe.split("\\|");

			if (stringArray != null && stringArray.length > 0) {

				for (int i = 0; i < stringArray.length; i++) {

					String content = null;
					if (group != null) {
						String[] groupArray = stringArray[i].split("\\_");
						if (Integer.parseInt(groupArray[0]) == group) {
							content = groupArray[1];
						}
					}
					else {
						content = stringArray[i];
					}

					if (content != null) {
						if (classType.equals(Integer.class)) {
							col.add(new Integer(content));
						}
						else {
							col.add(content);
						}
					}

				}

			}

		}

		return col;

	}

	/**
	 * Obtem a cidade operadora para a cidade do contrato especificada no parametro
	 * 
	 * @param cidContrato
	 * @return cidade operadora pesquisada
	 */
	@SuppressWarnings("unchecked")
	private SnCidadeOperadoraBean obterCidadeOperadora(String cidContrato){

		SnCidadeOperadoraBean snCidadeOperadoraBean = new SnCidadeOperadoraBean();
		snCidadeOperadoraBean.setCidContrato(cidContrato);

		List<SnCidadeOperadoraBean> cidades = super.search(
		                                                   "lstSnCidadeOperadoraById", snCidadeOperadoraBean);

		if (cidades != null && cidades.size() > 0) {

			snCidadeOperadoraBean = cidades.get(0);

			return snCidadeOperadoraBean;

		}

		return null;

	}

	/**
	 * Obtem parametro especificado no parametro
	 * 
	 * @param snCidadeOperadoraBean
	 * @param nomeParametro
	 * @return parametro pesquisado
	 */
	@SuppressWarnings("unchecked")
	private SnParametroBean obterParametro(SnCidadeOperadoraBean snCidadeOperadoraBean, String nomeParametro){

		DetachedCriteria criteria = DetachedCriteria.forClass(SnParametroBean.class);
		criteria.createAlias("empresa", "empresa");
		criteria.add(Restrictions.eq("empresa.cidContrato", snCidadeOperadoraBean.getCidContrato()));
		criteria.add(Restrictions.eq("nomeParametro", nomeParametro));
		List<SnParametroBean> lstParametro = (List<SnParametroBean>) getCurrentDAO().select(criteria);

		SnParametroBean snParametroBean = new SnParametroBean();
		if (lstParametro != null && !lstParametro.isEmpty() && lstParametro.size() == 1) {

			snParametroBean = (SnParametroBean) lstParametro.iterator().next();

			return snParametroBean;

		}

		return null;

	}

	/**
	 * Metodo responsavel pela busca dos lotes pendentes conforme sigla do tipo de lote e situacao do processamento
	 * 
	 * @author matorres
	 * @since 11/12/2007
	 * @param
	 * @return
	 * @ejb.interface-method view-type = "both"
	 */
	public List<SnLoteBean> selecionaLotePendentePorSigla(String siglaSituacaoProcessamento, String siglaTipoLote){

		DetachedCriteria criteria = DetachedCriteria.forClass(SnLoteBean.class);
		criteria.createAlias("snTipoLoteBean", "snTipoLoteBean");
		criteria.createAlias("situacaoProcessamento", "situacaoProcessamento");
		criteria.add(Restrictions.eq("snTipoLoteBean.sgTipoLote", siglaTipoLote));
		criteria.add(Restrictions.eq("situacaoProcessamento.sgSituacaoProcessamento", siglaSituacaoProcessamento));
		List<SnLoteBean> listaLote = (List<SnLoteBean>) getCurrentDAO().select(criteria);

		return listaLote;

	}

	/**
	 * Atualiza situacao do lote
	 * 
	 * @author matorres
	 * @since 29/11/2007
	 * @param snLoteBean
	 *            SnLoteBean
	 * @return
	 * 
	 * @ejb.interface-method view-type = "both"
	 * @ejb.transaction type="Required"
	 */
	public void atualizarSituacaoLote(SnLoteBean snLoteBean, SnSituacaoProcessamentoBean sitProcessando){

		snLoteBean.setSituacaoProcessamento(sitProcessando);

		// Atualiza situacao para processando
		this.update(snLoteBean, getUserSession().getCurrentDbService());

	}

	/**
	 * Metodo responsavel pela execucao da tarefa GerarArquivoRDCCTask, na qual gera os arquivos RDCC para o lote
	 * recebidos como parametro.
	 * 
	 * @author matorres
	 * @since 25/09/2007
	 * @return
	 * 
	 * @ejb.interface-method view-type="both"
	 * @ejb.transaction type="Required"
	 */

	public List<ArquivoRemessaTransporteDTO>  gerarArquivoPrintHouseTesteMensagem(List<String> criterios , Date dataVencimento, String tipoMsg){
		

		ArquivoPrintHouseService arquivoPrintHouseService = super.getService(ArquivoPrintHouseService.class);
		return arquivoPrintHouseService.gerarArquivoPrintHouseTesteMensagem(criterios, dataVencimento, tipoMsg);

	}

	/**
	 * Metodo que gera os arquivos print para lotes de boleto avulso pendentes.
	 * 
	 * @author pestana
	 * @since 26/10/2017
	 * @return void
	 * 
	 * @ejb.interface-method view-type="both"
	 * @ejb.transaction type="Required"
	 */
	public void geraArquivoPrintBoletoAvulso(Integer maxTreads){

		if (maxTreads > 0) {
			geraArquivoPrintBoletoAvulsoThreads(maxTreads);
		}
		
		geraArquivoPrintBoletoAvulsoSequencial();

	}

	/**
	 * Metodo que gera os arquivos print com processamento paralelo.
	 * @param Integer maxTreads - Numero de Threads para processamento paralelo
	 * @author pestana
	 * @since 26/10/2017
	 * @return void
	 * 
	 */
	private void geraArquivoPrintBoletoAvulsoThreads(Integer maxTreads){
		
		ExecutorService executor = Executors.newFixedThreadPool(maxTreads);
		CompletionService<SnLoteBean> tasksCompletionService = new ExecutorCompletionService<SnLoteBean>(executor);

		List<SnLoteBean> lotes = selecionaLotePendente(PENDENTE.getSigla(),
		                                               EMISSAO_PRINT_BOLETO_AVULSO.getChaveSigla());
		if (!CollectionUtils.isEmpty(lotes)) {

			List<SnLoteBean> lotesPendentes = new ArrayList<SnLoteBean>();
			for (SnLoteBean lote : lotes) {
				SnLoteBean lts = (SnLoteBean) super.findByPrimaryKey(lote);
				lotesPendentes.add(lts);
			}

			int qtdLotes = 0; 
			List<SnCidadeOperadoraBean> listaCidades = getListaCidadesLotes(lotesPendentes);
			for (SnCidadeOperadoraBean cidade : listaCidades) {
				List<SnLoteBean> lotesByCidade = getLotesByCidade(lotes, cidade);
				for (SnLoteBean snLoteBean : lotesByCidade) {
					
					log.info(String.format("Iniciando o processamento do lote %1$d de forma PARALELA",snLoteBean.getIdLote()));
					EmissaoService service = super.getService(EmissaoService.class);
					Callable<SnLoteBean> worker = new LoteBoletoAvulsoCallable(service, snLoteBean);
					tasksCompletionService.submit(worker);
					qtdLotes ++;
				}
				
				
			}

			SnLoteBean retorno = new SnLoteBean();
			for (int i=0; i < qtdLotes; ++i) {
				try {
					retorno = tasksCompletionService.take().get();
					log.info(String.format("Lote %1$d processado de forma PARALELA com SUCESSO.",retorno.getIdLote()));
				} catch (Exception e) {
					log.info(String.format("Erro ao processar o lote %1$d de forma PARALELA. %2$d",retorno.getIdLote(),e.getCause()));
				}
			}	
			executor.shutdown();
		}
    }
	
	private void geraArquivoPrintBoletoAvulsoSequencial(){

		List<SnLoteBean> lotes = selecionaLotePendente(PENDENTE.getSigla(),
		                                               EMISSAO_PRINT_BOLETO_AVULSO.getChaveSigla());

		if (!CollectionUtils.isEmpty(lotes)) {

			List<SnLoteBean> lotesPendentes = new ArrayList<SnLoteBean>();
			for (SnLoteBean lote : lotes) {
				SnLoteBean lts = (SnLoteBean) super.findByPrimaryKey(lote);
				lotesPendentes.add(lts);
			}

			List<SnCidadeOperadoraBean> listaCidades = getListaCidadesLotes(lotesPendentes);
			for (SnCidadeOperadoraBean cidade : listaCidades) {
				List<SnLoteBean> lotesByCidade = getLotesByCidade(lotes, cidade);
				for (SnLoteBean snLoteBean : lotesByCidade) {

					EmissaoService service = super.getService(EmissaoService.class);
					service.processaLoteBoletoAvulso(snLoteBean);
				}
			}

		}
	}

	/**
	 * Metodo que gera os arquivos print para lotes de boleto avulso pendentes.
	 * 
	 * @author pestana
	 * @since 26/10/2017
	 * @return List<ArquivoRemessaTransporteDTO>
	 * 
	 * @ejb.interface-method view-type="both"
	 * @ejb.transaction type="RequiresNew"
	 */
	public void processaLoteBoletoAvulso(SnLoteBean lote){
		ArquivoPrintHouseService arquivoPrintHouseService = super.getService(ArquivoPrintHouseService.class);
		try {
			log.info(new BasicAttachmentMessage("Alteração da situação do lote " + lote.getIdLote()
			        + " para PROCESSANDO.", AttachmentMessageLevel.INFO));
			arquivoPrintHouseService.atualizarSituacaoLote(lote, PROCESSANDO.getSigla());

			List<ArquivoRemessaTransporteDTO> arqTransporte = new ArrayList<ArquivoRemessaTransporteDTO>();

			arqTransporte.addAll(gerarArquivoPrintHouseTask(new String[] { String.valueOf(lote.getIdLote()) },
			                                                EMISSAO_PRINT_BOLETO_AVULSO.getChaveSigla()));

			log.info(new BasicAttachmentMessage("Alteração da situação do lote " + lote.getIdLote()
			        + " para FINALIZADO.", AttachmentMessageLevel.INFO));
			arquivoPrintHouseService.atualizarSituacaoLote(lote, FINALIZADO.getSigla());

			transportaArquivos(arqTransporte);
		} catch (Exception ex) {
			log.info(new BasicAttachmentMessage("Alteração da situação do lote " + lote.getIdLote()
			        + " para PENDENTE.", AttachmentMessageLevel.INFO));
			arquivoPrintHouseService.atualizarSituacaoLote(lote, PENDENTE.getSigla());
		}

	}

	/**
	 * Metodo filtra e retorna os lotes de uma operadora.
	 * 
	 * @author pestana
	 * @since 26/10/2017
	 * @param List
	 *            <SnLoteBean> Conjunto de Lotes
	 * @param SnCidadeOperadoraBean
	 *            cidade
	 * @return List<SnLoteBean>
	 * 
	 */
	private List<SnLoteBean> getLotesByCidade(List<SnLoteBean> lotesPendentes,
	                                          SnCidadeOperadoraBean cidade){

		List<SnLoteBean> lotesCidade = new ArrayList<SnLoteBean>();

		for (SnLoteBean lote : lotesPendentes) {
			if (lote.getSnCidadeOperadoraBean() != null &&
			        lote.getSnCidadeOperadoraBean().equals(cidade)) {
				lotesCidade.add(lote);
			}
		}
		return lotesCidade;
	}

	/**
	 * Metodo que retrna todas as operadoras contidas em um conjunto de lotes
	 * 
	 * @author pestana
	 * @since 26/10/2017
	 * @param List
	 *            <SnLoteBean> Conjunto de Lotes
	 * @return List<SnCidadeOperadoraBean>
	 */
	private List<SnCidadeOperadoraBean> getListaCidadesLotes(List<SnLoteBean> lotesPendentes){
		List<SnCidadeOperadoraBean> listaCidades = new ArrayList<SnCidadeOperadoraBean>();
		for (SnLoteBean snLoteBean : lotesPendentes) {
			if (!listaCidades.contains(snLoteBean.getSnCidadeOperadoraBean())) {
				listaCidades.add(snLoteBean.getSnCidadeOperadoraBean());
			}
		}
		return listaCidades;
	}

}
