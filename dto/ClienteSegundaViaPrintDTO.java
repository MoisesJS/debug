package br.com.netservicos.novosms.emissao.dto;

import java.util.Date;

public interface ClienteSegundaViaPrintDTO {
	public static final String PRIMARY_KEY = "pk fake";

    String getCodigoClienteNET();
	String getCpfCNPJCobranca();
    String getInscricaoEstadualCobranca();
    Date getDataProcessamentoBoleto();
	Long getIdentificadorBoleto();
    Date getDataVencimentoBoleto();
    Date getDataVencimentoBoletoOrigem();
	String getCodigoClienteNETFormatado();
	String getFormPagto();
	String getLinhaDigitavel();
	String getCodigoDeBarras(); 
	Double getValorCobrado(); 
	String getPixHashcode();
    
}
