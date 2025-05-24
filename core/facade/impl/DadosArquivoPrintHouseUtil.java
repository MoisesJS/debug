package br.com.netservicos.novosms.emissao.core.facade.impl;

import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.BAIRRO_COBRANCA;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.BASE;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.CEP_COBRANCA;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.CIDADE_COBRANCA;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.CIDCONTRATO;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.CODIGO_BANCO;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.CODIGO_CLIENTE_EBT;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.CODIGO_CLIENTE_NET;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.CODIGO_LOTE;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.DECLARANUALDEBITOEBT;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.DECLARANUALDEBITONET;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.EMAILDESTINO;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.EMAILORIGEM;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.ENDERECO_COBRANCA;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.LOGRADOURO_COBRANCA;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.NUMERO_COBRANCA;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.COMPLEMENTO_COBRANCA;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.ESTADO_COBRANCA;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.FORMAENVIOFATURA;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.FUNTEL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.FUST;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.IDENTIFICADORMENSAGEM;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.IDENTIFICADOR_BOLETO;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.IDENTIFICADOR_BOLETO_ORIGEM;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.LABELCLIENTE;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.LINKNAOOPTANTE;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.MENSAGEMEBT;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.MESVENCIMENTO;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.MIGRACAOPADPOL;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.NOME_CLIENTE_COBRANCA;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.PREFIXO;
import static br.com.netservicos.novosms.emissao.constants.PrintHouseConstants.LayoutArquivoPrintHouse.TIPOCOBRANCA;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import br.com.netservicos.fatura.util.StringUtils;
import br.com.netservicos.novosms.emissao.constants.PrintHouseConstants;
import br.com.netservicos.novosms.emissao.dto.DadosGeraisPrimeiraViaPrintDTO;
import br.com.netservicos.novosms.emissao.printhouse.FaturaNetDTO;

public class DadosArquivoPrintHouseUtil{
	
	/**
	 * Método de apoio para o método protected FaturaDTO gerarFaturaPrintHouse(Integer idLote, Long idBoleto)
	 * 
	 * @author Marcio Bellini - refactor Robin Michael Gray
	 * @number RF015_RF021
	 * 
	 * @param dadosGeraisPrimeiraViDTO
	 *            DTO contendo informações para o preenchimento do Map referente à interface ClienteCobrancaPrintDTO
	 * @return Map
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
    public Map preencheMapFatura(DadosGeraisPrimeiraViaPrintDTO dadosGeraisPrimeiraViDTO,
	                             FaturaNetDTO fatura,
	                             Boolean ValidaMsg,
	                             String tipoMsg){

		Map mapFatura = new HashMap<String, String>();
		mapFatura.put(PREFIXO, notNullObject(dadosGeraisPrimeiraViDTO.getPrefixo()));
		mapFatura.put(CODIGO_LOTE, notNullObject(dadosGeraisPrimeiraViDTO.getCodigoLote()));
		mapFatura.put(NOME_CLIENTE_COBRANCA, notNullObject(dadosGeraisPrimeiraViDTO.getNomeClienteCobranca()));
		mapFatura.put(ENDERECO_COBRANCA, notNullObject(dadosGeraisPrimeiraViDTO.getEnderecoCobranca()));
		mapFatura.put(LOGRADOURO_COBRANCA, notNullObject(dadosGeraisPrimeiraViDTO.getLogradouroCobranca()));
		mapFatura.put(NUMERO_COBRANCA, notNullObject(dadosGeraisPrimeiraViDTO.getNumeroCobranca()));
		mapFatura.put(COMPLEMENTO_COBRANCA, notNullObject(dadosGeraisPrimeiraViDTO.getComplementoCobranca()));
		mapFatura.put(BAIRRO_COBRANCA, notNullObject(dadosGeraisPrimeiraViDTO.getBairroCobranca()));
		mapFatura.put(CIDADE_COBRANCA, notNullObject(dadosGeraisPrimeiraViDTO.getCidadeCobranca()));
		mapFatura.put(ESTADO_COBRANCA, notNullObject(dadosGeraisPrimeiraViDTO.getEstadoCobranca()));
		
		mapFatura.put(CEP_COBRANCA, 
		              StringUtils.prepad(notEqualsNullString(dadosGeraisPrimeiraViDTO.getCepCobranca()), 8, '0'));

		// CODIGO BANCO - asoares
		mapFatura.put(CODIGO_BANCO, notNullObject(dadosGeraisPrimeiraViDTO.getCodigoBanco()));
		mapFatura.put(IDENTIFICADOR_BOLETO, notNullObject(dadosGeraisPrimeiraViDTO.getIdentificadorBoleto()));
		mapFatura.put(IDENTIFICADOR_BOLETO_ORIGEM,
		              notNullObject(dadosGeraisPrimeiraViDTO.getIdentificadorBoletoOrigem()));
		
		mapFatura.put(CODIGO_CLIENTE_NET,
		              notNullString(dadosGeraisPrimeiraViDTO.getCodOperadora() + "/" +
		                      StringUtils.prepad(notNullString(dadosGeraisPrimeiraViDTO.getCodigoClienteNET()), 9, '0')));

		mapFatura.put(CODIGO_CLIENTE_EBT, notNullString(dadosGeraisPrimeiraViDTO.getCodigoClienteEBT()));

		if (ValidaMsg) {
			mapFatura.put(LABELCLIENTE, tipoMsg + "Cliente");
		} else {
			mapFatura.put(LABELCLIENTE, "Cliente");
		}

		if ("C".equalsIgnoreCase(dadosGeraisPrimeiraViDTO.getFc_boleto_consolidado()) ||
				"B".equalsIgnoreCase(dadosGeraisPrimeiraViDTO.getFc_boleto_consolidado()) ||
				"P".equalsIgnoreCase(dadosGeraisPrimeiraViDTO.getFc_boleto_consolidado()) ||
				("D".equalsIgnoreCase(dadosGeraisPrimeiraViDTO.getFc_boleto_consolidado()) &&
		        fatura.isOperadoraNet() == false)) {

			String valorFust = "0.00";
			String valorFuntel = "0.00";

			if (fatura.getValorFust() != null) {
				valorFust = valor(fatura.getValorFust(), 5, 2);
			}

			if (fatura.getValorFuntel() != null) {
				valorFuntel = valor(fatura.getValorFuntel(), 5, 2);
			}
			
			String empresaFatura = "NET";
			if (fatura.getCidContrato().equals("02121")){
				empresaFatura = "CLARO";
			}

			mapFatura.put(FUST, "Contribuição FUST "+empresaFatura+" FONE = R$ " + valorFust);
			mapFatura.put(FUNTEL, "Contribuição FUNTTEL "+empresaFatura+" FONE = R$ " + valorFuntel);
			mapFatura.put(MENSAGEMEBT, dadosGeraisPrimeiraViDTO.getFc_boleto_consolidado());

		} else {
			mapFatura.put(FUST, "");
			mapFatura.put(FUNTEL, "");
			mapFatura.put(MENSAGEMEBT, notNullString(dadosGeraisPrimeiraViDTO.getFc_boleto_consolidado()));
		}

		//NSMSP_161258_NI_001\JAVA		
		if(dadosGeraisPrimeiraViDTO.getMensagemClaroClubeDTO()!=null && dadosGeraisPrimeiraViDTO.getMensagemClaroClubeDTO().getIsDemandaLigada() && dadosGeraisPrimeiraViDTO.getMensagemClaroClubeDTO().getDescMensagemFormatada()!=null ){
			mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.MENSAGEMCLAROCLUBE, dadosGeraisPrimeiraViDTO.getMensagemClaroClubeDTO().getDescMensagemFormatada());	
		}else{
			mapFatura.put(PrintHouseConstants.LayoutArquivoPrintHouse.MENSAGEMCLAROCLUBE, " ");	
		}

		mapFatura.put(FORMAENVIOFATURA, dadosGeraisPrimeiraViDTO.getFormaEnvioFatura());
		mapFatura.put(BASE, dadosGeraisPrimeiraViDTO.getBase());
		mapFatura.put(CIDCONTRATO, dadosGeraisPrimeiraViDTO.getCidContrato());
		mapFatura.put(TIPOCOBRANCA, dadosGeraisPrimeiraViDTO.getTipoCobranca());
		mapFatura.put(EMAILORIGEM, dadosGeraisPrimeiraViDTO.getEmailOrigem());
		mapFatura.put(EMAILDESTINO, dadosGeraisPrimeiraViDTO.getEmailDestino());
		mapFatura.put(LINKNAOOPTANTE, dadosGeraisPrimeiraViDTO.getLinkNaoOptante());
		mapFatura.put(MESVENCIMENTO, dadosGeraisPrimeiraViDTO.getMesVencimento());
		
		mapFatura.put("TIPOBOLETOAVULSO", dadosGeraisPrimeiraViDTO.getIdTipoBoletoAvulso());

		// Quitação de Debito Anual
		mapFatura.put(DECLARANUALDEBITONET, dadosGeraisPrimeiraViDTO.getDeclaracaoAnualDebitoNET());
		mapFatura.put(DECLARANUALDEBITOEBT, dadosGeraisPrimeiraViDTO.getDeclaracaoAnualDebitoEBT());

		// Mensagem migração política
		mapFatura.put(MIGRACAOPADPOL, dadosGeraisPrimeiraViDTO.getMigracaoPadPol());

		// Identificador da Mensagem de Quitacao
		mapFatura.put(IDENTIFICADORMENSAGEM, dadosGeraisPrimeiraViDTO.getIdentificadorMensagem());
		
		return mapFatura;
	}

	public Object notNullObject(Object dado){
		return dado == null ? "" : dado;
	}

	public String notNullString(String dado){
		return dado == null ? "" : dado;
	}

	public String notEqualsNullString(String dado){

		if (dado == null || dado.equalsIgnoreCase("null")) {
			return "";
		} else {
			return dado;
		}
	}
	
	public String valor(Double valor, Integer tamanho, Integer decimais){
    	NumberFormat numberFormat = NumberFormat.getInstance(new Locale("pt", "BR"));
    	String valorConstante;
    	int size = tamanho.intValue();
    	StringBuffer buffer = new StringBuffer( size );
    	
    	numberFormat.setMaximumFractionDigits( decimais.intValue() );
    	numberFormat.setMinimumFractionDigits(decimais.intValue());
    	numberFormat.setGroupingUsed(false);
    	valorConstante = numberFormat.format( valor.doubleValue() );
		
		if( size > valorConstante.length() ){
			buffer.append( filler( new Integer( size - valorConstante.length() ), ' ' ) );
			buffer.append( valorConstante );
		}else{
			buffer.append( valorConstante.substring( 0, size ) );
		}
		return buffer.toString();
    }
	
	public String filler(Integer tamanho, char ch) {
		char[] cs = new char[tamanho.intValue()];

		Arrays.fill(cs, ch);

		return new String(cs);
	}

}
