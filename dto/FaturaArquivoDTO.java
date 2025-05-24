package br.com.netservicos.novosms.emissao.dto;

import java.util.Collection;

import br.com.netservicos.framework.core.bean.DomainBean;

public class FaturaArquivoDTO extends DomainBean {

	public FaturaArquivoDTO() {
		super(PRIMARY_KEY);
	}

	public static final String PRIMARY_KEY = "pk fake";

	/**
	 * 
	 */
	private static final long serialVersionUID = 21948756550029974L;
	private LinhaDTO dadosOperadora;
	private Collection<LinhaDTO> listaMensagem;
	private Collection<LinhaDTO> listaBanco;
	private FaturaDTO faturaDTO;

	public FaturaDTO getFaturaDTO() {
		return faturaDTO;
	}

	public void setFaturaDTO(FaturaDTO faturaDTO) {
		this.faturaDTO = faturaDTO;
	}

	public Collection<LinhaDTO> getListaBanco() {
		return listaBanco;
	}

	public void setListaBanco(Collection<LinhaDTO> listaBanco) {
		this.listaBanco = listaBanco;
	}

	public Collection getListaMensagem() {
		return listaMensagem;
	}

	public void setListaMensagem(Collection<LinhaDTO> listaMensagem) {
		this.listaMensagem = listaMensagem;
	}

	public LinhaDTO getDadosOperadora() {
		return dadosOperadora;
	}

	public void setDadosOperadora(LinhaDTO dadosOperadora) {
		this.dadosOperadora = dadosOperadora;
	}
}
