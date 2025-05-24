package br.com.netservicos.novosms.emissao.dto;

public interface DemonstrativoFinanceiroPrintDTO {

	public static final String PRIMARY_KEY = "pk fake";
    Double getValorCobrado();
    Boolean isOperadoraNet();  
    String getCodigoClienteNET();
    String getCodOperadora();
	

}

