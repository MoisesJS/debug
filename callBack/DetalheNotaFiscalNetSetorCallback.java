package br.com.netservicos.novosms.emissao.callBack;

import java.util.Map;

import br.com.netservicos.framework.core.bean.DynamicBean;
import br.com.netservicos.novosms.emissao.dto.LinhaDTO;
import br.com.netservicos.novosms.emissao.dto.SetorDTO;

public class DetalheNotaFiscalNetSetorCallback extends SetorCallback {


	private static final String CAMPO_DESCRICAO = "NOMEAGRUPAMENTO";

	/**
	 * Variavel que armazena o registro anterior do resultset para que seja comparada com o valor a atual.
	 */
	private Map valorAnterior; 

	/**
	 * Construtor que recebe o setor que deve ser adicionado as linhas.
	 * 
	 * @version 1.0 - 31/03/2006 - Criacao
	 * @author Everton Ken Tomita
	 * @number RF015-021
	 * 
	 * @param Setor
	 *            objeto que armazena as linhas que irao compor o setor.
	 * 
	 * @semantics
	 * 
	 * Chama o contrutor da superclasse passando o parametro recebido
	 */
	public DetalheNotaFiscalNetSetorCallback(SetorDTO setorItemDetalhe) {
		super(setorItemDetalhe);
	}

	/**
	 * String de comando SQL que deve ser executado para montar os dados do setor de detalhamento da nota fiscal da
	 * NET.
	 * 
	 * @version 1.0 - 14/03/2006 - Criacao
	 * @author Everton Ken Tomita
	 * @number RF015-021
	 * 
	 * @semantics
	 * 
	 * Pegar a variavel (idNotaFiscal)
	 * 
	 * Retornar a string:
	 * 
	 * select TBSMS_TIPO_ITEM_HIST.DS_DESC, // 15 e 17 - nome do agrupamento TBSMS_ITEM_NOTA_FSCL.VL_ITEM, // 17 -
	 * valor da soma TBSMS_DESC_DET_ITEM.DS_DET_ITEM, // 16 - descricao TBSMS_DET_ITEM_NOTA_FSCL.VL_DET // 16 -
	 * valor bruto do item from TBSMS_NOTA_FSCL, TBSMS_TIPO_ITEM_HIST, TBSMS_ITEM_NOTA_FSCL,
	 * TBSMS_DET_ITEM_NOTA_FSCL, TBSMS_DESC_DET_ITEM where TBSMS_TIPO_ITEM_HIST.ID_TIPO_ITEM_HIST =
	 * TBSMS_ITEM_NOTA_FSCL.ID_TIPO_ITEM_HIST and TBSMS_ITEM_NOTA_FSCL.ID_ITEM_NOTA_FSCL =
	 * TBSMS_DET_ITEM_NOTA_FSCL.ID_ITEM_NOTA_FSCL and TBSMS_DET_ITEM_NOTA_FSCL.ID_DESC_DET_ITEM =
	 * TBSMS_DESC_DET_ITEM.ID_DESC_DET_ITEM and TBSMS_NOTA_FSCL.ID_NOTA_FSCL = TBSMS_ITEM_NOTA_FSCL.ID_NOTA_FSCL
	 * and TBSMS_NOTA_FSCL.ID_NOTA_FSCL = (idNotaFiscal) order by TBSMS_TIPO_ITEM_HIST.NR_ORDEM
	 */
	/*
	 * public String getComando() { }
	 */

	/**
	 * Coloca o resultado da consulta na ordem que devem ser montado no arquivo.
	 * 
	 * @version 1.0 - 14/03/2006 - Criacao
	 * @author Everton Ken Tomita
	 * @number RF015-021
	 * 
	 * @param valor
	 *            Map que contem os valores dos campos que foi feito a consulta. <br>
	 *            Onde: <br>
	 *            key - campo do comando SQL executado <br>
	 *            value - valor do campo do comando SQL
	 * @param rowNum
	 *            int com o valor da linha atual vindo do callback
	 * 
	 * @semantics
	 * 
	 * Se no map (valor) o campo "TBSMS_TIPO_ITEM_HIST.DS_DESC" da tabela "TBSMS_ITEM_NOTA_FSCL" eh diferente da
	 * linha anterior e eh o primeiro registro: Adicionar o map no setor com o tipo da linha 15
	 * 
	 * Se nao se, o campo eh igual ao anterior mas naum eh o primeiro registro: Adicionar o map no setor com o tipo
	 * da linha 17 Adicionar o map no setor com o tipo da linha 0 Adicionar o map no setor com o tipo da linha 15
	 * 
	 * Fim do Se
	 * 
	 * Adicionar o map no setor com o tipo da linha 16
	 * 
	 * Armezanar na variavel (valorAnterior) o valor do map (valor)
	 */
	public void execute(DynamicBean valor, int rowNum) {

		String registroAnterior = null;
		if (valorAnterior != null) {
			registroAnterior = (String)this.valorAnterior.get(CAMPO_DESCRICAO);
		}
		String registroAtual = (String) valor.get(CAMPO_DESCRICAO);

		if (registroAnterior == null ) {
			this.addLinha(LinhaDTO.TIPO_ITEN_NET_CABECALHO_AGRUPAMENTO.intValue(), valor);
		} else if (!registroAtual.equals(registroAnterior)) {
			this.addLinha(LinhaDTO.TIPO_ITEN_NET_RODAPE_AGRUPAMENTO.intValue(), valorAnterior);
			this.addLinha(LinhaDTO.TIPO_ITEN_BLANK_LINE.intValue(), valor);
			this.addLinha(LinhaDTO.TIPO_ITEN_NET_CABECALHO_AGRUPAMENTO.intValue(), valor);
		} 
		this.addLinha(LinhaDTO.TIPO_ITEN_NET_DETALHAMENTO.intValue(), valor);
		this.valorAnterior = valor;
	}

	/**
	 * Apos a finalizacao do callback, coloca o rodape do agrupamento de item no setor.
	 * 
	 * @version 1.0 - 15/03/2006 - Criacao
	 * @author Everton Ken Tomita
	 * @number RF015-021
	 * 
	 * @semantics
	 * 
	 * Se a variavel (valorAnterior) difernte de nulo faca: Adicionar o map (valorAnterior) no setor com o tipo da
	 * linha 17 Adicionar o map (valorAnterior) no setor com o tipo da linha 0 Fim do se
	 */
	public void finalizeDAOCallback() {
		
		if (this.valorAnterior != null) {
			this.addLinha(LinhaDTO.TIPO_ITEN_NET_RODAPE_AGRUPAMENTO.intValue(), this.valorAnterior);
			this.addLinha(LinhaDTO.TIPO_ITEN_BLANK_LINE.intValue(), this.valorAnterior);
		}
		
	}
}