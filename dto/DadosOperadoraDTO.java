package br.com.netservicos.novosms.emissao.dto;

import br.com.netservicos.framework.core.bean.DomainBean;

public class DadosOperadoraDTO extends DomainBean{

	public static final String	PRIMARY_KEY	= "pk fake";

	public DadosOperadoraDTO(){
		super(PRIMARY_KEY);
	}

	private static final long	serialVersionUID	= -8387763187719732760L;

	private String	          cep;
	private String	          logradouro;
	private String	          numero;
	private String	          cidade;
	private String	          bairro;
	private String	          estado;
	private String	          razaoSocial;
	private String	          cnpj;
	private String	          inscricaoEstadual;
	private String	          inscricaoMunicipal;
	private String	          cidContrato;
	private String	          nomeFebraban;
	private String	          empresaFatura;

	public String getCidContrato(){
		return cidContrato;
	}

	public void setCidContrato(String cidContrato){
		this.cidContrato = cidContrato;
	}

	public String getBairro(){
		return bairro;
	}

	public void setBairro(String bairro){
		this.bairro = bairro;
	}

	public String getCep(){
		return cep;
	}

	public void setCep(String cep){
		this.cep = cep;
	}

	public String getCidade(){
		return cidade;
	}

	public void setCidade(String cidade){
		this.cidade = cidade;
	}

	public String getCnpj(){
		return cnpj;
	}

	public void setCnpj(String cnpj){
		this.cnpj = cnpj;
	}

	public String getEstado(){
		return estado;
	}

	public void setEstado(String estado){
		this.estado = estado;
	}

	public String getInscricaoEstadual(){
		return inscricaoEstadual;
	}

	public void setInscricaoEstadual(String inscricaoEstadual){
		this.inscricaoEstadual = inscricaoEstadual;
	}

	public String getInscricaoMunicipal(){
		return inscricaoMunicipal;
	}

	public void setInscricaoMunicipal(String inscricaoMunicipal){
		this.inscricaoMunicipal = inscricaoMunicipal;
	}

	public String getLogradouro(){
		return logradouro;
	}

	public void setLogradouro(String logradouro){
		this.logradouro = logradouro;
	}

	public String getNumero(){
		return numero;
	}

	public void setNumero(String numero){
		this.numero = numero;
	}

	public String getRazaoSocial(){
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial){
		this.razaoSocial = razaoSocial;
	}

	public String getNomeFebraban(){
		return nomeFebraban;
	}

	public void setNomeFebraban(String nomeFebraban){
		this.nomeFebraban = nomeFebraban;
	}
	
	public String getEmpresaFatura(){
	    return empresaFatura;
    }
	
	public void setEmpresaFatura(String empresaFatura){
	    this.empresaFatura = empresaFatura;
    }
}

	