package br.com.netservicos.novosms.emissao.dto;

import java.util.Date;
import java.util.List;

import br.com.netservicos.framework.core.bean.DomainBean;

public class DadosCobrancaParceiroDTO extends DomainBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String PRIMARY_KEY = "pk fake";
	
	private Long idCobrancaParceiro;
	private String cidContrato; 
	private Long idCobrancaNotaFiscal;
	private String OperadoraNet;
	private Integer idParceiro;
	private Integer id_tipo_parceiro;
	private String cc_razao_social_oper;
	private String cc_logradouro_oper;
	private String cc_bairro_oper;
	private String cc_cidade_oper;
	private String cc_estado_oper;
	private String cc_cep_oper;
	private String cc_cnpj_oper;
	private String cc_inscricao_estadual_oper;
	private String cc_inscricao_municipal_oper;
	private Double valor_total;
	private Double valorCobranca;
	

	public Double getValorCobranca() {
		return valorCobranca;
	}


	public void setValorCobranca(Double valorCobranca) {
		this.valorCobranca = valorCobranca;
	}


	public DadosCobrancaParceiroDTO() {
		super(PRIMARY_KEY);	
	}


	public String getCidContrato() {
		return cidContrato;
	}


	public void setCidContrato(String cidContrato) {
		this.cidContrato = cidContrato;
	}


	public Long getIdCobrancaNotaFiscal() {
		return idCobrancaNotaFiscal;
	}


	public void setIdCobrancaNotaFiscal(Long idCobrancaNotaFiscal) {
		this.idCobrancaNotaFiscal = idCobrancaNotaFiscal;
	}


	public Long getIdCobrancaParceiro() {
		return idCobrancaParceiro;
	}


	public void setIdCobrancaParceiro(Long idCobrancaParceiro) {
		this.idCobrancaParceiro = idCobrancaParceiro;
	}


	public String getOperadoraNet() {
		return OperadoraNet;
	}


	public void setOperadoraNet(String operadoraNet) {
		OperadoraNet = operadoraNet;
	}


	public Integer getIdParceiro() { 
		return idParceiro;
	}


	public void setIdParceiro(Integer idParceiro) {
		this.idParceiro = idParceiro;
	}


	public String getCc_bairro_oper() {
		return cc_bairro_oper;
	}


	public void setCc_bairro_oper(String cc_bairro_oper) {
		this.cc_bairro_oper = cc_bairro_oper;
	}


	public String getCc_cep_oper() {
		return cc_cep_oper;
	}


	public void setCc_cep_oper(String cc_cep_oper) {
		this.cc_cep_oper = cc_cep_oper;
	}


	public String getCc_cidade_oper() {
		return cc_cidade_oper;
	}


	public void setCc_cidade_oper(String cc_cidade_oper) {
		this.cc_cidade_oper = cc_cidade_oper;
	}


	public String getCc_cnpj_oper() {
		return cc_cnpj_oper;
	}


	public void setCc_cnpj_oper(String cc_cnpj_oper) {
		this.cc_cnpj_oper = cc_cnpj_oper;
	}


	public String getCc_estado_oper() {
		return cc_estado_oper;
	}


	public void setCc_estado_oper(String cc_estado_oper) {
		this.cc_estado_oper = cc_estado_oper;
	}


	public String getCc_inscricao_estadual_oper() {
		return cc_inscricao_estadual_oper;
	}


	public void setCc_inscricao_estadual_oper(String cc_inscricao_estadual_oper) {
		this.cc_inscricao_estadual_oper = cc_inscricao_estadual_oper;
	}


	public String getCc_inscricao_municipal_oper() {
		return cc_inscricao_municipal_oper;
	}


	public void setCc_inscricao_municipal_oper(String cc_inscricao_municipal_oper) {
		this.cc_inscricao_municipal_oper = cc_inscricao_municipal_oper;
	}


	public String getCc_logradouro_oper() {
		return cc_logradouro_oper;
	}


	public void setCc_logradouro_oper(String cc_logradouro_oper) {
		this.cc_logradouro_oper = cc_logradouro_oper;
	}


	public String getCc_razao_social_oper() {
		return cc_razao_social_oper;
	}


	public void setCc_razao_social_oper(String cc_razao_social_oper) {
		this.cc_razao_social_oper = cc_razao_social_oper;
	}


	public Integer getId_tipo_parceiro() {
		return id_tipo_parceiro;
	}


	public void setId_tipo_parceiro(Integer id_tipo_parceiro) {
		this.id_tipo_parceiro = id_tipo_parceiro;
	}


	public Double getValor_total() {
		return valor_total;
	}


	public void setValor_total(Double valor_total) {
		this.valor_total = valor_total;
	}

	
}
