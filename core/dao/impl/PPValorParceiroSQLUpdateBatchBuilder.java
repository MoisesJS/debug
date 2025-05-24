package br.com.netservicos.novosms.emissao.core.dao.impl;

import java.sql.Types;

import br.com.netservicos.framework.core.dao.util.SQLUpdateBatchBuilder;

public class PPValorParceiroSQLUpdateBatchBuilder implements SQLUpdateBatchBuilder {

	public int getBatchSize() {
		return 1000;
	}

	public String getSQL() {
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("\n insert into pp_vlr_parceiro_fatura ( ");
		sb.append("\n 	    id_controle_arquivo ");
		sb.append("\n 	    , id_rel_cobranca_boleto ");
		sb.append("\n 	    , num_contrato ");
		sb.append("\n 	    , cid_contrato ");
		sb.append("\n 	    , dt_vencto ");
		sb.append("\n 	    , qtd_pagina ");
		sb.append("\n 	    , dt_atual ");
		sb.append("\n 	    , id_origem_emissao ");
		sb.append("\n 	    , vl_cobranca ");
		//sb.append("\n ) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
		sb.append("\n ) ");
		sb.append("\n select ");
		sb.append("\n 		?, rcb.id_rel_cobranca_boleto, ?, ?, ?, ?, ?, ?, ? ");
		sb.append("\n from sn_rel_cobranca_boleto rcb ");
		sb.append("\n where ");
		sb.append("\n 	    rcb.id_boleto = ? "); // idBoleto
		sb.append("\n 	    and ( ");
		sb.append("\n 	          (? is not null and ? = rcb.id_cobranca) or  ");      // (idCobranca is not null and idCobranca         = rcb.id_cobranca) or
		sb.append("\n 	          (? is     null and ? = rcb.id_cobranca_parceiro) "); // (idCobranca is     null and idCobrancaParceiro = rcb.id_cobranca_parceiro)
		sb.append("\n 	        ) ");
		
		return sb.toString();
		
	}

	public int[] getSqlTypes() {
		
		return new int[] {
				Types.NUMERIC
				, Types.NUMERIC
				, Types.VARCHAR
				, Types.DATE
				, Types.NUMERIC
				, Types.DATE
				, Types.NUMERIC
				, Types.DECIMAL
				, Types.NUMERIC
				, Types.NUMERIC
				, Types.NUMERIC
				, Types.NUMERIC
				, Types.NUMERIC
				};
		
	}

}
