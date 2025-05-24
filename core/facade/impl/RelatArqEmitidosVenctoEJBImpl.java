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
* $$Id: RelatArqEmitidosVenctoEJBImpl.java,v 1.3 2010/03/23 17:40:33 jucenali Exp $$
*/
package br.com.netservicos.novosms.emissao.core.facade.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.CreateException;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import br.com.netservicos.framework.core.bean.Bean;
import br.com.netservicos.framework.core.bean.DynamicBean;
import br.com.netservicos.framework.core.bean.task.constants.TaskConstants;
import br.com.netservicos.framework.service.report.BaseReportManager;
import br.com.netservicos.framework.service.report.BaseReportService;
import br.com.netservicos.framework.task.service.TaskService;
import br.com.netservicos.framework.util.BaseConstants;
import br.com.netservicos.framework.util.loader.ResourceLoader;
import br.com.netservicos.novosms.emissao.core.facade.RelatArqEmitidosVenctoService;
import br.com.netservicos.novosms.emissao.dto.RelatArqEmitidosVenctoDTO;
import br.com.netservicos.novosms.geral.constants.GeralConstants;


/**
 * @author Cleber Luis Simm
 * @since 10/08/2007
 * @version 1.0
 * 
 * @ejb.bean name="RelatArqEmitidosVenctoEJB" type="Stateless"
 *           display-name="RelatArqEmitidosVenctEJB"
 *           description="RelatArqEmitidosVenctEJB Session EJB Stateless"
 *           view-type="both"
 *           jndi-name="netservicos/ejb/emissao/RelatArqEmitidosVenctEJB"
 *           local-jndi-name="netservicos/ejb/local/emissao/RelatArqEmitidosVenctEJB"
 *           transaction-type="Container"
 * 
 * @ejb.interface local-extends="javax.ejb.EJBLocalObject"
 *                extends="javax.ejb.EJBObject"
 * 
 * @ejb.home local-extends="javax.ejb.EJBLocalHome" extends="javax.ejb.EJBHome"
 */
public class RelatArqEmitidosVenctoEJBImpl extends EmissaoBaseServiceImpl implements
		RelatArqEmitidosVenctoService {

	List <RelatArqEmitidosVenctoDTO> queryResult=null;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5114622716538821337L;
	
	private static final String REPORT_LOGO = "logo";
	private static final String REPORT_NTLOGO_PATH = "/br/com/netservicos/novosms/task/resources/logo2.gif";

	/**
	 * @ejb.create-method view-type="both"
	 * @throws CreateException
	 */
	public void ejbCreate() throws CreateException {
		super.ejbCreate();
	}
	
	/**
	 * @author Cleber Luis Simm
	 * @param DynamicBean
	 * 
	 *  RF094 - Relátorio dos arquivos emitidos por vencimento
	 *  
	 * 
	 * @ejb.interface-method view-type = "both"
	 * 
	 * @deprecated gera os relátorios pela fila. Não mais utilizado
	 *             gerando agora localmente
	 */
	public boolean geraRelatArqEmitidosVenct(DynamicBean bean){
		
		Integer tipo4 = (Integer)bean.get(DYNA_PROPERTY_IDTIPO4);
		Integer tipoRelatorio = (Integer)bean.get(DYNA_PROPERTY_TIPO_RELATORIO);
		String cidContrato = (String)bean.get(DYNA_PROPERTY_CIDONTRATO);
		String dtInicioVencimento = (String)bean.get(DYNA_PROPERTY_DTINICIOVENCIMENTO);;
		String dtFimVencimento = (String)bean.get(DYNA_PROPERTY_DTFIMVENCIMENTO);
		String nomeArquivo = (String)bean.get(DYNA_PROPERTY_NOMEARQUIVO);
		String idOrigemEmissao = (String)bean.get(DYNA_PROPERTY_IDORIGEMEMISSAO);
		String dtInicioEmissao = (String)bean.get(DYNA_PROPERTY_DTINICIOEMISSAO);
		String dtFimEmissao = (String)bean.get(DYNA_PROPERTY_DTFIMEMISSAO);
		Integer idTipoCobranca = (Integer)bean.get(DYNA_PROPERTY_IDTIPOCOBRANCA);
		Integer idTipoPostagem = (Integer)bean.get(DYNA_PROPERTY_IDTIPOPOSTAGEM);
		
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put(REPORT_CID_CONTRATO,cidContrato != null && cidContrato.equals("-1")? null :cidContrato);
		parametros.put(REPORT_DT_INICIO,dtInicioVencimento);
		parametros.put(REPORT_DT_FIM,dtFimVencimento);
		parametros.put(REPORT_NM_ARQUIVO,nomeArquivo);
		parametros.put(REPORT_ID_ORIGEM_EMISSAO,idOrigemEmissao != null && idOrigemEmissao.equals("-1")? null : idOrigemEmissao );
		parametros.put(REPORT_DT_INICIO_EMISSAO,dtInicioEmissao);
		parametros.put(REPORT_DT_FIM_EMISSAO,dtFimEmissao);
		parametros.put(REPORT_ID_TIPO_COBRANCA,idTipoCobranca != null && idTipoCobranca != -1  ? idTipoCobranca.toString() : null);
		parametros.put(REPORT_ID_TIPO_POSTAGEM,idTipoPostagem != null && idTipoPostagem != -1 ? idTipoPostagem.toString() : null);
		
		TaskService taskService = super.getService(TaskService.class);
		String currentDBService = super.getUserSession().getCurrentDbService();
		String currentDBIdentifier = currentDBService.substring(GeralConstants.PREFIXO_DATABASE_SERVICE.length());
		
		Long idFila = (Long) super.getPrincipalProperties().get(TaskConstants.ID_FILA);
		if (tipoRelatorio == 1){
			if( nomeArquivo != null && !nomeArquivo.equals("")){
				taskService.submitTaskToFila(TASK_REPORT_IMPRESSAO, parametros, super.getUserSession().getUserId(), currentDBIdentifier, idFila, "RelatorioImpressao");
			}else{
				taskService.submitTaskToFila(TASK_REPORT_RESUMO, parametros, super.getUserSession().getUserId(), currentDBIdentifier, idFila, "RelatorioResumo");
			}
			return true;
		}else if(tipoRelatorio == 2){
			
			if(nomeArquivo == null || nomeArquivo.equals("") ){
				taskService.submitTaskToFila(TASK_REPORT_POSTAGEM, parametros, super.getUserSession().getUserId(), currentDBIdentifier, idFila, "RelatorioPostagem");
			}else{
				taskService.submitTaskToFila(TASK_REPORT_POSTAGEMARQUIVO, parametros, super.getUserSession().getUserId(), currentDBIdentifier, idFila, "RelatorioPostagemArquivo");
			}
			return true;
		}else if(tipoRelatorio == 3){
			taskService.submitTaskToFila(TASK_REPORT_BANCARIO, parametros, super.getUserSession().getUserId(), currentDBIdentifier, idFila, "RelatorioBancario");
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * @ejb.transaction type="Required"
	 * @ejb.interface-method view-type="both"
	 * 
	 * Gera localmente o relátorios
	 */
	public Map geraRelatorioLocal(DynamicBean bean)
	{
		HashMap<String, Object> parametros = returnParam(bean);
		DynamicBean filtro = returnFiltro(bean);
		String[] ret= returnType(bean);
		queryResult = getDadosBeanByQuery(ret[1], filtro, false, RelatArqEmitidosVenctoDTO.class);
		
		JRDataSource ds = new JRBeanCollectionDataSource(queryResult);
		BaseReportManager reportManager = BaseReportManager.getInstance();
		Object report = reportManager.getReport(ret[0]);
		
		Map reportData = BaseReportService.generateReport(report, parametros, ds);
		retrieveTamanho(bean);	
		reportData.put(REPORT_REPORT_NAME, ret[0]);
		return reportData;
	}
	
	/**
	 * Método auxiliar para retornar o nome do relatório, nome da query e path do jasper<br>
	 * Índice 0: Nome do arquivo JRXML que gera o relatório.<br>
	 * Índice 1: Nome da query que é chamada pra montar o relatório.<br>
	 * Índice 2: Caminho do jasper.
	 * @param bean DynamicBean
	 * @return Array de String's contendo os valores
	 */
	private String[] returnType(DynamicBean bean){
		Integer tipo4 = (Integer)bean.get(DYNA_PROPERTY_IDTIPO4);
		Integer tipoRelatorio = (Integer)bean.get(DYNA_PROPERTY_TIPO_RELATORIO);
		String nomeArquivo = (String)bean.get(DYNA_PROPERTY_NOMEARQUIVO);
		
		String retorno[] = new String[3];
		if (tipoRelatorio == null ||tipoRelatorio.intValue()==1 ){	
			if (tipo4!=null && tipo4==4)
			{
				if(nomeArquivo==null || nomeArquivo.equals("")) {	
					retorno[0] = "RelatorioImpressaoAgrupado";
					retorno[1] = LST_RELATORIO_IMPRESSAO_AGRUPADO; }

			}else if( tipoRelatorio==1)
				if(nomeArquivo==null || nomeArquivo.equals(""))
				{ retorno[0] = "RelatorioImpressao";
				retorno[1] = LST_RELATORIO_IMPRESSAO;
				} else{
					retorno[0] = "RelatorioImpressaoResumo";
					retorno[1] = LST_RELATORIO_IMPRESSAO_RESUMO; 
				}
			
		}	
		else if(tipoRelatorio == 2){
			if(nomeArquivo == null || nomeArquivo.equals("") ){
				retorno[0] = "RelatorioPostagem";
				retorno[1] = LST_RELATORIO_POSTAGEM;
			}else{
				retorno[0] = "RelatorioPostagemArquivo";
				retorno[1] = LST_RELATORIO_POSTAGEM_ARQUIVO;
			}
		}else if(tipoRelatorio == 3){
			retorno[0] = "RelatorioBancario";
			retorno[1] = LST_RELATORIO_BANCARIO;
		}
		
		if(retorno[0].equals("RelatorioImpressaoAgrupado"))//verificar
			retorno[2] = "/br/com/netservicos/novosms/emissao/report/design/RelatorioImpressaoAgrupado.jasper";
		else if(retorno[0].equals("RelatorioImpressao"))
			retorno[2] = "/br/com/netservicos/novosms/task/emissao/report/RelatorioImpressao.jasper";
		else if(retorno[0].equals("RelatorioImpressaoResumo"))
			retorno[2] = "/br/com/netservicos/novosms/task/emissao/report/RelatorioImpressaoResumo.jasper";
		else if(retorno[0].equals("RelatorioPostagem"))
			retorno[2] = "/br/com/netservicos/novosms/task/emissao/report/RelatorioPostagem.jasper";
		else if(retorno[0].equals("RelatorioPostagemArquivo"))
			retorno[2] = "/br/com/netservicos/novosms/task/emissao/report/RelatorioPostagemArquivo.jasper";
		else if(retorno[0].equals("RelatorioBancario"))
			retorno[2] = "/br/com/netservicos/novosms/task/emissao/report/RelatorioBancario.jasper";
							
		return retorno;
	}
	
	/**
	 *  Método que envia a pesquisa pra ser processado no ambiente de filas.... 
	 * @param DynamicBean
	 * 
	 * @ejb.interface-method view-type = "both"
	 */
	public DynamicBean sendReport2Queue(DynamicBean bean){
		/*Integer tipo4 = (Integer)bean.get(DYNA_PROPERTY_IDTIPO4);
		Integer tipoRelatorio = (Integer)bean.get(DYNA_PROPERTY_TIPO_RELATORIO);*/
		String cidContrato = (String)bean.get(DYNA_PROPERTY_CIDONTRATO);
		String dtInicioVencimento = (String)bean.get(DYNA_PROPERTY_DTINICIOVENCIMENTO);
		String dtFimVencimento = (String)bean.get(DYNA_PROPERTY_DTFIMVENCIMENTO);
		String nomeArquivo = (String)bean.get(DYNA_PROPERTY_NOMEARQUIVO);
		String idOrigemEmissao = (String)bean.get(DYNA_PROPERTY_IDORIGEMEMISSAO);
		String dtInicioEmissao = (String)bean.get(DYNA_PROPERTY_DTINICIOEMISSAO);
		String dtFimEmissao = (String)bean.get(DYNA_PROPERTY_DTFIMEMISSAO);
		Integer idTipoCobranca = (Integer)bean.get(DYNA_PROPERTY_IDTIPOCOBRANCA);
		Integer idTipoPostagem = (Integer)bean.get(DYNA_PROPERTY_IDTIPOPOSTAGEM);
		String contentType = (String)bean.get(BaseConstants.CONTENT_TYPE);
		
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put(REPORT_CID_CONTRATO,cidContrato != null && cidContrato.equals("-1")? null :cidContrato);
		parametros.put(REPORT_DT_INICIO,dtInicioVencimento);
		parametros.put(REPORT_DT_FIM,dtFimVencimento);
		parametros.put(REPORT_NM_ARQUIVO,nomeArquivo);
		parametros.put(REPORT_ID_ORIGEM_EMISSAO,idOrigemEmissao != null && idOrigemEmissao.equals("-1")? null : idOrigemEmissao );
		parametros.put(REPORT_DT_INICIO_EMISSAO,dtInicioEmissao);
		parametros.put(REPORT_DT_FIM_EMISSAO,dtFimEmissao);
		parametros.put(REPORT_ID_TIPO_COBRANCA,idTipoCobranca != null && idTipoCobranca != -1  ? idTipoCobranca.toString() : null);
		parametros.put(REPORT_ID_TIPO_POSTAGEM,idTipoPostagem != null && idTipoPostagem != -1 ? idTipoPostagem.toString() : null);
		parametros.put(REPORT_CONTENT_TYPE ,contentType);
		parametros.put(REPORT_REPORT_NAME,returnType(bean)[0]);
		parametros.put(REPORT_QUERY_NAME ,returnType(bean)[1]);
		parametros.put(REPORT_PATH ,returnType(bean)[2]);
		
		TaskService taskService = super.getService(TaskService.class);
		String currentDBService = super.getUserSession().getCurrentDbService();
		String currentDBIdentifier = currentDBService.substring(GeralConstants.PREFIXO_DATABASE_SERVICE.length());
		
		//Long idFila = (Long) super.getPrincipalProperties().get(TaskConstants.ID_FILA);
		//idFila = taskService.submitTaskToFila(TASK_REPORT_ARQUIVOS_EMITIDOS_VENCIMENTO, parametros, super.getUserSession().getUserId(), currentDBIdentifier, idFila, returnType(bean)[0]);
		//Long idFila = this.insertTask(parametros, TASK_REPORT_ARQUIVOS_EMITIDOS_VENCIMENTO, "Relatório: " + returnType(bean)[0]);
		Long idFila = (Long) super.getPrincipalProperties().get(TaskConstants.ID_FILA);
		Long idFilaInserida = taskService.submitTaskToFila(TASK_REPORT_ARQUIVOS_EMITIDOS_VENCIMENTO, parametros, super.getUserSession().getUserId(), currentDBIdentifier, idFila, "Relatório: " + returnType(bean)[0]);
		bean.set(DYNA_PROPERTY_ID_FILA_TASK, idFilaInserida);
		
		return bean;
	}
			
	/**
	 * @ejb.transaction type="Required"
	 * @ejb.interface-method view-type="both"
	 */
	public  <T> List<T> getDadosBeanByQuery( String queryName , Bean beanParametro , Boolean cache , Class<T> beanResult){
		return(List<T>) super.getDadosBeanByQuery(queryName,beanParametro , cache, beanResult);
	}
	
	private HashMap<String, Object>returnParam(DynamicBean bean)
	{
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		ResourceLoader resourceLoader = new ResourceLoader(REPORT_NTLOGO_PATH);
		
		String cidContrato = (String)bean.get(DYNA_PROPERTY_CIDONTRATO);
		String dtInicioVencimento = (String)bean.get(DYNA_PROPERTY_DTINICIOVENCIMENTO);;
		String dtFimVencimento = (String)bean.get(DYNA_PROPERTY_DTFIMVENCIMENTO);
		String nomeArquivo = (String)bean.get(DYNA_PROPERTY_NOMEARQUIVO);
		String idOrigemEmissao = (String)bean.get(DYNA_PROPERTY_IDORIGEMEMISSAO);
		String dtInicioEmissao = (String)bean.get(DYNA_PROPERTY_DTINICIOEMISSAO);
		String dtFimEmissao = (String)bean.get(DYNA_PROPERTY_DTFIMEMISSAO);
		Integer idTipoCobranca = (Integer)bean.get(DYNA_PROPERTY_IDTIPOCOBRANCA);
		Integer idTipoPostagem = (Integer)bean.get(DYNA_PROPERTY_IDTIPOPOSTAGEM);
		String contentType = (String)bean.get(BaseConstants.CONTENT_TYPE);
		
		parametros.put(REPORT_CID_CONTRATO,cidContrato != null && cidContrato.equals("-1")? null :cidContrato);
		parametros.put(REPORT_DT_INICIO,dtInicioVencimento);
		parametros.put(REPORT_DT_FIM,dtFimVencimento);
		parametros.put(REPORT_NM_ARQUIVO,nomeArquivo);
		parametros.put(REPORT_ID_ORIGEM_EMISSAO,idOrigemEmissao != null && idOrigemEmissao.equals("-1")? null : idOrigemEmissao );
		parametros.put(REPORT_DT_INICIO_EMISSAO,dtInicioEmissao);
		parametros.put(REPORT_DT_FIM_EMISSAO,dtFimEmissao);
		parametros.put(REPORT_ID_TIPO_COBRANCA,idTipoCobranca != null && idTipoCobranca != -1  ? idTipoCobranca : null);
		parametros.put(REPORT_ID_TIPO_POSTAGEM,idTipoPostagem != null && idTipoPostagem != -1 ? idTipoPostagem : null);
		parametros.put(REPORT_LOGO, resourceLoader.getStream());
		parametros.put(BaseConstants.CONTENT_TYPE, contentType);
		
		return parametros;
	}
	
	private DynamicBean returnFiltro(DynamicBean bean)
	{
		DynamicBean filtro = new DynamicBean();

		String cidContrato = (String)bean.get(DYNA_PROPERTY_CIDONTRATO);
		String dtInicioVencimento = (String)bean.get(DYNA_PROPERTY_DTINICIOVENCIMENTO);;
		String dtFimVencimento = (String)bean.get(DYNA_PROPERTY_DTFIMVENCIMENTO);
		String nomeArquivo = (String)bean.get(DYNA_PROPERTY_NOMEARQUIVO);
		String idOrigemEmissao = (String)bean.get(DYNA_PROPERTY_IDORIGEMEMISSAO);
		String dtInicioEmissao = (String)bean.get(DYNA_PROPERTY_DTINICIOEMISSAO);
		String dtFimEmissao = (String)bean.get(DYNA_PROPERTY_DTFIMEMISSAO);
		Integer idTipoCobranca = (Integer)bean.get(DYNA_PROPERTY_IDTIPOCOBRANCA);
		Integer idTipoPostagem = (Integer)bean.get(DYNA_PROPERTY_IDTIPOPOSTAGEM);
		
		
		
		filtro.set(RelatArqEmitidosVenctoService.REPORT_CID_CONTRATO,cidContrato == null || cidContrato.equals("") ? "-1" : cidContrato);
		filtro.set(RelatArqEmitidosVenctoService.REPORT_DT_INICIO,dtInicioVencimento == null || dtInicioVencimento.equals("") ? "-1" : dtInicioVencimento);
		filtro.set(RelatArqEmitidosVenctoService.REPORT_DT_FIM,dtFimVencimento == null || dtFimVencimento.equals("") ? "-1" : dtFimVencimento);
		filtro.set(RelatArqEmitidosVenctoService.REPORT_NM_ARQUIVO,nomeArquivo == null || nomeArquivo.equals("") ? "-1" : nomeArquivo);
		filtro.set(RelatArqEmitidosVenctoService.REPORT_ID_ORIGEM_EMISSAO,idOrigemEmissao == null || idOrigemEmissao.equals("") ? "-1" : idOrigemEmissao);
		filtro.set(RelatArqEmitidosVenctoService.REPORT_DT_INICIO_EMISSAO,dtInicioEmissao == null || dtInicioEmissao.equals("")? "-1" : dtInicioEmissao);
		filtro.set(RelatArqEmitidosVenctoService.REPORT_DT_FIM_EMISSAO,dtFimEmissao == null || dtFimEmissao.equals("")? "-1" : dtFimEmissao);
		filtro.set(RelatArqEmitidosVenctoService.REPORT_ID_TIPO_COBRANCA,idTipoCobranca == null || idTipoCobranca.equals("") ? -1 : idTipoCobranca);
		filtro.set(RelatArqEmitidosVenctoService.REPORT_ID_TIPO_POSTAGEM,idTipoPostagem == null || idTipoPostagem.equals("") ? -1 : idTipoPostagem);
		
		
		return filtro;
	}
	
	/**
	 * @ejb.transaction type="Required"
	 * @ejb.interface-method view-type="both"
	 */
	public int retrieveTamanho(DynamicBean bean) {
		
		if (queryResult==null)	
		{
			return 0;
		}
		else 
		return queryResult.size();
		
    }
	

}

