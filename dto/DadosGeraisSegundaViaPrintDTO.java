package br.com.netservicos.novosms.emissao.dto;

import java.util.Date;

import br.com.netservicos.framework.core.bean.DomainBean;

public class DadosGeraisSegundaViaPrintDTO extends DomainBean implements
		ClienteCobrancaPrintDTO, ClienteSegundaViaPrintDTO,
		FichaArrecadacaoPrintDTO, DemonstrativoFinanceiroPrintDTO {

	private static final long serialVersionUID = 5130641421430619234L;

	public DadosGeraisSegundaViaPrintDTO() {
		super(PRIMARY_KEY);
	}

	public static final String PRIMARY_KEY = "pk fake";
	private String prefixo;
	private Long codigoLote;
	private String nomeClienteCobranca;
	private String enderecoCobranca;
	private String bairroCobranca;
	private String cidadeCobranca;
	private String estadoCobranca;
	private String cepCobranca;
	private Long identificadorBoleto;
	private Long identificadorBoletoOrigem;
	private String codigoClienteNETFormatado;
	private String codigoClienteNET;
	private String codigoClienteEBT;
	private String cpfCnpjCobranca;
	private String inscricaoEstadualCobranca;
	private Date dataProcessamentoBoleto;
	private Date dataVencimentoBoleto;
	private Date dataVencimentoBoletoOrigem;
	private Double valorCobrado;
	private String codigoDeBarras;
	private String linhaDigitavel;
	private String mesAnoReferencia;
	private String hashCode;
	private Double valorTotalNotaFiscal;
	private String codOperadora;
	private Boolean isOperadoraNet;
	private String codigoBanco;
	private String formPagto;
	private String formaEnvioFatura;
	private String base;
	private Integer cidContrato;
	private Integer tipoCobranca;
	private String emailOrigem;
	private String emailDestino;
	private String linkNaoOptante;
	private Date mesVencimento;	
	//QRCODE PIX
	private String pixHashcode;
	
	private String logradouroCobranca;
	private Integer numeroCobranca;
	private String complementoCobranca;
	
	public String getCodigoBanco() {
		return codigoBanco;
	}

	public void setCodigoBanco(String codigoBanco) {
		this.codigoBanco = codigoBanco;
	}
	
	public String getCodOperadora() {
		return codOperadora;
	}

	public void setCodOperadora(String codOperadora) {
		this.codOperadora = codOperadora;
	}

	public String getCpfCnpjCobranca() {
		return cpfCnpjCobranca;
	}

	public void setCpfCnpjCobranca(String cpfCnpjCobranca) {
		this.cpfCnpjCobranca = cpfCnpjCobranca;
	}

	public Double getValorTotalNotaFiscal() {
		return valorTotalNotaFiscal;
	}

	public void setValorTotalNotaFiscal(Double valorTotalNotaFiscal) {
		this.valorTotalNotaFiscal = valorTotalNotaFiscal;
	}

	public String getPrefixo() {
		return prefixo;
	}

	public void setPrefixo(String prefixo) {
		this.prefixo = prefixo;
	}

	public Long getCodigoLote() {
		return codigoLote;
	}

	public void setCodigoLote(Long codigoLote) {
		this.codigoLote = codigoLote;
	}

	public String getNomeClienteCobranca() {
		return nomeClienteCobranca;
	}

	public void setNomeClienteCobranca(String nomeClienteCobranca) {
		this.nomeClienteCobranca = nomeClienteCobranca;
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

	public String getCepCobranca() {
		return cepCobranca;
	}

	public void setCepCobranca(String cepCobranca) {
		this.cepCobranca = cepCobranca;
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

	public String getCodigoClienteNET() {
		return codigoClienteNET;
	}

	public void setCodigoClienteNET(String codigoClienteNET) {
		this.codigoClienteNET = codigoClienteNET;
	}

	public String getCpfCNPJCobranca() {
		return cpfCnpjCobranca;
	}

	public void setCpfCNPJCobranca(String cpfCNPJCobranca) {
		this.cpfCnpjCobranca = cpfCNPJCobranca;
	}

	public String getInscricaoEstadualCobranca() {
		return inscricaoEstadualCobranca;
	}

	public void setInscricaoEstadualCobranca(String inscricaoEstadualCobranca) {
		this.inscricaoEstadualCobranca = inscricaoEstadualCobranca;
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

	public Date getDataVencimentoBoletoOrigem() {
		return dataVencimentoBoletoOrigem;
	}

	public void setDataVencimentoBoletoOrigem(Date dataVencimentoBoletoOrigem) {
		this.dataVencimentoBoletoOrigem = dataVencimentoBoletoOrigem;
	}

	public Double getValorCobrado() {
		return valorCobrado;
	}

	public void setValorCobrado(Double valorCobrado) {
		this.valorCobrado = valorCobrado;
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

	public String getMesAnoReferencia() {
		return mesAnoReferencia;
	}

	public void setMesAnoReferencia(String mesAnoReferencia) {
		this.mesAnoReferencia = mesAnoReferencia;
	}

	public String getHashCode() {
		return hashCode;
	}

	public void setHashCode(String hashCode) {
		this.hashCode = hashCode;
	}

	public String getCodigoClienteEBT() {
		return codigoClienteEBT;
	}

	public void setCodigoClienteEBT(String codigoClienteEBT) {
		this.codigoClienteEBT = codigoClienteEBT;
	}

	public void setIsOperadoraNet(Boolean isOperadoraNet) {
		this.isOperadoraNet = isOperadoraNet;
	}
	
	public Boolean isOperadoraNet() {
		return "S".equals(isOperadoraNet);
	}

	public String getCodigoClienteNETFormatado() {
		return codigoClienteNETFormatado;
	}

	public void setCodigoClienteNETFormatado(String codigoClienteNETFormatado) {
		this.codigoClienteNETFormatado = codigoClienteNETFormatado;
	}

	public String getFormPagto() {
		
		return formPagto;
	}
	
	public void setFormPagto(String formPagto) {
		this.formPagto = formPagto;
	}

	public String getFormaEnvioFatura() {
		return formaEnvioFatura;
	}

	public void setFormaEnvioFatura(String formaEnvioFatura) {
		this.formaEnvioFatura = formaEnvioFatura;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public Integer getCidContrato() {
		return cidContrato;
	}

	public void setCidContrato(Integer cidContrato) {
		this.cidContrato = cidContrato;
	}

	public Integer getTipoCobranca() {
		return tipoCobranca;
	}

	public void setTipoCobranca(Integer tipoCobranca) {
		this.tipoCobranca = tipoCobranca;
	}

	public String getEmailOrigem() {
		return emailOrigem;
	}

	public void setEmailOrigem(String emailOrigem) {
		this.emailOrigem = emailOrigem;
	}

	public String getEmailDestino() {
		return emailDestino;
	}

	public void setEmailDestino(String emailDestino) {
		this.emailDestino = emailDestino;
	}

	public String getLinkNaoOptante() {
		return linkNaoOptante;
	}

	public void setLinkNaoOptante(String linkNaoOptante) {
		this.linkNaoOptante = linkNaoOptante;
	}

	public Date getMesVencimento() {
		return mesVencimento;
	}

	public void setMesVencimento(Date mesVencimento) {
		this.mesVencimento = mesVencimento;
	}
	
	//QRCODE PIX
	public String getPixHashcode() {
		return pixHashcode;
	}
	
	public void setPixHashcode(String pixHashcode) {
		this.pixHashcode = pixHashcode;
	}
	
	public String getLogradouroCobranca() {
		return logradouroCobranca;
	}

	public void setLogradouroCobranca(String logradouroCobranca) {
		this.logradouroCobranca = logradouroCobranca;
	}

	public Integer getNumeroCobranca() {
		return numeroCobranca;
	}

	public void setNumeroCobranca(Integer numeroCobranca) {
		this.numeroCobranca = numeroCobranca;
	}

	public String getComplementoCobranca() {
		return complementoCobranca;
	}

	public void setComplementoCobranca(String complementoCobranca) {
		this.complementoCobranca = complementoCobranca;
	}
	
}
