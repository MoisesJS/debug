package br.com.netservicos.novosms.emissao.core.dao;

import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.BAIRRO_EMISSOR_FISCAL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.CEP_EMISSOR_FISCAL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.CIDADE_EMISSOR_FISCAL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.CNPJ_EMISSOR_FISCAL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.ENDERECO_EMISSOR_FISCAL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.ESTADO_EMISSOR_FISCAL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.IE_EMISSOR_FISCAL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.IE_EMISSOR_FISCAL_F;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.IM_EMISSOR_FISCAL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.MSG_TOT_IMPOSTOS_NET;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.NOME_EMISSOR_FISCAL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.MesContants.getMesContantsByChave;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.Parametro.REMOVE_BOL_LOTE_SEG_VIA;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;

import br.com.netservicos.core.bean.sn.SnContratoBean;
import br.com.netservicos.core.bean.sn.SnLoteBean;
import br.com.netservicos.core.bean.sn.SnTipoLoteBean;
import br.com.netservicos.core.bean.sn.hibernate.ParceiroEnum;
import br.com.netservicos.framework.core.bean.DynamicBean;
import br.com.netservicos.framework.core.bean.ResultSetBeanCollection;
import br.com.netservicos.framework.core.dao.BaseDAOException;
import br.com.netservicos.framework.core.dao.BatchParameter;
import br.com.netservicos.framework.core.dao.JDBCDAO;
import br.com.netservicos.framework.core.dao.Parameter;
import br.com.netservicos.framework.util.attachments.messages.AttachmentMessageLevel;
import br.com.netservicos.framework.util.attachments.messages.BasicAttachmentMessage;
import br.com.netservicos.novosms.emissao.constants.PrintHouseConstants;
import br.com.netservicos.novosms.emissao.dto.BaixaLogHubDTO;
import br.com.netservicos.novosms.emissao.dto.CobrancaParceiroDTO;
import br.com.netservicos.novosms.emissao.dto.DadosCobrancaParceiroDTO;
import br.com.netservicos.novosms.emissao.dto.DadosGeraisPrimeiraViaPrintDTO;
import br.com.netservicos.novosms.emissao.dto.DadosGeraisPrimeiraViaPrintParceiroDTO;
import br.com.netservicos.novosms.emissao.dto.DadosItensNFDTO;
import br.com.netservicos.novosms.emissao.dto.DadosTributosDTO;
import br.com.netservicos.novosms.emissao.dto.DemonstrativoFinanceiroDTO;
import br.com.netservicos.novosms.emissao.dto.DemonstrativoFinanceiroParceiroDTO;
import br.com.netservicos.novosms.emissao.dto.DetalhamentoLigacaoParceiroDTO;
import br.com.netservicos.novosms.emissao.dto.DetalhamentoNotaFiscalDTO;
import br.com.netservicos.novosms.emissao.dto.FiliadosDTO;
import br.com.netservicos.novosms.emissao.dto.LoteBoletoFaturaDTO;
import br.com.netservicos.novosms.emissao.dto.MensagemAnatelDTO;
import br.com.netservicos.novosms.emissao.dto.MensagemClaroClubeDTO;
import br.com.netservicos.novosms.emissao.dto.MensagemDTO;
import br.com.netservicos.novosms.emissao.dto.MensagemStreamingDTO;
import br.com.netservicos.novosms.emissao.dto.MinhaNetDTO;
import br.com.netservicos.novosms.emissao.dto.TributoDTO;
import br.com.netservicos.novosms.emissao.exception.EmissaoBusinessResourceException;
import br.com.netservicos.novosms.emissao.printhouse.FaturaNetDTO;
import br.com.netservicos.novosms.emissao.printhouse.MensagensNotaFiscalDTO;
import br.com.netservicos.novosms.emissao.resources.EmissaoResources;
import br.com.netservicos.novosms.geral.core.facade.ControleArquivoService;

public class ArquivoPrintHouseDAO extends JDBCDAO {

	private static final Log log = LogFactory.getLog(ArquivoPrintHouseDAO.class);

	private static final long serialVersionUID = 1L;

	private static final String PRSMS_LST_FATURA_NET = 
			"{call PGSMS_EMISSAO_PRINT.PRSMS_LST_FATURA_NET_FAT (?,?,?,?,?,?,?,?,?,?)}";

	private static final String PRSMS_LST_FATURA_EBT = 
			"{call PGSMS_EMISSAO_PRINT.PRSMS_LST_FATURA_EBT_FAT (?,?,?,?,?,?,?,?,?,?,?)}";

	private static final String PRSMS_LST_FATURA_NET_EBT = 
			"{call PGSMS_EMISSAO_PRINT.PRSMS_LST_FATURA_NET_EBT_FAT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

	private static final String PRSMS_LST_BOLETO_AVULSO = 
			"{call PROD_JD.PGSMS_PRINT_BOLETO_AVULSO.PRSMS_CARREGA_BOLETO_AVULSO(?,?)}";
	
	private static final String PRSMS_INSERT_LOG_BAIXA_HUB = 
			"{call PROD_JD.PGSN_HUB.PRSMS_LOG_BAIXA_HUB_CC(?,?,?,?,?,?,?,?,?,?,?)}";
	
	private static final String PRSMS_EFETUA_BAIXA_CC_NETSMS = 
			"{call PROD_JD.PGSN_HUB.PRSMS_BAIXA_CARTAO_ONLINE(?,?,?,?,?,?,?,?)}";

	/*
	 * Constante para chamada da procedure PRC_MSG_TOTAL_IMPOSTOS 
	 * que retorna a mensagem com os totais dos impostos para a NF NET
	 */
	//private static final String PRC_MSG_TOTAL_IMPOSTOS = "{call PGSMS_EMISSAO_PRINT.PRC_MSG_TOTAL_IMPOSTOS(?,?)}";
	
	/*
	 * Constante para chamada da procedure PRC_MSG_TOTAL_IMPOSTOS_PARC 
	 * que retorna a mensagem com os totais dos impostos para a NF dos parceiros
	 */
	private static final String PRC_MSG_TOTAL_IMPOSTOS_PARC = "{call PGSMS_EMISSAO_PRINT.PRC_MSG_TOTAL_IMPOSTOS_PARC(?,?)}";
	
	private static final String PRC_MSG_COMBO_SVA = "{call PGSMS_EMISSAO_PRINT.PRC_MSG_COMBO_SVA(?,?,?)}";
	
	private static final String PRSMS_SEL_BOL_FATURA = "{call PGSMS_EMISSAO_PRINT.PRSMS_SEL_BOLETO_FATURA(?,?,?)}";
	private static final String PRSMS_SEL_BOLETO_AVULSOS = "{call PROD_JD.PGSMS_PRINT_BOLETO_AVULSO.PRSMS_SEL_BOLETO_AVULSO(?,?,?)}";

	private static final String PRSMS_SEL_CLI_NF_PAR_SEM_NF = "{call PGSMS_EMISSAO_PRINT.PRSMS_SEL_CLI_NF_PAR_SEM_NFFAT(?,?,?)}";

	private static final String FSN_ULT_OCORR_FAT = "{? = call PGSMS_EMISSAO_PRINT.FSN_ULT_OCORR_FAT(?,?)}";

	private static final String PRSMS_SEL_DADOS_1_VIA_SEM_NF = "{call PGSMS_EMISSAO_PRINT.PRSMS_SEL_DADOS_1_VIA_SEMNFFAT(?,?,?)}";

	//	private static final String PRSMS_LST_DEMONST_FINANCEIRO = "{call PGSMS_EMISSAO_PRINT.PRSMS_LST_DEMONST_FINANCEIRO(?,?,?)}";

	private static final String PRSMS_LST_DEMONST_FINAN_2VIA = "{call PGSMS_EMISSAO_PRINT.PRSMS_LST_DEMONST_FINAN_2VIA(?,?,?,?)}";

	private static final String PRSMS_LST_DEMONS_FINAN_PAR = "{call PGSMS_EMISSAO_PRINT.PRSMS_LST_DEMONS_FINAN_PAR(?,?,?,?)}";

	private static final Integer NUMERO_QUEBRA = 500;

	private static final String TPSMS_NT_NUMBER = "PROD_JD.TPSMS_NT_NUMBER";

	private static final String PRSMS_LST_DEMONST_FILIADO = "{call PGSMS_EMISSAO_PRINT.PRSMS_LST_DEMONST_FILIADO (?,?,?,?,?)}";

    private static final String LE_PARAMETRO = "{call PROD_JD.PSN_PARAMETRO.LE_PARAMETRO_STR(?, ?, ?, ?)}";

    private static final String PRSMS_LST_QUITACAO_DEBITO_ANUAL = "{call PGSMS_QDEB_MSG_FATURA.PR_RETORNA_ANO(?,?,?,?)}";
        
    private static final String FSN_LST_VERIFICA_ENVIO_QUITACAO_DEBITO_ANUAL = "{? = call PGSMS_QDEB_MSG_FATURA.FN_VERIFICA_ENVIO(?)}";

	private static final String FNC_VERIFICA_MGR_BOL_PAD_POLITICA = "{? = call PROD_JD.PCK_PADRONIZACAO_POLITICAS.FNC_VERIFICA_MIGR_BY_BOLETO(?)}";	

	private static final String FNC_POL_CONTRATO_PAD_POLITICA = "{? = call PROD_JD.PCK_PADRONIZACAO_POLITICAS.FNC_POLITICA_CONTRATO_EM(?,?,?)}";
	
	private static final String PR_GERA_MENSAGENS_ANATEL = "{call PGSMS_EMISSAO_PRINT.PR_GERA_MENSAGENS_ANATEL(?,?)}";
	
	private static final String PR_RETORNA_MENSAGENS_ANATEL = "{call PGSMS_EMISSAO_PRINT.PR_RETORNA_MENSAGENS_ANATEL(?,?,?)}";
		
	private static final String R_RETORNA_MSG_CLARO_CLUB = "{call PROD_JD.PGSMS_EMISSAO_PRINT.PR_RETORNA_MSG_CLARO_CLUB(?,?,?)}";
	
	private static final String PR_RETORNA_MENSAGENS_STREAMING = "{call PGSMS_EMISSAO_PRINT.PR_RETORNA_MENSAGENS_STREAMING(?,?,?)}";

	/**
     * Function busca tipode envio  
     */
	public static final String  ENVIO_LOTE_SEGVIA ="{call PROD_JD.PGSMS_EMISSAO_PRINT.ENVIO_LOTE_SEGVIA(?,?)}";
	
	
	private static final String NET_TV = "NET TV";
	private static final String CLARO_NET_TV = "CLARO NET TV";
	private static final String CLARO_TV = "CLARO TV";
	private static final String CLARO_INTERNET = "CLARO INTERNET";
	private static final String TV_POR_ASSINATURA = "TV POR ASSINATURA";
	private static final String NET_VIRTUA = "NET VIRTUA";
	private static final String CLARO_NET_VIRTUA = "CLARO NET VIRTUA";
	private static final String INTERNET = "Internet";
	private static final String NET_FONE = "NET FONE";
	private static final String CLARO_FONE = "CLARO FONE";
	private static final String NET_LAR = "NET LAR";
	private static final String OUTROS_SERVICOS = "OUTROS SERVIÇOS";
	
	
	
    private static Boolean dataInicioQuitacaoDebito = false;
    
    private static final String PRODUTO = "PRODUTO";
    private static final String GRUPO = "GRUPO";
    private static final String SUBGRUPO = "SUBGRUPO";
    private static final String SUBGRUPO_ALTER_PROD = "SUBGRUPO_ALTER_PROD";
    private static final String VALORITEMDEMONST_FINANC = "VALORITEMDEMONST_FINANC";
    private static final String TAMANHO = "TAMANHO";

    /*
     * Constante com a descrição do setor onde aparece a mensagem dos totais de impostos da NF NET
     */
	private static final String SETOR_MSG_TOTAIS_NF_NET = "PNFFE";
	
	/*
     * Constante com a descrição do setor onde aparece a mensagem dos totais de impostos da NF dos Parceiros
     */
	private static final String SETOR_MSG_TOTAIS_NF_PAR = "PNFFP";

	/*
     * Constante com o código de formatação da mensagem dos totais de impostos da NF NET
     */
	private static final String CODIGO_FORMAT_TOTAIS_NF_NET = "3";
	
	/*
     * Constante com o código de formatação da mensagem dos totais de impostos da NF dos Parceiros
     */
	private static final String CODIGO_FORMAT_TOTAIS_NF_PAR = "1";

	/*
     * Constante com o descrição do campo de imposto COFINS
     */
	private static final String COFINS = "COFINS";
	
	/*
     * Constante com o descrição do campo de imposto ICMS
     */
	private static final String ICMS = "ICMS";
	
	/*
     * Constante com o descrição do campo de imposto PIS
     */
	private static final String PIS = "PIS";
	
	/*
     * Constante com o descrição do campo de imposto ISS
     */
	private static final String ISS = "ISS";
    

    

	@SuppressWarnings("rawtypes")
    public List<LoteBoletoFaturaDTO> buscarBoeltosFaturas(Long idLote) {
		log.info("[INI] buscarDadosDoLote: " + idLote);
		BatchParameter[] ps = new BatchParameter[] { new BatchParameter(idLote, Types.NUMERIC), // p_ID_LOTE			
				new BatchParameter(Types.VARCHAR, true), // p_COD_RETORNO
				new BatchParameter(BatchParameter.ORACLE_CURSOR, true) // p_CURSOR
		};

		List list = executeBatch(PRSMS_SEL_BOL_FATURA, ps);
		List<LoteBoletoFaturaDTO> boletos = listarBoletosFaturas((ResultSetBeanCollection) list.get(2));
		log.info("[FIM] buscarDadosDoLote: " + idLote);
		return boletos;
	}

	public List<LoteBoletoFaturaDTO> buscarBoletosFaturas(SnLoteBean lote) {
		List<LoteBoletoFaturaDTO> result = new ArrayList<LoteBoletoFaturaDTO>();
		
		result.addAll(buscarBoeltosFaturas(Long.valueOf(lote.getIdLote())));
		result.addAll(buscarBoletosAvulsos(Long.valueOf(lote.getIdLote())));
		
		return result;
	}
	
	//insere log na tabela sn_log_baixa_hub_cc para baixa online de cartao de credito durante a emissao.
	public Long efetuaLogBaixaHub(BaixaLogHubDTO log){
		
		BatchParameter[] ps = new BatchParameter[] { 
				new BatchParameter(log.getNumeroTransacao(), Types.NUMERIC),   //id_transacao
				new BatchParameter(log.getIdFila(), Types.NUMERIC),            //id_fila
				new BatchParameter(log.getIdLote(), Types.NUMERIC),            //id_lote
				new BatchParameter(log.getIdBoleto(), Types.NUMERIC),          //id_boleto
				new BatchParameter(log.getValor(), Types.DOUBLE),              //vl_boleto
				new BatchParameter(log.getRespostaHub(), Types.VARCHAR),       //tx_resposta_hub
				new BatchParameter(log.getStatusBaixaNetsms(), Types.VARCHAR), //cd_status_baixa
				new BatchParameter(log.getRequestHub(), Types.VARCHAR),        //tx_request_hub
				new BatchParameter(log.getObs(), Types.VARCHAR),               //tx_obs_baixa
				new BatchParameter(log.getIdCodigoRetornoHub(), Types.NUMERIC),//id_codigo_retorno_hub
				new BatchParameter(Types.NUMERIC, true)                        //numero da transacao gerado
				
		};
		
		
		List list = executeBatch(PRSMS_INSERT_LOG_BAIXA_HUB, ps);
				
		
		return Long.valueOf(list.get(10).toString());

		
	}
	
	public int executaUpdateStCnab(long idBoleto){
		StringBuilder sb = new StringBuilder();
		sb.append(" UPDATE PROD_JD.SN_BOLETO SET ST_CNAB = 'S' ");
		sb.append(" WHERE ID_BOLETO =  ?");
		
		return super.update(sb.toString(), new Parameter[]{new Parameter(idBoleto, Types.INTEGER)});
	}
		
	
	//recupera token de CC para utilizar na baixa de cartao durante a emissao
	public HashMap<String, String> recuperaContratoCartao(Long idBoleto, String cidContrato){
		
		StringBuilder sb = new StringBuilder();
		sb.append(" select bol.num_contrato as NUM_CONTRATO, car.token as TOKEN from prod_jd.sn_boleto bol, prod_jd.sn_cartao_credito car ");
		sb.append(" where bol.id_boleto = "+idBoleto);
		sb.append(" and bol.cid_contrato = "+cidContrato);
		sb.append(" and bol.num_contrato = car.num_contrato ");
		sb.append(" and bol.cid_contrato = car.cid_contrato ");
		sb.append(" and car.dt_bloqueio is null ");
		
		System.out.println(sb.toString());
		
		
		HashMap<String, String> contratoCartao = new HashMap<String, String>();
		
		List<DynamicBean> retorno = select(sb.toString());
		String numContrato = "";
		String token = "";
		
		for (DynamicBean dados : retorno) {
			System.out.println(dados.get("NUM_CONTRATO"));
			numContrato = (String) dados.get("NUM_CONTRATO").toString();
			System.out.println(dados.get("TOKEN"));
			token = (String) dados.get("TOKEN").toString();
		}
		
		if((token == null)||(token.equals(""))){
			System.out.println("TOKEN NAO ENCONTRADO NA SN_CARTAO_CREDITO");
			
			StringBuilder sb2 = new StringBuilder();
			sb2.append(" select bol.num_contrato as NUM_CONTRATO, car.token as TOKEN from prod_jd.sn_boleto bol, prod_jd.sn_cartao_credito_parceiro car ");
			sb2.append(" where bol.id_boleto = "+idBoleto);
			sb2.append(" and bol.cid_contrato = "+cidContrato);
			sb2.append(" and bol.num_contrato = car.num_contrato ");
			sb2.append(" and bol.cid_contrato = car.cid_contrato ");
			sb2.append(" and car.dt_bloqueio is null ");
			
			List<DynamicBean> retorno2= select(sb.toString());
			
			for (DynamicBean dados : retorno2) {
				System.out.println(dados.get("NUM_CONTRATO"));
				numContrato = (String) dados.get("NUM_CONTRATO").toString();
				System.out.println(dados.get("TOKEN"));
				token = (String) dados.get("TOKEN").toString();
			}
		}
		
		contratoCartao.put("NUM_CONTRATO",numContrato);
		contratoCartao.put("TOKEN_CC",token);
		
		return contratoCartao;
	}
				
	@SuppressWarnings("rawtypes")
    public List<LoteBoletoFaturaDTO> buscarBoletosAvulsos(Long idLote) {
		BatchParameter[] ps = new BatchParameter[] { new BatchParameter(idLote, Types.NUMERIC), // p_ID_LOTE			
				new BatchParameter(Types.VARCHAR, true), // p_COD_RETORNO
				new BatchParameter(BatchParameter.ORACLE_CURSOR, true) // p_CURSOR
		};
		
		List list = executeBatch(PRSMS_SEL_BOLETO_AVULSOS, ps);
		
		List<LoteBoletoFaturaDTO> boletos = listarBoletosFaturas((ResultSetBeanCollection) list.get(2));
		log.info("[FIM] buscarDadosDoLote: " + idLote);
		
		return boletos;
	}
	
	/**
	 *
	 * Chamada para a procedure PGSMS_EMISSAO_PRINT.PRSMS_LST_FATURA_NET_EBT com
	 * os parametros:
	 * <ul>
	 * <li> p_ID_BOLETO - (input) - ID Boleto
	 * <li> p_ID_PARCEIRO - (input) </li>
	 * <li> p_SEL_CLIENTE_NF_BOL </li>
	 * <li> p_LST_DET_NF - (output) </li>
	 * <li> p_LST_TRIBUTO - (output)< /li>
	 * <li> p_SEL_FUST_FUNTEL - (output) </li>
	 * <li> p_SEL_DADOS_1_VIA_SEM_NF - (output) </li>
	 * <li> p_LST_TIT_NF_OPER_PAR - (output) </li>
	 * <li> p_LST_TIT_NF_OPER_POR_PAR - (output) </li>
	 * <li> p_SEL_CLI_NF_BLT_PAR - (output) </li>
	 * <li> p_LST_DET_NF_PAR - (output) </li>
	 * <li> p_LST_TRIBUTO_PARCEIRO </li>
	 * <li> p_LST_TRIBUTO_PARC_TOTAL </li>
	 * <li> p_LST_DEMONS_FINAN_PAR </li>
	 * <li> p_LST_COBRANCA_PAR_SEM_NF - (output) - listarDadosCobrancaParceiroSemNF </li>
	 * <li> p_COD_RETORNO </li>
	 * </ul>
	 * @throws Exception
	 */
	public List<FaturaNetDTO> buscarDadosFaturaNetEbt(List<LoteBoletoFaturaDTO> boletos, String idCriterio, String tipo, boolean isArquivo) throws Exception {
		return buscarDadosFaturaNetEbt(boletos, null, idCriterio, tipo, isArquivo, null);
	}
	

	/**
	 *
	 * Chamada para a procedure PGSMS_EMISSAO_PRINT.PRSMS_LST_FATURA_NET_EBT com
	 * os parametros:
	 * <ul>
	 * <li> p_ID_BOLETO - (input) - ID Boleto
	 * <li> p_ID_PARCEIRO - (input) </li>
	 * <li> p_SEL_CLIENTE_NF_BOL </li>
	 * <li> p_LST_DET_NF - (output) </li>
	 * <li> p_LST_TRIBUTO - (output)< /li>
	 * <li> p_SEL_FUST_FUNTEL - (output) </li>
	 * <li> p_SEL_DADOS_1_VIA_SEM_NF - (output) </li>
	 * <li> p_LST_TIT_NF_OPER_PAR - (output) </li>
	 * <li> p_LST_TIT_NF_OPER_POR_PAR - (output) </li>
	 * <li> p_SEL_CLI_NF_BLT_PAR - (output) </li>
	 * <li> p_LST_DET_NF_PAR - (output) </li>
	 * <li> p_LST_TRIBUTO_PARCEIRO </li>
	 * <li> p_LST_TRIBUTO_PARC_TOTAL </li>
	 * <li> p_LST_DEMONS_FINAN_PAR </li>
	 * <li> p_LST_COBRANCA_PAR_SEM_NF - (output) - listarDadosCobrancaParceiroSemNF </li>
	 * <li> p_COD_RETORNO </li>
	 * </ul>
	 * @throws Exception
	 */
	public List<FaturaNetDTO> buscarDadosFaturaNetEbt(List<LoteBoletoFaturaDTO> boletos, Long idNotaFiscal, String idCriterio, String tipo, boolean isArquivo, String tipoFatura) throws Exception {

		Long idLote = null;
		if(boletos != null && boletos.size() > 0 &&  boletos.get(0) != null && boletos.get(0).getIdLote() != null){
			idLote = Long.parseLong(String.valueOf(boletos.get(0).getIdLote()));
		}

		Long[] idBoletos = new Long[boletos.size()];
		int i = 0;
		for(LoteBoletoFaturaDTO boleto : boletos ){
			idBoletos[i] = boleto.getIdBoleto();
			i++;
		}

		BatchParameter[] ps = new BatchParameter[16];
		ps[0] = new BatchParameter(null, idBoletos, Types.ARRAY, false,TPSMS_NT_NUMBER);
		ps[1] = new BatchParameter(idLote, Types.NUMERIC, false);
		if (idNotaFiscal != null && idNotaFiscal > 0 && tipoFatura != null && tipoFatura.equalsIgnoreCase("NF")){
			ps[2] = new BatchParameter(idNotaFiscal, Types.NUMERIC, false);
		}else{
			ps[2] = new BatchParameter(null, Types.NUMERIC, false);
		}
		ps[3] = new BatchParameter(BatchParameter.ORACLE_CURSOR, true);
		ps[4] = new BatchParameter(null, BatchParameter.ORACLE_CURSOR, true);
		ps[5] = new BatchParameter(BatchParameter.ORACLE_CURSOR, true);
		ps[6] = new BatchParameter(BatchParameter.ORACLE_CURSOR, true);
		ps[7] = new BatchParameter(BatchParameter.ORACLE_CURSOR, true);
		ps[8] = new BatchParameter(BatchParameter.ORACLE_CURSOR, true);
		ps[9] = new BatchParameter(BatchParameter.ORACLE_CURSOR, true);
		ps[10] = new BatchParameter(BatchParameter.ORACLE_CURSOR, true);
		ps[11]= new BatchParameter(BatchParameter.ORACLE_CURSOR, true);
		ps[12]= new BatchParameter(BatchParameter.ORACLE_CURSOR, true);
		ps[13]= new BatchParameter(Types.VARCHAR, true);
		//ps[13]= new BatchParameter("p_Tipo_Emissao", tipo, Types.VARCHAR, false);
		ps[14]= new BatchParameter(BatchParameter.ORACLE_CURSOR, true);
		ps[15]= new BatchParameter(BatchParameter.ORACLE_CURSOR, true);

		FaturaNetDTO faturaCoboletada = new FaturaNetDTO();
		List<FaturaNetDTO> faturas = new ArrayList<FaturaNetDTO>();
		List list = executeBatch(PRSMS_LST_FATURA_NET_EBT, ps);

		Map<Long,List<DynamicBean>>  mapNFBoleto = listarBoletos( (ResultSetBeanCollection) list.get(3));
		Map<Long,List<DynamicBean>>  mapDetNFBoleto = listarBoletos( (ResultSetBeanCollection) list.get(4));
		Map<Long,List<DynamicBean>>  mapTribNFBoleto = listarBoletos( (ResultSetBeanCollection) list.get(5));
		Map<Long,List<DynamicBean>>  mapDemostrativoBoleto = listarBLTO( (ResultSetBeanCollection) list.get(10));
		Map<Long,List<DynamicBean>>  mapNFParceiroBoleto = listarBoletos( (ResultSetBeanCollection) list.get(6));
		Map<Long,List<DynamicBean>>  mapDetNFParceiroBoleto = listarBoletos( (ResultSetBeanCollection) list.get(7));
		Map<Long,List<DynamicBean>>  mapTribNFParceiroBoleto = listarBoletos( (ResultSetBeanCollection) list.get(8));
		Map<Long,List<DynamicBean>>  mapDemostrativoParceiroBoleto = listarBoletos( (ResultSetBeanCollection) list.get(9));
		Map<Long,List<DynamicBean>>  mapLigacoesBoleto = listarBoletos( (ResultSetBeanCollection) list.get(11));
		Map<Long,List<DynamicBean>>  mapLstMinhaNet = listarBLTO( (ResultSetBeanCollection) list.get(14));
		Map<Long,List<DynamicBean>>  maplstMensNotaFiscal = listarBLTO( (ResultSetBeanCollection) list.get(15));
		Map<Long,List<DynamicBean>>  mapFiliados = listarBLTO( (ResultSetBeanCollection) carregarFiliados(idLote).get(3) );

		Map<Long,List<String>> lstPlanoServicoEbt = null;
		if(isArquivo){
			try{
				lstPlanoServicoEbt = listarPlanoServicoEbt( (ResultSetBeanCollection) list.get(11));
			}catch(Exception e){
				log.error("Erro ao recuperar lista de plano de servico EBT", e);
			}
		}
		
		// Contem as cobrancas do parceiro que nao possuem notas fiscais, necessarias para a inclusao na pp_vlr_parceiro_fatura
		Map<Long,List<DynamicBean>>  mapDadosCobrancaParceiroSemNF = listarBoletos( (ResultSetBeanCollection) list.get(12));

		BigDecimal isolaLote = BigDecimal.ZERO;
		try {
			String cidadeOperadora = boletos.get(0).getCidContrato();				
            isolaLote = obterParametroObrigatorioNumerico(cidadeOperadora, REMOVE_BOL_LOTE_SEG_VIA);
		} catch (EmissaoBusinessResourceException e){
            log.info("Parametro de configuracao que isola boletos de segunda via com erros em um novo lote não foi encontrado. Configurando valor Default (desligado)."); 
		}  
		
		for (LoteBoletoFaturaDTO boleto : boletos){

				faturaCoboletada = new FaturaNetDTO();

				log.info("[INICIO] buscarDadosFaturaNetEmbratel: " + boleto.getIdBoleto());
				Long idBoleto = Long.valueOf(boleto.getIdBoleto());
				List<DadosGeraisPrimeiraViaPrintDTO> dadosGerais = new ArrayList<DadosGeraisPrimeiraViaPrintDTO>();

				
				  //NSMSP_172250_NI_003
				  SnLoteBean loteCorrente = new SnLoteBean();
				  if(boleto.getIdLote() != null){
				  loteCorrente.setIdLote(boleto.getIdLote());
				  SnTipoLoteBean snTipoLoteBean = new SnTipoLoteBean();
				  loteCorrente.setSnTipoLoteBean(snTipoLoteBean);
				  loteCorrente.getSnTipoLoteBean().setSgTipoLote(boleto.getSiglaLote());
				  }
				  
				  
				if(mapNFBoleto.get(Long.parseLong(String.valueOf(boleto.getIdBoleto()))) != null && tipo.equals("S")){
					dadosGerais = listarDadosSegundaVia(loteCorrente, ((List)mapNFBoleto.get(Long.parseLong(String.valueOf(boleto.getIdBoleto())))));
				}else if(mapNFBoleto.get(Long.parseLong(String.valueOf(boleto.getIdBoleto()))) != null ){
					dadosGerais = listarDadosPrimeiraVia( loteCorrente,((List)mapNFBoleto.get(Long.parseLong(String.valueOf(boleto.getIdBoleto())))),
							((List)maplstMensNotaFiscal.get(Long.parseLong(String.valueOf(boleto.getIdBoleto())))),
							    tipo, isArquivo);
				}


				if (dadosGerais.isEmpty()) {
					BatchParameter[] ps_no_nf = new BatchParameter[] { new BatchParameter(idBoleto, Types.NUMERIC), // p_ID_BOLETO
							new BatchParameter(Types.VARCHAR, true), // p_COD_RETORNO
							new BatchParameter(BatchParameter.ORACLE_CURSOR, true)}; // p_SEL_DADOS_1_VIA_SEM_NF

						 	List list_no_nf = executeBatch(PRSMS_SEL_DADOS_1_VIA_SEM_NF, ps_no_nf);

					if(tipo.equals("S")){
						dadosGerais = listarDadosSegundaVia( loteCorrente,(ResultSetBeanCollection) list_no_nf.get(2));
					}
					else{
						dadosGerais = listarDadosPrimeiraVia(loteCorrente,(ResultSetBeanCollection) list_no_nf.get(2),(ResultSetBeanCollection) list.get(15), tipo, isArquivo);
					}
					try{
						if ((isolaLote.compareTo(BigDecimal.ONE) == 0) &&
						     CollectionUtils.isEmpty(dadosGerais) && 
 							 tipo.equals("S")){
							
							log.info(new BasicAttachmentMessage("Boleto "+idBoleto+" nao retornou dados de NF NET e EBT." , AttachmentMessageLevel.WARNING));
							continue;
						}
						if(!dadosGerais.isEmpty()){
						dadosGerais.get(0).setIsOperadoraNet("S");
						}
					}catch(Exception ex){
						log.info(new BasicAttachmentMessage("Boleto "+idBoleto+" nao retornou dados de NF ." , AttachmentMessageLevel.ERROR));
						throw ex;
					}
					faturaCoboletada.setPossuiNF(Boolean.FALSE);
					faturaCoboletada.setOperadoraNet(Boolean.TRUE);
				}else{

					faturaCoboletada.setPossuiNF(Boolean.TRUE);
					if(!dadosGerais.isEmpty()){
					faturaCoboletada.setOperadoraNet(dadosGerais.get(0).isOperadoraNet());
				}
				}

				faturaCoboletada.setIdBoleto( boleto.getIdBoleto());
				if(boleto.getIdLote() != null){
					faturaCoboletada.setIdLote(boleto.getIdLote());
				}
				if(!dadosGerais.isEmpty()){
				faturaCoboletada.setNumContrato(Long.valueOf(dadosGerais.get(0).getCodigoClienteNET()));
				}
				faturaCoboletada.setCidContrato(boleto.getCidContrato());

				faturaCoboletada.setUltimasOcorrencias(this.buscarUltimasOcorrencias(faturaCoboletada.getNumContrato(), faturaCoboletada.getCidContrato()));
				if(!dadosGerais.isEmpty()){
				faturaCoboletada.setDataVencimento(dadosGerais.get(0).getDataVencimentoBoleto());
				}
				faturaCoboletada.setFcBoletoConsolidado(boleto.getFcBoletoConsolidado());
				if(idCriterio != null && !(idCriterio.equals("")) ){
					faturaCoboletada.setIdCriterio(Integer.parseInt(idCriterio));
				}

				long idBoletoAux = Long.parseLong(String.valueOf(boleto.getIdBoleto()));

				faturaCoboletada.setDadosGeraisNF(dadosGerais);
				faturaCoboletada.setDetalheNotaFiscal(listarDetalhamentoNotaFiscal(((List)mapDetNFBoleto.get(Long.parseLong(String.valueOf(boleto.getIdBoleto()))))));
				faturaCoboletada.setTributos(listarTributo(((List)mapTribNFBoleto.get(Long.parseLong(String.valueOf(boleto.getIdBoleto()))))));
				faturaCoboletada.setDemonstrativoFinanceiro(listarDemonstrativoFinanceiroGrupoSubGrupo(((List)mapDemostrativoBoleto.get(Long.parseLong(String.valueOf(boleto.getIdBoleto()))))));
				faturaCoboletada.setMinhaNet(listarMinhaNet(
						((List)mapLstMinhaNet.get(idBoletoAux))
						));
				
				if ( ((List)mapNFParceiroBoleto.get(Long.parseLong(String.valueOf(boleto.getIdBoleto())))) != null ) {
					faturaCoboletada.setDadosNotaFiscalParceiro(this.listarTituloNotaFiscalOperadoraPorParceiro((List)mapNFParceiroBoleto.get(Long.parseLong(String.valueOf(boleto.getIdBoleto()))),faturaCoboletada));
					faturaCoboletada.setDadosClienteParceiro(this.selecionarClienteNotaFiscalBoletoParceiro((List)mapNFParceiroBoleto.get(Long.parseLong(String.valueOf(boleto.getIdBoleto()))),faturaCoboletada));
				}

				faturaCoboletada.setItensNotaFiscalParceiro(this.listarDetalhamentoNotaFiscalParceiro((List)mapDetNFParceiroBoleto.get(Long.parseLong(String.valueOf(boleto.getIdBoleto())))));

				faturaCoboletada.setTributosNotaFiscalParceiro(this.listarTributoParceiro((List)mapTribNFParceiroBoleto.get(Long.parseLong(String.valueOf(boleto.getIdBoleto())))));
				faturaCoboletada.setTotalTributosNotaFiscalParceiro(this.listarTributoParceiroTotal((List)mapTribNFParceiroBoleto.get(Long.parseLong(String.valueOf(boleto.getIdBoleto())))));

				faturaCoboletada.setDemostrativoFinanceiroParceiro(this.listarDemonstrativoFinanceiroParceiro((List)mapDemostrativoParceiroBoleto.get(Long.parseLong(String.valueOf(boleto.getIdBoleto())))));

				faturaCoboletada.setLigacoes(this.listarDetalhamentoLigacaoParceiro((List)mapLigacoesBoleto.get(Long.parseLong(String.valueOf(boleto.getIdBoleto())))));
				
				try{
					
					if(isArquivo){
						faturaCoboletada.setPlanoServicoEbt((List<String>)lstPlanoServicoEbt.get(Long.parseLong(String.valueOf(boleto.getIdBoleto()))));
					}else{
						faturaCoboletada.setListMsgPlanoServicoEbt(this.listarMsgPlanoServicoEbt(((List)mapLigacoesBoleto.get(Long.parseLong(String.valueOf(boleto.getIdBoleto()))))));
					}
					
				}catch(Exception e){
					log.error("Erro ao adicionar lista de plano de servico EBT", e);
				}

				faturaCoboletada.setDadosCobrancaParceiroSemNF(
						this.listarDadosCobrancaParceiroSemNF((List)mapDadosCobrancaParceiroSemNF.get(boleto.getIdBoleto().longValue())));

				faturaCoboletada.setFiliados(listarFiliados((List) mapFiliados.get(Long.parseLong(String.valueOf(boleto.getIdBoleto())))));

				// Mensagens dos totais dos impostos
				if (isArquivo) {
//					faturaCoboletada.setMsgTotaisImpostos(buscarTotalImpostosNet(idBoleto, dadosGerais.get(0).getIdCobrancaNotaFiscal(), "").getDsMensagem());
					faturaCoboletada.setMsgTotaisImpostosParceiro(buscarTotalImpostosEbt(idBoleto, "").getDsMensagem());
				}
				
				faturas.add(faturaCoboletada);
				log.info("[FIM] buscarDadosFaturaNetEmbratel: " + boleto.getIdBoleto());
		}

		return faturas;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
    public List<FaturaNetDTO> buscarDadosBoletosAvulsos(List<LoteBoletoFaturaDTO> boletos, 
	                                                    String idCriterio, 
	                                                    String tipo, 
	                                                    boolean isArquivo) throws Exception {
		
		Long[] idBoletos = new Long[boletos.size()];
		
		int i = 0;
		
		for(LoteBoletoFaturaDTO boleto : boletos){
			idBoletos[i] = boleto.getIdBoleto();
			i++;
		}
		
		BatchParameter[] params = new BatchParameter[] {
				new BatchParameter(null, idBoletos, Types.ARRAY, false, TPSMS_NT_NUMBER),
		        new BatchParameter(BatchParameter.ORACLE_CURSOR, true)
		};
		
		List<FaturaNetDTO> faturas = new ArrayList<FaturaNetDTO>();
		List list = executeBatch(PRSMS_LST_BOLETO_AVULSO, params);
		List<DynamicBean> resultado = (ResultSetBeanCollection) list.get(1);
		
		int posicao = 0;
		
		for (DynamicBean item : resultado) {
	        DadosGeraisPrimeiraViaPrintDTO dados = listarDadosBoletoAvulso(item);
	        
	        LoteBoletoFaturaDTO boleto = boletos.get(posicao);
	        posicao++;
	        
	        log.info("[INICIO] buscarDadosBoletosAvulsos: " + boleto.getIdBoleto());
	        FaturaNetDTO fatura = new FaturaNetDTO();
	        
	        fatura.setPossuiNF(Boolean.FALSE);
	        fatura.setOperadoraNet(Boolean.TRUE);
	        
	        fatura.setIdBoleto( boleto.getIdBoleto());
	        
			if(boleto.getIdLote() != null){
				fatura.setIdLote(boleto.getIdLote());
			}
			
			String codigoClienteNET = dados.getCodigoClienteNET();
			if (StringUtils.isEmpty(codigoClienteNET)){
				codigoClienteNET = "0";
			}
			fatura.setNumContrato(Long.valueOf(codigoClienteNET));
			fatura.setCidContrato(boleto.getCidContrato());
			fatura.setDataVencimento(dados.getDataVencimentoBoleto());
			fatura.setFcBoletoConsolidado(boleto.getFcBoletoConsolidado());
			
			if(idCriterio != null && !(idCriterio.equals("")) ){
				fatura.setIdCriterio(Integer.parseInt(idCriterio));
			}
			
			List<DemonstrativoFinanceiroDTO> demonstrativos = new ArrayList<DemonstrativoFinanceiroDTO>();

			DemonstrativoFinanceiroDTO demonstrativo = new DemonstrativoFinanceiroDTO();
			demonstrativo.setDESCRICAOITEMDEMONST_FINANC(item.getBeanProperty("DS_DEMONSTRATIVO_PRINT"));
			demonstrativo.setVALORITEMDEMONST_FINANC(toDouble(item.get("VLR_COBRANCA")));
			
			demonstrativos.add(demonstrativo);
			fatura.setDemonstrativoFinanceiro(demonstrativos);
			fatura.setDadosGeraisNF(Arrays.asList(dados));
			
			faturas.add(fatura);
			log.info("[FIM] buscarDadosBoletosAvulsos: " + boleto.getIdBoleto());
        }
		
		return faturas;
	}

	
	/**
	 *
	 * Chamada para a procedure PGSMS_EMISSAO_PRINT.PRC_MSG_TOTAL_IMPOSTOS com
	 * os parametros:
	 * <ul>
	 * <li> p_ID_BOLETO - (input) - ID Boleto
	 * <li> v_MSG </li>
	 * </ul>
	 * @throws Exception
	 */
//	public MensagemDTO buscarTotalImpostosNet(Long idBoleto, Long idCobrancaNotaFiscal, String prefixo) throws Exception {
//
//		BatchParameter[] ps = new BatchParameter[3];
//		ps[0] = new BatchParameter(idBoleto, Types.NUMERIC, false);
//		ps[1] = new BatchParameter(idCobrancaNotaFiscal, Types.NUMERIC, false);
//		ps[2]= new BatchParameter(Types.VARCHAR, true);
//
//		List list = executeBatch(PRC_MSG_TOTAL_IMPOSTOS, ps);
//		
//		String dsMensagem = (String) list.get(2);
//
//		MensagemDTO mensagem = new MensagemDTO(); 
//		log.info("[INICIO] buscarTotalImpostosNet: " + idBoleto);
//
//		mensagem.setCcCodigoSetorizacao(SETOR_MSG_TOTAIS_NF_NET);
//		mensagem.setDsMensagem(dsMensagem);
//		mensagem.setCcCodigoFormatacao(CODIGO_FORMAT_TOTAIS_NF_NET);
//		mensagem.setPrefixo(prefixo);
//		
//		log.info("[FIM] buscarTotalImpostosNet: " + idBoleto);
//
//		return mensagem;
//
//	}
	
	/**
	 *
	 * Chamada para a procedure PGSMS_EMISSAO_PRINT.PRC_MSG_TOTAL_IMPOSTOS_PARC com
	 * os parametros:
	 * <ul>
	 * <li> p_ID_BOLETO - (input) - ID Boleto
	 * <li> v_MSG </li>
	 * </ul>
	 * @throws Exception
	 */
	public MensagemDTO buscarTotalImpostosEbt(Long idBoleto, String prefixo) throws Exception {

		BatchParameter[] ps = new BatchParameter[2];
		ps[0] = new BatchParameter(idBoleto, Types.NUMERIC, false);
		ps[1]= new BatchParameter(Types.VARCHAR, true);

		List list = executeBatch(PRC_MSG_TOTAL_IMPOSTOS_PARC, ps);

		String dsMensagem = (String) list.get(1);

		MensagemDTO mensagem = new MensagemDTO(); 
		log.info("[INICIO] buscarTotalImpostosEbt: " + idBoleto);

		mensagem.setCcCodigoSetorizacao(SETOR_MSG_TOTAIS_NF_PAR);
		mensagem.setDsMensagem(dsMensagem);
		mensagem.setCcCodigoFormatacao(CODIGO_FORMAT_TOTAIS_NF_PAR);
		mensagem.setPrefixo(prefixo);
		
		log.info("[FIM] buscarTotalImpostosEbt: " + idBoleto);

		return mensagem;

	}
	
	/**
	 *
	 * Chamada para a procedure PGSMS_EMISSAO_PRINT.PRC_MSG_COMBO_SVA
	 * <ul>
	 * <li> p_ID_BOLETO - (input) - ID Boleto</li>
	 * </ul>
	 * @throws Exception
	 */	
	public MensagemAnatelDTO buscarMensagemSVA(Long idBoleto) throws Exception {

		BatchParameter[] ps = new BatchParameter[3];
		ps[0] = new BatchParameter(idBoleto, Types.NUMERIC, false);
		ps[1]= new BatchParameter(Types.VARCHAR, true);
		ps[2]= new BatchParameter(Types.VARCHAR, true);

		List list = executeBatch(PRC_MSG_COMBO_SVA, ps);

		String dsMensagem = (String) list.get(1);
		MensagemAnatelDTO mensagem = null;
		if(dsMensagem != null && !dsMensagem.trim().equals("")){
			mensagem = new MensagemAnatelDTO();
			mensagem.setDescMensagemFormatada(dsMensagem);
		}		
		return mensagem;
	}
	
	/**
	 *
	 * Chamada para a procedure PR_RETORNA_MSG_CLARO_CLUB 
	 * <ul>
	 * <li> p_ID_BOLETO - (input) - ID Boleto</li>
	 * </ul>
	 */	
	public MensagemClaroClubeDTO buscarMensagemClaroClube(Long idBoleto){
		
		MensagemClaroClubeDTO mensagemClaroClubeDTO = new MensagemClaroClubeDTO();
		try {

			BatchParameter[] ps = new BatchParameter[3];
			ps[0] = new BatchParameter(idBoleto, Types.NUMERIC, false);
			ps[1] = new BatchParameter(Types.VARCHAR, true);
			ps[2] = new BatchParameter(Types.VARCHAR, true);

			List list = executeBatch(R_RETORNA_MSG_CLARO_CLUB, ps);

			String dsMensagem = (String) list.get(1);
			String ligado = (String) list.get(2);

			if (dsMensagem != null && !dsMensagem.trim().equals("")) {
				mensagemClaroClubeDTO.setDescMensagemFormatada(dsMensagem);
			}
			if(ligado != null && !ligado.trim().equals("")){
				if(ligado.equals("1")){
					mensagemClaroClubeDTO.setIsDemandaLigada(Boolean.TRUE);
				}else{
					mensagemClaroClubeDTO.setIsDemandaLigada(Boolean.FALSE);
				}				
			}else{
				mensagemClaroClubeDTO.setIsDemandaLigada(Boolean.FALSE);
			}
			return mensagemClaroClubeDTO;
//			 mensagemClaroClubeDTO.setIsDemandaLigada(true);   
//			 mensagemClaroClubeDTO.setIdBoleto(idBoleto);
//			 mensagemClaroClubeDTO.setDescMensagemFormatada("Saldo de pontos em 13/03/19                                                     #                  40|Pontos resgatados em 03/12                                                      #               1.000");
//			
		} catch ( Exception e) {
			mensagemClaroClubeDTO.setIsDemandaLigada(Boolean.FALSE);
			log.error(" Erro no método buscarMensagemClatoClube "
					+ e.getMessage());
		}

		return mensagemClaroClubeDTO;
	}
	

	/**
	 * Obtem a lista de dados relevantes das cobrancas do parceiro.
	 * @param bean
	 * @return lista de DadosCobrancaParceiroDTO
	 */
	private List<DadosCobrancaParceiroDTO> listarDadosCobrancaParceiroSemNF(List bean) {

		List<DadosCobrancaParceiroDTO> list = null;

		if (bean != null && bean.size() > 0) {
			list = new ArrayList<DadosCobrancaParceiroDTO>(bean.size());

			DynamicBean dyn = null;
			DadosCobrancaParceiroDTO dcp = null;

			for (Iterator i = bean.iterator(); i.hasNext();) {
				dyn = (DynamicBean) i.next();

				dcp = new DadosCobrancaParceiroDTO();
				dcp.setIdCobrancaParceiro(toLong(dyn.get("ID_COBRANCA_PARCEIRO")));
				dcp.setValorCobranca(toDouble(dyn.get("VLR_TOTAL")));
				dcp.setIdParceiro(toInteger(dyn.get("ID_PARCEIRO")));			

				list.add(dcp);
			}

		}

		return list;

	}
	/**
	* Alteração responsável para contemplar a funcionalidade das 5 últimas ocorrências na Fatura de 2º via.
	*
	* @author Sergio Ricardo Silva - implementação
	*
	*/
	public FaturaNetDTO buscarDadosSegundaViaFaturaNetEbt(Long idBoleto, String cidContrato, Long numContrato, String tipo) {

		log.info("[INICIO] buscarDadosFaturaNetEmbratel: " +idBoleto);

//		Long[] idBoletos = new Long[] {idBoleto};

/*
		BatchParameter[] ps1 = new BatchParameter[] {
				new BatchParameter("p_ID_BOLETO", idBoleto, Types.NUMERIC, false) // p_ID_BOLETO
				, new BatchParameter("p_COD_RETORNO", null, Types.VARCHAR, true) // p_COD_RETORNO
				, new BatchParameter("p_CURSOR", null, BatchParameter.ORACLE_CURSOR, true) // p_LST_DEMONST_FINANCEIRO
		};
*/

		BatchParameter[] ps1 = getParametroPRSMS_LST_DEMONST_FINAN_2VIA(idBoleto, tipo);

/*
		BatchParameter[] ps2 = new BatchParameter[] {
				new BatchParameter("p_ID_BOLETO", idBoletos, Types.ARRAY, false,TPSMS_NT_NUMBER)  // p_ID_BOLETO
				, new BatchParameter("p_COD_RETORNO", null, Types.VARCHAR, true) // p_COD_RETORNO
				, new BatchParameter("p_CURSOR", null, BatchParameter.ORACLE_CURSOR, true) // p_LST_DEMONST_FINANCEIRO
		};

*/
		BatchParameter[] ps2 = getParametroPRSMS_LST_DEMONS_FINAN_PAR(idBoleto);

		FaturaNetDTO faturaCoboletada = new FaturaNetDTO();

		log.info("[INICIO] buscarDadosFaturaNet: " + idBoleto);
		List list = executeBatch(PRSMS_LST_DEMONST_FINAN_2VIA, ps1);

		faturaCoboletada.setOperadoraNet(Boolean.TRUE);
		faturaCoboletada.setPossuiNF(Boolean.TRUE);

		if(list != null && list.size() > 0){
			faturaCoboletada
					.setDemonstrativoFinanceiro(listarDemonstrativoFinanceiroGrupoSubGrupo((ResultSetBeanCollection) list
							.get(2)));
			faturaCoboletada.setMinhaNet(listarMinhaNet((ResultSetBeanCollection) list.get(3)));


		}

		/**
		* Alteração responsável para contemplar a funcionalidade das 5 últimas ocorrências na Fatura de 2º via.
		*
		* @author Sergio Ricardo Silva - implementação
		*
		*/
		if(list != null && list.size() > 0){
			faturaCoboletada.setUltimasOcorrencias(this.buscarUltimasOcorrencias(numContrato, cidContrato));
		}

		List listParc = executeBatch(PRSMS_LST_DEMONS_FINAN_PAR, ps2);

		if(listParc != null && listParc.size() > 0){
			faturaCoboletada.setDemostrativoFinanceiroParceiro(listarDemonstrativoFinanceiroParceiro((ResultSetBeanCollection) listParc.get(2)));
		}

		log.info("[FIM] buscarDadosFaturaNet: " + faturaCoboletada.getIdBoleto());
		return faturaCoboletada;

	}

	private BatchParameter[] getParametroPRSMS_LST_DEMONST_FINAN_2VIA (Long idBoleto, String tipo) {

		BatchParameter[] ps = new BatchParameter[] {
				new BatchParameter(idBoleto, Types.NUMERIC, false) // p_ID_BOLETO
				, new BatchParameter(Types.VARCHAR, true) // p_COD_RETORNO
				, new BatchParameter(BatchParameter.ORACLE_CURSOR, true) // p_LST_DEMONST_FINANCEIRO
				, new BatchParameter(BatchParameter.ORACLE_CURSOR, true)
		};

		return ps;

	}

	private BatchParameter[] getParametroPRSMS_LST_DEMONS_FINAN_PAR (Long idBoleto) {

		Long[] idBoletos = new Long[] {idBoleto};

		BatchParameter[] ps = new BatchParameter[] {
				new BatchParameter(null, idBoletos, Types.ARRAY, false,TPSMS_NT_NUMBER)  // p_ID_BOLETO
				, new BatchParameter(Types.VARCHAR, true) // p_COD_RETORNO
				, new BatchParameter(BatchParameter.ORACLE_CURSOR, true) // p_LST_DEMONST_FINANCEIRO
				, new BatchParameter(BatchParameter.ORACLE_CURSOR, true)
		};

		return ps;

	}

	 public List<FaturaNetDTO> buscarDadosFaturas(String idCriterio, List<LoteBoletoFaturaDTO> boletos ) {

		List<FaturaNetDTO> faturas = new ArrayList<FaturaNetDTO>();

		for (LoteBoletoFaturaDTO boleto : boletos) {
			if( boleto.getFcBoletoConsolidado().equalsIgnoreCase("N") ||
					boleto.getFcBoletoConsolidado().equalsIgnoreCase("R") ||
				( boleto.getFcBoletoConsolidado().equalsIgnoreCase("D") && boleto.isOperadoraNet() ) ||
				boleto.getFcBoletoConsolidado().equalsIgnoreCase("M")) {

				//faturas.add(buscarDadosFaturaNet(boleto,idCriterio));

			}else if(boleto.getFcBoletoConsolidado().equalsIgnoreCase("C") ||
					boleto.getFcBoletoConsolidado().equalsIgnoreCase("B")){

				//faturas.add(buscarDadosFaturaNetEbt(boleto,idCriterio));

			}else if( boleto.getFcBoletoConsolidado().equalsIgnoreCase("P") ||
					boleto.getFcBoletoConsolidado().equalsIgnoreCase("A") ||
					 ( boleto.getFcBoletoConsolidado().equalsIgnoreCase("D") && boleto.isOperadoraNet() == false )){

				//faturas.add(buscarDadosFaturaEbt(boleto,idCriterio));

			}
		}
		return faturas;
	}


	 public List<FaturaNetDTO> buscarDadosFaturaNetArray(String idCriterio, Object [] faturaBoletos) {

			List<LoteBoletoFaturaDTO> boletosNet = new ArrayList<LoteBoletoFaturaDTO>();
			List<LoteBoletoFaturaDTO> boletosEbt = new ArrayList<LoteBoletoFaturaDTO>();
			List<LoteBoletoFaturaDTO> boletosNetEbt = new ArrayList<LoteBoletoFaturaDTO>();

			LoteBoletoFaturaDTO[] ids = new LoteBoletoFaturaDTO[faturaBoletos.length];

			for (int x = 0; x < faturaBoletos.length; x++) {

				ids[x] = (LoteBoletoFaturaDTO) faturaBoletos [x];

				if( ids[x].getFcBoletoConsolidado().equalsIgnoreCase("N") ||
						ids[x].getFcBoletoConsolidado().equalsIgnoreCase("R") ||
					( ids[x].getFcBoletoConsolidado().equalsIgnoreCase("D") && ids[x].isOperadoraNet() ) ||
					ids[x].getFcBoletoConsolidado().equalsIgnoreCase("M")) {

					boletosNet.add(ids[x]);
					//faturas.add(buscarDadosFaturaNet(ids[x],idCriterio));
				}else if(ids[x].getFcBoletoConsolidado().equalsIgnoreCase("C") ||
						ids[x].getFcBoletoConsolidado().equalsIgnoreCase("B")){

					boletosNetEbt.add(ids[x]);
					//faturas.add(buscarDadosFaturaNetEbt(ids[x],idCriterio));

				}else{

					boletosEbt.add(ids[x]);
					//faturas.add(buscarDadosFaturaEbt(ids[x],idCriterio));

				}
			}

			List<FaturaNetDTO> faturas = new ArrayList<FaturaNetDTO>(ids.length);

			if(!boletosNet.isEmpty())
				faturas.addAll(buscarBoletoNet(idCriterio, boletosNet)) ;

			if(!boletosNetEbt.isEmpty())
				faturas.addAll(buscarBoletoNetEbt(idCriterio, boletosNetEbt));

			if(!boletosEbt.isEmpty())
				faturas.addAll(buscarBoletoEbt(idCriterio, boletosEbt));

			List<FaturaNetDTO> faturasOrdenadas = new ArrayList<FaturaNetDTO>();
			for(int i = 0; i < ids.length; i++){
				LoteBoletoFaturaDTO boletoFatura = ids[i];
				FaturaNetDTO faturaPesquisa = new FaturaNetDTO();
				faturaPesquisa.setIdBoleto(boletoFatura.getIdBoleto());
				int indice = faturas.indexOf(faturaPesquisa);
				faturasOrdenadas.add(faturas.get(indice));
			}

			return faturasOrdenadas;
		}

	 private List<FaturaNetDTO> buscarBoletoNet(String idCriterio, List<LoteBoletoFaturaDTO> boletosNet){

		    List<FaturaNetDTO> faturas = new ArrayList<FaturaNetDTO>();
			int posicaoInicial = 0;
			int posicaoFinal = this.NUMERO_QUEBRA -1;
			Boolean terminou = Boolean.TRUE;
			Boolean alterarVar = Boolean.FALSE;

			if(posicaoFinal >= boletosNet.size()  ){
				alterarVar = Boolean.TRUE;
				posicaoFinal = boletosNet.size() - 1;
			}

			while(terminou){
				List<LoteBoletoFaturaDTO> array = boletosNet.subList(posicaoInicial, posicaoFinal+1);

				Long[] idBoletos = new Long[this.NUMERO_QUEBRA];
				int i = 0;
				for(LoteBoletoFaturaDTO boleto : array ){
					idBoletos[i] = boleto.getIdBoleto();
					i++;
				}

				//faturas.addAll(buscarDadosFaturaNet(array, idCriterio, idBoletos ));
				posicaoInicial = ++posicaoFinal;
				posicaoFinal +=  ( this.NUMERO_QUEBRA - 1 );
				if(alterarVar){
					terminou = Boolean.FALSE;
				}
				if(posicaoFinal >= boletosNet.size()  ){
					alterarVar = Boolean.TRUE;
					posicaoFinal = boletosNet.size() - 1;
				}
			}

			return faturas;

		 }

	 private List<FaturaNetDTO> buscarBoletoNetEbt(String idCriterio,List<LoteBoletoFaturaDTO> boletosNetEbt){

		    List<FaturaNetDTO> faturas = new ArrayList<FaturaNetDTO>();
			int posicaoInicial = 0;
			int posicaoFinal = this.NUMERO_QUEBRA -1;
			Boolean terminou = Boolean.TRUE;
			Boolean alterarVar = Boolean.FALSE;

			if(posicaoFinal >= boletosNetEbt.size()  ){
				alterarVar = Boolean.TRUE;
				posicaoFinal = boletosNetEbt.size() - 1;
			}

			while(terminou){
				List<LoteBoletoFaturaDTO> array = boletosNetEbt.subList(posicaoInicial, posicaoFinal+1);

				Long[] idBoletos = new Long[this.NUMERO_QUEBRA];
				int i = 0;
				for(LoteBoletoFaturaDTO boleto : array ){
					idBoletos[i] = boleto.getIdBoleto();
					i++;
				}

				//faturas.addAll(buscarDadosFaturaNetEbt(array, idCriterio, idBoletos ));
				posicaoInicial = ++posicaoFinal;
				posicaoFinal +=  ( this.NUMERO_QUEBRA - 1 );
				if(alterarVar){
					terminou = Boolean.FALSE;
				}
				if(posicaoFinal >= boletosNetEbt.size()  ){
					alterarVar = Boolean.TRUE;
					posicaoFinal = boletosNetEbt.size() - 1;
				}
			}

			return faturas;
		 }

	 private List<FaturaNetDTO> buscarBoletoEbt(String idCriterio, List<LoteBoletoFaturaDTO> boletosEbt){

		    List<FaturaNetDTO> faturas = new ArrayList<FaturaNetDTO>();
			int posicaoInicial = 0;
			int posicaoFinal = this.NUMERO_QUEBRA -1;
			Boolean terminou = Boolean.TRUE;
			Boolean alterarVar = Boolean.FALSE;

			if(posicaoFinal >= boletosEbt.size()  ){
				alterarVar = Boolean.TRUE;
				posicaoFinal = boletosEbt.size() - 1;
			}

			while(terminou){
				List<LoteBoletoFaturaDTO> array = boletosEbt.subList(posicaoInicial, posicaoFinal+1);

				Long[] idBoletos = new Long[this.NUMERO_QUEBRA];
				int i = 0;
				for(LoteBoletoFaturaDTO boleto : array ){
					idBoletos[i] = boleto.getIdBoleto();
					i++;
				}

				//faturas.addAll(buscarDadosFaturaEbt(array, idCriterio, idBoletos ));
				posicaoInicial = ++posicaoFinal;
				posicaoFinal +=  ( this.NUMERO_QUEBRA - 1 );
				if(alterarVar){
					terminou = Boolean.FALSE;
				}
				if(posicaoFinal >= boletosEbt.size()  ){
					alterarVar = Boolean.TRUE;
					posicaoFinal = boletosEbt.size() - 1;
				}
			}

			return faturas;


	 }


	 /**
	 *
	 * Chamada para a procedure PGSMS_EMISSAO_PRINT.PRSMS_LST_FATURA_NET com os
	 * parametros:
	 * <ul>
	 * <li>p_ID_BOLETO - (input) - ID Boleto</li>
	 * <li>p_SEL_CLIENTE_NF_BOL - (output) - Cursor
	 * selecionarClienteNotaFiscalBoleto</li>
	 * <li>p_LST_DET_NF - (output) - Cursor listarDetalhamentoNotaFiscal</li>
	 * <li>p_LST_TRIBUTO_PARCEIRO - (output) - Cursor listarTributo</li>
	 * <li>p_SEL_DADOS_1_VIA_SEM_NF - (output) - Cursor
	 * selecionarDadosPrimeiraViaSemNotaFiscal</li>
	 * <li>p_COD_RETORNO - (output) - Descricao de erro</li>
	 * </ul>
	 * @throws Exception
	 */
	public List<FaturaNetDTO> buscarDadosFaturaNet(List<LoteBoletoFaturaDTO> boletos ,String idCriterio, Long idLoteParametro, String tipo, boolean isArquivo) throws Exception {
		return buscarDadosFaturaNet(boletos , null, idCriterio, idLoteParametro, tipo, isArquivo, null);
	}
	
	
	
	 /**NSMSP_172250_NI_003
	 * buscar envio da fatura por boleto 
	 * @param idTipoLoteParam 
	 * @param idLote 
	 */
	public Integer buscarTipoEnvioFaturaPorLote(String cidContratoParam, SnLoteBean loteCorrenteParam, Integer idFormaEnvioFaturaParam ) {
					
		try{			
			//verifica se o parametro ta ligado 
			if(obterParametroObrigatorioNumerico(cidContratoParam, PrintHouseConstants.ALTERA_FORMA_ENVIO).longValue()>0){
				//verifica se o lote é de sengunda via 
				if(loteCorrenteParam!=null 
						&& loteCorrenteParam.getSnTipoLoteBean()!=null 
						&& loteCorrenteParam.getSnTipoLoteBean().getSgTipoLote()!=null
						&&  loteCorrenteParam.getSnTipoLoteBean().getSgTipoLote().equalsIgnoreCase(SnTipoLoteBean.Sigla.SEGUNDA_VIA.getChaveSigla())){
					Integer tipoEnvioRetorno = buscarTipoEnvioFaturaPorBoleto(loteCorrenteParam.getIdLote());
					// parametro igual a 0 retorna o valor convencional
						if(tipoEnvioRetorno < 1){
							return  idFormaEnvioFaturaParam;
						}
					return tipoEnvioRetorno;			
				}
			}
			
		}catch(Exception ex){ 
			log.error(new BasicAttachmentMessage(" loteCorrenteParam "+loteCorrenteParam.getIdLote()+" buscarTipoEnvioFaturaPorBoleto ." , AttachmentMessageLevel.ERROR));
			log.error(new BasicAttachmentMessage(" idFormaEnvioFaturaParam "+idFormaEnvioFaturaParam+" buscarTipoEnvioFaturaPorBoleto ." , AttachmentMessageLevel.ERROR));
			log.error(ex);
			return idFormaEnvioFaturaParam;
		}		
			return idFormaEnvioFaturaParam;		
			
	}

	
	
	 /**NSMSP_172250_NI_003
	 * buscar envio da fatura por boleto 
	 * @param idLote 
	 */
	public Integer buscarTipoEnvioFaturaPorBoleto( Integer idLoteParam ) {
		
		Integer tipoEnvioRetorno =0;
		 
		Long idLote = idLoteParam.longValue();
		try{ 
			BatchParameter[] ps = new BatchParameter[2];
			ps[0] = new BatchParameter(idLote, Types.NUMERIC, false);
			ps[1] = new BatchParameter(Types.NUMERIC, true); 
				
				List list = executeBatch(ENVIO_LOTE_SEGVIA, ps);	
		
				log.info("[INI] buscarTipoEnvioFaturaPorBoleto: " + idLoteParam); 	
				
				if (list != null) {
					 tipoEnvioRetorno = Integer.parseInt(list.get(1).toString());
				}
								
		}catch(Exception ex){   	
			log.info(new BasicAttachmentMessage("idLoteParam "+idLoteParam+" buscarTipoEnvioFaturaPorBoleto ." , AttachmentMessageLevel.ERROR));
			return tipoEnvioRetorno;
		}
				
		return tipoEnvioRetorno;
	}

	
	 /**
		 * montar o result set por forma de envio 
		 */
	private int listarTipoEnvioSegundaVia(List bean) {
		DynamicBean dyn = null;
		DadosGeraisPrimeiraViaPrintDTO dpv = null;
		for (Iterator i = bean.iterator(); i.hasNext();) {
			dyn = (DynamicBean) i.next();
			dpv = new DadosGeraisPrimeiraViaPrintDTO();			
			return toInteger(dyn.get("ID_FORMA_ENVIO_FATURA"));			
		}

		return 0;
	}
	
	
	/**
	 *
	 * Chamada para a procedure PGSMS_EMISSAO_PRINT.PRSMS_LST_FATURA_NET com os
	 * parametros:
	 * <ul>
	 * <li>p_ID_BOLETO - (input) - ID Boleto</li>
	 * <li>p_ID_NOTA_FISCAL - (input) - ID NF</li>
	 * <li>p_SEL_CLIENTE_NF_BOL - (output) - Cursor
	 * selecionarClienteNotaFiscalBoleto</li>
	 * <li>p_LST_DET_NF - (output) - Cursor listarDetalhamentoNotaFiscal</li>
	 * <li>p_LST_TRIBUTO_PARCEIRO - (output) - Cursor listarTributo</li>
	 * <li>p_SEL_DADOS_1_VIA_SEM_NF - (output) - Cursor
	 * selecionarDadosPrimeiraViaSemNotaFiscal</li>
	 * <li>p_COD_RETORNO - (output) - Descricao de erro</li>
	 * </ul>
	 * @throws Exception
	 */
	public List<FaturaNetDTO> buscarDadosFaturaNet(List<LoteBoletoFaturaDTO> boletos,Long idNotaFiscal ,String idCriterio, Long idLoteParametro, String tipo, boolean isArquivo, String tipoFatura) throws Exception {

		FaturaNetDTO fatura = null;

		Long[] idBoletos = new Long[boletos.size()];
		int i = 0;
		for(LoteBoletoFaturaDTO boleto : boletos ){
			idBoletos[i] = boleto.getIdBoleto();
			i++;
		}

		Long idLote = null;

		BatchParameter[] ps = new BatchParameter[10];
		ps[0] = new BatchParameter(null, idBoletos, Types.ARRAY, false,TPSMS_NT_NUMBER);

		if (idLoteParametro != null){
			idLote = idLoteParametro;
		} else if(boletos.get(0) != null && boletos.get(0).getIdLote() != null){
			idLote = Long.parseLong(String.valueOf(boletos.get(0).getIdLote()));

		}
		ps[1] = new BatchParameter(idLote, Types.NUMERIC, false);
		if (idNotaFiscal != null && idNotaFiscal > 0 && tipoFatura != null && tipoFatura.equalsIgnoreCase("NF")){
			ps[2] = new BatchParameter(idNotaFiscal, Types.NUMERIC, false);
		}else{
			ps[2] = new BatchParameter(null, Types.NUMERIC, false);
		}
		ps[3] = new BatchParameter(BatchParameter.ORACLE_CURSOR , true);
		ps[4] = new BatchParameter(BatchParameter.ORACLE_CURSOR , true);
		ps[5] = new BatchParameter(BatchParameter.ORACLE_CURSOR , true);
		ps[6] = new BatchParameter(BatchParameter.ORACLE_CURSOR , true);
		ps[7]= new BatchParameter(Types.VARCHAR, true);
		//ps[7]= new BatchParameter("p_Tipo_Emissao", tipo, Types.VARCHAR, false);
		ps[8]= new BatchParameter(BatchParameter.ORACLE_CURSOR, true);
		ps[9]= new BatchParameter(BatchParameter.ORACLE_CURSOR, true);	
		
		
		
		List list = executeBatch(PRSMS_LST_FATURA_NET, ps);
		
		List<FaturaNetDTO> faturas = new ArrayList<FaturaNetDTO>();
		

		Map<Long,List<DynamicBean>>  mapNFBoleto = listarBoletos( (ResultSetBeanCollection) list.get(3));
		
		
		Map<Long,List<DynamicBean>>  mapDetNFBoleto = listarBoletos( (ResultSetBeanCollection) list.get(4));
		Map<Long,List<DynamicBean>>  mapTribNFBoleto = listarBoletos( (ResultSetBeanCollection) list.get(5));
		Map<Long,List<DynamicBean>>  mapDemostrativoBoleto = listarBLTO( (ResultSetBeanCollection) list.get(6));
		Map<Long,List<DynamicBean>>  mapLstMinhaNet = listarBLTO( (ResultSetBeanCollection) list.get(8));
		Map<Long,List<DynamicBean>>  mapFiliados = listarBLTO( (ResultSetBeanCollection) carregarFiliados(idLote).get(3) );
		Map<Long,List<DynamicBean>>  maplstMensNotaFiscal = listarBLTO( (ResultSetBeanCollection) list.get(9));		

		BigDecimal isolaLote = BigDecimal.ZERO;
		try {
			String cidadeOperadora = boletos.get(0).getCidContrato();				
            isolaLote = obterParametroObrigatorioNumerico(cidadeOperadora, REMOVE_BOL_LOTE_SEG_VIA);
		} catch (EmissaoBusinessResourceException e){
            log.info("Parametro de configuracao que isola boletos de segunda via com erros em um novo lote não foi encontrado. Configurando valor Default (desligado)."); 
		}  

		for (LoteBoletoFaturaDTO boleto : boletos ){

				log.info("[INI] buscarDadosFaturaNet: " + boleto.getIdBoleto());
				Long idBoleto = Long.valueOf(boleto.getIdBoleto());

				fatura = new FaturaNetDTO();

				List<DadosGeraisPrimeiraViaPrintDTO> dadosGerais = new ArrayList<DadosGeraisPrimeiraViaPrintDTO>();

				//NSMSP_172250_NI_003
				  SnLoteBean loteCorrente = new SnLoteBean();
				  if(boleto.getIdLote() != null){
				  loteCorrente.setIdLote(boleto.getIdLote());
				  SnTipoLoteBean snTipoLoteBean = new SnTipoLoteBean();
				  loteCorrente.setSnTipoLoteBean(snTipoLoteBean);
				  loteCorrente.getSnTipoLoteBean().setSgTipoLote(boleto.getSiglaLote());
				  
				  }

				  
				  
				if(mapNFBoleto.get(Long.parseLong(String.valueOf(boleto.getIdBoleto()))) != null && tipo.equals("S")){
					dadosGerais = listarDadosSegundaVia(loteCorrente, ((List)mapNFBoleto.get(Long.parseLong(String.valueOf(boleto.getIdBoleto())))));
				}else if(mapNFBoleto.get(Long.parseLong(String.valueOf(boleto.getIdBoleto()))) != null ){
					dadosGerais = listarDadosPrimeiraVia(loteCorrente,((List)mapNFBoleto.get(Long.parseLong(String.valueOf(boleto.getIdBoleto())))),
							((List)maplstMensNotaFiscal.get(Long.parseLong(String.valueOf(boleto.getIdBoleto())))),
							tipo, isArquivo);
				}


				log.info("[INICIO] buscarDadosFaturaNet: " + boleto.getIdBoleto());
				if (dadosGerais.isEmpty()){

					BatchParameter[] ps_no_nf = new BatchParameter[] { new BatchParameter(idBoleto, Types.NUMERIC), // p_ID_BOLETO
							new BatchParameter(Types.VARCHAR, true), // p_COD_RETORNO
							new BatchParameter(BatchParameter.ORACLE_CURSOR, true)}; // p_SEL_DADOS_1_VIA_SEM_NF

						 	List list_no_nf = executeBatch(PRSMS_SEL_DADOS_1_VIA_SEM_NF, ps_no_nf);

					//se for segunda via, faz a chamada diferente pra popular o arquivo da print...	 	
					if(tipo.equals("S")){
						dadosGerais = listarDadosSegundaVia(loteCorrente,(ResultSetBeanCollection) list_no_nf.get(2));
					}	
					else{
						dadosGerais = listarDadosPrimeiraVia(loteCorrente,(ResultSetBeanCollection) list_no_nf.get(2), (ResultSetBeanCollection) list.get(9),tipo, isArquivo);
					}
						
					try{
						if ((isolaLote.compareTo(BigDecimal.ONE) == 0) &&
							CollectionUtils.isEmpty(dadosGerais) && 
						    tipo.equals("S")){
							
							log.info(new BasicAttachmentMessage("Boleto "+idBoleto+" nao retornou dados de NF NET ." , AttachmentMessageLevel.WARNING));
							continue;
						}
						if(!dadosGerais.isEmpty()){
						dadosGerais.get(0).setIsOperadoraNet("S");
						}
					}catch(Exception ex){
						log.info(new BasicAttachmentMessage("Boleto "+idBoleto+" nao retornou dados de NF ." , AttachmentMessageLevel.ERROR));
						throw ex;
					}

					fatura.setPossuiNF(Boolean.FALSE);
					fatura.setOperadoraNet(Boolean.TRUE);

				}else{
					if(!dadosGerais.isEmpty()){
					fatura.setOperadoraNet(dadosGerais.get(0).isOperadoraNet());
					}
					fatura.setPossuiNF(Boolean.TRUE);
				}

				if(boleto.getIdLote() != null){
					fatura.setIdLote(boleto.getIdLote());
				}

				if(!dadosGerais.isEmpty()){
				fatura.setNumContrato(Long.valueOf(dadosGerais.get(0).getCodigoClienteNET()));
				}
				fatura.setCidContrato(boleto.getCidContrato());

				fatura.setUltimasOcorrencias(this.buscarUltimasOcorrencias(fatura.getNumContrato(), fatura.getCidContrato()));

				fatura.setIdBoleto(boleto.getIdBoleto());
				if(!dadosGerais.isEmpty()){
				fatura.setDataVencimento(dadosGerais.get(0).getDataVencimentoBoleto());
				}
				fatura.setFcBoletoConsolidado(boleto.getFcBoletoConsolidado());
				if(idCriterio != null && !(idCriterio.equals("")) ){
					fatura.setIdCriterio(Integer.parseInt(idCriterio));
				}

				fatura.setDadosGeraisNF(dadosGerais);
				fatura.setDetalheNotaFiscal(listarDetalhamentoNotaFiscal(((List)mapDetNFBoleto.get(Long.parseLong(String.valueOf(boleto.getIdBoleto()))))));
				fatura.setTributos(listarTributo(((List)mapTribNFBoleto.get(Long.parseLong(String.valueOf(boleto.getIdBoleto()))))));
				fatura.setDemonstrativoFinanceiro(listarDemonstrativoFinanceiroGrupoSubGrupo(((List)mapDemostrativoBoleto.get(Long.parseLong(String.valueOf(boleto.getIdBoleto()))))));
				//fatura.setMinhaNet(listarMinhaNet(((List)mapDemostrativoBoleto.get(Long.parseLong(String.valueOf(boleto.getIdBoleto())))),null));
				fatura.setMinhaNet(listarMinhaNet(((List)mapLstMinhaNet.get(Long.parseLong(String.valueOf(boleto.getIdBoleto()))))));
				//fatura.setFustFuntel(selecionaFustFuntel((ResultSetBeanCollection) list.get(6)));
				fatura.setFiliados(listarFiliados((List) mapFiliados.get(Long.parseLong(String.valueOf(boleto.getIdBoleto())))));
						
				
//				// Mensagem dos totais dos impostos
//				if (isArquivo) {
//					fatura.setMsgTotaisImpostos(buscarTotalImpostosNet(idBoleto, dadosGerais.get(0).getIdCobrancaNotaFiscal(), "").getDsMensagem());
//				}
				
				faturas.add(fatura);
				log.info("[FIM] buscarDadosFaturaNet: " + fatura.getIdBoleto());
		}

		return faturas;
	}

	/**
	* Alteração responsável para contemplar a funcionalidade das 5 últimas ocorrências na Fatura de 2º via.
	*
	* @author Sergio Ricardo Silva - implementação
	*
	*/
	public FaturaNetDTO buscarDadosSegundaViaNet(Long idBoleto, String cidContrato, Long numContrato, String tipo) {
		log.info("[INI] buscarDadosFaturaNet: " + idBoleto);

/*
		BatchParameter[] ps = new BatchParameter[] { new BatchParameter(idBoleto, Types.NUMERIC), // p_ID_BOLETO
				new BatchParameter(Types.VARCHAR, true), // p_COD_RETORNO
				new BatchParameter(BatchParameter.ORACLE_CURSOR, true) // p_LST_DEMONST_FINANCEIRO

		};

*/
		BatchParameter[] ps = getParametroPRSMS_LST_DEMONST_FINAN_2VIA(idBoleto, tipo);

		log.info("[INICIO] buscarDadosFaturaNet: " + idBoleto);
		FaturaNetDTO fatura = new FaturaNetDTO();
//		List list = executeBatch(PRSMS_LST_DEMONST_FINANCEIRO, ps);
		List list = executeBatch(PRSMS_LST_DEMONST_FINAN_2VIA, ps);

		fatura.setOperadoraNet(Boolean.TRUE);
		fatura.setPossuiNF(Boolean.TRUE);

		if(list != null && list.size() > 0){
			fatura.setDemonstrativoFinanceiro(listarDemonstrativoFinanceiroGrupoSubGrupo((ResultSetBeanCollection) list.get(2)));
			fatura.setMinhaNet(listarMinhaNet((ResultSetBeanCollection) list.get(3)));
		}
		/**
		* Alteração responsável para contemplar a funcionalidade das 5 últimas ocorrências na Fatura de 2º via.
		*
		* @author Sergio Ricardo Silva - implementação
		*
		*/
		if(list != null && list.size() > 0){
			fatura.setUltimasOcorrencias(this.buscarUltimasOcorrencias(numContrato, cidContrato));
		}

		log.info("[FIM] buscarDadosFaturaNet: " + fatura.getIdBoleto());
		return fatura;
	}
	
	/**
	 *
	 * Chamada para a procedure PGSMS_EMISSAO_PRINT.PRSMS_LST_FATURA_EBT com os
	 * parametros:
	 * <ul>
	 * <li>p_ID_BOLETO - (input) - ID Boleto</li>
	 * <li>p_ID_PARCEIRO - (input) - ID Parceiro</li>
	 * <li>p_ID_COBRANCA_NOTA_FISCAL - (input) - ID Cobranca NF </li>
	 * <li>p_SEL_CLI_NF_PAR - (output) - Cursor
	 * selecionarClienteNotaFiscalParceiro</li>
	 * <li>p_LST_TIT_NF_OPER_PAR - (output) - Cursor
	 * listarTituloNotaFiscalOperadoraParceiro </li>
	 * <li>p_LST_TIT_NF_OPER_POR_PAR - (output) - Cursor
	 * listarTituloNotaFiscalOperadoraPorParceiro</li>
	 * <li>p_SEL_CLI_NF_BLT_PAR - (output) - Cursor
	 * selecionarClienteNotaFiscalBoletoParceiro</li>
	 * <li>p_LST_DET_NF_PAR - (output) - Cursor
	 * listarDetalhamentoNotaFiscalParceiro</li>
	 * <li>p_LST_TRIBUTO_PARCEIRO - (output) - Cursor listarTributoParceiro</li>
	 * <li>p_LST_TRIBUTO_PARC_TOTAL - (output) - Cursor
	 * listarTributoParceiroTotal</li>
	 * <li>p_LST_DEMONS_FINAN_PAR - (output) - Cursor
	 * listarDemonstrativoFinanceiroParceiro</li>
	 * <li>p_SEL_FUST_FUNTEL - (output) - Cursor selecionaFustFuntel</li>
	 * <li>p_LST_COBRANCA_PAR_SEM_NF - (output) - Cursor listarDadosCobrancaParceiroSemNF</li>
	 * <li>p_COD_RETORNO - (output) - Descricao de erro</li>
	 * </ul>
	 * @throws Exception
	 */
	public List<FaturaNetDTO> buscarDadosFaturaEbt(List<LoteBoletoFaturaDTO> boletos, String IdCriterio, boolean isArquivo) throws Exception {
		return buscarDadosFaturaEbt(boletos, null , IdCriterio, isArquivo, null);
	}

	protected BigDecimal obterParametroObrigatorioNumerico(String cidContrato,
	                                                       String nomeParametro){

		try {
			
		    Object valorParametro = getVlrSnParam(cidContrato, nomeParametro, true);

			if (valorParametro instanceof Double) {
				return new BigDecimal((Double) valorParametro);
			}

			throw new EmissaoBusinessResourceException(EmissaoResources.NULL_PARAMETER, new Object[] { nomeParametro,
					"Cidade: " + cidContrato });
			
		} catch (BaseDAOException e) {
			throw new EmissaoBusinessResourceException(EmissaoResources.NULL_PARAMETER, new Object[] { nomeParametro,
			                                           "Cidade: " + cidContrato });
		}	
		
	}
	
	/**
	 *
	 * Chamada para a procedure PGSMS_EMISSAO_PRINT.PRSMS_LST_FATURA_EBT com os
	 * parametros:
	 * <ul>
	 * <li>p_ID_BOLETO - (input) - ID Boleto</li>
	 * <li>p_ID_PARCEIRO - (input) - ID Parceiro</li>
	 * <li>p_ID_COBRANCA_NOTA_FISCAL - (input) - ID Cobranca NF </li>
	 * <li>p_SEL_CLI_NF_PAR - (output) - Cursor
	 * selecionarClienteNotaFiscalParceiro</li>
	 * <li>p_LST_TIT_NF_OPER_PAR - (output) - Cursor
	 * listarTituloNotaFiscalOperadoraParceiro </li>
	 * <li>p_LST_TIT_NF_OPER_POR_PAR - (output) - Cursor
	 * listarTituloNotaFiscalOperadoraPorParceiro</li>
	 * <li>p_SEL_CLI_NF_BLT_PAR - (output) - Cursor
	 * selecionarClienteNotaFiscalBoletoParceiro</li>
	 * <li>p_LST_DET_NF_PAR - (output) - Cursor
	 * listarDetalhamentoNotaFiscalParceiro</li>
	 * <li>p_LST_TRIBUTO_PARCEIRO - (output) - Cursor listarTributoParceiro</li>
	 * <li>p_LST_TRIBUTO_PARC_TOTAL - (output) - Cursor
	 * listarTributoParceiroTotal</li>
	 * <li>p_LST_DEMONS_FINAN_PAR - (output) - Cursor
	 * listarDemonstrativoFinanceiroParceiro</li>
	 * <li>p_SEL_FUST_FUNTEL - (output) - Cursor selecionaFustFuntel</li>
	 * <li>p_LST_COBRANCA_PAR_SEM_NF - (output) - Cursor listarDadosCobrancaParceiroSemNF</li>
	 * <li>p_COD_RETORNO - (output) - Descricao de erro</li>
	 * </ul>
	 * @throws Exception
	 */
	public List<FaturaNetDTO> buscarDadosFaturaEbt(List<LoteBoletoFaturaDTO> boletos, Long idNotaFiscal, String IdCriterio, boolean isArquivo, String tipoFatura) throws Exception {

		/*BatchParameter[] ps = new BatchParameter[] { new BatchParameter(idBoletos, Types.ARRAY), // array de inteiros p_ID_BOLETO
				new BatchParameter(BatchParameter.ORACLE_CURSOR, true), // p_SEL_CLI_NF_PAR
				//new BatchParameter(BatchParameter.ORACLE_CURSOR, true),// p_LST_TIT_NF_OPER_POR_PAR
				//new BatchParameter(BatchParameter.ORACLE_CURSOR, true),// p_SEL_CLI_NF_BLT_PAR
				new BatchParameter(BatchParameter.ORACLE_CURSOR, true),// p_LST_DET_NF_PAR
				new BatchParameter(BatchParameter.ORACLE_CURSOR, true),// p_LST_TRIBUTO_PARCEIRO
				new BatchParameter(BatchParameter.ORACLE_CURSOR, true),// p_LST_DEMONS_FINAN_PAR
				//new BatchParameter(BatchParameter.ORACLE_CURSOR, true),// p_SEL_FUST_FUNTEL
				new BatchParameter(BatchParameter.ORACLE_CURSOR, true),// p_DET_LIGACOES
				new BatchParameter(Types.VARCHAR, true) // p_COD_RETORNO
		};
*/

		Long[] idBoletos = new Long[boletos.size()];
		int i = 0;
		for(LoteBoletoFaturaDTO boleto : boletos ){
			idBoletos[i] = boleto.getIdBoleto();
			i++;
		}

		BatchParameter[] ps = new BatchParameter[11];
		ps[0] = new BatchParameter(null, idBoletos, Types.ARRAY, false,TPSMS_NT_NUMBER);
		if (idNotaFiscal != null && idNotaFiscal > 0 && tipoFatura != null && tipoFatura.equalsIgnoreCase("NF")){
			ps[1] = new BatchParameter(idNotaFiscal, Types.NUMERIC, false);
		}else{
			ps[1] = new BatchParameter(null, Types.NUMERIC, false);
		}
		ps[2] = new BatchParameter(BatchParameter.ORACLE_CURSOR, true);
		ps[3] = new BatchParameter(BatchParameter.ORACLE_CURSOR, true);
		ps[4] = new BatchParameter(BatchParameter.ORACLE_CURSOR, true);
		ps[5] = new BatchParameter(BatchParameter.ORACLE_CURSOR, true);
		ps[6] = new BatchParameter(BatchParameter.ORACLE_CURSOR, true);
		ps[7]= new BatchParameter(BatchParameter.ORACLE_CURSOR , true);
		ps[8] = new BatchParameter(Types.VARCHAR, true);
		ps[9]= new BatchParameter(BatchParameter.ORACLE_CURSOR, true);
		ps[10]= new BatchParameter(BatchParameter.ORACLE_CURSOR, true);		

		List list = executeBatch(PRSMS_LST_FATURA_EBT, ps);
		List<FaturaNetDTO> faturas = new ArrayList<FaturaNetDTO>();

		FaturaNetDTO faturaEmbratelDTO = null ;


		Map<Long,List<DynamicBean>>  mapNFBoleto = listarBoletos( (ResultSetBeanCollection) list.get(2));
		Map<Long,List<DynamicBean>>  mapDetNFBoleto = listarBoletos( (ResultSetBeanCollection) list.get(3));
		Map<Long,List<DynamicBean>>  mapTribNFBoleto = listarBoletos( (ResultSetBeanCollection) list.get(4));
		Map<Long,List<DynamicBean>>  mapDemostrativoBoleto = listarBoletos( (ResultSetBeanCollection) list.get(5));
		Map<Long,List<DynamicBean>>  mapLigacoesBoleto = listarBoletos( (ResultSetBeanCollection) list.get(6));
		Map<Long,List<DynamicBean>>  mapLstMinhaNet = listarBLTO( (ResultSetBeanCollection) list.get(9));
		
		Map<Long,List<String>> lstPlanoServicoEbt = null;
		try{
			if(isArquivo){
				lstPlanoServicoEbt = listarPlanoServicoEbt( (ResultSetBeanCollection) list.get(6));
			}
		}catch(Exception e){
			log.error("Erro ao recuperar lista de plano de servico EBT", e);
		}
		
		Map<Long,List<DynamicBean>>  maplstMensNotaFiscal = listarBLTO( (ResultSetBeanCollection) list.get(10));		
		
		// Contem as cobrancas do parceiro que nao possuem notas fiscais, necessarias para a inclusao na pp_vlr_parceiro_fatura
		Map<Long,List<DynamicBean>>  mapDadosCobrancaParceiroSemNF = listarBoletos( (ResultSetBeanCollection) list.get(7));

		
		BigDecimal isolaLote = BigDecimal.ZERO;
		try {
			String cidadeOperadora = boletos.get(0).getCidContrato();				
            isolaLote = obterParametroObrigatorioNumerico(cidadeOperadora, REMOVE_BOL_LOTE_SEG_VIA);
		} catch (EmissaoBusinessResourceException e){
            log.info("Parametro de configuracao que isola boletos de segunda via com erros em um novo lote não foi encontrado. Configurando valor Default (desligado)."); 
		}   
		
		for (LoteBoletoFaturaDTO boleto : boletos){


			faturaEmbratelDTO = new FaturaNetDTO();
			log.info("[INICIO] buscarDadosFaturaEBT: " + boleto.getIdBoleto());
			Long idBoleto = Long.valueOf(boleto.getIdBoleto());

				faturaEmbratelDTO.setIdBoleto( boleto.getIdBoleto());
				if(boleto.getIdLote() != null){
					faturaEmbratelDTO.setIdLote(boleto.getIdLote());
				}

				faturaEmbratelDTO.setCidContrato(boleto.getCidContrato());

				faturaEmbratelDTO.setFcBoletoConsolidado(boleto.getFcBoletoConsolidado());
				if(IdCriterio != null && !(IdCriterio.equals("")) ){
					faturaEmbratelDTO.setIdCriterio(Integer.parseInt(IdCriterio));
				}
				List<DadosGeraisPrimeiraViaPrintDTO> dadosGerais = new ArrayList<DadosGeraisPrimeiraViaPrintDTO>();

				if ( ((List)mapNFBoleto.get(Long.parseLong(String.valueOf(boleto.getIdBoleto())))) != null ) {
						dadosGerais = selecionarClienteNotaFiscalParceiro(boleto, faturaEmbratelDTO, (List)mapNFBoleto.get(Long.parseLong(String.valueOf(boleto.getIdBoleto()))));
				}


				if (dadosGerais.isEmpty()){

					BatchParameter[] ps_no_nf = new BatchParameter[] { new BatchParameter(idBoleto, Types.NUMERIC), // p_ID_BOLETO
							new BatchParameter(Types.VARCHAR, true), // p_COD_RETORNO
							new BatchParameter(BatchParameter.ORACLE_CURSOR, true)}; // p_SEL_DADOS_1_VIA_SEM_NF

						 	List list_no_nf = executeBatch(PRSMS_SEL_CLI_NF_PAR_SEM_NF, ps_no_nf);

					dadosGerais = selecionarClienteNotaFiscalParceiro(boleto,faturaEmbratelDTO, (ResultSetBeanCollection) list_no_nf.get(2));
					try{
						if ((isolaLote.compareTo(BigDecimal.ONE) == 0) &&
							 CollectionUtils.isEmpty(dadosGerais) && 
							(tipoFatura != null && tipoFatura.equalsIgnoreCase("S"))){
							log.info(new BasicAttachmentMessage("Boleto "+idBoleto+" nao retornou dados de NF Parceiros." , AttachmentMessageLevel.WARNING));
							continue;
						}
						if(!dadosGerais.isEmpty()){
						dadosGerais.get(0).setIsOperadoraNet("N");
						}
						
					}catch(Exception ex){
						log.info(new BasicAttachmentMessage("Boleto "+idBoleto+" nao retornou dados de NF ." , AttachmentMessageLevel.ERROR));
						throw ex;
					}

					faturaEmbratelDTO.setPossuiNF(Boolean.FALSE);
					faturaEmbratelDTO.setOperadoraNet(Boolean.FALSE);
				}else{
					if(!dadosGerais.isEmpty()){
					faturaEmbratelDTO.setOperadoraNet(dadosGerais.get(0).isOperadoraNet());
					}
					faturaEmbratelDTO.setPossuiNF(Boolean.TRUE);
				}

				faturaEmbratelDTO.setDadosGeraisNF(dadosGerais);
				if(!dadosGerais.isEmpty()){
				faturaEmbratelDTO.setDataVencimento(faturaEmbratelDTO.getDadosGeraisNF().get(0).getDataVencimentoBoleto());
				faturaEmbratelDTO.setOperadoraNet(faturaEmbratelDTO.getDadosGeraisNF().get(0).isOperadoraNet());
				faturaEmbratelDTO.setNumContrato(new Long(faturaEmbratelDTO.getDadosGeraisNF().get(0).getCodigoClienteNET()));
				}
				//faturaEmbratelDTO.setTituloNotaFiscaisParceiro(this.listarTituloNotaFiscalOperadoraParceiro((ResultSetBeanCollection) list.get(3)));
				if ( ((List)mapNFBoleto.get(Long.parseLong(String.valueOf(boleto.getIdBoleto())))) != null ) {
					faturaEmbratelDTO.setDadosNotaFiscalParceiro(this.listarTituloNotaFiscalOperadoraPorParceiro((List)mapNFBoleto.get(Long.parseLong(String.valueOf(boleto.getIdBoleto()))),faturaEmbratelDTO));
					faturaEmbratelDTO.setDadosClienteParceiro(this.selecionarClienteNotaFiscalBoletoParceiro((List)mapNFBoleto.get(Long.parseLong(String.valueOf(boleto.getIdBoleto()))),faturaEmbratelDTO));
				}
				faturaEmbratelDTO.setItensNotaFiscalParceiro(this.listarDetalhamentoNotaFiscalParceiro((List)mapDetNFBoleto.get(Long.parseLong(String.valueOf(boleto.getIdBoleto())))));
				faturaEmbratelDTO.setTributosNotaFiscalParceiro(this.listarTributoParceiro((List)mapTribNFBoleto.get(Long.parseLong(String.valueOf(boleto.getIdBoleto())))));
				faturaEmbratelDTO.setTotalTributosNotaFiscalParceiro(this.listarTributoParceiroTotal((List)mapTribNFBoleto.get(Long.parseLong(String.valueOf(boleto.getIdBoleto())))));
				faturaEmbratelDTO.setDemostrativoFinanceiroParceiro(this.listarDemonstrativoFinanceiroParceiro((List)mapDemostrativoBoleto.get(Long.parseLong(String.valueOf(boleto.getIdBoleto())))));
				//faturaEmbratelDTO.setFustFuntel(this.selecionaFustFuntel((ResultSetBeanCollection) list.get(8)));
				faturaEmbratelDTO.setLigacoes(this.listarDetalhamentoLigacaoParceiro((List)mapLigacoesBoleto.get(Long.parseLong(String.valueOf(boleto.getIdBoleto())))));
				faturaEmbratelDTO.setMinhaNet(listarMinhaNet((List)mapLstMinhaNet.get(Long.parseLong(String.valueOf(boleto.getIdBoleto())))));
				// Atribui a lista das cobrancas parceiro do boleto que nao possuem nota fiscal
				faturaEmbratelDTO.setDadosCobrancaParceiroSemNF(
						this.listarDadosCobrancaParceiroSemNF((List)mapDadosCobrancaParceiroSemNF.get(boleto.getIdBoleto().longValue())));
				
		//		faturaEmbratelDTO.setMensagensNotaFiscal(listarMensagemNotaFiscal(
		//				((List)maplstMensNotaFiscal.get(boleto.getIdBoleto()))
		//				));		
                
				try{
					if(isArquivo){
						faturaEmbratelDTO.setPlanoServicoEbt((List<String>)lstPlanoServicoEbt.get(Long.parseLong(String.valueOf(boleto.getIdBoleto()))));
					}else{
						faturaEmbratelDTO.setListMsgPlanoServicoEbt(this.listarMsgPlanoServicoEbt(((List)mapLigacoesBoleto.get(Long.parseLong(String.valueOf(boleto.getIdBoleto()))))));
					}
				}catch(Exception e){
					log.error("Erro ao adicionar lista de plano de servico EBT", e);
				}
				
                // Mensagem dos totais dos impostos
				if (isArquivo) {
					faturaEmbratelDTO.setMsgTotaisImpostosParceiro(buscarTotalImpostosEbt(idBoleto, "").getDsMensagem());
				}
				
				log.info("[FIM] buscarDadosFaturaEBT: " + faturaEmbratelDTO.getIdBoleto());
				faturas.add(faturaEmbratelDTO);
		}

		return faturas;

	}

	/**
	 * Listar mensagens de plano de serviço ebt
	 * @param resultSetBeanCollection
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private  Map<Long,List<String>> listarPlanoServicoEbt(
			ResultSetBeanCollection resultSetBeanCollection) {
		
		Map<Long,List<String>> map = new HashMap<Long,List<String>>();
		
		List<String> list = new ArrayList<String>();

		DynamicBean b = null;
		String plano = null;
		Long id = null;
		
		for (Iterator i = resultSetBeanCollection.iterator(); i.hasNext();) {

			b = (DynamicBean) i.next();
			id = toLong(b.get("ID_BOLETO"));
			plano = formatarMsgPlanoServicoEbt(b);

			if(plano!=null){
				if(!map.containsKey(id)){
					map.put(id, new ArrayList<String>());
					map.get(id).add(plano);
				}else if(!map.get(id).contains(plano)){
					map.get(id).add(plano);
				}
			}
		}

		return map;
		
	}

	/**
	 * Formatar mensagem plano serviço ebt
	 * @param b
	 * @return
	 */
	private String formatarMsgPlanoServicoEbt(DynamicBean b) {

		StringBuffer plano = new StringBuffer();
		
		if( ( b.get("DS_PLANO_COMERCIAL")!=null &&  !b.get("DS_PLANO_COMERCIAL").toString().trim().equals("")  ) 
				&& ( b.get("CC_PAS_LOCAL") !=null &&  !b.get("CC_PAS_LOCAL").toString().trim().equals("") ) 
				&& ( b.get("CC_PAS_LDN") != null &&  !b.get("CC_PAS_LDN").toString().trim().equals("") ) 
				&& ( b.get("CC_PAS_LDI") != null &&  !b.get("CC_PAS_LDI").toString().trim().equals("") ) ){
		
			plano.append(b.get("DS_PLANO_COMERCIAL").toString().trim());
			plano.append(";");
			plano.append(b.get("CC_PAS_LOCAL").toString().trim());
			plano.append(";");
			plano.append(b.get("CC_PAS_LDN").toString().trim());
			plano.append(";");
			plano.append(b.get("CC_PAS_LDI").toString().trim());
			
		}
		
		if(plano.toString().equals("")){
			return null;
		}else{
			return plano.toString();
		}
	}

	public FaturaNetDTO buscarDadosSegundaViaFaturaEbt(Long idBoleto, String tipo ) {
		log.info("[INICIO] buscarDadosFaturaNet: " + idBoleto);

		BatchParameter[] ps = getParametroPRSMS_LST_DEMONS_FINAN_PAR(idBoleto);

		FaturaNetDTO faturaEmbratelDTO = new FaturaNetDTO();

		log.info("[INICIO] buscarDadosFaturaNet: " + idBoleto);

		faturaEmbratelDTO.setOperadoraNet(Boolean.FALSE);
		faturaEmbratelDTO.setPossuiNF(Boolean.TRUE);

		List listParc = executeBatch(PRSMS_LST_DEMONS_FINAN_PAR, ps);

		if(listParc != null && listParc.size() > 0){
			faturaEmbratelDTO.setDemostrativoFinanceiroParceiro(listarDemonstrativoFinanceiroParceiro((ResultSetBeanCollection) listParc.get(2)));
			faturaEmbratelDTO.setMinhaNet(listarMinhaNet((ResultSetBeanCollection) listParc.get(3)));
		}

		log.info("[FIM] buscarDadosFaturaNet: " + faturaEmbratelDTO.getIdBoleto());
		return faturaEmbratelDTO;

	}
	
	private Integer parseAddressNumber(String s) {
		return s != null && s.length() > 0 ? Integer.parseInt(s.replaceAll("[^0-9]", "")) : null;
	}

	private List<DadosGeraisPrimeiraViaPrintDTO> listarDadosPrimeiraVia( SnLoteBean loteCorrente, List bean, List beanMens, String tipo, boolean isArquivo) {
		List<DadosGeraisPrimeiraViaPrintDTO> list = new ArrayList<DadosGeraisPrimeiraViaPrintDTO>(bean.size());

		DynamicBean dyn = null;
		DynamicBean dynMens = null;
		DadosGeraisPrimeiraViaPrintDTO dpv = null;
		Long idBoletoQuitacaoDebitoAnual = null;
		Long idBoletoMigracaoPadPol = null;

		for (Iterator i = bean.iterator(); i.hasNext();) {
			dyn = (DynamicBean) i.next();
			dpv = new DadosGeraisPrimeiraViaPrintDTO();
			
			//QRCODE PIX
			log.info("O valor é obtido QRCODE: "+dyn.get("PIXHASHCODE"));
			String vpixhashccode =  (String) dyn.get("PIXHASHCODE");
			if (vpixhashccode==null || vpixhashccode.equals(""))
			{
				dpv.setPixHashcode("");
			}else
			{
				dpv.setPixHashcode(vpixhashccode);
			}

			dpv.setBairroCobranca((String) dyn.get("BAIRROCOBRANCA"));
			dpv.setBairroNotaFiscal((String) dyn.get("BAIRRONOTAFISCAL"));
			dpv.setCepCobranca(String.valueOf(dyn.get("CEPCOBRANCA")));
			dpv.setCepNotaFiscal(String.valueOf(dyn.get("CEPNOTAFISCAL")));
			dpv.setCfopNotaFiscal((String) dyn.get("CFOPNOTAFISCAL"));
			dpv.setCidadeCobranca((String) dyn.get("CIDADECOBRANCA"));
			dpv.setCidadeNotaFiscal(String.valueOf(dyn.get("CIDADENOTAFISCAL")));
			dpv.setCodigoClienteEBT(toStringFromBigDecimal(dyn.get("CODIGOCLIENTEEBT")));

			String codigoBanco =  (String) dyn.get("ID_BANCO");
			if (codigoBanco==null || codigoBanco.equals(""))
			{
				dpv.setCodigoBanco("");
			}else
			{
				dpv.setCodigoBanco(codigoBanco);
			}
			dpv.setCodigoClienteNET(toStringFromBigDecimal(dyn.get("CODIGOCLIENTENET")));
			dpv.setCodigoDeBarras((String) dyn.get("CODIGODEBARRAS"));
			dpv.setCodigoLote(null);
			dpv.setCodOperadora((String) dyn.get("CODOPERADORA"));
			dpv.setCpfCnpjNotaFiscal((String) dyn.get("CPFCNPJNOTAFISCAL"));
			dpv.setDataEmissaoNotaFiscal((Date) dyn.get("DATAEMISSAONOTAFISCAL"));
			dpv.setDataVencimentoBoleto((Date) dyn.get("DATAVENCIMENTOBOLETO"));
			dpv.setDescServico(null);
			dpv.setEnderecoCobranca((String) dyn.get("ENDERECOCOBRANCA"));
			dpv.setEnderecoNotaFiscal((String) dyn.get("ENDERECONOTAFISCAL"));
			dpv.setEstadoCobranca((String) dyn.get("ESTADOCOBRANCA"));
			dpv.setEstadoNotaFiscal((String) dyn.get("ESTADONOTAFISCAL"));
			dpv.setFc_boleto_consolidado((String) dyn.get("FC_BOLETO_CONSOLIDADO"));
			dpv.setHashCode((String) dyn.get("HASHCODE"));
			dpv.setIdCobrancaNotaFiscal(toLong(dyn.get("ID_COBRANCA_NOTA_FISCAL")));
			dpv.setIdentificadorBoleto(toLong(dyn.get("IDENTIFICADORBOLETO")));
			dpv.setIdentificadorBoletoOrigem(toLong(dyn.get("IDENTIFICADORBOLETOORIGEM")));
			//dpv.setIdNotaFiscal(null);
			dpv.setInscricaoEstadualNotaFiscal((String) dyn.get("INSCRICAOESTADUALNOTAFISCAL"));
			dpv.setIsOperadoraNet((String) dyn.get("OPERADORANET"));
			dpv.setLinhaDigitavel((String) dyn.get("LINHADIGITAVEL"));
			dpv.setMesAnoReferencia((String) dyn.get("MESANOREFERENCIA"));
			dpv.setModeloNotaFiscal((String) dyn.get("MODELONOTAFISCAL"));
			dpv.setNomeClienteCobranca((String) dyn.get("NOMECLIENTECOBRANCA"));
			dpv.setNomeClienteNotaFiscal((String) dyn.get("NOMECLIENTENOTAFISCAL"));
			dpv.setNumContrato((String) dyn.get("codigoClienteNET"));
			dpv.setNumeroNotaFiscal(String.valueOf(dyn.get("NUMERONOTAFISCAL")));
			dpv.setPrefixo((String) dyn.get("PREFIXO"));
			dpv.setSerieNotaFiscal((String) dyn.get("SERIENOTAFISCAL"));
			dpv.setValorCobrado(toDouble(dyn.get("VALORCOBRADO")));
			dpv.setValorTotalNotaFiscal(toDouble(dyn.get("VALORTOTALNOTAFISCAL")));
			dpv.setIdCobranca(toLong(dyn.get("ID_COBRANCA")));
			dpv.setValorCobranca(toDouble(dyn.get("VLR_COBRANCA")));
			dpv.setFormPagto((String) dyn.get("FORMPAGTO"));
			
			dpv.setLogradouroCobranca((String) (dyn.get("LOGRADOUROCOBRANCA") == null ? dyn.get("ENDERFATURA") : dyn.get("LOGRADOUROCOBRANCA")));
			dpv.setNumeroCobranca(parseAddressNumber((String) (dyn.get("NUMEROCOBRANCA") == null ? dyn.get("ENDERNRFATURA") : dyn.get("NUMEROCOBRANCA"))));
			dpv.setComplementoCobranca((String) (dyn.get("COMPLEMENTOCOBRANCA") == null ? dyn.get("ENDERCOMPLFATURA") : dyn.get("COMPLEMENTOCOBRANCA")));

			//Faturas por e-mail
			dpv.setBase(super.dbService.toString().split("_")[2]);
			dpv.setEmailDestino(dyn.get("E_MAIL") == null?"":(String) dyn.get("E_MAIL"));
			
			
			
			
			//NSMSP_172250_NI_003_CI_001\JAVA ok
			MensagemClaroClubeDTO mensagemClaroClubeDTO = buscarMensagemClaroClube(toLong(dyn.get("ID_BOLETO")) == null ? toLong(dyn.get("IDENTIFICADORBOLETO")) : toLong(dyn.get("ID_BOLETO")));
	
			if(mensagemClaroClubeDTO!=null && mensagemClaroClubeDTO.getIsDemandaLigada() && mensagemClaroClubeDTO.getDescMensagemFormatada()!=null ){
				mensagemClaroClubeDTO.setDescMensagemFormatada(br.com.netservicos.novosms.emissao.util.StringUtils
						.formataMsgClaroClubeArquivo(mensagemClaroClubeDTO.getDescMensagemFormatada()));
				dpv.setMensagemClaroClubeDTO(mensagemClaroClubeDTO);
			}
			
			//NSMSP_172250_NI_003\JAVA
			Integer formaEnvioFatura = buscarTipoEnvioFaturaPorLote(toString(dyn.get("CID_CONTRATO")),  loteCorrente, toInteger(dyn.get("ID_FORMA_ENVIO_FATURA")));	
			dpv.setFormaEnvioFatura(formaEnvioFatura);
			dpv.setTipoCobranca(toInteger(dyn.get("ID_TIPO_COBRANCA")));
			dpv.setCidContrato(toString(dyn.get("CID_CONTRATO")));
			dpv.setLinkNaoOptante(toString(getVlrSnParam(toString(dyn.get("CID_CONTRATO")), PrintHouseConstants.Parametro.LINK_NAO_OPTANTE, false))
					+ "?operadora=" + dyn.get("CODOPERADORA") + "&contrato=" + dyn.get("NUM_CONTRATO") + "&email=" + (dyn.get("E_MAIL") == null?"":(String) dyn.get("E_MAIL")));
			dpv.setEmailOrigem(toString(getVlrSnParam(toString(dyn.get("CID_CONTRATO")), PrintHouseConstants.Parametro.EMAIL_ORIGEM, false)));
			
			Calendar cal = GregorianCalendar.getInstance(new Locale("pt_BR"));
			cal.setTime((Date)dyn.get("DATAVENCIMENTOBOLETO"));
			
			int ano = cal.get(Calendar.YEAR);
			String anoString = String.valueOf(ano);
			dpv.setMesVencimento(PrintHouseConstants.MesContants.getMesContantsByChave(cal.get(Calendar.MONTH)).getValor() + "/" + anoString.substring(2,4));
			//Faturas por e-mail

			//Informacao Declaracao Anual de Debito
			try{
				idBoletoQuitacaoDebitoAnual = toLong(dyn.get("ID_BOLETO")) == null ? toLong(dyn.get("IDENTIFICADORBOLETO")) : toLong(dyn.get("ID_BOLETO")); 					

				if(idBoletoQuitacaoDebitoAnual != null && tipo.equals("P")){

					dataInicioQuitacaoDebito = dataInicioQuitacaoDebito == false ? validaInicioEnvioMsgQdeb(toString(getVlrSnParam(toString(dyn.get("CID_CONTRATO")), PrintHouseConstants.Parametro.QDEB_PERM_QUITACAO, false))):dataInicioQuitacaoDebito;

					if(dataInicioQuitacaoDebito){
						List retMsgDeclaracaoDebitoAnual = this.buscaDebitosAnual(idBoletoQuitacaoDebitoAnual);

						if(!retMsgDeclaracaoDebitoAnual.isEmpty()){
							dpv.setDeclaracaoAnualDebitoNET(retMsgDeclaracaoDebitoAnual.get(0).toString());
							dpv.setDeclaracaoAnualDebitoEBT(retMsgDeclaracaoDebitoAnual.get(1).toString());					
							dpv.setIdentificadorMensagem(retMsgDeclaracaoDebitoAnual.get(2).toString());	
						}											
					}
				}
			}catch(Exception e){
				log.info(new BasicAttachmentMessage("ERRO AO BUSCAR MSG QUITACAO DEBITO ANUAL METODO --> " + "listarDadosPrimeiraVia" + " ID_BOLETO: " + idBoletoQuitacaoDebitoAnual, AttachmentMessageLevel.INFO));
			}		
			
			// Migração Política
			try {
				dpv.setMigracaoPadPol("");
				idBoletoMigracaoPadPol = toLong(dyn.get("ID_BOLETO")) == null ? toLong(dyn.get("IDENTIFICADORBOLETO")) : toLong(dyn.get("ID_BOLETO")); 					
				if (this.verificaMigracaoContratoByIdBoleto(idBoletoMigracaoPadPol)) {
					dpv.setMigracaoPadPol("1");
				}
			} catch (Exception e) {
				String message = "ERRO AO BUSCAR MSG MIGRACAO --> ID_BOLETO: " + idBoletoMigracaoPadPol;
				log.error(message, e);
				log.info(new BasicAttachmentMessage(message, AttachmentMessageLevel.INFO));
			}
			
			//Dados do Emissor da Nota fiscal
			dpv.setNomeEmissorFiscal((String) dyn.get(NOME_EMISSOR_FISCAL));
			dpv.setEnderecoEmissorFiscal((String) dyn.get(ENDERECO_EMISSOR_FISCAL));
			dpv.setBairroEmissorFiscal((String) dyn.get(BAIRRO_EMISSOR_FISCAL));
			dpv.setCidadeEmissorFiscal((String) dyn.get(CIDADE_EMISSOR_FISCAL));
			dpv.setEstadoEmissorFiscal((String) dyn.get(ESTADO_EMISSOR_FISCAL));
			dpv.setCepEmissorFiscal((String) dyn.get(CEP_EMISSOR_FISCAL));
			dpv.setCnpjEmissorFiscal((String) dyn.get(CNPJ_EMISSOR_FISCAL));
			if (isArquivo) {
			dpv.setIeEmissorFiscal((String) dyn.get(IE_EMISSOR_FISCAL));
			} else {
				dpv.setIeEmissorFiscal((String) dyn.get(IE_EMISSOR_FISCAL_F));
			}
			dpv.setImEmissorFiscal((String) dyn.get(IM_EMISSOR_FISCAL));
			
			if (beanMens != null) {
				for (Iterator j = beanMens.iterator(); j.hasNext();) {
					dynMens = (DynamicBean) j.next();
					
					if (Long.valueOf(dynMens.getBeanProperty("ID_COBRANCA")).equals(dpv.getIdCobranca())){
						MensagensNotaFiscalDTO msgDTO = new MensagensNotaFiscalDTO();
						
						msgDTO.setIdCobranca(Long.valueOf(dynMens.getBeanProperty("ID_COBRANCA")));
						msgDTO.setCodigoOperadoraMensagemNotaFiscal(dynMens.getBeanProperty("CID_CONTRATO"));
						msgDTO.setPrefixoMensagemNotaFiscal(dynMens.getBeanProperty("PREFIXO"));
						msgDTO.setSetorizacaoMensagemNotaFiscal(dynMens.getBeanProperty("CC_CODIGO_SETORIZACAO"));
						msgDTO.setMensagemNotaFiscal(dynMens.getBeanProperty("DS_MENSAGEM"));
						
						dpv.getMensagensNotaFiscal().add(msgDTO);
						j.remove();
					}						
				}
			}
			String msgTotalImpostosNET =  (String) dyn.get(MSG_TOT_IMPOSTOS_NET);
			if (msgTotalImpostosNET==null || msgTotalImpostosNET.equals(""))
			{
				dpv.setMsgTotaisTributosNET("");
			}else
			{
				dpv.setMsgTotaisTributosNET(msgTotalImpostosNET);
			}
		
			list.add(dpv);
		}

		return list;
	}

	private DadosGeraisPrimeiraViaPrintDTO listarDadosBoletoAvulso(DynamicBean bean) {
		DadosGeraisPrimeiraViaPrintDTO result = new DadosGeraisPrimeiraViaPrintDTO();
		
		//REGISTRO 02
		result.setCodOperadora(bean.getBeanProperty("CODOPERADORAEMISSORFISCAL"));
		result.setNomeEmissorFiscal(bean.getBeanProperty(NOME_EMISSOR_FISCAL));
		result.setEnderecoEmissorFiscal(bean.getBeanProperty(ENDERECO_EMISSOR_FISCAL));
		result.setBairroEmissorFiscal(bean.getBeanProperty(BAIRRO_EMISSOR_FISCAL));
		result.setCidadeEmissorFiscal(bean.getBeanProperty(CIDADE_EMISSOR_FISCAL));
		result.setEstadoEmissorFiscal(bean.getBeanProperty(ESTADO_EMISSOR_FISCAL));
		result.setCepEmissorFiscal(bean.getBeanProperty(CEP_EMISSOR_FISCAL));
		result.setCnpjEmissorFiscal(bean.getBeanProperty(CNPJ_EMISSOR_FISCAL));
		result.setIeEmissorFiscal(bean.getBeanProperty(IE_EMISSOR_FISCAL));
		result.setImEmissorFiscal(bean.getBeanProperty(IM_EMISSOR_FISCAL));
		

		String endereco = "";
		
		endereco = montaEndereco(bean, endereco, bean.getBeanProperty("LOGRADOUROCLIENTE"));
		endereco = montaEndereco(bean, endereco, bean.getBeanProperty("NUMEROCLIENTE"));
		endereco = montaEndereco(bean, endereco, bean.getBeanProperty("COMPLEMENTOSCLIENTE"));
		
		String cpfCnpj = 
				bean.getBeanProperty("CPF") != null ? bean.getBeanProperty("CPF") : bean.getBeanProperty("CNPJ");
				
		//REGISTRO 05
		result.setCodigoLote(null);
		result.setPrefixo(bean.getBeanProperty("PREFIXO"));
		result.setNomeClienteNotaFiscal(bean.getBeanProperty("NOME"));
		result.setEnderecoNotaFiscal(endereco);
		result.setBairroNotaFiscal(bean.getBeanProperty("BAIRROCLIENTE"));
		result.setCidadeNotaFiscal(bean.getBeanProperty("CIDADECLIENTE"));
		result.setEstadoNotaFiscal(bean.getBeanProperty("ESTADOCLIENTE"));
		result.setCepNotaFiscal(bean.getBeanProperty("CEPCLIENTE"));
		result.setIdBoleto(Long.valueOf(bean.getBeanProperty("ID_COBRANCA_AVULSA_BOLETO")));
		result.setIdentificadorBoleto(Long.valueOf(bean.getBeanProperty("ID_COBRANCA_AVULSA_BOLETO")));
		result.setCodigoClienteNET(bean.getBeanProperty("NUM_CONTRATO"));
		result.setCidContrato(bean.getBeanProperty("CID_CONTRATO"));
		result.setCodigoClienteEBT(bean.getBeanProperty("NUM_CONTRATO_EBT"));
		
		result.setLogradouroCobranca(bean.getBeanProperty("LOGRADOUROCLIENTESEGRE"));
		result.setNumeroCobranca(parseAddressNumber(bean.getBeanProperty("NUMEROCLIENTESEGRE")));
		result.setComplementoCobranca(bean.getBeanProperty("COMPLEMENTOSCLIENTESEGRE"));
		
		//NSMSP_161258_NI_001\JAVA
		MensagemClaroClubeDTO mensagemClaroClubeDTO = buscarMensagemClaroClube(result.getIdBoleto());	
	
		if(mensagemClaroClubeDTO!=null && mensagemClaroClubeDTO.getIsDemandaLigada() && mensagemClaroClubeDTO.getDescMensagemFormatada()!=null ){
			mensagemClaroClubeDTO.setDescMensagemFormatada(br.com.netservicos.novosms.emissao.util.StringUtils
							.formataMsgClaroClubeArquivo(mensagemClaroClubeDTO.getDescMensagemFormatada()));
			result.setMensagemClaroClubeDTO(mensagemClaroClubeDTO);
		}
		
		result.setFormaEnvioFatura(2);
		result.setEmailDestino(bean.getBeanProperty("EMAIL_DESTINO")); 
		result.setMesAnoReferencia(bean.getBeanProperty("MESANOREFERENCIA"));
		
		Calendar cal = GregorianCalendar.getInstance(new Locale("pt_BR"));
		cal.setTime((Date) bean.get("DT_VENCTO"));
		
		int ano = cal.get(Calendar.YEAR);
		String anoString = String.valueOf(ano);
		result.setMesVencimento(getMesContantsByChave(cal.get(Calendar.MONTH)).getValor() + "/" + anoString);
		
		result.setIdTipoBoletoAvulso(toInteger(bean.get("ID_TIPO_COBRANCA_AVULSA")));
		result.setTipoBoletoAvulso(bean.getBeanProperty("DS_TIPO_COBRANCA_AVULSA"));
		
		//REGISTRO 08
		result.setEnderecoCobranca(endereco);
		result.setBairroCobranca(bean.getBeanProperty("BAIRROCLIENTE"));
		result.setCidadeCobranca(bean.getBeanProperty("CIDADECLIENTE"));
		result.setEstadoCobranca(bean.getBeanProperty("ESTADOCLIENTE"));
		result.setCepCobranca(bean.getBeanProperty("CEPCLIENTE"));
		result.setCpfCnpjNotaFiscal(cpfCnpj);
		result.setNomeClienteCobranca(bean.getBeanProperty("NOME"));
		result.setDataVencimentoBoleto((Date) bean.get("DT_VENCTO"));
		//result.setDataEmissaoNotaFiscal((Date) bean.get("DT_EMISSAO"));
		result.setDataEmissaoNotaFiscal(new Date());
		
		//REGISTRO 11
		result.setValorTotalNotaFiscal(toDouble(bean.get("VLR_COBRANCA")));
		
		//REGISTRO 12
		result.setCodigoDeBarras(bean.getBeanProperty("CODIGO_BARRA"));
		result.setLinhaDigitavel(bean.getBeanProperty("LINHA_DIGITAVEL"));
		result.setValorCobrado(toDouble(bean.get("VLR_COBRANCA")));
		result.setValorCobranca(toDouble(bean.get("VLR_COBRANCA")));
		result.setCodigoBanco(bean.getBeanProperty("ID_BANCO"));
		result.setNomeBanco(bean.getBeanProperty("NOME_BANCO"));
		result.setAgencia(bean.getBeanProperty("AGENCIA"));
		result.setPixHashcode(bean.getBeanProperty("CC_QRCODE_PIX"));
		
		return result;
	}

	private String montaEndereco(DynamicBean bean, String endereco, String value){
		if (value != null){
			if (StringUtils.isEmpty(endereco)){
				endereco += value;
			}
			else{
				endereco += ", " + value;
			}
		}
		
	    return endereco;
    }

	private List<DadosGeraisPrimeiraViaPrintDTO> listarDadosSegundaVia( SnLoteBean loteCorrente, List bean) {
		List<DadosGeraisPrimeiraViaPrintDTO> list = new ArrayList<DadosGeraisPrimeiraViaPrintDTO>(bean.size());

		DynamicBean dyn = null;
		DadosGeraisPrimeiraViaPrintDTO dpv = null;

		for (Iterator i = bean.iterator(); i.hasNext();) {
			dyn = (DynamicBean) i.next();
			dpv = new DadosGeraisPrimeiraViaPrintDTO();
			
			//QRCODE PIX
			log.info("O valor é obtido QRCODE: "+dyn.get("PIXHASHCODE"));
			String vpixhashccode =  (String) dyn.get("PIXHASHCODE");
			if (vpixhashccode==null || vpixhashccode.equals(""))
			{
				dpv.setPixHashcode("");
			}else
			{
				dpv.setPixHashcode(vpixhashccode);
			}

			dpv.setBairroCobranca((String) dyn.get("BAIRROCOBRANCA"));
			dpv.setBairroNotaFiscal((String) dyn.get("BAIRRONOTAFISCAL"));
			dpv.setCepCobranca(String.valueOf(dyn.get("CEPCOBRANCA")));
			dpv.setCepNotaFiscal(String.valueOf(dyn.get("CEPNOTAFISCAL")));
			dpv.setCfopNotaFiscal((String) dyn.get("CFOPNOTAFISCAL"));
			dpv.setCidadeCobranca((String) dyn.get("CIDADECOBRANCA"));
			dpv.setCidadeNotaFiscal(String.valueOf(dyn.get("CIDADENOTAFISCAL")));
			dpv.setCodigoClienteEBT(toStringFromBigDecimal(dyn.get("CODIGOCLIENTEEBT")));

			String codigoBanco =  (String) dyn.get("ID_BANCO");
			if (codigoBanco==null || codigoBanco.equals(""))
			{
				dpv.setCodigoBanco("");
			}else
			{
				dpv.setCodigoBanco(codigoBanco);
			}
			dpv.setCodigoClienteNET(toStringFromBigDecimal(dyn.get("CODIGOCLIENTENET")));
			dpv.setCodigoDeBarras((String) dyn.get("CODIGODEBARRAS"));
			dpv.setCodigoLote(null);
			dpv.setCodOperadora((String) dyn.get("CODOPERADORA"));
			dpv.setCpfCnpjNotaFiscal((String) dyn.get("CPFCNPJNOTAFISCAL"));
			dpv.setDataEmissaoNotaFiscal((Date) dyn.get("DATAEMISSAONOTAFISCAL"));
			dpv.setDataVencimentoBoleto((Date) dyn.get("DATAVENCIMENTOBOLETO"));
			dpv.setDescServico(null);
			dpv.setEnderecoCobranca((String) dyn.get("ENDERECOCOBRANCA"));
			dpv.setEnderecoNotaFiscal((String) dyn.get("ENDERECONOTAFISCAL"));
			dpv.setEstadoCobranca((String) dyn.get("ESTADOCOBRANCA"));
			dpv.setEstadoNotaFiscal((String) dyn.get("ESTADONOTAFISCAL"));
			dpv.setFc_boleto_consolidado((String) dyn.get("FC_BOLETO_CONSOLIDADO"));
			dpv.setHashCode((String) dyn.get("HASHCODE"));
			dpv.setIdCobrancaNotaFiscal(toLong(dyn.get("ID_COBRANCA_NOTA_FISCAL")));
			dpv.setIdentificadorBoleto(toLong(dyn.get("IDENTIFICADORBOLETO")));
			dpv.setIdentificadorBoletoOrigem(toLong(dyn.get("IDENTIFICADORBOLETOORIGEM")));
			//dpv.setIdNotaFiscal(null);
			dpv.setInscricaoEstadualNotaFiscal((String) dyn.get("INSCRICAOESTADUALNOTAFISCAL"));
			dpv.setIsOperadoraNet((String) dyn.get("OPERADORANET"));
			dpv.setLinhaDigitavel((String) dyn.get("LINHADIGITAVEL"));
			dpv.setMesAnoReferencia((String) dyn.get("MESANOREFERENCIA"));
			dpv.setModeloNotaFiscal((String) dyn.get("MODELONOTAFISCAL"));
			dpv.setNomeClienteCobranca((String) dyn.get("NOMECLIENTECOBRANCA"));
			dpv.setNomeClienteNotaFiscal((String) dyn.get("NOMECLIENTENOTAFISCAL"));
			dpv.setNumContrato((String) dyn.get("codigoClienteNET"));
			dpv.setNumeroNotaFiscal(String.valueOf(dyn.get("NUMERONOTAFISCAL")));
			dpv.setPrefixo((String) dyn.get("PREFIXO"));
			dpv.setSerieNotaFiscal((String) dyn.get("SERIENOTAFISCAL"));
			dpv.setValorCobrado(toDouble(dyn.get("VALORCOBRADO")));
			dpv.setValorTotalNotaFiscal(toDouble(dyn.get("VALORTOTALNOTAFISCAL")));
			dpv.setIdCobranca(toLong(dyn.get("ID_COBRANCA")));
			dpv.setValorCobranca(toDouble(dyn.get("VLR_COBRANCA")));
			dpv.setFormPagto((String) dyn.get("FORMPAGTO"));
			
			dpv.setLogradouroCobranca((String) (dyn.get("LOGRADOUROCOBRANCA") == null ? dyn.get("ENDERFATURA") : dyn.get("LOGRADOUROCOBRANCA")));
			dpv.setNumeroCobranca(parseAddressNumber((String) (dyn.get("NUMEROCOBRANCA") == null ? dyn.get("ENDERNRFATURA") : dyn.get("NUMEROCOBRANCA"))));
			dpv.setComplementoCobranca((String) (dyn.get("COMPLEMENTOCOBRANCA") == null ? dyn.get("ENDERCOMPLFATURA") : dyn.get("COMPLEMENTOCOBRANCA")));
			
			//Faturas por e-mail
			dpv.setBase("");
			dpv.setEmailDestino("");
			
			//NSMSP_172250_NI_003_CI_001\JAVA
			MensagemClaroClubeDTO mensagemClaroClubeDTO = buscarMensagemClaroClube(toLong(dyn.get("ID_BOLETO")) == null ? toLong(dyn.get("IDENTIFICADORBOLETO")) : toLong(dyn.get("ID_BOLETO")));
		
			if(mensagemClaroClubeDTO!=null && mensagemClaroClubeDTO.getIsDemandaLigada() && mensagemClaroClubeDTO.getDescMensagemFormatada()!=null ){
				mensagemClaroClubeDTO.setDescMensagemFormatada(br.com.netservicos.novosms.emissao.util.StringUtils
						.formataMsgClaroClubeArquivo(mensagemClaroClubeDTO.getDescMensagemFormatada()));
				dpv.setMensagemClaroClubeDTO(mensagemClaroClubeDTO);
			}
			
			//NSMSP_172250_NI_003		
			Integer formaEnvioFatura = buscarTipoEnvioFaturaPorLote(toString(dyn.get("CID_CONTRATO")), loteCorrente, PrintHouseConstants.FORMAENVIO.FORMA_ENVIO_CORREIO);	
			dpv.setFormaEnvioFatura(formaEnvioFatura);
			dpv.setTipoCobranca(0);
			dpv.setCidContrato("000000");
			dpv.setLinkNaoOptante("");
			dpv.setEmailOrigem("");
			dpv.setMesVencimento("");
			//Faturas por e-mail	

			//Informacao Declaracao Anual de Debito
			dpv.setDeclaracaoAnualDebitoNET("");
			dpv.setDeclaracaoAnualDebitoEBT("");
					
			// Migração Política
			dpv.setMigracaoPadPol("");

			list.add(dpv);
		}

		return list;
	}
	
	
	/**
	 * Método para consultar informações da SN_PARAMETRO
	 * @param cidContrato Código da Cidade	
	 * @param nomeParametro Nome do Parametro
	 * @param numero Boolean indicando se é para retornar o valor normal(BigDecimal) ou o valor STR(String). TRUE -> Numero ----- FALSE-> String 
	 * @return
	 */
	private Object getVlrSnParam(String cidContrato, String nomeParametro, Boolean numero){
		Object retorno = null;
		BatchParameter[] snParam = new BatchParameter[] { 
				new BatchParameter(nomeParametro, Types.VARCHAR, false), // pparametro								
				new BatchParameter(cidContrato, Types.VARCHAR, false), // pcidctr
				new BatchParameter(Types.FLOAT, true),//pvlrpar
				new BatchParameter(Types.VARCHAR, true)}; // pvlrstrpar
				
			 	List ret = executeBatch(LE_PARAMETRO, snParam);	
			 	
		if(numero)
			retorno = ret.get(2);
		else
			retorno = ret.get(3);
		
		return retorno;
	}

	/**
	 * Responsável por popular o DTO com as informações do demonstrativo financeiro recuperado da base de dados.
	 * @param bean lista com informacoes do demonstrativo
	 * @return lista de DemonstrativoFinanceiroDTO
	 */
	private List<DemonstrativoFinanceiroDTO> listarDemonstrativoFinanceiroGrupoSubGrupo(List bean) {

		List<DemonstrativoFinanceiroDTO> list = null;

		if(bean != null) {
			list = new ArrayList<DemonstrativoFinanceiroDTO>(bean.size());

			DynamicBean db = null;
			DemonstrativoFinanceiroDTO df = null;
			for (Iterator i = bean.iterator(); i.hasNext();) {
				db = (DynamicBean) i.next();

				String produto = (String) db.get(PRODUTO);
				String grupo = (String) db.get(GRUPO);
				String subGrupo = (String) db.get(SUBGRUPO);
				String subGrupoAlteProd = (String) db.get(SUBGRUPO_ALTER_PROD);
				Object valor = db.get(VALORITEMDEMONST_FINANC);
				Object tamanho = db.get(TAMANHO);

				//So adiciona se o grupo nao for vazio. Grupo vazio pode se referir ao atributo "FIDELIDADE"
				if (grupo != null && !"".equals(grupo.trim())) {
					df = new DemonstrativoFinanceiroDTO();
					//df.setDESCRICAOITEMDEMONST_FINANC((String) db.get("DESCRICAOITEMDEMONST_FINANC"));
					//df.setGRUPO_DEMONST_FINANC((String) db.get("GRUPO_DEMONST_FINANC"));
					df.setDESCRICAOITEMDEMONST_FINANC(produto);
					df.setGRUPO_DEMONST_FINANC(grupo);
					df.setSUBGRUPO_DEMONST_FINANC(subGrupo);
					//Sub Grupo Criado para contem a Unificação de Pró-ratas.
					if(subGrupoAlteProd != null) {
						df.setSubgrupoAlterProd(subGrupoAlteProd);
					}
					df.setVALORITEMDEMONST_FINANC(toDouble(valor));
					df.setTAMANHO(toInteger(tamanho));
					//df.setVALORTEMDEMONST_FINANC(toDouble(db.get("VALORBRUTOITEM")));
					list.add(df);
				}
			}
		}

		return list;

	}

	private List<DemonstrativoFinanceiroDTO> listarDemonstrativoFinanceiro(List bean) {

		List<DemonstrativoFinanceiroDTO> list = null;

		if(bean != null) {
			list = new ArrayList<DemonstrativoFinanceiroDTO>(bean.size());

			DynamicBean db = null;
			DemonstrativoFinanceiroDTO df = null;
			for (Iterator i = bean.iterator(); i.hasNext();) {
				db = (DynamicBean) i.next();
				df = new DemonstrativoFinanceiroDTO();

				df.setDESCRICAOITEMDEMONST_FINANC((String) db.get("DESCRICAOITEMDEMONST_FINANC"));
				df.setGRUPO_DEMONST_FINANC((String) db.get("GRUPO_DEMONST_FINANC"));
				df.setVALORITEMDEMONST_FINANC(toDouble(db.get("VALORITEMDEMONST_FINANC")));

				list.add(df);
			}
		}

		return list;

	}

	/**
	 * Gera Lista de DTOs do minha net, unindo os produtos da NET com o NET FONE do parceiro. Mas
	 * deixa itens eventuais por ultimo.
	 * @param bean
	 * @param beanParceiro
	 * @return
	 */
	private List<MinhaNetDTO> listarMinhaNet(List bean) {

		List<MinhaNetDTO> list = null;
		DynamicBean db = null;
		MinhaNetDTO df = null;

		if (bean != null) {
			list = new ArrayList<MinhaNetDTO>(bean.size());

			for (Iterator<DynamicBean> i = bean.iterator(); i.hasNext();) {
				db = i.next();

				String minhaNet = (String) db.get("MINHANET");
				String nometipogrupo = (String) db.get("GRUPO");
				Long idParceiro = null; 
				if(db.get("ID_PARCEIRO") != null){
					idParceiro = toLong(db.get("ID_PARCEIRO"));
				}

				if (minhaNet != null && !minhaNet.equals("")) {

					df = new MinhaNetDTO();
					df.setMINHANET((String) minhaNet);

					df.setTIPOGRUPO(getTipoFonteMinhaNet(minhaNet, nometipogrupo, idParceiro));
					list.add(df);
				}
			}
		}

		return list;

	}
	/**
	 * Gera Lista de mensagens de Nota Fiscal 
	 * @param bean
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
    private List<MensagensNotaFiscalDTO> listarMensagemNotaFiscal(List bean) {
		List<MensagensNotaFiscalDTO> list = null;
		DynamicBean db = null;
		MensagensNotaFiscalDTO msg = null;
		if (bean != null) {
			list = new ArrayList<MensagensNotaFiscalDTO>(bean.size());
			for (Iterator<DynamicBean> i = bean.iterator(); i.hasNext();) {
				db = i.next();
				Long idCobranca = toLong(db.get("ID_COBRANCA"));
				String cidContrato = (String) db.get("CID_CONTRATO");
				String dsMensagem = (String) db.get("DS_MENSAGEM");
				String prefixo = (String) db.get("PREFIXO");
				String codigoSetorizacao = (String) db.get("CC_CODIGO_SETORIZACAO");
				if (dsMensagem != null && !dsMensagem.equals("")) {
					msg = new MensagensNotaFiscalDTO();
					msg.setIdCobranca(idCobranca);
					msg.setCodigoOperadoraMensagemNotaFiscal(cidContrato);
					msg.setMensagemNotaFiscal(dsMensagem);
					msg.setPrefixoMensagemNotaFiscal(prefixo);
					msg.setSetorizacaoMensagemNotaFiscal(codigoSetorizacao);
					list.add(msg);
				}
			}
		}
		return list;
	}

	private Map<Long, List<DetalhamentoNotaFiscalDTO>> listarDetalhamentoNotaFiscal(List bean) {
		Map<Long, List<DetalhamentoNotaFiscalDTO>> map = new HashMap<Long, List<DetalhamentoNotaFiscalDTO>>();

		Long id = null;
		DynamicBean dyn = null;
		DetalhamentoNotaFiscalDTO dnf = null;

		if ( bean != null ){
			for (Iterator i = bean.iterator(); i.hasNext();) {
				dyn = (DynamicBean) i.next();
				dnf = new DetalhamentoNotaFiscalDTO();
				id = toLong(dyn.get("ID_COBRANCA_NOTA_FISCAL"));

				if (!map.containsKey(id))
					map.put(id, new ArrayList<DetalhamentoNotaFiscalDTO>());

				dnf.setDescricao((String) dyn.get("DESCRICAO"));
				dnf.setNmGrupamento((String) dyn.get("NOMEAGRUPAMENTO"));
				dnf.setVlBrutoItem(toDouble(dyn.get("VALORBRUTOITEM")));
				dnf.setVlSomaItemAgrupamento(toDouble(dyn.get("VALORSOMAITENSAGRUPAMENTO")));
				dnf.setVlCofinsItem(dyn.containsKey(COFINS) ? toDouble(dyn.get(COFINS)) : 0);
				dnf.setVlPisItem(dyn.containsKey(PIS) ? toDouble(dyn.get(PIS)) : 0);
				dnf.setVlIcmsItem(dyn.containsKey(ICMS) ? toDouble(dyn.get(ICMS)) : 0);
				dnf.setVlIssItem(dyn.containsKey(ISS) ? toDouble(dyn.get(ISS)) : 0);

				map.get(id).add(dnf);
			}
		}

		return map;
	}

	private Map<Long, List<TributoDTO>> listarTributo(List bean) {
		Map<Long, List<TributoDTO>> map = new HashMap<Long, List<TributoDTO>>();

		DynamicBean d = null;
		TributoDTO t = null;
		Long id = null;
		if ( bean != null ){
			int tamanho = bean.size();
			for (Iterator i = bean.iterator(); i.hasNext();) {
				d = (DynamicBean) i.next();
				Double aliquota = toDouble(d.get("VALORALIQUOTA"));

				if((aliquota == 0 && tamanho == 1) || ( tamanho >= 1 && aliquota > 0)){
					t = new TributoDTO();
					id = toLong(d.get("ID_COBRANCA_NOTA_FISCAL"));

					if (!map.containsKey(id))
						map.put(id, new ArrayList<TributoDTO>());

					t.setBaseCalculo(toDouble(d.get("BASECALCULO")));
					t.setSiglaAtributo((String) d.get("SIGLATRIBUTO"));
					t.setVlAliquota((BigDecimal) d.get("VALORALIQUOTA"));
					t.setVlAtributo(toDouble(d.get("VALORTRIBUTO")));

					map.get(id).add(t);
				}
			}
		}

		return map;
	}


	/**
	 * Popula DTO a partir do retorno da procedure.
	 * @param boleto 
	 * @param faturaEmbratelDTO 
	 *
	 * @since 09/10/2007
	 * @param ResultSetBeanCollection
	 * @return Lista populado com objetos DadosGeraisPrimeiraViaPrintDTO
	 */
	  private List<DadosGeraisPrimeiraViaPrintDTO> selecionarClienteNotaFiscalParceiro(LoteBoletoFaturaDTO loteBoletoFaturaDTO, FaturaNetDTO faturaEmbratelDTO, List bean) {

		List<DadosGeraisPrimeiraViaPrintDTO> list = new ArrayList<DadosGeraisPrimeiraViaPrintDTO>();

		DynamicBean dyna = null;

		for (Iterator i = bean.iterator(); i.hasNext();) {

			dyna = (DynamicBean) i.next();

			DadosGeraisPrimeiraViaPrintDTO dadosGerais = new DadosGeraisPrimeiraViaPrintDTO();
			
			//QRCODE PIX
			String vpixhashccode =  (String) dyna.get("PIXHASHCODE");
			if (vpixhashccode==null || vpixhashccode.equals(""))
			{
				dadosGerais.setPixHashcode("");
			}else
			{
				dadosGerais.setPixHashcode(vpixhashccode);
			}

			dadosGerais.setIdCobrancaNotaFiscal(toLong(dyna.get("ID_COBRANCA_NOTA_FISCAL")));
			dadosGerais.setEnderecoNotaFiscal((String)(dyna.get("ENDERECONOTAFISCAL")));
			dadosGerais.setBairroNotaFiscal((String) (dyna.get("BAIRRONOTAFISCAL")));
			dadosGerais.setCidadeNotaFiscal((String) (dyna.get("CIDADENOTAFISCAL")));
			dadosGerais.setEstadoNotaFiscal((String) (dyna.get("ESTADONOTAFISCAL")));
			dadosGerais.setCepNotaFiscal(toStringFromBigDecimal(dyna.get("CEPNOTAFISCAL")));
			dadosGerais.setNomeClienteNotaFiscal((String) (dyna.get("NOMECLIENTENOTAFISCAL")));
			dadosGerais.setCfopNotaFiscal((String) (dyna.get("CFOPNOTAFISCAL")));
			dadosGerais.setInscricaoEstadualNotaFiscal((String) (dyna.get("INSCRICAOESTADUALNOTAFISCAL")));
			dadosGerais.setCpfCnpjNotaFiscal((String) (dyna.get("CPFCNPJNOTAFISCAL")));
			dadosGerais.setSerieNotaFiscal((String) (dyna.get("SERIENOTAFISCAL")));
			dadosGerais.setMesAnoReferencia((String) (dyna.get("MESANOREFERENCIA")));
			dadosGerais.setNumeroNotaFiscal(toStringFromBigDecimal(dyna.get("NUMERONOTAFISCAL")));
			dadosGerais.setHashCode((String) (dyna.get("HASHCODE")));
			dadosGerais.setDataEmissaoNotaFiscal((Date)(dyna.get("DATAEMISSAONOTAFISCAL")));
			dadosGerais.setValorTotalNotaFiscal(toDouble(dyna.get("VALORTOTALNOTAFISCAL")));
			dadosGerais.setIdentificadorBoleto(toLong(dyna.get("IDENTIFICADORBOLETO")));
			dadosGerais.setIdentificadorBoletoOrigem(toLong(dyna.get("IDENTIFICADORBOLETOORIGEM")));
			dadosGerais.setDataVencimentoBoleto((Date)(dyna.get("DATAVENCIMENTOBOLETO")));
			dadosGerais.setCodigoDeBarras((String) (dyna.get("CODIGODEBARRAS")));
			dadosGerais.setLinhaDigitavel((String) (dyna.get("LINHADIGITAVEL")));
			dadosGerais.setValorCobrado(toDouble(dyna.get("VALORCOBRADO")));
			dadosGerais.setEnderecoCobranca((String) (dyna.get("ENDERECOCOBRANCA")));
			dadosGerais.setBairroCobranca((String) (dyna.get("BAIRROCOBRANCA")));
			dadosGerais.setCidadeCobranca((String)(dyna.get("CIDADECOBRANCA")));
			dadosGerais.setEstadoCobranca((String) (dyna.get("ESTADOCOBRANCA")));
		    dadosGerais.setCepCobranca(toStringFromBigDecimal(dyna.get("CEPCOBRANCA")));
			dadosGerais.setPrefixo((String) (dyna.get("PREFIXO")));
			dadosGerais.setNomeClienteCobranca((String) (dyna.get("NOMECLIENTECOBRANCA")));
			dadosGerais.setModeloNotaFiscal((String)((dyna.get("MODELONOTAFISCAL"))));
			dadosGerais.setCodigoClienteEBT(toStringFromBigDecimal(dyna.get("CODIGOCLIENTEEBT")));
			
			dadosGerais.setLogradouroCobranca((String) (dyna.get("LOGRADOUROCOBRANCA") == null ? dyna.get("ENDERFATURA") : dyna.get("LOGRADOUROCOBRANCA")));
			dadosGerais.setNumeroCobranca(parseAddressNumber((String) (dyna.get("NUMEROCOBRANCA") == null ? dyna.get("ENDERNRFATURA") : dyna.get("NUMEROCOBRANCA"))));
			dadosGerais.setComplementoCobranca((String) (dyna.get("COMPLEMENTOCOBRANCA") == null ? dyna.get("ENDERCOMPLFATURA") : dyna.get("COMPLEMENTOCOBRANCA")));

			String codigoBanco =  (String) dyna.get("ID_BANCO");
			if (codigoBanco==null || codigoBanco.equals(""))
			{
				dadosGerais.setCodigoBanco("");
			}else
			{
				dadosGerais.setCodigoBanco(codigoBanco);
			}

			dadosGerais.setFc_boleto_consolidado((String)(dyna.get("FC_BOLETO_CONSOLIDADO")));
			dadosGerais.setCodOperadora((String) (dyna.get("CODOPERADORA")));
			dadosGerais.setIsOperadoraNet((String) (dyna.get("OPERADORANET")));
			dadosGerais.setCodigoClienteNET(String.valueOf((toLong(dyna.get("CODIGOCLIENTENET")))));
			dadosGerais.setIdCobranca(toLong(dyna.get("IDCOBRANCAPARCEIRO")));

			//Faturas por e-mail
			dadosGerais.setBase(super.dbService.toString().split("_")[2]);
			dadosGerais.setEmailDestino(dyna.get("E_MAIL") == null?"":(String) dyna.get("E_MAIL"));

			//NSMSP_172250_NI_003_CI_001\JAVA
			 MensagemClaroClubeDTO mensagemClaroClubeDTO = buscarMensagemClaroClube(toLong(dyna.get("ID_BOLETO")) == null ? toLong(dyna.get("IDENTIFICADORBOLETO")) : toLong(dyna.get("ID_BOLETO")));
			
			if(mensagemClaroClubeDTO!=null && mensagemClaroClubeDTO.getIsDemandaLigada() && mensagemClaroClubeDTO.getDescMensagemFormatada()!=null ){
				mensagemClaroClubeDTO.setDescMensagemFormatada(br.com.netservicos.novosms.emissao.util.StringUtils
						.formataMsgClaroClubeArquivo(mensagemClaroClubeDTO.getDescMensagemFormatada()));
				dadosGerais.setMensagemClaroClubeDTO(mensagemClaroClubeDTO);
			}

			
			  
			  SnLoteBean loteCorrente = new SnLoteBean();
			  if(faturaEmbratelDTO.getIdLote()!= null){
				  loteCorrente.setIdLote(faturaEmbratelDTO.getIdLote());
				  SnTipoLoteBean snTipoLoteBean = new SnTipoLoteBean();
				  loteCorrente.setSnTipoLoteBean(snTipoLoteBean);
				  if(loteBoletoFaturaDTO!=null){
					  loteCorrente.getSnTipoLoteBean().setSgTipoLote(loteBoletoFaturaDTO.getSiglaLote());  
				  }
				 
			  }
			  
			  
			 
			Integer formaEnvioFatura = buscarTipoEnvioFaturaPorLote(toString(dyna.get("CIDCONTRATO")),  loteCorrente, toInteger(dyna.get("ID_FORMA_ENVIO_FATURA")));	
			dadosGerais.setFormaEnvioFatura(formaEnvioFatura);

			dadosGerais.setTipoCobranca(toInteger(dyna.get("ID_TIPO_COBRANCA")));
			dadosGerais.setCidContrato(toString(dyna.get("CIDCONTRATO")));
			dadosGerais.setLinkNaoOptante(toString(getVlrSnParam(toString(dyna.get("CIDCONTRATO")), PrintHouseConstants.Parametro.LINK_NAO_OPTANTE, false))
					+ "?operadora=" + dyna.get("CODOPERADORA") + "&contrato=" + dyna.get("NUM_CONTRATO") + "&email=" + (dyna.get("E_MAIL") == null?"":(String) dyna.get("E_MAIL")));
			dadosGerais.setEmailOrigem(toString(getVlrSnParam(toString(dyna.get("CIDCONTRATO")), PrintHouseConstants.Parametro.EMAIL_ORIGEM, false)));
			
			Calendar cal = GregorianCalendar.getInstance(new Locale("pt_BR"));
			cal.setTime((Date)dyna.get("DATAVENCIMENTOBOLETO"));
			
			int ano = cal.get(Calendar.YEAR);
			String anoString = String.valueOf(ano);
			dadosGerais.setMesVencimento(PrintHouseConstants.MesContants.getMesContantsByChave(cal.get(Calendar.MONTH)).getValor() + "/" + anoString.substring(2,4));
			//Faturas por e-mail
			
			
			//Informacao Declaracao Anual de Debito
			dadosGerais.setDeclaracaoAnualDebitoNET("");
			dadosGerais.setDeclaracaoAnualDebitoEBT("");
			
			// Migração Política
			dadosGerais.setMigracaoPadPol("");

			list.add(dadosGerais);
		}

		return list;
	}


	  /**
		 * Popula DTO a partir do retorno da procedure.
		 *
		 * @since 09/10/2007
		 * @param ResultSetBeanCollection
		 * @return Lista populado com objetos DadosGeraisPrimeiraViaPrintDTO
		 */
		  private List<DetalhamentoLigacaoParceiroDTO> listarDetalhamentoLigacaoParceiro(List bean) {

			List<DetalhamentoLigacaoParceiroDTO> list = new ArrayList<DetalhamentoLigacaoParceiroDTO>();

			DynamicBean dynaBean = null;
			if( bean != null ) {
				for (Iterator i = bean.iterator(); i.hasNext();) {

					dynaBean = (DynamicBean) i.next();

					DetalhamentoLigacaoParceiroDTO detLigParcDTO = new DetalhamentoLigacaoParceiroDTO();

	//				detLigParcDTO.setAcao((String)dynaBean.get(""));
					detLigParcDTO.setGrupoNota((String)dynaBean.get("GRUPONOTA"));
					detLigParcDTO.setGrupoItem((String)dynaBean.get("GRUPOITEM"));
					detLigParcDTO.setServico((String)dynaBean.get("SERVICO"));
					detLigParcDTO.setDescItem((String)dynaBean.get("DESCITEM"));
					detLigParcDTO.setSequencia(this.getLong(dynaBean.get("SEQUENCIA")));
					detLigParcDTO.setTelefoneOrigem((String)dynaBean.get("TELEFONEORIGEM"));
					detLigParcDTO.setLocalidadeOrigem((String)dynaBean.get("LOCALIDADEORIGEM"));
					detLigParcDTO.setTelefoneDestino((String)dynaBean.get("TELEFONEDESTINO"));
					detLigParcDTO.setLocalidadeDestino((String)dynaBean.get("LOCALIDADEDESTINO"));
					detLigParcDTO.setDataInicio(this.getDate(dynaBean.get("DATAINICIO")));
					detLigParcDTO.setHoraInicio((String)dynaBean.get("HORAINICIO"));
					detLigParcDTO.setDataFim(this.getDate(dynaBean.get("DATAFIM")));
					detLigParcDTO.setHoraFim((String)dynaBean.get("HORAFIM"));
					detLigParcDTO.setQuantidade(this.getDouble(dynaBean.get("QUANTIDADE")).longValue());
					detLigParcDTO.setUnidadeTempo((String)dynaBean.get("UNIDADETEMPO"));
					detLigParcDTO.setDuracao(toString(dynaBean.get("DURACAO")));
					detLigParcDTO.setValorItem(this.getDouble(dynaBean.get("VALORITEM")));
					detLigParcDTO.setDuracaoTotal(this.getDouble(dynaBean.get("VL_QTDE_DETALHE")));
					detLigParcDTO.setIdTipoItem(this.toInteger(dynaBean.get("ID_GRUPO_TIPO_ITEM_EXTR_PARC")));


					list.add(detLigParcDTO);
				}
			}
			return list;
		}

		  private List<String> listarMsgPlanoServicoEbt(List bean) {

				List<String> listMsgPlanoServicoEbt = new ArrayList<String>();

				try {
					
					DynamicBean dynaBean = null;
					
					if( bean != null ) {
						
						for (Iterator i = bean.iterator(); i.hasNext();) {
	
							dynaBean = (DynamicBean) i.next();
							
							String msg = formatarPDFMsgPlanoServicoEbt(dynaBean);
							
							if(msg!=null && !msg.equalsIgnoreCase("") && !listMsgPlanoServicoEbt.contains(msg)){
								
								listMsgPlanoServicoEbt.add(msg);
							}
						}
					}
					
				} catch (Exception e) {
					log.error(e.getMessage(),e);
				}
				
				return listMsgPlanoServicoEbt;
			}

		  
		  /**
			 * Formatar mensagem plano serviço ebt
			 * @param b
			 * @return
			 */
			private String formatarPDFMsgPlanoServicoEbt(DynamicBean b) {

				StringBuffer msg = new StringBuffer();
				
				if( ( b.get("DS_PLANO_COMERCIAL")!=null &&  !b.get("DS_PLANO_COMERCIAL").toString().trim().equals("")  ) 
						&& ( b.get("CC_PAS_LOCAL") !=null &&  !b.get("CC_PAS_LOCAL").toString().trim().equals("") ) 
						&& ( b.get("CC_PAS_LDN") != null &&  !b.get("CC_PAS_LDN").toString().trim().equals("") ) 
						&& ( b.get("CC_PAS_LDI") != null &&  !b.get("CC_PAS_LDI").toString().trim().equals("") ) ){
					
					msg.append(b.get("DS_PLANO_COMERCIAL") != null ? b.get("DS_PLANO_COMERCIAL").toString().trim() : "");
					msg.append(" - Ligações locais PAS ");
					msg.append(b.get("CC_PAS_LOCAL") != null ? b.get("CC_PAS_LOCAL").toString().trim() : "");
					msg.append(" LC – DDD PAS ");
					msg.append(b.get("CC_PAS_LDN") != null ? b.get("CC_PAS_LDN").toString().trim() : "");
					
					msg.append(" LD – DDI PAS ");
					msg.append(b.get("CC_PAS_LDI") != null ? b.get("CC_PAS_LDI").toString().trim() : "");
					msg.append(" LD");
					
				}
				
				
				return msg.toString();
			}

	/**
	 * Popula DTO a partir do retorno da procedure.
	 *
	 * @since 09/10/2007
	 * @param ResultSetBeanCollection
	 * @return Lista populado com objetos DadosGeraisPrimeiraViaPrintDTO
	 */
	private List<DadosCobrancaParceiroDTO> listarTituloNotaFiscalOperadoraParceiro(List bean) {

		List<DadosCobrancaParceiroDTO> list = new ArrayList<DadosCobrancaParceiroDTO>(bean.size());

		DynamicBean dyna = new DynamicBean();

		for (Iterator i = bean.iterator(); i.hasNext();) {
			dyna = (DynamicBean) i.next();

			DadosCobrancaParceiroDTO dadosCobranca = new DadosCobrancaParceiroDTO();

			dadosCobranca.setIdCobrancaParceiro(toLong(dyna.get("IDCOBRANCAPARCEIRO")));
			dadosCobranca.setCidContrato((String) (dyna.get("CIDCONTRATO")));
			dadosCobranca.setIdCobrancaNotaFiscal(toLong(dyna.get("IDCOBRANCANOTAFISCAL")));
			dadosCobranca.setOperadoraNet((String) (dyna.get("OPERADORANET")));
			dadosCobranca.setIdParceiro(toInteger(dyna.get("IDPARCEIRO")));
			dadosCobranca.setId_tipo_parceiro(toInteger(dyna.get("ID_TIPO_PARCEIRO")));
			dadosCobranca.setCc_razao_social_oper((String) (dyna.get("CC_RAZAO_SOCIAL_OPER")));
			dadosCobranca.setCc_logradouro_oper((String) (dyna.get("CC_LOGRADOURO_OPER")));
			dadosCobranca.setCc_bairro_oper((String) (dyna.get("CC_BAIRRO_OPER")));
			dadosCobranca.setCc_cidade_oper((String) (dyna.get("CC_CIDADE_OPER")));
			dadosCobranca.setCc_estado_oper((String) (dyna.get("CC_ESTADO_OPER")));
			dadosCobranca.setCc_cep_oper((String) (dyna.get("CC_CEP_OPER")));
			dadosCobranca.setCc_cnpj_oper((String) (dyna.get("CC_CNPJ_OPER")));
			dadosCobranca.setCc_inscricao_municipal_oper((String)(dyna.get("CC_INSCRICAO_MUNICIPAL_OPER")));
			dadosCobranca.setCc_inscricao_estadual_oper((String) (dyna.get("CC_INSCRICAO_ESTADUAL_OPER")));

			list.add(dadosCobranca);
		}

		return list;

	}

	private Map<Long, List<DadosCobrancaParceiroDTO>> listarTituloNotaFiscalOperadoraPorParceiro(List bean , FaturaNetDTO fatura) {
		Map<Long, List<DadosCobrancaParceiroDTO>> map = new HashMap<Long, List<DadosCobrancaParceiroDTO>>();

		DadosCobrancaParceiroDTO dadosCobrancaParceiroParceiro = null;

		DynamicBean dyna = new DynamicBean();
		List<Long> parceiros = new ArrayList<Long>();
		Double valor_fust = 0.0;
		Double valor_funtel = 0.0;
		for (Iterator i = bean.iterator(); i.hasNext();) {
			dyna = (DynamicBean) i.next();

			DadosCobrancaParceiroDTO dadosCobranca = new DadosCobrancaParceiroDTO();

			Long idParceiro = toLong(dyna.get("ID_PARCEIRO"));
			Integer  idTipoParceiro = toInteger(dyna.get("ID_TIPO_PARCEIRO"));

			if (!map.containsKey(idParceiro)){
				map.put(idParceiro, new ArrayList<DadosCobrancaParceiroDTO>());
				parceiros.add(idParceiro);
			}
			dadosCobranca.setId_tipo_parceiro(idTipoParceiro);
			dadosCobranca.setIdParceiro(toInteger(dyna.get("ID_PARCEIRO")));
			dadosCobranca.setIdCobrancaParceiro(toLong(dyna.get("IDCOBRANCAPARCEIRO")));
			dadosCobranca.setCidContrato((String) (dyna.get("CIDCONTRATO")));
			dadosCobranca.setIdCobrancaNotaFiscal(toLong(dyna.get("ID_COBRANCA_NOTA_FISCAL")));
			dadosCobranca.setOperadoraNet((String) (dyna.get("OPERADORANET")));
			dadosCobranca.setCc_razao_social_oper((String) (dyna.get("CC_RAZAO_SOCIAL_OPER")));
			dadosCobranca.setCc_logradouro_oper((String) (dyna.get("CC_LOGRADOURO_OPER")));
			dadosCobranca.setCc_bairro_oper(toString(dyna.get("CC_BAIRRO_OPER")));
			dadosCobranca.setCc_cidade_oper((String) (dyna.get("CC_CIDADE_OPER")));
			dadosCobranca.setCc_estado_oper((String) (dyna.get("CC_ESTADO_OPER")));
			dadosCobranca.setCc_cep_oper((String) (dyna.get("CC_CEP_OPER")));
			dadosCobranca.setCc_cnpj_oper((String) (dyna.get("CC_CNPJ_OPER")));
			dadosCobranca.setCc_inscricao_municipal_oper((String)(dyna.get("CC_INSCRICAO_MUNICIPAL_OPER")));
			dadosCobranca.setCc_inscricao_estadual_oper((String) (dyna.get("CC_INSCRICAO_ESTADUAL_OPER")));
			dadosCobranca.setIdCobrancaNotaFiscal(toLong(dyna.get("ID_COBRANCA_NOTA_FISCAL")));
			dadosCobranca.setValor_total(this.getDouble(dyna.get("VL_TOTAL")));
			dadosCobranca.setValorCobranca(this.getDouble(dyna.get("VLR_COBRANCA")));

			if(idTipoParceiro == 2 ) {

				valor_fust = valor_fust +  toDouble(dyna.get("VL_FUST"));
				valor_funtel = valor_funtel +  toDouble(dyna.get("VL_FUNTEL"));
				fatura.setDadosCabecalhoParceiro(dadosCobranca);


			}

			if(idTipoParceiro == 3 ) {
				dadosCobrancaParceiroParceiro = dadosCobranca;
			}

			map.get(idParceiro).add(dadosCobranca);

		}

		if (fatura.getDadosCabecalhoParceiro() == null) {
			fatura.setDadosCabecalhoParceiro(dadosCobrancaParceiroParceiro);
		}

		fatura.setIdParceiros(parceiros);
		fatura.setValorFust(valor_fust);
		fatura.setValorFuntel(valor_funtel);
		return map;

	}

	private Map<Long, DadosGeraisPrimeiraViaPrintParceiroDTO> selecionarClienteNotaFiscalBoletoParceiro(
			List bean, FaturaNetDTO fatura) {
		Map<Long, DadosGeraisPrimeiraViaPrintParceiroDTO> map = new HashMap<Long, DadosGeraisPrimeiraViaPrintParceiroDTO>();
		DynamicBean dyna = null;

		DadosGeraisPrimeiraViaPrintParceiroDTO dadosGerais = null;

		for (Iterator i = bean.iterator(); i.hasNext();) {
			dyna = (DynamicBean) i.next();
			dadosGerais = new DadosGeraisPrimeiraViaPrintParceiroDTO();
			Long idCobranca = toLong(dyna.get("ID_COBRANCA_NOTA_FISCAL"));

			dadosGerais.setCcLogradouro(((String) (dyna.get("ENDERECONOTAFISCAL"))));
			dadosGerais.setCcBairro((String) (dyna.get("BAIRRONOTAFISCAL")));
			dadosGerais.setCcCidade((String) (dyna.get("CIDADENOTAFISCAL")));
			dadosGerais.setCcEstado((String) (dyna.get("ESTADONOTAFISCAL")));
			dadosGerais.setCepNotaFiscal((String) (dyna.get("CEPNOTAFISCAL")));
			dadosGerais.setCcNomeRazaoSocial((String) (dyna.get("NOMECLIENTENOTAFISCAL")));
			dadosGerais.setCcCodigoSefaz((String) (dyna.get("CFOPNOTAFISCAL")));
			dadosGerais.setCcInscricaoEstadual((String) (dyna.get("INSCRICAOESTADUALNOTAFISCAL")));
			dadosGerais.setCcCpfCnpj((String) (dyna.get("CPFCNPJNOTAFISCAL")));
			dadosGerais.setCcSerieNotaFiscal((String) (dyna.get("SERIENOTAFISCAL")));
			dadosGerais.setCcAnoMesRef((String) (dyna.get("MESANOREFERENCIA")));
			dadosGerais.setNrNotaFiscal(toStringFromBigDecimal(dyna.get("NUMERONOTAFISCAL")));
            dadosGerais.setNrFatura(toStringFromBigDecimal(dyna.get("NUMEROFATURA")));
			dadosGerais.setDtEmissao((Date) (dyna.get("DATAEMISSAONOTAFISCAL")));
			dadosGerais.setVlTotal(toDouble(dyna.get("VL_TOTAL")));
			dadosGerais.setIdBoleto(toStringFromBigDecimal(dyna.get("IDENTIFICADORBOLETO")));
			dadosGerais.setIdBoletoOrigem(toLong(dyna.get("IDENTIFICADORBOLETOORIGEM")));
			dadosGerais.setDtVencimento((Date) (dyna.get("DATAVENCIMENTOBOLETO")));
			dadosGerais.setCcCodigoBarra((String) (dyna.get("CODIGODEBARRAS")));
			dadosGerais.setCcLinhaDigitavel((String) (dyna.get("LINHADIGITAVEL")));
			dadosGerais.setVlDocumento(toDouble(dyna.get("VALORCOBRADO")));
			dadosGerais.setEnder((String) (dyna.get("ENDERECOCOBRANCA")));
			dadosGerais.setBairro((String) (dyna.get("BAIRROCOBRANCA")));
			dadosGerais.setCidade((String) (dyna.get("CIDADECOBRANCA")));
			dadosGerais.setEstado((String) (dyna.get("ESTADOCOBRANCA")));
			dadosGerais.setCep(toInteger(dyna.get("CEPCOBRANCA")));
			dadosGerais.setPrefixo((String) (dyna.get("PREFIXO")));
			dadosGerais.setNomeTitular((String) (dyna.get("NOMECLIENTECOBRANCA")));
			dadosGerais.setFc_boleto_consolidado((String) (dyna.get("FC_BOLETO_CONSOLIDADO")));
			dadosGerais.setCodigoClienteEBT(toStringFromBigDecimal(dyna.get("CODIGOCLIENTEEBT")));
			
			dadosGerais.setLogradouroCobranca((String) (dyna.get("LOGRADOUROCOBRANCA") == null ? dyna.get("ENDERFATURA") : dyna.get("LOGRADOUROCOBRANCA")));
			dadosGerais.setNumeroCobranca(parseAddressNumber((String) (dyna.get("NUMEROCOBRANCA") == null ? dyna.get("ENDERNRFATURA") : dyna.get("NUMEROCOBRANCA"))));
			dadosGerais.setComplementoCobranca((String) (dyna.get("COMPLEMENTOCOBRANCA") == null ? dyna.get("ENDERCOMPLFATURA") : dyna.get("COMPLEMENTOCOBRANCA")));
			
			String mensagemNF = (String) dyna.get("MENSAGEMNOTAFISCAL");
			if (mensagemNF == null || mensagemNF.equals("")) {
				dadosGerais.setDsMensagemNotaFiscal(PrintHouseConstants.NOTA_FISCAL_EBT.MENSAGEM_NF_PADRAO);
			} else {
				dadosGerais.setDsMensagemNotaFiscal(mensagemNF);
			}

			String codigoBanco =  (String) dyna.get("ID_BANCO");
			if (codigoBanco==null || codigoBanco.equals(""))
			{
				dadosGerais.setCodigoBanco("");
			}else
			{
				dadosGerais.setCodigoBanco(codigoBanco);
			}
			dadosGerais.setCodOperadora((String) (dyna.get("CODOPERADORA")));
			dadosGerais.setCodigoClienteNET(toStringFromBigDecimal(dyna.get("CODIGOCLIENTENET")));
			dadosGerais.setCc_razao_social_oper((String) (dyna.get("CC_RAZAO_SOCIAL_OPER")));
			dadosGerais.setCc_logradouro_oper((String) (dyna.get("CC_LOGRADOURO_OPER")));
			dadosGerais.setCc_bairro_oper((String) (dyna.get("CC_BAIRRO_OPER")));
			dadosGerais.setCc_cidade_oper((String) (dyna.get("CC_CIDADE_OPER")));
			dadosGerais.setCc_estado_oper((String) (dyna.get("CC_ESTADO_OPER")));
			dadosGerais.setCc_cep_oper((String) (dyna.get("CC_CEP_OPER")));
			dadosGerais.setCc_cnpj_oper((String) (dyna.get("CC_CNPJ_OPER")));
			dadosGerais.setCc_inscricao_municipal_oper((String)(dyna.get("CC_INSCRICAO_MUNICIPAL_OPER")));
			dadosGerais.setCc_inscricao_estadual_oper((String) (dyna.get("CC_INSCRICAO_ESTADUAL_OPER")));
			dadosGerais.setCcHashCode((String) (dyna.get("HASHCODE")));
			fatura.setNumFaturaParceiro(toStringFromBigDecimal(dyna.get("NUMEROFATURA")));
			// Campo presente no Dynamic mas que não possui equivalente no DTO =
			// NUM_CONTRATO

			map.put(idCobranca,dadosGerais);
		}

		return map;

	}
	//TODO Ver como agrupar pq não tem o retorno
	private Map<Long, List<DadosItensNFDTO>> listarDetalhamentoNotaFiscalParceiro(List bean) {
		Map<Long, List<DadosItensNFDTO>> map = new HashMap<Long, List<DadosItensNFDTO>>();
		List<DadosItensNFDTO> list = new ArrayList<DadosItensNFDTO>();

		DynamicBean dyn = null;
		DadosItensNFDTO dadosItensNFDTO = null;
		Long id = null;

		if ( bean != null ){
			for (Iterator i = bean.iterator(); i.hasNext();) {
				dyn = (DynamicBean) i.next();
				dadosItensNFDTO = new DadosItensNFDTO();

				id = toLong(dyn.get("ID_COBRANCA_NOTA_FISCAL"));

				if (!map.containsKey(id))
					map.put(id, new ArrayList<DadosItensNFDTO>());

				dadosItensNFDTO.setNomeAgrupamento((String) dyn.get("NOMEAGRUPAMENTO"));
				dadosItensNFDTO.setValorSomaItensAgrupamento(toDouble(dyn.get("VALOR_SOMA_ITENS_AGRUPAMENTO")));
				dadosItensNFDTO.setDescricao((String) dyn.get("DESCRICAO"));
				dadosItensNFDTO.setValorBrutoItem(toDouble(dyn.get("VALOR_BRUTO_ITEM")));
				dadosItensNFDTO.setAliquota((BigDecimal) dyn.get("ALIQUOTA"));
				dadosItensNFDTO.setValorCofinsItem(dyn.containsKey(COFINS) ? toDouble(dyn.get(COFINS)) : 0);
				dadosItensNFDTO.setValorPisItem(dyn.containsKey(PIS) ?  toDouble(dyn.get(PIS)) : 0);
				dadosItensNFDTO.setValorIcmsItem(dyn.containsKey(ICMS) ? toDouble(dyn.get(ICMS)) : 0);

				map.get(id).add(dadosItensNFDTO);
			}

	  }
		return map;
	}

	private Map<Long,List<DadosTributosDTO>> listarTributoParceiro(List bean) {
		Map<Long, List<DadosTributosDTO>> map = new HashMap<Long, List<DadosTributosDTO>>();
		List<DadosTributosDTO> list = new ArrayList<DadosTributosDTO>();

		DynamicBean dyn = null;
		DadosTributosDTO dadosTributosDTO = null;
		Long id = null;
		String fcTotal = null;
		if(bean != null){
		for (Iterator i = bean.iterator(); i.hasNext();) {
			dyn = (DynamicBean) i.next();
			fcTotal = ((String) dyn.get("FC_TOTAL"));

			if(fcTotal.equals("N")){
				dadosTributosDTO = new DadosTributosDTO();

				id = toLong(dyn.get("ID_COBRANCA_NOTA_FISCAL"));

				if (!map.containsKey(id))
					map.put(id, new ArrayList<DadosTributosDTO>());

				dadosTributosDTO.setSIGLATRIBUTO((String) dyn.get("SIGLA_TRIBUTO"));
				dadosTributosDTO.setBASECALCULO(toDouble(dyn.get("BASE_CALCULO")));
				dadosTributosDTO.setVALORALIQUOTA(toDouble(dyn.get("VALOR_ALIQUOTA")));
				dadosTributosDTO.setVALORTRIBUTO(toDouble(dyn.get("VALOR_IMPOSTO")));
				dadosTributosDTO.setVALORTOTAL(toDouble(dyn.get("VALOR_TOTAL")));
				dadosTributosDTO.setVALORISENTO(toDouble(dyn.get("VALOR_ISENTO")));
				dadosTributosDTO.setVALOROUTROS(toDouble(dyn.get("VALOR_OUTROS")));

				map.get(id).add(dadosTributosDTO);
			}
		 }
		}

		return map;
	}

	private Map<Long,List<DadosTributosDTO>> listarTributoParceiroTotal(List bean) {
		Map<Long, List<DadosTributosDTO>> map = new HashMap<Long, List<DadosTributosDTO>>();
		List<DadosTributosDTO> list = new ArrayList<DadosTributosDTO>();

		DynamicBean dyn = null;
		DadosTributosDTO dadosTributosDTO = null;
		Long id = null;
		String fcTotal = null;

		if(bean != null){
			for (Iterator i = bean.iterator(); i.hasNext();) {
				dyn = (DynamicBean) i.next();
				fcTotal = ((String) dyn.get("FC_TOTAL"));

				if(fcTotal.equals("S")){
					dadosTributosDTO = new DadosTributosDTO();

					id = toLong(dyn.get("ID_COBRANCA_NOTA_FISCAL"));

					if (!map.containsKey(id))
						map.put(id, new ArrayList<DadosTributosDTO>());

					dadosTributosDTO.setSIGLATRIBUTO((String) dyn.get("SIGLA_TRIBUTO"));
					dadosTributosDTO.setBASECALCULO(toDouble(dyn.get("BASE_CALCULO")));
					dadosTributosDTO.setVALORALIQUOTA(toDouble(dyn.get("VALOR_ALIQUOTA")));
					dadosTributosDTO.setVALORTRIBUTO(toDouble(dyn.get("VALOR_IMPOSTO")));
					dadosTributosDTO.setVALORTOTAL(toDouble(dyn.get("VALOR_TOTAL")));
					dadosTributosDTO.setVALORISENTO(toDouble(dyn.get("VALOR_ISENTO")));
					dadosTributosDTO.setVALOROUTROS(toDouble(dyn.get("VALOR_OUTROS")));
					dadosTributosDTO.setSIGLATRIBUTO((String) dyn.get("SIGLA_TRIBUTO"));

					map.get(id).add(dadosTributosDTO);
			}
		  }
		}

		return map;
	}

	private List<DemonstrativoFinanceiroParceiroDTO> listarDemonstrativoFinanceiroParceiro(List bean) {
		List<DemonstrativoFinanceiroParceiroDTO> list = new ArrayList<DemonstrativoFinanceiroParceiroDTO>();

		DynamicBean d = null;
		DemonstrativoFinanceiroParceiroDTO demonstrativoFinanceiroParceiroDTO = null;

		if(bean != null){
			for (Iterator i = bean.iterator(); i.hasNext();) {
				d = (DynamicBean) i.next();
				demonstrativoFinanceiroParceiroDTO = new DemonstrativoFinanceiroParceiroDTO();

				demonstrativoFinanceiroParceiroDTO.setIdBoleto(toLong(d.get("IDBOLETO")));
				//demonstrativoFinanceiroParceiroDTO.setGRUPO_DEMONST_FINANC((String) d.get("GRUPO_DEMONST_FINANC"));
				demonstrativoFinanceiroParceiroDTO.setGRUPO_DEMONST_FINANC((String) d.get("GRUPO"));
				demonstrativoFinanceiroParceiroDTO.setDESCRICAOITEMDEMONST_FINANC((String) d.get("DESCRICAOITEMDEMONST_FINANC"));
				demonstrativoFinanceiroParceiroDTO.setDURACAO((String) d.get("DURACAO"));
				demonstrativoFinanceiroParceiroDTO.setVALORITEMDEMONST_FINANC(toDouble(d.get("VALORITEMDEMONST_FINANC")));
				if(d.get("ID_PARCEIRO") != null){
					demonstrativoFinanceiroParceiroDTO.setID_PARCEIRO(toLong(d.get("ID_PARCEIRO")));
				}

				list.add(demonstrativoFinanceiroParceiroDTO);
			}
		}

		return list;
	}


	private Map<Long,List<DynamicBean>> listarBoletos(ResultSetBeanCollection bean) {
		Map<Long,List<DynamicBean>> map = new HashMap<Long,List<DynamicBean>>();

		DynamicBean b = null;
		Long id = null;

		for (Iterator i = bean.iterator(); i.hasNext();) {
			b = (DynamicBean) i.next();
			id = toLong(b.get("ID_BOLETO"));

			if (!map.containsKey(id))
				map.put(id, new ArrayList<DynamicBean>());

			 map.get(id).add(b);
		}
		return map;
	}

	private Map<Long,List<DynamicBean>> listarBLTO(ResultSetBeanCollection bean) {
		Map<Long,List<DynamicBean>> map = new HashMap<Long,List<DynamicBean>>();

		DynamicBean b = null;
		Long id = null;

		for (Iterator i = bean.iterator(); i.hasNext();) {
			b = (DynamicBean) i.next();
			id = toLong(b.get("ID_BLTO"));

			if (!map.containsKey(id))
				map.put(id, new ArrayList<DynamicBean>());

			 map.get(id).add(b);
		}
		return map;
	}


	private CobrancaParceiroDTO selecionaFustFuntel(List bean) {
		DynamicBean d = null;
		CobrancaParceiroDTO cobrancaParceiroDTO = new CobrancaParceiroDTO();
		Iterator i = bean.iterator();
		if (i.hasNext()) {
			d = (DynamicBean) i.next();
			cobrancaParceiroDTO.setValorFust(toDouble(d.get("VL_FUST")));
			cobrancaParceiroDTO.setValorFuntel(toDouble(d.get("VL_FUNTEL")));
		}
		return cobrancaParceiroDTO;
	}


	private List<LoteBoletoFaturaDTO> listarBoletosFaturas(ResultSetBeanCollection bean){
		List<LoteBoletoFaturaDTO> list = new ArrayList<LoteBoletoFaturaDTO>(bean.size());

		DynamicBean d = null;
		LoteBoletoFaturaDTO boleto = null;

		for (Iterator i = bean.iterator(); i.hasNext();) {
			d = (DynamicBean) i.next();
			boleto = new LoteBoletoFaturaDTO();

			boleto.setIdBoleto(toLong(d.get("ID_BLTO")));
			boleto.setCepCobranca(String.valueOf(toLong(d.get("CC_CEP"))));
			boleto.setIdLote(toInteger(d.get("ID_LOTE")));
			boleto.setDataVencimento(getDate(d.get("DT_VCTO")));
			boleto.setCidContrato((String)d.get("CID_CONTRATO"));
			boleto.setFcBoletoConsolidado((String)d.get("FC_BOLETO_CONSOLIDADO"));
			if((toInteger(d.get("ID_PARCEIRO")).equals(1))){
				boleto.setIsOperadoraNet("S");
			}else{
				boleto.setIsOperadoraNet("N");
			}
			boleto.setTipoLote(toInteger(d.get("ID_TIPO_LOTE")));
			boleto.setSiglaLote(d.getBeanProperty("SG_TIPO_LOTE"));
			list.add(boleto);
		}

		return list;
	}
	
	private List<MensagemAnatelDTO> listarMensagemAnatel(ResultSetBeanCollection bean) throws SQLException{
		
		List<MensagemAnatelDTO> list = new ArrayList<MensagemAnatelDTO>(bean.size());

		DynamicBean d = null;
		MensagemAnatelDTO mensagem = null;

		for (Iterator i = bean.iterator(); i.hasNext();) {
			
			d = (DynamicBean) i.next();
			mensagem = new MensagemAnatelDTO();

			mensagem.setIdMensagemAnatel(d.get("ID_MENSAGEM_ANATEL") != null ? d.get("ID_MENSAGEM_ANATEL").toString() : "");
			mensagem.setNumContrato(d.get("NUM_CONTRATO") != null ? d.get("NUM_CONTRATO").toString() : "");
			mensagem.setCidContrato(d.get("CID_CONTRATO") != null ? d.get("CID_CONTRATO").toString() : "");
			mensagem.setIdBoleto(d.get("ID_BOLETO") != null ? d.get("ID_BOLETO").toString() : "");
			mensagem.setIdFrase(d.get("ID_FRASE") != null ? d.get("ID_FRASE").toString() : "");
			mensagem.setDtMensagem(d.get("DT_MENSAGEM") != null ? d.get("DT_MENSAGEM").toString() : "");
			mensagem.setNumOrdemMensagem(d.get("NR_ORDEM_MENSAGEM") != null ? d.get("NR_ORDEM_MENSAGEM").toString() : "");
			mensagem.setDescMensagemFormatada(d.get("DS_MENSAGEM_FORMATADA") != null ? ((java.sql.Clob)d.get("DS_MENSAGEM_FORMATADA")).getSubString(1, new Long(((java.sql.Clob)d.get("DS_MENSAGEM_FORMATADA")).length()).intValue()) : "");
			
			if(mensagem.getDescMensagemFormatada() != null && mensagem.getDescMensagemFormatada().length() > 250){
			
				mensagem.setDescMensagemFormatada(mensagem.getDescMensagemFormatada().substring(0, 250));
			}
			
			list.add(mensagem);
		}

		return list;
	}
	
	private List<MensagemStreamingDTO> listarMensagemStreaming(ResultSetBeanCollection bean) throws SQLException{
		
		List<MensagemStreamingDTO> list = new ArrayList<MensagemStreamingDTO>(bean.size());

		DynamicBean d = null;
		MensagemStreamingDTO mensagem = null;

		for (Iterator i = bean.iterator(); i.hasNext();) {
			
			d = (DynamicBean) i.next();
			mensagem = new MensagemStreamingDTO();

			mensagem.setIdMensagemStreaming(d.get("ID_MENSAGEM_STREAMING") != null ? d.get("ID_MENSAGEM_STREAMING").toString() : "");
			mensagem.setNumContrato(d.get("NUM_CONTRATO") != null ? d.get("NUM_CONTRATO").toString() : "");
			mensagem.setCidContrato(d.get("CID_CONTRATO") != null ? d.get("CID_CONTRATO").toString() : "");
			mensagem.setIdBoleto(d.get("ID_BOLETO") != null ? d.get("ID_BOLETO").toString() : "");
			mensagem.setIdFrase(d.get("ID_FRASE") != null ? d.get("ID_FRASE").toString() : "");
			mensagem.setDtMensagem(d.get("DT_MENSAGEM") != null ? d.get("DT_MENSAGEM").toString() : "");
			mensagem.setNumOrdemMensagem(d.get("NR_ORDEM_MENSAGEM") != null ? d.get("NR_ORDEM_MENSAGEM").toString() : "");
			mensagem.setDescMensagemFormatada(d.get("DS_MENSAGEM_FORMATADA") != null ? ((java.sql.Clob)d.get("DS_MENSAGEM_FORMATADA")).getSubString(1, new Long(((java.sql.Clob)d.get("DS_MENSAGEM_FORMATADA")).length()).intValue()) : "");
			
			if(mensagem.getDescMensagemFormatada() != null && mensagem.getDescMensagemFormatada().length() > 250){
			
				mensagem.setDescMensagemFormatada(mensagem.getDescMensagemFormatada().substring(0, 250));
			}
			
			list.add(mensagem);
		}

		return list;
	}

	public String buscarUltimasOcorrencias(Long numeroContrato, String cidContrato){
		String ultimasOcorrencias = "";
		try{
		BatchParameter[] ps = new BatchParameter[] { new BatchParameter(Types.VARCHAR, true),
				new BatchParameter(numeroContrato, Types.NUMERIC),
				new BatchParameter(cidContrato,Types.VARCHAR)
				};

			 	List list = executeBatch(FSN_ULT_OCORR_FAT, ps);

			 	ultimasOcorrencias = (String)list.get(0);
		}catch(Exception ex){
			log.error(ex);
		}
		return ultimasOcorrencias;
	}

	private Double toDouble(Object o) {
		if (o != null) {
			return ((BigDecimal) o).doubleValue();
		} else {
			return null;
		}
	}

	private Long toLong(Object o) {
		if (o != null) {
			return ((BigDecimal) o).longValue();
		} else {
			return null;
		}
	}


	private String toString(Object o) {
		if (o != null) {
			return ((String) o);
		} else {
			return "";
		}
	}

	private Integer toInteger(Object o) {
		if (o != null) {
			return ((BigDecimal) o).intValue();
		} else {
			return null;
		}
	}

	private String toStringFromBigDecimal(Object o) {
		if (o != null) {
			return o.toString();
		} else {
			return null;
		}

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
			// TODO O que fazer quando data estiver inválida no banco?
		}

		return dataRetorno;

	}

	private Long getLong(Object valor) {

		Long longValue = null;

		if(valor != null) {
			longValue = Long.valueOf(valor.toString());
		}

		return longValue;

	}

	private Double getDouble(Object valor) {

		Double doubleValue = null;

		if(valor != null) {
			doubleValue = Double.valueOf(valor.toString());
		}

		return doubleValue;

	}

	public int updateLotesToProcess (int idLote) throws Exception {


		StringBuffer sb = new StringBuffer();
		sb.append("update sn_lote  ");
		sb.append("	  set id_situacao_processamento = 8");
		sb.append("	where id_lote = ? ");
		sb.append("	and id_situacao_processamento not in (8,5) ");

		return super.update(sb.toString(), new Parameter[]{new Parameter(idLote, Types.INTEGER)});

/*		Connection 	connection = null;
		PreparedStatement pstmt = null;
		int i  = 0;

			try{

				connection = getConnection();
				pstmt = connection.prepareStatement(sb.toString());
				pstmt.setInt(1, idLote);
				i  = pstmt.executeUpdate();

			}catch(Exception ex){
				throw new BaseDAOException(ex);
		    } finally {
		    	JdbcUtils.closeStatement(pstmt);
		    }
		return i;
*/	}

	/**
	 * Retorna o tipo de fonte correto.
	 * NET TV : Tipo de Fonte 01
	 * NET VIRTUA : Tipo de Fonte 02
	 * NET FONE : Tipo de Fonte 03
	 * CYBERS : Tipo de Fonte 04
	 * FIDELIDADE: 05
	 * @param minhaNet
	 * @param nometipogrupo
	 * @return
	 */
	private String getTipoFonteMinhaNet(String minhaNet, String nometipogrupo, Long idParceiro) {
		String tipoFonte = "";

		if (minhaNet != null && nometipogrupo != null && !"".equals(nometipogrupo) ) {
 
			if (nometipogrupo.startsWith(CLARO_NET_TV) || nometipogrupo.startsWith(NET_TV) || nometipogrupo.startsWith(TV_POR_ASSINATURA) 
					|| nometipogrupo.toUpperCase(Locale.getDefault()).startsWith(CLARO_TV)) {
				tipoFonte = "01";
			}
			else if (nometipogrupo.toUpperCase(Locale.getDefault()).startsWith(CLARO_NET_VIRTUA) || nometipogrupo.startsWith(NET_VIRTUA) || nometipogrupo.startsWith(INTERNET) 
					|| nometipogrupo.toUpperCase(Locale.getDefault()).startsWith(CLARO_INTERNET)) {
				if (minhaNet.lastIndexOf("CYBER") == -1) {
					tipoFonte = "02";
				}
				else {
					tipoFonte = "04";
				}
			}
			else if (nometipogrupo.toUpperCase().startsWith(NET_FONE) || nometipogrupo.toUpperCase().startsWith(CLARO_FONE)) {
				tipoFonte = "03";
			}
			else if (nometipogrupo.toUpperCase().startsWith(NET_LAR) ||
					nometipogrupo.toUpperCase().startsWith(OUTROS_SERVICOS)) {
				tipoFonte = "06";
			}
			else if (idParceiro != null && ParceiroEnum.CLARO.getIdParceiro() == idParceiro.intValue()){
				tipoFonte = "04";
			}
			else if (minhaNet != null) { // && "FIDELIDADE".equals(minhaNet.trim().toUpperCase())) {
				tipoFonte = "05"; //FIDELIDADE
			}
		}

		return tipoFonte;
	}


	/**
	 * Retorna o cursor de todos boletos filiados de um lote
	 * @param idLote
	 * @return
	 */
	private List carregarFiliados(Long idLote) {

		BatchParameter[] ps = new BatchParameter[5];
		ps[0] = new BatchParameter(idLote, Types.NUMERIC, false);
		ps[1] = new BatchParameter(Types.NUMERIC, false);
		ps[2] = new BatchParameter("P", Types.VARCHAR, false);
		ps[3] = new BatchParameter(BatchParameter.ORACLE_CURSOR , true);
		ps[4]= new BatchParameter(Types.VARCHAR, true);

		List listFiliados = executeBatch(PRSMS_LST_DEMONST_FILIADO, ps);

		return listFiliados;
	}

	/**
	 * Mapeia a listagem de filiados para lista de FiliadosDTO
	 * @param bean
	 * @return
	 */
	private List<FiliadosDTO> listarFiliados(List bean) {

		List<FiliadosDTO> list = null;
		
		if(bean != null) {
			list = new ArrayList<FiliadosDTO>(bean.size());
			
			DynamicBean db = null;
			FiliadosDTO df = null;
			for (Iterator i = bean.iterator(); i.hasNext();) {
				db = (DynamicBean) i.next();
				
				String nome = (String) db.get("NOME");
				String produto = (String) db.get("PRODUTO");
				Object valor = db.get("VALOR");
				Object tamanho = db.get("TAMANHO");
				Object ordsub = db.get("ORDSUB");
				String endereco = (String) db.get("ENDERECO");
				
				if (nome != null && !"".equals(nome.trim())) {
					df = new FiliadosDTO();
					df.setNOME(nome);
					df.setPRODUTO(produto);
					df.setVALOR(toDouble(valor));
					df.setTAMANHO(toInteger(tamanho));
					df.setORDSUB(toInteger(ordsub));
					df.setENDERECO(endereco);
					list.add(df);
				}
			}
		}
		
		return list;
	}
	
	/**
	 * Método responsável por buscar as informaçoes da Declaracao de Debito Anual para Cobranca.
	 **/	
	private Boolean validaInicioEnvioMsgQdeb(String data) {	
		try {

			SimpleDateFormat fmt  = new SimpleDateFormat("dd/MM/yyyy");
			Calendar calDataAtual = GregorianCalendar.getInstance(new Locale("pt_BR"));
			Calendar dataIniQdeb  = GregorianCalendar.getInstance(new Locale("pt_BR"));		

			dataIniQdeb.setTime(fmt.parse(data));
			dataIniQdeb.getTime();

			if(calDataAtual.after(dataIniQdeb)){
				return true;
			}

		} catch (Exception e) {
			log.info(new BasicAttachmentMessage("COMPARA DATA INICIO PROCESSAMENTO METODO validaInicioEnvioMsgQdeb -- DATA: " + data, AttachmentMessageLevel.INFO));
		}
		
		return false;
	}
	
	/**
	 * Método responsável por buscar as informaçoes da Declaracao de Debito Anual para Cobranca.
	 **/	
	private List buscaDebitosAnual(Long idBoleto) {

			List<String> strRetProc =  new ArrayList<String>();

			try{

				BatchParameter[] parameters = new BatchParameter[4];
				parameters[0] = new BatchParameter(idBoleto, Types.NUMERIC, false);
				parameters[1] = new BatchParameter(Types.VARCHAR, true);
				parameters[2] = new BatchParameter(Types.VARCHAR, true);
				parameters[3] = new BatchParameter(Types.VARCHAR, true);

				List listRetProc = executeBatch(PRSMS_LST_QUITACAO_DEBITO_ANUAL, parameters);

				strRetProc.add(listRetProc.get(1) == " " ? "" : listRetProc.get(1).toString());
				strRetProc.add(listRetProc.get(2) == " " ? "" : listRetProc.get(2).toString());
				strRetProc.add(listRetProc.get(3) == " " ? "" : listRetProc.get(3).toString());

			}catch(Exception e){
				log.info(new BasicAttachmentMessage("ERRO AO ENVIAR MSG QUITACAO DEBITO ANUAL PROC -->" + PRSMS_LST_QUITACAO_DEBITO_ANUAL + " ID_BOLETO: " + idBoleto, AttachmentMessageLevel.INFO));
			}

			return strRetProc;	
	}
	
	/**
	 * Verifica se o contrato foi migrado a partir do id do boleto gerado
	 * @param idBoleto
	 * @return
	 */
	public boolean verificaMigracaoContratoByIdBoleto(Long idBoleto){	
		
		if (idBoleto != null) {
			try {
				BatchParameter[] parameters = new BatchParameter[2];
				parameters[0] = new BatchParameter(Types.NUMERIC, true); // retorno
				parameters[1] = new BatchParameter(idBoleto, Types.NUMERIC, false);

				List lstMigracao = super.executeBatch(FNC_VERIFICA_MGR_BOL_PAD_POLITICA, parameters);
				
				if (lstMigracao != null && !lstMigracao.isEmpty()) {
					int ret = ((BigDecimal)lstMigracao.get(0)).intValue();
					if (ret == 1) {
						return true; // Deve mostrar mensagem na fatura
					}
				}
			} catch (Exception e) {
				log.info(new BasicAttachmentMessage("ERRO AO VERIFICAR MIGRACAO DE POLITICA", AttachmentMessageLevel.INFO));
				e.printStackTrace();
			}
		} else {
			log.info(new BasicAttachmentMessage("SEM ID_BOLETO PARA VERIFICAR MIGRACAO DE POLITICA", AttachmentMessageLevel.INFO));
		}
		return false;
	}	

	/**
	 * Verifica se o contrato foi migrado a partir do id do boleto gerado
	 * @param idBoleto
	 * @return
	 */
	public Integer getPoliticaContratoEm(SnContratoBean contrato, Date data){	
		
		if (contrato != null) {
			try {
				BatchParameter[] parameters = new BatchParameter[4];
				parameters[0] = new BatchParameter(Types.NUMERIC, true); // retorno
				parameters[1] = new BatchParameter(contrato.getCompositeKey().getNumContrato(), Types.NUMERIC, false);
				parameters[2] = new BatchParameter(contrato.getCompositeKey().getCidContrato(), Types.NUMERIC, false);
				parameters[3] = new BatchParameter(data, Types.TIMESTAMP, false);

				List lst = super.executeBatch(FNC_POL_CONTRATO_PAD_POLITICA, parameters);
				
				if (lst != null && !lst.isEmpty()) {
					int ret = ((BigDecimal)lst.get(0)).intValue();
					return ret;
				}
			} catch (Exception e) {
				log.info(new BasicAttachmentMessage("ERRO AO VERIFICAR POLÍTICA DO CONTRATO EM DATA (PADPOL)", AttachmentMessageLevel.INFO));
				e.printStackTrace();
			}
		} else {
			log.info(new BasicAttachmentMessage("SEM CONTRATO PARA VERIFICAR POLÍTICA (PADPOL)", AttachmentMessageLevel.INFO));
		}
		return null;
	}

	/**
	 * Gera mensagens complemento campo importante - ANATEL a partir do id do boleto 
	 * @param idBoleto
	 * @return
	 */
	public void gerarMensagensAnatel(Long idBoleto) {
	
		BatchParameter[] ps = new BatchParameter[2];
		ps[0] = new BatchParameter(idBoleto, Types.NUMERIC, false);
		ps[1]= new BatchParameter(Types.VARCHAR, true);
		
	
		log.info("[INICIO] gerarMensagensAnatel: " + idBoleto);
		
		List list = executeBatch(PR_GERA_MENSAGENS_ANATEL, ps);

		log.info("[FIM] gerarMensagensAnatel: " + idBoleto);

	}	
	
	/**
	 *
	 * Busca mensagens complemento campo importante - ANATEL a partir do id do boleto 
	 * <ul>
	 * <li> p_ID_BOLETO - (input) - ID Boleto
	 * </ul>
	 * @throws Exception
	 */
	public List<MensagemAnatelDTO> buscarMensagensAnatel(Long idBoleto) throws Exception {

		BatchParameter[] ps = new BatchParameter[3];
		ps[0] = new BatchParameter(idBoleto, Types.NUMERIC, false);
		ps[1] = new BatchParameter(BatchParameter.ORACLE_CURSOR , true);
		ps[2]= new BatchParameter(Types.VARCHAR, true);
		
		log.info("[INICIO] buscarMensagensAnatel: " + idBoleto);
		
		List list = executeBatch(PR_RETORNA_MENSAGENS_ANATEL, ps);

		List<MensagemAnatelDTO> listMensagem = this.listarMensagemAnatel((ResultSetBeanCollection) list.get(1)); 

		log.info("[FIM] buscarMensagensAnatel: " + idBoleto);

		return listMensagem;
	}
	
	/**
	 *
	 * Busca mensagens streaming - a partir do id do boleto 
	 * <ul>
	 * <li> p_ID_BOLETO - (input) - ID Boleto
	 * </ul>
	 * @throws Exception
	 */
	public List<MensagemStreamingDTO> buscarMensagemStreaming(Long idBoleto) throws Exception {

		BatchParameter[] ps = new BatchParameter[3];
		ps[0] = new BatchParameter(idBoleto, Types.NUMERIC, false);
		ps[1] = new BatchParameter(BatchParameter.ORACLE_CURSOR , true);
		ps[2]= new BatchParameter(Types.VARCHAR, true);
		
		log.info("[INICIO] buscarMensagemStreaming: " + idBoleto);
		
		List list = executeBatch(PR_RETORNA_MENSAGENS_STREAMING, ps);

		List<MensagemStreamingDTO> listMensagem = this.listarMensagemStreaming((ResultSetBeanCollection) list.get(1)); 

		log.info("[FIM] buscarMensagemStreaming: " + idBoleto);

		return listMensagem;
	}
}