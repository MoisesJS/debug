package br.com.netservicos.novosms.emissao.dto;

import java.util.Date;

import br.com.netservicos.framework.core.bean.DomainBean;

public class DadosGeraisPrimeiraViaPrintParceiroDTO extends DomainBean {

	public static final String PRIMARY_KEY = "pk fake";

	private static final long serialVersionUID = 1L;

	
	 private String ccLogradouro;
	 private String ccBairro;
	 private String ccCidade;
	 private String ccEstado;
	 private String cepNotaFiscal;
	 private String ccNomeRazaoSocial;
	 private String ccCodigoSefaz;
	 private String ccInscricaoEstadual;
	 private String ccCpfCnpj;
	 private String ccSerieNotaFiscal;
	 private String ccAnoMesRef;
	 private String nrNotaFiscal;
	 private String nrFatura;
	 private String ccHashCode;
	 private Date   dtEmissao;
	 private Double vlTotal;
	 private String idBoleto;
	 private Long   idBoletoOrigem;
	 private Date   dtVencimento;
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
	 private String cc_razao_social_oper;
	 private String cc_logradouro_oper;
	 private String cc_bairro_oper; 
	 private String cc_cidade_oper;
	 private String cc_estado_oper;
	 private String cc_cep_oper;
	 private String cc_cnpj_oper;
	 private String cc_inscricao_estadual_oper;
	 private String cc_inscricao_municipal_oper;
	 private Long codigoLote;
	 private String fc_boleto_consolidado;
	 private String codigoClienteEBT;
	 private String codigoClienteNET;
	 private String codOperadora;
	 private String isOperadoraNet;
	 private String codigoBanco;
	 private String dsMensagemNotaFiscal;

	 private String logradouroCobranca;
	 private Integer numeroCobranca;
	 private String complementoCobranca;
	 
	 
	public String getDsMensagemNotaFiscal() {
		return dsMensagemNotaFiscal;
	}

	public void setDsMensagemNotaFiscal(String dsMensagemNotaFiscal) {
		this.dsMensagemNotaFiscal = dsMensagemNotaFiscal;
	}

	public String getCodigoBanco() {
		return codigoBanco;
	}

	public void setCodigoBanco(String codigoBanco) {
		this.codigoBanco = codigoBanco;
	}

	public void setIsOperadoraNet(String isOperadoraNet) {
		this.isOperadoraNet = isOperadoraNet;
	}

	public String getCodigoClienteEBT() {
		return codigoClienteEBT;
	}

	public void setCodigoClienteEBT(String codigoClienteEBT) {
		this.codigoClienteEBT = codigoClienteEBT;
	}

	public String getCodigoClienteNET() {
		return codigoClienteNET;
	}

	public void setCodigoClienteNET(String codigoClienteNET) {
		this.codigoClienteNET = codigoClienteNET;
	}

	public String getCodOperadora() {
		return codOperadora;
	}

	public void setCodOperadora(String codOperadora) {
		this.codOperadora = codOperadora;
	}

	public String getFc_boleto_consolidado() {
		return fc_boleto_consolidado;
	}

	public void setFc_boleto_consolidado(String fc_boleto_consolidado) {
		this.fc_boleto_consolidado = fc_boleto_consolidado;
	}

	public DadosGeraisPrimeiraViaPrintParceiroDTO() {
			super(PRIMARY_KEY);
	}
	 
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getCcAnoMesRef() {
		return ccAnoMesRef;
	}
	public void setCcAnoMesRef(String ccAnoMesRef) {
		this.ccAnoMesRef = ccAnoMesRef;
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
	public String getCcCodigoBarra() {
		return ccCodigoBarra;
	}
	public void setCcCodigoBarra(String ccCodigoBarra) {
		this.ccCodigoBarra = ccCodigoBarra;
	}
	public String getCcCodigoSefaz() {
		return ccCodigoSefaz;
	}
	public void setCcCodigoSefaz(String ccCodigoSefaz) {
		this.ccCodigoSefaz = ccCodigoSefaz;
	}
	public String getCcCpfCnpj() {
		return ccCpfCnpj;
	}
	public void setCcCpfCnpj(String ccCpfCnpj) {
		this.ccCpfCnpj = ccCpfCnpj;
	}
	public String getCcEstado() {
		return ccEstado;
	}
	public void setCcEstado(String ccEstado) {
		this.ccEstado = ccEstado;
	}
	public String getCcHashCode() {
		return ccHashCode;
	}
	public void setCcHashCode(String ccHashCode) {
		this.ccHashCode = ccHashCode;
	}
	public String getCcInscricaoEstadual() {
		return ccInscricaoEstadual;
	}
	public void setCcInscricaoEstadual(String ccInscricaoEstadual) {
		this.ccInscricaoEstadual = ccInscricaoEstadual;
	}
	public String getCcLinhaDigitavel() {
		return ccLinhaDigitavel;
	}
	public void setCcLinhaDigitavel(String ccLinhaDigitavel) {
		this.ccLinhaDigitavel = ccLinhaDigitavel;
	}
	public String getCcLogradouro() {
		return ccLogradouro;
	}
	public void setCcLogradouro(String ccLogradouro) {
		this.ccLogradouro = ccLogradouro;
	}
	public String getCcNomeRazaoSocial() {
		return ccNomeRazaoSocial;
	}
	public void setCcNomeRazaoSocial(String ccNomeRazaoSocial) {
		this.ccNomeRazaoSocial = ccNomeRazaoSocial;
	}
	public String getCcSerieNotaFiscal() {
		return ccSerieNotaFiscal;
	}
	public void setCcSerieNotaFiscal(String ccSerieNotaFiscal) {
		this.ccSerieNotaFiscal = ccSerieNotaFiscal;
	}
	public Integer getCep() {
		return cep;
	}
	public void setCep(Integer cep) {
		this.cep = cep;
	}
	public String getCepNotaFiscal() {
		return cepNotaFiscal;
	}
	public void setCepNotaFiscal(String cepNotaFiscal) {
		this.cepNotaFiscal = cepNotaFiscal;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public Date getDtEmissao() {
		return dtEmissao;
	}
	public void setDtEmissao(Date dtEmissao) {
		this.dtEmissao = dtEmissao;
	}
	public Date getDtVencimento() {
		return dtVencimento;
	}
	public void setDtVencimento(Date dtVencimento) {
		this.dtVencimento = dtVencimento;
	}
	public String getEnder() {
		return ender;
	}
	public void setEnder(String ender) {
		this.ender = ender;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getIdBoleto() {
		return idBoleto;
	}
	public void setIdBoleto(String idBoleto) {
		this.idBoleto = idBoleto;
	}
	public Long getIdBoletoOrigem() {
		return idBoletoOrigem;
	}
	public void setIdBoletoOrigem(Long idBoletoOrigem) {
		this.idBoletoOrigem = idBoletoOrigem;
	}
	public String getNomeTitular() {
		return nomeTitular;
	}
	public void setNomeTitular(String nomeTitular) {
		this.nomeTitular = nomeTitular;
	}
	public String getNrNotaFiscal() {
		return nrNotaFiscal;
	}
	public void setNrNotaFiscal(String nrNotaFiscal) {
		this.nrNotaFiscal = nrNotaFiscal;
	}
	
	public String getNrFatura() {
        return nrFatura;
    }

    public void setNrFatura(String nrFatura) {
        this.nrFatura = nrFatura;
    }

    public String getPrefixo() {
		return prefixo;
	}
	public void setPrefixo(String prefixo) {
		this.prefixo = prefixo;
	}
	public Double getVlDocumento() {
		return vlDocumento;
	}
	public void setVlDocumento(Double vlDocumento) {
		this.vlDocumento = vlDocumento;
	}
	public Double getVlTotal() {
		return vlTotal;
	}
	public void setVlTotal(Double vlTotal) {
		this.vlTotal = vlTotal;
	}

	public String getCc_bairro_oper() {
		return cc_bairro_oper;
	}

	public void setCc_bairro_oper(String cc_bairro_oper) {
		this.cc_bairro_oper = cc_bairro_oper;
	}

	public String getCc_cep_oper() {
		return cc_cep_oper;
	}

	public void setCc_cep_oper(String cc_cep_oper) {
		this.cc_cep_oper = cc_cep_oper;
	}

	public String getCc_cidade_oper() {
		return cc_cidade_oper;
	}

	public void setCc_cidade_oper(String cc_cidade_oper) {
		this.cc_cidade_oper = cc_cidade_oper;
	}

	public String getCc_cnpj_oper() {
		return cc_cnpj_oper;
	}

	public void setCc_cnpj_oper(String cc_cnpj_oper) {
		this.cc_cnpj_oper = cc_cnpj_oper;
	}

	public String getCc_estado_oper() {
		return cc_estado_oper;
	}

	public void setCc_estado_oper(String cc_estado_oper) {
		this.cc_estado_oper = cc_estado_oper;
	}

	public String getCc_inscricao_estadual_oper() {
		return cc_inscricao_estadual_oper;
	}

	public void setCc_inscricao_estadual_oper(String cc_inscricao_estadual_oper) {
		this.cc_inscricao_estadual_oper = cc_inscricao_estadual_oper;
	}

	public String getCc_inscricao_municipal_oper() {
		return cc_inscricao_municipal_oper;
	}

	public void setCc_inscricao_municipal_oper(String cc_inscricao_municipal_oper) {
		this.cc_inscricao_municipal_oper = cc_inscricao_municipal_oper;
	}

	public String getCc_logradouro_oper() {
		return cc_logradouro_oper;
	}

	public void setCc_logradouro_oper(String cc_logradouro_oper) {
		this.cc_logradouro_oper = cc_logradouro_oper;
	}

	public String getCc_razao_social_oper() {
		return cc_razao_social_oper;
	}

	public void setCc_razao_social_oper(String cc_razao_social_oper) {
		this.cc_razao_social_oper = cc_razao_social_oper;
	}

	public Long getCodigoLote() {
		return codigoLote;
	}

	public void setCodigoLote(Long codigoLote) {
		this.codigoLote = codigoLote;
	}

	public Boolean isOperadoraNet() {
		return isOperadoraNet.equals("S");
	}

	public String getLogradouroCobranca() {
		return logradouroCobranca;
	}

	public void setLogradouroCobranca(String logradouroCobranca) {
		this.logradouroCobranca = logradouroCobranca;
	}

	public Integer getNumeroCobranca() {
		return numeroCobranca;
	}

	public void setNumeroCobranca(Integer numeroCobranca) {
		this.numeroCobranca = numeroCobranca;
	}

	public String getComplementoCobranca() {
		return complementoCobranca;
	}

	public void setComplementoCobranca(String complementoCobranca) {
		this.complementoCobranca = complementoCobranca;
	}
	
	
}
