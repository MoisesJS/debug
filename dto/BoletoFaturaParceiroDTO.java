package br.com.netservicos.novosms.emissao.dto;

import java.util.Date;

import br.com.netservicos.framework.core.bean.DomainBean;

public class BoletoFaturaParceiroDTO extends DomainBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6760498682411203419L;

	private Long rownum;
	private Long idBoleto;
	private Integer ccCep;
	private Long idLote;
	private Date dtVencimento;

	public BoletoFaturaParceiroDTO(String primaryKeyName) {
		super(primaryKeyName);
		// TODO Auto-generated constructor stub
	}

	public Long getRownum() {
		return rownum;
	}

	public void setRownum(Long rownum) {
		this.rownum = rownum;
	}

	public Long getIdBoleto() {
		return idBoleto;
	}

	public void setIdBoleto(Long idBoleto) {
		this.idBoleto = idBoleto;
	}

	public Integer getCcCep() {
		return ccCep;
	}

	public void setCcCep(Integer ccCep) {
		this.ccCep = ccCep;
	}

	public Long getIdLote() {
		return idLote;
	}

	public void setIdLote(Long idLote) {
		this.idLote = idLote;
	}

	public Date getDtVencimento() {
		return dtVencimento;
	}

	public void setDtVencimento(Date dtVencimento) {
		this.dtVencimento = dtVencimento;
	}

}
