/** Project : NovoSMSWeb
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
 * $Id: DisparoGeracaoArquivoMastersafAction.java,v 1.1 2008/01/17 16:46:58 jucenali Exp $
 * $Id: DisparoGeracaoArquivoMastersafAction.java,v 1.1 2008/01/17 16:46:58 jucenali Exp $
 * $Id: DisparoGeracaoArquivoMastersafAction.java,v 1.1 2008/01/17 16:46:58 jucenali Exp $
 **/
package br.com.netservicos.novosms.emissao.mastersaf.web.struts.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import br.com.netservicos.core.bean.sn.SnCidadeOperadoraBean;
import br.com.netservicos.core.bean.sn.SnConfiguracaoArquivoBean;
import br.com.netservicos.framework.core.bean.DynamicBean;
import br.com.netservicos.framework.file.reader.impl.FileMapperServiceEJBImpl;
import br.com.netservicos.framework.service.delegate.DelegateMethod;
import br.com.netservicos.framework.util.BaseConstants;
import br.com.netservicos.framework.util.attachments.messages.AttachmentMessageLevel;
import br.com.netservicos.framework.util.attachments.messages.BasicAttachmentMessage;
import br.com.netservicos.framework.util.exception.BaseFailureException;
import br.com.netservicos.framework.web.struts.action.SimpleCadAction;
import br.com.netservicos.novosms.emissao.core.facade.DisparoGeracaoArquivoMastersafService;
import br.com.netservicos.novosms.emissao.exception.EmissaoBusinessResourceException;
import br.com.netservicos.novosms.geral.core.facade.ConfiguracaoArquivoService;
 
/**
 * <P>
 * <B>Description :</B><BR>
 * Action que recebe os valores de operadora e competência para geracao do arquivo mastersaf da RF77.
 * </P>
 * <P>
 * <B> Issues : <BR>
 * None </B>
 * 
 * @author Everson dos Santos
 * @since 23/07/2007
 */
public class DisparoGeracaoArquivoMastersafAction extends SimpleCadAction {

	/**
	 * 
	 */
	private final ResourceBundle bundle = ResourceBundle
			.getBundle("br.com.netservicos.novosms.emissao.resources.EmissaoResourcesMessages");

	/**
	 * 
	 */
	private final Log log = LogFactory.getLog(FileMapperServiceEJBImpl.class);
	
	/**
	 * 
	 */
	private static final String FORWARD_SUCCESS = "success_gerarArquivos";
	
	/**
	 * 
	 */
	private static final String FORWARD_ERROR = "error_gerarArquivos";

	/**
	 * @author Everson dos Santos
	 * @since 26/07/2007
	 * @param mapping mapping
	 * @param form form
	 * @param request request
	 * @param response response
	 * @return nada
	 * @throws Exception
	 */
	public ActionForward gerarArquivos(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		StringBuffer operadoraIncluida = new StringBuffer();
		//StringBuffer operadoraRemovida = new StringBuffer();
		DynamicBean dynamicBean = null;
		try {
			String[] idEmpresa = {};
			String mesRef = "";
			try {
				idEmpresa = request.getParameterValues("idEmpresa");
				mesRef = request.getParameter("mesRef");
			} catch (NullPointerException n) {
				this.executaLog("error.uc.mastersaf.disparo.form", n);
				ActionMessages error = new ActionMessages();
				error.add("idEmpresa", new ActionMessage("uc.mastersaf.label.disparo.operadoraobrigatorio"));
				error.add("mesRefString", new ActionMessage("uc.mastersaf.label.disparo.mesobrigatorio"));
				saveErrors(request, error);
				return mapping.findForward(FORWARD_SUCCESS);
			}
			
			// Pega dados parametrizados //netsms.emissao.mastersaf.arquivoparametrizado
			ConfiguracaoArquivoService arquivoService = getService(ConfiguracaoArquivoService.class, request, true);
			SnConfiguracaoArquivoBean arquivoBean = arquivoService.selecionarConfiguracaoArquivo("MSAF"); 
			if (arquivoBean == null) {
				this.executaLog("error.uc.mastersaf.disparo.parametrosbase", new NullPointerException());
				throw new EmissaoBusinessResourceException("error.uc.mastersaf.disparo.parametrosbase",
						new NullPointerException());
			}
			
			// Laço com as operadoras selecionadas
			for (String id : idEmpresa) {
				dynamicBean = new DynamicBean();
				dynamicBean.set("idEmpresa", id);
				dynamicBean.set("mesRef", this.stringToDate(mesRef));
				dynamicBean.set("ccAnoMesEmissao", this.inverteMesRef(mesRef));

				/**
				DelegateMethod method = getDelegate().getDelegateMethod(BaseConstants.SERVICE_SEARCH,
						new Object[] { "lstFechamentoMensalByMesReferencia", 
						dynamicBean }, 
						getUserInfo(request));

				//Verifica se operadora seleciona possui fechamento mensal
				List listaFechamentoMensal = (ArrayList) method.execute();
				**/

				// Pega o cidContrato e descrição
				DelegateMethod m = getDelegate().getDelegateMethod(BaseConstants.SERVICE_SEARCH,
						new Object[] { "lstSnCidadeOperadoraByIdEmpresa", dynamicBean }, getUserInfo(request));

				List < SnCidadeOperadoraBean > listaCidadeOperadora = (ArrayList < SnCidadeOperadoraBean >) m.execute();

				for (SnCidadeOperadoraBean cidade : listaCidadeOperadora) {
					//if (listaFechamentoMensal.isEmpty()) {
					//	operadoraRemovida.append(cidade.getCiNome());
					//	operadoraRemovida.append(",");
					//} else {
					dynamicBean.set("cidContrato", cidade.getCidContrato());
					dynamicBean.set("codOperadora", cidade.getCodOperadora());
					dynamicBean.set("localDestinoArquivo", 
							arquivoBean.getLocalOrigemArquivo().getDescricaoLocal());
					
					operadoraIncluida.append(cidade.getCiNome());
					operadoraIncluida.append(",");
					DisparoGeracaoArquivoMastersafService service = getService(
							DisparoGeracaoArquivoMastersafService.class, request, true);
					
					/** EXECUTA COM TASK **/
					//boolean disparo = true; // SEM TASK
					boolean disparo = service.dispararArquivos(dynamicBean);
					if (!disparo) {
						request.setAttribute("isGerados", Boolean.FALSE);
						request.setAttribute("erroOperadoraIncluida", 
								this.removeVirgulaOperadora(operadoraIncluida.toString()));
						return mapping.findForward(FORWARD_ERROR);
					}
					
					/** EXECUTA SEM TASK  **/
					//service.gerarArquivos(dynamicBean);
					//}
				}
			}
			request.setAttribute("isGerados", Boolean.TRUE);	
			request.setAttribute("operadoraIncluida", this.removeVirgulaOperadora(operadoraIncluida.toString()));
			//request.setAttribute("operadoraRemovida", this.removeVirgulaOperadora(operadoraRemovida.toString()));

		} catch (BaseFailureException b) {
			this.executaLog("error.uc.mastersaf.disparo.geracao", b);
			throw b;
		} catch (ParseException p) {
			this.executaLog("error.uc.mastersaf.disparo.parsedata", p);
			throw new BaseFailureException("Erro ao converter data", p);
		} catch (Exception e) {
			this.executaLog("error.uc.mastersaf.disparo.geracao", e);
			throw new BaseFailureException(e);
		}
		return mapping.findForward(FORWARD_SUCCESS);
	}

	/**
	 * Adiciona Exception no Log.
	 * 
	 * @since 20/08/2007
	 * @param key chave do bundle
	 * @param throwable excessão
	 */
	private void executaLog(final String key, final Throwable throwable) {
		try {
			log.error(new BasicAttachmentMessage(bundle.getString(key) + throwable.getMessage(),
					AttachmentMessageLevel.ERROR));
		} catch (Exception e) {
			throw new BaseFailureException("error.uc.mastersaf.disparo.log", e);
		}
	}

	/**
	 * Converte String formatada para data dd/mm/yyyy.
	 * 
	 * @since 08/08/2007
	 * @param data data
	 * @return retorna uma string convertida para date dd/mm/yyyy
	 * @throws ParseException lança excessão
	 */
	private Date stringToDate(final String data) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat();
		dateFormat.applyPattern("dd/MM/yyyy");
		return dateFormat.parse(data);
	}

	/**
	 * Retira a última virgula.
	 * @author Everson dos Santos
	 * @since 13/08/2007
	 * @param valor operadoras
	 * @return retorna a última operadora sem vírgula
	 */
	private String removeVirgulaOperadora(final String valor) {
		return valor.replaceFirst("[\\,]$", "");
	}
	
	/**
	 * Inverte a data para yyyymm.
	 * @since 15/09/2007
	 * @param mesref mes de referencia
	 * @return retorna data  yyyymm.
	 */
	private String inverteMesRef(final String mesref) {
		String[] data = mesref.split("/");
		return data[2] + data[1];
	}
}
