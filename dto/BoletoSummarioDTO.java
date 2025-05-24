package br.com.netservicos.novosms.emissao.dto;

import java.util.Date;

import br.com.netservicos.framework.core.bean.DomainBean;

public class BoletoSummarioDTO extends DomainBean {
	public BoletoSummarioDTO() {
		super(PRIMARY_KEY);
		// TODO Auto-generated constructor stub
	}

	public static final String PRIMARY_KEY = "pk fake";
	/**
	 * 
	 */
	private static final long serialVersionUID = 2985542756061609155L;
	/** Mapeia o campo TBSMS_BLTO.ID_BLTO */
	private Long idBoleto;
	/** Mapeia o campo TBSMS_BLTO.DT_VCTO */
	private Date dataVencimento;
	/** Mapeia o campo TBSMS_BLTO.CID_CONTRATO */
	private String cidContrato;

	public Long getIdBoleto() {
		return idBoleto;
	}

	public void setIdBoleto(Long idBoleto) {
		this.idBoleto = idBoleto;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public String getCidContrato() {
		return cidContrato;
	}

	public void setCidContrato(String cidContrato) {
		this.cidContrato = cidContrato;
	}

}
