package br.com.netservicos.novosms.emissao.dto;

import java.io.Serializable;
import java.util.Date;

public class FaturaEmailRejeitadoDTO implements Serializable {

	private static final long serialVersionUID = 8242718670691996068L;

	private Date dtRejeicao;
	private Long numContrato;
	private String nomeTitular;
	private String dsEmailRejeitado;
	private Long idBoleto;
	private Date dtVencto;
	private String fcResolvido;
	private String dsFormaEnvioFatura;
	private String descricaoCobranca;
	private String dsCodErroRejEmail;
	private Integer idTipoResolEmailRej;
	private String dsTipoResolEmailRej;
	private String cdOperadora;
	private Long idFaturaEmailRejeitado;
	public Date getDtRejeicao() {
		return dtRejeicao;
	}
	public void setDtRejeicao(Date dtRejeicao) {
		this.dtRejeicao = dtRejeicao;
	}
	public Long getNumContrato() {
		return numContrato;
	}
	public void setNumContrato(Long numContrato) {
		this.numContrato = numContrato;
	}
	public String getNomeTitular() {
		return nomeTitular;
	}
	public void setNomeTitular(String nomeTitular) {
		this.nomeTitular = nomeTitular;
	}
	public String getDsEmailRejeitado() {
		return dsEmailRejeitado;
	}
	public void setDsEmailRejeitado(String dsEmailRejeitado) {
		this.dsEmailRejeitado = dsEmailRejeitado;
	}
	public Long getIdBoleto() {
		return idBoleto;
	}
	public void setIdBoleto(Long idBoleto) {
		this.idBoleto = idBoleto;
	}
	public Date getDtVencto() {
		return dtVencto;
	}
	public void setDtVencto(Date dtVencto) {
		this.dtVencto = dtVencto;
	}
	public String getFcResolvido() {
		return fcResolvido;
	}
	public void setFcResolvido(String fcResolvido) {
		this.fcResolvido = fcResolvido;
	}
	public String getDsFormaEnvioFatura() {
		return dsFormaEnvioFatura;
	}
	public void setDsFormaEnvioFatura(String dsFormaEnvioFatura) {
		this.dsFormaEnvioFatura = dsFormaEnvioFatura;
	}
	public String getDescricaoCobranca() {
		return descricaoCobranca;
	}
	public void setDescricaoCobranca(String descricaoCobranca) {
		this.descricaoCobranca = descricaoCobranca;
	}
	public String getDsCodErroRejEmail() {
		return dsCodErroRejEmail;
	}
	public void setDsCodErroRejEmail(String dsCodErroRejEmail) {
		this.dsCodErroRejEmail = dsCodErroRejEmail;
	}
	public Integer getIdTipoResolEmailRej() {
		return idTipoResolEmailRej;
	}
	public void setIdTipoResolEmailRej(Integer idTipoResolEmailRej) {
		this.idTipoResolEmailRej = idTipoResolEmailRej;
	}
	public String getDsTipoResolEmailRej() {
		return dsTipoResolEmailRej;
	}
	public void setDsTipoResolEmailRej(String dsTipoResolEmailRej) {
		this.dsTipoResolEmailRej = dsTipoResolEmailRej;
	}
	public String getCdOperadora() {
		return cdOperadora;
	}
	public void setCdOperadora(String cdOperadora) {
		this.cdOperadora = cdOperadora;
	}
	public Long getIdFaturaEmailRejeitado() {
		return idFaturaEmailRejeitado;
	}
	public void setIdFaturaEmailRejeitado(Long idFaturaEmailRejeitado) {
		this.idFaturaEmailRejeitado = idFaturaEmailRejeitado;
	}
	
	
}
