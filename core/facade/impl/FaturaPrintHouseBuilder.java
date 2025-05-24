package br.com.netservicos.novosms.emissao.core.facade.impl;

import br.com.netservicos.core.bean.sn.SnLoteBean;
import br.com.netservicos.novosms.emissao.dto.FaturaDTO;
import br.com.netservicos.novosms.emissao.printhouse.FaturaNetDTO;

public interface FaturaPrintHouseBuilder{

	public FaturaDTO gerarFatura(FaturaNetDTO fatura, SnLoteBean loteCorrente);
}
