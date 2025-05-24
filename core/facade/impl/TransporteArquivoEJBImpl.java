/**
 * 
 */
package br.com.netservicos.novosms.emissao.core.facade.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import br.com.netservicos.core.bean.sn.SnCidadeOperadoraBean;
import br.com.netservicos.core.bean.sn.SnControleArquivoBean;
import br.com.netservicos.core.bean.sn.SnParametroBean;
import br.com.netservicos.core.bean.sn.SnStatusArquivoBean;
import br.com.netservicos.emissao.bean.ArquivoRemessaTransporteDTO;
import br.com.netservicos.emissao.bean.TransporteArquivos;
import br.com.netservicos.emissao.bean.TransporteDTO;
import br.com.netservicos.framework.transporte.BaseTransporteArquivoTransacao;
import br.com.netservicos.framework.transporte.ProtocoloTransporteEnum;
import br.com.netservicos.framework.transporte.exception.FailReason;
import br.com.netservicos.framework.transporte.exception.TransporteArquivoException;
import br.com.netservicos.framework.transporte.listener.DefaultTransporteArquivoListener;
import br.com.netservicos.framework.transporte.listener.TransporteArquivoListener;
import br.com.netservicos.framework.transporte.name.DefaultNameBuilder;
import br.com.netservicos.framework.util.attachments.messages.AttachmentMessageLevel;
import br.com.netservicos.framework.util.attachments.messages.BasicAttachmentMessage;
import br.com.netservicos.netframework.security.encrypt.NETFrameworkEncrypter;
import br.com.netservicos.netframework.security.encrypt.NETFrameworkEncrypterFactory;
import br.com.netservicos.novosms.emissao.core.facade.TransporteArquivoService;


/**
 * <P><B>Description :</B><BR>
 * 	RF190 – Transporte de arquivos, via FTP
 * </P>
 * <P>
 * <B>
 * Issues : <BR>
 * None
 * </B>
 * @author mbellini
 * @since 10/08/2007
 * @version $Revision: 1.1 $
 *
 *</P>
 *
 * @ejb.bean name = "TransporteArquivoEJB"
 *          type = "Stateless"
 *          display-name = "TransporteArquivoEJB"
 *          description  = "TransporteArquivoEJB EJB"
 *          view-type    = "both"
 *          local-jndi-name ="netservicos/novosms/emissao/ejb/local/TransporteArquivoEJB"
 *          jndi-name="netservicos/novosms/emissao/ejb/TransporteArquivoEJB"
 *          transaction-type="Container"
 *          generate="true"
 *
 * @ejb.interface
 *   local-extends="javax.ejb.EJBLocalObject"
 *   extends="javax.ejb.EJBObject"
 *
 *
 * @ejb.home
 *   local-extends="javax.ejb.EJBLocalHome"
 *   extends="javax.ejb.EJBHome"
 * 
 */ 
public class TransporteArquivoEJBImpl extends EmissaoBaseServiceImpl implements TransporteArquivoService  {

	private Log logger = org.apache.commons.logging.LogFactory.getLog(TransporteArquivoEJBImpl.class);
	
	private static final String TRAN = "TRANSFOK";
	private static final String TRANSERRO = "TRANSERRO";
	private static final String TRANSFPEN = "TRANSFPEN";
	private SimpleDateFormat format = new SimpleDateFormat("HHmmss");

	/* (non-Javadoc)
	 * @see br.com.netservicos.novosms.emissao.facade.TransporteArquivo#transportarArquivos(java.util.List, java.lang.Integer)
	 */
	public void transportarArquivos(
			List<ArquivoRemessaTransporteDTO> transportesArquivoDTO,
			Integer idFila) {
	}
	
	/**
	 * @param transportesArquivos
	 * @throws TransporteArquivoException
	 * 
	 * @ejb.interface-method view-type = "both"
     * @ejb.transaction type="Required"
	 */
	public void transportarArquivosUpload(
			List<TransporteArquivos> transportesArquivos) throws TransporteArquivoException {
		
		for(TransporteArquivos trans: transportesArquivos) {
			transportarArquivoUpload(trans.getOrigem(), trans.getDestino(), trans.getPathLog()	);
		}
		
	}
	
	/**
	 * @param origem Contém as informações do arquivo no contexto local
	 * @param destino Contém as informações do arquivo fora do contexto local
	 * @param transporteArquivoEvento  o ouvinte para os eventos de erro e para o evento de sucesso na transferencia. 
	 * @param filter o filtro que será utilizado para filtrar os arquivos que serão enviados
	 * @throws TransporteArquivoException
	 * 
	 * @ejb.interface-method view-type = "both"
     * @ejb.transaction type="Required"
	 */
	public String transportarArquivoUpload(
			TransporteDTO origem, TransporteDTO destino, 
			String pathListener ) throws TransporteArquivoException {
			
		BaseTransporteArquivoTransacao transporteArquivo = new BaseTransporteArquivoTransacao();
		SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
		String data = sdf.format(new Date());
		
		//cria o DefaultTransporteArquivoListener
		Collection falhas  = new ArrayList();
		Collection results = new ArrayList();
		File f = new File(origem.getDiretorio() + File.separator + origem.getNomeArquivo());

		TransporteArquivoListener transporteArquivoEvento = 
				new DefaultTransporteArquivoListener( falhas, results, f);
		
		//cria o DefaultTransporteArquivoFiltro
		//TransporteArquivoFiltro filter = new DefaultTransporteArquivoFiltro("*");
		
		//TODO alterar isto para máscara específica ou passar a máscara
		//TransporteNameBuider nameBuilder = new TransporteNameBuider(format.format(new Date()));
		DefaultNameBuilder nameBuilder = new DefaultNameBuilder(destino.getIdFila().toString() + data);
		
		Short porta = new Short( destino.getPorta() );

		// Decriptografa a senha
		NETFrameworkEncrypter desEncrypter = NETFrameworkEncrypterFactory.getEncrypter(
				userInfo.getCurrentSystemIdentifier()
				, userInfo.getCurrentApplicationIdentifier()
				);

		String senha = desEncrypter.decrypt(destino.getSenha());

		// Efetua transporte do arquivo
		transporteArquivo.transportarArquivoUpload(
				destino.getProtocolo()
				, destino.getServidor()
				, porta
				, destino.getUsuario()
				, senha
				, destino.getDiretorio()
				, origem.getDiretorio()
				, origem.getNomeArquivo()
				, destino.getNomeArquivo()
				, nameBuilder
				, destino.getBinario().booleanValue()
				, transporteArquivoEvento
				);
		
		if(!falhas.isEmpty()) {
			
			 FailReason fail = (FailReason) falhas.iterator().next();
			 logger.error(new BasicAttachmentMessage(fail.getMessage(),AttachmentMessageLevel.ERROR));
			 
			 return fail.getMessage();
			
		}
		
		logger.info(new BasicAttachmentMessage("sucesso",AttachmentMessageLevel.INFO));
		return null;

	}
	
	/**
	 * @ejb.interface-method view-type = "both"
     * @ejb.transaction type="Required"
	 */
	public void gravaErroStatus(String erro, SnControleArquivoBean controle) {

		SnStatusArquivoBean statusArquivo = obtemStatus(TRANSERRO);

		controle = (SnControleArquivoBean)super.find(controle);
		controle.setStatusArquivo(statusArquivo);

		super.update(controle, getUserSession().getCurrentDbService());

	}
	
	/**
	 * @ejb.interface-method view-type = "both"
     * @ejb.transaction type="Required"
	 */
	public void gravaStatus( SnControleArquivoBean controle, String status) {

		SnStatusArquivoBean statusArquivo = obtemStatus(status);
		controle = (SnControleArquivoBean)super.find(controle);
		controle.setStatusArquivo(statusArquivo);

		super.update(controle, getUserSession().getCurrentDbService());
		
	}
	
	private SnStatusArquivoBean obtemStatus(String descStatus) {
		SnStatusArquivoBean status = new SnStatusArquivoBean();
		status.setSiglaStatus(descStatus);
		List<SnStatusArquivoBean> list = search(status.LST_STATUS_BY_SIGLA, status);
		if(!list.isEmpty()) {
			return list.get(0);
		}
		
		//não encontrou
		return null;
	}
	
	public SnControleArquivoBean obtemControle(Long idControleArquivo, String cidContrato){
			
		SnControleArquivoBean controle = new SnControleArquivoBean();
		controle.setIdControleArquivo(idControleArquivo);
		SnCidadeOperadoraBean cidade = new SnCidadeOperadoraBean();
		cidade.setCidContrato(cidContrato);
		controle.setCidContrato(cidade);
		controle = (SnControleArquivoBean) find(controle);

		return controle;
	}
	
	public TransporteArquivos preencheParametros(SnControleArquivoBean controle){
		TransporteArquivos transporte = new TransporteArquivos();
		TransporteDTO origem = new TransporteDTO(); 
		TransporteDTO destino = new TransporteDTO(); 
		String pathFilter;
		String pathLog;
		
		origem.setDiretorio(controle.getConfiguracaoArquivo().getLocalOrigemArquivo().getDescricaoLocal());
		origem.setNomeArquivo(controle.getNomeArquivo());
		StringBuffer nomeMascara = new StringBuffer("[");
		nomeMascara.append(controle.getNomeArquivo().substring(0, controle.getNomeArquivo().length() - 4));
		nomeMascara.append("]");
		nomeMascara.append(controle.getConfiguracaoArquivo().getMascaraArquivo());
		origem.setMascara(nomeMascara.toString());
		
		ProtocoloTransporteEnum protocolo = ProtocoloTransporteEnum.valueOf(controle.getConfiguracaoArquivo().getServidorArquivo().getProtocolo().toUpperCase());
		destino.setProtocolo(protocolo);
		destino.setServidor(controle.getConfiguracaoArquivo().getServidorArquivo().getIpServidor());
		destino.setPorta(new Integer(controle.getConfiguracaoArquivo().getServidorArquivo().getPorta()).toString());
		//destino.setUsuario("ftp");
		//destino.setSenha("ftp");
		destino.setUsuario(controle.getConfiguracaoArquivo().getServidorArquivo().getFtpUsuario());
		destino.setSenha(controle.getConfiguracaoArquivo().getServidorArquivo().getFtpSenha());
		
		
		destino.setBinario(true);
		destino.setDiretorio(controle.getConfiguracaoArquivo().getLocalDestinoArquivo().getDescricaoLocal());
		destino.setNomeArquivo(controle.getNomeArquivo());
		
		transporte.setOrigem(origem);
		transporte.setDestino(destino);
		
		return transporte;
	}

	/**
	 * @param idControleArquivo
	 * @param cidContrato
	 * @throws TransporteArquivoException
	 * 
	 * @ejb.interface-method view-type = "both"
     * @ejb.transaction type="Required"
	 */
	public void efetuaUpload(Long idControleArquivo, String cidContrato, Long idFila) throws TransporteArquivoException {

		String pathLog = "arquivoLogs.txt"; 
		
		SnControleArquivoBean controle = obtemControle(idControleArquivo,cidContrato);
		
		boolean transportar = this.transportar(controle.getCidContrato(), "TRANSPORTE_ATIVO");
		
		if (transportar) {
		
			logger.info(new BasicAttachmentMessage("Task de transporte ativa.", AttachmentMessageLevel.INFO));
			
			TransporteArquivos params = preencheParametros(controle);
			params.getDestino().setIdFila(idFila.intValue());
			String ret = null;
			
			TransporteArquivoService service = getService(TransporteArquivoService.class);
			
			try {
				
				// Altera status do controle arquivo para transportando em transação separada
				service.gravaStatus(controle, TRANSFPEN);
				
				ret = transportarArquivoUpload(params.getOrigem(),params.getDestino(),pathLog);
				
				if(ret != null) {
					// Altera status do controle arquivo para Erro em transação separada
					service.gravaErroStatus(ret, controle);
				}
				else {
					
					controle.setDhSaida(new Date());
					
					// Altera status do controle arquivo para Transportado em transação separada
					service.gravaStatus(controle, TRAN);
				}
				
			} catch (TransporteArquivoException e) {
				
				// Altera status do controle arquivo para Erro em transação separada
				service.gravaErroStatus(ret, controle);
				
				logger.error(new BasicAttachmentMessage(e.getMessage(),AttachmentMessageLevel.ERROR));
				
				throw new TransporteArquivoException(e);
				
			}
			
		} else {
			logger.info(new BasicAttachmentMessage("Task de transporte inativa.", AttachmentMessageLevel.INFO));
		}
		
	}
	
	private boolean transportar(SnCidadeOperadoraBean cidOperadora, String parametro ) {
		
		SnParametroBean snParametroBean = this.obterParametro(cidOperadora, parametro);
		if (snParametroBean == null
				|| snParametroBean.getVlrParametroStr() == null
				|| snParametroBean.getVlrParametroStr().equals("N")) {
			return false;
		} else {
			return true;
		}

	}
	
	/**
	 * Obtem parametro especificado no parametro
	 * @param snCidadeOperadoraBean
	 * @param nomeParametro
	 * @return parametro pesquisado
	 */
	@SuppressWarnings("unchecked")
	private SnParametroBean obterParametro(SnCidadeOperadoraBean snCidadeOperadoraBean, String nomeParametro) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(SnParametroBean.class);
		criteria.createAlias("empresa", "empresa");
		criteria.add(Restrictions.eq("empresa.cidContrato", snCidadeOperadoraBean.getCidContrato()));
		criteria.add(Restrictions.eq("nomeParametro", nomeParametro));
		List<SnParametroBean> lstParametro = (List<SnParametroBean>)getCurrentDAO().select(criteria);
		
		SnParametroBean snParametroBean = new SnParametroBean();
		if (lstParametro != null && !lstParametro.isEmpty() && lstParametro.size() == 1) {
			
			snParametroBean = (SnParametroBean) lstParametro.iterator().next();
			
			return snParametroBean;
			
		}
		
		return null;

	}
	
}
