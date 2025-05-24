package br.com.netservicos.novosms.emissao.web.struts.action;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import br.com.netservicos.framework.core.bean.Bean;
import br.com.netservicos.framework.core.bean.DynamicBean;
import br.com.netservicos.framework.util.BaseConstants;
import br.com.netservicos.framework.util.exception.BaseFailureException;
import br.com.netservicos.framework.web.struts.action.SimpleCadAction;
import br.com.netservicos.novosms.geral.constants.GeralConstants;
 
/**
 * <P><B>Description : Classe que disponibiliza os status das faturas enviadas por email.</B><BR></P>
 * <B>
 * Issues : <BR>
 * None
 * </B>
 * @author Leonar Edson Inoue
 * @since 03/09/2009
 */
public class RelatorioFaturasPorEmailAction extends SimpleCadAction {

	//Constantes referentes a parâmetros da request 
	private static final String DYNA_PROPERTY_DB_SERVICE = "dbService";
	private static final String DYNA_PROPERTY_LST_CIDADE_OPERADORA = "lstSnCidadeOperadora";
	private static final String DYNA_PROPERTY_DATA_ATUAL = "data";
	
	private static final String DATA_INICIAL = "dataInicial";
	private static final String DATA_FINAL = "dataFinal";
	private static final String LST_CIDCONTRATO = "lstCidContrato";
	private static final String STATUS_RESOLUCAO = "statusResolucao";
	
	
	/**
	 * Obtem as bases de dados das operadoras para listar na combo da pagina
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@Override
	public ActionForward prepareSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		setarCidadeOperadora(request, form);
		request.setAttribute(DYNA_PROPERTY_DATA_ATUAL, new SimpleDateFormat(GeralConstants.FORMATO_DATA_DD_MM_YYY).format(new Date()));
		
		return super.prepareSearch(mapping, form, request, response);
	}
	
	@Override
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynamicBean dynaBean = (DynamicBean) this.getWebBean(form);

		Collection<String> colCidContrato = this.getCollectionFromStringArray((String)dynaBean.get("lstCidContrato"), String.class, null);
		dynaBean.put("lstCidContratoArray", colCidContrato);
		
		setForm(request, form);
		return super.search(mapping, form, request, response);
	}
	
	/**
	 * Obtem as cidades operadoras para a base corrente. Como o usuário sempre loga em apenas uma base, pega o dbService corrente.
	 * @param request
	 * @param form
	 * @return void
	 */
	public void setarCidadeOperadora(HttpServletRequest request, ActionForm form){ 
		DynamicBean dynaBean = (DynamicBean) this.getWebBean(form);
		
		Collection lstCidadeOperadora = null;
		String dbService = (String)dynaBean.get(DYNA_PROPERTY_DB_SERVICE);
		
		if (dbService == null) {
			lstCidadeOperadora = (Collection)this.getDelegate().getDelegateMethod(
					BaseConstants.SERVICE_SEARCH_UNION,
					new Class[] { String.class, Bean.class },
					new Object[] { "lstSnCidadeOperadora", dynaBean },
					this.getUserInfo(request), true, true).execute();
			
		} else {
			lstCidadeOperadora = (Collection) this.getDelegate().getDelegateMethod(
					BaseConstants.SERVICE_SEARCH,
					new Class[] { String.class, Bean.class },
					new Object[] { "lstSnCidadeOperadora", new DynamicBean() },
					this.getUserInfo(request), true, true).execute();
		}

		request.setAttribute(DYNA_PROPERTY_LST_CIDADE_OPERADORA, lstCidadeOperadora);
		request.setAttribute(DYNA_PROPERTY_DB_SERVICE, dbService);
	}
	
	public void setForm(HttpServletRequest request, ActionForm form){
		DynamicBean dynaBean = (DynamicBean) this.getWebBean(form);
		
		request.setAttribute(DATA_INICIAL, (String)dynaBean.get(DATA_INICIAL));
		request.setAttribute(DATA_FINAL, (String)dynaBean.get(DATA_FINAL));
		request.setAttribute(LST_CIDCONTRATO, (String)dynaBean.get(LST_CIDCONTRATO));
		request.setAttribute(STATUS_RESOLUCAO, (String)dynaBean.get(STATUS_RESOLUCAO));
	}
	
	@Override
	protected void processResponseReport(HttpServletResponse response,	HttpServletRequest request, Map reportData, String contentType) {
		BufferedOutputStream ouputStream = null;
		ByteArrayOutputStream byteArrayStream = null;
		try {
			if(contentType==null)
				contentType = (String)reportData.get(BaseConstants.CONTENT_TYPE);
			response.reset();
			response.setContentType(contentType);
		
			response.setHeader("content-type", contentType);
			response.setHeader("Content-Type", contentType);
			String reportName = (String) reportData.get(REPORT_NAME);
			
			if (reportName != null) {
				response.setHeader("Content-Disposition", "attachment; filename=" + reportName);
				response.setHeader("content-disposition", "attachment; filename=" + reportName);
			}
			
			if (reportData.containsKey(REPORT_IMAGES)) {
				Map images = (Map) reportData.get(REPORT_IMAGES);
				request.getSession().setAttribute(REPORT_IMAGES, images);
			}
				
			byte[] report = (byte[]) reportData.get(REPORT_BYTES);
		
			ouputStream = new BufferedOutputStream(response.getOutputStream());
		
			byteArrayStream = new ByteArrayOutputStream();
			byteArrayStream.write(report);
		
			response.setContentLength(byteArrayStream.size());
			byteArrayStream.writeTo(response.getOutputStream());
		
			byteArrayStream.flush();
		
			ouputStream.flush();
			} 
		catch (IOException e) {
			throw new BaseFailureException(e);
		}finally{
			try {
				if(byteArrayStream!=null){
					byteArrayStream.close();
				}
				if(ouputStream!=null){
					ouputStream.close();
				}
			} catch (IOException e) {
			throw new BaseFailureException(e);
			}
		}
	}
	
	@Override
	public ActionForward generateReport(ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		DynamicBean dynaBean = (DynamicBean) this.getWebBean(form);

		Collection<String> colCidContrato = this.getCollectionFromStringArray((String)dynaBean.get("lstCidContrato"), String.class, null);
		dynaBean.put("lstCidContratoArray", colCidContrato);
		
		return super.generateReport(mapping, form, request, response);
	}	
	private Collection getCollectionFromStringArray(String stringWithPipe, Class classType, Integer group) {
		
		Collection col = null;

		if(stringWithPipe != null && !stringWithPipe.equals("")) {
			
			col = new ArrayList();
			
			String[] stringArray = stringWithPipe.split("\\|");
			
			if (stringArray != null && stringArray.length > 0) {
				
				for (int i = 0; i < stringArray.length; i++) {
					
					String content = null;
					if (group != null) {
						String[] groupArray = stringArray[i].split("\\_");
						if (Integer.parseInt(groupArray[0]) == group) {
							content = groupArray[1];
						}
					}
					else {
						content = stringArray[i];
					}
					
					if (content != null) {
						if(classType.equals(Integer.class)) {
							col.add(new Integer(content));
						}
						else {
							col.add(content);
						}
					}
					
				}
				
			}
			
		}
		
		return col;

	}
}
