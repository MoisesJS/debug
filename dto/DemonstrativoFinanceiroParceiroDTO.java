package br.com.netservicos.novosms.emissao.dto;

import java.math.BigDecimal;

import br.com.netservicos.framework.core.bean.DomainBean;

public class DemonstrativoFinanceiroParceiroDTO extends DomainBean {

	
	public static final String PRIMARY_KEY = "pk fake";
	
	
	
	private static final long serialVersionUID = 1L; 
		
	private Long idBoleto;
	private String GRUPO_DEMONST_FINANC;
	private String DESCRICAOITEMDEMONST_FINANC;
	private String DURACAO;
	private Double VALORITEMDEMONST_FINANC;
	private Long ID_PARCEIRO;

	
	public DemonstrativoFinanceiroParceiroDTO() {
		super(PRIMARY_KEY);
	}
	
	public String getDESCRICAOITEMDEMONST_FINANC() {
		return DESCRICAOITEMDEMONST_FINANC;
	}

	public void setDESCRICAOITEMDEMONST_FINANC(String descricaoitemdemonst_financ) {
		DESCRICAOITEMDEMONST_FINANC = descricaoitemdemonst_financ;
	}

	public String getDURACAO() {
		
		if ( DURACAO == null){  
			return new String(" ");
		}else{
			return DURACAO;
		}
			
	}

	public void setDURACAO(String duracao) {
		DURACAO = duracao;
	}

	public String getGRUPO_DEMONST_FINANC() {
		return GRUPO_DEMONST_FINANC;
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

	public Long getID_PARCEIRO() {
		return ID_PARCEIRO;
	}

	public void setID_PARCEIRO(Long id_parceiro) {
		ID_PARCEIRO = id_parceiro;
	}
}
