package br.com.netservicos.novosms.emissao.printhouse;

import br.com.netservicos.novosms.emissao.constants.PrintHouseConstants;

public class MensagensNotaFiscalDTO {
 // PK
	public static final String PRIMARY_KEY = "pk fake";
	//Dados das mensagens das notas fiscais
	Long idCobranca;
	String codigoOperadoraMensagemNotaFiscal;
	String prefixoMensagemNotaFiscal;
	String setorizacaoMensagemNotaFiscal;
	String mensagemNotaFiscal;
	
	
	public String getCodigoOperadoraMensagemNotaFiscal() {
		return codigoOperadoraMensagemNotaFiscal;
	}
	public void setCodigoOperadoraMensagemNotaFiscal(
			String codigoOperadoraMensagemNotaFiscal) {
		this.codigoOperadoraMensagemNotaFiscal = codigoOperadoraMensagemNotaFiscal;
	}
	public String getPrefixoMensagemNotaFiscal() {
		return prefixoMensagemNotaFiscal;
	}
	public void setPrefixoMensagemNotaFiscal(String prefixoMensagemNotaFiscal) {
		this.prefixoMensagemNotaFiscal = prefixoMensagemNotaFiscal;
	}
	public String getSetorizacaoMensagemNotaFiscal() {
		return setorizacaoMensagemNotaFiscal;
	}
	public void setSetorizacaoMensagemNotaFiscal(
			String setorizacaoMensagemNotaFiscal) {
		this.setorizacaoMensagemNotaFiscal = setorizacaoMensagemNotaFiscal;
	}
	public String getMensagemNotaFiscal() {
		return mensagemNotaFiscal;
	}
	public void setMensagemNotaFiscal(String mensagemNotaFiscal) {
		this.mensagemNotaFiscal = mensagemNotaFiscal;
	}
	public Long getIdCobranca() {
		return idCobranca;
	}
	public void setIdCobranca(Long idCobranca) {
		this.idCobranca = idCobranca;
	}
	
}
