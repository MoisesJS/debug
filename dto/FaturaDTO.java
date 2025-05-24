package br.com.netservicos.novosms.emissao.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import br.com.netservicos.framework.core.bean.DomainBean;

public class FaturaDTO extends DomainBean {

	public FaturaDTO() {
		super(PRIMARY_KEY);
	}

	public static final String PRIMARY_KEY = "pk fake";

	/**
	 *
	 */
	private static final long serialVersionUID = -4213743142591252508L;

	/**
	 * Lista que contem as linhas (LinhaDTO) que serao gravadas no arquivo a ser
	 * enviada para a print house.
	 */
	private Collection<LinhaDTO> listaLinha;

	/** Quantidade de paginas que ha na fatura. */
	private Integer quantidadePaginas;

	/** Valor cobrado do boleto. */
	private Double valorCobrado;

//	private List<Long> idCobrancaNet;
//	private List<Long> idCobrancaParceiro;
//	private String fcBoletoConsolidado;

	private Long idControleArquivo;
	private Long numContrato;
	private String cidContrato;
	private Long idBoleto;
	private String sgTipoLote;
	private Map<Long, Object[]> mapCobrancaNet;
	private Map<Long, Object[]> mapCobrancaParceiro;
	private String ultimasOcorrencias;

	/**
	 * Contador de paginas da fatura
	 */
	private int numPaginaNet;
	private int numPaginaParceiro;

	private List<String> listMsgPlanoServicoEbt;

	/*
	public List<Long> getIdCobrancaNet() {
		return idCobrancaNet;
	}

	public void setIdCobrancaNet(List<Long> idCobrancaNet) {
		this.idCobrancaNet = idCobrancaNet;
	}

	public List<Long> getIdCobrancaParceiro() {
		return idCobrancaParceiro;
	}

	public void setIdCobrancaParceiro(List<Long> idCobrancaParceiro) {
		this.idCobrancaParceiro = idCobrancaParceiro;
	}

	public String getFcBoletoConsolidado() {
		return fcBoletoConsolidado;
	}

	public void setFcBoletoConsolidado(String fcBoletoConsolidado) {
		this.fcBoletoConsolidado = fcBoletoConsolidado;
	}
	*/

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

	public String getUltimasOcorrencias() {
		return ultimasOcorrencias;
	}

	public void setUltimasOcorrencias(String ultimasOcorrencias) {
		this.ultimasOcorrencias = ultimasOcorrencias;
	}

	public Collection getListaLinha() {
		return this.listaLinha;
	}

	/**
	 * Adiciona uma linha na fatura.
	 *
	 * @version 1.0 - 05/04/2006 - Criacao
	 * @author Everton Ken Tomita
	 * @number RF015-021
	 *
	 * @param linha
	 *            que representa uma linha dentro do arquivo a ser enviado para
	 *            a print house
	 *
	 * @semantics
	 *
	 * Adiciona a linha para a lista (listaLinha).
	 *
	 */
	public void addLinha(LinhaDTO linha) {
		if (listaLinha == null || listaLinha.isEmpty()) {
			listaLinha = new ArrayList<LinhaDTO>();
		}
		listaLinha.add(linha);
	}

	/**
	 * Retorna a linha que esta dentro da fatura.
	 *
	 * @version 1.0 - 05/04/2006 - Criacao
	 * @author Everton Ken Tomita
	 * @number RF015-021
	 *
	 * @param indice
	 *            que deve ser pego dentro da lista.
	 * @return Linha que foi encontrada atraves da posicao especificada.
	 *
	 * @semantics
	 *
	 * Retorna a linha (Linha) na posicao (indice) que esta dentro da lista
	 * (listaLinha)
	 *
	 */
	public LinhaDTO get(Integer indice) {
		return (LinhaDTO) ((ArrayList) listaLinha).get(indice.intValue());
	}

	/**
	 * Retorna quantidade de linhas existente na fatura.
	 *
	 * @version 1.0 - 06/04/2006 - Criacao
	 * @author Everton Ken Tomita
	 * @number RF015-021
	 *
	 * @return Integer com o total de linhas dentro da fatura.
	 *
	 * @semantics
	 *
	 * Retorna (listaLinha.size)
	 */
	public Integer getQuantidadeLinhas() {
		Integer retorno = listaLinha == null ? new Integer(0) : new Integer(
				listaLinha.size());
		return retorno;
	}

	/**
	 * Retorna quantidade de paginas existente na fatura.
	 *
	 * @version 1.0 - 05/04/2006 - Criacao
	 * @author Everton Ken Tomita
	 * @number RF015-021
	 *
	 * @return Integer com o total de paginas dentro da fatura.
	 *
	 * @semantics
	 *
	 * Retorna (quantidadePaginas)
	 */
	public Integer getQuantidadePaginas() {
		return quantidadePaginas;
	}

	/**
	 * Seta a quantidade de paginas dentro da fatura.
	 *
	 * @version 1.0 - 05/04/2006 - Criacao
	 * @author Everton Ken Tomita
	 * @number RF015-021
	 *
	 * @param Integer
	 *            com o total de paginas dentro da fatura
	 *
	 * @semantics
	 *
	 * Seta (quantidadePaginas) com o valor do parametro
	 */
	public void setQuantidadePaginas(Integer quantidadePaginas) {
		this.quantidadePaginas = quantidadePaginas;
	}

	/**
	 * Retorna valor cobrado da fatura.
	 *
	 * @version 1.0 - 05/04/2006 - Criacao
	 * @author Everton Ken Tomita
	 * @number RF015-021
	 *
	 * @return JNetDecimal com o valor cobrado
	 *
	 * @semantics
	 *
	 * Retorna (valorCobrado)
	 */
	public Double getValorCobrado() {
		return valorCobrado;
	}

	/**
	 * Seta o valor cobrado da fatura.
	 *
	 * @version 1.0 - 05/04/2006 - Criacao
	 * @author Everton Ken Tomita
	 * @number RF015-021
	 *
	 * @param Integer
	 *            com o total de paginas dentro da fatura
	 *
	 * @semantics
	 *
	 * Seta (valorCobrado) com o valor do parametro
	 */
	public void setValorCobrado(Double valorCobrado) {
		this.valorCobrado = valorCobrado;
	}

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

	public List<String> getListMsgPlanoServicoEbt() {
		return listMsgPlanoServicoEbt;
	}

	public void setListMsgPlanoServicoEbt(List<String> listMsgPlanoServicoEbt) {
		this.listMsgPlanoServicoEbt = listMsgPlanoServicoEbt;
	}
	
}
