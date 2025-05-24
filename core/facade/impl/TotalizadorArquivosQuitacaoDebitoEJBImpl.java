package br.com.netservicos.novosms.emissao.core.facade.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.com.netservicos.core.bean.sn.SnConfiguracaoArquivoBean;
import br.com.netservicos.core.bean.sn.SnControleArquivoBean;
import br.com.netservicos.core.bean.sn.SnLoteBean;
import br.com.netservicos.core.bean.sn.SnParametroBean;
import br.com.netservicos.core.bean.sn.SnParametroKey;
import br.com.netservicos.core.bean.sn.SnStatusArquivoBean;
import br.com.netservicos.core.bean.sn.SnTipoControleArquivoBean;
import br.com.netservicos.framework.core.bean.DynamicBean;
import br.com.netservicos.framework.core.task.execption.BaseTaskException;
import br.com.netservicos.framework.transporte.exception.FailReason;
import br.com.netservicos.framework.util.attachments.messages.AttachmentMessageLevel;
import br.com.netservicos.framework.util.attachments.messages.BasicAttachmentMessage;
import br.com.netservicos.novosms.emissao.core.facade.TotalizadorArquivosQuitacaoDebitoService;
import br.com.netservicos.novosms.geral.arquivos.BaseFileTransportSecurity;
import br.com.netservicos.novosms.geral.constants.GeralConstants;

/**
 * <P>
 * <B> Description : </B> EJB responsável pela regra de negócio da geração de
 * totalizador de cartas de quitação anual de débito. <BR>
 * </P>
 * <P>
 * <B> Issues : </B>
 * 
 * 
 * @since 15/02/2011
 * 
 * @ejb.bean name="TotalizadorArquivosQuitacaoDebitoEJB" type="Stateless"
 *           display-name="TotalizadorArquivosQuitacaoDebitoEJB"
 *           description="Contém a regra de negócio do totalizador de arquivos
 *           de Declaração Anual Quitacao" view-type="both"
 *           jndi-name="netservicos/ejb/emissao/TotalizadorArquivosQuitacaoDebitoEJB"
 *           local-jndi-name=
 *           "netservicos/ejb/local/emissao/TotalizadorArquivosQuitacaoDebitoEJB"
 *           transaction-type="Container"
 * @ejb.interface local-extends="javax.ejb.EJBLocalObject"
 *                extends="javax.ejb.EJBObject"
 * @ejb.home local-extends="javax.ejb.EJBLocalHome" extends="javax.ejb.EJBHome"
 */
public class TotalizadorArquivosQuitacaoDebitoEJBImpl extends AbstractEmissaoEJBImpl implements
																TotalizadorArquivosQuitacaoDebitoService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3583215343319913385L;
	/**
	 * LOG.
	 */
	private static final Log LOG = LogFactory.getLog(TotalizadorArquivosQuitacaoDebitoEJBImpl.class);
	/**
	 * String.
	 */
	private static final String MSG_DISP_DADOS = "Não foi possível disponibilizar os dados solicitados. ";
	/**
	 * String.
	 */
	private static final String MSG_DT_FIM_DT_INI = "Data inicial maior que data final";
	/**
	 * String.
	 */
	private static final String IS_AUTOSYS = "isAutosys";
	/**
	 * Chave para identificador do arquivo.
	 */
	private static final String ID_CONFIG_ARQUIVO = "idConfiguracaoArquivo";
	/**
	 * Chave para nome arquivo.
	 */
	private static final String NOME_ARQUIVO = "nomeArquivo";
	/**
	 * Período máximo de geração de totalizador via On-going.
	 */
	private static final Integer DIAS_MES = Integer.valueOf("31");
	/**
	 * Status do Arquivo.
	 */
	private static final String TRANSFOK = "TRANSFOK";
	/**
	 * 1 dia = 86 400 000 milisegundos 
	 */
	private static final long MILISECONDS = 86400000L;

	/**
	 * <p>
	 * <b>Description:</b><br/>
	 * Método Responsável por validar a quantidade de dias de geração do arquivo.
	 * <p>
	 * 
	 * @param dataInicio
	 * @param dataFim
	 * @since 17/02/2011
	 */
	private void validaQuantidadeDias(final String dataInicio, final String dataFim) {
		LOG.info("Validando periodos");
		final int totalDias = this.calcularDiferencaDias(dataInicio, dataFim);
		LOG.debug("Diferencia dos dias:" + totalDias);
		if (totalDias > DIAS_MES) {
			LOG.error(new BasicAttachmentMessage(MSG_DISP_DADOS
							+ "O período máximo permitido é de 31 dias",
					AttachmentMessageLevel.ERROR));
			throw new IllegalArgumentException(MSG_DISP_DADOS
							+ "O período máximo permitido é de 31 dias");
		}
	}

	/**
	 * <p>
	 * <b>Description:</b><br/>
	 * Método Responsável por validar um período.
	 * <p>
	 * 
	 * @param dataInicio
	 * @param dataFim
	 * @return true, se a data for válida.
	 * @since 17/02/2011
	 */
	private boolean isPeriodoValido(final String dataInicial, final String dataFinal) {
		final SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", new Locale("pt", "BR"));
		Date dtIni = null;
		Date dtFim = null;
		try {
			dtIni = fmt.parse(dataInicial);
			dtFim = fmt.parse(dataFinal);
		} catch (final ParseException parseExc) {
			LOG.error(new BasicAttachmentMessage("Formato de data invalido.",
					AttachmentMessageLevel.ERROR));
			throw new IllegalArgumentException("Formato de data invalido.",parseExc);
		}
		return !dtFim.before(dtIni);
	}

	/**
	 * <p>
	 * <b>Description:</b><br/>
	 * Metodo responsavel por gerar o arquivo totalizador de lotes.
	 * <p>
	 * 
	 * @param dynaBean
	 * @return dynaBean
	 * @since 17/02/2011
	 * @ejb.interface-method view-type = "both"
	 */
	@SuppressWarnings("unchecked")
	public DynamicBean gerarArquivoTotalizadorLotes(final DynamicBean dynaBean) {
		LOG.info("inicio da geração dos totalizadores");
		LOG.info("Obtendo data atual do banco de dados.");
		final String dataAtual = this.obterDataAtual();
		LOG.debug("Data atual do banco de dados: " + dataAtual);
		dynaBean.set("dataAtual", dataAtual);
		final List<DynamicBean> lstTotal = new ArrayList<DynamicBean>();
		final String[] dbServices = userInfo.getSelectedDbServices();
		SnConfiguracaoArquivoBean configBean = null;
		final String dbServiceOrigem = this.getCurrentDBService();
		dynaBean.put("dbServiceOrigem", dbServiceOrigem);
		/* percorre todos as bases. */
		for (final String dbService : dbServices) {
			LOG.debug("dbService: " + dbService);
			getUserSession().setCurrentDbService(dbService);
			/* busca o tipo de controle arquivo de qdeb. */
			configBean = this.obterConfiguracaoArquivo(GeralConstants.SIGLA_ARQUIVO_PAGAMENTO);
			dynaBean.put(ID_CONFIG_ARQUIVO, configBean.getIdConfiguracaoArquivo());
			final DynamicBean bean = this.obterParametrosData(dynaBean);
			bean.put(ID_CONFIG_ARQUIVO, configBean.getIdConfiguracaoArquivo());
			/* busca arquivos enviados no periodo pesquisado. */
			final List<Object[]> lstPrincipal = this.search("lstArquivosEnviadosPrintHouse", bean, Boolean.FALSE);
			for (Object[] registro : lstPrincipal) {
				final DynamicBean localBean = new DynamicBean();
				 Long quantidade = (Long) registro[2];
				if((Long) registro[2] == null){
					quantidade = 0L;
				}
				if (isLoteCarta((Integer) registro[0])) {
					localBean.set("qtdeCorreio", quantidade);
					localBean.set("qtdeEmail", Long.valueOf(0));
				} else {
					localBean.set("qtdeCorreio", Long.valueOf(0));
					localBean.set("qtdeEmail", quantidade);
				}
				localBean.set(NOME_ARQUIVO, (String) registro[1]);
				localBean.set("totalGeral", quantidade);
				lstTotal.add(localBean);
			}
		}
		configBean = this.obterConfiguracaoArquivo("LOGCARTAQDEBANUAL");
		String nomeArquivo = configBean.getMascaraArquivo();
		final SimpleDateFormat fmt = new SimpleDateFormat("ddMMyyyy_HHmmss", new Locale("pt", "BR"));
		final String dataAtualArquivo = fmt.format(Calendar.getInstance()
				.getTime());
		nomeArquivo = nomeArquivo.replace("DDMMAAAA_HHMISS", dataAtualArquivo);
		final String caminhoArquivo = configBean.getLocalOrigemArquivo()
				.getDescricaoLocal()
				+ nomeArquivo;
		dynaBean.set(NOME_ARQUIVO, nomeArquivo);
		dynaBean.set("caminhoArquivo", caminhoArquivo);
		dynaBean.set("lstTotal", lstTotal);
		return dynaBean;
	}

	/**
	 * <p>
	 * <b>Description:</b><br/>
	 * Metodo responsavel por enviar o arquivo totalizador de lotes.
	 * <p>
	 * 
	 * @param dynaBean
	 * @since 17/02/2011
	 * @ejb.interface-method view-type = "both"
	 */
	public void enviarArquivoTotalizadorLotes(final DynamicBean dynaBean) {
		LOG.info("Enviando o arquivo totalizador de lotes");
		LOG.debug("isAutosys: " + dynaBean.get(IS_AUTOSYS));
		if (dynaBean.get(IS_AUTOSYS) != null
				&& (Boolean) dynaBean.get(IS_AUTOSYS)) {
			this.atualizaHorarioExecucao((String) dynaBean.get("dataAtual"));
		}
		this.getUserSession().setCurrentDbService((String) dynaBean.get("dbServiceOrigem"));
		final SnConfiguracaoArquivoBean confArqBean = this
				.obterConfiguracaoArquivo("LOGCARTAQDEBANUAL");
		this.subirArquivoTotalizador((String) dynaBean.get(NOME_ARQUIVO),
				confArqBean, (String) dynaBean.get("idFila"));
		final SnControleArquivoBean controleArq = new SnControleArquivoBean();
		controleArq.setNomeArquivo((String) dynaBean.get(NOME_ARQUIVO));
		final Date date = Calendar.getInstance().getTime();
		controleArq.setDhGeracao(date);
		controleArq.setDhProcessamento(date);
		controleArq.setDhSaida(date);
		controleArq.setUsuarioExec(getUserSession().getUserId());
		controleArq.setStatusArquivo(new SnStatusArquivoBean(
				SnStatusArquivoBean.Sigla.TRANSFERIDO));
		controleArq.setConfiguracaoArquivo(confArqBean);
		this.insert(controleArq,(String) dynaBean.get("dbServiceOrigem"));
	}

	/**
	 * <p>
	 * <b>Description:</b><br/>
	 * Metodo responsavel por obter dados de configuração do arquivo.
	 * <p>
	 * 
	 * @param String
	 * @return SnConfiguracaoArquivoBean
	 * @since 17/02/2011
	 */
	@SuppressWarnings("unchecked")
	private SnConfiguracaoArquivoBean obterConfiguracaoArquivo(final String sigla) {
		LOG.debug("sigla: " + sigla);
		final SnTipoControleArquivoBean tpControle = new SnTipoControleArquivoBean();
		tpControle.setSigla(sigla);
		SnConfiguracaoArquivoBean snConf = new SnConfiguracaoArquivoBean();
		snConf.setTipoControleArquivo(tpControle);
		final List<SnConfiguracaoArquivoBean> lstConfigArquivo = this.search(
				SnConfiguracaoArquivoBean.CONFIGURACAO_ARQUIVO_BYSIGLA, snConf, false);
		if (lstConfigArquivo != null && !lstConfigArquivo.isEmpty()) {
			snConf = lstConfigArquivo.get(0);
		}
		return snConf;
	}

	/**
	 * <p>
	 * <b>Description:</b><br/>
	 * Metodo responsavel por verificar se o lote é carta.
	 * <p>
	 * 
	 * @param String
	 * @return true, se lote for carta.
	 * @since 17/02/2011
	 */
	private boolean isLoteCarta(final Integer idLote) {
		LOG.info("Validando se o lote é carta.");
		final SnLoteBean lote = (SnLoteBean) this.getCurrentDAO()
				.findByPrimaryKey(SnLoteBean.class, idLote);
		boolean retorno = false;
		if (lote != null
				&& ("PRVACRQDEB".equals(lote.getSnTipoLoteBean()
						.getSgTipoLote()) || "SGVACRQDEB".equals(lote
						.getSnTipoLoteBean().getSgTipoLote()))) {
			retorno = true;
		}
		return retorno;
	}

	/**
	 * <p>
	 * <b>Description:</b><br/>
	 * Metodo responsavel por definir os parametros de data.
	 * <p>
	 * 
	 * @param DynamicBean
	 * @return DynamicBean
	 * @since 17/02/2011
	 */
	private DynamicBean obterParametrosData(final DynamicBean dynaBean) {
		final DynamicBean bean = new DynamicBean();
		LOG.info("Obtendo parametros data de geração");
		final Boolean isAutoSys = (Boolean) dynaBean.get(IS_AUTOSYS);
		if (Boolean.TRUE.equals(isAutoSys)) {
			final String dataAtual = (String) dynaBean.get("dataAtual");
			final String ultimaGeracao = this.buscaDtUltimaGeracao();
			if (this.isPeriodoValido(ultimaGeracao, dataAtual)) {
				LOG.debug("dataInicio: " + dataAtual);
				LOG.debug("dataFim: " + ultimaGeracao);
				LOG.info("Sem datas, origem autosys");
				bean.set("dataInicio", ultimaGeracao);
				bean.set("dataFim", dataAtual);
				LOG.debug("dataInicio: " + ultimaGeracao);
				LOG.debug("dataFim: " + dataAtual);
				bean.put(ID_CONFIG_ARQUIVO, dynaBean.get(ID_CONFIG_ARQUIVO));
				this.verificarArquivosPendentes(bean);
				if (this.verificarUltimaGeracaoLog(dataAtual, ultimaGeracao)) {
					LOG.error(new BasicAttachmentMessage("Arquivo de Log já foi gerado.",
							AttachmentMessageLevel.ERROR));
					throw new BaseTaskException("Arquivo de Log já foi gerado.");
				}
			} else {
				LOG.error(new BasicAttachmentMessage(MSG_DISP_DADOS	+ MSG_DT_FIM_DT_INI,
						AttachmentMessageLevel.ERROR));
				throw new IllegalArgumentException(	MSG_DISP_DADOS + MSG_DT_FIM_DT_INI);
			}
		} else {
			LOG.info("Existem datas, origem on-going");
			final String dataInicio = (String) dynaBean.get("dataInicio");
			final String dataFim = (String) dynaBean.get("dataFim");
			bean.set("dataInicio", dataInicio);
			bean.set("dataFim", dataFim);
			if (isPeriodoValido(dataInicio, dataFim)) {
				LOG.debug("dataInicio: " + dataInicio);
				LOG.debug("dataFim: " + dataFim);
				this.validaQuantidadeDias(dataInicio, dataFim);
			} else {
				LOG.error(new BasicAttachmentMessage(MSG_DISP_DADOS	+ MSG_DT_FIM_DT_INI,
						AttachmentMessageLevel.ERROR));
				throw new IllegalArgumentException(	MSG_DISP_DADOS+ MSG_DT_FIM_DT_INI);
			}
		}
		return bean;
	}

	/**
	 * <p>
	 * <b>Description:</b><br/>
	 * Metodo responsavel por verificar a data de última geração do arquivo de log.
	 * <p>
	 * 
	 * @param String
	 * @param String
	 * @return true, se já foi gerado arquivo para data informada.
	 * @since 17/02/2011
	 */
	private boolean verificarUltimaGeracaoLog(final String dataAtual,final String ultimaGeracao) {
		LOG.info("Verificando a data de última geração do arquivo de log.");
		boolean hasLog = false;
		try {
			final SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy",
					new Locale("pt", "BR"));
			final Date dtAtual = fmt.parse(ultimaGeracao);
			final Date dtFinal = fmt.parse(dataAtual);
			hasLog = dtAtual.equals(dtFinal);
		} catch (final ParseException excep) {
			LOG.error(new BasicAttachmentMessage("Data inicial ou final inválida.",
					AttachmentMessageLevel.ERROR));
			throw new BaseTaskException(excep);
		}
		return hasLog;
	}

	/**
	 * <p>
	 * <b>Description:</b><br/>
	 * Metodo responsavel por verificar se existe arquivos pendentes de processamento.
	 * <p>
	 * 
	 * @param DynamicBean
	 * @since 17/02/2011
	 */
	private void verificarArquivosPendentes(final DynamicBean dynaBean) {
		LOG.info("Verificando Arquivos Pendentes.");
		dynaBean.put("sgStatusArquivo", TRANSFOK);
		final List<?> list = this.search("lstVerificarArquivosPendentes",
				dynaBean, false);
		if (!list.isEmpty()) {
			final Long count = (Long) list.get(0);
			if (count > 0) {
				LOG.error(new BasicAttachmentMessage(
						"Existem arquivos pendentes de processamento.",
						AttachmentMessageLevel.ERROR));
				throw new BaseTaskException(
						"Existem arquivos pendentes de processamento.");
			}
		}
	}

	/**
	 * <p>
	 * <b>Description:</b><br/>
	 * Metodo responsavel por buscar data de ultima geracao do arquivo.
	 * <p>
	 * 
	 * @return String
	 * @since 17/02/2011
	 */
	private String buscaDtUltimaGeracao() {
		LOG.info("Buscanco data de ultima geração do arquivo.");
		String ultimaExecucao = "";
		final List<SnParametroBean> lstParametro = this
				.buscaParametrosUltimaExecucao();
		if (lstParametro != null && !lstParametro.isEmpty()) {
			ultimaExecucao = lstParametro.get(0).getVlrParametroStr();
		} else {
			LOG.error(new BasicAttachmentMessage(
							"Não foi encontrada data da última execução. Favor informar o período desejado.",
							AttachmentMessageLevel.ERROR));
			throw new BaseTaskException(
					"Não foi encontrada data da última execução. Favor informar o período desejado.");
		}
		return ultimaExecucao;
	}

	/**
	 * <p>
	 * <b>Description:</b><br/>
	 * Metodo responsavel por buscar parametros de ultima execuçao do arquivo.
	 * <p>
	 * 
	 * @return String
	 * @since 17/02/2011
	 */
	@SuppressWarnings("unchecked")
	private List<SnParametroBean> buscaParametrosUltimaExecucao() {
		LOG.info("Buscando parametro de última geração.");
		final SnParametroBean snParametro = new SnParametroBean();
		final SnParametroKey composyteKey = new SnParametroKey();
		composyteKey.setNomeParametro("QDEB_ULT_LOG_CARTA");
		snParametro.setCompositeKey(composyteKey);
		return this.search("lstSnParametroByNomeParametro", snParametro);
	}

	/**
	 * <p>
	 * <b>Description:</b><br/>
	 * Metodo responsavel por atualizar o horario de execução do arquivo.
	 * <p>
	 * 
	 * @param String
	 * @since 17/02/2011
	 */
	private void atualizaHorarioExecucao(final String dataAtual) {
		LOG.info("Atualizando o horario de execução");
		final String[] dbServices = userInfo.getSelectedDbServices();
		for (final String dbService : dbServices) {
			LOG.debug("setCurrentDbService: " + dbService);
			getUserSession().setCurrentDbService(dbService);
			final List<SnParametroBean> lstParametro = buscaParametrosUltimaExecucao();
			if (lstParametro != null) {
				for (final SnParametroBean snParametroBean : lstParametro) {
					snParametroBean.setVlrParametroStr(dataAtual);
					getCurrentDAO().update(snParametroBean);
				}
			}
		}

	}

	/**
	 * <p>
	 * <b>Description:</b><br/>
	 * Metodo responsavel por calcular difenca entre dias.
	 * <p>
	 * 
	 * @param String
	 * @param String
	 * @since 17/02/2011
	 */
	private int calcularDiferencaDias(final String dataInicial,
			final String dataFinal) {
		LOG.info("Calculando diferença de dias entre as datas");
		if (dataInicial == null || dataFinal == null) {
			LOG.error(new BasicAttachmentMessage(
					"Data inicial ou final não pode ser nula.",
					AttachmentMessageLevel.ERROR));
			throw new IllegalArgumentException();
		}
		final SimpleDateFormat fmt = new SimpleDateFormat(
				"dd/MM/yyyy HH:mm:ss", new Locale("pt", "BR"));
		try {
			final Date dtIni = fmt.parse(dataInicial);
			final Date dtFim = fmt.parse(dataFinal);
			int result = -1;
			if (dtIni != null && dtFim != null) {
				final Date dtFinal = org.apache.commons.lang.time.DateUtils
						.truncate(dtIni, Calendar.DAY_OF_MONTH);
				final Date dtAtual = org.apache.commons.lang.time.DateUtils
						.truncate(dtFim, Calendar.DAY_OF_MONTH);
				final long diferenca = dtAtual.getTime() - dtFinal.getTime();
				/* 1 dia = 86 400 000 milisegundos */
				result = (int) (diferenca / MILISECONDS);
			}
			return result;
		} catch (Exception e) {
			LOG.error(new BasicAttachmentMessage(
					"Data inicial ou final inválida.",
					AttachmentMessageLevel.ERROR));
			throw new BaseTaskException(e);
		}
	}

	/**
	 * <p>
	 * <b>Description:</b><br/>
	 * Metodo responsavel por enviar o arquivo para o servidor (Upload).
	 * <p>
	 * 
	 * @param String
	 * @param SnConfiguracaoArquivoBean
	 * @param String
	 * @since 17/02/2011
	 */
	private void subirArquivoTotalizador(final String nomeArq,
			final SnConfiguracaoArquivoBean confArqBean, final String idFila) {
		LOG.info("Subindo Arquivo Totalizador.");
		final BaseFileTransportSecurity transportSecurity = new BaseFileTransportSecurity();
		try {
			LOG.debug(new StringBuilder().append("Chamando TransportSecurity(nomeAquivo: ").append(nomeArq)
					.append(" Configuracao Arquivo: ").append(confArqBean).append(" userInfo: ")
					.append(userInfo).append(" idFila: ").append(idFila).append(")"));
			transportSecurity.fileTransportSecurityUpload(nomeArq, confArqBean,
					userInfo, idFila);
		} catch (Exception e) {
			LOG.error(new BasicAttachmentMessage(
					"Erro ao enviar arquivo totalizador.",
					AttachmentMessageLevel.ERROR));
		}
		if (!transportSecurity.getFailureFile().isEmpty()) {
			final FailReason fail = (FailReason) transportSecurity
					.getFailureFile().iterator().next();
			throw new BaseTaskException(fail.getMessage(), fail.getException());
		}
	}

	/**
	 * <p>
	 * <b>Description:</b><br/>
	 * Metodo responsavel por obter a data atual do banco de dados (sysdate).
	 * <p>
	 * 
	 * @since 17/02/2011
	 * @return String
	 */
	private String obterDataAtual() {
		LOG.info("Obtendo data atual do banco de dados.");
		return (String) this.search("obterDataAtualDatabase",
				new DynamicBean(), false).get(0);
	}
}
