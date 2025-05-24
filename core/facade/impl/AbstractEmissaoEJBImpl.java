/**
 * Created on 17/08/2007
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
 * $Id: AbstractEmissaoEJBImpl.java,v 1.1 2008/01/17 16:19:37 jucenali Exp $
 */
package br.com.netservicos.novosms.emissao.core.facade.impl;

import static br.com.netservicos.novosms.emissao.resources.EmissaoResources.BUNDLE_KEY;
import br.com.netservicos.framework.core.facade.hibernate.AbstractSimpleCadMessageEJBImpl;
/**
 * <P><B>Description :</B><BR>
 * 	TODO descrever
 * </P>
 * <P>
 * <B>
 * Issues : <BR>
 * None
 * </B>
 * @author Robin Michael Gray
 * @since 17/08/2007
 * @ejb.bean
 *   name="AbstractEmissaoEJB"
 *   display-name="AbstractEmissaoEJB"
 *   generate="false"
 *
 * @ejb.interface local-extends="javax.ejb.EJBLocalObject"
 *                extends="javax.ejb.EJBObject"
 * 
 * @ejb.home local-extends="javax.ejb.EJBLocalHome" extends="javax.ejb.EJBHome"   
 */
public abstract class AbstractEmissaoEJBImpl extends
		AbstractSimpleCadMessageEJBImpl {

	/**
	 * 
	 * @see br.com.netservicos.framework.core.facade.hibernate.AbstractSimpleCadMessageEJBImpl#getResourceBundle()
	 */
	@Override
	protected String getResourceBundle() {
		return BUNDLE_KEY;
	}

}
