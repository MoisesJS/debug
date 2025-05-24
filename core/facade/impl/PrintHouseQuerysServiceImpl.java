package br.com.netservicos.novosms.emissao.core.facade.impl;


import java.util.List;

import br.com.netservicos.framework.core.bean.DomainBean;




public class PrintHouseQuerysServiceImpl<T>  extends EmissaoBaseServiceImpl  {  
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1131083830068047663L; 
	
	private List<T> selecionarPrefixo; 

	public <T>  List<T> getSelecionarPrefixo(DomainBean beanParametro, Class<T> beanResult) {
		
		return (List<T>) super.getDadosBeanByQuery("selecionarPrefixo", beanParametro, false, beanResult);		 
		
	}		
	
}
