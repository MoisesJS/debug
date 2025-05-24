package br.com.netservicos.novosms.emissao.core.facade.impl;


import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import br.com.netservicos.framework.core.bean.Bean;




public class EmissaoBaseServiceImpl extends AbstractEmissaoEJBImpl  {  
		

	/**
	 * 
	 */
	private static final long serialVersionUID = 1131083830068047663L;
	
	
	
	protected   <T> List<T> getDadosBean( List<Map> dadosMap , Class<T> beanResult)  {
	
		List lista = new ArrayList();
		try{
			Class<T> beanGeneric = (Class<T>) Class.forName(beanResult.getName());		
			for(Map item : dadosMap){		
					T bean = beanGeneric.newInstance();
					BeanUtils.copyProperties(bean, item);
					lista.add(bean);					
			}
		}catch(IllegalAccessException e){
		   e.printStackTrace();
	    }catch(InvocationTargetException e ){
	    	e.printStackTrace();
	    }catch(InstantiationException e ){
	    	e.printStackTrace();
	    } catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	
	    return lista;
	
	}
	
	
	protected  <T> T getDadosBean( Map dadosMap , Class<T> beanResult)  {	
		
		T beanRetorno = null;		
		try{
			Class<T> beanGeneric = (Class<T>) Class.forName(beanResult.getName());					
			T bean = beanGeneric.newInstance();
			BeanUtils.copyProperties(bean, dadosMap);
			beanRetorno = bean;		
		}catch(IllegalAccessException e){
		   e.printStackTrace();
	    }catch(InvocationTargetException e ){
	    	e.printStackTrace();
	    }catch(InstantiationException e ){
	    	e.printStackTrace();
	    } catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return beanRetorno;
	
	    
	
	}
	
	
	protected  <T> List<T> getDadosBeanByQuery( String queryName , Bean beanParametro , Boolean cache , Class<T> beanResult)  {	
				
		List<Map> dadosMap = (List<Map>) super.search(queryName, beanParametro, cache);		
		return this.getDadosBean(dadosMap, beanResult);
		
	 
	 
	 
	}	
	
	
	protected  <T> T getDadosBeanbyQuery( String queryName , Bean beanParametro , Boolean cache , Class<T> beanResult)  {	
				
		//List<Map> dadosMap = (List<Map>) getCurrentDAO().select(queryName, beanParametro, cache);
		
		List<Map> dadosMap = (List<Map>) super.search(queryName, beanParametro, cache);		
		
		T bean = null; 
		
		if(dadosMap != null && dadosMap.size() > 0){
			bean = this.getDadosBean(dadosMap.get(0), beanResult);
		}				
		return bean;
	
	}	
	
	
	protected  String getDadosBeanbyQuery( String queryName , Bean beanParametro , Boolean cache )  {	
		
		//List<Map> dadosMap = (List<Map>) getCurrentDAO().select(queryName, beanParametro, cache);
		
		String formPagto = null;
		
		List<Map> dadosMap = (List<Map>) super.search(queryName, beanParametro, cache);		
	
		if(dadosMap != null && !dadosMap.isEmpty()){
			formPagto = (String) dadosMap.get(0).get("formpagto");
		}
		
		return formPagto;
	
	}	
	
	
	
	
	
}