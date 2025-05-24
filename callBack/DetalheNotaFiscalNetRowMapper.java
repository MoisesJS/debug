package br.com.netservicos.novosms.emissao.callBack;

import java.sql.ResultSet;

import br.com.netservicos.framework.core.bean.DynamicBean;
import br.com.netservicos.framework.core.dao.RowMapper;

public class DetalheNotaFiscalNetRowMapper implements RowMapper<DynamicBean, ResultSet>{

	
	private static final String CAMPO_DESCRICAO = "NOMEAGRUPAMENTO";
	
	public DynamicBean mapRow(ResultSet rs, int row)  {
		
		DynamicBean sn = new DynamicBean();
		
//		String registroAnterior = null;
//		if (valorAnterior != null) {
//			registroAnterior = (String)this.valorAnterior.get(CAMPO_DESCRICAO);
//		}
//		String registroAtual = (String) valor.get(CAMPO_DESCRICAO);
//
//		if (registroAnterior == null ) {
//			this.addLinha(LinhaDTO.TIPO_ITEN_NET_CABECALHO_AGRUPAMENTO.intValue(), valor);
//		} else if (!registroAtual.equals(registroAnterior)) {
//			this.addLinha(LinhaDTO.TIPO_ITEN_NET_RODAPE_AGRUPAMENTO.intValue(), valorAnterior);
//			this.addLinha(LinhaDTO.TIPO_ITEN_BLANK_LINE.intValue(), valor);
//			this.addLinha(LinhaDTO.TIPO_ITEN_NET_CABECALHO_AGRUPAMENTO.intValue(), valor);
//		}
//		this.addLinha(LinhaDTO.TIPO_ITEN_NET_DETALHAMENTO.intValue(), valor);
//		this.valorAnterior = valor;
		try{			
			sn.addBeanProperty(CAMPO_DESCRICAO, rs.getString("NOMEAGRUPAMENTO"));			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return sn;
	}

}
