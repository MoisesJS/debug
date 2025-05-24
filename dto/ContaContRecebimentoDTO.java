package br.com.netservicos.novosms.emissao.dto;

import java.io.Serializable;

public class ContaContRecebimentoDTO implements Serializable {

	private static final long serialVersionUID = -8503950755750988695L;

	private String descLocPagto;
	private String nomeParceiro;
	private String nomeAdquirente;
	private String contaDebito;
	private String contaCredito;
	private String nomeEmpresa;

	public String getDescLocPagto() {
		return descLocPagto;
	}
	public void setDescLocPagto(String descLocPagto) {
		this.descLocPagto = descLocPagto;
	}
	public String getNomeParceiro() {
		return nomeParceiro;
	}
	public void setNomeParceiro(String nomeParceiro) {
		this.nomeParceiro = nomeParceiro;
	}
	public String getNomeAdquirente() {
		return nomeAdquirente;
	}
	public void setNomeAdquirente(String nomeAdquirente) {
		this.nomeAdquirente = nomeAdquirente;
	}
	public String getContaDebito() {
		return contaDebito;
	}
	public void setContaDebito(String contaDebito) {
		this.contaDebito = contaDebito;
	}
	public String getContaCredito() {
		return contaCredito;
	}
	public void setContaCredito(String contaCredito) {
		this.contaCredito = contaCredito;
	}
	public String getNomeEmpresa() {
		return nomeEmpresa;
	}
	public void setNomeEmpresa(String nomeEmpresa) {
		this.nomeEmpresa = nomeEmpresa;
	}
	
	
}
