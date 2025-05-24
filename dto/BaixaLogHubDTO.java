package br.com.netservicos.novosms.emissao.dto;

import java.util.Date;

import br.com.netservicos.framework.core.bean.DomainBean;

public class BaixaLogHubDTO extends DomainBean {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String PRIMARY_KEY = "pk fake";

	public BaixaLogHubDTO() {
		super(PRIMARY_KEY);   
	}
	
	private Long numeroTransacao;
	private Long idFila;
	private Long idLote;
	private Long idBoleto; 
	private Double valor;
	private String dataEnvioHub;
	private String dataRetornoHub;
	private String dataBaixaNetsms;
	private String respostaHub;
	private String statusBaixaNetsms;
	private String requestHub;
	private String obs;
	private String numContrato;
	private String tokenCC;
	private Long idCodigoRetornoHub;
	private String cidContrato;
	
	public Long getIdFila() {
		return idFila;
	}
	public void setIdFila(Long idFila) {
		this.idFila = idFila;
	}
	public Long getIdBoleto() {
		return idBoleto;
	}
	public void setIdBoleto(Long idBoleto) {
		this.idBoleto = idBoleto;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public String getDataEnvioHub() {
		return dataEnvioHub;
	}
	public void setDataEnvioHub(String dataEnvioHub) {
		this.dataEnvioHub = dataEnvioHub;
	}
	public String getDataRetornoHub() {
		return dataRetornoHub;
	}
	public void setDataRetornoHub(String dataRetornoHub) {
		this.dataRetornoHub = dataRetornoHub;
	}
	public String getDataBaixaNetsms() {
		return dataBaixaNetsms;
	}
	public void setDataBaixaNetsms(String dataBaixaNetsms) {
		this.dataBaixaNetsms = dataBaixaNetsms;
	}
	public String getStatusBaixaNetsms() {
		return statusBaixaNetsms;
	}
	public void setStatusBaixaNetsms(String statusBaixaNetsms) {
		this.statusBaixaNetsms = statusBaixaNetsms;
	}
	public String getRequestHub() {
		return requestHub;
	}
	public void setRequestHub(String requestHub) {
		this.requestHub = requestHub;
	}
	public Long getIdLote() {
		return idLote;
	}
	public void setIdLote(Long idLote) {
		this.idLote = idLote;
	}
	public String getRespostaHub() {
		return respostaHub;
	}
	public void setRespostaHub(String respostaHub) {
		this.respostaHub = respostaHub;
	}
	public Long getNumeroTransacao() {
		return numeroTransacao;
	}
	public void setNumeroTransacao(Long numeroTransacao) {
		this.numeroTransacao = numeroTransacao;
	}
	public String getObs() {
		return obs;
	}
	public void setObs(String obs) {
		this.obs = obs;
	}
	public String getNumContrato() {
		return numContrato;
	}
	public void setNumContrato(String numContrato) {
		this.numContrato = numContrato;
	}
	public String getTokenCC() {
		return tokenCC;
	}
	public void setTokenCC(String tokenCC) {
		this.tokenCC = tokenCC;
	}
	
	public Long getIdCodigoRetornoHub() {
		return idCodigoRetornoHub;
	}
	
	public void setIdCodigoRetornoHub(Long idCodigoRetornoHub) {
		this.idCodigoRetornoHub = idCodigoRetornoHub;
	}
	
	public String getCidContrato() {
		return cidContrato;
	}
	public void setCidContrato(String cidContrato) {
		this.cidContrato = cidContrato;
	}
	
	@Override
	public String toString() {
		return "BaixaLogHubDTO [numeroTransacao=" + numeroTransacao
				+ ", idFila=" + idFila + ", idLote=" + idLote + ", idBoleto="
				+ idBoleto + ", valor=" + valor + ", dataEnvioHub="
				+ dataEnvioHub + ", dataRetornoHub=" + dataRetornoHub
				+ ", dataBaixaNetsms=" + dataBaixaNetsms + ", respostaHub="
				+ respostaHub + ", statusBaixaNetsms=" + statusBaixaNetsms
				+ ", requestHub=" + requestHub + ", obs=" + obs
				+ ", numContrato=" + numContrato + ", tokenCC=" + tokenCC + "]";
	}

}

	