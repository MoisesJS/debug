package br.com.netservicos.novosms.emissao.facade.impl;


import java.util.List;

import br.com.netservicos.emissao.bean.ArquivoRemessaTransporteDTO;
import br.com.netservicos.emissao.bean.TransporteDTO;
import br.com.netservicos.framework.transporte.BaseTransporteArquivoTransacao;
import br.com.netservicos.framework.transporte.exception.TransporteArquivoException;
import br.com.netservicos.framework.transporte.filter.TransporteArquivoFiltro;
import br.com.netservicos.framework.transporte.listener.TransporteArquivoListener;
import br.com.netservicos.framework.transporte.name.DefaultNameBuilder;
import br.com.netservicos.novosms.emissao.core.facade.impl.AbstractEmissaoEJBImpl;
import br.com.netservicos.novosms.emissao.facade.TransporteArquivo;

	public class TransporteArquivoImpl extends AbstractEmissaoEJBImpl implements TransporteArquivo {
	
	
	


	/**
	 * 
	 */
	private static final long serialVersionUID = 4844312864422840556L;

	/**
	 * @number RF190
	 * @param transportesArquivoDTO
	 * @param idFila TODO
	 * @since 1.20 RF190
	 */
	public void transportarArquivos(List<ArquivoRemessaTransporteDTO> transportesArquivoDTO, Integer idFila){
				
		BaseTransporteArquivoTransacao transporteArquivo = new BaseTransporteArquivoTransacao();
		
		
		for(ArquivoRemessaTransporteDTO arquivo : transportesArquivoDTO) {

		}
		
	}

	

	
		/**
		 * Método responsável por copiar o(s) arquivo(s) no contexto local, para outro servidor. <br>
		 * @param origem Contém as informações do arquivo no contexto local
		 * @param destino Contém as informações do arquivo fora do contexto local
		 * @param transporteArquivoEvento o ouvinte para os eventos de erro e para o evento de sucesso na transferencia. 
		 * @throws TransporteArquivoException 
		 * @throws JNetException
		 * @see {@link br.com.netservicos.sms.util.transporte.TransporteArquivoTransacao#transportarArquivoUpload(TransporteDTO, TransporteDTO, TransporteArquivoListener)}
		 */
		public void transportarArquivoUpload(
				TransporteDTO origem, TransporteDTO destino, 
				TransporteArquivoListener transporteArquivoEvento, TransporteArquivoFiltro filter ) throws TransporteArquivoException {
				
			BaseTransporteArquivoTransacao transporteArquivo = new BaseTransporteArquivoTransacao();
			


			DefaultNameBuilder nameBuilder = new DefaultNameBuilder(origem.getIdFila().toString());
			Short porta = new Short( destino.getPorta() );
			
			transporteArquivo.transportarArquivoUpload(destino.getProtocolo(), destino.getServidor(),  
					porta, destino.getUsuario(), destino.getSenha(), destino.getDiretorio(), 
					origem.getDiretorio(), origem.getNomeArquivo(), destino.getNomeArquivo(), 
					nameBuilder, destino.getBinario().booleanValue(),
					filter, transporteArquivoEvento);	

		}		

		
		
}
