package br.com.netservicos.novosms.emissao.callBack;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import br.com.netservicos.framework.core.bean.DynamicBean;
import br.com.netservicos.novosms.emissao.dto.LinhaDTO;
import br.com.netservicos.novosms.emissao.dto.SetorDTO;

public class DetalheDemonFinanSetorCallback extends SetorCallback {

	/**
	 * Variavel que armazena o registro anterior do resultset para que seja comparada com o valor a atual.
	 */
	private Map valorAnterior;
	
	private Map valorAnteriorServicosPeriodo;
	
	private double totalSubGrupo;
	
	private double totalServicosPeriodo;
	
	private static final String KEY_GROUPO = "GRUPO_DEMONST_FINANC";
	private static final String KEY_DESCRICAO = "DESCRICAOITEMDEMONST_FINANC";
	private static final String KEY_VALOR = "VALORITEMDEMONST_FINANC";
	private static final String KEY_ID_BLTO = "ID_BLTO";
	
	
	/**
	 * @param setor
	 */
	public DetalheDemonFinanSetorCallback(SetorDTO setor) {
		super(setor);
		totalSubGrupo = 0;
		totalServicosPeriodo = 0;
	}
	
	private Object notNullObject(Object dado) {
		return dado == null ? "" : dado;
	}

	/* (non-Javadoc)
	 * @see br.com.netservicos.sms.fatcobranca.daocallback.SetorCallback#execute(java.util.Map, int)
	 */
	public void execute(DynamicBean valor, int rowNum) {
		String registroAnterior = null;
		if (valorAnterior != null) {
			registroAnterior = (String)this.valorAnterior.get(KEY_GROUPO);
		}
		String registroAtual = (String) valor.get(KEY_GROUPO);

		//Serviços do periodo
		if (registroAnterior == null && (registroAtual == null || registroAtual.trim().equals(""))) {
			
			this.totalServicosPeriodo += ((Double)valor.get(KEY_VALOR)).doubleValue();
			this.valorAnteriorServicosPeriodo = valor;
			
//			this.addLinha(LinhaDTO.TIPO_ITEN_ITEN_DEMONSTRATIVO_FINACEIRO.intValue(), valor);
		} else {
		
			if (registroAnterior == null ) {
				//Add o somar de serviçõs de periodo
				if (valorAnteriorServicosPeriodo != null) {
					Map valorAnteriorCopia = new HashMap(valorAnteriorServicosPeriodo);
					valorAnteriorCopia.put(KEY_VALOR, new Double(totalServicosPeriodo));
					this.addLinha(LinhaDTO.TIPO_ITEN_SERVICO_PERIODO.intValue(), valorAnteriorCopia);
					
					
					Map subTotalServicosPeriodos = new HashMap(valorAnteriorServicosPeriodo);
					subTotalServicosPeriodos.put(KEY_DESCRICAO, "SubTotal Serviços de TV por Assinatura e/ou Banda Larga ");
					subTotalServicosPeriodos.put(KEY_VALOR, new Double(totalServicosPeriodo));
					this.addLinha(LinhaDTO.TIPO_ITEN_SERVICO_PERIODO.intValue(), subTotalServicosPeriodos);
					
					valorAnteriorServicosPeriodo = null;
				} else {
					Serializable[] params = new Serializable[]{(Long)valor.get(KEY_ID_BLTO)};
					//throw new SystemJNetException(FatCobrancaErrorsConstants.ERROS_BUNDLE, FatCobrancaErrorsConstants.SERVICOS_PERIODO_NAO_INFORMADO, params);
				}
				
				//HEADER
				Map valorCopia = new HashMap(valor);
				valorCopia.put(KEY_VALOR, "");
				valorCopia.put(KEY_DESCRICAO, valorCopia.get(KEY_GROUPO));
				this.addLinha(LinhaDTO.TIPO_ITEN_CABECALHO_AGRUPAMENTO_DEMO_FINACEIRO.intValue(), valorCopia);
			} else if (!registroAtual.equals(registroAnterior)) {
				//FOOTER
				Map valorAnteriorCopia = new HashMap(valorAnterior);
				valorAnteriorCopia.put(KEY_DESCRICAO, "SUB-TOTAL " + registroAnterior);
				valorAnteriorCopia.put(KEY_VALOR, new Double(totalSubGrupo));
				this.addLinha(LinhaDTO.TIPO_ITEN_RODAPE_AGRUPAMENTO_DEMO_FINACEIRO.intValue(), valorAnteriorCopia);
				this.totalSubGrupo = 0;
				//HEADER
				Map valorCopia = new HashMap(valor);
				valorCopia.put(KEY_VALOR, "");
				valorCopia.put(KEY_DESCRICAO, valorCopia.get(KEY_GROUPO));
				this.addLinha(LinhaDTO.TIPO_ITEN_CABECALHO_AGRUPAMENTO_DEMO_FINACEIRO.intValue(), valorCopia);
			}
			//ITEM
			String descricao = "   " + valor.get(KEY_DESCRICAO);
			valor.put(KEY_DESCRICAO, descricao);
			this.addLinha(LinhaDTO.TIPO_ITEN_ITEN_DEMONSTRATIVO_FINACEIRO.intValue(), valor);
			this.valorAnterior = valor;
			this.totalSubGrupo += ((Double)valor.get(KEY_VALOR)).doubleValue();
		}	
	}
	
	public void finalizeDAOCallback() { 
		
		
		if (this.valorAnteriorServicosPeriodo != null) {
			Map valorAnteriorCopia = new HashMap(valorAnteriorServicosPeriodo);
			//valorAnteriorCopia.put(KEY_DESCRICAO, "SUB-TOTAL " + (String)this.valorAnterior.get(KEY_DESCRICAO));
			valorAnteriorCopia.put(KEY_VALOR, new Double(totalServicosPeriodo));
			this.addLinha(LinhaDTO.TIPO_ITEN_SERVICO_PERIODO.intValue(), valorAnteriorCopia);
			
			Map subTotalServicosPeriodos = new HashMap(valorAnteriorServicosPeriodo);
			subTotalServicosPeriodos.put(KEY_DESCRICAO, "SubTotal Serviços de TV por Assinatura e/ou Banda Larga ");
			subTotalServicosPeriodos.put(KEY_VALOR, new Double(totalServicosPeriodo));
			this.addLinha(LinhaDTO.TIPO_ITEN_SERVICO_PERIODO.intValue(), subTotalServicosPeriodos);
			
			valorAnteriorServicosPeriodo = null;
			
		}
		
		if (this.valorAnterior != null) {
			//FOOTER
			String registroAnterior = (String)this.valorAnterior.get(KEY_GROUPO);
			Map valorAnteriorCopia = new HashMap(valorAnterior);
			valorAnteriorCopia.put(KEY_DESCRICAO, "SUB-TOTAL " + registroAnterior);
			valorAnteriorCopia.put(KEY_VALOR, new Double(totalSubGrupo));
			this.addLinha(LinhaDTO.TIPO_ITEN_RODAPE_AGRUPAMENTO_DEMO_FINACEIRO.intValue(), valorAnteriorCopia);
			this.totalSubGrupo = 0;
		} 
	}

}
