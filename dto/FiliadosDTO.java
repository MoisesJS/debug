package br.com.netservicos.novosms.emissao.dto;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class FiliadosDTO {
	
	public static final String PRIMARY_KEY = "pk fake";
	private String ID_BLTO;
	private String NOME;
	private String PRODUTO;
	private Double VALOR;
	private Integer TAMANHO;
	private Integer ORDSUB;
	private String ENDERECO;

	public String getENDERECO() {
		return ENDERECO;
	}

	public void setENDERECO(String endereco) {
		ENDERECO = endereco;
	}

	public String getID_BLTO() {
		return ID_BLTO;
	}

	public void setID_BLTO(String id_blto) {
		ID_BLTO = id_blto;
	}

	public String getNOME() {
		return NOME;
	}

	public void setNOME(String nome) {
		NOME = nome;
	}

	public String getPRODUTO() {
		return PRODUTO;
	}

	public void setPRODUTO(String produto) {
		PRODUTO = produto;
	}


	public Double getVALOR() {
		return VALOR;
	}

	public void setVALOR(Double valor) {
		VALOR = valor;
	}

	public Integer getTAMANHO() {
		return TAMANHO;
	}

	public void setTAMANHO(Integer tamanho) {
		TAMANHO = tamanho;
	}

	public Integer getORDSUB() {
		return ORDSUB;
	}

	public void setORDSUB(Integer ordsub) {
		ORDSUB = ordsub;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@Override
	public boolean equals(Object arg0) {
		if (arg0 instanceof FiliadosDTO) {
			FiliadosDTO aux = (FiliadosDTO)arg0;
			if (aux.NOME.equals(this.NOME) && aux.PRODUTO.equals(this.PRODUTO) && aux.VALOR.equals(this.VALOR)) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public int hashCode() {
		if (this.NOME != null)
			return this.NOME.hashCode() + this.PRODUTO.hashCode();
		else
			return super.hashCode();
	}
		
	
}
