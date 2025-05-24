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
* $$Id: RelatArqEmitidosVenctoAction.java,v 1.2 2010/03/23 17:40:35 jucenali Exp $$
*/
package br.com.netservicos.novosms.emissao.web.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import br.com.netservicos.framework.core.bean.DynamicBean;
import br.com.netservicos.framework.util.BaseConstants;
import br.com.netservicos.framework.web.struts.action.SimpleCadAction;
import br.com.netservicos.novosms.emissao.core.facade.RelatArqEmitidosVenctoService;

/**
 * <P>
 * <B>Description :</B><BR>
 * Action do Relátorio de arquivos emitidos por vencimento
 * </P>
 * <P>
 * <B> Issues : <BR>
 * None </B>
 * 
 * @author Cleber Luis Simm
 * @since 10/08/2007
 */

public class RelatArqEmitidosVenctoAction extends SimpleCadAction {
	
	
	private static final String FORWARD_SUCCESS_GERA_RELATORIO = "success_gera_relatorio";
	private static final String DYNA_PROPERTY_QTDE_TASK = "qtdeTask";
	private static final String DYNA_PROPERTY_ID_FILA_TASK = "idFilaInserida";
	private static final String ACTION_GERA_RELATORIO = "gerarRelatorios";
	
	/**
	 * 
	 * @since 16/10/2007
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 
	 * Gera o relátorios localmente
	 */
	public ActionForward enviarFila(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		DynamicBean bean = (DynamicBean)getWebBean(form);
		// Guarda o dbService atual
		String dbServiceAtual = this.getUserInfo(request).getCurrentDbService();
		
		RelatArqEmitidosVenctoService arqEmitidosVenctoService = getService(RelatArqEmitidosVenctoService.class, request, true);
		
		bean = arqEmitidosVenctoService.sendReport2Queue(bean);		
		
		// Volta o dbService atual
		this.getUserInfo(request).setCurrentDbService(dbServiceAtual);

		request.setAttribute(DYNA_PROPERTY_ID_FILA_TASK, bean.get(DYNA_PROPERTY_ID_FILA_TASK));
		//addRequestMessage(request, "FILA GERADA: " + bean.get(DYNA_PROPERTY_ID_FILA_TASK));
			   
		return mapping.findForward(BaseConstants.PREFIX_SUCCESS + "submitQueue");
	}

}