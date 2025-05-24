package br.com.netservicos.novosms.emissao.core.facade.impl;

import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.BAIRRO_NOTA_FISCAL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.CEP_NOTA_FISCAL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.CIDADE_NOTA_FISCAL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.CODIGO_CLIENTE_NET;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.CODIGO_DE_BARRAS;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.CONTROLE_PAGINACAO;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.CPF_CNPJ_NOTA_FISCAL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.DATA_EMISSAO_NOTA_FISCAL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.DATA_VENCIMENTO_BOLETO;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.ENDERECO_NOTA_FISCAL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.ESTADO_NOTA_FISCAL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.INSCRICAO_ESTADUAL_NOTA_FISCAL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.LINHA_DIGITAVEL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.MES_ANO_REFERENCIA;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.NOME_CLIENTE_COBRANCA;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.NOME_CLIENTE_NOTA_FISCAL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.NUMERO_NOTA_FISCAL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.VALOR_COBRADO;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.VALOR_TOTAL_NOTA_FISCAL;
import static br.com.netservicos.novosms.emissao.core.facade.impl.ArquivoPrintHouseServiceEJBImpl.QT_LN_PAG_FAT_PRINT;
import static br.com.netservicos.novosms.emissao.core.facade.impl.ArquivoPrintHouseServiceEJBImpl.TAMANHO_MAX_LINHA_DF;
import static br.com.netservicos.novosms.emissao.dto.LinhaDTO.TIPO_ITEM_CABECALHO_DEMONSTRATIVO_FINANCEIRO;
import static br.com.netservicos.novosms.emissao.dto.LinhaDTO.TIPO_ITEM_DADOS_ASS_BOLETO_AVULSO;
import static br.com.netservicos.novosms.emissao.dto.LinhaDTO.TIPO_ITEM_INICIO_BOLETO_AVULSO;
import static br.com.netservicos.novosms.emissao.dto.LinhaDTO.TIPO_ITEM_INICIO_PAGINA_BOLETO_AVULSO;
import static br.com.netservicos.novosms.emissao.dto.LinhaDTO.TIPO_ITEM_ITEM_DEMONSTRATIVO_FINANCEIRO;
import static br.com.netservicos.novosms.emissao.dto.TipoSetorPrint.SETOR_MINHA_NET;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import br.com.netservicos.core.bean.sn.SnLoteBean;
import br.com.netservicos.novosms.emissao.constants.PrintHouseConstants;
import br.com.netservicos.novosms.emissao.core.dao.ArquivoPrintHouseDAO;
import br.com.netservicos.novosms.emissao.dto.ClienteNotaFiscalPrintDTO;
import br.com.netservicos.novosms.emissao.dto.DadosGeraisPrimeiraViaPrintDTO;
import br.com.netservicos.novosms.emissao.dto.DemonstrativoFinanceiroDTO;
import br.com.netservicos.novosms.emissao.dto.DemonstrativoFinanceiroPrintDTO;
import br.com.netservicos.novosms.emissao.dto.FaturaDTO;
import br.com.netservicos.novosms.emissao.dto.FichaArrecadacaoPrintDTO;
import br.com.netservicos.novosms.emissao.dto.LinhaDTO;
import br.com.netservicos.novosms.emissao.dto.SetorDTO;
import br.com.netservicos.novosms.emissao.dto.SetorListaDTO;
import br.com.netservicos.novosms.emissao.exception.EmissaoBusinessResourceException;
import br.com.netservicos.novosms.emissao.printhouse.FaturaNetDTO;
import br.com.netservicos.novosms.emissao.resources.EmissaoResources;
import br.com.netservicos.novosms.emissao.util.StringUtils;

class BoletoAvulsoPrintHouseBuilder extends AbstractFaturaPrintHouseBuilder{

	public BoletoAvulsoPrintHouseBuilder(ArquivoPrintHouseDAO dao){
		super(dao);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public FaturaDTO gerarFatura(FaturaNetDTO fatura,  SnLoteBean loteCorrente){

		String cidContrato = fatura.getCidContrato();

		DadosGeraisPrimeiraViaPrintDTO dadosGeraisDTO = fatura.getDadosGeraisNF().get(0);

		if (dadosGeraisDTO == null) {
			throw new EmissaoBusinessResourceException(EmissaoResources.ERRO_DADOS_GERAIS_PRIM_VIA_NULO);
		}

		dadosGeraisDTO.setCodigoLote(new Long(fatura.getIdLote().longValue()));

		//NSMSP_172250_NI_003
		Integer formaEnvioFatura = dao.buscarTipoEnvioFaturaPorLote( fatura.getCidContrato(), loteCorrente, dadosGeraisDTO.getFormaEnvioFatura());
		dadosGeraisDTO.setFormaEnvioFatura(formaEnvioFatura);

		Map mapFatura = dadosUtil.preencheMapFatura(dadosGeraisDTO, fatura, false, "");

		SetorListaDTO setorLista = new SetorListaDTO(new LinhaDTO(LinhaDTO.TIPO_ITEN_INICIO_FATURA, mapFatura),
		                                             new LinhaDTO(LinhaDTO.TIPO_ITEN_FIM_FATURA));

		cidContrato = fatura.getCidContrato();

		if (dadosGeraisDTO != null) {
			// Gera o setor minha net
			SetorDTO setorMinhaNET = criarSetorMinhaNet(dadosGeraisDTO);

//			gerarSetorMinhaNet(setorMinhaNET, fatura);
			setorLista.addSetor(setorMinhaNET);

//			SetorDTO setorDemFinFicArr = criarSetorDemonstrativoFichaArrecadacao(dadosGeraisDTO, dadosGeraisDTO);

			gerarSetorDemonstrativoFichaArrecadacao(setorMinhaNET,
			                                        fatura.getIdBoleto(),
			                                        dadosGeraisDTO,
			                                        dadosGeraisDTO,
			                                        dadosGeraisDTO.getValorTotalNotaFiscal(),
			                                        cidContrato,
			                                        fatura.getFcBoletoConsolidado(),
			                                        fatura,
			                                        0);
			
			setorLista.setValorCobrado(dadosGeraisDTO.getValorCobrado());
		}

		return gerarFatura(cidContrato, setorLista, new FaturaDTO());
	}

	@SuppressWarnings("unchecked")
    private FaturaDTO gerarFatura(String cidContrato, SetorListaDTO setorLista, FaturaDTO fatura){
		int linhasPorPagina =  QT_LN_PAG_FAT_PRINT;
		linhasPorPagina *= 100; // Multiplica por 100 para tratar decimal como inteiro no tamanho da linha
		
		int totalPaginas = contabilizaPaginasFatura(fatura, setorLista, linhasPorPagina);
		int restoFatura = 0;
		int paginaAtual = 1;

		fatura.addLinha(setorLista.getLinhaInicio());

		// Para cada Setor (SetorDTO) dentro de (SetorLista) faça:
		for (int indiceSetor = 0; indiceSetor < setorLista.getTotalSetor().intValue(); indiceSetor++) {

			SetorDTO setorDTO = setorLista.get(new Integer(indiceSetor));

			if (setorDTO.getLinhaInicio() != null) {
				fatura.addLinha(setorDTO.getLinhaInicio());
			}

			for (int indiceLinha = 0; indiceLinha < setorDTO.getQuantidadeLinhasSetor().intValue(); indiceLinha++) {
				LinhaDTO linhaDTO = setorDTO.get(indiceLinha);
				
				if (linhaDTO.getTipo() == TIPO_ITEM_INICIO_PAGINA_BOLETO_AVULSO){
					linhaDTO.getValor().put(CONTROLE_PAGINACAO,
					                        StringUtils.prepad(String.valueOf(paginaAtual), 2, '0') + "/" +
	                                        StringUtils.prepad(String.valueOf(totalPaginas), 2, '0'));
				}

				if (restoFatura + linhaDTO.getQuantidadeOcupa().intValue() >= linhasPorPagina) {
					paginaAtual++;
					restoFatura = adicionaCabecalhoQuebra(fatura, setorDTO, paginaAtual, totalPaginas);
				}
				
				fatura.addLinha(linhaDTO);
				restoFatura += linhaDTO.getQuantidadeOcupa();
			}

			if (setorDTO.getLinhaFim() != null) {
				fatura.addLinha(setorDTO.getLinhaFim());
			}
		}

		if (setorLista.getLinhaFim() != null) {
			fatura.addLinha(setorLista.getLinhaFim());
		}
		
		fatura.setQuantidadePaginas(new Integer(totalPaginas));
		fatura.setValorCobrado(setorLista.getValorCobrado());

		// FIM DA MONTAGEM DA FATURA
		return fatura;
	}
	
	private int contabilizaPaginasFatura(FaturaDTO fatura, SetorListaDTO setorLista, int linhasPorPagina){
		int totalPaginas = 0;
		int restoLinhas = 0;

		for (int i = 0; i < setorLista.getTotalSetor().intValue(); i++) {
			SetorDTO setorDTO = setorLista.get(i);

			totalPaginas += (int) setorDTO.getQuantidadeLinhasOcupadas().intValue() / linhasPorPagina;
			restoLinhas = setorDTO.getQuantidadeLinhasOcupadas().intValue() % linhasPorPagina;
			
			if (restoLinhas >= linhasPorPagina){
				totalPaginas++;
				
				restoLinhas -= linhasPorPagina;
			}
		}

		if (restoLinhas != 0) {
			totalPaginas++;
		}
		
		fatura.setNumPaginaNet(totalPaginas);
		fatura.setNumPaginaParceiro(0);
		
		return totalPaginas;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private SetorDTO criarSetorMinhaNet(ClienteNotaFiscalPrintDTO clienteNFDTO){

		Map mapClienteNotaFiscal = new HashMap();
		mapClienteNotaFiscal.put(NUMERO_NOTA_FISCAL, notNullObject(clienteNFDTO.getNumeroNotaFiscal()));

		if (clienteNFDTO.getInscricaoEstadualNotaFiscal() == null) {
			mapClienteNotaFiscal.put(INSCRICAO_ESTADUAL_NOTA_FISCAL, PrintHouseConstants.NotaFiscal.IE_ISENTO);
		} else {
			mapClienteNotaFiscal.put(INSCRICAO_ESTADUAL_NOTA_FISCAL,
			                         notNullObject(clienteNFDTO.getInscricaoEstadualNotaFiscal()));
		}

		mapClienteNotaFiscal.put(VALOR_TOTAL_NOTA_FISCAL, notNullObject(clienteNFDTO.getValorTotalNotaFiscal()));
		mapClienteNotaFiscal.put(ENDERECO_NOTA_FISCAL, notNullObject(clienteNFDTO.getEnderecoNotaFiscal()));
		mapClienteNotaFiscal.put(BAIRRO_NOTA_FISCAL, notNullObject(clienteNFDTO.getBairroNotaFiscal()));
		mapClienteNotaFiscal.put(CIDADE_NOTA_FISCAL, notNullObject(clienteNFDTO.getCidadeNotaFiscal()));
		mapClienteNotaFiscal.put(ESTADO_NOTA_FISCAL, notNullObject(clienteNFDTO.getEstadoNotaFiscal()));
		mapClienteNotaFiscal.put(CEP_NOTA_FISCAL, StringUtils.prepad(notNullString(clienteNFDTO.getCepNotaFiscal()),
		                                                             8,
		                                                             '0'));

		mapClienteNotaFiscal.put(CPF_CNPJ_NOTA_FISCAL, notNullObject(clienteNFDTO.getCpfCnpjNotaFiscal()));
		mapClienteNotaFiscal.put(NOME_CLIENTE_NOTA_FISCAL, notNullObject(clienteNFDTO.getNomeClienteNotaFiscal()));
		mapClienteNotaFiscal.put(CODIGO_CLIENTE_NET, notNullString(clienteNFDTO.getCodOperadora()) + "/" +
		        StringUtils.prepad(notNullString(clienteNFDTO.getCodigoClienteNET()), 9, '0'));

		mapClienteNotaFiscal.put(DATA_VENCIMENTO_BOLETO, notNullObject(clienteNFDTO.getDataVencimentoBoleto()));
		mapClienteNotaFiscal.put(MES_ANO_REFERENCIA, notNullObject(clienteNFDTO.getMesAnoReferencia()));
		mapClienteNotaFiscal.put(DATA_EMISSAO_NOTA_FISCAL, notNullObject(clienteNFDTO.getDataEmissaoNotaFiscal()));

		SetorDTO setorMinhaNet = new SetorDTO(SETOR_MINHA_NET,
		                                      null,
		                                      null,
		                                      Collections.emptyList(),
		                                      Boolean.FALSE,
		                                      Boolean.FALSE);
		
		setorMinhaNet.addLinha(new LinhaDTO(TIPO_ITEM_INICIO_BOLETO_AVULSO, new HashMap<String, String>()));
		setorMinhaNet.addLinha(new LinhaDTO(TIPO_ITEM_INICIO_PAGINA_BOLETO_AVULSO, new HashMap<String, String>()));
		setorMinhaNet.addLinha(new LinhaDTO(TIPO_ITEM_DADOS_ASS_BOLETO_AVULSO, mapClienteNotaFiscal));

		return setorMinhaNet;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int adicionaCabecalhoQuebra(FaturaDTO fatura, SetorDTO setorDTO, int paginaAtual, int totalPaginas){
		int linhasOcupadas = 0;
		
		Iterator iterCabecalhoQuebraPagina = setorDTO.getCabecalhoQuebraPagina().iterator();

		while(iterCabecalhoQuebraPagina.hasNext()) {
			LinhaDTO linhaQuebraPagina = (LinhaDTO) iterCabecalhoQuebraPagina.next();

			LinhaDTO linhaQuebra = new LinhaDTO(linhaQuebraPagina.getTipo(),
			                                    new HashMap(linhaQuebraPagina.getValor()),
			                                    linhaQuebraPagina.getQuantidadeOcupa());

			linhaQuebra.getValor().put(CONTROLE_PAGINACAO,
			                           StringUtils.prepad(String.valueOf(paginaAtual), 2, '0') + "/" +
			                           StringUtils.prepad(String.valueOf(totalPaginas), 2, '0'));
			
			fatura.addLinha(linhaQuebra);
			linhasOcupadas += linhaQuebra.getQuantidadeOcupa();
		}
		
		return linhasOcupadas;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private int[] gerarSetorDemonstrativoFichaArrecadacao(SetorDTO setorDemFinFicArr,
	                                                      Long idBoleto,
	                                                      DemonstrativoFinanceiroPrintDTO demonstrativoFinanceiroDTO,
	                                                      FichaArrecadacaoPrintDTO fichaArrecadacaoDTO,
	                                                      Double vlrTotalNF,
	                                                      String cidContrato,
	                                                      String boletoConsolidado,
	                                                      FaturaNetDTO fatura,
	                                                      int contLinhas){

		contLinhas = 0;

		int linhasFichaArrecadacao = QT_LN_PAG_FAT_PRINT;
		linhasFichaArrecadacao *= 100; // multiplica por 100 para eliminar o decimal

		Double valorCobrado = null;

		if (demonstrativoFinanceiroDTO.getValorCobrado() <= 0) {
			valorCobrado = 0.0;
		} else {
			valorCobrado = demonstrativoFinanceiroDTO.getValorCobrado();
		}

		setorDemFinFicArr.addLinha(new LinhaDTO(TIPO_ITEM_CABECALHO_DEMONSTRATIVO_FINANCEIRO,
		                                        new HashMap<String, String>()));

		List<DemonstrativoFinanceiroDTO> listDemonstrativo = new ArrayList<DemonstrativoFinanceiroDTO>();

		if (fatura != null && fatura.getDemonstrativoFinanceiro() != null) {
			listDemonstrativo.addAll(fatura.getDemonstrativoFinanceiro());
		}

		int paginaAtual = 1;

		// Armazena os itens eventuais para jogar no fim do demonstrativo financeiro
		List<LinhaDTO> itensEventuaisTemp = new ArrayList<LinhaDTO>();

		for (DemonstrativoFinanceiroDTO item : listDemonstrativo) {
			DemonstrativoFinanceiroDTO valor = new DemonstrativoFinanceiroDTO();

			valor.setDESCRICAOITEMDEMONST_FINANC(item.getDESCRICAOITEMDEMONST_FINANC());
			valor.setGRUPO_DEMONST_FINANC("");
			valor.setSUBGRUPO_DEMONST_FINANC("");
			valor.setVALORITEMDEMONST_FINANC(item.getVALORITEMDEMONST_FINANC());

			int tamanhoMilimetros = 400;

			if (item.getTAMANHO() != null && item.getTAMANHO() > TAMANHO_MAX_LINHA_DF) {
				tamanhoMilimetros = 600;
			}

			LinhaDTO tempLinha =
			        llk.criarLinhaDemonstrativoFinanceiro(TIPO_ITEM_ITEM_DEMONSTRATIVO_FINANCEIRO.intValue(),
			                                              valor,
			                                              tamanhoMilimetros);

			int[] aux = layoutUtil.adicionarItem(item.getGRUPO_DEMONST_FINANC(),
			                                     tempLinha,
			                                     setorDemFinFicArr,
			                                     itensEventuaisTemp,
			                                     contLinhas,
			                                     paginaAtual,
			                                     linhasFichaArrecadacao);
			
			paginaAtual = aux[0];
			contLinhas = aux[1];
		}

		Map mapDemonstrativoFinanceiroDTO = new HashMap<String, String>();

		if (valorCobrado <= 0) {
			mapDemonstrativoFinanceiroDTO.put(VALOR_COBRADO, "0");
		} else {
			mapDemonstrativoFinanceiroDTO.put(VALOR_COBRADO, valorCobrado);
		}

		setorDemFinFicArr.addLinha(new LinhaDTO(LinhaDTO.TIPO_ITEM_TOTAL_DEMONSTRATIVO_FINANCEIRO,
		                                        mapDemonstrativoFinanceiroDTO,
		                                        0));

		Map mapFichaArrecadacaoDTO = new HashMap<String, String>();
		mapFichaArrecadacaoDTO.put(CODIGO_DE_BARRAS, fichaArrecadacaoDTO.getCodigoDeBarras());
		mapFichaArrecadacaoDTO.put(DATA_VENCIMENTO_BOLETO, fichaArrecadacaoDTO.getDataVencimentoBoleto());
		mapFichaArrecadacaoDTO.put(LINHA_DIGITAVEL, fichaArrecadacaoDTO.getLinhaDigitavel());
		mapFichaArrecadacaoDTO.put(MES_ANO_REFERENCIA, fichaArrecadacaoDTO.getMesAnoReferencia());
		mapFichaArrecadacaoDTO.put(NOME_CLIENTE_COBRANCA, fichaArrecadacaoDTO.getNomeClienteCobranca());
		mapFichaArrecadacaoDTO.put(VALOR_COBRADO, valorCobrado);
		
		
		//Se tem linha digitavel, preenche o qrcode
		if((fichaArrecadacaoDTO.getLinhaDigitavel() != null) && (!fichaArrecadacaoDTO.getLinhaDigitavel().equals(""))){
			try{
				mapFichaArrecadacaoDTO.put(PrintHouseConstants.LayoutArquivoPrintHouse.PIX_HASHCODE, fichaArrecadacaoDTO.getPixHashcode());
			}catch(Exception e){
				e.printStackTrace();
				mapFichaArrecadacaoDTO.put(PrintHouseConstants.LayoutArquivoPrintHouse.PIX_HASHCODE, "");
			}
		}else{
			mapFichaArrecadacaoDTO.put(PrintHouseConstants.LayoutArquivoPrintHouse.PIX_HASHCODE, "");
		}
		
		
		
		if (demonstrativoFinanceiroDTO instanceof DadosGeraisPrimeiraViaPrintDTO){
			DadosGeraisPrimeiraViaPrintDTO dadosGerais = (DadosGeraisPrimeiraViaPrintDTO) demonstrativoFinanceiroDTO;
			
			mapFichaArrecadacaoDTO.put("AGENCIADEBITO", dadosGerais.getAgencia());
			mapFichaArrecadacaoDTO.put("BANCODEBITO", dadosGerais.getNomeBanco());
		}

		String operadora = StringUtils.prepad(demonstrativoFinanceiroDTO.getCodOperadora(), 3, '0');
		String numContrato = StringUtils.prepad(notNullString(demonstrativoFinanceiroDTO.getCodigoClienteNET()), 9, '0');
		String param = StringUtils.prepad(notNullString(demonstrativoFinanceiroDTO.getCodigoClienteNET()), 9, '0');

		mapFichaArrecadacaoDTO.put(CODIGO_CLIENTE_NET, notNullString(operadora +
		        numContrato +
		        String.valueOf(geraDigVerificador(param))));

		// nao quebra pagina nele, pois depois vem a NF e ela sempre quebra pagina
		setorDemFinFicArr.addLinha(new LinhaDTO(LinhaDTO.TIPO_ITEM_FICHA_ARRECADACAO,
		                                        mapFichaArrecadacaoDTO,
		                                        0));

		return new int[] { paginaAtual, contLinhas };
	}

	private Integer geraDigVerificador(String valor){

		int aux = 0;
		int multiplicador = 2;
		int i = valor.length();

		while(i > 0) {
			aux = aux + multiplicador * Integer.parseInt(valor.substring(i - 1, i));
			multiplicador = multiplicador + 1;
			if (multiplicador > 9) {
				multiplicador = 2;
			}
			i = i - 1;
		}

		aux = aux % 11;
		aux = 11 - aux;

		if (aux == 11) {
			return 1;
		} else if (aux == 10) {
			return 0;
		} else {
			return aux;
		}

	}
}