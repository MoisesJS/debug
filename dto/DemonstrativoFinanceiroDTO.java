package br.com.netservicos.novosms.emissao.dto;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import br.com.netservicos.framework.core.bean.DomainBean;

public class DemonstrativoFinanceiroDTO extends DomainBean {

	public static final String PRIMARY_KEY = "pk fake";

	private static final long serialVersionUID = 1L;

	private Long idBoleto;

	private String GRUPO_DEMONST_FINANC;
	
	private String SUBGRUPO_DEMONST_FINANC;
	/**
	 * Indica se sera exibido um subgrupo na fatura.
	 */
	private String subgrupoAlterProd;

	private String DESCRICAOITEMDEMONST_FINANC;

	private Double VALORITEMDEMONST_FINANC;
	
	private Integer TAMANHO;

	public Integer getTAMANHO() {
		return TAMANHO;
	}

	public void setTAMANHO(Integer tamanho) {
		TAMANHO = tamanho;
	}

	public DemonstrativoFinanceiroDTO() {
		super(PRIMARY_KEY);
	}

	public String getDESCRICAOITEMDEMONST_FINANC() {
		return DESCRICAOITEMDEMONST_FINANC;
	}

	public void setDESCRICAOITEMDEMONST_FINANC(String descricaoitemdemonst_financ) {
		DESCRICAOITEMDEMONST_FINANC = descricaoitemdemonst_financ;
	}

	public String getGRUPO_DEMONST_FINANC() {
		return GRUPO_DEMONST_FINANC;
	}

	public String getSUBGRUPO_DEMONST_FINANC() {
		return SUBGRUPO_DEMONST_FINANC;
	}

	public void setSUBGRUPO_DEMONST_FINANC(String subgrupo_demonst_financ) {
		SUBGRUPO_DEMONST_FINANC = subgrupo_demonst_financ;
	}

	public void setGRUPO_DEMONST_FINANC(String grupo_demonst_financ) {
		GRUPO_DEMONST_FINANC = grupo_demonst_financ;
	}

	public Long getIdBoleto() {
		return idBoleto;
	}

	public void setIdBoleto(Long idBoleto) {
		this.idBoleto = idBoleto;
	}

	public Double getVALORITEMDEMONST_FINANC() {
		return VALORITEMDEMONST_FINANC;
	}

	public void setVALORITEMDEMONST_FINANC(Double valoritemdemonst_financ) {
		VALORITEMDEMONST_FINANC = valoritemdemonst_financ;
	}

	public String getSubgrupoAlterProd() {
		return subgrupoAlterProd;
	}

	public void setSubgrupoAlterProd(String subgrupoAlterProd) {
		this.subgrupoAlterProd = subgrupoAlterProd;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
