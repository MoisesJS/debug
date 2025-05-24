package br.com.netservicos.novosms.emissao.dto;

import java.util.Date;

import br.com.netservicos.framework.core.bean.BaseTransferObject;
import br.com.netservicos.framework.core.bean.DomainBean;

public class DadosArquivosSefazDTO extends DomainBean implements
		BaseTransferObject {

	private static final long serialVersionUID = -2948332251985515408L;

	/**
	 * Classe para transporte dos dados para geração de arquivo SEFAZ - CAT79
	 * 
	 */
	public DadosArquivosSefazDTO() {
		super(PRIMARY_KEY);
	}

	public static final String PRIMARY_KEY = "pk_fake";

	private String idRow;

	private Integer idCobrancaNotaFiscal;

	private Long nrNotaFiscal;

	private Integer idAgrupaItemNF;

	private Integer idItemNotaFiscal;

	private Integer idImpostoItemNotaFiscal;

	private String ccCpfCnpj;

	private String ccInscricaoEstadual;

	private String ccNomeRazaoSocial;

	private String ccTipoUtilizacao;

	private String cidContrato;

	private Long numContrato;

	private Date dtEmissao;

	private String ccModeloNotaFiscal;

	private String ccSerieNotaFiscal;

	private String ccHashCode;

	private Double vlTotalNF;

	private String ccAnoMesEmissao;

	private String ccLogradouro;

	private String ccNumero;

	private String ccComplemento;

	private String ccCep;

	private String ccBairro;

	private String ccCidade;

	private String ccEstado;

	private String ccTelefone;

	private String ccRazaoSocialOper;

	private String ccInscricaoEstadualOper;

	private String ccCnpjOper;

	private String ccLogradouroOper;

	private String ccBairroOper;

	private String ccCidadeOper;

	private String ccCepOper;

	private String ccEstadoOper;

	private Double vlDescontoNF;

	private Double vlAcrescimoNF;

	private Double vlBaseCalculoImpItemNF;

	private Double vlBaseCalculoImpNF;

	private Double vlTotalImpostoNF;

	private Double vlIsentoImpNF;

	private Double vlIsentoImpItemNF;

	private Double vlOutroImpNF;

	private String fcTotalImpNF;

	private String fcSituacaoDcto;

	private String ccAnoMesRef;

	private String ccCodCfop;

	private Integer nrSequencia;

	private Integer idTipoItemExtrHist;

	private String dsTipoItemExtrHist;

	private String ccClassItem;

	private Double vlItem;

	private Double vlDescontoItemNF;

	private Double vlAcrescimoItemNF;

	private Double vlImpostoItem;

	private Double pcAliquota;

	private Double vlOutroImpItem;

	private String descricao;

	private String fcSituacaoDocumento;

	private String contatoEmpresa;

	private String fone;

	private String nomeCidadeOperadora;

	private String codCidadeOperadora;

	private Integer nrParcelas;

	public static String getPRIMARY_KEY() {
		return PRIMARY_KEY;
	}

	public String getRownum() {
		return idRow;
	}

	public void setRownum(String rownum) {
		this.idRow = rownum;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getCcAnoMesEmissao() {
		return ccAnoMesEmissao;
	}

	public void setCcAnoMesEmissao(String ccAnoMesEmissao) {
		this.ccAnoMesEmissao = ccAnoMesEmissao;
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

	public String getCcBairroOper() {
		return ccBairroOper;
	}

	public void setCcBairroOper(String ccBairroOper) {
		this.ccBairroOper = ccBairroOper;
	}

	public String getCcCep() {
		return ccCep;
	}

	public void setCcCep(String ccCep) {
		this.ccCep = ccCep;
	}

	public String getCcCepOper() {
		return ccCepOper;
	}

	public void setCcCepOper(String ccCepOper) {
		this.ccCepOper = ccCepOper;
	}

	public String getCcCidade() {
		return ccCidade;
	}

	public void setCcCidade(String ccCidade) {
		this.ccCidade = ccCidade;
	}

	public String getCcCidadeOper() {
		return ccCidadeOper;
	}

	public void setCcCidadeOper(String ccCidadeOper) {
		this.ccCidadeOper = ccCidadeOper;
	}

	public String getCcClassItem() {
		return ccClassItem;
	}

	public void setCcClassItem(String ccClassItem) {
		this.ccClassItem = ccClassItem;
	}

	public String getCcCnpjOper() {
		return ccCnpjOper;
	}

	public void setCcCnpjOper(String ccCnpjOper) {
		this.ccCnpjOper = ccCnpjOper;
	}

	public String getCcCodCfop() {
		return ccCodCfop;
	}

	public void setCcCodCfop(String ccCodCfop) {
		this.ccCodCfop = ccCodCfop;
	}

	public String getCcComplemento() {
		return ccComplemento;
	}

	public void setCcComplemento(String ccComplemento) {
		this.ccComplemento = ccComplemento;
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

	public String getCcEstadoOper() {
		return ccEstadoOper;
	}

	public void setCcEstadoOper(String ccEstadoOper) {
		this.ccEstadoOper = ccEstadoOper;
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

	public String getCcInscricaoEstadualOper() {
		return ccInscricaoEstadualOper;
	}

	public void setCcInscricaoEstadualOper(String ccInscricaoEstadualOper) {
		this.ccInscricaoEstadualOper = ccInscricaoEstadualOper;
	}

	public String getCcLogradouro() {
		return ccLogradouro;
	}

	public void setCcLogradouro(String ccLogradouro) {
		this.ccLogradouro = ccLogradouro;
	}

	public String getCcLogradouroOper() {
		return ccLogradouroOper;
	}

	public void setCcLogradouroOper(String ccLogradouroOper) {
		this.ccLogradouroOper = ccLogradouroOper;
	}

	public String getCcModeloNotaFiscal() {
		return ccModeloNotaFiscal;
	}

	public void setCcModeloNotaFiscal(String ccModeloNotaFiscal) {
		this.ccModeloNotaFiscal = ccModeloNotaFiscal;
	}

	public String getCcNomeRazaoSocial() {
		return ccNomeRazaoSocial;
	}

	public void setCcNomeRazaoSocial(String ccNomeRazaoSocial) {
		this.ccNomeRazaoSocial = ccNomeRazaoSocial;
	}

	public String getCcRazaoSocialOper() {
		return ccRazaoSocialOper;
	}

	public void setCcRazaoSocialOper(String ccRazaoSocialOper) {
		this.ccRazaoSocialOper = ccRazaoSocialOper;
	}

	public String getCcSerieNotaFiscal() {
		return ccSerieNotaFiscal;
	}

	public void setCcSerieNotaFiscal(String ccSerieNotaFiscal) {
		this.ccSerieNotaFiscal = ccSerieNotaFiscal;
	}

	public String getCcTelefone() {
		return ccTelefone;
	}

	public void setCcTelefone(String ccTelefone) {
		this.ccTelefone = ccTelefone;
	}

	public String getCcTipoUtilizacao() {
		return ccTipoUtilizacao;
	}

	public void setCcTipoUtilizacao(String ccTipoUtilizacao) {
		this.ccTipoUtilizacao = ccTipoUtilizacao;
	}

	public String getCidContrato() {
		return cidContrato;
	}

	public void setCidContrato(String cidContrato) {
		this.cidContrato = cidContrato;
	}

	public String getContatoEmpresa() {
		return contatoEmpresa;
	}

	public void setContatoEmpresa(String contatoEmpresa) {
		this.contatoEmpresa = contatoEmpresa;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDsTipoItemExtrHist() {
		return dsTipoItemExtrHist;
	}

	public void setDsTipoItemExtrHist(String dsTipoItemExtrHist) {
		this.dsTipoItemExtrHist = dsTipoItemExtrHist;
	}

	public Date getDtEmissao() {
		return dtEmissao;
	}

	public void setDtEmissao(Date dtEmissao) {
		this.dtEmissao = dtEmissao;
	}

	public String getFcSituacaoDcto() {
		return fcSituacaoDcto;
	}

	public void setFcSituacaoDcto(String fcSituacaoDcto) {
		this.fcSituacaoDcto = fcSituacaoDcto;
	}

	public String getFcSituacaoDocumento() {
		return fcSituacaoDocumento;
	}

	public void setFcSituacaoDocumento(String fcSituacaoDocumento) {
		this.fcSituacaoDocumento = fcSituacaoDocumento;
	}

	public String getFcTotalImpNF() {
		return fcTotalImpNF;
	}

	public void setFcTotalImpNF(String fcTotalImpNF) {
		this.fcTotalImpNF = fcTotalImpNF;
	}

	public String getFone() {
		return fone;
	}

	public void setFone(String fone) {
		this.fone = fone;
	}

	public Integer getIdAgrupaItemNF() {
		return idAgrupaItemNF;
	}

	public void setIdAgrupaItemNF(Integer idAgrupaItemNF) {
		this.idAgrupaItemNF = idAgrupaItemNF;
	}

	public Integer getIdCobrancaNotaFiscal() {
		return idCobrancaNotaFiscal;
	}

	public void setIdCobrancaNotaFiscal(Integer idCobrancaNotaFiscal) {
		this.idCobrancaNotaFiscal = idCobrancaNotaFiscal;
	}

	public Integer getIdImpostoItemNotaFiscal() {
		return idImpostoItemNotaFiscal;
	}

	public void setIdImpostoItemNotaFiscal(Integer idImpostoItemNotaFiscal) {
		this.idImpostoItemNotaFiscal = idImpostoItemNotaFiscal;
	}

	public Integer getIdItemNotaFiscal() {
		return idItemNotaFiscal;
	}

	public void setIdItemNotaFiscal(Integer idItemNotaFiscal) {
		this.idItemNotaFiscal = idItemNotaFiscal;
	}

	public Integer getIdTipoItemExtrHist() {
		return idTipoItemExtrHist;
	}

	public void setIdTipoItemExtrHist(Integer idTipoItemExtrHist) {
		this.idTipoItemExtrHist = idTipoItemExtrHist;
	}

	public Long getNrNotaFiscal() {
		return nrNotaFiscal;
	}

	public void setNrNotaFiscal(Long nrNotaFiscal) {
		this.nrNotaFiscal = nrNotaFiscal;
	}

	public Integer getNrSequencia() {
		return nrSequencia;
	}

	public void setNrSequencia(Integer nrSequencia) {
		this.nrSequencia = nrSequencia;
	}

	public Long getNumContrato() {
		return numContrato;
	}

	public void setNumContrato(Long numContrato) {
		this.numContrato = numContrato;
	}

	public Double getPcAliquota() {
		return pcAliquota;
	}

	public void setPcAliquota(Double pcAliquota) {
		this.pcAliquota = pcAliquota;
	}

	public Double getVlAcrescimoItemNF() {
		return vlAcrescimoItemNF;
	}

	public void setVlAcrescimoItemNF(Double vlAcrescimoItemNF) {
		this.vlAcrescimoItemNF = vlAcrescimoItemNF;
	}

	public Double getVlAcrescimoNF() {
		return vlAcrescimoNF;
	}

	public void setVlAcrescimoNF(Double vlAcrescimoNF) {
		this.vlAcrescimoNF = vlAcrescimoNF;
	}

	public Double getVlBaseCalculoImpItemNF() {
		return vlBaseCalculoImpItemNF;
	}

	public void setVlBaseCalculoImpItemNF(Double vlBaseCalculoImpItemNF) {
		this.vlBaseCalculoImpItemNF = vlBaseCalculoImpItemNF;
	}

	public Double getVlBaseCalculoImpNF() {
		return vlBaseCalculoImpNF;
	}

	public void setVlBaseCalculoImpNF(Double vlBaseCalculoImpNF) {
		this.vlBaseCalculoImpNF = vlBaseCalculoImpNF;
	}

	public Double getVlDescontoItemNF() {
		return vlDescontoItemNF;
	}

	public void setVlDescontoItemNF(Double vlDescontoItemNF) {
		this.vlDescontoItemNF = vlDescontoItemNF;
	}

	public Double getVlDescontoNF() {
		return vlDescontoNF;
	}

	public void setVlDescontoNF(Double vlDescontoNF) {
		this.vlDescontoNF = vlDescontoNF;
	}

	public Double getVlImpostoItem() {
		return vlImpostoItem;
	}

	public void setVlImpostoItem(Double vlImposto) {
		this.vlImpostoItem = vlImposto;
	}

	public Double getVlIsentoImpNF() {
		return vlIsentoImpNF;
	}

	public void setVlIsentoImpNF(Double vlIsentoImpNF) {
		this.vlIsentoImpNF = vlIsentoImpNF;
	}

	public Double getVlIsentoImpItemNF() {
		return vlIsentoImpItemNF;
	}

	public void setVlIsentoImpItemNF(Double vlIsentoImpItemNF) {
		this.vlIsentoImpItemNF = vlIsentoImpItemNF;
	}

	public Double getVlItem() {
		return vlItem;
	}

	public void setVlItem(Double vlItem) {
		this.vlItem = vlItem;
	}

	public Double getVlOutroImpItem() {
		return vlOutroImpItem;
	}

	public void setVlOutroImpItem(Double vlOutroImpItem) {
		this.vlOutroImpItem = vlOutroImpItem;
	}

	public Double getVlOutroImpNF() {
		return vlOutroImpNF;
	}

	public void setVlOutroImpNF(Double vlOutroImpNF) {
		this.vlOutroImpNF = vlOutroImpNF;
	}

	public Double getVlTotalImpostoNF() {
		return vlTotalImpostoNF;
	}

	public void setVlTotalImpostoNF(Double vlTotalImpostoNF) {
		this.vlTotalImpostoNF = vlTotalImpostoNF;
	}

	public Double getVlTotalNF() {
		return vlTotalNF;
	}

	public void setVlTotalNF(Double vlTotalNF) {
		this.vlTotalNF = vlTotalNF;
	}

	public String getNomeCidadeOperadora() {
		return nomeCidadeOperadora;
	}

	public void setNomeCidadeOperadora(String nomeCidadeOperadora) {
		this.nomeCidadeOperadora = nomeCidadeOperadora;
	}

	public String getCodCidadeOperadora() {
		return codCidadeOperadora;
	}

	public void setCodCidadeOperadora(String codCidadeOperadora) {
		this.codCidadeOperadora = codCidadeOperadora;
	}

	public Integer getNrParcelas() {
		return nrParcelas;
	}

	public void setNrParcelas(Integer nrParcelas) {
		this.nrParcelas = nrParcelas;
	}

	public String getCcNumero() {
		return ccNumero;
	}

	public void setCcNumero(String ccNumero) {
		this.ccNumero = ccNumero;
	}

}
