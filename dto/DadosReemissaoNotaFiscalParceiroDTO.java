package br.com.netservicos.novosms.emissao.dto;

import java.util.Date;

import br.com.netservicos.framework.core.bean.DomainBean;

public class DadosReemissaoNotaFiscalParceiroDTO extends DomainBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2621930925143921197L;
	
	private Long rownum;
	private String codigoClienteNET;
	private String enderecoNotaFiscal;
	private String bairroNotaFiscal;
	private String cidadeNotaFiscal;
	private String estadoNotaFiscal;
	private String cepNotaFiscal;
	private String nomeClienteNotaFiscal;
	private String cfopNotaFiscal;
	private String inscricaoEstadual;
	private String cpfCNPJNotaFiscal;
	private String serieNotaFiscal;
	private String mesAnoReferencia;
	private Long numeroNotaFiscal;
	private String hashCode;
	private Date dataEmissaoNotaFiscal;
	private Double valorTotalNotaFiscal;
	private Long identificadorBoleto;
	private Long identificadorBoletoOrigem;
	private Date dataVencimentoBoleto;
	private String codigoDeBarras;
	private String linhaDigitavel;
	private Double valorCobrado;
	private String enderecoCobranca;
	private String bairroCobranca;
	private String cidadeCobranca;
	private String estadoCobranca;
	private Integer cepCobranca;
	private String prefixo;
	private String nomeClienteCobranca;
	
	public DadosReemissaoNotaFiscalParceiroDTO(String primaryKeyName) {
		super(primaryKeyName);
		// TODO Auto-generated constructor stub
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

	public String getEnderecoNotaFiscal() {
		return enderecoNotaFiscal;
	}

	public void setEnderecoNotaFiscal(String enderecoNotaFiscal) {
		this.enderecoNotaFiscal = enderecoNotaFiscal;
	}

	public String getBairroNotaFiscal() {
		return bairroNotaFiscal;
	}

	public void setBairroNotaFiscal(String bairroNotaFiscal) {
		this.bairroNotaFiscal = bairroNotaFiscal;
	}

	public String getCidadeNotaFiscal() {
		return cidadeNotaFiscal;
	}

	public void setCidadeNotaFiscal(String cidadeNotaFiscal) {
		this.cidadeNotaFiscal = cidadeNotaFiscal;
	}

	public String getEstadoNotaFiscal() {
		return estadoNotaFiscal;
	}

	public void setEstadoNotaFiscal(String estadoNotaFiscal) {
		this.estadoNotaFiscal = estadoNotaFiscal;
	}

	public String getCepNotaFiscal() {
		return cepNotaFiscal;
	}

	public void setCepNotaFiscal(String cepNotaFiscal) {
		this.cepNotaFiscal = cepNotaFiscal;
	}

	public String getNomeClienteNotaFiscal() {
		return nomeClienteNotaFiscal;
	}

	public void setNomeClienteNotaFiscal(String nomeClienteNotaFiscal) {
		this.nomeClienteNotaFiscal = nomeClienteNotaFiscal;
	}

	public String getCfopNotaFiscal() {
		return cfopNotaFiscal;
	}

	public void setCfopNotaFiscal(String cfopNotaFiscal) {
		this.cfopNotaFiscal = cfopNotaFiscal;
	}

	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}

	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}

	public String getCpfCNPJNotaFiscal() {
		return cpfCNPJNotaFiscal;
	}

	public void setCpfCNPJNotaFiscal(String cpfCNPJNotaFiscal) {
		this.cpfCNPJNotaFiscal = cpfCNPJNotaFiscal;
	}

	public String getSerieNotaFiscal() {
		return serieNotaFiscal;
	}

	public void setSerieNotaFiscal(String serieNotaFiscal) {
		this.serieNotaFiscal = serieNotaFiscal;
	}

	public String getMesAnoReferencia() {
		return mesAnoReferencia;
	}

	public void setMesAnoReferencia(String mesAnoReferencia) {
		this.mesAnoReferencia = mesAnoReferencia;
	}

	public Long getNumeroNotaFiscal() {
		return numeroNotaFiscal;
	}

	public void setNumeroNotaFiscal(Long numeroNotaFiscal) {
		this.numeroNotaFiscal = numeroNotaFiscal;
	}

	public String getHashCode() {
		return hashCode;
	}

	public void setHashCode(String hashCode) {
		this.hashCode = hashCode;
	}

	public Date getDataEmissaoNotaFiscal() {
		return dataEmissaoNotaFiscal;
	}

	public void setDataEmissaoNotaFiscal(Date dataEmissaoNotaFiscal) {
		this.dataEmissaoNotaFiscal = dataEmissaoNotaFiscal;
	}

	public Double getValorTotalNotaFiscal() {
		return valorTotalNotaFiscal;
	}

	public void setValorTotalNotaFiscal(Double valorTotalNotaFiscal) {
		this.valorTotalNotaFiscal = valorTotalNotaFiscal;
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

}
