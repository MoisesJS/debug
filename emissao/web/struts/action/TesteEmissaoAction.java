package br.com.netservicos.novosms.emissao.web.struts.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import br.com.netservicos.framework.task.service.TaskService;
import br.com.netservicos.framework.web.struts.action.SimpleCadAction;
import br.com.netservicos.novosms.emissao.core.facade.EmissaoService;
import br.com.netservicos.novosms.emissao.core.facade.TransporteArquivoService;
import br.com.netservicos.novosms.geral.constants.GeralConstants;

public class TesteEmissaoAction extends SimpleCadAction {

	public ActionForward testaEmissao(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {  
		
		TransporteArquivoService transporte = (TransporteArquivoService) getService(TransporteArquivoService.class,request,  true);
		
		
		String idControleArquivo = (String) request.getParameter("idControleArquivo");
		String cidContrato = (String) request.getParameter("cidContrato");
		String idFila = (String) request.getParameter("idFila");
		
		transporte.efetuaUpload(new Long(idControleArquivo), cidContrato, new Long(idFila));

		
		return null;
		
	}
	
	public ActionForward defaultAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		EmissaoService emissao = getService(EmissaoService.class, request, true);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");		
		String idOperadora  = null;		
		String idLote = "324";	
		String tipoLote      = "EMISPRT";
		
		List<String> lotes = new ArrayList<String>();
		lotes.add(idLote); 
		emissao.iniciarPrintHousePorLotes(idOperadora, lotes , tipoLote); 
		
		return null;
		
	}	
	
	public void insereFila(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		TaskService taskService = super.getService(TaskService.class,request,true);
		
		String idControleArquivo = (String) request.getParameter("idControleArquivo");
		String cidContrato = (String) request.getParameter("cidContrato");
		
		HashMap<String,String> parameters = new HashMap<String, String>();
		parameters.put("idControleArquivo", idControleArquivo);
		parameters.put("cidContrato", cidContrato);
		
		String currentDBIdentifier = getUserInfo(request).getCurrentDbService().substring(GeralConstants.PREFIXO_DATABASE_SERVICE.length());
		
		                                                                                              
		taskService.submitTaskToFila("EMISSAO_TRANSPORTE", parameters, getUserInfo(request).getUserId(), currentDBIdentifier);

		
		
	}
	

	
}