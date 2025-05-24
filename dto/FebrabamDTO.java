package br.com.netservicos.novosms.emissao.dto;

import br.com.netservicos.framework.core.bean.DomainBean;

public class FebrabamDTO extends DomainBean {

	
	public static final String PRIMARY_KEY = "pk fake";

	public FebrabamDTO() {
		super(PRIMARY_KEY);   
	}


	private static final long serialVersionUID = -8387763187719732760L;
		
	
	private String febraban;
	private Long idBoleto; 
	private String parametro;


	public String getParametro() {
		return parametro;
	}


	public void setParametro(String parametro) {
		this.parametro = parametro;
	}


	public Long getIdBoleto() {
		return idBoleto;
	}


	public void setIdBoleto(Long idBoleto) {
		this.idBoleto = idBoleto;
	}


	public String getFebraban() {
		return febraban;
	}


	public void setFebraban(String febraban) {
		this.febraban = febraban;
	}
	

}

	