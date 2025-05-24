package br.com.netservicos.novosms.emissao.printhouse;

import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.EBT.DATAINICIOCOBRANCA;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.EBT.DURACAO;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.EBT.ESPACOEMBRANCO1;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.EBT.ESPACOEMBRANCO4;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.EBT.HORAINICIO;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.EBT.ITEMDETALHAMENTOVALOR;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.EBT.LOCALIDADEDESTINO;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.EBT.NOMEAGRUPAMENTO;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.EBT.NUMEROTELEFONE;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.BAIRRO;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.BAIRRO_COBRANCA;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.BAIRRO_NOTA_FISCAL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.CEP;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.CEP_COBRANCA;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.CEP_NOTA_FISCAL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.CFOP_NOTA_FISCAL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.CIDADE;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.CIDADE_COBRANCA;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.CIDADE_NOTA_FISCAL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.CNPJ;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.CODIGO_CLIENTE_EBT;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.CODIGO_CLIENTE_NET;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.CODIGO_DE_BARRAS;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.CODIGO_LOTE;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.CODIGO_OPERACAO_NET;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.CODIGO_OPERADORA;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.CODIGO_REGISTRO_EMISSAO;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.CODIGO_SETORIZACAO_MENSAGEM;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.CPF_CNPJ_COBRANCA;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.CPF_CNPJ_NOTA_FISCAL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.DATA_EMISSAO_NOTA_FISCAL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.DATA_FIM_GERACAO_ARQUIVO;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.DATA_INICIO_GERACAO_ARQUIVO;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.DATA_PROCESSAMENTO_BOLETO;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.DATA_VENCIMENTO_BOLETO;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.DATA_VENCIMENTO_BOLETO_ORIGEM;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.ENDERECO;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.ENDERECO_COBRANCA;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.ENDERECO_NOTA_FISCAL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.ESTADO;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.ESTADO_COBRANCA;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.ESTADO_NOTA_FISCAL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.FORMATACAO;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.FUNTEL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.FUST;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.HASH_CODE;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.HORA_FIM_GERACAO_ARQUIVO;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.HORA_INICIO_GERACAO_ARQUIVO;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.IDENTIFICADOR_BOLETO;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.IDENTIFICADOR_BOLETO_ORIGEM;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.INSCRICAO_ESTADUAL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.INSCRICAO_ESTADUAL_COBRANCA;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.INSCRICAO_ESTADUAL_NOTA_FISCAL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.INSCRICAO_MUNICIPAL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.LINHA_DIGITAVEL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.MARCA_DA_AQUA;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.MENSAGEM;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.MENSAGEMEBT;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.MES_ANO_REFERENCIA;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.MODELO_NOTA_FISCAL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.NOME_CLIENTE_COBRANCA;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.NOME_CLIENTE_NOTA_FISCAL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.*;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.NOME_OPERACAO_NET;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.NUMERO_NOTA_FISCAL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.PREFIXO;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.QUANTIDADE_FATURAS_EMITIDAS;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.SERIE_NOTA_FISCAL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.ULTIMAS_OCORRENCIAS;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.VALOR_COBRADO;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.VALOR_TOTAL_FATURAS;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.VALOR_TOTAL_LINHAS_ARQUIVO;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.VALOR_TOTAL_NOTA_FISCAL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.FLAGMSGQUITACAO;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import br.com.netservicos.core.bean.sn.SnCidadeOperadoraBean;
import br.com.netservicos.core.bean.sn.SnRegistroEmissaoBean;
import br.com.netservicos.novosms.emissao.constants.PrintHouseConstants;
import br.com.netservicos.novosms.emissao.dto.ArquivoPrintHouseDTO;
import br.com.netservicos.novosms.emissao.dto.ClienteNotaFiscalPrintDTO;
import br.com.netservicos.novosms.emissao.dto.ClientePrintDTO;
import br.com.netservicos.novosms.emissao.dto.ClienteSegundaViaPrintDTO;
import br.com.netservicos.novosms.emissao.dto.CobrancaParceiroDTO;
import br.com.netservicos.novosms.emissao.dto.DadosCobrancaParceiroDTO;
import br.com.netservicos.novosms.emissao.dto.DadosGeraisPrimeiraViaPrintDTO;
import br.com.netservicos.novosms.emissao.dto.DadosGeraisPrimeiraViaPrintParceiroDTO;
import br.com.netservicos.novosms.emissao.dto.DadosGeraisSegundaViaPrintDTO;
import br.com.netservicos.novosms.emissao.dto.DadosItensNFDTO;
import br.com.netservicos.novosms.emissao.dto.DadosOperadoraDTO;
import br.com.netservicos.novosms.emissao.dto.DadosTributosDTO;
import br.com.netservicos.novosms.emissao.dto.DemonstrativoFinanceiroDTO;
import br.com.netservicos.novosms.emissao.dto.DetalhamentoLigacaoParceiroDTO;
import br.com.netservicos.novosms.emissao.dto.DetalhamentoNotaFiscalDTO;
import br.com.netservicos.novosms.emissao.dto.FebrabamDTO;
import br.com.netservicos.novosms.emissao.dto.FichaArrecadacaoPrintDTO;
import br.com.netservicos.novosms.emissao.dto.FiliadosDTO;
import br.com.netservicos.novosms.emissao.dto.LinhaDTO;
import br.com.netservicos.novosms.emissao.dto.MensagemAnatelDTO;
import br.com.netservicos.novosms.emissao.dto.MensagemDTO;
import br.com.netservicos.novosms.emissao.dto.MensagemStreamingDTO;
import br.com.netservicos.novosms.emissao.dto.TributoDTO;
import br.com.netservicos.novosms.emissao.dto.ArquivoPrintHouseDTO.ArquivoGerado;
import br.com.netservicos.novosms.emissao.util.StringUtils;

/**
 * Esta classe é uma classe é responsável por criar linhas para o relatório para
 * PrintHouse. Evitando que toda complexidade dos HashMaps e etc fique dentro de
 * ArquivoPrintHouseService.
 */
public class LinhasLeiauteKit {
	private static final String _ = " ";

	private static final String KEY_DESCRICAO = "DESCRICAOITEMDEMONST_FINANC";

	private static final String KEY_VALOR = "VALORITEMDEMONST_FINANC";

	@SuppressWarnings("unchecked")
	public LinhaDTO criarLinhaItenCabecalhoArquivo(SnRegistroEmissaoBean registroEmissao) {
		final NotNullValuesHashMap map = new NotNullValuesHashMap(4);

		map.put(CODIGO_REGISTRO_EMISSAO, String.valueOf(registroEmissao.getIdRegistroEmissao()));
		map.put(DATA_INICIO_GERACAO_ARQUIVO, registroEmissao.getDhProcessamento());
		map.put(HORA_INICIO_GERACAO_ARQUIVO, registroEmissao.getDhProcessamento());
		map.put(MARCA_DA_AQUA, "");

		return new LinhaDTO(LinhaDTO.TIPO_ITEN_CABECALHO_ARQUIVO, map);
	}

	@SuppressWarnings("unchecked")
	public LinhaDTO criarLinhaItenFimArquivo(ArquivoPrintHouseDTO a) {
		final NotNullValuesHashMap map = new NotNullValuesHashMap(6);
		final Date dataatual = new Date();
		final ArquivoGerado ca = a.getCurrentArquivo();

		map.put(VALOR_TOTAL_FATURAS, ca.getValorTotalFatura());
		map.put(CODIGO_REGISTRO_EMISSAO, a.getRegistroEmissao().getIdRegistroEmissao());
		map.put(DATA_FIM_GERACAO_ARQUIVO, dataatual);
		map.put(HORA_FIM_GERACAO_ARQUIVO, dataatual);
		map.put(VALOR_TOTAL_LINHAS_ARQUIVO, ca.getGerarArquivo().getQtdLinhasGerados() + 1);
		map.put(QUANTIDADE_FATURAS_EMITIDAS, ca.getQuantidadeFaturasEmitidas());

		return new LinhaDTO(LinhaDTO.TIPO_ITEN_FIM_ARQUIVO, map);
	}

	@SuppressWarnings("unchecked")
	public LinhaDTO criarLinhaItenOperacaoNet(SnCidadeOperadoraBean cidade, DadosOperadoraDTO operadora) {
		final NotNullValuesHashMap map = new NotNullValuesHashMap(11);

		// cidade
		map.put(CODIGO_OPERACAO_NET, cidade.getCodOperadora());
		map.put(NOME_OPERACAO_NET, cidade.getRazaoSocial());
		// operadora
		map.put(ENDERECO, operadora.getLogradouro() + ", " + operadora.getNumero());
		map.put(BAIRRO, operadora.getBairro());
		map.put(CIDADE, operadora.getCidade());
		map.put(ESTADO, operadora.getEstado());
		map.put(CEP, StringUtils.removeAllNonNumbers(operadora.getCep()));
		map.put(CNPJ, operadora.getCnpj());
		map.put(INSCRICAO_ESTADUAL, operadora.getInscricaoEstadual());
		map.put(INSCRICAO_MUNICIPAL, "");
		map.put(NOME_FANTASIA_OPERACAO_NET, operadora.getNomeFebraban());
		map.put(EMPRESA_FATURA, operadora.getEmpresaFatura());

		return new LinhaDTO(LinhaDTO.TIPO_ITEN_OPERACAO_NET, map);
	}

	@SuppressWarnings("unchecked")
	public LinhaDTO criarLinhaItenUltimasOcorrencias(FaturaNetDTO fatura) {
		final NotNullValuesHashMap map = new NotNullValuesHashMap(1);

		map.put(ULTIMAS_OCORRENCIAS, fatura.getUltimasOcorrencias());

		return new LinhaDTO(LinhaDTO.TIPO_ITEN_NET_FIM_TITULO, map);
	}

	@SuppressWarnings("unchecked")
	public LinhaDTO criarLinhaItenMensagem(SnCidadeOperadoraBean cidade, MensagemDTO mensagem) {
		final NotNullValuesHashMap map = new NotNullValuesHashMap(5);

		// cidade
		map.put(CODIGO_OPERADORA, cidade.getCodOperadora());
		// mensagem
		map.put(CODIGO_SETORIZACAO_MENSAGEM, mensagem.getCcCodigoSetorizacao());
		map.put(MENSAGEM, mensagem.getDsMensagem());
		map.put(PREFIXO, mensagem.getPrefixo());
		map.put(FORMATACAO, mensagem.getCcCodigoFormatacao());
		map.put(FLAGMSGQUITACAO, mensagem.getFlagMsgQuitacao());

		return new LinhaDTO(LinhaDTO.TIPO_ITEN_MENSAGEM, map);
	}

	@SuppressWarnings("unchecked")
	public LinhaDTO criarLinhaItenEbtDetalhamento(DetalhamentoLigacaoParceiroDTO item) {

		final SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		final NotNullValuesHashMap map = new NotNullValuesHashMap(9);

		map.put(DATAINICIOCOBRANCA, df.format(item.getDataInicio()));
		map.put(NUMEROTELEFONE, item.getTelefoneDestino());
		map.put(LOCALIDADEDESTINO, item.getLocalidadeDestino());
		map.put(HORAINICIO, item.getHoraInicio());
		map.put(DURACAO, item.getDuracao());
		map.put(ITEMDETALHAMENTOVALOR, valor(item.getValorItem(), 17, 2));
		map.put(FORMATACAO, "16");
		map.put(ESPACOEMBRANCO1, _);
		map.put(ESPACOEMBRANCO4, _);

		return new LinhaDTO(LinhaDTO.TIPO_ITEN_EBT_DETALHAMENTO, map);
	}

	@SuppressWarnings("unchecked")
	public LinhaDTO criarLinhaItenEbtDetalhamentoSubTotal(Double valor, String formatacao) {
		final NotNullValuesHashMap map = new NotNullValuesHashMap(9);
		final String subTotalStr = "SubTotal";

		map.put(DATAINICIOCOBRANCA, _);
		map.put(NUMEROTELEFONE, _);
		map.put(LOCALIDADEDESTINO, _);
		map.put(HORAINICIO, subTotalStr);
		map.put(DURACAO, _);
		map.put(ESPACOEMBRANCO1, _);
		map.put(ESPACOEMBRANCO4, _);
		map.put(ITEMDETALHAMENTOVALOR, valor(valor, 17, 2));
		map.put(FORMATACAO, formatacao);

		return new LinhaDTO(LinhaDTO.TIPO_ITEN_EBT_DETALHAMENTO, map);
	}

	@SuppressWarnings("unchecked")
	public LinhaDTO criarLinhaItenEbtDetalhamentoTotal(Double valor) {
		final NotNullValuesHashMap map = new NotNullValuesHashMap(10);

		map.put(NOMEAGRUPAMENTO, _);
		map.put(DATAINICIOCOBRANCA, "");
		map.put(NUMEROTELEFONE, _);
		map.put(LOCALIDADEDESTINO, _);
		map.put(HORAINICIO, "Total Ser");
		map.put(ESPACOEMBRANCO4, "viço");
		map.put(ESPACOEMBRANCO1, _);
		map.put(DURACAO, _);
		map.put(ITEMDETALHAMENTOVALOR, valor(valor, 17, 2));
		map.put(FORMATACAO, "13");

		return new LinhaDTO(LinhaDTO.TIPO_ITEN_EBT_DETALHAMENTO, map);
	}

	@SuppressWarnings("unchecked")
	public LinhaDTO criarLinhaItenEbtDetalhamentoColuna1() {
		final NotNullValuesHashMap map = new NotNullValuesHashMap(9);

		map.put(DATAINICIOCOBRANCA, "PERIODO/DA");
		map.put(ESPACOEMBRANCO1, "TA");
		map.put(NUMEROTELEFONE, "TELEFONE");
		map.put(LOCALIDADEDESTINO, "LOCAL");
		map.put(HORAINICIO, "HORA");
		map.put(DURACAO, "DURACAO");
		map.put(ESPACOEMBRANCO4, _);
		map.put(ITEMDETALHAMENTOVALOR, "       VALOR (R$)");
		map.put(FORMATACAO, "16");

		return new LinhaDTO(LinhaDTO.TIPO_ITEN_EBT_DETALHAMENTO, map);
	}

	@SuppressWarnings("unchecked")
	public LinhaDTO criarLinhaItenEbtDetalhamentoColuna2() {
		final NotNullValuesHashMap map = new NotNullValuesHashMap(9);

		map.put(DATAINICIOCOBRANCA, _);
		map.put(NUMEROTELEFONE, "DESTINO");
		map.put(LOCALIDADEDESTINO, "DESTINO");
		map.put(HORAINICIO, "INICIO");
		map.put(DURACAO, _);
		map.put(ESPACOEMBRANCO1, _);
		map.put(ESPACOEMBRANCO4, _);
		map.put(ITEMDETALHAMENTOVALOR, _);
		map.put(FORMATACAO, "16");

		return new LinhaDTO(LinhaDTO.TIPO_ITEN_EBT_DETALHAMENTO, map);
	}

	@SuppressWarnings("unchecked")
	public LinhaDTO criarLinhaItenEbtCabecalhoAgrupamento(String nome, String formatacao) {
		final NotNullValuesHashMap map = new NotNullValuesHashMap(2);

		map.put(NOMEAGRUPAMENTO, nome);
		map.put(FORMATACAO, formatacao);

		return new LinhaDTO(LinhaDTO.TIPO_ITEN_EBT_CABECALHO_AGRUPAMENTO, map);
	}

	@SuppressWarnings("unchecked")
	public LinhaDTO criarLinhaDadosAssinanteEbt(DadosGeraisPrimeiraViaPrintParceiroDTO nota) {
		final NotNullValuesHashMap map = new NotNullValuesHashMap(10);

		map.put(PrintHouseConstants.NOTA_FISCAL_EBT.NNOMECLIENTE, nota.getCcNomeRazaoSocial());
		map.put(PrintHouseConstants.NOTA_FISCAL_EBT.CCDIGOCLIENTEEBT, nota.getCodigoClienteEBT());
		map.put(PrintHouseConstants.NOTA_FISCAL_EBT.NUMEROFATURA, nota.getNrNotaFiscal());
		map.put(PrintHouseConstants.NOTA_FISCAL_EBT.ENDERECO, nota.getCcLogradouro());
		map.put(PrintHouseConstants.NOTA_FISCAL_EBT.BAIRRO, nota.getCcBairro());
		map.put(PrintHouseConstants.NOTA_FISCAL_EBT.CIDADE, nota.getCcCidade());
		map.put(PrintHouseConstants.NOTA_FISCAL_EBT.ESTADO, nota.getCcEstado());
		map.put(PrintHouseConstants.NOTA_FISCAL_EBT.CEP, nota.getCepNotaFiscal());
		map.put(PrintHouseConstants.NOTA_FISCAL_EBT.CNPJ, nota.getCcCpfCnpj());
		map.put(PrintHouseConstants.NOTA_FISCAL_EBT.INSCRICAOESTADUAL, nota.getCcInscricaoEstadual());

		return new LinhaDTO(LinhaDTO.TIPO_DADOS_ASSINANTE_EBT, map);
	}

	@SuppressWarnings("unchecked")
	public LinhaDTO criarLinhaTributosNfEbt(DadosTributosDTO tributo) {
		final NotNullValuesHashMap map = new NotNullValuesHashMap(7);

		map.put(PrintHouseConstants.NOTA_FISCAL_EBT.SGTRIBUTO, tributo.getSIGLATRIBUTO());
		map.put(PrintHouseConstants.NOTA_FISCAL_EBT.BASECALCULO, tributo.getBASECALCULO());
		map.put(PrintHouseConstants.NOTA_FISCAL_EBT.VALORTOTAL, tributo.getVALORTOTAL());
		map.put(PrintHouseConstants.NOTA_FISCAL_EBT.ALIQUOTA, _);
		map.put(PrintHouseConstants.NOTA_FISCAL_EBT.VALORICMS, tributo.getVALORTRIBUTO());
		map.put(PrintHouseConstants.NOTA_FISCAL_EBT.VALORISENTO, tributo.getVALORISENTO());
		map.put(PrintHouseConstants.NOTA_FISCAL_EBT.VALOROUTROS, tributo.getVALOROUTROS());

		return new LinhaDTO(LinhaDTO.TIPO_TRIBUTOS_NF_EBT, map);
	}

	@SuppressWarnings("unchecked")
	public LinhaDTO criarLinhaLabelReservadoFiscalNfEbt(String label) {
		final NotNullValuesHashMap map = new NotNullValuesHashMap(1);

		map.put(PrintHouseConstants.NOTA_FISCAL_EBT.LABELFISCO, label);

		return new LinhaDTO(LinhaDTO.TIPO_LABEL_RESERVADO_FISCAL_NF_EBT, map);
	}

	@SuppressWarnings("unchecked")
	public LinhaDTO criarLinhaTotalNfEbt(DadosTributosDTO item) {
		final NotNullValuesHashMap map = new NotNullValuesHashMap(7);

		map.put(PrintHouseConstants.NOTA_FISCAL_EBT.SGTRIBUTO, item.getSIGLATRIBUTO());
		map.put(PrintHouseConstants.NOTA_FISCAL_EBT.BASECALCULO, item.getBASECALCULO());
		map.put(PrintHouseConstants.NOTA_FISCAL_EBT.ALIQUOTA, valor(item.getVALORALIQUOTA(), 20, 2));
		map.put(PrintHouseConstants.NOTA_FISCAL_EBT.VALORTOTAL, item.getVALORTOTAL());
		map.put(PrintHouseConstants.NOTA_FISCAL_EBT.VALORICMS, item.getVALORTRIBUTO());
		map.put(PrintHouseConstants.NOTA_FISCAL_EBT.VALORISENTO, item.getVALORISENTO());
		map.put(PrintHouseConstants.NOTA_FISCAL_EBT.VALOROUTROS, item.getVALOROUTROS());

		return new LinhaDTO(LinhaDTO.TIPO_TOTAL_NF_EBT, map);
	}

	@SuppressWarnings("unchecked")
	public LinhaDTO criarLinhaReservadoFiscalNfEbt(String hashcode) {
		final NotNullValuesHashMap map = new NotNullValuesHashMap(1);

		map.put(PrintHouseConstants.NOTA_FISCAL_EBT.HASHCODE, hashcode);

		return new LinhaDTO(LinhaDTO.TIPO_RESERVADO_FISCAL_NF_EBT, map);
	}

	@SuppressWarnings("unchecked")
	public LinhaDTO criarLinhaItensNfEbt(DadosItensNFDTO item) {
		final NotNullValuesHashMap map = new NotNullValuesHashMap(3);

		map.put(PrintHouseConstants.NOTA_FISCAL_EBT.DESCRICAOTELEFONE, item.getDescricao());
		map.put(PrintHouseConstants.NOTA_FISCAL_EBT.ALIQUOTA, item.getAliquota());
		map.put(PrintHouseConstants.NOTA_FISCAL_EBT.VALOR, item.getValorBrutoItem());

		return new LinhaDTO(LinhaDTO.TIPO_ITENS_NF_EBT, map);
	}

	@SuppressWarnings("unchecked")
	public LinhaDTO criarLinhaCabecalhoNfEbt(DadosGeraisPrimeiraViaPrintParceiroDTO nota) {
		final NotNullValuesHashMap map = new NotNullValuesHashMap(4);

		map.put(PrintHouseConstants.NOTA_FISCAL_EBT.SERIENOTAFISCAL, nota.getCcSerieNotaFiscal());
		map.put(PrintHouseConstants.NOTA_FISCAL_EBT.NUMERONOTAFISCAL, nota.getNrNotaFiscal());
		map.put(PrintHouseConstants.NOTA_FISCAL_EBT.DATAEMISSAONOTAFISCAL, nota.getDtEmissao());
		map.put(PrintHouseConstants.NOTA_FISCAL_EBT.UNIDADEFISCAL, nota.getCcEstado());

		return new LinhaDTO(LinhaDTO.TIPO_CABECALHO_NF_EBT, map);
	}

	@SuppressWarnings("unchecked")
	public LinhaDTO criarLinhaDadosParceiroNfEbt(DadosGeraisPrimeiraViaPrintParceiroDTO nota) {
		Map map = new NotNullValuesHashMap(9);

		map.put(PrintHouseConstants.NOTA_FISCAL_EBT.NOMEEMPRESAPARCEIRA, nota.getCc_razao_social_oper());
		map.put(PrintHouseConstants.NOTA_FISCAL_EBT.ENDERECO, nota.getCc_logradouro_oper());
		map.put(PrintHouseConstants.NOTA_FISCAL_EBT.BAIRRO, nota.getCc_bairro_oper());
		map.put(PrintHouseConstants.NOTA_FISCAL_EBT.CIDADE, nota.getCc_cidade_oper());
		map.put(PrintHouseConstants.NOTA_FISCAL_EBT.ESTADO, nota.getCc_estado_oper());
		map.put(PrintHouseConstants.NOTA_FISCAL_EBT.CEP, nota.getCc_cep_oper());
		map.put(PrintHouseConstants.NOTA_FISCAL_EBT.CNPJ, nota.getCc_cnpj_oper());
		map.put(PrintHouseConstants.NOTA_FISCAL_EBT.INSCRICAOESTADUAL, nota.getCc_inscricao_estadual_oper());
		map.put(PrintHouseConstants.NOTA_FISCAL_EBT.INSCRICAOMUNICIPAL, nota.getCc_inscricao_municipal_oper());

		return new LinhaDTO(LinhaDTO.TIPO_DADOS_PARCEIRO_NF_EBT, map);
	}

	@SuppressWarnings("unchecked")
	public LinhaDTO criarLinhaItenMensagemInicioFatura(ClienteNotaFiscalPrintDTO nota) {
		final NotNullValuesHashMap map = new NotNullValuesHashMap(2);

		map.put(SERIE_NOTA_FISCAL, nota.getSerieNotaFiscal());
		map.put(MODELO_NOTA_FISCAL, nota.getModeloNotaFiscal());

		return new LinhaDTO(LinhaDTO.TIPO_ITEN_MENSAGEM_INICIO_FATURA, map);
	}

	@SuppressWarnings("unchecked")
	public LinhaDTO criarLinhaItenNetReservadoFiscoCodigo(String hashCode) {
		final NotNullValuesHashMap map = new NotNullValuesHashMap(1);

		map.put(HASH_CODE, hashCode);

		return new LinhaDTO(LinhaDTO.TIPO_ITEN_NET_RESERVADO_FISCO_CODIGO, map);
	}

	@SuppressWarnings("unchecked")
	public LinhaDTO criarLinhaItenNetTotalNotaFiscal(ClienteNotaFiscalPrintDTO cliente) {
		Map map = new NotNullValuesHashMap(1);

		map.put(VALOR_TOTAL_NOTA_FISCAL, cliente.getValorTotalNotaFiscal());

		return new LinhaDTO(LinhaDTO.TIPO_ITEN_NET_TOTAL_NOTA_FISCAL, map);
	}

	@SuppressWarnings("unchecked")
	public LinhaDTO criarLinhaItenEbtCabecalho(ClientePrintDTO cliente) {
		final NotNullValuesHashMap map = new NotNullValuesHashMap(6);

		map.put(PrintHouseConstants.EBT.NOMECLIENTE, cliente.getNomeClienteNotaFiscal());
		map.put(PrintHouseConstants.EBT.CODIGOCLIENTEEBT, cliente.getCodigoClienteEBT());
		map.put(PrintHouseConstants.EBT.NUMEROFATURA, cliente.getNumeroNotaFiscal());
		map.put(PrintHouseConstants.EBT.NOMESERVICO, cliente.getDescServico());
		map.put(PrintHouseConstants.EBT.NUMEROTELEFONE, cliente.getDescServico());
		map.put(PrintHouseConstants.EBT.CONTROLEPAGINACAO, cliente.getDescServico());

		return new LinhaDTO(LinhaDTO.TIPO_ITEN_EBT_CABECALHO, map);
	}

	@SuppressWarnings("unchecked")
	public LinhaDTO criarLinhaDadosParceiroNf(DadosCobrancaParceiroDTO cliente) {
		Map map = new NotNullValuesHashMap(8);

		map.put(PrintHouseConstants.NOTA_FISCAL_EBT.ENDERECO, cliente.getCc_logradouro_oper());
		map.put(PrintHouseConstants.NOTA_FISCAL_EBT.BAIRRO, cliente.getCc_bairro_oper());
		map.put(PrintHouseConstants.NOTA_FISCAL_EBT.CIDADE, cliente.getCc_cidade_oper());
		map.put(PrintHouseConstants.NOTA_FISCAL_EBT.ESTADO, cliente.getCc_estado_oper());
		map.put(PrintHouseConstants.NOTA_FISCAL_EBT.CEP, cliente.getCc_cep_oper());
		map.put(PrintHouseConstants.NOTA_FISCAL_EBT.CNPJ, cliente.getCc_cnpj_oper());
		map.put(PrintHouseConstants.NOTA_FISCAL_EBT.INSCRICAOESTADUAL, cliente.getCc_inscricao_estadual_oper());
		map.put(PrintHouseConstants.NOTA_FISCAL_EBT.INSCRICAOMUNICIPAL, cliente.getCc_inscricao_municipal_oper());

		return new LinhaDTO(LinhaDTO.TIPO_DADOS_PARCEIRO_NF, map);
	}

	@SuppressWarnings("unchecked")
	public LinhaDTO criarLinhaItenDadosAssNetSemNf(ClientePrintDTO cliente) {
		Map map = new NotNullValuesHashMap(11);
		map.put(BAIRRO_NOTA_FISCAL, cliente.getBairroNotaFiscal());
		map.put(CEP_NOTA_FISCAL, cliente.getCepNotaFiscal());
		map.put(CIDADE_NOTA_FISCAL, cliente.getCidadeNotaFiscal());
		map.put(CODIGO_CLIENTE_NET, cliente.getCodOperadora() + "/"
				+ StringUtils.prepad(cliente.getCodigoClienteNET(), 9, '0'));
		map.put(CPF_CNPJ_NOTA_FISCAL, cliente.getCpfCnpjNotaFiscal());
		map.put(DATA_EMISSAO_NOTA_FISCAL, cliente.getDataEmissaoNotaFiscal());
		map.put(DATA_VENCIMENTO_BOLETO, cliente.getDataVencimentoBoleto());
		map.put(ENDERECO_NOTA_FISCAL, cliente.getEnderecoNotaFiscal());
		map.put(ESTADO_NOTA_FISCAL, cliente.getEstadoNotaFiscal());
		map.put(MES_ANO_REFERENCIA, cliente.getMesAnoReferencia());
		map.put(NOME_CLIENTE_NOTA_FISCAL, cliente.getNomeClienteNotaFiscal());

		return new LinhaDTO(LinhaDTO.TIPO_ITEN_DADOS_ASS_NET_SEM_NF, map);
	}

	@SuppressWarnings("unchecked")
	public LinhaDTO criarLinhaItenDadosAssSegundaVia(ClienteSegundaViaPrintDTO cliente) {
		Map map = new NotNullValuesHashMap(7);

		map.put(CODIGO_CLIENTE_NET, cliente.getCodigoClienteNET());
		map.put(CPF_CNPJ_COBRANCA, cliente.getCpfCNPJCobranca());
		map.put(DATA_PROCESSAMENTO_BOLETO, cliente.getDataProcessamentoBoleto());
		map.put(DATA_VENCIMENTO_BOLETO, cliente.getDataVencimentoBoleto());
		map.put(DATA_VENCIMENTO_BOLETO_ORIGEM, cliente.getDataVencimentoBoletoOrigem());
		map.put(IDENTIFICADOR_BOLETO, cliente.getIdentificadorBoleto());

		if (cliente.getInscricaoEstadualCobranca() == null) {
			map.put(INSCRICAO_ESTADUAL_COBRANCA, PrintHouseConstants.NotaFiscal.IE_ISENTO);
		} else {
			map.put(INSCRICAO_ESTADUAL_COBRANCA, cliente.getInscricaoEstadualCobranca());
		}

		return new LinhaDTO(LinhaDTO.TIPO_ITEN_DADOS_ASS_SEGUNDA_VIA, map);
	}

	@SuppressWarnings("unchecked")
	public LinhaDTO criarLinhaItenDadosAssNet(ClienteNotaFiscalPrintDTO cliente, boolean comNf) {

		Map map = new NotNullValuesHashMap(16);

		map.put(NUMERO_NOTA_FISCAL, cliente.getNumeroNotaFiscal());
		map.put(CFOP_NOTA_FISCAL, cliente.getCfopNotaFiscal());

		if (cliente.getInscricaoEstadualNotaFiscal() == null) {
			map.put(INSCRICAO_ESTADUAL_NOTA_FISCAL, PrintHouseConstants.NotaFiscal.IE_ISENTO);
		} else {
			map.put(INSCRICAO_ESTADUAL_NOTA_FISCAL, cliente.getInscricaoEstadualNotaFiscal());
		}

		map.put(VALOR_TOTAL_NOTA_FISCAL, cliente.getValorTotalNotaFiscal());
		map.put(HASH_CODE, cliente.getHashCode());
		map.put(ENDERECO_NOTA_FISCAL, cliente.getEnderecoNotaFiscal());
		map.put(BAIRRO_NOTA_FISCAL, cliente.getBairroNotaFiscal());
		map.put(CIDADE_NOTA_FISCAL, cliente.getCidadeNotaFiscal());
		map.put(ESTADO_NOTA_FISCAL, cliente.getEstadoNotaFiscal());
		map.put(CEP_NOTA_FISCAL, cliente.getCepNotaFiscal());
		map.put(CPF_CNPJ_NOTA_FISCAL, cliente.getCpfCnpjNotaFiscal());
		map.put(NOME_CLIENTE_NOTA_FISCAL, cliente.getNomeClienteNotaFiscal());
		map.put(CODIGO_CLIENTE_NET, cliente.getCodOperadora() + "/"
				+ StringUtils.prepad(cliente.getCodigoClienteNET(), 9, '0'));
		map.put(DATA_VENCIMENTO_BOLETO, cliente.getDataVencimentoBoleto());
		map.put(MES_ANO_REFERENCIA, cliente.getMesAnoReferencia());
		map.put(DATA_EMISSAO_NOTA_FISCAL, cliente.getDataEmissaoNotaFiscal());

		return new LinhaDTO(comNf ? LinhaDTO.TIPO_ITEN_DADOS_ASS_NET_COM_NF : LinhaDTO.TIPO_ITEN_DADOS_ASS_NET_SEM_NF,
				map);
	}

	@SuppressWarnings("unchecked")
	public LinhaDTO criarLinhaItenInicioFatura(DadosGeraisPrimeiraViaPrintDTO dadosGeraisPrimeiraViaPrintDTO) {
		Map map = new NotNullValuesHashMap(20);

		map.put(BAIRRO_COBRANCA, dadosGeraisPrimeiraViaPrintDTO.getBairroCobranca());
		map.put(CEP_COBRANCA, dadosGeraisPrimeiraViaPrintDTO.getCepCobranca());
		map.put(CIDADE_COBRANCA, dadosGeraisPrimeiraViaPrintDTO.getCidadeCobranca());
		map.put(CODIGO_LOTE, dadosGeraisPrimeiraViaPrintDTO.getCodigoLote());
		map.put(CODIGO_CLIENTE_NET, dadosGeraisPrimeiraViaPrintDTO.getCodigoClienteNET());
		map.put(ENDERECO_COBRANCA, dadosGeraisPrimeiraViaPrintDTO.getEnderecoCobranca());
		map.put(ESTADO_COBRANCA, dadosGeraisPrimeiraViaPrintDTO.getEstadoCobranca());
		map.put(IDENTIFICADOR_BOLETO, dadosGeraisPrimeiraViaPrintDTO.getIdentificadorBoleto());
		map.put(IDENTIFICADOR_BOLETO_ORIGEM, dadosGeraisPrimeiraViaPrintDTO.getIdentificadorBoletoOrigem());
		map.put(NOME_CLIENTE_COBRANCA, dadosGeraisPrimeiraViaPrintDTO.getNomeClienteCobranca());
		map.put(PREFIXO, dadosGeraisPrimeiraViaPrintDTO.getPrefixo());
		map.put(CODIGO_CLIENTE_EBT, dadosGeraisPrimeiraViaPrintDTO.getCodigoClienteEBT());
		
		//NSMSP_161258_NI_001\JAVA		
		if(dadosGeraisPrimeiraViaPrintDTO.getMensagemClaroClubeDTO()!=null && dadosGeraisPrimeiraViaPrintDTO.getMensagemClaroClubeDTO().getIsDemandaLigada() && dadosGeraisPrimeiraViaPrintDTO.getMensagemClaroClubeDTO().getDescMensagemFormatada()!=null ){
			map.put(PrintHouseConstants.LayoutArquivoPrintHouse.MENSAGEMCLAROCLUBE, dadosGeraisPrimeiraViaPrintDTO.getMensagemClaroClubeDTO().getDescMensagemFormatada());	
		}else{
			map.put(PrintHouseConstants.LayoutArquivoPrintHouse.MENSAGEMCLAROCLUBE, " ");	
		}	
		
		map.put(PrintHouseConstants.LayoutArquivoPrintHouse.FORMAENVIOFATURA, dadosGeraisPrimeiraViaPrintDTO.getFormaEnvioFatura());
		map.put(PrintHouseConstants.LayoutArquivoPrintHouse.BASE, dadosGeraisPrimeiraViaPrintDTO.getBase());
		map.put(PrintHouseConstants.LayoutArquivoPrintHouse.CIDCONTRATO, dadosGeraisPrimeiraViaPrintDTO.getCidContrato());
		map.put(PrintHouseConstants.LayoutArquivoPrintHouse.TIPOCOBRANCA, dadosGeraisPrimeiraViaPrintDTO.getTipoCobranca());
		map.put(PrintHouseConstants.LayoutArquivoPrintHouse.EMAILORIGEM, dadosGeraisPrimeiraViaPrintDTO.getEmailOrigem());
		map.put(PrintHouseConstants.LayoutArquivoPrintHouse.EMAILDESTINO, dadosGeraisPrimeiraViaPrintDTO.getEmailDestino());
		map.put(PrintHouseConstants.LayoutArquivoPrintHouse.LINKNAOOPTANTE, dadosGeraisPrimeiraViaPrintDTO.getLinkNaoOptante());
		map.put(PrintHouseConstants.LayoutArquivoPrintHouse.MESVENCIMENTO, dadosGeraisPrimeiraViaPrintDTO.getMesVencimento());

		return new LinhaDTO(LinhaDTO.TIPO_ITEN_INICIO_FATURA, map);
	}

	/**
	 * Método de apoio para o método protected FaturaDTO
	 * gerarFaturaPrintHouse(Integer idLote, Integer idBoleto)
	 * 
	 * @author Marcio Bellini - refactor Robin Michael Gray
	 * @number RF015_RF021
	 * 
	 * @param dados
	 *            DTO contendo informações para o preenchimento do Map referente
	 *            à interface ClienteCobrancaPrintDTO
	 * @return Map
	 */
	@SuppressWarnings( { "unchecked", "unused" })
	public LinhaDTO criarLinhaItenInicioFatura(DadosGeraisPrimeiraViaPrintDTO dados, CobrancaParceiroDTO dadosFunt) {

		final NotNullValuesHashMap map = new NotNullValuesHashMap(23);

		map.put(PREFIXO, dados.getPrefixo());
		map.put(CODIGO_LOTE, dados.getCodigoLote());
		map.put(NOME_CLIENTE_COBRANCA, dados.getNomeClienteCobranca());
		map.put(ENDERECO_COBRANCA, dados.getEnderecoCobranca());
		map.put(BAIRRO_COBRANCA, dados.getBairroCobranca());
		map.put(CIDADE_COBRANCA, dados.getCidadeCobranca());
		map.put(ESTADO_COBRANCA, dados.getEstadoCobranca());
		map.put(CEP_COBRANCA, dados.getCepCobranca());
		map.put(IDENTIFICADOR_BOLETO, dados.getIdentificadorBoleto());
		map.put(IDENTIFICADOR_BOLETO_ORIGEM, dados.getIdentificadorBoletoOrigem());
		map.put(CODIGO_CLIENTE_NET, dados.getCodOperadora() + "/"
				+ StringUtils.prepad(dados.getCodigoClienteNET(), 9, '0'));
		map.put(CODIGO_CLIENTE_EBT, dados.getCodigoClienteEBT());

		if (dados.getFc_boleto_consolidado().equalsIgnoreCase("C")
				|| dados.getFc_boleto_consolidado().equalsIgnoreCase("B")
				|| dados.getFc_boleto_consolidado().equalsIgnoreCase("P")) {

			String empresaFatura = "NET";
			if (dados.getCidContrato().equals("02121")){
				empresaFatura = "CLARO";
			}
			
			map.put(FUST, "Contribuição FUST "+empresaFatura+" FONE = R$ "
					+ valor(dadosFunt.getValorFust(), String.valueOf(dadosFunt.getValorFust()).length(), 2));
			map.put(FUNTEL, "Contribuição FUNTTEL "+empresaFatura+" FONE = R$ "
					+ valor(dadosFunt.getValorFuntel(), String.valueOf(dadosFunt.getValorFuntel()).length(), 2));
			map.put(MENSAGEMEBT, "S");

		} else {
			map.put(FUST, "");
			map.put(FUNTEL, "");
			map.put(MENSAGEMEBT, "N");
		}
		
		//NSMSP_161258_NI_001\JAVA		
		if(dados.getMensagemClaroClubeDTO()!=null && dados.getMensagemClaroClubeDTO().getIsDemandaLigada() && dados.getMensagemClaroClubeDTO().getDescMensagemFormatada()!=null ){
			map.put(PrintHouseConstants.LayoutArquivoPrintHouse.MENSAGEMCLAROCLUBE, dados.getMensagemClaroClubeDTO().getDescMensagemFormatada());	
		}else{
			map.put(PrintHouseConstants.LayoutArquivoPrintHouse.MENSAGEMCLAROCLUBE, " ");	
		}
		map.put(PrintHouseConstants.LayoutArquivoPrintHouse.FORMAENVIOFATURA, dados.getFormaEnvioFatura());
		map.put(PrintHouseConstants.LayoutArquivoPrintHouse.BASE, dados.getBase());
		map.put(PrintHouseConstants.LayoutArquivoPrintHouse.CIDCONTRATO, dados.getCidContrato());
		map.put(PrintHouseConstants.LayoutArquivoPrintHouse.TIPOCOBRANCA, dados.getTipoCobranca());
		map.put(PrintHouseConstants.LayoutArquivoPrintHouse.EMAILORIGEM, dados.getEmailOrigem());
		map.put(PrintHouseConstants.LayoutArquivoPrintHouse.EMAILDESTINO, dados.getEmailDestino());
		map.put(PrintHouseConstants.LayoutArquivoPrintHouse.LINKNAOOPTANTE, dados.getLinkNaoOptante());
		map.put(PrintHouseConstants.LayoutArquivoPrintHouse.MESVENCIMENTO, dados.getMesVencimento());
		
		return new LinhaDTO(LinhaDTO.TIPO_ITEN_INICIO_FATURA, map);
	}

	@SuppressWarnings("unchecked")
	public LinhaDTO criarLinhaItenInicioFatura(DadosGeraisSegundaViaPrintDTO dadosGeraisSegundaViaPrintDTO) {
		final NotNullValuesHashMap map = new NotNullValuesHashMap(20);

		map.put(BAIRRO_COBRANCA, dadosGeraisSegundaViaPrintDTO.getBairroCobranca());
		map.put(CEP_COBRANCA, dadosGeraisSegundaViaPrintDTO.getCepCobranca());
		map.put(CIDADE_COBRANCA, dadosGeraisSegundaViaPrintDTO.getCidadeCobranca());
		map.put(CODIGO_LOTE, dadosGeraisSegundaViaPrintDTO.getCodigoLote());
		map.put(CODIGO_CLIENTE_NET, dadosGeraisSegundaViaPrintDTO.getCodigoClienteNET());
		map.put(ENDERECO_COBRANCA, dadosGeraisSegundaViaPrintDTO.getEnderecoCobranca());
		map.put(ESTADO_COBRANCA, dadosGeraisSegundaViaPrintDTO.getEstadoCobranca());
		map.put(IDENTIFICADOR_BOLETO, dadosGeraisSegundaViaPrintDTO.getIdentificadorBoleto());
		map.put(IDENTIFICADOR_BOLETO_ORIGEM, dadosGeraisSegundaViaPrintDTO.getIdentificadorBoletoOrigem());
		map.put(NOME_CLIENTE_COBRANCA, dadosGeraisSegundaViaPrintDTO.getNomeClienteCobranca());
		map.put(PREFIXO, dadosGeraisSegundaViaPrintDTO.getPrefixo());
		map.put(CODIGO_CLIENTE_EBT, dadosGeraisSegundaViaPrintDTO.getCodigoClienteEBT());
		map.put(PrintHouseConstants.LayoutArquivoPrintHouse.FORMAENVIOFATURA, dadosGeraisSegundaViaPrintDTO.getFormaEnvioFatura());
		map.put(PrintHouseConstants.LayoutArquivoPrintHouse.BASE, dadosGeraisSegundaViaPrintDTO.getBase());
		map.put(PrintHouseConstants.LayoutArquivoPrintHouse.CIDCONTRATO, dadosGeraisSegundaViaPrintDTO.getCidContrato());
		map.put(PrintHouseConstants.LayoutArquivoPrintHouse.TIPOCOBRANCA, dadosGeraisSegundaViaPrintDTO.getTipoCobranca());
		map.put(PrintHouseConstants.LayoutArquivoPrintHouse.EMAILORIGEM, dadosGeraisSegundaViaPrintDTO.getEmailOrigem());
		map.put(PrintHouseConstants.LayoutArquivoPrintHouse.EMAILDESTINO, dadosGeraisSegundaViaPrintDTO.getEmailDestino());
		map.put(PrintHouseConstants.LayoutArquivoPrintHouse.LINKNAOOPTANTE, dadosGeraisSegundaViaPrintDTO.getLinkNaoOptante());
		map.put(PrintHouseConstants.LayoutArquivoPrintHouse.MESVENCIMENTO, dadosGeraisSegundaViaPrintDTO.getMesVencimento());
				
		return new LinhaDTO(LinhaDTO.TIPO_ITEN_INICIO_FATURA, map);
	}

	@SuppressWarnings("unchecked")
	public LinhaDTO criarLinhaItenFichaArrecadacao(FichaArrecadacaoPrintDTO ficha, FebrabamDTO codFebrabam,
			Integer linhasFichaArrecadacao, Double valorCobrado) {

		final NotNullValuesHashMap map = new NotNullValuesHashMap(7);

		map.put(CODIGO_DE_BARRAS, ficha.getCodigoDeBarras());
		map.put(DATA_VENCIMENTO_BOLETO, ficha.getDataVencimentoBoleto());
		map.put(LINHA_DIGITAVEL, ficha.getLinhaDigitavel());
		map.put(MES_ANO_REFERENCIA, ficha.getMesAnoReferencia());
		map.put(NOME_CLIENTE_COBRANCA, ficha.getNomeClienteCobranca());
		map.put(VALOR_COBRADO, valorCobrado);
		map.put(CODIGO_CLIENTE_NET, codFebrabam.getFebraban()
				+ String.valueOf(geraDigVerificador(codFebrabam.getParametro())));

		return new LinhaDTO(LinhaDTO.TIPO_ITEN_FICHA_ARRECADACAO, map, linhasFichaArrecadacao);
	}

	@SuppressWarnings("unchecked")
	public LinhaDTO criarLinhaItenItenDemonstrativoFinanceiro(Double valor, String descricao) {
		final NotNullValuesHashMap map = new NotNullValuesHashMap(2);

		map.put(KEY_DESCRICAO, descricao);
		map.put(KEY_VALOR, valor);

		return new LinhaDTO(LinhaDTO.TIPO_ITEN_ITEN_DEMONSTRATIVO_FINACEIRO, map);
	}

	@SuppressWarnings("unchecked")
	public LinhaDTO criarLinhaItenRodapeAgrupamentoDemoFinanceiro(Double valor, String descricao) {
		final NotNullValuesHashMap map = new NotNullValuesHashMap(2);

		map.put(KEY_DESCRICAO, descricao);
		map.put(KEY_VALOR, valor);

		return new LinhaDTO(LinhaDTO.TIPO_ITEN_RODAPE_AGRUPAMENTO_DEMO_FINACEIRO, map);
	}

	@SuppressWarnings("unchecked")
	public LinhaDTO criarLinhaItenCabecalhoAgrupamentoDemoFinanceiro(Double valor, String descricao) {
		final NotNullValuesHashMap map = new NotNullValuesHashMap(2);

		map.put(KEY_DESCRICAO, descricao);
		map.put(KEY_VALOR, valor);

		return new LinhaDTO(LinhaDTO.TIPO_ITEN_CABECALHO_AGRUPAMENTO_DEMO_FINACEIRO, map);
	}

	@SuppressWarnings("unchecked")
	public LinhaDTO criarLinhaItenTotalDemonstrativoFinanceiro(Double valorCobrado) {

		Map map = new NotNullValuesHashMap(1);

		map.put(PrintHouseConstants.LayoutArquivoPrintHouse.VALOR_COBRADO, valorCobrado);

		if (valorCobrado <= 0) {
			map.put(PrintHouseConstants.LayoutArquivoPrintHouse.VALOR_COBRADO, "0");
		}

		return new LinhaDTO(LinhaDTO.TIPO_ITEN_TOTAL_DEMONSTRATIVO_FINACEIRO, map);
	}
	
	@SuppressWarnings("unchecked")
	public LinhaDTO criarLinhaTributo(TributoDTO tributo) {
		return this.criarLinhaTributo(tributo, new Integer(1));
	}
	
	public LinhaDTO criarLinhaTributo(TributoDTO tributo, Integer quantidadeOcupa) {
		
		final NotNullValuesHashMap map = new NotNullValuesHashMap(5);

		map.put(PrintHouseConstants.Tributo.TRIBUTO_ID_BEAN, tributo.getIdBean());
		map.put(PrintHouseConstants.Tributo.TRIBUTO_SIGLA_ATRIBUTO, tributo.getSiglaAtributo());
		map.put(PrintHouseConstants.Tributo.TRIBUTO_VL_ALIQUOTA, tributo.getVlAliquota());
		map.put(PrintHouseConstants.Tributo.TRIBUTO_VL_ATRIBUTO, tributo.getVlAtributo());
		map.put(PrintHouseConstants.Tributo.TRIBUTO_BASE_CALCULO, tributo.getBaseCalculo());
		
		return new LinhaDTO(LinhaDTO.TIPO_ITEN_NET_TRIBUTOS_NOTA_FISCAL, map, quantidadeOcupa);
		
	}
	
	@SuppressWarnings("unchecked")
	public LinhaDTO criarLinhaMensagemNota(MensagensNotaFiscalDTO msg, Integer quantidadeOcupa) {
		final NotNullValuesHashMap mapDadosMensagemFiscal = new NotNullValuesHashMap(5);
		mapDadosMensagemFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.CODIGO_OPERADORA_MENSAGEM_NOTA_FISCAL,
		                           msg.getCodigoOperadoraMensagemNotaFiscal());
		mapDadosMensagemFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.PREFIXO_MENSAGEM_NOTA_FISCAL,
		                           msg.getPrefixoMensagemNotaFiscal());
		mapDadosMensagemFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.SETORIZACAO_MENSAGEM_NOTA_FISCAL,
		                           msg.getSetorizacaoMensagemNotaFiscal());
		mapDadosMensagemFiscal.put(PrintHouseConstants.LayoutArquivoPrintHouse.MENSAGEM_NOTA_FISCAL,
		                           msg.getMensagemNotaFiscal());		
		return new LinhaDTO(LinhaDTO.TIPO_MENSAGEM_NOTA_FISCAL, mapDadosMensagemFiscal, quantidadeOcupa);
	}
	
	/**
	 * Método para criação de linha de detalhes da nota fiscal NEt com os valores retornados do banco
	 * 
	 * @param tipoItem
	 * @param detalhamentoNotaFiscalDTO
	 * @return LinhaDTO
	 */
	@SuppressWarnings("unchecked")
	public LinhaDTO criarLinhaDetalheNotaFiscal(final Integer tipoItem, DetalhamentoNotaFiscalDTO detalhamentoNotaFiscalDTO) {
		return this.criarLinhaDetalheNotaFiscal(tipoItem, detalhamentoNotaFiscalDTO, Integer.valueOf(1), false);
	}
	
	/**
	 * Método para criação de linha de detalhes da nota fiscal NEt com os valores retornados do banco
	 * 
	 * @param tipoItem
	 * @param detalhamentoNotaFiscalDTO
	 * @param quantidadeOcupa
	 * @return LinhaDTO
	 */
	@SuppressWarnings("unchecked")
	public LinhaDTO criarLinhaDetalheNotaFiscal(final Integer tipoItem
			, DetalhamentoNotaFiscalDTO detalhamentoNotaFiscalDTO
			, Integer quantidadeOcupa) {
		return this.criarLinhaDetalheNotaFiscal(tipoItem, detalhamentoNotaFiscalDTO, Integer.valueOf(1), false);
	}

	/**
	 * Método para criação de linha de detalhes da nota fiscal NEt com os valores retornados do banco
	 * 
	 * @param tipoItem
	 * @param detalhamentoNotaFiscalDTO
	 * @param quantidadeOcupa
	 * @param temISS
	 * @return LinhaDTO
	 */
	public LinhaDTO criarLinhaDetalheNotaFiscal(final Integer tipoItem
			, DetalhamentoNotaFiscalDTO detalhamentoNotaFiscalDTO
			, Integer quantidadeOcupa, boolean temISS) {
		
		final NotNullValuesHashMap map = new NotNullValuesHashMap(5);
		
		map.put(PrintHouseConstants.DetalheNotaFiscal.ID_BEAN, detalhamentoNotaFiscalDTO.getIdBean());
		map.put(PrintHouseConstants.DetalheNotaFiscal.NOME_AGRUPAMENTO,detalhamentoNotaFiscalDTO.getNmGrupamento() );
		map.put(PrintHouseConstants.DetalheNotaFiscal.VALOR_SOMA_ITENS_AGRUPAMENTO, detalhamentoNotaFiscalDTO.getVlSomaItemAgrupamento());
		map.put(PrintHouseConstants.DetalheNotaFiscal.DESCRICAO, detalhamentoNotaFiscalDTO.getDescricao());
		map.put(PrintHouseConstants.DetalheNotaFiscal.COFINS,"");	
		map.put(PrintHouseConstants.DetalheNotaFiscal.ICMS,detalhamentoNotaFiscalDTO.getVlIcmsItem());
		map.put(PrintHouseConstants.DetalheNotaFiscal.PIS,"");	
		map.put(PrintHouseConstants.DetalheNotaFiscal.ISS,temISS ? detalhamentoNotaFiscalDTO.getVlIssItem() : "");	
		map.put(PrintHouseConstants.DetalheNotaFiscal.VALOR_BRUTO_ITEM,detalhamentoNotaFiscalDTO.getVlBrutoItem() );
		
		return new LinhaDTO(tipoItem, map, quantidadeOcupa);
		
	}

	public LinhaDTO criarLinhaDemonstrativoFinanceiro(final Integer tipoItem, DemonstrativoFinanceiroDTO itemDemonstrativo) {
		return criarLinhaDemonstrativoFinanceiro(tipoItem, itemDemonstrativo, new Integer(1));
	}
	
	public LinhaDTO criarLinhaDemonstrativoFinanceiro(final Integer tipoItem
			, DemonstrativoFinanceiroDTO itemDemonstrativo
			, Integer quantidadeOcupa
			) {
		
		final NotNullValuesHashMap map = new NotNullValuesHashMap(3);
		// nenhuma alteração feita no arquivo 
		map.put(PrintHouseConstants.DemonstrativoFinanceiroConstants.KEY_DESCRICAO,itemDemonstrativo.getDESCRICAOITEMDEMONST_FINANC());
		map.put(PrintHouseConstants.DemonstrativoFinanceiroConstants.KEY_VALOR, itemDemonstrativo.getDESCRICAOITEMDEMONST_FINANC() == null ? "" : itemDemonstrativo.getDESCRICAOITEMDEMONST_FINANC().contains("- VALIDO ATÉ") ? "        " : itemDemonstrativo.getVALORITEMDEMONST_FINANC());
		map.put(PrintHouseConstants.DemonstrativoFinanceiroConstants.KEY_GROUPO, itemDemonstrativo.getGRUPO_DEMONST_FINANC());
		
		return new LinhaDTO(tipoItem, map, quantidadeOcupa);
		
	}
	
	public LinhaDTO criarLinhaDemonstrativoFinanceiroTitulo(final Integer tipoItem, DemonstrativoFinanceiroDTO itemDemonstrativo) {
		return criarLinhaDemonstrativoFinanceiroTitulo(tipoItem, itemDemonstrativo, new Integer(1));
	}

	public LinhaDTO criarLinhaDemonstrativoFinanceiroTitulo(final Integer tipoItem
			, DemonstrativoFinanceiroDTO itemDemonstrativo
			, Integer quantidadeOcupa) {
		
		final NotNullValuesHashMap map = new NotNullValuesHashMap(3);
		
		map.put(PrintHouseConstants.DemonstrativoFinanceiroConstants.KEY_DESCRICAO,itemDemonstrativo.getDESCRICAOITEMDEMONST_FINANC());
		map.put(PrintHouseConstants.DemonstrativoFinanceiroConstants.KEY_VALOR,"");
		map.put(PrintHouseConstants.DemonstrativoFinanceiroConstants.KEY_GROUPO, itemDemonstrativo.getGRUPO_DEMONST_FINANC());		
		
		return new LinhaDTO(tipoItem, map, quantidadeOcupa);
		
	}

	
	private Integer geraDigVerificador(String valor) {

		int aux = 0;
		int multiplicador = 2;
		int i = valor.length();

		while (i > 0) {
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

	private String valor(Double valor, Integer tamanho, Integer decimais) {
		NumberFormat numberFormat = NumberFormat.getInstance(new Locale("pt", "BR"));
		String valorConstante;
		int size = tamanho.intValue();
		StringBuffer buffer = new StringBuffer(size);

		numberFormat.setMaximumFractionDigits(decimais.intValue());
		numberFormat.setMinimumFractionDigits(decimais.intValue());
		numberFormat.setGroupingUsed(false);

		valorConstante = numberFormat.format(valor.doubleValue());

		if (size > valorConstante.length()) {
			buffer.append(filler(new Integer(size - valorConstante.length()), ' '));
			buffer.append(valorConstante);
		} else {
			buffer.append(valorConstante.substring(0, size));
		}
		return buffer.toString();
	}

	private String filler(Integer tamanho, char ch) {
		char[] cs = new char[tamanho.intValue()];

		Arrays.fill(cs, ch);

		return new String(cs);
	}
	
	public LinhaDTO criarLinhaInicioFiliados() {
		final NotNullValuesHashMap map = new NotNullValuesHashMap(3);

		map.put(PrintHouseConstants.FiliadosConstants.KEY_DESCRICAO,"COLETIVO");
		map.put(PrintHouseConstants.FiliadosConstants.KEY_VALOR,"");

		return new LinhaDTO(LinhaDTO.TIPO_ITEN_CABECALHO_AGRUPAMENTO_DEMO_FINACEIRO, map, 0);
	}
	
	public LinhaDTO criarLinhaGrupoFiliados(String nomeGrupo, Integer quantidadeOcupa) {
		final NotNullValuesHashMap map = new NotNullValuesHashMap(3);

		map.put(PrintHouseConstants.FiliadosConstants.KEY_DESCRICAO,nomeGrupo);
		map.put(PrintHouseConstants.FiliadosConstants.KEY_VALOR,"");

		return new LinhaDTO(LinhaDTO.TIPO_ITEN_CABECALHO_AGRUPAMENTO_DEMO_FINACEIRO, map, quantidadeOcupa);
	}
	
	public LinhaDTO criarLinhaFiliadosTitulo(FiliadosDTO filiadosDTO, Integer quantidadeOcupa) {
		final NotNullValuesHashMap map = new NotNullValuesHashMap(3);

		map.put(PrintHouseConstants.FiliadosConstants.KEY_DESCRICAO,filiadosDTO.getNOME());
		map.put(PrintHouseConstants.FiliadosConstants.KEY_VALOR,"");

		return new LinhaDTO(LinhaDTO.TIPO_ITEN_FILIADOS_TITULO, map, quantidadeOcupa);
	}

	public LinhaDTO criarLinhaFiliados(FiliadosDTO filiadosDTO, Integer quantidadeOcupa) {
		final NotNullValuesHashMap map = new NotNullValuesHashMap(3);
		
		map.put(PrintHouseConstants.FiliadosConstants.KEY_DESCRICAO,filiadosDTO.getPRODUTO());
		map.put(PrintHouseConstants.FiliadosConstants.KEY_VALOR, filiadosDTO.getVALOR());
		
		return new LinhaDTO(LinhaDTO.TIPO_ITEN_FILIADOS, map, quantidadeOcupa);
	}
	
	public LinhaDTO criarLinhaFiliadosSubtotal(FiliadosDTO filiadosDTO, Integer quantidadeOcupa) {
		final NotNullValuesHashMap map = new NotNullValuesHashMap(3);
		
		map.put(PrintHouseConstants.FiliadosConstants.KEY_DESCRICAO, "");
		map.put(PrintHouseConstants.FiliadosConstants.KEY_VALOR, filiadosDTO.getVALOR());
		
		return new LinhaDTO(LinhaDTO.TIPO_ITEN_FILIADOS_SUBTOTAL, map, quantidadeOcupa);
	}

	public LinhaDTO criarLinhaFiliadosTotal(FiliadosDTO filiadosDTO, Integer quantidadeOcupa, String nomeGrupo) {
		final NotNullValuesHashMap map = new NotNullValuesHashMap(3);
		
		map.put(PrintHouseConstants.FiliadosConstants.KEY_DESCRICAO, "TOTAL " + nomeGrupo);
		map.put(PrintHouseConstants.FiliadosConstants.KEY_VALOR, filiadosDTO.getVALOR());
		
		return new LinhaDTO(LinhaDTO.TIPO_ITEN_FILIADOS_TOTAL, map, quantidadeOcupa);
	}
	
	@SuppressWarnings("unchecked")
	public LinhaDTO criarLinhaComplementoCampoImportante(MensagemAnatelDTO mensagemAnatelDTO) {
		final NotNullValuesHashMap map = new NotNullValuesHashMap(2);
		
		map.put(PrintHouseConstants.LayoutArquivoPrintHouse.ORIGEM_MENSAGEM, mensagemAnatelDTO.getNumOrdemMensagem());
		map.put(PrintHouseConstants.LayoutArquivoPrintHouse.MENSAGEM, mensagemAnatelDTO.getDescMensagemFormatada());
		
		return new LinhaDTO(LinhaDTO.TIPO_COMPLEMENTO_CAMPO_IMPORTANTE_RODAPE, map);
	}
	
	@SuppressWarnings("unchecked")
	public LinhaDTO criarLinhaMensagemStreaming(MensagemStreamingDTO mensagemStreamingDTO) {
		final NotNullValuesHashMap map = new NotNullValuesHashMap(1);
		
		map.put(PrintHouseConstants.LayoutArquivoPrintHouse.MENSAGEM_STREAMING, mensagemStreamingDTO.getDescMensagemFormatada());
		
		return new LinhaDTO(LinhaDTO.TIPO_MENSAGEM_STREAMING, map);
	}
	
	@SuppressWarnings("unchecked")
	public LinhaDTO criarLinhaPlanoServicoEbt(String planoServicoEbt) {
		final NotNullValuesHashMap map = new NotNullValuesHashMap(1);
		
		map.put(PrintHouseConstants.EBT.PLANOSERVICO, planoServicoEbt);
		
		return new LinhaDTO(LinhaDTO.TIPO_PLANO_SERVICO_EBT, map);
	}
}
