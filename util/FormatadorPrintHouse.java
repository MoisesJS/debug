/* Generated by Together */

package br.com.netservicos.novosms.emissao.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import br.com.netservicos.fatura.util.StringUtils;
import br.com.netservicos.framework.file.exceptions.ArgumentNullException;
import br.com.netservicos.framework.file.exceptions.InvalidDataException;
import br.com.netservicos.framework.file.formater.StandardFormater;
import br.com.netservicos.framework.util.exception.BaseFailureException;
import bsh.StringUtil;


/**
 * Classe que oferece as regras de formata��o para os dados que comp�em 
 * arquivos enviados para EMBRATEL (qualquer leiaute).
 * 
 * @version 1.0 (28/01/2006) - cria��o
 * @author Clayton F. S. Marques
 *
 */
public class  FormatadorPrintHouse extends StandardFormater {
		
    private static final String TIPO_MES_REF = "MES_REF";

    private static final String TIPO_ALIQUOTA = "ALIQUOTA";
    
    private static final String FORMATO_DATA = "DATA";

    
	/* (non-Javadoc)
	 * @see br.com.netservicos.sms.util.formatadores.FormatadorPadrao#formatar(java.lang.StringBuffer, java.lang.Object, java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	public String formatar(StringBuffer linhaGerado, Object dadoQueSeraFormatado, Integer tamanho, Integer decimais, String tipo) throws BaseFailureException, ArgumentNullException {
		if (tipo.equals(TIPO_MES_REF) ) {
			return this.mesReferencia(dadoQueSeraFormatado, tamanho);
		} else if (tipo.equals(TIPO_ALIQUOTA)) {
			return this.aliquota(dadoQueSeraFormatado, tamanho);
		} else if (tipo.equals(FORMATO_DATA)){
			if( dadoQueSeraFormatado instanceof String ) {
				return this.formataData( dadoQueSeraFormatado, tipo );
			}else if(dadoQueSeraFormatado instanceof Date){
				return super.data((Date) dadoQueSeraFormatado, tipo);
			}
		} else {
			return super.formatar(linhaGerado, dadoQueSeraFormatado, tamanho, decimais,	tipo);
		}
		return tipo;
	}

	protected String hora(Date data) {
		
		SimpleDateFormat horaDateFormat = new SimpleDateFormat( "HH:mm:ss" );		
    	return horaDateFormat.format( data );
	}
	
    public String valor(Double valor, Integer tamanho, Integer decimais){
    	NumberFormat numberFormat = NumberFormat.getInstance(new Locale("pt", "BR"));
    	String valorConstante;
    	int size = tamanho.intValue();
    	StringBuffer buffer = new StringBuffer( size );
    	
    	numberFormat.setMaximumFractionDigits( decimais.intValue() );
    	numberFormat.setMinimumFractionDigits(decimais.intValue());
    	numberFormat.setGroupingUsed(false);
    	valorConstante = numberFormat.format( valor.doubleValue() );
		
		if( size > valorConstante.length() ){
			buffer.append( filler( new Integer( size - valorConstante.length() ), ' ' ) );
			buffer.append( valorConstante );
		}else{
			buffer.append( valorConstante.substring( 0, size ) );
		}
		return buffer.toString();
    }
    
    /**
     * Retorna uma String com o dado recebido alinhado a esquerda.<br>
     * @semantics <br>
     * - Preenche com espa�os a direita at� atingir o tamanho informado como parametro.<br>
     * @param paramRecebido 
     * @param tamanho
     */
    protected String alfanumerico(String paramRecebido, Integer tamanho){
		
    	return constante( paramRecebido, tamanho );
    }
    
    
    public String mesReferencia(Object paramRecebido, Integer tamanho) throws BaseFailureException {
    	
    	if (paramRecebido == null || (paramRecebido instanceof String && ((String)paramRecebido).trim().equals("")) ) {
    		return constante( "", tamanho );
    	} else {
	    	try {
	    		SimpleDateFormat monthDateFormat = new SimpleDateFormat("MMMM/yyyy", new Locale("pt", "BR"));	    		    
	    		SimpleDateFormat yearMonthFormat = new SimpleDateFormat("yyyyMM");
	    		
	    		Date dateYearMonth = yearMonthFormat.parse((String)paramRecebido);
				String monthString = monthDateFormat.format(dateYearMonth);
				
				return constante( monthString, tamanho );
			} catch (ParseException e) {
				throw new BaseFailureException(e);
			}
    	}
    }

	/**
	 * @param dadoQueSeraFormatado
	 * @param tamanho
	 * @return
	 */
	private String aliquota(Object dadoQueSeraFormatado, Integer tamanho) {
		
		if (dadoQueSeraFormatado == null) {
			return constante("", tamanho );
		}
		
		double valueDbl = 0;
		if (dadoQueSeraFormatado instanceof String) {
			
			try {
				String value = StringUtils.replace((String)dadoQueSeraFormatado, ",", ".");
				valueDbl = Double.valueOf(value).doubleValue();
			} catch (NumberFormatException e) {
				return constante("", tamanho );
			}
			
		} else if (dadoQueSeraFormatado instanceof BigDecimal) {
			valueDbl = ((BigDecimal)dadoQueSeraFormatado).doubleValue();
		}

		valueDbl = valueDbl / 100;
		DecimalFormat aliquotaDecimalFormat = new DecimalFormat("0.00%");
		
		return constante( aliquotaDecimalFormat.format(valueDbl), tamanho );
		
	}
	
	/**
	 * M�todo sobrescrito da classe Formater para formata��o de data com situa��es
	 * espec�ficas do m�dulo de emiss�o. O m�todo original n�o trata, por exemplo
	 * quando o dado recebido � vazio ou nulo
	 * @author Carlos Augusto Leite
	 */
	private String formataData( Object dadoQueSeraFormatado, String tipo ){

		System.out.println("Data recebida ---> " + (String) dadoQueSeraFormatado );

//		try{
		  
			if( !org.apache.commons.lang.StringUtils.isBlank((String) dadoQueSeraFormatado) && dadoQueSeraFormatado != null && !(dadoQueSeraFormatado.equals(""))){
				SimpleDateFormat dataFormatada = new SimpleDateFormat( "dd/MM/yyyy" );
				return dataFormatada.format( new Date((String) dadoQueSeraFormatado));
			}else
				return "";
//		}catch(Exception e){
//			throw new InvalidDataException();
//		}
	}
	
	protected String constante(String valorConstante, Integer tamanho) {
		
		int size = tamanho.intValue();
		
		if(!org.apache.commons.lang.StringUtils.isBlank(valorConstante)){
			StringBuffer buffer = new StringBuffer( size );
		
			if( size > valorConstante.length() ){
				buffer.append( valorConstante );
				buffer.append( filler( new Integer( size - valorConstante.length() ) ) );
			}else{
				buffer.append( valorConstante.substring( 0, size ) );
			}			
			return buffer.toString();
		}else{
			return " ";			
		}
	}


}