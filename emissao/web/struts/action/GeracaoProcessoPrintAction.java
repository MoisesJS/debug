package br.com.netservicos.novosms.emissao.web.struts.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import br.com.netservicos.framework.web.struts.action.SimpleCadAction;
import br.com.netservicos.novosms.emissao.core.facade.ArquivoPrintHouseService;
import br.com.netservicos.novosms.emissao.core.facade.EmissaoService;
import br.com.netservicos.novosms.emissao.dto.FaturaArquivoDTO;

public class GeracaoProcessoPrintAction extends SimpleCadAction {
	
	/**
	 * 
	 * @param mapping
	 * @param form 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ActionForward defaultAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		EmissaoService emissao = getService(EmissaoService.class, request, true);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");		
		String idOperadora  = null;		
		//Brasil
		String idLote = "176"; 	
		//String idLote2 = "186"; Segunda Via
		//String idLote3 = "429";
		//String idLote4 = "430";
		//BH
		//String idLote = "156";
		
		
		
		

		String tipoLote      = "EMISPRT";
		//String tipoLote      = "SEGVIA";
		//String tipoLote      = "CPNF";
		
		List<String> lotes = new ArrayList<String>();
		lotes.add(idLote);	
		emissao.iniciarPrintHousePorLotes(idOperadora, lotes , tipoLote); 
		
		return null;
		
	}
	
	
	public ActionForward segundaViaAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		EmissaoService emissao = getService(EmissaoService.class, request, true);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");		
		String idOperadora  = null;		
		Long idBoleto = new Long(46643024);
		
		
		FaturaArquivoDTO fatura = emissao.gerarFaturaSegundaVia(idBoleto);
				
		return null;
		
	}
	public ActionForward testarDAO(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		getService(ArquivoPrintHouseService.class, request, true).testarDAO(null);

		return null;

	}
}
