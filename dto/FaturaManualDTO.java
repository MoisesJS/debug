package br.com.netservicos.novosms.emissao.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.netservicos.core.bean.sn.SnLoteBean;
import br.com.netservicos.framework.core.bean.DomainBean;
import br.com.netservicos.netsms.utilities.operator.bean.OperatorDTO;

public class FaturaManualDTO extends DomainBean {

	
	public static final String PRIMARY_KEY = "pk fake";

	public FaturaManualDTO() {
		super(PRIMARY_KEY);
	}

	private static final long serialVersionUID = 5900387555578405579L;


	private List<SnLoteBean> listaLote  = new ArrayList<SnLoteBean>();
	
	//atributois para pesquisa
	private Date dataInicial;	
	private Date dataFinal;	
	private String idOperadora = "";	
	private String dataInicialString;	
	private String dataFinalString;	
	
	
	
	//atributos para retrono
	private Integer id_lote;
	private Date dt_vcto;
	private Integer nr_qtd_boleto;
	private double vl_total;
	private Date dt_geracao;
	private Integer id_tipo_lote;
	private Integer id_tipo_cobranca;
	private Integer id_operadora_cartao;
	private Integer id_banco;
	private String  cid_contrato;
	private Integer id_criterio;
	private Integer id_cc_cedente;
	private Integer id_situacao_processamento;
	private String  desc_cidadeoperadora;
	
	
	
	
	private String desc_tipolote = "";
	private String desc_tipocobranca = "";
	private String desc_operadoracartao = "";
	private String desc_criteriosegboleto = "";
	private String desc_situacaoprocessamento = "";
	
	//private OperatorDTO operatorDTO;
	
	
	
	
	

	/*public OperatorDTO getOperatorDTO() {
		return operatorDTO;
	}

	public void setOperatorDTO(OperatorDTO operatorDTO) {
		this.operatorDTO = operatorDTO;
	}*/

	public String getDesc_tipolote() {
		return desc_tipolote;
	}

	public void setDesc_tipolote(String desc_tipolote) {
		this.desc_tipolote = desc_tipolote;
	}

	public String getDesc_criteriosegboleto() {
		return desc_criteriosegboleto;
	}

	public void setDesc_criteriosegboleto(String desc_criteriosegboleto) {
		this.desc_criteriosegboleto = desc_criteriosegboleto;
	}

	

	public String getDesc_operadoracartao() {
		return desc_operadoracartao;
	}

	public void setDesc_operadoracartao(String desc_operadoracartao) {
		this.desc_operadoracartao = desc_operadoracartao;
	}

	public String getDesc_tipocobranca() {
		return desc_tipocobranca;
	}

	public void setDesc_tipocobranca(String desc_tipocobranca) {
		this.desc_tipocobranca = desc_tipocobranca;
	}

	
	public String getDesc_situacaoprocessamento() {
		return desc_situacaoprocessamento;
	}

	public void setDesc_situacaoprocessamento(String desc_situacaoprocessamento) {
		this.desc_situacaoprocessamento = desc_situacaoprocessamento;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public String getIdOperadora() {
		return idOperadora;
	}

	public void setIdOperadora(String idOperadora) {
		this.idOperadora = idOperadora;
	}

	public List<SnLoteBean> getListaLote() {
		return listaLote;
	}

	public void setListaLote(List<SnLoteBean> listaLote) {
		this.listaLote = listaLote;
	}

	public String getCid_contrato() {
		return cid_contrato;
	}

	public void setCid_contrato(String cid_contrato) {
		this.cid_contrato = cid_contrato;
	}

	public Date getDt_geracao() {
		return dt_geracao;
	}

	public void setDt_geracao(Date dt_geracao) {
		this.dt_geracao = dt_geracao;
	}

	public Date getDt_vcto() {
		return dt_vcto;
	}

	public void setDt_vcto(Date dt_vcto) {
		this.dt_vcto = dt_vcto;
	}

	public Integer getId_banco() {
		return id_banco;
	}

	public void setId_banco(Integer id_banco) {
		this.id_banco = id_banco;
	}

	public Integer getId_cc_cedente() {
		return id_cc_cedente;
	}

	public void setId_cc_cedente(Integer id_cc_cedente) {
		this.id_cc_cedente = id_cc_cedente;
	}

	public Integer getId_criterio() {
		return id_criterio;
	}

	public void setId_criterio(Integer id_criterio) {
		this.id_criterio = id_criterio;
	}

	public Integer getId_lote() {
		return id_lote;
	}

	public void setId_lote(Integer id_lote) {
		this.id_lote = id_lote;
	}

	public Integer getId_operadora_cartao() {
		return id_operadora_cartao;
	}

	public void setId_operadora_cartao(Integer id_operadora_cartao) {
		this.id_operadora_cartao = id_operadora_cartao;
	}

	public Integer getId_situacao_processamento() {
		return id_situacao_processamento;
	}

	public void setId_situacao_processamento(Integer id_situacao_processamento) {
		this.id_situacao_processamento = id_situacao_processamento;
	}

	public Integer getId_tipo_cobranca() {
		return id_tipo_cobranca;
	}

	public void setId_tipo_cobranca(Integer id_tipo_cobranca) {
		this.id_tipo_cobranca = id_tipo_cobranca;
	}

	public Integer getId_tipo_lote() {
		return id_tipo_lote;
	}

	public void setId_tipo_lote(Integer id_tipo_lote) {
		this.id_tipo_lote = id_tipo_lote;
	}

	public Integer getNr_qtd_boleto() {
		return nr_qtd_boleto;
	}

	public void setNr_qtd_boleto(Integer nr_qtd_boleto) {
		this.nr_qtd_boleto = nr_qtd_boleto;
	}

	public double getVl_total() {
		return vl_total;
	}

	public void setVl_total(double vl_total) {
		this.vl_total = vl_total;
	}

	public String getDataFinalString() {
		return dataFinalString;
	}

	public void setDataFinalString(String dataFinalString) {
		this.dataFinalString = dataFinalString;
	}

	public String getDataInicialString() {
		return dataInicialString;
	}

	public void setDataInicialString(String dataInicialString) {
		this.dataInicialString = dataInicialString;
	}

	public String getDesc_cidadeoperadora() {
		return desc_cidadeoperadora;
	}

	public void setDesc_cidadeoperadora(String desc_cidadeoperadora) {
		this.desc_cidadeoperadora = desc_cidadeoperadora;
	}
	
	
	
	
	
	
	

}
