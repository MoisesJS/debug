package br.com.netservicos.novosms.emissao.core.facade;

import java.util.Map;

import br.com.netservicos.framework.core.bean.Bean;
import br.com.netservicos.framework.core.bean.DynamicBean;
import br.com.netservicos.framework.core.facade.Service;

public interface FaturasEmailService extends Service{

	/**
	 * Metodo responsavel por salvar os dados da tela de e-mails rejeitados. Estes dados será o status e o tipo de resolução.
	 * @author Faturas por email
	 * @since 18/09/2009
	 * @return
	 *  
	 * @ejb.interface-method view-type="both"
	 * @ejb.transaction type="Required"
	 */
	public void salvarDadosEmailsRejeitados(Bean bean);
	
	/**
	 * Metodo responsável por gerar relatórios de e-mails rejeitados.....
	 * @author Faturas por email
	 * @since 18/09/2009
	 * @return
	 *  
	 * @ejb.interface-method view-type="both"
	 * @ejb.transaction type="Required"
	 */
	public Map generateReport(Object report, DynamicBean parameters, String queryName, String[] reportFields);
	
}
