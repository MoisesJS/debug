package br.com.netservicos.novosms.emissao.core.dao.impl;

import java.sql.Types;

import br.com.netservicos.framework.core.dao.util.SQLUpdateBatchBuilder;

public class SnMovimentoCobrancaSQLUpdateBatchBuilder implements SQLUpdateBatchBuilder {

	public int getBatchSize() {
		return 1000;
	}

	public String getSQL() {
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("\n insert into sn_movimento_cobranca ( ");
		sb.append("\n 	    id_movimento_cobranca ");
		sb.append("\n 	    , id_cobranca_parceiro ");
		sb.append("\n 	    , id_parceiro ");
		sb.append("\n 	    , dt_movimento ");
		sb.append("\n 	    , dt_inclusao ");
		sb.append("\n 	    , vl_movimento ");
		sb.append("\n 	    , cc_usr_movimento ");
		sb.append("\n 	    , fc_enviado_parceiro ");
		sb.append("\n 	    , id_tipo_movimento ");
		sb.append("\n 	    , id_situacao_movimento ");
		sb.append("\n 	    , id_tipo_cobranca ");
		sb.append("\n ) values (sqsn_movimento_cobranca_01.nextval, ?, ?, ?, ?, ?, ?, ?, ?,?,?)");
		
		
		return sb.toString();
		
	}

	public int[] getSqlTypes() {
		
		return new int[] {				
				  Types.NUMERIC
				, Types.NUMERIC
				, Types.DATE
				, Types.DATE
				, Types.NUMERIC
				, Types.VARCHAR
				, Types.VARCHAR
				, Types.NUMERIC
				, Types.NUMERIC
				, Types.NUMERIC				
				};
		
	}

}
