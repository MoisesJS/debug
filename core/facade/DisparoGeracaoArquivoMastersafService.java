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
 * $Id: DisparoGeracaoArquivoMastersafService.java,v 1.1 2008/01/17 16:19:37 jucenali Exp $
 **/
package br.com.netservicos.novosms.emissao.core.facade;

import java.util.ArrayList;

import br.com.netservicos.core.bean.sn.SnCobrancaNotaFiscalBean;
import br.com.netservicos.core.bean.sn.SnProdutoBean;
import br.com.netservicos.framework.core.bean.DynamicBean;
import br.com.netservicos.framework.core.facade.Service;

/**
 * <P>
 * <B>Description :</B><BR>
 * Interface do EJB DisparoGeracaoArquivoMastersafEJBImpl
 * </P>
 * <P>
 * <B> Issues : <BR>
 * None </B>
 * 
 * @author Everson dos Santos
 * @since 23/07/2007
 */
public interface DisparoGeracaoArquivoMastersafService extends Service {

	/**
	 * Dispara a geração com TASK
	 * @since 04/09/2007
	 * @param dynamicBean
	 * @return
	 */
	public boolean dispararArquivos(DynamicBean dynamicBean);
	
	/**
	 * Dispara a geraçaão sem TASK
	 * @since 06/09/2007
	 * @param dynamicBean
	 * @return
	 */
	public void gerarArquivos(DynamicBean dynamicBean);

}
