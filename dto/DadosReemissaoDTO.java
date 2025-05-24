package br.com.netservicos.novosms.emissao.dto;

import java.util.Date;

import br.com.netservicos.framework.core.bean.DomainBean;

public class DadosReemissaoDTO extends DomainBean {

	
	public static final String PRIMARY_KEY = "pk fake";
	
	public DadosReemissaoDTO() {
		super(PRIMARY_KEY);
	}
	
	
	private Long idCobranacaNotaFiscal;
	private String situacaoDoc;
	private Long idBoleto; 
	
	

	public Long getIdBoleto() {
		return idBoleto;
	}



	public void setIdBoleto(Long idBoleto) {
		this.idBoleto = idBoleto;
	}



	public Long getIdCobranacaNotaFiscal() {
		return idCobranacaNotaFiscal;
	}



	public void setIdCobranacaNotaFiscal(Long idCobranacaNotaFiscal) {
		this.idCobranacaNotaFiscal = idCobranacaNotaFiscal;
	}



	public String getSituacaoDoc() {
		return situacaoDoc;
	}



	public void setSituacaoDoc(String situacaoDoc) {
		this.situacaoDoc = situacaoDoc;
	}


	
}
