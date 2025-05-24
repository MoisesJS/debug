package br.com.netservicos.novosms.emissao.printhouse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import br.com.netservicos.novosms.emissao.dto.CobrancaParceiroDTO;
import br.com.netservicos.novosms.emissao.dto.DadosCobrancaDTO;
import br.com.netservicos.novosms.emissao.dto.DadosCobrancaParceiroDTO;
import br.com.netservicos.novosms.emissao.dto.DadosGeraisPrimeiraViaPrintDTO;
import br.com.netservicos.novosms.emissao.dto.DadosGeraisPrimeiraViaPrintParceiroDTO;
import br.com.netservicos.novosms.emissao.dto.DadosItensNFDTO;
import br.com.netservicos.novosms.emissao.dto.DadosTributosDTO;
import br.com.netservicos.novosms.emissao.dto.DemonstrativoFinanceiroDTO;
import br.com.netservicos.novosms.emissao.dto.DemonstrativoFinanceiroParceiroDTO;
import br.com.netservicos.novosms.emissao.dto.DetalhamentoLigacaoParceiroDTO;
import br.com.netservicos.novosms.emissao.dto.DetalhamentoNotaFiscalDTO;
import br.com.netservicos.novosms.emissao.dto.FiliadosDTO;
import br.com.netservicos.novosms.emissao.dto.MensagemClaroClubeDTO;
import br.com.netservicos.novosms.emissao.dto.MinhaNetDTO;
import br.com.netservicos.novosms.emissao.dto.TributoDTO;

public class FaturaNetDTO {

	private Long idBoleto;

	private Integer idLote;

	private Date dataVencimento;

	private Integer idCriterio;

	private Boolean operadoraNet;

	private String fcBoletoConsolidado;

	private String cidContrato;

	private Long numContrato;

	private Boolean possuiNF;

	private Double valorFust;

	private Double valorFuntel;
	
	/*
	 * Mensagem com os totais dos impostos parceiros
	 */
	private String msgTotaisImpostosParceiro;

	private String numFaturaParceiro;

	private String ultimasOcorrencias;

	private List<DadosGeraisPrimeiraViaPrintDTO> dadosGeraisNF;

	private Map<Long, List<TributoDTO>> tributos;

	private Map<Long, List<DetalhamentoNotaFiscalDTO>> detalheNotaFiscal;

	private List<MinhaNetDTO> minhaNet;

	private List<DemonstrativoFinanceiroDTO> demonstrativoFinanceiro;

	private List<DemonstrativoFinanceiroParceiroDTO> demostrativoFinanceiroParceiro;

	private List<FiliadosDTO> filiados;

	private CobrancaParceiroDTO fustFuntel;

	private DadosCobrancaParceiroDTO dadosCabecalhoParceiro;

	private List<Long> idParceiros;
	// por parceiro
	private Map<Long, List<DadosCobrancaParceiroDTO>> dadosNotaFiscalParceiro;
	// por nrIdCobranca
	private Map<Long, DadosGeraisPrimeiraViaPrintParceiroDTO> dadosClienteParceiro;
	// por idCobrnaca
	private Map<Long, List<DadosItensNFDTO>> itensNotaFiscalParceiro;
	// por idCobranca
	private Map<Long, List<DadosTributosDTO>> tributosNotaFiscalParceiro;
	// por idCobranca
	private Map<Long, List<DadosTributosDTO>> totalTributosNotaFiscalParceiro;

	private List<DetalhamentoLigacaoParceiroDTO> ligacoes;
	
	//Lista de mensagens de planos de serviço EBT
	private List<String> planoServicoEbt;
	private List<String> listMsgPlanoServicoEbt;

	// Cobrancas do parceiro sem notas fiscais
	private List<DadosCobrancaParceiroDTO> dadosCobrancaParceiroSemNF;

	private String empresaFatura;
	
	private List<MensagemClaroClubeDTO> mensagemClaroClubeDTO;
	
	public String getUltimasOcorrencias() {
		return ultimasOcorrencias;
	}

	public void setUltimasOcorrencias(String ultimasOcorrencias) {
		this.ultimasOcorrencias = ultimasOcorrencias;
	}

	// Cobrancas net sem notas fiscais
	private List<DadosCobrancaDTO> dadosCobrancaSemNF;

	public List<DetalhamentoLigacaoParceiroDTO> getLigacoes() {
		return ligacoes;
	}

	public void setLigacoes(List<DetalhamentoLigacaoParceiroDTO> ligacoes) {
		this.ligacoes = ligacoes;
	}
	
	public List<String> getPlanoServicoEbt() {
		return planoServicoEbt;
	}

	public void setPlanoServicoEbt(List<String> planoServicoEbt) {
		this.planoServicoEbt = planoServicoEbt;
	}

	public Map<Long, List<DetalhamentoNotaFiscalDTO>> getDetalheNotaFiscal() {
		return detalheNotaFiscal;
	}

	public void setDetalheNotaFiscal(
			Map<Long, List<DetalhamentoNotaFiscalDTO>> detalheNotaFiscal) {
		this.detalheNotaFiscal = detalheNotaFiscal;
	}

	public Map<Long, List<TributoDTO>> getTributos() {
		return tributos;
	}

	public void setTributos(Map<Long, List<TributoDTO>> tributos) {
		this.tributos = tributos;
	}

	public List<TributoDTO> getTributos(Long idCobrancaNotaFiscal) {
		return this.getTributos().get(idCobrancaNotaFiscal) != null ? this
				.getTributos().get(idCobrancaNotaFiscal) : null;
	}

	public List<DetalhamentoNotaFiscalDTO> getDetalheNotaFiscal(
			Long idCobrancaNotaFiscal) {
		return this.getDetalheNotaFiscal().get(idCobrancaNotaFiscal) != null ? this
				.getDetalheNotaFiscal().get(idCobrancaNotaFiscal)
				: null;
	}

	public List<DemonstrativoFinanceiroDTO> getDemonstrativoFinanceiro() {
		return demonstrativoFinanceiro;
	}

	public void setDemonstrativoFinanceiro(
			List<DemonstrativoFinanceiroDTO> demonstrativoFinanceiro) {
		this.demonstrativoFinanceiro = demonstrativoFinanceiro;
	}

	public Map<Long, DadosGeraisPrimeiraViaPrintParceiroDTO> getDadosClienteParceiro() {
		return dadosClienteParceiro;
	}

	public DadosGeraisPrimeiraViaPrintParceiroDTO getDadosClienteParceiro(
			Long idCobranca) {
		return this.getDadosClienteParceiro().get(idCobranca) != null ? this
				.getDadosClienteParceiro().get(idCobranca) : null;
	}

	public void setDadosClienteParceiro(
			Map<Long, DadosGeraisPrimeiraViaPrintParceiroDTO> dadosClienteParceiro) {
		this.dadosClienteParceiro = dadosClienteParceiro;
	}

	public Map<Long, List<DadosCobrancaParceiroDTO>> getDadosNotaFiscalParceiro() {
		return dadosNotaFiscalParceiro;
	}

	public List<DadosCobrancaParceiroDTO> getDadosNotaFiscalParceiro(
			Long idParceiro) {
		return this.getDadosNotaFiscalParceiro().get(idParceiro) != null ? this
				.getDadosNotaFiscalParceiro().get(idParceiro) : null;
	}

	public void setDadosNotaFiscalParceiro(
			Map<Long, List<DadosCobrancaParceiroDTO>> dadosNotaFiscalParceiro) {
		this.dadosNotaFiscalParceiro = dadosNotaFiscalParceiro;
	}

	public List<DemonstrativoFinanceiroParceiroDTO> getDemostrativoFinanceiroParceiro() {
		return demostrativoFinanceiroParceiro;
	}

	public void setDemostrativoFinanceiroParceiro(
			List<DemonstrativoFinanceiroParceiroDTO> demostrativoFinanceiroParceiro) {
		this.demostrativoFinanceiroParceiro = demostrativoFinanceiroParceiro;
	}

	public CobrancaParceiroDTO getFustFuntel() {
		return fustFuntel;
	}

	public void setFustFuntel(CobrancaParceiroDTO fustFuntel) {
		this.fustFuntel = fustFuntel;
	}

	public Map<Long, List<DadosItensNFDTO>> getItensNotaFiscalParceiro() {
		return itensNotaFiscalParceiro;
	}

	public List<DadosItensNFDTO> getItensNotaFiscalParceiro(Long idCobranca) {
		return this.getItensNotaFiscalParceiro().get(idCobranca) != null ? this
				.getItensNotaFiscalParceiro().get(idCobranca) : null;
	}

	public void setItensNotaFiscalParceiro(
			Map<Long, List<DadosItensNFDTO>> itensNotaFiscalParceiro) {
		this.itensNotaFiscalParceiro = itensNotaFiscalParceiro;
	}

	public Map<Long, List<DadosTributosDTO>> getTotalTributosNotaFiscalParceiro() {
		return totalTributosNotaFiscalParceiro;
	}

	public List<DadosTributosDTO> getTotalTributosNotaFiscalParceiro(
			Long idCobranca) {
		return this.getTotalTributosNotaFiscalParceiro().get(idCobranca) != null ? this
				.getTotalTributosNotaFiscalParceiro().get(idCobranca)
				: null;
	}

	public void setTotalTributosNotaFiscalParceiro(
			Map<Long, List<DadosTributosDTO>> totalTributosNotaFiscalParceiro) {
		this.totalTributosNotaFiscalParceiro = totalTributosNotaFiscalParceiro;
	}

	public List<DadosTributosDTO> getTributosNotaFiscalParceiro(Long idCobranca) {
		return this.getTributosNotaFiscalParceiro().get(idCobranca) != null ? this
				.getTributosNotaFiscalParceiro().get(idCobranca)
				: null;
	}

	public void setTributosNotaFiscalParceiro(
			Map<Long, List<DadosTributosDTO>> tributosNotaFiscalParceiro) {
		this.tributosNotaFiscalParceiro = tributosNotaFiscalParceiro;
	}

	public List<DadosGeraisPrimeiraViaPrintDTO> getDadosGeraisNF() {
		return dadosGeraisNF;
	}

	public void setDadosGeraisNF(
			List<DadosGeraisPrimeiraViaPrintDTO> dadosGeraisNF) {
		this.dadosGeraisNF = dadosGeraisNF;
	}

	public Long getIdBoleto() {
		return idBoleto;
	}

	public void setIdBoleto(Long idBoleto) {
		this.idBoleto = idBoleto;
	}

	public Map<Long, List<DadosTributosDTO>> getTributosNotaFiscalParceiro() {
		return tributosNotaFiscalParceiro;
	}

	public Boolean getPossuiNF() {
		return possuiNF;
	}

	public void setPossuiNF(Boolean possuiNF) {
		this.possuiNF = possuiNF;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public Integer getIdLote() {
		return idLote;
	}

	public void setIdLote(Integer idLote) {
		this.idLote = idLote;
	}

	public Integer getIdCriterio() {
		return idCriterio;
	}

	public void setIdCriterio(Integer idCriterio) {
		this.idCriterio = idCriterio;
	}

	public Boolean isOperadoraNet() {
		return operadoraNet;
	}

	public void setOperadoraNet(Boolean operadoraNet) {
		this.operadoraNet = operadoraNet;
	}

	public String getFcBoletoConsolidado() {
		return fcBoletoConsolidado;
	}

	public void setFcBoletoConsolidado(String fcBoletoConsolidado) {
		this.fcBoletoConsolidado = fcBoletoConsolidado;
	}

	public String getCidContrato() {
		return cidContrato;
	}

	public void setCidContrato(String cidContrato) {
		this.cidContrato = cidContrato;
	}

	public Long getNumContrato() {
		return numContrato;
	}

	public void setNumContrato(Long numContrato) {
		this.numContrato = numContrato;
	}

	public DadosCobrancaParceiroDTO getDadosCabecalhoParceiro() {
		return dadosCabecalhoParceiro;
	}

	public void setDadosCabecalhoParceiro(
			DadosCobrancaParceiroDTO dadosCabecalhoParceiro) {
		this.dadosCabecalhoParceiro = dadosCabecalhoParceiro;
	}

	public List<Long> getIdParceiros() {
		return idParceiros;
	}

	public void setIdParceiros(List<Long> idParceiros) {
		this.idParceiros = idParceiros;
	}

	public Double getValorFuntel() {
		return valorFuntel;
	}

	public void setValorFuntel(Double valorFuntel) {
		this.valorFuntel = valorFuntel;
	}

	public Double getValorFust() {
		return valorFust;
	}

	public void setValorFust(Double valorFust) {
		this.valorFust = valorFust;
	}

	public String getMsgTotaisImpostosParceiro() {
		return msgTotaisImpostosParceiro;
	}

	public void setMsgTotaisImpostosParceiro(String msgTotaisImpostosParceiro) {
		this.msgTotaisImpostosParceiro = msgTotaisImpostosParceiro;
	}

	public String getNumFaturaParceiro() {
		return numFaturaParceiro;
	}

	public void setNumFaturaParceiro(String numFaturaParceiro) {
		this.numFaturaParceiro = numFaturaParceiro;
	}

	public List<MinhaNetDTO> getMinhaNet() {
		return minhaNet;
	}

	public void setMinhaNet(List<MinhaNetDTO> minhaNet) {
		this.minhaNet = minhaNet;
	}

	public int hashCode() {
		final int PRIME = 31;
		Integer result = 1;
		result = PRIME * result + idBoleto.intValue();
		return result;
	}

	public List<FiliadosDTO> getFiliados() {
		return filiados;
	}

	public void setFiliados(List<FiliadosDTO> filiados) {
		this.filiados = filiados;
	}

	public boolean equals(Object obj) {

		final FaturaNetDTO other = (FaturaNetDTO) obj;
		if (idBoleto == other.getIdBoleto())
			return true;
		return false;
	}

	public List<DadosCobrancaParceiroDTO> getDadosCobrancaParceiroSemNF() {
		return dadosCobrancaParceiroSemNF;
	}

	public void setDadosCobrancaParceiroSemNF(
			List<DadosCobrancaParceiroDTO> dadosCobrancaParceiroSemNF) {
		this.dadosCobrancaParceiroSemNF = dadosCobrancaParceiroSemNF;
	}

	public List<DadosCobrancaDTO> getDadosCobrancaSemNF() {
		return dadosCobrancaSemNF;
	}

	public void setDadosCobrancaSemNF(List<DadosCobrancaDTO> dadosCobrancaSemNF) {
		this.dadosCobrancaSemNF = dadosCobrancaSemNF;
	}

	public List<String> getListMsgPlanoServicoEbt() {
		return listMsgPlanoServicoEbt;
	}

	public void setListMsgPlanoServicoEbt(List<String> listMsgPlanoServicoEbt) {
		this.listMsgPlanoServicoEbt = listMsgPlanoServicoEbt;
	}

	public String getEmpresaFatura() {
		return empresaFatura;
	}

	public void setEmpresaFatura(String empresaFatura) {
		this.empresaFatura = empresaFatura;
	}
	
	public List<MensagemClaroClubeDTO> getMensagemClaroClubeDTO() {
		return mensagemClaroClubeDTO;
	}

	public void setMensagemClaroClubeDTO(List<MensagemClaroClubeDTO> mensagemClaroClube) {
		this.mensagemClaroClubeDTO = mensagemClaroClube;
	}
	
}
