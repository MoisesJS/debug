package br.com.netservicos.novosms.emissao.core.facade.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.com.netservicos.novosms.emissao.core.dao.ArquivoPrintHouseDAO;
import br.com.netservicos.novosms.emissao.printhouse.LinhasLeiauteKit;

abstract class AbstractFaturaPrintHouseBuilder implements FaturaPrintHouseBuilder{

	protected final ArquivoPrintHouseDAO	dao;
	protected final LayoutArquivoPrintHouseUtil layoutUtil;
	protected final DadosArquivoPrintHouseUtil dadosUtil;

	protected Log logger = LogFactory.getLog(AbstractFaturaPrintHouseBuilder.class);

	protected LinhasLeiauteKit llk;
	
	public AbstractFaturaPrintHouseBuilder(ArquivoPrintHouseDAO dao){
		this.dao = dao;
		this.layoutUtil = new LayoutArquivoPrintHouseUtil();
		this.dadosUtil = new DadosArquivoPrintHouseUtil();

		this.llk = new LinhasLeiauteKit();
	}

	protected Object notNullObject(Object dado){
		return dadosUtil.notNullObject(dado);
	}

	protected String notNullString(String dado){
		return dadosUtil.notNullString(dado);
	}

	protected String notEqualsNullString(String dado){
		return dadosUtil.notEqualsNullString(dado);
	}

	protected Object notNullObjectEspaco(Object dado){
		return dadosUtil.notNullObject(dado);
	}

	protected String notNullStringEspaco(String dado){
		return dadosUtil.notNullString(dado);
	}

	protected String valor(Double valor, Integer tamanho, Integer decimais){
		return dadosUtil.valor(valor, tamanho, decimais);
	}

	protected String filler(Integer tamanho, char ch){
		return dadosUtil.filler(tamanho, ch);
	}
}
