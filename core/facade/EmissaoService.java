package br.com.netservicos.novosms.emissao.core.facade;

import java.util.Date;
import java.util.List;

import br.com.netservicos.core.bean.sn.SnLoteBean;
import br.com.netservicos.core.bean.sn.SnSituacaoProcessamentoBean;
import br.com.netservicos.emissao.bean.TransporteDTO;
import br.com.netservicos.framework.core.bean.DynamicBean;
import br.com.netservicos.framework.core.facade.Service;
import br.com.netservicos.novosms.emissao.dto.ArquivoRemessaTransporteDTO;
import br.com.netservicos.novosms.emissao.dto.FaturaArquivoDTO;

public interface EmissaoService extends Service{

	/**
	 * Inicia o processo de geração de Arquivo Print House para um Lote
	 * @author Fabio Ikeda
	 * @number RF190, RF015_RF021
	 * @param idLote
	 * @param idFila TODO
	 * @throws JNetException
	 * @semantics
	 *  
	 * 1) Buscar o lote correspondente ao idLote passado (getLoteDAO().selecionarLote(idLote))
	 *  
	 * 2) Aciona o método arquivoPrintHouseService.gerarArquivoPrintHouse(lotesPendPrint,FatCobrancaConstants.TipoLote.SIGLA_TIPO_LOTE_EMISSAO);
	 * 
	 * 3) Aciona o método netSMSService.dispararAtualizacao(lote);
	 * 
	 * 4) Inserir na fila a tarefa de Transporte de arquivo remessa usando como parâmetros o retorno de arquivoPrintHouseService.gerarArquivoPrintHouse  
	 * 
	 */
	public List iniciarPrintHouse(Integer idLote);
	
    /**
	 * @author Fabio Ikeda
	 * @number RF190
	 * @param lotesPendPrint
	 * @param idFila TODO
	 * @throws JNetException
	 * @since 1.4 RF190
	 * 
	 */
	public List iniciarGeracaoArquivoPrint(List<SnLoteBean> lotesPendPrint,String tipoLote);
		
	/**
	 * Inicia o processo de geração de Arquivo PrintHouse
     * @author Fabio Ikeda
     * @number RF190, RF015_RF021
	 * @param idOperadora
	 * @param dtVencimento
	 * @param idFila TODO
	 * @throws JNetException
	 * @semantics
	 * 
	 * 1) Busca os lotes relacionados ao parametros passados e pendentes de impressao (getLoteDAO().buscarLotes( Map ))
	 * 
	 * 2) Aciona o método arquivoPrintHouseService.gerarArquivoPrintHouse(lotesPendPrint,TIPO LOTE EMISSAO);
	 * 
	 * 3) Aciona o método netSMSService.dispararAtualizacao(lote);
	 * 
	 * 4) Inserir na fila a tarefa de Transporte de arquivo remessa usando como parâmetros o retorno de arquivoPrintHouseService.gerarArquivoPrintHouse  
	 * 
	 */
	public List iniciarPrintHousePorLotes(String cidContrato , List<String> lotes, String tipoLote);
	
	/**
	 * Inicia o processo de geração de Arquivo PrintHouse
     * @author Fabio Ikeda
     * @number RF190, RF015_RF021
	 * @param idOperadora
	 * @param dtVencimento
	 * @param idFila TODO
	 * @throws JNetException
	 * @semantics
	 * 
	 * 1) Busca os lotes relacionados ao parametros passados e pendentes de impressao (getLoteDAO().buscarLotes( Map ))
	 * 
	 * 2) Aciona o método arquivoPrintHouseService.gerarArquivoPrintHouse(lotesPendPrint,TIPO LOTE EMISSAO);
	 * 
	 * 3) Aciona o método netSMSService.dispararAtualizacao(lote);
	 * 
	 * 4) Inserir na fila a tarefa de Transporte de arquivo remessa usando como parâmetros o retorno de arquivoPrintHouseService.gerarArquivoPrintHouse  
	 * 
	 */
	public List iniciarPrintHouse(String cidContrato, Date dtVencimento, String tipoLote );
	
	/**
	 * @author Fabio Ikeda
	 * @number RF190, RF015_RF021
	 * @param idOperadora
	 * @param dtVencimento
	 * @param idInstituicao
	 * @param idModalidade
	 * @param idFila TODO
	 * @throws JNetException
	 * @number RF190
	 * @semantics
	 * 
	 * 1) Busca os lotes relacionados ao parametros passados e pendentes de impressao (getLoteDAO().buscarLotes( Map ))
	 * 
	 * 2) Aciona o método arquivoPrintHouseService.gerarArquivoPrintHouse(lotesPendPrint,TIPO LOTE EMISSAO);
	 * 
	 * 3) Aciona o método netSMSService.dispararAtualizacao(lote);
	 * 
	 * 4) Inserir na fila a tarefa de Transporte de arquivo remessa usando como parâmetros o retorno de arquivoPrintHouseService.gerarArquivoPrintHouse  
	 * 
	 */
	public List iniciarPrintHouse(Integer idOperadora, Date dtVencimento, Integer idInstituicao, Integer idModalidade , String tipoLote);
	
	/**
	 * @version 1.0 
	 * @author Fabio Ikeda
	 * @number RF190, RF017, RF015_RF021 
	 * @param origem
	 * @param destino
	 * @throws JNetException
	 * @semantics Inicia o transporte do arquivo requisitado
	 */
	public List iniciarTransporteArquivos(TransporteDTO origem, TransporteDTO destino, String protocolo, Integer idFila, Integer idControleArquivo, Boolean apagarOrigem);
	
	public Integer iniciarPrintHouseTest();
	
	public FaturaArquivoDTO gerarFaturaSegundaVia(Long idBoleto);
	
	/**
	 * Metodo responsavel pela busca dos lotes que serao apresentados para escolha do processamento
	 * @author marcelo
	 * @since 30/08/2007
	 * @param dynaBean DynamicBean contendo parametros para a query (dataInicial, dataFinal e lstCidContrato)
	 * @return dynaBean com a lista de lotes encontratos
	 */
	public DynamicBean buscarLotesParaEscolha(DynamicBean dynaBean);
	
	/**
	 * Metodo responsavel pela gravacao dos lotes selecionados
	 * @author marcelo
	 * @since 31/08/2007
	 * @param dynaBean DynamicBean contendo parametros para a query (lstLote)
	 * @return dynaBean
	 */
	public DynamicBean persisteLotes(DynamicBean dynaBean);

	/**
	 * Metodo responsavel pela busca dos bancos das operadoras selecionadas
	 * @author matorres
	 * @since 03/09/2007
	 * @param dynaBean DynamicBean contendo parametros para a query (lstCidContrato)
	 * @return dynaBean
	 */
	public DynamicBean obterBanco(DynamicBean dynaBean);

	/**
	 * Metodo responsavel por buscar os lotes cnab para conforme filtro
	 * @author matorres
	 * @since 04/09/2007
	 * @param dynaBean DynamicBean contendo parametros para a query (dataInicial, dataFinal, lstCidContrato e lstBanco)
	 * @return dynaBean com a lista de lotes encontratos
	 */
	public DynamicBean buscarLotesCnab(DynamicBean dynaBean);

	/**
	 * Metodo responsavel pela gravacao dos lotes cnab selecionados
	 * @author matorres
	 * @since 04/09/2007
	 * @param dynaBean DynamicBean contendo parametros para a query (lstLote)
	 * @return dynaBean
	 */
	public DynamicBean persisteLotesCnab(DynamicBean dynaBean);

	/**
	 * Metodo responsavel pela busca dos lotes pendentes conforme tipo de lote e situacao do processamento
	 * @author matorres
	 * @since 10/09/2007
	 * @param
	 * @return List<SnLoteBean> Lista dos lotes selecionados.
	 */
	public List<SnLoteBean> selecionaLotePendente(String siglaSituacaoProcessamento, String siglaTipoLote);
	
	/**
	 * Metodo responsavel pela busca dos lotes pendentes conforme sigla do tipo de lote e situacao do processamento
	 * @author matorres
	 * @since 11/12/2007
	 * @param
	 * @return List<SnLoteBean> Lista dos lotes selecionados.
	 */
	public List<SnLoteBean> selecionaLotePendentePorSigla(String siglaSituacaoProcessamento, String siglaTipoLote);
	
	/**
	 * Metodo responsavel pela execucao das tarefas CriarTarefaLotesPrintHousePrimViaTask, CriarTarefaLotesPrintHouseReemissaoNFTask e
	 * CriarTarefaLotesPrintHouseSegViaTask na qual cria uma tarefa GerarArquivoPrintHouseTask para cada conjunto de lotes agrupados.
	 * @author matorres
	 * @since 10/09/2007
	 * @param listLote Lista de lotes pendentes para processar e gerar arquivo print house.
	 * @param siglaTipoLote Sigla do tipo de lote.
	 * @return Long id da fila inserida
	 */
	public Long criarTarefaLotesPrintHouseTask(List<SnLoteBean> listLote, String siglaTipoLote);
	
	/**
	 * Metodo responsavel pela execucao da tarefa GerarArquivoPrintHouseTask, na qual gera os arquivos printhouse, segunda via e c—pia da nota fiscal
	 * para os lotes recebidos como parametro e apos a geracao do(s) arquivo(s).
	 * @author matorres
	 * @since 08/09/2007
	 * @param lotes String[] contendo a lista de lotes a processar.
	 * @param tipoLote tipo do lote a ser processado.
	 * @return
	 */
	public List<ArquivoRemessaTransporteDTO> gerarArquivoPrintHouseTask(String[] lotes, String tipoLote);
	
	/**
	 * Metodo responsavel pela execucao da tarefa CriarTarefaLoteCNABTask, na qual cria uma tarefa GerarArquivoCNABTask para o lote informado.
	 * @author matorres
	 * @since 10/09/2007
	 * @param snLoteBean Lote para gerar arquivo
	 * @return Long id da fila inserida
	 */
	public Long criarTarefaLoteCNABTask(SnLoteBean snLoteBean);
	
	/**
	 * Metodo responsavel pela execucao da tarefa criarTarefaLoteRDCCTask, na qual cria uma tarefa GerarArquivoRDCCTask para o lote informado.
	 * @author matorres
	 * @since 25/09/2007
	 * @return Long id da fila inserida
	 */
	public Long criarTarefaLoteRDCCTask();
	
	/**
	 * Metodo responsavel pela execucao da tarefa GerarArquivoCNABTask, na qual gera os arquivos CNAB para o lote recebidos como parametro.
	 * @author matorres
	 * @since 11/09/2007
	 * @param lote Lote para processar.
	 * @return
	 *  
	 * @ejb.interface-method view-type="both"
	 * @ejb.transaction type="Required"
	 */
	public List<ArquivoRemessaTransporteDTO> gerarArquivoCNABTask(Integer lote);
	
	/**
	 * Metodo responsavel pela execucao da tarefa GerarArquivoRDCCTask, na qual gera os arquivos RDCC para o lote recebidos como parametro.
	 * @author matorres
	 * @since 25/09/2007
	 * @return
	 *  
	 * @ejb.interface-method view-type="both"
	 * @ejb.transaction type="Required"
	 */
	public List<ArquivoRemessaTransporteDTO> gerarArquivoRDCCTask();
	
	/**
	 * Metodo responsavel por criar a tarefa de transporte dos arquivos gerados.
	 * @author matorres
	 * @since 08/09/2007
	 * @param transportesArquivo Lista dos arquivos para transportar.
	 * @return
	 */
	public void transportaArquivos(List<ArquivoRemessaTransporteDTO> transportesArquivo);
	
	/**
	 * Metodo responsavel por obter os dbServices disponiveis e ativos
	 * @author matorres
	 * @since 25/09/2007
	 * @return
	 */
	public String[] getActiveDbServices();

	/**
	 * Atualiza situacao do lote
	 * @param snLoteBean
	 * @param sitProcessando
	 */
	public void atualizarSituacaoLote(SnLoteBean snLoteBean, SnSituacaoProcessamentoBean sitProcessando);
	
	/**
	 * Metodo responsavel pela gravacao dos criterios selecionados
	 * @author wmaeda
	 * @since 26/02/2008
	 * @param dynaBean DynamicBean contendo parametros para a query (lstLote)
	 * @return dynaBean
	 */
	public DynamicBean persisteCriterios(DynamicBean dynaBean);
	
	/**
	 * Metodo responsavel pela execucao da tarefa GerarArquivoRDCCTask, na qual gera os arquivos RDCC para o lote recebidos como parametro.
	 * @author matorres
	 * @since 25/09/2007
	 * @return
	 *  
	 * @ejb.interface-method view-type="both"
	 * @ejb.transaction type="Required"
	 */
	public List<ArquivoRemessaTransporteDTO> gerarArquivoPrintHouseTesteMensagem(List<String> criterios , Date dataVencimento , String tipoMsg);
	
	/**
	 * Metodo que sepera os lotes de boleto avulso para processamento (Cidade -> TipoCobranca).
	 * @author pestana
	 * @param maxTreads 
	 * @since 26/10/2017
	 * @param maxTreads quantidades de Threads para procesamento de arquivos
	 * @return void
	 *  
	 * @ejb.interface-method view-type="both"
	 * @ejb.transaction type="Required"
	 */
	public void geraArquivoPrintBoletoAvulso(Integer maxTreads);
	
	
	/**
	 * Metodo que gera os arquivos print para lotes de boleto avulso pendentes.
	 * 
	 * @author pestana
	 * @since 26/10/2017
	 * @return List<ArquivoRemessaTransporteDTO>
	 * 
	 * @ejb.interface-method view-type="both"
	 * @ejb.transaction type="RequiresNew"
	 */
	public void processaLoteBoletoAvulso(SnLoteBean lotesAProcessar);

	/**
	 * Metodo responsavel por buscar os lotes baixa online conforme filtro
	 * @author cassio
	 * @since 12/10/2021
	 * @param dynaBean DynamicBean contendo parametros para a query (dataInicial, dataFinal, lstCidContrato e lstBanco)
	 * @return dynaBean com a lista de lotes encontratos
	 */
	public DynamicBean buscarLotesCartaoOnline(DynamicBean dynaBean);
	
	/**
	 * Metodo responsavel pela busca de operadoras cartão conforme filtro
	 * @author cassio
	 * @since 12/10/2021
	 * @param dynaBean DynamicBean contendo parametros para a query (lstCidContrato)
	 * @return dynaBean
	 */
	public DynamicBean obterOperadoraCartao(DynamicBean dynaBean);
	
	
	/**
	 * Metodo responsavel pela gravacao dos lotes baixa online selecionados
	 * @author cassio
	 * @since 12/10/2021
	 * @param dynaBean DynamicBean contendo parametros para a query (lstLote)
	 * @return dynaBean
	 */
	public DynamicBean persisteLotesBaixaOnline(DynamicBean dynaBean);

}
