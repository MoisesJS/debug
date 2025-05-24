package br.com.netservicos.novosms.emissao.core.facade.impl;

import br.com.netservicos.novosms.emissao.dto.ArquivoPrintHouseDTO;

final class CarregadorBoletoPrintFactory{
	
	private CarregadorBoletoPrintFactory(){ }
	
	public static CarregadorBoletoPrint getInstance(ArquivoPrintHouseDTO dto){
		return new CarregadorBoletoPrintImpl();
	}

}
