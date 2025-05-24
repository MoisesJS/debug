package br.com.netservicos.novosms.emissao.dto;

import java.util.List;

import br.com.netservicos.novosms.emissao.printhouse.MensagensNotaFiscalDTO;

public interface ClienteNotaFiscalPrintDTO extends ClientePrintDTO {
	static final String PRIMARY_KEY = "pk fake";

	String getNumeroNotaFiscal();
    String getCfopNotaFiscal();
    String getInscricaoEstadualNotaFiscal();
	Double getValorTotalNotaFiscal();
    String getHashCode();
    String getSerieNotaFiscal();
	String getModeloNotaFiscal();	
	String getNomeEmissorFiscal();
	String getEnderecoEmissorFiscal();
	String getBairroEmissorFiscal();
	String getCidadeEmissorFiscal();
	String getEstadoEmissorFiscal();
	String getCepEmissorFiscal();
	String getCnpjEmissorFiscal();
	String getIeEmissorFiscal();
	String getImEmissorFiscal();
	List<MensagensNotaFiscalDTO> getMensagensNotaFiscal();
	String getMsgTotaisTributosNET();
	
}