package br.com.netservicos.novosms.emissao.core.facade.impl;

import java.text.SimpleDateFormat;

import br.com.netservicos.core.bean.sn.SnCidadeOperadoraBean;
import br.com.netservicos.core.bean.sn.SnTipoCobrancaBean;
import br.com.netservicos.core.bean.sn.SnTipoLoteBean;
import br.com.netservicos.novosms.emissao.dto.ArquivoPrintHouseDTO;

public class BoletoAvulsoUtils{

	public static String geraNomeArquivoBoletoAvulso(ArquivoPrintHouseDTO arquivoPrintHouseDTO,
	                                                 SnCidadeOperadoraBean cidade,
	                                                 SnTipoLoteBean tipoLote,
	                                                 Integer sequencia){

		SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");

		String dtEmissaoLote = dateFormat.format(arquivoPrintHouseDTO.getRegistroEmissao().getDtVctoInicial());

		String codigoOperadora = cidade.getCodOperadora();

		StringBuffer nomeArquivo = new StringBuffer();
		String idRegistroEmissao = String.valueOf(arquivoPrintHouseDTO.getRegistroEmissao().getIdRegistroEmissao());

		if (tipoLote.getSnTipoCobrancaBean().getIdTipoCobranca() == SnTipoCobrancaBean.BOLETO) {
			nomeArquivo.append("EBOL_AVULSO");
		} else if (tipoLote.getSnTipoCobrancaBean().getIdTipoCobranca() == SnTipoCobrancaBean.DEBITO_EM_CONTA) {
			nomeArquivo.append("DBOL_AVULSO");
		}

		nomeArquivo.append(codigoOperadora);
		nomeArquivo.append(dtEmissaoLote);
		nomeArquivo.append("_");
		nomeArquivo.append(idRegistroEmissao);

		return nomeArquivo.toString();
	}

}
