package br.com.netservicos.novosms.emissao.dto;


import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import br.com.netservicos.core.bean.sn.SnCidadeOperadoraBean;

public class ArquivoPrintHouseZipDTO extends ArquivoPrintHouseFileDTO {
	public static final String PRIMARY_KEY = "pk fake"; 

	/**
	 * 
	 */
	private static final long serialVersionUID = 2420292921587702667L;

	/**
	 * Coleção do controle arquivos que esta dentro o zip
	 */
	private Collection<ArquivoPrintHouseFileDTO> arquivoPrintHouseFiles;

	/**
	 * A operadora para este zip
	 */
	private SnCidadeOperadoraBean cidade;

	private String prefixoNome;
	
	private Date dtVencimento; 

	/**
	 * Construtor
	 * 
	 * @param zipFile
	 *            Zip file
	 * @param siglaTipoLote
	 *            Sigla do tipo do lote
	 */
	public ArquivoPrintHouseZipDTO(File zipFile, String siglaTipoLote, SnCidadeOperadoraBean cidade, String prefixoNome, Date dtVencimento) {
		super(zipFile, siglaTipoLote);
		this.cidade = cidade;
		this.arquivoPrintHouseFiles = new ArrayList();
		this.prefixoNome = prefixoNome;
		this.dtVencimento = dtVencimento;
	}

	/**
	 * Construtor
	 * 
	 * @param zipFile
	 *            Zip file
	 * @param siglaTipoLote
	 *            Sigla do tipo do lote
	 */
	public ArquivoPrintHouseZipDTO(File zipFile, String siglaTipoLote, SnCidadeOperadoraBean cidade, Date dtVencimento) {
		super(zipFile, siglaTipoLote);
		this.cidade = cidade;
		this.arquivoPrintHouseFiles = new ArrayList();
		this.dtVencimento = dtVencimento;
	}

	/**
	 * Adcionar uma arquivo dentro o coleção do arquivos
	 * 
	 * @param arquivo
	 *            Controle Arquivo para addicionar
	 */
	public void addArquivoPrintHouseFile(
			ArquivoPrintHouseFileDTO arquivoPrintHouseFile) {
		this.arquivoPrintHouseFiles.add(arquivoPrintHouseFile);
	}

	/**
	 * @return the Controle Arquivo's
	 */
	public Collection getArquivoPrintHouseFiles() {
		return this.arquivoPrintHouseFiles;
	}

	/**
	 * @return a cidade
	 */
	public SnCidadeOperadoraBean getSnCidadeOperadoraBean() {
		return cidade;
	}

	public String getPrefixoNome() {
		return prefixoNome;
	}

	public Date getDtVencimento() {
		return dtVencimento;
	}

	public void setDtVencimento(Date dtVencimento) {
		this.dtVencimento = dtVencimento;
	} 


	
}
