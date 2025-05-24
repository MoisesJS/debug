package br.com.netservicos.novosms.emissao.dto;

import java.util.Date;

import br.com.netservicos.framework.core.bean.DomainBean;

public class DadosPrimeiraViaSemNotaFiscalParceiroDTO extends DomainBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5338585979502305992L;
	
	private Long rownum;
	private String codigoClienteNET;
	private String enderecoInstalacao;
	private String bairroNotaFiscal;
	private String cidadeNotaFiscal;
	private String estadoNotaFiscal;
	private Integer cepNotaFiscal;
	private String nomeClienteNotaFiscal;
	private String cfopNotaFiscal;
	private String inscricaoEstadual;
	private String cpfCNPJNotaFiscal;
	private String mesAnoReferencia;
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
	private String codOperadora;


	public String getBairroCobranca() {
		return bairroCobranca;
	}


	public void setBairroCobranca(String bairroCobranca) {
		this.bairroCobranca = bairroCobranca;
	}


	public String getBairroNotaFiscal() {
		return bairroNotaFiscal;
	}


	public void setBairroNotaFiscal(String bairroNotaFiscal) {
		this.bairroNotaFiscal = bairroNotaFiscal;
	}


	public Integer getCepCobranca() {
		return cepCobranca;
	}


	public void setCepCobranca(Integer cepCobranca) {
		this.cepCobranca = cepCobranca;
	}


	public Integer getCepNotaFiscal() {
		return cepNotaFiscal;
	}


	public void setCepNotaFiscal(Integer cepNotaFiscal) {
		this.cepNotaFiscal = cepNotaFiscal;
	}


	public String getCfopNotaFiscal() {
		return cfopNotaFiscal;
	}


	public void setCfopNotaFiscal(String cfopNotaFiscal) {
		this.cfopNotaFiscal = cfopNotaFiscal;
	}


	public String getCidadeCobranca() {
		return cidadeCobranca;
	}


	public void setCidadeCobranca(String cidadeCobranca) {
		this.cidadeCobranca = cidadeCobranca;
	}


	public String getCidadeNotaFiscal() {
		return cidadeNotaFiscal;
	}


	public void setCidadeNotaFiscal(String cidadeNotaFiscal) {
		this.cidadeNotaFiscal = cidadeNotaFiscal;
	}


	public String getCodigoClienteNET() {
		return codigoClienteNET;
	}


	public void setCodigoClienteNET(String codigoClienteNET) {
		this.codigoClienteNET = codigoClienteNET;
	}


	public String getCodigoDeBarras() {
		return codigoDeBarras;
	}


	public void setCodigoDeBarras(String codigoDeBarras) {
		this.codigoDeBarras = codigoDeBarras;
	}


	public String getCodOperadora() {
		return codOperadora;
	}


	public void setCodOperadora(String codOperadora) {
		this.codOperadora = codOperadora;
	}


	public String getCpfCNPJNotaFiscal() {
		return cpfCNPJNotaFiscal;
	}


	public void setCpfCNPJNotaFiscal(String cpfCNPJNotaFiscal) {
		this.cpfCNPJNotaFiscal = cpfCNPJNotaFiscal;
	}


	public Date getDataVencimentoBoleto() {
		return dataVencimentoBoleto;
	}


	public void setDataVencimentoBoleto(Date dataVencimentoBoleto) {
		this.dataVencimentoBoleto = dataVencimentoBoleto;
	}


	public String getEnderecoCobranca() {
		return enderecoCobranca;
	}


	public void setEnderecoCobranca(String enderecoCobranca) {
		this.enderecoCobranca = enderecoCobranca;
	}


	public String getEnderecoInstalacao() {
		return enderecoInstalacao;
	}


	public void setEnderecoInstalacao(String enderecoInstalacao) {
		this.enderecoInstalacao = enderecoInstalacao;
	}


	public String getEstadoCobranca() {
		return estadoCobranca;
	}


	public void setEstadoCobranca(String estadoCobranca) {
		this.estadoCobranca = estadoCobranca;
	}


	public String getEstadoNotaFiscal() {
		return estadoNotaFiscal;
	}


	public void setEstadoNotaFiscal(String estadoNotaFiscal) {
		this.estadoNotaFiscal = estadoNotaFiscal;
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


	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}


	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}


	public String getLinhaDigitavel() {
		return linhaDigitavel;
	}


	public void setLinhaDigitavel(String linhaDigitavel) {
		this.linhaDigitavel = linhaDigitavel;
	}


	public String getMesAnoReferencia() {
		return mesAnoReferencia;
	}


	public void setMesAnoReferencia(String mesAnoReferencia) {
		this.mesAnoReferencia = mesAnoReferencia;
	}


	public String getNomeClienteCobranca() {
		return nomeClienteCobranca;
	}


	public void setNomeClienteCobranca(String nomeClienteCobranca) {
		this.nomeClienteCobranca = nomeClienteCobranca;
	}


	public String getNomeClienteNotaFiscal() {
		return nomeClienteNotaFiscal;
	}


	public void setNomeClienteNotaFiscal(String nomeClienteNotaFiscal) {
		this.nomeClienteNotaFiscal = nomeClienteNotaFiscal;
	}


	public String getPrefixo() {
		return prefixo;
	}


	public void setPrefixo(String prefixo) {
		this.prefixo = prefixo;
	}


	public Double getValorCobrado() {
		return valorCobrado;
	}


	public void setValorCobrado(Double valorCobrado) {
		this.valorCobrado = valorCobrado;
	}


	public DadosPrimeiraViaSemNotaFiscalParceiroDTO(String primaryKeyName) {
		super(primaryKeyName);
		// TODO Auto-generated constructor stub
	}
	
}
