/**
 * Created on 06/12/2007
 * Project : NETEmissao
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
 * $Id: DisparoGeracaoArquivoSafxService.java,v 1.2 2008/09/23 00:03:47 jucenali Exp $
 */
package br.com.netservicos.novosms.emissao.core.facade;

import java.io.File;
import java.util.Map;

import br.com.netservicos.framework.core.bean.DynamicBean;
import br.com.netservicos.framework.core.facade.Service;
import br.com.netservicos.framework.file.writer.NoLayoutGenerateFile;

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
 * @since 06/12/2007
 */
public interface DisparoGeracaoArquivoSafxService extends Service {

	
	void gerarLinhaSAFX042(Map hashMap, DynamicBean bean, DynamicBean dynamicBean, NoLayoutGenerateFile noFormatFile);
	
	void gerarLinhaSAFX04(Map hashMap, DynamicBean bean, DynamicBean dynamicBean, NoLayoutGenerateFile noFormatFile);
	
	void gerarLinhaSAFX43(Map hashMap, DynamicBean bean, DynamicBean dynamicBean, NoLayoutGenerateFile noFormatFile);
	
	void gerarLinhaSAFX2013(Map hashMap, DynamicBean bean, DynamicBean dynamicBean, NoLayoutGenerateFile noFormatFile);

	
	File arquivoDiretorio(final DynamicBean dynamicBean, final String aa); 
}
