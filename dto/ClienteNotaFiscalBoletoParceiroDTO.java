package br.com.netservicos.novosms.emissao.dto;

import java.util.Date;

import br.com.netservicos.framework.core.bean.DomainBean;

public class ClienteNotaFiscalBoletoParceiroDTO extends DomainBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7849395517596564071L;

	private Long rownum;
	private Long numContrato;
	private String ccLogradou;
	private String ccBairro;
	private String ccCidade;
	private String ccEstado;
	private String ccCep;
	private String ccNomeRazaoSocial;
	private String ccCodigoSefaz;
	private String ccInscricaoEstadual;
	private String ccCpfCnpj;
	private String ccSerieNotaFiscal;
	private String ccAnoMesRef;
	private Long nrNotaFiscal;
	private String ccHashCode;
	private Date dtEmissao;
	private Double vlTotal;
	private Long idBoleto;
	private Long idBoletoOrigem;
	private Date dtVencimento;
	private String ccCodigoBarra;
	private String ccLinhaDigitavel;
	private Double vlDocumento;
	private String ender;
	private String bairro;
	private String cidade;
	private String estado;
	private Integer cep;
	private String prefixo;
	private String nomeTitular;

	public ClienteNotaFiscalBoletoParceiroDTO(String primaryKeyName) {
		super(primaryKeyName);
		// TODO Auto-generated constructor stub
	}

	public Long getRownum() {
		return rownum;
	}

	public void setRownum(Long rownum) {
		this.rownum = rownum;
	}

	public Long getNumContrato() {
		return numContrato;
	}

	public void setNumContrato(Long numContrato) {
		this.numContrato = numContrato;
	}

	public String getCcLogradou() {
		return ccLogradou;
	}

	public void setCcLogradou(String ccLogradou) {
		this.ccLogradou = ccLogradou;
	}

	public String getCcBairro() {
		return ccBairro;
	}

	public void setCcBairro(String ccBairro) {
		this.ccBairro = ccBairro;
	}

	public String getCcCidade() {
		return ccCidade;
	}

	public void setCcCidade(String ccCidade) {
		this.ccCidade = ccCidade;
	}

	public String getCcEstado() {
		return ccEstado;
	}

	public void setCcEstado(String ccEstado) {
		this.ccEstado = ccEstado;
	}

	public String getCcCep() {
		return ccCep;
	}

	public void setCcCep(String ccCep) {
		this.ccCep = ccCep;
	}

	public String getCcNomeRazaoSocial() {
		return ccNomeRazaoSocial;
	}

	public void setCcNomeRazaoSocial(String ccNomeRazaoSocial) {
		this.ccNomeRazaoSocial = ccNomeRazaoSocial;
	}

	public String getCcCodigoSefaz() {
		return ccCodigoSefaz;
	}

	public void setCcCodigoSefaz(String ccCodigoSefaz) {
		this.ccCodigoSefaz = ccCodigoSefaz;
	}

	public String getCcInscricaoEstadual() {
		return ccInscricaoEstadual;
	}

	public void setCcInscricaoEstadual(String ccInscricaoEstadual) {
		this.ccInscricaoEstadual = ccInscricaoEstadual;
	}

	public String getCcCpfCnpj() {
		return ccCpfCnpj;
	}

	public void setCcCpfCnpj(String ccCpfCnpj) {
		this.ccCpfCnpj = ccCpfCnpj;
	}

	public String getCcSerieNotaFiscal() {
		return ccSerieNotaFiscal;
	}

	public void setCcSerieNotaFiscal(String ccSerieNotaFiscal) {
		this.ccSerieNotaFiscal = ccSerieNotaFiscal;
	}

	public String getCcAnoMesRef() {
		return ccAnoMesRef;
	}

	public void setCcAnoMesRef(String ccAnoMesRef) {
		this.ccAnoMesRef = ccAnoMesRef;
	}

	public Long getNrNotaFiscal() {
		return nrNotaFiscal;
	}

	public void setNrNotaFiscal(Long nrNotaFiscal) {
		this.nrNotaFiscal = nrNotaFiscal;
	}

	public String getCcHashCode() {
		return ccHashCode;
	}

	public void setCcHashCode(String ccHashCode) {
		this.ccHashCode = ccHashCode;
	}

	public Date getDtEmissao() {
		return dtEmissao;
	}

	public void setDtEmissao(Date dtEmissao) {
		this.dtEmissao = dtEmissao;
	}

	public Double getVlTotal() {
		return vlTotal;
	}

	public void setVlTotal(Double vlTotal) {
		this.vlTotal = vlTotal;
	}

	public Long getIdBoleto() {
		return idBoleto;
	}

	public void setIdBoleto(Long idBoleto) {
		this.idBoleto = idBoleto;
	}

	public Long getIdBoletoOrigem() {
		return idBoletoOrigem;
	}

	public void setIdBoletoOrigem(Long idBoletoOrigem) {
		this.idBoletoOrigem = idBoletoOrigem;
	}

	public Date getDtVencimento() {
		return dtVencimento;
	}

	public void setDtVencimento(Date dtVencimento) {
		this.dtVencimento = dtVencimento;
	}

	public String getCcCodigoBarra() {
		return ccCodigoBarra;
	}

	public void setCcCodigoBarra(String ccCodigoBarra) {
		this.ccCodigoBarra = ccCodigoBarra;
	}

	public String getCcLinhaDigitavel() {
		return ccLinhaDigitavel;
	}

	public void setCcLinhaDigitavel(String ccLinhaDigitavel) {
		this.ccLinhaDigitavel = ccLinhaDigitavel;
	}

	public Double getVlDocumento() {
		return vlDocumento;
	}

	public void setVlDocumento(Double vlDocumento) {
		this.vlDocumento = vlDocumento;
	}

	public String getEnder() {
		return ender;
	}

	public void setEnder(String ender) {
		this.ender = ender;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Integer getCep() {
		return cep;
	}

	public void setCep(Integer cep) {
		this.cep = cep;
	}

	public String getPrefixo() {
		return prefixo;
	}

	public void setPrefixo(String prefixo) {
		this.prefixo = prefixo;
	}

	public String getNomeTitular() {
		return nomeTitular;
	}

	public void setNomeTitular(String nomeTitular) {
		this.nomeTitular = nomeTitular;
	}

}
