package br.com.netservicos.novosms.emissao.dto;

import java.util.Date;

import br.com.netservicos.framework.core.bean.DomainBean;

public class DadosArquivoControleSefazDTO extends DomainBean  {

	public static final String PRIMARY_KEY = "pk_fake";
	
	public DadosArquivoControleSefazDTO(String primaryKeyName) {
		super(primaryKeyName);
	}

	public DadosArquivoControleSefazDTO() {
		super(PRIMARY_KEY);
	}

	private static final long serialVersionUID = -2948332251985515408L;

	//Dados Para Controle e Etiqueta: originados do Arquivo .INI
	private String cnpjCpf;
	private String ie;
	private String razaoSocial;
	private String endereco;
	private String cep;
	private String bairro;
	private String municipio;
	private String uf;
	private String responsavel;
	private String cargo;
	private String telefoneContato;
	private String email;
	private String volume;
	private String totalVolume;
	private String serieNF;
	
	//Campos Novos Para Controle: Arquivo Mestre
	private Integer qtdeRegistrosArqMestre;
	private Integer qtdeNFCanceladasMestre;
	private Date dtEmissaoPrimeiroDocFiscalMestre;
	private Date dtEmissaoUltimoDocFiscalMestre;
	private Long nrPrimeiroDocFiscalMestre;
	private Long nrUltimoDocFiscalMestre;
	private Double valorTotalMestre;
	private Double bcICMSMestre;
	private Double ICMSMestre;
	private Double operacoesIsentasMestre;
	private Double outrosValoresMestre;
	private String nomeArqMestre;
	private String statusMestre;
	private String codAutenticDigitalArqMestre;
	private Integer posicaoUltimoDocFiscalMestre; 

	//Campos Novos Para Controle: Arquivo Item de Docto Fiscal
	private Integer qtdeRegistrosArqItem;
	private Integer qtdeNFCanceladosItem;
	private Date dtEmissaoPrimeiroDocFiscalItem;
	private Date dtEmissaoUltimoDocFiscalItem;
	private Long nrPrimeiroDocFiscalItem;
	private Long nrUltimoDocFiscalItem;
	private Double totalItem;
	private Double descontosItem;
	private Double acrescimosDespesasItem;
	private Double bcICMSItem;
	private Double ICMSItem;
	private Double operacoesIsentasItem;
	private Double outrosValoresItem;
	private String nomeArqItem;
	private String statusItem;
	private String codAutenticDigitalArqItem;

	//Campos Novos Para Controle: Arquivo Dados Cadastrais
	private Integer qtdeRegistrosArqDadosCadastrais;
	private String nomeArqDadosCadastrais;
	private String codAutenticDigitalArqDadosCadastrais;
	private String codAutenticDigitalRegistro;
	private String brancos;

	
	public String getTotalVolume() {
		return totalVolume;
	}

	public void setTotalVolume(String totalVolume) {
		this.totalVolume = totalVolume;
	}

	public String getSerieNF() {
		return serieNF;
	}

	public void setSerieNF(String serieNF) {
		this.serieNF = serieNF;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getCnpjCpf() {
		return cnpjCpf;
	}

	public void setCnpjCpf(String cnpjCpf) {
		this.cnpjCpf = cnpjCpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIE() {
		return ie;
	}

	public void setIE(String ie) {
		this.ie = ie;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

//	public String getNumero() {
//		return numero;
//	}
//
//	public void setNumero(String numero) {
//		this.numero = numero;
//	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public String getTelefoneContato() {
		return telefoneContato;
	}

	public void setTelefoneContato(String telefoneContato) {
		this.telefoneContato = telefoneContato;
	}

	public String getUF() {
		return uf;
	}

	public void setUF(String uf) {
		this.uf = uf;
	}

	public Double getAcrescimosDespesasItem() {
		return acrescimosDespesasItem;
	}

	public void setAcrescimosDespesasItem(Double acrescimosDespesasItem) {
		this.acrescimosDespesasItem = acrescimosDespesasItem;
	}

	public Double getBcICMSItem() {
		return bcICMSItem;
	}

	public void setBcICMSItem(Double bcICMSItem) {
		this.bcICMSItem = bcICMSItem;
	}

	public Double getBcICMSMestre() {
		return bcICMSMestre;
	}

	public void setBcICMSMestre(Double bcICMSMestre) {
		this.bcICMSMestre = bcICMSMestre;
	}

	public String getBrancos() {
		return brancos;
	}

	public void setBrancos(String brancos) {
		this.brancos = brancos;
	}

	public String getCodAutenticDigitalArqDadosCadastrais() {
		return codAutenticDigitalArqDadosCadastrais;
	}

	public void setCodAutenticDigitalArqDadosCadastrais(
			String codAutenticDigitalArqDadosCadastrais) {
		this.codAutenticDigitalArqDadosCadastrais = codAutenticDigitalArqDadosCadastrais;
	}

	public String getCodAutenticDigitalArqItem() {
		return codAutenticDigitalArqItem;
	}

	public void setCodAutenticDigitalArqItem(String codAutenticDigitalArqItem) {
		this.codAutenticDigitalArqItem = codAutenticDigitalArqItem;
	}

	public String getCodAutenticDigitalArqMestre() {
		return codAutenticDigitalArqMestre;
	}

	public void setCodAutenticDigitalArqMestre(String codAutenticDigitalArqMestre) {
		this.codAutenticDigitalArqMestre = codAutenticDigitalArqMestre;
	}

	public String getCodAutenticDigitalRegistro() {
		return codAutenticDigitalRegistro;
	}

	public void setCodAutenticDigitalRegistro(String codAutenticDigitalRegistro) {
		this.codAutenticDigitalRegistro = codAutenticDigitalRegistro;
	}

	public Double getDescontosItem() {
		return descontosItem;
	}

	public void setDescontosItem(Double descontosItem) {
		this.descontosItem = descontosItem;
	}

	public Date getDtEmissaoPrimeiroDocFiscalItem() {
		return dtEmissaoPrimeiroDocFiscalItem;
	}

	public void setDtEmissaoPrimeiroDocFiscalItem(
			Date dtEmissaoPrimeiroDocFiscalItem) {
		this.dtEmissaoPrimeiroDocFiscalItem = dtEmissaoPrimeiroDocFiscalItem;
	}

	public Date getDtEmissaoPrimeiroDocFiscalMestre() {
		return dtEmissaoPrimeiroDocFiscalMestre;
	}

	public void setDtEmissaoPrimeiroDocFiscalMestre(
			Date dtEmissaoPrimeiroDocFiscalMestre) {
		this.dtEmissaoPrimeiroDocFiscalMestre = dtEmissaoPrimeiroDocFiscalMestre;
	}

	public Date getDtEmissaoUltimoDocFiscalItem() {
		return dtEmissaoUltimoDocFiscalItem;
	}

	public void setDtEmissaoUltimoDocFiscalItem(Date dtEmissaoUltimoDocFiscalItem) {
		this.dtEmissaoUltimoDocFiscalItem = dtEmissaoUltimoDocFiscalItem;
	}

	public Date getDtEmissaoUltimoDocFiscalMestre() {
		return dtEmissaoUltimoDocFiscalMestre;
	}

	public void setDtEmissaoUltimoDocFiscalMestre(
			Date dtEmissaoUltimoDocFiscalMestre) {
		this.dtEmissaoUltimoDocFiscalMestre = dtEmissaoUltimoDocFiscalMestre;
	}

	public Double getICMSItem() {
		return ICMSItem;
	}

	public void setICMSItem(Double item) {
		ICMSItem = item;
	}

	public Double getICMSMestre() {
		return ICMSMestre;
	}

	public void setICMSMestre(Double mestre) {
		ICMSMestre = mestre;
	}

	public String getNomeArqDadosCadastrais() {
		return nomeArqDadosCadastrais;
	}

	public void setNomeArqDadosCadastrais(String nomeArqDadosCadastrais) {
		this.nomeArqDadosCadastrais = nomeArqDadosCadastrais;
	}

	public String getNomeArqItem() {
		return nomeArqItem;
	}

	public void setNomeArqItem(String nomeArqItem) {
		this.nomeArqItem = nomeArqItem;
	}

	public String getNomeArqMestre() {
		return nomeArqMestre;
	}

	public void setNomeArqMestre(String nomeArqMestre) {
		this.nomeArqMestre = nomeArqMestre;
	}

	public Long getNrPrimeiroDocFiscalItem() {
		return nrPrimeiroDocFiscalItem;
	}

	public void setNrPrimeiroDocFiscalItem(Long nrPrimeiroDocFiscalItem) {
		this.nrPrimeiroDocFiscalItem = nrPrimeiroDocFiscalItem;
	}

	public Long getNrPrimeiroDocFiscalMestre() {
		return nrPrimeiroDocFiscalMestre;
	}

	public void setNrPrimeiroDocFiscalMestre(Long nrPrimeiroDocFiscalMestre) {
		this.nrPrimeiroDocFiscalMestre = nrPrimeiroDocFiscalMestre;
	}

	public Long getNrUltimoDocFiscalItem() {
		return nrUltimoDocFiscalItem;
	}

	public void setNrUltimoDocFiscalItem(Long nrUltimoDocFiscalItem) {
		this.nrUltimoDocFiscalItem = nrUltimoDocFiscalItem;
	}

	public Long getNrUltimoDocFiscalMestre() {
		return nrUltimoDocFiscalMestre;
	}

	public void setNrUltimoDocFiscalMestre(Long nrUltimoDocFiscalMestre) {
		this.nrUltimoDocFiscalMestre = nrUltimoDocFiscalMestre;
	}

	public Double getOperacoesIsentasItem() {
		return operacoesIsentasItem;
	}

	public void setOperacoesIsentasItem(Double operacoesIsentasItem) {
		this.operacoesIsentasItem = operacoesIsentasItem;
	}

	public Double getOperacoesIsentasMestre() {
		return operacoesIsentasMestre;
	}

	public void setOperacoesIsentasMestre(Double operacoesIsentasMestre) {
		this.operacoesIsentasMestre = operacoesIsentasMestre;
	}

	public Double getOutrosValoresItem() {
		return outrosValoresItem;
	}

	public void setOutrosValoresItem(Double outrosValoresItem) {
		this.outrosValoresItem = outrosValoresItem;
	}

	public Double getOutrosValoresMestre() {
		return outrosValoresMestre;
	}

	public void setOutrosValoresMestre(Double outrosValoresMestre) {
		this.outrosValoresMestre = outrosValoresMestre;
	}

	public Integer getQtdeNFCanceladosItem() {
		return qtdeNFCanceladosItem;
	}

	public void setQtdeNFCanceladosItem(Integer qtdeNFCanceladosItem) {
		this.qtdeNFCanceladosItem = qtdeNFCanceladosItem;
	}

	public Integer getQtdeNFCanceladasMestre() {
		return qtdeNFCanceladasMestre;
	}

	public void setQtdeNFCanceladasMestre(Integer qtdeNFCanceladasMestre) {
		this.qtdeNFCanceladasMestre = qtdeNFCanceladasMestre;
	}

	public Integer getQtdeRegistrosArqDadosCadastrais() {
		return qtdeRegistrosArqDadosCadastrais;
	}

	public void setQtdeRegistrosArqDadosCadastrais(
			Integer qtdeRegistrosArqDadosCadastrais) {
		this.qtdeRegistrosArqDadosCadastrais = qtdeRegistrosArqDadosCadastrais;
	}

	public Integer getQtdeRegistrosArqItem() {
		return qtdeRegistrosArqItem;
	}

	public void setQtdeRegistrosArqItem(Integer qtdeRegistrosArqItem) {
		this.qtdeRegistrosArqItem = qtdeRegistrosArqItem;
	}

	public Integer getQtdeRegistrosArqMestre() {
		return qtdeRegistrosArqMestre;
	}

	public void setQtdeRegistrosArqMestre(Integer qtdeRegistrosArqMestre) {
		this.qtdeRegistrosArqMestre = qtdeRegistrosArqMestre;
	}

	public String getStatusItem() {
		return statusItem;
	}

	public void setStatusItem(String statusItem) {
		this.statusItem = statusItem;
	}

	public String getStatusMestre() {
		return statusMestre;
	}

	public void setStatusMestre(String statusMestre) {
		this.statusMestre = statusMestre;
	}

	public Double getTotalItem() {
		return totalItem;
	}

	public void setTotalItem(Double totalItem) {
		this.totalItem = totalItem;
	}

	public Double getValorTotalMestre() {
		return valorTotalMestre;
	}

	public void setValorTotalMestre(Double valorTotalMestre) {
		this.valorTotalMestre = valorTotalMestre;
	}

	public Integer getPosicaoUltimoDocFiscalMestre() {
		return posicaoUltimoDocFiscalMestre;
	}

	public void setPosicaoUltimoDocFiscalMestre(Integer posicaoUltimoDocFiscalMestre) {
		this.posicaoUltimoDocFiscalMestre = posicaoUltimoDocFiscalMestre;
	}
	
	


}
