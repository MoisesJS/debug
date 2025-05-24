package br.com.netservicos.novosms.emissao.dto;

import java.util.Date;

import br.com.netservicos.framework.core.bean.DomainBean;

public class DetalhamentoLigacaoParceiroDTO extends DomainBean {

	public static final String PRIMARY_KEY = "pk fake";
	
	private static final long serialVersionUID = 1L; 

	private String grupoNota;
	private String grupoItem;
	private String servico;
	private String descItem;
	private Long sequencia;
	private String telefoneOrigem;
	private String localidadeOrigem;
	private String telefoneDestino;
	private String localidadeDestino;
	private Date dataInicio;
	private String horaInicio;
	private Date dataFim;
	private String horaFim;
	private Long quantidade;
	private String unidadeTempo;	
	private String duracao;
	private Double valorItem;
	private Double duracaoTotal;
	private Integer idTipoItem;
	
	
	

	public Integer getIdTipoItem() {
		return idTipoItem;
	}

	public void setIdTipoItem(Integer idTipoItem) {
		this.idTipoItem = idTipoItem;
	}

	public DetalhamentoLigacaoParceiroDTO() {
		super(PRIMARY_KEY);
	}
	
	public String getGrupoNota() {
		return grupoNota;
	}

	public void setGrupoNota(String grupoNota) {
		this.grupoNota = grupoNota;
	}

	public String getGrupoItem() {
		return grupoItem;
	}

	public void setGrupoItem(String grupoItem) {
		this.grupoItem = grupoItem;
	}

	public String getServico() {
		return servico;
	}

	public void setServico(String servico) {
		this.servico = servico;
	}

	public String getDescItem() {
		return descItem;
	}

	public void setDescItem(String descItem) {
		this.descItem = descItem;
	}

	public Long getSequencia() {
		return sequencia;
	}

	public void setSequencia(Long sequencia) {
		this.sequencia = sequencia;
	}

	public String getTelefoneOrigem() {
		return telefoneOrigem;
	}

	public void setTelefoneOrigem(String telefoneOrigem) {
		this.telefoneOrigem = telefoneOrigem;
	}

	public String getLocalidadeOrigem() {
		return localidadeOrigem;
	}

	public void setLocalidadeOrigem(String localidadeOrigem) {
		this.localidadeOrigem = localidadeOrigem;
	}

	public String getTelefoneDestino() {
		return telefoneDestino;
	}

	public void setTelefoneDestino(String telefoneDestino) {
		this.telefoneDestino = telefoneDestino;
	}

	public String getLocalidadeDestino() {
		return localidadeDestino;
	}

	public void setLocalidadeDestino(String localidadeDestino) {
		this.localidadeDestino = localidadeDestino;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public String getHoraFim() {
		return horaFim;
	}

	public void setHoraFim(String horaFim) {
		this.horaFim = horaFim;
	}

	public Long getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Long quantidade) {
		this.quantidade = quantidade;
	}

	public String getUnidadeTempo() {
		return unidadeTempo;
	}

	public void setUnidadeTempo(String unidadeTempo) {
		this.unidadeTempo = unidadeTempo;
	}

	public String getDuracao() {
		return duracao;
	}

	public void setDuracao(String duracao) {
		this.duracao = duracao;
	}

	public Double getValorItem() {
		return valorItem;
	}

	public void setValorItem(Double valorItem) {
		this.valorItem = valorItem;
	}

	public Double getDuracaoTotal() {
		return duracaoTotal;
	}

	public void setDuracaoTotal(Double duracaoTotal) {
		this.duracaoTotal = duracaoTotal;
	}

}
