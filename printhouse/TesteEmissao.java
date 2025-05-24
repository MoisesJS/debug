package br.com.netservicos.novosms.emissao.printhouse;

import br.com.netservicos.novosms.emissao.util.StringUtils;

public class TesteEmissao {

	
	public static void main ( String args[]){
		
		
		int linhasPorPagina = 80;
		Double result = (( double )82) / ((double) (linhasPorPagina));
		int sobra = Math.abs(( 82 % linhasPorPagina ));
		
		System.out.println(" RESTO ====>"+sobra);
		
	}
	
	private static String converteParaHoras(Double arg0) {

		int horas = (int) (arg0 / 60);		
		int minutos = (int) (arg0 - (horas * 60));
		double mod = (double) Math.abs(( arg0 % 60 )) - ((int) Math.abs( arg0 % 60 ));
		
		int segundos = (int) (mod * 60) ;
		
		System.out.println(" RESULTADO HORAS --> "+horas);
		System.out.println(" RESULTADO MINUTOS--> "+minutos);
		System.out.println(" RESULTADO SEGUNDOS--> "+segundos);
		System.out.println(" RESULTADO MODULO--> "+mod);
		
		String horasFormatada = StringUtils.prepad(String.valueOf(horas),2,'0');
		String minutosFormatado = StringUtils.prepad(String.valueOf(minutos),2,'0');
		String segundosFormatado = StringUtils.prepad(String.valueOf(segundos),2,'0');
		

		String horaMinutoFormatada = horasFormatada + "h" + minutosFormatado+"m"+segundosFormatado+"s";

		return horaMinutoFormatada;

	}
}
