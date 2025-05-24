package br.com.netservicos.novosms.emissao.core.facade;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import br.com.netservicos.core.bean.sn.SnBoletoBean;
import br.com.netservicos.core.bean.sn.SnCidadeOperadoraBean;
import br.com.netservicos.core.bean.sn.SnControleArquivoBean;
import br.com.netservicos.core.bean.sn.SnLoteBean;
import br.com.netservicos.framework.core.bean.DynamicBean;
import br.com.netservicos.framework.core.facade.Service;
import br.com.netservicos.novosms.emissao.dto.ArquivoPrintHouseDTO;
import br.com.netservicos.novosms.emissao.dto.ArquivoRemessaTransporteDTO;
import br.com.netservicos.novosms.emissao.dto.BaixaLogHubDTO;
import br.com.netservicos.novosms.emissao.dto.FaturaArquivoDTO;
import br.com.netservicos.novosms.emissao.dto.LoteBoletoFaturaDTO;
import br.com.netservicos.novosms.emissao.exception.EmissaoBusinessResourceException;



/**
* @author : Alex Simas Braz 
* @version : 1.0
* <P><B>
* Description : Interface do componente de Geração do Arquivo da PrintHouse
* </B>
* <BR>
* TODO:
* </P>
* <P>
* <B>
* Issues :
* </B>
* <PRE>
* ==============================================================================
* Description 							   Date 	   By
* ---------------------------------------- ----------- ------------------------
* Criacão                                  10/08/2007   Alex Simas Braz
* ==============================================================================
* </PRE>
*
* <P><B>
* Revision History:
* </B><PRE>
* ==============================================================================
* Prior Date By 			   Version  Description
* ---------- ----------------  -------- -------------------------------------------
* 
* ==============================================================================
* </PRE>
*/
public interface ArquivoPrintHouseService extends Service  {
	
	
	@SuppressWarnings("unchecked")
	public Collection gerarArquivoPrintHouse(Collection<SnLoteBean> lotes, String siglaTipoLote);
	
	public FaturaArquivoDTO gerarFaturaSegundaVia(Long idBoleto);
	
	public FaturaArquivoDTO gerarFaturaPrimeiraVia(Long idBoleto);
	
	public FaturaArquivoDTO gerarReEmissaoNotaFiscal(Long idNotaFiscal, Long idBoleto);
	
	public FaturaArquivoDTO gerarReEmissaoNotaFiscal(Long idNotaFiscal);
	
		public SnLoteBean atualizarSituacaoLote(SnLoteBean lote,String chave);
	
	public  SnControleArquivoBean registrarArquivoPrintHouseCriado (
			SnControleArquivoBean arquivo
			, String nomeInicial
			, ArquivoPrintHouseDTO arquivoPrintDTO
			, SnCidadeOperadoraBean cidade
			, Date dataVencimento
			, String siglaTipoLote
			) throws EmissaoBusinessResourceException; 

	/***
	 * Atualiza a situacao dos lotes para a sigla informada.
	 * @param lotes
	 * @param sigla
	 */
	public void atualizarSituacaoLote(Collection<SnLoteBean> lotes, String sigla);
	
	public  List<SnLoteBean> atualizarSituacaoLoteTask(Collection<SnLoteBean> lotes, String sigla) throws Exception;
	
	void testarDAO(DynamicBean bean);
	
	public List<ArquivoRemessaTransporteDTO> gerarArquivoPrintHouseTesteMensagem(List<String> criterios , Date dataVencimento
			,String tipoMsg);
	
	public boolean faturaTemBoleto(Long idBoleto);
	
	public List<LoteBoletoFaturaDTO> retornaBoletos(SnLoteBean lote);
	
	public void efetuaProcessoBaixaHub(BaixaLogHubDTO log);
	
	public SnBoletoBean retornaDetalhesBoleto(Long idBoleto, String cidContrato);
}
