/**
 * Created on 29/06/2007
 * Project : NETBaixa
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
 * $Id: EmissaoBusinessResourceException.java,v 1.1 2008/01/17 16:19:43 jucenali Exp $
 */
package br.com.netservicos.novosms.emissao.exception;

import static br.com.netservicos.novosms.emissao.resources.EmissaoResources.BUNDLE_KEY;

import java.util.Collection;

import br.com.netservicos.framework.util.exception.BaseBusinessResourcesException;

/**
 * <P><B>Description :</B><BR>
 * 	Exception to be used for all business exceptions of the module NETEmissao
 * </P>
 * <P>
 * <B>
 * Issues : <BR>
 * None
 * </B>
 * @author Robin Michael Gray
 * @since 29/06/2007
 */
public class EmissaoBusinessResourceException extends BaseBusinessResourcesException {

	/**
	 * Serial version
	 */
	private static final long serialVersionUID = -2594127950127821011L;

	/**
	 * @since 29/06/2007
	 */
	public EmissaoBusinessResourceException() {
		super(BUNDLE_KEY);
	}

	/**
	 * @since 29/06/2007
	 * @param errorTrace
	 */
	public EmissaoBusinessResourceException(String errorTrace) {
		super(errorTrace, BUNDLE_KEY);
	}

	/**
	 * @since 29/06/2007
	 * @param errorKeys
	 */
	public EmissaoBusinessResourceException(Collection<String> errorKeys) {
		super(errorKeys, BUNDLE_KEY);
	}

	/**
	 * @since 29/06/2007
	 * @param cause
	 * @param errorTrace
	 */
	public EmissaoBusinessResourceException(Throwable cause, String errorTrace) {
		super(cause, errorTrace, BUNDLE_KEY);		
	}

	/**
	 * @since 29/06/2007
	 * @param errorKey
	 * @param cause
	 */
	public EmissaoBusinessResourceException(String errorKey, Throwable cause) {
		super(errorKey, cause, BUNDLE_KEY);
	}

	/**
	 * @since 29/06/2007
	 * @param errorKey
	 * @param errorParameters
	 * @param cause
	 */
	public EmissaoBusinessResourceException(String errorKey,
			Object[] errorParameters, Throwable cause) {
		super(errorKey, errorParameters, cause, BUNDLE_KEY);
	}
	
	/**
	 * @since 29/06/2007
	 * @param errorKey
	 * @param errorParameters
	 */
	public EmissaoBusinessResourceException(String errorKey,
			Object[] errorParameters) {
		super(errorKey, errorParameters, BUNDLE_KEY);
	}

}
