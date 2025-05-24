package br.com.netservicos.novosms.emissao.dto;

import java.util.Date;

public interface FichaArrecadacaoPrintDTO {
	
	public static final String PRIMARY_KEY = "pk fake";

    Double getValorCobrado();
    String getCodigoDeBarras();
    String getLinhaDigitavel();
    String getNomeClienteCobranca();
    String  getCodigoClienteNET();
    String getMesAnoReferencia();
    Date getDataVencimentoBoleto();
	//QRCODE PIX
    String getPixHashcode();
    
}
