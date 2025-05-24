package br.com.netservicos.novosms.emissao.core.dao.impl;

import java.sql.Types;

import br.com.netservicos.framework.core.dao.util.SQLUpdateBatchBuilder;

public class SnInterfaceParceiroSQLUpdateBatchBuilder implements SQLUpdateBatchBuilder {

	public int getBatchSize() {
		return 1000;
	}

	public String getSQL() { 
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("\n insert into sn_interface_parceiro ( ");
		sb.append("\n 	    id_interface_parceiro ");
		sb.append("\n 	    , id_cobranca_parceiro ");
		sb.append("\n 	    , id_parceiro ");
		sb.append("\n 	    , num_contrato ");
		sb.append("\n 	    , cid_contrato ");
		sb.append("\n 	    , id_evento_parceiro ");
		sb.append("\n 	    , id_usr ");
		sb.append("\n 	    , dt_inclusao ");		
		sb.append("\n 	    , id_movimento_cobranca ");
		sb.append("\n ) values (sqsn_interface_parceiro_01.nextval, ?, ?, ?, ?, ?, ?, ? , " );
		sb.append("\n ( select max(a.id_movimento_cobranca)  from sn_movimento_cobranca a where id_cobranca_parceiro =  ? ))");
		
		
		return sb.toString();
		
	}

	public int[] getSqlTypes() {
		
		return new int[] {				  
				  Types.NUMERIC
				, Types.NUMERIC
				, Types.NUMERIC
				, Types.VARCHAR
				, Types.NUMERIC
				, Types.VARCHAR
				, Types.DATE	
				, Types.NUMERIC
				};
		
	}

}
