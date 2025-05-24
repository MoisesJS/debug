package br.com.netservicos.novosms.emissao.dto;

import java.io.Serializable;
import java.util.Map;

public class DadosFatura implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6476742399950512877L;
	private Long idControleArquivo;
	private Long numContrato;
	private String cidContrato;
	private Long idBoleto;
	private String ultimasOcorrencias;
	private String sgTipoLote;
	private Map<Long, Object[]> mapCobrancaNet;
	private Map<Long, Object[]> mapCobrancaParceiro;
	private Integer formaEnvioFatura;

	//NSMSP_161258_NI_001\JAVA
	private MensagemClaroClubeDTO mensagemClaroClubeDTO;

	/**
	 * Contador de paginas da fatura
	 */
	private int numPaginaNet;
	private int numPaginaParceiro;

	public int getNumPaginaNet() {
		return numPaginaNet;
	}

	public void setNumPaginaNet(int numPaginaNet) {
		this.numPaginaNet = numPaginaNet;
	}

	public int getNumPaginaParceiro() {
		return numPaginaParceiro;
	}

	public void setNumPaginaParceiro(int numPaginaParceiro) {
		this.numPaginaParceiro = numPaginaParceiro;
	}

	private Integer[] tipo;

	public Long getIdControleArquivo() {
		return idControleArquivo;
	}

	public void setIdControleArquivo(Long idControleArquivo) {
		this.idControleArquivo = idControleArquivo;
	}

	public Long getNumContrato() {
		return numContrato;
	}

	public void setNumContrato(Long numContrato) {
		this.numContrato = numContrato;
	}

	public String getCidContrato() {
		return cidContrato;
	}

	public void setCidContrato(String cidContrato) {
		this.cidContrato = cidContrato;
	}

	public Long getIdBoleto() {
		return idBoleto;
	}

	public void setIdBoleto(Long idBoleto) {
		this.idBoleto = idBoleto;
	}

	public String getUltimasOcorrencias() {
		return ultimasOcorrencias;
	}

	public void setUltimasOcorrencias(String ultimasOcorrencias) {
		this.ultimasOcorrencias = ultimasOcorrencias;
	}

	public String getSgTipoLote() {
		return sgTipoLote;
	}

	public void setSgTipoLote(String sgTipoLote) {
		this.sgTipoLote = sgTipoLote;
	}

	public Map<Long, Object[]> getMapCobrancaNet() {
		return mapCobrancaNet;
	}

	public void setMapCobrancaNet(Map<Long, Object[]> mapCobrancaNet) {
		this.mapCobrancaNet = mapCobrancaNet;
	}

	public Map<Long, Object[]> getMapCobrancaParceiro() {
		return mapCobrancaParceiro;
	}

	public void setMapCobrancaParceiro(Map<Long, Object[]> mapCobrancaParceiro) {
		this.mapCobrancaParceiro = mapCobrancaParceiro;
	}

	public Integer[] getTipo() {
		return tipo;
	}

	public void setTipo(Integer[] tipo) {
		this.tipo = tipo;
	}

	public Integer getFormaEnvioFatura() {
		return formaEnvioFatura;
	}

	public void setFormaEnvioFatura(Integer formaEnvioFatura) {
		this.formaEnvioFatura = formaEnvioFatura;
	}	

	/**
	 * @return the mensagemClaroClubeDTO
	 */
	public MensagemClaroClubeDTO getMensagemClaroClubeDTO() {
		return mensagemClaroClubeDTO;
	}

	/**
	 * @param mensagemClaroClubeDTO the mensagemClaroClubeDTO to set
	 */
	public void setMensagemClaroClubeDTO(MensagemClaroClubeDTO mensagemClaroClubeDTO) {
		this.mensagemClaroClubeDTO = mensagemClaroClubeDTO;
	}	
}
