/** Project : NovoSMSWeb
 * Copyright © 2007 NET.
 * Brasil
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of NET. 
 * You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Net Serviços.
 * 
 * $Id: DisparoGeracaoArquivoMastersafEJBImpl.java,v 1.1 
 * 2007/08/20 11:12:51 evsantos Exp $
 **/
package br.com.netservicos.novosms.emissao.core.facade.impl;

import static br.com.netservicos.core.bean.sn.SnStatusArquivoBean.Sigla.EMISSAO_PROCESSADO;

import java.io.File;
import java.io.IOException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.com.netservicos.core.bean.ms.MsParamOperBean;
import br.com.netservicos.core.bean.sn.SnCidadeOperadoraBean;
import br.com.netservicos.core.bean.sn.SnCobrancaNotaFiscalBean;
import br.com.netservicos.core.bean.sn.SnConfiguracaoArquivoBean;
import br.com.netservicos.core.bean.sn.SnContratoBean;
import br.com.netservicos.core.bean.sn.SnContratoKey;
import br.com.netservicos.core.bean.sn.SnControleArquivoBean;
import br.com.netservicos.core.bean.sn.SnParametroBean;
import br.com.netservicos.core.bean.sn.SnParametroKey;
import br.com.netservicos.core.bean.sn.SnStatusArquivoBean;
import br.com.netservicos.framework.core.bean.DynamicBean;
import br.com.netservicos.framework.core.bean.task.constants.TaskConstants;
import br.com.netservicos.framework.core.dao.BaseDAOException;
import br.com.netservicos.framework.core.dao.BatchParameter;
import br.com.netservicos.framework.core.dao.DAOCallbackDecorator;
import br.com.netservicos.framework.core.dao.OracleCallbackBatchParameter;
import br.com.netservicos.framework.file.reader.impl.FileMapperServiceEJBImpl;
import br.com.netservicos.framework.file.writer.NoLayoutGenerateFile;
import br.com.netservicos.framework.task.service.TaskService;
import br.com.netservicos.framework.util.attachments.messages.AttachmentMessageLevel;
import br.com.netservicos.framework.util.attachments.messages.BasicAttachmentMessage;
import br.com.netservicos.framework.util.exception.BaseFailureException;
import br.com.netservicos.framework.util.io.ZipUtils;
import br.com.netservicos.novosms.emissao.constants.EmissaoConstants;
import br.com.netservicos.novosms.emissao.core.facade.DisparoGeracaoArquivoMastersafService;
import br.com.netservicos.novosms.emissao.core.facade.DisparoGeracaoArquivoSafxService;
import br.com.netservicos.novosms.emissao.exception.EmissaoBusinessResourceException;
import br.com.netservicos.novosms.emissao.resources.EmissaoResources;
import br.com.netservicos.novosms.geral.constants.GeralConstants;
import br.com.netservicos.novosms.geral.core.facade.ConfiguracaoArquivoService;
import br.com.netservicos.novosms.geral.core.facade.StatusArquivoService;


/**
 * <P>
 * <B>Description :</B><BR>
 * Essa classe EJB implementa os códigos para geracao dos arquivos mastersaf.
 * </P>
 * <P>
 * <B> Issues : <BR>
 * None </B>
 * 
 * @author Everson dos Santos
 * @since 23/07/2007
 */

/**
 * @ejb.bean name="DisparoGeracaoArquivoMastersafEJB" 
 * 		type="Stateless" 
 * 		display-name="DisparoGeracaoArquivoMastersafEJB"
 * 		description="Gera arquivos mastersaf" 
 * 		view-type="both"
 * 		jndi-name="netservicos/ejb/emissao/DisparoGeracaoArquivoMastersafEJB" 
 * 		local-jndi-name="netservicos/ejb/local/emissao/DisparoGeracaoArquivoMastersafEJB" 
 * 		transaction-type="Container"
 * 
 * @ejb.interface local-extends="javax.ejb.EJBLocalObject" extends="javax.ejb.EJBObject"
 * 
 * @ejb.home local-extends="javax.ejb.EJBLocalHome" extends="javax.ejb.EJBHome"
 * 
 */
public class DisparoGeracaoArquivoMastersafEJBImpl extends AbstractEmissaoEJBImpl implements
		DisparoGeracaoArquivoMastersafService {

	//TODO QUANDO "FINALIZAR" ADICIONAR A ROLE
	private static final long serialVersionUID = -2027200476294657878L;
	
	private final Log log = LogFactory.getLog(FileMapperServiceEJBImpl.class);
	
	private  final String MASTERSAF = "MASTERSAF";
	
	private  final String EMISSAO_TRANSPORTE = "EMISSAO_TRANSPORTE";
	
	private  final String PRSMS_SAFX_04_42 = "{call PGSMS_MASTERSAF.PRSMS_SAFX_04_42(?,?,?,?)}";
	
	private  final String PRSMS_SAFX_2013 = "{call PGSMS_MASTERSAF.PRSMS_SAFX_2013(?,?,?)}";
	
	private  final String PRSMS_SAFX_43_FULL = "{call PGSMS_MASTERSAF.PRSMS_SAFX_43_FULL(?,?,?,?)}";
	
	private  final String PROC_NAO_ENCONTRADA = "must be declared";
	
	private DynamicBean dynamicBeanCallBack = new DynamicBean();
	/**
	 * @see br.com.netservicos.novosms.emissao.core.facade.DisparoGeracaoArquivoMastersafService#gerarArquivos(br.com.netservicos.framework.core.bean.DynamicBean)
	 * @ejb.interface-method view-type="both"
	 * @ejb.transaction type="Required"
	 */
	public void gerarArquivos(DynamicBean dynamicBean) {
		try{
			SnCobrancaNotaFiscalBean fiscalBean = new SnCobrancaNotaFiscalBean();
			SnContratoBean contratoBean = new SnContratoBean();
			SnContratoKey  contratoKey = new SnContratoKey();
			fiscalBean.setCcAnoMesEmissao(dynamicBean.get("ccAnoMesEmissao").toString());
			contratoKey.setCidContrato(dynamicBean.get("cidContrato").toString());
			contratoBean.setCompositeKey(contratoKey);
			fiscalBean.setContrato(contratoBean);
			
			//Busca parametros que serão utilizados no arquivo
			ArrayList < MsParamOperBean > listParamOper = (ArrayList < MsParamOperBean >) super.search("objMsParamOper", dynamicBean);
			
			//Se não encontrar os parametros retorna exception
			if(listParamOper.isEmpty()){
				this.executaLog(super.getMessage(EmissaoResources.ERRO_CONSULTA_PARAM_OPER), new NullPointerException());
				throw new EmissaoBusinessResourceException(super.getMessage(
						EmissaoResources.ERRO_CONSULTA_PARAM_OPER), new NullPointerException());
			}	
			
			for(MsParamOperBean paramOperBean : listParamOper){
				dynamicBean.put("codOperJdeMestre",  paramOperBean.getCodOperJdeMestre().substring(0,3));
				dynamicBean.put("codOperJdeFisc", paramOperBean.getCodOperJdeFisc());
			}
			
			//this.apagaDiretorio(dynamicBean);
			this.dynamicBeanCallBack = dynamicBean;
			
			/**
			 * Classe anônima para arquivo 42 e 04
			 */
			DAOCallbackDecorator < DynamicBean > callBackSafx42 = new DAOCallbackDecorator < DynamicBean >(){
				
				private Map < String , Object > valoresArquivo42;
				private Map < String , Object >  valoresArquivo04;
				DisparoGeracaoArquivoSafxService safxService = getService(DisparoGeracaoArquivoSafxService.class);
				
				File file42 = null;
				File file04 = null;
				
				NoLayoutGenerateFile noFormatFile42 = null;
				NoLayoutGenerateFile noFormatFile04 = null;
				
				
				public void execute(DynamicBean bean, int rowNum) {
					safxService.gerarLinhaSAFX042(valoresArquivo42, bean, dynamicBeanCallBack, noFormatFile42);
				//	safxService.gravarSAFX042(valoresArquivo42, dynamicBeanCallBack, noFormatFile42);
					
					safxService.gerarLinhaSAFX04(valoresArquivo04, bean, dynamicBeanCallBack, noFormatFile04);
					//safxService.gravarSAFX04(valoresArquivo04, dynamicBeanCallBack, noFormatFile04);
				}

				public void initializeDAOCallback() {
					file42 = safxService.arquivoDiretorio(dynamicBeanCallBack, "safx42");
					noFormatFile42 = new NoLayoutGenerateFile(file42);
					noFormatFile42.setLineSeparator(NoLayoutGenerateFile.LINE_SEPARATOR_WINDOWS);
					
					file04 = safxService.arquivoDiretorio(dynamicBeanCallBack, "safx04");
					noFormatFile04 = new NoLayoutGenerateFile(file04);
					noFormatFile04.setLineSeparator(NoLayoutGenerateFile.LINE_SEPARATOR_WINDOWS);
					
					valoresArquivo42 = new HashMap < String , Object >();
					valoresArquivo04 = new HashMap < String , Object >();
				}
				
				public void finalizeDAOCallback() {
					
					noFormatFile42.finalizar();
					noFormatFile04.finalizar();
				}
				
				public void onRowSucess(DynamicBean bean, int rowNum) {
					//Executa após a passagem pelo execute
				}
			};
			 
			
			/**
			 * Classe anônima para arquivo 2013
			 */
			DAOCallbackDecorator < DynamicBean > callBackSafx2013 = new DAOCallbackDecorator < DynamicBean >(){
				
				private Map < String , Object > valoresArquivo;
				DisparoGeracaoArquivoSafxService safxService = getService(DisparoGeracaoArquivoSafxService.class);
				
				File file2013 = null;
				
				NoLayoutGenerateFile noFormatFile2013 = null;
			
				
				public void execute(DynamicBean bean, int rowNum) {
					safxService.gerarLinhaSAFX2013(valoresArquivo, bean, dynamicBeanCallBack, noFormatFile2013);
				//	safxService.gravarSAFX2013(valoresArquivo, dynamicBeanCallBack,noFormatFile2013);
						
				}	

				public void initializeDAOCallback() {
					
					file2013 = safxService.arquivoDiretorio(dynamicBeanCallBack, "safx2013");
					noFormatFile2013 = new NoLayoutGenerateFile(file2013);
					noFormatFile2013.setLineSeparator(NoLayoutGenerateFile.LINE_SEPARATOR_WINDOWS);
					
					
					valoresArquivo = new HashMap < String , Object >();
				}
				
				public void finalizeDAOCallback() {
					noFormatFile2013.finalizar();
				}
				
				public void onRowSucess(DynamicBean bean, int rowNum) {
					//Executa após a passagem pelo execute
				}
			};
			
			
			/**
			 * Classe anônima para arquivo 43
			 * Alteração Leandro Ambrósio - 19/02/2010 - Projeto CAT6
			 */
			DAOCallbackDecorator < DynamicBean > callBackSafx43 = new DAOCallbackDecorator < DynamicBean >(){
				
				private Map < String , Object > valoresArquivo;
				DisparoGeracaoArquivoSafxService safxService = getService(DisparoGeracaoArquivoSafxService.class);
				
				File file43= null;
				Long numeroNotaFiscal    = new Long(0); //Alteração CAT6 - Variável de controle do NR NF
				Long numeroSeqNotaFiscal = new Long(0); //Alteração CAT6 - Variável de controle da Seq do item da NF
				
				NoLayoutGenerateFile noFormatFile43 = null;
				
				public void execute(DynamicBean bean, int rowNum) {
					//Alteração CAT6 - Verifica se repetiu o número NF
					//soma variável de controle da seq do item
					//seta uma variável aux no bean com o valor da seq do item
					if((numeroNotaFiscal.longValue() == ((new Long (bean.get("NR_NOTA_FISCAL").toString())).longValue()) ) && 
						                                 	((new Long ((bean.get("NR_NOTA_FISCAL").toString())).longValue()) != 0)){
						numeroSeqNotaFiscal++;	
						bean.set("numeroSeqNotaFiscal", numeroSeqNotaFiscal);
					}else {
						numeroNotaFiscal = ((new Long (bean.get("NR_NOTA_FISCAL").toString())).longValue());
						numeroSeqNotaFiscal = 1L;						
					}				
					
					safxService.gerarLinhaSAFX43(valoresArquivo, bean, dynamicBeanCallBack, noFormatFile43);
				//	safxService.gravarSAFX43(valoresArquivo, dynamicBeanCallBack,noFormatFile43);
				}

				public void initializeDAOCallback() {
					
					file43 = safxService.arquivoDiretorio(dynamicBeanCallBack, "safx43");
					noFormatFile43= new NoLayoutGenerateFile(file43);
					noFormatFile43.setLineSeparator(NoLayoutGenerateFile.LINE_SEPARATOR_WINDOWS);
					valoresArquivo = new HashMap < String , Object > ();
				}
				
				public void finalizeDAOCallback() {
					
					noFormatFile43.finalizar();
				}
				
				public void onRowSucess(DynamicBean bean, int rowNum) {
					//Executa após a passagem pelo execute
				}
			};
			
			//Executa procedures
			this.procSafx04_42(dynamicBean, callBackSafx42);
			this.procSafx43(dynamicBean, callBackSafx43);
			this.procSafx2013(dynamicBean, callBackSafx2013);
			
			dynamicBean.put("nomeArq", this.ziparArquivosComMascara(dynamicBean));
			this.controleArquivos(dynamicBean);
			
		}catch(BaseDAOException b){
			if(this.findPattern(b.getMessage(), PROC_NAO_ENCONTRADA))
				throw new EmissaoBusinessResourceException(super.getMessage(EmissaoResources.ERRO_PROC_NAO_ENCONTRADA),b);
		}
		catch(Exception b){
			throw new EmissaoBusinessResourceException(super.getMessage(EmissaoResources.ERRO_DISPARO_GERACAO),b);
		}
	}
	
	/**
	 * Busca o pattern na String informada 
	 * @since 14/11/2007
	 * @param word String onde será feita a busca
	 * @param patternStr Pattern que se deseja achar
	 * @return retorna true se encontrar o pattern
	 */
	private boolean findPattern(final String word, final String patternStr){
	     Pattern pattern = Pattern.compile(patternStr);
	     Matcher matcher = pattern.matcher(word);
	     return matcher.find();
	}
	
	/**
	 * Apaga todos os .zip do diretório.
	 * Gera .zip com máscara. 
	 * Apaga todos os .txt do diretório.
	 * MSAF[_cid_contrato]99999[_mes_competencia]yyyyMM[_enviado]ddMMyyyy[.H]999999[.zip]
	 * MSAF_15890_200708_21092007.153025.zip
	 * 
	 * @since 24/09/2007
	 * @param dynamicBean
	 * @return
	 * @throws IOException
	 */
	private String ziparArquivosComMascara(DynamicBean dynamicBean) throws IOException{
		String nomeDir="";
		StringBuilder nomeZip = new StringBuilder();
		try{	
			nomeDir = this.geraPathArquivo(dynamicBean.get("localDestinoArquivo").toString(),
					  this.geraNomeDiretorio(dynamicBean));
			
			Calendar cal = new GregorianCalendar();
			nomeZip.append("MSAF");
			nomeZip.append("_");
			nomeZip.append(dynamicBean.get("cidContrato"));
			nomeZip.append("_");
			nomeZip.append(dynamicBean.get("ccAnoMesEmissao"));
			nomeZip.append("_");
			nomeZip.append(cal.get(Calendar.DAY_OF_MONTH));
			nomeZip.append(cal.get(Calendar.MONTH));
			nomeZip.append(cal.get(Calendar.YEAR));
			nomeZip.append(".");
			nomeZip.append(cal.get(Calendar.HOUR_OF_DAY));		
			nomeZip.append(cal.get(Calendar.MINUTE));
			nomeZip.append(cal.get(Calendar.SECOND));
			nomeZip.append(".zip");

			File arqDestino= new File(nomeDir, nomeZip.toString());
			File path = new File(nomeDir);
			
			//Apaga todos os arquivos zip do diretorio
			File[] arquivos = path.listFiles();
			for(File f : arquivos){
				if(f.getName().endsWith("zip"))
					f.delete();
			}
			
			//Compacta todos arquivos do diretorio e salva o zip
			arquivos = path.listFiles();
			ZipUtils.zipFiles(arqDestino, arquivos);
			
			//Apaga todos os arquivos txt do diretorio após compactá-los
			File[] arquivosTxt = path.listFiles();
			for(File f : arquivosTxt){
				if(f.getName().endsWith("txt"))
					f.delete();
			}
			
		}catch(IOException e){
			if(this.findPattern(e.getCause().toString(), "denied")){
				throw new EmissaoBusinessResourceException(e, super.getMessage(EmissaoResources.ERRO_ZIP_ACESSO)+nomeDir+nomeZip);
			}else{
				throw new EmissaoBusinessResourceException(e, super.getMessage(EmissaoResources.ERRO_ZIP_COMPACTAR));
			}
		}
		return nomeZip.toString();
	}
	
	/**
	 * @see br.com.netservicos.novosms.emissao.core.facade.DisparoGeracaoArquivoMastersafService#dispararArquivos(br.com.netservicos.framework.core.bean.DynamicBean)
	 * @ejb.interface-method view-type="both"
	 * @ejb.transaction type="Required"
	 */
	public boolean dispararArquivos(DynamicBean dynamicBean) {
		try{
			HashMap < String , String > param = new HashMap < String , String >();
			param.put("cidContrato", dynamicBean.get("cidContrato").toString());
			param.put("codOperadora", dynamicBean.get("codOperadora").toString());
			param.put("ccAnoMesEmissao", dynamicBean.get("ccAnoMesEmissao").toString());
			param.put("localDestinoArquivo", dynamicBean.get("localDestinoArquivo").toString());
			param.put("idEmpresa", dynamicBean.get("idEmpresa").toString());
			
			Long idFila = (Long) super.getPrincipalProperties().get(TaskConstants.ID_FILA);
			String currentDBService = super.getUserSession().getCurrentDbService();
			String currentDBIdentifier = currentDBService.substring(GeralConstants.PREFIXO_DATABASE_SERVICE.length());
			param.put("base",currentDBIdentifier);
			
			TaskService taskService = super.getService(TaskService.class);
			taskService.submitTaskToFila(
					MASTERSAF, 
					param, 
					super.getUserSession().getUserId(), 
					currentDBIdentifier, 
					idFila );
		}catch(Exception e){
			return false;
		}
		return true;
	}
	
	/**
	 * Retorna os valores da procedure safx2013
	 * @since @since 07/12/07
	 * @param dynamicBean
	 * @return
	 */
	private void procSafx2013(DynamicBean dynamicBean,DAOCallbackDecorator callBackSafx42){		
		BatchParameter[] parameters = parametrosProcSafx2013(dynamicBean, callBackSafx42);
		super.getCurrentDAO().executeBatchTransactionSuspended(PRSMS_SAFX_2013, parameters);
	}
	
	
	/**
	 * Encapsula os parametros a serem usados na procedure safx2013
	 * @since 07/12/07
	 * @param dynamicBean
	 * @return
	 */
	private BatchParameter[] parametrosProcSafx2013(DynamicBean dynamicBean, DAOCallbackDecorator callBackSafx2013) {
		BatchParameter[] parameters = new BatchParameter[3];
		parameters[0] = new BatchParameter(dynamicBean.get("cidContrato"), Types.VARCHAR, false);
		parameters[1] = new BatchParameter(Types.VARCHAR, true);
		parameters[2] = new OracleCallbackBatchParameter( callBackSafx2013);
		return parameters;
	}
	
	

	/**
	 * Adiciona Exception no Log.
	 * 
	 * @author Everson dos Santos
	 * @since 17/08/2007
	 * @param key mensagem do arquivo.properties
	 * @param throwable objeto da excessão
	 */
	private void executaLog(final String key, final Throwable throwable) {
		try {
			log.error(new BasicAttachmentMessage(super.getMessage(key) + throwable.getMessage(), AttachmentMessageLevel.ERROR));
		} catch (Exception e) {
			throw new BaseFailureException(super.getMessage(EmissaoResources.ERRO_GERAR_LOG), e);
		}
	}
	

	
	/**
	 * Retorna os valores da procedure safx43
	 * @since 07/12/2007
	 * @param dynamicBean
	 * @return
	 */
	private void procSafx43(DynamicBean dynamicBean, DAOCallbackDecorator callBackSafx43){		
		BatchParameter[] parameters = parametrosProcSafx04_42_43(dynamicBean, callBackSafx43);
		super.getCurrentDAO().executeBatchTransactionSuspended(PRSMS_SAFX_43_FULL, parameters);
	}
	
	/**
	 * Retorna os valores da procedure safx 04 e 42
	 * @since 08/11/2007
	 * @param dynamicBean
	 * @return
	 */
	private void procSafx04_42(DynamicBean dynamicBean,DAOCallbackDecorator callBackSafx42){		
		BatchParameter[] parameters = parametrosProcSafx04_42_43(dynamicBean, callBackSafx42);
		super.getCurrentDAO().executeBatchTransactionSuspended(PRSMS_SAFX_04_42, parameters);
	}
	
	/**
	 * Encapsula os parametros para safx 04 42 43
	 * @since 08/11/2007
	 * @param dynamicBean
	 * @return
	 */
	private BatchParameter[] parametrosProcSafx04_42_43(DynamicBean dynamicBean, DAOCallbackDecorator callBackSafx42) {
		BatchParameter[] parameters = new BatchParameter[4];
		parameters[0] = new BatchParameter(dynamicBean.get("ccAnoMesEmissao"), Types.VARCHAR, false);
		parameters[1] = new BatchParameter(dynamicBean.get("cidContrato"), Types.VARCHAR, false);
		parameters[2] = new BatchParameter(Types.VARCHAR, true);
		parameters[3] = new OracleCallbackBatchParameter( callBackSafx42);
		return parameters;
	}
	
	/**
	 * @deprecated Valor recuperado via Procedure para safx 2013. Para safx08 ainda pendente
	 * @since 12/09/2007
	 * @param dynamicBean
	 * @return
	 */
	private String getVBase(DynamicBean dynamicBean){
		SnParametroBean parametroBean = new SnParametroBean();
		SnParametroKey parametroKey = new SnParametroKey();
		parametroKey.setIdEmpresa(dynamicBean.get("idEmpresa").toString());
		parametroKey.setNomeParametro("CEN_BASE");
		parametroBean.setCompositeKey(parametroKey);
		String saida="";
		ArrayList < SnParametroBean > list = (ArrayList < SnParametroBean >) super.search("lstSnParametroIdEmpresa", parametroBean);
		for (SnParametroBean bean : list) {
			saida = bean.getVlrParametroStr().toString();
		}
		return saida;
	}

	/**
	 * Apaga o diretório/arquivos antes de iniciar a geracao dos arquivos.
	 */
	private boolean apagaDiretorio(final DynamicBean dynamicBean) {
		try {
			String nomeDir = this.geraPathArquivo(
					dynamicBean.get("localDestinoArquivo").toString(),
					this.geraNomeDiretorio(dynamicBean));
			
			File file = new File(nomeDir);
			return deleteDir(file);
		} catch (Exception e) {
			this.executaLog(super.getMessage(EmissaoResources.ERRO_DELETAR_DIRETORIO), e);
			throw new BaseFailureException(super.getMessage(EmissaoResources.ERRO_DELETAR_DIRETORIO), e);
		}
	}

	/**
	 * Apaga diretório, subdiretório e arquivos do path enviado no parametro.
	 * @since 14/08/2007
	 * @param dir path que vai ser deletado
	 * @return retorna true quando deletar o diretório/arquivos
	 */
	private boolean deleteDir(final File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// The directory is now empty so delete it
		return dir.delete();
	}

	

	/**
	 * 
	 * @since 25/09/2007
	 * @param localDestino
	 * @param nomeDiretorioArquivo
	 * @return
	 */
	private String geraPathArquivo(final String localDestino, final String nomeDiretorioArquivo){
		StringBuilder builder = new StringBuilder();
		builder.append(localDestino);
		builder.append(File.separator);
		builder.append(nomeDiretorioArquivo);
		return builder.toString();
	}
	
	/**
	 * @author Everson dos Santos
	 * @since 17/08/2007
	 * @param dynamicBean parametros encapsulados. cidContrato e ccAnoMesEmissao
	 * @return retorna diretorio e subdiretorio onde vai ser salvo os arquivos
	 */
	private String geraNomeDiretorio(final DynamicBean dynamicBean) {
		final StringBuilder builder = new StringBuilder();
		try {
			builder.append(File.separator);
			builder.append(dynamicBean.get("cidContrato").toString());
			builder.append(File.separator);
			builder.append(dynamicBean.get("ccAnoMesEmissao").toString());
			builder.append(File.separator);
		} catch (Exception i) {
			this.executaLog(super.getMessage(EmissaoResources.ERRO_GERAR_NOME_DIRETORIO), i);
			throw new BaseFailureException(super.getMessage(EmissaoResources.ERRO_GERAR_NOME_DIRETORIO), i);
		}
		return builder.toString();
	}

	 /**
     * Salva no banco dados referentes ao controle do arquivo
     * 
     * @since 07/08/2007
     * @param dynamicBean
     */
	protected void controleArquivos(final DynamicBean dynamicBean) {
    	try{
	    	SnConfiguracaoArquivoBean confArquivo = new  SnConfiguracaoArquivoBean();
			ConfiguracaoArquivoService arquivoService = getService(ConfiguracaoArquivoService.class);
			
			confArquivo = arquivoService.selecionarConfiguracaoArquivo("MSAF");
			if(confArquivo == null)
				throw new EmissaoBusinessResourceException(super.getMessage(EmissaoResources.ERRO_CONSULTA_CONF_ARQUIVO)+": MSAF", new NullPointerException());
			
			StatusArquivoService statusArquivoService = super.getService(StatusArquivoService.class);
			SnStatusArquivoBean statusArquivo = statusArquivoService.selecionar(EMISSAO_PROCESSADO.getChaveSigla());
			if(statusArquivo == null)
				throw new EmissaoBusinessResourceException(super.getMessage(EmissaoResources.ERRO_CONSULTA_STATUS_ARQUIVO), new NullPointerException());
			
			SnCidadeOperadoraBean cidadeOperadoraBean = new SnCidadeOperadoraBean();
	    	cidadeOperadoraBean.setCidContrato(dynamicBean.get("cidContrato").toString());
			
	    	final String dbService = super.getUserSession().getCurrentDbService();
	        SnControleArquivoBean controleArquivo = new SnControleArquivoBean();
	        controleArquivo.setConfiguracaoArquivo(confArquivo);          
	        controleArquivo.setDhEntrada(new Date());
	        controleArquivo.setDhProcessamento(new Date());
	        controleArquivo.setUsuarioExec(userInfo.getUserId());
	        controleArquivo.setNomeArquivo(dynamicBean.get("nomeArq").toString());
	        controleArquivo.setCidContrato(cidadeOperadoraBean);
	        controleArquivo.setStatusArquivo(statusArquivo);
	        super.insert(controleArquivo, dbService);
	        
	        Collection < SnControleArquivoBean > controleArquivoInserido = 
	        	(ArrayList < SnControleArquivoBean >) super.search("lstArquivoByName", controleArquivo);
	        
	        if(!controleArquivoInserido.isEmpty()){
	        	for(SnControleArquivoBean arq : controleArquivoInserido )
	        		this.transporteArquivoTask(arq);	      		
	        }else{
	        	throw new EmissaoBusinessResourceException(super.getMessage(EmissaoResources.ERRO_REGISTRO_CONTROLE_ARQUIVO));
	        }
    	}
    	catch(Exception e){
    		throw new EmissaoBusinessResourceException(super.getMessage(EmissaoResources.ERRO_CONTROLE_ARQUIVO), e);
    	}
    }
	/**
	 * Reutiliza componente para efetuar transporte de arquivos gerados
	 * @since 15/10/2007
	 * @param arquivoBean
	 */
	private void transporteArquivoTask(final SnControleArquivoBean arquivoBean){
		try{
			String currentDBService = super.getUserSession ().getCurrentDbService();
	        String currentDBIdentifier = currentDBService.substring(GeralConstants.PREFIXO_DATABASE_SERVICE.length ());
	        Long idFila = (Long) super.getPrincipalProperties().get(TaskConstants.ID_FILA);
	        
	        HashMap<String, String> parameters = new HashMap<String, String>();
	        parameters.put("idControleArquivo", arquivoBean.getIdControleArquivo().toString());
	        parameters.put("cidContrato", arquivoBean.getCidContrato().getCidContrato().toString());

	        TaskService taskService = super.getService(TaskService.class );
	        taskService.submitTaskToFila(EMISSAO_TRANSPORTE, parameters, super.getUserSession ().getUserId(), currentDBIdentifier, idFila);
		
		}catch(Exception e){
			throw new EmissaoBusinessResourceException(super.getMessage(EmissaoResources.ERRO_TRANSPORTE_TASK), e);
		}
	}
	
}