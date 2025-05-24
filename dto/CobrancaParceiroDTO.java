package br.com.netservicos.novosms.emissao.dto;

import br.com.netservicos.framework.core.bean.DomainBean;

public class CobrancaParceiroDTO extends DomainBean {

	private static final long serialVersionUID = 1L;

	/**
	 * Definição da chave primaria do BEAN
	 * @author Carlos Augusto Leite
	 */
	public static final String PRIMARY_KEY = "pk fake";
	
	/**
	 * Construtor padrão do Bean
	 */
	public CobrancaParceiroDTO(){
		super(PRIMARY_KEY);
	}

	/**
	 * Atributos privados do Bean
	 */
	private Double valorFust;
	private Double valorFuntel;

	public Double getValorFust() {
		return valorFust;
	}
	public void setValorFust(Double valorFust) {
		this.valorFust = valorFust;
	}
	public Double getValorFuntel() {
		return valorFuntel;
	}
	public void setValorFuntel(Double valorFuntel) {
		this.valorFuntel = valorFuntel;
	}
}