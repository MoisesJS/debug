package br.com.netservicos.novosms.emissao.core.dao.impl;

import java.sql.Types;

import br.com.netservicos.framework.core.dao.util.SQLUpdateBatchBuilder;

public class SnBoletoStatusSQLUpdateBatchBuilder implements SQLUpdateBatchBuilder {

	public int getBatchSize() {
		return 1000;
	}

	public String getSQL() {
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("\n update sn_boleto ");
		sb.append("\n set st_impresso = ? ");
		sb.append("\n where id_boleto = ? ");

		return sb.toString();
		
	}

	public int[] getSqlTypes() {
		
		return new int[] {
				Types.VARCHAR
				, Types.NUMERIC
				};
		
	}

}
