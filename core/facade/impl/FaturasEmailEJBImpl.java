package br.com.netservicos.novosms.emissao.core.facade.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.CreateException;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import br.com.netservicos.core.bean.sn.SnFaturaEmailRejeitadoBean;
import br.com.netservicos.core.bean.sn.SnTipoResolEmailRejBean;
import br.com.netservicos.framework.core.bean.Bean;
import br.com.netservicos.framework.core.bean.DynamicBean;
import br.com.netservicos.framework.service.report.BaseReportService;
import br.com.netservicos.framework.util.BaseConstants;
import br.com.netservicos.framework.util.loader.ResourceLoader;
import br.com.netservicos.novosms.emissao.core.facade.FaturasEmailService;
import br.com.netservicos.novosms.emissao.dto.FaturaEmailRejeitadoDTO;


/**
 * @author Faturas Por Email
 * @since 09/2009
 * @version 1.0
 * 
 * @ejb.bean name="FaturasEmailEJB" type="Stateless"
 *           display-name="FaturasEmailEJB"
 *           description="FaturasEmailEJB Session EJB Stateless"
 *           view-type="both"
 *           jndi-name="netservicos/ejb/emissao/FaturasEmailEJB"
 *           local-jndi-name="netservicos/ejb/local/emissao/FaturasEmailEJB"
 *           transaction-type="Container"
 * 
 * @ejb.interface local-extends="javax.ejb.EJBLocalObject"
 *                extends="javax.ejb.EJBObject"
 * 
 * @ejb.home local-extends="javax.ejb.EJBLocalHome" extends="javax.ejb.EJBHome"
 */
public class FaturasEmailEJBImpl extends EmissaoBaseServiceImpl implements
		FaturasEmailService {

	private static final long serialVersionUID = 626045551071497640L;
	private static final String PDF = "pdf";
	private static final String EXCEL = "excel";
	private static final String CSV = "csv";
	private static final String REPORT_NTLOGO_PATH = "/br/com/netservicos/novosms/report/imagens/logo2.gif";
											          
	/**
	 * @ejb.create-method view-type="both"
	 * @throws CreateException
	 */
	public void ejbCreate() throws CreateException {
		super.ejbCreate();
	}

	/**
	 * Método responsável por salvar as alterações dos emails rejeitados
	 * @author Faturas Email
	 * @param Bean
	 * 
	 *  
	 * @ejb.interface-method view-type = "both"
	 * 
	 */
	public void salvarDadosEmailsRejeitados(Bean bean) {
		DynamicBean dBean = (DynamicBean)bean;
		SnFaturaEmailRejeitadoBean[] snFaturaEmail = (SnFaturaEmailRejeitadoBean[])dBean.get("snFaturaEmail");
		
		SnFaturaEmailRejeitadoBean fat;
		
		for(int a = 0; a < snFaturaEmail.length; a++){
			if(snFaturaEmail[a].getFcResolvido() != null && snFaturaEmail[a].getFcResolvido().equals("S")){
				SnTipoResolEmailRejBean resol = new SnTipoResolEmailRejBean();
				resol.setIdTipoResolEmailRej(snFaturaEmail[a].getTipoResolEmail().getIdTipoResolEmailRej());
				resol = (SnTipoResolEmailRejBean)getCurrentDAO().findByPrimaryKey(resol);
				
				fat = new SnFaturaEmailRejeitadoBean();
				fat.setIdFaturaEmailRejeitado(snFaturaEmail[a].getIdFaturaEmailRejeitado());
				fat = (SnFaturaEmailRejeitadoBean)getCurrentDAO().findByPrimaryKey(fat);
		
				//Verifica se existe outro registro com cidcontrato, numcontrato e fcresolvido = S
				//Se estiver, deletar ...
				verificaEmailRejeitado(fat);
				
				fat.setUsr(super.getUserSession().getUserId());
				fat.setTipoResolEmail(resol);
				fat.setFcResolvido("S");
				fat.setDtResolucao(new Date());
				getCurrentDAO().update(fat);
				
			}
		}
	}
	
	/**
	 * Método que verifica se existe algum e-mail rejeitado com FCRESOLVIDO = S....
	 * Se existir, deletar pois haverá posteriormente um update para fcresolvido = S....as chaves da tabela não permitem 
	 * um contrato e cidade com status de resolvido = S
	 * 
	 * @param fat
	 */
	private void verificaEmailRejeitado(SnFaturaEmailRejeitadoBean fat){
		DetachedCriteria criteria = DetachedCriteria.forClass(SnFaturaEmailRejeitadoBean.class);
		criteria.add(Restrictions.eq("numContrato", fat.getNumContrato()));
		criteria.add(Restrictions.eq("cidContrato", fat.getCidContrato()));
		criteria.add(Restrictions.eq("fcResolvido", "S"));
		
		List<SnFaturaEmailRejeitadoBean> lista = getCurrentDAO().select(criteria);
		
		if(lista != null && lista.size()>0){
			SnFaturaEmailRejeitadoBean fatura = lista.get(0);
			getCurrentDAO().delete(fatura);
		}
	}
	
	/**
	 * Método responsável gerar os relatórios de e-mails rejeitados...
	 * @author Faturas Email
	 * @param Bean
	 * 
	 *  
	 * @ejb.interface-method view-type = "both"
	 * 
	 */
	public Map generateReport(Object report, DynamicBean parameters, String queryName, String[] reportFields) {
		ResourceLoader resourceLoader = new ResourceLoader(REPORT_NTLOGO_PATH);
		parameters.put("logo", resourceLoader.getStream());
		List<Object[]> dados =  search(queryName,parameters);
		List<FaturaEmailRejeitadoDTO> lista = new ArrayList<FaturaEmailRejeitadoDTO>(dados.size());
		FaturaEmailRejeitadoDTO fatDto;
		for(Object[] obj: dados){
			fatDto = new FaturaEmailRejeitadoDTO();
			fatDto.setDtRejeicao((Date)obj[0]);
			fatDto.setNumContrato((Long)obj[1]);
			fatDto.setNomeTitular((String)obj[2]);
			fatDto.setDsEmailRejeitado((String)obj[3]);
			fatDto.setIdBoleto((Long)obj[4]);
			fatDto.setDtVencto((Date)obj[5]);
			fatDto.setFcResolvido((String)obj[6]);
			fatDto.setDsFormaEnvioFatura((String)obj[7]);
			fatDto.setDescricaoCobranca((String)obj[8]);
			fatDto.setDsCodErroRejEmail((String)obj[9]);
			fatDto.setIdTipoResolEmailRej((Integer)obj[10]);
			fatDto.setDsTipoResolEmailRej((String)obj[11]);
			fatDto.setCdOperadora((String)obj[12]);
			fatDto.setIdFaturaEmailRejeitado((Long)obj[13]);
			lista.add(fatDto);
		}
		
		String tipo = (String)parameters.get("radioContentType");
		String contentType = "";
		if(PDF.equals(tipo))
			contentType = BaseConstants.CONTENT_TYPE_PDF;
		else if(EXCEL.equals(tipo))
			contentType = BaseConstants.CONTENT_TYPE_EXCEL;
		else if(CSV.equals(tipo))
			contentType = BaseConstants.CONTENT_TYPE_CSV;
		else
			contentType = BaseConstants.CONTENT_TYPE_HTML;

		parameters.put("contentType", contentType);
		
		JRDataSource ds = new JRBeanCollectionDataSource(lista);
		Map retorno = BaseReportService.generateReport(report, parameters,ds);
		retorno.put("contentType", contentType);
		return retorno;
	}
	
	
}

