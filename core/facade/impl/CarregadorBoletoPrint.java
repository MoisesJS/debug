package br.com.netservicos.novosms.emissao.core.facade.impl;

import java.util.List;

import br.com.netservicos.novosms.emissao.printhouse.FaturaNetDTO;

public interface CarregadorBoletoPrint{
	
	public List<FaturaNetDTO> carrega(EventoCargaBoletoPrint evento) throws Exception;

}
