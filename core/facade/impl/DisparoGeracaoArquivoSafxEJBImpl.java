/**
 * Created on 06/12/2007
 * Project : NETEmissao
 *
 * Copyright © 2007 NET.
 * Brasil
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of NET. 
 * You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Net Serviços.
 * 
 * $Id: DisparoGeracaoArquivoSafxEJBImpl.java,v 1.6 2010/03/23 17:40:33 jucenali Exp $
 */
package br.com.netservicos.novosms.emissao.core.facade.impl;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.com.netservicos.framework.core.bean.DynamicBean;
import br.com.netservicos.framework.file.reader.impl.FileMapperServiceEJBImpl;
import br.com.netservicos.framework.file.writer.NoLayoutGenerateFile;
import br.com.netservicos.framework.util.attachments.messages.AttachmentMessageLevel;
import br.com.netservicos.framework.util.attachments.messages.BasicAttachmentMessage;
import br.com.netservicos.framework.util.exception.BaseFailureException;
import br.com.netservicos.novosms.emissao.constants.EmissaoConstants;
import br.com.netservicos.novosms.emissao.core.facade.DisparoGeracaoArquivoSafxService;
import br.com.netservicos.novosms.emissao.exception.EmissaoBusinessResourceException;
import br.com.netservicos.novosms.emissao.resources.EmissaoResources;

/**
 * <P><B>Description :</B><BR>
 * 	TODO descrever
 * </P>
 * <P>
 * <B>
 * Issues : <BR>
 * None
 * </B>
 * @author pzd8y8
 * @since 06/12/2007
 * 
 * 
 * @ejb.bean name="DisparoGeracaoArquivoSafxEJB" 
 * 		type="Stateless" 
 * 		display-name="DisparoGeracaoArquivoSafxEJB"
 * 		description="Gera arquivos mastersaf através de callback" 
 * 		view-type="both"
 * 		jndi-name="netservicos/ejb/emissao/DisparoGeracaoArquivoSafxEJB" 
 * 		local-jndi-name="netservicos/ejb/local/emissao/DisparoGeracaoArquivoSafxEJB" 
 * 		transaction-type="Container"
 * 
 * @ejb.interface local-extends="javax.ejb.EJBLocalObject" extends="javax.ejb.EJBObject"
 * 
 * @ejb.home local-extends="javax.ejb.EJBLocalHome" extends="javax.ejb.EJBHome"
 * 
 */
public class DisparoGeracaoArquivoSafxEJBImpl extends AbstractEmissaoEJBImpl implements
		DisparoGeracaoArquivoSafxService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6847951705165597648L;

	private final Log log = LogFactory.getLog(FileMapperServiceEJBImpl.class);
	
	private final String NULLPOINTER_VERIFICA_TAM_NUM = "NullPointerException: A consulta não retornou algum valor";
	 
	
	/**
	 * @see br.com.netservicos.novosms.emissao.core.facade.DisparoGeracaoArquivoSafxService#gerarLinhaSAFX042(java.util.Map, br.com.netservicos.framework.core.bean.DynamicBean, br.com.netservicos.framework.core.bean.DynamicBean, int)
	 * @ejb.interface-method view-type="both"
	 * @ejb.transaction type="RequiresNew"
	 */
	public void gerarLinhaSAFX042(Map valores, DynamicBean bean, DynamicBean dynamicBean, NoLayoutGenerateFile noFormatFile) {
		try {
				valores = new HashMap<String,Object>();
				
				valores.put("codEmpresa", this.verificaTamanho(EmissaoConstants.TAMANHO_TRES, dynamicBean.get("codOperJdeMestre").toString()));
				valores.put("codEstabelecimento", this.verificaTamanho(EmissaoConstants.TAMANHO_SEIS, dynamicBean.get("codOperJdeFisc").toString()));
				valores.put("dtEmissao", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_OITO, bean.get("DT_EMISSAO").toString(), 0));
				valores.put("indicadorPessoaFJ", this.verificaTamanho(EmissaoConstants.TAMANHO_UM, "2"));
				valores.put("codDestinatarioEmitenteRemetente", this.verificaTamanho(EmissaoConstants.TAMANHO_UM+ EmissaoConstants.TAMANHO_QUATRO, 	bean.get("ID_ASSINANTE")));
				valores.put("numDocumentoFiscal", this.verificaTamanho(EmissaoConstants.TAMANHO_UM	+ EmissaoConstants.TAMANHO_DOIS, bean.get("NR_NOTA_FISCAL")));
				valores.put("serie", this.verificaTamanho(EmissaoConstants.TAMANHO_TRES, bean.get("CC_SERIE_NOTA_FISCAL")));
				valores.put("subserie", this.verificaTamanho(EmissaoConstants.TAMANHO_DOIS, "@"));
				valores.put("tipoRegistro", this.verificaTamanho(EmissaoConstants.TAMANHO_UM, "1"));
				valores.put("codCFOP", this.verificaTamanho(EmissaoConstants.TAMANHO_QUATRO, bean.get("CC_COD_CFOP")));
				valores.put("dtSaida", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_OITO, bean.get("DT_EMISSAO"),0));
				valores.put("classeUsuario", this.verificaTamanho(EmissaoConstants.TAMANHO_TRES, bean.get("COD_CLASSE_USU")));
				valores.put("modeloDocumento", this.verificaTamanho(EmissaoConstants.TAMANHO_DOIS, bean.get("CC_MODELO_NOTA_FISCAL")));
				valores.put("tipoReceita", this.verificaTamanho(EmissaoConstants.TAMANHO_UM, "1"));
				valores.put("numTelefoneCliente", this.verificaTamanho(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_CINCO, "@"));
				valores.put("valorServicos", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_SETE, bean.get("VL_TOTAL"), 2));
				valores.put("valorTotalDocFiscal", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_UM	+ EmissaoConstants.TAMANHO_SETE, bean.get("VL_TOTAL"), 2));
				valores.put("valorOutrasDespesas", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_UM	+ EmissaoConstants.TAMANHO_SETE, "@", 2));
				valores.put("valorAbatimentoDeducoes", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_UM	+ EmissaoConstants.TAMANHO_SETE, "@", 2));
				valores.put("valorTotalPagar", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_UM	+ EmissaoConstants.TAMANHO_SETE, bean.get("VL_TOTAL"), 2));
				valores.put("situacaoNota", this.verificaTamanho(EmissaoConstants.TAMANHO_UM, "N"));
				valores.put("baseCalculo", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_UM 	+ EmissaoConstants.TAMANHO_SETE, bean.get("VL_BASE_CALCULO_ICMS"), 2));//VlBaseCalculo
				valores.put("baseIsenta", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_SETE, bean.get("VL_ISENTO_ICMS"), 2)); //VlIsento
				valores.put("baseOutros", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_SETE, "0", 2));
				valores.put("baseReducao", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_UM 	+ EmissaoConstants.TAMANHO_SETE, "@", 2));
				valores.put("valorICMS", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_SETE, bean.get("VL_IMPOSTO_ICMS"), 2)); //VlImposto
				valores.put("aliquotaICMS", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_SETE, 	bean.get("VL_ALIQUOTA_ICMS"), 4)); //PcAliquota
				valores.put("baseCalculoUfDestino", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_SETE, "@", 2));
				valores.put("valorICMSUfDestino", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_SETE, "@", 2));
				valores.put("aliquotaICMSDestino", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_SETE, "@", 4));
				valores.put("telefoneAcesso", this.verificaTamanho(EmissaoConstants.TAMANHO_UM 	+ EmissaoConstants.TAMANHO_CINCO,"@"));
				valores.put("dtVencimento", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_OITO, bean.get("DT_EMISSAO"), 0));
				valores.put("mesReferencia", this.verificaTamanho(EmissaoConstants.TAMANHO_DOIS, bean.get("CC_ANO_MES_REF").toString().substring(4, 6)));
				valores.put("anoReferencia", this.verificaTamanho(EmissaoConstants.TAMANHO_QUATRO, 	bean.get("CC_ANO_MES_REF").toString().substring(0, 4)));
				valores.put("codLocalidade", this.verificaTamanho(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_ZERO,"@"));
				valores.put("UFICMSDestino", this.verificaTamanho(EmissaoConstants.TAMANHO_DOIS, "@"));
				valores.put("condicaoParticipante", this.verificaTamanho(EmissaoConstants.TAMANHO_DOIS, "@"));
				valores.put("identificadorParticipante", this.verificaTamanho(EmissaoConstants.TAMANHO_DOIS, "@"));
				valores.put("contaContabil", this.verificaTamanho(EmissaoConstants.TAMANHO_SETE + EmissaoConstants.TAMANHO_ZERO,"@"));
				valores.put("codOperacao", this.verificaTamanho(EmissaoConstants.TAMANHO_DOIS, "@"));
				valores.put("codUsuarioFinal", this.verificaTamanho(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_ZERO, "@"));
				valores.put("codBarras", this.verificaTamanho(EmissaoConstants.TAMANHO_DOIS + EmissaoConstants.TAMANHO_QUATRO,"@"));
				valores.put("numCiclo", this.verificaTamanho(EmissaoConstants.TAMANHO_SETE, "1"));
				valores.put("valorICMSSubsTributaria", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_UM	+ EmissaoConstants.TAMANHO_SETE, "@", 2));
				valores.put("baseICMSSubsTributaria", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_SETE, "@", 2));
				valores.put("observacao", this.verificaTamanho(EmissaoConstants.TAMANHO_DOIS + EmissaoConstants.TAMANHO_ZERO + EmissaoConstants.TAMANHO_ZERO, "@"));
				valores.put("valorDescontoCondicional", this.verificaTamanho(EmissaoConstants.TAMANHO_UM+ EmissaoConstants.TAMANHO_SETE, "@"));
				valores.put("codAutenticacao", this.verificaTamanho(EmissaoConstants.TAMANHO_TRES+ EmissaoConstants.TAMANHO_DOIS, bean.get("CC_HASH_CODE").toString().replaceAll("\\.", "")));
				valores.put("tipoUtilizacao", this.verificaTamanho(EmissaoConstants.TAMANHO_DOIS, "3"));
				valores.put("classificacaoDocFiscal", this.verificaTamanho(EmissaoConstants.TAMANHO_UM, "1"));
				valores.put("baseIsentaISS", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_SETE, "@", 2));
				valores.put("baseISSTerceiros", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_UM	+ EmissaoConstants.TAMANHO_SETE, "@", 2));
				valores.put("codMunicipioISS", this.verificaTamanho(EmissaoConstants.TAMANHO_SEIS, "@"));
				valores.put("canalDistribuicaoCodObra", this.verificaTamanho(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_ZERO, "@"));
				valores.put("dataFatoGerador", this.verificaTamanho(EmissaoConstants.TAMANHO_OITO, "@"));
				valores.put("dataCancelamento", this.verificaTamanho(EmissaoConstants.TAMANHO_OITO, "@"));
				valores.put("tipoDocumento", this.verificaTamanho(EmissaoConstants.TAMANHO_CINCO, "NFSC"));
				valores.put("grupoTensao", this.verificaTamanho(EmissaoConstants.TAMANHO_DOIS, "@"));
				valores.put("numContaConsumo", this.verificaTamanho(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_ZERO, "@"));
				valores.put("UFHabilitacaoTelefoneCliente", this.verificaTamanho(EmissaoConstants.TAMANHO_DOIS, "@"));
				valores.put("indicadorNFViaUnicaICMS115", this.verificaTamanho(EmissaoConstants.TAMANHO_UM, bean.get("IND_CONVENIO_115")));
				// Flag para ligar/desligar as alterações SPED
				valores.put("flagSped", bean.get("FLAG_SPED"));
				valores.put("codigoSituacaoDoc", this.verificaTamanho(EmissaoConstants.TAMANHO_DOIS, "00"));
				valores.put("classeConsumo", this.verificaTamanho(EmissaoConstants.TAMANHO_DOIS, bean.get("CLASSE_CONSUMO")));
				valores.put("naturezaOperacao", this.verificaTamanho(EmissaoConstants.TAMANHO_TRES, "@"));
				valores.put("especReceita", this.verificaTamanho(EmissaoConstants.TAMANHO_UM, "0"));
				valores.put("codigoObservacao", this.verificaTamanho(EmissaoConstants.TAMANHO_OITO, "@"));
				valores.put("numeroContrato", this.verificaTamanho(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_QUATRO, bean.get("ID_ASSINANTE")));
				valores.put("areaTerminalFat", this.verificaTamanho(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_QUATRO, bean.get("COD_AREA_TERMINAL")));
				valores.put("dataConsumoIni", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_OITO, bean.get("DATA_CONSUMO_INI").toString(), 0));
				valores.put("dataConsumoFim", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_OITO, bean.get("DATA_CONSUMO_FIM").toString(), 0));
				valores.put("dataConsumoLeit", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_OITO, "@", 0));
				valores.put("qtdContratadaPonta", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_SETE, "@", 6));
				valores.put("qtdContratadaFPonta", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_SETE, "@", 6));
				valores.put("qtdConsumoTotal", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_SETE, "@", 6));
				valores.put("ufConsumo", this.verificaTamanho(EmissaoConstants.TAMANHO_DOIS, bean.get("CC_ESTADO")));
				valores.put("codMunicipioConsumo", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_CINCO, "@", 0));
				valores.put("valorTerceiros", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_SETE, "@", 0));
				valores.put("codigoModeloCOTEPE", this.verificaTamanho(EmissaoConstants.TAMANHO_DOIS, "21"));
				valores.put("numeroNFCEESubst", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_DOIS, "@", 0));
				valores.put("serieNFCEESubst", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_TRES, "@", 0));
				valores.put("hipoteseEstorno", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_UM, "@", 0));
				valores.put("motivoEstorno", this.verificaTamanho(EmissaoConstants.TAMANHO_DOIS + EmissaoConstants.TAMANHO_ZERO + EmissaoConstants.TAMANHO_ZERO, "@"));
				valores.put("NFBaseadaRegime", this.verificaTamanho(EmissaoConstants.TAMANHO_UM, bean.get("IND_NF_REG_ESPE")));
				valores.put("indicador", this.verificaTamanho(EmissaoConstants.TAMANHO_UM, "@"));
				
				
				StringBuffer buffer = new StringBuffer();
				buffer.append(dynamicBean.get("codOperJdeMestre").toString());
				buffer.append("\t");
				buffer.append(dynamicBean.get("codOperJdeFisc").toString());
				buffer.append("\t");
				buffer.append(valores.get("dtEmissao").toString());
				buffer.append("\t");
				buffer.append(valores.get("indicadorPessoaFJ").toString());
				buffer.append("\t");
				buffer.append(valores.get("codDestinatarioEmitenteRemetente").toString());
				buffer.append("\t");
				buffer.append(valores.get("numDocumentoFiscal").toString());
				buffer.append("\t");
				buffer.append(valores.get("serie").toString());
				buffer.append("\t");
				buffer.append(valores.get("subserie").toString());
				buffer.append("\t");
				buffer.append(valores.get("tipoRegistro").toString());
				buffer.append("\t");
				buffer.append(valores.get("codCFOP").toString());
				buffer.append("\t");
				buffer.append(valores.get("dtSaida").toString());
				buffer.append("\t");
				buffer.append(valores.get("classeUsuario").toString());
				buffer.append("\t");
				buffer.append(valores.get("modeloDocumento").toString());
				buffer.append("\t");
				buffer.append(valores.get("tipoReceita").toString());
				buffer.append("\t");
				buffer.append(valores.get("numTelefoneCliente").toString());
				buffer.append("\t");
				buffer.append(valores.get("valorServicos").toString());
				buffer.append("\t");
				buffer.append(valores.get("valorTotalDocFiscal").toString());
				buffer.append("\t");
				buffer.append(valores.get("valorOutrasDespesas").toString());
				buffer.append("\t");
				buffer.append(valores.get("valorAbatimentoDeducoes").toString());
				buffer.append("\t");
				buffer.append(valores.get("valorTotalPagar").toString());
				buffer.append("\t");
				buffer.append(valores.get("situacaoNota").toString());
				buffer.append("\t");
				buffer.append(valores.get("baseCalculo").toString());
				buffer.append("\t");
				buffer.append(valores.get("baseIsenta").toString());//null
				buffer.append("\t");
				buffer.append(valores.get("baseOutros").toString());
				buffer.append("\t");
				buffer.append(valores.get("baseReducao").toString());
				buffer.append("\t");
				buffer.append(valores.get("valorICMS").toString());//null
				buffer.append("\t");
				buffer.append(valores.get("aliquotaICMS").toString());
				buffer.append("\t");
				buffer.append(valores.get("baseCalculoUfDestino").toString());
				buffer.append("\t");
				buffer.append(valores.get("valorICMSUfDestino").toString());
				buffer.append("\t");
				buffer.append(valores.get("aliquotaICMSDestino").toString());
				buffer.append("\t");
				buffer.append(valores.get("telefoneAcesso").toString());
				buffer.append("\t");
				buffer.append(valores.get("dtVencimento").toString());
				buffer.append("\t");
				buffer.append(valores.get("mesReferencia").toString());
				buffer.append("\t");
				buffer.append(valores.get("anoReferencia").toString());
				buffer.append("\t");
				buffer.append(valores.get("codLocalidade").toString());
				buffer.append("\t");
				buffer.append(valores.get("UFICMSDestino").toString());
				buffer.append("\t");
				buffer.append(valores.get("condicaoParticipante").toString());
				buffer.append("\t");
				buffer.append(valores.get("identificadorParticipante").toString());
				buffer.append("\t");
				buffer.append(valores.get("contaContabil").toString());
				buffer.append("\t");
				buffer.append(valores.get("codOperacao").toString());
				buffer.append("\t");
				buffer.append(valores.get("codUsuarioFinal").toString());
				buffer.append("\t");
				buffer.append(valores.get("codBarras").toString());
				buffer.append("\t");
				buffer.append(valores.get("numCiclo").toString());
				buffer.append("\t");
				buffer.append(valores.get("valorICMSSubsTributaria").toString());
				buffer.append("\t");
				buffer.append(valores.get("baseICMSSubsTributaria").toString());
				buffer.append("\t");
				buffer.append(valores.get("observacao").toString());
				buffer.append("\t");
				buffer.append(valores.get("valorDescontoCondicional").toString());
				buffer.append("\t");
				buffer.append(valores.get("codAutenticacao").toString());
				buffer.append("\t");
				buffer.append(valores.get("tipoUtilizacao").toString());
				buffer.append("\t");
				buffer.append(valores.get("classificacaoDocFiscal").toString());
				buffer.append("\t");
				buffer.append(valores.get("baseIsentaISS").toString());
				buffer.append("\t");
				buffer.append(valores.get("baseISSTerceiros").toString());
				buffer.append("\t");
				buffer.append(valores.get("codMunicipioISS").toString());
				buffer.append("\t");
				buffer.append(valores.get("canalDistribuicaoCodObra").toString());
				buffer.append("\t");
				buffer.append(valores.get("dataFatoGerador").toString());
				buffer.append("\t");
				buffer.append(valores.get("dataCancelamento").toString());
				buffer.append("\t");
				buffer.append(valores.get("tipoDocumento").toString());
				buffer.append("\t");
				buffer.append(valores.get("grupoTensao").toString());
				buffer.append("\t");
				buffer.append(valores.get("numContaConsumo").toString());
				buffer.append("\t");
				buffer.append(valores.get("UFHabilitacaoTelefoneCliente").toString());
				buffer.append("\t");
				buffer.append(valores.get("indicadorNFViaUnicaICMS115").toString());
				if(valores.get("flagSped").equals("S")){
					buffer.append("\t");
					buffer.append(valores.get("codigoSituacaoDoc").toString());
					buffer.append("\t");
					buffer.append(valores.get("classeConsumo").toString());
					buffer.append("\t");
					buffer.append(valores.get("naturezaOperacao").toString());
					buffer.append("\t");
					buffer.append(valores.get("especReceita").toString());
					buffer.append("\t");
					buffer.append(valores.get("codigoObservacao").toString());
					buffer.append("\t");
					buffer.append(valores.get("numeroContrato").toString());
					buffer.append("\t");
					buffer.append(valores.get("areaTerminalFat").toString());
					buffer.append("\t");
					buffer.append(valores.get("dataConsumoIni").toString());
					buffer.append("\t");
					buffer.append(valores.get("dataConsumoFim").toString());
					buffer.append("\t");
					buffer.append(valores.get("dataConsumoLeit").toString());
					buffer.append("\t");
					buffer.append(valores.get("qtdContratadaPonta").toString());
					buffer.append("\t");
					buffer.append(valores.get("qtdContratadaFPonta").toString());
					buffer.append("\t");
					buffer.append(valores.get("qtdConsumoTotal").toString());
					buffer.append("\t");
					buffer.append(valores.get("ufConsumo").toString());
					buffer.append("\t");
					buffer.append(valores.get("codMunicipioConsumo").toString());
					buffer.append("\t");
					buffer.append(valores.get("valorTerceiros").toString());
					buffer.append("\t");
					buffer.append(valores.get("codigoModeloCOTEPE").toString());
					buffer.append("\t");
					buffer.append(valores.get("numeroNFCEESubst").toString());
					buffer.append("\t");
					buffer.append(valores.get("serieNFCEESubst").toString());
					buffer.append("\t");
					buffer.append(valores.get("hipoteseEstorno").toString());
					buffer.append("\t");
					buffer.append(valores.get("motivoEstorno").toString());
					buffer.append("\t");
					buffer.append(valores.get("NFBaseadaRegime").toString());
					buffer.append("\t");
					buffer.append(valores.get("indicador").toString());
					buffer.append("\t");
				}
				noFormatFile.addLine(buffer);
				
				//valoresArquivo.put(rowNum-1, valores);
		} catch (BaseFailureException e) {
			this.executaLog(super.getMessage(EmissaoResources.ERRO_PREENCHER_SAFX042), e);
			throw new EmissaoBusinessResourceException(super.getMessage(EmissaoResources.ERRO_PREENCHER_SAFX042), e);
		}
		
	}
	
	/**
	 * @see br.com.netservicos.novosms.emissao.core.facade.DisparoGeracaoArquivoSafxService#gerarLinhaSAFX2013(java.util.Map, br.com.netservicos.framework.core.bean.DynamicBean, br.com.netservicos.framework.core.bean.DynamicBean, int)
	 * @ejb.interface-method view-type="both"
	 * @ejb.transaction type="RequiresNew"
	 */
	public void gerarLinhaSAFX2013(Map  valores, DynamicBean bean, DynamicBean dynamicBean, NoLayoutGenerateFile noFormatFile) {
		try {
			valores = new HashMap<String,Object>();
			
			valores.put("indicadorProduto", this.verificaTamanho(EmissaoConstants.TAMANHO_UM, "1"));
			valores.put("codigoProduto", this.verificaTamanho(EmissaoConstants.TAMANHO_TRES	+ EmissaoConstants.TAMANHO_CINCO, bean.get("ID_PRODUTO").toString()));
			valores.put("data", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_OITO, dynamicBean.get("ccAnoMesEmissao")+"01", 0));
			valores.put("descricaoProduto", this.verificaTamanho(EmissaoConstants.TAMANHO_CINCO + EmissaoConstants.TAMANHO_ZERO, bean.get("DESCRICAO").toString().replaceAll("\\t", " ")));
			valores.put("codigoNBM", this.verificaTamanho(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_ZERO, "@"));
			valores.put("codigoNCM", this.verificaTamanho(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_ZERO, "@"));
			valores.put("codigoNALADI", this.verificaTamanho(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_ZERO, "@"));
			valores.put("substituicaoTributaria", this.verificaTamanho(EmissaoConstants.TAMANHO_UM, "@"));
			valores.put("IPIControladoSelo", this.verificaTamanho(EmissaoConstants.TAMANHO_UM, "@"));
			valores.put("grupoSelo", this.verificaTamanho(EmissaoConstants.TAMANHO_DOIS, "@"));
			valores.put("subGrupoSelo", this.verificaTamanho(EmissaoConstants.TAMANHO_DOIS, "@"));
			valores.put("corSelo", this.verificaTamanho(EmissaoConstants.TAMANHO_UM	+ EmissaoConstants.TAMANHO_CINCO, "@"));
			valores.put("serieSelo", this.verificaTamanho(EmissaoConstants.TAMANHO_TRES, "@"));
			valores.put("codigoMedida", this.verificaTamanho(EmissaoConstants.TAMANHO_TRES, "UN"));
			valores.put("tipoProduto", this.verificaTamanho(EmissaoConstants.TAMANHO_CINCO, "@"));
			valores.put("codigoGrupoIncentivo", this.verificaTamanho(EmissaoConstants.TAMANHO_CINCO, "@"));
			valores.put("grupoSubstituicaoTributaria", this.verificaTamanho(EmissaoConstants.TAMANHO_DOIS, "@"));
			valores.put("contaContabil", this.verificaTamanho(EmissaoConstants.TAMANHO_SETE	+ EmissaoConstants.TAMANHO_ZERO, "@"));
			valores.put("tipoProdutoServico", this.verificaTamanho(EmissaoConstants.TAMANHO_UM, "2"));
			valores.put("unidadeMedidaPadrao", this.verificaTamanho(EmissaoConstants.TAMANHO_OITO, "UN"));
			valores.put("pesoUnitarioKg", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_TRES, "@", 4));
			valores.put("descricaoDetalhada", this.verificaTamanho(EmissaoConstants.TAMANHO_DOIS + EmissaoConstants.TAMANHO_CINCO + EmissaoConstants.TAMANHO_ZERO, bean.get("DESCRICAO").toString().replaceAll("\\t", " ")));
			valores.put("FabricacaoEstabelecimento", this.verificaTamanho(EmissaoConstants.TAMANHO_UM, "@"));
			valores.put("fatorConversao", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_SETE, "@", 6));
			valores.put("classificacaoICMS", this.verificaTamanho(EmissaoConstants.TAMANHO_UM, "@"));
			valores.put("descricaoModelo", this.verificaTamanho(EmissaoConstants.TAMANHO_OITO + EmissaoConstants.TAMANHO_ZERO, "@"));
			valores.put("origem", this.verificaTamanho(EmissaoConstants.TAMANHO_UM, "@"));
			valores.put("grupoProdutoDICSE", this.verificaTamanho(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_DOIS, "@"));
			valores.put("incidenciaPIS", this.verificaTamanho(EmissaoConstants.TAMANHO_UM, "@"));
			valores.put("aliquotaPIS", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_SETE, "@", 4));
			valores.put("incidenciaCOFINS", this.verificaTamanho(EmissaoConstants.TAMANHO_UM, "@"));
			valores.put("aliquotaCOFINS", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_SETE, "@", 4));
			valores.put("funrural", this.verificaTamanho(EmissaoConstants.TAMANHO_UM, "@"));
			valores.put("petroleoEnergia", this.verificaTamanho(EmissaoConstants.TAMANHO_UM, "@"));
			valores.put("produtoIncentivado", this.verificaTamanho(EmissaoConstants.TAMANHO_UM, "@"));
			valores.put("ICMSDiferido", this.verificaTamanho(EmissaoConstants.TAMANHO_UM, "@"));
			valores.put("capacidadeVolumetrica", this.verificaTamanho(EmissaoConstants.TAMANHO_CINCO, "@"));
			valores.put("codigoEspecie", this.verificaTamanho(EmissaoConstants.TAMANHO_TRES, "@"));
			// Flag para ligar/desligar as alterações SPED
			valores.put("flagSped", bean.get("FLAG_SPED"));
			valores.put("classificacaoItem", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_DOIS, "09", 0));
			valores.put("codigoBarras", this.verificaTamanho(EmissaoConstants.TAMANHO_SEIS	+ EmissaoConstants.TAMANHO_ZERO, "@"));
			valores.put("codigoANP", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_DOIS, "@", 0));
			valores.put("idAnteriorProduto", this.verificaTamanho(EmissaoConstants.TAMANHO_UM, "@"));
			valores.put("codigoAnteriorItem", this.verificaTamanho(EmissaoConstants.TAMANHO_TRES	+ EmissaoConstants.TAMANHO_CINCO, "@"));
			valores.put("dataAlteracaoCodigo", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_OITO, "@", 0));
			valores.put("classeEnquadramentoIPI", this.verificaTamanho(EmissaoConstants.TAMANHO_CINCO, "@"));
			
			//valoresArquivo.put(new Integer(rowNum-1), valores);

			StringBuffer buffer = new StringBuffer();
		
			buffer.append(valores.get("indicadorProduto").toString());
			buffer.append("\t");
			buffer.append(valores.get("codigoProduto").toString());
			buffer.append("\t");
			buffer.append(valores.get("data").toString());
			buffer.append("\t");
			buffer.append(valores.get("descricaoProduto").toString());
			buffer.append("\t");
			buffer.append(valores.get("codigoNBM").toString());
			buffer.append("\t");
			buffer.append(valores.get("codigoNCM").toString());
			buffer.append("\t");
			buffer.append(valores.get("codigoNALADI").toString());
			buffer.append("\t");
			buffer.append(valores.get("substituicaoTributaria").toString());
			buffer.append("\t");
			buffer.append(valores.get("IPIControladoSelo").toString());
			buffer.append("\t");
			buffer.append(valores.get("grupoSelo").toString());
			buffer.append("\t");
			buffer.append(valores.get("subGrupoSelo").toString());
			buffer.append("\t");
			buffer.append(valores.get("corSelo").toString());
			buffer.append("\t");
			buffer.append(valores.get("serieSelo").toString());
			buffer.append("\t");
			buffer.append(valores.get("codigoMedida").toString());
			buffer.append("\t");
			buffer.append(valores.get("tipoProduto").toString());
			buffer.append("\t");
			buffer.append(valores.get("codigoGrupoIncentivo").toString());
			buffer.append("\t");
			buffer.append(valores.get("grupoSubstituicaoTributaria").toString());
			buffer.append("\t");
			buffer.append(valores.get("contaContabil").toString());
			buffer.append("\t");
			buffer.append(valores.get("tipoProdutoServico").toString());
			buffer.append("\t");
			buffer.append(valores.get("unidadeMedidaPadrao").toString());
			buffer.append("\t");
			buffer.append(valores.get("pesoUnitarioKg").toString());
			buffer.append("\t");
			buffer.append(valores.get("descricaoDetalhada").toString());
			buffer.append("\t");
			buffer.append(valores.get("FabricacaoEstabelecimento").toString());
			buffer.append("\t");
			buffer.append(valores.get("fatorConversao").toString());
			buffer.append("\t");
			buffer.append(valores.get("classificacaoICMS").toString());
			buffer.append("\t");
			buffer.append(valores.get("descricaoModelo").toString());
			buffer.append("\t");
			buffer.append(valores.get("origem").toString());
			buffer.append("\t");
			buffer.append(valores.get("grupoProdutoDICSE").toString());
			buffer.append("\t");
			buffer.append(valores.get("incidenciaPIS").toString());
			buffer.append("\t");
			buffer.append(valores.get("aliquotaPIS").toString());
			buffer.append("\t");
			buffer.append(valores.get("incidenciaCOFINS").toString());
			buffer.append("\t");
			buffer.append(valores.get("aliquotaCOFINS").toString());
			buffer.append("\t");
			buffer.append(valores.get("funrural").toString());
			buffer.append("\t");
			buffer.append(valores.get("petroleoEnergia").toString());
			buffer.append("\t");
			buffer.append(valores.get("produtoIncentivado").toString());
			buffer.append("\t");
			buffer.append(valores.get("ICMSDiferido").toString());
			buffer.append("\t");
			buffer.append(valores.get("capacidadeVolumetrica").toString());
			buffer.append("\t");
			buffer.append(valores.get("codigoEspecie").toString());
			if(valores.get("flagSped").equals("S")){
				buffer.append("\t");
				buffer.append(valores.get("classificacaoItem").toString());
				buffer.append("\t");
				buffer.append(valores.get("codigoBarras").toString());
				buffer.append("\t");
				buffer.append(valores.get("codigoANP").toString());
				buffer.append("\t");
				buffer.append(valores.get("idAnteriorProduto").toString());
				buffer.append("\t");
				buffer.append(valores.get("codigoAnteriorItem").toString());
				buffer.append("\t");
				buffer.append(valores.get("dataAlteracaoCodigo").toString());
				buffer.append("\t");
				buffer.append(valores.get("classeEnquadramentoIPI").toString());
			}
			noFormatFile.addLine(buffer);
		
		} catch (BaseFailureException e) {
			this.executaLog(super.getMessage(EmissaoResources.ERRO_GERACAO_SAFX2013), e);
			throw new EmissaoBusinessResourceException(super.getMessage(EmissaoResources.ERRO_GERACAO_SAFX2013), e);
		}
		
	}
	

	
	/**
	 * @see br.com.netservicos.novosms.emissao.core.facade.DisparoGeracaoArquivoSafxService#gerarLinhaSAFX43(java.util.Map, br.com.netservicos.framework.core.bean.DynamicBean, br.com.netservicos.framework.core.bean.DynamicBean, int)
	 * @ejb.interface-method view-type="both"
	 * @ejb.transaction type="RequiresNew"
	 */
	public void gerarLinhaSAFX43(Map v, DynamicBean bean, DynamicBean dynamicBean,  NoLayoutGenerateFile noFormatFile) {
		try {
				v = new HashMap<String,Object>();
				//Alteração CAT6 Leandro Ambrósio 19/02/2010
				long numeroSeqNotaFiscal = 0L;
				// Verifica se o bean contém uma variável denominada numeroSeqNotaFiscal
				// Caso contenha utiliza este valor para montar o arquivo
				if (bean.containsKey("numeroSeqNotaFiscal")){
					numeroSeqNotaFiscal =  ((new Long (bean.get("numeroSeqNotaFiscal").toString())).longValue());
				}
				
				v.put("codEmpresa", this.verificaTamanho(EmissaoConstants.TAMANHO_TRES, dynamicBean.get("codOperJdeMestre").toString()));
				v.put("codEstabelecimento", this.verificaTamanho(EmissaoConstants.TAMANHO_SEIS,	dynamicBean.get("codOperJdeFisc").toString()));
				v.put("dtEmissao", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_OITO,bean.get("DT_EMISSAO").toString(), 0));
				v.put("indicadorPessoaFJ", this.verificaTamanho(EmissaoConstants.TAMANHO_UM, "2"));
				v.put("codDestinatarioEmitente", this.verificaTamanho(EmissaoConstants.TAMANHO_UM+ EmissaoConstants.TAMANHO_QUATRO, bean.get("ID_ASSINANTE")));
				v.put("numDocumentoFiscal", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_DOIS, bean.get("NR_NOTA_FISCAL"),0));
				v.put("serie", this.verificaTamanho(EmissaoConstants.TAMANHO_TRES, bean.get("CC_SERIE_NOTA_FISCAL")));
				v.put("subserie", this.verificaTamanho(EmissaoConstants.TAMANHO_DOIS, "@"));
				//Alteração CAT6 - Caso a variável numeroSeqNotaFiscal seja diferente de 0
				//Significa que devemos utilizar o valor da mesma para a montar o arquivo
				if (numeroSeqNotaFiscal != 0){
					v.put("itemNotaFiscal", this.verificaTamanho(EmissaoConstants.TAMANHO_SETE, numeroSeqNotaFiscal));
				}else {
					v.put("itemNotaFiscal", this.verificaTamanho(EmissaoConstants.TAMANHO_SETE, 1));
				}				
				v.put("tipoItem", this.verificaTamanho(EmissaoConstants.TAMANHO_UM, "@"));
				v.put("indicadorProduto", this.verificaTamanho(EmissaoConstants.TAMANHO_UM, "1"));
				v.put("codProduto", this.verificaTamanho(EmissaoConstants.TAMANHO_TRES 	+ EmissaoConstants.TAMANHO_CINCO, bean.get("COD_PRODUTO")));
				v.put("codFiscal", this.verificaTamanho(EmissaoConstants.TAMANHO_QUATRO, bean.get("CC_COD_CFOP")));
				v.put("dtServico", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_OITO, bean.get("DT_EMISSAO"), 0));
				v.put("hrServico", this.verificaTamanho(EmissaoConstants.TAMANHO_SEIS, "@"));
				v.put("duracao", this.verificaTamanho(EmissaoConstants.TAMANHO_OITO, "@"));
				v.put("telefoneDestino", this.verificaTamanho(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_CINCO, "@"));
				v.put("valorDesconto", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_UM 	+ EmissaoConstants.TAMANHO_SETE, bean.get("VL_DESCONTO"), 2));
				v.put("valorServico", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_SETE, bean.get("VL_ITEM"), 2));
				v.put("adicaoDesconto", this.verificaTamanho(EmissaoConstants.TAMANHO_UM, "@"));
				v.put("aliquotaICMS", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_SETE, bean.get("PC_ALIQUOTA"), 4));
				v.put("valorICMS", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_UM 	+ EmissaoConstants.TAMANHO_SETE, bean.get("VL_IMPOSTO"), 2));
				v.put("baseTributada", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_SETE, bean.get("VL_BASE_CALCULO"), 2));
				v.put("baseIsenta", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_SETE, bean.get("VL_ISENTO"), 2));
				v.put("baseOutras", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_SETE, bean.get("VL_OUTRO"), 2));
				v.put("baseReducao", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_SETE, "@", 2));
				v.put("baseCalculoUFDestino", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_UM+ EmissaoConstants.TAMANHO_SETE, "@", 2));
				v.put("valorICMSUFDestino", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_UM	+ EmissaoConstants.TAMANHO_SETE, "@", 2));
				v.put("aliquotaICMSDestino", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_SETE, "@", 4));
				v.put("descProdutoServico", this.verificaTamanho(EmissaoConstants.TAMANHO_CINCO	+ EmissaoConstants.TAMANHO_ZERO, bean.get("DS_PRODUTO")));
				v.put("codPais", this.verificaTamanho(EmissaoConstants.TAMANHO_TRES, "105"));
				v.put("codSituacaoTributarioA", this.verificaTamanho(EmissaoConstants.TAMANHO_UM, "0"));
				v.put("codSituacaoTributarioB", this.verificaTamanho(EmissaoConstants.TAMANHO_DOIS, bean.get("COD_SIT_TRIB_B")));
				v.put("classificacaoServico", this.verificaTamanho(EmissaoConstants.TAMANHO_UM, "1"));
				v.put("foneAcesso", this.verificaTamanho(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_CINCO, "@"));
				v.put("baseISS", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_SETE, "@", 2));
				v.put("aliquotaISS", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_SETE, "@", 4));
				v.put("valorISS", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_SETE, "@", 2));
				v.put("valorSubsTributaria", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_SETE, "@", 2));
				v.put("baseSubsTributaria", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_UM	+ EmissaoConstants.TAMANHO_SETE, "@", 2));
				v.put("valorDescontoCondicional", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_SETE, "@", 2));
				v.put("classificacaoItem", this.verificaTamanho(EmissaoConstants.TAMANHO_QUATRO, bean.get("CC_CLASS_ITEM")));
				v.put("uniMedida", this.verificaTamanho(EmissaoConstants.TAMANHO_TRES, "UN"));
				v.put("qtdeContratada", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_SETE, "1.000000", 6));
				v.put("qtdeFornecida", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_SETE,	"1.000000", 6));
				v.put("valorOutrasDespesas", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_SETE, "@", 2));
				v.put("classificacaoDocFiscal", this.verificaTamanho(EmissaoConstants.TAMANHO_UM, "1"));
				v.put("codServico", this.verificaTamanho(EmissaoConstants.TAMANHO_QUATRO, "@"));
				v.put("baseIsentaISS", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_SETE,	"@", 2));
				v.put("baseISSTerceiros", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_SETE, "@", 2));
				v.put("contrato", this.verificaTamanho(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_QUATRO, "@"));
				v.put("CNPJOperadoraDest", this.verificaTamanho(EmissaoConstants.TAMANHO_UM	+ EmissaoConstants.TAMANHO_QUATRO, "@"));
				v.put("contaContabil", this.verificaTamanho(EmissaoConstants.TAMANHO_SETE + EmissaoConstants.TAMANHO_ZERO, "@"));
				// Flag para ligar/desligar as alterações SPED
				v.put("flagSped", bean.get("FLAG_SPED"));
				v.put("cpfOperadora", this.verificaTamanho(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_QUATRO, "@"));
				v.put("ufOperadora", this.verificaTamanho(EmissaoConstants.TAMANHO_DOIS, "@"));
				v.put("inscEstOperadora", this.verificaTamanho(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_QUATRO, "@"));
				v.put("especReceita", this.verificaTamanho(EmissaoConstants.TAMANHO_UM, "0"));
				v.put("valorUnitario", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_NOVE, bean.get("VL_ITEM"), 4));
				v.put("indicadorServicoPPago", this.verificaTamanho(EmissaoConstants.TAMANHO_UM, "@"));
				v.put("numCartaoPPago", this.verificaTamanho(EmissaoConstants.TAMANHO_DOIS + EmissaoConstants.TAMANHO_ZERO, "@"));
				v.put("codModalidadePPago", this.verificaTamanho(EmissaoConstants.TAMANHO_DOIS, "@"));
				v.put("horaDispCredPPago", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_SEIS, "@", 0));
				v.put("ufAcesso", this.verificaTamanho(EmissaoConstants.TAMANHO_DOIS, "@"));
				v.put("dataDispCredPPago", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_OITO, "@", 0));
				v.put("nfReferencia", this.verificaTamanho(EmissaoConstants.TAMANHO_NOVE, "@"));
				v.put("serieReferencia", this.verificaTamanho(EmissaoConstants.TAMANHO_TRES, "@"));
				v.put("dataNF", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_OITO, "@", 0));
				v.put("agenteArrecadador", this.verificaTamanho(EmissaoConstants.TAMANHO_DOIS + EmissaoConstants.TAMANHO_ZERO, "@"));
				v.put("ufRecarga", this.verificaTamanho(EmissaoConstants.TAMANHO_DOIS, "@"));
				v.put("dataPrimeiraRecarga", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_OITO, "@", 0));
				v.put("horaPrimeiraRecarga", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_SEIS, "@", 0));
				v.put("dataUltimaRecarga", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_OITO, "@", 0));
				v.put("horaUltimaRecarga", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_SEIS, "@", 0));
				v.put("numeroLotePIN", this.verificaTamanho(EmissaoConstants.TAMANHO_DOIS + EmissaoConstants.TAMANHO_ZERO, "@"));
				v.put("valorTerceiros", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_SETE, "@", 2));
				v.put("classItemCOTEPE", this.verificaTamanho(EmissaoConstants.TAMANHO_DOIS, "@"));
				v.put("valorPIS", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_SETE, "@", 2));
				v.put("valorBasePIS", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_SETE, "@", 2));
				v.put("valorAliqPIS", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_SETE, "@", 4));
				v.put("valorCOFINS", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_SETE, "@", 2));
				v.put("valorBaseCOFINS", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_SETE, "@", 2));
				v.put("valorAliqCOFINS", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_SETE, "@", 4));
				//v.put("indicadorFisReceita", this.verificaTamanho(EmissaoConstants.TAMANHO_UM, "2"));
				//v.put("codigoFisReceita", this.verificaTamanho(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_QUATRO, bean.get("ID_ASSINANTE")));
				v.put("indicadorFisReceita", this.verificaTamanho(EmissaoConstants.TAMANHO_UM, "@"));
				v.put("codigoFisReceita", this.verificaTamanho(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_QUATRO, "@"));
				v.put("aliquotaICMSST", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_SETE, "@", 4));
				//v.put("valorICMSFECP", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_SETE, "@", 2));
				v.put("indicadorTipoServico", this.verificaTamanho(EmissaoConstants.TAMANHO_UM, bean.get("IND_TP_SERV")));
				v.put("dataIniPresServico", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_OITO, bean.get("DAT_INIC_SERV").toString(), 0));
				v.put("dataFimPresServico", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_OITO, bean.get("DAT_FINAL_SERV").toString(), 0));
				v.put("codigoAreaTerminalFat", this.verificaTamanho(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_QUATRO, bean.get("COD_AREA_TERM_FAT")));
				v.put("terminalFaturado", this.verificaTamanho(EmissaoConstants.TAMANHO_DOIS + EmissaoConstants.TAMANHO_ZERO, bean.get("COD_TERM_FAT")));
			
				
				
				//valoresArquivo.put(rowNum-1, v);
				
				StringBuffer buffer = new StringBuffer();
				
				buffer.append(dynamicBean.get("codOperJdeMestre").toString());
				buffer.append("\t");
				buffer.append(dynamicBean.get("codOperJdeFisc").toString());
				buffer.append("\t");
				buffer.append(v.get("dtEmissao").toString());
				buffer.append("\t");
				buffer.append(v.get("indicadorPessoaFJ").toString());
				buffer.append("\t");
				buffer.append(v.get("codDestinatarioEmitente").toString());
				buffer.append("\t");
				buffer.append(v.get("numDocumentoFiscal").toString());
				buffer.append("\t");
				buffer.append(v.get("serie").toString());
				buffer.append("\t");
				buffer.append(v.get("subserie").toString());
				buffer.append("\t");
				buffer.append(v.get("itemNotaFiscal").toString());
				buffer.append("\t");
				buffer.append(v.get("tipoItem").toString());
				buffer.append("\t");
				buffer.append(v.get("indicadorProduto").toString());
				buffer.append("\t");
				buffer.append(v.get("codProduto").toString());
				buffer.append("\t");
				buffer.append(v.get("codFiscal").toString());
				buffer.append("\t");
				buffer.append(v.get("dtServico").toString());
				buffer.append("\t");
				buffer.append(v.get("hrServico").toString());
				buffer.append("\t");
				buffer.append(v.get("duracao").toString());
				buffer.append("\t");
				buffer.append(v.get("telefoneDestino").toString());
				buffer.append("\t");
				buffer.append(v.get("valorDesconto").toString());
				buffer.append("\t");
				buffer.append(v.get("valorServico").toString());
				buffer.append("\t");
				buffer.append(v.get("adicaoDesconto").toString());
				buffer.append("\t");
				buffer.append(v.get("aliquotaICMS").toString());
				buffer.append("\t");
				buffer.append(v.get("valorICMS").toString());
				buffer.append("\t");
				buffer.append(v.get("baseTributada").toString());
				buffer.append("\t");
				buffer.append(v.get("baseIsenta").toString());
				buffer.append("\t");
				buffer.append(v.get("baseOutras").toString());
				buffer.append("\t");
				buffer.append(v.get("baseReducao").toString());
				buffer.append("\t");
				buffer.append(v.get("baseCalculoUFDestino").toString());
				buffer.append("\t");
				buffer.append(v.get("valorICMSUFDestino").toString());
				buffer.append("\t");
				buffer.append(v.get("aliquotaICMSDestino").toString());
				buffer.append("\t");
				buffer.append(v.get("descProdutoServico").toString());
				buffer.append("\t");
				buffer.append(v.get("codPais").toString());
				buffer.append("\t");
				buffer.append(v.get("codSituacaoTributarioA").toString());
				buffer.append("\t");
				buffer.append(v.get("codSituacaoTributarioB").toString());
				buffer.append("\t");
				buffer.append(v.get("classificacaoServico").toString());
				buffer.append("\t");
				buffer.append(v.get("foneAcesso").toString());
				buffer.append("\t");
				buffer.append(v.get("baseISS").toString());
				buffer.append("\t");
				buffer.append(v.get("aliquotaISS").toString());
				buffer.append("\t");
				buffer.append(v.get("valorISS").toString());
				buffer.append("\t");
				buffer.append(v.get("valorSubsTributaria").toString());
				buffer.append("\t");
				buffer.append(v.get("baseSubsTributaria").toString());
				buffer.append("\t");
				buffer.append(v.get("valorDescontoCondicional").toString());
				buffer.append("\t");
				buffer.append(v.get("classificacaoItem").toString());
				buffer.append("\t");
				buffer.append(v.get("uniMedida").toString());
				buffer.append("\t");
				buffer.append(v.get("qtdeContratada").toString());
				buffer.append("\t");
				buffer.append(v.get("qtdeFornecida").toString());
				buffer.append("\t");
				buffer.append(v.get("valorOutrasDespesas").toString());
				buffer.append("\t");
				buffer.append(v.get("classificacaoDocFiscal").toString());
				buffer.append("\t");
				buffer.append(v.get("codServico").toString());
				buffer.append("\t");
				buffer.append(v.get("baseIsentaISS").toString());
				buffer.append("\t");
				buffer.append(v.get("baseISSTerceiros").toString());
				buffer.append("\t");
				buffer.append(v.get("contrato").toString());
				buffer.append("\t");
				buffer.append(v.get("CNPJOperadoraDest").toString());
				buffer.append("\t");
				buffer.append(v.get("contaContabil").toString());
				if(v.get("flagSped").equals("S")){
					buffer.append("\t");
					buffer.append(v.get("cpfOperadora").toString());
					buffer.append("\t");
					buffer.append(v.get("ufOperadora").toString());
					buffer.append("\t");
					buffer.append(v.get("inscEstOperadora").toString());
					buffer.append("\t");
					buffer.append(v.get("especReceita").toString());
					buffer.append("\t");
					buffer.append(v.get("valorUnitario").toString());
					buffer.append("\t");
					buffer.append(v.get("indicadorServicoPPago").toString());
					buffer.append("\t");
					buffer.append(v.get("numCartaoPPago").toString());
					buffer.append("\t");
					buffer.append(v.get("codModalidadePPago").toString());
					buffer.append("\t");
					buffer.append(v.get("horaDispCredPPago").toString());
					buffer.append("\t");
					buffer.append(v.get("ufAcesso").toString());
					buffer.append("\t");
					buffer.append(v.get("dataDispCredPPago").toString());
					buffer.append("\t");
					buffer.append(v.get("nfReferencia").toString());
					buffer.append("\t");
					buffer.append(v.get("serieReferencia").toString());
					buffer.append("\t");
					buffer.append(v.get("dataNF").toString());
					buffer.append("\t");
					buffer.append(v.get("agenteArrecadador").toString());
					buffer.append("\t");
					buffer.append(v.get("ufRecarga").toString());
					buffer.append("\t");
					buffer.append(v.get("dataPrimeiraRecarga").toString());
					buffer.append("\t");
					buffer.append(v.get("horaPrimeiraRecarga").toString());
					buffer.append("\t");
					buffer.append(v.get("dataUltimaRecarga").toString());
					buffer.append("\t");
					buffer.append(v.get("horaUltimaRecarga").toString());
					buffer.append("\t");
					buffer.append(v.get("numeroLotePIN").toString());
					buffer.append("\t");
					buffer.append(v.get("valorTerceiros").toString());
					buffer.append("\t");
					buffer.append(v.get("classItemCOTEPE").toString());		
					buffer.append("\t");
					buffer.append(v.get("valorPIS").toString());
					buffer.append("\t");
					buffer.append(v.get("valorBasePIS").toString());
					buffer.append("\t");
					buffer.append(v.get("valorAliqPIS").toString());
					buffer.append("\t");
					buffer.append(v.get("valorCOFINS").toString());
					buffer.append("\t");
					buffer.append(v.get("valorBaseCOFINS").toString());
					buffer.append("\t");
					buffer.append(v.get("valorAliqCOFINS").toString());
					buffer.append("\t");
					buffer.append(v.get("indicadorFisReceita").toString());
					buffer.append("\t");
					buffer.append(v.get("codigoFisReceita").toString());
					buffer.append("\t");
					buffer.append(v.get("aliquotaICMSST").toString());
					buffer.append("\t");
					/*buffer.append(v.get("valorICMSFECP").toString());
					buffer.append("\t");*/
					buffer.append(v.get("indicadorTipoServico").toString());
					buffer.append("\t");
					buffer.append(v.get("dataIniPresServico").toString());
					buffer.append("\t");
					buffer.append(v.get("dataFimPresServico").toString());
					buffer.append("\t");
					buffer.append(v.get("codigoAreaTerminalFat").toString());
					buffer.append("\t");
					buffer.append(v.get("terminalFaturado").toString());
				}
				noFormatFile.addLine(buffer);
			
				
		} catch (BaseFailureException e) {
			this.executaLog(super.getMessage(EmissaoResources.ERRO_PREENCHER_SAFX43), e);
			throw new EmissaoBusinessResourceException(super.getMessage(EmissaoResources.ERRO_PREENCHER_SAFX43), e);
		}
		
	}
	
	
	
	
	/**
	 * @see br.com.netservicos.novosms.emissao.core.facade.DisparoGeracaoArquivoSafxService#gerarLinhaSAFX04(java.util.Map, br.com.netservicos.framework.core.bean.DynamicBean, br.com.netservicos.framework.core.bean.DynamicBean, int)
	 * @ejb.interface-method view-type="both"
	 * @ejb.transaction type="RequiresNew"
	 */
	public void gerarLinhaSAFX04(Map valores, DynamicBean bean, DynamicBean dynamicBean, NoLayoutGenerateFile noFormatFile) {
		try {
			valores = new HashMap<String,Object>();
			valores.put("indicadorPessoaFJ", this.verificaTamanho(EmissaoConstants.TAMANHO_UM, "2"));
			valores.put("codPessoaFJ", this.verificaTamanho(EmissaoConstants.TAMANHO_UM	+ EmissaoConstants.TAMANHO_QUATRO, bean.get("ID_ASSINANTE")));
			valores.put("dtInicioInclusao", this.verificaTamanhoNum(EmissaoConstants.TAMANHO_OITO, bean.get("DT_EMISSAO"), 0));
			valores.put("indicadorConteudoCod", this.verificaTamanho(EmissaoConstants.TAMANHO_UM, "1"));
			valores.put("razaoSocial", this.verificaTamanho(EmissaoConstants.TAMANHO_SETE+ EmissaoConstants.TAMANHO_ZERO, bean.get("CC_NOME_RAZAO_SOCIAL")));
			valores.put("cpfCgc", this.verificaTamanho(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_QUATRO, bean.get("CC_CPF_CNPJ")));
			valores.put("codAtividadeEcon", this.verificaTamanho(EmissaoConstants.TAMANHO_SETE, "@"));
			valores.put("inscricaoEstadual", this.verificaTamanho(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_QUATRO, bean.get("CC_INSCRICAO_ESTADUAL")));
			valores.put("inscricaoMunicipal", this.verificaTamanho(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_QUATRO, "@"));
			valores.put("inscricaoSuframa", this.verificaTamanho(EmissaoConstants.TAMANHO_UM+ EmissaoConstants.TAMANHO_QUATRO, bean.get("INSC_SUFRAMA")));
			valores.put("nomeFantasia", this.verificaTamanho(EmissaoConstants.TAMANHO_CINCO	+ EmissaoConstants.TAMANHO_ZERO, bean.get("CC_NOME_RAZAO_SOCIAL")));
			valores.put("endereco", this.verificaTamanho(EmissaoConstants.TAMANHO_CINCO	+ EmissaoConstants.TAMANHO_ZERO, bean.get("CC_LOGRADOURO")));
			valores.put("numEndereco", this.verificaTamanho(EmissaoConstants.TAMANHO_UM	+ EmissaoConstants.TAMANHO_ZERO, bean.get("CC_NUMERO")));
			valores.put("complementoEndereco", this.verificaTamanho(EmissaoConstants.TAMANHO_QUATRO	+ EmissaoConstants.TAMANHO_CINCO, bean.get("CC_COMPLEMENTO")));
			valores.put("bairro", this.verificaTamanho(EmissaoConstants.TAMANHO_DOIS+ EmissaoConstants.TAMANHO_ZERO, bean.get("CC_BAIRRO")));
			valores.put("municipio", this.verificaTamanho(EmissaoConstants.TAMANHO_TRES	+ EmissaoConstants.TAMANHO_ZERO, bean.get("CC_CIDADE")));
			valores.put("distrito", this.verificaTamanho(EmissaoConstants.TAMANHO_DOIS + EmissaoConstants.TAMANHO_ZERO, "@"));
			valores.put("subDistrito", this.verificaTamanho(EmissaoConstants.TAMANHO_DOIS + EmissaoConstants.TAMANHO_ZERO, "@"));
			valores.put("uf", this.verificaTamanho(EmissaoConstants.TAMANHO_DOIS, bean.get("CC_ESTADO")));
			valores.put("cep", this.verificaTamanho(EmissaoConstants.TAMANHO_OITO, bean.get("CC_CEP")));
			valores.put("pais", this.verificaTamanho(EmissaoConstants.TAMANHO_TRES, "105"));
			valores.put("ddd", this.verificaTamanho(EmissaoConstants.TAMANHO_CINCO, "@"));
			valores.put("telefone", this.verificaTamanho(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_ZERO, bean.get("CC_TELEFONE")));
			valores.put("fax", this.verificaTamanho(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_ZERO, "@"));
			valores.put("codMunicipio", this.verificaTamanho(EmissaoConstants.TAMANHO_CINCO,  bean.get("CD_MUNICIPIO_IBGE")));
			valores.put("classePessoaFJ", this.verificaTamanho(EmissaoConstants.TAMANHO_DOIS, "@"));
			valores.put("classeEmpresa", this.verificaTamanho(EmissaoConstants.TAMANHO_DOIS, "@"));
			valores.put("pessoaFisContribuinte", this.verificaTamanho(EmissaoConstants.TAMANHO_UM, "@"));
			valores.put("codContabil", this.verificaTamanho(EmissaoConstants.TAMANHO_DOIS + EmissaoConstants.TAMANHO_ZERO, "@"));
			valores.put("cei", this.verificaTamanho(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_DOIS, "@"));
			valores.put("indicadorClienteSID", this.verificaTamanho(EmissaoConstants.TAMANHO_UM, "N"));
			valores.put("cpfCnpjIcms115", this.verificaTamanho(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_QUATRO, "@"));
			valores.put("socContaParticipacao", this.verificaTamanho(EmissaoConstants.TAMANHO_UM, "@"));
			valores.put("cbo", this.verificaTamanho(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_ZERO,"@"));
			valores.put("numPisPasep", this.verificaTamanho(EmissaoConstants.TAMANHO_UM	+ EmissaoConstants.TAMANHO_UM, "@"));
			valores.put("catTrabalhador", this.verificaTamanho(EmissaoConstants.TAMANHO_DOIS, "@"));
			valores.put("ocorrenciaTrabalhador", this.verificaTamanho(EmissaoConstants.TAMANHO_DOIS, "@"));
			valores.put("caixaPostal", this.verificaTamanho(EmissaoConstants.TAMANHO_DOIS + EmissaoConstants.TAMANHO_ZERO, "@"));
			valores.put("numCepCaixaPostal", this.verificaTamanho(EmissaoConstants.TAMANHO_OITO, "@"));
			// Flag para ligar/desligar as alterações SPED
			valores.put("flagSped", bean.get("FLAG_SPED"));
			valores.put("email", this.verificaTamanho(EmissaoConstants.TAMANHO_CINCO + EmissaoConstants.TAMANHO_ZERO, bean.get("E_MAIL")));
			valores.put("indFomeZero", this.verificaTamanho(EmissaoConstants.TAMANHO_UM, bean.get("IND_FOME_ZERO")));
			valores.put("lougradouro", this.verificaTamanho(EmissaoConstants.TAMANHO_UM + EmissaoConstants.TAMANHO_ZERO, "@"));
			valores.put("enquadroSimples", this.verificaTamanho(EmissaoConstants.TAMANHO_UM, "@"));
			
			StringBuffer buffer = new StringBuffer();
			buffer.append(valores.get("indicadorPessoaFJ").toString());
			buffer.append("\t");
			buffer.append(valores.get("codPessoaFJ").toString());
			buffer.append("\t");
			buffer.append(valores.get("dtInicioInclusao").toString());
			buffer.append("\t");
			buffer.append(valores.get("indicadorConteudoCod").toString());
			buffer.append("\t");
			buffer.append(valores.get("razaoSocial").toString());//null
			buffer.append("\t");
			buffer.append(valores.get("cpfCgc").toString());
			buffer.append("\t");
			buffer.append(valores.get("codAtividadeEcon").toString());
			buffer.append("\t");
			buffer.append(valores.get("inscricaoEstadual").toString());
			buffer.append("\t");
			buffer.append(valores.get("inscricaoMunicipal").toString());
			buffer.append("\t");
			buffer.append(valores.get("inscricaoSuframa").toString());
			buffer.append("\t");
			buffer.append(valores.get("nomeFantasia").toString());//null
			buffer.append("\t");
			buffer.append(valores.get("endereco").toString());
			buffer.append("\t");
			buffer.append(valores.get("numEndereco").toString());
			buffer.append("\t");
			buffer.append(valores.get("complementoEndereco").toString());
			buffer.append("\t");
			buffer.append(valores.get("bairro").toString());
			buffer.append("\t");
			buffer.append(valores.get("municipio").toString());
			buffer.append("\t");
			buffer.append(valores.get("distrito").toString());
			buffer.append("\t");
			buffer.append(valores.get("subDistrito").toString());
			buffer.append("\t");
			buffer.append(valores.get("uf").toString());
			buffer.append("\t");
			buffer.append(valores.get("cep").toString());
			buffer.append("\t");
			buffer.append(valores.get("pais").toString());
			buffer.append("\t");
			buffer.append(valores.get("ddd").toString());
			buffer.append("\t");
			buffer.append(valores.get("telefone").toString());
			buffer.append("\t");
			buffer.append(valores.get("fax").toString());
			buffer.append("\t");
			buffer.append(valores.get("codMunicipio").toString());
			buffer.append("\t");
			buffer.append(valores.get("classePessoaFJ").toString());
			buffer.append("\t");
			buffer.append(valores.get("classeEmpresa").toString());
			buffer.append("\t");
			buffer.append(valores.get("pessoaFisContribuinte").toString());
			buffer.append("\t");
			buffer.append(valores.get("codContabil").toString());
			buffer.append("\t");
			buffer.append(valores.get("cei").toString());
			buffer.append("\t");
			buffer.append(valores.get("indicadorClienteSID").toString());
			buffer.append("\t");
			buffer.append(valores.get("cpfCnpjIcms115").toString());
			buffer.append("\t");
			buffer.append(valores.get("socContaParticipacao").toString());
			buffer.append("\t");
			buffer.append(valores.get("cbo").toString());
			buffer.append("\t");
			buffer.append(valores.get("numPisPasep").toString());
			buffer.append("\t");
			buffer.append(valores.get("catTrabalhador").toString());
			buffer.append("\t");
			buffer.append(valores.get("ocorrenciaTrabalhador").toString());
			buffer.append("\t");
			buffer.append(valores.get("caixaPostal").toString());
			buffer.append("\t");
			buffer.append(valores.get("numCepCaixaPostal").toString());
			if(valores.get("flagSped").equals("S")){
				buffer.append("\t");
				buffer.append(valores.get("email").toString());
				buffer.append("\t");
				buffer.append(valores.get("indFomeZero").toString());
				buffer.append("\t");
				buffer.append(valores.get("lougradouro").toString());
				buffer.append("\t");
				buffer.append(valores.get("enquadroSimples").toString());
			}
			noFormatFile.addLine(buffer);
		
			
			//valoresArquivo.put(new Integer(rowNum-1), valores);
		} catch (BaseFailureException e) {
			this.executaLog(super.getMessage(EmissaoResources.ERRO_GERACAO_SAFX04), e);
			throw new EmissaoBusinessResourceException(super.getMessage(EmissaoResources.ERRO_GERACAO_SAFX04), e);
		}
	}
	
	
	
	/**
	 * Verifica o tamanho máximo de cada valor e retorna o value até o tamanho máximo permitido e retira os pontos dos.
	 * valores numéricos
	 * 
	 * @since 17/08/2007
	 * @param maxCaracteres tamanho máximo permitido
	 * @param valor conteúdo da coluna
	 * @param casaDecimal quantidade de casas decimais
	 * @return retorna conteudo da coluna de acordo com o tamanho máximo permitido
	 */
	private  Object verificaTamanhoNum(final String maxCaracteres, final Object valor, final int casaDecimal) {
		try{
			if(!valor.equals("@")){
				final int maximo = Integer.parseInt(maxCaracteres);		
				String valorObj = String.valueOf(valor);				
				
				if(!valorObj.equalsIgnoreCase("null") || valor != null ){
					//Verifica se é valor(.) ou data(-)
					if(!findPattern(valorObj, "\\-")){
						String[] valorDecimal = valorObj.split("\\.");				
						
						switch(valorDecimal.length){
							case	1	:	/** Valor xx **/
								for(int i=0; i< casaDecimal; i++){
									valorDecimal[0] += 0;
								}
								valorObj = valorDecimal[0];
							break;
							case	2	:	/** Valor xx.x **/
								while(valorDecimal[1].length() < casaDecimal){
									valorDecimal[1] += 0;
								}
								valorObj = valorDecimal[0] +"."+ valorDecimal[1];
							break;
						}
					}
					valorObj = valorObj.replaceAll("[\\.\\-]+", "");
					
					//verifica se o value esta dentro do tamanho permitido 
					if (valorObj.length() > maximo) {
						return valorObj.substring(0, maximo);
					} else {
						return valorObj;
					}
				}else{
					return "@";
				}
			}else{
				return "@";
			}
		}catch(NullPointerException e){
			this.executaLog(NULLPOINTER_VERIFICA_TAM_NUM, e);
			return "@";
		}
	}
	
	/**
	 * 
	 * @since 10/12/2007
	 * @param word
	 * @param patternStr
	 * @return
	 */
	private boolean findPattern(final String word, final String patternStr){
	     Pattern pattern = Pattern.compile(patternStr);
	     Matcher matcher = pattern.matcher(word);
	     return matcher.find();
	}
	
	/**
	 * Verifica o tamanho máximo de cada valor e retorna o value até o tamanho máximo permitido.
	 * 
	 * @since 08/08/2007
	 * @param m tamanho máximo de caracteres da coluna
	 * @param valor conteúdo da coluna
	 * @param alfaNum "era" o tipo da coluna. A ou N
	 * @return retorna o conteudo da coluna
	 */
	private Object verificaTamanho(final String m, final Object valor) {
		
		final int max = Integer.parseInt(m);
		final String value = String.valueOf(valor);
		if(!value.equalsIgnoreCase("null") || valor != null){	
			String saida = "";
			if (value.length() > max) {
				saida = value.substring(0, max);
			} else {
				saida = value;
			}
			return saida;
		}else{
			return "@";
		}
	}
	
	/**
	 * Adiciona Exception no Log.
	 * 
	 * @since 17/08/2007
	 * @param key mensagem do arquivo.properties
	 * @param throwable objeto da excessão
	 */
	private void executaLog(final String key, final Throwable throwable) {
		try {
			log.error(new BasicAttachmentMessage(super.getMessage(key) + throwable.getMessage(), AttachmentMessageLevel.ERROR));
		} catch (Exception e) {
			throw new BaseFailureException(super.getMessage(EmissaoResources.ERRO_GERAR_LOG), e);
		}
	}
	
	/**
	 * @see br.com.netservicos.novosms.emissao.core.facade.DisparoGeracaoArquivoSafxService#arquivoDiretorio(java.util.Map, br.com.netservicos.framework.core.bean.DynamicBean, br.com.netservicos.framework.core.bean.DynamicBean, int)
	 * @ejb.interface-method view-type="both"
	 * @ejb.transaction type="Required"
	 */
	public File arquivoDiretorio(final DynamicBean dynamicBean, final String aa) {
		try {
			String nomeArquivo = this.geraNomeArquivo(dynamicBean.get("cidContrato").toString(), aa);
			String nomeDir = this.geraPathArquivo(dynamicBean.get("localDestinoArquivo").toString(),
							 this.geraNomeDiretorio(dynamicBean));
			File filePath = new File(nomeDir);
			File file = new File(filePath, nomeArquivo);
			return file;
		} catch (BaseFailureException b) {
			throw b;
		}
	}
	/**
	 * 
	 * @since 25/09/2007
	 * @param localDestino
	 * @param nomeDiretorioArquivo
	 * @return
	 */
	private String geraPathArquivo(final String localDestino, final String nomeDiretorioArquivo){
		StringBuilder builder = new StringBuilder();
		builder.append(localDestino);
		builder.append(File.separator);
		builder.append(nomeDiretorioArquivo);
		return builder.toString();
	}
	
	/**
	 * @since 17/08/2007
	 * @param dynamicBean parametros encapsulados. cidContrato e ccAnoMesEmissao
	 * @return retorna diretorio e subdiretorio onde vai ser salvo os arquivos
	 */
	private String geraNomeDiretorio(final DynamicBean dynamicBean) {
		final StringBuilder builder = new StringBuilder();
		try {
			builder.append(File.separator);
			builder.append(dynamicBean.get("cidContrato").toString());
			builder.append(File.separator);
			builder.append(dynamicBean.get("ccAnoMesEmissao").toString());
			builder.append(File.separator);
		} catch (Exception i) {
			this.executaLog(super.getMessage(EmissaoResources.ERRO_GERAR_NOME_DIRETORIO), i);
			throw new BaseFailureException(super.getMessage(EmissaoResources.ERRO_GERAR_NOME_DIRETORIO), i);
		}
		return builder.toString();
	}

	/**
	 * Gera o nome do arquivo segundo regras definidas na FAT128 da RF77.
	 * 
	 * @since 08/08/2007
	 * @param codOperadora codOperadora código da operadora
	 * @param codArquivo código do arquivo
	 * @param tipo tipo de arquivo(eg. txt)
	 * @return retorna nome do arquivo de acordo com fat128
	 * @throws BaseFailureException
	 */
	private String geraNomeArquivo(final String codOperadora, final String nmArquivo) {
		StringBuilder sb = new StringBuilder();
		try{
			sb.append(nmArquivo);	
			sb.append("_");
			sb.append(codOperadora);
			sb.append(".txt");
		}catch(Exception i){
			this.executaLog(super.getMessage(EmissaoResources.ERRO_GERAR_NOME_ARQUIVO), i);
			throw new BaseFailureException(super.getMessage(EmissaoResources.ERRO_GERAR_NOME_ARQUIVO), i);
		}
		return sb.toString();
	}
}
