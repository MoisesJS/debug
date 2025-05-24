package br.com.netservicos.novosms.emissao.dto;

import java.math.BigDecimal;
import java.util.Date;

import br.com.netservicos.framework.core.bean.DomainBean;

public class DadosItensNFDTO extends DomainBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String PRIMARY_KEY = "pk fake";
	
	public DadosItensNFDTO() {
		super(PRIMARY_KEY);	
	}
	
	private String nomeAgrupamento;
	private Double valorSomaItensAgrupamento;
	private String descricao;
	private Double valorBrutoItem;
	private Double valorIcmsItem;
	private Double valorPisItem;
	private Double valorCofinsItem;
	private BigDecimal aliquota;
	

	public BigDecimal getAliquota() {
		return aliquota;
	}
	public void setAliquota(BigDecimal aliquota) {
		this.aliquota = aliquota;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getNomeAgrupamento() {
		return nomeAgrupamento;
	}
	public void setNomeAgrupamento(String nomeAgrupamento) {
		this.nomeAgrupamento = nomeAgrupamento;
	}
	public Double getValorBrutoItem() {
		return valorBrutoItem;
	}
	public void setValorBrutoItem(Double valorBrutoItem) {
		this.valorBrutoItem = valorBrutoItem;
	}
	public Double getValorSomaItensAgrupamento() {
		return valorSomaItensAgrupamento;
	}
	public void setValorSomaItensAgrupamento(Double valorSomaItensAgrupamento) {
		this.valorSomaItensAgrupamento = valorSomaItensAgrupamento;
	}
	public Double getValorIcmsItem() {
		return valorIcmsItem;
	}
	public void setValorIcmsItem(Double valorIcmsItem) {
		this.valorIcmsItem = valorIcmsItem;
	}
	public Double getValorPisItem() {
		return valorPisItem;
	}
	public void setValorPisItem(Double valorPisItem) {
		this.valorPisItem = valorPisItem;
	}
	public Double getValorCofinsItem() {
		return valorCofinsItem;
	}
	public void setValorCofinsItem(Double valorCofinsItem) {
		this.valorCofinsItem = valorCofinsItem;
	}
	
	
	
	

	
}
