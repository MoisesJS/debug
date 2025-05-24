package br.com.netservicos.novosms.emissao.core.facade.impl;

import java.util.concurrent.Callable;

import br.com.netservicos.core.bean.sn.SnLoteBean;
import br.com.netservicos.novosms.emissao.core.facade.EmissaoService;

public class LoteBoletoAvulsoCallable implements Callable<SnLoteBean>{

	EmissaoService	         emissaoService;
	SnLoteBean	             lote;

	public LoteBoletoAvulsoCallable(EmissaoService service, SnLoteBean lote){
		this.emissaoService = service;
		this.lote = lote;
	}

	public SnLoteBean call() throws Exception{
		
		emissaoService.processaLoteBoletoAvulso(lote);
		return lote;
	}

}
