package br.com.netservicos.novosms.emissao.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.netservicos.novosms.emissao.constants.PrintHouseConstants;

public class TesteString {

	
	
	public static void main (String args[] ){
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		String dataini =  dateFormat.format(new Date()).substring(0,5);			
		String datafim =  dateFormat.format(new Date()).substring(5,10);		
		
		System.out.println(" DATA INI-->"+dataini);
		System.out.println(" DATA FIM-->"+datafim);
		
	}
	
}
