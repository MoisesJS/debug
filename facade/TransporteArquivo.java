package br.com.netservicos.novosms.emissao.facade;


import java.util.List;

import br.com.netservicos.emissao.bean.ArquivoRemessaTransporteDTO;
import br.com.netservicos.framework.core.facade.Service;

public interface TransporteArquivo extends Service{
	
	
	
	/**
	 * @number RF190
	 * @param transportesArquivoDTO
	 * @param idFila TODO
	 * @throws JNetException
	 * @since 1.20 RF190
	 */
	public void transportarArquivos(List<ArquivoRemessaTransporteDTO> transportesArquivoDTO, Integer idFila);

	
	
	
}
