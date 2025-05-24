package br.com.netservicos.novosms.emissao.core.facade;

import br.com.netservicos.framework.core.bean.DynamicBean;
import br.com.netservicos.framework.core.facade.Service;

public interface TotalizadorArquivosQuitacaoDebitoService extends Service {

	
	/**
	 * @param dynaBean
	 * @return
	 */
	DynamicBean gerarArquivoTotalizadorLotes(final DynamicBean dynaBean);

	/**
	 * @param dynaBean
	 */
	void enviarArquivoTotalizadorLotes(final DynamicBean dynaBean);
}
