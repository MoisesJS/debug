package br.com.netservicos.novosms.emissao.core.facade.impl;

import static br.com.netservicos.core.bean.sn.SnTipoLoteBean.Sigla.CPNF;
import static br.com.netservicos.core.bean.sn.SnTipoLoteBean.Sigla.CPNF_WEB;
import static br.com.netservicos.core.bean.sn.SnTipoLoteBean.Sigla.EMISSAOPRINT;
import static br.com.netservicos.core.bean.sn.SnTipoLoteBean.Sigla.EMISSAO_PRINT_BOLETO_AVULSO;
import static br.com.netservicos.core.bean.sn.SnTipoLoteBean.Sigla.SEGUNDA_VIA;
import static br.com.netservicos.core.bean.sn.SnTipoLoteBean.Sigla.SEGUNDA_VIA_WEB;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.BAIRRO_EMISSOR_FISCAL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.BAIRRO_NOTA_FISCAL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.CEP_EMISSOR_FISCAL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.CEP_NOTA_FISCAL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.CFOP_NOTA_FISCAL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.CIDADE_EMISSOR_FISCAL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.CIDADE_NOTA_FISCAL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.CNPJ_EMISSOR_FISCAL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.CODIGO_CLIENTE_NET;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.CPF_CNPJ_NOTA_FISCAL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.DATA_EMISSAO_NOTA_FISCAL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.DATA_VENCIMENTO_BOLETO;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.ENDERECO_EMISSOR_FISCAL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.ENDERECO_NOTA_FISCAL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.ESTADO_EMISSOR_FISCAL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.ESTADO_NOTA_FISCAL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.HASH_CODE;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.IE_EMISSOR_FISCAL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.IM_EMISSOR_FISCAL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.INSCRICAO_ESTADUAL_NOTA_FISCAL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.MES_ANO_REFERENCIA;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.MODELO_NOTA_FISCAL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.NOME_CLIENTE_NOTA_FISCAL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.NOME_EMISSOR_FISCAL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.NUMERO_NOTA_FISCAL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.SERIE_NOTA_FISCAL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.Parametro.DIRETORIO_DESTINO_OPERADORA_ARQUIVO_PRINT;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.Parametro.DIR_ARQ_PRINT;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.Parametro.EXT_ARQ_PRINT;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.Parametro.NM_TEMP_ARQ_PRINT;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.Parametro.PACOTE_LAYOUT_ARQUIVO_BOLETO_AVULSO;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.Parametro.PACOTE_LAYOUT_ARQUIVO_GERACAO;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.Parametro.QT_FAT_ARQ_PRINT;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.Parametro.QUANTIDADE_LINHA_ARQUIVO_PRINT;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.Parametro.REMOVE_BOL_LOTE_SEG_VIA;
import static br.com.netservicos.novosms.geral.constants.GeralConstants.PREFIXO_DATABASE_SERVICE;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.sql.Types;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.ejb.CreateException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.jdbc.object.BatchSqlUpdate;
import org.springframework.util.CollectionUtils;

import br.com.netservicos.core.bean.pp.PpVlrParceiroFaturaBean;
import br.com.netservicos.core.bean.pp.SnPpVlrParceiroFaturaKey;
import br.com.netservicos.core.bean.sn.Cobranca;
import br.com.netservicos.core.bean.sn.GeracaoCobranca;
import br.com.netservicos.core.bean.sn.SnBoletoBean;
import br.com.netservicos.core.bean.sn.SnCidadeOperadoraBean;
import br.com.netservicos.core.bean.sn.SnCobrancaBean;
import br.com.netservicos.core.bean.sn.SnCobrancaParceiroBean;
import br.com.netservicos.core.bean.sn.SnConfiguracaoArquivoBean;
import br.com.netservicos.core.bean.sn.SnContratoBean;
import br.com.netservicos.core.bean.sn.SnContratoKey;
import br.com.netservicos.core.bean.sn.SnControleArquivoBean;
import br.com.netservicos.core.bean.sn.SnCriterioSegmentacaoBoletoBean;
import br.com.netservicos.core.bean.sn.SnGrupoArquivoBean;
import br.com.netservicos.core.bean.sn.SnLoteBean;
import br.com.netservicos.core.bean.sn.SnMensagemBean;
import br.com.netservicos.core.bean.sn.SnOrigemEmissaoBean;
import br.com.netservicos.core.bean.sn.SnParametroBean;
import br.com.netservicos.core.bean.sn.SnParametroKey;
import br.com.netservicos.core.bean.sn.SnParceiroBean;
import br.com.netservicos.core.bean.sn.SnRegistroEmissaoBean;
import br.com.netservicos.core.bean.sn.SnRelCobrancaBoletoBean;
import br.com.netservicos.core.bean.sn.SnRelLoteBoletoBean;
import br.com.netservicos.core.bean.sn.SnRelLoteBoletoKey;
import br.com.netservicos.core.bean.sn.SnSegregacaoBean;
import br.com.netservicos.core.bean.sn.SnSetorizacaoBean;
import br.com.netservicos.core.bean.sn.SnSituacaoProcessamentoBean;
import br.com.netservicos.core.bean.sn.SnStatusArquivoBean;
import br.com.netservicos.core.bean.sn.SnTipoCobrancaBean;
import br.com.netservicos.core.bean.sn.SnTipoControleArquivoBean;
import br.com.netservicos.core.bean.sn.SnTipoLoteBean;
import br.com.netservicos.core.bean.sn.SnTipoLoteBean.Sigla;
import br.com.netservicos.core.bean.sn.hibernate.ParceiroEnum;
import br.com.netservicos.framework.core.bean.DynamicBean;
import br.com.netservicos.framework.core.dao.BatchParameter;
import br.com.netservicos.framework.file.writer.GenerateFile;
import br.com.netservicos.framework.util.attachments.messages.AttachmentMessageLevel;
import br.com.netservicos.framework.util.attachments.messages.BasicAttachmentMessage;
import br.com.netservicos.framework.util.io.ZipUtils;
import br.com.netservicos.novosms.emissao.constants.PrintHouseConstants;
import br.com.netservicos.novosms.emissao.core.dao.ArquivoPrintHouseDAO;
import br.com.netservicos.novosms.emissao.core.dao.impl.BoletoAvulsoBatchBuilder;
import br.com.netservicos.novosms.emissao.core.dao.impl.PPValorParceiroSQLUpdateBatchBuilder;
import br.com.netservicos.novosms.emissao.core.dao.impl.SnBoletoFormaEnvioSQLUpdateBatchBuilder;
import br.com.netservicos.novosms.emissao.core.dao.impl.SnBoletoStatusSQLUpdateBatchBuilder;
import br.com.netservicos.novosms.emissao.core.dao.impl.SnInterfaceParceiroSQLUpdateBatchBuilder;
import br.com.netservicos.novosms.emissao.core.dao.impl.SnMovimentoCobrancaSQLUpdateBatchBuilder;
import br.com.netservicos.novosms.emissao.core.facade.ArquivoPrintHouseService;
import br.com.netservicos.novosms.emissao.dto.ArquivoPrintHouseDTO;
import br.com.netservicos.novosms.emissao.dto.ArquivoPrintHouseFileDTO;
import br.com.netservicos.novosms.emissao.dto.ArquivoPrintHouseZipDTO;
import br.com.netservicos.novosms.emissao.dto.ArquivoRemessaTransporteDTO;
import br.com.netservicos.novosms.emissao.dto.BaixaLogHubDTO;
import br.com.netservicos.novosms.emissao.dto.BoletoSummarioDTO;
import br.com.netservicos.novosms.emissao.dto.ClienteNotaFiscalPrintDTO;
import br.com.netservicos.novosms.emissao.dto.ClientePrintDTO;
import br.com.netservicos.novosms.emissao.dto.ClienteSegundaViaPrintDTO;
import br.com.netservicos.novosms.emissao.dto.DadosCobrancaParceiroDTO;
import br.com.netservicos.novosms.emissao.dto.DadosFatura;
import br.com.netservicos.novosms.emissao.dto.DadosGeraisPrimeiraViaPrintDTO;
import br.com.netservicos.novosms.emissao.dto.DadosGeraisPrimeiraViaPrintParceiroDTO;
import br.com.netservicos.novosms.emissao.dto.DadosGeraisSegundaViaPrintDTO;
import br.com.netservicos.novosms.emissao.dto.DadosItensNFDTO;
import br.com.netservicos.novosms.emissao.dto.DadosOperadoraDTO;
import br.com.netservicos.novosms.emissao.dto.DadosReemissaoDTO;
import br.com.netservicos.novosms.emissao.dto.DadosTributosDTO;
import br.com.netservicos.novosms.emissao.dto.DemonstrativoFinanceiroDTO;
import br.com.netservicos.novosms.emissao.dto.DemonstrativoFinanceiroParceiroDTO;
import br.com.netservicos.novosms.emissao.dto.DemonstrativoFinanceiroPrintDTO;
import br.com.netservicos.novosms.emissao.dto.DetalhamentoLigacaoParceiroDTO;
import br.com.netservicos.novosms.emissao.dto.DetalhamentoNotaFiscalDTO;
import br.com.netservicos.novosms.emissao.dto.FaturaArquivoDTO;
import br.com.netservicos.novosms.emissao.dto.FaturaDTO;
import br.com.netservicos.novosms.emissao.dto.FichaArrecadacaoPrintDTO;
import br.com.netservicos.novosms.emissao.dto.FiliadosDTO;
import br.com.netservicos.novosms.emissao.dto.LinhaDTO;
import br.com.netservicos.novosms.emissao.dto.LoteBoletoFaturaDTO;
import br.com.netservicos.novosms.emissao.dto.MensagemAnatelDTO;
import br.com.netservicos.novosms.emissao.dto.MensagemClaroClubeDTO;
import br.com.netservicos.novosms.emissao.dto.MensagemDTO;
import br.com.netservicos.novosms.emissao.dto.MensagemStreamingDTO;
import br.com.netservicos.novosms.emissao.dto.MinhaNetDTO;
import br.com.netservicos.novosms.emissao.dto.SetorDTO;
import br.com.netservicos.novosms.emissao.dto.SetorListaDTO;
import br.com.netservicos.novosms.emissao.dto.TipoSetorPrint;
import br.com.netservicos.novosms.emissao.dto.TributoDTO;
import br.com.netservicos.novosms.emissao.exception.EmissaoBusinessResourceException;
import br.com.netservicos.novosms.emissao.printhouse.FaturaNetDTO;
import br.com.netservicos.novosms.emissao.printhouse.LinhasLeiauteKit;
import br.com.netservicos.novosms.emissao.printhouse.MensagensNotaFiscalDTO;
import br.com.netservicos.novosms.emissao.resources.EmissaoResources;
import br.com.netservicos.novosms.emissao.util.StringUtils;
import br.com.netservicos.novosms.geral.core.facade.ApigeeService;
import br.com.netservicos.novosms.geral.core.facade.ControleArquivoService;
import br.com.netservicos.novosms.geral.core.facade.LogErroAbortService;

import com.sun.jersey.api.client.ClientResponse;

/**
 * <P>
 * <B> Description : Componente responsável por executar regras de negócio de
 * alta granularidade, transacionais. </B> <BR>
 * 
 * </P>
 * <P>
 * <B> Issues : </B>
 * 
 * <PRE>
 * 	
 * =============================================================================
 * Description Date By ---------------------------------------- -----------
 * ------------------------ Criação do arquivo fonte da classe, e transferência
 * dos métodos de negócio do caso de uso para um componente de negócio próprio
 * 10/08/2007 Alex Simas Braz
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
 * -------- -------------- --------------------------- 10/08/2007 Alex Simas
 * Braz N/A NETCommon Desenvolvimento.
 * =============================================================================
 * 
 * </PRE>
 * 
 * @author Alex Simas Braz
 * @since 10/08/2007
 * @version 1.1
 * 
 * @ejb.bean name="ArquivoPrintHouseEJB" type="Stateless"
 *           display-name="ArquivoPrintHouseEJB"
 *           description="ArquivoPrintHouseEJB" view-type="both"
 *           jndi-name="netservicos/novosms/emissao/ejb/ArquivoPrintHouseEJB"
 *           local-jndi-name ="netservicos/novosms/emissao/ejb/local/ArquivoPrintHouseEJB"            
 *           transaction-type="Container" generate="true"
 * 
 * 
 * @ejb.interface local-extends="javax.ejb.EJBLocalObject"
 *                extends="javax.ejb.EJBObject"
 * 
 * @ejb.home local-extends="javax.ejb.EJBLocalHome" extends="javax.ejb.EJBHome"
 */
public class ArquivoPrintHouseServiceEJBImpl extends EmissaoBaseServiceImpl 
		implements ArquivoPrintHouseService {


	private Log logger = org.apache.commons.logging.LogFactory.getLog(ArquivoPrintHouseServiceEJBImpl.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3102874614263445645L; 
	
	private static final String NOME_ITENS_EVENTUAIS = "Itens Eventuais";
	public static final int QT_LN_PAG_FAT_PRINT = 74;
	private static final String TPPRIMEIRAVIA = "P";
	private static final String TPSEGUNDAVIA = "S";
	private static final String TPCOPIA = "C";
	//A pagina de itens de NF EBT e parceiros tem tamanho 225mm - 4cm cabecalho
	private static final int TAM_PAGINA_NF_EBT_PARCEIROS = 1851;
	
	private static final int TAM_PAGINA_DETALHAMENTO_EBT = 2250;
	private static final int TAM_CABECALHO_DET_EBT = 125;
	
	// código criado para identificar o título da listagem de filiados na fatura online
	private static final String ID_TITULO_FILIADOS = "?;?";
	
	// indica tipo de fatura primeira via
	private static final String FATURA_PRIMEIRA_VIA = "FATURA_PRIMEIRA_VIA";
	
	//tamanho maximo de caracteres na linha do demonstrativo financeiro antes de quebrar para proxima linha
	public static final int TAMANHO_MAX_LINHA_DF = 73;
	//tamanho maximo de caracteres no grupo  do demonstrativo financeiro antes de quebrar para proxima linha
	private static final int TAMANHO_MAX_GRUPO_DF = 73;
	
	private static final String CHECA_LINHAS_FONE = "CHECA_LINHAS_FONE";
	
	private Long idBoletoAtual;
	private Integer tipoAtual;
	private String valorAtual; 
	private LinhasLeiauteKit llk;
	private static final String ID_CRITERIO="1";
	
	private static final String FSN_LST_VERIFICA_ENVIO_QUITACAO_DEBITO_ANUAL = "{? = call PGSMS_QDEB_MSG_FATURA.FN_VERIFICA_ENVIO(?)}";	

	private static final int ID_MENSAGEM_PADPOL = 6283;	
	
	private boolean existsFaturaNet = false;
	private boolean existsMsgComplCampoImpor = false;
	private boolean existsFaturaEbt = false;
	
	private static final String PARAM_APIGEE_HUB_URL_AUTH = "APIGEE_HUB_URL_AUTH";
	private static final String PARAM_APIGEE_HUB_CC_TOKEN = "APIGEE_HUB_CC_TOKEN";
	private static final String PARAM_APIGEE_HUB_CC_TOKEN_EXP = "APIGEE_HUB_CC_TOKEN_EXP";
	private static final String PARAM_APIGEE_HUB_URL = "APIGEE_HUB_URL";
	private static final String PARAM_APIGEE_HUB_DEL_URL = "APIGEE_HUB_DEL_URL";
	private static final String PARAM_APIGEE_HUB_USER_AUTH = "APIGEE_HUB_USER_AUTH";
	private static final String CONTENT_TYPE_JSON = "application/json";
	
	/*
	 * Constante para verificação de arquivo printHouse
	 */
	private static final boolean FLAG_ARQUIVO_PRINT = true;

	//Numero maximo de linhas por folha do Demonstrativo Financeiro
	private final int[] linhasFolha = {22000, 38000, 50000}; //1a, 2a, 3a folha em diante e' indice 2

	//private static final Integer NUMERO_QUEBRA = 100;
	
	//NSMSM_50206_CI_001 - Variavel para validar se existem itens eventuais na fatura
	private boolean existsItensEventuais = true;
	
	private static final String NET_FONE = "NET FONE";
	private static final String CLARO_FONE = "CLARO FONE";
	
	private Long getIdBoletoAtual () {
		return this.idBoletoAtual;
	}
	
	private void setIdBoletoAtual(Long idBoletoAtual) {
		this.idBoletoAtual = idBoletoAtual;
	} 

	private Integer getTipoAtual() {
		return tipoAtual;
	}
	
	private void setTipoAtual(Integer tipoAtual) {
		this.tipoAtual = tipoAtual;
	}
	
	private String getValorAtual() {
		return valorAtual;
	}
	
	private void setValorAtual(String valorAtual) {
		this.valorAtual = valorAtual;
	}
	
	private Map<Integer, SnBoletoBean> boletos;

	/**
	 * @ejb.create-method view-type="both"
	 * @throws CreateException
	 */
	public void ejbCreate() throws CreateException {
		super.ejbCreate();

		llk = new LinhasLeiauteKit();
		boletos = new WeakHashMap<Integer, SnBoletoBean>(99); 
	}
	
	/**
	 * @author Marcio Bellini
	 * @version $Revision: 1.3.4.7 $ - Revisão da implementação do método
	 * @number RF015_RF021_FAT002
	 * @see br.com.netservicos.sms.fatcobranca.service.ArquivoPrintHouseService#gerarArquivoPrintHouse(Collection
	 *      lotes)
	 * @since 1.87 RF015_RF021
	 */ 

	/** 
	 * 
	 * @author Alex Simas Braz
	 * @since 14/06/2007
	 * 
	 * @ejb.interface-method view-type = "both"
     * @ejb.transaction type="Required"
	 * 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Collection gerarArquivoPrintHouse(Collection<SnLoteBean> lotes, String siglaTipoLote) {
		Collection transporteArquivos;
		
		ArquivoPrintHouseDTO arquivoPrintHouseDTO = null;

		List<SnLoteBean> lotesList = new ArrayList<SnLoteBean>();
		try {
			
			logger.info(new BasicAttachmentMessage("Inicio da geração do arquivo print house.",
			                                       AttachmentMessageLevel.INFO));
			
			for (SnLoteBean lote : lotes) {							
				SnLoteBean item = (SnLoteBean) find(lote);
				lotesList.add(item);
			}

			// Atualizar o situação de todos os lotes para processando
			arquivoPrintHouseDTO = new ArquivoPrintHouseDTO(siglaTipoLote);
			Collection<SnCidadeOperadoraBean> listaCidades = new ArrayList<SnCidadeOperadoraBean>();
			SnRegistroEmissaoBean registroEmissao = criarRegistroEmissao(lotesList,	listaCidades);
			arquivoPrintHouseDTO.setRegistroEmissao(registroEmissao);
			logger.info(new BasicAttachmentMessage("Criação do registro de emissão " + 
			                                       registroEmissao.getIdRegistroEmissao() + ".",
			                                       AttachmentMessageLevel.INFO));

			if (listaCidades.size() == 1){
				SnCidadeOperadoraBean cidade = (SnCidadeOperadoraBean) listaCidades.iterator().next();
		
				logger.info(new BasicAttachmentMessage("Processando cidade " + cidade.getNomePessoa() + ".",
				                                       AttachmentMessageLevel.INFO));
				
				Boolean controleSegregacao = this.getControleSegregacao(cidade.getCidContrato(), 
				                                                        siglaTipoLote);
				
				String cidContrato = cidade.getCidContrato();
				String qt_fat_arq_print = obterParametroObrigatorioString(cidContrato, QT_FAT_ARQ_PRINT);
				String qt_ln_pag_fat_print = Integer.toString(QT_LN_PAG_FAT_PRINT);
				String qt_ln_arq_print = obterParametroObrigatorioString(cidContrato, QUANTIDADE_LINHA_ARQUIVO_PRINT);
				String ext_arq_print = obterParametroObrigatorioString(cidContrato, EXT_ARQ_PRINT);
				String nm_temp_arq_print = obterParametroObrigatorioString(cidContrato, NM_TEMP_ARQ_PRINT);
				String dir_arq_print = obterParametroObrigatorioString(cidContrato, DIR_ARQ_PRINT);

				arquivoPrintHouseDTO.setQuantidadeFaturaPorArquivoPrint(new Integer(qt_fat_arq_print));
				arquivoPrintHouseDTO.setQuantidadeMaximaLinhasPorFolha(new Integer(qt_ln_pag_fat_print));
				arquivoPrintHouseDTO.setQuantidadeMaximaNumeroLinhasArquivo(new Integer(qt_ln_arq_print));
				arquivoPrintHouseDTO.setExtensaoArquivoPrint(ext_arq_print);
				arquivoPrintHouseDTO.setNomeTemporarioDoArquivoPrint(nm_temp_arq_print);
				arquivoPrintHouseDTO.setDiretorioGravacaoArquivoPrint(dir_arq_print);
			    
			    logger.info(new BasicAttachmentMessage(
	   						(new StringBuffer()).append("Obtém parâmetros [qt_fat_arq_print=" + qt_fat_arq_print +"],")
	   						.append(" [qt_ln_pag_fat_print=" + qt_ln_pag_fat_print + "],")
	   						.append(" [qt_ln_arquivo_print=" + qt_ln_arq_print + "],")
	   						.append(" [ext_arq_print=" + ext_arq_print + "],")
	   						.append(" [nm_temp_arq_print=" + nm_temp_arq_print + "],")
	   						.append(" [dir_arq_print=" + dir_arq_print + "],")
	   						.append(" [controleSegregacao=" + controleSegregacao + "]").toString()
			   						, AttachmentMessageLevel.INFO));
								
				if (controleSegregacao.booleanValue()) { // ligado
					for (SnLoteBean lote : lotesList) {
						processarArquivo(lote, cidade, arquivoPrintHouseDTO);
					}
				} else { // desligado
					arquivoPrintHouseDTO.iniciarArquivo(cidade,controleSegregacao);
					processarArquivo(lotesList, cidade, arquivoPrintHouseDTO);
				}
			} else if (listaCidades.size() > 1) {

				for (SnCidadeOperadoraBean cidade : listaCidades) {

					logger.info(new BasicAttachmentMessage("Processando cidade " + cidade.getNomePessoa() + ".", 
					                                       AttachmentMessageLevel.INFO));
		
					Collection<SnLoteBean> lotesCidade = new ArrayList<SnLoteBean>();

					for (Iterator iterLotes = lotesList.iterator(); iterLotes.hasNext();) {
						SnLoteBean lote = (SnLoteBean) iterLotes.next();
						if (lote.getSnCidadeOperadoraBean() != null && 
								lote.getSnCidadeOperadoraBean().equals(cidade)){
							lotesCidade.add(lote); 
						}
					}
					
					Boolean controleSegregacao = this.getControleSegregacao(cidade.getCidContrato(), 
					                                                        siglaTipoLote);
					
					if (controleSegregacao.booleanValue()) { // ligado
						for (SnLoteBean lote : lotesCidade) {
							arquivoPrintHouseDTO.iniciarArquivo(cidade, controleSegregacao);							
							processarArquivo(lotesList, cidade, arquivoPrintHouseDTO);
						}
					} else { // desligado			

						arquivoPrintHouseDTO.iniciarArquivo(cidade,
								controleSegregacao);

						processarArquivo(lotesCidade, cidade,
								arquivoPrintHouseDTO);
						
						
					}
				}
			} else {

			}

			registroEmissao.setNrFaturas(arquivoPrintHouseDTO
					.getQuantidadeFaturasEmitidads());
			SnSituacaoProcessamentoBean snSituacaoProcessamentoBean = this
					.getSituacaoProcessamento(SnSituacaoProcessamentoBean.Sigla.FINALIZADO);
			registroEmissao
					.setSnSituacaoProcessamentoBean(snSituacaoProcessamentoBean);
			
			this.update(registroEmissao, getUserSession()
					.getCurrentDbService());

			String mensagen = "Atualiza situação de processamento do registro de emissão para finalizado.";
			logger.info(new BasicAttachmentMessage(mensagen, AttachmentMessageLevel.INFO));	
			
			transporteArquivos = finalizarProcesso(arquivoPrintHouseDTO
					.getArquivoPrintHouseZipDTOs(),arquivoPrintHouseDTO);
		

			return transporteArquivos;

		} catch (Exception e) {

			
			logger.error(new BasicAttachmentMessage("Aborta processo de geração de arquivo.", AttachmentMessageLevel.INFO));

			// Loga algumas informacoes importantes para rastrear quando houver erro
			logger.error(new BasicAttachmentMessage("Boleto=" + this.getIdBoletoAtual(), AttachmentMessageLevel.INFO));
			logger.error(new BasicAttachmentMessage("Tipo=" + this.getTipoAtual(), AttachmentMessageLevel.INFO));
			logger.error(new BasicAttachmentMessage("Valor= " + this.getValorAtual(), AttachmentMessageLevel.INFO));
			
//			ArquivoPrintHouseService service = getService(ArquivoPrintHouseService.class);
//			
//			 //Seta o situação do lote para processamento pendente
//			for(SnLoteBean lote : lotesList) {
//				
//				service.atualizarSituacaoLote(lote,SnSituacaoProcessamentoBean.Sigla.PENDENTE.getSigla());
//
//				logger.info(new BasicAttachmentMessage("Volta situação do lote " + lote.getIdLote() + " para pendente.", AttachmentMessageLevel.INFO));
//
//			}

			
			logger.info(new BasicAttachmentMessage("Fim anormal da geração do arquivo print house", AttachmentMessageLevel.INFO));
			
			if (e instanceof IllegalStateException || e instanceof EmissaoBusinessResourceException) {

				StringBuffer lotesStr = new StringBuffer("IDs Lote: ");
				for (Iterator iterLotes = lotes.iterator(); iterLotes.hasNext();) {
					SnLoteBean lote = (SnLoteBean) iterLotes.next();
					lotesStr.append(lote.getIdLote());
					if (iterLotes.hasNext()) {
						lotesStr.append(", ");
					}
				}

				logger.error(new BasicAttachmentMessage(super.getMessage(EmissaoResources.ERRO_PROCESSO
						, new Object[] {
						"Geracao de Arquivo Print House",
						"Sigla Tipo Lote: " + siglaTipoLote }
					), AttachmentMessageLevel.INFO));
						
				 
				throw new IllegalStateException(e);
				
			} else if (e instanceof RuntimeException) {
				throw (RuntimeException) e;
			} else {				
				throw new IllegalStateException();
			}
		}
	}

	/***
	 * Atualiza a situacao dos lotes para a sigla informada.
	 * @param lotes
	 * @param sigla
	 * @ejb.transaction  type="Required"
	 * @ejb.interface-method view-type = "both"
	 */
	public void atualizarSituacaoLote(Collection<SnLoteBean> lotes, String sigla) {
		
		for(SnLoteBean lote : lotes){							
			
			lote = this.atualizarSituacaoLote(lote, sigla);

			logger.info(new BasicAttachmentMessage("Alteração da situação do lote " + lote.getIdLote() 
					+ " para processando.", AttachmentMessageLevel.INFO));
		
		}

	}
	
	/** 
	 * @version $Revision: 1.3.4.7 $ - Revisão da implementação dp método 
	 * @number RF015_RF021_FAT149
	 * @see br.com.netservicos.sms.fatcobranca.service.ArquivoPrintHouseService#gerarFaturaSegundaVia(Integer)
	 * @since 1.1 RF015_RF021
	 * @ejb.transaction  type="Required"
	 * @ejb.interface-method view-type = "both"
	 * 
	 */
	public FaturaArquivoDTO gerarFaturaSegundaVia(Long idBoleto) {
		
		FaturaArquivoDTO faturaArquivoDTO = new FaturaArquivoDTO();	
		SnBoletoBean boleto = new SnBoletoBean();
		boleto.setIdBoleto(idBoleto);
		boleto = (SnBoletoBean) super.find(boleto);
		String cidContrato = boleto.getContrato().getCompositeKey().getCidContrato();

		SnCidadeOperadoraBean snCidadeOperadoraBean = new SnCidadeOperadoraBean();
		snCidadeOperadoraBean.setCidContrato(cidContrato);
		snCidadeOperadoraBean = (SnCidadeOperadoraBean) find(snCidadeOperadoraBean);

		faturaArquivoDTO.setDadosOperadora(operadoraNet(snCidadeOperadoraBean));
		
		Boolean controleSegregacao = this.getControleSegregacao(snCidadeOperadoraBean.getCidContrato());
		LoteBoletoFaturaDTO param = new LoteBoletoFaturaDTO();
		param.setIdBoleto(idBoleto);
		
		LoteBoletoFaturaDTO loteDTO = super.getDadosBeanbyQuery("selecionarLotePeloBoleto", param, false, LoteBoletoFaturaDTO.class);
		Collection mensagens = null;
		
		if (loteDTO != null) {
			String[] lotesList = new String[]{String.valueOf(loteDTO.getIdLote())};
			mensagens = obterMensagens(boleto.getDtVencimento(), snCidadeOperadoraBean, ArquivoPrintHouseServiceEJBImpl.ID_CRITERIO,SnSetorizacaoBean.Prefixo.SEGUNDA_VIA, controleSegregacao,idBoleto,lotesList);
		}
		else {
			mensagens = obterMensagens(boleto.getDtVencimento(), snCidadeOperadoraBean, ArquivoPrintHouseServiceEJBImpl.ID_CRITERIO,SnSetorizacaoBean.Prefixo.SEGUNDA_VIA, controleSegregacao, idBoleto);			
		}
		
		faturaArquivoDTO.setListaMensagem(mensagens);		
		faturaArquivoDTO.setListaBanco(obterBancoConveniado(snCidadeOperadoraBean, boleto.getSnTipoCobrancaBean().getIdTipoCobranca(), boleto.getDtVencimento()));
		faturaArquivoDTO.setFaturaDTO(gerarSegundaViaDemonstrativoFinanceiroFichaArrecadacaoInstalacao(boleto.getIdBoleto(), (loteDTO != null) ? loteDTO.getIdLote() : null, "PDF", TPPRIMEIRAVIA));
		ArquivoPrintHouseDAO dao = getAphDao();
		FaturaDTO faturaDto = faturaArquivoDTO.getFaturaDTO();
		faturaDto.setUltimasOcorrencias(notNullString(dao.buscarUltimasOcorrencias(boleto.getContrato().getCompositeKey().getNumContrato(), boleto.getContrato().getCompositeKey().getCidContrato())));
		faturaArquivoDTO.setFaturaDTO(faturaDto);
		
		return faturaArquivoDTO;
	}
	
	
	/**
	 *  * @version $Revision: 1.3.4.7 $ - Implementação do método
	 * @throws Exception 
	 * @number RF015_RF021_FAT152
	 * @see br.com.netservicos.sms.fatcobranca.service.ArquivoPrintHouseService#gerarReEmissaoNotaFiscal(java.lang.Integer, java.lang.Integer)
	 * @since 1.1 RF015_RF021
	 * 
	 * @ejb.transaction
	 *   type="Required"
	 * @ejb.interface-method view-type = "both"
	 */
	public FaturaArquivoDTO gerarReEmissaoNotaFiscal(Long idNotaFiscal, Long idBoleto)  {
		
		FaturaArquivoDTO faturaArquivoDTO = new FaturaArquivoDTO();	

		SnBoletoBean boleto = (SnBoletoBean) getDAO(
				getUserSession().getCurrentDbService()).find(
				SnBoletoBean.class, idBoleto);
		
		SnCidadeOperadoraBean snCidadeOperadoraBean = (SnCidadeOperadoraBean) getDAO(
				getUserSession().getCurrentDbService())
				.find(SnCidadeOperadoraBean.class,
						((SnContratoKey)boleto.getContrato().getPrimaryKey()).getCidContrato());

		faturaArquivoDTO.setDadosOperadora(operadoraNet(snCidadeOperadoraBean));
		
		String idCriterio = (boleto.getCriterio() != null)? boleto.getCriterio().getIdCriterio().toString() : null;
		Boolean controleSegregacao = this.getControleSegregacao(snCidadeOperadoraBean.getCidContrato());
		
		LoteBoletoFaturaDTO param = new LoteBoletoFaturaDTO();
		param.setIdBoleto(boleto.getIdBoleto());
		LoteBoletoFaturaDTO loteDTO = super.getDadosBeanbyQuery("selecionarLotePeloBoleto", param, false, LoteBoletoFaturaDTO.class);
		String[] lotesList = new String[]{String.valueOf(loteDTO.getIdLote())};		
		
		faturaArquivoDTO.setListaMensagem(obterMensagens(boleto
				.getDtVencimento(), snCidadeOperadoraBean, idCriterio,
				SnSetorizacaoBean.Prefixo.COPIA_NF, controleSegregacao, null, lotesList));		
		
		faturaArquivoDTO
				.setListaBanco(obterBancoConveniado(snCidadeOperadoraBean, boleto.getSnTipoCobrancaBean().getIdTipoCobranca(), boleto.getDtVencimento()));
		try {
			faturaArquivoDTO.setFaturaDTO(gerarCopiaDeNotaFiscal(idNotaFiscal,
					idBoleto, "NF"));
		}catch(Exception e){
			throw new EmissaoBusinessResourceException(
					"error.reemissaonf.parceiro.naodisponivel", new Object[] {
						 "| ID Nota Fiscal: "  });		
		}
		ArquivoPrintHouseDAO dao = getAphDao();
		FaturaDTO faturaDto = faturaArquivoDTO.getFaturaDTO();
		faturaDto.setUltimasOcorrencias(notNullString(dao.buscarUltimasOcorrencias(boleto.getContrato().getCompositeKey().getNumContrato(), boleto.getContrato().getCompositeKey().getCidContrato())));
		faturaArquivoDTO.setFaturaDTO(faturaDto);
		return faturaArquivoDTO;
	}
	
	

	/**
	 *  * @version $Revision: 1.3.4.7 $ - Implementação do método
	 * @throws Exception 
	 * @number RF015_RF021_FAT152
	 * @see br.com.netservicos.sms.fatcobranca.service.ArquivoPrintHouseService#gerarReEmissaoNotaFiscal(java.lang.Integer)
	 * @since 1.1 RF015_RF021
	 * 
	 * @ejb.transaction
	 *   type="Required"
	 * @ejb.interface-method view-type = "both"
	 */
	public FaturaArquivoDTO gerarReEmissaoNotaFiscal(Long idNotaFiscal)  {
		
		FaturaArquivoDTO faturaArquivoDTO = new FaturaArquivoDTO();	
		BoletoSummarioDTO boletoSummarioDTO = null;
		// TODO BoletoSummarioDTO boletoSummarioDTO =
		// this.getBoletoDAO().selecionarBoletoOriginal(idNotaFiscal);
		DynamicBean filtro = new DynamicBean();
		filtro.set("idCobrancaNotaFiscal", idNotaFiscal);
		boletoSummarioDTO = getDadosBeanbyQuery(
				"selecionarBoletoOriginal", filtro, false, BoletoSummarioDTO.class);
		
		if (boletoSummarioDTO == null) {
			boletoSummarioDTO = getDadosBeanbyQuery(
					"selecionarBoletoOriginalParceiro", filtro, false, BoletoSummarioDTO.class);			
			if (boletoSummarioDTO == null) {
				throw new EmissaoBusinessResourceException(
						EmissaoResources.ERRO_PROCESSO, new Object[] {
								"selecionarBoletoOriginal",
								"ID Nota Fiscal: " + idNotaFiscal });
			}
//			else {
//				throw new EmissaoBusinessResourceException(
//						"error.reemissaonf.parceiro.naodisponivel", new Object[] {
//							 "| ID Nota Fiscal: " + idNotaFiscal });				
//			}
		}
		
		SnBoletoBean boleto = (SnBoletoBean) getDAO(
				getUserSession().getCurrentDbService()).find(
				SnBoletoBean.class, boletoSummarioDTO.getIdBoleto());
		SnCidadeOperadoraBean snCidadeOperadoraBean = (SnCidadeOperadoraBean) getDAO(
				getUserSession().getCurrentDbService())
				.find(SnCidadeOperadoraBean.class,
						boletoSummarioDTO.getCidContrato());

		faturaArquivoDTO.setDadosOperadora(operadoraNet(snCidadeOperadoraBean));
		
		String idCriterio = (boleto.getCriterio() != null)? boleto.getCriterio().getIdCriterio().toString() : null;
		Boolean controleSegregacao = this.getControleSegregacao(snCidadeOperadoraBean.getCidContrato());
		
		LoteBoletoFaturaDTO param = new LoteBoletoFaturaDTO();
		param.setIdBoleto(boleto.getIdBoleto());
		LoteBoletoFaturaDTO loteDTO = super.getDadosBeanbyQuery("selecionarLotePeloBoleto", param, false, LoteBoletoFaturaDTO.class);
		String[] lotesList = new String[]{String.valueOf(loteDTO.getIdLote())};		
		
		faturaArquivoDTO.setListaMensagem(obterMensagens(boleto
				.getDtVencimento(), snCidadeOperadoraBean, idCriterio,
				SnSetorizacaoBean.Prefixo.COPIA_NF, controleSegregacao, null, lotesList));		
		
		faturaArquivoDTO
				.setListaBanco(obterBancoConveniado(snCidadeOperadoraBean, boleto.getSnTipoCobrancaBean().getIdTipoCobranca(), boleto.getDtVencimento()));
		try {
			faturaArquivoDTO.setFaturaDTO(gerarCopiaDeNotaFiscal(idNotaFiscal,
					boletoSummarioDTO.getIdBoleto(), "NF"));
		}catch(Exception e){
			throw new EmissaoBusinessResourceException(
					"error.reemissaonf.parceiro.naodisponivel", new Object[] {
						 "| ID Nota Fiscal: "  });		
		}
		ArquivoPrintHouseDAO dao = getAphDao();
		FaturaDTO faturaDto = faturaArquivoDTO.getFaturaDTO();
		faturaDto.setUltimasOcorrencias(notNullString(dao.buscarUltimasOcorrencias(boleto.getContrato().getCompositeKey().getNumContrato(), boleto.getContrato().getCompositeKey().getCidContrato())));
		faturaArquivoDTO.setFaturaDTO(faturaDto);
		return faturaArquivoDTO;
	}

	
	/**
	 * Método responsável por registrar na entidade Registro de Emissão o
	 * processamento da solicitação de geração dos arquivos para print house.
	 * 
	 * @version 1.0 - criação do Metodo - 22/03/2006
	 * @number RF015_RF021
	 * @return RegistroEmissao
	 * @param Collection
	 *            lote
	 * @param Collection
	 *            listaOperadora
	 * 
	 * @semantics <br>	
	 * 
     * @ejb.transaction type="Required"
	 */
	protected SnRegistroEmissaoBean criarRegistroEmissao(
			Collection<SnLoteBean> lotes,
			Collection<SnCidadeOperadoraBean> listaCidades) {

		if (lotes == null || lotes.size() < 1) {			
			throw new EmissaoBusinessResourceException(EmissaoResources.ERRO_LOTES_NULO);
		}

		SnRegistroEmissaoBean registroEmissao = new SnRegistroEmissaoBean();

		double valorTotalLote = 0;
		int quantidadeFaturasNoLote = 0;

		for (SnLoteBean domainLote : lotes) {
			//domainLote = (SnLoteBean) find(domainLote);
			setDataVencimentoRegistroEmissao(registroEmissao, domainLote
					.getDtVcto());

			valorTotalLote += domainLote.getVlTotal();
			quantidadeFaturasNoLote += domainLote.getNrQtdBoleto();

			if (!listaCidades.contains(domainLote.getSnCidadeOperadoraBean())) {
				listaCidades.add(domainLote.getSnCidadeOperadoraBean());
			}
		}

		registroEmissao.setVlTotal(valorTotalLote);
		registroEmissao.setNrFaturas(quantidadeFaturasNoLote);
		registroEmissao.setDtVctoInicial(new Date());

		SnSituacaoProcessamentoBean snSituacaoProcessamentoBean = this
				.getSituacaoProcessamento(SnSituacaoProcessamentoBean.Sigla.PENDENTE);
		registroEmissao
				.setSnSituacaoProcessamentoBean(snSituacaoProcessamentoBean);
		registroEmissao.setDhProcessamento(new Date()); 
		registroEmissao.setCidContrato(listaCidades.iterator().next());
		SnRegistroEmissaoBean result = (SnRegistroEmissaoBean) insert(registroEmissao, getUserSession().getCurrentDbService());

		return result;
	}
	
	
	/**
	 * Método responsável por retornar os boletos de um lote.
	 * 
	 * @author Luiz Palazzo
	 * @version 1.0 - criação do Metodo - 01/11/2021
	 * 
	 * @ejb.interface-method view-type = "both"
	 * @ejb.transaction type="RequiresNew"
	 * 
	 * 
	 */
	public List<LoteBoletoFaturaDTO> retornaBoletos(SnLoteBean lote){
		
		ArquivoPrintHouseDAO dao = getAphDao(); 
		List<LoteBoletoFaturaDTO> listaBoletos = dao.buscarBoletosFaturas(lote);
		return listaBoletos;
	}
	
	/** 
	 * Método responsável por retornar detalhes de um boleto.
	 * 
	 * @author Luiz Palazzo
	 * @version 1.0 - criação do Metodo - 01/11/2021
	 * @ejb.transaction  type="Required"
	 * @ejb.interface-method view-type = "both"
	 * 
	 */
	public SnBoletoBean retornaDetalhesBoleto(Long idBoleto, String cidContrato){
		
		//realizar tratamento de erro caso nao encontrar o token!
		
		SnBoletoBean boleto = new SnBoletoBean();
		boleto.setIdBoleto(idBoleto);
		boleto = (SnBoletoBean)find(boleto);
		
		HashMap<String, String> contratoCartao = new HashMap<String, String>();
		
		ArquivoPrintHouseDAO dao = getAphDao();

		boleto.setMetaData(dao.recuperaContratoCartao(boleto.getIdBoleto(), cidContrato));
		
		return boleto;
	}
	
	/**
	 * Método responsável por processar o envio do pagamento via cartao de credito para o HUB.
	 * 
	 * @author Luiz Palazzo
	 * @version 1.0 - criação do Metodo - 01/11/2021
	 * 
	 * @ejb.interface-method view-type = "both"
	 * @ejb.transaction type="NotSupported"
	 * 
	 */
	@SuppressWarnings("rawtypes")
	public void efetuaProcessoBaixaHub(BaixaLogHubDTO log){
	
		try{
			logger.info(new BasicAttachmentMessage("Iniciando processo de baixa de pagamento cartao via HUB para o boleto "+log.getIdBoleto() 
					 , AttachmentMessageLevel.INFO));
			ArquivoPrintHouseDAO dao = getAphDao();
			
			//Efetuando chamada REST ao HUB de pagamentos...
			String requestHUB = "{  \"data\": {    \"value\": \""+log.getValor().toString().replace(".", "")+"\",    \"token\": \""+log.getTokenCC()+"\",    \"paymentType\": 1,    \"numberInstallments\": 1,    \"isAutomaticConfirmation\": true,    \"isFraudAnalysis\": false  }}";
						
			log.setRequestHub(requestHUB);
			
			logger.info(new BasicAttachmentMessage("Request enviada ao HUB de pagamentos "+log.getRequestHub() 
					 , AttachmentMessageLevel.INFO));

			logger.info(new BasicAttachmentMessage("Gravando log para o boleto: "+log.getIdBoleto(), AttachmentMessageLevel.INFO));
			long numeroTransacao = efetuaChamadaProcLog(log); //Primeiro log com inserção na tabela
			
			
			log.setNumeroTransacao(numeroTransacao);

			String respostaHUB = envioBaixaHub(requestHUB, log.getCidContrato());
			
			String[] campos = null;
			String[] respostaErroHub = null;
			String campoCodigoRetornoHUB = ""; //4 e 6 = sucesso, outros = erro
	        String campoAdquirente = "";
	        String mensagemErro = "";
	        StringBuilder sbError = new StringBuilder();	        	       
	        
			//destrinchando campos do HUB necessarios para a baixa no NETSMS
			
	        //Casos onde é retornado código de erro (cartao roubado, cartao sem saldo, etc)
			if(respostaHUB.contains("error")){
				campos = respostaHUB.split(",");
				
				for(String campo:campos){
		            if(!campo.contains("EJBException")){ //removendo a stack desnecessaria da fachada
		            	sbError.append(campo.replace("\\", "")+"\n");
		            	
		            	if(campo.contains("detailedMessage")){ //ultima linha do response
		            		mensagemErro = campo.replace("\\", "").replace("\"", "").replace("detailedMessage:", "");
	            			System.out.println("MensagemErro: "+mensagemErro); //CIELO - 78 - NOT APPROVED
	            			respostaErroHub = mensagemErro.split("-");
	            			
	            			if(respostaErroHub.length > 2){
	            				campoAdquirente = respostaErroHub[0].trim();
		            			campoCodigoRetornoHUB = respostaErroHub[1].trim();
		            			
		            			System.out.println("campoAdquirente "+campoAdquirente);
		            			System.out.println("campoCodigoRetornoHUB "+campoCodigoRetornoHUB);
                                if (!campoCodigoRetornoHUB.equals("")){
                                    log.setIdCodigoRetornoHub(Long.valueOf(campoCodigoRetornoHUB));
                                }
	            			}
	            			
	            			
			            	sbError.append("}");
			            	break;
			            }
		            	
		            }
		            
		        }
				logger.info(new BasicAttachmentMessage("Resposta do HUB de pagamentos com ERRO: "+sbError.toString() 
						 , AttachmentMessageLevel.INFO));
				log.setRespostaHub(sbError.toString()); //JSON de retorno do HUB em caso de ERRO				

			
			//Caso de sucesso - Cartão foi cobrado pelo HUB
			}else{
				campos = respostaHUB.replace("\"","").replace(" ","").replace("{","").replace("}","").replace("data:","").split(",");
				
				 for(String campo:campos){
			            if(campo.contains("responseCode:")){
			            	campoCodigoRetornoHUB = campo.replace("responseCode:", ""); //retirando o label e mantendo apenas o valor.
			            }else{
			            	if(campo.contains("flag:")){
			            		campoAdquirente = campo.replace("processors:processor:flag:", ""); //retirando o label e mantendo apenas o valor.
			            	}else{
			            		if(campo.contains("message:")){
			            			mensagemErro = campo.replace("message:", "");
			            		}
			            	}
			            }
			        }
				 log.setRespostaHub(respostaHUB); //JSON de retorno do HUB em caso de SUCESSO				 
				 
				 logger.info(new BasicAttachmentMessage("Resposta do HUB de pagamentos: "+respostaHUB 
						 , AttachmentMessageLevel.INFO));
				 
				 if(!campoCodigoRetornoHUB.equals("")){
					 log.setIdCodigoRetornoHub(Long.valueOf(campoCodigoRetornoHUB));
					 
					 //alterando ST_CNAB para S, pois foi processado com sucesso pelo HUB.
					 try{
						 logger.info(new BasicAttachmentMessage("Alterando ST_CNAB para S do boleto: "+log.getIdBoleto() 
								 , AttachmentMessageLevel.INFO));
						 dao.executaUpdateStCnab(log.getIdBoleto());
					 }catch(Exception e){
						e.printStackTrace();
						logger.error(new BasicAttachmentMessage("Falha ao alterar ST_CNAB do boleto: "+log.getIdBoleto() + " erro: " + e.getMessage()
									 , AttachmentMessageLevel.ERROR));
					 }
				 }
				 
			}					
			
			//resposta tratada pela API onde deve existir ResponseCode - FIM
						
			String codigoRetornoHUB = campoCodigoRetornoHUB; //Codigo de retorno da operadora do cartao devolvida pelo HUB
			if(campoCodigoRetornoHUB.equals("4")||campoCodigoRetornoHUB.equals("6")){ //responses de sucesso do HUB
				codigoRetornoHUB = "000";
			}
			
			try{
				log.setIdCodigoRetornoHub(Long.valueOf(codigoRetornoHUB));
			}catch(NumberFormatException e){
				logger.info(new BasicAttachmentMessage("código de retorno não informado (responseCode)" 
						 , AttachmentMessageLevel.INFO));
				
				log.setIdCodigoRetornoHub(null); // HUB nao devolveu codigo de retorno
			}
			
						
			logger.debug("Atualizando log com informações do HUB..."+log.toString());
			efetuaChamadaProcLog(log); //Segundo log com informacoes do HUB (update)
			
			ControleArquivoService serviceCCHub = getService(ControleArquivoService.class);
			
			List listRetornoBaixa = new ArrayList();
									
			if(log.getIdCodigoRetornoHub() != null){
				BatchParameter[] ps = new BatchParameter[] { 
						new BatchParameter(log.getIdBoleto(), Types.NUMERIC),                        //p_id_boleto
						new BatchParameter(codigoRetornoHUB, Types.VARCHAR),                         //p_cd_ret_cc
						new BatchParameter(String.valueOf(log.getNumeroTransacao()), Types.VARCHAR), //p_cd_trans_cc
						new BatchParameter(0, Types.NUMERIC),                        				 //p_cd_adquirente
						new BatchParameter(campoAdquirente, Types.VARCHAR),                        	 //p_nome_adquirente
						new BatchParameter(null, Types.NUMERIC),                                     //p_gera_arq
						new BatchParameter(Types.NUMERIC, true),                                     //p_retorno
						new BatchParameter(Types.VARCHAR, true)                                      //p_msg_retorno
						
				};
				
				logger.info(new BasicAttachmentMessage("Efetuando chamada na procedure de baixa cartão para boleto "+log.getIdBoleto() 
						 , AttachmentMessageLevel.INFO));
				listRetornoBaixa = serviceCCHub.executaBaixaCCHub(ps);
				logger.info(new BasicAttachmentMessage("Finalizada chamada na procedure de baixa cartão para boleto "+log.getIdBoleto() 
						 , AttachmentMessageLevel.INFO));
			}

			if (listRetornoBaixa.size() > 0){
				try{
					log.setStatusBaixaNetsms(listRetornoBaixa.get(6).toString()); //p_retorno
					log.setObs(listRetornoBaixa.get(7).toString());
				}catch(NullPointerException npe){
					log.setStatusBaixaNetsms(""); //p_retorno
					log.setObs("");
				}
			} else {
				log.setStatusBaixaNetsms(""); //p_retorno
				log.setObs("");
				
				logger.info(new BasicAttachmentMessage("Falha ao efetuar a baixa para o boleto: "+log.getIdBoleto() , AttachmentMessageLevel.ERROR));
			}
			
			logger.info(new BasicAttachmentMessage("Gravando retorno da baixa para o boleto: "+log.getIdBoleto(), AttachmentMessageLevel.INFO));
			efetuaChamadaProcLog(log); //terceiro log com informacoes da baixa netsms (update)

			logger.info(new BasicAttachmentMessage("Alterando Status do Lote: "+log.getIdLote(), AttachmentMessageLevel.INFO));
			alteraStatusLote(log);

			// retornos da procedure
			// 0 = Sucesso
			// 1 = Alterada a Cobranca
		    // 2 = Alterado o Contrato e a Cobrança
			
			if(listRetornoBaixa.size() > 0){
				
				if(listRetornoBaixa.get(6).toString().equals("0")){
					logger.info(new BasicAttachmentMessage("Baixa efetuada com sucesso", AttachmentMessageLevel.INFO));
				}else{
					if(listRetornoBaixa.get(6).toString().equals("1")){
						logger.info(new BasicAttachmentMessage("Cobranca alterada para boleto", AttachmentMessageLevel.INFO));
					}else{
						if(listRetornoBaixa.get(6).toString().equals("2")){
							logger.info(new BasicAttachmentMessage("Modalidade do contrato alterada para boleto", AttachmentMessageLevel.INFO));
							
							String requestDeleteTokenHub = "clientIdentification="+log.getNumContrato()+"&token="+log.getTokenCC();
							String respostaDeleteTokenHub = "";
							
							try{
								logger.info(new BasicAttachmentMessage("Request do delete do token enviada ao HUB "+requestDeleteTokenHub, AttachmentMessageLevel.INFO));
								respostaDeleteTokenHub = deleteTokenHub(requestDeleteTokenHub, log.getCidContrato());
								logger.info(new BasicAttachmentMessage("Resposta do delete do token no HUB "+respostaDeleteTokenHub, AttachmentMessageLevel.INFO));

							}catch(Exception e){
								e.printStackTrace();
								logger.info(new BasicAttachmentMessage("Resposta do delete do token no HUB "+respostaDeleteTokenHub+" - "+e.getMessage(), AttachmentMessageLevel.ERROR));
							}
							
						}else{
							logger.info(new BasicAttachmentMessage("Ocorreu erro ao excluir token via HUB: "+listRetornoBaixa.get(6).toString(), AttachmentMessageLevel.ERROR));
						}
					}
				}
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error(new BasicAttachmentMessage("Falha no processo de baixa C/C HUB de pagamentos: " + e.getMessage()
					 , AttachmentMessageLevel.ERROR));
		}
		
		
	}
	
	//altera status dos lotes de cartao de credito online durante emissao
	private void alteraStatusLote(BaixaLogHubDTO log) {
		
		BatchParameter[] ps = new BatchParameter[] { 
				new BatchParameter(log.getIdLote(), Types.NUMERIC, false),
				new BatchParameter(null, Types.NUMERIC, true)
		};
		
		ControleArquivoService serviceCCHub = getService(ControleArquivoService.class);
		
		Long filasPendentes = null;

		filasPendentes = serviceCCHub.alteraStatusLoteCC(ps);
		
		if(filasPendentes > 0){
			logger.info("Filas pendentes para o lote: "+log.getIdLote() + " " +filasPendentes);
		}

	}
	
	private Long efetuaChamadaProcLog(BaixaLogHubDTO log){
		// Reduzir num parametros - sonar
		String reqRespHub = log.getNumeroTransacao() == null ? log.getRequestHub() : log.getRespostaHub();
		
		BatchParameter[] ps = new BatchParameter[] { 
				new BatchParameter(null, Types.NUMERIC, true),                        //r_numero_transacao out
				new BatchParameter(log.getNumeroTransacao(), Types.NUMERIC, false),   //p_numero_transacao in
				new BatchParameter(log.getIdFila(), Types.NUMERIC, false),            //id_fila
				new BatchParameter(log.getIdLote(), Types.NUMERIC, false),            //id_lote
				new BatchParameter(log.getIdBoleto(), Types.NUMERIC, false),          //id_boleto
				new BatchParameter(log.getValor(), Types.DOUBLE, false),              //vl_boleto
				new BatchParameter(reqRespHub, Types.VARCHAR, false),                 //p_reqresphub
				new BatchParameter(log.getStatusBaixaNetsms(), Types.VARCHAR, false), //cd_status_baixa
				new BatchParameter(log.getObs(), Types.VARCHAR, false),               //tx_obs_baixa
				new BatchParameter(log.getIdCodigoRetornoHub(), Types.NUMERIC, false) //id_codigo_retorno_hub
		};
		
		
		ControleArquivoService serviceCCHub = getService(ControleArquivoService.class);
		
		Long numeroTransacao = null;

		numeroTransacao = serviceCCHub.efetuaLogBaixaHub(ps);
		
		return numeroTransacao;
	}
	
	private String envioBaixaHub(String request, String cidContrato){
		
		String respostaHUB = "";
		
		ApigeeService service = getService(ApigeeService.class);
		
		try {
			String uriToken = getParamInstrucao(PARAM_APIGEE_HUB_URL_AUTH, cidContrato);
			String auth = getParamInstrucao(PARAM_APIGEE_HUB_USER_AUTH, cidContrato);
			
			String token = service.getApigeeToken(uriToken, auth, PARAM_APIGEE_HUB_CC_TOKEN, PARAM_APIGEE_HUB_CC_TOKEN_EXP);
			
			String uri = getParamInstrucao(PARAM_APIGEE_HUB_URL, cidContrato);

			ClientResponse response = service.doPostRequest(uri, CONTENT_TYPE_JSON, request, token);
			
			respostaHUB = response.getEntity(String.class);
			
			
			int responseHTTPStatus = response.getStatus();
			
	        if (responseHTTPStatus != HttpURLConnection.HTTP_OK) { 
	        	
	        	 logger.info(new BasicAttachmentMessage("Falha no envio/retorno do HUB de pagamentos : " + response.getStatus() + " " + response.getEntity(String.class)
						 , AttachmentMessageLevel.ERROR));	        	 	 	        	 	        
	        }
	        

	      } catch (Exception e) {
	    	  logger.error(new BasicAttachmentMessage("Falha no envio/retorno do HUB de pagamentos : " + e.getMessage()
						 , AttachmentMessageLevel.ERROR));
				throw new RuntimeException("Falha no envio/retorno do HUB de pagamentos : " + e.getMessage());
	      }
							
		return respostaHUB;
	}
	
	private String deleteTokenHub(String queryString, String cidContrato){
		
		String respostaHUB = "";
		
		ApigeeService service = getService(ApigeeService.class);
		
		try {
			String uriToken = getParamInstrucao(PARAM_APIGEE_HUB_URL_AUTH, cidContrato);
			String auth = getParamInstrucao(PARAM_APIGEE_HUB_USER_AUTH, cidContrato);
			
			String token = service.getApigeeToken(uriToken, auth, PARAM_APIGEE_HUB_CC_TOKEN, PARAM_APIGEE_HUB_CC_TOKEN_EXP);
			
			String uri = getParamInstrucao(PARAM_APIGEE_HUB_DEL_URL, cidContrato);

			ClientResponse response = service.doDeleteRequest(uri, CONTENT_TYPE_JSON, null, token, queryString);
			
			respostaHUB = response.getEntity(String.class);			
			
			int responseHTTPStatus = response.getStatus();
			
	        if (responseHTTPStatus != HttpURLConnection.HTTP_OK) { 
	        	
	        	 logger.info(new BasicAttachmentMessage("Falha no envio/retorno do HUB de pagamentos : " + response.getStatus() + " " + response.getEntity(String.class)
						 , AttachmentMessageLevel.ERROR));	        	 	 	        	 	        
	        }
	        
	      } catch (Exception e) {
	    	  logger.error(new BasicAttachmentMessage("Falha no envio/retorno do HUB de pagamentos : " + e.getMessage()
						 , AttachmentMessageLevel.ERROR));
				throw new RuntimeException("Falha no envio/retorno do HUB de pagamentos : " + e.getMessage());
	      }
							
		return respostaHUB;
	}
	
	private String getParamInstrucao(String nomeParam, String cidContrato) {
		ControleArquivoService serviceCCHub = getService(ControleArquivoService.class);
		
		return serviceCCHub.getParamInstrucao(nomeParam, cidContrato);
	}

	/**
	 * Método para processamento da Fatura quando o Controle de segragação
	 * estiver com o estado de Desligado. Localiza cada fatura que deve ser
	 * gerada para faturamento.
	 *
	 * @version 1.0 - criação do Metodo - 29/03/2006
	 * @number RF015_RF021
	 * @return void
	 * @param Collection
	 *            lote
	 * @param Operadora
	 *            operadora
	 * @param ArquivoPrintHouseDTO
	 *            arquivoPrintHouseDTO
	 * @semantics <br>	
	 * 
	 * @ejb.transaction type="Required"
	 * 
	 */
	protected void processarArquivo(Collection<SnLoteBean> lotes,
			                        SnCidadeOperadoraBean cidade,
			                        ArquivoPrintHouseDTO arquivoPrintHouseDTO) throws Exception {

		SnTipoLoteBean tipoLote = this.getTipoLote(lotes);
		
		// Lista contendo todas as faturas para poder inserir na pp_vlr_parceiro_fatura
		List<DadosFatura> lstDadosFatura = new ArrayList<DadosFatura>();
		
		List<LoteBoletoFaturaDTO> colLoteBoletoFaturaDTO = new ArrayList<LoteBoletoFaturaDTO>();
		
		Date dtVenctoLote = null;
		String idCriterio = null;
		Integer seq_arquivo = 0;
		String [] lotesList = new String[lotes.size()];
		
		int i = 0;
		
		ArquivoPrintHouseDAO dao = getAphDao(); 
		List<MensagemDTO> colPrefixos  = null;
		for (SnLoteBean lote : lotes) {			
			List<LoteBoletoFaturaDTO> list = dao.buscarBoletosFaturas(lote);
			
			colLoteBoletoFaturaDTO.addAll(list);
			colPrefixos = getDadosBeanByQuery("selecionarPrefixo", lote, false, MensagemDTO.class);
			
			// Obtem a data de vencimento e o idCriterio do primeiro lote
			if(dtVenctoLote == null) {
				dtVenctoLote = lote.getDtVcto();
				idCriterio = lote.getSnCriterioSegmentacaoBoletoBean().getIdCriterio().toString();
			}
			
			lotesList[i] = String.valueOf(lote.getIdLote());
			i++;
		}
		
		SnControleArquivoBean arquivo = this.inicializarArquivoPrint(arquivoPrintHouseDTO, 
		                                                             cidade, 
		                                                             dtVenctoLote, 
		                                                             tipoLote, 
		                                                             idCriterio,
		                                                             seq_arquivo,
		                                                             lotesList);
		
		arquivoPrintHouseDTO.getCurrentArquivo().setPrefixo(colPrefixos.get(0).getPrefixo());
		if (colLoteBoletoFaturaDTO != null && colLoteBoletoFaturaDTO.size() > 0) {

			// Utilzado para performance : para não buscar pelo o lote de novo
			List<SnLoteBean> listLotes = new ArrayList<SnLoteBean>(lotes);

			this.ordenarFaturaPeloCepCobranca(colLoteBoletoFaturaDTO);
			
			//Object [] boletos = new LoteBoletoFaturaDTO[colLoteBoletoFaturaDTO.size()];
			//boletos = colLoteBoletoFaturaDTO.toArray();
			
			ArquivoPrintHouseDAO printDAO = getAphDao();
			//List<FaturaNetDTO> fauras = printDAO.buscarDadosFaturaNetArray(idCriterio,boletos);
			
			int posicaoInicial = 0;			
			Integer NUMERO_QUEBRA = null;
			
			try {
				NUMERO_QUEBRA = Integer.parseInt(String.valueOf(obterParametroObrigatorioNumerico(cidade.getCidContrato(), PrintHouseConstants.Parametro.QTD_RANGE_BLT_PRINT)));
			}catch(NumberFormatException e){			
				logger.info(new BasicAttachmentMessage(" Parametro QTD_RANGE_BLT_PRINT nao cadastrado ."						
						, AttachmentMessageLevel.INFO));								
			}catch(Exception ex ){				
				logger.info(new BasicAttachmentMessage(" Parametro QTD_RANGE_BLT_PRINT nao cadastrado ."	
						, AttachmentMessageLevel.INFO));
			}
			
			if ( NUMERO_QUEBRA == null){
				NUMERO_QUEBRA = 500;
			}
			
			
			int posicaoFinal = NUMERO_QUEBRA -1;
			Boolean alterarVar = Boolean.FALSE;
			Boolean terminou = Boolean.TRUE;
			//int i = 0;
			List<FaturaNetDTO> faturasOrdenadas = null;
			
			if(posicaoFinal >= colLoteBoletoFaturaDTO.size()  ){
				alterarVar = Boolean.TRUE;
				posicaoFinal = colLoteBoletoFaturaDTO.size() - 1;
			}
			
		    while(terminou){
				 List<LoteBoletoFaturaDTO> boletos = colLoteBoletoFaturaDTO.subList(posicaoInicial, posicaoFinal+1);
				 LoteBoletoFaturaDTO [] array =  boletos.toArray(new LoteBoletoFaturaDTO[boletos.size()]);

				 CarregadorBoletoPrint carregador = CarregadorBoletoPrintFactory.getInstance(arquivoPrintHouseDTO);
				 faturasOrdenadas = carregador.carrega(new EventoCargaBoletoPrint(array,
				                                                                 arquivoPrintHouseDTO,
				                                                                 printDAO,
				                                                                 idCriterio));
				 
				 for (FaturaNetDTO loteBoletoFatura : faturasOrdenadas) {
					 
					 // Encontra o objeto lote correspondente
					 SnLoteBean lote = null;
					 for(SnLoteBean item : lotes){					
						 if(item.getIdLote() == loteBoletoFatura.getIdLote()){ 
							 lote = item;
							 break;
						 }
					 }
					 
					 try {
						 
						 try {
							 
							 setDataVencimentoRegistroEmissao(arquivoPrintHouseDTO.getRegistroEmissao(), 
							                                  loteBoletoFatura.getDataVencimento());
							 
							 if(lote.getSnCriterioSegmentacaoBoletoBean() != null) {
								 loteBoletoFatura.setIdCriterio(lote.getSnCriterioSegmentacaoBoletoBean().getIdCriterio()); 
							 }
							 
						 } catch (Exception ex) {
							 
							 logger.info(new BasicAttachmentMessage("Lote [" + lote.getIdLote()
									 + "] - Boleto [" + loteBoletoFatura.getIdBoleto()
									 + "] com problema identificado antes de incluir linhas no arquivo."
									 , AttachmentMessageLevel.INFO));
							 
							 throw new EmissaoBusinessResourceException(EmissaoResources.ERRO_BOLETO_COM_PROBLEMA, ex);
							 
						 }
						 
						 DadosFatura dadosFatura = this.gravarFaturaArquivoPrintHouse(lote, loteBoletoFatura, arquivoPrintHouseDTO);
						 
						 try {
							 
							 // Incluir na coleção se objeto faturaDTO estiver preenchido
							 if (dadosFatura != null) {
								 lstDadosFatura.add(dadosFatura);
							 }
							 
							 if (arquivoPrintHouseDTO.getCurrentArquivo().getGerarArquivo()
									 .getQtdLinhasGerados().intValue() > arquivoPrintHouseDTO
									 .getQuantidadeMaximaNumeroLinhasArquivo().intValue()) {
								 seq_arquivo++;
								 this.finalizarArquivo(Boolean.TRUE, tipoLote,
										 loteBoletoFatura.getDataVencimento(), arquivo,
										 arquivoPrintHouseDTO,lotesList,idCriterio,seq_arquivo);
								 
							 } else if (arquivoPrintHouseDTO.getCurrentArquivo()
									 .getQuantidadeFaturasEmitidas() > arquivoPrintHouseDTO
									 .getQuantidadeFaturaPorArquivoPrint()) {
								 seq_arquivo++;
								 this.finalizarArquivo(Boolean.TRUE, tipoLote,
										 loteBoletoFatura.getDataVencimento(), arquivo,
										 arquivoPrintHouseDTO,lotesList,idCriterio,seq_arquivo);
								 
							 }
							 
						 }
						 catch (Exception ex) {
							 
							 logger.info(new BasicAttachmentMessage("Lote [" + lote.getIdLote()
									 + "] - Boleto [" + loteBoletoFatura.getIdBoleto()
									 + "] com problema identificado apos incluir linhas no arquivo."
									 , AttachmentMessageLevel.INFO));
							 
							 // Grava sn_log_erro_abort em transacao separada (efetua commit)
							 LogErroAbortService leaService = getService(LogErroAbortService.class);
							 leaService.inserirLogErrorAbort(loteBoletoFatura.getCidContrato()
									 , loteBoletoFatura.getNumContrato()
									 , null
									 , null
									 , "ERRO NO BOLETO: " + loteBoletoFatura.getIdBoleto() + 
									 " DO LOTE: " + lote.getIdLote() + 
									 ". ABORTANDO PROCESSAMENTO. ERRO: " + this.getStackTrace(ex).substring(0, 3800)
									 , "EMISSAO PRINTHOUSE"
									 , null);

							 throw new RuntimeException(ex);
						 }
						 
					 } catch (EmissaoBusinessResourceException ex) {
						 
						 logger.info(new BasicAttachmentMessage("Lote [" + lote.getIdLote()
								 + "] - Boleto [" + loteBoletoFatura.getIdBoleto()
								 + "] sera movido para lote com erro."
								 , AttachmentMessageLevel.INFO));
						 
						 logger.error(new BasicAttachmentMessage("Erro: " + this.getStackTrace(ex), AttachmentMessageLevel.ERROR), ex);
						 
						 this.moverBoletoLote(loteBoletoFatura, lote, this.getStackTrace(ex).substring(0, 3800));
						 
					 }
					 
				 }

				 posicaoInicial = ++posicaoFinal;
				 posicaoFinal +=  ( NUMERO_QUEBRA - 1 );
				 if(alterarVar){
					 terminou = Boolean.FALSE;
				 }
				 if(posicaoFinal >= colLoteBoletoFaturaDTO.size()  ){
					 alterarVar = Boolean.TRUE;
					 posicaoFinal = colLoteBoletoFaturaDTO.size() - 1;
				 }
			
			}
			
		}

		this.finalizarArquivo(Boolean.FALSE, tipoLote, dtVenctoLote, arquivo,
				arquivoPrintHouseDTO,lotesList,idCriterio,seq_arquivo);
		
		// Insere em lote na pp_vlr_parceiro_fatura
		this.inserirPPVlrParceiroBatch(arquivoPrintHouseDTO, lstDadosFatura);
		
		//Insere em lote na  sn_movimento_cobranca
		this.inserirMovimentoCobrancaBatch(arquivoPrintHouseDTO, lstDadosFatura);
		
		//Insere em lote na sn_interface_parceiro
		this.inserirInterfaceParceiroBatch(arquivoPrintHouseDTO, lstDadosFatura);
	}

	// Mapeamento que guarda os lotes processados e o lote de erro caso houve erro no lote
	private Map<Integer, SnLoteBean> loteProcessado;
	
	private void setLoteProcessadoMap(Map<Integer, SnLoteBean> loteProcessado) {
		this.loteProcessado = loteProcessado;
	}
	
	private Map<Integer, SnLoteBean> getLoteProcessadoMap() {
		if (this.loteProcessado == null) {
			this.loteProcessado = new HashMap<Integer, SnLoteBean>();
		}
		return this.loteProcessado;
	}

	private SnLoteBean snLoteNovo;
	
	private void setSnLoteBean(SnLoteBean snLoteNovo) {
		this.snLoteNovo = snLoteNovo;
	}
	
	private SnLoteBean getSnLoteBean() {
		return this.snLoteNovo;
	}
	
	private SnSituacaoProcessamentoBean situacaoProcessamentoErro;
	
	private SnSituacaoProcessamentoBean getSituacaoProcessamentoERRO() {

		if(this.situacaoProcessamentoErro == null) {
			// Pesquisa a situacao de processamento ERRO
			this.situacaoProcessamentoErro = this.pesquisarSituacaoProcessamento(SnSituacaoProcessamentoBean.Sigla.ERRO.getSigla());
		}

		return this.situacaoProcessamentoErro;
		
	}
	
	private SnSituacaoProcessamentoBean pesquisarSituacaoProcessamento(String sigla) {
		// Pesquisa a situacao de processamento
		SnSituacaoProcessamentoBean situacaoProcessamento = new SnSituacaoProcessamentoBean();
		situacaoProcessamento.setSgSituacaoProcessamento(sigla);
		return (SnSituacaoProcessamentoBean) super.search("lstSnSituacaoProcessamentoBySigla", situacaoProcessamento).get(0);		
	}
	
	private void moverBoletoLote(FaturaNetDTO loteBoletoFatura, SnLoteBean snLoteOrigem, String errorMessage) { 
		
		// Obtem do map o lote novo correspondente ao atual
		SnLoteBean snLoteNovo = this.getLoteProcessadoMap().get(snLoteOrigem.getIdLote());

		// Pesquisa boleto com problema
		SnBoletoBean boleto = new SnBoletoBean();
		boleto.setIdBoleto(loteBoletoFatura.getIdBoleto());
		boleto = (SnBoletoBean)find(boleto);
		
		if (snLoteNovo == null) {
			
			// Cria lote novo para agrupar os boletos com erro do lote atual
			snLoteNovo = copiarLote(snLoteOrigem, this.getSituacaoProcessamentoERRO(), boleto.getVlDocumento());
			
			logger.info(new BasicAttachmentMessage("Cria Lote [" + snLoteNovo.getIdLote()
					+ "]"
					, AttachmentMessageLevel.INFO));

			// Coloca o novo lote no map para o lote em questao
			this.getLoteProcessadoMap().put(snLoteOrigem.getIdLote(), snLoteNovo);
			
		}
		else {
			
			// Atualiza total e valor de boletos no lote de erro
			snLoteNovo.setNrQtdBoleto(snLoteNovo.getNrQtdBoleto() + 1); // incrementa um boleto
			snLoteNovo.setVlTotal(snLoteNovo.getVlTotal() + boleto.getVlDocumento()); // soma valor do boleto
			super.update(snLoteNovo, super.getUserSession().getCurrentDbService());
			
			logger.info(new BasicAttachmentMessage("Atualiza Lote [" + snLoteNovo.getIdLote()
					+ "] com quantidade de boletos [" + snLoteNovo.getNrQtdBoleto()
					+ "] e valor total [" + snLoteNovo.getVlTotal()
					+ "]"
					, AttachmentMessageLevel.INFO));

		}
		
		// Grava boleto na SnRelLoteBoleto para o lote de Erro
		SnRelLoteBoletoBean snRelLoteBoletoNovo = new SnRelLoteBoletoBean();
		snRelLoteBoletoNovo.setCompositeKey(new SnRelLoteBoletoKey(boleto.getIdBoleto(), snLoteNovo.getIdLote()));
		snRelLoteBoletoNovo = (SnRelLoteBoletoBean)super.insert(snRelLoteBoletoNovo, super.getUserSession().getCurrentDbService());

		logger.info(new BasicAttachmentMessage("Grava boleto na sn_rel_lote_boleto para o Lote [" + snLoteNovo.getIdLote()
				+ "] e boleto [" + boleto.getIdBoleto()
				+ "]"
				, AttachmentMessageLevel.INFO));

		// Remove boleto da SnRelLoteBoleto para o lote atual
		SnRelLoteBoletoBean snRelLoteBoleto = new SnRelLoteBoletoBean();
		snRelLoteBoleto.setCompositeKey(new SnRelLoteBoletoKey(boleto.getIdBoleto(), snLoteOrigem.getIdLote()));
		super.delete(snRelLoteBoleto, super.getUserSession().getCurrentDbService());

		logger.info(new BasicAttachmentMessage("Remove boleto da sn_rel_lote_boleto para o Lote [" + snLoteOrigem.getIdLote()
				+ "] e boleto [" + boleto.getIdBoleto()
				+ "]"
				, AttachmentMessageLevel.INFO));

		// Atualiza total e valor de boletos no lote atual
		snLoteOrigem.setNrQtdBoleto(snLoteOrigem.getNrQtdBoleto() - 1); // decrementa um boleto
		snLoteOrigem.setVlTotal(snLoteOrigem.getVlTotal() - boleto.getVlDocumento()); // subtrai valor do boleto
		super.update(snLoteNovo, super.getUserSession().getCurrentDbService());
		
		logger.info(new BasicAttachmentMessage("Atualiza Lote [" + snLoteOrigem.getIdLote()
				+ "] com quantidade de boletos [" + snLoteOrigem.getNrQtdBoleto()
				+ "] e valor total [" + snLoteOrigem.getVlTotal()
				+ "]"
				, AttachmentMessageLevel.INFO));

		// Grava sn_log_erro_abort em transacao separada (efetua commit)
		LogErroAbortService leaService = getService(LogErroAbortService.class);
		leaService.inserirLogErrorAbort(boleto.getContrato().getCompositeKey().getCidContrato()
				, boleto.getContrato().getCompositeKey().getNumContrato()
				, null
				, null
				, "ERRO NO BOLETO: " + boleto.getIdBoleto() + 
					" DO LOTE: " + snLoteOrigem.getIdLote() + 
					". MOVENDO PARA LOTE: " + snLoteNovo.getIdLote() + ". ERRO: " + errorMessage
				, "EMISSAO PRINTHOUSE"
				, null);

	}
	
	private SnLoteBean copiarLote(SnLoteBean snLoteOrigem, SnSituacaoProcessamentoBean situacaoProcessamento, double vlTotal) {
		
		// Efetua copia do lote original
		SnLoteBean snLote = new SnLoteBean();
		snLote.setDtVcto(snLoteOrigem.getDtVcto());
		snLote.setNrQtdBoleto(1);
		snLote.setVlTotal(vlTotal);
		snLote.setDtGeracao(snLoteOrigem.getDtGeracao());
		snLote.setSnTipoLoteBean(snLoteOrigem.getSnTipoLoteBean());
		snLote.setSnTipoCobrancaBean(snLoteOrigem.getSnTipoCobrancaBean());
		snLote.setSnOperadoraCartaoBean(snLoteOrigem.getSnOperadoraCartaoBean());
		snLote.setSnCidadeOperadoraBean(snLoteOrigem.getSnCidadeOperadoraBean());
		snLote.setSnBancoBean(snLoteOrigem.getSnBancoBean());
		snLote.setSnCriterioSegmentacaoBoletoBean(snLoteOrigem.getSnCriterioSegmentacaoBoletoBean());
		snLote.setSnCcBean(snLoteOrigem.getSnCcBean());
		snLote.setSituacaoProcessamento(situacaoProcessamento);
		
		// Insere novo lote
		snLote = (SnLoteBean) super.insert(snLote, super.getUserSession().getCurrentDbService());
		
		return snLote;
		
	}

	/**
	 * Método para processamento da Fatura quando o Controle de segragação
	 * estiver com o estado de Ligado.<br>
	 * Localiza cada fatura que deve ser gerada para faturamento.
	 *	
	 * @version 1.0 - criação do Metodo - 29/03/2006
	 * @number RF015_RF021
	 * @return void
	 * @param Lote
	 *            lote
	 * @param Operadora
	 *            operadora
	 * @param ArquivoPrintHouseDTO
	 *            arquivoPrintHouseDTO
	 * @semantics <br> 
	 * 
	 * @ejb.transaction type="Required"
	 * 
	 */
	protected void processarArquivo(SnLoteBean lote,
			                        SnCidadeOperadoraBean cidade,
			                        ArquivoPrintHouseDTO arquivoPrintHouseDTO) throws Exception {

		// Lista contendo todas as faturas para poder inserir na pp_vlr_parceiro_fatura
		List<DadosFatura> lstDadosFatura = new ArrayList<DadosFatura>();
		Integer seq_arquivo = 0;
		Collection<MensagemDTO> colPrefixos = getDadosBeanByQuery("selecionarPrefixo", lote, false, MensagemDTO.class);

		logger.info(new BasicAttachmentMessage("Obtém coleção de prefixos.", AttachmentMessageLevel.INFO));
		
		// Obtem o idCriterio de segmentacao do lote
		String idCriterio = lote.getSnCriterioSegmentacaoBoletoBean().getIdCriterio().toString();
		String [] lotes = new String[]{String.valueOf(lote.getIdLote())};
		
		
		for (MensagemDTO prefixo : colPrefixos) {
			
			// String prefixo = (String) iterColPrefixos.next();
			arquivoPrintHouseDTO.iniciarArquivo(cidade, Boolean.TRUE);
			arquivoPrintHouseDTO.getCurrentArquivo().setPrefixo(prefixo.getPrefixo());
			
			SnControleArquivoBean arquivo = this.inicializarArquivoPrint(arquivoPrintHouseDTO, 
			                                                             cidade, 
			                                                             lote.getDtVcto(), 
			                                                             lote.getSnTipoLoteBean(), 
			                                                             idCriterio,
			                                                             seq_arquivo, 
			                                                             lotes);
			
			lote.setPrefixo(prefixo.getPrefixo());
			ArquivoPrintHouseDAO dao = getAphDao();
			
			List<LoteBoletoFaturaDTO> colBolFatura = dao.buscarBoletosFaturas(lote);
						
			logger.info(new BasicAttachmentMessage("Obtém coleção de faturas para lote " + 
			                                             lote.getIdLote() + " e prefixo " + prefixo.getPrefixo() + ".", 
			                                       AttachmentMessageLevel.INFO));
			
			if (colBolFatura != null && colBolFatura.size() > 0) {
				
				this.ordenarFaturaPeloCepCobranca(colBolFatura);
				
				String mensagen = 
						"Ordena faturas pelo cep de cobrança e itera a coleção de faturas para gerar o arquivo.";
				
				logger.info(new BasicAttachmentMessage(mensagen, AttachmentMessageLevel.INFO));
							
				ArquivoPrintHouseDAO printDAO = getAphDao();
							
				setDataVencimentoRegistroEmissao(arquivoPrintHouseDTO.getRegistroEmissao(), lote.getDtVcto());
					
				int posicaoInicial = 0;
				
				Boolean alterarVar = Boolean.FALSE;
				Boolean terminou = Boolean.TRUE;
				
				Integer NUMERO_QUEBRA = null;
				
				try {
					NUMERO_QUEBRA = Integer.parseInt(String.valueOf(obterParametroObrigatorioNumerico(cidade.getCidContrato(), PrintHouseConstants.Parametro.QTD_RANGE_BLT_PRINT)));
				}catch(NumberFormatException e){			
					logger.info(new BasicAttachmentMessage(" Parametro QTD_RANGE_BLT_PRINT nao cadastrado ."						
							, AttachmentMessageLevel.INFO));								
				}catch(Exception ex ){				
					logger.info(new BasicAttachmentMessage(" Parametro QTD_RANGE_BLT_PRINT nao cadastrado ."	
							, AttachmentMessageLevel.INFO));
				}
				
				if ( NUMERO_QUEBRA == null){
					NUMERO_QUEBRA = 500;
				}
				int posicaoFinal = NUMERO_QUEBRA -1;
				//int i = 0;
				List<FaturaNetDTO> faturasOrdenadas = null;
				
				if(posicaoFinal >= colBolFatura.size()  ){
					alterarVar = Boolean.TRUE;
					posicaoFinal = colBolFatura.size() - 1;
				}
				
				BigDecimal isolaLote = BigDecimal.ZERO;
				try {
                    isolaLote = obterParametroObrigatorioNumerico(cidade.getCidContrato(), REMOVE_BOL_LOTE_SEG_VIA);
				} catch (EmissaoBusinessResourceException e){
                    logger.info("Parametro de configuracao que isola boletos de segunda via com erros em um novo lote não foi encontrado. Configurando valor Default (desligado)."); 
				}   
				
				while(terminou){
					 List<LoteBoletoFaturaDTO>  boletos = colBolFatura.subList(posicaoInicial, posicaoFinal+1);
					 LoteBoletoFaturaDTO [] array =  boletos.toArray(new LoteBoletoFaturaDTO[boletos.size()]);
					 
                    CarregadorBoletoPrint carregador = CarregadorBoletoPrintFactory.getInstance(arquivoPrintHouseDTO);
					faturasOrdenadas = carregador.carrega(new EventoCargaBoletoPrint(array,
					                                                                 arquivoPrintHouseDTO,
					                                                                 printDAO,
					                                                                 idCriterio));

					if (isolaLote.compareTo(BigDecimal.ONE) == 0){
                        trataBoletosEncontrados(boletos,faturasOrdenadas, lote);
					}
					 
					 for (FaturaNetDTO loteBoletoFaturaDTO : faturasOrdenadas) {
						 
						 
						 // Flag de controle de rollback se houver erro no boleto
						 boolean efetuarRollback = false;		
						 
						 try {								
							 // Liga o flag para efetuar rollback caso haja erro a partir deste ponto
							 efetuarRollback = true;
							 
							 DadosFatura dadosFatura = this.gravarFaturaArquivoPrintHouse(lote, 
							                                                              loteBoletoFaturaDTO, 
							                                                              arquivoPrintHouseDTO);
							 
							 try {
								 
								 // Incluir na coleção se objeto dadosFatura estiver preenchido
								 if (dadosFatura != null) {
									 lstDadosFatura.add(dadosFatura);
								 }
								 
								 if (arquivoPrintHouseDTO.getCurrentArquivo()
										 .getGerarArquivo().getQtdLinhasGerados().intValue() > arquivoPrintHouseDTO
										 .getQuantidadeMaximaNumeroLinhasArquivo()
										 .intValue()) {
									 seq_arquivo++;
									 this.finalizarArquivo(Boolean.TRUE, lote
											 .getSnTipoLoteBean(), lote.getDtVcto(),
											 arquivo, arquivoPrintHouseDTO,lotes,idCriterio,seq_arquivo);
									 
								 } else if (arquivoPrintHouseDTO.getCurrentArquivo()
										 .getQuantidadeFaturasEmitidas() >= arquivoPrintHouseDTO
										 .getQuantidadeFaturaPorArquivoPrint()) {
									 
									 seq_arquivo++;
									 this.finalizarArquivo(Boolean.TRUE, lote
											 .getSnTipoLoteBean(), lote.getDtVcto(), 
											 arquivo, arquivoPrintHouseDTO,lotes,idCriterio,seq_arquivo);
									 
								 }
								 
							 } catch (Exception ex) {

								 // Grava sn_log_erro_abort em transacao separada (efetua commit)
								 LogErroAbortService leaService = getService(LogErroAbortService.class);
								 leaService.inserirLogErrorAbort(loteBoletoFaturaDTO.getCidContrato()
										 , loteBoletoFaturaDTO.getNumContrato()
										 , null
										 , null
										 , "ERRO NO BOLETO: " + loteBoletoFaturaDTO.getIdBoleto() + 
										 " DO LOTE: " + lote.getIdLote() + 
										 ". ABORTANDO PROCESSAMENTO. ERRO: " + this.getStackTrace(ex).substring(0, 3800)
										 , "EMISSAO PRINTHOUSE"
										 , null);

								 throw new RuntimeException(ex);

							 }
							 
						 } catch (Exception ex) {
							 
							 if (efetuarRollback == false || ex instanceof EmissaoBusinessResourceException) {
								 // O erro aconteceu antes de gerar linha no arquivo, por isso nao e preciso fazer
								 // rollback do processamento
								 
								 logger.info(new BasicAttachmentMessage("Lote [" + lote.getIdLote()
										 + "] - Boleto [" + loteBoletoFaturaDTO.getIdBoleto()
										 + "] com problema identificado antes de incluir linhas no arquivo."
										 , AttachmentMessageLevel.INFO));
								 
								 logger.info(new BasicAttachmentMessage("Lote [" + lote.getIdLote()
										 + "] - Boleto [" + loteBoletoFaturaDTO.getIdBoleto()
										 + "] sera movido para lote com erro."
										 , AttachmentMessageLevel.INFO));
								 
								 logger.error(new BasicAttachmentMessage("Erro: " + ex.getMessage(), AttachmentMessageLevel.ERROR), ex);
								 
								 this.moverBoletoLote(loteBoletoFaturaDTO, lote, this.getStackTrace(ex).substring(0, 3800));
								 
							 } else {
								 
								 logger.info(new BasicAttachmentMessage("Lote [" + lote.getIdLote()
										 + "] - Boleto [" + loteBoletoFaturaDTO.getIdBoleto()
										 + "] com problema identificado apos incluir linhas no arquivo."
										 , AttachmentMessageLevel.INFO));
								 
								 throw ex;
								 
							 }
						 }					
						 //getCurrentDAO().flush();					
						 
					 }
					 
					 posicaoInicial = ++posicaoFinal;
					 posicaoFinal +=  ( NUMERO_QUEBRA - 1 );
					 if(alterarVar){
						 terminou = Boolean.FALSE;
					 }
					 if(posicaoFinal >= colBolFatura.size()  ){
						 alterarVar = Boolean.TRUE;
						 posicaoFinal = colBolFatura.size() - 1;
					 }
					 
				}
				this.finalizarArquivo(Boolean.FALSE, lote.getSnTipoLoteBean(),
						lote.getDtVcto(), arquivo, arquivoPrintHouseDTO,lotes,idCriterio,seq_arquivo);
				
				logger.info(new BasicAttachmentMessage("Finaliza arquivo.", AttachmentMessageLevel.INFO));
				
			}
			
		}
		
		// Insere em lote na pp_vlr_parceiro_fatura
		this.inserirPPVlrParceiroBatch(arquivoPrintHouseDTO, lstDadosFatura);
		
		// Insere em lote na  sn_movimento_cobranca
		this.inserirMovimentoCobrancaBatch(arquivoPrintHouseDTO, lstDadosFatura);
		
		//Insere em lote na sn_interface_parceiro
		this.inserirInterfaceParceiroBatch(arquivoPrintHouseDTO, lstDadosFatura);
		
	}

	private void trataBoletosEncontrados(List<LoteBoletoFaturaDTO> boletosPrevistosProcessamento, List<FaturaNetDTO> boletosProcessados, SnLoteBean lote){
	   
	   
		if (lote.getSnTipoLoteBean().getSgTipoLote().equals(SnTipoLoteBean.Sigla.SEGUNDA_VIA.getChaveSigla())) {
		   
		   Set<Long> boletosAEmitir = new HashSet<Long>();
		   for (LoteBoletoFaturaDTO bol : boletosPrevistosProcessamento)
		       boletosAEmitir.add(bol.getIdBoleto());
	    
		   Set<Long> boletosEmitidos = new HashSet<Long>();
		   for (FaturaNetDTO fat : boletosProcessados)
			   boletosEmitidos.add(fat.getIdBoleto());
		    
		   if (boletosAEmitir.size() == boletosEmitidos.size()){
			   return;
		   }
		   
		   boletosAEmitir.removeAll(boletosEmitidos);		   
		   
		   for (Long bol : boletosAEmitir){
			 logger.info(new BasicAttachmentMessage("Lote [" + lote.getIdLote() + "] - Boleto [" + bol + "] com problemas de geracao de arquivo", AttachmentMessageLevel.INFO));
			 logger.info(new BasicAttachmentMessage("Lote [" + lote.getIdLote() + "] - Boleto [" + bol + "] sera movido para lote com erro." , AttachmentMessageLevel.INFO));
			 
			 FaturaNetDTO fat = new FaturaNetDTO();
			 fat.setIdBoleto(bol);
			 this.moverBoletoLote(fat, lote, "");
	       }
	   }
    }

	/**
	 * @number RF015_RF021
	 * @param arquivoPrintHouseZipDTOs
	 * @return
	 * @throws JNetException
	 *     
     * @ejb.interface-method view-type = "both"
     * @ejb.transaction type="Required"
	 */	
	public   Collection finalizarProcesso(Collection arquivoPrintHouseZipDTOs , ArquivoPrintHouseDTO arquivo)  {

		Collection arquivoPrintHouseTransporteDTOs = new ArrayList();
		SnCidadeOperadoraBean cidade  = null;
		String prefixo = arquivo.getCurrentArquivo().getPrefixo();
		//Para cada arquivo ZIP
		for (Iterator iterZipDTOs = arquivoPrintHouseZipDTOs.iterator(); iterZipDTOs.hasNext();) {
			ArquivoPrintHouseZipDTO arquivoPrintHouseZipDTO = (ArquivoPrintHouseZipDTO) iterZipDTOs.next();
			cidade = arquivoPrintHouseZipDTO.getSnCidadeOperadoraBean();
			
			//Pegar os arquivos TXT dentro o ZIP
			Collection arquivoPrintHouseFiles = arquivoPrintHouseZipDTO.getArquivoPrintHouseFiles();
			
			//Gerar os objetos (com dados de destino) para subuir os arquivos
			Collection arquivoTransportes = this.generateArquivoPrintHouseTransporteDTOs(arquivoPrintHouseZipDTO.getFileGerado(), 
																						arquivoPrintHouseFiles, 
																						arquivoPrintHouseZipDTO.getSiglaTipoLote(),
																						prefixo,arquivoPrintHouseZipDTO.getDtVencimento(),cidade);
			arquivoPrintHouseTransporteDTOs.addAll(arquivoTransportes);	

			for (Iterator iterArquivoPrintHouseFiles = arquivoPrintHouseFiles.iterator(); iterArquivoPrintHouseFiles.hasNext();) {
				ArquivoPrintHouseFileDTO arquivoPrintHouseFile = (ArquivoPrintHouseFileDTO) iterArquivoPrintHouseFiles.next();

//				//Excluir o arquivo TXT (.rem)
				arquivoPrintHouseFile.getFileGerado().delete();
			}
		}
		
		return arquivoPrintHouseTransporteDTOs;
	}


	
	@SuppressWarnings("rawtypes")
    private  SnControleArquivoBean inicializarArquivoPrint(ArquivoPrintHouseDTO arquivoPrintHouseDTO,
	                                                       SnCidadeOperadoraBean cidade, 
	                                                       Date dataVencimentoLote,
	                                                       SnTipoLoteBean tipoLote, 
	                                                       String idCriterio,
	                                                       Integer sequencia, 
	                                                       String... lotes ) throws Exception {

		boolean controleSegregacao = arquivoPrintHouseDTO.getCurrentArquivo().getControleSegregacao().booleanValue();
		SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");

		String dtVencimentoLote = null;
		if (dataVencimentoLote != null) {
			dtVencimentoLote = dateFormat.format(dataVencimentoLote);
		}

		String dtEmissaoLote = dateFormat.format(arquivoPrintHouseDTO.getRegistroEmissao().getDtVctoInicial());

		String cidContrato = cidade.getCidContrato();
		String codigoOperadora = cidade.getCodOperadora();

		StringBuffer nomeArquivo = new StringBuffer();
		String idRegistroEmissao = String.valueOf(arquivoPrintHouseDTO.getRegistroEmissao().getIdRegistroEmissao());

		if (controleSegregacao && 
				SnTipoLoteBean.Sigla.EMISSAOPRINT.getChaveSigla().equals(tipoLote.getSgTipoLote())) {
			// Controle de emissão ligado

			nomeArquivo.append(arquivoPrintHouseDTO.getCurrentArquivo().getPrefixo());
			nomeArquivo.append(codigoOperadora);
			nomeArquivo.append(dtVencimentoLote);
			nomeArquivo.append("_");
			nomeArquivo.append(idRegistroEmissao);

		} else if (!controleSegregacao && 
				SnTipoLoteBean.Sigla.EMISSAOPRINT.getChaveSigla().equals(tipoLote.getSgTipoLote())) {
			// Controle de emissão desligado

			nomeArquivo.append("EMISPRT"); // ALTERAR - PEGAR DO PARAMETRO
			nomeArquivo.append(codigoOperadora);
			nomeArquivo.append(dtVencimentoLote);
			nomeArquivo.append("_");
			nomeArquivo.append(idRegistroEmissao);

		} else if (controleSegregacao && 
				(SnTipoLoteBean.Sigla.SEGUNDA_VIA.getChaveSigla().equals(tipoLote.getSgTipoLote()) || 
						SnTipoLoteBean.Sigla.SEGUNDA_VIA_WEB.getChaveSigla().equals(tipoLote.getSgTipoLote()) || 
						SnTipoLoteBean.Sigla.CPNF.equals(tipoLote.getSgTipoLote()) || 
						SnTipoLoteBean.Sigla.CPNF_WEB.getChaveSigla().equals(tipoLote.getSgTipoLote()))) {

			String nomeSegArquivo = null;
			String nomeParametro = null;
			SnParametroBean snParametroBean = null;
			
			if (SnTipoLoteBean.Sigla.SEGUNDA_VIA.getChaveSigla().equals(tipoLote.getSgTipoLote())) {
				// Segunda via segregado
				nomeParametro = PrintHouseConstants.Parametro.NM_TEMP_ARQ_PRINT_SEG_VIA;
				snParametroBean = obterParametro(cidContrato, nomeParametro);
			} else if (SnTipoLoteBean.Sigla.CPNF.getChaveSigla().equals(tipoLote.getSgTipoLote())) {
				// CPNF segregado
				nomeParametro = PrintHouseConstants.Parametro.NM_TEMP_ARQ_PRINT_REEMISSAO_NF;
				snParametroBean = obterParametro(cidContrato, nomeParametro);
			}
			
			if (snParametroBean == null) {
				//throw new IllegalStateException();
				throw new EmissaoBusinessResourceException(EmissaoResources.NULL_PARAMETER, 
				                                           new Object[] {nomeParametro, cidContrato});
			}
			
			nomeSegArquivo = snParametroBean.getVlrParametroStr();
			nomeArquivo.append(nomeSegArquivo);
			nomeArquivo.append(arquivoPrintHouseDTO.getCurrentArquivo().getPrefixo());
			nomeArquivo.append(codigoOperadora);
			nomeArquivo.append(dtEmissaoLote);
			nomeArquivo.append("_");
			nomeArquivo.append(idRegistroEmissao);

		} else if (!controleSegregacao && 
				(SnTipoLoteBean.Sigla.SEGUNDA_VIA.getChaveSigla().equals(tipoLote.getSgTipoLote()) || 
						SnTipoLoteBean.Sigla.SEGUNDA_VIA_WEB.getChaveSigla().equals(tipoLote.getSgTipoLote()) || 
						SnTipoLoteBean.Sigla.CPNF.equals(tipoLote.getSgTipoLote()) ||
						SnTipoLoteBean.Sigla.CPNF_WEB.getChaveSigla().equals(tipoLote.getSgTipoLote()))) {
			
			String nomeSegArquivo = null;
			String nomeParametro = null;
			SnParametroBean snParametroBean = null;
			
			if (SnTipoLoteBean.Sigla.SEGUNDA_VIA.getChaveSigla().equals(tipoLote.getSgTipoLote()) || 
					SnTipoLoteBean.Sigla.SEGUNDA_VIA_WEB.getChaveSigla().equals(tipoLote.getSgTipoLote())) {
				// Segunda via não segregado
				nomeParametro = PrintHouseConstants.Parametro.NM_TEMP_ARQ_PRINT_SEG_VIA;
				snParametroBean = this.obterParametro(cidContrato, nomeParametro);
			} else if (SnTipoLoteBean.Sigla.CPNF.equals(tipoLote.getSgTipoLote()) || 
					SnTipoLoteBean.Sigla.CPNF_WEB.equals(tipoLote.getSgTipoLote())) {
				// CPNF via não segregado
				nomeParametro = PrintHouseConstants.Parametro.NM_TEMP_ARQ_PRINT_REEMISSAO_NF;
				snParametroBean = this.obterParametro(cidContrato, nomeParametro);
			}
			
			if (snParametroBean == null) {				
				throw new EmissaoBusinessResourceException(EmissaoResources.NULL_PARAMETER, 
				                                           new Object[] {nomeParametro, cidContrato});
			}
			
			nomeSegArquivo = snParametroBean.getVlrParametroStr();
			nomeArquivo.append(nomeSegArquivo);
			nomeArquivo.append(codigoOperadora);
			nomeArquivo.append(dtEmissaoLote);
			nomeArquivo.append("_");
			nomeArquivo.append(idRegistroEmissao);

		} else if (SnTipoLoteBean.Sigla.EMISSAO_PRINT_BOLETO_AVULSO.getChaveSigla().equals(tipoLote.getSgTipoLote())){
			nomeArquivo.append(BoletoAvulsoUtils.geraNomeArquivoBoletoAvulso(arquivoPrintHouseDTO, 
			                                                                 cidade, 
			                                                                 tipoLote, 
			                                                                 sequencia));
		}

		SnParametroBean snParametroBean = obterParametro(cidContrato, 
		                                                 DIRETORIO_DESTINO_OPERADORA_ARQUIVO_PRINT);

		if (snParametroBean.getVlrParametroStr() == null) {
			// Sem diretorio
			snParametroBean = obterParametro(cidContrato, DIR_ARQ_PRINT);
		}
		
		String dir_arq_print = snParametroBean.getVlrParametroStr();

		// Monta set de lotes que serao associados ao controle de arquivo
		Set<SnLoteBean> listLotes = new HashSet<SnLoteBean>();
		for (String strLote : lotes) {
			SnLoteBean snLoteBean = new SnLoteBean();
			snLoteBean.setIdLote(Integer.parseInt(strLote));
			snLoteBean = (SnLoteBean)this.find(snLoteBean);
			listLotes.add(snLoteBean);
		}
		
		SnControleArquivoBean arquivo = new SnControleArquivoBean();	
		arquivo.setDhGeracao(new Date());	
		arquivo.setCidContrato(cidade);
		arquivo.setLotes(listLotes); //associa os lotes ao controle de arquivo
		arquivo.setNrSequencia(sequencia);

		arquivoPrintHouseDTO.setSequecialNomeArquivo(sequencia);
		
		if(arquivoPrintHouseDTO.getSequecialNomeArquivo() != 0){
			 nomeArquivo.append("_"+arquivoPrintHouseDTO.getSequecialNomeArquivo()); 
		}
		
		arquivo = this.registrarArquivoPrintHouseCriado(arquivo, 
		                                                nomeArquivo.toString(), 
		                                                arquivoPrintHouseDTO, 
		                                                cidade, 
		                                                dataVencimentoLote, 
		                                                tipoLote.getSgTipoLote());
		
		
		arquivoPrintHouseDTO.getCurrentArquivo().setSnControleArquivoBean(arquivo);

		File localizacaoArquivoGerado = new File(dir_arq_print + 
		                                         File.separator + 
		                                         nomeArquivo + 
		                                         arquivoPrintHouseDTO.getExtensaoArquivoPrint());		
		
		logger.info(new BasicAttachmentMessage("Cria arquivo [" + localizacaoArquivoGerado.getName() + "], [" 
				+ tipoLote.getSgTipoLote() + "], [" 
				+ (controleSegregacao ? "" : "não") + "] segregado.", AttachmentMessageLevel.INFO));

		 
		GenerateFile gerarArquivo; 
		InputStream inputStreamLeiauteXML = null;
		
		try {
			String layout = EMISSAO_PRINT_BOLETO_AVULSO.getChaveSigla().equals(tipoLote.getSgTipoLote()) ?
								PACOTE_LAYOUT_ARQUIVO_BOLETO_AVULSO :
								PACOTE_LAYOUT_ARQUIVO_GERACAO;	
			
			inputStreamLeiauteXML = ArquivoPrintHouseServiceEJBImpl.class.getResourceAsStream(layout);

			gerarArquivo = new GenerateFile(localizacaoArquivoGerado, inputStreamLeiauteXML);
			gerarArquivo.setLineSeparator(GenerateFile.LINE_SEPARATOR_WINDOWS);
			
		} finally {
			if (inputStreamLeiauteXML != null) {
				try {
					inputStreamLeiauteXML.close();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					inputStreamLeiauteXML = null;
				}
			}
		}
		
		arquivoPrintHouseDTO.getCurrentArquivo().setGerarArquivo(gerarArquivo); 

		this.gravarArquivoPrintHouse(gerarArquivo, this.cabecalhoArquivo(arquivoPrintHouseDTO));
		this.gravarArquivoPrintHouse(gerarArquivo, this.operadoraNet(cidade));
		
		String sgTipoLote = tipoLote.getSgTipoLote();
	    SnSetorizacaoBean.Prefixo prefixoSetor = null;

		if (EMISSAOPRINT.getChaveSigla().equals(sgTipoLote)) {
			prefixoSetor = SnSetorizacaoBean.Prefixo.PRIMEIRA_VIA;
		}
		else if (SEGUNDA_VIA.getChaveSigla().equals(sgTipoLote) || SEGUNDA_VIA_WEB.getChaveSigla().equals(sgTipoLote)) {
			prefixoSetor = SnSetorizacaoBean.Prefixo.SEGUNDA_VIA;
		} 
		else if (CPNF.getChaveSigla().equals(sgTipoLote) || CPNF_WEB.getChaveSigla().equals(sgTipoLote)) {
			prefixoSetor = SnSetorizacaoBean.Prefixo.COPIA_NF;
		}
		else if (EMISSAO_PRINT_BOLETO_AVULSO.getChaveSigla().equals(sgTipoLote)){
			prefixoSetor = SnSetorizacaoBean.Prefixo.BOLETO_AVULSO;
		}
		
		Collection<LinhaDTO> colLinhasMsg = new ArrayList<LinhaDTO>();
		if (EMISSAO_PRINT_BOLETO_AVULSO.getChaveSigla().equals(sgTipoLote)) {
			colLinhasMsg = obterMensagensBoletoAvulso(dataVencimentoLote,
			                                          cidade,
			                                          idCriterio,
			                                          prefixoSetor,
			                                          controleSegregacao,
			                                          null,
			                                          lotes);
		} else {
			colLinhasMsg = this.obterMensagens(dataVencimentoLote,
			                                   cidade,
			                                   idCriterio,
			                                   prefixoSetor,
			                                   controleSegregacao,
			                                   null,
			                                   lotes);
		}
		
		for (Iterator iterLinhas = colLinhasMsg.iterator(); iterLinhas.hasNext();) {
			this.gravarArquivoPrintHouse(gerarArquivo, (LinhaDTO) iterLinhas.next());
		}

		Collection colLinhasBanco = this.obterBancoConveniado(cidade,
		                                                      tipoLote.getSnTipoCobrancaBean().getIdTipoCobranca(), 
		                                                      dataVencimentoLote);
		
		for (Iterator iterLinhas = colLinhasBanco.iterator(); iterLinhas.hasNext();) {
			this.gravarArquivoPrintHouse(gerarArquivo, (LinhaDTO) iterLinhas.next());
		}
		
		return arquivo;
	}

	private Collection<LinhaDTO> obterMensagensBoletoAvulso(Date dataVencimento,
	                                                        SnCidadeOperadoraBean cidade, 
	                                                        String idCriterio,
	                                                        SnSetorizacaoBean.Prefixo siglaSetorizacaoFatura,
	                                                        Boolean controleSegregacao,
	                                                        Long idBoleto,
	                                                        String[] lotes){
		
		Collection<LinhaDTO> colLinhasDTO = new ArrayList<LinhaDTO>();

		MensagemDTO mensagem = new MensagemDTO();
		List<String> lotesList = new ArrayList<String>();

		for (int i = 0; i < lotes.length; i++) {
			lotesList.add(lotes[i]);
		}
		mensagem.setDataVencimentoTitulo(dataVencimento);
		Collection<MensagemDTO> colMensagemPrint = null;

		mensagem.setCidContrato(cidade.getCidContrato());
		mensagem.setSiglaSetorizacaoFatura(siglaSetorizacaoFatura.getChave() + "%");
		mensagem.setIdLotes(lotesList);
		mensagem.setIdBoleto(idBoleto);
		colMensagemPrint = super.getDadosBeanByQuery("listarMensagemAssociadaBoletoAvulsoPrint",
		                                             mensagem,
		                                             false,
		                                             MensagemDTO.class);

		if (colMensagemPrint != null) {
			try {
				// Busca MSG Quitação de Debitos Anual
				for (MensagemDTO mensagemAssociada : colMensagemPrint) {
					if (mensagemAssociada.getCcCodigoFormatacao() == null) {
						throw new EmissaoBusinessResourceException(EmissaoResources.ERRO_MSG_ASSOCIADA_COD_FORMATACAO_NULO);
					}
					colLinhasDTO.add(llk.criarLinhaItenMensagem(cidade, mensagemAssociada));
				}
			} catch (Exception e) {
				logger.info(new BasicAttachmentMessage("ERRO AO COPIAR PROPERTIES mensagemAssociada",
				                                       AttachmentMessageLevel.INFO));
				e.printStackTrace();
			}
		}

		return colLinhasDTO;
	}

	private String getPrefixoTipoCobranca(Integer idTipoCobranca) {
		
		//tipo de cobranca
		String prefixoTipoCob = "";
		if (idTipoCobranca.compareTo(SnTipoCobrancaBean.BOLETO) == 0) {
			prefixoTipoCob = "E"; 
		}
		else if (idTipoCobranca.compareTo(SnTipoCobrancaBean.DEBITO_EM_CONTA) == 0) {
			prefixoTipoCob = "D"; 
		}
		else if (idTipoCobranca.compareTo(SnTipoCobrancaBean.CARTAO_DE_CREDITO) == 0) {
			prefixoTipoCob = "C"; 
		}
		
		return prefixoTipoCob;
		
	}
	
	/**
	 * Método responsável por gravar as faturas no arquivo de envio a Print
	 * House.
	 * 
	 * @author Harrisson Ferreira Gomes - implementação Marcio Bellini -
	 *         refactor Robin Michael Gray
	 * @version 1.0 - criação da interface - 06/04/2006
	 * @number RF015_RF021
	 * @return
	 * @param Integer
	 *            idLote, Long idBoleto, ArquivoPrintHouseDTO
	 *            arquivoPrintHouseDTO
	 * @semantics <br> 
	 *      
     * @ejb.interface-method view-type = "both"
     * @ejb.transaction type="Required"
	 *	
	 *  
	 */
    public DadosFatura gravarFaturaArquivoPrintHouse(SnLoteBean lote, 
	                                                 FaturaNetDTO fatura,
	                                                 ArquivoPrintHouseDTO arquivoPrintHouseDTO) {

		// Flag de controle de rollback se houver erro no boleto
		AtomicBoolean efetuarRollback = new AtomicBoolean(false);
		
		// DadosFatura que sera utilizado para gerar os registros em lote na pp_vlr_parceiro_fatura
		// Somente retorna valor quando for Emissao Print
		DadosFatura dadosFatura = null;
		
		try {
			Long idBoleto = fatura.getIdBoleto();
			
			// Guarda o id do boleto atual para poder logar se houver erro
			this.setIdBoletoAtual(idBoleto);
			
			logger.info(" ID BOLETO CORRENTE --------------------------------------->"+fatura.getIdBoleto());

			if (EMISSAOPRINT.getChaveSigla().equals(arquivoPrintHouseDTO.getSiglaTipoLote())){
				
				dadosFatura = geraFaturaEmissaoPrint(lote, fatura, arquivoPrintHouseDTO, efetuarRollback);
				
			} else if (SEGUNDA_VIA.getChaveSigla().equals(arquivoPrintHouseDTO.getSiglaTipoLote()) || 
					   SEGUNDA_VIA_WEB.equals(arquivoPrintHouseDTO.getSiglaTipoLote())) {
				
				geraFaturaSegundaViaPrint(lote, fatura, arquivoPrintHouseDTO, efetuarRollback);

			} else if (CPNF.getChaveSigla().equals(arquivoPrintHouseDTO.getSiglaTipoLote()) || 
					   CPNF_WEB.getChaveSigla().equals(arquivoPrintHouseDTO.getSiglaTipoLote())) {

				geraFaturaCopiaNFPrint(lote, fatura, arquivoPrintHouseDTO, efetuarRollback);
			} else if (EMISSAO_PRINT_BOLETO_AVULSO.getChaveSigla().equals(arquivoPrintHouseDTO.getSiglaTipoLote())){
				dadosFatura = geraFaturaEmissaoPrintBoletoAvulso(lote, fatura, arquivoPrintHouseDTO, efetuarRollback);
			}
			
		} catch(Exception ex) {
			
			if (!efetuarRollback.get()) {

				logger.info(new BasicAttachmentMessage("Lote [" + lote.getIdLote()
						+ "] - Boleto [" + fatura.getIdBoleto()
						+ "] com problema identificado antes de incluir linhas no arquivo."
						, AttachmentMessageLevel.INFO));

				logger.error(new BasicAttachmentMessage("Erro: " + this.getStackTrace(ex), AttachmentMessageLevel.ERROR), ex);

				throw new EmissaoBusinessResourceException(ex, ex.getMessage());
				
			} else {
				
				logger.error(new BasicAttachmentMessage("Lote [" + lote.getIdLote()
						+ "] - Boleto [" + fatura.getIdBoleto()
						+ "] com problema identificado apos incluir linhas no arquivo. Processo devera ser abortado. Motivo [" 
						+ ex.getMessage() + "]"
						, AttachmentMessageLevel.ERROR));
				
				logger.error(new BasicAttachmentMessage(this.getStackTrace(ex), AttachmentMessageLevel.ERROR));

				 // Grava sn_log_erro_abort em transacao separada (efetua commit)
				 LogErroAbortService leaService = getService(LogErroAbortService.class);
				 leaService.inserirLogErrorAbort(fatura.getCidContrato()
						 , fatura.getNumContrato()
						 , null
						 , null
						 , "ERRO NO BOLETO: " + fatura.getIdBoleto() + 
						 " DO LOTE: " + lote.getIdLote() + 
						 ". ABORTANDO PROCESSAMENTO. ERRO: " + this.getStackTrace(ex).substring(0, 3800)
						 , "EMISSAO PRINTHOUSE"
						 , null);

				throw new RuntimeException(ex);
				
			}
			
		}
		
//		return faturaDTORetonar;
		return dadosFatura;
		
	}

	@SuppressWarnings("rawtypes")
    private void geraFaturaCopiaNFPrint(SnLoteBean lote,
                                        FaturaNetDTO fatura,
                                        ArquivoPrintHouseDTO arquivoPrintHouseDTO,
                                        AtomicBoolean efetuarRollback) throws Exception{
	    
		Long idBoleto = fatura.getIdBoleto();
		Date dataVencimento = fatura.getDataVencimento();
		
		//WMC rever
	    DynamicBean param = new DynamicBean();
	    param.set("idBoleto",fatura.getIdBoleto() );			
	    
	    List<DadosReemissaoDTO> listaNotas = getDadosBeanByQuery("selecionarNotaFiscalFatura", 
	                                                             param, 
	                                                             false, 
	                                                             DadosReemissaoDTO.class);
	    		
	    
	    for (Iterator iterListaNotas = listaNotas.iterator(); iterListaNotas.hasNext();) {
	    	DadosReemissaoDTO item = (DadosReemissaoDTO) iterListaNotas.next();
	    	FaturaDTO faturaDTO = this.gerarCopiaDeNotaFiscal(item.getIdCobranacaNotaFiscal(), idBoleto, "");
	    	
	    	faturaDTO.setCidContrato(fatura.getCidContrato());
	    	faturaDTO.setNumContrato(fatura.getNumContrato());
	    	faturaDTO.setUltimasOcorrencias(fatura.getUltimasOcorrencias());
	    	
	    	// Liga o flag para efetuar rollback caso haja erro a partir deste ponto
	    	efetuarRollback.set(true);

	    	// Cria registros por cobranca(fatura) na pp_vlr_parceiro_fatura
	    	this.inserirPpVlrParceiro(arquivoPrintHouseDTO, fatura, faturaDTO, lote);
	    				
	    	// Atualizar status impressão
	    	this.finalizarFatura(faturaDTO, lote, fatura, dataVencimento,
	    			arquivoPrintHouseDTO);
	    	
	    	atualizarBoteto(idBoleto);
	    }
    }

	private void geraFaturaSegundaViaPrint(SnLoteBean lote,
                                           FaturaNetDTO fatura,
                                           ArquivoPrintHouseDTO arquivoPrintHouseDTO,
                                           AtomicBoolean efetuarRollback){
		
		Long idBoleto = fatura.getIdBoleto();
		Date dataVencimento = fatura.getDataVencimento();
		
	    FaturaDTO faturaDTO = gerarSegundaViaDemonstrativoFinanceiroFichaArrecadacao(idBoleto, 
	                                                                                 lote.getIdLote(),
	                                                                                 "ARQUIVO", 
	                                                                                 TPPRIMEIRAVIA);
	    faturaDTO.setCidContrato(fatura.getCidContrato());
	    faturaDTO.setNumContrato(fatura.getNumContrato());
	    faturaDTO.setUltimasOcorrencias(fatura.getUltimasOcorrencias());

	    // Liga o flag para efetuar rollback caso haja erro a partir deste ponto
	    efetuarRollback.set(true);

	    // Cria registros por cobranca(fatura) na pp_vlr_parceiro_fatura
	    inserirPpVlrParceiro(arquivoPrintHouseDTO, fatura, faturaDTO, lote);
	    				
	    finalizarFatura(faturaDTO, lote, fatura, dataVencimento, arquivoPrintHouseDTO);
	    
	    atualizarBoteto(idBoleto);
    }
	
	@SuppressWarnings("rawtypes")
    private DadosFatura geraFaturaEmissaoPrint(SnLoteBean lote, 
                                               FaturaNetDTO fatura,
                                               ArquivoPrintHouseDTO dto, 
                                               AtomicBoolean efetuarRollback){
		
		DadosFatura dadosFatura = null;
		
		Long idBoleto = fatura.getIdBoleto();
		Date dataVencimento = fatura.getDataVencimento();

		FaturaDTO faturaDTO = gerarFaturaPrintHouse(fatura);
		
		// Liga o flag para efetuar rollback caso haja erro a partir deste ponto
		efetuarRollback.set(true);

		// Monta os maps das cobrancas para inserir na pp_vlr_parceiro_fatura no final do processo
		Map<Long, Object[]> mapCobrancaNet = new HashMap<Long, Object[]>();
		
		// Obtem cobrancas net
		List<DadosGeraisPrimeiraViaPrintDTO> dadosGeraisNF = fatura.getDadosGeraisNF();
		
		for (DadosGeraisPrimeiraViaPrintDTO dadosGeraisPrimeiraViaPrintDTO : dadosGeraisNF) {
			mapCobrancaNet.put(dadosGeraisPrimeiraViaPrintDTO.getIdCobranca(), 
			                   new Object[] { dadosGeraisPrimeiraViaPrintDTO.getDataVencimentoBoleto(), 
				                              dadosGeraisPrimeiraViaPrintDTO.getValorCobranca() });
		}
		
		Map<Long, Object[]> mapCobrancaParceiro = new HashMap<Long, Object[]>();

		// Obtem cobrancas do parceiro que possuem nota fiscal
		Map<Long, List<DadosCobrancaParceiroDTO>> mapNotaFiscaisParceiro = fatura.getDadosNotaFiscalParceiro();
		
		if (mapNotaFiscaisParceiro != null) {
			Iterator it = mapNotaFiscaisParceiro.keySet().iterator();
			
			while (it.hasNext()) {
				
				Long idParceiro = (Long)it.next();
				List<DadosCobrancaParceiroDTO> lstNotaFiscaisParceiro = mapNotaFiscaisParceiro.get(idParceiro);
				
				for (DadosCobrancaParceiroDTO dadosCobrancaParceiroDTO : lstNotaFiscaisParceiro) {
					
					Object[] dados = mapCobrancaParceiro.get(dadosCobrancaParceiroDTO.getIdCobrancaParceiro());
					if (dados == null) {
						mapCobrancaParceiro.put(dadosCobrancaParceiroDTO.getIdCobrancaParceiro(), 
						                        new Object[] { lote.getDtVcto(), 
							                                   dadosCobrancaParceiroDTO.getValorCobranca(), 
							                                   dadosCobrancaParceiroDTO.getIdParceiro() });
					}
				}
			}
		}

		// Obtem cobrancas do parceiro que nao possuem nota fiscal
		List<DadosCobrancaParceiroDTO> lstDadosCobrancaParceiroSemNF = fatura.getDadosCobrancaParceiroSemNF();
		
		if (lstDadosCobrancaParceiroSemNF != null) {
			for (DadosCobrancaParceiroDTO dadosCobrancaParceiroDTO : lstDadosCobrancaParceiroSemNF) {
				mapCobrancaParceiro.put(dadosCobrancaParceiroDTO.getIdCobrancaParceiro(), 
				                        new Object[] { lote.getDtVcto(), 
					                                   dadosCobrancaParceiroDTO.getValorCobranca(), 
					                                   dadosCobrancaParceiroDTO.getIdParceiro()
				});
			}
		}
		
		faturaDTO.setCidContrato(fatura.getCidContrato());
		faturaDTO.setNumContrato(fatura.getNumContrato());
		faturaDTO.setUltimasOcorrencias(fatura.getUltimasOcorrencias());
		faturaDTO.setIdBoleto(idBoleto);
		faturaDTO.setIdControleArquivo(dto.getCurrentArquivo().getSnControleArquivoBean().getIdControleArquivo());
		faturaDTO.setSgTipoLote(lote.getSnTipoLoteBean().getSgTipoLote());
		
		// Se a fatura for somente do parceiro, e necessario verificar em qual lista retornou, sendo que
		// a lista padrao e a de parceiro
		if (this.getTipoFatura(fatura).equals("P")) {
			if (mapCobrancaParceiro == null || mapCobrancaParceiro.size() == 0 ) {
				faturaDTO.setMapCobrancaParceiro(mapCobrancaNet); // dados da fatura do parceiro e não da NET
			} else {
				faturaDTO.setMapCobrancaParceiro(mapCobrancaParceiro); // dados da fatura do parceiro e não da NET
			}
			
			faturaDTO.setMapCobrancaNet(null); // null
		} else {
			faturaDTO.setMapCobrancaNet(mapCobrancaNet);
			faturaDTO.setMapCobrancaParceiro(mapCobrancaParceiro);
		}
		
		finalizarFatura(faturaDTO, lote, fatura, dataVencimento, dto);

		DadosGeraisPrimeiraViaPrintDTO dadosDTO = (DadosGeraisPrimeiraViaPrintDTO) fatura.getDadosGeraisNF().get(0);
		
		dadosFatura = preencheDadosFatura(faturaDTO);
		
		//NSMSP_161258_NI_001\JAVA
		dadosFatura.setMensagemClaroClubeDTO(dadosDTO.getMensagemClaroClubeDTO());
		dadosFatura.setFormaEnvioFatura(dadosDTO.getFormaEnvioFatura());
		
		return dadosFatura;
	}

	private DadosFatura geraFaturaEmissaoPrintBoletoAvulso(SnLoteBean lote,
	                                                       FaturaNetDTO fatura,
	                                                       ArquivoPrintHouseDTO dto,
	                                                       AtomicBoolean efetuarRollback){
		
		DadosFatura dadosFatura = null;
		
		Long idBoleto = fatura.getIdBoleto();
		Date dataVencimento = fatura.getDataVencimento();
		
		FaturaDTO faturaDTO = gerarFaturaPrintHouseBoletoAvulso(fatura);
		
		// Liga o flag para efetuar rollback caso haja erro a partir deste ponto
		efetuarRollback.set(true);
		
		// Monta os maps das cobrancas para inserir na pp_vlr_parceiro_fatura no final do processo
		Map<Long, Object[]> mapCobrancaNet = new HashMap<Long, Object[]>();
		
		// Obtem cobrancas net
		List<DadosGeraisPrimeiraViaPrintDTO> dadosGeraisNF = fatura.getDadosGeraisNF();
		
		for (DadosGeraisPrimeiraViaPrintDTO dadosGeraisPrimeiraViaPrintDTO : dadosGeraisNF) {
			mapCobrancaNet.put(dadosGeraisPrimeiraViaPrintDTO.getIdCobranca(), 
			                   new Object[] { dadosGeraisPrimeiraViaPrintDTO.getDataVencimentoBoleto(), 
				                              dadosGeraisPrimeiraViaPrintDTO.getValorCobranca() });
		}
		
		faturaDTO.setCidContrato(fatura.getCidContrato());
		faturaDTO.setNumContrato(fatura.getNumContrato());
		faturaDTO.setUltimasOcorrencias(fatura.getUltimasOcorrencias());
		faturaDTO.setIdBoleto(idBoleto);
		faturaDTO.setIdControleArquivo(dto.getCurrentArquivo().getSnControleArquivoBean().getIdControleArquivo());
		faturaDTO.setSgTipoLote(lote.getSnTipoLoteBean().getSgTipoLote());
		faturaDTO.setMapCobrancaParceiro(null); 
		faturaDTO.setMapCobrancaNet(mapCobrancaNet);
		
		finalizarFatura(faturaDTO, lote, fatura, dataVencimento, dto);
		
		DadosGeraisPrimeiraViaPrintDTO dadosDTO = (DadosGeraisPrimeiraViaPrintDTO) fatura.getDadosGeraisNF().get(0);
		
		dadosFatura = preencheDadosFatura(faturaDTO);
		
		//NSMSP_161258_NI_001\JAVA
		dadosFatura.setMensagemClaroClubeDTO(dadosDTO.getMensagemClaroClubeDTO());		
		dadosFatura.setFormaEnvioFatura(dadosDTO.getFormaEnvioFatura());
		
		return dadosFatura;
	}

	private DadosFatura preencheDadosFatura(FaturaDTO faturaDTO) {
		
		DadosFatura dadosFatura = new DadosFatura();
		dadosFatura.setCidContrato(faturaDTO.getCidContrato());
		dadosFatura.setIdBoleto(faturaDTO.getIdBoleto());
		dadosFatura.setIdControleArquivo(faturaDTO.getIdControleArquivo());
		dadosFatura.setNumContrato(faturaDTO.getNumContrato());
		dadosFatura.setSgTipoLote(faturaDTO.getSgTipoLote());
		dadosFatura.setMapCobrancaNet(faturaDTO.getMapCobrancaNet());
		dadosFatura.setUltimasOcorrencias(faturaDTO.getUltimasOcorrencias());
		dadosFatura.setMapCobrancaParceiro(faturaDTO.getMapCobrancaParceiro());
		dadosFatura.setNumPaginaNet(faturaDTO.getNumPaginaNet());
		dadosFatura.setNumPaginaParceiro(faturaDTO.getNumPaginaParceiro());
		
		Collection<LinhaDTO> colLinhaDTO = faturaDTO.getListaLinha();
		Integer[] tipo = new Integer[colLinhaDTO.size()];
		
		int i = 0;
		for (LinhaDTO linhaDTO : colLinhaDTO) {
			tipo[i] = linhaDTO.getTipo();
			i++;
		}
		
		dadosFatura.setTipo(tipo);
		
		return dadosFatura;
		
	}

/* TODO apagar
	private Integer[] obterPaginasPorParceiro(Integer[] lstTipoLinha, boolean isSegVia) {

		int numPaginaNet = 0;
		int numPaginaParceiro = 0;
		boolean somar = false;
		
		//Marcelo Torres
		//Codigo duplicado momentaneamente... deve ser refeito
		for (int i = 0; i < lstTipoLinha.length; i++) {
			
			int tipo = lstTipoLinha[i];
			
			somar = somar || (tipo == 11);
			
			if (isSegVia) {
				
				if (somar && (tipo == 7)) {
					numPaginaNet++;
					somar = false;
				}
				
			} else {
				
				if (somar && (tipo == 51 || tipo == 60)) {
					numPaginaParceiro++;
					somar = false;
				}
				else if (somar && (tipo == 06 || tipo == 62)) {
					numPaginaNet++;
					somar = false;
				}
				
			}
			
		}
		
		return new Integer[] {Integer.valueOf(numPaginaNet), Integer.valueOf(numPaginaParceiro)};
		
	}
	*/
	
	private Integer[] obterPaginasPorParceiro(FaturaDTO faturaDTO, boolean isSegVia) {
		return new Integer[] {faturaDTO.getNumPaginaNet(), faturaDTO.getNumPaginaParceiro()};
	}
	
	private Integer[] obterPaginasPorParceiro(DadosFatura dadosFatura, boolean isSegVia) {
		return new Integer[] {dadosFatura.getNumPaginaNet(), dadosFatura.getNumPaginaParceiro()};
	}
	
	private void inserirPpVlrParceiro(ArquivoPrintHouseDTO arquivoPrintHouseDTO
			, FaturaNetDTO bol, FaturaDTO faturaDTO, SnLoteBean lote) {
		
		SnRelCobrancaBoletoBean snRelCobrancaBoletoBean = new SnRelCobrancaBoletoBean();
		snRelCobrancaBoletoBean.setIdBoleto(bol.getIdBoleto());
		
		DetachedCriteria criteria = DetachedCriteria.forClass(SnRelCobrancaBoletoBean.class);
		criteria.add(Restrictions.eq("idBoleto", bol.getIdBoleto()));
		List<SnRelCobrancaBoletoBean> lstRelCobBoleto = (List<SnRelCobrancaBoletoBean>)getCurrentDAO().select(criteria);
		
		String sgTipoLote = lote.getSnTipoLoteBean().getSgTipoLote();
		String tpOrigemEmissao = "PrintHouse";
		String ccOrigemEmissao = null;
		
		boolean isSegVia = false;
		if (arquivoPrintHouseDTO.getSiglaTipoLote().equals(SnTipoLoteBean.Sigla.EMISSAOPRINT.getChaveSigla())) {
			ccOrigemEmissao = "PP";
		}
		if (arquivoPrintHouseDTO.getSiglaTipoLote().equals(SnTipoLoteBean.Sigla.SEGUNDA_VIA.getChaveSigla())
				|| arquivoPrintHouseDTO.getSiglaTipoLote().equals(SnTipoLoteBean.Sigla.SEGUNDA_VIA_WEB.getChaveSigla())
				) {
			ccOrigemEmissao = "SP";
			isSegVia = true;
		}
		if (arquivoPrintHouseDTO.getSiglaTipoLote().equals(SnTipoLoteBean.Sigla.CPNF.getChaveSigla())
				|| arquivoPrintHouseDTO.getSiglaTipoLote().equals(SnTipoLoteBean.Sigla.CPNF_WEB.getChaveSigla())
				) {
			ccOrigemEmissao = "NP";
		}

		Integer[] paginas = this.obterPaginasPorParceiro(faturaDTO, isSegVia);
		int numPaginasNet = paginas[0];
		int numPaginasParceiro = paginas[1];
		
		DetachedCriteria criteriaOrigemEmissao = DetachedCriteria.forClass(SnOrigemEmissaoBean.class);
		criteriaOrigemEmissao.add(Restrictions.eq("ccOrigemEmissao", ccOrigemEmissao));
		criteriaOrigemEmissao.add(Restrictions.eq("tpOrigemEmissao", tpOrigemEmissao));
		List<SnOrigemEmissaoBean> lstOrigemEmissao = (List<SnOrigemEmissaoBean>)getCurrentDAO().select(criteriaOrigemEmissao);
		
		SnOrigemEmissaoBean origemEmissaoBean = null;
		if (lstOrigemEmissao != null && lstOrigemEmissao.size() > 0) {
			origemEmissaoBean = lstOrigemEmissao.get(0);
		}

		boolean hasNet = false;
		
		for (SnRelCobrancaBoletoBean relCobBoleto : lstRelCobBoleto) {
			
			Integer idCobranca = relCobBoleto.getIdCobranca();
			
			if(idCobranca != null && idCobranca.intValue() != 0) {
				hasNet = true;
				break;
			}
			
		}

		// Para segunda via, deve-se atribuir a quantidade de pagina para o parceiro
		// se o tipo de fatura for parceiro, senao, o numero de paginas fica para a net mesmo.
		if (isSegVia) {
			// Não co-boletado
			String tipoFatura = this.getTipoFatura(bol);
			if (tipoFatura.equals("P")) {
				numPaginasParceiro = numPaginasNet;
				numPaginasNet = 0;
			}
		} else {
			// Se nao tem cobranca net, soma o numero de paginas net no numero de paginas do
			// parceiro
			numPaginasParceiro += hasNet ? 0 : numPaginasNet;
		}

		for (SnRelCobrancaBoletoBean relCobBoleto : lstRelCobBoleto) {
			
			Integer idCobranca = relCobBoleto.getIdCobranca();
			Integer idCobrancaParceiro = relCobBoleto.getIdCobrancaParceiro();
			Integer idParceiro = relCobBoleto.getIdParceiro();
			Integer idRelCobrancaBoleto = relCobBoleto.getIdRelCobrancaBoleto();
			
			if(idCobranca != null && idCobranca.intValue() != 0) {
				SnCobrancaBean cobranca = new SnCobrancaBean();
				cobranca.setIdCobranca(idCobranca);
				
				cobranca = (SnCobrancaBean)this.find(cobranca);
				
				this.inserirPpVlrParceiro(arquivoPrintHouseDTO, bol, 
						cobranca.getVlrTotal(), cobranca.getDtVencto(),
						idRelCobrancaBoleto, numPaginasNet, origemEmissaoBean);
				
				numPaginasNet = 0; // Se existir mais cobrancas Net, zera o contador
				
			}
			else {
				SnParceiroBean snParceiroBean = new SnParceiroBean();
				snParceiroBean.setIdParceiro(idParceiro);
				
				SnCobrancaParceiroBean cobrancaParceiro = new SnCobrancaParceiroBean();
				cobrancaParceiro.setIdCobranca(idCobrancaParceiro);
				cobrancaParceiro.setSnParceiroBean(snParceiroBean);
				
				cobrancaParceiro = (SnCobrancaParceiroBean)this.find(cobrancaParceiro);

				this.inserirPpVlrParceiro(arquivoPrintHouseDTO, bol,
						cobrancaParceiro.getVlrTotal(), cobrancaParceiro.getDtVencto(), 
						idRelCobrancaBoleto, numPaginasParceiro, origemEmissaoBean);
				
				numPaginasParceiro = 0; // Se existir mais cobrancas Parceiro, zera o contador
				
			}
			
		}
		
	}
		
	private void inserirPpVlrParceiro(
			ArquivoPrintHouseDTO arquivoPrintHouseDTO, FaturaNetDTO bol, Double vlrTotalCobranca,
			Date dtVenctoCobranca, Integer idRelCobrancaBoleto, Integer quantidadePaginas, SnOrigemEmissaoBean origemEmissaoBean) {
			
		SnPpVlrParceiroFaturaKey compositeKey = new SnPpVlrParceiroFaturaKey();
		compositeKey.setIdControleArquivo(arquivoPrintHouseDTO.getCurrentArquivo().getSnControleArquivoBean().getIdControleArquivo());
		compositeKey.setIdRelCobrancaBoleto(idRelCobrancaBoleto);
		
		PpVlrParceiroFaturaBean valorFatura = new PpVlrParceiroFaturaBean();
		valorFatura.setCompositeKey(compositeKey);
		//valorFatura.setCidContrato(arquivoPrintHouseDTO.getCurrentArquivo().getSnCidadeOperadoraBean().getCidContrato());
		valorFatura.setCidContrato(bol.getCidContrato());
		valorFatura.setNumContrato(bol.getNumContrato());
		valorFatura.setDtAtual(new Date());
		valorFatura.setDtVencimento(dtVenctoCobranca);
		valorFatura.setIdOrigemEmissao(origemEmissaoBean.getIdOrigemEmissao());
		valorFatura.setVlrCobranca(vlrTotalCobranca);
		valorFatura.setQuantidadePaginas(quantidadePaginas);
		
		try {
			
			// Tenta inserir na pp_vlr_parceiro_fatura
			insert(valorFatura, this.getUserSession().getCurrentDbService());
			
		} catch (Exception ex) {
			
			logger.warn(new BasicAttachmentMessage(this.getStackTrace(ex), AttachmentMessageLevel.WARNING), ex);

			// Se der erro, provavelmente é pq o boleto está duplicado, entao no lugar de inserir
			// faz uma atualização da quantidade de paginas
			valorFatura = (PpVlrParceiroFaturaBean)find(valorFatura);
			valorFatura.setQuantidadePaginas(valorFatura.getQuantidadePaginas() + quantidadePaginas);
			update(valorFatura, this.getUserSession().getCurrentDbService());
			
			logger.info(new BasicAttachmentMessage("Faz update na pp_vlr_parceiro_fatura no lugar do insert devido ao boleto duplicado em lotes diferentes. idRelCobrancaBoleto = " + idRelCobrancaBoleto, AttachmentMessageLevel.INFO));

		}
		
	}
	
	/** 
	 * Método responsável por gravar as faturas no arquivo de envio a Print
	 * House.
	 * 
	 * @author Harrisson Ferreira Gomes - implementação Marcio Bellini -
	 *         refactor Robin Michael Gray
	 * @version 1.0 - criação da interface - 28/03/2006
	 * @number RF015_RF021
	 * @return void
	 * @param GerarArquivo
	 *            gerarArquivo, LinhaDTO linha
	 * @semantics <br>
	 *            acionar o GerarArquivo.setLine( linha.tipo ); acionar o
	 *            GerarArquivo.addLine( linha.valor );
	 * @ejb.interface-method view-type = "both"
     * @ejb.transaction type="Required"
	 */
	public  void gravarArquivoPrintHouse(GenerateFile gerarArquivo,
			LinhaDTO linha) {	
		
		if(linha.getTipo() == 5 || linha.getTipo() == 98){
			
			existsFaturaNet = false;
			existsMsgComplCampoImpor = false;
			existsFaturaEbt = false;
		}
		
		if(linha.getTipo() == 15){
			
			existsFaturaNet = true;
		}
		
		if(existsFaturaNet && linha.getTipo() == 23){
		
			existsMsgComplCampoImpor = true;
		}
		
		if(linha.getTipo() == 33){
			
			existsFaturaEbt = true;
		}
		
		if(linha.getTipo() == 23 && existsFaturaEbt && existsMsgComplCampoImpor){
			
			this.setTipoAtual(linha.getTipo());
			this.setValorAtual(linha.getValor().toString());
		}
		else{
		
			this.setTipoAtual(linha.getTipo());
			this.setValorAtual(linha.getValor().toString());		
			gerarArquivo.setLine(String.valueOf(linha.getTipo()));
			gerarArquivo.addLine(linha.getValor());
		}
	}

	/**
	 * @number RF015_RF021
	 * @param lote
	 * @param idBoleto
	 * @param arquivoPrintHouseDTO
	 * @param gerarArquivo
	 * @throws JNetException
	 * @ejb.interface-method view-type = "both"
     * @ejb.transaction type="Required"
     * 
	 */
	public  void finalizarFatura(FaturaDTO faturaDTO, SnLoteBean lote,
			FaturaNetDTO boleto, Date dataVencimento,
			ArquivoPrintHouseDTO arquivoPrintHouseDTO) 
	{
		for (int i = 0; i < faturaDTO.getQuantidadeLinhas().intValue(); i++) {
			this.gravarArquivoPrintHouse(arquivoPrintHouseDTO
					.getCurrentArquivo().getGerarArquivo(), faturaDTO
					.get(new Integer(i)));
		}
		// MC retirado para testar performace
		//SnBoletoBean bol = new SnBoletoBean();
		//bol.setIdBoleto(boleto.getIdBoleto());
		//bol = (SnBoletoBean) super.find(bol);
		//bol.setStatusImpressao("S");
		//this.update(bol, getUserSession().getCurrentDbService());
		// Atualizar status impressão

		//registrarMovimentacao(boleto);

		int totalFaturasEmitidas = arquivoPrintHouseDTO.getCurrentArquivo()
			.getQuantidadeFaturasEmitidas();
		arquivoPrintHouseDTO.getCurrentArquivo().setQuantidadeFaturasEmitidas(
		new Integer(++totalFaturasEmitidas));

		double valorTotalFatura = (arquivoPrintHouseDTO.getCurrentArquivo()
				.getValorTotalFatura() == null ? 0 : arquivoPrintHouseDTO
						.getCurrentArquivo().getValorTotalFatura().doubleValue());

		double valorCobrado = faturaDTO.getValorCobrado();

		if( valorCobrado < 0 ){
			valorCobrado = 0;
		}

		valorTotalFatura += valorCobrado;

		arquivoPrintHouseDTO.getCurrentArquivo().setValorTotalFatura(
				valorTotalFatura);


	}
	/**
	 * Método responsável por registrar na entidade de controle arquivo os dados
	 * do arquivo que sera gravado com as faturas de envio a Print House.
	 * 
	 * @author Harrisson Ferreira Gomes/Everton Ken Tomita - implementação
	 *         Marcio Bellini - refactor Robin Michael Gray
	 * @version 1.0 - criação da interface - 28/03/2006
	 * @number RF015_RF021
	 * @return Arquivo
	 * @param Arquivo
	 *            arquivo
	 * 
	 * @semantics <br>
	 * 
	 * @ejb.interface-method view-type = "both"
     * @ejb.transaction type="Required"
     * 
	 */
	public SnControleArquivoBean registrarArquivoPrintHouseCriado (
			SnControleArquivoBean arquivo
			, String nomeInicial
			, ArquivoPrintHouseDTO arquivoPrintDTO
			, SnCidadeOperadoraBean cidade
			, Date dataVencimento
			, String siglaTipoLote
			) throws EmissaoBusinessResourceException {

		SnStatusArquivoBean statusArquivo = this.getStatusArquivo(SnStatusArquivoBean.Sigla.EM_PROCESSAMENTO); 
		arquivo.setStatusArquivo(statusArquivo); 
		String extensaoArquivo = arquivoPrintDTO.getExtensaoArquivoPrint();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		
		if (!extensaoArquivo.startsWith(".")) {
			extensaoArquivo = "." + extensaoArquivo;
		}

		String fileName = null;
		Integer lastSequencia = arquivoPrintDTO.getSequecialNomeArquivo();
		if (lastSequencia != null && lastSequencia.intValue() > 0) {
			arquivo.setNrSequencia(lastSequencia);
			fileName = nomeInicial + "_" + lastSequencia + extensaoArquivo;
		} else {
			fileName = nomeInicial + extensaoArquivo;
		}

		if (EMISSAO_PRINT_BOLETO_AVULSO.getChaveSigla().equals(siglaTipoLote)){
			arquivo.setNomeArquivo(fileName);
		}
		
		Collection arquivos = search("lstArquivoByName", arquivo);

		if (arquivos != null && arquivos.size() > 1) {			 
			throw new EmissaoBusinessResourceException(EmissaoResources.ERRO_ARQUIVO_EXISTENTE); 
		} else if (arquivos != null && arquivos.size() == 1) {
			SnControleArquivoBean arquivoSalvado = (SnControleArquivoBean) arquivos
					.iterator().next();
			if (arquivoSalvado.getStatusArquivo() != null
					&& arquivoSalvado
							.getStatusArquivo()
							.getSiglaStatus() 
							.equals(getStatusArquivo(SnStatusArquivoBean.Sigla.EM_PROCESSAMENTO).getSiglaStatus()

							)) {

				arquivo.setNrSequencia(arquivoSalvado.getNrSequencia());				

				return arquivo;
			} else {
				if (lastSequencia == null) {
					lastSequencia = new Integer(1);
				} else {
					lastSequencia = new Integer(lastSequencia.intValue() + 1);
				}
				arquivo.setNrSequencia(lastSequencia);
				arquivo.setNomeArquivo(nomeInicial + "_" + lastSequencia
						+ extensaoArquivo);
				
				return this.registrarArquivoPrintHouseCriado(arquivo
						, nomeInicial
						, arquivoPrintDTO
						, cidade
						, dataVencimento
						, siglaTipoLote
						);
				
			}

		} else {
						
			
			arquivo.setNomeArquivo(fileName);
							
			
			arquivo.setDhGeracao(new Date());
			arquivo.setDhProcessamento(new Date());
			arquivo.setQtdTotalRegistros(new Integer(arquivoPrintDTO.getQuantidadeFaturaPorArquivoPrint()));			
			arquivo.setCidContrato(cidade);
			arquivo.setUsuarioExec(getUserSession().getUserId());
			arquivo.setUsuarioProcessamento(getUserSession().getUserId());
			arquivo.setIdRegistroEmissao(arquivoPrintDTO.getRegistroEmissao().getIdRegistroEmissao());
			
			/*
			String dtVendimento = null;
			
			if ( dataVencimento != null ) {   
				dtVendimento = dateFormat.format(dataVencimento);
			}
			SnTipoControleArquivoBean tipoControle  = new SnTipoControleArquivoBean();
			 
			 if (dtVendimento.substring(6,8).equals("05")){
				 tipoControle.setSigla(PrintHouseConstants.Parametro.SIGLA_TIPO_CONTROLE_ARQUIVO_PRINT_5);
			 }else if (dtVendimento.substring(6,8).equals("08")){
				 tipoControle.setSigla(PrintHouseConstants.Parametro.SIGLA_TIPO_CONTROLE_ARQUIVO_PRINT_5);
			 }else if (dtVendimento.substring(6,8).equals("10")){
				 tipoControle.setSigla(PrintHouseConstants.Parametro.SIGLA_TIPO_CONTROLE_ARQUIVO_PRINT_10);
			 }else if (dtVendimento.substring(6,8).equals("15")){			 
				 tipoControle.setSigla(PrintHouseConstants.Parametro.SIGLA_TIPO_CONTROLE_ARQUIVO_PRINT_15);
			 }else if (dtVendimento.substring(6,8).equals("20")){
				 tipoControle.setSigla(PrintHouseConstants.Parametro.SIGLA_TIPO_CONTROLE_ARQUIVO_PRINT_20);
			 }else{
				 tipoControle.setSigla(PrintHouseConstants.Parametro.SIGLA_TIPO_CONTROLE_ARQUIVO_PRINT_25);
			 }
						
			 tipoControle = (SnTipoControleArquivoBean) super.search("lstSnTipoControleArquivoBySigla", tipoControle).get(0);
			 
			 
			 ConfiguracaoArquivoService configuracaoArquivoService = getService(ConfiguracaoArquivoService.class);		
			 SnConfiguracaoArquivoBean configuracaoArquivo = new SnConfiguracaoArquivoBean();
			 configuracaoArquivo = configuracaoArquivoService.selecionarConfiguracaoArquivo(tipoControle.getSigla());	
			 */
			
			SnConfiguracaoArquivoBean configuracaoArquivo = this.obterConfiguracaoArquivo(dataVencimento
					, siglaTipoLote
					, cidade.getCidContrato()
					, false);
			
			arquivo.setConfiguracaoArquivo(configuracaoArquivo);			 
			arquivo.setDataFim(dataVencimento);
			arquivo.setDataInicio(dataVencimento);
			
			insert(arquivo, getUserSession().getCurrentDbService());

			return arquivo;
			
		}

	}

	/**
	 * Método responsável por buscar os dados da OperadoraNet do arquivo.
	 * 
	 * @author Harrisson Ferreira Gomes - implementação Marcio Bellini -
	 *         refactor Robin Michael Gray
	 * @version 1.0 - criação do Metodo - 29/03/2006
	 * @number RF015_RF021
	 * @return Linha
	 * @param
	 * 
	 * @semantics
	 *	
	 */
	private LinhaDTO cabecalhoArquivo(ArquivoPrintHouseDTO arquivoPrintHouseDTO) {

		SnRegistroEmissaoBean registroEmissao = arquivoPrintHouseDTO
				.getRegistroEmissao();
		return llk.criarLinhaItenCabecalhoArquivo(registroEmissao);

	}

	/**
	 * Método responsável por finalizar o arquivo e iniciar um novo arquivo se
	 * for necessario.
	 * 
	 * @author Harrisson Ferreira Gomes
	 * @version 1.0 - criação do Metodo - 06/04/2006
	 * @author Harrisson Ferreira Gomes - implementação Marcio Bellini -
	 *         refactor Robin Michael Gray
	 * @version 1.1 - 23/05/2006 - Atualização semantics
	 * @number RF015_RF021
	 * @return
	 * @param Boolean
	 *            continuarArquivo true - indica continuação de sequencia. false -
	 *            fim de sequencia do arquivo.
	 * @param Arquivo
	 *            arquivo
	 * @param ArquivoPrintHouseDTO
	 *            arquivoPrintHouseDTO
	 * @semantics 
	 * 
	 * @ejb.interface-method view-type = "both"
     * @ejb.transaction type="Required"
     * 
	 */

	public  void finalizarArquivo(Boolean continuarArquivo,
			SnTipoLoteBean tipoLote, Date dataVencimento,
			SnControleArquivoBean arquivo,
			ArquivoPrintHouseDTO arquivoPrintHouseDTO,String [] lotesList, String idCriterio, Integer seq_arquivo) throws Exception {

		Date dataatual = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String dtVendimento = null;
		
		if ( dataVencimento != null ) {
			dtVendimento = dateFormat.format(dataVencimento);
		}
		
		List segregacoes = new ArrayList();
		
		this.gravarArquivoPrintHouse(arquivoPrintHouseDTO.getCurrentArquivo()
				.getGerarArquivo(), llk.criarLinhaItenFimArquivo(arquivoPrintHouseDTO));

		File outputFile = arquivoPrintHouseDTO.getCurrentArquivo()
				.getGerarArquivo().finalizar();

		logger.info(new BasicAttachmentMessage("Grava arquivo no diretório " + arquivoPrintHouseDTO.getDiretorioGravacaoArquivoPrint() + ".", AttachmentMessageLevel.INFO));
				
			
		SnCidadeOperadoraBean operadora = arquivoPrintHouseDTO.getCurrentArquivo().getSnCidadeOperadoraBean();
		StringBuffer zipFileName = null;
		String prefixoNome = PrintHouseConstants.MascaraArquivo.PREFIXO_NOME_NET;
		if(verificaSegregacao(arquivoPrintHouseDTO.getCurrentArquivo().getPrefixo(),segregacoes)){
			prefixoNome = PrintHouseConstants.MascaraArquivo.PREFIXO_NOME_NETEBT;
		}
		   
		ArquivoPrintHouseZipDTO arquivoPrintHouseZipDTO = arquivoPrintHouseDTO.getArquivoPrintHouseZipDTO(Integer.parseInt(operadora.getCodOperadora()), tipoLote.getSgTipoLote(),prefixoNome); 
		if (arquivoPrintHouseZipDTO == null) {
				
			zipFileName = new StringBuffer();
			if(prefixoNome != null && !"".equals(prefixoNome)){
				zipFileName.append(prefixoNome + "_");
				
			}
			
			String nomeBanco = (StringUtils.replace(getUserSession().getCurrentDbService(), PREFIXO_DATABASE_SERVICE, "")).toUpperCase();
				 
			zipFileName.append(tipoLote.getSgTipoLote());
			zipFileName.append("_");
			zipFileName.append(nomeBanco);
			zipFileName.append("_");
			zipFileName.append(operadora.getCodOperadora());
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("_dd");
			zipFileName.append(simpleDateFormat.format(arquivoPrintHouseDTO.getRegistroEmissao().getDtVctoInicial()));
			simpleDateFormat.applyPattern("_dd_MM_");
			zipFileName.append(simpleDateFormat.format(arquivoPrintHouseDTO.getRegistroEmissao().getDtVctoFinal()));
			zipFileName.append(arquivoPrintHouseDTO.getRegistroEmissao().getIdRegistroEmissao());			
			zipFileName.append(".zip");
			File zipFile = new File(outputFile.getParentFile().getAbsolutePath() + File.separator + zipFileName.toString());
			
			arquivoPrintHouseZipDTO = arquivoPrintHouseDTO.getArquivoPrintHouseZipDTO(operadora.getCodOperadora(), tipoLote.getSgTipoLote(), prefixoNome);
			
			if (arquivoPrintHouseZipDTO == null) {
				arquivoPrintHouseZipDTO = new ArquivoPrintHouseZipDTO(zipFile, tipoLote.getSgTipoLote(), operadora, prefixoNome,dataVencimento);
			}
			
			arquivoPrintHouseDTO.addArquivoPrintHouseZipDTO(operadora.getCodOperadora(), tipoLote.getSgTipoLote(), prefixoNome,arquivoPrintHouseZipDTO);
				 
			logger.info(new BasicAttachmentMessage("Grava arquivo compactado " + zipFile.getName() + ".", AttachmentMessageLevel.INFO));

		}
		ArquivoPrintHouseFileDTO arquivoPrintHouseFile = new ArquivoPrintHouseFileDTO(outputFile, tipoLote.getSgTipoLote(), arquivoPrintHouseDTO.getCurrentArquivo().getSnControleArquivoBean().getIdControleArquivo());
		arquivoPrintHouseZipDTO.addArquivoPrintHouseFile(arquivoPrintHouseFile);
		
		if (continuarArquivo.booleanValue()) {
			
			arquivoPrintHouseDTO.setSequecialNomeArquivo(new Integer(arquivoPrintHouseDTO.getSequecialNomeArquivo().intValue() + 1));
			Boolean controleSegregacao = arquivoPrintHouseDTO.getCurrentArquivo().getControleSegregacao();
			arquivoPrintHouseDTO.finalizeArquivo();
			
			String prefixoSegrecacao = arquivoPrintHouseDTO.getCurrentArquivo().getPrefixo();
			
			arquivoPrintHouseDTO.iniciarArquivo(operadora, controleSegregacao);
			arquivoPrintHouseDTO.getCurrentArquivo().setPrefixo(prefixoSegrecacao);
			
			this.inicializarArquivoPrint(arquivoPrintHouseDTO, 
									operadora, 
									dataVencimento, 
									tipoLote,idCriterio,seq_arquivo,lotesList);
		} else {
			arquivoPrintHouseDTO.finalizeArquivo();
		}	
	}
	
	/**
	 * @number RF015_RF021
	 * @param zipFile
	 * @param arquivoPrintHouseFiles
	 * @param siglaTipoLote
	 * @return
	 * @throws JNetException
	 * 
	 * @ejb.interface-method view-type = "both"
     * @ejb.transaction type="Required"
     * 
	 */
	public  Collection generateArquivoPrintHouseTransporteDTOs(File zipFile
			, Collection arquivoPrintHouseFiles
			, String siglaTipoLote
			, String prefixoNome
			, Date dataVencimento
			, SnCidadeOperadoraBean cidade) {			
			
		try {

			List arquivoPrintHouseTransporteDTOs = new ArrayList();

			Collection filesGerado = new ArrayList(arquivoPrintHouseFiles.size());
			String prefixo = null;
						 
				
			for (Iterator iterArquivoPrintHouseFiles = arquivoPrintHouseFiles.iterator(); iterArquivoPrintHouseFiles.hasNext();) {
				
				ArquivoPrintHouseFileDTO arquivo = (ArquivoPrintHouseFileDTO) iterArquivoPrintHouseFiles.next();				
				filesGerado.add(arquivo.getFileGerado());
				
			}
			
			ZipUtils.zipFiles(zipFile, filesGerado);

			SnControleArquivoBean zipControleArquivo = new SnControleArquivoBean();
			// TODO seta tipo do arquivo : para implementar depois de recebr
			// especifições

			SnStatusArquivoBean situacaoArquivo = new SnStatusArquivoBean();
			situacaoArquivo.setSiglaStatus(SnStatusArquivoBean.Sigla.EMISSAO_PROCESSADO.getChaveSigla());

			List<SnStatusArquivoBean> statusList = super.search("lstStatusBySigla", situacaoArquivo);
			
			if (statusList != null && statusList.size() > 0) {
				situacaoArquivo = statusList.get(0);
			} else {
				situacaoArquivo.setIdStatusArquivo(new Integer(40));
				situacaoArquivo = (SnStatusArquivoBean) find(situacaoArquivo);
			}

			zipControleArquivo.setNomeArquivo(zipFile.getName());
			zipControleArquivo.setDhGeracao(new Date());
			zipControleArquivo.setDhProcessamento(new Date());
			zipControleArquivo.setQtdTotalRegistros(new Integer(arquivoPrintHouseFiles.size()));
			zipControleArquivo.setStatusArquivo(situacaoArquivo);
			zipControleArquivo.setCidContrato(cidade);
			zipControleArquivo.setUsuarioExec(getUserSession().getUserId());
			zipControleArquivo.setUsuarioProcessamento(getUserSession().getUserId());

			SnConfiguracaoArquivoBean configuracaoArquivo = this.obterConfiguracaoArquivo(dataVencimento
					, siglaTipoLote
					, cidade.getCidContrato()
					, false);
			
			zipControleArquivo.setConfiguracaoArquivo(configuracaoArquivo);
			zipControleArquivo.setDataFim(dataVencimento);
			zipControleArquivo.setDataInicio(dataVencimento);

			insert(zipControleArquivo, getUserSession().getCurrentDbService());

			if (this.isArquivoParaPrintHouse(siglaTipoLote)) {
				
				ArquivoRemessaTransporteDTO arquivoPrintHouseTransporteDTO = new ArquivoRemessaTransporteDTO();

				File tempPrintFile = new File(zipFile.getParentFile(), "PRT_"+ zipFile.getName());
				FileUtils.copyFile(zipFile, tempPrintFile);
				arquivoPrintHouseTransporteDTO.setLocalFile(tempPrintFile);
				arquivoPrintHouseTransporteDTO.setDestinoFileName(zipFile.getName());
				arquivoPrintHouseTransporteDTO.setApagarArquivoOrigem(Boolean.TRUE);

				// TODO seta tipo do arquivo : para implementar depois de recebr
				// especifições
				SnControleArquivoBean zipControleArquivoPRT = new SnControleArquivoBean();

				zipControleArquivoPRT.setNomeArquivo("PRT_" + zipFile.getName());
				zipControleArquivoPRT.setDhGeracao(new Date());
				zipControleArquivoPRT.setDhProcessamento(new Date());
				zipControleArquivoPRT.setQtdTotalRegistros(new Integer(arquivoPrintHouseFiles.size()));
				zipControleArquivoPRT.setStatusArquivo(situacaoArquivo);
				zipControleArquivoPRT.setCidContrato(cidade);
				zipControleArquivoPRT.setUsuarioExec(getUserSession().getUserId());
				zipControleArquivoPRT.setUsuarioProcessamento(getUserSession().getUserId());
				zipControleArquivoPRT.setConfiguracaoArquivo(configuracaoArquivo);
				zipControleArquivoPRT.setDataFim(dataVencimento);
				zipControleArquivoPRT.setDataInicio(dataVencimento);

				insert(zipControleArquivoPRT, getUserSession().getCurrentDbService());

				arquivoPrintHouseTransporteDTO.setCidContrato(cidade.getCidContrato());
				arquivoPrintHouseTransporteDTO.setIdControleArquivo(zipControleArquivoPRT.getIdControleArquivo());
				arquivoPrintHouseTransporteDTOs.add(arquivoPrintHouseTransporteDTO);
				logger.info(" ID CONTROLE ARQUIVO -->" + arquivoPrintHouseTransporteDTO.getIdControleArquivo());

			}

			if (this.isArquivoParaGlobo(siglaTipoLote)) {
				
				ArquivoRemessaTransporteDTO arquivoPrintHouseTransporteDTO = new ArquivoRemessaTransporteDTO();

				File tempGloboFile = new File(zipFile.getParentFile(), "GLB_"+prefixoNome+"_"+zipFile.getName());
				FileUtils.copyFile(zipFile, tempGloboFile);
				arquivoPrintHouseTransporteDTO.setLocalFile(tempGloboFile);
				arquivoPrintHouseTransporteDTO.setDestinoFileName(zipFile.getName());
				arquivoPrintHouseTransporteDTO.setApagarArquivoOrigem(Boolean.TRUE);

				configuracaoArquivo = this.obterConfiguracaoArquivo(dataVencimento
						, siglaTipoLote
						, cidade.getCidContrato()
						, true);	

				// TODO seta tipo do arquivo : para implementar depois de recebr
				// especifições
				SnControleArquivoBean zipControleArquivoGLB = new SnControleArquivoBean();

				zipControleArquivoGLB.setNomeArquivo("GLB_"+prefixoNome+"_"+ zipFile.getName());
				zipControleArquivoGLB.setDhGeracao(new Date());
				zipControleArquivoGLB.setDhProcessamento(new Date());
				zipControleArquivoGLB.setQtdTotalRegistros(new Integer(arquivoPrintHouseFiles.size()));
				zipControleArquivoGLB.setStatusArquivo(situacaoArquivo);
				zipControleArquivoGLB.setCidContrato(cidade);
				zipControleArquivoGLB.setUsuarioExec(getUserSession().getUserId());
				zipControleArquivoGLB.setUsuarioProcessamento(getUserSession().getUserId());
				zipControleArquivoGLB.setConfiguracaoArquivo(configuracaoArquivo);
				zipControleArquivoGLB.setDataFim(dataVencimento);
				zipControleArquivoGLB.setDataInicio(dataVencimento);

				insert(zipControleArquivoGLB, getUserSession().getCurrentDbService());

				arquivoPrintHouseTransporteDTO.setIdControleArquivo(zipControleArquivoGLB.getIdControleArquivo());
				arquivoPrintHouseTransporteDTO.setCidContrato(cidade.getCidContrato());
				arquivoPrintHouseTransporteDTOs.add(arquivoPrintHouseTransporteDTO);
				logger.info(" ID CONTROLE ARQUIVO -->" + arquivoPrintHouseTransporteDTO.getIdControleArquivo());
			}

			return arquivoPrintHouseTransporteDTOs;

		} catch (IOException e) {
			// throw new
			// EmissaoBusinessResourceException(EmissaoResources.ERRO_MSG_ASSOCIADA_COD_FORMATACAO_NULO);
			throw new EmissaoBusinessResourceException(
					EmissaoResources.ERRO_MSG_ASSOCIADA_COD_FORMATACAO_NULO,
					new Object[] {}, e);
		}
		
	}
	
	@SuppressWarnings("unchecked")
    private SnConfiguracaoArquivoBean obterConfiguracaoArquivo(Date dataVencimento, 
	                                                           String siglaTipoLote, 
	                                                           String cidContrato, 
	                                                           boolean isGlobo){

		Integer idGrupoArquivo = this.obterIdGrupoLote(siglaTipoLote, isGlobo);

		DetachedCriteria criteria = DetachedCriteria.forClass(SnConfiguracaoArquivoBean.class);
		criteria.createAlias("grupoArquivo", "grupoArquivo");

		if (idGrupoArquivo == SnGrupoArquivoBean.GRUPO_PRINT_HOUSE) {
			criteria.createAlias("cidContrato", "cidContrato");
			criteria.createAlias("tipoControleArquivo", "tipoControleArquivo");
			criteria.add(Restrictions.eq("cidContrato.cidContrato", cidContrato));
			criteria.add(Restrictions.eq("tipoControleArquivo.sigla", obterSiglaTipoControleArquivo(dataVencimento)));
		}
		else if (idGrupoArquivo == SnGrupoArquivoBean.GRUPO_PRINT_HOUSE_BOLETO_AVULSO) {
			criteria.createAlias("cidContrato", "cidContrato");
			criteria.add(Restrictions.eq("cidContrato.cidContrato", cidContrato));
		}

		criteria.add(Restrictions.eq("grupoArquivo.idGrupoArquivo", idGrupoArquivo));

		List<SnConfiguracaoArquivoBean> resultados = (List<SnConfiguracaoArquivoBean>) getCurrentDAO().select(criteria);

		SnConfiguracaoArquivoBean configuracaoArquivo = (SnConfiguracaoArquivoBean) resultados.get(0);

		return configuracaoArquivo;
	}
	
	private String obterSiglaTipoControleArquivo (Date dataVencimento) {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String diaVencimento = null;
		
		if (dataVencimento != null) {
			diaVencimento = dateFormat.format(dataVencimento).substring(6, 8);
		}

		String sigla = "";

		if (diaVencimento.equals("05")) {
			sigla = PrintHouseConstants.Parametro.SIGLA_TIPO_CONTROLE_ARQUIVO_PRINT_5;
		} else if (diaVencimento.equals("08")) {
			sigla = PrintHouseConstants.Parametro.SIGLA_TIPO_CONTROLE_ARQUIVO_PRINT_5;
		} else if (diaVencimento.equals("10")) {
			sigla = PrintHouseConstants.Parametro.SIGLA_TIPO_CONTROLE_ARQUIVO_PRINT_10;
		} else if (diaVencimento.equals("15")) {
			sigla = PrintHouseConstants.Parametro.SIGLA_TIPO_CONTROLE_ARQUIVO_PRINT_15;
		} else if (diaVencimento.equals("20")) {
			sigla = PrintHouseConstants.Parametro.SIGLA_TIPO_CONTROLE_ARQUIVO_PRINT_20;
		} else if (diaVencimento.equals("28")) {
			sigla = PrintHouseConstants.Parametro.SIGLA_TIPO_CONTROLE_ARQUIVO_PRINT_28;
		} else {
			sigla = PrintHouseConstants.Parametro.SIGLA_TIPO_CONTROLE_ARQUIVO_PRINT_25;
		}
	
		return sigla;
		
	}

	private Integer obterIdGrupoLote (String siglaTipoLote, boolean isGlobo) {
		
		Integer idGrupoArquivo = null;
		
		if (siglaTipoLote.equals(SnTipoLoteBean.Sigla.EMISSAOPRINT.getChaveSigla())) {
			idGrupoArquivo = SnGrupoArquivoBean.GRUPO_PRINT_HOUSE;
		} else if (siglaTipoLote.equals(SnTipoLoteBean.Sigla.SEGUNDA_VIA.getChaveSigla()) ||
				siglaTipoLote.equals(SnTipoLoteBean.Sigla.SEGUNDA_VIA_WEB.getChaveSigla())) {
			idGrupoArquivo = SnGrupoArquivoBean.GRUPO_PRINT_HOUSE_SEG_VIA;
		} else if (siglaTipoLote.equals(SnTipoLoteBean.Sigla.CPNF_WEB.getChaveSigla()) ||
				siglaTipoLote.equals(SnTipoLoteBean.Sigla.CPNF.getChaveSigla())) {
			idGrupoArquivo = SnGrupoArquivoBean.GRUPO_PRINT_HOUSE_CPNF;
		}
		
		if (isGlobo) {
			idGrupoArquivo = SnGrupoArquivoBean.GRUPO_PRINT_HOUSE_GLOBO;
		}
		
		if (siglaTipoLote.equals(SnTipoLoteBean.Sigla.EMISSAO_PRINT_BOLETO_AVULSO.getChaveSigla())){
			return SnGrupoArquivoBean.GRUPO_PRINT_HOUSE_BOLETO_AVULSO;
		}

		return idGrupoArquivo;

	}
	
	private boolean verificaSegregacao(String prefixo, Collection segregacoes) {

		for (Iterator iterSegrc = segregacoes.iterator(); iterSegrc.hasNext();) {
			SnSegregacaoBean segregacao = (SnSegregacaoBean) iterSegrc.next();
			if (prefixo.equals(segregacao.getCcPrefixo())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Método responsável por buscar os dados da OperadoraNet do arquivo.
	 * 
	 * @author Harrisson Ferreira Gomes/Everton Ken Tomita - implementação
	 *         Marcio Bellini - refactor Robin Michael Gray
	 * @version 1.0 - criação do Metodo - 29/03/2006
	 * @number RF015_RF021
	 * @return Linha
	 * @param
	 * 
	 * @semantics	
	 */
	protected LinhaDTO operadoraNet(SnCidadeOperadoraBean cidade) {
	
				 
		 DadosOperadoraDTO dadosOperParam = new DadosOperadoraDTO();
		 dadosOperParam.setCidContrato(cidade.getCidContrato());
		
		 DadosOperadoraDTO dadosOperadora = super.getDadosBeanbyQuery("listarDadosOperadora", dadosOperParam, false, DadosOperadoraDTO.class);		
		 
		 return llk.criarLinhaItenOperacaoNet(cidade, dadosOperadora);
	}

	
	protected Collection<LinhaDTO> obterMensagens(Date dataVencimento,
			SnCidadeOperadoraBean cidade, String idCriterio,
			SnSetorizacaoBean.Prefixo siglaSetorizacaoFatura,
			Boolean controleSegregacao, Long idBoleto, String... lotes) {
		return obterMensagens(dataVencimento, cidade, idCriterio,
				siglaSetorizacaoFatura, controleSegregacao, idBoleto,
				new ArrayList<LinhaDTO>(), lotes);

	}

	/**
	 * Método responsável por buscar todas as mensagens atraves da operadora
	 * e/ou segregaçao.
	 * 
	 * @author Harrisson Ferreira Gomes
	 * @version 1.0 - criação do Metodo - 22/03/2006
	 * @author Harrisson Ferreira Gomes
	 * @version 1.1 - Lógica do método, Assinatura - 16/05/2006
	 * @author Harrisson Ferreira Gomes - refactor Robin Michael Gray
	 * @version 2.0 - Assinatura, Semantics na lógica de chamada de método -
	 *          01/08/2006
	 * @number RF015_RF021
	 * @return Collection objetos Linha
	 * @param
	 * @semantics Criar o <domain>Segregacao; Se prefixo != null; Atribuir
	 *            prefixo para Segregacao.codigoPrefixo; Acionar o método
	 *            SegregacaoService.listarSegregacao(segregacao):<Collection>listaRetorno;
	 *
	 * 
     * @ejb.transaction type="Required"
     * 
	 */
	protected Collection<LinhaDTO> obterMensagens(Date dataVencimento,
			SnCidadeOperadoraBean cidade, String idCriterio,
			SnSetorizacaoBean.Prefixo siglaSetorizacaoFatura , Boolean controleSegregacao , Long idBoleto,Collection<LinhaDTO> colLinhasDTO, String...lotes) {

		// TODO Collection colMensagemPrint =
		// super.getMensagemAssociadaService().listarMensagemAssociadaPrint(cidade.getCidContrato(),
		// segregacao.getIdSegregacao(), dataVencimento,
		// siglaSetorizacaoFatura);

		MensagemDTO mensagem = new MensagemDTO(); 
		List<String> lotesList = new ArrayList<String>();

		String msgDeclaracaoDebitoAnual = "";

		for(int i = 0;i<lotes.length;i++){
			lotesList.add(lotes[i]);			
		}
		mensagem.setDataVencimentoTitulo(dataVencimento);
		Collection<MensagemDTO> colMensagemPrint = null;

		if ( controleSegregacao == Boolean.TRUE){

			mensagem.setCidContrato(cidade.getCidContrato());
			mensagem.setIdCriterio(idCriterio);	
			mensagem.setSiglaSetorizacaoFatura(siglaSetorizacaoFatura.getChave()+"%");
			mensagem.setTpMensagem("A");

			colMensagemPrint = super.getDadosBeanByQuery("listarMensagemAssociadaPrint", mensagem, false, MensagemDTO.class);
		}else{
			mensagem.setCidContrato(cidade.getCidContrato());			
			mensagem.setSiglaSetorizacaoFatura(siglaSetorizacaoFatura.getChave()+"%");			
			mensagem.setIdLotes(lotesList) ;
			mensagem.setIdBoleto(idBoleto);
			
			if(lotes.length > 0){
				//segunda via é obrigatório passar o idBoleto como parametro
				if(idBoleto != null){

					colMensagemPrint = super.getDadosBeanByQuery("listarMensagemAssociadaSemSegregacaoPrintByIdBoleto", mensagem, false, MensagemDTO.class);
				}else{
					colMensagemPrint = super.getDadosBeanByQuery("listarMensagemAssociadaSemSegregacaoPrint", mensagem, false, MensagemDTO.class);
				}
			}else{
				//segunda via é obrigatório passar o idBoleto como parametro
				if(idBoleto != null){
					colMensagemPrint = super.getDadosBeanByQuery("listarMensagemAssociadaSemSegregacaoPrintSemLoteByIdBoleto", mensagem, false, MensagemDTO.class);
				}else{
					colMensagemPrint = super.getDadosBeanByQuery("listarMensagemAssociadaSemSegregacaoPrintSemLote", mensagem, false, MensagemDTO.class);
				}
			}
		}
		
		if(idBoleto != null){
			// Adicionando mensagens de totais de impostos
			ArquivoPrintHouseDAO dao = getAphDao();
			String prefixo = colMensagemPrint.toArray(new MensagemDTO[colMensagemPrint.size()])[0].getPrefixo();
			
			try {
				//colMensagemPrint.add(dao.buscarTotalImpostosNet(idBoleto, prefixo));
				colMensagemPrint.add(dao.buscarTotalImpostosEbt(idBoleto, prefixo));
			} catch (Exception e) {						
				logger.error(new BasicAttachmentMessage("ERRO AO BUSCAR MENSAGEM totalImpostos", AttachmentMessageLevel.ERROR));
			}
		}
		
		String msgDeclaracaoDebitoAnualQ1EBT = "";
		String msgDeclaracaoDebitoAnualQ2EBT = "";
		
		boolean contratoMigrado = false;
		boolean isReajuste = false;
		
		if(idBoleto != null){
			if(!siglaSetorizacaoFatura.getChave().equals("S")){ // Segunda via
				if(!siglaSetorizacaoFatura.getChave().equals("N")){ // Cópia NF					
					//Busca informação de Declaracao de Quitação Debito EBT, envia na Interface de Carga Fatura Embratel
					List retMsgEbt = this.buscaMsgDeclaracaoDebitoEBT(idBoleto);										
					if(!retMsgEbt.isEmpty()){
						//Coloca espaço e quebra de linha devido layout Jasper para este tipo de mensagens
						msgDeclaracaoDebitoAnualQ1EBT = "                               " + retMsgEbt.get(0).toString() + "                                                                                                             \n";
						if(retMsgEbt.size()>1){									
							msgDeclaracaoDebitoAnualQ2EBT = (retMsgEbt.get(1)!=null) ? retMsgEbt.get(1).toString() + "                                                                                                                       \n":"                                                                                                                                                     \n";
						}	
					}															
					msgDeclaracaoDebitoAnual = buscaMsgEnvioDebitoAnual(idBoleto);
				}
			}

			isReajuste = buscaIdCriterioMsgReajuste(idCriterio);

			// Verifica se o contrato associado a esse boleto foi migrado de política
			contratoMigrado = getAphDao().verificaMigracaoContratoByIdBoleto(idBoleto);
		}

		if (colMensagemPrint != null) {
			try {				
				//Busca MSG Quitação de Debitos Anual		
				MensagemDTO mensagemAssociadaDebitoAnualNET = new MensagemDTO();
				MensagemDTO mensagemAssociada1DebitoAnualEBT = new MensagemDTO();
				MensagemDTO mensagemAssociada2DebitoAnualEBT = new MensagemDTO();	
				
				Integer validaEnvioMsgQuitacaoDebitoEBT = 0;
				boolean inseriuMsgQuitacaoDebito = false;
				boolean inserirMsgAssociadaQuitacaoDebitoAnual = false;
				boolean inseriuMsgMigracao = false;
				boolean inserirMsgMigracao = false;
				
				MensagemDTO mensagemMigracaoPolitica = new MensagemDTO();	
				String dsMensagemMigracao = this.getMensagemMigracao();

				for (Iterator iterMensagens = colMensagemPrint.iterator(); iterMensagens.hasNext();) {
					MensagemDTO mensagemAssociada = (MensagemDTO) iterMensagens.next();

					if (mensagemAssociada.getCcCodigoFormatacao() == null)
						throw new EmissaoBusinessResourceException(EmissaoResources.ERRO_MSG_ASSOCIADA_COD_FORMATACAO_NULO);

					if (idBoleto != null) {
						if (mensagemAssociada.getCcCodigoSetorizacao().substring(1).equals("FAFT")
								&& mensagemAssociada.getDsSegmentacao().contains("Cobranças DCC")
								&& mensagemAssociada.getIdSetorizacao() == 7
								&& mensagemAssociada.getDsMensagem().contains("debito autorizado pelo assinante")) {
							String msg = mensagemAssociada.getDsMensagem();
							msg = msg.replaceAll("\\.", ", no ").concat(buscarNomeDoBanco(idBoleto));
							mensagemAssociada.setDsMensagem(msg);
						}

						// Mensagem importante
						if (mensagemAssociada.getCcCodigoSetorizacao().substring(1).equals("IPFE")) {
							if (!isReajuste) {
								// Precisa inserir mensagem quitação e ainda não inseriu
								if (!msgDeclaracaoDebitoAnual.equals("") && !inseriuMsgQuitacaoDebito ) {
									mensagemAssociada.setDsMensagem(msgDeclaracaoDebitoAnual); // substitui mensagem atual
									inseriuMsgQuitacaoDebito = true;
								}
							} else {
								// Precisa inserir mensagem quitação mas não pode substituir mensagem atual
								if (!msgDeclaracaoDebitoAnual.equals("")) {
									BeanUtils.copyProperties(mensagemAssociadaDebitoAnualNET, mensagemAssociada);
									inserirMsgAssociadaQuitacaoDebitoAnual = true; // insere no fim das importantes 							
								}
							}

							colLinhasDTO.add(llk.criarLinhaItenMensagem(cidade, mensagemAssociada)); // insere mensagem normal


							// Contrato foi migrado
							if (contratoMigrado && !inserirMsgMigracao){
								BeanUtils.copyProperties(mensagemMigracaoPolitica, mensagemAssociada);
								inserirMsgMigracao = true; // insere no fim das importantes 							
							}
						} else {
							// Inserção mensagem quitação e migração no fim das mensagens importantes
							if (inserirMsgAssociadaQuitacaoDebitoAnual && !inseriuMsgQuitacaoDebito) {
								mensagemAssociadaDebitoAnualNET.setDsMensagem(msgDeclaracaoDebitoAnual);
								inseriuMsgQuitacaoDebito = true;
								inserirMsgAssociadaQuitacaoDebitoAnual = false;
								colLinhasDTO.add(llk.criarLinhaItenMensagem(cidade, mensagemAssociadaDebitoAnualNET));
							}
							if (inserirMsgMigracao && !inseriuMsgMigracao) {
								mensagemMigracaoPolitica.setDsMensagem(dsMensagemMigracao);
								colLinhasDTO.add(llk.criarLinhaItenMensagem(cidade, mensagemMigracaoPolitica));
								inseriuMsgMigracao = true;
								inserirMsgMigracao = false;
							}
							
							if (!msgDeclaracaoDebitoAnualQ1EBT.equals("") && mensagemAssociada.getCcCodigoSetorizacao().substring(1).equals("NFFP")) {	
								
								if(validaEnvioMsgQuitacaoDebitoEBT==1){
									mensagemAssociada.setDsMensagem(mensagemAssociada.getDsMensagem().trim());
									colLinhasDTO.add(llk.criarLinhaItenMensagem(cidade, mensagemAssociada));								
									
									BeanUtils.copyProperties(mensagemAssociada1DebitoAnualEBT, mensagemAssociada);																
									mensagemAssociada1DebitoAnualEBT.setDsMensagem(msgDeclaracaoDebitoAnualQ1EBT.toUpperCase());																						
									colLinhasDTO.add(llk.criarLinhaItenMensagem(cidade, mensagemAssociada1DebitoAnualEBT));
									
									BeanUtils.copyProperties(mensagemAssociada2DebitoAnualEBT, mensagemAssociada);																
									mensagemAssociada2DebitoAnualEBT.setDsMensagem(msgDeclaracaoDebitoAnualQ2EBT.toUpperCase());																						
									colLinhasDTO.add(llk.criarLinhaItenMensagem(cidade, mensagemAssociada2DebitoAnualEBT));																
									validaEnvioMsgQuitacaoDebitoEBT ++;
								}else{
									colLinhasDTO.add(llk.criarLinhaItenMensagem(cidade, mensagemAssociada));
									validaEnvioMsgQuitacaoDebitoEBT ++;
								}								
							} else {
								colLinhasDTO.add(llk.criarLinhaItenMensagem(cidade, mensagemAssociada));							
							}
						}
					}else{
						colLinhasDTO.add(llk.criarLinhaItenMensagem(cidade, mensagemAssociada));
					}
				}	

			} catch (Exception e) {								
				logger.info(new BasicAttachmentMessage("ERRO AO COPIAR PROPERTIES mensagemAssociada", AttachmentMessageLevel.INFO));
				e.printStackTrace();
			} 				
			
		}
		return colLinhasDTO;
	}	
	
	/**
	 * Método responsável por criar todas as mensagems "complemento campo importante".
	 * 
	 * @author Leandro Roderigues
	 * @version 1.0 - criação do Metodo - 26/01/2015
	 * @number Demanda Anatel
	 * @return Collection objetos Linha
	 * @param
	 * 
     * @ejb.transaction type="Required"
     * 
	 */
	protected void criarComplementoCampoImportante(Long idBoleto) {

		try {
		
			ArquivoPrintHouseDAO dao = getAphDao();
	
			dao.gerarMensagensAnatel(idBoleto);
			
		
		} catch (Exception e) {								
			logger.info(new BasicAttachmentMessage("ERRO AO criarComplementoCampoImportante", AttachmentMessageLevel.INFO));
			e.printStackTrace();
		} 	

	}	
	
	
	/**
	 * Método responsável por buscar todas as mensagems "complemento campo importante".
	 * 
	 * @author Leandro Roderigues
	 * @version 1.0 - criação do Metodo - 26/01/2015
	 * @number Demanda Anatel
	 * @return Collection objetos Linha
	 * @param
	 * 
     * @ejb.transaction type="Required"
     * 
	 */
	protected List<SetorDTO> obterComplementoCampoImportante(Long idBoleto) {

		List<SetorDTO> listaSetorDTO = new ArrayList<SetorDTO>();
		SetorDTO setorComplementoCampoImportante = null;
		int count = 0;
		
		try {
		
			ArquivoPrintHouseDAO dao = getAphDao();
	
			List<MensagemAnatelDTO> listMensagemAnatelDTO =  dao.buscarMensagensAnatel(idBoleto);
			
			/*try{   -- | COMBO SVA - 151237 - DEVE REMOVER ESSE CODIGO
				MensagemAnatelDTO mensagemSVA = dao.buscarMensagemSVA(idBoleto);
				if(mensagemSVA != null){
					listMensagemAnatelDTO.add(mensagemSVA);
				}
			}catch(Exception e){
				logger.error(new BasicAttachmentMessage("ERRO AO BUSCAR MENSAGEM SVA", AttachmentMessageLevel.ERROR));
			}*/
			
			for (MensagemAnatelDTO mensagemAnatelDTO : listMensagemAnatelDTO) {
				
				count++;
				
				if(count != listMensagemAnatelDTO.size())
					setorComplementoCampoImportante = new SetorDTO(TipoSetorPrint.NOTA_FISCAL_NET, null, null, null, false, false);
				else
					setorComplementoCampoImportante = new SetorDTO(TipoSetorPrint.NOTA_FISCAL_NET, null, new LinhaDTO(LinhaDTO.TIPO_ITEN_NET_FIM_TITULO), null, false, false);
				
				setorComplementoCampoImportante.addLinha(this.llk.criarLinhaComplementoCampoImportante(mensagemAnatelDTO));
				
				listaSetorDTO.add(setorComplementoCampoImportante);
				
			}
		
		} catch (Exception e) {								
			logger.info(new BasicAttachmentMessage("ERRO AO BUSCAR MENSAGENS CAMPO IMPORTANTE", AttachmentMessageLevel.ERROR));
			e.printStackTrace();
		} 	

		return listaSetorDTO;
	}
	
	/**
	 * Método responsável por buscar mensagem streaming.
	 * 
	 * @return Collection objetos Linha
	 * @param
	 * 
     * @ejb.transaction type="Required"
     * 
	 */
	protected List<SetorDTO> obterMensagemStreaming(Long idBoleto) {

		List<SetorDTO> listaSetorDTO = new ArrayList<SetorDTO>();
		SetorDTO setorMensageStreaming = null;
		
		try {
		
			ArquivoPrintHouseDAO dao = getAphDao();
	
			List<MensagemStreamingDTO> listMensagemStreamingDTO =  dao.buscarMensagemStreaming(idBoleto);
			
			for (MensagemStreamingDTO mensagemStreamingDTO : listMensagemStreamingDTO) {
				
				setorMensageStreaming = new SetorDTO(TipoSetorPrint.MENSAGEM_STREAMING, null, null, null, false, false);
				
				setorMensageStreaming.addLinha(this.llk.criarLinhaMensagemStreaming(mensagemStreamingDTO));
				
				listaSetorDTO.add(setorMensageStreaming);
			}
		
		} catch (Exception e) {								
			
			logger.info(new BasicAttachmentMessage("ERRO AO BUSCAR MENSAGEM STREAMING", AttachmentMessageLevel.ERROR));
			e.printStackTrace();
		} 	

		return listaSetorDTO;
	}
	
	/**
	 * Método responsável por buscar so PREFIXO permitidos para MSG do tipo PIPFE.
	 * 
	 **/	
	private Boolean buscaIdCriterioMsgReajuste(String idCriterio){	
		
		DynamicBean dynamicBean = new DynamicBean();
		dynamicBean.set("idCriterio", idCriterio);
		
		List lstIdCriterio = super.search("lstBuscaIdCriterioMsgReajuste", dynamicBean);
		
		if(!lstIdCriterio.isEmpty()){
			return true;
		}		
		return false;		 
	}	

	/**
	 * Retorna a mensagem de migração de política
	 * @return
	 */
	private String getMensagemMigracao(){	
		SnMensagemBean msg = new SnMensagemBean();
		msg.setIdMensagem(ID_MENSAGEM_PADPOL);
		msg = (SnMensagemBean)super.findByPrimaryKey(msg);
		if (msg != null) {
			return msg.getDsDescricao();
		} else {
	 		return null;
		}
	}	

	/**
	 * Método responsável por buscar as informaçoes da Declaracao de Debito Anual para Cobranca.
	 **/	
	
	private String buscaMsgEnvioDebitoAnual(Long idBoleto) {

		String strRetProc = "";

		try{
			
			BatchParameter[] parameters = new BatchParameter[2];
			
			parameters[0] = new BatchParameter(Types.VARCHAR, true);
			parameters[1] = new BatchParameter(idBoleto, Types.NUMERIC, false);

			List listRetProc = super.getCurrentDAO().executeBatch(FSN_LST_VERIFICA_ENVIO_QUITACAO_DEBITO_ANUAL, parameters);
			
			strRetProc = listRetProc.get(0) == null ? "" : listRetProc.get(0).toString();			

		}catch(Exception e){
			logger.info(new BasicAttachmentMessage("ERRO AO VALIDAR ENVIO MSG QUITACAO DEBITO ANUAL PROC -->" + FSN_LST_VERIFICA_ENVIO_QUITACAO_DEBITO_ANUAL + " ID_BOLETO: " + idBoleto, AttachmentMessageLevel.INFO));
		}

		return strRetProc;	
	}	

	/**
	 * Método responsável por buscar todas os bancos que estão Conveniados a
	 * operadora.
	 * 
	 * @author Harrisson Ferreira Gomes/Everton Ken Tomita - refactor Robin
	 *         Michael Gray
	 * @version 1.0 - criação do Metodo - 27/03/2006
	 * @number RF015_RF021
	 * @return Collection objetos Linha
	 * @param Operadora
	 *            operadora
	 * 
	 * @semantics <br>
	 *            acionar o método
	 *            PrintDAO.selecionarBancoConveniados(idOperadora);
	 */
	protected Collection<LinhaDTO> obterBancoConveniado(
			SnCidadeOperadoraBean cidade, Integer tipoCobranca, Date vencimento) {

		DynamicBean bean = new DynamicBean();
    	bean.put("cidContrato", cidade.getCidContrato());
    	bean.put("tipoCobranca", tipoCobranca);
    	bean.put("vencimento", vencimento);
		
    	Collection<LinhaDTO> retorno = new ArrayList<LinhaDTO>();

		List<Map> lista = (List<Map>) super.search("selecionarBancoConveniados", bean,false);		
		LinhaDTO  linha = null;	
		
		for(Map hm : lista){						
			linha = new LinhaDTO(LinhaDTO.TIPO_ITEN_BANCO, hm);
			retorno.add(linha);
		}

		return retorno;
	}

	/**
	 * Gera uma fatura para o layout da print house. <br>
	 * Eh retornado uma estrutura de dados <code>FaturaDTO</code> que
	 * representa o layout de uma fatura dentro do arquivo que sera enviado para
	 * a print. <br>
	 * O metodo ja retorna as linhas na ordem que devem ser colocadas dentro do
	 * arquivo. Alem disso faz a contabilizacao de paginas que a fatura ira
	 * gerar. <br>
	 * Possui regras para gerar uma fatura somente da NET. Nao esta implementado
	 * as regras para montar nota fiscal de empresas parceiras. <br>
	 * 
	 * @version 1.0 - 16/03/2006 - Criacao
	 * @author Everton Ken Tomita
	 * @version 1.1 - 19/03/2006 - Correcoes na implementacao do metodo
	 * @author Everton Ken Tomita
	 * @version 1.2 - 27/03/2006 - Colocando regra de boleto sem nota fiscal
	 * @author Everton Ken Tomita - implementação Marcio Bellini - refactor
	 *         Robin Michael Gray
	 * @number RF015_RF021
	 * 
	 * @param idLote
	 *            identificador do lote que o boleto pertence para ser gerado
	 * @param idBoleto
	 *            identificador do boleto que deve ser gerado no arquivo.
	 * @return FaturaDTO que contem as linhas na ordem que devem ser coloca
	 *         dentro do arquivo que sera enviado para Print.
	 * @throws JNetException
	 * 
	 * @semantics
	 *	
	 * 
	 * @ejb.interface-method view-type = "both"
     * @ejb.transaction type="Required"
     * 
	 */ 
	
	@SuppressWarnings("unchecked")
	public FaturaDTO gerarFaturaPrintHouse(FaturaNetDTO fatura){

		// Guarda o id do boleto atual para poder logar se houver erro
		this.setIdBoletoAtual(fatura.getIdBoleto());

		// Criar variavel do tipo SetorListaDTO (setorLista) // nao iniciar
		SetorListaDTO setorLista = null;

		// busca os dados de nf e titulos do boleto Executar

		int contLinhas = 0;

		Map mapFatura = null;
		boolean firstListaParametro = true;
		DadosGeraisPrimeiraViaPrintDTO dadosGeraisPrimeiraViaPrintDTO = null; 
		Boolean isOperadoraNet  = Boolean.TRUE;

		Collection<DadosGeraisPrimeiraViaPrintDTO> listaNotaFiscais = null;
		String cidContrato = fatura.getCidContrato();
		isOperadoraNet = fatura.isOperadoraNet();


		ArquivoPrintHouseDAO dao = getAphDao();

		int qdeNFNet = 0;
		Integer countNFNet = 0;

		this.criarComplementoCampoImportante(fatura.getIdBoleto());
		List<SetorDTO> listComplementoCampoImportante = obterComplementoCampoImportante(fatura.getIdBoleto());

		List<MensagemAnatelDTO> listMensagemAnatelDTO = null;	

		try {
			listMensagemAnatelDTO = dao.buscarMensagensAnatel(fatura.getIdBoleto());
		} catch (Exception e) {								
			logger.info(new BasicAttachmentMessage("ERRO AO BUSCAR MENSAGENS CAMPO IMPORTANTE", 
			                                       AttachmentMessageLevel.ERROR));
			
			e.printStackTrace();
		} 	
		if(fatura.getDemonstrativoFinanceiro() != null){
			for ( DemonstrativoFinanceiroDTO descricao : fatura.getDemonstrativoFinanceiro()  ) {
				
				if (descricao.getDESCRICAOITEMDEMONST_FINANC().startsWith("#!#")){
					descricao.setDESCRICAOITEMDEMONST_FINANC(descricao.getDESCRICAOITEMDEMONST_FINANC().replace("#!#", "   "));
				}
				
				if (descricao.getDESCRICAOITEMDEMONST_FINANC().startsWith("#@#")){
					descricao.setDESCRICAOITEMDEMONST_FINANC(descricao.getDESCRICAOITEMDEMONST_FINANC().replace("#@#", "   "));
				}
			}
		}
		if(fatura.getFcBoletoConsolidado().equalsIgnoreCase("C") || 
				fatura.getFcBoletoConsolidado().equalsIgnoreCase("B")|| 
				fatura.getFcBoletoConsolidado().equalsIgnoreCase("N") || 
				fatura.getFcBoletoConsolidado().equalsIgnoreCase("R") ||
				(fatura.getFcBoletoConsolidado().equalsIgnoreCase("D") && isOperadoraNet) || 
				fatura.getFcBoletoConsolidado().equalsIgnoreCase("M")){

			if (setorLista == null) {
				dadosGeraisPrimeiraViaPrintDTO =  fatura.getDadosGeraisNF().get(0);

				if (dadosGeraisPrimeiraViaPrintDTO == null) {		
					throw new EmissaoBusinessResourceException(EmissaoResources.ERRO_DADOS_GERAIS_PRIM_VIA_NULO);
				}

				dadosGeraisPrimeiraViaPrintDTO.setCodigoLote(new Long(fatura.getIdLote().longValue()));

				mapFatura = this.preencheMapFatura(dadosGeraisPrimeiraViaPrintDTO,fatura,false,"");
				setorLista = new SetorListaDTO(new LinhaDTO(LinhaDTO.TIPO_ITEN_INICIO_FATURA, mapFatura), 
				                               new LinhaDTO(LinhaDTO.TIPO_ITEN_FIM_FATURA));

			}

			cidContrato = fatura.getCidContrato();
			if ( dadosGeraisPrimeiraViaPrintDTO != null ) {
				//Gera o setor minha net
				SetorDTO setorMinhaNET = criarSetorMinhaNet(dadosGeraisPrimeiraViaPrintDTO, 
				                                            fatura.getFcBoletoConsolidado());

				gerarSetorMinhaNet(setorMinhaNET, fatura);
				setorLista.addSetor(setorMinhaNET);

				//Gera o setor de ocorrencias
				SetorDTO setorOcorrencia = gerarSetorOcorrencias(fatura, dadosGeraisPrimeiraViaPrintDTO);
				if (setorOcorrencia != null) setorLista.addSetor(setorOcorrencia);

				SetorDTO setorDemFinFicArr = 
						criarSetorDemonstrativoFichaArrecadacao(dadosGeraisPrimeiraViaPrintDTO,
						                                                            fatura.getFcBoletoConsolidado(), 
						                                                            dadosGeraisPrimeiraViaPrintDTO);
				
				int[] aux = this.gerarSetorDemonstrativoFichaArrecadacao(setorDemFinFicArr,
				                                                                  fatura.getIdBoleto(),
				                                                                  dadosGeraisPrimeiraViaPrintDTO,
				                                                                  dadosGeraisPrimeiraViaPrintDTO,
				                                                                  dadosGeraisPrimeiraViaPrintDTO.getValorTotalNotaFiscal(),
				                                                                  cidContrato,
				                                                                  fatura.getFcBoletoConsolidado(),
				                                                                  fatura,
				                                                                  contLinhas);
				setorLista.addSetor(setorDemFinFicArr);
				setorLista.setValorCobrado(dadosGeraisPrimeiraViaPrintDTO.getValorCobrado());

				//Gera o setor de filiados
				//Setor de filiados comeca sempre em nova pagina se o demonstrativo estiver na 1a folha
				//senao ele continuara o demonstrativo financeiro. 
				int paginaAtualDemonstrativo = aux[0];
				int auxContLinhas = aux[1];
				
				SetorDTO setorFiliados = criarSetorFiliados(dadosGeraisPrimeiraViaPrintDTO, fatura, paginaAtualDemonstrativo);
				
				if (paginaAtualDemonstrativo < 3) {
					//Se demonstrativo ocupou apenas 1 coluna, o filiados sera 2 
					//colunas na frente. Se ocupou as 2 colunas, sera apenas 1 coluna a frente.
					paginaAtualDemonstrativo += (paginaAtualDemonstrativo % 2 == 0)? 1 : 2;
					auxContLinhas = 0;
				}
				
				gerarSetorFiliados(setorFiliados, fatura, paginaAtualDemonstrativo, cidContrato, setorDemFinFicArr, auxContLinhas, null);
				
				if (setorFiliados!= null) {
					setorLista.addSetor(setorFiliados);
				}
			}

			if(fatura.getFcBoletoConsolidado().equalsIgnoreCase("C") || 
					fatura.getFcBoletoConsolidado().equalsIgnoreCase("P") ||
					fatura.getFcBoletoConsolidado().equalsIgnoreCase("A") || 
					(fatura.getFcBoletoConsolidado().equalsIgnoreCase("D") && isOperadoraNet == false) || 
					fatura.getFcBoletoConsolidado().equalsIgnoreCase("B")){

				BigDecimal vlrParametro = buscaVlrParametro(CHECA_LINHAS_FONE);
				boolean parametroLigado = vlrParametro.compareTo(BigDecimal.ONE) == 0;
				
				if((fatura.getLigacoes() != null &&
				   fatura.getLigacoes().size() > 0 &&
				   parametroLigado) ||
				   !parametroLigado) {
					
					// Detalhes Ligações 
					SetorDTO setorDetLigacoesNetEBT = this.criarSetorDetalheLigacoesNETEBT(dadosGeraisPrimeiraViaPrintDTO,
					                                                                       fatura);

					this.gerarSetorDetalheLigacoesNETEBT(setorDetLigacoesNetEBT, 
					                                     fatura.getIdBoleto(),
					                                     dadosGeraisPrimeiraViaPrintDTO, 
					                                     dadosGeraisPrimeiraViaPrintDTO,
					                                     dadosGeraisPrimeiraViaPrintDTO.getValorTotalNotaFiscal(), 
					                                     cidContrato, 
					                                     fatura.getFcBoletoConsolidado(),
					                                     fatura);
					
					setorLista.addSetor(setorDetLigacoesNetEBT);
				}
			}

			if(fatura.getPossuiNF() && fatura.getDadosGeraisNF() != null) {
				qdeNFNet = fatura.getDadosGeraisNF().size();
				
				for (DadosGeraisPrimeiraViaPrintDTO dadosGeraisPrimeiraVia : fatura.getDadosGeraisNF()) {

					if (dadosGeraisPrimeiraVia.isOperadoraNet().booleanValue()) { 

						countNFNet++;
						
						dadosGeraisPrimeiraVia.setCodigoLote(new Long(fatura.getIdLote().longValue()));

						SetorDTO setorNotaFiscalNet = criarSetorNotaFiscalNet(dadosGeraisPrimeiraVia,
						                                                      TipoSetorPrint.NOTA_FISCAL_NET,
						                                                      fatura.getFcBoletoConsolidado(), 
						                                                      true, 
						                                                      listComplementoCampoImportante);
						
						SetorDTO setorTributosNet = criarSetorNotaFiscalNet(dadosGeraisPrimeiraVia,
						                                                    TipoSetorPrint.NOTA_FISCAL_TRIBUTOS_NET,
						                                                    fatura.getFcBoletoConsolidado(), 
						                                                    true, 
						                                                    listComplementoCampoImportante);
						
						contLinhas = gerarSetorNotaFiscalNet(setorNotaFiscalNet, 
						                                     setorTributosNet, 
						                                     dadosGeraisPrimeiraVia.getIdCobrancaNotaFiscal(), 
						                                     dadosGeraisPrimeiraVia, 
						                                     fatura, 
						                                     qdeNFNet);
						
						setorLista.addSetor(setorNotaFiscalNet);
						setorLista.addSetor(setorTributosNet);
						dadosGeraisPrimeiraViaPrintDTO = dadosGeraisPrimeiraVia;
						
						if(countNFNet != qdeNFNet && (listComplementoCampoImportante != null && 
								listComplementoCampoImportante.size() != 0)){
							setorLista.addSetor(new SetorDTO(TipoSetorPrint.NOTA_FISCAL_NET, null, new LinhaDTO(LinhaDTO.TIPO_ITEN_NET_FIM_TITULO), null, false, false));
						}
					}
				}

				if(listComplementoCampoImportante != null){
					for (SetorDTO setorDTO : listComplementoCampoImportante) {
						setorLista.addSetor(setorDTO);
					}
				}
			}



			if(fatura.getFcBoletoConsolidado().equalsIgnoreCase("C") || 
					fatura.getFcBoletoConsolidado().equalsIgnoreCase("P") ||
					fatura.getFcBoletoConsolidado().equalsIgnoreCase("A") || 
					(fatura.getFcBoletoConsolidado().equalsIgnoreCase("D") && isOperadoraNet == false) || 
					fatura.getFcBoletoConsolidado().equalsIgnoreCase("B")){

				if ( fatura.getIdParceiros() != null ){

					for(Long parceiro : fatura.getIdParceiros() ) {

						this.gerarSetorNotaFiscalEBT(setorLista, 
						                             dadosGeraisPrimeiraViaPrintDTO, 
						                             cidContrato, 
						                             parceiro, 
						                             fatura, 
						                             listMensagemAnatelDTO);

					}	

					calcularAgrupamentoNFParceiro(setorLista, cidContrato);
				}
			}
		}

		if(fatura.getFcBoletoConsolidado().equalsIgnoreCase("C") || 
				fatura.getFcBoletoConsolidado().equalsIgnoreCase("P") ||
				fatura.getFcBoletoConsolidado().equalsIgnoreCase("A") || 
				(fatura.getFcBoletoConsolidado().equalsIgnoreCase("D") && isOperadoraNet == false)|| 
				fatura.getFcBoletoConsolidado().equalsIgnoreCase("B")){


			if (fatura.getFcBoletoConsolidado().equalsIgnoreCase("P") || 
					fatura.getFcBoletoConsolidado().equalsIgnoreCase("A") || 
					fatura.getFcBoletoConsolidado().equalsIgnoreCase("D")){

				if  ( fatura != null ) {

					firstListaParametro = true;

					for (DadosGeraisPrimeiraViaPrintDTO dadosGeraisPrimeiraVia : fatura.getDadosGeraisNF()) {

						dadosGeraisPrimeiraViaPrintDTO = dadosGeraisPrimeiraVia;

						dadosGeraisPrimeiraViaPrintDTO = fatura.getDadosGeraisNF().iterator().next();

						if (dadosGeraisPrimeiraViaPrintDTO.isOperadoraNet().booleanValue() == false)  {

							dadosGeraisPrimeiraViaPrintDTO.setCodigoLote(new Long(fatura.getIdLote().longValue()));		

							if (firstListaParametro) {

								firstListaParametro = false;
								mapFatura = this.preencheMapFatura(dadosGeraisPrimeiraViaPrintDTO,fatura,false,"");
								setorLista = new SetorListaDTO(new LinhaDTO(LinhaDTO.TIPO_ITEN_INICIO_FATURA, 
								                                            mapFatura),
								                               new LinhaDTO(LinhaDTO.TIPO_ITEN_FIM_FATURA));
							}
						}
					}
				}

				if  ( fatura != null ) {
					if (dadosGeraisPrimeiraViaPrintDTO.isOperadoraNet().booleanValue() == false) {
						cidContrato = fatura.getCidContrato();
						if ( dadosGeraisPrimeiraViaPrintDTO != null ){
							//Gera o setor minha net
							SetorDTO setorMinhaNET = criarSetorMinhaNet(dadosGeraisPrimeiraViaPrintDTO, 
							                                            fatura.getFcBoletoConsolidado());
							
							gerarSetorMinhaNet(setorMinhaNET, fatura);
							setorLista.addSetor(setorMinhaNET);

							SetorDTO setorDemFinFicArr = 
									criarSetorDemonstrativoFichaArrecadacao(dadosGeraisPrimeiraViaPrintDTO,
									                                        fatura.getFcBoletoConsolidado(), 
									                                        dadosGeraisPrimeiraViaPrintDTO);
							
							int aux[] = gerarSetorDemonstrativoFichaArrecadacao(setorDemFinFicArr,
							                                                    fatura.getIdBoleto(),
							                                                    dadosGeraisPrimeiraViaPrintDTO,
							                                                    dadosGeraisPrimeiraViaPrintDTO,
							                                   dadosGeraisPrimeiraViaPrintDTO.getValorTotalNotaFiscal(),
							                                                    cidContrato,
							                                                    fatura.getFcBoletoConsolidado(),
							                                                    fatura,
							                                                    contLinhas);
							
							setorLista.addSetor(setorDemFinFicArr);
							setorLista.setValorCobrado(dadosGeraisPrimeiraViaPrintDTO.getValorCobrado());

							//Gera o setor de filiados
							//Setor de filiados comeca sempre em nova pagina se o demonstrativo estiver na 1a folha
							//senao ele continuara o demonstrativo financeiro. 
							int paginaAtualDemonstrativo = aux[0];
							int auxContLinhas = aux[1];
							SetorDTO setorFiliados = criarSetorFiliados(dadosGeraisPrimeiraViaPrintDTO, 
							                                            fatura, 
							                                            paginaAtualDemonstrativo);
							
							if (paginaAtualDemonstrativo < 3) {
								//Se demonstrativo ocupou apenas 1 coluna, o filiados sera 2 
								//colunas na frente. Se ocupou as 2 colunas, sera apenas 1 coluna a frente.
								paginaAtualDemonstrativo += (paginaAtualDemonstrativo % 2 == 0)? 1 : 2;
								auxContLinhas = 0;
							}
							
							gerarSetorFiliados(setorFiliados, 
							                   fatura, 
							                   paginaAtualDemonstrativo, 
							                   cidContrato, 
							                   setorDemFinFicArr, 
							                   auxContLinhas, 
							                   null);
							
							if (setorFiliados!=null){
								setorLista.addSetor(setorFiliados);
							}
						}		
					}
				}

				// Detalhes Ligações 
				SetorDTO setorDetLigacoesNetEBT = criarSetorDetalheLigacoesNETEBT(dadosGeraisPrimeiraViaPrintDTO,
				                                                                  fatura);

				gerarSetorDetalheLigacoesNETEBT(setorDetLigacoesNetEBT, 
				                                fatura.getIdBoleto(),
				                                dadosGeraisPrimeiraViaPrintDTO, 
				                                dadosGeraisPrimeiraViaPrintDTO,
				                                dadosGeraisPrimeiraViaPrintDTO.getValorTotalNotaFiscal(),
				                                cidContrato, 
				                                fatura.getFcBoletoConsolidado(),
				                                fatura);
				
				setorLista.addSetor(setorDetLigacoesNetEBT);

				if ( fatura.getIdParceiros() != null ){
					for(Long parceiro : fatura.getIdParceiros() ) {

						this.gerarSetorNotaFiscalEBT(setorLista, 
						                             dadosGeraisPrimeiraViaPrintDTO, 
						                             cidContrato, 
						                             parceiro, 
						                             fatura, 
						                             listMensagemAnatelDTO);

					}
					
					calcularAgrupamentoNFParceiro(setorLista, cidContrato);
				}
			}
		}
		
		try {

			List<SetorDTO> listMensagemStreaming = obterMensagemStreaming(fatura.getIdBoleto());

			if (listMensagemStreaming != null) {

				for (SetorDTO setorDTO : listMensagemStreaming) {

					setorLista.addSetor(setorDTO);
				}
			}
		} 
		catch (Exception e) {

			logger.info(new BasicAttachmentMessage(
					"ERRO AO BUSCAR MENSAGEM STREAMING",
					AttachmentMessageLevel.ERROR));
			e.printStackTrace();
		}

		return this.gerarFatura(cidContrato, setorLista);
	}
	
    public FaturaDTO gerarFaturaPrintHouseBoletoAvulso(FaturaNetDTO fatura){
		// Guarda o id do boleto atual para poder logar se houver erro
		setIdBoletoAtual(fatura.getIdBoleto());
		
		FaturaPrintHouseBuilder builder = new BoletoAvulsoPrintHouseBuilder(getAphDao());

		SnLoteBean loteCorrente = new SnLoteBean();
		//NSMSP_172250_NI_003
		if(fatura.getIdLote() != null){
			loteCorrente = buscarLote(fatura.getIdLote());
		}
		
		return builder.gerarFatura(fatura, loteCorrente);
	}
	
	private void gerarSetorDetalheLigacoesNETEBT(
			SetorDTO setorDetItemEBT, Long idBoleto,
			DemonstrativoFinanceiroPrintDTO demonstrativoFinanceiroPrintDTO,
			FichaArrecadacaoPrintDTO fichaArrecadacaoPrintDTO,Double vlrTotalNF, String cidContrato,
			String boletoConsolidado, FaturaNetDTO fatura) {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		List<DetalhamentoLigacaoParceiroDTO> ligacoes = fatura.getLigacoes();
		
		int linhasPorPagina = TAM_PAGINA_DETALHAMENTO_EBT;
		
		String grupoAnterior = null;
		String servicoAnterior = null;
		String telefoneAnterior = null;
		Double totalSubGrupo = 0.0;
		Double totalDuracaoSubGrupo = 0.0;
		Double totalGeral = 0.0;
		Double subTotalServico = 0.0;
		Double subTotalTelefone = 0.0;
		Double subTotalDuracaoTelefone = 0.0;
		String branco = " ";
		String subTotalStr = "SubTotal";
		String subTotalServicoStrIni = "Total Ser";
		String subTotalServicoStrFim = "viço";
		Boolean impSubGrupo = Boolean.TRUE;
		Boolean impSubTotalTel = Boolean.TRUE;
		Integer tamanho = 17;
		Integer decimais = 2;
		Boolean somarDuracao  = Boolean.TRUE;
		Boolean somarValor  = Boolean.TRUE;

		int contLinhas = 0; 
		
		QuebraAux quebra = this.new QuebraAux();
		quebra.setQuebrouPagina(false);
		quebra.setTamLinha(0);
		
		DetalhamentoLigacaoParceiroDTO lastItem = null;
		
		int tamCabecalho = 0;
		int tamDetalhe = 0;
		
		for(DetalhamentoLigacaoParceiroDTO item : ligacoes ) {
			
			somarDuracao  = Boolean.TRUE;
			somarValor = Boolean.TRUE;
			
			if(servicoAnterior == null || !(servicoAnterior.equalsIgnoreCase(item.getServico()))){
				
				if ( servicoAnterior != null ) {
					
					Map mapSubTotalItensEBT = new HashMap();
					Map mapSubTotalServItensEBT = new  HashMap();
					
					if ( grupoAnterior != null ) { 
						
						// Verifica se deve ser gerado na mesma pagina ou na proxima
						// e soma tamanho da linha de acordo com o tipo do registro e formatacao
						quebra = this.verificarNovaPaginaNETEBT(setorDetItemEBT
								, lastItem
								, contLinhas
								, linhasPorPagina
								, 28
								);
						
						mapSubTotalItensEBT.put(PrintHouseConstants.EBT.DATAINICIOCOBRANCA, branco);
						mapSubTotalItensEBT.put(PrintHouseConstants.EBT.NUMEROTELEFONE, branco);
						mapSubTotalItensEBT.put(PrintHouseConstants.EBT.LOCALIDADEDESTINO, branco);
						mapSubTotalItensEBT.put(PrintHouseConstants.EBT.HORAINICIO, subTotalStr);
						mapSubTotalItensEBT.put(PrintHouseConstants.EBT.DURACAO, this.converteParaHorasOuBranco(totalDuracaoSubGrupo));
						mapSubTotalItensEBT.put(PrintHouseConstants.EBT.ESPACOEMBRANCO1, branco);
						mapSubTotalItensEBT.put(PrintHouseConstants.EBT.ESPACOEMBRANCO4, branco);
						mapSubTotalItensEBT.put(PrintHouseConstants.EBT.ITEMDETALHAMENTOVALOR, valor(totalSubGrupo,tamanho,decimais));
						mapSubTotalItensEBT.put(PrintHouseConstants.EBT.FORMATACAO, "13");
						setorDetItemEBT.addLinha(new LinhaDTO(LinhaDTO.TIPO_ITEN_EBT_DETALHAMENTO, mapSubTotalItensEBT, quebra.getTamLinha()));
						
						totalSubGrupo = 0.0;
						totalDuracaoSubGrupo = 0.0;
						impSubGrupo = Boolean.FALSE;

						contLinhas = quebra.getQuebrouPagina() ? quebra.getTamLinhaCabecalho() + quebra.getTamLinha() : contLinhas + quebra.getTamLinha();
						
					}

					if ( telefoneAnterior != null ) {

						// Verifica se deve ser gerado na mesma pagina ou na proxima
						// e soma tamanho da linha de acordo com o tipo do registro e formatacao
						quebra = this.verificarNovaPaginaNETEBT(setorDetItemEBT
								, lastItem
								, contLinhas
								, linhasPorPagina
								, 60
								);
						
						Map mapSubTotalTeefoneEBT = new HashMap();
						mapSubTotalTeefoneEBT.put(PrintHouseConstants.EBT.DATAINICIOCOBRANCA, branco);
						mapSubTotalTeefoneEBT.put(PrintHouseConstants.EBT.NUMEROTELEFONE, branco);
						mapSubTotalTeefoneEBT.put(PrintHouseConstants.EBT.LOCALIDADEDESTINO, branco);
						mapSubTotalTeefoneEBT.put(PrintHouseConstants.EBT.HORAINICIO, subTotalStr);
						mapSubTotalTeefoneEBT.put(PrintHouseConstants.EBT.DURACAO, this.converteParaHorasOuBranco(subTotalDuracaoTelefone));
						mapSubTotalTeefoneEBT.put(PrintHouseConstants.EBT.ESPACOEMBRANCO1, branco);
						mapSubTotalTeefoneEBT.put(PrintHouseConstants.EBT.ESPACOEMBRANCO4, branco);
						mapSubTotalTeefoneEBT.put(PrintHouseConstants.EBT.ITEMDETALHAMENTOVALOR, valor(subTotalTelefone,tamanho,decimais));
						mapSubTotalTeefoneEBT.put(PrintHouseConstants.EBT.FORMATACAO, "14");
						setorDetItemEBT.addLinha(new LinhaDTO(LinhaDTO.TIPO_ITEN_EBT_DETALHAMENTO, mapSubTotalTeefoneEBT, quebra.getTamLinha()));

						subTotalTelefone = 0.0;
						subTotalDuracaoTelefone = 0.0;
						impSubTotalTel = Boolean.FALSE;
						
						contLinhas = quebra.getQuebrouPagina() ? quebra.getTamLinhaCabecalho() + quebra.getTamLinha() : contLinhas + quebra.getTamLinha();
						
					}

					// Verifica se deve ser gerado na mesma pagina ou na proxima
					// e soma tamanho da linha de acordo com o tipo do registro e formatacao
					quebra = this.verificarNovaPaginaNETEBT(setorDetItemEBT
							, lastItem
							, contLinhas
							, linhasPorPagina
							, 70
							);
					
					mapSubTotalServItensEBT.put(PrintHouseConstants.EBT.NOMEAGRUPAMENTO, branco); 
					mapSubTotalServItensEBT.put(PrintHouseConstants.EBT.DATAINICIOCOBRANCA, "");
					mapSubTotalServItensEBT.put(PrintHouseConstants.EBT.NUMEROTELEFONE, branco);
					mapSubTotalServItensEBT.put(PrintHouseConstants.EBT.LOCALIDADEDESTINO, branco);
					mapSubTotalServItensEBT.put(PrintHouseConstants.EBT.HORAINICIO, subTotalServicoStrIni);
					mapSubTotalServItensEBT.put(PrintHouseConstants.EBT.ESPACOEMBRANCO4, subTotalServicoStrFim);
					mapSubTotalServItensEBT.put(PrintHouseConstants.EBT.ESPACOEMBRANCO1, branco);
					mapSubTotalServItensEBT.put(PrintHouseConstants.EBT.DURACAO, branco);
					mapSubTotalServItensEBT.put(PrintHouseConstants.EBT.ITEMDETALHAMENTOVALOR, valor(subTotalServico,tamanho,decimais));
					mapSubTotalServItensEBT.put(PrintHouseConstants.EBT.FORMATACAO, "15");
					setorDetItemEBT.addLinha(new LinhaDTO(LinhaDTO.TIPO_ITEN_EBT_DETALHAMENTO, mapSubTotalServItensEBT, quebra.getTamLinha()));

					subTotalServico = 0.0;
					
					contLinhas = quebra.getQuebrouPagina() ? quebra.getTamLinhaCabecalho() + quebra.getTamLinha() : contLinhas + quebra.getTamLinha();
					
					// Caso tenha quebrado página antes de gerar total do seviço, precisa gerar novamente os dados de cabeçalho 
					// para o novo serviço na nova página, pois o cabeçalho que foi gerado na nova página é do serviço anterior
					// seguido do total do serviço anterior.
					quebra.setQuebrouPagina(false);
					
				}

				// Se nao quebrou pagina, trata o novo servico.
				// Se ja quebrou nao e necessario, senao ira repetir o cabecalho
				if (!quebra.getQuebrouPagina()) {
					
					// Requer tratamento especial
					// Se o tamanho das linhas + o tamanho do cabecalho + o tamanho de um detalhe que vai imprimir 
					// estourar o numero de linhas permitido, insere linhas em branco ate quebrar pagina
					//if (contLinhas + tamCabecalho + tamDetalhe > linhasPorPagina) {
					if (contLinhas + TAM_CABECALHO_DET_EBT > linhasPorPagina ) {
						//Define o ultimo item com tamanho maximo, de forma a quebrar pagina
						int index = setorDetItemEBT.size();
						LinhaDTO auxLinha = setorDetItemEBT.get(index-1);
						int auxTamanho = auxLinha.getQuantidadeOcupa() + linhasPorPagina - contLinhas;
						auxLinha.setQuantidadeOcupa(auxTamanho);
						contLinhas +=auxTamanho; 
					} else {
					
						quebra.setTamLinha(TAM_CABECALHO_DET_EBT); //Cabecalho de titulo
						
						Map<String,String> mapServicosItensEBT = new HashMap<String,String>();
						mapServicosItensEBT.put(PrintHouseConstants.EBT.NOMEAGRUPAMENTO, item.getServico());
						mapServicosItensEBT.put(PrintHouseConstants.EBT.FORMATACAO, "12");				
						setorDetItemEBT.addLinha(new LinhaDTO(LinhaDTO.TIPO_ITEN_EBT_CABECALHO_AGRUPAMENTO, mapServicosItensEBT, quebra.getTamLinha()));
						
						contLinhas += quebra.getTamLinha();

						quebra.setTamLinha(0); //Esta linha nao pode ter quebra com a anterior
						
						Map mapNomeColuna1ServEBT = new HashMap();
						mapNomeColuna1ServEBT.put(PrintHouseConstants.EBT.DATAINICIOCOBRANCA, "PERIODO/DA");
						mapNomeColuna1ServEBT.put(PrintHouseConstants.EBT.ESPACOEMBRANCO1, "TA");
						mapNomeColuna1ServEBT.put(PrintHouseConstants.EBT.NUMEROTELEFONE, "TELEFONE");				 
						mapNomeColuna1ServEBT.put(PrintHouseConstants.EBT.LOCALIDADEDESTINO, "LOCAL");
						mapNomeColuna1ServEBT.put(PrintHouseConstants.EBT.HORAINICIO,"HORA");							
						mapNomeColuna1ServEBT.put(PrintHouseConstants.EBT.DURACAO, "DURACAO");			
						mapNomeColuna1ServEBT.put(PrintHouseConstants.EBT.ESPACOEMBRANCO4, branco);
						mapNomeColuna1ServEBT.put(PrintHouseConstants.EBT.ITEMDETALHAMENTOVALOR, "       VALOR (R$)");
						mapNomeColuna1ServEBT.put(PrintHouseConstants.EBT.FORMATACAO, "16");
						setorDetItemEBT.addLinha(new LinhaDTO(LinhaDTO.TIPO_ITEN_EBT_DETALHAMENTO, mapNomeColuna1ServEBT, quebra.getTamLinha()));

						contLinhas += quebra.getTamLinha();
						
						quebra.setTamLinha(0); //Esta linha nao pode ter quebra com a anterior
						
						Map mapNomeColuna2ServEBT = new HashMap();
						mapNomeColuna2ServEBT.put(PrintHouseConstants.EBT.DATAINICIOCOBRANCA, branco);				
						mapNomeColuna2ServEBT.put(PrintHouseConstants.EBT.NUMEROTELEFONE, "DESTINO");				 
						mapNomeColuna2ServEBT.put(PrintHouseConstants.EBT.LOCALIDADEDESTINO, "DESTINO");
						mapNomeColuna2ServEBT.put(PrintHouseConstants.EBT.HORAINICIO, "INICIO");					
						mapNomeColuna2ServEBT.put(PrintHouseConstants.EBT.DURACAO, branco);
						mapNomeColuna2ServEBT.put(PrintHouseConstants.EBT.ESPACOEMBRANCO1, branco);
						mapNomeColuna2ServEBT.put(PrintHouseConstants.EBT.ESPACOEMBRANCO4, branco);
						mapNomeColuna2ServEBT.put(PrintHouseConstants.EBT.ITEMDETALHAMENTOVALOR, branco );
						mapNomeColuna2ServEBT.put(PrintHouseConstants.EBT.FORMATACAO, "16");
						setorDetItemEBT.addLinha(new LinhaDTO(LinhaDTO.TIPO_ITEN_EBT_DETALHAMENTO, mapNomeColuna2ServEBT, quebra.getTamLinha()));

						contLinhas += quebra.getTamLinha();
						
						// força gerar linha de cabeçalho de telefone e grupo (Atual)
						telefoneAnterior = null;
						grupoAnterior = null;

					}
				}
				
			}
			
			if(telefoneAnterior == null || !(telefoneAnterior.equalsIgnoreCase(item.getTelefoneOrigem()))){			
				
				//if ( grupoAnterior != null && !(grupoAnterior.equalsIgnoreCase(item.getDescItem()))  && impSubGrupo ){				
				if ( grupoAnterior != null  && impSubGrupo ){
						
					// Verifica se deve ser gerado na mesma pagina ou na proxima
					// e soma tamanho da linha de acordo com o tipo do registro e formatacao
					quebra = this.verificarNovaPaginaNETEBT(setorDetItemEBT
							, lastItem
							, contLinhas
							, linhasPorPagina
							, 28
							);
					
					Map mapSubTotalItensEBT = new HashMap();
					mapSubTotalItensEBT.put(PrintHouseConstants.EBT.DATAINICIOCOBRANCA, branco);					
					mapSubTotalItensEBT.put(PrintHouseConstants.EBT.NUMEROTELEFONE, branco);					 
					mapSubTotalItensEBT.put(PrintHouseConstants.EBT.LOCALIDADEDESTINO, branco);
					mapSubTotalItensEBT.put(PrintHouseConstants.EBT.HORAINICIO, subTotalStr);
					mapSubTotalItensEBT.put(PrintHouseConstants.EBT.DURACAO, this.converteParaHorasOuBranco(totalDuracaoSubGrupo));
					mapSubTotalItensEBT.put(PrintHouseConstants.EBT.ESPACOEMBRANCO1, branco);
					mapSubTotalItensEBT.put(PrintHouseConstants.EBT.ESPACOEMBRANCO4, branco);
					mapSubTotalItensEBT.put(PrintHouseConstants.EBT.ITEMDETALHAMENTOVALOR, valor(totalSubGrupo,tamanho,decimais));
					mapSubTotalItensEBT.put(PrintHouseConstants.EBT.FORMATACAO, "13");
					setorDetItemEBT.addLinha(new LinhaDTO(LinhaDTO.TIPO_ITEN_EBT_DETALHAMENTO, mapSubTotalItensEBT, quebra.getTamLinha()));

					totalSubGrupo = 0.0;
					totalDuracaoSubGrupo = 0.0;
					impSubGrupo = Boolean.FALSE;
					
					contLinhas = quebra.getQuebrouPagina() ? quebra.getTamLinhaCabecalho() + quebra.getTamLinha() : contLinhas + quebra.getTamLinha();

				}
				
				if ( telefoneAnterior != null && impSubTotalTel) {
					
					// Verifica se deve ser gerado na mesma pagina ou na proxima
					// e soma tamanho da linha de acordo com o tipo do registro e formatacao
					quebra = this.verificarNovaPaginaNETEBT(setorDetItemEBT
							, lastItem
							, contLinhas
							, linhasPorPagina
							, 60
							);
					
					Map mapSubTotalItensEBT = new HashMap();
					mapSubTotalItensEBT.put(PrintHouseConstants.EBT.DATAINICIOCOBRANCA, branco);
					mapSubTotalItensEBT.put(PrintHouseConstants.EBT.NUMEROTELEFONE, branco);
					mapSubTotalItensEBT.put(PrintHouseConstants.EBT.LOCALIDADEDESTINO, branco);
					mapSubTotalItensEBT.put(PrintHouseConstants.EBT.HORAINICIO, subTotalStr);
					mapSubTotalItensEBT.put(PrintHouseConstants.EBT.DURACAO, this.converteParaHorasOuBranco(subTotalDuracaoTelefone));
					mapSubTotalItensEBT.put(PrintHouseConstants.EBT.ESPACOEMBRANCO1, branco);
					mapSubTotalItensEBT.put(PrintHouseConstants.EBT.ESPACOEMBRANCO4, branco);
					mapSubTotalItensEBT.put(PrintHouseConstants.EBT.ITEMDETALHAMENTOVALOR, valor(subTotalTelefone,tamanho,decimais));
					mapSubTotalItensEBT.put(PrintHouseConstants.EBT.FORMATACAO, "14");
					setorDetItemEBT.addLinha(new LinhaDTO(LinhaDTO.TIPO_ITEN_EBT_DETALHAMENTO, mapSubTotalItensEBT, quebra.getTamLinha()));
					
					subTotalTelefone = 0.0;
					subTotalDuracaoTelefone = 0.0;
					
					contLinhas = quebra.getQuebrouPagina() ? quebra.getTamLinhaCabecalho() + quebra.getTamLinha() : contLinhas + quebra.getTamLinha();

				}

				int tamAgrupamento17 = 50; //5mm

				// Requer tratamento especial
				// Se o tamanho de linhas + o tamanho do agrupamento + o tamanho de um detalhe que vai imprimir 
				// estourar o numero de linhas permitido, insere linhas em branco ate quebrar pagina
				if (contLinhas + tamAgrupamento17 + tamDetalhe > linhasPorPagina) {
					//nada
				} else {

					Map<String,String> mapTelefoneItensEBT = new HashMap<String,String>();
					mapTelefoneItensEBT.put(PrintHouseConstants.EBT.NOMEAGRUPAMENTO, "Telefone: "+item.getTelefoneOrigem());
					mapTelefoneItensEBT.put(PrintHouseConstants.EBT.FORMATACAO, "17");
					setorDetItemEBT.addLinha(new LinhaDTO(LinhaDTO.TIPO_ITEN_EBT_CABECALHO_AGRUPAMENTO, mapTelefoneItensEBT, tamAgrupamento17));

					contLinhas += tamAgrupamento17;
					
				}
				
			}
			
			if ( grupoAnterior == null || !(grupoAnterior.equalsIgnoreCase(item.getDescItem()))){				
				
				if ( grupoAnterior != null && impSubGrupo) {
					
					// Verifica se deve ser gerado na mesma pagina ou na proxima
					// e soma tamanho da linha de acordo com o tipo do registro e formatacao
					quebra = this.verificarNovaPaginaNETEBT(setorDetItemEBT
							, lastItem
							, contLinhas
							, linhasPorPagina
							, 28
							);
					
					Map mapSubTotalItensEBT = new HashMap();
					mapSubTotalItensEBT.put(PrintHouseConstants.EBT.DATAINICIOCOBRANCA, branco);
					mapSubTotalItensEBT.put(PrintHouseConstants.EBT.NUMEROTELEFONE, branco);
					mapSubTotalItensEBT.put(PrintHouseConstants.EBT.LOCALIDADEDESTINO, branco);
					mapSubTotalItensEBT.put(PrintHouseConstants.EBT.HORAINICIO, subTotalStr);
					mapSubTotalItensEBT.put(PrintHouseConstants.EBT.DURACAO, this.converteParaHorasOuBranco(totalDuracaoSubGrupo));
					mapSubTotalItensEBT.put(PrintHouseConstants.EBT.ESPACOEMBRANCO1, branco);
					mapSubTotalItensEBT.put(PrintHouseConstants.EBT.ESPACOEMBRANCO4, branco);
					mapSubTotalItensEBT.put(PrintHouseConstants.EBT.ITEMDETALHAMENTOVALOR, valor(totalSubGrupo,tamanho,decimais));
					mapSubTotalItensEBT.put(PrintHouseConstants.EBT.FORMATACAO, "13");
					setorDetItemEBT.addLinha(new LinhaDTO(LinhaDTO.TIPO_ITEN_EBT_DETALHAMENTO, mapSubTotalItensEBT, quebra.getTamLinha()));

					totalSubGrupo = 0.0;
					totalDuracaoSubGrupo = 0.0;
					
					contLinhas = quebra.getQuebrouPagina() ? quebra.getTamLinhaCabecalho() + quebra.getTamLinha() : contLinhas + quebra.getTamLinha();

				}

				int tamAgrupamento11 = 40;

				// Requer tratamento especial
				// Se o tamanho de linhas + o tamanho do agrupamento + o tamanho de um detalhe que vai imprimir 
				// estourar o numero de linhas permitido, insere linhas em branco ate quebrar pagina
				if ( contLinhas + tamAgrupamento11 + quebra.getTamLinha() > linhasPorPagina) {
					//nada
				} else {

					Map<String,String> mapGrupoItensEBT = new HashMap<String,String>();
					mapGrupoItensEBT.put(PrintHouseConstants.EBT.NOMEAGRUPAMENTO, item.getDescItem());	
					mapGrupoItensEBT.put(PrintHouseConstants.EBT.FORMATACAO, "11");
					setorDetItemEBT.addLinha(new LinhaDTO(LinhaDTO.TIPO_ITEN_EBT_CABECALHO_AGRUPAMENTO, mapGrupoItensEBT, tamAgrupamento11));
					
					contLinhas += tamAgrupamento11;
					
				}
				
			}

			Map mapDetalhesLigacoesEBT = new HashMap();

			String dtInicio = null;
			String dtFim = null;
			
			if (item.getDataInicio() != null) {
				dtInicio = dateFormat.format(new Date());
			}
			
			if (item.getDataFim() != null) {
				dtFim = dateFormat.format(new Date());
			}
			
			
			// Verifica se deve ser gerado na mesma pagina ou na proxima
			// e soma tamanho da linha de acordo com o tipo do registro e formatacao
			quebra = this.verificarNovaPaginaNETEBT(setorDetItemEBT
					, item
					, contLinhas
					, linhasPorPagina
					, 28
					);
			
			if (item.getIdTipoItem() == 1 || item.getIdTipoItem() == 9 || item.getIdTipoItem() == 12 || item.getIdTipoItem() == 13){

				// tipo data inicio e valor 
				mapDetalhesLigacoesEBT.put(PrintHouseConstants.EBT.DATAINICIOCOBRANCA, item.getDataInicio() == null ? " " : dateFormat.format(item.getDataInicio()));			
				mapDetalhesLigacoesEBT.put(PrintHouseConstants.EBT.NUMEROTELEFONE, branco);			
				mapDetalhesLigacoesEBT.put(PrintHouseConstants.EBT.LOCALIDADEDESTINO, branco);
				mapDetalhesLigacoesEBT.put(PrintHouseConstants.EBT.HORAINICIO, branco);					
				mapDetalhesLigacoesEBT.put(PrintHouseConstants.EBT.DURACAO, branco);					
				mapDetalhesLigacoesEBT.put(PrintHouseConstants.EBT.ITEMDETALHAMENTOVALOR, valor(item.getValorItem(),tamanho,decimais));
				mapDetalhesLigacoesEBT.put(PrintHouseConstants.EBT.FORMATACAO, "16");
				mapDetalhesLigacoesEBT.put(PrintHouseConstants.EBT.ESPACOEMBRANCO1, branco);
				mapDetalhesLigacoesEBT.put(PrintHouseConstants.EBT.ESPACOEMBRANCO4, branco);
				setorDetItemEBT.addLinha(new LinhaDTO(LinhaDTO.TIPO_ITEN_EBT_DETALHAMENTO, mapDetalhesLigacoesEBT, quebra.getTamLinha()));
				somarDuracao = Boolean.FALSE;
				
			} else if ( item.getIdTipoItem() == 14 || item.getIdTipoItem() == 10 ){

				// tipo data inicio e data fim e valor 
				mapDetalhesLigacoesEBT.put(PrintHouseConstants.EBT.DATAINICIOCOBRANCA, item.getDataInicio() == null ? " " : dateFormat.format(item.getDataInicio()));			
				mapDetalhesLigacoesEBT.put(PrintHouseConstants.EBT.ESPACOEMBRANCO1, "  A  " );			
				mapDetalhesLigacoesEBT.put(PrintHouseConstants.EBT.NUMEROTELEFONE, item.getDataFim()== null ? " " : dateFormat.format(item.getDataFim()));
				mapDetalhesLigacoesEBT.put(PrintHouseConstants.EBT.LOCALIDADEDESTINO, branco);
				mapDetalhesLigacoesEBT.put(PrintHouseConstants.EBT.HORAINICIO, branco);					
				mapDetalhesLigacoesEBT.put(PrintHouseConstants.EBT.DURACAO, branco );					
				mapDetalhesLigacoesEBT.put(PrintHouseConstants.EBT.ITEMDETALHAMENTOVALOR, valor(item.getValorItem(),tamanho,decimais));
				mapDetalhesLigacoesEBT.put(PrintHouseConstants.EBT.FORMATACAO, "16");					
				mapDetalhesLigacoesEBT.put(PrintHouseConstants.EBT.ESPACOEMBRANCO4, branco);
				setorDetItemEBT.addLinha(new LinhaDTO(LinhaDTO.TIPO_ITEN_EBT_DETALHAMENTO, mapDetalhesLigacoesEBT, quebra.getTamLinha()));
				somarDuracao = Boolean.FALSE;
				
			} else if ( item.getIdTipoItem() == 15 ) {
				
				// tipo data inicio e fim , duracao e valor
				mapDetalhesLigacoesEBT.put(PrintHouseConstants.EBT.DATAINICIOCOBRANCA, item.getDataInicio() == null ? " " : dateFormat.format(item.getDataInicio()));			
				mapDetalhesLigacoesEBT.put(PrintHouseConstants.EBT.ESPACOEMBRANCO1, "  A  ");			
				mapDetalhesLigacoesEBT.put(PrintHouseConstants.EBT.NUMEROTELEFONE, item.getDataFim()== null ? " " : dateFormat.format(item.getDataFim()));
				mapDetalhesLigacoesEBT.put(PrintHouseConstants.EBT.LOCALIDADEDESTINO, branco);
				mapDetalhesLigacoesEBT.put(PrintHouseConstants.EBT.HORAINICIO,branco);					
				mapDetalhesLigacoesEBT.put(PrintHouseConstants.EBT.DURACAO, item.getDuracaoTotal() );					
				mapDetalhesLigacoesEBT.put(PrintHouseConstants.EBT.ITEMDETALHAMENTOVALOR, valor(item.getValorItem(),tamanho,decimais));
				mapDetalhesLigacoesEBT.put(PrintHouseConstants.EBT.FORMATACAO, "16");					
				mapDetalhesLigacoesEBT.put(PrintHouseConstants.EBT.ESPACOEMBRANCO4, branco);
				setorDetItemEBT.addLinha(new LinhaDTO(LinhaDTO.TIPO_ITEN_EBT_DETALHAMENTO, mapDetalhesLigacoesEBT, quebra.getTamLinha()));
				somarDuracao = Boolean.FALSE;	
				
			} else if ( item.getIdTipoItem() == 11 ) {

				// tipo data inicio . telefone e  valor
				mapDetalhesLigacoesEBT.put(PrintHouseConstants.EBT.DATAINICIOCOBRANCA, item.getDataInicio() == null ? " " : dateFormat.format(item.getDataInicio()));			
				mapDetalhesLigacoesEBT.put(PrintHouseConstants.EBT.NUMEROTELEFONE, item.getTelefoneDestino());			
				mapDetalhesLigacoesEBT.put(PrintHouseConstants.EBT.LOCALIDADEDESTINO, branco);
				mapDetalhesLigacoesEBT.put(PrintHouseConstants.EBT.HORAINICIO,branco);					
				mapDetalhesLigacoesEBT.put(PrintHouseConstants.EBT.DURACAO, branco);					
				mapDetalhesLigacoesEBT.put(PrintHouseConstants.EBT.ITEMDETALHAMENTOVALOR, valor(item.getValorItem(),tamanho,decimais));
				mapDetalhesLigacoesEBT.put(PrintHouseConstants.EBT.FORMATACAO, "16");
				mapDetalhesLigacoesEBT.put(PrintHouseConstants.EBT.ESPACOEMBRANCO1, branco);
				mapDetalhesLigacoesEBT.put(PrintHouseConstants.EBT.ESPACOEMBRANCO4, branco);
				setorDetItemEBT.addLinha(new LinhaDTO(LinhaDTO.TIPO_ITEN_EBT_DETALHAMENTO, mapDetalhesLigacoesEBT, quebra.getTamLinha()));
				somarDuracao = Boolean.FALSE;
				
			} else {
				
				// item normal de ligacao 
				mapDetalhesLigacoesEBT.put(PrintHouseConstants.EBT.DATAINICIOCOBRANCA, item.getDataInicio() == null ? " " : dateFormat.format(item.getDataInicio()));			
				mapDetalhesLigacoesEBT.put(PrintHouseConstants.EBT.NUMEROTELEFONE, item.getTelefoneDestino());			
				mapDetalhesLigacoesEBT.put(PrintHouseConstants.EBT.LOCALIDADEDESTINO, item.getLocalidadeDestino());
				mapDetalhesLigacoesEBT.put(PrintHouseConstants.EBT.HORAINICIO, item.getHoraInicio());					
				mapDetalhesLigacoesEBT.put(PrintHouseConstants.EBT.DURACAO, item.getDuracao() );					
				mapDetalhesLigacoesEBT.put(PrintHouseConstants.EBT.ITEMDETALHAMENTOVALOR, valor(item.getValorItem(),tamanho,decimais));
				mapDetalhesLigacoesEBT.put(PrintHouseConstants.EBT.FORMATACAO, "16");
				mapDetalhesLigacoesEBT.put(PrintHouseConstants.EBT.ESPACOEMBRANCO1, branco);
				mapDetalhesLigacoesEBT.put(PrintHouseConstants.EBT.ESPACOEMBRANCO4, branco);
				setorDetItemEBT.addLinha(new LinhaDTO(LinhaDTO.TIPO_ITEN_EBT_DETALHAMENTO, mapDetalhesLigacoesEBT, quebra.getTamLinha()));
				somarDuracao = Boolean.TRUE;
				
			}
			
			contLinhas = quebra.getQuebrouPagina() ? quebra.getTamLinhaCabecalho() + quebra.getTamLinha() : contLinhas + quebra.getTamLinha();

			if( item.getValorItem() != null){
				totalSubGrupo = totalSubGrupo + item.getValorItem();
				totalGeral = totalGeral + item.getValorItem();					
				subTotalServico = subTotalServico + item.getValorItem();
				if(	somarValor ){
					subTotalTelefone = subTotalTelefone + item.getValorItem();					
				}
			}
			
			if( item.getDuracaoTotal() != null){
				
				if(	somarDuracao ){
					totalDuracaoSubGrupo = totalDuracaoSubGrupo + item.getDuracaoTotal();
					subTotalDuracaoTelefone = subTotalDuracaoTelefone + item.getDuracaoTotal();
					
					// Arredondamento necessario
					totalDuracaoSubGrupo = Math.floor((totalDuracaoSubGrupo * 100.0) + 0.5) / 100.0;
					subTotalDuracaoTelefone = Math.floor((subTotalDuracaoTelefone * 100.0) + 0.5) / 100.0;
					
				}
			}						
			
			grupoAnterior = item.getDescItem();
			servicoAnterior = item.getServico();							
			telefoneAnterior = item.getTelefoneOrigem();
										
			impSubGrupo = Boolean.TRUE;
			impSubTotalTel = Boolean.TRUE;
						
			// Guarda ultimo para a quebra de pagina no sub-total final
			lastItem = item;
			
		}
		
		// Verifica se deve ser gerado na mesma pagina ou na proxima
		// e soma tamanho da linha de acordo com o tipo do registro e formatacao
		quebra = this.verificarNovaPaginaNETEBT(setorDetItemEBT
				, lastItem
				, contLinhas
				, linhasPorPagina
				, 28
				);
		
		Map mapSubTotalFinalItensEBT = new HashMap();
		mapSubTotalFinalItensEBT.put(PrintHouseConstants.EBT.DATAINICIOCOBRANCA, branco);
		mapSubTotalFinalItensEBT.put(PrintHouseConstants.EBT.NUMEROTELEFONE, branco);
		mapSubTotalFinalItensEBT.put(PrintHouseConstants.EBT.LOCALIDADEDESTINO, branco);
		mapSubTotalFinalItensEBT.put(PrintHouseConstants.EBT.HORAINICIO, subTotalStr);
		mapSubTotalFinalItensEBT.put(PrintHouseConstants.EBT.DURACAO, this.converteParaHorasOuBranco(totalDuracaoSubGrupo));	
		mapSubTotalFinalItensEBT.put(PrintHouseConstants.EBT.ITEMDETALHAMENTOVALOR, valor(totalSubGrupo,tamanho,decimais));
		mapSubTotalFinalItensEBT.put(PrintHouseConstants.EBT.FORMATACAO, "13");
		mapSubTotalFinalItensEBT.put(PrintHouseConstants.EBT.ESPACOEMBRANCO1, branco);
		mapSubTotalFinalItensEBT.put(PrintHouseConstants.EBT.ESPACOEMBRANCO4, branco);
		setorDetItemEBT.addLinha(new LinhaDTO(LinhaDTO.TIPO_ITEN_EBT_DETALHAMENTO, mapSubTotalFinalItensEBT, quebra.getTamLinha()));

		contLinhas = quebra.getQuebrouPagina() ? quebra.getTamLinhaCabecalho() + quebra.getTamLinha() : contLinhas + quebra.getTamLinha();

		// Verifica se deve ser gerado na mesma pagina ou na proxima
		// e soma tamanho da linha de acordo com o tipo do registro e formatacao
		quebra = this.verificarNovaPaginaNETEBT(setorDetItemEBT
				, lastItem
				, contLinhas
				, linhasPorPagina
				, 70
				);
		
		Map mapSubTotalTelefoneItensEBT = new HashMap();
		mapSubTotalTelefoneItensEBT.put(PrintHouseConstants.EBT.DATAINICIOCOBRANCA, branco);
		mapSubTotalTelefoneItensEBT.put(PrintHouseConstants.EBT.NUMEROTELEFONE, branco);
		mapSubTotalTelefoneItensEBT.put(PrintHouseConstants.EBT.LOCALIDADEDESTINO, branco);
		mapSubTotalTelefoneItensEBT.put(PrintHouseConstants.EBT.HORAINICIO, subTotalStr);
		mapSubTotalTelefoneItensEBT.put(PrintHouseConstants.EBT.DURACAO, this.converteParaHorasOuBranco(subTotalDuracaoTelefone));
		mapSubTotalTelefoneItensEBT.put(PrintHouseConstants.EBT.ITEMDETALHAMENTOVALOR, valor(subTotalTelefone,tamanho,decimais));
		mapSubTotalTelefoneItensEBT.put(PrintHouseConstants.EBT.FORMATACAO, "15");
		mapSubTotalTelefoneItensEBT.put(PrintHouseConstants.EBT.ESPACOEMBRANCO1, branco);
		mapSubTotalTelefoneItensEBT.put(PrintHouseConstants.EBT.ESPACOEMBRANCO4, branco);
		setorDetItemEBT.addLinha(new LinhaDTO(LinhaDTO.TIPO_ITEN_EBT_DETALHAMENTO, mapSubTotalTelefoneItensEBT, quebra.getTamLinha()));
					
		contLinhas = quebra.getQuebrouPagina() ? quebra.getTamLinhaCabecalho() + quebra.getTamLinha() : contLinhas + quebra.getTamLinha();

		// Verifica se deve ser gerado na mesma pagina ou na proxima
		// e soma tamanho da linha de acordo com o tipo do registro e formatacao
		quebra = this.verificarNovaPaginaNETEBT(setorDetItemEBT
				, lastItem
				, contLinhas
				, linhasPorPagina
				, 70
				);
		
		Map mapTotalItensEBT = new HashMap();
		mapTotalItensEBT.put(PrintHouseConstants.EBT.DATAINICIOCOBRANCA, branco);
		mapTotalItensEBT.put(PrintHouseConstants.EBT.NUMEROTELEFONE, branco);
		mapTotalItensEBT.put(PrintHouseConstants.EBT.LOCALIDADEDESTINO, branco);
		mapTotalItensEBT.put(PrintHouseConstants.EBT.HORAINICIO, subTotalServicoStrIni);						
		mapTotalItensEBT.put(PrintHouseConstants.EBT.DURACAO, branco);
		mapTotalItensEBT.put(PrintHouseConstants.EBT.ESPACOEMBRANCO4, subTotalServicoStrFim);
		mapTotalItensEBT.put(PrintHouseConstants.EBT.ITEMDETALHAMENTOVALOR, valor(totalGeral,tamanho,decimais));
		mapTotalItensEBT.put(PrintHouseConstants.EBT.FORMATACAO, "15");
		mapTotalItensEBT.put(PrintHouseConstants.EBT.ESPACOEMBRANCO1, branco);		
		setorDetItemEBT.addLinha(new LinhaDTO(LinhaDTO.TIPO_ITEN_EBT_DETALHAMENTO, mapTotalItensEBT, quebra.getTamLinha()));
		
		contLinhas = quebra.getQuebrouPagina() ? quebra.getTamLinhaCabecalho() + quebra.getTamLinha() : contLinhas + quebra.getTamLinha();

		setorDetItemEBT.addLinha(new LinhaDTO(LinhaDTO.TIPO_ITEN_EBT_RODAPE_AGRUPAMENTO, new HashMap<String, String>(),0));
		
		Map mapPlanoServicoEBT;
		try{
			if(fatura.getPlanoServicoEbt()!=null){
				for(String planos : fatura.getPlanoServicoEbt()){
					String[] plano = planos.split(";");
					StringBuffer serv = new StringBuffer();
					
					serv.append(plano[0]);
					serv.append(" ");
					serv.append(this.getMessage("netsms.print.fatura.ebt.ligacoes"));
					serv.append(plano[1]);
					serv.append(" ");
					serv.append(this.getMessage("netsms.print.fatura.ebt.lc.ddd"));
					serv.append(plano[2]);
					serv.append(" ");
					serv.append(this.getMessage("netsms.print.fatura.ebt.ld.ddi"));
					serv.append(plano[3]);
					serv.append(" ");
					serv.append(this.getMessage("netsms.print.fatura.ebt.ld"));
					
					mapPlanoServicoEBT = new HashMap();
					mapPlanoServicoEBT.put(PrintHouseConstants.EBT.PLANOSERVICO,serv.toString());
					setorDetItemEBT.addLinha(new LinhaDTO(LinhaDTO.TIPO_PLANO_SERVICO_EBT, mapPlanoServicoEBT,150));
				}
			}
		}catch(Exception e){
			logger.error("Erro ao adicionar linha de plano de servico EBT", e);
		}
		
		setorDetItemEBT.addLinha(new LinhaDTO(LinhaDTO.TIPO_ITEN_FIM_PTOD_EBT,new HashMap<String, String>(),0));
		
	}
	
	private String converteParaHorasOuBranco(Double valor) {
		
		String branco = " ";
		
		if (valor == 0.0) {
			return branco;
		}
		
		return this.converteParaHoras(valor);
		
	}
	
	private void addLinhaBrancoDetalheEBT(SetorDTO setorDetItemEBT) {
		
		QuebraAux quebra = this.new QuebraAux();
		
		String branco = " ";
		int tamLinha = 28;
		
		Map mapBrancoEBT = new HashMap();
		mapBrancoEBT.put(PrintHouseConstants.EBT.DATAINICIOCOBRANCA, branco);
		mapBrancoEBT.put(PrintHouseConstants.EBT.ESPACOEMBRANCO1, branco);
		mapBrancoEBT.put(PrintHouseConstants.EBT.NUMEROTELEFONE, branco);				 
		mapBrancoEBT.put(PrintHouseConstants.EBT.LOCALIDADEDESTINO, branco);
		mapBrancoEBT.put(PrintHouseConstants.EBT.HORAINICIO, branco);							
		mapBrancoEBT.put(PrintHouseConstants.EBT.DURACAO, branco);			
		mapBrancoEBT.put(PrintHouseConstants.EBT.ESPACOEMBRANCO4, branco);
		mapBrancoEBT.put(PrintHouseConstants.EBT.ITEMDETALHAMENTOVALOR, branco);
		mapBrancoEBT.put(PrintHouseConstants.EBT.FORMATACAO, branco);
		setorDetItemEBT.addLinha(new LinhaDTO(LinhaDTO.TIPO_ITEN_EBT_DETALHAMENTO, mapBrancoEBT, tamLinha));

	}
	
	private QuebraAux verificarNovaPaginaNETEBT (SetorDTO setorDetItemEBT
			, DetalhamentoLigacaoParceiroDTO item
			, int qtdLinha
			, int qtdLinhaPorPagina
			, int tamLinha
			) {
		
		String branco = " ";
		
		QuebraAux quebra = this.new QuebraAux();
		quebra.setQuebrouPagina(false);

		quebra.setTamLinha(tamLinha);

		if (qtdLinha + tamLinha > qtdLinhaPorPagina) {

			tamLinha = TAM_CABECALHO_DET_EBT;
			
			Map<String,String> mapServicosItensEBT = new HashMap<String,String>();
			mapServicosItensEBT.put(PrintHouseConstants.EBT.NOMEAGRUPAMENTO, item.getServico());
			mapServicosItensEBT.put(PrintHouseConstants.EBT.FORMATACAO, "12");				
			setorDetItemEBT.addLinha(new LinhaDTO(LinhaDTO.TIPO_ITEN_EBT_CABECALHO_AGRUPAMENTO, mapServicosItensEBT, tamLinha));

			quebra.setTamLinhaCabecalho(tamLinha);
			
			tamLinha = 0;

			Map mapNomeColuna1ServEBT = new HashMap();
			mapNomeColuna1ServEBT.put(PrintHouseConstants.EBT.DATAINICIOCOBRANCA, "PERIODO/DA");
			mapNomeColuna1ServEBT.put(PrintHouseConstants.EBT.ESPACOEMBRANCO1, "TA");
			mapNomeColuna1ServEBT.put(PrintHouseConstants.EBT.NUMEROTELEFONE, "TELEFONE");				 
			mapNomeColuna1ServEBT.put(PrintHouseConstants.EBT.LOCALIDADEDESTINO, "LOCAL");
			mapNomeColuna1ServEBT.put(PrintHouseConstants.EBT.HORAINICIO,"HORA");							
			mapNomeColuna1ServEBT.put(PrintHouseConstants.EBT.DURACAO, "DURACAO");			
			mapNomeColuna1ServEBT.put(PrintHouseConstants.EBT.ESPACOEMBRANCO4, branco);
			mapNomeColuna1ServEBT.put(PrintHouseConstants.EBT.ITEMDETALHAMENTOVALOR, "       VALOR (R$)");
			mapNomeColuna1ServEBT.put(PrintHouseConstants.EBT.FORMATACAO, "16");
			setorDetItemEBT.addLinha(new LinhaDTO(LinhaDTO.TIPO_ITEN_EBT_DETALHAMENTO, mapNomeColuna1ServEBT, tamLinha));
			
			quebra.addTamLinhaCabecalho(tamLinha);

			tamLinha = 0;
			
			Map mapNomeColuna2ServEBT = new HashMap();
			mapNomeColuna2ServEBT.put(PrintHouseConstants.EBT.DATAINICIOCOBRANCA, branco);				
			mapNomeColuna2ServEBT.put(PrintHouseConstants.EBT.NUMEROTELEFONE, "DESTINO");				 
			mapNomeColuna2ServEBT.put(PrintHouseConstants.EBT.LOCALIDADEDESTINO, "DESTINO");
			mapNomeColuna2ServEBT.put(PrintHouseConstants.EBT.HORAINICIO, "INICIO");					
			mapNomeColuna2ServEBT.put(PrintHouseConstants.EBT.DURACAO, branco);
			mapNomeColuna2ServEBT.put(PrintHouseConstants.EBT.ESPACOEMBRANCO1, branco);
			mapNomeColuna2ServEBT.put(PrintHouseConstants.EBT.ESPACOEMBRANCO4, branco);
			mapNomeColuna2ServEBT.put(PrintHouseConstants.EBT.ITEMDETALHAMENTOVALOR, branco );
			mapNomeColuna2ServEBT.put(PrintHouseConstants.EBT.FORMATACAO, "16");
			setorDetItemEBT.addLinha(new LinhaDTO(LinhaDTO.TIPO_ITEN_EBT_DETALHAMENTO, mapNomeColuna2ServEBT, tamLinha));

			quebra.addTamLinhaCabecalho(tamLinha);
			
			tamLinha = 50;
			
			Map<String,String> mapTelefoneItensEBT = new HashMap<String,String>();
			mapTelefoneItensEBT.put(PrintHouseConstants.EBT.NOMEAGRUPAMENTO, "Telefone: "+item.getTelefoneOrigem());
			mapTelefoneItensEBT.put(PrintHouseConstants.EBT.FORMATACAO, "17");
			setorDetItemEBT.addLinha(new LinhaDTO(LinhaDTO.TIPO_ITEN_EBT_CABECALHO_AGRUPAMENTO, mapTelefoneItensEBT, tamLinha));
			
			quebra.addTamLinhaCabecalho(tamLinha);
			
			tamLinha = 40;
			
			Map<String,String> mapGrupoItensEBT = new HashMap<String,String>();
			mapGrupoItensEBT.put(PrintHouseConstants.EBT.NOMEAGRUPAMENTO, item.getDescItem());	
			mapGrupoItensEBT.put(PrintHouseConstants.EBT.FORMATACAO, "11");
			setorDetItemEBT.addLinha(new LinhaDTO(LinhaDTO.TIPO_ITEN_EBT_CABECALHO_AGRUPAMENTO, mapGrupoItensEBT, tamLinha));

			quebra.addTamLinhaCabecalho(tamLinha);
			
			quebra.setQuebrouPagina(true);
			
		}
		
		return quebra;
		
	}
	
	private void gerarSetorNotaFiscalEBT(SetorListaDTO setorLista
			, DadosGeraisPrimeiraViaPrintDTO dadosGeraisPrimeiraViaPrintDTO
			, String cidContrato
			, Long parceiro
			, FaturaNetDTO fatura
			, List<MensagemAnatelDTO> listMensagemAnatelDTO) {
			
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		
		Long idBoleto = fatura.getIdBoleto();
		Double vlrTotalNF = dadosGeraisPrimeiraViaPrintDTO.getValorTotalNotaFiscal();
		
		DemonstrativoFinanceiroPrintDTO demonstrativoFinanceiroPrintDTO = dadosGeraisPrimeiraViaPrintDTO;
		FichaArrecadacaoPrintDTO fichaArrecadacaoPrintDTO = dadosGeraisPrimeiraViaPrintDTO;
		
		String boletoConsolidado = fatura.getFcBoletoConsolidado();
		
		// 31 Dados do assinante
		DynamicBean param = new DynamicBean();
		param.set("idBoleto",idBoleto );
		param.set("idParceiro",parceiro );
		//List<DadosCobrancaParceiroDTO> notasParceiros = getDadosBeanByQuery("listarTituloNotaFiscalOperadoraPorParceiro", param, false, DadosCobrancaParceiroDTO.class);
		List<DadosCobrancaParceiroDTO> notasParceiros = fatura.getDadosNotaFiscalParceiro(parceiro);
		
		//Notas Fiscais Embratel
		SetorDTO setorNFItemEBT = null;		
		boolean isCriarSetor = true;
		int qtdNota = 0;
		int tamanho;
		for(DadosCobrancaParceiroDTO cobranca : notasParceiros){				
			
			// Carregar registro 38.						 
			SnParceiroBean nomeParc = new SnParceiroBean(); 
			nomeParc.setIdParceiro(cobranca.getIdParceiro());  
			SnParceiroBean parceiroBean = (SnParceiroBean) find(nomeParc);			
			
			if (parceiroBean.getSnTipoParceiroBean().getIdTipoParceiro() == 3) {
				
				qtdNota++;
				isCriarSetor = (qtdNota % 2) > 0;
				
			} else {
				
				qtdNota = 0;
				isCriarSetor = true;
				
			}

			if (isCriarSetor) {
				
				//Notas Fiscais Embratel
				setorNFItemEBT = this.criarSetorNotaFiscalEBT(fatura.getDadosCabecalhoParceiro());		
				
			} else {
				
				Map parceiroMap = this.obterMapEBT(fatura.getDadosCabecalhoParceiro());
				setorNFItemEBT.addLinha(new LinhaDTO(LinhaDTO.TIPO_INICIO_NOTA_FISCAL_EBT, new HashMap<String,String>(), 0));
				setorNFItemEBT.addLinha(new LinhaDTO(LinhaDTO.TIPO_DADOS_PARCEIRO_NF, parceiroMap, 0));
				
			}
			
			DynamicBean paramCobranca = new DynamicBean(); 
			paramCobranca.set("idCobrancaNotaFiscal", cobranca.getIdCobrancaNotaFiscal());
			paramCobranca.set("idBoleto", idBoleto);
			
			//DadosGeraisPrimeiraViaPrintParceiroDTO nota = getDadosBeanByQuery("selecionarClienteNotaFiscalBoletoParceiro", paramCobranca, false, DadosGeraisPrimeiraViaPrintParceiroDTO.class).get(0);
			DadosGeraisPrimeiraViaPrintParceiroDTO nota = fatura.getDadosClienteParceiro(cobranca.getIdCobrancaNotaFiscal());
			
			Map dadosAssinanteEBT = new HashMap();
			
			String codCliente = new String();
			
			if( nota.getCodigoClienteEBT() != null){
				codCliente = nota.getCodigoClienteEBT();
			}
			
			int b = codCliente.length()-4;
			
			dadosAssinanteEBT.put(PrintHouseConstants.NOTA_FISCAL_EBT.NNOMECLIENTE, nota.getCcNomeRazaoSocial());						
			if(!org.apache.commons.lang.StringUtils.isBlank(codCliente)){
				dadosAssinanteEBT.put(PrintHouseConstants.NOTA_FISCAL_EBT.CCDIGOCLIENTEEBT, StringUtils.prepad(codCliente.substring(0,b),11,'0')+'-'+StringUtils.prepad(codCliente.substring(b,codCliente.length()),4,'0'));
			}else{
				dadosAssinanteEBT.put(PrintHouseConstants.NOTA_FISCAL_EBT.CCDIGOCLIENTEEBT, " ");
			}
			
            dadosAssinanteEBT.put(PrintHouseConstants.NOTA_FISCAL_EBT.NUMEROFATURA, nota.getNrFatura());
			dadosAssinanteEBT.put(PrintHouseConstants.NOTA_FISCAL_EBT.ENDERECO, nota.getCcLogradouro());
			dadosAssinanteEBT.put(PrintHouseConstants.NOTA_FISCAL_EBT.BAIRRO, nota.getCcBairro());
			dadosAssinanteEBT.put(PrintHouseConstants.NOTA_FISCAL_EBT.CIDADE, nota.getCcCidade());
			dadosAssinanteEBT.put(PrintHouseConstants.NOTA_FISCAL_EBT.ESTADO, nota.getCcEstado());
			dadosAssinanteEBT.put(PrintHouseConstants.NOTA_FISCAL_EBT.CEP, nota.getCepNotaFiscal());
			dadosAssinanteEBT.put(PrintHouseConstants.NOTA_FISCAL_EBT.CNPJ, nota.getCcCpfCnpj());
			dadosAssinanteEBT.put(PrintHouseConstants.NOTA_FISCAL_EBT.INSCRICAOESTADUAL, nota.getCcInscricaoEstadual());
			dadosAssinanteEBT.put(PrintHouseConstants.NOTA_FISCAL_EBT.MENSAGEM_NF, nota.getDsMensagemNotaFiscal());
			tamanho = 65; //NF EBT referente a "REGIME ESPECIAL..." 
			if (parceiroBean.getSnTipoParceiroBean().getIdTipoParceiro() == 3 ){
				tamanho = 1; //NF Parceiro nao tem esta parte impressa, mas nao pode ser 0 senao o gerarFatura imprime a quebra de paginas novamente
			}
			setorNFItemEBT.addLinha(new LinhaDTO(LinhaDTO.TIPO_DADOS_ASSINANTE_EBT,dadosAssinanteEBT, tamanho));
			
			if (parceiroBean.getSnTipoParceiroBean().getIdTipoParceiro() == 3 ){
				
				Map parceiroMap = new HashMap();
				parceiroMap.put(PrintHouseConstants.NOTA_FISCAL_EBT.NOMEEMPRESAPARCEIRA, nota.getCc_razao_social_oper());
				parceiroMap.put(PrintHouseConstants.NOTA_FISCAL_EBT.ENDERECO, nota.getCc_logradouro_oper());
				parceiroMap.put(PrintHouseConstants.NOTA_FISCAL_EBT.BAIRRO, nota.getCc_bairro_oper());
				parceiroMap.put(PrintHouseConstants.NOTA_FISCAL_EBT.CIDADE, nota.getCc_cidade_oper());
				parceiroMap.put(PrintHouseConstants.NOTA_FISCAL_EBT.ESTADO, nota.getCc_estado_oper());
				parceiroMap.put(PrintHouseConstants.NOTA_FISCAL_EBT.CEP, nota.getCc_cep_oper());
				parceiroMap.put(PrintHouseConstants.NOTA_FISCAL_EBT.CNPJ, nota.getCc_cnpj_oper());
				parceiroMap.put(PrintHouseConstants.NOTA_FISCAL_EBT.INSCRICAOESTADUAL, nota.getCc_inscricao_estadual_oper());
				parceiroMap.put(PrintHouseConstants.NOTA_FISCAL_EBT.INSCRICAOMUNICIPAL, nota.getCc_inscricao_municipal_oper());
				//Cabecalho parceiro tem 2,54 cm
				setorNFItemEBT.addLinha(new LinhaDTO(LinhaDTO.TIPO_DADOS_PARCEIRO_NF_EBT,parceiroMap, 254));
				
			}			
			
			//Registro 32
			Map cabecalhoNFEBT = new HashMap();
			cabecalhoNFEBT.put(PrintHouseConstants.NOTA_FISCAL_EBT.SERIENOTAFISCAL, nota.getCcSerieNotaFiscal());
			cabecalhoNFEBT.put(PrintHouseConstants.NOTA_FISCAL_EBT.NUMERONOTAFISCAL, StringUtils.prepad(nota.getNrNotaFiscal(),9,'0'));
			cabecalhoNFEBT.put(PrintHouseConstants.NOTA_FISCAL_EBT.DATAEMISSAONOTAFISCAL, nota.getDtEmissao());				  
			cabecalhoNFEBT.put(PrintHouseConstants.NOTA_FISCAL_EBT.UNIDADEFISCAL, nota.getCcEstado());
			tamanho=35; //NF EBT referente a "NOTA FISCAL DE SERVICOS DE TELECOMUNICACAO"
			if (parceiroBean.getSnTipoParceiroBean().getIdTipoParceiro() == 3 ){
				tamanho = 0; //NF Parceiro nao tem esta parte impressa
			}
			setorNFItemEBT.addLinha(new LinhaDTO(LinhaDTO.TIPO_CABECALHO_NF_EBT,cabecalhoNFEBT, tamanho));
			
			// Registro 33 - Itens NF		  
			//  List<DadosItensNFDTO> itensNF = getDadosBeanByQuery("listarDetalhamentoNotaFiscalParceiro", paramCobranca, false, DadosItensNFDTO.class);
			List<DadosItensNFDTO> itensNF = fatura.getItensNotaFiscalParceiro(cobranca.getIdCobrancaNotaFiscal()); 
			
			for(DadosItensNFDTO item : itensNF){
				
				Map itemNFEBT = new HashMap();						
				itemNFEBT.put(PrintHouseConstants.NOTA_FISCAL_EBT.DESCRICAOTELEFONE, item.getDescricao());
				itemNFEBT.put(PrintHouseConstants.NOTA_FISCAL_EBT.ALIQUOTA, item.getAliquota());
				itemNFEBT.put(PrintHouseConstants.NOTA_FISCAL_EBT.VALOR, item.getValorBrutoItem());
				itemNFEBT.put(PrintHouseConstants.NOTA_FISCAL_EBT.EBT_ICMS, item.getValorIcmsItem());
				itemNFEBT.put(PrintHouseConstants.NOTA_FISCAL_EBT.EBT_COFINS, " ");
				itemNFEBT.put(PrintHouseConstants.NOTA_FISCAL_EBT.EBT_PIS, " ");
				//Cada item do resumo EBT ou parceiro tem 0.4cm
				setorNFItemEBT.addLinha(new LinhaDTO(LinhaDTO.TIPO_ITENS_NF_EBT,itemNFEBT, 40));
				
			}
			
			//Registro 36 - Reservado ao Fisco
			Map labelFiscoMap = new HashMap();
			labelFiscoMap.put(PrintHouseConstants.NOTA_FISCAL_EBT.LABELFISCO,"Reservado ao Fisco");
			setorNFItemEBT.addLinha(new LinhaDTO(LinhaDTO.TIPO_LABEL_RESERVADO_FISCAL_NF_EBT,labelFiscoMap, 0));
			// 37
			Map fiscoMap = new HashMap();
			//fiscoMap.put(PrintHouseConstants.NOTA_FISCAL_EBT.LABELFISCO,"Reservado ao Fisco");
			fiscoMap.put(PrintHouseConstants.NOTA_FISCAL_EBT.HASHCODE,nota.getCcHashCode());
			tamanho=490; //NF EBT quadro de resumo inicia em 22,2cm e mensagens inicia 27,1, entao tem 4,9cm
			if (parceiroBean.getSnTipoParceiroBean().getIdTipoParceiro() == 3 ){
				tamanho = 390; //NF Parceiro quadro de resumo tem tamanho 3,9cm
			}
			setorNFItemEBT.addLinha(new LinhaDTO(LinhaDTO.TIPO_RESERVADO_FISCAL_NF_EBT,fiscoMap, tamanho));		 
			
			// 	Registro 35 - Tributos
			//List<DadosTributosDTO> tributosNF = getDadosBeanByQuery("listarTributoParceiro", paramCobranca, false, DadosTributosDTO.class);
			List<DadosTributosDTO> tributosNF =  fatura.getTributosNotaFiscalParceiro(cobranca.getIdCobrancaNotaFiscal());
			
			for(DadosTributosDTO item : tributosNF){
				
				Map tributoNFEBT = new HashMap();			 			
				tributoNFEBT.put(PrintHouseConstants.NOTA_FISCAL_EBT.SGTRIBUTO, item.getSIGLATRIBUTO());
				tributoNFEBT.put(PrintHouseConstants.NOTA_FISCAL_EBT.BASECALCULO, item.getBASECALCULO());
				tributoNFEBT.put(PrintHouseConstants.NOTA_FISCAL_EBT.ALIQUOTA, valor(item.getVALORALIQUOTA(),20,2)); 
				tributoNFEBT.put(PrintHouseConstants.NOTA_FISCAL_EBT.VALORTOTAL, item.getVALORTOTAL());					 	
				tributoNFEBT.put(PrintHouseConstants.NOTA_FISCAL_EBT.VALORICMS, item.getVALORTRIBUTO());
				tributoNFEBT.put(PrintHouseConstants.NOTA_FISCAL_EBT.VALORISENTO, item.getVALORISENTO());
				tributoNFEBT.put(PrintHouseConstants.NOTA_FISCAL_EBT.VALOROUTROS, item.getVALOROUTROS());					 	
				setorNFItemEBT.addLinha(new LinhaDTO(LinhaDTO.TIPO_TOTAL_NF_EBT,tributoNFEBT, 0)); 
				
			}
			
			// Registro 34 - Tributos
			//List<DadosTributosDTO> totalTributosNF = getDadosBeanByQuery("listarTributoParceiroTotal", paramCobranca, false, DadosTributosDTO.class);
			List<DadosTributosDTO> totalTributosNF =  fatura.getTotalTributosNotaFiscalParceiro(cobranca.getIdCobrancaNotaFiscal());
			
			for(DadosTributosDTO tributo : totalTributosNF){
				
				Map totalTributoNFEBT = new HashMap();						
				totalTributoNFEBT.put(PrintHouseConstants.NOTA_FISCAL_EBT.SGTRIBUTO, tributo.getSIGLATRIBUTO());
				totalTributoNFEBT.put(PrintHouseConstants.NOTA_FISCAL_EBT.BASECALCULO, tributo.getBASECALCULO());
				totalTributoNFEBT.put(PrintHouseConstants.NOTA_FISCAL_EBT.VALORTOTAL, tributo.getVALORTOTAL());
				totalTributoNFEBT.put(PrintHouseConstants.NOTA_FISCAL_EBT.ALIQUOTA, " ");
				totalTributoNFEBT.put(PrintHouseConstants.NOTA_FISCAL_EBT.VALORICMS, tributo.getVALORTRIBUTO());
				totalTributoNFEBT.put(PrintHouseConstants.NOTA_FISCAL_EBT.VALORISENTO, tributo.getVALORISENTO());
				totalTributoNFEBT.put(PrintHouseConstants.NOTA_FISCAL_EBT.VALOROUTROS, tributo.getVALOROUTROS());					 	
				setorNFItemEBT.addLinha(new LinhaDTO(LinhaDTO.TIPO_TRIBUTOS_NF_EBT,totalTributoNFEBT, 0));
				
			}			 
			
			if (parceiroBean.getSnTipoParceiroBean().getIdTipoParceiro() != 3 ){
				
				//	 Registro 36 -Mensagens
				Map mensagem1Map = new HashMap();
				mensagem1Map.put(PrintHouseConstants.NOTA_FISCAL_EBT.LABELFISCO,
				"1 - ESTE DOCUMENTO DESTINA-SE, APENAS , A ATENDIMENTO A INFORMAÇÕES DA CARATER FISCAL ");
				setorNFItemEBT.addLinha(new LinhaDTO(LinhaDTO.TIPO_LABEL_RESERVADO_FISCAL_NF_EBT,mensagem1Map, 300));

				Map mensagem2Map = new HashMap();
				mensagem2Map.put(PrintHouseConstants.NOTA_FISCAL_EBT.LABELFISCO,
				"    NÃO DEVE SER UTILIZADO PARA PAGAMENTO DE SERVIÇOS ");
				setorNFItemEBT.addLinha(new LinhaDTO(LinhaDTO.TIPO_LABEL_RESERVADO_FISCAL_NF_EBT,mensagem2Map, 0));
				
				if (fatura.getMsgTotaisImpostosParceiro() != null) {
					Map mensagem3Map = new HashMap();
					mensagem3Map.put(PrintHouseConstants.NOTA_FISCAL_EBT.LABELFISCO,
					fatura.getMsgTotaisImpostosParceiro());
					setorNFItemEBT.addLinha(new LinhaDTO(LinhaDTO.TIPO_LABEL_RESERVADO_FISCAL_NF_EBT,mensagem3Map, 0));
				}
								
				//Busca informação de Declaracao de Quitação Debito EBT, envia na Interface de Carga Fatura Embratel
				List retMsgEbt = this.buscaMsgDeclaracaoDebitoEBT(idBoleto);
				
				if(!retMsgEbt.isEmpty()){									
					for(int i=0; i < retMsgEbt.size(); i++){
						Map msgMap = new HashMap();
						
						if(retMsgEbt.get(i)!=null){
						msgMap.put(PrintHouseConstants.NOTA_FISCAL_EBT.LABELFISCO, retMsgEbt.get(i).toString());						
						setorNFItemEBT.addLinha(new LinhaDTO(LinhaDTO.TIPO_LABEL_RESERVADO_FISCAL_NF_EBT, msgMap, 300));						
						}				
					}								
				}			
			}
			
			if(listMensagemAnatelDTO != null){
			
				for (MensagemAnatelDTO mensagemAnatelDTO : listMensagemAnatelDTO) {
					
					setorNFItemEBT.addLinha(this.llk.criarLinhaComplementoCampoImportante(mensagemAnatelDTO));
				}
			}
			
			setorNFItemEBT.addLinha(new LinhaDTO(LinhaDTO.TIPO_FIM_FISCAL_NF_EBT, 0));	
			
			setorLista.addSetor(setorNFItemEBT);
			
		}
		
	}
			
	/**
	 * Método de apoio para o método protected FaturaDTO
	 * gerarFaturaPrintHouse(Integer idLote, Long idBoleto)
	 * 
	 * @author Marcio Bellini - refactor Robin Michael Gray
	 * @number RF015_RF021
	 * 
	 * @param dadosGeraisPrimeiraViDTO
	 *            DTO contendo informações para o preenchimento do Map referente
	 *            à interface ClienteCobrancaPrintDTO
	 * @return Map
	 */
	private Map preencheMapFatura(DadosGeraisPrimeiraViaPrintDTO dadosGeraisPrimeiraViDTO,
	                              FaturaNetDTO fatura,
	                              Boolean ValidaMsg,
	                              String tipoMsg){
		//NSMSP_172250_NI_003
		SnLoteBean loteCorrente = new SnLoteBean();
		if(dadosGeraisPrimeiraViDTO.getCodigoLote() != null){
			loteCorrente = buscarLote(dadosGeraisPrimeiraViDTO.getCodigoLote().intValue());
		}
		
		ArquivoPrintHouseDAO dao = getAphDao();
		Integer formaEnvioFatura = dao.buscarTipoEnvioFaturaPorLote(fatura.getCidContrato(), loteCorrente, dadosGeraisPrimeiraViDTO.getFormaEnvioFatura());
		dadosGeraisPrimeiraViDTO.setFormaEnvioFatura(formaEnvioFatura);
		
		return new DadosArquivoPrintHouseUtil().preencheMapFatura(dadosGeraisPrimeiraViDTO, fatura, ValidaMsg, tipoMsg);
	}
	
	
	public FaturaDTO gerarFatura(String cidContrato, SetorListaDTO setorLista)
	{
		return gerarFatura(cidContrato, setorLista, new FaturaDTO());
	}  
	
	
	/**
	 * @ejb.interface-method view-type = "both"
	 * @ejb.transaction type="Required"
	 */
	
	public  FaturaDTO gerarFatura(String cidContrato, SetorListaDTO setorLista, FaturaDTO fatura)  {
		int totalPaginas = 0;
		int restoLinhas = 0;
		int totalPaginasEBT = 0;
		boolean possuiNFNET = false;
		boolean nfCabeNoTijolinho = false;
		int aux[] = null;
		
		int paginasFATCOMBO = 0;
		int paginasDETEBT = 0;
		int paginasNFNET = 0;
		int paginasNFEBT = 0;
		int paginasNFPARC = 0;
		
		int linhasPorPagina =  QT_LN_PAG_FAT_PRINT;
		
		linhasPorPagina *= 100; // Multiplica por 100 para tratar decimal como inteiro no tamanho da linha

		int[] totalPaginasNFNet = new int[setorLista.getTotalSetor().intValue()];
		
		int[] totalPaginasNFEBT = new int[setorLista.getTotalSetor().intValue()];
		
		// INICIO DE CONTABILIZACAO DE TOTAL DE PAGINAS E TOTAL DE PAGINAS PARA NF NET
		for (int i = 0; i < setorLista.getTotalSetor().intValue(); i++) {
			SetorDTO setorDTO = setorLista.get(new Integer(i));
			int tipoSetor = setorDTO.getTipoSetor().intValue();
			
			//Na contagem de paginas desses "setores / linhasPorPagina" sempre retorna indice 0 (1 a menos)
			if (tipoSetor == TipoSetorPrint.DEMONSTRATIVO_FINANCEIRO_FICHA_ARRECADACAO.getValue()) {
				totalPaginas += 1;
			}
			//Na contagem de paginas desses "setores / linhasPorPagina" sempre retorna indice 0 (1 a menos)
			else if (tipoSetor == TipoSetorPrint.FILIADOS.getValue()) {
				//1 pagina a mais apenas se o setor de filiados iniciar em nova pagina
				if (setorDTO.isSetorComecaSempreNovaPagina()){ 
					totalPaginas += 1;
				}
			}
			
			if (tipoSetor == TipoSetorPrint.NOTA_FISCAL_NET.getValue()) {
				possuiNFNET = true;
			}
						
			if (tipoSetor == TipoSetorPrint.NOTA_FISCAL_NET.getValue() ||
					tipoSetor == TipoSetorPrint.NOTA_FISCAL_TRIBUTOS_NET.getValue() ||
					tipoSetor == TipoSetorPrint.DETALHES_LIGACOES_NET_FONE.getValue() ){ // tipo 
				// NF
				if (( tipoSetor == TipoSetorPrint.NOTA_FISCAL_NET.getValue()) && 
						setorDTO.getQuantidadeLinhasOcupadas().intValue() <= 0) {
					totalPaginasNFNet[i] = 0; 
				}
				
				//para agrupar os setores de nf de nf tributos
				if ( tipoSetor == TipoSetorPrint.NOTA_FISCAL_TRIBUTOS_NET.getValue()) {
					int totalPaginasNFAnteiror = totalPaginasNFNet[i-1];
					totalPaginasNFNet[i] = totalPaginasNFAnteiror;
				}
				else if ( tipoSetor == TipoSetorPrint.DETALHES_LIGACOES_NET_FONE.getValue()){									 
					//Detalhes ligacoes tem a mesma qde de milimetros q a pagina de nf ebt/parceiros
					aux = contaPaginasMilimetros(setorDTO, TAM_PAGINA_DETALHAMENTO_EBT, restoLinhas); 
					int total = aux[0]+1;
					
					totalPaginasNFEBT[i]= total;
					totalPaginasEBT = total;
				}
			}	

			if (setorDTO.isSetorComecaSempreNovaPagina().booleanValue()) {
				//Se o setor comeca em nova pagina e ja temos itens, entao pula pra proxima pagina
				if (restoLinhas != 0) { 
					totalPaginas++;
					restoLinhas=0;
				}
				
				//Caso NF EBT ou parceiro a contagem é por milimetros 
				if (tipoSetor==5 || tipoSetor==6 || tipoSetor == TipoSetorPrint.DETALHES_LIGACOES_NET_FONE.getValue()){
					
					int tamanho = TAM_PAGINA_NF_EBT_PARCEIROS;
					
					if (tipoSetor == TipoSetorPrint.DETALHES_LIGACOES_NET_FONE.getValue()) {
						tamanho = TAM_PAGINA_DETALHAMENTO_EBT;
					}
					
					aux = contaPaginasMilimetros(setorDTO, tamanho, restoLinhas);
					totalPaginas 	+= aux[0];
					restoLinhas 	=  aux[1]; //resto em mm
				}
				else {
					//fluxo antigo com contagem por quantidade de linhas
					totalPaginas 	+= (int)setorDTO.getQuantidadeLinhasOcupadas().intValue() / linhasPorPagina;
					restoLinhas 	=  setorDTO.getQuantidadeLinhasOcupadas().intValue() % linhasPorPagina; //resto em nro de linhas
				}
			} else  if (setorDTO.isSetorPodeQuebrarPagina().booleanValue()) {
				//Caso NF EBT ou parceiro a contagem é por milimetros 
				if (tipoSetor==5 || tipoSetor==6 || tipoSetor == TipoSetorPrint.DETALHES_LIGACOES_NET_FONE.getValue()) {
					int tamanho = TAM_PAGINA_NF_EBT_PARCEIROS;
					if (tipoSetor == TipoSetorPrint.DETALHES_LIGACOES_NET_FONE.getValue()) {
						tamanho = TAM_PAGINA_DETALHAMENTO_EBT;
					}

					aux = contaPaginasMilimetros(setorDTO, tamanho, restoLinhas);
					totalPaginas 	+= aux[0];
					restoLinhas 	=  aux[1]; //resto em mm
				}
				else {
					//fluxo antigo com contagem por quantidade de linhas
					totalPaginas 	+= (int)(setorDTO.getQuantidadeLinhasOcupadas().intValue() + restoLinhas) / linhasPorPagina;
					restoLinhas 	=  (setorDTO.getQuantidadeLinhasOcupadas().intValue() + restoLinhas) % linhasPorPagina; //resto em nro de linhas
				}
			} else {	
				if (setorDTO.getQuantidadeLinhasOcupadas().intValue() + restoLinhas <= linhasPorPagina) {
					
					restoLinhas = setorDTO.getQuantidadeLinhasOcupadas().intValue() + restoLinhas;
					
				} else {
					totalPaginas++;
					restoLinhas = setorDTO.getQuantidadeLinhasOcupadas().intValue() % linhasPorPagina;
					totalPaginas 	+= (int)setorDTO.getQuantidadeLinhasOcupadas().intValue()  / linhasPorPagina;
					
					if ( tipoSetor == TipoSetorPrint.NOTA_FISCAL_TRIBUTOS_NET.getValue() ) {
						totalPaginasNFNet[i]++;
						totalPaginasNFNet[i] += (int)setorDTO.getQuantidadeLinhasOcupadas().intValue()  / linhasPorPagina;
						totalPaginasNFNet[i-1] = totalPaginasNFNet[i]; 
						paginasNFNET++;
					}
					
					if ( tipoSetor == TipoSetorPrint.DETALHES_LIGACOES_NET_FONE.getValue() ) { 
						totalPaginasNFEBT[i]++;
						totalPaginasNFEBT[i] += aux[0];
						totalPaginasNFEBT[i-1] = totalPaginasNFEBT[i]; 
					}
					
					
				}
			}
	
			//Conta quantidade de paginas de cada tipo de setor
			
			//Paginas NF
			if ( tipoSetor == TipoSetorPrint.NOTA_FISCAL_NET.getValue()) {
				if (setorDTO.getQuantidadeLinhasOcupadas().intValue() <= 0) {
					//Cabe no tijolinho, mas nao contara como pagina somente se for auto-envelopado
					nfCabeNoTijolinho = true; 
					paginasNFNET = 0;
					continue;
				}
				else {
					nfCabeNoTijolinho = false;
					paginasNFNET += 1 + (int)setorDTO.getQuantidadeLinhasOcupadas().intValue() / linhasPorPagina;
				}
			}
			//Paginas DF
			else if (tipoSetor == TipoSetorPrint.DEMONSTRATIVO_FINANCEIRO_FICHA_ARRECADACAO.getValue()){ 
				paginasFATCOMBO += 1 + ((int) (setorDTO.getQuantidadeLinhasOcupadas().intValue() / linhasPorPagina));
			}
			else if (tipoSetor == TipoSetorPrint.FILIADOS.getValue()){ 
				//se os filiados comecam em nova pagina, ocupa 1 a mais
				if (setorDTO.isSetorComecaSempreNovaPagina()) {
					paginasFATCOMBO += 1;
				}
				paginasFATCOMBO += ((int) (setorDTO.getQuantidadeLinhasOcupadas().intValue() / linhasPorPagina));
			}
			//Paginas EBT
			else if (tipoSetor == TipoSetorPrint.DETALHES_LIGACOES_NET_FONE.getValue()){									 
				int total = aux[0];
				paginasDETEBT = total;
				if (aux[1]!=0) paginasDETEBT++;
			}
			else if (tipoSetor == TipoSetorPrint.NOTA_FISCAL_EMBRATEL.getValue()) {
				int total = aux[0];
				if (setorDTO.isSetorComecaSempreNovaPagina()) {
					total++; //se setor comeca em nova pagina ocupa 1 a mais
				}
				
				boolean ehNFEBT = true; //indica se a NF é EBT ou de parceiro
				//Se tiver algum item 38 é NFPARC senao é NFEBT
				for (int x = 0; x < setorDTO.size(); x++) {
					LinhaDTO linhaDTO = setorDTO.get(x);
					if (linhaDTO.getTipo().intValue()==38) {
						ehNFEBT = false;
						break;
					}
				}
				
				if (ehNFEBT) {
					paginasNFEBT += total; 
				}
				else {
					paginasNFPARC += total;	
				}
				
			}
			else if (tipoSetor == TipoSetorPrint.NOTA_FISCAL_PARCEIRO_EMBRATEL.getValue()) {
				int total = aux[0];
				paginasNFPARC += total;
			}
			
		} // término do para

		if (restoLinhas != 0) {
			totalPaginas++;
		}
		
		boolean autoEnvelopamento = calculaEnvelopamento(paginasFATCOMBO, paginasNFNET, paginasNFEBT, paginasDETEBT, paginasNFPARC, possuiNFNET, nfCabeNoTijolinho);
		if (!autoEnvelopamento) {
			if (nfCabeNoTijolinho) {
				totalPaginas++;
				paginasNFNET += 1;//No inserido a NFNET ocupa pelo menos uma pagina
				nfCabeNoTijolinho = false;
			}
		}
		
		//Contabiliza total de paginas NET/Parceiro da fatura
		fatura.setNumPaginaNet(paginasFATCOMBO + paginasNFNET);
		fatura.setNumPaginaParceiro(paginasNFEBT + paginasDETEBT + paginasNFPARC);
		
		// FIM DA CONTABILIZACAO DE PAGINA

		// INICIO DE MONTAGEM DA FATURA
		int restoFatura = 0;
		int paginaAtual = 0;
		//FaturaDTO fatura = new FaturaDTO();
		int paginaAtualNF = 0;
		int paginaAtualEBT = 0;

		fatura.addLinha(setorLista.getLinhaInicio());
		
		// Para cada Setor (SetorDTO) dentro de (SetorLista) faça:
		for (int i = 0; i < setorLista.getTotalSetor().intValue(); i++) {
			
			SetorDTO setorDTO = setorLista.get(new Integer(i));

			if (setorDTO.getTipoSetor().intValue() == TipoSetorPrint.NOTA_FISCAL_NET.getValue()) {
				paginaAtualNF = 0;
			}
			
			if (setorDTO.getLinhaInicio() != null) {
				fatura.addLinha(setorDTO.getLinhaInicio());
			}

			if (setorDTO.isSetorComecaSempreNovaPagina().booleanValue()) {
				restoFatura = 0;
				
			} else if (!setorDTO.isSetorPodeQuebrarPagina().booleanValue()) {
				
				if ((setorDTO.getQuantidadeLinhasOcupadas().intValue() + restoFatura ) > linhasPorPagina) {
					restoFatura = 0;
				}
			}
			
			// Para cada Linha (linha) dentro de (SetorDTO) faça:
			for (int j = 0; j < setorDTO.getQuantidadeLinhasSetor().intValue(); j++) {
				
				LinhaDTO linhaDTO = setorDTO.get(j);

				//Se a NF net nao vai estar no tijolinho, deve ser paginada
				if (
						(setorDTO.getTipoSetor().intValue() == TipoSetorPrint.NOTA_FISCAL_NET.getValue()
						|| setorDTO.getTipoSetor().intValue() == TipoSetorPrint.NOTA_FISCAL_TRIBUTOS_NET.getValue())
						&& linhaDTO.getQuantidadeOcupa().intValue() ==0
						&& !nfCabeNoTijolinho ) {
					linhaDTO.setQuantidadeOcupa(1);
				}
					
				
				if (
						(setorDTO.getTipoSetor().intValue() == TipoSetorPrint.SETOR_MINHA_NET.getValue() && j!=0) //So imprime cabecalho para primeiro item do minha net
						|| (setorDTO.getTipoSetor().intValue() == TipoSetorPrint.OCORENCIAS.getValue())
						|| (setorDTO.getTipoSetor().intValue() == TipoSetorPrint.NOTA_FISCAL_NET.getValue() && nfCabeNoTijolinho && paginasNFNET == 0 && j!=0) //NFNET q cabe no tijolinho so tem cabeçalho sem paginacao
					) {
					//Nao tem paginacao para esses itens.
					restoFatura = 1;
				}
				
				//A linha do demonstrativo é colocada antes da quebra
				if (setorDTO.getTipoSetor().intValue() == TipoSetorPrint.DEMONSTRATIVO_FINANCEIRO_FICHA_ARRECADACAO.getValue()
						|| setorDTO.getTipoSetor().intValue() == TipoSetorPrint.FILIADOS.getValue()){

					//Filiados: verifica se é para iniciar em nova pagina
					if (j==0 && setorDTO.isSetorComecaSempreNovaPagina() && setorDTO.getTipoSetor().intValue() == TipoSetorPrint.FILIADOS.getValue()) {
						paginaAtual++;
						restoFatura = 0;
						adicionaCabecalhoQuebra(fatura, setorDTO, possuiNFNET, paginaAtual, paginaAtualNF, paginaAtualEBT, totalPaginas, totalPaginasNFNet, totalPaginasNFEBT, i, nfCabeNoTijolinho);
						linhaDTO.setQuantidadeOcupa(0); 
					}
					
					//Demonstrativo financeiro: a paginacao é por milimetros
					fatura.addLinha(linhaDTO);
					if ( restoFatura + linhaDTO.getQuantidadeOcupa().intValue() >= linhasPorPagina) {
						paginaAtual++;
						restoFatura = 0;
						adicionaCabecalhoQuebra(fatura, setorDTO, possuiNFNET, paginaAtual, paginaAtualNF, paginaAtualEBT, totalPaginas, totalPaginasNFNet, totalPaginasNFEBT, i, nfCabeNoTijolinho);
						linhaDTO.setQuantidadeOcupa(0); 
					}
				}	
				//Outros: a paginacao é por numero de linhas
				else {
					int tamanhoMaximo = linhasPorPagina; //Numero maximo de linhas
					if (setorDTO.getTipoSetor().intValue()==5 || setorDTO.getTipoSetor().intValue()==6) {
						tamanhoMaximo = TAM_PAGINA_NF_EBT_PARCEIROS; //Numero maximo de milimetros, no caso de NF EBT ou Parceiros
					}
					if (setorDTO.getTipoSetor().intValue()==TipoSetorPrint.DETALHES_LIGACOES_NET_FONE.getValue()) {
						tamanhoMaximo = TAM_PAGINA_DETALHAMENTO_EBT;
					}
					
					//Se inicio da fatura, ou quebra de pagina, ou primeira linha do minha net(minha net é o primeiro setor)
					if ( restoFatura == 0 || (restoFatura + linhaDTO.getQuantidadeOcupa().intValue() > tamanhoMaximo)) {
						
						if (!(  (setorDTO.getTipoSetor().intValue() == TipoSetorPrint.NOTA_FISCAL_NET.getValue() 
								|| setorDTO.getTipoSetor().intValue()==TipoSetorPrint.NOTA_FISCAL_TRIBUTOS_NET.getValue())  
								&& nfCabeNoTijolinho)) {
							//Se for NF no tijolinho, nao conta pagina. Para outros casos sim.
						paginaAtual++;
						}
						
						if ( setorDTO.getTipoSetor().intValue() == TipoSetorPrint.DETALHES_LIGACOES_NET_FONE.getValue()){
							paginaAtualEBT++;
						}
						if (setorDTO.getTipoSetor().intValue() == TipoSetorPrint.NOTA_FISCAL_NET.getValue()) {
							paginaAtualNF++; //So incrementa pagina de nota fiscal se estamos no setor de NF									
						}
						restoFatura = 0;					

						adicionaCabecalhoQuebra(fatura, setorDTO, possuiNFNET, paginaAtual, paginaAtualNF, paginaAtualEBT, totalPaginas, totalPaginasNFNet, totalPaginasNFEBT, i, nfCabeNoTijolinho);
					}
					if (LinhaDTO.TIPO_ITEN_BLANK_LINE != linhaDTO.getTipo()) {
					fatura.addLinha(linhaDTO);
				}
				}
				restoFatura += linhaDTO.getQuantidadeOcupa().intValue();				
			}
			
			// Adicionar linha (setor.linhaFim) na fatura (fatura)
			if (setorDTO.getLinhaFim() != null) {
				fatura.addLinha(setorDTO.getLinhaFim());
			}

		} // próximo setor

		if (setorLista.getLinhaFim() != null) {
			fatura.addLinha(setorLista.getLinhaFim());
		}
		fatura.setQuantidadePaginas(new Integer(totalPaginas));
		fatura.setValorCobrado(setorLista.getValorCobrado());

		// FIM DA MONTAGEM DA FATURA
		
		return fatura;
	}

	/**
	 * Cria objeto SetorDTO do setor de nota fiscal da NET.
	 * 
	 * @version 1.0 - 19/04/2006 - Criacao
	 * @author Everton Ken Tomita - implementação Marcio Bellini - refactor
	 *         Robin Michael Gray
	 * @number RF015_RF021
	 * 
	 * @param ClienteNotaFiscalPrintBean
	 *            contem informacoes para montar a linha do tipo 12
	 * @return Cria um novo setor da nota fiscal NET
	 * 
	 * @semantics 
	 * 
	 */
	private SetorDTO criarSetorNotaFiscalNet(
			ClienteNotaFiscalPrintDTO clienteNotaFiscalPrintDTO,
			TipoSetorPrint tipoSetor , String boletoConsolidado, boolean cabecalhoNF, List<SetorDTO> listComplementoCampoImportante) {

		Map mapClienteNotaFiscal = new HashMap();
		mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.NUMERO_NOTA_FISCAL,notNullObject(clienteNotaFiscalPrintDTO.getNumeroNotaFiscal()));
		mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.CFOP_NOTA_FISCAL,notNullObject(clienteNotaFiscalPrintDTO.getCfopNotaFiscal()));
		if (clienteNotaFiscalPrintDTO.getInscricaoEstadualNotaFiscal() == null) {
			mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.INSCRICAO_ESTADUAL_NOTA_FISCAL,PrintHouseConstants.NotaFiscal.IE_ISENTO);
		} else {
			mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.INSCRICAO_ESTADUAL_NOTA_FISCAL,notNullObject(clienteNotaFiscalPrintDTO.getInscricaoEstadualNotaFiscal()));
		}
		mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.VALOR_TOTAL_NOTA_FISCAL,notNullObject(clienteNotaFiscalPrintDTO.getValorTotalNotaFiscal()));
		mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.HASH_CODE,notNullObject(clienteNotaFiscalPrintDTO.getHashCode()));
		mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.ENDERECO_NOTA_FISCAL,notNullObject(clienteNotaFiscalPrintDTO.getEnderecoNotaFiscal()));
		mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.BAIRRO_NOTA_FISCAL,notNullObject(clienteNotaFiscalPrintDTO.getBairroNotaFiscal()));
		mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.CIDADE_NOTA_FISCAL,notNullObject(clienteNotaFiscalPrintDTO.getCidadeNotaFiscal()));
		mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.ESTADO_NOTA_FISCAL,notNullObject(clienteNotaFiscalPrintDTO.getEstadoNotaFiscal()));
		mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.CEP_NOTA_FISCAL,StringUtils.prepad(notNullString(clienteNotaFiscalPrintDTO.getCepNotaFiscal()),8,'0'));
		mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.CPF_CNPJ_NOTA_FISCAL,notNullObject(clienteNotaFiscalPrintDTO.getCpfCnpjNotaFiscal()));
		mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.NOME_CLIENTE_NOTA_FISCAL,notNullObject(clienteNotaFiscalPrintDTO.getNomeClienteNotaFiscal()));
		mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.CODIGO_CLIENTE_NET,notNullString(clienteNotaFiscalPrintDTO.getCodOperadora())+"/"+StringUtils.prepad(notNullString(clienteNotaFiscalPrintDTO.getCodigoClienteNET()),9,'0'));
		//notNullString(clienteNotaFiscalPrintDTO.getCodigoClienteNET()));
		mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.DATA_VENCIMENTO_BOLETO,notNullObject(clienteNotaFiscalPrintDTO.getDataVencimentoBoleto()));
		mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.MES_ANO_REFERENCIA,notNullObject(clienteNotaFiscalPrintDTO.getMesAnoReferencia()));
		mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.DATA_EMISSAO_NOTA_FISCAL,notNullObject(clienteNotaFiscalPrintDTO.getDataEmissaoNotaFiscal()));

		Map mapInicioNotaFiscal = new HashMap();
		mapInicioNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.SERIE_NOTA_FISCAL,notNullObject(clienteNotaFiscalPrintDTO.getSerieNotaFiscal()));
		mapInicioNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.MODELO_NOTA_FISCAL,clienteNotaFiscalPrintDTO.getModeloNotaFiscal());

Collection<LinhaDTO> cabecalhoQuebraPagina = new ArrayList<LinhaDTO>();
		cabecalhoQuebraPagina.add(new LinhaDTO(LinhaDTO.TIPO_ITEN_INICIO_PAGINA)); //Para quebra de pagina so precisamos do codigo 11

		cabecalhoQuebraPagina.add(new LinhaDTO(LinhaDTO.TIPO_ITEN_MENSAGEM_INICIO_FATURA,mapInicioNotaFiscal));
		Map emissorFiscal = new HashMap();
		emissorFiscal.put(NOME_EMISSOR_FISCAL, notNullObject(clienteNotaFiscalPrintDTO.getNomeEmissorFiscal())); 
		emissorFiscal.put(ENDERECO_EMISSOR_FISCAL, notNullObject(clienteNotaFiscalPrintDTO.getEnderecoEmissorFiscal())); 
		emissorFiscal.put(BAIRRO_EMISSOR_FISCAL, notNullObject(clienteNotaFiscalPrintDTO.getBairroEmissorFiscal())); 
		emissorFiscal.put(CIDADE_EMISSOR_FISCAL, notNullObject(clienteNotaFiscalPrintDTO.getCidadeEmissorFiscal())); 
		emissorFiscal.put(ESTADO_EMISSOR_FISCAL, notNullObject(clienteNotaFiscalPrintDTO.getEstadoEmissorFiscal())); 
		emissorFiscal.put(CEP_EMISSOR_FISCAL, notNullObject(clienteNotaFiscalPrintDTO.getCepEmissorFiscal())); 
		emissorFiscal.put(CNPJ_EMISSOR_FISCAL, notNullObject(clienteNotaFiscalPrintDTO.getCnpjEmissorFiscal())); 
		emissorFiscal.put(IE_EMISSOR_FISCAL, notNullObject(clienteNotaFiscalPrintDTO.getIeEmissorFiscal())); 
		emissorFiscal.put(IM_EMISSOR_FISCAL, notNullObject(clienteNotaFiscalPrintDTO.getImEmissorFiscal())); 
		cabecalhoQuebraPagina.add(new LinhaDTO(LinhaDTO.TIPO_DADOS_EMISSOR_FISCAL, emissorFiscal));
		if( boletoConsolidado.equalsIgnoreCase("C") ||boletoConsolidado.equalsIgnoreCase("N")|| 
				boletoConsolidado.equalsIgnoreCase("B") || boletoConsolidado.equalsIgnoreCase("M") || 
					boletoConsolidado.equalsIgnoreCase("R") || ( boletoConsolidado.equalsIgnoreCase("D") &&  clienteNotaFiscalPrintDTO.isOperadoraNet())){
			
			if (cabecalhoNF) {
				cabecalhoQuebraPagina.add(new LinhaDTO(LinhaDTO.TIPO_ITEN_DADOS_ASS_NET_COM_NF, mapClienteNotaFiscal));
			}	
			//cabecalhoQuebraPagina.add(new LinhaDTO(LinhaDTO.TIPO_ITEN_DADOS_ASS_NET_SEM_NF, mapClienteNotaFiscal));
			//cabecalhoQuebraPagina.add(new LinhaDTO(LinhaDTO.TIPO_ITEN_BLANK_LINE));
			cabecalhoQuebraPagina.add(new LinhaDTO(LinhaDTO.TIPO_ITEN_CABECALHO_DETALHAMENTO));			
			//cabecalhoQuebraPagina.add(new LinhaDTO(LinhaDTO.TIPO_ITEN_BLANK_LINE));
			
		}else if(boletoConsolidado.equalsIgnoreCase("P") || boletoConsolidado.equalsIgnoreCase("A") || 
				( boletoConsolidado.equalsIgnoreCase("D") &&  clienteNotaFiscalPrintDTO.isOperadoraNet() == false)){
			cabecalhoQuebraPagina.add(new LinhaDTO(LinhaDTO.TIPO_ITEN_MENSAGEM_INICIO_FATURA_BRANCO));
			//cabecalhoQuebraPagina.add(new LinhaDTO(LinhaDTO.TIPO_ITEN_DADOS_ASS_NET_SEM_NF, mapClienteNotaFiscal));
			//cabecalhoQuebraPagina.add(new LinhaDTO(LinhaDTO.TIPO_ITEN_BLANK_LINE));
			
		}

		
		SetorDTO setorFaturaNet = null;
		
		if (TipoSetorPrint.NOTA_FISCAL_NET.getChave().toString().equals(tipoSetor.getChave().toString())) 
		{
			setorFaturaNet = new SetorDTO(TipoSetorPrint.NOTA_FISCAL_NET, null, null,cabecalhoQuebraPagina, Boolean.TRUE, Boolean.TRUE);
		} 
		else if (TipoSetorPrint.NOTA_FISCAL_TRIBUTOS_NET.getChave().toString().equals(tipoSetor.getChave().toString())) 
		{
			if(listComplementoCampoImportante == null || listComplementoCampoImportante.size() == 0){
			
				setorFaturaNet = new SetorDTO(TipoSetorPrint.NOTA_FISCAL_TRIBUTOS_NET, null,new LinhaDTO(LinhaDTO.TIPO_ITEN_NET_FIM_TITULO),cabecalhoQuebraPagina, Boolean.FALSE, Boolean.FALSE);
			}
			else{
			
				setorFaturaNet = new SetorDTO(TipoSetorPrint.NOTA_FISCAL_TRIBUTOS_NET, null,null,cabecalhoQuebraPagina, Boolean.FALSE, Boolean.FALSE);
			}
		}
		
		return setorFaturaNet;
	}

	/**
	 * Adiciona no setor passado como parametro, as linhas que irao compor a
	 * nota fiscal da NET.
	 * 
	 * @version 1.0 - 17/04/2006 - Criacao
	 * @author Everton Ken Tomita - implementação Marcio Bellini - refactor
	 *         Robin Michael Gray
	 * @number RF015_RF021
	 * 
	 * @param SetorDTO
	 *            estara sendo utilizado para incluir as linhas que compoem o
	 *            setor
	 * @param Integer
	 *            do id da nota fiscal
	 * @param ClienteNotaFiscalPrintBean
	 *            contem informacoes para montar a linha do tipo 18 e 21
	 * @throws JNetException
	 * 
	 * @semantics	
	 */
private int gerarSetorNotaFiscalNet(SetorDTO setorFaturaNet,
			SetorDTO setorTributosNet, Long idNotaFiscal,
			ClienteNotaFiscalPrintDTO clienteNotaFiscalPrintDTO, FaturaNetDTO fatura, int qdeNFNET) {

		int tamLinha = 0;
		int contLinhas = 0; 
		
		double milimetrosOcupados = 10; //mensagens=10mm e cabeçalho de 15mm nao conta para o total de 59mm
		
		QuebraAux quebra = this.new QuebraAux();
		quebra.setQuebrouPagina(false);
		quebra.setTamLinha(0);

		int linhasPorPagina = QT_LN_PAG_FAT_PRINT;
		linhasPorPagina *= 100; // Multiplica por 100 para tratar decimal como inteiro no tamanho da linha

		Map mapClienteNotaFiscal = new HashMap<String,String>();

		mapClienteNotaFiscal.put(NUMERO_NOTA_FISCAL, clienteNotaFiscalPrintDTO
				.getNumeroNotaFiscal());
		mapClienteNotaFiscal.put(CFOP_NOTA_FISCAL, clienteNotaFiscalPrintDTO
				.getCfopNotaFiscal());
		mapClienteNotaFiscal.put(INSCRICAO_ESTADUAL_NOTA_FISCAL,
				clienteNotaFiscalPrintDTO.getInscricaoEstadualNotaFiscal());
		mapClienteNotaFiscal.put(
				PrintHouseConstants.LayoutArquivoPrintHouse.VALOR_TOTAL_NOTA_FISCAL,
				clienteNotaFiscalPrintDTO.getValorTotalNotaFiscal());
		mapClienteNotaFiscal.put(HASH_CODE, clienteNotaFiscalPrintDTO
				.getHashCode());
		mapClienteNotaFiscal.put(ENDERECO_NOTA_FISCAL,
				clienteNotaFiscalPrintDTO.getEnderecoNotaFiscal());
		mapClienteNotaFiscal.put(BAIRRO_NOTA_FISCAL, clienteNotaFiscalPrintDTO
				.getBairroNotaFiscal());
		
		if (clienteNotaFiscalPrintDTO.getCidadeNotaFiscal() != null ){
			mapClienteNotaFiscal.put(CIDADE_NOTA_FISCAL, clienteNotaFiscalPrintDTO.getCidadeNotaFiscal());
		}else{
			mapClienteNotaFiscal.put(CIDADE_NOTA_FISCAL, " ");
		}		
		
		mapClienteNotaFiscal.put(ESTADO_NOTA_FISCAL, clienteNotaFiscalPrintDTO
				.getEstadoNotaFiscal());
		
		if( clienteNotaFiscalPrintDTO.getCepNotaFiscal() != null ){
			mapClienteNotaFiscal.put(CEP_NOTA_FISCAL, StringUtils.prepad(notNullString(clienteNotaFiscalPrintDTO.getCepNotaFiscal()),8,'0'));
		}else{
			mapClienteNotaFiscal.put(CEP_NOTA_FISCAL,"00000000" );
		}
		
		mapClienteNotaFiscal.put(CPF_CNPJ_NOTA_FISCAL,
				clienteNotaFiscalPrintDTO.getCpfCnpjNotaFiscal());
		mapClienteNotaFiscal.put(NOME_CLIENTE_NOTA_FISCAL,
				clienteNotaFiscalPrintDTO.getNomeClienteNotaFiscal());
		mapClienteNotaFiscal.put(CODIGO_CLIENTE_NET, clienteNotaFiscalPrintDTO
				.getCodigoClienteNET());
		mapClienteNotaFiscal.put(DATA_VENCIMENTO_BOLETO,
				clienteNotaFiscalPrintDTO.getDataVencimentoBoleto());
		mapClienteNotaFiscal.put(SERIE_NOTA_FISCAL, clienteNotaFiscalPrintDTO
				.getSerieNotaFiscal());
		mapClienteNotaFiscal.put(MODELO_NOTA_FISCAL, clienteNotaFiscalPrintDTO
				.getModeloNotaFiscal());
		mapClienteNotaFiscal.put(MES_ANO_REFERENCIA, clienteNotaFiscalPrintDTO
				.getMesAnoReferencia());
		mapClienteNotaFiscal.put(DATA_EMISSAO_NOTA_FISCAL,
				clienteNotaFiscalPrintDTO.getDataEmissaoNotaFiscal());

		DynamicBean b = new DynamicBean();		
		b.set("idCobrancaNotaFiscal", idNotaFiscal);
	
		//List<DetalhamentoNotaFiscalDTO> lstDetalhamentosNotaFiscal =  (List<DetalhamentoNotaFiscalDTO>)getDadosBeanByQuery("listarDetalhamentoNotaFiscal" , b, false, DetalhamentoNotaFiscalDTO.class);
		
		//Manipulação da NF para apresentação do subtítulo dos tributos
		boolean temISS = false;
		int tamMaxNmAgrupamento = 146;
		String subtituloTributos = "ICMS";
		
		List<TributoDTO> tributos = fatura.getTributos(idNotaFiscal);
		if( tributos != null ) {	
			for (TributoDTO tributo: tributos) {
				if(tributo.getSiglaAtributo().equals("ISS")) {
					temISS = true;
					tamMaxNmAgrupamento = 137;
					subtituloTributos = "ISS      ICMS";
					break;
				}
			}
		}
		
		List<DetalhamentoNotaFiscalDTO> lstDetalhamentosNotaFiscal =  fatura.getDetalheNotaFiscal(idNotaFiscal);
		if ( lstDetalhamentosNotaFiscal != null ) {
			DetalhamentoNotaFiscalDTO vlAnterior = null;
			for(DetalhamentoNotaFiscalDTO detalhamentoNotaFiscalDTO :lstDetalhamentosNotaFiscal){
				String registroAnterior = null;
				if (vlAnterior != null) {
					registroAnterior = vlAnterior.getNmGrupamento();
				}
				String registroAtual = detalhamentoNotaFiscalDTO.getNmGrupamento();
				
				if (registroAnterior == null ) {
					
					// Obtem o tamanho da linha
					tamLinha = quebra.obterTamLinhaDetalheNet(LinhaDTO.TIPO_ITEN_NET_CABECALHO_AGRUPAMENTO, 01);
					
					int tamAgrupamento = detalhamentoNotaFiscalDTO.getNmGrupamento().length();
					if (tamAgrupamento < tamMaxNmAgrupamento) {
						detalhamentoNotaFiscalDTO.setNmGrupamento(StringUtils.postpad(detalhamentoNotaFiscalDTO.getNmGrupamento(), tamMaxNmAgrupamento));
					}
					
					detalhamentoNotaFiscalDTO.setNmGrupamento(detalhamentoNotaFiscalDTO.getNmGrupamento() + subtituloTributos);
						
					setorFaturaNet.addLinha(
							llk.criarLinhaDetalheNotaFiscal(LinhaDTO.TIPO_ITEN_NET_CABECALHO_AGRUPAMENTO
									, detalhamentoNotaFiscalDTO
									, tamLinha));
					
					detalhamentoNotaFiscalDTO.setNmGrupamento(registroAtual);
					contLinhas += tamLinha;
			
			milimetrosOcupados += getTamanhoLinhaNF(LinhaDTO.TIPO_ITEN_NET_CABECALHO_AGRUPAMENTO);
					
				} else if (!registroAtual.equals(registroAnterior)) {
					
					// Obtem o tamanho da linha
					tamLinha = quebra.obterTamLinhaDetalheNet(LinhaDTO.TIPO_ITEN_NET_RODAPE_AGRUPAMENTO, 01);
					
					setorFaturaNet.addLinha(
							llk.criarLinhaDetalheNotaFiscal(LinhaDTO.TIPO_ITEN_NET_RODAPE_AGRUPAMENTO
									, vlAnterior
									, tamLinha)
					);
					
					contLinhas += tamLinha;

					milimetrosOcupados += getTamanhoLinhaNF(LinhaDTO.TIPO_ITEN_NET_RODAPE_AGRUPAMENTO);

					// Obtem o tamanho da linha removidas linhas em branco
//					tamLinha = quebra.obterTamLinhaDetalheNet(LinhaDTO.TIPO_ITEN_BLANK_LINE, 01);
//					
//					setorFaturaNet.addLinha(
//							llk.criarLinhaDetalheNotaFiscal(LinhaDTO.TIPO_ITEN_BLANK_LINE
//									, detalhamentoNotaFiscalDTO
//									, tamLinha)
//					);
//					
//					contLinhas += tamLinha;
					
					// Obtem o tamanho da linha
					tamLinha = quebra.obterTamLinhaDetalheNet(LinhaDTO.TIPO_ITEN_NET_CABECALHO_AGRUPAMENTO, 01);
					
					setorFaturaNet.addLinha(
							llk.criarLinhaDetalheNotaFiscal(LinhaDTO.TIPO_ITEN_NET_CABECALHO_AGRUPAMENTO
									, detalhamentoNotaFiscalDTO
									, tamLinha)
					);
					
					contLinhas += tamLinha;
					
					milimetrosOcupados += getTamanhoLinhaNF(LinhaDTO.TIPO_ITEN_NET_CABECALHO_AGRUPAMENTO);			
				} 
				
				// Obtem o tamanho da linha
				tamLinha = quebra.obterTamLinhaDetalheNet(LinhaDTO.TIPO_ITEN_NET_DETALHAMENTO, 01);
				
				setorFaturaNet.addLinha(
						llk.criarLinhaDetalheNotaFiscal(LinhaDTO.TIPO_ITEN_NET_DETALHAMENTO
								, detalhamentoNotaFiscalDTO
								, tamLinha
								, temISS)
				);
				
				contLinhas += tamLinha;
				
					milimetrosOcupados += getTamanhoLinhaNF(LinhaDTO.TIPO_ITEN_NET_DETALHAMENTO);
					
				vlAnterior = detalhamentoNotaFiscalDTO;
			}
			if (vlAnterior!= null) {
				
				// Obtem o tamanho da linha
				tamLinha = quebra.obterTamLinhaDetalheNet(LinhaDTO.TIPO_ITEN_NET_RODAPE_AGRUPAMENTO, 01);
				
				setorFaturaNet.addLinha(
						llk.criarLinhaDetalheNotaFiscal(LinhaDTO.TIPO_ITEN_NET_RODAPE_AGRUPAMENTO.intValue()
								, vlAnterior
								, tamLinha)
				);
				
				contLinhas += tamLinha;
			
				milimetrosOcupados += getTamanhoLinhaNF(LinhaDTO.TIPO_ITEN_NET_RODAPE_AGRUPAMENTO);
						
//				// Obtem o tamanho da linha _ SEM LINhas em branco
//				tamLinha = quebra.obterTamLinhaDetalheNet(LinhaDTO.TIPO_ITEN_NET_RODAPE_AGRUPAMENTO, 01);
//				
//				setorFaturaNet.addLinha(
//						llk.criarLinhaDetalheNotaFiscal(LinhaDTO.TIPO_ITEN_BLANK_LINE.intValue()
//								, vlAnterior
//								, tamLinha)
//				);
//				
//				contLinhas += tamLinha;
				
			}
			
		}
		
		// Obtem o tamanho da linha
		tamLinha = quebra.obterTamLinhaDetalheNet(LinhaDTO.TIPO_ITEN_NET_TOTAL_NOTA_FISCAL, 01);
		
		setorTributosNet.addLinha(new LinhaDTO(LinhaDTO.TIPO_ITEN_NET_TOTAL_NOTA_FISCAL
				, mapClienteNotaFiscal
				, tamLinha)
		);
		
		contLinhas += tamLinha;
	
		milimetrosOcupados += getTamanhoLinhaNF(LinhaDTO.TIPO_ITEN_NET_TOTAL_NOTA_FISCAL);
		
		// Obtem o tamanho da linha
//		tamLinha = quebra.obterTamLinhaDetalheNet(LinhaDTO.TIPO_ITEN_NET_TOTAL_NOTA_FISCAL, 01);
//		
//		setorTributosNet.addLinha(new LinhaDTO(LinhaDTO.TIPO_ITEN_BLANK_LINE
//				, new HashMap<String, String>()
//				, tamLinha));
//		
//		contLinhas += tamLinha;
		
		//DDGenericoSetorCallback genericoSetorCallback = new GenericoSetorCallback(
		//	setorTributosNet, LinhaDTO.TIPO_ITEN_NET_TRIBUTOS_NOTA_FISCAL);
		
		//List<TributoDTO> tributos = (List<TributoDTO>)getDadosBeanByQuery("listarTributo", b, false, TributoDTO.class);

		if( tributos != null ) {		
			
			// Obtem o tamanho da linha
			tamLinha = quebra.obterTamLinhaDetalheNet(LinhaDTO.TIPO_ITEN_NET_TRIBUTOS_NOTA_FISCAL, 01);
			
			for(TributoDTO tributo: tributos){
				
				LinhaDTO linha = llk.criarLinhaTributo(tributo, tamLinha);
				
				setorTributosNet.addLinha(linha);
				
				contLinhas += tamLinha;
			
			milimetrosOcupados += getTamanhoLinhaNF(LinhaDTO.TIPO_ITEN_NET_TRIBUTOS_NOTA_FISCAL);
			
			}
			
		}
		
		// Obtem o tamanho da linha
//		tamLinha = quebra.obterTamLinhaDetalheNet(LinhaDTO.TIPO_ITEN_BLANK_LINE, 01);
		
		Map mapReservadoFisicoCodigo = new HashMap<String,String>();
		mapReservadoFisicoCodigo.put(
				PrintHouseConstants.LayoutArquivoPrintHouse.HASH_CODE,
				clienteNotaFiscalPrintDTO.getHashCode());
		
//		setorTributosNet.addLinha(new LinhaDTO(LinhaDTO.TIPO_ITEN_BLANK_LINE
//				, new HashMap<String, String>()
//				, tamLinha));
//		
//		contLinhas += tamLinha;
		
		// Obtem o tamanho da linha
		tamLinha = quebra.obterTamLinhaDetalheNet(LinhaDTO.TIPO_ITEN_NET_RESERVADO_FISCO_LABEL, 01);
		
		setorTributosNet.addLinha(new LinhaDTO(
				LinhaDTO.TIPO_ITEN_NET_RESERVADO_FISCO_LABEL
				, new HashMap<String, String>()
				, tamLinha));
		
		contLinhas += tamLinha;
	
		milimetrosOcupados += getTamanhoLinhaNF(LinhaDTO.TIPO_ITEN_NET_RESERVADO_FISCO_LABEL);
			
		// Obtem o tamanho da linha
		tamLinha = quebra.obterTamLinhaDetalheNet(LinhaDTO.TIPO_ITEN_NET_RESERVADO_FISCO_CODIGO, 01);
		
		setorTributosNet.addLinha(new LinhaDTO(
				LinhaDTO.TIPO_ITEN_NET_RESERVADO_FISCO_CODIGO
				, mapReservadoFisicoCodigo
				, tamLinha));
		
		contLinhas += tamLinha;	
		milimetrosOcupados += getTamanhoLinhaNF(LinhaDTO.TIPO_ITEN_NET_RESERVADO_FISCO_CODIGO);
		
		// Adiciona a mensagem de totais de tributos para NF NET
		if (clienteNotaFiscalPrintDTO.getMsgTotaisTributosNET() != null) {
			Map mapMensagemTotaisNet = new HashMap<String,String>();
			mapMensagemTotaisNet.put(
					PrintHouseConstants.LayoutArquivoPrintHouse.TOTAISIMPOSTOSNET,
					clienteNotaFiscalPrintDTO.getMsgTotaisTributosNET());
			
			// Obtem o tamanho da linha
			tamLinha = quebra.obterTamLinhaDetalheNet(LinhaDTO.TIPO_ITEN_NET_MENSAGEM_RODAPE, 01);
			
			setorTributosNet.addLinha(new LinhaDTO(
					LinhaDTO.TIPO_ITEN_NET_MENSAGEM_RODAPE
					, mapMensagemTotaisNet
					, tamLinha));
			
			contLinhas += tamLinha;	
			milimetrosOcupados += getTamanhoLinhaNF(LinhaDTO.TIPO_ITEN_NET_MENSAGEM_RODAPE);
		}
		List<MensagensNotaFiscalDTO> mensagensNotaFiscal = clienteNotaFiscalPrintDTO.getMensagensNotaFiscal();
		if (!CollectionUtils.isEmpty(mensagensNotaFiscal)){
			for(MensagensNotaFiscalDTO msg: mensagensNotaFiscal){
				tamLinha = quebra.obterTamLinhaDetalheNet(LinhaDTO.TIPO_MENSAGEM_NOTA_FISCAL, 01);
				setorTributosNet.addLinha(llk.criarLinhaMensagemNota(msg, tamLinha));
				contLinhas += tamLinha;	
				milimetrosOcupados += getTamanhoLinhaNF(LinhaDTO.TIPO_ITEN_NET_MENSAGEM_RODAPE);
			}
		}

		//Caso a NF caiba no tijolinho
		if (milimetrosOcupados <= 59 && qdeNFNET == 1) {
			//Definimos todas linhas como de tamanho zero, para nao ocupar pagina extra no caso de auto-envelopado. 
			for (int i=0; i<setorTributosNet.getQuantidadeLinhasSetor().intValue(); i++) {
				LinhaDTO linhaDTO = setorTributosNet.get(i);
				linhaDTO.setQuantidadeOcupa(0);
			}
			for (int j = 0; j < setorFaturaNet.getQuantidadeLinhasSetor().intValue(); j++) {
				LinhaDTO linhaDTO = setorFaturaNet.get(j);
				linhaDTO.setQuantidadeOcupa(0);
			}
		}
		
		return contLinhas;
	}

	/**
	 * Cria objeto SetorDTO do setor de demonstrativo financeiro e ficha de
	 * arrecadacao da primeira via da nota fiscal.
	 * 
	 * @version 1.0 - 19/04/2006 - Criacao
	 * @author Everton Ken Tomita - implementação Marcio Bellini - refactor
	 *         Robin Michael Gray
	 * @number RF015_RF021
	 * 
	 * @param ClientePrintBean
	 *            que contem informacoes para montar a linha do tipo 13
	 * @return Cria um novo setor de demonstrativo financeiro e ficha de
	 *         arrecadacao
	 * 
	 * @semantics	
	 */
	private SetorDTO criarSetorDetalheLigacoesNETEBT(
			ClientePrintDTO clientePrintDTO,FaturaNetDTO fatura) { 

		Map mapClientePrintDTO = new HashMap<String,String>();
		String branco = " ";
		mapClientePrintDTO.put(PrintHouseConstants.EBT.NOMECLIENTE,notNullObject(clientePrintDTO.getNomeClienteNotaFiscal()));
		if(clientePrintDTO.getCodigoClienteEBT() != null && !(clientePrintDTO.getCodigoClienteEBT().trim().equals(""))){
			mapClientePrintDTO.put(PrintHouseConstants.EBT.CODIGOCLIENTEEBT,notNullObject(StringUtils.prepad(clientePrintDTO.getCodigoClienteEBT(),15,'0')));
		}else{
			mapClientePrintDTO.put(PrintHouseConstants.EBT.CODIGOCLIENTEEBT,"000000000000000");
		}
		//StringUtils.prepad(dadosGeraisPrimeiraViaPrintDTO.getCodigoClienteNET(),9,'0')));
		mapClientePrintDTO.put(PrintHouseConstants.EBT.NUMEROFATURA,notNullObject(fatura.getNumFaturaParceiro()));
		mapClientePrintDTO.put(PrintHouseConstants.EBT.NOMESERVICO,notNullObject(clientePrintDTO.getDescServico()));
		mapClientePrintDTO.put(PrintHouseConstants.EBT.NUMEROTELEFONE,notNullObject(clientePrintDTO.getDescServico()));
				
		Collection<LinhaDTO> cabecalhoQuebraPagina = new ArrayList<LinhaDTO>();
		//cabecalhoQuebraPagina.add(new LinhaDTO(LinhaDTO.TIPO_INICIO_PRODUTO_EBT));
		cabecalhoQuebraPagina.add(new LinhaDTO(LinhaDTO.TIPO_ITEN_INICIO_PAGINA));
		cabecalhoQuebraPagina.add(new LinhaDTO(LinhaDTO.TIPO_ITEN_EBT_CABECALHO, mapClientePrintDTO));
		cabecalhoQuebraPagina.add(new LinhaDTO(LinhaDTO.TIPO_GRUPO_PARCEIRO_NF));

		/*Map mapNomeColuna1ServEBT = new HashMap();
		mapNomeColuna1ServEBT.put(PrintHouseConstants.EBT.DATAINICIOCOBRANCA, "PERIODO/DA");
		mapNomeColuna1ServEBT.put(PrintHouseConstants.EBT.ESPACOEMBRANCO1, "TA");
		mapNomeColuna1ServEBT.put(PrintHouseConstants.EBT.NUMEROTELEFONE, "TELEFONE");				 
		mapNomeColuna1ServEBT.put(PrintHouseConstants.EBT.LOCALIDADEDESTINO, "LOCAL");
		mapNomeColuna1ServEBT.put(PrintHouseConstants.EBT.HORAINICIO,"HORA");							
		mapNomeColuna1ServEBT.put(PrintHouseConstants.EBT.DURACAO, "DURACAO");			
		mapNomeColuna1ServEBT.put(PrintHouseConstants.EBT.ESPACOEMBRANCO4, branco);
		mapNomeColuna1ServEBT.put(PrintHouseConstants.EBT.ITEMDETALHAMENTOVALOR, "       VALOR (R$)");
		mapNomeColuna1ServEBT.put(PrintHouseConstants.EBT.FORMATACAO, "16");
		cabecalhoQuebraPagina.add(new LinhaDTO(LinhaDTO.TIPO_ITEN_EBT_DETALHAMENTO,mapNomeColuna1ServEBT));

		Map mapNomeColuna2ServEBT = new HashMap();

		mapNomeColuna2ServEBT.put(PrintHouseConstants.EBT.DATAINICIOCOBRANCA, branco);				
		mapNomeColuna2ServEBT.put(PrintHouseConstants.EBT.NUMEROTELEFONE, "DESTINO");				 
		mapNomeColuna2ServEBT.put(PrintHouseConstants.EBT.LOCALIDADEDESTINO, "DESTINO");
		mapNomeColuna2ServEBT.put(PrintHouseConstants.EBT.HORAINICIO, "INICIO");					
		mapNomeColuna2ServEBT.put(PrintHouseConstants.EBT.DURACAO, branco);
		mapNomeColuna2ServEBT.put(PrintHouseConstants.EBT.ESPACOEMBRANCO1, branco);
		mapNomeColuna2ServEBT.put(PrintHouseConstants.EBT.ESPACOEMBRANCO4, branco);
		mapNomeColuna2ServEBT.put(PrintHouseConstants.EBT.ITEMDETALHAMENTOVALOR, branco );
		mapNomeColuna2ServEBT.put(PrintHouseConstants.EBT.FORMATACAO, "16");
		cabecalhoQuebraPagina.add(new LinhaDTO(LinhaDTO.TIPO_ITEN_EBT_DETALHAMENTO,mapNomeColuna2ServEBT));*/
		
		return new SetorDTO(TipoSetorPrint.DETALHES_LIGACOES_NET_FONE,
				new LinhaDTO(LinhaDTO.TIPO_INICIO_PRODUTO_EBT), null,
				cabecalhoQuebraPagina, Boolean.TRUE, Boolean.TRUE);
				
	}
	
	
	private SetorDTO criarSetorNotaFiscalEBT(
			DadosCobrancaParceiroDTO clientePrintDTO) {

		Map mapClientePrintDTO = new HashMap<String,String>();
					
		Map parceiroMap = this.obterMapEBT(clientePrintDTO);
		
		Collection<LinhaDTO> cabecalhoQuebraPagina = new ArrayList<LinhaDTO>();
		cabecalhoQuebraPagina.add(new LinhaDTO(LinhaDTO.TIPO_ITEN_INICIO_PAGINA));		
		cabecalhoQuebraPagina.add(new LinhaDTO(LinhaDTO.TIPO_DADOS_PARCEIRO_NF,parceiroMap));

		return new SetorDTO(TipoSetorPrint.NOTA_FISCAL_EMBRATEL,
				new LinhaDTO(LinhaDTO.TIPO_INICIO_NOTA_FISCAL_EBT), null,
				cabecalhoQuebraPagina, Boolean.TRUE, Boolean.TRUE); 

	}


	private Map obterMapEBT (DadosCobrancaParceiroDTO clientePrintDTO) {
		
		Map parceiroMap = new HashMap();
		parceiroMap.put(PrintHouseConstants.NOTA_FISCAL_EBT.NOMEEMPRESAPARCEIRA, notNullObjectEspaco(clientePrintDTO.getCc_razao_social_oper()));
		parceiroMap.put(PrintHouseConstants.NOTA_FISCAL_EBT.ENDERECO, notNullObjectEspaco(clientePrintDTO.getCc_logradouro_oper()));
		parceiroMap.put(PrintHouseConstants.NOTA_FISCAL_EBT.BAIRRO, notNullObjectEspaco(clientePrintDTO.getCc_bairro_oper()));
		parceiroMap.put(PrintHouseConstants.NOTA_FISCAL_EBT.CIDADE, notNullObjectEspaco(clientePrintDTO.getCc_cidade_oper()));
		parceiroMap.put(PrintHouseConstants.NOTA_FISCAL_EBT.ESTADO, notNullObjectEspaco(clientePrintDTO.getCc_estado_oper()));
		parceiroMap.put(PrintHouseConstants.NOTA_FISCAL_EBT.CEP, notNullObjectEspaco(clientePrintDTO.getCc_cep_oper()));
		parceiroMap.put(PrintHouseConstants.NOTA_FISCAL_EBT.CNPJ, notNullObjectEspaco(clientePrintDTO.getCc_cnpj_oper()));
		parceiroMap.put(PrintHouseConstants.NOTA_FISCAL_EBT.INSCRICAOESTADUAL, notNullObjectEspaco(clientePrintDTO.getCc_inscricao_estadual_oper()));
		parceiroMap.put(PrintHouseConstants.NOTA_FISCAL_EBT.INSCRICAOMUNICIPAL, notNullObjectEspaco(clientePrintDTO.getCc_inscricao_municipal_oper()));

		return parceiroMap;

	}
	
	/**
	 * Adiciona no setor passado como parametro, o setor
	 * 
	 * @version 1.0 - 17/04/2006 - Criacao
	 * @author Everton Ken Tomita - implementação Marcio Bellini - refactor
	 *         Robin Michael Gray
	 * @number RF015_RF021
	 * 
	 * @param SetorDTO
	 *            estara sendo utilizado para incluir as linhas que compoem o
	 *            setor
	 * @param Integer
	 *            do id da nota fiscal
	 * @param ClienteNotaFiscalPrintDTO
	 *            contem informacoes para montar a linha do tipo 18 e 21
	 * @throws JNetException
	 * 
	 * @semantics  
	 */
	private int[] gerarSetorDemonstrativoFichaArrecadacao(
			SetorDTO setorDemFinFicArr
			, Long idBoleto
			, DemonstrativoFinanceiroPrintDTO demonstrativoFinanceiroPrintDTO
			, FichaArrecadacaoPrintDTO fichaArrecadacaoPrintDTO,Double vlrTotalNF
			, String cidContrato
			, String boletoConsolidado
			, FaturaNetDTO fatura
			, int contLinhas
			) {


		contLinhas = 0;
		
		QuebraAux quebra = this.new QuebraAux();
		quebra.setQuebrouPagina(false);
		quebra.setTamLinha(0);

		int linhasFichaArrecadacao =  QT_LN_PAG_FAT_PRINT;
		linhasFichaArrecadacao *= 100; // multiplica por 100 para eliminar o decimal

		Map mapDemonstrativoFinanceiroDTO = new HashMap<String,String>();
		Map mapFichaArrecadacaoDTO = new HashMap<String,String>();

		DynamicBean b = new DynamicBean();
		b.set("idBoleto", idBoleto);
		b.set("VlTotalNotaFiscal", vlrTotalNF);
		
		Double valorCobrado = null;
		
		if ( demonstrativoFinanceiroPrintDTO.getValorCobrado() <= 0){
			valorCobrado = 0.0 ;
		} else {
			valorCobrado = demonstrativoFinanceiroPrintDTO.getValorCobrado();
		}
		
		mapDemonstrativoFinanceiroDTO.put(
				PrintHouseConstants.LayoutArquivoPrintHouse.VALOR_COBRADO,
				valorCobrado);
		mapFichaArrecadacaoDTO.put(
				PrintHouseConstants.LayoutArquivoPrintHouse.CODIGO_DE_BARRAS,
				fichaArrecadacaoPrintDTO.getCodigoDeBarras());
		mapFichaArrecadacaoDTO.put(
				PrintHouseConstants.LayoutArquivoPrintHouse.DATA_VENCIMENTO_BOLETO, 
				fichaArrecadacaoPrintDTO.getDataVencimentoBoleto());
		mapFichaArrecadacaoDTO.put(
				PrintHouseConstants.LayoutArquivoPrintHouse.LINHA_DIGITAVEL,
				fichaArrecadacaoPrintDTO.getLinhaDigitavel());
		//QRCODE PIX
		mapFichaArrecadacaoDTO.put( 
				PrintHouseConstants.LayoutArquivoPrintHouse.PIX_HASHCODE,
				fichaArrecadacaoPrintDTO.getPixHashcode());

		mapFichaArrecadacaoDTO.put( 
				PrintHouseConstants.LayoutArquivoPrintHouse.MES_ANO_REFERENCIA,
				fichaArrecadacaoPrintDTO.getMesAnoReferencia());
		mapFichaArrecadacaoDTO.put(
				PrintHouseConstants.LayoutArquivoPrintHouse.NOME_CLIENTE_COBRANCA,
				fichaArrecadacaoPrintDTO.getNomeClienteCobranca());
		mapFichaArrecadacaoDTO.put(
				PrintHouseConstants.LayoutArquivoPrintHouse.VALOR_COBRADO,
				valorCobrado);
		
		//FebrabamDTO  beanParam = new FebrabamDTO();  
		//beanParam.setIdBoleto(idBoleto);
		//FebrabamDTO  codFebrabam = (super.getDadosBeanByQuery("listarCodFebraban", beanParam, false, FebrabamDTO.class).get(0));
		
		String operadora = StringUtils.prepad(demonstrativoFinanceiroPrintDTO.getCodOperadora(),3,'0');		 
		String numContrato = StringUtils.prepad(demonstrativoFinanceiroPrintDTO.getCodigoClienteNET(), 9, '0');
		//<NSMSM_922415_CI_001 - jnunes@artit.com.br
        String param = StringUtils.prepad(demonstrativoFinanceiroPrintDTO.getCodigoClienteNET(), 9, '0');
        //</NSMSM_922415_CI_001 - jnunes@artit.com.br
		
		mapFichaArrecadacaoDTO.put(
				PrintHouseConstants.LayoutArquivoPrintHouse.CODIGO_CLIENTE_NET,
				notNullString(operadora+numContrato+String.valueOf(this.geraDigVerificador(param))));
		
		setorDemFinFicArr.addLinha(new LinhaDTO(LinhaDTO.TIPO_ITEN_CABECALHO_DEMONSTRATIVO_FINACEIRO
				, new HashMap<String, String>()
				, 0
				));

//		setorDemFinFicArr.addLinha(new LinhaDTO(LinhaDTO.TIPO_ITEN_BLANK_LINE
//				, new HashMap<String, String>()
//				, 0
//				));
	
		List<DemonstrativoFinanceiroDTO> listDemonstrativo = null;
		
		if(fatura != null && fatura.getDemonstrativoFinanceiro() != null) {
			listDemonstrativo = fatura.getDemonstrativoFinanceiro();
		}
		
		DemonstrativoFinanceiroDTO valorAnterior=null;
		
		DemonstrativoFinanceiroDTO valorAnteriorServicosPeriodo=null;
		
		double totalSubGrupoAlterProd = 0;
		
		double totalSubGrupo = 0;
		
		double totalGrupo = 0;
		
		double totalServicosPeriodo = 0;
		
		int paginaAtual = 1;
		
		//Armazena os itens eventuais para jogar no fim do demonstrativo financeiro
		List<LinhaDTO> itensEventuaisTemp = new ArrayList<LinhaDTO>();
		//Armazena o sub Grupo Alteração produto
		DemonstrativoFinanceiroDTO subGrupoAterProdHeader = null;
		
		String KEY_GROUPO = "GRUPO_DEMONST_FINANC";
		String KEY_SUBGRUPO = "SUBGRUPOGRUPO_DEMONST_FINANC";
		String KEY_DESCRICAO = "DESCRICAOITEMDEMONST_FINANC";
		String KEY_VALOR = "VALORITEMDEMONST_FINANC";
		String KEY_ID_BLTO = "ID_BLTO";
		
		//Tipo de Cabeçalho a ser criado para Sub Grupo Alteração de Produto 
		int header = 1;
		int footer = 2;
		
		//Solucao para representacao numerica na memoria de java.
		//Correcao de zero quando valor aparecia com zero seguido de sinal negativo ("-0")
		NumberFormat format = NumberFormat.getInstance();
		
		if ( listDemonstrativo != null ) {
			for(DemonstrativoFinanceiroDTO item : listDemonstrativo ){
				
				String[] registroAnterior = null; 
				if (valorAnterior != null) { 
					registroAnterior = new String[2];
					registroAnterior[0] = valorAnterior.getGRUPO_DEMONST_FINANC();
					registroAnterior[1] = valorAnterior.getSUBGRUPO_DEMONST_FINANC();
				}
				String[] registroAtual = new String[]{ item.getGRUPO_DEMONST_FINANC(), item.getSUBGRUPO_DEMONST_FINANC() };
	
				//Serviços do periodo
				if (registroAnterior == null && (registroAtual == null || registroAtual[0] == null || registroAtual[0].trim().equals(""))) {
					
					if(item.getVALORITEMDEMONST_FINANC() != null){
						totalServicosPeriodo += ((Double)item.getVALORITEMDEMONST_FINANC()).doubleValue();
					}
					
					valorAnteriorServicosPeriodo =  new DemonstrativoFinanceiroDTO();
					valorAnteriorServicosPeriodo.setDESCRICAOITEMDEMONST_FINANC(item.getDESCRICAOITEMDEMONST_FINANC());
					valorAnteriorServicosPeriodo.setVALORITEMDEMONST_FINANC(item.getVALORITEMDEMONST_FINANC());
					valorAnteriorServicosPeriodo.setGRUPO_DEMONST_FINANC(item.getGRUPO_DEMONST_FINANC());
					valorAnteriorServicosPeriodo.setSUBGRUPO_DEMONST_FINANC(item.getSUBGRUPO_DEMONST_FINANC());
					
	//				this.addLinha(LinhaDTO.TIPO_ITEN_ITEN_DEMONSTRATIVO_FINACEIRO.intValue(), valor);
				} else {
				
					if (registroAnterior == null ) {
						//Add o somar de serviçõs de periodo
						if (valorAnteriorServicosPeriodo != null) {
							
							if (true) throw new RuntimeException("Grupo vazio");
						}							
												
												
						//HEADER GRUPO
						DemonstrativoFinanceiroDTO valorCopia = new DemonstrativoFinanceiroDTO();
						//valorCopia.setVALORITEMDEMONST_FINANC();
						valorCopia.setGRUPO_DEMONST_FINANC(item.getGRUPO_DEMONST_FINANC());					
						valorCopia.setSUBGRUPO_DEMONST_FINANC(item.getSUBGRUPO_DEMONST_FINANC());
						valorCopia.setDESCRICAOITEMDEMONST_FINANC(valorCopia.getGRUPO_DEMONST_FINANC());
						String nomeGrupo = item.getGRUPO_DEMONST_FINANC();
						LinhaDTO tempLinha = llk.criarLinhaDemonstrativoFinanceiroTitulo(LinhaDTO.TIPO_ITEN_CABECALHO_AGRUPAMENTO_DEMO_FINACEIRO.intValue()
								, valorCopia
											, 700);
						int [] aux = adicionarItem(item.getGRUPO_DEMONST_FINANC(), tempLinha, setorDemFinFicArr, itensEventuaisTemp, contLinhas, paginaAtual, linhasFichaArrecadacao);
						paginaAtual = aux[0];
						contLinhas = aux[1];
						
						//HEADER SUBITEM
						valorCopia = new DemonstrativoFinanceiroDTO();
						valorCopia.setGRUPO_DEMONST_FINANC(item.getGRUPO_DEMONST_FINANC());					
						valorCopia.setSUBGRUPO_DEMONST_FINANC(item.getSUBGRUPO_DEMONST_FINANC());
						valorCopia.setDESCRICAOITEMDEMONST_FINANC(valorCopia.getSUBGRUPO_DEMONST_FINANC());
						tempLinha = llk.criarLinhaDemonstrativoFinanceiroTitulo(LinhaDTO.TIPO_ITEN_SUBCABECALHO_AGRUPAMENTO_DEMO_FINACEIRO.intValue()
								, valorCopia
								, 400);
						aux = adicionarItem(item.getGRUPO_DEMONST_FINANC(), tempLinha, setorDemFinFicArr, itensEventuaisTemp, contLinhas, paginaAtual, linhasFichaArrecadacao);
						paginaAtual = aux[0];
						contLinhas = aux[1];

					} else if (!(registroAtual[0].equals(registroAnterior[0]) && registroAtual[1].equals(registroAnterior[1]))) {

						//Projeto Cessar Cobrança - Unificação de Pró-ratas
						//Cria Footer do sub Total Alteração Produto, quando existe mais de um sub grupo
						if (subGrupoAterProdHeader != null && subGrupoAterProdHeader.getSubgrupoAlterProd() != null){
							
							if (format.format(totalSubGrupoAlterProd).equals("-0")){
								totalSubGrupoAlterProd= 0;
							}
							setSubGrupoItensEventuais(subGrupoAterProdHeader, totalSubGrupoAlterProd, setorDemFinFicArr, itensEventuaisTemp, contLinhas, paginaAtual, linhasFichaArrecadacao, footer);
							
							//Atribui valor 0 (Zero), para iniciar o proximo Sub grupo 
							totalSubGrupoAlterProd = 0;
							subGrupoAterProdHeader = null;
						}
						
						//FOOTER
						DemonstrativoFinanceiroDTO valorAnteriorCopia = new DemonstrativoFinanceiroDTO();
						valorAnteriorCopia.setDESCRICAOITEMDEMONST_FINANC("Sub-Total " + registroAnterior[1]);
						valorAnteriorCopia.setGRUPO_DEMONST_FINANC("");
						valorAnteriorCopia.setSUBGRUPO_DEMONST_FINANC("");
						valorAnteriorCopia.setVALORITEMDEMONST_FINANC(new Double(totalSubGrupo));
						LinhaDTO tempLinha = llk.criarLinhaDemonstrativoFinanceiro(LinhaDTO.TIPO_ITEN_SUBRODAPE_AGRUPAMENTO_DEMO_FINACEIRO.intValue()
								, valorAnteriorCopia
								, 400);
						int[] aux = adicionarItem(registroAnterior[0], tempLinha, setorDemFinFicArr, itensEventuaisTemp, contLinhas, paginaAtual, linhasFichaArrecadacao);
						paginaAtual = aux[0];
						contLinhas = aux[1];

						totalSubGrupo = 0;

						//Item de grupo diferente do anterior
						if (!(registroAtual[0].equals(registroAnterior[0]))) {
							//FOOTER TOTALIZACAO DO GRUPO
							valorAnteriorCopia = new DemonstrativoFinanceiroDTO();
							valorAnteriorCopia.setDESCRICAOITEMDEMONST_FINANC("Total " + registroAnterior[0]);
							valorAnteriorCopia.setGRUPO_DEMONST_FINANC("");
							valorAnteriorCopia.setSUBGRUPO_DEMONST_FINANC("");
							valorAnteriorCopia.setVALORITEMDEMONST_FINANC(new Double(totalGrupo));
										tempLinha = llk.criarLinhaDemonstrativoFinanceiro(LinhaDTO.TIPO_ITEN_RODAPE_AGRUPAMENTO_DEMO_FINACEIRO.intValue()
									, valorAnteriorCopia
								, 400);
							aux = adicionarItem(registroAnterior[0], tempLinha, setorDemFinFicArr, itensEventuaisTemp, contLinhas, paginaAtual, linhasFichaArrecadacao);
							paginaAtual = aux[0];
							contLinhas = aux[1];
							
							totalGrupo = 0;
							
							//HEADER de GRUPO
							DemonstrativoFinanceiroDTO valorCopia = new DemonstrativoFinanceiroDTO();
							valorCopia.setDESCRICAOITEMDEMONST_FINANC(item.getGRUPO_DEMONST_FINANC());
							valorCopia.setGRUPO_DEMONST_FINANC(item.getGRUPO_DEMONST_FINANC());
							valorCopia.setSUBGRUPO_DEMONST_FINANC(item.getSUBGRUPO_DEMONST_FINANC());
							tempLinha = llk.criarLinhaDemonstrativoFinanceiroTitulo(LinhaDTO.TIPO_ITEN_CABECALHO_AGRUPAMENTO_DEMO_FINACEIRO.intValue()
									, valorCopia
									, 700);
							aux = adicionarItem(item.getGRUPO_DEMONST_FINANC(), tempLinha, setorDemFinFicArr, itensEventuaisTemp, contLinhas, paginaAtual, linhasFichaArrecadacao);
							paginaAtual = aux[0];
							contLinhas = aux[1];							
						}
						
						//HEADER DE SUBITEM
						DemonstrativoFinanceiroDTO valorCopia = new DemonstrativoFinanceiroDTO();
						//valorCopia.set(KEY_VALOR, "");
						valorCopia.setDESCRICAOITEMDEMONST_FINANC(item.getSUBGRUPO_DEMONST_FINANC());
						valorCopia.setGRUPO_DEMONST_FINANC(item.getGRUPO_DEMONST_FINANC());
						valorCopia.setSUBGRUPO_DEMONST_FINANC(item.getSUBGRUPO_DEMONST_FINANC());
						tempLinha = llk.criarLinhaDemonstrativoFinanceiroTitulo(LinhaDTO.TIPO_ITEN_SUBCABECALHO_AGRUPAMENTO_DEMO_FINACEIRO.intValue()
								, valorCopia
									, 400);
						aux = adicionarItem(item.getGRUPO_DEMONST_FINANC(), tempLinha, setorDemFinFicArr, itensEventuaisTemp, contLinhas, paginaAtual, linhasFichaArrecadacao);
						paginaAtual = aux[0];
						contLinhas = aux[1];
					}
					
					//Projeto Cessar Cobrança - Unificação de Pró-ratas
					//Cria Header do sub Total Alteração Produto
					if(subGrupoAterProdHeader == null && item.getSubgrupoAlterProd() != null && totalSubGrupoAlterProd == 0){
						
						subGrupoAterProdHeader = new DemonstrativoFinanceiroDTO();
						subGrupoAterProdHeader = setSubGrupoItensEventuais(item, totalSubGrupoAlterProd, setorDemFinFicArr, itensEventuaisTemp, contLinhas, paginaAtual, linhasFichaArrecadacao, header);
					}
					
					//ITEM
					DemonstrativoFinanceiroDTO valor = new DemonstrativoFinanceiroDTO();
					valor.setDESCRICAOITEMDEMONST_FINANC( item.getDESCRICAOITEMDEMONST_FINANC());
					valor.setGRUPO_DEMONST_FINANC("");
					valor.setSUBGRUPO_DEMONST_FINANC("");
					valor.setVALORITEMDEMONST_FINANC(item.getVALORITEMDEMONST_FINANC());
					int tamanhoMilimetros = 400;
					if (item.getTAMANHO() != null && item.getTAMANHO()>TAMANHO_MAX_LINHA_DF) {
						tamanhoMilimetros = 600;
					}
					LinhaDTO tempLinha = llk.criarLinhaDemonstrativoFinanceiro(LinhaDTO.TIPO_ITEN_ITEN_DEMONSTRATIVO_FINACEIRO.intValue()
							, valor
							, tamanhoMilimetros);
					int[] aux = adicionarItem(item.getGRUPO_DEMONST_FINANC(), tempLinha, setorDemFinFicArr, itensEventuaisTemp, contLinhas, paginaAtual, linhasFichaArrecadacao);
					paginaAtual = aux[0];
					contLinhas = aux[1];
					
					valorAnterior = item;
					
					totalSubGrupo += item.getVALORITEMDEMONST_FINANC();					
					totalGrupo += item.getVALORITEMDEMONST_FINANC();
					
					/*Unificação de Pro-rata */
					if (item.getSubgrupoAlterProd()!= null){
						totalSubGrupoAlterProd += item.getVALORITEMDEMONST_FINANC();
					}
				}
			}
			
			if (format.format(totalSubGrupo).equals("-0")){
				totalSubGrupo= 0;
			}
			if (format.format(totalGrupo).equals("-0")){
				totalGrupo= 0;
			}
			if (format.format(totalSubGrupoAlterProd).equals("-0")){
				totalSubGrupoAlterProd= 0;
			}
			
			if (valorAnteriorServicosPeriodo != null) {

					if (true) throw new RuntimeException("Grupo Vazio.");
			}
							
			if (valorAnterior != null) {
				
				//Projeto Cessar Cobrança - Unificação de Pró-ratas
				//Cria Footer do sub Total Alteração Produto
				if (subGrupoAterProdHeader != null && valorAnterior.getSubgrupoAlterProd() != null){
					setSubGrupoItensEventuais(subGrupoAterProdHeader, totalSubGrupoAlterProd, setorDemFinFicArr, itensEventuaisTemp, contLinhas, paginaAtual, linhasFichaArrecadacao, footer);
				}
				
				//FOOTER SUBITEM
				String registroAnterior = valorAnterior.getSUBGRUPO_DEMONST_FINANC();
				DemonstrativoFinanceiroDTO valorAnteriorCopia = new DemonstrativoFinanceiroDTO();
				valorAnteriorCopia.setGRUPO_DEMONST_FINANC(valorAnterior.getGRUPO_DEMONST_FINANC());
				valorAnteriorCopia.setSUBGRUPO_DEMONST_FINANC(valorAnterior.getSUBGRUPO_DEMONST_FINANC());
				valorAnteriorCopia.setDESCRICAOITEMDEMONST_FINANC("Sub-Total " + registroAnterior);
				valorAnteriorCopia.setVALORITEMDEMONST_FINANC(new Double(totalSubGrupo));
				LinhaDTO tempLinha = llk.criarLinhaDemonstrativoFinanceiro(LinhaDTO.TIPO_ITEN_SUBRODAPE_AGRUPAMENTO_DEMO_FINACEIRO.intValue()
						, valorAnteriorCopia
						, 400);
				int[] aux = adicionarItem(valorAnterior.getGRUPO_DEMONST_FINANC(), tempLinha, setorDemFinFicArr, itensEventuaisTemp, contLinhas, paginaAtual, linhasFichaArrecadacao);
				paginaAtual = aux[0];
				contLinhas = aux[1];
				
				//FOOTER GRUPO
				registroAnterior = valorAnterior.getGRUPO_DEMONST_FINANC();
				valorAnteriorCopia = new DemonstrativoFinanceiroDTO();
				valorAnteriorCopia.setGRUPO_DEMONST_FINANC(valorAnterior.getGRUPO_DEMONST_FINANC());
				valorAnteriorCopia.setSUBGRUPO_DEMONST_FINANC(valorAnterior.getSUBGRUPO_DEMONST_FINANC());
				valorAnteriorCopia.setDESCRICAOITEMDEMONST_FINANC("Total " + registroAnterior);
				valorAnteriorCopia.setVALORITEMDEMONST_FINANC(new Double(totalGrupo));
				tempLinha = llk.criarLinhaDemonstrativoFinanceiro(LinhaDTO.TIPO_ITEN_RODAPE_AGRUPAMENTO_DEMO_FINACEIRO.intValue()
						, valorAnteriorCopia
						, 400);
				aux = adicionarItem(valorAnterior.getGRUPO_DEMONST_FINANC(), tempLinha, setorDemFinFicArr, itensEventuaisTemp, contLinhas, paginaAtual, linhasFichaArrecadacao);
				paginaAtual = aux[0];
				contLinhas = aux[1];

				totalSubGrupo = 0;
				
			} 
		
		}
		
		if ( boletoConsolidado.equalsIgnoreCase("C") || boletoConsolidado.equalsIgnoreCase("P") || 
				boletoConsolidado.equalsIgnoreCase("A")	|| boletoConsolidado.equalsIgnoreCase("B") || 
				( boletoConsolidado.equalsIgnoreCase("D") && demonstrativoFinanceiroPrintDTO.isOperadoraNet() == false) ) {		
			 
			List<DemonstrativoFinanceiroParceiroDTO> dFparceirosList = null;
			if (fatura != null && fatura.getDemostrativoFinanceiroParceiro()!= null) {
				dFparceirosList = fatura.getDemostrativoFinanceiroParceiro();
			}
			
			String vlrAnterior = null;
			Double somaVlr = 0.0;		
			String nomeGrupo = "";
			int iteracao = 0;
			
			if(dFparceirosList != null) {
				Boolean gerarSegTit = Boolean.TRUE;		
				for(DemonstrativoFinanceiroParceiroDTO item : dFparceirosList ){
					
					if ( vlrAnterior == null) {	
						nomeGrupo = item.getGRUPO_DEMONST_FINANC(); //Nome do grupo para utilizar no SUB-TOTAL
						
						Map valor = new HashMap();
						valor.put(KEY_VALOR, "");
						valor.put(KEY_DESCRICAO, item.getGRUPO_DEMONST_FINANC() ); 
						LinhaDTO linha = new LinhaDTO(LinhaDTO.TIPO_ITEN_CABECALHO_AGRUPAMENTO_DEMO_FINACEIRO.intValue()
								, valor
								, 700
								);
						int[] aux = adicionarItem(nomeGrupo, linha, setorDemFinFicArr, itensEventuaisTemp, contLinhas, paginaAtual, linhasFichaArrecadacao);
						paginaAtual = aux[0];
						contLinhas = aux[1];
						
						//Será criado a linha com informação de Duração apenas para Itens da Embratel.
						if(gerarSegTit && (nomeGrupo.toUpperCase().startsWith(NET_FONE) || nomeGrupo.toUpperCase().startsWith(CLARO_FONE))) {
							Map valor2 = new HashMap();
							valor2.put(KEY_VALOR, "");
							valor2.put(KEY_DESCRICAO, StringUtils.postpad("SERVIÇO",80,' ')+StringUtils.postpad("DURAÇÃO",25,' '));
							LinhaDTO linha2 = new LinhaDTO(LinhaDTO.TIPO_ITEN_ITEN_DEMONSTRATIVO_FINACEIRO.intValue()
									, valor2
									, 400
							);
							aux = adicionarItem(nomeGrupo, linha2, setorDemFinFicArr, itensEventuaisTemp, contLinhas, paginaAtual, linhasFichaArrecadacao);
							paginaAtual = aux[0];
							contLinhas = aux[1];
							gerarSegTit = Boolean.FALSE;
						}
									
						vlrAnterior = item.getGRUPO_DEMONST_FINANC();
					}
					
					if(vlrAnterior.equalsIgnoreCase(item.getGRUPO_DEMONST_FINANC())){
						Map valor = new HashMap(); 
						valor.put(KEY_DESCRICAO, StringUtils.postpad(item.getDESCRICAOITEMDEMONST_FINANC()== null ? " ":item.getDESCRICAOITEMDEMONST_FINANC(),80,' ') + StringUtils.postpad(item.getDURACAO() == null ? " ":item.getDURACAO(),25,' '));
						valor.put(KEY_VALOR, item.getVALORITEMDEMONST_FINANC());
						LinhaDTO linha = new LinhaDTO(LinhaDTO.TIPO_ITEN_ITEN_DEMONSTRATIVO_FINACEIRO.intValue()
								, valor
								, 400
								);
						int[] aux = adicionarItem(nomeGrupo, linha, setorDemFinFicArr, itensEventuaisTemp, contLinhas, paginaAtual, linhasFichaArrecadacao);
						paginaAtual = aux[0];
						contLinhas = aux[1];

						vlrAnterior = item.getGRUPO_DEMONST_FINANC();
											
					}
					//Soma total dos itens, para adicionar no Sub Total
					somaVlr = somaVlr +  item.getVALORITEMDEMONST_FINANC();
					iteracao++;
					//A cada final de um determinado tipo, onde a lista tem que vir ordenada pelo seu tipo, será criado o Sub Total.
					if(vlrAnterior != null &&
							 (iteracao == dFparceirosList.size() || (iteracao < dFparceirosList.size() && 
									 !vlrAnterior.equalsIgnoreCase(((DemonstrativoFinanceiroParceiroDTO)dFparceirosList.get(iteracao)).getGRUPO_DEMONST_FINANC())))){
						Map valorAnteriorCopia = new HashMap();
						valorAnteriorCopia.put(KEY_DESCRICAO, "Total " + nomeGrupo);
						valorAnteriorCopia.put(KEY_VALOR, somaVlr);
						LinhaDTO linha = new LinhaDTO(LinhaDTO.TIPO_ITEN_RODAPE_AGRUPAMENTO_DEMO_FINACEIRO.intValue()
								, valorAnteriorCopia
								, 400);
						int[] aux = adicionarItem(nomeGrupo, linha, setorDemFinFicArr, itensEventuaisTemp, contLinhas, paginaAtual, linhasFichaArrecadacao);
						paginaAtual = aux[0];
						contLinhas = aux[1];
						somaVlr = 0.0;	
						vlrAnterior = null;
					}									
				}			
		    }
		}
	
		//Coloca  os "itens eventuais" no fim do demonstrativo
			int[] aux = ajustaItensEventuais(setorDemFinFicArr, itensEventuaisTemp, contLinhas, paginaAtual, linhasFichaArrecadacao);
		paginaAtual = aux[0];
		contLinhas = aux[1];
					
//		setorDemFinFicArr.addLinha(new LinhaDTO(LinhaDTO.TIPO_ITEN_BLANK_LINE
//				, new HashMap<String, String>()
//				, 0));
		
		if(valorCobrado <= 0) { 
			
			Map mapDemonstrativoFinanceiroZeroDTO = new HashMap<String,String>();
			
			mapDemonstrativoFinanceiroZeroDTO.put("VALORCOBRADO", "0");
			setorDemFinFicArr.addLinha(new LinhaDTO(
					LinhaDTO.TIPO_ITEN_TOTAL_DEMONSTRATIVO_FINACEIRO
					, mapDemonstrativoFinanceiroZeroDTO
					, 0));
			
		} else {
			
			setorDemFinFicArr.addLinha(new LinhaDTO(
					LinhaDTO.TIPO_ITEN_TOTAL_DEMONSTRATIVO_FINACEIRO
					, mapDemonstrativoFinanceiroDTO
					, 0));
			
		}

		setorDemFinFicArr.addLinha(new LinhaDTO(
				LinhaDTO.TIPO_ITEN_FICHA_ARRECADACAO, mapFichaArrecadacaoDTO,
				0));//nao quebra pagina nele, pois depois vem a NF e ela sempre quebra pagina

		//NSMSM_50206_CI_001 - Atribuicao a variavel de controle caso existam setor de Itens Eventuais na Fatura
		if (itensEventuaisTemp != null && !itensEventuaisTemp.isEmpty()) {
			existsItensEventuais = true;
		}
		else
		{
			existsItensEventuais = false;
		}
		
		

		
		return new int[]{paginaAtual, contLinhas};
	}
	
	/*private void gerarSubTotalDemonsParceiro(){
		
		Map valorAnteriorCopia = new HashMap();
		valorAnteriorCopia.put(KEY_DESCRICAO, "Total " + nomeGrupo);
		valorAnteriorCopia.put(KEY_VALOR, somaVlr);
		LinhaDTO linha = new LinhaDTO(LinhaDTO.TIPO_ITEN_RODAPE_AGRUPAMENTO_DEMO_FINACEIRO.intValue()
				, valorAnteriorCopia
				, 400);
		int[] aux = adicionarItem(nomeGrupo, linha, setorDemFinFicArr, itensEventuaisTemp, contLinhas, paginaAtual, linhasFichaArrecadacao);
		paginaAtual = aux[0];
		contLinhas = aux[1];
		somaVlr = 0.0;	
		vlrAnterior = null;
		
	}*/

	/**
	 * Método responsável por ordenar a Collection de LoteBoletoFaturaDTO pelo
	 * atributo do CEP
	 * 
	 * @author Harrisson Ferreira Gomes/Everton Ken Tomita - implementação
	 *         Marcio Bellini - refactor Robin Michael Gray
	 * @version 1.0 - criação do Metodo - 27/03/2006
	 * @number RF015_RF021
	 * @param Collection
	 *            loteBoletoFaturaDTO
	 * @semantics <br>
	 *            Deve ordenar a Collection de LoteBoletoFaturaDTO, pelo
	 *            atributo cepCobranca em ordem crescente.
	 * 
	 */
	private void ordenarFaturaPeloCepCobranca(
			Collection<LoteBoletoFaturaDTO> lotesBoletoFaturaDTO) {

		List<LoteBoletoFaturaDTO> loteSemCep = new ArrayList<LoteBoletoFaturaDTO>();
		for (LoteBoletoFaturaDTO lote : lotesBoletoFaturaDTO) {
			if (lote.getCepCobranca() == null) {		
				 lote.setCepCobranca("00000000");  // buscar no parametro
			}
		}
		

		Collections.sort(new ArrayList<LoteBoletoFaturaDTO>(
				lotesBoletoFaturaDTO), new Comparator<LoteBoletoFaturaDTO>() {
			public int compare(LoteBoletoFaturaDTO o1, LoteBoletoFaturaDTO o2) {

				String cepCobranca1 = ((LoteBoletoFaturaDTO) o1)
						.getCepCobranca();
				String cepCobranca2 = ((LoteBoletoFaturaDTO) o2)
						.getCepCobranca();

				return (-1 * (cepCobranca1.compareTo(cepCobranca2)));

			}
		});
	}

	/**
	 * Método responsável por atualizar a Situação do Lote.
	 * 
	 * @author Harrisson Ferreira Gomes/Everton Ken Tomita - implementação
	 *         Marcio Bellini - refactor Robin Michael Gray
	 * @version 1.0 - criação do Metodo - 27/03/2006
	 * @number RF015_RF021
	 * @return
	 * @param Lote
	 *            lote
	 * @param String
	 *            chave
	 * @semantics <br>
	 *
	 * @ejb.interface-method view-type = "both"
     * @ejb.transaction type="RequiresNew" 
     *  
	 * 
	 */
	public SnLoteBean atualizarSituacaoLote(SnLoteBean lote,
			String chave) {

		SnSituacaoProcessamentoBean situacaoProcessamento = new SnSituacaoProcessamentoBean();
		situacaoProcessamento.setSgSituacaoProcessamento(chave);
		
		situacaoProcessamento = (SnSituacaoProcessamentoBean) super.search("lstSnSituacaoProcessamentoBySigla",situacaoProcessamento).get(0);		
		lote = (SnLoteBean)find(lote);		
		lote.setSituacaoProcessamento(situacaoProcessamento);		
		this.update(lote,getUserSession().getCurrentDbService());		
		return lote;
		

	}


	/**
	 * Cria objeto SetorDTO do setor de demonstrativo financeiro e ficha de
	 * arrecadacao da segunda via da nota fiscal.
	 * 
	 * @version 1.0 - 25/04/2006 - Criacao
	 * @author Everton Ken Tomita - refactor Robin Michael Gray
	 * @number RF015_RF021
	 * 
	 * @param ClienteSegundaViaPrintBean
	 *            que contem informacoes para montar a linha do tipo 7
	 * @return Cria um novo setor de demonstrativo financeiro e ficha de
	 *         arrecadacao
	 * 
	 * @semantics
	 * Retornar setor criado
	 */
	private SetorDTO criarSetorDemonstrativoFinaceiroFichaArrecadacaoSegundaVia(
			ClienteSegundaViaPrintDTO clienteSegundaViaPrintDTO) {

		Map map = new HashMap<String,String>();
		map.put(PrintHouseConstants.LayoutArquivoPrintHouse.CODIGO_CLIENTE_NET,
				notNullString(clienteSegundaViaPrintDTO.getCodigoClienteNETFormatado()));
		map.put(PrintHouseConstants.LayoutArquivoPrintHouse.CPF_CNPJ_COBRANCA,
				notNullObject(clienteSegundaViaPrintDTO.getCpfCNPJCobranca()));
		map
				.put(
						PrintHouseConstants.LayoutArquivoPrintHouse.DATA_PROCESSAMENTO_BOLETO,
						notNullObject(clienteSegundaViaPrintDTO
								.getDataProcessamentoBoleto()));
		map
				.put(
						PrintHouseConstants.LayoutArquivoPrintHouse.DATA_VENCIMENTO_BOLETO,
						notNullObject(clienteSegundaViaPrintDTO
								.getDataVencimentoBoleto()));
		map
				.put(
						PrintHouseConstants.LayoutArquivoPrintHouse.DATA_VENCIMENTO_BOLETO_ORIGEM,
						notNullObject(clienteSegundaViaPrintDTO
								.getDataVencimentoBoletoOrigem()));
		map
				.put(
						PrintHouseConstants.LayoutArquivoPrintHouse.IDENTIFICADOR_BOLETO,
						notNullObject(clienteSegundaViaPrintDTO
								.getIdentificadorBoleto()));
		if (clienteSegundaViaPrintDTO.getInscricaoEstadualCobranca() == null) {
			map
					.put(
							PrintHouseConstants.LayoutArquivoPrintHouse.INSCRICAO_ESTADUAL_COBRANCA,
							PrintHouseConstants.NotaFiscal.IE_ISENTO);
		} else {
			map
					.put(
							PrintHouseConstants.LayoutArquivoPrintHouse.INSCRICAO_ESTADUAL_COBRANCA,
							notNullObject(clienteSegundaViaPrintDTO
									.getInscricaoEstadualCobranca()));
		}
		
		/*
		linha digitavel
		codigo de barra
		valorcobrado
		*/
	
		//QRCODE PIX
		map.put(PrintHouseConstants.LayoutArquivoPrintHouse.PIX_HASHCODE, notNullObject(clienteSegundaViaPrintDTO.getPixHashcode()));

		map.put(PrintHouseConstants.LayoutArquivoPrintHouse.LINHA_DIGITAVEL, notNullObject(clienteSegundaViaPrintDTO.getLinhaDigitavel()));
		map.put(PrintHouseConstants.LayoutArquivoPrintHouse.CODIGO_DE_BARRAS, notNullObject(clienteSegundaViaPrintDTO.getCodigoDeBarras()));
		map.put(PrintHouseConstants.LayoutArquivoPrintHouse.VALOR_COBRADO, notNullObject(clienteSegundaViaPrintDTO.getValorCobrado()));
		
		List<LinhaDTO> cabecalhoQuebraPagina = new ArrayList<LinhaDTO>();
		cabecalhoQuebraPagina.add(0, new LinhaDTO(LinhaDTO.TIPO_ITEN_INICIO_PAGINA));
		cabecalhoQuebraPagina.add(1, new LinhaDTO(LinhaDTO.TIPO_ITEN_DADOS_ASS_SEGUNDA_VIA, map));
		Collections.unmodifiableList(cabecalhoQuebraPagina);

		SetorDTO setorDemFinFicArr = new SetorDTO(
				TipoSetorPrint.DEMONSTRATIVO_FINANCEIRO_FICHA_ARRECADACAO,
				null, null, cabecalhoQuebraPagina, Boolean.FALSE, Boolean.TRUE);

		return setorDemFinFicArr;
	}

	
	
	private SetorDTO criarSetorOcorrenciaSegundaVia(
			ClienteSegundaViaPrintDTO clienteSegundaViaPrintDTO) {

		Map map = new HashMap<String,String>();
		map.put(PrintHouseConstants.LayoutArquivoPrintHouse.CODIGO_CLIENTE_NET,
				notNullString(clienteSegundaViaPrintDTO.getCodigoClienteNETFormatado()));
		map.put(PrintHouseConstants.LayoutArquivoPrintHouse.CPF_CNPJ_COBRANCA,
				notNullObject(clienteSegundaViaPrintDTO.getCpfCNPJCobranca()));
		map
				.put(
						PrintHouseConstants.LayoutArquivoPrintHouse.DATA_PROCESSAMENTO_BOLETO,
						notNullObject(clienteSegundaViaPrintDTO
								.getDataProcessamentoBoleto()));
		map
				.put(
						PrintHouseConstants.LayoutArquivoPrintHouse.DATA_VENCIMENTO_BOLETO,
						notNullObject(clienteSegundaViaPrintDTO
								.getDataVencimentoBoleto()));
		map
				.put(
						PrintHouseConstants.LayoutArquivoPrintHouse.DATA_VENCIMENTO_BOLETO_ORIGEM,
						notNullObject(clienteSegundaViaPrintDTO
								.getDataVencimentoBoletoOrigem()));
		map
				.put(
						PrintHouseConstants.LayoutArquivoPrintHouse.IDENTIFICADOR_BOLETO,
						notNullObject(clienteSegundaViaPrintDTO
								.getIdentificadorBoleto()));
		if (clienteSegundaViaPrintDTO.getInscricaoEstadualCobranca() == null) {
			map
					.put(
							PrintHouseConstants.LayoutArquivoPrintHouse.INSCRICAO_ESTADUAL_COBRANCA,
							PrintHouseConstants.NotaFiscal.IE_ISENTO);
		} else {
			map
					.put(
							PrintHouseConstants.LayoutArquivoPrintHouse.INSCRICAO_ESTADUAL_COBRANCA,
							notNullObject(clienteSegundaViaPrintDTO
									.getInscricaoEstadualCobranca()));
		}
		List<LinhaDTO> cabecalhoQuebraPagina = new ArrayList<LinhaDTO>();
		cabecalhoQuebraPagina.add(0, new LinhaDTO(LinhaDTO.TIPO_ITEN_INICIO_PAGINA));
		cabecalhoQuebraPagina.add(1, new LinhaDTO(LinhaDTO.TIPO_ITEN_DADOS_ASS_SEGUNDA_VIA, map));
		Collections.unmodifiableList(cabecalhoQuebraPagina);

		SetorDTO setorOcorrencias =  new SetorDTO(
		 TipoSetorPrint.OCORENCIAS,
		 null, null,
		 cabecalhoQuebraPagina, Boolean.FALSE, Boolean.FALSE);

		return setorOcorrencias;
	}
	
	/**
	 * Gera uma fatura somente com dados do demonstrativo financeiro e da ficha
	 * de arrecadacao, conforme layout de 2º via. <br>
	 * Eh retornado uma estrutura de dados <code>FaturaDTO</code> que
	 * representa o layout de uma fatura dentro do arquivo que sera enviado para
	 * a print. <br>
	 * O metodo ja retorna as linhas na ordem que devem ser colocadas dentro do
	 * arquivo. Alem disso faz a contabilizacao de paginas que a fatura ira
	 * gerar. <br>
	 * 
	 * @author Everton Ken Tomita, Jorge Rasuck - refactor Robin Michael Gray
	 * @version $Revision: 1.3.4.7 $ - Revisão da implementação do método
	 * @number RF015_RF021_FAT149
	 * 
	 * @param idBoleto
	 *            identificador do boleto que deve ser gerado no arquivo.
	 * @param idLote
	 *            identificador do lote que o boleto pertence para ser gerado.
	 *            Parametro nao requerido.
	 * @return FaturaDTO que contem as linhas na ordem que devem ser coloca
	 *         dentro do arquivo que sera enviado para Print.
	 * 
	 * @semantics
	 *
	 * 
	 */
	protected FaturaDTO gerarSegundaViaDemonstrativoFinanceiroFichaArrecadacao(
			Long idBoleto, Integer idLote, String idTipo, String tipo) {

		SetorListaDTO setorLista = null;
		SetorDTO setorDemFinFicArr = null;
		DadosGeraisSegundaViaPrintDTO dadosGeraisSegundaViaPrintDTO = null;
		FaturaDTO faturaDTO = null;
		String FormPagto = null;
		
		DynamicBean bean = new DynamicBean();
		bean.set("idBoleto", idBoleto);
//		dadosGeraisSegundaViaPrintDTO = getDadosBeanbyQuery("selecionarDadosSegundaViaBoletoOrigem", bean, false,DadosGeraisSegundaViaPrintDTO.class);
//		
//		if (dadosGeraisSegundaViaPrintDTO == null) {
//			dadosGeraisSegundaViaPrintDTO = getDadosBeanbyQuery("selecionarDadosSegundaVia", bean, false,DadosGeraisSegundaViaPrintDTO.class);
//
//			if (dadosGeraisSegundaViaPrintDTO == null) {
//				dadosGeraisSegundaViaPrintDTO = getDadosBeanbyQuery("selecionarDadosSegundaViaBoletoOrigemParceiro", bean, false,DadosGeraisSegundaViaPrintDTO.class);
//
//				if (dadosGeraisSegundaViaPrintDTO == null) {
//					dadosGeraisSegundaViaPrintDTO = getDadosBeanbyQuery("selecionarDadosSegundaViaParceiro",bean, false, DadosGeraisSegundaViaPrintDTO.class);
//				}
//			}
//		}
		
		dadosGeraisSegundaViaPrintDTO = getDadosBeanbyQuery("selecionarDadosSegundaViaBoletoOrigemEndereco", bean, false,DadosGeraisSegundaViaPrintDTO.class);
		
		if (dadosGeraisSegundaViaPrintDTO == null) {
			dadosGeraisSegundaViaPrintDTO = getDadosBeanbyQuery("selecionarDadosSegundaViaEndereco", bean, false,DadosGeraisSegundaViaPrintDTO.class);

			if (dadosGeraisSegundaViaPrintDTO == null) {
				dadosGeraisSegundaViaPrintDTO = getDadosBeanbyQuery("selecionarDadosSegundaViaBoletoOrigemParceiroEndereco", bean, false,DadosGeraisSegundaViaPrintDTO.class);

				if (dadosGeraisSegundaViaPrintDTO == null) {
					dadosGeraisSegundaViaPrintDTO = getDadosBeanbyQuery("selecionarDadosSegundaViaParceiroEndereco",bean, false, DadosGeraisSegundaViaPrintDTO.class);
				}
			}
		}

		
		if(dadosGeraisSegundaViaPrintDTO != null){
			FormPagto = obterFormaPagamento_BoletoOrigem(bean);
		}
		if(dadosGeraisSegundaViaPrintDTO != null && FormPagto == null){
			FormPagto = obterFormaPagamento_SegundaVia(bean);
		}
		if(dadosGeraisSegundaViaPrintDTO != null && FormPagto == null){
			FormPagto = obterFormaPagamento_BoletoOrigemParceiro(bean);
		}
		if(dadosGeraisSegundaViaPrintDTO != null && FormPagto == null){
			FormPagto = obterFormaPagamento_SegundaViaParceiro(bean);
		}
		
		this.popularMesAnoReferencia(dadosGeraisSegundaViaPrintDTO, idBoleto);

		if (idLote != null) {
			if(dadosGeraisSegundaViaPrintDTO == null){
				dadosGeraisSegundaViaPrintDTO = new DadosGeraisSegundaViaPrintDTO();
			}
			dadosGeraisSegundaViaPrintDTO.setCodigoLote(new Long(idLote.intValue()));
		}

		SnBoletoBean boleto = new SnBoletoBean();
		boleto.setIdBoleto(idBoleto);
		boleto = (SnBoletoBean) super.find(boleto);
		Hibernate.initialize(boleto.getContrato());
		
		if(boleto.getContrato() != null && boleto.getContrato().getNumContratoEmbratel() != null ){
			String numContratoEmbratel = boleto.getContrato().getNumContratoEmbratel().toString();
			dadosGeraisSegundaViaPrintDTO.setCodigoClienteEBT(numContratoEmbratel);
		}	
		
		Map mapFatura = new HashMap<String,String>();

		// Atributos da interface ClienteCobrancaPrintDTO serão setados
		if(dadosGeraisSegundaViaPrintDTO != null){
			mapFatura.put(  PrintHouseConstants.LayoutArquivoPrintHouse.BAIRRO_COBRANCA,
					notNullObject(dadosGeraisSegundaViaPrintDTO.getBairroCobranca()));
			mapFatura.put(	PrintHouseConstants.LayoutArquivoPrintHouse.CEP_COBRANCA,
						StringUtils.prepad(notNullString(dadosGeraisSegundaViaPrintDTO.getCepCobranca()),8,'0'));
			mapFatura.put(  PrintHouseConstants.LayoutArquivoPrintHouse.CIDADE_COBRANCA,
							notNullObject(dadosGeraisSegundaViaPrintDTO.getCidadeCobranca()));
			mapFatura.put(	PrintHouseConstants.LayoutArquivoPrintHouse.CODIGO_LOTE,
							notNullObject(dadosGeraisSegundaViaPrintDTO.getCodigoLote()));
			mapFatura.put(	PrintHouseConstants.LayoutArquivoPrintHouse.CODIGO_CLIENTE_NET,
							notNullString(dadosGeraisSegundaViaPrintDTO.getCodigoClienteNETFormatado()));
			mapFatura.put( 	PrintHouseConstants.LayoutArquivoPrintHouse.ENDERECO_COBRANCA,
							notNullObject(dadosGeraisSegundaViaPrintDTO.getEnderecoCobranca()));
			//GPOST
			mapFatura.put( 	PrintHouseConstants.LayoutArquivoPrintHouse.LOGRADOURO_COBRANCA,
					notNullObject(dadosGeraisSegundaViaPrintDTO.getLogradouroCobranca()));
			mapFatura.put( 	PrintHouseConstants.LayoutArquivoPrintHouse.NUMERO_COBRANCA,
					notNullObject(dadosGeraisSegundaViaPrintDTO.getNumeroCobranca()));
			mapFatura.put( 	PrintHouseConstants.LayoutArquivoPrintHouse.COMPLEMENTO_COBRANCA,
					notNullObject(dadosGeraisSegundaViaPrintDTO.getComplementoCobranca()));
			
			mapFatura.put( 	PrintHouseConstants.LayoutArquivoPrintHouse.ESTADO_COBRANCA,
							notNullObject(dadosGeraisSegundaViaPrintDTO.getEstadoCobranca()));
			mapFatura.put(
					PrintHouseConstants.LayoutArquivoPrintHouse.CODIGO_BANCO,
					notNullObject(dadosGeraisSegundaViaPrintDTO
							.getCodigoBanco()));
			mapFatura.put( 	PrintHouseConstants.LayoutArquivoPrintHouse.IDENTIFICADOR_BOLETO,
							notNullObject(dadosGeraisSegundaViaPrintDTO.getIdentificadorBoleto()));
			mapFatura.put( 	PrintHouseConstants.LayoutArquivoPrintHouse.IDENTIFICADOR_BOLETO_ORIGEM,
							notNullObject(dadosGeraisSegundaViaPrintDTO.getIdentificadorBoletoOrigem()));
			mapFatura.put( 	PrintHouseConstants.LayoutArquivoPrintHouse.NOME_CLIENTE_COBRANCA,
							notNullObject(dadosGeraisSegundaViaPrintDTO.getNomeClienteCobranca()));
			mapFatura.put(	PrintHouseConstants.LayoutArquivoPrintHouse.PREFIXO,
							notNullObject(dadosGeraisSegundaViaPrintDTO.getPrefixo()));
			mapFatura.put( 	PrintHouseConstants.LayoutArquivoPrintHouse.CODIGO_CLIENTE_EBT,
							notNullString(dadosGeraisSegundaViaPrintDTO.getCodigoClienteEBT()));			
			mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.FUST,"");
			mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.FUNTEL,"");
			mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.MENSAGEMEBT,"N");
			mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.LABELCLIENTE,"Cliente");
			
			mapFatura.put(
					PrintHouseConstants.LayoutArquivoPrintHouse.CODIGO_BANCO,
					notNullObject(dadosGeraisSegundaViaPrintDTO
							.getCodigoBanco()));
			
			//QRCODE PIX
			mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.PIX_HASHCODE, notNullObject(dadosGeraisSegundaViaPrintDTO.getPixHashcode()));
			
			mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.LINHA_DIGITAVEL, notNullObject(dadosGeraisSegundaViaPrintDTO.getLinhaDigitavel()));
			mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.CODIGO_DE_BARRAS, notNullObject(dadosGeraisSegundaViaPrintDTO.getCodigoDeBarras()));
			mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.VALOR_COBRADO, notNullObject(dadosGeraisSegundaViaPrintDTO.getValorCobrado()));
			
			//NSMSP_172250_NI_003_CI_001\JAVA 
			ArquivoPrintHouseDAO dao = getAphDao();
			MensagemClaroClubeDTO mensagemClaroClubeDTO = dao.buscarMensagemClaroClube(idBoleto == null ? dadosGeraisSegundaViaPrintDTO.getIdentificadorBoleto() : idBoleto);			
			
			//NSMSP_161258_NI_001\JAVA		
			if(mensagemClaroClubeDTO!=null && mensagemClaroClubeDTO.getIsDemandaLigada() && mensagemClaroClubeDTO.getDescMensagemFormatada()!=null ){
				mensagemClaroClubeDTO.setDescMensagemFormatada(br.com.netservicos.novosms.emissao.util.StringUtils
						.formataMsgClaroClubeArquivo(mensagemClaroClubeDTO.getDescMensagemFormatada()));
				mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.MENSAGEMCLAROCLUBE, mensagemClaroClubeDTO.getDescMensagemFormatada());
			}else{
				mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.MENSAGEMCLAROCLUBE, " ");	
			}
					
			
			//NSMSP_172250_NI_003
			SnLoteBean loteCorrente = new SnLoteBean();
			Integer formaEnvioFatura = 2;

			if(dadosGeraisSegundaViaPrintDTO.getCodigoLote() != null){
				loteCorrente = buscarLote(dadosGeraisSegundaViaPrintDTO.getCodigoLote().intValue());
				formaEnvioFatura = dao.buscarTipoEnvioFaturaPorLote(boleto.getContrato().getCompositeKey().getCidContrato(), loteCorrente, 2);	
			}
			
			mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.FORMAENVIOFATURA, formaEnvioFatura);
			
			mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.BASE, "");
			//getCurrentDBService().split("_")
			
			mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.CIDCONTRATO, 000000);
			mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.TIPOCOBRANCA, 0);
			mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.EMAILORIGEM, "");
			mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.EMAILDESTINO, "");
			mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.LINKNAOOPTANTE, "");
			mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.MESVENCIMENTO, "");
			
	        //Quitação de Debito Anual 
	        mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.DECLARANUALDEBITONET, "");
	        mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.DECLARANUALDEBITOEBT, "");			

	        //Mensagem migração política
	        mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.MIGRACAOPADPOL, "");

	        //Identificador da Mensagem de Quitacao
	        mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.IDENTIFICADORMENSAGEM, "");
	        
			setorLista = new SetorListaDTO(new LinhaDTO(
							LinhaDTO.TIPO_ITEN_INICIO_FATURA, mapFatura), new LinhaDTO(LinhaDTO.TIPO_ITEN_FIM_FATURA));
	
			String cidContrato = boleto.getContrato().getCompositeKey().getCidContrato();
	
			setorDemFinFicArr = criarSetorDemonstrativoFinaceiroFichaArrecadacaoSegundaVia(dadosGeraisSegundaViaPrintDTO);
			 

			FaturaNetDTO fatura = null;
					
						
			if( boleto.getFcBoletoConsolidado().equalsIgnoreCase("N") || 
					boleto.getFcBoletoConsolidado().equalsIgnoreCase("R") || 
				   boleto.getFcBoletoConsolidado().equalsIgnoreCase("M")) {			
				/**
				* Alteração responsável para contemplar a funcionalidade das 5 últimas ocorrências na Fatura de 2º via.
				* 
				* @author Sergio Ricardo Silva - implementação
				* 
				*/
				fatura = dao.buscarDadosSegundaViaNet(new Long(boleto.getIdBoleto()), boleto.getContrato().getCompositeKey().getCidContrato().toString(),boleto.getContrato().getCompositeKey().getNumContrato(), tipo);
			
			}else if(boleto.getFcBoletoConsolidado().equalsIgnoreCase("C") || 
					boleto.getFcBoletoConsolidado().equalsIgnoreCase("B")||
					boleto.getFcBoletoConsolidado().equalsIgnoreCase("D")){
				
				/**
				* Alteração responsável para contemplar a funcionalidade das 5 últimas ocorrências na Fatura de 2º via.
				* 
				* @author Sergio Ricardo Silva - implementação
				* 
				*/
				fatura = dao.buscarDadosSegundaViaFaturaNetEbt(new Long(boleto.getIdBoleto()), boleto.getContrato().getCompositeKey().getCidContrato().toString(),boleto.getContrato().getCompositeKey().getNumContrato(), tipo);
			}else if( boleto.getFcBoletoConsolidado().equalsIgnoreCase("P") || 
					boleto.getFcBoletoConsolidado().equalsIgnoreCase("A")) {				
				
				fatura = dao.buscarDadosSegundaViaFaturaEbt(new Long(boleto.getIdBoleto()), tipo); 
			}
	
			if (cidContrato!=null) fatura.setCidContrato(cidContrato);
			
			if(!"PDF".equals(idTipo)){
				SetorDTO setorMinhaNET = criarSetorMinhaNetSegundaVia(dadosGeraisSegundaViaPrintDTO); 
				gerarSetorMinhaNet(setorMinhaNET, fatura);
				setorLista.addSetor(setorMinhaNET);
				
								
				
			/**
			* Alteração responsável para contemplar a funcionalidade das 5 últimas ocorrências na Fatura de 2º via.
			*
			* @author Sergio Ricardo Silva - implementação
			*
			*/			
				SetorDTO setorFaturaNet = criarSetorOcorrenciaSegundaVia(dadosGeraisSegundaViaPrintDTO);
				
				if(fatura.getUltimasOcorrencias() != null){
					if (!fatura.getUltimasOcorrencias().trim().equals("")) {
						Map mapUltimasOcorrencias = new HashMap<String, String>();
						mapUltimasOcorrencias
						.put(
						PrintHouseConstants.LayoutArquivoPrintHouse.ULTIMAS_OCORRENCIAS,
						fatura.getUltimasOcorrencias());
						setorFaturaNet.addLinha(new LinhaDTO(
						LinhaDTO.TIPO_ITEN_ULTIMAS_OCORRENCIAS,
						mapUltimasOcorrencias,0));
					}
				}
				setorLista.addSetor(setorFaturaNet);
			}					
			
			
			if("PDF".equals(idTipo)){	
			   	SetorDTO setorMinhaNET = criarSetorMinhaNetSegundaVia(dadosGeraisSegundaViaPrintDTO); 
				gerarSetorMinhaNet(setorMinhaNET, fatura);
				setorLista.addSetor(setorMinhaNET);
										
				SetorDTO setoFormPagto = new SetorDTO(TipoSetorPrint.FORMPAGTO, null,
						null, null, Boolean.FALSE, Boolean.FALSE);
				
				if(FormPagto != null && !FormPagto.equals("")){
					Map<String, String> mapFormPagto = new HashMap<String, String>();
					mapFormPagto.put("FormPagto", FormPagto);
					LinhaDTO linha = new LinhaDTO (LinhaDTO.TIPO_FORMA_DE_PAGAMENTO, mapFormPagto) ;
					setoFormPagto.addLinha(linha);
			    }
								
				setorLista.addSetor(setoFormPagto);
				
			}
			
			if(fatura.getDemonstrativoFinanceiro() != null){
				for ( DemonstrativoFinanceiroDTO descricao : fatura.getDemonstrativoFinanceiro()  ) {
					
					if (descricao.getDESCRICAOITEMDEMONST_FINANC().startsWith("#!#")){
						descricao.setDESCRICAOITEMDEMONST_FINANC(descricao.getDESCRICAOITEMDEMONST_FINANC().replace("#!#", "   "));
					}
					
					if (descricao.getDESCRICAOITEMDEMONST_FINANC().startsWith("#@#")){
						descricao.setDESCRICAOITEMDEMONST_FINANC(descricao.getDESCRICAOITEMDEMONST_FINANC().replace("#@#", "   "));
					}
				}
			}
			
			
			//fatura.setDadosGeraisNF()
			gerarSetorDemonstrativoFichaArrecadacao(setorDemFinFicArr
			, idBoleto
			, dadosGeraisSegundaViaPrintDTO
			, dadosGeraisSegundaViaPrintDTO
			, dadosGeraisSegundaViaPrintDTO.getValorTotalNotaFiscal()
			, cidContrato
			, boleto.getFcBoletoConsolidado()
			, fatura
			, 0);

			setorLista.addSetor(setorDemFinFicArr);
			
			
			
			
			setorLista.setValorCobrado(dadosGeraisSegundaViaPrintDTO.getValorCobrado());
			faturaDTO = gerarFatura(cidContrato, setorLista);
			}

			return faturaDTO;

	}
	
	/**
	 * Obter Forma de Pagamento
	 * selecionarDadosSegundaViaBoletoOrigem
	 * @param bean
	 * @return
	 */
	private String obterFormaPagamento_BoletoOrigem(DynamicBean bean) {

		String FormPagto = getDadosBeanbyQuery(
				"selecionarDadosSegundaViaBoletoOrigemEndereco", bean, false);

		return FormPagto;
	}
	
	/**
	 * Obter Forma de Pagamento
	 * selecionarDadosSegundaVia
	 * @param bean
	 * @return
	 */
	private String obterFormaPagamento_SegundaVia(DynamicBean bean) {

		String FormPagto = getDadosBeanbyQuery(
				"selecionarDadosSegundaViaEndereco", bean, false);

		return FormPagto;
	}

	/**
	 * Obter Forma de Pagamento
	 * selecionarDadosSegundaViaBoletoOrigemParceiro
	 * @param bean
	 * @return
	 */
	private String obterFormaPagamento_BoletoOrigemParceiro(DynamicBean bean) {
	
		String FormPagto = getDadosBeanbyQuery(
				"selecionarDadosSegundaViaBoletoOrigemParceiroEndereco", bean, false);

		return FormPagto;
	}
	
	/**
	 * Obter Forma de Pagamento
	 * selecionarDadosSegundaViaParceiro
	 * @param bean
	 * @return
	 */
	private String obterFormaPagamento_SegundaViaParceiro(DynamicBean bean) {

		String FormPagto = getDadosBeanbyQuery(
				"selecionarDadosSegundaViaParceiroEndereco", bean, false);

		return FormPagto;
	}
	
	
	/**
	 * Obter o Mês Referência
	 * @param dadosGeraisSegundaViaPrintDTO
	 * @param idBoleto
	 */
	private void popularMesAnoReferencia(DadosGeraisSegundaViaPrintDTO dadosGeraisSegundaViaPrintDTO, Long idBoleto){
		SnBoletoBean boleto = new SnBoletoBean();
		boleto.setIdBoleto(idBoleto);
		boleto = (SnBoletoBean) find(boleto);
		
		Hibernate.initialize(boleto.getRelCobrancaBoleto()); 
		
		List<SnRelCobrancaBoletoBean> cobracasBoletos = boleto.getRelCobrancaBoleto();
		
		if(cobracasBoletos != null && cobracasBoletos.size() > 0){
			
			Date mesReferencia = null;
			
			for(SnRelCobrancaBoletoBean relCobrancaBoleto: cobracasBoletos){

				Cobranca cobranca = null;
				
				if(relCobrancaBoleto.getIdCobranca() != null){
					
					SnCobrancaBean cobrancaBean = new SnCobrancaBean();
					cobrancaBean.setIdCobranca(relCobrancaBoleto.getIdCobranca());
					cobrancaBean = (SnCobrancaBean) find(cobrancaBean);
					
					cobranca = cobrancaBean;
					
				}else{
					
					SnCobrancaParceiroBean cobrancaParceiro = new SnCobrancaParceiroBean();
					cobrancaParceiro.setIdCobranca(relCobrancaBoleto.getIdCobrancaParceiro());
					cobrancaParceiro = (SnCobrancaParceiroBean) find(cobrancaParceiro);
					
					cobranca = cobrancaParceiro;

				}
				
				Hibernate.initialize(cobranca.getGeracaoCobranca()); 
				
				GeracaoCobranca geracaoCobranca = cobranca.getGeracaoCobranca();
				
				if(geracaoCobranca == null){
					continue;
				}
				
				mesReferencia = geracaoCobranca.getMesReferencia();
				if(mesReferencia != null){
					//RCA20100111
					if(cobranca instanceof SnCobrancaBean) {
						if (this.getPoliticaContratoEm(cobranca.getContrato(),geracaoCobranca.getDtGeracao()) == 0) {
							Calendar cal = Calendar.getInstance();
							cal.setTime(mesReferencia);
							cal.add(Calendar.MONTH, -1);
							mesReferencia = cal.getTime();
						}
					}
					//RCA20100111
					break;
				}

			}
			
			if(mesReferencia == null && boleto.getIdBoletoOrigem() != null){
				this.popularMesAnoReferencia(dadosGeraisSegundaViaPrintDTO, boleto.getIdBoletoOrigem());
			}
			
			if(dadosGeraisSegundaViaPrintDTO.getMesAnoReferencia() == null && mesReferencia != null){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
				dadosGeraisSegundaViaPrintDTO.setMesAnoReferencia(sdf.format(mesReferencia));
			}
		}
		
	}

	private Integer getPoliticaContratoEm(SnContratoBean contrato, Date data) {
		
		return getAphDao().getPoliticaContratoEm(contrato, data);
	}

	protected FaturaDTO gerarCopiaDeNotaFiscal(Long idNotaFiscal,
			Long idBoleto, String tipoFatura) throws Exception {
		return gerarCopiaDeNotaFiscal(idNotaFiscal, idBoleto, new FaturaDTO(), tipoFatura, null);

	}
	

	/**
	 * Gera uma fatura somente com dados da nota fiscal, conforme layout de 2º
	 * via. <br>
	 * Eh retornado uma estrutura de dados <code>FaturaDTO</code> que
	 * representa o layout de uma fatura dentro do arquivo que sera enviado para
	 * a print. <br>
	 * O metodo ja retorna as linhas na ordem que devem ser colocadas dentro do
	 * arquivo. Alem disso faz a contabilizacao de paginas que a fatura ira
	 * gerar. <br>
	 * 
	 * @version 1.0 - 27/04/2006 - Criacao
	 * @author Everton Ken Tomita
	 * @number RF015_RF021
	 * 
	 * @param Integer
	 *            idNotaFiscal identificador da nota fiscal que deve ser gerado
	 * @return FaturaDTO que contem as linhas na ordem de acordo com arquivo de
	 *         configuracao (arquivo excel).
	 * @throws Exception 
	 * 
	 * @semantics	
	 * 
	 */
	protected FaturaDTO gerarCopiaDeNotaFiscal(Long idNotaFiscal,
			Long idBoleto, FaturaDTO faturaDTO, String tipoFatura, Long idBoletoAtual) throws Exception {

		// TODO DadosGeraisPrimeiraViaPrintDTO dadosGeraisPrimeiraViaPrintDTO =
		// this.getPrintDAO().selecionarClienteNotaFiscalBoleto(idBoleto,
		// idNotaFiscal);
		DadosGeraisPrimeiraViaPrintDTO dadosGeraisPrimeiraViaPrintDTO;
		// TODO
		// dadosGeraisPrimeiraViaPrintDTO.setCodigoClienteEBT(this.getPrintDAO().getCodigoEmbratel(idBoleto));
		// TODO colocar na busca de dadosGerais
		// dadosGeraisPrimeiraViaPrintDTO.setCodigoClienteEBT("pegar codigo
		// embratel");
		ArquivoPrintHouseDAO dao = getAphDao();
		DynamicBean filtro = new DynamicBean();
		Integer idSituacaoBoleto = null;
		
		
		
		SnBoletoBean boleto = new SnBoletoBean();
		if (idBoletoAtual != null){
			boleto.setIdBoleto(idBoletoAtual);
			boleto = (SnBoletoBean) super.find(boleto);
			idSituacaoBoleto = boleto.getSnSituacaoBoletoBean().getIdSituacaoBoleto().intValue();
		}else{
			idSituacaoBoleto = -1;
		}
		
		if (idBoletoAtual != null && idBoletoAtual.equals(idBoleto)){
			filtro.set("idBoleto", idBoleto);
			filtro.set("idCobrancaNotaFiscal", idNotaFiscal);
			List<DadosGeraisPrimeiraViaPrintDTO> listDados = getDadosBeanByQuery(
					"selecionarClienteNotaFiscalBoleto", filtro, false, DadosGeraisPrimeiraViaPrintDTO.class);
			
			if (listDados == null || listDados.isEmpty()) {
				listDados = getDadosBeanByQuery(
						"selecionarClienteNotaFiscalBoletoParceiro", filtro, false, DadosGeraisPrimeiraViaPrintDTO.class);
				
			}
	
			if (listDados != null && !listDados.isEmpty()) {
				dadosGeraisPrimeiraViaPrintDTO = listDados.get(0);
				
			} 
			else {
					throw new EmissaoBusinessResourceException(
							"error.reemissaonf.parceiro.naodisponivel", new Object[] {
								 "| ID Nota Fiscal: " + idNotaFiscal });
			}
		}else{
			if(idSituacaoBoleto.equals(new Integer(9))){
				filtro.set("idBoleto", idBoletoAtual);
				filtro.set("idCobrancaNotaFiscal", idNotaFiscal);
				List<DadosGeraisPrimeiraViaPrintDTO> listDados = getDadosBeanByQuery(
						"selecionarClienteNotaFiscalBoleto2", filtro, false, DadosGeraisPrimeiraViaPrintDTO.class);
				
				if (listDados == null || listDados.isEmpty()) {
					listDados = getDadosBeanByQuery(
							"selecionarClienteNotaFiscalBoletoParceiro", filtro, false, DadosGeraisPrimeiraViaPrintDTO.class);
					
				}
		
				if (listDados != null && !listDados.isEmpty()) {
					dadosGeraisPrimeiraViaPrintDTO = listDados.get(0);
					
				} 
				else {
						throw new EmissaoBusinessResourceException(
								"error.reemissaonf.parceiro.naodisponivel", new Object[] {
									 "| ID Nota Fiscal: " + idNotaFiscal });
				}
			}else{
				filtro.set("idBoleto", idBoleto);
				filtro.set("idCobrancaNotaFiscal", idNotaFiscal);
				List<DadosGeraisPrimeiraViaPrintDTO> listDados = getDadosBeanByQuery(
						"selecionarClienteNotaFiscalBoleto2", filtro, false, DadosGeraisPrimeiraViaPrintDTO.class);
				
				if (listDados == null || listDados.isEmpty()) {
					listDados = getDadosBeanByQuery(
							"selecionarClienteNotaFiscalBoletoParceiro", filtro, false, DadosGeraisPrimeiraViaPrintDTO.class);
					
				}
		
				if (listDados != null && !listDados.isEmpty()) {
					dadosGeraisPrimeiraViaPrintDTO = listDados.get(0);
					
				} 
				else {
						throw new EmissaoBusinessResourceException(
								"error.reemissaonf.parceiro.naodisponivel", new Object[] {
									 "| ID Nota Fiscal: " + idNotaFiscal });
				}
			}
				
		}

		DadosGeraisPrimeiraViaPrintDTO dadosGeraisPrimeiraViaAtual = null;
		// condição criada para atender o IDBOLETO passado na tela, para o caso de boleto desmembrado. 
		// O boleto original é desmembrado em NET e PARCEIROS criando assim 2 ids boleto a mais para cada tipo de boleto.
		// Como a geração de copia de primeira via trabalha sempre em cima do boleto origem, precisamos do IDBOLETO (idBoletoAtual) 
		// passado na tela para controlar qual cursor deverá ser invocado através do objeto dadosGeraisPrimeiraViaAtual.getFc_boleto_consolidado()
		if(idBoletoAtual != null && !idBoletoAtual.equals(idBoleto)) {
			
			
			DynamicBean filter = new DynamicBean();
			filter.set("idBoleto", idBoletoAtual);
			filter.set("idCobrancaNotaFiscal", idNotaFiscal);
			List<DadosGeraisPrimeiraViaPrintDTO> list = getDadosBeanByQuery(
					"selecionarClienteNotaFiscalBoleto", filter, false, DadosGeraisPrimeiraViaPrintDTO.class);
			
			if (list == null || list.isEmpty()) {
				list = getDadosBeanByQuery(
						"selecionarClienteNotaFiscalBoletoParceiro", filter, false, DadosGeraisPrimeiraViaPrintDTO.class);
				
			}

			if (list != null && !list.isEmpty()) {
				dadosGeraisPrimeiraViaAtual = list.get(0);
				
			} 
			else {
					throw new EmissaoBusinessResourceException(
							"error.reemissaonf.parceiro.naodisponivel", new Object[] {
								 "| ID Nota Fiscal: " + idNotaFiscal });
			}
		}
		
		Map mapFatura = new HashMap<String,String>();

		// Atributos da interface ClienteCobrancaPrintDTO serão setados
		mapFatura.put(
				PrintHouseConstants.LayoutArquivoPrintHouse.BAIRRO_COBRANCA,
				notNullObject(dadosGeraisPrimeiraViaPrintDTO
						.getBairroCobranca()));
		mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.CEP_COBRANCA,
				StringUtils.prepad(notNullString(dadosGeraisPrimeiraViaPrintDTO.getCepCobranca()),8,'0'));
		mapFatura.put(
				PrintHouseConstants.LayoutArquivoPrintHouse.CIDADE_COBRANCA,
				notNullObject(dadosGeraisPrimeiraViaPrintDTO
						.getCidadeCobranca()));
		mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.CODIGO_LOTE,
				notNullObject(dadosGeraisPrimeiraViaPrintDTO.getCodigoLote()));
		mapFatura.put(
				PrintHouseConstants.LayoutArquivoPrintHouse.CODIGO_CLIENTE_NET,
				notNullString(dadosGeraisPrimeiraViaPrintDTO
						.getCodigoClienteNET()));
		mapFatura.put(
				PrintHouseConstants.LayoutArquivoPrintHouse.ENDERECO_COBRANCA,
				notNullObject(dadosGeraisPrimeiraViaPrintDTO
						.getEnderecoCobranca()));
		mapFatura.put(
				PrintHouseConstants.LayoutArquivoPrintHouse.ESTADO_COBRANCA,
				notNullObject(dadosGeraisPrimeiraViaPrintDTO
						.getEstadoCobranca()));
		mapFatura
				.put(
						PrintHouseConstants.LayoutArquivoPrintHouse.IDENTIFICADOR_BOLETO,
						notNullObject(dadosGeraisPrimeiraViaPrintDTO
								.getIdentificadorBoleto()));
		mapFatura
				.put(
						PrintHouseConstants.LayoutArquivoPrintHouse.IDENTIFICADOR_BOLETO_ORIGEM,
						notNullObject(dadosGeraisPrimeiraViaPrintDTO
								.getIdentificadorBoletoOrigem()));
		mapFatura
				.put(
						PrintHouseConstants.LayoutArquivoPrintHouse.NOME_CLIENTE_COBRANCA,
						notNullObject(dadosGeraisPrimeiraViaPrintDTO
								.getNomeClienteCobranca()));
		mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.PREFIXO,
				notNullObject(dadosGeraisPrimeiraViaPrintDTO.getPrefixo()));
		mapFatura.put(
				PrintHouseConstants.LayoutArquivoPrintHouse.CODIGO_CLIENTE_EBT,
				notNullString(dadosGeraisPrimeiraViaPrintDTO
						.getCodigoClienteEBT()));
		mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.LABELCLIENTE,"Cliente");
		
		
		//NSMSP_161258_NI_001\JAVA		
		if(dadosGeraisPrimeiraViaPrintDTO.getMensagemClaroClubeDTO()!=null && dadosGeraisPrimeiraViaPrintDTO.getMensagemClaroClubeDTO().getIsDemandaLigada() && dadosGeraisPrimeiraViaPrintDTO.getMensagemClaroClubeDTO().getDescMensagemFormatada()!=null ){
			mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.MENSAGEMCLAROCLUBE, dadosGeraisPrimeiraViaPrintDTO.getMensagemClaroClubeDTO().getDescMensagemFormatada());	
		}else{
			mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.MENSAGEMCLAROCLUBE, " ");	
		}
		
		//NSMSP_172250_NI_003
		SnLoteBean loteCorrente = new SnLoteBean();
		Integer formaEnvioFatura = 2;
		if(dadosGeraisPrimeiraViaPrintDTO.getCodigoLote() != null){
			loteCorrente = buscarLote( dadosGeraisPrimeiraViaPrintDTO.getCodigoLote().intValue());
			formaEnvioFatura = dao.buscarTipoEnvioFaturaPorLote(boleto.getContrato().getCompositeKey().getCidContrato(), loteCorrente, 2);	
			
		}
		mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.FORMAENVIOFATURA, formaEnvioFatura);
		mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.BASE, "");
		mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.CIDCONTRATO, 000000);
		mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.TIPOCOBRANCA, 0);
		mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.EMAILORIGEM, "");
		mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.EMAILDESTINO, "");
		mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.LINKNAOOPTANTE, "ArquivoPrintHouseServiceEJBImpl.gerarCopiaDeNotaFiscal");
		mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.MESVENCIMENTO, "");
		
        //Quitação de Debito Anual 
        mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.DECLARANUALDEBITONET, "");
        mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.DECLARANUALDEBITOEBT, "");	

        //Mensagem migração política
        mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.MIGRACAOPADPOL, "");
        
        //Identificador da Mensagem de Quitacao
        mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.IDENTIFICADORMENSAGEM, "");
        
        this.criarComplementoCampoImportante(idBoleto);
        List<SetorDTO> listComplementoCampoImportante = obterComplementoCampoImportante(idBoleto);
        
        List<MensagemAnatelDTO> listMensagemAnatelDTO = null;
        try {
			
			 listMensagemAnatelDTO =  dao.buscarMensagensAnatel(idBoleto);
		
		 } catch (Exception e) {								
			logger.info(new BasicAttachmentMessage("ERRO AO BUSCAR MENSAGENS CAMPO IMPORTANTE", AttachmentMessageLevel.ERROR));
			e.printStackTrace();
		 } 

		SetorDTO setorNotaFiscalNet = criarSetorNotaFiscalNet(
				dadosGeraisPrimeiraViaPrintDTO, TipoSetorPrint.NOTA_FISCAL_NET,dadosGeraisPrimeiraViaPrintDTO.getFc_boleto_consolidado(), true, listComplementoCampoImportante);
		SetorDTO setorTributoNet = criarSetorNotaFiscalNet(
				dadosGeraisPrimeiraViaPrintDTO,
				TipoSetorPrint.NOTA_FISCAL_TRIBUTOS_NET,dadosGeraisPrimeiraViaPrintDTO.getFc_boleto_consolidado(), true, listComplementoCampoImportante);
		
		FaturaNetDTO fatura = null;
		LoteBoletoFaturaDTO boletoFatura = new LoteBoletoFaturaDTO();
		
		boleto = new SnBoletoBean();
		boleto.setIdBoleto(idBoleto);
		boleto = (SnBoletoBean)findByPrimaryKey(boleto);
		String cidContrato = boleto.getContrato().getCompositeKey().getCidContrato();
		
		if(idBoletoAtual != null && idSituacaoBoleto.equals(new Integer(9))){
			boletoFatura.setIdBoleto(idBoletoAtual);
		}else{
			boletoFatura.setIdBoleto(idBoleto);
		}
		
		boletoFatura.setFcBoletoConsolidado(dadosGeraisPrimeiraViaPrintDTO.getFc_boleto_consolidado());
		boletoFatura.setFcBoletoConsolidado(dadosGeraisPrimeiraViaPrintDTO.getNumContrato());
		boletoFatura.setCidContrato(cidContrato);
		ArrayList<LoteBoletoFaturaDTO> boletoFaturas = new ArrayList<LoteBoletoFaturaDTO>();
		DynamicBean bean = new DynamicBean();
		bean.set("idBoleto", idBoleto);
		
		List boletoLotes = super.search("idLotePorIdBoleto", bean, false);
		Long idLote = null;
		
		if (boletoLotes != null && !boletoLotes.isEmpty()) {
			idLote = Long.parseLong(String.valueOf(boletoLotes.get(0)));
		
		boletoFatura.setIdLote(idLote.intValue());
		}
		
		boletoFaturas.add(boletoFatura);
		
		if( dadosGeraisPrimeiraViaPrintDTO.getFc_boleto_consolidado().equalsIgnoreCase("N") || 
				dadosGeraisPrimeiraViaPrintDTO.getFc_boleto_consolidado().equalsIgnoreCase("R") || 
				( dadosGeraisPrimeiraViaPrintDTO.getFc_boleto_consolidado().equalsIgnoreCase("D") && dadosGeraisPrimeiraViaPrintDTO.isOperadoraNet() ) ||			  
				dadosGeraisPrimeiraViaPrintDTO.getFc_boleto_consolidado().equalsIgnoreCase("M") ||
				(dadosGeraisPrimeiraViaAtual != null && dadosGeraisPrimeiraViaAtual.getFc_boleto_consolidado().equalsIgnoreCase("R"))) {			
			
			List<FaturaNetDTO> faturas = dao.buscarDadosFaturaNet(boletoFaturas, idNotaFiscal, new String(),idLote, TPCOPIA, !FLAG_ARQUIVO_PRINT, tipoFatura);
			fatura = faturas.get(0);
		
		}else if((dadosGeraisPrimeiraViaPrintDTO.getFc_boleto_consolidado().equalsIgnoreCase("C") || 
				dadosGeraisPrimeiraViaPrintDTO.getFc_boleto_consolidado().equalsIgnoreCase("B")) &&
				!(dadosGeraisPrimeiraViaAtual != null && dadosGeraisPrimeiraViaAtual.getFc_boleto_consolidado().equalsIgnoreCase("A"))){
	
			List<FaturaNetDTO> faturas = dao.buscarDadosFaturaNetEbt(boletoFaturas, idNotaFiscal, new String(), TPCOPIA, !FLAG_ARQUIVO_PRINT, tipoFatura);
			fatura = faturas.get(0);

		}else if( dadosGeraisPrimeiraViaPrintDTO.getFc_boleto_consolidado().equalsIgnoreCase("P") || 
				dadosGeraisPrimeiraViaPrintDTO.getFc_boleto_consolidado().equalsIgnoreCase("A") ||
				(dadosGeraisPrimeiraViaAtual != null && dadosGeraisPrimeiraViaAtual.getFc_boleto_consolidado().equalsIgnoreCase("A")) ||
				( dadosGeraisPrimeiraViaPrintDTO.getFc_boleto_consolidado().equalsIgnoreCase("D") && dadosGeraisPrimeiraViaPrintDTO.isOperadoraNet() == false )){	
				
			List<FaturaNetDTO> faturas = dao.buscarDadosFaturaEbt(boletoFaturas,idNotaFiscal, new String(), !FLAG_ARQUIVO_PRINT, tipoFatura); 
			fatura = faturas.get(0); 
		}
		if(fatura.getValorFust() != null)		
			mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.FUST,valor(fatura.getValorFust(),5,2));
		if(fatura.getValorFuntel() != null)
		mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.FUNTEL,valor(fatura.getValorFuntel(),5,2));
		
		SetorListaDTO setorListaDTO = new SetorListaDTO(new LinhaDTO(
				LinhaDTO.TIPO_ITEN_INICIO_FATURA, mapFatura), new LinhaDTO(
				LinhaDTO.TIPO_ITEN_FIM_FATURA));
		
		// gera setor de detalhe de ligações e nota fiscal embratel se o tipo de fatura não for nota fiscal NET
		if (tipoFatura.equals(FATURA_PRIMEIRA_VIA) ) {
			// cria setor de Detalhe de ligações		
			SetorDTO setorDetalheLigacoes = criarSetorDetalheLigacoesNETEBT(
					dadosGeraisPrimeiraViaPrintDTO, fatura);
			if (fatura.getLigacoes() != null && fatura.getLigacoes().size() > 0) {
				gerarSetorDetalheLigacoesNETEBT(setorDetalheLigacoes, idBoleto,
						dadosGeraisPrimeiraViaPrintDTO,
						dadosGeraisPrimeiraViaPrintDTO,
						dadosGeraisPrimeiraViaPrintDTO
								.getValorTotalNotaFiscal(), cidContrato, fatura
								.getFcBoletoConsolidado(), fatura);
				setorListaDTO.addSetor(setorDetalheLigacoes);
			}
			
			
			// cria setor Nota Fiscal. Para cada parceiro é criado o setor
			if (fatura.getDadosNotaFiscalParceiro() != null) {
				Long idParceiro = null;
				for (Iterator iterParceiros = fatura.getIdParceiros().iterator(); iterParceiros.hasNext();) {
					
					idParceiro = (Long) iterParceiros.next();

					gerarSetorNotaFiscalEBT(setorListaDTO,dadosGeraisPrimeiraViaPrintDTO, cidContrato,
							idParceiro, fatura, listMensagemAnatelDTO);
				}
				calcularAgrupamentoNFParceiro(setorListaDTO, cidContrato);
			}
			
			if (fatura.getFiliados() != null) {

				SetorDTO setorDemFinFicArr = criarSetorDemonstrativoFichaArrecadacao(
						dadosGeraisPrimeiraViaPrintDTO, boleto
								.getFcBoletoConsolidado(),
						dadosGeraisPrimeiraViaPrintDTO);
				
				//NSMSM_50206_CI_001 - Considerar quebra em nova pagina para evitar subreport overflow 
				setorDemFinFicArr.setIsSetorComecaSempreNovaPagina(Boolean.TRUE);
				
				
				int aux[] = gerarSetorDemonstrativoFichaArrecadacao(
						setorDemFinFicArr, idBoleto,
						dadosGeraisPrimeiraViaPrintDTO,
						dadosGeraisPrimeiraViaPrintDTO,
						dadosGeraisPrimeiraViaPrintDTO
								.getValorTotalNotaFiscal(), cidContrato, boleto
								.getFcBoletoConsolidado(), fatura, 0);

				// cria setor de Filiados

				//Gera o setor de filiados
				 //Setor de filiados comeca sempre em nova pagina se o demonstrativo estiver na 1a folha
				 //senao ele continuara o demonstrativo financeiro. 
				 int paginaAtualDemonstrativo = aux[0];
				 int auxContLinhas = aux[1];
					SetorDTO setorFiliados = criarSetorFiliados(dadosGeraisPrimeiraViaPrintDTO, fatura, paginaAtualDemonstrativo);
				 if (paginaAtualDemonstrativo < 3) {
					//Se demonstrativo ocupou apenas 1 coluna, o filiados sera 2 
					//colunas na frente. Se ocupou as 2 colunas, sera apenas 1 coluna a frente.
					paginaAtualDemonstrativo += (paginaAtualDemonstrativo % 2 == 0)? 1 : 2;
					auxContLinhas = 0;
				 } 
				 
				//NSMSM_50206_CI_001
				 if (existsItensEventuais) {
					 paginaAtualDemonstrativo = 2;	 
				 }else {
					 paginaAtualDemonstrativo = 1;
				 }

				if  (auxContLinhas < 900){
				   auxContLinhas = 2000;	
				}					    			    
				gerarSetorFiliados(setorFiliados, fatura, paginaAtualDemonstrativo, cidContrato, setorDemFinFicArr, auxContLinhas, ID_TITULO_FILIADOS);
				setorListaDTO.addSetor(setorFiliados);
			}
			
		}
		
		else if (tipoFatura.equals("NF") && fatura.getItensNotaFiscalParceiro() != null ) {
			if(fatura.getItensNotaFiscalParceiro(idNotaFiscal) != null) {
				// cria setor Nota Fiscal. Para cada parceiro é criado o setor
				if (fatura.getDadosNotaFiscalParceiro() != null) {
					Long idParceiro = null;
					for (Iterator iterParceiros = fatura.getIdParceiros().iterator(); iterParceiros.hasNext();) {
						
						idParceiro = (Long) iterParceiros.next();

						gerarSetorNotaFiscalEBT(setorListaDTO,dadosGeraisPrimeiraViaPrintDTO, cidContrato,
								idParceiro, fatura, listMensagemAnatelDTO);
					}
					calcularAgrupamentoNFParceiro(setorListaDTO, cidContrato);
				}
			}
		}
		int qdeNFNet;
		
		if(fatura.getPossuiNF() && fatura.getDadosGeraisNF() != null) {
			qdeNFNet = fatura.getDadosGeraisNF().size();
			for (DadosGeraisPrimeiraViaPrintDTO dadosGeraisPrimeiraVia : fatura.getDadosGeraisNF()) {
				
				if (dadosGeraisPrimeiraVia.isOperadoraNet().booleanValue()) { 
					if(fatura != null && fatura.getIdLote() != null){
						dadosGeraisPrimeiraVia.setCodigoLote(new Long(fatura.getIdLote().longValue()));
					}
	
					SetorDTO setorNFNet = this.criarSetorNotaFiscalNet(dadosGeraisPrimeiraVia,TipoSetorPrint.NOTA_FISCAL_NET,dadosGeraisPrimeiraViaPrintDTO.getFc_boleto_consolidado(), true, listComplementoCampoImportante);
					SetorDTO setorNotaFiscalTributosNet = this.criarSetorNotaFiscalNet(dadosGeraisPrimeiraVia, TipoSetorPrint.NOTA_FISCAL_TRIBUTOS_NET,dadosGeraisPrimeiraViaPrintDTO.getFc_boleto_consolidado(), true, listComplementoCampoImportante);
					gerarSetorNotaFiscalNet(setorNFNet, setorNotaFiscalTributosNet, dadosGeraisPrimeiraVia.getIdCobrancaNotaFiscal(), dadosGeraisPrimeiraVia, fatura, qdeNFNet);
					
					if (fatura.getUltimasOcorrencias() != null) {
						if (!fatura.getUltimasOcorrencias().trim().equals("")) {
							Map mapUltimasOcorrencias = new HashMap<String, String>();
							mapUltimasOcorrencias.put(PrintHouseConstants.LayoutArquivoPrintHouse.ULTIMAS_OCORRENCIAS, fatura.getUltimasOcorrencias());
							setorNFNet.addLinha(new LinhaDTO(LinhaDTO.TIPO_ITEN_ULTIMAS_OCORRENCIAS, mapUltimasOcorrencias, 0));

						}
					}
					
					setorListaDTO.addSetor(setorNFNet);
					setorListaDTO.addSetor(setorNotaFiscalTributosNet);
				
					dadosGeraisPrimeiraViaPrintDTO = dadosGeraisPrimeiraVia;
					
			
			        if(listComplementoCampoImportante != null){
				
				        for (SetorDTO setorDTO : listComplementoCampoImportante) {
						
					         setorListaDTO.addSetor(setorDTO);
						 }
					}
				 }
			 }
		 }
		
		
		/*if (fatura.getDetalheNotaFiscal() != null) {
			gerarSetorNotaFiscalNet(setorNotaFiscalNet, setorTributoNet,
					idNotaFiscal, dadosGeraisPrimeiraViaPrintDTO, fatura, 1); //TODO buscar qde NFNET

			*//**
			 * Alteração responsável para contemplar a funcionalidade das 5
			 * últimas ocorrências na Fatura de 2º via.
			 * 
			 * @author Sergio Ricardo Silva - implementação
			 * 
			 *//*
			if (fatura.getUltimasOcorrencias() != null) {
				if (!fatura.getUltimasOcorrencias().trim().equals("")) {
					Map mapUltimasOcorrencias = new HashMap<String, String>();
					mapUltimasOcorrencias
							.put(
									PrintHouseConstants.LayoutArquivoPrintHouse.ULTIMAS_OCORRENCIAS,
									fatura.getUltimasOcorrencias());
					setorNotaFiscalNet.addLinha(new LinhaDTO(
							LinhaDTO.TIPO_ITEN_ULTIMAS_OCORRENCIAS,
							mapUltimasOcorrencias, 0));

				}
			}

			setorListaDTO.addSetor(setorNotaFiscalNet);
			setorListaDTO.addSetor(setorTributoNet);
		}*/
		setorListaDTO.setValorCobrado(dadosGeraisPrimeiraViaPrintDTO
				.getValorCobrado());
		
		try {

			List<SetorDTO> listMensagemStreaming = obterMensagemStreaming(fatura.getIdBoleto());

			if (listMensagemStreaming != null) {

				for (SetorDTO setorDTO : listMensagemStreaming) {

					setorListaDTO.addSetor(setorDTO);
				}
			}
		} 
		catch (Exception e) {

			logger.info(new BasicAttachmentMessage(
					"ERRO AO BUSCAR MENSAGEM STREAMING",
					AttachmentMessageLevel.ERROR));
			e.printStackTrace();
		}
		
		faturaDTO = gerarFatura(cidContrato, setorListaDTO, faturaDTO);
		
		try {
			
			faturaDTO.setListMsgPlanoServicoEbt(fatura.getListMsgPlanoServicoEbt());
			
		} catch (Exception e) {

			logger.error(e.getMessage(),e);
		}
		
		return faturaDTO;
	}

	private Object notNullObject(Object dado) {
		return dado == null ? "" : dado;
	}

	private String notNullString(String dado) {
		return dado == null ? "" : dado;
	}
	
	private String notEqualsNullString(String dado) {
		
		if(dado == null || dado.equalsIgnoreCase("null")){
		   return "";
		}else{	
			return dado;
		}
	}
	
	private Object notNullObjectEspaco(Object dado) {
		return dado == null ? " " : dado;
	}

	private String notNullStringEspaco(String dado) {
		return dado == null ? " " : dado;
	}

	/**
	 * @number RF015_RF021
	 * @param siglaTipoLote
	 * @return
	 */
	protected String getCodigoOrigem(SnTipoLoteBean.Sigla siglaTipoLote) {

		String codigoOrigem = null;

		switch (siglaTipoLote) {
		case EMISSAOPRINT:
			codigoOrigem = PrintHouseConstants.Origem.CODIGO_ORIGEM_PRIMEIRA_VIA_PRINT_HOUSE;
			break;

		case SEGUNDA_VIA:
			codigoOrigem = PrintHouseConstants.Origem.CODIGO_ORIGEM_SEGUNDA_VIA_PRINT_HOUSE;
			break;
		case SEGUNDA_VIA_WEB:
			codigoOrigem = PrintHouseConstants.Origem.CODIGO_ORIGEM_SEGUNDA_VIA_WEB;
			break;
		case CPNF:
			codigoOrigem = PrintHouseConstants.Origem.CODIGO_ORIGEM_COPIA_NF_PRINT_HOUSE;
			break;
		default:
			//throw new IllegalStateException();
			throw new EmissaoBusinessResourceException(EmissaoResources.ERRO_SIGLA_TIPO_LOTE_INVALIDA);
		}

		return codigoOrigem;
	}

	/**
	 * Metodo que pegar o tipo de lote de uma colleção de lotes<br>
	 * Se uma das lotes tem o tipo de lote differente retorno com exeção <br>
	 * Utilzado qdo a geração de arquivo e para 'n' lotes.
	 * 
	 * @number RF015_RF021
	 * @author Robin Michael Gray
	 * @param lotes
	 *            Colleção de lotes para extrair o tipo de lote
	 * @return TipoLote commun de todos os lotes
	 * @throws BusinessJNetException
	 *             se uma lote tem o tipo de lote differente
	 */
	protected SnTipoLoteBean getTipoLote(Collection<SnLoteBean> lotes) {
		SnTipoLoteBean tipoLote = null;
		for (SnLoteBean lote : lotes) {
			//lote = (SnLoteBean)find(lote);
			if (tipoLote == null) {
				tipoLote = lote.getSnTipoLoteBean();
			} else {

				if (!tipoLote.getSgTipoLote().equals(
						lote.getSnTipoLoteBean().getSgTipoLote())) {
					Serializable[] params = new Serializable[] {
							"Tipo de Lote",
							"Os tipos de lotes de lotes não esta igual." };
					throw new EmissaoBusinessResourceException(EmissaoResources.ERRO_SIGLA_TIPO_LOTE_DIFERENTE);
				}
			}
		}
		return tipoLote;
	}

	/**
	 * Método protected de apoio, somente para retornar o valor do método
	 * obterValor da ParametroService recebe um Object que será enviada como
	 * parâmetro para o método
	 * 
	 * @number RF015_RF021
	 * @author Marcio Bellini - refactor Robin Michael Gray
	 * @version 1.0 - criação da interface - 29/05/2006
	 * @return String contendo o valor encontrado ou nulo
	 * @param String
	 *            de chave
	 */
	protected String obterParametroObrigatorioString(String cidContrato,
			String nomeParametro) {

		SnParametroBean snParametroBean = obterParametro(cidContrato,
				nomeParametro);

		if (snParametroBean != null) {
			return snParametroBean.getVlrParametroStr();
		}
		throw new EmissaoBusinessResourceException(
				EmissaoResources.NULL_PARAMETER, new Object[] { nomeParametro,
						"Cidade: " + cidContrato });
	}

	protected BigDecimal obterParametroObrigatorioNumerico(String cidContrato,
			String nomeParametro) {

		SnParametroBean snParametroBean = obterParametro(cidContrato,
				nomeParametro);

		if (snParametroBean != null) {
			return snParametroBean.getVlrParametro();
		}
		throw new EmissaoBusinessResourceException(
				EmissaoResources.NULL_PARAMETER, new Object[] { nomeParametro,
						"Cidade: " + cidContrato });
	}

	/**
	 * Método protected de apoio, somente para retornar o valor do método
	 * obterValor da ParametroService recebe um Object que será enviada como
	 * parâmetro para o método
	 * 
	 * @number RF015_RF021
	 * @author Marcio Bellini - refactor Robin Michael Gray
	 * @version 1.0 - criação da interface - 29/05/2006
	 * @return String contendo o valor encontrado ou nulo
	 * @param String
	 *            de chave
	 */
	protected SnParametroBean obterParametro(String cidContrato,
			String nomeParametro) {

		SnParametroBean snParametroBean = new SnParametroBean();
		SnCidadeOperadoraBean snCidadeOperadoraBean = new SnCidadeOperadoraBean();
		snCidadeOperadoraBean.setCidContrato(cidContrato);

		List<SnCidadeOperadoraBean> cidades = super.search(
				"lstSnCidadeOperadoraById", snCidadeOperadoraBean);

		if (cidades != null && cidades.size() > 0) {

			snCidadeOperadoraBean = cidades.get(0);

			if (snCidadeOperadoraBean != null) {

				snParametroBean.setEmpresa(snCidadeOperadoraBean);
				SnParametroKey key = new SnParametroKey();
				key.setNomeParametro(nomeParametro);
				key.setIdEmpresa(snCidadeOperadoraBean.getIdEmpresa());
				snParametroBean.setCompositeKey(key);

				// Estava usando: SnParametroBean.LST_SN_PARAMETRO_BY_FILTER que faz like
				// e causa problema.
				List retorno = super.search(
						SnParametroBean.LST_SN_PARAMETRO_BY_CID_CONTRATO,
						snParametroBean, true);
				if (retorno != null && !retorno.isEmpty()
						&& retorno.size() == 1) {
					snParametroBean = (SnParametroBean) retorno.iterator()
							.next();
					return snParametroBean;
				}

			}
		}

		return null;
	}


	/**
	 * @number RF015_RF021
	 * @param siglaTipoLote
	 * @param isPrintHouse
	 * @return
	 */
	protected SnConfiguracaoArquivoBean getConfiguracaoEnvio(
			SnTipoLoteBean.Sigla siglaTipoLote, boolean isPrintHouse) {

		String mascara = null;
		SnConfiguracaoArquivoBean configuracaoArquivo = new SnConfiguracaoArquivoBean();
		SnTipoControleArquivoBean tipoControleArquivo = new SnTipoControleArquivoBean();

		switch (siglaTipoLote) {
		case EMISSAOPRINT:
			if (isPrintHouse) {
				mascara = PrintHouseConstants.MascaraArquivo.EMISSAO_PRINT;
			} else {
				mascara = PrintHouseConstants.MascaraArquivo.EMISSAO_GLOBO;
			}
			break;
		case SEGUNDA_VIA:
		case SEGUNDA_VIA_WEB:
			if (isPrintHouse) {
				mascara = PrintHouseConstants.MascaraArquivo.SEGVIA_PRINT;
			} else {
				mascara = PrintHouseConstants.MascaraArquivo.SEGVIA_GLOBO;
			}
			break;
		case CPNF:
		case CPNF_WEB:
			if (isPrintHouse) {
				mascara = PrintHouseConstants.MascaraArquivo.CPNF_PRINT;
			} else {
				mascara = PrintHouseConstants.MascaraArquivo.CPNF_GLOBO;
			}
		default:
			break;
		}

		tipoControleArquivo.setSigla("PRINT");

		configuracaoArquivo.setMascaraArquivo(mascara);
		configuracaoArquivo.setTipoControleArquivo(tipoControleArquivo);

		// recupera o resultado executando a query com os parâmetros submetidos
		List retorno = super.search(
				SnConfiguracaoArquivoBean.LST_ALL_CONFIGURACAO_ARQUIVO,
				configuracaoArquivo);

		if (retorno != null && !retorno.isEmpty() && retorno.size() == 1) {
			configuracaoArquivo = (SnConfiguracaoArquivoBean) retorno
					.iterator().next();
			return configuracaoArquivo;
		}
		return null;
	}

	/**
	 * @number RF015_RF021
	 * @param registroEmissao
	 * @param dataVencimento
	 */
	private void setDataVencimentoRegistroEmissao(
			SnRegistroEmissaoBean registroEmissao, Date dataVencimento) {
		if (dataVencimento == null) {
			return; 
		} else {
			if (registroEmissao.getDtVctoInicial() == null
					|| dataVencimento
							.before(registroEmissao.getDtVctoInicial())) {
				registroEmissao.setDtVctoInicial(dataVencimento);
			}

			if (registroEmissao.getDtVctoFinal() == null
					|| dataVencimento.after(registroEmissao.getDtVctoFinal())) {
				registroEmissao.setDtVctoFinal(dataVencimento);
			}
		}
	}

	/**
	 * @number RF015_RF021
	 * @param operadora
	 * @return
	 * @throws JNetException
	 */
	protected Boolean getControleSegregacao(String cidade) {

		SnParametroBean snParametroBean = obterParametro(
				cidade,
				PrintHouseConstants.Parametro.CONTROLE_SEGREGACAO_ARQUIVO_OPERADORA);

		if (snParametroBean == null
				|| snParametroBean.getVlrParametroStr() == null
				|| "N".equals(snParametroBean.getVlrParametroStr())) {
			return Boolean.FALSE;
		} else {
			return Boolean.TRUE;
		}
	}
	
	protected Boolean getControleSegregacao(String cidade, String siglaLote) {
		if (EMISSAO_PRINT_BOLETO_AVULSO.getChaveSigla().equals(siglaLote)){
			return Boolean.FALSE;
		}
		return getControleSegregacao(cidade);
	}

	/**
	 * @number RF015_RF021
	 * @param siglaTipoLote
	 * @return
	 */
	protected boolean isArquivoParaPrintHouse(String siglaTipoLote) {
		
		if(siglaTipoLote.equals(SnTipoLoteBean.Sigla.CPNF_WEB.getChaveSigla())){
			return true;
		}else if(siglaTipoLote.equals(SnTipoLoteBean.Sigla.CPNF.getChaveSigla())){
			return true;	
		}else if(siglaTipoLote.equals(SnTipoLoteBean.Sigla.SEGUNDA_VIA.getChaveSigla())){
			return true;
		}else if(siglaTipoLote.equals(SnTipoLoteBean.Sigla.EMISSAOPRINT.getChaveSigla())){
			return true;
		}else if(siglaTipoLote.equals(SnTipoLoteBean.Sigla.EMISSAO_PRINT_BOLETO_AVULSO.getChaveSigla())){
			return true;
		}else{
			return false;
		}
		
	}

	/**
	 * @number RF015_RF021
	 * @param siglaTipoLote
	 * @return
	 */
	protected boolean isArquivoParaGlobo(String siglaTipoLote) {
		
		if(siglaTipoLote.equals(SnTipoLoteBean.Sigla.CPNF_WEB.getChaveSigla())){
			return true;
		}else if(siglaTipoLote.equals(SnTipoLoteBean.Sigla.SEGUNDA_VIA_WEB.getChaveSigla())){
			return true;	
		}else if(siglaTipoLote.equals(SnTipoLoteBean.Sigla.SEGUNDA_VIA.getChaveSigla())){
			return true;
		}else if(siglaTipoLote.equals(SnTipoLoteBean.Sigla.EMISSAOPRINT.getChaveSigla())){
			return true;
		}else{
			return false;
		}
		
	}

	/**
	 * @number RF015_RF021
	 * @param chaveRegistro
	 * @return
	 * @throws JNetException
	 * 
	 */
	private SnSituacaoProcessamentoBean getSituacaoProcessamento(
			SnSituacaoProcessamentoBean.Sigla situacaoProcessamento) {

		SnSituacaoProcessamentoBean snSituacaoProcessamentoBean = new SnSituacaoProcessamentoBean();
		snSituacaoProcessamentoBean
				.setIdSituacaoProcessamento(situacaoProcessamento.getChave());
		snSituacaoProcessamentoBean
				.setSgSituacaoProcessamento(situacaoProcessamento.getSigla());

		snSituacaoProcessamentoBean = (SnSituacaoProcessamentoBean) find(snSituacaoProcessamentoBean);

		return snSituacaoProcessamentoBean;
	}
	
	
	private SetorDTO criarSetorDemonstrativoFichaArrecadacao(
			ClientePrintDTO clientePrintDTO , String boletoConsolidado, ClienteNotaFiscalPrintDTO clienteNotaFiscalPrintDTO) {

		Map mapClientePrintDTO = new HashMap<String,String>();
		mapClientePrintDTO.put(
				PrintHouseConstants.LayoutArquivoPrintHouse.BAIRRO_NOTA_FISCAL,
				notNullObject(clientePrintDTO.getBairroNotaFiscal()));
		mapClientePrintDTO.put(
				PrintHouseConstants.LayoutArquivoPrintHouse.CEP_NOTA_FISCAL,
				StringUtils.prepad(notNullString(clientePrintDTO.getCepNotaFiscal()),8,'0'));
		mapClientePrintDTO.put(
				PrintHouseConstants.LayoutArquivoPrintHouse.CIDADE_NOTA_FISCAL, 
				notNullObject(clientePrintDTO.getCidadeNotaFiscal()));
		mapClientePrintDTO.put(PrintHouseConstants.LayoutArquivoPrintHouse.CODIGO_CLIENTE_NET,notNullString(clientePrintDTO.getCodOperadora() + "/" + StringUtils.prepad(clientePrintDTO.getCodigoClienteNET(),9,'0')));
		mapClientePrintDTO.put(
				PrintHouseConstants.LayoutArquivoPrintHouse.CPF_CNPJ_NOTA_FISCAL,
				notNullObject(clientePrintDTO.getCpfCnpjNotaFiscal()));
		mapClientePrintDTO.put(
				PrintHouseConstants.LayoutArquivoPrintHouse.DATA_EMISSAO_NOTA_FISCAL,
				notNullObject(clientePrintDTO.getDataEmissaoNotaFiscal()));
		mapClientePrintDTO.put(
				PrintHouseConstants.LayoutArquivoPrintHouse.DATA_VENCIMENTO_BOLETO,
				notNullObject(clientePrintDTO.getDataVencimentoBoleto()));
		mapClientePrintDTO.put(
				PrintHouseConstants.LayoutArquivoPrintHouse.ENDERECO_NOTA_FISCAL,
				notNullObject(clientePrintDTO.getEnderecoNotaFiscal()));
		mapClientePrintDTO.put(
				PrintHouseConstants.LayoutArquivoPrintHouse.ESTADO_NOTA_FISCAL,
				notNullObject(clientePrintDTO.getEstadoNotaFiscal()));
		mapClientePrintDTO.put(
				PrintHouseConstants.LayoutArquivoPrintHouse.MES_ANO_REFERENCIA, 
				notNullObject(clientePrintDTO.getMesAnoReferencia()));
		mapClientePrintDTO.put(
				PrintHouseConstants.LayoutArquivoPrintHouse.NOME_CLIENTE_NOTA_FISCAL,
				notNullObject(clientePrintDTO.getNomeClienteNotaFiscal()));
		
		Collection<LinhaDTO> cabecalhoQuebraPagina = new ArrayList<LinhaDTO>();
		
		cabecalhoQuebraPagina.add(new LinhaDTO(LinhaDTO.TIPO_ITEN_INICIO_PAGINA
				, new HashMap<String, String>()));
		
		
		Map mapClienteNotaFiscal = new HashMap();
		mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.NUMERO_NOTA_FISCAL,notNullObject(clienteNotaFiscalPrintDTO.getNumeroNotaFiscal()));
		mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.CFOP_NOTA_FISCAL,notNullObject(clienteNotaFiscalPrintDTO.getCfopNotaFiscal()));
		if (clienteNotaFiscalPrintDTO.getInscricaoEstadualNotaFiscal() == null) {
			mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.INSCRICAO_ESTADUAL_NOTA_FISCAL,PrintHouseConstants.NotaFiscal.IE_ISENTO);
		} else {
			mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.INSCRICAO_ESTADUAL_NOTA_FISCAL,notNullObject(clienteNotaFiscalPrintDTO.getInscricaoEstadualNotaFiscal()));
		}
		mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.VALOR_TOTAL_NOTA_FISCAL,notNullObject(clienteNotaFiscalPrintDTO.getValorTotalNotaFiscal()));
		mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.HASH_CODE,notNullObject(clienteNotaFiscalPrintDTO.getHashCode()));
		mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.ENDERECO_NOTA_FISCAL,notNullObject(clienteNotaFiscalPrintDTO.getEnderecoNotaFiscal()));
		mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.BAIRRO_NOTA_FISCAL,notNullObject(clienteNotaFiscalPrintDTO.getBairroNotaFiscal()));
		mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.CIDADE_NOTA_FISCAL,notNullObject(clienteNotaFiscalPrintDTO.getCidadeNotaFiscal()));
		mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.ESTADO_NOTA_FISCAL,notNullObject(clienteNotaFiscalPrintDTO.getEstadoNotaFiscal()));
		mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.CEP_NOTA_FISCAL,StringUtils.prepad(notNullString(clienteNotaFiscalPrintDTO.getCepNotaFiscal()),8,'0'));
		mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.CPF_CNPJ_NOTA_FISCAL,notNullObject(clienteNotaFiscalPrintDTO.getCpfCnpjNotaFiscal()));
		mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.NOME_CLIENTE_NOTA_FISCAL,notNullObject(clienteNotaFiscalPrintDTO.getNomeClienteNotaFiscal()));
		mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.CODIGO_CLIENTE_NET,notNullString(clienteNotaFiscalPrintDTO.getCodOperadora())+"/"+StringUtils.prepad(notNullString(clienteNotaFiscalPrintDTO.getCodigoClienteNET()),9,'0'));
		//notNullString(clienteNotaFiscalPrintDTO.getCodigoClienteNET()));
		mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.DATA_VENCIMENTO_BOLETO,notNullObject(clienteNotaFiscalPrintDTO.getDataVencimentoBoleto()));
		mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.MES_ANO_REFERENCIA,notNullObject(clienteNotaFiscalPrintDTO.getMesAnoReferencia()));
		mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.DATA_EMISSAO_NOTA_FISCAL,notNullObject(clienteNotaFiscalPrintDTO.getDataEmissaoNotaFiscal()));

		Map mapInicioNotaFiscal = new HashMap();
		mapInicioNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.SERIE_NOTA_FISCAL,notNullObject(clienteNotaFiscalPrintDTO.getSerieNotaFiscal()));
		mapInicioNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.MODELO_NOTA_FISCAL,clienteNotaFiscalPrintDTO.getModeloNotaFiscal());

		//cabecalhoQuebraPagina.add(new LinhaDTO(LinhaDTO.TIPO_ITEN_MENSAGEM_INICIO_FATURA,mapInicioNotaFiscal));
		cabecalhoQuebraPagina.add(new LinhaDTO(LinhaDTO.TIPO_ITEN_INICIO_NF_NET));
		cabecalhoQuebraPagina.add(new LinhaDTO(LinhaDTO.TIPO_ITEN_DADOS_ASS_NET_SEM_NF, mapClienteNotaFiscal));
		
		return new SetorDTO(
				TipoSetorPrint.DEMONSTRATIVO_FINANCEIRO_FICHA_ARRECADACAO,
				null, null, cabecalhoQuebraPagina, Boolean.FALSE, Boolean.TRUE);

	}

	/**
	 * @number RF015_RF021
	 * @param siglaSituacaoArquivo
	 * @return
	 * @throws JNetException
	 * @ejb.transaction type="Required"
	 */
	protected SnStatusArquivoBean getStatusArquivo(
			SnStatusArquivoBean.Sigla statusArquivo) {
		SnStatusArquivoBean statusArquivoBean = new SnStatusArquivoBean();
		statusArquivoBean.setSiglaStatus(statusArquivo.getChaveSigla());
		statusArquivoBean
				.setIdStatusArquivo(statusArquivo.getValue());

		return (SnStatusArquivoBean) find(statusArquivoBean);
	}

	protected void validaParametroNulo(Object parametro, String paramName,
			String otherInfos) {
		if (parametro == null) {
			throw new EmissaoBusinessResourceException(
					EmissaoResources.NULL_PARAMETER, new Object[] { paramName,
							otherInfos });
		}
	}
		
	private Integer geraDigVerificador( String valor ){
		
		int aux = 0; 
		int multiplicador = 2;
		int i = valor.length(); 
		
		while(i > 0 ){			
			aux = aux + multiplicador *  Integer.parseInt(valor.substring(i-1,i));   
			multiplicador = multiplicador + 1;
			if(multiplicador > 9){
				multiplicador = 2;
			}		
			i = i - 1;		
		}
		
		aux = aux % 11;
		aux = 11 - aux;
		
		if (aux == 11 ){
			return 1;
		}else if(aux == 10){
			return 	0;
		}else{
			return aux; 
		}
		
	}
	
	private String valor(Double valor, Integer tamanho, Integer decimais){
    	NumberFormat numberFormat = NumberFormat.getInstance(new Locale("pt", "BR"));
    	String valorConstante;
    	int size = tamanho.intValue();
    	StringBuffer buffer = new StringBuffer( size );
    	
    	numberFormat.setMaximumFractionDigits( decimais.intValue() );
    	numberFormat.setMinimumFractionDigits(decimais.intValue());
    	numberFormat.setGroupingUsed(false);
    	valorConstante = numberFormat.format( valor.doubleValue() );
		
		if( size > valorConstante.length() ){
			buffer.append( filler( new Integer( size - valorConstante.length() ), ' ' ) );
			buffer.append( valorConstante );
		}else{
			buffer.append( valorConstante.substring( 0, size ) );
		}
		return buffer.toString();
    }
	
	private String filler(Integer tamanho, char ch) {

		char[] cs = new char[tamanho.intValue()];

		Arrays.fill(cs, ch);

		return new String(cs);
	}
		
	private Date getDate(Object data) {
		return this.getDate(data, "yyyy-MM-dd");
	}
	
	private Date getDate(Object data, String format) {
		
		Date dataRetorno = null;

		try {

			if(data != null) {
				dataRetorno = org.apache.commons.lang.time.DateUtils.parseDate(data.toString(), new String[] {format});
			}

		} catch (ParseException e) {

		}

		return dataRetorno;

	}
	
	private Double getDouble(Object valor) {
		
		Double doubleValue = null;
		
		if(valor != null) {
			doubleValue = Double.valueOf(valor.toString());
		}
		
		return doubleValue;
		
	}
	
	private Integer getInteger(Object valor) {
		
		Integer integerValue = null;
		
		if(valor != null) {
			integerValue = Integer.valueOf(valor.toString());
		}
		
		return integerValue;
		
	}
	
	private Long getLong(Object valor) {
		
		Long longValue = null;
		
		if(valor != null) {
			longValue = Long.valueOf(valor.toString());
		}
		
		return longValue;
		
	}

	/**
	 * @ejb.interface-method view-type = "both"
	 * @ejb.transaction type="Required"
	 */
	public void testarDAO(DynamicBean bean) {
		ArquivoPrintHouseDAO dao = getAphDao();
	//	dao.buscarDadosFaturaNet(46640686l);
	//	dao.buscarDadosFaturaEbt(46642122l);
	//	dao.buscarDadosFaturaNetEbt(46642122l);
	}
	
	private String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        t.printStackTrace(pw);
        pw.flush();
        sw.flush();
        return sw.toString();
    }
	

	private ArquivoPrintHouseDAO getAphDao() {
		return (ArquivoPrintHouseDAO) getDAOFactory().getDAO("print", getUserSession().getCurrentDbService());
	}
	
	private static String converteParaHoras(Double arg0) {

		int horas = (int) (arg0 / 60);		
		int minutos = (int) (arg0 - (horas * 60));
		//double mod = (double) Math.abs(( arg0 % 60 )) - ((int) Math.abs( arg0 % 60 ));
		//int segundos = (int) (mod * 60) ;

		int segundos = (int) (Double.parseDouble("0." + ((arg0 + "").split("\\."))[1]) * 60);
		
		String horasFormatada = StringUtils.prepad(String.valueOf(horas),1,'0');
		String minutosFormatado = StringUtils.prepad(String.valueOf(minutos),2,'0');
		String segundosFormatado = StringUtils.prepad(String.valueOf(segundos),2,'0');
		

		String horaMinutoFormatada = horasFormatada + "h" + minutosFormatado+"m"+segundosFormatado+"s";

		return horaMinutoFormatada;
	}
	
	private void inserirPPVlrParceiroBatch(
			ArquivoPrintHouseDTO arquivoPrintHouseDTO
			, List<DadosFatura> lstDadosFatura
			) {
		
		logger.info(new BasicAttachmentMessage("Inicio da inclusao da pp_vlr_parceiro_fatura em lote.", AttachmentMessageLevel.INFO));

		// Efetua flush dos registros atualizados antes de entrar no loop, pois e necessario 
		// o idControleArquivo e este deve ja estar no banco, nao na memoria do hibernate
		getCurrentDAO().flush();					

		// Monta a tabela completa de origem emissao
		SnOrigemEmissaoBean snOrigemEmissaoBean = new SnOrigemEmissaoBean();
		List<SnOrigemEmissaoBean> lstSnOrigemEmissaoBean = this.search("lstSnOrigemEmissao", snOrigemEmissaoBean);
		
		// Cria o batch sql update para o update na sn_boleto
		PPValorParceiroSQLUpdateBatchBuilder ppVlrParcBatchBuilder = new PPValorParceiroSQLUpdateBatchBuilder();
		BatchSqlUpdate ppVlrParcBatchSqlUpdate = super.getCurrentDAO().createBatchSqlUpdate(ppVlrParcBatchBuilder);
		
		// Cria o batch sql update para o insert na pp_vlr_parceiro_fatura
		SnBoletoStatusSQLUpdateBatchBuilder boletoBatchBuilder = new SnBoletoStatusSQLUpdateBatchBuilder();
		BatchSqlUpdate boletoBatchSqlUpdate = super.getCurrentDAO().createBatchSqlUpdate(boletoBatchBuilder);
		
		SnBoletoFormaEnvioSQLUpdateBatchBuilder boletoFormaEnvio = new SnBoletoFormaEnvioSQLUpdateBatchBuilder();
		BatchSqlUpdate boletoFormaEnvioBatchSqlUpdate = super.getCurrentDAO().createBatchSqlUpdate(boletoFormaEnvio);
		
		BoletoAvulsoBatchBuilder boletoAvulsoBuilder = new BoletoAvulsoBatchBuilder();
		BatchSqlUpdate boletoAvulsoBatchUpdate = super.getCurrentDAO().createBatchSqlUpdate(boletoAvulsoBuilder);
		
		// Itera toda a colecao de boletos inserindo em lote na pp_vlr_parceiro_fatura
		for (DadosFatura dadosFatura : lstDadosFatura) {
			
			if (Sigla.EMISSAO_PRINT_BOLETO_AVULSO.getChaveSigla().equals(dadosFatura.getSgTipoLote())){
				Object[] params = new Object[]{
						new Date(),
						2,
						dadosFatura.getIdBoleto()
				};
				
				boletoAvulsoBatchUpdate.update(params);
				
				continue;
			}
			
			Map<Long, Object[]> mapCobrancaNet = dadosFatura.getMapCobrancaNet();
			Map<Long, Object[]> mapCobrancaParceiro = dadosFatura.getMapCobrancaParceiro();
			
			// Obtem a origem emissao do lote
			String sgTipoLote = dadosFatura.getSgTipoLote(); //lote.getSnTipoLoteBean().getSgTipoLote();
			snOrigemEmissaoBean = this.obterOrigemEmissao(sgTipoLote, lstSnOrigemEmissaoBean);
			
			// Obtem a quantidade de paginas da fatura Net e Parceiro
			Integer[] paginas = this.obterPaginasPorParceiro(dadosFatura, false);
			int numPaginasNet = paginas[0];
			int numPaginasParceiro = paginas[1]; 
			
			// Quando a fatura nao for coboletada e for do Parceiro, as paginas identificadas
			// como Net deverao ser contabilizadas no Parceiro e nao na Net
			if ((mapCobrancaNet == null || mapCobrancaNet.size() == 0)
					&& (mapCobrancaParceiro != null && mapCobrancaParceiro.size() > 0)) {
				numPaginasParceiro += numPaginasNet;
			}
			
			// Itera a lista de cobrancas net para inserir na tabela
			if (mapCobrancaNet != null) {
				
				Iterator it = mapCobrancaNet.keySet().iterator();
				while (it.hasNext()) {
					
					Long idCobrancaNet = (Long)it.next();
					
					Object[] campos = mapCobrancaNet.get(idCobrancaNet);
					Date dtVenctoCobranca = (Date)campos[0];
					Double vlrTotalCobranca = (Double)campos[1];
					
					Object[] params = new Object[] {
							dadosFatura.getIdControleArquivo()
							, dadosFatura.getNumContrato()
							, dadosFatura.getCidContrato()
							, dtVenctoCobranca
							, numPaginasNet
							, new Date()
							, snOrigemEmissaoBean.getIdOrigemEmissao()
							, vlrTotalCobranca
							, dadosFatura.getIdBoleto()
							, idCobrancaNet
							, idCobrancaNet
							, idCobrancaNet
							, null
					};
					
					ppVlrParcBatchSqlUpdate.update(params);
					
					// Zera o numero de paginas depois de inserir a primeira cobranca
					numPaginasNet = 0;
					
				}
				
			}
			
			if (mapCobrancaParceiro != null) {
				
				// Itera a lista de cobrancas parceiro para inserir na tabela
				Iterator it = mapCobrancaParceiro.keySet().iterator();
				while (it.hasNext()) {
					
					Long idCobrancaParceiro = (Long)it.next();
					
					Object[] campos = mapCobrancaParceiro.get(idCobrancaParceiro);
					Date dtVenctoCobranca = (Date)campos[0];
					Double vlrTotalCobranca = (Double)campos[1];
					
					Object[] params = new Object[] {
							dadosFatura.getIdControleArquivo()
							, dadosFatura.getNumContrato()
							, dadosFatura.getCidContrato()
							, dtVenctoCobranca
							, numPaginasParceiro
							, new Date()
							, snOrigemEmissaoBean.getIdOrigemEmissao()
							, vlrTotalCobranca
							, dadosFatura.getIdBoleto()
							, null
							, null
							, null
							, idCobrancaParceiro
					};
					
					ppVlrParcBatchSqlUpdate.update(params);
					
					// Zera o numero de paginas depois de inserir a primeira cobranca
					numPaginasParceiro = 0;
					
				}
				
			}
			
			if (sgTipoLote.equals(SnTipoLoteBean.Sigla.SEGUNDA_VIA.getChaveSigla()) || sgTipoLote.equals(SnTipoLoteBean.Sigla.SEGUNDA_VIA_WEB.getChaveSigla())) {
			boletoBatchSqlUpdate.update(new Object[] {PrintHouseConstants.STATUS_IMPRESSO_BOLETO, dadosFatura.getIdBoleto()});
			}else{
				// Atualiza situacao do boleto
				boletoFormaEnvioBatchSqlUpdate.update(new Object[] {PrintHouseConstants.STATUS_IMPRESSO_BOLETO, dadosFatura.getFormaEnvioFatura(), dadosFatura.getIdBoleto()});				
			}
		}

		// Manda para o banco o resto das atualizacoes
		ppVlrParcBatchSqlUpdate.flush();
		boletoBatchSqlUpdate.flush();
		boletoFormaEnvioBatchSqlUpdate.flush();
		boletoAvulsoBatchUpdate.flush();
		
		logger.info(new BasicAttachmentMessage("Termino da inclusao da pp_vlr_parceiro_fatura em lote.", AttachmentMessageLevel.INFO));

	}

	private SnOrigemEmissaoBean obterOrigemEmissao(String sgTipoLote, List<SnOrigemEmissaoBean> lstSnOrigemEmissaoBean) {
		
		String ccOrigemEmissao = null;
		
		if (sgTipoLote.equals(SnTipoLoteBean.Sigla.EMISSAOPRINT.getChaveSigla())) {
			ccOrigemEmissao = "PP";
		}
		if (sgTipoLote.equals(SnTipoLoteBean.Sigla.SEGUNDA_VIA.getChaveSigla())) {
			ccOrigemEmissao = "SP";
		}
		if (sgTipoLote.equals(SnTipoLoteBean.Sigla.SEGUNDA_VIA_WEB.getChaveSigla())) {
			ccOrigemEmissao = "SW";
		}
		if (sgTipoLote.equals(SnTipoLoteBean.Sigla.CPNF.getChaveSigla())) {
			ccOrigemEmissao = "NP";
		}
		if (sgTipoLote.equals(SnTipoLoteBean.Sigla.CPNF_WEB.getChaveSigla())) {
//			ccOrigemEmissao = "NP"; // Nao tem correspondente na tabela sn_origem_emissao
		}
		
		for (SnOrigemEmissaoBean snOrigemEmissaoBean : lstSnOrigemEmissaoBean) {
			if(snOrigemEmissaoBean.getCcOrigemEmissao().equals(ccOrigemEmissao)) {
				return snOrigemEmissaoBean;
			}
		} 
		
		return null;
		
	}

	private String getTipoFatura (FaturaNetDTO fatura) {
		
		if ( fatura.getFcBoletoConsolidado().equalsIgnoreCase("N") || 
				fatura.getFcBoletoConsolidado().equalsIgnoreCase("R") || 
				( fatura.getFcBoletoConsolidado().equalsIgnoreCase("D") && fatura.isOperadoraNet() ) ||			  
				fatura.getFcBoletoConsolidado().equalsIgnoreCase("M")) {			
		
				return "N"; // Net
				
		} else if (fatura.getFcBoletoConsolidado().equalsIgnoreCase("C") || 
				fatura.getFcBoletoConsolidado().equalsIgnoreCase("B")){
			
			return "C"; // Co-boletado
			
		} else if ( fatura.getFcBoletoConsolidado().equalsIgnoreCase("P") || 
				fatura.getFcBoletoConsolidado().equalsIgnoreCase("A") ||
				( fatura.getFcBoletoConsolidado().equalsIgnoreCase("D") && fatura.isOperadoraNet() == false )){

			return "P"; // Parceiro
			
		}

		return "";
		
	}
	
	/**
	 * @number RF015_RF021
	 * @param lote
	 * @param idBoleto
	 * @param arquivoPrintHouseDTO
	 * @param gerarArquivo
	 * @throws JNetException
	 * @ejb.interface-method view-type = "both"
     * @ejb.transaction type="Required"
     * 
	 */
	public  void atualizarBoteto(Long idBoleto){
		
		SnBoletoBean bol = new SnBoletoBean();
		bol.setIdBoleto(idBoleto);
		bol = (SnBoletoBean) super.find(bol);
		bol.setStatusImpressao("S");
		this.update(bol, getUserSession().getCurrentDbService());
	}

	public class QuebraAux {
		
		private int tamLinhaCabecalho = 0;
		private int tamLinha = 0;
		private boolean quebrouPagina = false;
		
		public int getTamLinhaCabecalho () {
			return this.tamLinhaCabecalho;
		}
		
		public void setTamLinhaCabecalho (int tamLinhaCabecalho) {
			this.tamLinhaCabecalho = tamLinhaCabecalho;
		}
		
		public int getTamLinha () {
			return this.tamLinha;
		}
		
		public void setTamLinha (int tamLinha) {
			this.tamLinha = tamLinha;
		}
		
		public void addTamLinhaCabecalho (int tamLinhaCabecalho) {
			this.tamLinhaCabecalho += tamLinhaCabecalho;
		}
		
		public boolean getQuebrouPagina () {
			return this.quebrouPagina;
		}
		
		public void setQuebrouPagina (boolean quebrouPagina) {
			this.quebrouPagina = quebrouPagina;
		}
		
		private int[][] lstTamLinhaDetalheNet;
		private int[][] lstTamLinhaDemonstrativoFinanceiro;
		private int[][] lstTamLinhaDetalheNETEBT;
		
		public int obterTamLinhaDetalheNet(Integer tipo, Integer formatacao) {
			
			formatacao = formatacao == null ? 1 : formatacao;
			
			if (this.lstTamLinhaDetalheNet == null) {
				
				this.lstTamLinhaDetalheNet = new int[][] {
						new int[] {LinhaDTO.TIPO_ITEN_CABECALHO_DETALHAMENTO, 01, 134}
						, new int[] {LinhaDTO.TIPO_ITEN_NET_CABECALHO_AGRUPAMENTO, 01, 120}
						, new int[] {LinhaDTO.TIPO_ITEN_NET_DETALHAMENTO, 01, 100}
						, new int[] {LinhaDTO.TIPO_ITEN_NET_RODAPE_AGRUPAMENTO, 01, 100}
						, new int[] {LinhaDTO.TIPO_ITEN_NET_TOTAL_NOTA_FISCAL, 01, 200}
						, new int[] {LinhaDTO.TIPO_ITEN_NET_TRIBUTOS_NOTA_FISCAL, 01, 265}
						, new int[] {LinhaDTO.TIPO_ITEN_NET_RESERVADO_FISCO_LABEL, 01, 100}
						, new int[] {LinhaDTO.TIPO_ITEN_NET_RESERVADO_FISCO_CODIGO, 01, 100}
						, new int[] {LinhaDTO.TIPO_ITEN_NET_MENSAGEM_RODAPE, 01, 100}
						, new int[] {LinhaDTO.TIPO_ITEN_BLANK_LINE, 01, 100}
				};
				
			}
			
			for (int i = 0; i < this.lstTamLinhaDetalheNet.length; i++) {
				
				if (this.lstTamLinhaDetalheNet[i][0] == tipo 
						&& this.lstTamLinhaDetalheNet[i][1] == formatacao) {
					return this.lstTamLinhaDetalheNet[i][2];
				}
				
			}
			
			return 0;
			
		}
		
		public int obterTamLinhaDemonstrativoFinanceiro(Integer tipo, Integer formatacao) {
			
			formatacao = formatacao == null ? 1 : formatacao;
			
			if (this.lstTamLinhaDemonstrativoFinanceiro == null) {
				
				this.lstTamLinhaDemonstrativoFinanceiro = new int[][] {
						new int[] {LinhaDTO.TIPO_ITEN_CABECALHO_DEMONSTRATIVO_FINACEIRO, 01, 125}
						, new int[] {LinhaDTO.TIPO_ITEN_ITEN_DEMONSTRATIVO_FINACEIRO, 01, 400}
						, new int[] {LinhaDTO.TIPO_ITEN_ITEN_DEMONSTRATIVO_FINACEIRO, 02, 400}
						, new int[] {LinhaDTO.TIPO_ITEN_TOTAL_DEMONSTRATIVO_FINACEIRO, 01, 400}
						, new int[] {LinhaDTO.TIPO_ITEN_BLANK_LINE, 01, 400}
						, new int[] {LinhaDTO.TIPO_ITEN_SERVICO_PERIODO, 01, 400}
						, new int[] {LinhaDTO.TIPO_ITEN_CABECALHO_AGRUPAMENTO_DEMO_FINACEIRO, 02, 700}
						, new int[] {LinhaDTO.TIPO_ITEN_SUBCABECALHO_AGRUPAMENTO_DEMO_FINACEIRO, 03, 400}
						, new int[] {LinhaDTO.TIPO_ITEN_RODAPE_AGRUPAMENTO_DEMO_FINACEIRO, 01, 400}
						, new int[] {LinhaDTO.TIPO_ITEN_SUBRODAPE_AGRUPAMENTO_DEMO_FINACEIRO, 04, 400}
						, new int[] {LinhaDTO.TIPO_ITEN_RODAPE_AGRUPAMENTO_DEMO_FINACEIRO, 05, 400}
				};
				
			}
			
			for (int i = 0; i < this.lstTamLinhaDemonstrativoFinanceiro.length; i++) {
				
				if (this.lstTamLinhaDemonstrativoFinanceiro[i][0] == tipo 
						&& this.lstTamLinhaDemonstrativoFinanceiro[i][1] == formatacao) {
					return this.lstTamLinhaDemonstrativoFinanceiro[i][2];
				}
				
			}
			
			return 0;
			
		}
		
	}
	
	/**
	 * Método responsável por atualizar a Situação do Lote.
	 * 
	 * @author Harrisson Ferreira Gomes/Everton Ken Tomita - implementação
	 *         Marcio Bellini - refactor Robin Michael Gray
	 * @version 1.0 - criação do Metodo - 27/03/2006
	 * @number RF015_RF021
	 * @return
	 * @param Lote
	 *            lote
	 * @param String
	 *            chave
	 * @semantics <br>
	 * 
	 * @ejb.interface-method view-type = "both"
	 * @ejb.transaction type="RequiresNew"
	 * 
	 * 
	 */
	public List<SnLoteBean> atualizarSituacaoLoteTask(
			Collection<SnLoteBean> lotes, String sigla) throws Exception {

		List<SnLoteBean> lotesToProcess = new ArrayList<SnLoteBean>();
		StringBuffer lotesStr = new StringBuffer();
		for (SnLoteBean lote : lotes) {

			// lote = this.atualizarSituacaoLote(lote, sigla);

			ArquivoPrintHouseDAO dao = getAphDao();
			int qtdUpdated = dao.updateLotesToProcess(lote.getIdLote());

			if (qtdUpdated > 0) {
				lotesToProcess.add(lote);
			}else{
				lotesStr.append(lote.getIdLote());
				
			}
			logger
					.info(new BasicAttachmentMessage(
							"Alteração da situação do lote " + lote.getIdLote()
									+ " para processando.",
							AttachmentMessageLevel.INFO));

		}

		if (lotesToProcess.size() == lotes.size()) {
			return lotesToProcess;
		} else {
			throw new EmissaoBusinessResourceException(
					"error.reemissaonf.lote.ja.emitido",
					new Object[] { "Lotes : " +  lotesStr.toString()});
	

		}

	}
	
	/**
	 * @author Alex Simas Braz
	 * @since 14/06/2007
	 * 
	 * @ejb.interface-method view-type = "both"
     * @ejb.transaction type="Required" 
	 * 
	 **/
	public List<ArquivoRemessaTransporteDTO> gerarArquivoPrintHouseTesteMensagem(
			List<String> criterios, Date dataVencimento, String tipoMsg) {

		SnCidadeOperadoraBean snCidadeOperadoraBean = new SnCidadeOperadoraBean();
		List<SnCidadeOperadoraBean> cidades = super.search("lstSnCidadeOperadora", snCidadeOperadoraBean);
		ArquivoPrintHouseDTO arquivoPrintHouseDTO = null;
		
		arquivoPrintHouseDTO = new ArquivoPrintHouseDTO("EMISPRT");
				
		List<ArquivoRemessaTransporteDTO> arquivoTransporteList = new ArrayList<ArquivoRemessaTransporteDTO>();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
		
		String dtVencimentoStr = null;
		if (dataVencimento != null) {
			dtVencimentoStr = dateFormat.format(dataVencimento);
		}	
		
		List<String> prefixoList = new ArrayList<String>();
		for(String criterio : criterios){
			SnCriterioSegmentacaoBoletoBean seg = new SnCriterioSegmentacaoBoletoBean();
			seg.setIdCriterio(Integer.parseInt(criterio));
			seg = (SnCriterioSegmentacaoBoletoBean) super.findByPrimaryKey(seg);
			prefixoList.add(seg.getPrefixo());			
		}
		
		String extensaoArquivo = ".rem";
		int i = 1;
		for (SnCidadeOperadoraBean cidade :  cidades ){
			
			String nomeArquivo = "VALIDACAO_MSG_"+cidade.getCiNome()+"_"+dtVencimentoStr;
			i++;
			SnParametroBean snParametroBean = obterParametro(cidade.getCidContrato(), PrintHouseConstants.Parametro.DIRETORIO_DESTINO_OPERADORA_ARQUIVO_PRINT);
	
			if (snParametroBean.getVlrParametroStr() == null) {
				// Sem diretorio
				snParametroBean = obterParametro(cidade.getCidContrato(), PrintHouseConstants.Parametro.DIR_ARQ_PRINT);
			}
			
			String dir_arq_print = snParametroBean.getVlrParametroStr();
		
			String qt_fat_arq_print = obterParametroObrigatorioString(cidade.getCidContrato(),PrintHouseConstants.Parametro.QT_FAT_ARQ_PRINT);
			String qt_ln_pag_fat_print = obterParametroObrigatorioString(cidade.getCidContrato(),PrintHouseConstants.Parametro.QT_LN_PAG_FAT_PRINT);
			String qt_ln_arquivo_print = obterParametroObrigatorioString(cidade.getCidContrato(),PrintHouseConstants.Parametro.QUANTIDADE_LINHA_ARQUIVO_PRINT);						
			String ext_arq_print = obterParametroObrigatorioString(cidade.getCidContrato(),PrintHouseConstants.Parametro.EXT_ARQ_PRINT);
			String nm_temp_arq_print = obterParametroObrigatorioString(cidade.getCidContrato(),PrintHouseConstants.Parametro.NM_TEMP_ARQ_PRINT);
			

			arquivoPrintHouseDTO.setQuantidadeFaturaPorArquivoPrint(new Integer(qt_fat_arq_print));
			arquivoPrintHouseDTO.setQuantidadeMaximaLinhasPorFolha(new Integer(qt_ln_pag_fat_print));
			arquivoPrintHouseDTO.setQuantidadeMaximaNumeroLinhasArquivo(new Integer(qt_ln_arquivo_print));
			arquivoPrintHouseDTO.setExtensaoArquivoPrint(ext_arq_print);
			arquivoPrintHouseDTO.setNomeTemporarioDoArquivoPrint(nm_temp_arq_print);
			arquivoPrintHouseDTO.setDiretorioGravacaoArquivoPrint(dir_arq_print);
			arquivoPrintHouseDTO.iniciarArquivo(cidade,Boolean.FALSE);
			
			File localizacaoArquivoGerado = new File(dir_arq_print + File.separator + nomeArquivo + extensaoArquivo);		
					 
			GenerateFile gerarArquivo; 
			InputStream inputStreamLeiauteXML = null;
			try {
				inputStreamLeiauteXML = ArquivoPrintHouseServiceEJBImpl.class
						.getResourceAsStream(PrintHouseConstants.Parametro.PACOTE_LAYOUT_ARQUIVO_GERACAO);

				gerarArquivo = new GenerateFile(localizacaoArquivoGerado,
						inputStreamLeiauteXML);
				
				gerarArquivo.setLineSeparator(GenerateFile.LINE_SEPARATOR_WINDOWS);
				
			} finally {
				if (inputStreamLeiauteXML != null) {
					try {
						inputStreamLeiauteXML.close();
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						inputStreamLeiauteXML = null;
					}
				}
			}			
			
			
			arquivoPrintHouseDTO.getCurrentArquivo().setGerarArquivo(gerarArquivo);
			SnRegistroEmissaoBean registroEmissao = new SnRegistroEmissaoBean();
			registroEmissao.setIdRegistroEmissao(125);
			registroEmissao.setDhProcessamento(new Date());
			arquivoPrintHouseDTO.setRegistroEmissao(registroEmissao);
			this.gravarArquivoPrintHouse(gerarArquivo, this.cabecalhoArquivo(arquivoPrintHouseDTO));
			this.gravarArquivoPrintHouse(gerarArquivo, this.operadoraNet(cidade));

			
			MensagemDTO mensagem = new MensagemDTO();			
			mensagem.setDataVencimentoTitulo(dataVencimento);
			mensagem.setCidContrato(cidade.getCidContrato());
			mensagem.setIdPrefixos(prefixoList);
			Collection<MensagemDTO> colMensagemPrint = null;			
			List<LinhaDTO> colLinhasDTO = new ArrayList<LinhaDTO>();
			
			colMensagemPrint = super.getDadosBeanByQuery("listarMensagemAssociadaTestePrint", mensagem, false, MensagemDTO.class);
			
			List<String> prefixosToGenerate = new ArrayList<String>();
			String prefixo = null;
			if (colMensagemPrint != null) {

				for (Iterator iterMensagens = colMensagemPrint.iterator(); iterMensagens.hasNext();) {
					MensagemDTO mensagemAssociada = (MensagemDTO) iterMensagens.next();
					
					
					
					colLinhasDTO.add(llk.criarLinhaItenMensagem(cidade, mensagemAssociada));
					
					if((prefixo == null) || (!prefixo.equals(mensagemAssociada.getPrefixo()))){
						prefixosToGenerate.add(mensagemAssociada.getPrefixo());
						prefixo = mensagemAssociada.getPrefixo();
					}
					
				}
			}
			
			
			for (Iterator iterLinhas = colLinhasDTO.iterator(); iterLinhas
			.hasNext();) {
				this.gravarArquivoPrintHouse(arquivoPrintHouseDTO.getCurrentArquivo()
						.getGerarArquivo(), (LinhaDTO) iterLinhas
				.next());
			}
			
			Long idBoleto = 1L;
			
			this.criarComplementoCampoImportante(idBoleto);
			List<SetorDTO> listComplementoCampoImportante = obterComplementoCampoImportante(idBoleto);

			for ( String prefixos : prefixosToGenerate ) {
			
					DadosGeraisPrimeiraViaPrintDTO dadosGeraisPrimeiraVia = new DadosGeraisPrimeiraViaPrintDTO();
					FaturaNetDTO fatura = new FaturaNetDTO();
					fatura.setOperadoraNet(true);
					fatura.setFcBoletoConsolidado("N");
					fatura.setCidContrato(cidade.getCidContrato());
					MinhaNetDTO minhaNet = new MinhaNetDTO();
					minhaNet.setMINHANET("NET TV");
					minhaNet.setTIPOGRUPO("NET TV");
					List<MinhaNetDTO> lmn = new ArrayList<MinhaNetDTO>();
					lmn.add(minhaNet);
					fatura.setMinhaNet(lmn);
					dadosGeraisPrimeiraVia.setFc_boleto_consolidado("N");
					dadosGeraisPrimeiraVia.setPrefixo(prefixos);
					dadosGeraisPrimeiraVia.setCodigoLote(new Long(1));
					dadosGeraisPrimeiraVia.setNomeClienteCobranca("XXXXXXXXXXXX");
					dadosGeraisPrimeiraVia.setNomeClienteNotaFiscal("XXXXXXXXXXXX");
					dadosGeraisPrimeiraVia.setEnderecoCobranca("XX XXXX XXXXX , XXXX");
					dadosGeraisPrimeiraVia.setBairroCobranca("XXX XXXX XXXXXX");
					dadosGeraisPrimeiraVia.setCidadeCobranca("XXX XXXX");
					dadosGeraisPrimeiraVia.setEstadoCobranca("XX");
					dadosGeraisPrimeiraVia.setCepCobranca("11111111");
					dadosGeraisPrimeiraVia.setIdentificadorBoleto(new Long(idBoleto));
					dadosGeraisPrimeiraVia.setIdBoleto(new Long(idBoleto));
					dadosGeraisPrimeiraVia.setCodOperadora("003");
					dadosGeraisPrimeiraVia.setCodigoClienteNET("111111111");
					dadosGeraisPrimeiraVia.setNumeroNotaFiscal("111111");					
					dadosGeraisPrimeiraVia.setCfopNotaFiscal("1111");
					dadosGeraisPrimeiraVia.setValorTotalNotaFiscal(new Double(10));
					dadosGeraisPrimeiraVia.setValorCobrado(new Double(10));
					dadosGeraisPrimeiraVia.setHashCode("94A9.EE60.9673.843D.A425.64E0.EB2C.B62C");
					dadosGeraisPrimeiraVia.setCodigoDeBarras("0000000000000000000000000000000000000000000");
					dadosGeraisPrimeiraVia.setLinhaDigitavel("0000000000000000000000000000000000000000000");
					dadosGeraisPrimeiraVia.setEnderecoNotaFiscal("XX XXXX XXXXX , XXXX");
					dadosGeraisPrimeiraVia.setBairroNotaFiscal("XXXXXXXXXXXX");
					dadosGeraisPrimeiraVia.setCidadeNotaFiscal("XXX XXXXX");
					dadosGeraisPrimeiraVia.setEstadoNotaFiscal("XX");
					dadosGeraisPrimeiraVia.setCepNotaFiscal("11111111");
					dadosGeraisPrimeiraVia.setCpfCnpjNotaFiscal("11111111");
					dadosGeraisPrimeiraVia.setDataVencimentoBoleto(new Date());
					dadosGeraisPrimeiraVia.setMesAnoReferencia("022008");
					dadosGeraisPrimeiraVia.setDataEmissaoNotaFiscal(new Date());
					dadosGeraisPrimeiraVia.setSerieNotaFiscal("B1");
					dadosGeraisPrimeiraVia.setModeloNotaFiscal("B1");
										
					
					
					Map mapFatura = this.preencheMapFatura(dadosGeraisPrimeiraVia,fatura,true,tipoMsg);
					SetorListaDTO setorLista = new SetorListaDTO(new LinhaDTO(LinhaDTO.TIPO_ITEN_INICIO_FATURA, mapFatura),new LinhaDTO(LinhaDTO.TIPO_ITEN_FIM_FATURA));
					
					SetorDTO setorMinhaNET = criarSetorMinhaNet(dadosGeraisPrimeiraVia, fatura.getFcBoletoConsolidado());
					gerarSetorMinhaNet(setorMinhaNET, fatura);
					setorLista.addSetor(setorMinhaNET);

					List<DemonstrativoFinanceiroDTO> demonstrativoFinanceiroList = new ArrayList<DemonstrativoFinanceiroDTO>();
					DemonstrativoFinanceiroDTO demonstrativoFinanceiro1 = new DemonstrativoFinanceiroDTO();
					DemonstrativoFinanceiroDTO demonstrativoFinanceiro2 = new DemonstrativoFinanceiroDTO();
					
					
					demonstrativoFinanceiro1.setDESCRICAOITEMDEMONST_FINANC("MENSALIDADE TV PRINCIPAL");
					demonstrativoFinanceiro1.setGRUPO_DEMONST_FINANC("NET TV");
					demonstrativoFinanceiro1.setSUBGRUPO_DEMONST_FINANC("Mensalidade NET TV");
					demonstrativoFinanceiro1.setVALORITEMDEMONST_FINANC(new Double(234.9));
					demonstrativoFinanceiro1.setTAMANHO(20);
					
					demonstrativoFinanceiroList.add(demonstrativoFinanceiro1);
					
					demonstrativoFinanceiro2.setDESCRICAOITEMDEMONST_FINANC("DESCONTO DE MENSALIDADE");
					demonstrativoFinanceiro2.setGRUPO_DEMONST_FINANC("DESCONTOS / CANCELAMENTOS");
					demonstrativoFinanceiro2.setSUBGRUPO_DEMONST_FINANC("Mensalidade NET TV");
					demonstrativoFinanceiro2.setVALORITEMDEMONST_FINANC(new Double(-19.83));
					demonstrativoFinanceiro2.setTAMANHO(20);
					
					demonstrativoFinanceiroList.add(demonstrativoFinanceiro2);
					
					
					fatura.setDemonstrativoFinanceiro(demonstrativoFinanceiroList);
					
					int contLinhas = 0;
					
					SetorDTO setorDemFinFicArr = this.criarSetorDemonstrativoFichaArrecadacao(dadosGeraisPrimeiraVia,fatura.getFcBoletoConsolidado(), dadosGeraisPrimeiraVia);
					this.gerarSetorDemonstrativoFichaArrecadacao(setorDemFinFicArr
							 , fatura.getIdBoleto()
							 , dadosGeraisPrimeiraVia
							 , dadosGeraisPrimeiraVia
							 , dadosGeraisPrimeiraVia.getValorTotalNotaFiscal()
							 , cidade.getCidContrato()
							 , fatura.getFcBoletoConsolidado()
							 , fatura
							 , contLinhas
							 );
					setorLista.addSetor(setorDemFinFicArr);
					setorLista.setValorCobrado(dadosGeraisPrimeiraVia.getValorCobrado());					
					
					SetorDTO setorNotaFiscalNet = this.criarSetorNotaFiscalNet(dadosGeraisPrimeiraVia,TipoSetorPrint.NOTA_FISCAL_NET,fatura.getFcBoletoConsolidado(), true, listComplementoCampoImportante);
					SetorDTO setorNotaFiscalTributosNet = this.criarSetorNotaFiscalNet(dadosGeraisPrimeiraVia, TipoSetorPrint.NOTA_FISCAL_TRIBUTOS_NET,fatura.getFcBoletoConsolidado(), true, listComplementoCampoImportante);
					
					Map<Long , List<DetalhamentoNotaFiscalDTO>> detalheNotaFiscalMap = new HashMap<Long,List<DetalhamentoNotaFiscalDTO>>();
					DetalhamentoNotaFiscalDTO item1 = new DetalhamentoNotaFiscalDTO();
					item1.setNmGrupamento("TV POR ASSINATURA");
					item1.setVlSomaItemAgrupamento(new Double(64.9));
					item1.setVlBrutoItem(new Double(64.90));
					item1.setDescricao("01/10/07 A 31/10/07  MENSALIDADE TV PRINCIPAL SELEÇÃO COMBO STANDARD");
					
					DetalhamentoNotaFiscalDTO item2 = new DetalhamentoNotaFiscalDTO();
					List<DetalhamentoNotaFiscalDTO> detalhesList = new ArrayList<DetalhamentoNotaFiscalDTO>();
					
					detalhesList.add(item1);
					//detalhesList.add(item2);
					detalheNotaFiscalMap.put(new Long(1),detalhesList);
					fatura.setDetalheNotaFiscal(detalheNotaFiscalMap);
					List<TributoDTO> tributosList = new ArrayList<TributoDTO>();
					TributoDTO tributo = new TributoDTO();
					
					tributo.setSiglaAtributo("ICMS");
					tributo.setBaseCalculo(new Double(100));
					BigDecimal aliquota = new BigDecimal(1);
					tributo.setVlAliquota(aliquota);
					tributo.setVlAtributo(new Double(10));
										
					tributosList.add(tributo);
					
					Map<Long , List<TributoDTO>> tributosMap = new HashMap<Long,List<TributoDTO>>();


					tributosMap.put(new Long(1),tributosList);
					
					fatura.setTributos(tributosMap);
					contLinhas = this.gerarSetorNotaFiscalNet(setorNotaFiscalNet, setorNotaFiscalTributosNet, new Long(1), dadosGeraisPrimeiraVia,fatura, 1);
					setorLista.addSetor(setorNotaFiscalNet);
					setorLista.addSetor(setorNotaFiscalTributosNet);
					
					if(listComplementoCampoImportante != null){
						
						 for (SetorDTO setorDTO : listComplementoCampoImportante) {
								
							 setorLista.addSetor(setorDTO);
						 }
					 }
					
					List<SetorDTO> listMensagemStreaming = obterMensagemStreaming(fatura.getIdBoleto());

					if (listMensagemStreaming != null) {

						for (SetorDTO setorDTO : listMensagemStreaming) {

							setorLista.addSetor(setorDTO);
						}
					}
					
					FaturaDTO faturaDTO = this.gerarFatura(cidade.getCidContrato(), setorLista);
									
					for (int k = 0; k < faturaDTO.getQuantidadeLinhas().intValue(); k++) {
						this.gravarArquivoPrintHouse(arquivoPrintHouseDTO
								.getCurrentArquivo().getGerarArquivo(), faturaDTO
								.get(new Integer(k)));
					}
					
					//this.finalizarFatura(faturaDTO, "1", fatura, dataVencimento,
					//		arquivoPrintHouseDTO);
					idBoleto++;
			}
			
			this.gravarArquivoPrintHouse(arquivoPrintHouseDTO.getCurrentArquivo()
							.getGerarArquivo(), llk.criarLinhaItenFimArquivo(arquivoPrintHouseDTO));
					
			
			SnConfiguracaoArquivoBean configuracaoArquivo = this.obterConfiguracaoArquivo(dataVencimento
					, "EMISPRT"
					, cidade.getCidContrato()
					, false);
			
			
			SnControleArquivoBean arquivo = new SnControleArquivoBean();
			
			
			arquivo.setNomeArquivo(nomeArquivo + extensaoArquivo);
							
			
			arquivo.setDhGeracao(new Date());
			arquivo.setDhProcessamento(new Date());
			arquivo.setQtdTotalRegistros(new Integer(arquivoPrintHouseDTO.getQuantidadeFaturaPorArquivoPrint()));			
			arquivo.setCidContrato(cidade);
			arquivo.setUsuarioExec(getUserSession().getUserId());
			arquivo.setUsuarioProcessamento(getUserSession().getUserId());
			//arquivo.setIdRegistroEmissao(arquivoPrintHouseDTO.getRegistroEmissao().getIdRegistroEmissao());
			
			arquivo.setConfiguracaoArquivo(configuracaoArquivo);			 
			arquivo.setDataFim(dataVencimento);
			arquivo.setDataInicio(dataVencimento);
			
			SnStatusArquivoBean statusArquivo = this.getStatusArquivo(SnStatusArquivoBean.Sigla.EMISSAO_FINALIZADO); 
			arquivo.setStatusArquivo(statusArquivo); 
			
			insert(arquivo, getUserSession().getCurrentDbService());
			
			ArquivoRemessaTransporteDTO arquivoTransporte = new ArquivoRemessaTransporteDTO();
			
			arquivoTransporte.setCidContrato(cidade.getCidContrato());
			arquivoTransporte.setIdControleArquivo(arquivo.getIdControleArquivo());
						
			arquivoTransporteList.add(arquivoTransporte);
			
			File outputFile = arquivoPrintHouseDTO.getCurrentArquivo()
							.getGerarArquivo().finalizar();
			
			
			
			
		 }
		
		return arquivoTransporteList;

	}
	
	
	private void inserirMovimentoCobrancaBatch(
			ArquivoPrintHouseDTO arquivoPrintHouseDTO
			, List<DadosFatura> lstDadosFatura
			) {
		
		logger.info(new BasicAttachmentMessage("Inicio da inclusao da sn_movimento_cobranca em lote.", AttachmentMessageLevel.INFO));

		// Efetua flush dos registros atualizados antes de entrar no loop, pois e necessario 
		// o idControleArquivo e este deve ja estar no banco, nao na memoria do hibernate
		getCurrentDAO().flush();					
		
				
		// Cria o batch sql update para o update na sn_boleto 
		SnMovimentoCobrancaSQLUpdateBatchBuilder snMovimentoCobBatchBuilder = new SnMovimentoCobrancaSQLUpdateBatchBuilder();
		BatchSqlUpdate snMovCobrancaBatchSqlUpdate = super.getCurrentDAO().createBatchSqlUpdate(snMovimentoCobBatchBuilder);
		
			
		// Itera toda a colecao de boletos inserindo em lote na pp_vlr_parceiro_fatura
		for (DadosFatura dadosFatura : lstDadosFatura) {
			
			if (Sigla.EMISSAO_PRINT_BOLETO_AVULSO.getChaveSigla().equals(dadosFatura.getSgTipoLote())){
				continue;
			}
			
			Map<Long, Object[]> mapCobrancaNet = dadosFatura.getMapCobrancaNet();
			Map<Long, Object[]> mapCobrancaParceiro = dadosFatura.getMapCobrancaParceiro();
			
		
			
			if (mapCobrancaParceiro != null) {
				
				// Itera a lista de cobrancas parceiro para inserir na tabela
				Iterator it = mapCobrancaParceiro.keySet().iterator();
				while (it.hasNext()) {
					
					Long idCobrancaParceiro = (Long)it.next();
					
					Object[] campos = mapCobrancaParceiro.get(idCobrancaParceiro);
					Date dtVenctoCobranca = (Date)campos[0];
					Double vlrTotalCobranca = (Double)campos[1];
					Integer idParceiro = campos[2] != null ? (Integer)campos[2] : ParceiroEnum.EMBRATEL.getIdParceiro();
					
					SnBoletoBean bol = new SnBoletoBean();
					bol.setIdBoleto(dadosFatura.getIdBoleto());
					bol = (SnBoletoBean) super.findByPrimaryKey(bol);
					
					Object[] params = new Object[] {
							idCobrancaParceiro
							,idParceiro
							,dtVenctoCobranca
							,new Date()
							,vlrTotalCobranca
							,getUserSession().getUserId()
							,"N"
							,2
							,4
							,bol.getSnTipoCobrancaBean().getIdTipoCobranca()};
					
					snMovCobrancaBatchSqlUpdate.update(params);
									
				}
				
			}
						
		}

		// Manda para o banco o resto das atualizacoes
		snMovCobrancaBatchSqlUpdate.flush();		
		
		logger.info(new BasicAttachmentMessage("Termino da inclusao da sn_movimento_cobranca em lote.", AttachmentMessageLevel.INFO));

	}
	
	private void inserirInterfaceParceiroBatch(
			ArquivoPrintHouseDTO arquivoPrintHouseDTO
			, List<DadosFatura> lstDadosFatura
			) {
		
		logger.info(new BasicAttachmentMessage("Inicio da inclusao da sn_interface_parceiro em lote.", AttachmentMessageLevel.INFO));

		// Efetua flush dos registros atualizados antes de entrar no loop, pois e necessario 
		// o idControleArquivo e este deve ja estar no banco, nao na memoria do hibernate
		getCurrentDAO().flush();					
		
				
		// Cria o batch sql update para o update na sn_boleto
		SnInterfaceParceiroSQLUpdateBatchBuilder  snIntParceiroBatchBuilder = new SnInterfaceParceiroSQLUpdateBatchBuilder();
		BatchSqlUpdate snIntParceiroBatchSqlUpdate = super.getCurrentDAO().createBatchSqlUpdate(snIntParceiroBatchBuilder);
		
			
		// Itera toda a colecao de boletos inserindo em lote na pp_vlr_parceiro_fatura
		for (DadosFatura dadosFatura : lstDadosFatura) {
			
			if (Sigla.EMISSAO_PRINT_BOLETO_AVULSO.getChaveSigla().equals(dadosFatura.getSgTipoLote())){
				continue;
			}
			
			Map<Long, Object[]> mapCobrancaNet = dadosFatura.getMapCobrancaNet();
			Map<Long, Object[]> mapCobrancaParceiro = dadosFatura.getMapCobrancaParceiro();
			
		
			
			if (mapCobrancaParceiro != null) {
				
				// Itera a lista de cobrancas parceiro para inserir na tabela
				Iterator it = mapCobrancaParceiro.keySet().iterator();
				while (it.hasNext()) {
					
					Long idCobrancaParceiro = (Long)it.next();
					
					Object[] campos = mapCobrancaParceiro.get(idCobrancaParceiro);
					Date dtVenctoCobranca = (Date)campos[0];
					Double vlrTotalCobranca = (Double)campos[1];
					Integer idParceiro = campos[2] != null ? (Integer)campos[2] : ParceiroEnum.EMBRATEL.getIdParceiro();
					
					Object[] params = new Object[] {
							idCobrancaParceiro
							,idParceiro													
							,dadosFatura.getNumContrato()
							,dadosFatura.getCidContrato()
							,1
							,getUserSession().getUserId()
							,new Date()
							,idCobrancaParceiro
					};
					
					snIntParceiroBatchSqlUpdate.update(params);
					
				}
				
			}
						
		}

		// Manda para o banco o resto das atualizacoes
		snIntParceiroBatchSqlUpdate.flush();		
		
		logger.info(new BasicAttachmentMessage("Termino da inclusao da sn_interface_parceiro em lote.", AttachmentMessageLevel.INFO));

	}
	
		
		private SetorDTO criarSetorMinhaNet(ClienteNotaFiscalPrintDTO clienteNotaFiscalPrintDTO, String boletoConsolidado) {
	
		Map mapClienteNotaFiscal = new HashMap();
		mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.NUMERO_NOTA_FISCAL,notNullObject(clienteNotaFiscalPrintDTO.getNumeroNotaFiscal()));
		mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.CFOP_NOTA_FISCAL,notNullObject(clienteNotaFiscalPrintDTO.getCfopNotaFiscal()));
		if (clienteNotaFiscalPrintDTO.getInscricaoEstadualNotaFiscal() == null) {
			mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.INSCRICAO_ESTADUAL_NOTA_FISCAL,PrintHouseConstants.NotaFiscal.IE_ISENTO);
		} else {
			mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.INSCRICAO_ESTADUAL_NOTA_FISCAL,notNullObject(clienteNotaFiscalPrintDTO.getInscricaoEstadualNotaFiscal()));
		}
			mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.VALOR_TOTAL_NOTA_FISCAL,notNullObject(clienteNotaFiscalPrintDTO.getValorTotalNotaFiscal()));
		mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.HASH_CODE,notNullObject(clienteNotaFiscalPrintDTO.getHashCode()));
		mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.ENDERECO_NOTA_FISCAL,notNullObject(clienteNotaFiscalPrintDTO.getEnderecoNotaFiscal()));
		mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.BAIRRO_NOTA_FISCAL,notNullObject(clienteNotaFiscalPrintDTO.getBairroNotaFiscal()));
		mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.CIDADE_NOTA_FISCAL,notNullObject(clienteNotaFiscalPrintDTO.getCidadeNotaFiscal()));
		mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.ESTADO_NOTA_FISCAL,notNullObject(clienteNotaFiscalPrintDTO.getEstadoNotaFiscal()));
		mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.CEP_NOTA_FISCAL,StringUtils.prepad(notNullString(clienteNotaFiscalPrintDTO.getCepNotaFiscal()),8,'0'));
		mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.CPF_CNPJ_NOTA_FISCAL,notNullObject(clienteNotaFiscalPrintDTO.getCpfCnpjNotaFiscal()));
		mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.NOME_CLIENTE_NOTA_FISCAL,notNullObject(clienteNotaFiscalPrintDTO.getNomeClienteNotaFiscal()));
		mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.CODIGO_CLIENTE_NET,notNullString(clienteNotaFiscalPrintDTO.getCodOperadora())+"/"+StringUtils.prepad(notNullString(clienteNotaFiscalPrintDTO.getCodigoClienteNET()),9,'0'));
		//notNullString(clienteNotaFiscalPrintDTO.getCodigoClienteNET()));
		mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.DATA_VENCIMENTO_BOLETO,notNullObject(clienteNotaFiscalPrintDTO.getDataVencimentoBoleto()));
		mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.MES_ANO_REFERENCIA,notNullObject(clienteNotaFiscalPrintDTO.getMesAnoReferencia()));
		mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.DATA_EMISSAO_NOTA_FISCAL,notNullObject(clienteNotaFiscalPrintDTO.getDataEmissaoNotaFiscal()));

		Map mapInicioNotaFiscal = new HashMap();
		mapInicioNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.SERIE_NOTA_FISCAL,notNullObject(clienteNotaFiscalPrintDTO.getSerieNotaFiscal()));
		mapInicioNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.MODELO_NOTA_FISCAL,clienteNotaFiscalPrintDTO.getModeloNotaFiscal());

		Collection<LinhaDTO> cabecalhoQuebraPagina = new ArrayList<LinhaDTO>();
		cabecalhoQuebraPagina.add(new LinhaDTO(LinhaDTO.TIPO_ITEN_INICIO_PAGINA));
		cabecalhoQuebraPagina.add(new LinhaDTO(LinhaDTO.TIPO_ITEN_MENSAGEM_INICIO_FATURA,mapInicioNotaFiscal));
		
		if( boletoConsolidado.equalsIgnoreCase("C") ||boletoConsolidado.equalsIgnoreCase("N")|| 
				boletoConsolidado.equalsIgnoreCase("B") || boletoConsolidado.equalsIgnoreCase("M") || 
					boletoConsolidado.equalsIgnoreCase("R") || ( boletoConsolidado.equalsIgnoreCase("D") &&  clienteNotaFiscalPrintDTO.isOperadoraNet())){
			
			cabecalhoQuebraPagina.add(new LinhaDTO(LinhaDTO.TIPO_ITEN_DADOS_ASS_NET_COM_NF, mapClienteNotaFiscal));		
			cabecalhoQuebraPagina.add(new LinhaDTO(LinhaDTO.TIPO_ITEN_DADOS_ASS_NET_SEM_NF, mapClienteNotaFiscal));
			
		}else if(boletoConsolidado.equalsIgnoreCase("P") || boletoConsolidado.equalsIgnoreCase("A") || 
				( boletoConsolidado.equalsIgnoreCase("D") &&  clienteNotaFiscalPrintDTO.isOperadoraNet() == false)){
			//cabecalhoQuebraPagina.add(new LinhaDTO(LinhaDTO.TIPO_ITEN_MENSAGEM_INICIO_FATURA_BRANCO));
			cabecalhoQuebraPagina.add(new LinhaDTO(LinhaDTO.TIPO_ITEN_DADOS_ASS_NET_SEM_NF, mapClienteNotaFiscal));
			
		}

		SetorDTO setorMinhaNet = new SetorDTO(TipoSetorPrint.SETOR_MINHA_NET,
				null, null, cabecalhoQuebraPagina, Boolean.TRUE, Boolean.FALSE);
						
		return setorMinhaNet;
		
	}
	/**
	 * 
	 * @param fatura
	 * @return
	 */
	private void gerarSetorMinhaNet(SetorDTO setorMinhaNet, FaturaNetDTO fatura) {

		LinhaDTO linha = null;
		List<MinhaNetDTO> minhaNet = fatura.getMinhaNet();
		Map<String, String> mapDescricoes = new HashMap<String, String>();

		if (fatura != null && minhaNet != null) {
			if (minhaNet != null && !minhaNet.isEmpty()) {
				for (MinhaNetDTO item : minhaNet) {
					String descr = item.getMINHANET();
					if (mapDescricoes.get(descr) != null) {
						continue;
					}
					else {
						mapDescricoes.put(descr, "");
					}
					
					Map<String, String> mapMinhaNet = new HashMap<String, String>();
					mapMinhaNet.put("MINHANET", descr);
					mapMinhaNet.put("TIPOGRUPO", item.getTIPOGRUPO());

					linha = new LinhaDTO(LinhaDTO.TIPO_ITEN_MINHA_NET, mapMinhaNet, 0);
					
					setorMinhaNet.addLinha(linha);
				}
			}
		}
		
		if (setorMinhaNet.getQuantidadeLinhasSetor()==0) {
			//Se nao tiver nenhum item no minha net, força a inclusao do cabeçalho de inicio de pagina, pois este eh o primeiro setor
			setorMinhaNet.addLinha(new LinhaDTO(LinhaDTO.TIPO_ITEN_BLANK_LINE, null, 0));
		}
	}
	
	/**
	 * Adiciona uma linha no demonstrativo financeiro e coloca a quebra de coluna/pagina se necessario
	 * Se a linha for do grupo "Itens Eventuais" entao adiciona numa lista temporaria, para que depois eles 
	 * possam ser colocados no fim do demonstrativo financeiro, apos o "Net Fone"
	 * @param nomeGrupo
	 * @param linha
	 * @param setorDemFinFicArr
	 * @param itensEventuaisTemp
	 */
	private int[] adicionarItem(String nomeGrupo,
	                            LinhaDTO linha,
	                            SetorDTO setorDemFinFicArr,
	                            List<LinhaDTO> itensEventuaisTemp,
	                            int contLinhas,
	                            int paginaAtual,
	                            int linhasFichaArrecadacao){
		return new LayoutArquivoPrintHouseUtil().adicionarItem(nomeGrupo,
		                                                       linha,
		                                                       setorDemFinFicArr,
		                                                       itensEventuaisTemp,
		                                                       contLinhas,
		                                                       paginaAtual,
		                                                       linhasFichaArrecadacao);
	}
		
	
	/**
	 * Adiciona as linhas do itensEventuaisTemp no setorDemFinFicArr, verificando se e'
	 * necessaria a quebra de colunas.
	 * @param setorDemFinFicArr
	 * @param itensEventuaisTemp
	 */
	private int[] ajustaItensEventuais(SetorDTO setorDemFinFicArr, List<LinhaDTO> itensEventuaisTemp, int contLinhas, int paginaAtual, int linhasFichaArrecadacao) {
		if (itensEventuaisTemp != null && !itensEventuaisTemp.isEmpty()) {
			boolean quebrarColuna = false;

			//Itens eventuais so inicia na 2a coluna caso estejamos na 1a coluna da 1a pagina
			//e ja haja algum item na 1a coluna. 
			if (paginaAtual == 1 && setorDemFinFicArr.getQuantidadeLinhasSetor().intValue() != 1) { 
				quebrarColuna = true; //Itens eventuais se iniciam em outra coluna somente se estivermos na 1a coluna
			}
			
			for(Iterator<LinhaDTO> it = itensEventuaisTemp.iterator(); it.hasNext(); ) {
				LinhaDTO linha = it.next();
				int[] aux = verificaQuebraColunaPagina(linha, contLinhas, paginaAtual, setorDemFinFicArr, linhasFichaArrecadacao, quebrarColuna);
				quebrarColuna = false; //So forca quebra para o grupo itens eventuais, depois somente se necessario
				paginaAtual = aux[0];
				contLinhas = aux[1];
				setorDemFinFicArr.addLinha(linha);
				//contLinhas += linha.getQuantidadeOcupa();
			}
		}
		
		return new int[]{ paginaAtual, contLinhas }; 
	}

	/**
	 * Verifica se a linha a ser inserida vai gerar quebra de coluna ou quebra de pagina.
	 * Nestes casos, adiciona uma linha para delimitar.
	 * @param tamLinha
	 * @param contLinhas
	 * @param paginaAtual
	 * @param setorDemFinFicArr
	 * @param nomeGrupo
	 * @param itensEventuaisTemp
	 * @return
	 */
	private int[] verificaQuebraColunaPagina(LinhaDTO linha, int contLinhas, int paginaAtual, SetorDTO setorDemFinFicArr, String nomeGrupo, List<LinhaDTO> itensEventuaisTemp, int linhasFichaArrecadacao) {
		return verificaQuebraColunaPagina(linha, contLinhas, paginaAtual, setorDemFinFicArr, linhasFichaArrecadacao, false);
	}
	
	/**
	 * Verifica se a linha a ser inserida vai gerar quebra de coluna ou quebra de pagina.
	 * Nestes casos, adiciona uma linha para delimitar.
	 * @param tamLinha
	 * @param contLinhas
	 * @param paginaAtual
	 * @param setorDemFinFicArr
	 * @param nomeGrupo
	 * @param itensEventuaisTemp
	 * @param forcarQuebra se TRUE, faz quebra de pagina/coluna mesmo que ainda caibam mais itens.
	 * @return
	 */
	private int[] verificaQuebraColunaPagina(LinhaDTO linha, int contLinhas, int paginaAtual, SetorDTO setorDemFinFicArr, int linhasFichaArrecadacao, boolean forcarQuebra) {
		//Tamanho da linha atual
		int tamLinha = linha.getQuantidadeOcupa();
		
		//Numero variavel de linhas no demonstrativo financeiro. Só a ultima linha de cada pagina
		//vai ter tamanho setado de forma a forçar quebra da pagina.
		linha.setQuantidadeOcupa(0);
		
		//Cada 2 paginas eh uma folha
		int folhaAtual = ((int) (paginaAtual-1)/2) + 1;
		
		int linhasPaginaAtual = folhaAtual >= linhasFolha.length ? linhasFolha[linhasFolha.length-1] : linhasFolha[folhaAtual-1];
		int linhasColunaAtual = linhasPaginaAtual/2;
		
		//Verifica se ha algum tipo de quebra
		if (forcarQuebra || (tamLinha + contLinhas > linhasColunaAtual)) {
			if (paginaAtual % 2 == 0) { //Pagina numero par, entao deve quebrar para proxima folha
				//quebra coluna e página

				//A linha da quebra de pagina do demonstrativo é setada com o nro maximo de linhas
				//-1, pq o gerarFatura atribui tamanha 1 pras linhas q estao com 0,
				//para forçar uma quebra de paginas pelo componente de paginacao, ja que a quantidade
				//de itens no demonstrativo financeiro é variavel
				LinhaDTO linhaTmp = setorDemFinFicArr.get(setorDemFinFicArr.getQuantidadeLinhasSetor()-1);//Linha anterior tem quebra
				linhaTmp.setQuantidadeOcupa(linhasFichaArrecadacao);
			}
			else {
				//só há quebra de coluna
				LinhaDTO tempLinha = new LinhaDTO(LinhaDTO.TIPO_QUEBRA_COLUNA_DEMONSTRATIVO, new HashMap<String, String>(), 0);
				setorDemFinFicArr.addLinha(tempLinha);
			}
			paginaAtual++; 
			contLinhas = 0; //se ha quebra, zera o contador de linhas para a coluna
		}
		contLinhas += tamLinha;
		return new int[]{ paginaAtual, contLinhas };
	}
	
	private SetorDTO gerarSetorOcorrencias(FaturaNetDTO fatura, ClienteNotaFiscalPrintDTO clienteNotaFiscalPrintDTO) {
		
		/**
		* Alteração responsável para contemplar a funcionalidade das 5 últimas ocorrências na Fatura de 2º via.
		* 
		* @author Sergio Ricardo Silva - implementação
		* 
		*/
		SetorDTO setorOcorrencias = null;
		if(fatura.getUltimasOcorrencias() != null){
			if (!fatura.getUltimasOcorrencias().trim().equals("")) {
								
				Map mapUltimasOcorrencias = new HashMap<String, String>();
				mapUltimasOcorrencias
						.put(
								PrintHouseConstants.LayoutArquivoPrintHouse.ULTIMAS_OCORRENCIAS,
								fatura.getUltimasOcorrencias());
				setorOcorrencias = new SetorDTO(TipoSetorPrint.OCORENCIAS, null, null, null, false, false);
				setorOcorrencias.addLinha(new LinhaDTO(
						LinhaDTO.TIPO_ITEN_ULTIMAS_OCORRENCIAS,
						mapUltimasOcorrencias, 0));
			}
		}
		
		return setorOcorrencias;
	}
	
	/**
	 * Retorna o tamanho em milimetros de cada item da NF
	 * @param codigo
	 * @return
	 */
	private double getTamanhoLinhaNF(int codigo) {
		double result = 0;
		switch (codigo){
			case 6:
				result = 4;
				break;
			case 14:
			case 15:
			case 16:
			case 19:
				result = 2;
				break;
			case 17:
				result = 2.5;
				break;
			case 18:
				result = 3;
				break;
			case 20:
			case 21:
				result = 11;
				break;
		}
		
		return result;
	}
	
private SetorDTO criarSetorMinhaNetSegundaVia(ClienteSegundaViaPrintDTO clienteSegundaViaPrintDTO) {
			Map map = new HashMap<String,String>();
			map.put(PrintHouseConstants.LayoutArquivoPrintHouse.CODIGO_CLIENTE_NET,
					notNullString(clienteSegundaViaPrintDTO.getCodigoClienteNETFormatado()));
			map.put(PrintHouseConstants.LayoutArquivoPrintHouse.CPF_CNPJ_COBRANCA,
					notNullObject(clienteSegundaViaPrintDTO.getCpfCNPJCobranca()));
			map
					.put(
							PrintHouseConstants.LayoutArquivoPrintHouse.DATA_PROCESSAMENTO_BOLETO,
							notNullObject(clienteSegundaViaPrintDTO
									.getDataProcessamentoBoleto()));
			map
					.put(
							PrintHouseConstants.LayoutArquivoPrintHouse.DATA_VENCIMENTO_BOLETO,
							notNullObject(clienteSegundaViaPrintDTO
									.getDataVencimentoBoleto()));
			map
					.put(
							PrintHouseConstants.LayoutArquivoPrintHouse.DATA_VENCIMENTO_BOLETO_ORIGEM,
							notNullObject(clienteSegundaViaPrintDTO
									.getDataVencimentoBoletoOrigem()));
			map
					.put(
							PrintHouseConstants.LayoutArquivoPrintHouse.IDENTIFICADOR_BOLETO,
							notNullObject(clienteSegundaViaPrintDTO
									.getIdentificadorBoleto()));
			if (clienteSegundaViaPrintDTO.getInscricaoEstadualCobranca() == null) {
				map
						.put(
								PrintHouseConstants.LayoutArquivoPrintHouse.INSCRICAO_ESTADUAL_COBRANCA,
								PrintHouseConstants.NotaFiscal.IE_ISENTO);
			} else {
				map
						.put(
								PrintHouseConstants.LayoutArquivoPrintHouse.INSCRICAO_ESTADUAL_COBRANCA,
								notNullObject(clienteSegundaViaPrintDTO
										.getInscricaoEstadualCobranca()));
			}
			List<LinhaDTO> cabecalhoQuebraPagina = new ArrayList<LinhaDTO>();
			cabecalhoQuebraPagina.add(0, new LinhaDTO(LinhaDTO.TIPO_ITEN_INICIO_PAGINA));
			cabecalhoQuebraPagina.add(1, new LinhaDTO(LinhaDTO.TIPO_ITEN_DADOS_ASS_SEGUNDA_VIA, map));
			Collections.unmodifiableList(cabecalhoQuebraPagina);

			SetorDTO setorMinhaNet = new SetorDTO(TipoSetorPrint.SETOR_MINHA_NET,
					null, null,
					cabecalhoQuebraPagina, Boolean.FALSE, Boolean.FALSE);
			
			return setorMinhaNet;
		
	}
	
	/**
	 * Verifica se a fatura é inserida ou auto-envelopada.
	 * @param fatCombo Nro de paginas de fatura net
	 * @param nfNet Nro de paginas da NF net
	 * @param nfEbt Nro de paginas da NF embratel
	 * @param detEbt Nro de paginas da fatura Embratel
	 * @param nfParc Nro de paginas da NF de parceiro
	 * @param nfNetExiste Indica se existe NF NET
	 * @param nfTijolinho Indica se a NF NET cabe no tijolinho
	 * @return
	 */
	private boolean calculaEnvelopamento(int fatCombo, int nfNet, int nfEbt, int detEbt, int nfParc, boolean nfNetExiste, boolean nfTijolinho) {
		boolean autoEnvelopado = false;

		//FATNET ou FATRCOMBO = 1
		if (fatCombo == 1) {
			//NFNET(T)
			if ((!nfNetExiste || (nfNetExiste && nfTijolinho)) && nfEbt==0 && detEbt==0 && nfParc==0 ) {
				autoEnvelopado = true; //simples
			}
			//NFNET(P)
			else if (nfNet==1 && nfEbt==0 && detEbt==0 && nfParc==0) {
				autoEnvelopado = true; //duplo
			}
			//2 DETEBT + NFNET(T)
			else if (detEbt <=2 && (!nfNetExiste || (nfNetExiste && nfTijolinho)) && nfEbt==0 && nfParc==0) {
				autoEnvelopado = true; //duplo
			}
			//1 DETEBT
			else if (detEbt == 1) {
				//1 NFEBT + NFNET(T)
				if (nfEbt == 1 && (!nfNetExiste || (nfNetExiste && nfTijolinho)) && nfParc==0) {
					autoEnvelopado = true; //duplo
				}
				//NFNET(P)
				else if (nfNetExiste && nfNet==1 && nfEbt==0 && nfParc==0) {
					autoEnvelopado = true; //duplo
				}
				//NFNET(P) + 1 NFEBT + até 2 NFPARC
				/*TODO so é valido quando as 2nfparc estao na mesma pagina. Por enquanto elas saem separadas
				else if (nfNetExiste && nfNet==1 && nfEbt==1 && nfParc<=2) {
					autoEnvelopado = true; //triplo
				}
				*/
				//TODO apagar quando descomentar a linha acima
				else if (nfNetExiste && nfNet==1 && nfEbt==1 && nfParc<=1) {
					autoEnvelopado = true; //triplo
				}
				
			}
			//2 DETEBT + 1 NFEBT + ate 2 NFPARC + NFNET(T)
			/*TODO so é valido quando as 2nfparc estao na mesma pagina. Por enquanto elas saem separadas
			else if (detEbt==2 && nfEbt==1 && nfParc <=2 && (!nfNetExiste || (nfNetExiste && nfTijolinho))) {
				autoEnvelopado = true; //triplo
			}
			*/
			//TODO apagar quando descomentar a linha acima
			else if (detEbt==2 && nfEbt==1 && nfParc <=1 && (!nfNetExiste || (nfNetExiste && nfTijolinho))) {
				autoEnvelopado = true; //triplo
			}
			
			
			//ate 2 DETEBT + NFNET(P) + 1 NFEBT
			else if (detEbt<=2 && nfNetExiste && nfNet==1 && nfEbt==1  && nfParc==0) {
				autoEnvelopado = true; //triplo
			}
			//3 DETEBT + 1 NFEBT + NFNET(T)
			else if (detEbt==3 && nfEbt==1 && (!nfNetExiste || (nfNetExiste && nfTijolinho)) && nfParc==0) {
				autoEnvelopado = true; //triplo
			}
			//4 DETEBT + NFNET(T)
			else if (detEbt==4 && (!nfNetExiste || (nfNetExiste && nfTijolinho)) && nfParc==0 && nfEbt==0) {
				autoEnvelopado = true; //triplo
			}
		}
		//FATNET ou FATRCOMBO = 2
		else if (fatCombo==2) {
			//NFNET(P)
			if (nfNetExiste && nfNet==1 && nfEbt==0 && nfParc==0) {
				autoEnvelopado = true; //duplo
			}
			//1 DETEBT
			else if (detEbt==1) {
				//NFNET(T)
				 if ((!nfNetExiste || (nfNetExiste && nfTijolinho)) && nfEbt==0 && nfParc==0) {
					 autoEnvelopado = true; //duplo 
				 }
				 //NFNET(P) + 1 NFEBT
				 else if (nfNetExiste && nfNet==1 && nfEbt==1 && nfParc==0) {
					 autoEnvelopado = true; //triplo 
				 }
				 //1 NFEBT + ate 2 NFPARC + NFNET(T)
				 /*TODO Este caso so e valido se as 2 NFParc estao na mesma pagina, como por enquanto todas saem em pags separadas
				  * entao neste caso nao sera auto envelopado. Quando as 2 sairem na mesma pagina, isso pode ser descomentado
				 else if (nfEbt==1 && nfParc <=2 && (!nfNetExiste || (nfNetExiste && nfTijolinho))) {
						autoEnvelopado = true; //triplo
				 }
				 */
				 //TODO apagar quando descomentar a linha acima
				 else if (nfEbt==1 && nfParc <=1 && (!nfNetExiste || (nfNetExiste && nfTijolinho))) {
						autoEnvelopado = true; //triplo
				 }
				 
			}
			//2 DETEBT + 1 NFEBT + NFNET(T)
			else if (detEbt==2 && nfEbt == 1 && (!nfNetExiste || (nfNetExiste && nfTijolinho)) && nfParc==0) {
				autoEnvelopado = true; //triplo
			}
			//3 DETEBT + NFNET(T)
			else if (detEbt==3 && (!nfNetExiste || (nfNetExiste && nfTijolinho)) && nfParc==0 && nfEbt==0) {
				autoEnvelopado = true; //triplo
			}
		}
		//FATNET ou FATRCOMBO = 3
		else if (fatCombo==3) {
			//1 DETEBT
			if (detEbt==1) {
				//NFNET(P)
				if (nfNetExiste && nfNet==1 && nfEbt==0 && nfParc==0) {
					autoEnvelopado = true; //triplo
				}
				//1 NFEBT + NFNET(T)
				else if (nfEbt == 1 && (!nfNetExiste || (nfNetExiste && nfTijolinho)) && nfParc==0) {
					autoEnvelopado = true; //triplo
				}
			}
			//ate 2 DETEBT + NFNET(T) 
			else if (detEbt<=2 && (!nfNetExiste || (nfNetExiste && nfTijolinho)) && nfParc==0 &&nfEbt==0) {
				autoEnvelopado = true; //triplo
			}
		}
		//FATNET ou FATRCOMBO = 4
		else if (fatCombo==4) {
			//1 DETEBT + NFNET(T)
			if (detEbt==1 && (!nfNetExiste || (nfNetExiste && nfTijolinho)) && nfParc==0 && nfEbt==0) {
				autoEnvelopado = true; //triplo
			}
		}
		//2 ate 3 FATNET + NFNET(T)
		else if ( (fatCombo==2 || fatCombo==3) && (!nfNetExiste || (nfNetExiste && nfTijolinho)) && nfParc==0 && nfEbt==0 && detEbt==0) {
			autoEnvelopado = true; //duplo
		}
		//4 ate 5 FATNET + NFNET(T)
		else if ( (fatCombo==4 || fatCombo==5) && (!nfNetExiste || (nfNetExiste && nfTijolinho)) && nfParc==0 && nfEbt==0 && detEbt==0) {
			autoEnvelopado = true; //triplo
		}
		//3 ate 4 FATNET + NFNET(P)
		else if ( (fatCombo==3 || fatCombo==4) && nfNetExiste && nfNet==1 && nfEbt==0 && nfParc==0) {
			autoEnvelopado = true; //triplo
		}
		
		return autoEnvelopado;
	}

	/**
	 * Coloca na fatura o cabecalho de paginacao do setor
	 * @param fatura
	 * @param setorDTO
	 * @param possuiNFNET
	 * @param paginaAtual
	 * @param paginaAtualNF
	 * @param paginaAtualEBT
	 * @param totalPaginas
	 * @param totalPaginasNFNet
	 * @param totalPaginasNFEBT
	 * @param i
	 */
	private void adicionaCabecalhoQuebra(FaturaDTO fatura, SetorDTO setorDTO, boolean possuiNFNET, int paginaAtual, int paginaAtualNF, int paginaAtualEBT, int totalPaginas, int[] totalPaginasNFNet, int[] totalPaginasNFEBT, int i, boolean nfCabeNoTijolinho) {
		Iterator iterCabecalhoQuebraPagina = setorDTO.getCabecalhoQuebraPagina().iterator();
		while (iterCabecalhoQuebraPagina.hasNext()) {
			Boolean adicionarLinha = Boolean.TRUE;
			LinhaDTO linhaCabecalhoQuebraPagina = (LinhaDTO) iterCabecalhoQuebraPagina.next();

			//Pode ter 'n' paginas e o Cabecalho e differente pq do paginação
			LinhaDTO linhaCabecalhoQuebraPaginaTemp = new LinhaDTO(linhaCabecalhoQuebraPagina.getTipo(), new HashMap(linhaCabecalhoQuebraPagina.getValor()), linhaCabecalhoQuebraPagina.getQuantidadeOcupa());

			if (linhaCabecalhoQuebraPaginaTemp.getTipo() == LinhaDTO.TIPO_ITEN_INICIO_PAGINA) {
				linhaCabecalhoQuebraPaginaTemp.getValor().put(PrintHouseConstants.LayoutArquivoPrintHouse.CONTROLE_PAGINACAO, StringUtils.prepad(String.valueOf(paginaAtual), 2, '0')  + "/" + StringUtils.prepad(String.valueOf(totalPaginas), 2, '0'));
				//Se a NF cabe no tijolinho, nao tem paginacao
				if (   (setorDTO.getTipoSetor().intValue() == TipoSetorPrint.NOTA_FISCAL_NET.getValue() 
						|| setorDTO.getTipoSetor().intValue()==TipoSetorPrint.NOTA_FISCAL_TRIBUTOS_NET.getValue())  
						&& nfCabeNoTijolinho) {
					adicionarLinha = Boolean.FALSE;		
				}
				
			} else if (linhaCabecalhoQuebraPaginaTemp.getTipo() == LinhaDTO.TIPO_ITEN_DADOS_ASS_NET_COM_NF) {							
			 
				linhaCabecalhoQuebraPaginaTemp.getValor().put(PrintHouseConstants.LayoutArquivoPrintHouse.CONTROLE_PAGINACAO,  StringUtils.prepad(String.valueOf(paginaAtualNF), 2, '0') + "/" + StringUtils.prepad(String.valueOf(totalPaginasNFNet[i]), 2, '0'));
				if( !possuiNFNET ){
					adicionarLinha = Boolean.FALSE;						 
				}
			} else if (linhaCabecalhoQuebraPaginaTemp.getTipo() == LinhaDTO.TIPO_ITEN_DADOS_ASS_NET_SEM_NF && (setorDTO.getTipoSetor().intValue() == TipoSetorPrint.NOTA_FISCAL_NET.getValue() || setorDTO.getTipoSetor().intValue() == TipoSetorPrint.SETOR_MINHA_NET.getValue())) {
				
				if( possuiNFNET ){
					if (setorDTO.getTipoSetor().intValue() == TipoSetorPrint.SETOR_MINHA_NET.getValue()) {
						adicionarLinha = Boolean.FALSE;
					}
				}


			} else if (linhaCabecalhoQuebraPaginaTemp.getTipo() == LinhaDTO.TIPO_ITEN_EBT_CABECALHO) {
				linhaCabecalhoQuebraPaginaTemp.getValor().put(PrintHouseConstants.LayoutArquivoPrintHouse.CONTROLE_PAGINACAO,  StringUtils.prepad(String.valueOf(paginaAtualEBT), 2, '0') + "/" + StringUtils.prepad(String.valueOf(totalPaginasNFEBT[i]), 2, '0'));
			} else if (linhaCabecalhoQuebraPaginaTemp.getTipo() == LinhaDTO.TIPO_ITEN_MENSAGEM_INICIO_FATURA) {
				//Se nao possui NF é 62 ao inves de linha 6
				if (!possuiNFNET) {
					linhaCabecalhoQuebraPaginaTemp = new LinhaDTO(LinhaDTO.TIPO_ITEN_MENSAGEM_INICIO_FATURA_BRANCO);
				}
				
				//Na primeira pagina trocamos o codigo "6" pelo codigo "10"
				if (paginaAtual==1 && setorDTO.getTipoSetor().intValue() == TipoSetorPrint.SETOR_MINHA_NET.getValue()) {
					linhaCabecalhoQuebraPaginaTemp = new LinhaDTO(LinhaDTO.TIPO_ITEN_INICIO_NF_NET);
				}
				//Nas quebras de pagina demonstrativo financeiro so sai codigo 11
				else if (paginaAtual!=1 && setorDTO.getTipoSetor().intValue() == TipoSetorPrint.DEMONSTRATIVO_FINANCEIRO_FICHA_ARRECADACAO.getValue()) {
					adicionarLinha = false;
				}
			
			}
		
			if(adicionarLinha){
				fatura.addLinha(linhaCabecalhoQuebraPaginaTemp);
			}
		}
	}
	
	/** 
	 * @version $Revision: 1.3.4.7 $ - Criacao
	 * @number 
	 * @see br.com.netservicos.sms.fatcobranca.service.ArquivoPrintHouseService#gerarFaturaPrimeiraVia(Integer)
	 * @since 1.0 
	 * @ejb.transaction  type="Required"
	 * @ejb.interface-method view-type = "both"
	 * 
	 */
	public FaturaArquivoDTO gerarFaturaPrimeiraVia(Long idBoleto) {
		
		FaturaArquivoDTO faturaArquivoDTO = new FaturaArquivoDTO();	
		SnBoletoBean boleto = new SnBoletoBean();
		boleto.setIdBoleto(idBoleto);
		boleto = (SnBoletoBean) super.find(boleto);
		String cidContrato = boleto.getContrato().getCompositeKey().getCidContrato();

		SnCidadeOperadoraBean snCidadeOperadoraBean = new SnCidadeOperadoraBean();
		snCidadeOperadoraBean.setCidContrato(cidContrato);
		snCidadeOperadoraBean = (SnCidadeOperadoraBean) find(snCidadeOperadoraBean);

		faturaArquivoDTO.setDadosOperadora(operadoraNet(snCidadeOperadoraBean));
		String idCriterio = (boleto.getCriterio() != null)? boleto.getCriterio().getIdCriterio().toString() : null;
		Boolean controleSegregacao = this.getControleSegregacao(snCidadeOperadoraBean.getCidContrato());
		LoteBoletoFaturaDTO param = new LoteBoletoFaturaDTO();
		param.setIdBoleto(idBoleto);
		
		LoteBoletoFaturaDTO loteDTO = super.getDadosBeanbyQuery("selecionarLotePeloBoleto", param, false, LoteBoletoFaturaDTO.class);
		Collection mensagens = null;
		
		if (loteDTO != null) {
			String[] lotesList = new String[]{String.valueOf(loteDTO.getIdLote())};
			mensagens = obterMensagens(boleto.getDtVencimento(), snCidadeOperadoraBean, idCriterio,SnSetorizacaoBean.Prefixo.PRIMEIRA_VIA, controleSegregacao,idBoleto,lotesList);
		}else{
			mensagens = obterMensagens(boleto.getDtVencimento(), snCidadeOperadoraBean, idCriterio,SnSetorizacaoBean.Prefixo.PRIMEIRA_VIA, controleSegregacao, idBoleto);			
		}
		
		faturaArquivoDTO.setListaMensagem(mensagens);		
		faturaArquivoDTO.setListaBanco(obterBancoConveniado(snCidadeOperadoraBean, boleto.getSnTipoCobrancaBean().getIdTipoCobranca(), boleto.getDtVencimento()));
		faturaArquivoDTO.setFaturaDTO(gerarSegundaViaDemonstrativoFinanceiroFichaArrecadacaoInstalacao(boleto.getIdBoleto(), (loteDTO != null) ? loteDTO.getIdLote() : null, "PDF", TPCOPIA));
		
		
		ArquivoPrintHouseDAO dao = getAphDao();
		FaturaDTO faturaDto = faturaArquivoDTO.getFaturaDTO();
		faturaDto.setUltimasOcorrencias(notNullString(dao.buscarUltimasOcorrencias(boleto.getContrato().getCompositeKey().getNumContrato(), boleto.getContrato().getCompositeKey().getCidContrato())));
		
		faturaDto.setIdBoleto(idBoleto);
		
		
		
		SnRelCobrancaBoletoBean snRelCobrancaBoletoBean = new SnRelCobrancaBoletoBean();
		snRelCobrancaBoletoBean.setIdBoleto(idBoleto);
		List<Integer> lstSnCobrancaBean= (List<Integer>) super.search("listaCobrancaNotaFiscalByIdBoleto", snRelCobrancaBoletoBean);
		
		if(lstSnCobrancaBean.isEmpty()) {
			lstSnCobrancaBean= (List<Integer>) super.search("listaCobrancaNotaFiscalParceiroByIdBoleto", snRelCobrancaBoletoBean);
		}
		
		
		if(!lstSnCobrancaBean.isEmpty()){
		
		Integer idCobrancaNotaFiscal=  lstSnCobrancaBean.get(0);
		
		
		
		
		
		
		
		/*
		 * buscar nota fiscal
		 */
		
		
		BoletoSummarioDTO boletoSummarioDTO = null;
		DynamicBean filtro = new DynamicBean();
		filtro.set("idCobrancaNotaFiscal", idCobrancaNotaFiscal);
		boletoSummarioDTO = getDadosBeanbyQuery(
				"selecionarBoletoOriginal", filtro, false, BoletoSummarioDTO.class);
		
		if (boletoSummarioDTO == null) {
			boletoSummarioDTO = getDadosBeanbyQuery(
					"selecionarBoletoOriginalParceiro", filtro, false, BoletoSummarioDTO.class);			
			if (boletoSummarioDTO == null) {
				throw new EmissaoBusinessResourceException(
						EmissaoResources.ERRO_PROCESSO, new Object[] {
								"selecionarBoletoOriginal",
								"ID Nota Fiscal: " + idCobrancaNotaFiscal });
			}

		}
		
		
		faturaArquivoDTO.setDadosOperadora(operadoraNet(snCidadeOperadoraBean));
		
		idCriterio = (boleto.getCriterio() != null)? boleto.getCriterio().getIdCriterio().toString() : null;
		controleSegregacao = this.getControleSegregacao(snCidadeOperadoraBean.getCidContrato());
		
		
		
		if (loteDTO != null) {
			String[] lotesList = new String[] { String.valueOf(loteDTO
					.getIdLote()) };
		}
		
		
		
		
		DynamicBean filtroNotaFiscal = new DynamicBean();
		filtroNotaFiscal.set("idCobrancaNotaFiscal", idCobrancaNotaFiscal);
		boletoSummarioDTO = getDadosBeanbyQuery(
				"selecionarBoletoOriginal", filtroNotaFiscal, false, BoletoSummarioDTO.class);
		
		if (boletoSummarioDTO == null) {
			boletoSummarioDTO = getDadosBeanbyQuery(
					"selecionarBoletoOriginalParceiro", filtroNotaFiscal, false, BoletoSummarioDTO.class);			
			if (boletoSummarioDTO == null) {
				throw new EmissaoBusinessResourceException(
						EmissaoResources.ERRO_PROCESSO, new Object[] {
								"selecionarBoletoOriginal",
								"ID Nota Fiscal: " + idCobrancaNotaFiscal});
			}
		}
		
		try {
			faturaDto=gerarCopiaDeNotaFiscal(Long.parseLong(idCobrancaNotaFiscal.toString()), boletoSummarioDTO.getIdBoleto(), faturaDto, FATURA_PRIMEIRA_VIA, idBoleto);
			
		}catch(Exception e){
			throw new EmissaoBusinessResourceException(
					"error.reemissaonf.parceiro.naodisponivel", new Object[] {
						 "| ID Nota Fiscal: "  });		
		}
		
		faturaArquivoDTO.setFaturaDTO(faturaDto);
		}
		return faturaArquivoDTO;
	}

		
	/**
	 * @version $Revision: 1.3.4.7 $ - Criacao
	 * @number
	 * @see br.com.netservicos.sms.fatcobranca.service.ArquivoPrintHouseService#gerarFaturaPrimeiraVia(Integer)
	 * @since 1.0
	 * @ejb.transaction type="Required"
	 * @ejb.interface-method view-type = "both"
	 * 
	 */
	public boolean faturaTemBoleto(Long idBoleto) {
		SnRelCobrancaBoletoBean snRelCobrancaBoletoBean = new SnRelCobrancaBoletoBean();
		snRelCobrancaBoletoBean.setIdBoleto(idBoleto);
		List<Integer> lstSnCobrancaBean = (List<Integer>) super.search(
				"listaCobrancaNotaFiscalByIdBoleto", snRelCobrancaBoletoBean);
		return !lstSnCobrancaBean.isEmpty();

	}
	
	private String buscarNomeDoBanco(Long idBoleto) {
		SnBoletoBean boletoBean = new SnBoletoBean();
		boletoBean.setIdBoleto(idBoleto);
		List<String> lstBanco = (List<String>) super.search(
				"buscaNomeBancoByIdBoleto", boletoBean);
		if (lstBanco == null || lstBanco.isEmpty())
			return "";
		else
			return lstBanco.get(0);
	}

	/**
	 * Monta o setor com a listagem de filiados
	 * @param setorFiliados
	 * @param fatura
	 */
	private void gerarSetorFiliados(SetorDTO setorFiliados, FaturaNetDTO fatura, int paginaAtual, String cidContrato, 
			SetorDTO setorDemonstrativoFinanceiro, int contLinhas, String idTituloFiliados) {
		List<FiliadosDTO> listFiliados = null;
		
		if (fatura != null) {
			listFiliados = fatura.getFiliados();
		}
		
		if ( listFiliados != null ) {
				
				FiliadosDTO itemAtual=null, itemAnterior=null;
				LinhaDTO linha;
				double totalFiliados = 0;
				double subtotalFiliados = 0;
				int[] aux;
				
				//Busca total de linhas do demonstrativo financeiro
				SnParametroBean snParametroBean; 
				int linhasFichaArrecadacao = QT_LN_PAG_FAT_PRINT;
				linhasFichaArrecadacao *= 100; // multiplica por 100 para eliminar o decimal

				//Busca o nome do grupo do filiados
				snParametroBean = this.obterParametro(cidContrato,PrintHouseConstants.Parametro.TITULO_LST_FILIADO);
				String nomeGrupoFiliados = snParametroBean.getVlrParametroStr();
				// concatenação criada para identificar o título da listagem de filiados na fatura online
				if(idTituloFiliados != null && idTituloFiliados.equals(ID_TITULO_FILIADOS))
					nomeGrupoFiliados = ID_TITULO_FILIADOS + nomeGrupoFiliados;	
				
				//Linha de marcador de inicio de filiados que a grafica pediu
				linha = llk.criarLinhaInicioFiliados();
				setorFiliados.addLinha(linha);
				
				//Grupo de filiados
				linha = llk.criarLinhaGrupoFiliados(nomeGrupoFiliados, 700);
				aux = addLinhaFiliados(setorFiliados, linha, contLinhas, paginaAtual, linhasFichaArrecadacao, false);
				paginaAtual = aux[0];
				contLinhas = aux[1];
				//remover setorFiliados.addLinha(linha);

				for(FiliadosDTO item : listFiliados ) {

					itemAtual = new FiliadosDTO();
					itemAtual.setNOME(item.getNOME());
					itemAtual.setVALOR(item.getVALOR());
					itemAtual.setPRODUTO(item.getPRODUTO());
					itemAtual.setTAMANHO(item.getTAMANHO());
					itemAtual.setENDERECO(item.getENDERECO());
					
					//Subtotal de Filiado
					//if (itemAnterior !=null && !itemAtual.getNOME().equals(itemAnterior.getNOME())) {
					if (itemAnterior !=null && !itemAtual.getENDERECO().equals(itemAnterior.getENDERECO())) {
						itemAnterior.setVALOR(subtotalFiliados);
						linha = llk.criarLinhaFiliadosSubtotal(itemAnterior, 400); 
						
						aux = addLinhaFiliados(setorFiliados, linha, contLinhas, paginaAtual, linhasFichaArrecadacao, false);
						paginaAtual = aux[0];
						contLinhas = aux[1];
					}
					
					//Se primeiro item do Filiados, ou grupo diferente entao...
					if (itemAnterior==null || !itemAtual.getENDERECO().equals(itemAnterior.getENDERECO())) {
						//Header de grupo
						int tamanhoMilimetros = 400;
						if (item.getNOME() != null && item.getNOME().length()>TAMANHO_MAX_GRUPO_DF) {
							tamanhoMilimetros = 800;
						}
						linha = llk.criarLinhaFiliadosTitulo(itemAtual, tamanhoMilimetros); 
						
						aux = addLinhaFiliados(setorFiliados, linha, contLinhas, paginaAtual, linhasFichaArrecadacao, false);
						paginaAtual = aux[0];
						contLinhas = aux[1];
						
						//Zera subtotal do grupo
						subtotalFiliados = 0; 
					}
					
					//item de Filiados
					int tamanhoMilimetros = 400;
					if (item.getTAMANHO() != null && item.getTAMANHO()>TAMANHO_MAX_LINHA_DF) {
						tamanhoMilimetros = 600;
					}
					linha = llk.criarLinhaFiliados(itemAtual, tamanhoMilimetros); 
					
					aux = addLinhaFiliados(setorFiliados, linha, contLinhas, paginaAtual, linhasFichaArrecadacao, false);
					paginaAtual = aux[0];
					contLinhas = aux[1];
					
					subtotalFiliados += itemAtual.getVALOR();
					totalFiliados += itemAtual.getVALOR();
					
					itemAnterior = itemAtual;
			}
			
			//Subtotal de Filiado
			if (!listFiliados.isEmpty()) {
				itemAtual.setVALOR(subtotalFiliados);
				linha = llk.criarLinhaFiliadosSubtotal(itemAtual, 400);
				aux = addLinhaFiliados(setorFiliados, linha, contLinhas, paginaAtual, linhasFichaArrecadacao, false);
				paginaAtual = aux[0];
				contLinhas = aux[1];
			}
			
			//Total de filiado
			if (!listFiliados.isEmpty()) {
				itemAtual.setVALOR(totalFiliados);
				linha = llk.criarLinhaFiliadosTotal(itemAtual, 400, nomeGrupoFiliados); 
				aux = addLinhaFiliados(setorFiliados, linha, contLinhas, paginaAtual, linhasFichaArrecadacao, false);
				paginaAtual = aux[0];
				contLinhas = aux[1];
			}
			
			//Muda a linha 42 e 43 para o filiados
			LinhaDTO linha42 = setorDemonstrativoFinanceiro.get(setorDemonstrativoFinanceiro.size()-2);
			LinhaDTO linha43 = setorDemonstrativoFinanceiro.get(setorDemonstrativoFinanceiro.size()-1);
			if (linha42.getTipo().intValue()!=LinhaDTO.TIPO_ITEN_TOTAL_DEMONSTRATIVO_FINACEIRO.intValue()) throw new RuntimeException("A penultima linha do demonstrativo financeiro nao é a 42");
			if (linha43.getTipo().intValue()!=LinhaDTO.TIPO_ITEN_FICHA_ARRECADACAO.intValue()) throw new RuntimeException("A ultima linha do demonstrativo financeiro nao é a 43");
			setorDemonstrativoFinanceiro.remove(setorDemonstrativoFinanceiro.size()-1); //remove o 43 do demonstrativo
			setorDemonstrativoFinanceiro.remove(setorDemonstrativoFinanceiro.size()-1); //remove o 42 do demonstrativo
			setorFiliados.addLinha(linha42); //adiciona o 42 no filiados
			setorFiliados.addLinha(linha43); //adiciona o 43 no filiados
		}
		
	}
	
	/**
	 * Imprime um item de linha de filiados e realiza a quebra de coluna se necessario
	 * @param setorFiliados
	 * @param contLinhas
	 */
	private int[] addLinhaFiliados(SetorDTO setorFiliados, LinhaDTO linha, int contLinhas, int paginaAtual, int linhasFichaArrecadacao, boolean forcarQuebra) {
		int[] aux = verificaQuebraColunaPagina(linha, contLinhas, paginaAtual, setorFiliados, linhasFichaArrecadacao, forcarQuebra);
		setorFiliados.addLinha(linha);
		contLinhas = aux[1];
		contLinhas += linha.getQuantidadeOcupa();
		int [] result = new int[]{ aux[0], contLinhas };
		
		return result;
	}
	
	/**
	 * Define o cabecalho do setor de filiados
	 * @param clientePrintDTO
	 * @param clienteNotaFiscalPrintDTO
	 * @return
	 */
	private SetorDTO criarSetorFiliados(ClienteNotaFiscalPrintDTO clienteNotaFiscalPrintDTO, FaturaNetDTO fatura, int paginaAtualDemonstrativo) {
		SetorDTO setorFiliados = null;
		if (fatura.getFiliados()!=null && !fatura.getFiliados().isEmpty()) {
			ClientePrintDTO clientePrintDTO = clienteNotaFiscalPrintDTO;
	
			Map mapClientePrintDTO = new HashMap<String,String>();
			mapClientePrintDTO.put(
					PrintHouseConstants.LayoutArquivoPrintHouse.BAIRRO_NOTA_FISCAL,
					notNullObject(clientePrintDTO.getBairroNotaFiscal()));
			mapClientePrintDTO.put(
					PrintHouseConstants.LayoutArquivoPrintHouse.CEP_NOTA_FISCAL,
					StringUtils.prepad(notNullString(clientePrintDTO.getCepNotaFiscal()),8,'0'));
			mapClientePrintDTO.put(
					PrintHouseConstants.LayoutArquivoPrintHouse.CIDADE_NOTA_FISCAL, 
					notNullObject(clientePrintDTO.getCidadeNotaFiscal()));
			mapClientePrintDTO.put(PrintHouseConstants.LayoutArquivoPrintHouse.CODIGO_CLIENTE_NET,notNullString(clientePrintDTO.getCodOperadora() + "/" + StringUtils.prepad(clientePrintDTO.getCodigoClienteNET(),9,'0')));
			mapClientePrintDTO.put(
					PrintHouseConstants.LayoutArquivoPrintHouse.CPF_CNPJ_NOTA_FISCAL,
					notNullObject(clientePrintDTO.getCpfCnpjNotaFiscal()));
			mapClientePrintDTO.put(
					PrintHouseConstants.LayoutArquivoPrintHouse.DATA_EMISSAO_NOTA_FISCAL,
					notNullObject(clientePrintDTO.getDataEmissaoNotaFiscal()));
			mapClientePrintDTO.put(
					PrintHouseConstants.LayoutArquivoPrintHouse.DATA_VENCIMENTO_BOLETO,
					notNullObject(clientePrintDTO.getDataVencimentoBoleto()));
			mapClientePrintDTO.put(
					PrintHouseConstants.LayoutArquivoPrintHouse.ENDERECO_NOTA_FISCAL,
					notNullObject(clientePrintDTO.getEnderecoNotaFiscal()));
			mapClientePrintDTO.put(
					PrintHouseConstants.LayoutArquivoPrintHouse.ESTADO_NOTA_FISCAL,
					notNullObject(clientePrintDTO.getEstadoNotaFiscal()));
			mapClientePrintDTO.put(
					PrintHouseConstants.LayoutArquivoPrintHouse.MES_ANO_REFERENCIA, 
					notNullObject(clientePrintDTO.getMesAnoReferencia()));
			mapClientePrintDTO.put(
					PrintHouseConstants.LayoutArquivoPrintHouse.NOME_CLIENTE_NOTA_FISCAL,
					notNullObject(clientePrintDTO.getNomeClienteNotaFiscal()));
			
			Collection<LinhaDTO> cabecalhoQuebraPagina = new ArrayList<LinhaDTO>();
			
			cabecalhoQuebraPagina.add(new LinhaDTO(LinhaDTO.TIPO_ITEN_INICIO_PAGINA
					, new HashMap<String, String>()));
			
			
			Map mapClienteNotaFiscal = new HashMap();
			mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.NUMERO_NOTA_FISCAL,notNullObject(clienteNotaFiscalPrintDTO.getNumeroNotaFiscal()));
			mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.CFOP_NOTA_FISCAL,notNullObject(clienteNotaFiscalPrintDTO.getCfopNotaFiscal()));
			if (clienteNotaFiscalPrintDTO.getInscricaoEstadualNotaFiscal() == null) {
				mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.INSCRICAO_ESTADUAL_NOTA_FISCAL,PrintHouseConstants.NotaFiscal.IE_ISENTO);
			} else {
				mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.INSCRICAO_ESTADUAL_NOTA_FISCAL,notNullObject(clienteNotaFiscalPrintDTO.getInscricaoEstadualNotaFiscal()));
			}
			mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.VALOR_TOTAL_NOTA_FISCAL,notNullObject(clienteNotaFiscalPrintDTO.getValorTotalNotaFiscal()));
			mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.HASH_CODE,notNullObject(clienteNotaFiscalPrintDTO.getHashCode()));
			mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.ENDERECO_NOTA_FISCAL,notNullObject(clienteNotaFiscalPrintDTO.getEnderecoNotaFiscal()));
			mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.BAIRRO_NOTA_FISCAL,notNullObject(clienteNotaFiscalPrintDTO.getBairroNotaFiscal()));
			mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.CIDADE_NOTA_FISCAL,notNullObject(clienteNotaFiscalPrintDTO.getCidadeNotaFiscal()));
			mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.ESTADO_NOTA_FISCAL,notNullObject(clienteNotaFiscalPrintDTO.getEstadoNotaFiscal()));
			mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.CEP_NOTA_FISCAL,StringUtils.prepad(notNullString(clienteNotaFiscalPrintDTO.getCepNotaFiscal()),8,'0'));
			mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.CPF_CNPJ_NOTA_FISCAL,notNullObject(clienteNotaFiscalPrintDTO.getCpfCnpjNotaFiscal()));
			mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.NOME_CLIENTE_NOTA_FISCAL,notNullObject(clienteNotaFiscalPrintDTO.getNomeClienteNotaFiscal()));
			mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.CODIGO_CLIENTE_NET,notNullString(clienteNotaFiscalPrintDTO.getCodOperadora())+"/"+StringUtils.prepad(notNullString(clienteNotaFiscalPrintDTO.getCodigoClienteNET()),9,'0'));
			//notNullString(clienteNotaFiscalPrintDTO.getCodigoClienteNET()));
			mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.DATA_VENCIMENTO_BOLETO,notNullObject(clienteNotaFiscalPrintDTO.getDataVencimentoBoleto()));
			mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.MES_ANO_REFERENCIA,notNullObject(clienteNotaFiscalPrintDTO.getMesAnoReferencia()));
			mapClienteNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.DATA_EMISSAO_NOTA_FISCAL,notNullObject(clienteNotaFiscalPrintDTO.getDataEmissaoNotaFiscal()));
	
			Map mapInicioNotaFiscal = new HashMap();
			mapInicioNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.SERIE_NOTA_FISCAL,notNullObject(clienteNotaFiscalPrintDTO.getSerieNotaFiscal()));
			mapInicioNotaFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.MODELO_NOTA_FISCAL,clienteNotaFiscalPrintDTO.getModeloNotaFiscal());
	
			//cabecalhoQuebraPagina.add(new LinhaDTO(LinhaDTO.TIPO_ITEN_MENSAGEM_INICIO_FATURA,mapInicioNotaFiscal));
			cabecalhoQuebraPagina.add(new LinhaDTO(LinhaDTO.TIPO_ITEN_INICIO_NF_NET));
			cabecalhoQuebraPagina.add(new LinhaDTO(LinhaDTO.TIPO_ITEN_DADOS_ASS_NET_SEM_NF, mapClienteNotaFiscal));
			
			//Se o demonstrativo esta na primeira folha (1 a 2 paginas) entao o filiados comeca em outra pagina.
			//Senao o filiados continua na mesma pagina do demonstrativo
			Boolean novaPagina = Boolean.TRUE;
			if (paginaAtualDemonstrativo > 2) novaPagina = Boolean.FALSE;
			
			setorFiliados = new SetorDTO(TipoSetorPrint.FILIADOS,
					null, null, cabecalhoQuebraPagina, novaPagina, Boolean.TRUE);
		}			
		
		return setorFiliados;
	}

	/**
	 * Este metodo  recebera uma lista com todos setores da fatura e 
	 * calculara o tamanho, em milimetros, apenas dos setores de NF 
	 * de parceiros. Inicialmente, todas NF estao marcadas com uma 
	 * FLAG para iniciarem em uma nova pagina. Sabendo quantos milimetros 
	 * cabem em uma pagina, o metodo verificara se cabem mais NF em uma 
	 * mesma pagina e entao mudará a FLAG de forma que a NF nao inicie 
	 * em nova pagina e permaneça agrupada na NF anterior.
	 * @param setorLista
	 */
	private void calcularAgrupamentoNFParceiro(SetorListaDTO setorLista, String cidContrato) {
		int tamAnterior = 0;
		int index = 0;
		
		for (int i = 0; i < setorLista.getTotalSetor().intValue(); i++) {
			SetorDTO setorDTO = setorLista.get(new Integer(i));
			
			if (setorDTO.getTipoSetor().intValue()==5 || setorDTO.getTipoSetor().intValue()==6) {
				index++;
				
				if ((tamAnterior + setorDTO.getQuantidadeLinhasOcupadas() + 1) <= TAM_PAGINA_NF_EBT_PARCEIROS ) { //+1 devido ao tamanho adicional da linha 60 caso haja agrupamento
					tamAnterior += setorDTO.getQuantidadeLinhasOcupadas();
					
					if (index==1) 
						continue; //a primeira NF comeca sempre em nova pagina...          

					setorDTO.setIsSetorComecaSempreNovaPagina(false);
					//Se nao há quebra, copia a linha 60 do cabeçalho para que esta continue a aparecer.
					LinhaDTO linha60 = (LinhaDTO) setorDTO.getCabecalhoQuebraPagina().toArray()[1];
					if (linha60.getTipo() != LinhaDTO.TIPO_DADOS_PARCEIRO_NF) throw new RuntimeException("A segunda linha do cabeçalho de NF Parceiro não é de Tipo 60.");

					setorDTO.addLinhaInicio(linha60);
				}
				else { 
					tamAnterior = setorDTO.getQuantidadeLinhasOcupadas();
				}
			}
		}
	}
	
	/**
	 * Dado um setor com linhas definidas em milimetros, calcula quantas paginas serao ocupadas, e quantas linhas 
	 * adicionais tem
	 * @param setorDTO
	 * @param tamanhoMaximoPagina tamanho maximo de mm na pagina
	 * @return
	 */
	private int[] contaPaginasMilimetros(SetorDTO setorDTO, int tamanhoMaximoPagina, int restoLinha) {
			//Calculo do total de paginas
			int auxTotalPaginas = 0, auxLinhasOcupadas = restoLinha;
			for (int j=0; j<setorDTO.size(); j++) {
				LinhaDTO auxLinha = setorDTO.get(j);
				if (auxLinha.getQuantidadeOcupa() + auxLinhasOcupadas > tamanhoMaximoPagina) {
					//Linha nao cabe na pagina atual
					auxTotalPaginas++;
					auxLinhasOcupadas=auxLinha.getQuantidadeOcupa();
				}
				else {
					//Linha cabe na pagina Atual
					auxLinhasOcupadas+=auxLinha.getQuantidadeOcupa();
				}
			}
			
			return new int[]{ auxTotalPaginas, auxLinhasOcupadas };		
	}
	
	
	/**
	 * Gera uma fatura somente com dados do demonstrativo financeiro e da ficha
	 * de arrecadacao, conforme layout de 2º via. <br>
	 * Eh retornado uma estrutura de dados <code>FaturaDTO</code> que
	 * representa o layout de uma fatura dentro do arquivo que sera enviado para
	 * a print. <br>
	 * O metodo ja retorna as linhas na ordem que devem ser colocadas dentro do
	 * arquivo. Alem disso faz a contabilizacao de paginas que a fatura ira
	 * gerar. <br>
	 * 
	 * @author Everton Ken Tomita, Jorge Rasuck - refactor Robin Michael Gray
	 * @version $Revision: 1.3.4.7 $ - Revisão da implementação do método
	 * @number RF015_RF021_FAT149
	 * 
	 * @param idBoleto
	 *            identificador do boleto que deve ser gerado no arquivo.
	 * @param idLote
	 *            identificador do lote que o boleto pertence para ser gerado.
	 *            Parametro nao requerido.
	 * @return FaturaDTO que contem as linhas na ordem que devem ser coloca
	 *         dentro do arquivo que sera enviado para Print.
	 * 
	 * @semantics
	 *
	 * 
	 */
	protected FaturaDTO gerarSegundaViaDemonstrativoFinanceiroFichaArrecadacaoInstalacao(
			Long idBoleto, Integer idLote, String idTipo, String tipo) {

		SetorListaDTO setorLista = null;
		SetorDTO setorDemFinFicArr = null;
		DadosGeraisSegundaViaPrintDTO dadosGeraisSegundaViaPrintDTO = null;
		FaturaDTO faturaDTO = null;
		String FormPagto = null;
		
		DynamicBean bean = new DynamicBean();
		bean.set("idBoleto", idBoleto);
//		dadosGeraisSegundaViaPrintDTO = getDadosBeanbyQuery("selecionarDadosSegundaViaBoletoOrigem", bean, false,DadosGeraisSegundaViaPrintDTO.class);
//		
//		if (dadosGeraisSegundaViaPrintDTO == null) {
//			dadosGeraisSegundaViaPrintDTO = getDadosBeanbyQuery("selecionarDadosSegundaVia", bean, false,DadosGeraisSegundaViaPrintDTO.class);
//
//			if (dadosGeraisSegundaViaPrintDTO == null) {
//				dadosGeraisSegundaViaPrintDTO = getDadosBeanbyQuery("selecionarDadosSegundaViaBoletoOrigemParceiro", bean, false,DadosGeraisSegundaViaPrintDTO.class);
//
//				if (dadosGeraisSegundaViaPrintDTO == null) {
//					dadosGeraisSegundaViaPrintDTO = getDadosBeanbyQuery("selecionarDadosSegundaViaParceiro",bean, false, DadosGeraisSegundaViaPrintDTO.class);
//				}
//			}
//		}
		
		dadosGeraisSegundaViaPrintDTO = getDadosBeanbyQuery("selecionarDadosSegundaViaBoletoOrigemEnderecoInstalacao", bean, false,DadosGeraisSegundaViaPrintDTO.class);
		
		if (dadosGeraisSegundaViaPrintDTO == null) {
			dadosGeraisSegundaViaPrintDTO = getDadosBeanbyQuery("selecionarDadosSegundaViaEnderecoInstalacao", bean, false,DadosGeraisSegundaViaPrintDTO.class);

			if (dadosGeraisSegundaViaPrintDTO == null) {
				dadosGeraisSegundaViaPrintDTO = getDadosBeanbyQuery("selecionarDadosSegundaViaBoletoOrigemParceiroEnderecoInstalacao", bean, false,DadosGeraisSegundaViaPrintDTO.class);

				if (dadosGeraisSegundaViaPrintDTO == null) {
					dadosGeraisSegundaViaPrintDTO = getDadosBeanbyQuery("selecionarDadosSegundaViaParceiroEnderecoInstalacao",bean, false, DadosGeraisSegundaViaPrintDTO.class);
				}
			}
		}

		
		if(dadosGeraisSegundaViaPrintDTO != null){
			FormPagto = obterFormaPagamento_BoletoOrigem(bean);
		}
		if(dadosGeraisSegundaViaPrintDTO != null && FormPagto == null){
			FormPagto = obterFormaPagamento_SegundaVia(bean);
		}
		if(dadosGeraisSegundaViaPrintDTO != null && FormPagto == null){
			FormPagto = obterFormaPagamento_BoletoOrigemParceiro(bean);
		}
		if(dadosGeraisSegundaViaPrintDTO != null && FormPagto == null){
			FormPagto = obterFormaPagamento_SegundaViaParceiro(bean);
		}
		
		this.popularMesAnoReferencia(dadosGeraisSegundaViaPrintDTO, idBoleto);

		if (idLote != null) {
			if(dadosGeraisSegundaViaPrintDTO == null){
				dadosGeraisSegundaViaPrintDTO = new DadosGeraisSegundaViaPrintDTO();
			}
			dadosGeraisSegundaViaPrintDTO.setCodigoLote(new Long(idLote.intValue()));
		}

		SnBoletoBean boleto = new SnBoletoBean();
		boleto.setIdBoleto(idBoleto);
		boleto = (SnBoletoBean) super.find(boleto);
		Hibernate.initialize(boleto.getContrato());
		
		if(boleto.getContrato() != null && boleto.getContrato().getNumContratoEmbratel() != null ){
			String numContratoEmbratel = boleto.getContrato().getNumContratoEmbratel().toString();
			dadosGeraisSegundaViaPrintDTO.setCodigoClienteEBT(numContratoEmbratel);
		}	
		
		Map mapFatura = new HashMap<String,String>();

		// Atributos da interface ClienteCobrancaPrintDTO serão setados
		if(dadosGeraisSegundaViaPrintDTO != null){
			mapFatura.put(  PrintHouseConstants.LayoutArquivoPrintHouse.BAIRRO_COBRANCA,
					notNullObject(dadosGeraisSegundaViaPrintDTO.getBairroCobranca()));
			mapFatura.put(	PrintHouseConstants.LayoutArquivoPrintHouse.CEP_COBRANCA,
						StringUtils.prepad(notNullString(dadosGeraisSegundaViaPrintDTO.getCepCobranca()),8,'0'));
			mapFatura.put(  PrintHouseConstants.LayoutArquivoPrintHouse.CIDADE_COBRANCA,
							notNullObject(dadosGeraisSegundaViaPrintDTO.getCidadeCobranca()));
			mapFatura.put(	PrintHouseConstants.LayoutArquivoPrintHouse.CODIGO_LOTE,
							notNullObject(dadosGeraisSegundaViaPrintDTO.getCodigoLote()));
			mapFatura.put(	PrintHouseConstants.LayoutArquivoPrintHouse.CODIGO_CLIENTE_NET,
							notNullString(dadosGeraisSegundaViaPrintDTO.getCodigoClienteNETFormatado()));
			mapFatura.put( 	PrintHouseConstants.LayoutArquivoPrintHouse.ENDERECO_COBRANCA,
							notNullObject(dadosGeraisSegundaViaPrintDTO.getEnderecoCobranca()));
			mapFatura.put( 	PrintHouseConstants.LayoutArquivoPrintHouse.ESTADO_COBRANCA,
							notNullObject(dadosGeraisSegundaViaPrintDTO.getEstadoCobranca()));
			mapFatura.put(
					PrintHouseConstants.LayoutArquivoPrintHouse.CODIGO_BANCO,
					notNullObject(dadosGeraisSegundaViaPrintDTO
							.getCodigoBanco()));
			mapFatura.put( 	PrintHouseConstants.LayoutArquivoPrintHouse.IDENTIFICADOR_BOLETO,
							notNullObject(dadosGeraisSegundaViaPrintDTO.getIdentificadorBoleto()));
			mapFatura.put( 	PrintHouseConstants.LayoutArquivoPrintHouse.IDENTIFICADOR_BOLETO_ORIGEM,
							notNullObject(dadosGeraisSegundaViaPrintDTO.getIdentificadorBoletoOrigem()));
			mapFatura.put( 	PrintHouseConstants.LayoutArquivoPrintHouse.NOME_CLIENTE_COBRANCA,
							notNullObject(dadosGeraisSegundaViaPrintDTO.getNomeClienteCobranca()));
			mapFatura.put(	PrintHouseConstants.LayoutArquivoPrintHouse.PREFIXO,
							notNullObject(dadosGeraisSegundaViaPrintDTO.getPrefixo()));
			mapFatura.put( 	PrintHouseConstants.LayoutArquivoPrintHouse.CODIGO_CLIENTE_EBT,
							notNullString(dadosGeraisSegundaViaPrintDTO.getCodigoClienteEBT()));			
			mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.FUST,"");
			mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.FUNTEL,"");
			mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.MENSAGEMEBT,"N");
			mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.LABELCLIENTE,"Cliente");
			
			mapFatura.put(
					PrintHouseConstants.LayoutArquivoPrintHouse.CODIGO_BANCO,
					notNullObject(dadosGeraisSegundaViaPrintDTO
							.getCodigoBanco()));
			
			//QRCODE PIX
			mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.PIX_HASHCODE, notNullObject(dadosGeraisSegundaViaPrintDTO.getPixHashcode()));
			
			mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.LINHA_DIGITAVEL, notNullObject(dadosGeraisSegundaViaPrintDTO.getLinhaDigitavel()));
			mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.CODIGO_DE_BARRAS, notNullObject(dadosGeraisSegundaViaPrintDTO.getCodigoDeBarras()));
			mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.VALOR_COBRADO, notNullObject(dadosGeraisSegundaViaPrintDTO.getValorCobrado()));
			
			
			
			ArquivoPrintHouseDAO dao = getAphDao();
			//NSMSP_172250_NI_003
			SnLoteBean loteCorrente = new SnLoteBean();
			Integer formaEnvioFatura = 2;
			if(dadosGeraisSegundaViaPrintDTO.getCodigoLote() != null){
				 loteCorrente = buscarLote(dadosGeraisSegundaViaPrintDTO.getCodigoLote().intValue());
				 formaEnvioFatura = dao.buscarTipoEnvioFaturaPorLote(boleto.getContrato().getCompositeKey().getCidContrato(), loteCorrente, 2);	
			}
			
			mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.FORMAENVIOFATURA, formaEnvioFatura);
			
			mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.BASE, "");
			mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.CIDCONTRATO, 000000);
			mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.TIPOCOBRANCA, 0);
			mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.EMAILORIGEM, "");
			mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.EMAILDESTINO, "");
			mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.LINKNAOOPTANTE, "ArquivoPrintHouseServiceEJBImpl.gerarSegundaViaDemonstrativoFinanceiroFichaArrecadacaoInstalacao");
			mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.MESVENCIMENTO, "");
			
	        //Quitação de Debito Anual 
	        mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.DECLARANUALDEBITONET, "");
	        mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.DECLARANUALDEBITOEBT, "");	

	        //Mensagem migração política
	        mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.MIGRACAOPADPOL, "");
	        
	        //Identificador da Mensagem de Quitacao
	        mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.IDENTIFICADORMENSAGEM, "");
	        
			setorLista = new SetorListaDTO(new LinhaDTO(
							LinhaDTO.TIPO_ITEN_INICIO_FATURA, mapFatura), new LinhaDTO(LinhaDTO.TIPO_ITEN_FIM_FATURA));
	
			String cidContrato = boleto.getContrato().getCompositeKey().getCidContrato();
	
			setorDemFinFicArr = criarSetorDemonstrativoFinaceiroFichaArrecadacaoSegundaVia(dadosGeraisSegundaViaPrintDTO);
			 
			FaturaNetDTO fatura = null;
					
						
			if( boleto.getFcBoletoConsolidado().equalsIgnoreCase("N") || 
					boleto.getFcBoletoConsolidado().equalsIgnoreCase("R") || 
				   boleto.getFcBoletoConsolidado().equalsIgnoreCase("M")) {			
				/**
				* Alteração responsável para contemplar a funcionalidade das 5 últimas ocorrências na Fatura de 2º via.
				* 
				* @author Sergio Ricardo Silva - implementação
				* 
				*/
				fatura = dao.buscarDadosSegundaViaNet(new Long(boleto.getIdBoleto()), boleto.getContrato().getCompositeKey().getCidContrato().toString(),boleto.getContrato().getCompositeKey().getNumContrato(), tipo);
			
			}else if(boleto.getFcBoletoConsolidado().equalsIgnoreCase("C") || 
					boleto.getFcBoletoConsolidado().equalsIgnoreCase("B")||
					boleto.getFcBoletoConsolidado().equalsIgnoreCase("D")){
				
				/**
				* Alteração responsável para contemplar a funcionalidade das 5 últimas ocorrências na Fatura de 2º via.
				* 
				* @author Sergio Ricardo Silva - implementação
				* 
				*/
				fatura = dao.buscarDadosSegundaViaFaturaNetEbt(new Long(boleto.getIdBoleto()), boleto.getContrato().getCompositeKey().getCidContrato().toString(),boleto.getContrato().getCompositeKey().getNumContrato(), tipo);
			}else if( boleto.getFcBoletoConsolidado().equalsIgnoreCase("P") || 
					boleto.getFcBoletoConsolidado().equalsIgnoreCase("A")) {				
				
				fatura = dao.buscarDadosSegundaViaFaturaEbt(new Long(boleto.getIdBoleto()), tipo); 
			}
	
			if (cidContrato!=null) fatura.setCidContrato(cidContrato);
			
			if(!"PDF".equals(idTipo)){
				SetorDTO setorMinhaNET = criarSetorMinhaNetSegundaVia(dadosGeraisSegundaViaPrintDTO); 
				gerarSetorMinhaNet(setorMinhaNET, fatura);
				setorLista.addSetor(setorMinhaNET);
				
								
				
			/**
			* Alteração responsável para contemplar a funcionalidade das 5 últimas ocorrências na Fatura de 2º via.
			*
			* @author Sergio Ricardo Silva - implementação
			*
			*/			
				SetorDTO setorFaturaNet = criarSetorOcorrenciaSegundaVia(dadosGeraisSegundaViaPrintDTO);
				
				if(fatura.getUltimasOcorrencias() != null){
					if (!fatura.getUltimasOcorrencias().trim().equals("")) {
						Map mapUltimasOcorrencias = new HashMap<String, String>();
						mapUltimasOcorrencias
						.put(
						PrintHouseConstants.LayoutArquivoPrintHouse.ULTIMAS_OCORRENCIAS,
						fatura.getUltimasOcorrencias());
						setorFaturaNet.addLinha(new LinhaDTO(
						LinhaDTO.TIPO_ITEN_ULTIMAS_OCORRENCIAS,
						mapUltimasOcorrencias,0));
					}
				}
				setorLista.addSetor(setorFaturaNet);
			}					
			
			
			if("PDF".equals(idTipo)){	
			   	SetorDTO setorMinhaNET = criarSetorMinhaNetSegundaVia(dadosGeraisSegundaViaPrintDTO); 
				gerarSetorMinhaNet(setorMinhaNET, fatura);
				setorLista.addSetor(setorMinhaNET);
										
				SetorDTO setoFormPagto = new SetorDTO(TipoSetorPrint.FORMPAGTO, null,
						null, null, Boolean.FALSE, Boolean.FALSE);
				
				if(FormPagto != null && !FormPagto.equals("")){
					Map<String, String> mapFormPagto = new HashMap<String, String>();
					mapFormPagto.put("FormPagto", FormPagto);
					LinhaDTO linha = new LinhaDTO (LinhaDTO.TIPO_FORMA_DE_PAGAMENTO, mapFormPagto) ;
					setoFormPagto.addLinha(linha);
			    }
								
				setorLista.addSetor(setoFormPagto);
				
			}
			
			
			//fatura.setDadosGeraisNF()
			gerarSetorDemonstrativoFichaArrecadacao(setorDemFinFicArr
			, idBoleto
			, dadosGeraisSegundaViaPrintDTO
			, dadosGeraisSegundaViaPrintDTO
			, dadosGeraisSegundaViaPrintDTO.getValorTotalNotaFiscal()
			, cidContrato
			, boleto.getFcBoletoConsolidado()
			, fatura
			, 0);

			setorLista.addSetor(setorDemFinFicArr);
			
			
			
			
			setorLista.setValorCobrado(dadosGeraisSegundaViaPrintDTO.getValorCobrado());
			
			//NSMSP_172250_NI_003_CI_001\JAVA 
			Long idBoletoClaroClube = 0L;
			if(idBoleto!=null &&  idBoleto > 0){				
				idBoletoClaroClube = idBoleto;
			}else{
				idBoletoClaroClube = dadosGeraisSegundaViaPrintDTO.getIdentificadorBoleto();
			}
			
			MensagemClaroClubeDTO mensagemClaroClubeDTO = dao.buscarMensagemClaroClube(idBoletoClaroClube);			
					
			
			//NSMSP_161258_NI_001\JAVA	
			if(mensagemClaroClubeDTO!=null && mensagemClaroClubeDTO.getIsDemandaLigada() && mensagemClaroClubeDTO.getDescMensagemFormatada()!=null ){
				List<MensagemClaroClubeDTO> mensagemClaroClube = new ArrayList<MensagemClaroClubeDTO>();
				mensagemClaroClubeDTO
				.setDescMensagemFormatada(br.com.netservicos.novosms.emissao.util.StringUtils
						.formataMsgClaroClubePDF(mensagemClaroClubeDTO
								.getDescMensagemFormatada()));
				mensagemClaroClube.add(mensagemClaroClubeDTO);
				fatura.setMensagemClaroClubeDTO(mensagemClaroClube);

				SetorDTO setorClaro = new SetorDTO(TipoSetorPrint.DEMONSTRATIVO_FINANCEIRO_FICHA_ARRECADACAO, null,
						null, null, Boolean.FALSE, Boolean.FALSE);
				gerarSetorClaroClube(setorClaro, fatura);
				
				setorLista.addSetor(setorClaro);
			}
			
			
			faturaDTO = gerarFatura(cidContrato, setorLista);
			}

			return faturaDTO;

	}
	
	
	
private void gerarSetorClaroClube(SetorDTO setorClaro, FaturaNetDTO fatura) {
		
		LinhaDTO linha = null;
		List<MensagemClaroClubeDTO> mensagemClaroClube = (List<MensagemClaroClubeDTO>) fatura.getMensagemClaroClubeDTO();
		Map<String, String> mapDescricoes = new HashMap<String, String>();

		if (fatura != null && mensagemClaroClube != null) {
			if (mensagemClaroClube != null && !mensagemClaroClube.isEmpty()) {
				for (MensagemClaroClubeDTO item : mensagemClaroClube) {
					String descricao = item.getDescMensagemFormatada();
					if (mapDescricoes.get(descricao) != null) {
						continue;
					}
					else {
						mapDescricoes.put(descricao, "");
					}			
	
					String[] aCampos = descricao.split("\\|");
					if(aCampos!=null && aCampos.length >0){
						for(int i = 0;i < aCampos.length;i++){
							Map<String, String> mapMensagemClaroClube = new HashMap<String, String>();
							mapMensagemClaroClube.put("CLAROCLUBE", br.com.netservicos.novosms.emissao.util.StringUtils.completarEspaco(124, aCampos[i])); 
							mapMensagemClaroClube.put("TIPOGRUPO", "01"); 	
							linha = new LinhaDTO(LinhaDTO.TIPO_ITEN_CLARO_CLUBE, mapMensagemClaroClube, 0);
							setorClaro.addLinha(linha);
						}
					}				
				}
			}
		}
	}
	
	private SnLoteBean buscarLote(int idLoteParam) {
		SnLoteBean snLoteBean = new SnLoteBean();
		snLoteBean.setIdLote(idLoteParam);
		return snLoteBean = (SnLoteBean) super.findByPrimaryKey(snLoteBean);
	}
	
	/**
	 * Método responsável por buscar as MSG de Declaracao de Quitacao Debito EBT.
	 * 
	 **/	
	private List buscaMsgDeclaracaoDebitoEBT(Long idBoleto){	
		
		DynamicBean dynamicBean = new DynamicBean();
		List lstMsgQuitacaoDebitoEBT = new ArrayList<List>();

		try{
			dynamicBean.set("idBoleto", idBoleto);		
			lstMsgQuitacaoDebitoEBT = super.search("lstMsgQuitacaoDebitoEBT", dynamicBean, false);

		}catch(Exception e){
			logger.info(new BasicAttachmentMessage("ERRO AO BUSCAR MSG QUITACAO DEBITO ANUAL EBT" + " ID_BOLETO: " + idBoleto, AttachmentMessageLevel.INFO));
		}		
		
		return lstMsgQuitacaoDebitoEBT;			 
	}
	
	/**
	 * Cria o sub Grupo Alteração Produto, para projeto Unificação de Pró-ratas
	 * @param demonstrativoFinanceiroDTO - contem os itens do demonstrativo financeiro
	 * @param totalSubGrupoAlterProd - valor total do subgrupo criado
	 * @param setorDemFinFicArr - setor demonstrativo financeiro
	 * @param itensEventuaisTemp - itens eventuais temporarios
	 * @param contLinhas - contador de linhas
	 * @param paginaAtual - pagina atual
	 * @param linhasFichaArrecadacao - linhas ficha de arrecadacao
	 * @param tipoCabecalho - tipo de linha a ser criada.
	 * @return
	 */
	private DemonstrativoFinanceiroDTO setSubGrupoItensEventuais(DemonstrativoFinanceiroDTO demonstrativoFinanceiroDTO, double totalSubGrupoAlterProd, SetorDTO setorDemFinFicArr, List<LinhaDTO> itensEventuaisTemp, int contLinhas, int paginaAtual, int linhasFichaArrecadacao, int tipoCabecalho){
		
		DemonstrativoFinanceiroDTO subGrupoAterProd = new DemonstrativoFinanceiroDTO();
		LinhaDTO tempLinha = null;
		subGrupoAterProd.setGRUPO_DEMONST_FINANC(demonstrativoFinanceiroDTO.getGRUPO_DEMONST_FINANC());
		subGrupoAterProd.setSUBGRUPO_DEMONST_FINANC("");
		subGrupoAterProd.setSubgrupoAlterProd(demonstrativoFinanceiroDTO.getSubgrupoAlterProd());
		int tamanhoMilimetros = 400;
		if (subGrupoAterProd.getDESCRICAOITEMDEMONST_FINANC() != null && subGrupoAterProd.getDESCRICAOITEMDEMONST_FINANC().length()>TAMANHO_MAX_LINHA_DF) {
			tamanhoMilimetros = 600;
		}
		if(tipoCabecalho == 1){
			subGrupoAterProd.setDESCRICAOITEMDEMONST_FINANC(demonstrativoFinanceiroDTO.getSubgrupoAlterProd());
			tempLinha = llk.criarLinhaDemonstrativoFinanceiro(LinhaDTO.TIPO_ITEN_SUBCABECALHO_AGRUPAMENTO_DEMO_FINACEIRO.intValue()
				, subGrupoAterProd
				, tamanhoMilimetros);
		}else{
			subGrupoAterProd.setDESCRICAOITEMDEMONST_FINANC("Sub-Total " + demonstrativoFinanceiroDTO.getSubgrupoAlterProd());
			subGrupoAterProd.setVALORITEMDEMONST_FINANC(totalSubGrupoAlterProd);
			tempLinha = llk.criarLinhaDemonstrativoFinanceiro(LinhaDTO.TIPO_ITEN_SUBRODAPE_AGRUPAMENTO_DEMO_FINACEIRO.intValue()
					, subGrupoAterProd
					, tamanhoMilimetros);
		}
		int[] aux = adicionarItem(subGrupoAterProd.getGRUPO_DEMONST_FINANC(), tempLinha, setorDemFinFicArr, itensEventuaisTemp, contLinhas, paginaAtual, linhasFichaArrecadacao);
		paginaAtual = aux[0];
		contLinhas = aux[1];
		
		return subGrupoAterProd;
	}
	
	// metodo que pega o parametro na base
	private BigDecimal buscaVlrParametro(final String parametro) {
		// Inicia a demanda desligada  
		BigDecimal retorno = new BigDecimal(0);
		
		try {
			SnParametroBean snParametroBean = new SnParametroBean();
			SnParametroKey compositeKey = new SnParametroKey();
			compositeKey.setNomeParametro(parametro);
			snParametroBean.setCompositeKey(compositeKey);			
			snParametroBean = (SnParametroBean) super.search( "lstSnParametroByNomeParametro", snParametroBean, false).get(0);
			
			if(snParametroBean!=null && snParametroBean.getVlrParametro()!= null) {
				retorno = snParametroBean.getVlrParametro();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		return retorno;
	}
	
}