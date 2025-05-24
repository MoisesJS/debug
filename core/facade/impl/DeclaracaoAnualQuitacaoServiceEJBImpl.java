package br.com.netservicos.novosms.emissao.core.facade.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.ejb.CreateException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.com.netservicos.core.bean.qd.QdCartaBean;
import br.com.netservicos.core.bean.qd.QdCobrancaQuitacaoBean;
import br.com.netservicos.core.bean.qd.QdFormaEnvioBean;
import br.com.netservicos.core.bean.qd.QdHistoricoEnvioQuitacaoBean;
import br.com.netservicos.core.bean.qd.QdStatusEnvioBean;
import br.com.netservicos.core.bean.sn.SnAssinanteBean;
import br.com.netservicos.core.bean.sn.SnBoletoBean;
import br.com.netservicos.core.bean.sn.SnCidadeOperadoraBean;
import br.com.netservicos.core.bean.sn.SnContratoBean;
import br.com.netservicos.core.bean.sn.SnContratoKey;
import br.com.netservicos.core.bean.sn.SnParceiroBean;
import br.com.netservicos.framework.core.dao.BatchParameter;
import br.com.netservicos.novosms.emissao.core.facade.DeclaracaoAnualQuitacaoService;


/**
 * <P>
 * <B> Description : </B> EJB responsável pela regra de negócio da declaração anual de débito. <BR>
 * </P>
 * <P>
 * <B> Issues : </B>
 * 
 * <PRE>
 * 
 * =============================================================================
 * Description Date By ---------------------------------------- -----------
 * ------------------------
 * =============================================================================
 * 
 * </PRE>
 * 
 * <P>
 * <B> Revision History: </B>
 * 
 * <PRE>
 * 
 * =============================================================================
 * Prior Date By Version Project/CSR Description ---------- --------------
 * -------- -------------- --------------------------- 24/07/2007 Anderson
 * de Souza Signorette N/A NovoSMS Created.
 * =============================================================================
 * 
 * </PRE>
 * 
 * @author t0049044
 * @since 18/06/2010
 * @version 1.1 $Id: DeclaracaoAnualQuitacaoEJBImpl.java,v 1.11 2009/10/27
 *          16:49:33 T5001097 Exp $
 * 
 * @ejb.bean name="DeclaracaoAnualQuitacaoServiceEJB" type="Stateless"
 *           display-name="DeclaracaoAnualQuitacaoServiceEJB"
 *           description="Contém a regra de negócio da Declaração Anual QuitacaoEJB"
 *           view-type="both"
 *           jndi-name="netservicos/ejb/emissao/DeclaracaoAnualQuitacaoServiceEJB"
 *           local-jndi-name
 *           ="netservicos/ejb/local/emissao/DeclaracaoAnualQuitacaoServiceEJB"
 *           transaction-type="Container" 
 * 
 * 
 * @ejb.interface local-extends="javax.ejb.EJBLocalObject"
 *                extends="javax.ejb.EJBObject"
 * 
 * @ejb.home local-extends="javax.ejb.EJBLocalHome" extends="javax.ejb.EJBHome"
 */
public class DeclaracaoAnualQuitacaoServiceEJBImpl extends
		AbstractEmissaoEJBImpl implements DeclaracaoAnualQuitacaoService {

	/**
	 * serial version
	 */
	private static final long serialVersionUID = -6426982828901871573L;
	/**
	 * @param contrato
	 *            .
	 */
	private SnContratoBean contrato;
	/**
	 * @param v
	 *            .
	 */	
	private SnContratoKey compositeKey;
	/**
	 * @param quitacao
	 *            .
	 */
	private QdHistoricoEnvioQuitacaoBean quitacao;
	/**
	 * @param assinanteBean
	 *            .
	 */
	private SnAssinanteBean assinanteBean;
	/**
	 * @param quitacaoBean
	 *            .
	 */
	private QdHistoricoEnvioQuitacaoBean quitacaoBean;
	/**
	 * @param cidadeOperadoraBean
	 *            .
	 */
	private SnCidadeOperadoraBean cidadeOperadoraBean;
	/**
	 * @param cobrancaQuitacao
	 *            .
	 */
	private QdCobrancaQuitacaoBean cobrancaQuitacao;
	/**
	 * @param retornoQdCobrancaBean
	 *            .
	 */
	private List<QdCobrancaQuitacaoBean> retornoQdCobrancaBean;
	/**
	 * @param NET
	 *            .
	 */
	private static final String NET = "FUNDO";	
	/**
	 * @param IMG_NET
	 *            .
	 */
	private static final String IMG_NET = "logo_nets.PNG";	
	private static final String IMG_CLARO = "logo_claro.PNG";

	/**
	 * @param PATH_IMG
	 *            .
	 */
	private static final String PATH_IMG = "common/imgs/";
	/**
	 * @param REPORT_NET_WAR
	 *            .
	 */
	private static final String REPORT_NET_WAR = "NovoSMSWeb.war";
	
	/**
	 * @param REPORT_NET_WAR
	 *            .
	 */
	private static final String REPORT_JASPER = "WEB-INF/classes/br/com/netservicos/novosms/emissao/report/" +
			"declaracao/DeclaracaoAnualdeQuitacao.jasper";
	
	
	/**
	 * @param PLSQL_CRIAR_CARTA
	 *            .
	 */
	private static final String PLSQL_CRIAR_CARTA = "{call PROD_JD.PGSMS_QDEB_CARTA.PR_CRIA_CARTA(?,?,?)} ";
	/**
	 * @param PLSQL_TEXTO_CARTA
	 *            .
	 */
	private static final String PLSQL_TEXTO_CARTA = "{? = call PROD_JD.PGSMS_QDEB_CARTA.FN_TEXTO_2VIA_QUITACAO(?)}";
	
	/**
	 * @param PLSQL_OPERADORA_BASE
	 *            .
	 */
	private static final String PLSQL_OPERADORA_BASE = "{? = call PROD_JD.PGSMS_MODULO_MULTICIDADES.FCSMS_REGRA_EMPRESA_EXECUCAO(?, ?)}";
	
	
	/**
	 * @param logger
	 *            .
	 */
	private static final Log logger = LogFactory.getLog(DeclaracaoAnualQuitacaoServiceEJBImpl.class);
	public DeclaracaoAnualQuitacaoServiceEJBImpl() {
         super();
		assinanteBean = new SnAssinanteBean();
		cidadeOperadoraBean = new SnCidadeOperadoraBean();
	}

	/**
	 * @ejb.create-method view-type="both"
	 * @throws CreateException
	 */
	@Override
	public void ejbCreate() throws CreateException {
		super.ejbCreate();
	}

	/*
	 * Retorna List de quitações anual de débito
	 * 
	 * @param numContrato
	 * 
	 * @param cidContrato
	 * 
	 * @see
	 * br.com.netservicos.novosms.geral.core.facade.DeclaracaoAnualQuitacaoService
	 * #listarQuitacao(java.lang.Long, java.lang.String)
	 */
	/**
	 * (non-Javadoc)
	 * 
	 * @ejb.interface-method view-type="both"
	 * @ejb.transaction type="Required"
	 * Obtem lista de quitações dos parceiros
	 * @param Long numContrato and String cidContrato
	 * @return List<QdHistoricoEnvioQuitacaoBean>
	 */
	public List<QdHistoricoEnvioQuitacaoBean> listarQuitacao(Long numContrato,
			String cidContrato) {
		contrato = new SnContratoBean();
		compositeKey = new SnContratoKey(Long.valueOf(numContrato), cidContrato);
		contrato.setCompositeKey(compositeKey);
		quitacao = new QdHistoricoEnvioQuitacaoBean();
		quitacao.setContrato(contrato);
		

		setLazyPropertiesQuitacao(quitacao);

		List<Object[]> object = (List<Object[]>) search(
				QdHistoricoEnvioQuitacaoBean.LST_CONTRATO_ID_PARCEIRO,
				quitacao.getContrato(), false);

		List<QdHistoricoEnvioQuitacaoBean> quitacoesBean = new ArrayList<QdHistoricoEnvioQuitacaoBean>();

		if (object == null || object.isEmpty()){
			return null;
		}

		for (int i = 0; i < object.size(); i++) {

			quitacaoBean = (QdHistoricoEnvioQuitacaoBean) object.get(i)[0];
			// Inicializa os Objetos
			quitacao = addLinhaHistorioEnvioQuitacao();
			quitacao.setContrato(contrato);
			quitacao.setBoleto(addLinhaSnBoletoBean());
			quitacao.setCarta(addLinhaQdCartaBean());
			quitacao.setFormaEnvio(addLinhaQdFormaEnvioBean());
			quitacao.setQdStatusEnvio(addLinhaQdStatusEnvioBean());
			quitacao.setTipoParceiro(addLinhaSnParceiroBean());

			quitacao.setAnoConsolidado(quitacaoBean.getAnoConsolidado());
			quitacao.setDataEnvio(quitacaoBean.getDataEnvio());
			  if(quitacaoBean.getFormaEnvio() != null ){
			quitacao.getFormaEnvio().setDsFormaEnvio(
					quitacaoBean.getFormaEnvio().getDsFormaEnvio());
			  }
			  if(quitacaoBean.getBoleto() != null ){
			quitacao.getBoleto().setDtVencimento(
					quitacaoBean.getBoleto().getDtVencimento());
			  }
			  if(quitacaoBean.getFormaEnvio() != null ){
			quitacao.getFormaEnvio().setIdFormaEnvio(
					quitacaoBean.getFormaEnvio().getIdFormaEnvio());
			  }
			  if(quitacaoBean.getTipoParceiro() != null ){
			quitacao.getTipoParceiro().setIdParceiro(
					quitacaoBean.getTipoParceiro().getIdParceiro());
			  }
			  if(quitacaoBean.getTipoParceiro() != null ){
			quitacao.getTipoParceiro().setNmParceiro(
					quitacaoBean.getTipoParceiro().getNmParceiro());
			  }
			  if(quitacaoBean.getCarta() != null ){
			quitacao.getCarta().setIdCarta(
					quitacaoBean.getCarta().getIdCarta());
			  }

			quitacoesBean.add(quitacao);
		}

		return quitacoesBean;

	}

	/*
	 * Montagem da carta de quitacão anual de débito e retorno da carta
	 * 
	 * @param Número Contrato
	 * 
	 * @param Cidade Contrato
	 * 
	 * @param Ano Consolidado
	 * 
	 * @param Id Parceiro
	 * 
	 * @see
	 * br.com.netservicos.novosms.geral.core.facade.DeclaracaoAnualQuitacaoService
	 * #consultar(java.lang.Long, java.lang.String, java.lang.Integer,
	 * java.lang.Integer)
	 */
	/**
	 * (non-Javadoc)
	 * 
	 * @ejb.interface-method view-type="both"
	 * @ejb.transaction type="Required"
	 * Obtem e gera carta , consultas sobre carta anual de quitação de débito
	 * @param Long numContrato,String cidContrato, Integer aaConsolidado, Integer idParceiro
	 * @return QdHistoricoEnvioQuitacaoBean
	 */
	public QdHistoricoEnvioQuitacaoBean consultar(Long numContrato,
			String cidContrato, Integer aaConsolidado, Integer idParceiro) {
		contrato = new SnContratoBean();
		compositeKey = new SnContratoKey(Long.valueOf(numContrato), cidContrato);
		contrato.setCompositeKey(compositeKey);
		quitacao = new QdHistoricoEnvioQuitacaoBean();
		quitacao.setContrato(contrato);
		quitacao.setAnoConsolidado(aaConsolidado);
		quitacao.setTipoParceiro(addLinhaSnParceiroBean());
		quitacao.getTipoParceiro().setIdParceiro(idParceiro);
		quitacao.setBoleto(addLinhaSnBoletoBean());
		quitacao.setCarta(addLinhaQdCartaBean());
		quitacao.setFormaEnvio(addLinhaQdFormaEnvioBean());
		quitacao.setQdStatusEnvio(addLinhaQdStatusEnvioBean());
		quitacaoBean = new QdHistoricoEnvioQuitacaoBean();
		setLazyPropertiesQuitacao(quitacao);

		List<Object[]> objects = (List<Object[]>) search(QdHistoricoEnvioQuitacaoBean.LTS_DADOS_CLIENTE, quitacao.getContrato(), false);

		if (objects != null && !objects.isEmpty()) {
			contrato = (SnContratoBean) objects.get(0)[0];
			assinanteBean = (SnAssinanteBean) objects.get(0)[1];
			cidadeOperadoraBean = (SnCidadeOperadoraBean) objects.get(0)[2];
			}		
		List<Object> object = getListaDadosHistorico();		
		
		if (object != null && !object.isEmpty()){			
					quitacaoBean = (QdHistoricoEnvioQuitacaoBean) object.get(0);			
		}else{
			return null;
		}
		setLazyPropertiesQuitacao(quitacaoBean);

		if (quitacaoBean != null) {
			
			if (!(quitacaoBean.getCarta() != null)) {						
					QdCartaBean carta = new QdCartaBean();
					carta.setIdCarta(solicitarGeracaoCarta(quitacaoBean));
					
					quitacaoBean = new QdHistoricoEnvioQuitacaoBean();
					List<Object> qd = getListaDadosHistorico();
					if (qd != null && !qd.isEmpty())
					{
								quitacaoBean = (QdHistoricoEnvioQuitacaoBean) qd.get(0);
								quitacaoBean.setCarta(carta);
					}else{
						return null;
					}							
			}

			if (quitacaoBean.getCarta() != null) {

				quitacaoBean.setContrato(contrato);
				quitacaoBean.getContrato().setAssinante(assinanteBean);
				quitacaoBean.setCidOperadora(cidadeOperadoraBean);
				getParceiros(quitacaoBean);				
				getTextoCarta(quitacaoBean);			
				quitacao = new QdHistoricoEnvioQuitacaoBean();
				quitacao.setCobranca(new ArrayList<List<QdCobrancaQuitacaoBean>>());
				for (int i = 0; i < quitacaoBean.getAnosQuitacao().size(); i++) {

					cobrancaQuitacao = addLinhaCobrancaQuitacaoBean();
					setLazyPropertiesCobranca(cobrancaQuitacao);
					cobrancaQuitacao.setContratoBean(quitacaoBean.getContrato());
					cobrancaQuitacao.setParceiroBean(quitacaoBean.getParceiros().get(i));
					cobrancaQuitacao.setMenorAAConsolidado(quitacaoBean.getAnosQuitacao().get(i).get(0));
					cobrancaQuitacao.setMaiorAAConsolidado(quitacaoBean.getAnosQuitacao().get(i).get(
									quitacaoBean.getAnosQuitacao().get(i).size() - 1));
					addListaCobranca(quitacao);
				}
				// Inicializa os objetos;
				quitacao.setContrato(contrato);
				quitacao.setAnoConsolidado(aaConsolidado);
				quitacao.setTipoParceiro(new SnParceiroBean());
				quitacao.setBoleto(new SnBoletoBean());
				quitacao.setCarta(new QdCartaBean(null));
				quitacao.setFormaEnvio(new QdFormaEnvioBean(null));

				quitacao.setAnosQuitacao(new ArrayList<List<Integer>>());
				quitacao.getCarta().setIdCarta(quitacaoBean.getCarta().getIdCarta());
				quitacao.setDataEnvio(quitacaoBean.getDataEnvio());
				quitacao.setTextoCarta(quitacaoBean.getTextoCarta());				
				quitacao.setNumHashCode(getNumHashCode(quitacao));
				quitacao.getFormaEnvio().setIdFormaEnvio(quitacaoBean.getFormaEnvio().getIdFormaEnvio());
				quitacao.getFormaEnvio().setDsFormaEnvio(quitacaoBean.getFormaEnvio().getDsFormaEnvio());
				// ver o lista de parceiros
				quitacao.setParceiros(quitacaoBean.getParceiros());
				quitacao.setAnosQuitacao(quitacaoBean.getAnosQuitacao());
				quitacao.getContrato().getAssinante().setNomeTitular(quitacaoBean.getContrato().getAssinante().getNomeTitular());
			}
		}

	return quitacao;	
	}
	
	/**
	 * 
	 * @see seta lazy no objeto QdHistoricoEnvioQuitacaoBean
	 * 
	 */	
	public void setLazyPropertiesQuitacao(QdHistoricoEnvioQuitacaoBean quitacao)
	{
		quitacao.setLazyProperties(new String[] { "contrato","formaEnvio", "tipoParceiro", "statusEnvio", "carta",
				"boleto", "cidOperadora" });
	}
	/**
	 * 
	 * @see seta lazy no objeto QdCobrancaQuitacaoBean
	 * 
	 */	
	public void setLazyPropertiesCobranca(QdCobrancaQuitacaoBean cobrancaQuitacao)
	{
		cobrancaQuitacao.setLazyProperties(new String[] {"cobrancaParceiroBean", "cobrancaBean","contratoBean", "parceiroBean",
		"sitCobrancaBean" });
	}
	
	
	
	/**
	 * 
	 * 
	 * 
	 * @param QdHistoricoEnvioQuitacaoBean
	 * @see Adiciona meses na qdCobranca
	 * 
	 */
	public void addListaCobranca(QdHistoricoEnvioQuitacaoBean quitacao  ){
		List<Object[]> subJudice = search(QdCobrancaQuitacaoBean.LST_MESES_SUB_JUDICE,
				cobrancaQuitacao, false);

		if (subJudice != null && !subJudice.isEmpty()) {
			retornoQdCobrancaBean = new ArrayList<QdCobrancaQuitacaoBean>();

			for (int j = 0; j < subJudice.size(); j++) {

				retornoQdCobrancaBean
						.add((QdCobrancaQuitacaoBean) subJudice
								.get(j)[0]);

			}

			quitacao.getCobranca().add(retornoQdCobrancaBean);
		}
		}

	
	/**
	 * 
	 * @param List liParams
	 * @return  BatchParameter[]
	 */	
	private BatchParameter[] convertListToBatchParameter(List liParams) {
		// Converte a lista de parametros para um array
		int size = liParams.size();
		return (BatchParameter[]) liParams.toArray(new BatchParameter[size]);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @ejb.interface-method view-type="both"
	 * @ejb.transaction type="Required"
	 * Obtem e gera carta , consultas e pdf sobre carta anual de quitação de débito 
	 * @param Long numContrato, String cidContrato, Integer aaConsolidado, Integer idParceiro
	 * @return byte[]
	 */
	public byte[] gerarPDF(Long numContrato, String cidContrato,
			Integer aaConsolidado, Integer idParceiro) {
		QdHistoricoEnvioQuitacaoBean quitacaoBean = null;
		logger.info("Gerando pdf e consulta quitações");
		
		quitacaoBean = consultar(numContrato, cidContrato, aaConsolidado,
				idParceiro);
		String operadora = null;
		
		try {
		
			List<BatchParameter> listaP = new ArrayList<BatchParameter>();
			listaP.add(new BatchParameter(Types.VARCHAR, true));
			listaP.add(new BatchParameter("CID_CONTRATO", Types.VARCHAR, false));
			listaP.add(new BatchParameter(cidContrato,    Types.VARCHAR, false));
			BatchParameter[] parametersB = convertListToBatchParameter(listaP);
			operadora = (String) getCurrentDAO().executeBatch(PLSQL_OPERADORA_BASE, parametersB).get(0);
			if (operadora == null){
				operadora = "NET";
			}
		} catch (Exception e) {
			operadora = "NET";
		}
		
		
		URL url = DeclaracaoAnualQuitacaoServiceEJBImpl.class.getResource("DeclaracaoAnualQuitacaoServiceEJBImpl.class");
		String path = url.toString().substring(4,url.toString().lastIndexOf("NET"));
	
		
		ZipFile arquivo = null;
		InputStream input = null;
		InputStream img_fundo = null;
		try {
			ZipEntry zipfundo = null;
			arquivo = new ZipFile(path + REPORT_NET_WAR);
			ZipEntry zipentry = arquivo.getEntry(REPORT_JASPER);
			if (operadora.equalsIgnoreCase("CLARO")){
				zipfundo = arquivo.getEntry(PATH_IMG + IMG_CLARO);
			}else{
				zipfundo = arquivo.getEntry(PATH_IMG + IMG_NET);
			}	
					
			
			
			input = arquivo.getInputStream(zipentry);
			img_fundo = arquivo.getInputStream(zipfundo);
			
		} catch (IOException e) {
			
			logger.error("Erro ao encontrar o arquivo!!!", e);
		}
		
		
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(NET, img_fundo);
		
		
		List<QdHistoricoEnvioQuitacaoBean> lista = new ArrayList<QdHistoricoEnvioQuitacaoBean>();
		lista.add(quitacaoBean);
	  
		

		try {
			JRBeanCollectionDataSource data = new JRBeanCollectionDataSource(
					lista);
			JasperPrint jp = JasperFillManager.fillReport(input , parameters, data);
			return JasperExportManager.exportReportToPdf(jp);

		} catch (JRException e) {

			logger.error("Erro jasper reports Declaração anual",e);
		}
		return new byte[0];

		
		
	}
	
	
	/**
	 * (non-Javadoc)
	 * 
	 * @ejb.interface-method view-type="local"
	 * @ejb.transaction type="Required"
	 * 
	 * @return Long id_carta
	 * @param QdHistoricoEnvioQuitacaoBean
	 */
	public Long solicitarGeracaoCarta(QdHistoricoEnvioQuitacaoBean historicoEnvioBean)
	{
		logger.info("Criando Carta");
		List retorno;
		List<BatchParameter> lista = new ArrayList<BatchParameter>();
		
		if(historicoEnvioBean.getBoleto() != null){
			lista.add(new BatchParameter(quitacaoBean.getBoleto().getIdBoleto(), Types.NUMERIC, false));
			lista.add(new BatchParameter(Types.NUMERIC, true));
			lista.add(new BatchParameter(Types.VARCHAR, true));
		}else{
			return null;	
		}
		
		BatchParameter[] parameters = convertListToBatchParameter(lista);
		
				
		retorno = getCurrentDAO().executeBatchTransactionSuspended(PLSQL_CRIAR_CARTA, parameters);
		
		super.getCurrentDAO().flush();
		
		return Long.valueOf(retorno.get(1).toString());
		
	}
	/**
	 * 
	 * 
	 * @return QdHistoricoEnvioQuitacaoBean.LTS_DADOS_HISTORICO popula o bean QdHistoricoEnvioQuitacaoBean
	 */
	@SuppressWarnings("unchecked")
	private List<Object> getListaDadosHistorico()
	{logger.info("Populando bean QdHistoricoEnvioQuitacaoBean, executando query LTS_DADOS_HISTORICO ");
		return search(QdHistoricoEnvioQuitacaoBean.LTS_DADOS_HISTORICO, quitacao,false);
	}
/**
 * 
 * @see Pega parceiros e seta dentro deuma lista
 * @param QdHistoricoEnvioQuitacaoBean
 * 	
 */	
	private void getParceiros(QdHistoricoEnvioQuitacaoBean quitacaoBean)
	{
		logger.info("Trazendo parceiros por id , executando query LTS_ID_PARCEIRO_ANO_DE_REFERENCIA");
		List<Object[]> parceiros = search(QdHistoricoEnvioQuitacaoBean.LTS_ID_PARCEIRO_ANO_DE_REFERENCIA,quitacaoBean, false);
		List<SnParceiroBean> listaParceiros = new ArrayList<SnParceiroBean>();
		quitacaoBean.setParceiros(new ArrayList<SnParceiroBean>());
		for (int i = 0; i < parceiros.size(); i++) {
			SnParceiroBean parc = null;
			if (parceiros != null) {
				parc = addLinhaSnParceiroBean();

				parc.setIdParceiro((Integer) parceiros.get(i)[0]);
				parc.setNmParceiro((String) parceiros.get(i)[1]);
			}

			listaParceiros.add(parc);
		}
		quitacaoBean.setParceiros(listaParceiros);
		

		QdHistoricoEnvioQuitacaoBean quitacaoB = null;
		quitacaoBean.setAnosQuitacao(new ArrayList<List<Integer>>());
		for (int i = 0; i < listaParceiros.size(); i++) {

			quitacaoB = addLinhaHistorioEnvioQuitacao();
			quitacaoB.setTipoParceiro(addLinhaSnParceiroBean());
			quitacaoB.setTipoParceiro(listaParceiros.get(i));
			quitacaoB.setCarta(addLinhaQdCartaBean());
			quitacaoB.getCarta().setIdCarta(
					quitacaoBean.getCarta().getIdCarta());

			List<Integer> anosQuitacao = search(
					QdHistoricoEnvioQuitacaoBean.LTS_ANOS_QUITADO_PARCEIRO,
					quitacaoB, false);

			List<Integer> anos = addLinhaAnos() ;

			if (anosQuitacao != null && !anosQuitacao.isEmpty()) {
				for (int j = 0; j < anosQuitacao.size(); j++) {

					anos.add(anosQuitacao.get(j));
				}

				quitacaoBean.getAnosQuitacao().add(anos);
				}
		}		

	}
	/**
	 * 
	 * @param QdHistoricoEnvioQuitacaoBean
	 * @see Pega o texto da carta de quitação
	 */
	private void getTextoCarta(QdHistoricoEnvioQuitacaoBean quitacaoBean) {
		logger.info("Pegando texto da carta de quitação");
		List<BatchParameter> lista = new ArrayList<BatchParameter>();
		
		if (!(quitacaoBean.getCarta().getIdCarta() == null)) {
			lista.add(new BatchParameter(Types.VARCHAR, true));
			lista.add(new BatchParameter(quitacaoBean.getCarta()
					.getIdCarta(), Types.NUMERIC, false));

			BatchParameter[] parameters = convertListToBatchParameter(lista);
			quitacaoBean.setTextoCarta((String) getCurrentDAO().executeBatch(
					PLSQL_TEXTO_CARTA, parameters).get(0));
		}

	}
	/**
	 * 
	 * 
	 * @return String numero hash code carta
	 */	
	public String getNumHashCode(QdHistoricoEnvioQuitacaoBean quitacao)
	{
		logger.info("Populando bean QdHistoricoEnvioQuitacaoBean, executando query NUM_HASH_CODE ");
		List<String> objList = search(QdHistoricoEnvioQuitacaoBean.NUM_HASH_CODE, quitacao,false);
		String numHascode = "";
		if(!objList.isEmpty()){
		      numHascode = objList.get(0);
		}
		return numHascode;
	}
	/**
	 * 
	 * 
	 * @return QdHistoricoEnvioQuitacaoBean
	 */
	private QdHistoricoEnvioQuitacaoBean addLinhaHistorioEnvioQuitacao() {
		return new QdHistoricoEnvioQuitacaoBean();
		}
	
	/**
	 * 
	 * 
	 * @return QdCobrancaQuitacaoBean
	 */
	private QdCobrancaQuitacaoBean addLinhaCobrancaQuitacaoBean() {
		return new QdCobrancaQuitacaoBean();
		}
	
	/**
	 * 
	 * 
	 * @return SnParceiroBean
	 */
	private SnParceiroBean addLinhaSnParceiroBean() {
		return new SnParceiroBean();
		}
	
	/**
	 * 
	 * 
	 * @return SnParceiroBean
	 */
	private List<Integer> addLinhaAnos() {
		return new ArrayList<Integer>();
		}
	/**
	 * 
	 * 
	 * @return SnBoletoBean
	 */
	private SnBoletoBean addLinhaSnBoletoBean() {
		return new SnBoletoBean();
		}
	
	/**
	 * 
	 * 
	 * @return QdCartaBean
	 */
	private QdCartaBean addLinhaQdCartaBean() {
		return new QdCartaBean(null);
		}
	
	/**
	 * 
	 * 
	 * @return QdFormaEnvioBean
	 */
	private QdFormaEnvioBean addLinhaQdFormaEnvioBean() {
		return new QdFormaEnvioBean(null);
		}
	
	/**
	 * 
	 * 
	 * @return QdFormaEnvioBean
	 */
	private QdStatusEnvioBean addLinhaQdStatusEnvioBean() {
		return new QdStatusEnvioBean(null);
		}
	
	
}
