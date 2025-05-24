package br.com.netservicos.novosms.emissao.core.facade.impl;

import br.com.netservicos.novosms.emissao.core.dao.ArquivoPrintHouseDAO;
import br.com.netservicos.novosms.emissao.dto.ArquivoPrintHouseDTO;
import br.com.netservicos.novosms.emissao.dto.LoteBoletoFaturaDTO;

class EventoCargaBoletoPrint{
	
	private final LoteBoletoFaturaDTO[] boletos;
	
	private final ArquivoPrintHouseDTO dto;
	
	private final ArquivoPrintHouseDAO dao;
	private final String criterio;

	public EventoCargaBoletoPrint(LoteBoletoFaturaDTO[] boletos,
                                  ArquivoPrintHouseDTO dto,
                                  ArquivoPrintHouseDAO dao,
                                  String criterio){
	    super();
	    
	    this.boletos = boletos;
	    this.dto = dto;
	    this.dao = dao;
	    this.criterio = criterio;
    }
	
	public ArquivoPrintHouseDTO getDto(){
	    return dto;
    }

	public LoteBoletoFaturaDTO[] getBoletos(){
	    return boletos;
    }
	
	public ArquivoPrintHouseDAO getDao(){
	    return dao;
    }
	
	public String getCriterio(){
	    return criterio;
    }

}
