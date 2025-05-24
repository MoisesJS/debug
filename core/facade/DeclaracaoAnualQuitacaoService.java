package br.com.netservicos.novosms.emissao.core.facade;

import java.util.List;

import net.sf.jasperreports.engine.JRException;



import br.com.netservicos.core.bean.qd.QdHistoricoEnvioQuitacaoBean;
import br.com.netservicos.framework.core.facade.Service;

public interface DeclaracaoAnualQuitacaoService extends Service {

	public List<QdHistoricoEnvioQuitacaoBean> listarQuitacao(Long numContrato,
			String cidContrato);

	public QdHistoricoEnvioQuitacaoBean consultar(Long numContrato,
			String cidContrato, Integer aaConsolidado, Integer idParceiro);

	public byte[] gerarPDF(Long numContrato, String cidContrato,
			Integer aaConsolidado, Integer idParceiro);
}
