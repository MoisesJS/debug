package br.com.netservicos.novosms.emissao.dto;

import java.io.Serializable;

public class ContaContFatCancImpDTO implements Serializable {

	private static final long serialVersionUID = 9039151632744093661L;
	
	private String nmGrpContabilizacao;
	private String descItem;
	private String cnpjSocParc;
	private String descImposto;
	private String cdContaBilledDebito;
	private String cdContaBilledCredito;
	private String cdContaUnbilledDebito;
	private String cdContaUnbilledCredito;
	private String flagNtlc;
	private String flagContRec;
	private String descNatOper;
	private String nomeEmpresa;

	public String getNomeEmpresa() {
		return nomeEmpresa;
	}
	public void setNomeEmpresa(String nomeEmpresa) {
		this.nomeEmpresa = nomeEmpresa;
	}
	public String getNmGrpContabilizacao() {
		return nmGrpContabilizacao;
	}
	public void setNmGrpContabilizacao(String nmGrpContabilizacao) {
		this.nmGrpContabilizacao = nmGrpContabilizacao;
	}
	public String getDescItem() {
		return descItem;
	}
	public void setDescItem(String descItem) {
		this.descItem = descItem;
	}
	public String getCnpjSocParc() {
		return cnpjSocParc;
	}
	public void setCnpjSocParc(String cnpjSocParc) {
		this.cnpjSocParc = cnpjSocParc;
	}
	public String getDescImposto() {
		return descImposto;
	}
	public void setDescImposto(String descImposto) {
		this.descImposto = descImposto;
	}
	public String getCdContaBilledDebito() {
		return cdContaBilledDebito;
	}
	public void setCdContaBilledDebito(String cdContaBilledDebito) {
		this.cdContaBilledDebito = cdContaBilledDebito;
	}
	public String getCdContaBilledCredito() {
		return cdContaBilledCredito;
	}
	public void setCdContaBilledCredito(String cdContaBilledCredito) {
		this.cdContaBilledCredito = cdContaBilledCredito;
	}
	public String getCdContaUnbilledDebito() {
		return cdContaUnbilledDebito;
	}
	public void setCdContaUnbilledDebito(String cdContaUnbilledDebito) {
		this.cdContaUnbilledDebito = cdContaUnbilledDebito;
	}
	public String getCdContaUnbilledCredito() {
		return cdContaUnbilledCredito;
	}
	public void setCdContaUnbilledCredito(String cdContaUnbilledCredito) {
		this.cdContaUnbilledCredito = cdContaUnbilledCredito;
	}
	public String getFlagNtlc() {
		return flagNtlc;
	}
	public void setFlagNtlc(String flagNtlc) {
		this.flagNtlc = flagNtlc;
	}
	public String getFlagContRec() {
		return flagContRec;
	}
	public void setFlagContRec(String flagContRec) {
		this.flagContRec = flagContRec;
	}
	public String getDescNatOper() {
		return descNatOper;
	}
	public void setDescNatOper(String descNatOper) {
		this.descNatOper = descNatOper;
	}
}
