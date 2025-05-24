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
 * $Id: FaturaManualAction.java,v 1.2 2008/05/06 18:20:45 jucenali Exp $
 **/
package br.com.netservicos.novosms.emissao.faturamanual.web.struts.action;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import br.com.netservicos.framework.core.bean.Bean;
import br.com.netservicos.framework.core.bean.DynamicBean;
import br.com.netservicos.framework.util.BaseConstants;
import br.com.netservicos.framework.web.struts.action.SimpleCadAction;
import br.com.netservicos.novosms.emissao.core.facade.EmissaoService;
import br.com.netservicos.novosms.web.util.DatabaseUtil;
 
/**
 * <P><B>Description :</B><BR>
 * 	TODO descrever
 * </P>
 * <P>
 * <B>
 * Issues : <BR>
 * None
 * </B>
 * @author Everson dos Santos
 * @since 23/07/2007
 */
public class FaturaManualAction extends SimpleCadAction {

	//Constantes referentes a parâmetros da request 
	private static final String DYNA_PROPERTY_DB_SERVICE = "dbService";
	private static final String DYNA_PROPERTY_FILTRO_CARTAO = "filtroCartao";
	private static final String DYNA_PROPERTY_LST_LOTE = "lstLote";
	private static final String DYNA_PROPERTY_LST_BANCO = "lstSnBanco";
	private static final String DYNA_PROPERTY_LST_OPERADORA_CARTARO = "lstSnOperadoraCartao";
	private static final String DYNA_PROPERTY_MAP_BASES = "mapBases";
	private static final String DYNA_PROPERTY_LST_CIDADE_OPERADORA = "lstSnCidadeOperadora";
	private static final String DYNA_PROPERTY_LST_CRITERIO = "lstCriterioSegmentacao";
	private static final String DYNA_PROPERTY_LST_TRANSPORTE_ARQUIVO = "lstTransporteArquivo";
	private static final String DYNA_PROPERTY_QTDE_TASK = "qtdeTask";
	private static final String DYNA_PROPERTY_ID_FILA_TASK = "idFilaInserida";

	private static final String ACTION_OBTER_CIDADE_OPERADORA = "obterCidadeOperadora";
	private static final String ACTION_OBTER_CRITERIO = "obterCriterio";	
	private static final String ACTION_PERSISTE_LOTE = "persisteLote";
	private static final String ACTION_PERSISTE_CRITERIO = "persisteCriterio";	
	private static final String ACTION_FATURA_CNAB_SEARCH = "faturaCnabSearch";
	private static final String ACTION_LOTE_CNAB_SEARCH = "loteCnabSearch";
	private static final String ACTION_PERSISTE_LOTE_CNAB = "persisteLoteCnab";
	private static final String ACTION_OBTER_BANCO = "obterBanco";
	private static final String ACTION_FATURA_MENSSAGENS_SEARCH = "faturaMensagensSearch";
	private static final String ACTION_FATURA_ONLINE_SEARCH = "faturaOnlineSearch";
	private static final String ACTION_LOTE_ONLINE_SEARCH = "loteOnlineSearch";
	private static final String ACTION_PERSISTE_LOTE_ONLINE = "persisteLoteOnline";

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

		DynamicBean dynaBean = (DynamicBean) this.getWebBean(form);
		
		Map<String, String> mapBases = DatabaseUtil.getDatabaseDescriptions(request);
		
		request.setAttribute(DYNA_PROPERTY_MAP_BASES, mapBases);
		request.setAttribute(DYNA_PROPERTY_DB_SERVICE, this.getUserInfo(request).getCurrentDbService());
		
		this.setRequest(request, dynaBean);
		
		return super.prepareSearch(mapping, form, request, response);
		
	}
	
	/**
	 * Obtem as cidades operadoras para a base selecionada na combo da pagina
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward obterCidadeOperadora(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
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
			
			// Guarda o dbService atual
			String dbServiceAtual = this.getUserInfo(request).getCurrentDbService();
			
			// Seta o dbService da base selecionada na tela
			this.getUserInfo(request).setCurrentDbService(dbService);
			
			// Busca as cidades operadoras do dbService selecionado na tela
			lstCidadeOperadora = (Collection) this.getDelegate().getDelegateMethod(
					BaseConstants.SERVICE_SEARCH,
					new Class[] { String.class, Bean.class },
					new Object[] { "lstSnCidadeOperadora", new DynamicBean() },
					this.getUserInfo(request), true, true).execute();

			// Volta o dbService atual
			this.getUserInfo(request).setCurrentDbService(dbServiceAtual);

		}

		request.setAttribute(DYNA_PROPERTY_LST_CIDADE_OPERADORA, lstCidadeOperadora);
		this.setRequest(request, dynaBean);

		//return mapping.findForward(BaseConstants.PREFIX_SUCCESS + ACTION_OBTER_CIDADE_OPERADORA);
		return mapping.findForward(BaseConstants.PREFIX_SUCCESS + ACTION_OBTER_CIDADE_OPERADORA);

	}
	
	/**
	 * Obtem as cidades operadoras para a base selecionada na combo da pagina
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward obterCriterio(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		DynamicBean dynaBean = (DynamicBean) this.getWebBean(form);
		
		Collection lstCidadeOperadora = null;
		String dbService = (String)dynaBean.get(DYNA_PROPERTY_DB_SERVICE);
		
		if (dbService == null) {
			
			lstCidadeOperadora = (Collection)this.getDelegate().getDelegateMethod(
					BaseConstants.SERVICE_SEARCH_UNION,
					new Class[] { String.class, Bean.class },
					new Object[] { "lstCriterioSegmentacao", dynaBean },
					this.getUserInfo(request), true, true).execute();
			
		} else {
			
			// Guarda o dbService atual
			String dbServiceAtual = this.getUserInfo(request).getCurrentDbService();
			
			// Seta o dbService da base selecionada na tela
			this.getUserInfo(request).setCurrentDbService(dbService);
			
			// Busca as cidades operadoras do dbService selecionado na tela
			lstCidadeOperadora = (Collection) this.getDelegate().getDelegateMethod(
					BaseConstants.SERVICE_SEARCH,
					new Class[] { String.class, Bean.class },
					new Object[] { "lstCriterioSegmentacao", new DynamicBean() },
					this.getUserInfo(request), true, true).execute();

			// Volta o dbService atual
			this.getUserInfo(request).setCurrentDbService(dbServiceAtual);

		}

		request.setAttribute(DYNA_PROPERTY_LST_CRITERIO, lstCidadeOperadora);
		this.setRequest(request, dynaBean);

		//return mapping.findForward(BaseConstants.PREFIX_SUCCESS + ACTION_OBTER_CIDADE_OPERADORA);
		return mapping.findForward(BaseConstants.PREFIX_SUCCESS + ACTION_OBTER_CRITERIO);

	}


	/**
	 * Obtem os lotes para as cidades operadoras selecionadas na lista da pagina
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@Override
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		DynamicBean dynaBean = (DynamicBean) this.getWebBean(form);
		
		// Guarda o dbService atual
		String dbServiceAtual = this.getUserInfo(request).getCurrentDbService();

		// Muda o dbService para a base selecionada
		String dbService = request.getParameter(DYNA_PROPERTY_DB_SERVICE);
		this.getUserInfo(request).setCurrentDbService(dbService);
		
		EmissaoService service = this.getService(EmissaoService.class, request, true);				
		dynaBean = service.buscarLotesParaEscolha(dynaBean);		
		
		// Volta o dbService atual
		this.getUserInfo(request).setCurrentDbService(dbServiceAtual);

		this.setRequest(request, dynaBean);
		
		return mapping.findForward(BaseConstants.PREFIX_SUCCESS + ACTION_SEARCH);
		
	}
	
	/**
	 * 
	 * @since 26/07/2007
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward persisteLote(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		DynamicBean dynaBean = (DynamicBean) this.getWebBean(form);
		
		// Guarda o dbService atual
		String dbServiceAtual = this.getUserInfo(request).getCurrentDbService();

		// Muda o dbService para a base selecionada
		String dbService = request.getParameter(DYNA_PROPERTY_DB_SERVICE);
		this.getUserInfo(request).setCurrentDbService(dbService);
		
		EmissaoService service = this.getService(EmissaoService.class, request, true);				
		dynaBean = service.persisteLotes(dynaBean);		
		
		// Volta o dbService atual
		this.getUserInfo(request).setCurrentDbService(dbServiceAtual);

		this.setRequest(request, dynaBean);
		
		return mapping.findForward(BaseConstants.PREFIX_SUCCESS + ACTION_PERSISTE_LOTE);

	}
	
	
	/**
	 * 
	 * @since 26/07/2007
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward persisteCriterio(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		
		DynamicBean dynaBean = (DynamicBean) this.getWebBean(form);
		// Guarda o dbService atual
		String dbServiceAtual = this.getUserInfo(request).getCurrentDbService();

		// Muda o dbService para a base selecionada
		String dbService = request.getParameter(DYNA_PROPERTY_DB_SERVICE);
		this.getUserInfo(request).setCurrentDbService(dbService);
		
		EmissaoService service = this.getService(EmissaoService.class, request, true);				
		dynaBean = service.persisteCriterios(dynaBean);		
		
		// Volta o dbService atual
		this.getUserInfo(request).setCurrentDbService(dbServiceAtual);

		this.setRequest(request, dynaBean);
		
		return mapping.findForward(BaseConstants.PREFIX_SUCCESS + ACTION_PERSISTE_CRITERIO);

	}


	/**
	 * Obtém as bases para a página inicial de seleção de lotes cnab
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward faturaCnabSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DynamicBean dynaBean = (DynamicBean) this.getWebBean(form);
		
		Map<String, String> mapBases = DatabaseUtil.getDatabaseDescriptions(request);
		
		request.setAttribute(DYNA_PROPERTY_MAP_BASES, mapBases);
		request.setAttribute(DYNA_PROPERTY_DB_SERVICE, this.getUserInfo(request).getCurrentDbService());
		
		this.setRequest(request, dynaBean);
		
		return mapping.findForward(BaseConstants.PREFIX_SUCCESS + ACTION_FATURA_CNAB_SEARCH);
		
	}
	
	
	/**
	 * Obtém as bases para a página inicial de selecao de lotes online
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward faturaOnlineSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DynamicBean dynaBean = (DynamicBean) this.getWebBean(form);
		
		Map<String, String> mapBases = DatabaseUtil.getDatabaseDescriptions(request);
		
		request.setAttribute(DYNA_PROPERTY_MAP_BASES, mapBases);
		request.setAttribute(DYNA_PROPERTY_DB_SERVICE, this.getUserInfo(request).getCurrentDbService());
		
		this.setRequest(request, dynaBean);
		
		return mapping.findForward(BaseConstants.PREFIX_SUCCESS + ACTION_FATURA_ONLINE_SEARCH);
		
	}
	
	/**
	 * Obtém os lotes online para as cidades operadoras e banco selecionados na lista da pagina
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward loteOnlineSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		DynamicBean dynaBean = (DynamicBean) this.getWebBean(form);
		
		// Guarda o dbService atual
		String dbServiceAtual = this.getUserInfo(request).getCurrentDbService();

		// Muda o dbService para a base selecionada
		String dbService = request.getParameter(DYNA_PROPERTY_DB_SERVICE);
		this.getUserInfo(request).setCurrentDbService(dbService);
		
		EmissaoService service = this.getService(EmissaoService.class, request, true);				
		dynaBean = service.buscarLotesCartaoOnline(dynaBean);	//	
		
		// Volta o dbService atual
		this.getUserInfo(request).setCurrentDbService(dbServiceAtual);

		this.setRequest(request, dynaBean);
		
		return mapping.findForward(BaseConstants.PREFIX_SUCCESS + ACTION_LOTE_ONLINE_SEARCH);
		
	}
	
	
	/**
	 * Obtém os bancos para popular a lista
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward obterBanco(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		DynamicBean dynaBean = (DynamicBean) this.getWebBean(form);
		
		// Guarda o dbService atual
		String dbServiceAtual = this.getUserInfo(request).getCurrentDbService();

		String dbService = request.getParameter(DYNA_PROPERTY_DB_SERVICE);
		
		String filtroCartao = request.getParameter(DYNA_PROPERTY_FILTRO_CARTAO);
		
		// Muda o dbService para a base selecionada
		this.getUserInfo(request).setCurrentDbService(dbService);									
		
		EmissaoService service = this.getService(EmissaoService.class, request, true);			
		
		if(filtroCartao != null && Boolean.parseBoolean(filtroCartao)){
													
				dynaBean = service.obterOperadoraCartao(dynaBean);
				
		} else {
				
				dynaBean = service.obterBanco(dynaBean);	
		}
						
		
		// Volta o dbService atual
		this.getUserInfo(request).setCurrentDbService(dbServiceAtual);

		this.setRequest(request, dynaBean);
		
		return mapping.findForward(BaseConstants.PREFIX_SUCCESS + ACTION_OBTER_BANCO);

	}

	/**
	 * Obtem os lotes cnab para as cidades operadoras e banco selecionados na lista da pagina
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward loteCnabSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		DynamicBean dynaBean = (DynamicBean) this.getWebBean(form);
		
		// Guarda o dbService atual
		String dbServiceAtual = this.getUserInfo(request).getCurrentDbService();

		// Muda o dbService para a base selecionada
		String dbService = request.getParameter(DYNA_PROPERTY_DB_SERVICE);
		this.getUserInfo(request).setCurrentDbService(dbService);
		
		EmissaoService service = this.getService(EmissaoService.class, request, true);				
		dynaBean = service.buscarLotesCnab(dynaBean);		
		
		// Volta o dbService atual
		this.getUserInfo(request).setCurrentDbService(dbServiceAtual);

		this.setRequest(request, dynaBean);
		
		return mapping.findForward(BaseConstants.PREFIX_SUCCESS + ACTION_LOTE_CNAB_SEARCH);
		
	}
	
	/**
	 * Obtem os lotes cnab para as cidades operadoras e banco selecionados na lista da pagina
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward faturaMensagensSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		DynamicBean dynaBean = (DynamicBean) this.getWebBean(form);
		
		Map<String, String> mapBases = DatabaseUtil.getDatabaseDescriptions(request);
		
		request.setAttribute(DYNA_PROPERTY_MAP_BASES, mapBases);
		request.setAttribute(DYNA_PROPERTY_DB_SERVICE, this.getUserInfo(request).getCurrentDbService());
		
		this.setRequest(request, dynaBean);
		
		return mapping.findForward(BaseConstants.PREFIX_SUCCESS + ACTION_FATURA_MENSSAGENS_SEARCH);
		
	}
	
	/**
	 * 
	 * @since 26/07/2007
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward persisteLoteCnab(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		DynamicBean dynaBean = (DynamicBean) this.getWebBean(form);
		
		// Guarda o dbService atual
		String dbServiceAtual = this.getUserInfo(request).getCurrentDbService();

		// Muda o dbService para a base selecionada
		String dbService = request.getParameter(DYNA_PROPERTY_DB_SERVICE);
		this.getUserInfo(request).setCurrentDbService(dbService);
		
		EmissaoService service = this.getService(EmissaoService.class, request, true);				
		dynaBean = service.persisteLotesCnab(dynaBean);		
		
		// Volta o dbService atual
		this.getUserInfo(request).setCurrentDbService(dbServiceAtual);

		this.setRequest(request, dynaBean);
		
		return mapping.findForward(BaseConstants.PREFIX_SUCCESS + ACTION_PERSISTE_LOTE_CNAB);

	}
	
	
	/**
	 * 
	 * @since 26/10/2021
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward persisteLoteOnline(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		DynamicBean dynaBean = (DynamicBean) this.getWebBean(form);
		
		// Guarda o dbService atual
		String dbServiceAtual = this.getUserInfo(request).getCurrentDbService();

		// Muda o dbService para a base selecionada
		String dbService = request.getParameter(DYNA_PROPERTY_DB_SERVICE);
		this.getUserInfo(request).setCurrentDbService(dbService);
		
		EmissaoService service = this.getService(EmissaoService.class, request, true);	
		
		dynaBean = service.persisteLotesBaixaOnline(dynaBean);		
		
		// Volta o dbService atual
		this.getUserInfo(request).setCurrentDbService(dbServiceAtual);

		this.setRequest(request, dynaBean);
		
		return mapping.findForward(BaseConstants.PREFIX_SUCCESS + ACTION_PERSISTE_LOTE_ONLINE);

	}
	
	

	/**
	 * Atribui campos para a request 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private void setRequest(HttpServletRequest request, DynamicBean dynaBean) {
		
		request.setAttribute(DYNA_PROPERTY_LST_LOTE, dynaBean.get(DYNA_PROPERTY_LST_LOTE));
		request.setAttribute(DYNA_PROPERTY_LST_BANCO, dynaBean.get(DYNA_PROPERTY_LST_BANCO));
		request.setAttribute(DYNA_PROPERTY_LST_OPERADORA_CARTARO, dynaBean.get(DYNA_PROPERTY_LST_OPERADORA_CARTARO));
		request.setAttribute(DYNA_PROPERTY_LST_TRANSPORTE_ARQUIVO, dynaBean.get(DYNA_PROPERTY_LST_TRANSPORTE_ARQUIVO));
		request.setAttribute(DYNA_PROPERTY_QTDE_TASK, dynaBean.get(DYNA_PROPERTY_QTDE_TASK));
		request.setAttribute(DYNA_PROPERTY_ID_FILA_TASK, dynaBean.get(DYNA_PROPERTY_ID_FILA_TASK));
		
	}
	
}
