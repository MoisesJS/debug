package br.com.netservicos.novosms.emissao.dto;


import java.io.File;

import br.com.netservicos.framework.core.bean.DomainBean;
import br.com.netservicos.framework.transporte.ProtocoloTransporteEnum;

public class ArquivoRemessaTransporteDTO extends DomainBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4966026161106859929L;

	public ArquivoRemessaTransporteDTO() {
		super("");
		// TODO Auto-generated constructor stub
	}

	private Long idControleArquivo;
	
	private String cidContrato;
	
	private File localFile;
	
	private String destinoFileName;
	
	private ProtocoloTransporteEnum protocoloTransporte;
	
	private String nomeServidorDestino;
	
	private Integer portaServidorDestino;
	
	private String usuarioServidorDestino;
	
	private String senhaServidorDestino;
	
	private String pastaServidorDestino;

	private Boolean apagarArquivoOrigem;
	
	/**
	 * @return the localFile
	 */
	public File getLocalFile() {
		return localFile;
	}

	/**
	 * @param localFile the localFile to set
	 */
	public void setLocalFile(File localFile) {
		this.localFile = localFile;
	}

	/**
	 * @return the nomeServidorDestino
	 */
	public String getNomeServidorDestino() {
		return nomeServidorDestino;
	}

	/**
	 * @param nomeServidorDestino the nomeServidorDestino to set
	 */
	public void setNomeServidorDestino(String nomeServidorDestino) {
		this.nomeServidorDestino = nomeServidorDestino;
	}

	/**
	 * @return the pastaServidorDestino
	 */
	public String getPastaServidorDestino() {
		return pastaServidorDestino;
	}

	/**
	 * @param pastaServidorDestino the pastaServidorDestino to set
	 */
	public void setPastaServidorDestino(String pastaServidorDestino) {
		this.pastaServidorDestino = pastaServidorDestino;
	}

	/**
	 * @return the portaServidorDestino
	 */
	public Integer getPortaServidorDestino() {
		return portaServidorDestino;
	}

	/**
	 * @param portaServidorDestino the portaServidorDestino to set
	 */
	public void setPortaServidorDestino(Integer portaServidorDestino) {
		this.portaServidorDestino = portaServidorDestino;
	}

	/**
	 * @return the protocoloTransporte
	 */
	public ProtocoloTransporteEnum getProtocoloTransporte() {
		return protocoloTransporte;
	}

	/**
	 * @param protocoloTransporte the protocoloTransporte to set
	 */
	public void setProtocoloTransporte(ProtocoloTransporteEnum protocoloTransporte) {
		this.protocoloTransporte = protocoloTransporte;
	}

	/**
	 * @return the senhaServidorDestino
	 */
	public String getSenhaServidorDestino() {
		return senhaServidorDestino;
	}

	/**
	 * @param senhaServidorDestino the senhaServidorDestino to set
	 */
	public void setSenhaServidorDestino(String senhaServidorDestino) {
		this.senhaServidorDestino = senhaServidorDestino;
	}

	/**
	 * @return the usuarioServidorDestino
	 */
	public String getUsuarioServidorDestino() {
		return usuarioServidorDestino;
	}

	/**
	 * @param usuarioServidorDestino the usuarioServidorDestino to set
	 */
	public void setUsuarioServidorDestino(String usuarioServidorDestino) {
		this.usuarioServidorDestino = usuarioServidorDestino;
	}

	/**
	 * @return the destinoFileName
	 */
	public String getDestinoFileName() {
		return destinoFileName;
	}

	/**
	 * @param destinoFileName the destinoFileName to set
	 */
	public void setDestinoFileName(String destinoFileName) {
		this.destinoFileName = destinoFileName;
	}

	public Long getIdControleArquivo() {
		return idControleArquivo;
	}

	public void setIdControleArquivo(Long idControleArquivo) {
		this.idControleArquivo = idControleArquivo;
	}
	
	public Boolean getApagarArquivoOrigem() {
		return apagarArquivoOrigem;
	}

	public void setApagarArquivoOrigem(Boolean apagarArquivoOrigem) {
		this.apagarArquivoOrigem = apagarArquivoOrigem;
	}

	public String getCidContrato() {
		return cidContrato;
	}

	public void setCidContrato(String cidContrato) {
		this.cidContrato = cidContrato;
	}
}
