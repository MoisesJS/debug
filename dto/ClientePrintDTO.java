package br.com.netservicos.novosms.emissao.dto;

import java.util.Date;

public interface ClientePrintDTO {
	
	public static final String PRIMARY_KEY = "pk fake";

    String getEnderecoNotaFiscal();
    String getBairroNotaFiscal();
    String getCidadeNotaFiscal();
    String getEstadoNotaFiscal();
    String getCepNotaFiscal();
    String getCpfCnpjNotaFiscal();
    String getNomeClienteNotaFiscal();
    String getCodigoClienteNET();
    Date getDataVencimentoBoleto();
    String getMesAnoReferencia();
    Date getDataEmissaoNotaFiscal();
    String getCodOperadora(); 
    String getCodigoClienteEBT();
    String getNumeroNotaFiscal();
    String getDescServico();
    Boolean isOperadoraNet(); 
	
	
}
