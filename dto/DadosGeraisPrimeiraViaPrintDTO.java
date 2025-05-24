package br.com.netservicos.novosms.emissao.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.util.CollectionUtils;

import br.com.netservicos.framework.core.bean.DomainBean;
import br.com.netservicos.novosms.emissao.printhouse.MensagensNotaFiscalDTO;

public class DadosGeraisPrimeiraViaPrintDTO extends DomainBean implements ClienteCobrancaPrintDTO,
		ClienteNotaFiscalPrintDTO, FichaArrecadacaoPrintDTO, DemonstrativoFinanceiroPrintDTO {

	public Long getIdCobranca() {
		return idCobranca;
	}

	public void setIdCobranca(Long idCobranca) {
		this.idCobranca = idCobranca;
	}

	public DadosGeraisPrimeiraViaPrintDTO() {
		super(PRIMARY_KEY);
	}

	public static final String PRIMARY_KEY = "pk fake";

	private static final long serialVersionUID = -948166961275708260L;

	private String prefixo;

	private Long codigoLote;

	private String nomeClienteCobranca;

	private String enderecoCobranca;

	private String bairroCobranca;

	private String estadoCobranca;

	private String cidadeCobranca;

	private String cepCobranca;

	private String numeroNotaFiscal;

	private String cfopNotaFiscal;

	private String inscricaoEstadualNotaFiscal;

	private String enderecoNotaFiscal;

	private String bairroNotaFiscal;

	private String cidadeNotaFiscal;

	private String estadoNotaFiscal;

	private String cepNotaFiscal;

	private String cpfCnpjNotaFiscal;

	private String nomeClienteNotaFiscal;

	private String codigoClienteEBT;

	private String codigoBanco;
	
	private String nomeBanco;

	private String codigoClienteNET;

	private Date dataVencimentoBoleto;

	private String serieNotaFiscal;

	private String mesAnoReferencia;

	private Date dataEmissaoNotaFiscal;

	private Double valorCobrado;

	private String codigoDeBarras;

	private String linhaDigitavel;

	private Double valorTotalNotaFiscal;

	private String hashCode;

	private Long identificadorBoleto;

	private Long identificadorBoletoOrigem;

	private String modeloNotaFiscal;

	private Long idNotaFiscal;

	private String codOperadora;

	private String descServico;

	private String numContrato;

	private String fc_boleto_consolidado;

	private Long idBoleto;

	private String isOperadoraNet;

	private Long idCobrancaNotaFiscal;

	private Long idCobranca;
	
	private Double valorCobranca;
	
	private String formPagto;
	
	private Integer formaEnvioFatura;
	
	private String base;
	
	private String cidContrato;
	
	private Integer tipoCobranca;
	
	private String emailOrigem;
	
	private String emailDestino;
	
	private String linkNaoOptante;
	
	private String mesVencimento;	

	private String declaracaoAnualDebitoNET;

	private String declaracaoAnualDebitoEBT;

	private String migracaoPadPol;

	private String identificadorMensagem;
	private String nomeEmissorFiscal;
	private String enderecoEmissorFiscal;
	private String bairroEmissorFiscal;
	private String cidadeEmissorFiscal;
	private String estadoEmissorFiscal;
	private String cepEmissorFiscal;
	private String cnpjEmissorFiscal;

	private String ieEmissorFiscal;
	private String imEmissorFiscal;
	private List<MensagensNotaFiscalDTO> mensagensNotaFiscal;	
	private String msgTotaisTributosNET; 
	
	private Integer idTipoBoletoAvulso;
	private String tipoBoletoAvulso;
	private String agencia;
	
	//NSMSP_161258_NI_001\JAVA
	private MensagemClaroClubeDTO mensagemClaroClubeDTO;
	
	//QRCODE PIX
	private String pixHashcode;
	
	private String logradouroCobranca;
	private Integer numeroCobranca;
	private String complementoCobranca;
	
	public void setFormPagto(String formPagto) {
		this.formPagto = formPagto;
	}

	public String getDescServico() {
		return descServico;
	}

	public void setDescServico(String descServico) {
		this.descServico = descServico;
	}

	public void setPrefixo(String prefixo) {
		this.prefixo = prefixo;
	}

	public String getPrefixo() {
		return prefixo;
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

	public String getNumeroNotaFiscal() {
		return numeroNotaFiscal;
	}

	public void setNumeroNotaFiscal(String numeroNotaFiscal) {
		this.numeroNotaFiscal = numeroNotaFiscal;
	}

	public String getCfopNotaFiscal() {
		return cfopNotaFiscal;
	}

	public void setCfopNotaFiscal(String cfopNotaFiscal) {
		this.cfopNotaFiscal = cfopNotaFiscal;
	}

	public String getInscricaoEstadualNotaFiscal() {
		return inscricaoEstadualNotaFiscal;
	}

	public void setInscricaoEstadualNotaFiscal(String inscricaoEstadualNotaFiscal) {
		this.inscricaoEstadualNotaFiscal = inscricaoEstadualNotaFiscal;
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

	public String getCpfCnpjNotaFiscal() {
		return cpfCnpjNotaFiscal;
	}

	public void setCpfCnpjNotaFiscal(String cpfCnpjNotaFiscal) {
		this.cpfCnpjNotaFiscal = cpfCnpjNotaFiscal;
	}

	public String getNomeClienteNotaFiscal() {
		return nomeClienteNotaFiscal;
	}

	public void setNomeClienteNotaFiscal(String nomeClienteNotaFiscal) {
		this.nomeClienteNotaFiscal = nomeClienteNotaFiscal;
	}

	public String getCodigoClienteNET() {
		return codigoClienteNET;
	}

	public void setCodigoClienteNET(String codigoClienteNET) {
		this.codigoClienteNET = codigoClienteNET;
	}

	public Date getDataVencimentoBoleto() {
		return dataVencimentoBoleto;
	}

	public void setDataVencimentoBoleto(Date dataVencimentoBoleto) {
		this.dataVencimentoBoleto = dataVencimentoBoleto;
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

	public Date getDataEmissaoNotaFiscal() {
		return dataEmissaoNotaFiscal;
	}

	public void setDataEmissaoNotaFiscal(Date dataEmissaoNotaFiscal) {
		this.dataEmissaoNotaFiscal = dataEmissaoNotaFiscal;
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

	public Double getValorTotalNotaFiscal() {
		return valorTotalNotaFiscal;
	}

	public void setValorTotalNotaFiscal(Double valorTotalNotaFiscal) {
		this.valorTotalNotaFiscal = valorTotalNotaFiscal;
	}

	public String getHashCode() {
		return hashCode;
	}

	public void setHashCode(String hashCode) {
		this.hashCode = hashCode;
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

	public String getModeloNotaFiscal() {
		return modeloNotaFiscal;
	}

	public void setModeloNotaFiscal(String modeloNotaFiscal) {
		this.modeloNotaFiscal = modeloNotaFiscal;
	}

	public String getCodigoClienteEBT() {
		return codigoClienteEBT;
	}

	public void setCodigoClienteEBT(String codigoClienteEBT) {
		this.codigoClienteEBT = codigoClienteEBT;
	}

	public Long getIdNotaFiscal() {
		return idNotaFiscal;
	}

	public void setIdNotaFiscal(Long idNotaFiscal) {
		this.idNotaFiscal = idNotaFiscal;
	}

	public String getCodOperadora() {
		return codOperadora;
	}

	public void setCodOperadora(String codOperadora) {
		this.codOperadora = codOperadora;
	}

	public String getFc_boleto_consolidado() {
		return fc_boleto_consolidado;
	}

	public void setFc_boleto_consolidado(String fc_boleto_consolidado) {
		this.fc_boleto_consolidado = fc_boleto_consolidado;
	}

	public String getNumContrato() {
		return numContrato;
	}

	public void setNumContrato(String numContrato) {
		this.numContrato = numContrato;
	}

	public Long getIdBoleto() {
		return idBoleto;
	}

	public void setIdBoleto(Long idBoleto) {
		this.idBoleto = idBoleto;
	}

	public Boolean isOperadoraNet() {
		return isOperadoraNet.equals("S");
	}

	public Long getIdCobrancaNotaFiscal() {
		return idCobrancaNotaFiscal;
	}

	public void setIdCobrancaNotaFiscal(Long idCobrancaNotaFiscal) {
		this.idCobrancaNotaFiscal = idCobrancaNotaFiscal;
	}

	public void setIsOperadoraNet(String isOperadoraNet) {
		this.isOperadoraNet = isOperadoraNet;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public Double getValorCobranca() {
		return valorCobranca;
	}

	public void setValorCobranca(Double valorCobranca) {
		this.valorCobranca = valorCobranca;
	}
			
	public String getCodigoBanco() {
		return codigoBanco;
	}

	public void setCodigoBanco(String codigoBanco) {
		this.codigoBanco = codigoBanco;
	}
	
	public String getNomeBanco(){
	    return nomeBanco;
    }
	
	public void setNomeBanco(String nomeBanco){
	    this.nomeBanco = nomeBanco;
    }

	public String getFormPagto() {
		
		return this.formPagto;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public String getCidContrato() {
		return cidContrato;
	}

	public void setCidContrato(String cidContrato) {
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

	public String getMesVencimento() {
		return mesVencimento;
	}

	public void setMesVencimento(String mesVencimento) {
		this.mesVencimento = mesVencimento;
	}

	public Integer getFormaEnvioFatura() {
		return formaEnvioFatura;
	}

	public void setFormaEnvioFatura(Integer formaEnvioFatura) {
		this.formaEnvioFatura = formaEnvioFatura;
	}

        public String getDeclaracaoAnualDebitoNET() {
		return declaracaoAnualDebitoNET;
	}

	public void setDeclaracaoAnualDebitoNET(String declaracaoAnualDebitoNET) {
		this.declaracaoAnualDebitoNET = declaracaoAnualDebitoNET;
	}

	public String getDeclaracaoAnualDebitoEBT() {
		return declaracaoAnualDebitoEBT;
	}

	public void setDeclaracaoAnualDebitoEBT(String declaracaoAnualDebitoEBT) {
		this.declaracaoAnualDebitoEBT = declaracaoAnualDebitoEBT;
	}

	public String getMigracaoPadPol() {
		return migracaoPadPol;
	}

	public void setMigracaoPadPol(String migracaoPadPol) {
		this.migracaoPadPol = migracaoPadPol;
	}

	public String getIdentificadorMensagem() {
		return identificadorMensagem;
	}

	public void setIdentificadorMensagem(String identificadorMensagem) {
		this.identificadorMensagem = identificadorMensagem;
	}
	public String getNomeEmissorFiscal() {
		return nomeEmissorFiscal;
	}
	public void setNomeEmissorFiscal(String nomeEmissorFiscal) {
		this.nomeEmissorFiscal = nomeEmissorFiscal;
	}
	public String getEnderecoEmissorFiscal() {
		return enderecoEmissorFiscal;
	}
	public void setEnderecoEmissorFiscal(String enderecoEmissorFiscal) {
		this.enderecoEmissorFiscal = enderecoEmissorFiscal;
	}
	public String getBairroEmissorFiscal() {
		return bairroEmissorFiscal;
	}
	public void setBairroEmissorFiscal(String bairroEmissorFiscal) {
		this.bairroEmissorFiscal = bairroEmissorFiscal;
	}
	public String getCidadeEmissorFiscal() {
		return cidadeEmissorFiscal;
	}
	public void setCidadeEmissorFiscal(String cidadeEmissorFiscal) {
		this.cidadeEmissorFiscal = cidadeEmissorFiscal;
	}
	public String getEstadoEmissorFiscal() {
		return estadoEmissorFiscal;
	}
	public void setEstadoEmissorFiscal(String estadoEmissorFiscal) {
		this.estadoEmissorFiscal = estadoEmissorFiscal;
	}
	public String getCepEmissorFiscal() {
		return cepEmissorFiscal;
	}
	public void setCepEmissorFiscal(String cepEmissorFiscal) {
		this.cepEmissorFiscal = cepEmissorFiscal;
	}
	public String getCnpjEmissorFiscal() {
		return cnpjEmissorFiscal;
	}
	public void setCnpjEmissorFiscal(String cnpjEmissorFiscal) {
		this.cnpjEmissorFiscal = cnpjEmissorFiscal;
	}
	public String getIeEmissorFiscal() {
		return ieEmissorFiscal;
	}
	public void setIeEmissorFiscal(String ieEmissorFiscal) {
		this.ieEmissorFiscal = ieEmissorFiscal;
	}
	public String getImEmissorFiscal() {
		return imEmissorFiscal;
	}
	public void setImEmissorFiscal(String imEmissorFiscal) {
		this.imEmissorFiscal = imEmissorFiscal;
	}
	
	public List<MensagensNotaFiscalDTO> getMensagensNotaFiscal() {
		if (CollectionUtils.isEmpty(mensagensNotaFiscal)) {
			this.mensagensNotaFiscal = new ArrayList<MensagensNotaFiscalDTO>();
		}
		return mensagensNotaFiscal;
	}

	public void setMensagensNotaFiscal(List<MensagensNotaFiscalDTO> mensagensNotaFiscal) {
		this.mensagensNotaFiscal = mensagensNotaFiscal;
	}
	
	public String getMsgTotaisTributosNET() {
		return msgTotaisTributosNET;
	}

	public void setMsgTotaisTributosNET(String msgTotaisTributosNET) {
		this.msgTotaisTributosNET = msgTotaisTributosNET;
	}
	
	public Integer getIdTipoBoletoAvulso(){
	    return idTipoBoletoAvulso;
    }
	
	public void setIdTipoBoletoAvulso(Integer idTipoBoletoAvulso){
	    this.idTipoBoletoAvulso = idTipoBoletoAvulso;
    }
	
	public String getTipoBoletoAvulso(){
	    return tipoBoletoAvulso;
    }
	
	public void setTipoBoletoAvulso(String tipoBoletoAvulso){
	    this.tipoBoletoAvulso = tipoBoletoAvulso;
    }
	
	public String getAgencia(){
	    return agencia;
    }
	
	public void setAgencia(String agencia){
	    this.agencia = agencia;
    }

	/**
	 * @return the mensagemClaroClubeDTO
	 */
	public MensagemClaroClubeDTO getMensagemClaroClubeDTO() {
		return mensagemClaroClubeDTO;
	}

	/**
	 * @param mensagemClaroClubeDTO the mensagemClaroClubeDTO to set
	 */
	public void setMensagemClaroClubeDTO(MensagemClaroClubeDTO mensagemClaroClubeDTO) {
		this.mensagemClaroClubeDTO = mensagemClaroClubeDTO;
	}
	
	//GB PIX
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
