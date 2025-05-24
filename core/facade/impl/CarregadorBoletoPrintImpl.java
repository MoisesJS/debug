package br.com.netservicos.novosms.emissao.core.facade.impl;

import static br.com.netservicos.core.bean.sn.SnTipoLoteBean.Sigla.EMISSAO_PRINT_BOLETO_AVULSO;

import java.util.ArrayList;
import java.util.List;

import br.com.netservicos.core.bean.sn.SnTipoLoteBean;
import br.com.netservicos.novosms.emissao.core.dao.ArquivoPrintHouseDAO;
import br.com.netservicos.novosms.emissao.dto.ArquivoPrintHouseDTO;
import br.com.netservicos.novosms.emissao.dto.LoteBoletoFaturaDTO;
import br.com.netservicos.novosms.emissao.printhouse.FaturaNetDTO;

class CarregadorBoletoPrintImpl implements CarregadorBoletoPrint{

	/*
	 * Constante para verificação de arquivo printHouse
	 */
	private static final boolean	  FLAG_ARQUIVO_PRINT	= true;
	
	private static final String	      TPPRIMEIRAVIA	     = "P";
	private static final String	      TPSEGUNDAVIA	     = "S";

	private List<LoteBoletoFaturaDTO>	boletosNet	     = new ArrayList<LoteBoletoFaturaDTO>();
	private List<LoteBoletoFaturaDTO>	boletosEbt	     = new ArrayList<LoteBoletoFaturaDTO>();
	private List<LoteBoletoFaturaDTO>	boletosNetEbt	 = new ArrayList<LoteBoletoFaturaDTO>();
	private List<LoteBoletoFaturaDTO>	boletosAvulsos 	 = new ArrayList<LoteBoletoFaturaDTO>();

	private String tipoLote;

	public List<FaturaNetDTO> carrega(EventoCargaBoletoPrint evento) throws Exception{
		checkTipoLote(evento.getDto());
		
		LoteBoletoFaturaDTO[] boletos = evento.getBoletos();

		Long[] ids = new Long[boletos.length];
		int x = 0;

		for (LoteBoletoFaturaDTO boleto : boletos) {
			ids[x] = boleto.getIdBoleto();
			x++;

			separaBoletoPorParceiro(boleto);
		}

		List<FaturaNetDTO> faturas = carregaDadosFatura(evento);

		return agrupaBoletosOrdemInicial(ids, faturas);
	}

	private void checkTipoLote(ArquivoPrintHouseDTO dto){
		tipoLote = TPPRIMEIRAVIA;

		if (SnTipoLoteBean.Sigla.SEGUNDA_VIA.getChaveSigla().equals(dto.getSiglaTipoLote())) {
			tipoLote = TPSEGUNDAVIA;
		}
	}

	private void separaBoletoPorParceiro(LoteBoletoFaturaDTO boleto){
		String flagBoletoConsolidado = boleto.getFcBoletoConsolidado();
		
		if (boleto.getSiglaLote().equals(EMISSAO_PRINT_BOLETO_AVULSO.getChaveSigla())){
			boletosAvulsos.add(boleto);
			return;
		}

		if (flagBoletoConsolidado.equalsIgnoreCase("N") ||
		        flagBoletoConsolidado.equalsIgnoreCase("R") ||
		        (flagBoletoConsolidado.equalsIgnoreCase("D") && boleto.isOperadoraNet()) ||
		        flagBoletoConsolidado.equalsIgnoreCase("M")) {

			boletosNet.add(boleto);
			return;
		}

		if (flagBoletoConsolidado.equalsIgnoreCase("C") || flagBoletoConsolidado.equalsIgnoreCase("B")) {

			boletosNetEbt.add(boleto);
			return;
		}

		boletosEbt.add(boleto);
	}

	private List<FaturaNetDTO> agrupaBoletosOrdemInicial(Long[] ids, List<FaturaNetDTO> faturas){
		List<FaturaNetDTO> faturasOrdenadas = new ArrayList<FaturaNetDTO>();

		for (Long idBoleto : ids) {
			FaturaNetDTO faturaPesquisa = new FaturaNetDTO();
			faturaPesquisa.setIdBoleto(idBoleto);

			int indice = faturas.indexOf(faturaPesquisa);
            
			if (indice >= 0) {
			    faturasOrdenadas.add(faturas.get(indice));
            }    
		}

		return faturasOrdenadas;
	}

	private List<FaturaNetDTO> carregaDadosFatura(EventoCargaBoletoPrint evento) throws Exception{
		ArquivoPrintHouseDAO dao = evento.getDao();
		String criterio = evento.getCriterio();

		List<FaturaNetDTO> faturas = new ArrayList<FaturaNetDTO>();
		
		carregaDadosFaturaNet(faturas, dao, criterio);
		carregaDadosFaturaNetEbt(faturas, dao, criterio);
		carregaDadosFaturaEbt(faturas, dao, criterio);
		carregaDadosFaturasBoletoAvulso(faturas, dao, criterio);

		return faturas;
	}

	private void carregaDadosFaturaEbt(List<FaturaNetDTO> faturas,
	                                   ArquivoPrintHouseDAO dao,
	                                   String criterio) throws Exception{
		if (!boletosEbt.isEmpty()) {
			faturas.addAll(dao.buscarDadosFaturaEbt(boletosEbt, criterio, FLAG_ARQUIVO_PRINT));
		}
	}

	private void carregaDadosFaturaNetEbt(List<FaturaNetDTO> faturas,
	                                      ArquivoPrintHouseDAO dao,
	                                      String criterio) throws Exception{
		if (!boletosNetEbt.isEmpty()) {
			faturas.addAll(dao.buscarDadosFaturaNetEbt(boletosNetEbt, criterio, tipoLote, FLAG_ARQUIVO_PRINT));
		}
	}

	private void carregaDadosFaturaNet(List<FaturaNetDTO> faturas,
	                                   ArquivoPrintHouseDAO dao,
	                                   String criterio) throws Exception{
		if (!boletosNet.isEmpty()) {
			faturas.addAll(dao.buscarDadosFaturaNet(boletosNet, criterio, null, tipoLote, FLAG_ARQUIVO_PRINT));
		}
	}
	
	private void carregaDadosFaturasBoletoAvulso(List<FaturaNetDTO> faturas,
	                                             ArquivoPrintHouseDAO dao,
	                                             String criterio) throws Exception{
		
		if (!boletosAvulsos.isEmpty()){
			faturas.addAll(dao.buscarDadosBoletosAvulsos(boletosAvulsos, criterio, tipoLote, FLAG_ARQUIVO_PRINT));
		}
	}
}
