package br.com.netservicos.novosms.emissao.dto;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import br.com.netservicos.framework.core.bean.DomainBean;

public class DetalhamentoNotaFiscalDTO extends DomainBean {

	private static final long serialVersionUID = -3416724948930985032L;

	private Long idBean;

	private String nmGrupamento;

	private Double vlSomaItemAgrupamento;

	private String descricao;

	private Double vlBrutoItem;
	
	private Double vlIcmsItem;
	
	private Double vlIssItem;
	
	private Double vlPisItem;
	
	private Double vlCofinsItem;

	public static final String PRIMARY_KEY = "idBean";

	public DetalhamentoNotaFiscalDTO() {
		super(PRIMARY_KEY);

	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Long getIdBean() {
		return idBean;
	}

	public void setIdBean(Long idBean) {
		this.idBean = idBean;
	}

	public String getNmGrupamento() {
		return nmGrupamento;
	}

	public void setNmGrupamento(String nmGrupamento) {
		this.nmGrupamento = nmGrupamento;
	}

	public Double getVlBrutoItem() {
		return vlBrutoItem;
	}

	public void setVlBrutoItem(Double vlBrutoItem) {
		this.vlBrutoItem = vlBrutoItem;
	}

	public Double getVlSomaItemAgrupamento() {
		return vlSomaItemAgrupamento;
	}

	public void setVlSomaItemAgrupamento(Double vlSomaItemAgrupamento) {
		this.vlSomaItemAgrupamento = vlSomaItemAgrupamento;
	}

	public Double getVlIcmsItem() {
		return vlIcmsItem;
	}

	public void setVlIcmsItem(Double vlIcmsItem) {
		this.vlIcmsItem = vlIcmsItem;
	}

	public Double getVlIssItem() {
		return vlIssItem;
	}

	public void setVlIssItem(Double vlIssItem) {
		this.vlIssItem = vlIssItem;
	}

	public Double getVlPisItem() {
		return vlPisItem;
	}

	public void setVlPisItem(Double vlPisItem) {
		this.vlPisItem = vlPisItem;
	}

	public Double getVlCofinsItem() {
		return vlCofinsItem;
	}

	public void setVlCofinsItem(Double vlCofinsItem) {
		this.vlCofinsItem = vlCofinsItem;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
