package br.com.netservicos.novosms.emissao.dto;

import java.util.Date;

import br.com.netservicos.framework.core.bean.DomainBean;

public class DadosSegundaViaParceiroDTO extends DomainBean {

	public static final String PRIMARY_KEY = "pk fake";

	/**
	 * 
	 */
	private static final long serialVersionUID = -3695386320778539930L;

	private Long rownum;
	private String codigoClienteNET; 
	private String enderecoCobranca;
	private String bairroCobranca;
	private String cidadeCobranca;
	private String estadoCobranca;
	private Integer cepCobranca;
	private String prefixo;
	private String nomeClienteCobranca;
	private String cpfCNPJCobranca;
	private String inscricaoEstadualCobranca;
	private Long identificadorBoleto;
	private Long identificadorBoletoOrigem;
	private Date dataProcessamentoBoleto;
	private Date dataVencimentoBoleto;
	private String codigoDeBarras;
	private String linhaDigitavel;
	private Double valorCobrado;
	private Date dataVencimentoBoletoOrigem;
	private String mesAnoReferencia;
	
	public DadosSegundaViaParceiroDTO(String primaryKeyName) {
		super(primaryKeyName);
	}

	public Long getRownum() {
		return rownum;
	}

	public void setRownum(Long rownum) {
		this.rownum = rownum;
	}

	public String getCodigoClienteNET() {
		return codigoClienteNET;
	}

	public void setCodigoClienteNET(String codigoClienteNET) {
		this.codigoClienteNET = codigoClienteNET;
	}

	public String getEnderecoCobranca() {
		return enderecoCobranca;
	}

	public void setEnderecoCobranca(String enderecoCobranca) {
		this.enderecoCobranca = enderecoCobranca;
	}

	public String getBairroCobranca() {
		return bairroCobranca;
	}

	public void setBairroCobranca(String bairroCobranca) {
		this.bairroCobranca = bairroCobranca;
	}

	public String getCidadeCobranca() {
		return cidadeCobranca;
	}

	public void setCidadeCobranca(String cidadeCobranca) {
		this.cidadeCobranca = cidadeCobranca;
	}

	public String getEstadoCobranca() {
		return estadoCobranca;
	}

	public void setEstadoCobranca(String estadoCobranca) {
		this.estadoCobranca = estadoCobranca;
	}

	public Integer getCepCobranca() {
		return cepCobranca;
	}

	public void setCepCobranca(Integer cepCobranca) {
		this.cepCobranca = cepCobranca;
	}

	public String getPrefixo() {
		return prefixo;
	}

	public void setPrefixo(String prefixo) {
		this.prefixo = prefixo;
	}

	public String getNomeClienteCobranca() {
		return nomeClienteCobranca;
	}

	public void setNomeClienteCobranca(String nomeClienteCobranca) {
		this.nomeClienteCobranca = nomeClienteCobranca;
	}

	public String getCpfCNPJCobranca() {
		return cpfCNPJCobranca;
	}

	public void setCpfCNPJCobranca(String cpfCNPJCobranca) {
		this.cpfCNPJCobranca = cpfCNPJCobranca;
	}

	public String getInscricaoEstadualCobranca() {
		return inscricaoEstadualCobranca;
	}

	public void setInscricaoEstadualCobranca(String inscricaoEstadualCobranca) {
		this.inscricaoEstadualCobranca = inscricaoEstadualCobranca;
	}

	public Long getIdentificadorBoleto() {
		return identificadorBoleto;
	}

	public void setIdentificadorBoleto(Long identificadorBoleto) {
		this.identificadorBoleto = identificadorBoleto;
	}

	public Long getIdentificadorBoletoOrigem() {
		return identificadorBoletoOrigem;
	}

	public void setIdentificadorBoletoOrigem(Long identificadorBoletoOrigem) {
		this.identificadorBoletoOrigem = identificadorBoletoOrigem;
	}

	public Date getDataProcessamentoBoleto() {
		return dataProcessamentoBoleto;
	}

	public void setDataProcessamentoBoleto(Date dataProcessamentoBoleto) {
		this.dataProcessamentoBoleto = dataProcessamentoBoleto;
	}

	public Date getDataVencimentoBoleto() {
		return dataVencimentoBoleto;
	}

	public void setDataVencimentoBoleto(Date dataVencimentoBoleto) {
		this.dataVencimentoBoleto = dataVencimentoBoleto;
	}

	public String getCodigoDeBarras() {
		return codigoDeBarras;
	}

	public void setCodigoDeBarras(String codigoDeBarras) {
		this.codigoDeBarras = codigoDeBarras;
	}

	public String getLinhaDigitavel() {
		return linhaDigitavel;
	}

	public void setLinhaDigitavel(String linhaDigitavel) {
		this.linhaDigitavel = linhaDigitavel;
	}

	public Double getValorCobrado() {
		return valorCobrado;
	}

	public void setValorCobrado(Double valorCobrado) {
		this.valorCobrado = valorCobrado;
	}

	public Date getDataVencimentoBoletoOrigem() {
		return dataVencimentoBoletoOrigem;
	}

	public void setDataVencimentoBoletoOrigem(Date dataVencimentoBoletoOrigem) {
		this.dataVencimentoBoletoOrigem = dataVencimentoBoletoOrigem;
	}

	public String getMesAnoReferencia() {
		return mesAnoReferencia;
	}

	public void setMesAnoReferencia(String mesAnoReferencia) {
		this.mesAnoReferencia = mesAnoReferencia;
	}
	
}
