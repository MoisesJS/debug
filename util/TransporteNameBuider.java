/**
 * 
 */
package br.com.netservicos.novosms.emissao.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.netservicos.framework.transporte.name.NameBuilder;

/**
 * @author mbellini
 *
 */
public class TransporteNameBuider implements NameBuilder {

	private static final String _ENVIADO_ = "_enviado_";
	private String uniqueKey;
	private SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
	
	
	/**
	 * 
	 * @since 31/05/2007
	 * @param uniqueKey a chave unica para identificar o arquivo - irá compor a parte string da hhmmss - que não poderá variar 
	 * durante as várias chamadas desse método durante o processo
	 */
	public TransporteNameBuider( String uniqueKey ){
		
		this.uniqueKey = uniqueKey;
	}
	

	 
	/* (non-Javadoc)
	 */
	public String getOriginName(String baseName) {
		String nome = baseName;
		String ext = "";
		if(nome.length() >= 4) {
			nome = baseName.substring(0, baseName.length() - 4);  //retira a extensão
			ext = baseName.substring(baseName.length() - 4,baseName.length());
		}
		
		return ( new StringBuffer() ).append( nome ).append( 
				_ENVIADO_ ).append( format.format(new Date()) ).append(uniqueKey).append( 
						ext ).toString();
	}

	/* (non-Javadoc)
	 * @see br.com.netservicos.framework.transporte.name.NameBuilder#getTempName(java.lang.String)
	 */
	public String getTempName(String baseName) {
		return getOriginName(baseName);
	}

}
