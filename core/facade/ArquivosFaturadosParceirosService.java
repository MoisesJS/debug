package br.com.netservicos.novosms.emissao.core.facade;

import br.com.netservicos.framework.core.bean.Bean;
import br.com.netservicos.framework.core.facade.Service;

public interface ArquivosFaturadosParceirosService extends Service{
	public void gerarArquivo(Bean dados);
}
