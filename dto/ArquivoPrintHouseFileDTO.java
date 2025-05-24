package br.com.netservicos.novosms.emissao.dto;

import java.io.File;

import br.com.netservicos.framework.core.bean.DomainBean;

public class ArquivoPrintHouseFileDTO extends DomainBean {
	public static final String PRIMARY_KEY = "pk fake";

	/**
	 * 
	 */
	private static final long serialVersionUID = -2724791494436235665L;

	protected Long idControleArquivo;
	
	/**
	 * Arquivo gerado
	 */
	protected File fileGerado;
	/**
	 * Sigla do tipo do lote para este zip<br>
	 */
	protected String siglaTipoLote;

	/**
	 * 
	 */
	public ArquivoPrintHouseFileDTO(File fileGerado, String siglaTipoLote) {
		super(PRIMARY_KEY);
		this.fileGerado = fileGerado;
		this.siglaTipoLote = siglaTipoLote;
	}

	/**
	 * 
	 */
	public ArquivoPrintHouseFileDTO(File fileGerado, String siglaTipoLote, Long idControleArquivo) {
		this(fileGerado, siglaTipoLote);
		this.idControleArquivo = idControleArquivo;
	}
	
	/**
	 * @return the siglaTipoLote
	 */
	public String getSiglaTipoLote() {
		return siglaTipoLote;
	}

	/**
	 * @return the zipFile
	 */
	public File getFileGerado() {
		return fileGerado;
	}

	/**
	 * @return the idControleArquivo
	 */
	public Long getIdControleArquivo() {
		return idControleArquivo;
	}

	/**
	 * @param idControleArquivo the idControleArquivo to set
	 */
	public void setIdControleArquivo(Long idControleArquivo) {
		this.idControleArquivo = idControleArquivo;
	}
}
