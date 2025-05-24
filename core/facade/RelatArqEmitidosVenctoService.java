/**
 * Created on ${date}
 * Project : ${project_name}
 *
 * Copyright © 2007 NET.
 * Brasil
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of NET.
 * You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Net Serviços.
 *
 * $$Id: RelatArqEmitidosVenctoService.java,v 1.3 2010/03/23 17:40:34 jucenali Exp $$
 */

package br.com.netservicos.novosms.emissao.core.facade;

import java.util.List;
import java.util.Map;

import br.com.netservicos.framework.core.bean.Bean;
import br.com.netservicos.framework.core.bean.DynamicBean;
import br.com.netservicos.framework.core.facade.Service;

/**
 * <P>
 * <B>Description :</B><BR>
 * Interface do RelatArqEmitidosVenctoEJBImpl.
 * </P>
 * <P>
 * <B> Issues : <BR>
 * None </B>
 * 
 * @author Cleber Luis Simm
 * @since 10/08/2007
 */

public interface RelatArqEmitidosVenctoService extends Service {

	static final String DYNA_PROPERTY_TIPO_RELATORIO = "tipoRelatorio";
	static final String DYNA_PROPERTY_CIDONTRATO = "cidContrato";
	static final String DYNA_PROPERTY_DTINICIOVENCIMENTO ="dtInicioVencimento";
	static final String DYNA_PROPERTY_DTFIMVENCIMENTO ="dtFimVencimento";
	static final String DYNA_PROPERTY_NOMEARQUIVO = "nomeArquivo";
	static final String DYNA_PROPERTY_IDORIGEMEMISSAO = "idOrigemEmissao";
	static final String DYNA_PROPERTY_DTINICIOEMISSAO = "dtInicioEmissao";
	static final String DYNA_PROPERTY_DTFIMEMISSAO = "dtFimEmissao";
	static final String DYNA_PROPERTY_IDTIPOCOBRANCA ="idTipoCobranca";
	static final String DYNA_PROPERTY_IDTIPOPOSTAGEM ="idTipoPostagem";
	static final String DYNA_PROPERTY_IDTIPO4 ="tipo4";
	static final String DYNA_PROPERTY_ID_FILA_TASK = "idFilaInserida";
	static final String DYNA_PROPERTY_CONTENT_TYPE = "contentType";
	
	static final String TASK_REPORT_IMPRESSAO = "RelatorioImpressaoTask";
	static final String TASK_REPORT_RESUMO = "RelatorioResumoTask";
	static final String TASK_REPORT_POSTAGEM = "RelatorioPostagemTask";
	static final String TASK_REPORT_POSTAGEMARQUIVO = "RelatorioPostagemArquivoTask";
	static final String TASK_REPORT_BANCARIO = "RelatorioBancarioTask";
	static final String TASK_REPORT_ARQUIVOS_EMITIDOS_VENCIMENTO = "REL_ARQ_EMIT_VENC";
	
	static final String REPORT_CID_CONTRATO = "CID_CONTRATO";
	static final String REPORT_DT_INICIO = "DT_INICIO_VNCTO";
	static final String REPORT_DT_FIM = "DT_FIM_VNCTO";
	static final String REPORT_NM_ARQUIVO = "NM_ARQUIVO";
	static final String REPORT_ID_ORIGEM_EMISSAO = "ID_ORIGEM_EMISSAO";
	static final String REPORT_DT_INICIO_EMISSAO = "DT_INICIO_EMISSAO";
	static final String REPORT_DT_FIM_EMISSAO = "DT_FIM_EMISSAO";
	static final String REPORT_ID_TIPO_COBRANCA = "ID_TIPO_COBRANCA";
	static final String REPORT_ID_TIPO_POSTAGEM = "ID_TIPO_POSTAGEM";
	static final String REPORT_ID_TIPO_RELATORIO = "ID_TIPO_RELATORIO";
	static final String REPORT_REPORT_NAME = "REPORT_NAME";
	static final String REPORT_QUERY_NAME = "QUERY_NAME";
	static final String REPORT_CONTENT_TYPE = "CONTENT_TYPE";
	static final String REPORT_PATH = "PATH";
	
	static final String LST_RELATORIO_IMPRESSAO_AGRUPADO = "lstRelatorioImpressaoAgrupado";
	static final String LST_RELATORIO_IMPRESSAO = "lstRelatorioImpressao";
	static final String LST_RELATORIO_IMPRESSAO_RESUMO = "lstRelatorioImpressaoResumo";
	static final String LST_RELATORIO_POSTAGEM = "lstRelatorioPostagem";
	static final String LST_RELATORIO_POSTAGEM_ARQUIVO = "lstRelatorioPostagemArquivo";
	static final String LST_RELATORIO_BANCARIO = "lstRelatorioBancario";
	
	/**
	 * 
	 * @param DynamicBean
	 * @author Cleber Luis Simm
	 * @since 10/08/2007
	 * @deprecated gera relátorio pela fila. Não mais utilizado
	 * 			   gerando localmente agora
	 */
	public boolean geraRelatArqEmitidosVenct(DynamicBean bean);
	
	/**
	 * 
	 * @since 16/10/2007
	 * @param bean
	 * @return
	 * 
	 * Gera o relátorio localmente
	 */
	public Map geraRelatorioLocal(DynamicBean bean); 
	
	/**
	 * @since 30/04/2009
	 * @param bean
	 * @return
	 */
	int retrieveTamanho(DynamicBean bean);
		
	
	<T> List<T> getDadosBeanByQuery( String queryName , Bean beanParametro , Boolean cache , Class<T> beanResult);

	public DynamicBean sendReport2Queue(DynamicBean bean);
}
