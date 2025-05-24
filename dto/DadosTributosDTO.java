package br.com.netservicos.novosms.emissao.dto;

import java.math.BigDecimal;
import java.util.Date;

import br.com.netservicos.framework.core.bean.DomainBean;

public class DadosTributosDTO extends DomainBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String PRIMARY_KEY = "pk fake";
	
	public DadosTributosDTO() {
		super(PRIMARY_KEY);	
	}
	
	private String SIGLATRIBUTO;
	private Double BASECALCULO; 
	private Double  VALORALIQUOTA;
	private Double VALORTRIBUTO;
	private Double VALORTOTAL;
	private Double VALORISENTO;
	private Double VALOROUTROS;

	public Double getVALORALIQUOTA() {
		return VALORALIQUOTA;
	}
	public void setVALORALIQUOTA(Double valoraliquota) {
		VALORALIQUOTA = valoraliquota;
	}
	public Double getBASECALCULO() {
		return BASECALCULO;
	}
	public void setBASECALCULO(Double basecalculo) {
		BASECALCULO = basecalculo;
	}
	public String getSIGLATRIBUTO() {
		return SIGLATRIBUTO;
	}
	public void setSIGLATRIBUTO(String siglatributo) {
		SIGLATRIBUTO = siglatributo;
	}
	public Double getVALORISENTO() {
		return VALORISENTO;
	}
	public void setVALORISENTO(Double valorisento) {
		VALORISENTO = valorisento;
	}
	public Double getVALOROUTROS() {
		return VALOROUTROS;
	}
	public void setVALOROUTROS(Double valoroutros) {
		VALOROUTROS = valoroutros;
	}
	public Double getVALORTOTAL() {
		return VALORTOTAL;
	}
	public void setVALORTOTAL(Double valortotal) {
		VALORTOTAL = valortotal;
	}
	public Double getVALORTRIBUTO() {
		return VALORTRIBUTO;
	}
	public void setVALORTRIBUTO(Double valortributo) {
		VALORTRIBUTO = valortributo;
	}
	
	
	
}
