package br.com.netservicos.novosms.emissao.core.dao.impl;

import java.sql.Types;

import br.com.netservicos.framework.core.dao.util.SQLUpdateBatchBuilder;

public class BoletoAvulsoBatchBuilder implements SQLUpdateBatchBuilder {

	public int getBatchSize() {
		return 1000;
	}

	public String getSQL() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("\n update prod_jd.sn_cobranca_avulsa ca ");
		sb.append("\n set ca.dt_emissao = ?, ");
		sb.append("\n     ca.id_sit_cobranca_avulsa = ? ");
		sb.append("\n where ca.id_cobranca_avulsa = (select cab.id_cobranca_avulsa ");
		sb.append("\n                                 from prod_jd.sn_cobranca_avulsa_boleto cab ");
		sb.append("\n                                where cab.id_cobranca_avulsa_boleto = ? ");
		sb.append("\n                                  and rownum = 1 ) ");

		return sb.toString();
	}

	public int[] getSqlTypes(){
		return new int[] {
		        Types.DATE,
		        Types.NUMERIC,
		        Types.NUMERIC
		};
	}
}
