package br.com.netservicos.novosms.emissao.dto;


public interface ClienteCobrancaPrintDTO {
	public static final String PRIMARY_KEY = "pk fake";

	String getPrefixo();

	Long getCodigoLote();

	String getNomeClienteCobranca();

	String getEnderecoCobranca();

	String getBairroCobranca();

	String getCidadeCobranca();

	String getEstadoCobranca();

	String getCepCobranca();

	Long getIdentificadorBoleto();

	Long getIdentificadorBoletoOrigem();

	String getCodigoClienteNET();

}
