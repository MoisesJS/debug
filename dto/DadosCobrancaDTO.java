package br.com.netservicos.novosms.emissao.dto;

import br.com.netservicos.framework.core.bean.DomainBean;

public class DadosCobrancaDTO extends DomainBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String PRIMARY_KEY = "pk fake";
	
	private Long idCobranca;
	private Double valorCobranca;
	
	public DadosCobrancaDTO() {
		super(PRIMARY_KEY);	
	}

	public Double getValorCobranca() {
		return valorCobranca;
	}

	public void setValorCobranca(Double valorCobranca) {
		this.valorCobranca = valorCobranca;
	}

	public Long getIdCobranca() {
		return idCobranca;
	}

	public void setIdCobranca(Long idCobranca) {
		this.idCobranca = idCobranca;
	}
	
}
