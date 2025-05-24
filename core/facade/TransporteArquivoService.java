package br.com.netservicos.novosms.emissao.core.facade;

import java.util.List;

import br.com.netservicos.core.bean.sn.SnControleArquivoBean;
import br.com.netservicos.emissao.bean.TransporteArquivos;
import br.com.netservicos.emissao.bean.TransporteDTO;
import br.com.netservicos.framework.core.facade.Service;
import br.com.netservicos.framework.transporte.exception.TransporteArquivoException;

public interface TransporteArquivoService extends Service {

	public void transportarArquivosUpload(
			List<TransporteArquivos> transportesArquivos) throws TransporteArquivoException ;
	
	public String transportarArquivoUpload(
			TransporteDTO origem, TransporteDTO destino, 
			String pathListener ) throws TransporteArquivoException ;

	public void efetuaUpload(Long idControleArquivo, String cidContrato, Long idFila) 
		throws TransporteArquivoException;		
	
	public void gravaStatus( SnControleArquivoBean controle, String status);
	
	public void gravaErroStatus(String erro, SnControleArquivoBean controle);

}
