package br.com.netservicos.novosms.emissao.dto;

import br.com.netservicos.framework.core.bean.DomainBean;

public class DetalhamentoNotaFiscalParceiroDTO extends DomainBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4826419645376456327L;

	private Long rownum;
	private String nomeAgrupamento;
	private Double valorSomaItensAgrupamento;
	private String descricao;
	private Double valorBrutoItem;

	public Long getRownum() {
		return rownum;
	}

	public void setRownum(Long rownum) {
		this.rownum = rownum;
	}

	public String getNomeAgrupamento() {
		return nomeAgrupamento;
	}

	public void setNomeAgrupamento(String nomeAgrupamento) {
		this.nomeAgrupamento = nomeAgrupamento;
	}

	public Double getValorSomaItensAgrupamento() {
		return valorSomaItensAgrupamento;
	}

	public void setValorSomaItensAgrupamento(Double valorSomaItensAgrupamento) {
		this.valorSomaItensAgrupamento = valorSomaItensAgrupamento;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getValorBrutoItem() {
		return valorBrutoItem;
	}

	public void setValorBrutoItem(Double valorBrutoItem) {
		this.valorBrutoItem = valorBrutoItem;
	}

	public DetalhamentoNotaFiscalParceiroDTO(String primaryKeyName) {
		super(primaryKeyName);
		// TODO Auto-generated constructor stub
	}

}
