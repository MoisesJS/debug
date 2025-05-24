package br.com.netservicos.novosms.emissao.core.facade.impl;

import static org.apache.commons.lang.StringUtils.isEmpty;

import java.util.HashMap;
import java.util.List;

import br.com.netservicos.novosms.emissao.constants.PrintHouseConstants;
import br.com.netservicos.novosms.emissao.dto.LinhaDTO;
import br.com.netservicos.novosms.emissao.dto.SetorDTO;

public class LayoutArquivoPrintHouseUtil{

	// Numero maximo de linhas por folha do Demonstrativo Financeiro
	private static final int[]	LINHAS_FOLHA = { 22000, 38000, 50000 }; // 1a, 2a, 3a folha em diante e' indice 2
	private static final String	NOME_ITENS_EVENTUAIS	= "Itens Eventuais";

	public int[] adicionarItem(String nomeGrupo,
	                           LinhaDTO linha,
	                           SetorDTO setorDemFinFicArr,
	                           List<LinhaDTO> itensEventuaisTemp,
	                           int contLinhas,
	                           int paginaAtual,
	                           int linhasFichaArrecadacao){
		int[] result = new int[] { paginaAtual, contLinhas };

		// NET LAR nao tem subgrupo
		Object tmpNome = linha.getValor().get(PrintHouseConstants.DemonstrativoFinanceiroConstants.KEY_DESCRICAO);
		if (tmpNome != null & tmpNome.toString().indexOf(":::") != -1) {
			return result;
		}

		// Se a linha for de itens eventuais, armazena em local temporario para depois jogar no fim do demonstrativo
		if (!isEmpty(nomeGrupo) && nomeGrupo.startsWith(NOME_ITENS_EVENTUAIS)) {
			itensEventuaisTemp.add(linha);
		}
		else {
			int[] aux = verificaQuebraColunaPagina(linha,
			                                       contLinhas,
			                                       paginaAtual,
			                                       setorDemFinFicArr,
			                                       nomeGrupo,
			                                       itensEventuaisTemp,
			                                       linhasFichaArrecadacao);
			setorDemFinFicArr.addLinha(linha);
			contLinhas = aux[1];
			contLinhas += linha.getQuantidadeOcupa();
			result = new int[] { aux[0], contLinhas };
		}

		return result;
	}

	/**
	 * Verifica se a linha a ser inserida vai gerar quebra de coluna ou quebra de pagina. Nestes casos, adiciona uma
	 * linha para delimitar.
	 * 
	 * @param tamLinha
	 * @param contLinhas
	 * @param paginaAtual
	 * @param setorDemFinFicArr
	 * @param nomeGrupo
	 * @param itensEventuaisTemp
	 * @return
	 */
	private int[] verificaQuebraColunaPagina(LinhaDTO linha,
	                                         int contLinhas,
	                                         int paginaAtual,
	                                         SetorDTO setorDemFinFicArr,
	                                         String nomeGrupo,
	                                         List<LinhaDTO> itensEventuaisTemp,
	                                         int linhasFichaArrecadacao){
		return verificaQuebraColunaPagina(linha,
		                                  contLinhas,
		                                  paginaAtual,
		                                  setorDemFinFicArr,
		                                  linhasFichaArrecadacao,
		                                  false);
	}

	/**
	 * Verifica se a linha a ser inserida vai gerar quebra de coluna ou quebra de pagina. Nestes casos, adiciona uma
	 * linha para delimitar.
	 * 
	 * @param tamLinha
	 * @param contLinhas
	 * @param paginaAtual
	 * @param setorDemFinFicArr
	 * @param nomeGrupo
	 * @param itensEventuaisTemp
	 * @param forcarQuebra
	 *            se TRUE, faz quebra de pagina/coluna mesmo que ainda caibam mais itens.
	 * @return
	 */
	private int[] verificaQuebraColunaPagina(LinhaDTO linha,
	                                         int contLinhas,
	                                         int paginaAtual,
	                                         SetorDTO setorDemFinFicArr,
	                                         int linhasFichaArrecadacao,
	                                         boolean forcarQuebra){
		// Tamanho da linha atual
		int tamLinha = linha.getQuantidadeOcupa();

		// Numero variavel de linhas no demonstrativo financeiro. Só a ultima linha de cada pagina
		// vai ter tamanho setado de forma a forçar quebra da pagina.
		linha.setQuantidadeOcupa(0);

		// Cada 2 paginas eh uma folha
		int folhaAtual = ((int) (paginaAtual - 1) / 2) + 1;

		int linhasPaginaAtual = folhaAtual >= LINHAS_FOLHA.length ? LINHAS_FOLHA[LINHAS_FOLHA.length - 1]
		        : LINHAS_FOLHA[folhaAtual - 1];
		int linhasColunaAtual = linhasPaginaAtual / 2;

		// Verifica se ha algum tipo de quebra
		if (forcarQuebra || (tamLinha + contLinhas > linhasColunaAtual)) {
			if (paginaAtual % 2 == 0) { // Pagina numero par, entao deve quebrar para proxima folha
				// quebra coluna e página

				// A linha da quebra de pagina do demonstrativo é setada com o nro maximo de linhas
				// -1, pq o gerarFatura atribui tamanha 1 pras linhas q estao com 0,
				// para forçar uma quebra de paginas pelo componente de paginacao, ja que a quantidade
				// de itens no demonstrativo financeiro é variavel
				LinhaDTO linhaTmp = setorDemFinFicArr.get(setorDemFinFicArr.getQuantidadeLinhasSetor() - 1);
				
				// Linha anterior tem quebra
				linhaTmp.setQuantidadeOcupa(linhasFichaArrecadacao);
			}
			else {
				// só há quebra de coluna
				LinhaDTO tempLinha = new LinhaDTO(LinhaDTO.TIPO_QUEBRA_COLUNA_DEMONSTRATIVO,
				                                  new HashMap<String, String>(),
				                                  0);
				setorDemFinFicArr.addLinha(tempLinha);
			}
			paginaAtual++;
			contLinhas = 0; // se ha quebra, zera o contador de linhas para a coluna
		}
		contLinhas += tamLinha;
		return new int[] { paginaAtual, contLinhas };
	}
}
