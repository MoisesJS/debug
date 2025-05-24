package br.com.netservicos.novosms.emissao.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Stack;

import br.com.netservicos.core.bean.pp.PpVlrParceiroFaturaBean;
import br.com.netservicos.core.bean.sn.SnCidadeOperadoraBean;
import br.com.netservicos.core.bean.sn.SnControleArquivoBean;
import br.com.netservicos.core.bean.sn.SnRegistroEmissaoBean;
import br.com.netservicos.core.bean.sn.SnTipoLoteBean;
import br.com.netservicos.framework.core.bean.DomainBean;
import br.com.netservicos.framework.file.writer.GenerateFile;

public class ArquivoPrintHouseDTO extends DomainBean {

	public static final String PRIMARY_KEY = "pk fake";

	/**
	 * 
	 */
	private static final long serialVersionUID = -8660164507859994892L;

	/**
	 * Hash do ArquivoPrintHouseZipDTO's key - idOperadora + "_" + siglaTipoLote
	 */
	private Hashtable<String, ArquivoPrintHouseFileDTO> arquivoPrintHouseZips = null;

	/**
	 * Uma registro de emissão por 'n' lotes
	 */
	private SnRegistroEmissaoBean registroEmissao;

	/**
	 * Sigla de tipo de lote
	 */
	private String siglaTipoLote;
	
	
	private String nomeBase;
	
	
	private Date dtVencimento;

	/**
	 * Stack de arquivos gerados
	 */
	private Stack<ArquivoGerado> arquivosGerados;

	/**
	 * Seguencia de nome de arquivo
	 */
	private Integer sequecialNomeArquivo;
	/**
	 * QT_LINHA_ARQ_PRINT , max. numero de quantidade de linhas dentro uma
	 * arquivo
	 */
	private Integer quantidadeMaximaNumeroLinhasArquivo;
	/**
	 * QT_FAT_ARQ_PRINT, quantidade de faturas que o arquivo a ser gerado para a
	 * Print suporta.
	 */
	private Integer quantidadeFaturaPorArquivoPrint;
	/**
	 * QT_LN_PAG_FAT_PRINT, Qtidade máxima de linhas por folha da fatura
	 */
	private Integer quantidadeMaximaLinhasPorFolha;
	/**
	 * EXT_ARQ_PRINT
	 */
	private String extensaoArquivoPrint;
	/**
	 * NM_TEMP_ARQ_PRINT
	 */
	private String nomeTemporarioDoArquivoPrint;
	/**
	 * DIR_ARQ_PRINT
	 */
	private String diretorioGravacaoArquivoPrint;
	
	private List<PpVlrParceiroFaturaBean> valoresFaturas; 
	
	
	public void addValoreFatura( PpVlrParceiroFaturaBean valorFatura ){
		
		if( this.valoresFaturas == null) {
			this.valoresFaturas = new ArrayList<PpVlrParceiroFaturaBean>();
		}		
		this.valoresFaturas.add(valorFatura);		
	}
	
	public List<PpVlrParceiroFaturaBean> getValoresFaturas() {
		return valoresFaturas;
	}

	/**
	 * @param siglaTipoLote
	 *            Sigla do tipo do lote
	 */
	public ArquivoPrintHouseDTO(String siglaTipoLote) {
		super(PRIMARY_KEY);

		this.arquivosGerados = new Stack<ArquivoGerado>();
		this.sequecialNomeArquivo = new Integer(0);
		this.siglaTipoLote = siglaTipoLote;
		this.arquivoPrintHouseZips = new Hashtable<String, ArquivoPrintHouseFileDTO>();
	}

	/**
	 * @number RF015_RF021 Pegar o ArquivoPrintHouseZipDTO para o operadora e
	 *         sigla do tipo lote
	 * @param idOperadora
	 *            Id do operadora
	 * @param siglaTipoLote
	 *            Sigla do tipo do lote
	 * @return ArquivoPrintHouseZipDTO se existe , se não nulo
	 */
	/*public ArquivoPrintHouseZipDTO getArquivoPrintHouseZipDTO(
			Integer idOperadora, String siglaTipoLote) {

		String key = idOperadora + "_" + siglaTipoLote;

		if (this.arquivoPrintHouseZips.containsKey(key)) {
			// return (ArquivoPrintHouseZipDTO) this.arquivoPrintHouseZips
			// .get(key);
			// FIXME justar DTO  
			return null;
		} else {
			return null;
		}
	}*/
	
	public ArquivoPrintHouseZipDTO getArquivoPrintHouseZipDTO(Integer idOperadora, String siglaTipoLote, String prefixo) {
		
		String key = idOperadora + "_" + siglaTipoLote + "_" + prefixo;
		
		if (this.arquivoPrintHouseZips.containsKey(key)) {
			return (ArquivoPrintHouseZipDTO) this.arquivoPrintHouseZips.get(key);
		} else {
			return null;
		}
	}

	public ArquivoPrintHouseZipDTO getArquivoPrintHouseZipDTO(String idOperadora, String siglaTipoLote, String prefixo) {
		
		String key = idOperadora + "_" + siglaTipoLote + "_" + prefixo;
		
		if (this.arquivoPrintHouseZips.containsKey(key)) {
			return (ArquivoPrintHouseZipDTO) this.arquivoPrintHouseZips.get(key);
		} else {
			return null;
		}
	}

	/**
	 * @number RF015_RF021 Addicionar o ArquivoPrintHouseZipDTO dentro o
	 *         hashtable
	 * @param idOperadora
	 *            Id do operadora
	 * @param siglaTipoLote
	 *            Sigla do tipo do lote
	 * @param arquivoPrintHouseZipDTO
	 *            ArquivoPrintHouseZipDTO para adicionar
	 */
	public void addArquivoPrintHouseZipDTO(Integer idOperadora,
			String siglaTipoLote,
			ArquivoPrintHouseFileDTO arquivoPrintHouseZipDTO) {
		String key = idOperadora + "_" + siglaTipoLote;
		this.arquivoPrintHouseZips.put(key, arquivoPrintHouseZipDTO);
	}

	/**
	 * @number RF015_RF021 Pegar o ArquivoPrintHouseZipDTO para o operadora e
	 *         sigla do tipo lote
	 * @param idOperadora
	 *            Id do operadora
	 * @param siglaTipoLote
	 *            Sigla do tipo do lote
	 * @return ArquivoPrintHouseZipDTO se existe , se não nulo
	 */
	public ArquivoPrintHouseZipDTO getArquivoPrintHouseZipDTO(
			String codOperadora, String siglaTipoLote) { 

		String key = codOperadora + "_" + siglaTipoLote;
		
		if (this.arquivoPrintHouseZips.containsKey(key)) {
			return (ArquivoPrintHouseZipDTO) this.arquivoPrintHouseZips.get(key);
		} else {
			return null;
		}
	}

	/**
	 * @number RF015_RF021 Addicionar o ArquivoPrintHouseZipDTO dentro o
	 *         hashtable
	 * @param idOperadora
	 *            Id do operadora
	 * @param siglaTipoLote
	 *            Sigla do tipo do lote
	 * @param arquivoPrintHouseZipDTO
	 *            ArquivoPrintHouseZipDTO para adicionar
	 */
	public void addArquivoPrintHouseZipDTO(String codOperadora,
			String siglaTipoLote, ArquivoPrintHouseFileDTO arquivoPrintHouseZipDTO) {
		String key = codOperadora + "_" + siglaTipoLote;
		this.arquivoPrintHouseZips.put(key, arquivoPrintHouseZipDTO);
	}

	public void addArquivoPrintHouseZipDTO(String idOperadora, String siglaTipoLote, String prefixo, ArquivoPrintHouseFileDTO arquivoPrintHouseZipDTO) {
		String key = idOperadora + "_" + siglaTipoLote + "_" + prefixo;
		this.arquivoPrintHouseZips.put(key, arquivoPrintHouseZipDTO);
	}
	
	/**
	 * Pegar todos os ArquivoPrintHouseZipDTO gerado
	 * 
	 * @return Collection do ArquivoPrintHouseZipDTO gerado
	 */
	public Collection<ArquivoPrintHouseFileDTO> getArquivoPrintHouseZipDTOs() {
		return Collections.list(this.arquivoPrintHouseZips.elements());
	}

	/**
	 * Pegar o arquivo gerado atual <br>
	 * O arquivo que o processo esta utilizando na este momento
	 * 
	 * @return ArquivoGerado atual
	 */
	public ArquivoGerado getCurrentArquivo() {
		return (ArquivoGerado) this.arquivosGerados.peek();
	}

	/**
	 * @number RF015_RF021 Criar o novo ArquivoGerado e colocar em cima do Stack
	 * @param operadora
	 *            operadora do Arquivo
	 * @param controleSegregacao
	 *            Se o controle se segregacao esta ligado ou não
	 * @return
	 */
	public ArquivoGerado iniciarArquivo(SnCidadeOperadoraBean cidade,
			Boolean controleSegregacao) {
		ArquivoGerado arquivoGerado = new ArquivoGerado(cidade,
				controleSegregacao);
		this.arquivosGerados.push(arquivoGerado);
		return arquivoGerado;
	}

	/**
	 * Seta o seguenica de nome do arquivo para 0
	 */
	public void finalizeArquivo() {
		this.sequecialNomeArquivo = new Integer(0);
	}

	/**
	 * Pegar todos os arquivos gerados
	 * 
	 * @return Collection do ArquivosGerado
	 */
	public Collection getArquivosGerados() {
		return Collections.list(this.arquivosGerados.elements());
	}

	public Integer getQuantidadeMaximaNumeroLinhasArquivo() {
		return quantidadeMaximaNumeroLinhasArquivo;
	}

	public void setQuantidadeMaximaNumeroLinhasArquivo(
			Integer quantidadeMaximaNumeroLinhasArquivo) {
		this.quantidadeMaximaNumeroLinhasArquivo = quantidadeMaximaNumeroLinhasArquivo;
	}

	/**
	 * @number RF015_RF021 Calcular o total do faturas emitidas
	 * @return total do faturas emitidas
	 */
	public Integer getQuantidadeFaturasEmitidads() {

		int qtdFaturas = 0;

		Enumeration<ArquivoGerado> enumArquivosGerados = this.arquivosGerados
				.elements();
		while (enumArquivosGerados.hasMoreElements()) {
			ArquivoGerado arquivoGerado = (ArquivoGerado) enumArquivosGerados
					.nextElement();
			qtdFaturas += (arquivoGerado.getQuantidadeFaturasEmitidas() == null ? 0
					: arquivoGerado.getQuantidadeFaturasEmitidas().intValue());

		}

		return new Integer(qtdFaturas);

	}

	// public void addPostagem(Integer idTipoPostagem, Integer idOperadora, Date
	// dataVencimento, Integer qtdPaginas) {
	// String hashKey = idTipoPostagem + "_" + idOperadora + "_" +
	// keyDateFormat.format(dataVencimento);
	// if (this.summarioPostagem.containsKey(hashKey)) {
	// Postagem postagem = (Postagem) this.summarioPostagem.get(hashKey);
	// Integer qtdPaginasTotal = new
	// Integer(postagem.getQuantidadePostagem().intValue() +
	// qtdPaginas.intValue());
	// postagem.setQuantidadePostagem(qtdPaginasTotal);
	// } else {
	// Postagem postagem = new Postagem();
	// postagem.setIdOperadora(idOperadora);
	// postagem.setIdTipoPostagem(idTipoPostagem);
	// postagem.setQuantidadePostagem(qtdPaginas);
	// postagem.setDtVencimento(dataVencimento);
	// this.summarioPostagem.put(hashKey, postagem);
	// }
	// }
	//    
	// public Collection getPostagens() {
	// return this.summarioPostagem.values();
	// }

	public SnRegistroEmissaoBean getRegistroEmissao() {
		return registroEmissao;
	}

	public void setRegistroEmissao(SnRegistroEmissaoBean registroEmissao) {
		this.registroEmissao = registroEmissao;
	}

	/**
	 * @return the siglaTipoLote
	 */
	public String getSiglaTipoLote() {
		return siglaTipoLote;
	}

	public Integer getSequecialNomeArquivo() {
		return sequecialNomeArquivo;
	}

	public void setSequecialNomeArquivo(Integer sequecialNomeArquivo) {
		this.sequecialNomeArquivo = sequecialNomeArquivo;
	}

	public String getDiretorioGravacaoArquivoPrint() {
		return diretorioGravacaoArquivoPrint;
	}

	public void setDiretorioGravacaoArquivoPrint(
			String diretorioGravacaoArquivoPrint) {
		this.diretorioGravacaoArquivoPrint = diretorioGravacaoArquivoPrint;
	}

	public String getNomeTemporarioDoArquivoPrint() {
		return nomeTemporarioDoArquivoPrint;
	}

	public void setNomeTemporarioDoArquivoPrint(
			String nomeTemporarioDoArquivoPrint) {
		this.nomeTemporarioDoArquivoPrint = nomeTemporarioDoArquivoPrint;
	}

	public Integer getQuantidadeFaturaPorArquivoPrint() {
		return quantidadeFaturaPorArquivoPrint;
	}

	public void setQuantidadeFaturaPorArquivoPrint(
			Integer quantidadeFaturaPorArquivoPrint) {
		this.quantidadeFaturaPorArquivoPrint = quantidadeFaturaPorArquivoPrint;
	}

	public Integer getQuantidadeMaximaLinhasPorFolha() {
		return quantidadeMaximaLinhasPorFolha;
	}

	public void setQuantidadeMaximaLinhasPorFolha(
			Integer quantidadeMaximaLinhasPorFolha) {
		this.quantidadeMaximaLinhasPorFolha = quantidadeMaximaLinhasPorFolha;
	}

	public String getExtensaoArquivoPrint() {
		return extensaoArquivoPrint;
	}

	public void setExtensaoArquivoPrint(String extensaoArquivoPrint) {
		this.extensaoArquivoPrint = extensaoArquivoPrint;
	}

	/**
	 * Classe que representar o arquivo gerado
	 * 
	 * @author Robin Michael Gray
	 * @version 1.1 - Criação - 04/10/2006
	 * 
	 */
	public class ArquivoGerado {
		// /**
		// * Numero de linhas gravadas para este arquivo
		// */
		// private Integer numeroLinhasGravadas;
		/**
		 * Qtd de faturas emitidas dentro este arquivo
		 */
		private Integer quantidadeFaturasEmitidas = new Integer(0);
		/**
		 * Valor total do faturas dentro este arquivo
		 */
		private Double valorTotalFatura = new Double(0);
		/**
		 * Prefixo do segregação para este arquivo
		 */
		private String prefixo;
		/**
		 * Status de controle de segregação
		 */
		private Boolean controleSegregacao;
		/**
		 * Operadora para este arquivo
		 */
		private SnCidadeOperadoraBean cidade;
		/**
		 * Instancia do GerarArquivo para este arquivo
		 */
		private GenerateFile gerarArquivo;
		/**
		 * Instancia do Arquivo para este arquivo
		 */
		private SnControleArquivoBean controleArquivo;
		
		private Integer idCriterio;

		/**
		 * Constructor
		 * 
		 * @param operadora
		 *            Operadora
		 * @param controleSegregacao
		 *            Controle do Segregacao
		 */
		public ArquivoGerado(SnCidadeOperadoraBean cidade,
				Boolean controleSegregacao) {
			super();
			this.cidade = cidade;
			this.controleSegregacao = controleSegregacao;
		}

		// /**
		// * Addicionar o summario do boleto
		// * @param idIntituicaoFinanceira
		// * @param idModalidadeCobracao
		// * @param dataVencimento
		// * @param totalPaginasBoleto
		// * @param valorFaturaNet
		// * @param valorFaturaEbt
		// */
		// public void addBoletoSumario(Integer idIntituicaoFinanceira,
		// Integer idModalidadeCobracao,
		// Date dataVencimento,
		// Integer totalPaginasBoleto,
		// Double valorFaturaNet,
		// Double valorFaturaEbt) {
		//			
		// String key = this.operadora.getId() + "_" + idIntituicaoFinanceira +
		// "_" + idModalidadeCobracao + "_" +
		// keyDateFormat.format(dataVencimento);
		// SumarioPrintHouseArquivo sumarioPrintHouseArquivo = null;
		// if (this.sumarioPrintHouseArquivos.containsKey(key)) {
		// sumarioPrintHouseArquivo = (SumarioPrintHouseArquivo)
		// this.sumarioPrintHouseArquivos.get(key);
		// sumarioPrintHouseArquivo.addBoleto(totalPaginasBoleto,
		// valorFaturaNet, valorFaturaEbt);
		// } else {
		// sumarioPrintHouseArquivo = new
		// SumarioPrintHouseArquivo(this.operadora.getId(),
		// idIntituicaoFinanceira,
		// idModalidadeCobracao,
		// dataVencimento,
		// totalPaginasBoleto,
		// valorFaturaNet,
		// valorFaturaEbt );
		//
		// }
		// this.sumarioPrintHouseArquivos.put(key, sumarioPrintHouseArquivo);
		//			
		//			
		// }
		//
		// public Collection getSummarioPrintHouses() {
		//			
		// Enumeration enumSummarioPrintHouses =
		// this.sumarioPrintHouseArquivos.elements();
		// while (enumSummarioPrintHouses.hasMoreElements()) {
		//				
		// SumarioPrintHouseDTO sumarioPrintHouseDTO = (SumarioPrintHouseDTO)
		// enumSummarioPrintHouses.nextElement();
		// sumarioPrintHouseDTO.setIdControleArquivo(this.controleArquivo.getNumero());
		// }
		//			
		// return Collections.list(this.sumarioPrintHouseArquivos.elements());
		//			
		// }
		//		
		// public void addLoteSummario(Integer idLote, Integer
		// quantidadePaginasCriado) {
		//			
		// if (quantidadePaginasCriado == null) {
		// quantidadePaginasCriado = new Integer(0);
		// }
		//			
		// if (summarioPaginasLotes.containsKey(idLote)) {
		// Integer qtdPaginasCriado = (Integer)
		// summarioPaginasLotes.get(idLote);
		// if (qtdPaginasCriado != null) {
		// quantidadePaginasCriado = new Integer(qtdPaginasCriado.intValue() +
		// quantidadePaginasCriado.intValue());
		// }
		// }
		// summarioPaginasLotes.put(idLote, quantidadePaginasCriado);
		// }

		// public Collection getRegistroEmissaoArquivos() {
		//			
		// Collection registroEmissaoArquivos = new ArrayList();
		//				
		// Enumeration enumSummarioPaginasLotesKeys =
		// summarioPaginasLotes.keys();
		// while (enumSummarioPaginasLotesKeys.hasMoreElements()) {
		//				
		// Integer idLote = (Integer)
		// enumSummarioPaginasLotesKeys.nextElement();
		// Integer qunatidadePaginas = (Integer)
		// summarioPaginasLotes.get(idLote);
		//
		// RegistroEmissaoArquivo registroEmissaoArquivo = new
		// RegistroEmissaoArquivo();
		//
		// RegistroEmissaoArquivoKey registroEmissaoArquivoKey = new
		// RegistroEmissaoArquivoKey();
		// registroEmissaoArquivo.setRegistroEmissaoArquivoKey(registroEmissaoArquivoKey);
		//				
		// Lote lote = new Lote();
		// lote.setNumeroLote(idLote);
		// registroEmissaoArquivo.setLote(lote);
		//
		// registroEmissaoArquivo.setRegistroEmissao(getRegistroEmissao());
		// registroEmissaoArquivo.setArquivo(getControleArquivo());
		// registroEmissaoArquivo.setNumeroPagina(qunatidadePaginas);
		//				
		// registroEmissaoArquivos.add(registroEmissaoArquivo);
		//				
		// }
		//			
		// return registroEmissaoArquivos;
		//			
		// }

		/**
		 * @return the controleSegregacao
		 */
		public Boolean getControleSegregacao() {
			return controleSegregacao;
		}

		/**
		 * @return the gerarArquivo
		 */
		public GenerateFile getGerarArquivo() {
			return gerarArquivo;
		}

		/**
		 * @param gerarArquivo
		 *            the gerarArquivo to set
		 */
		public void setGerarArquivo(GenerateFile gerarArquivo) {
			this.gerarArquivo = gerarArquivo;
		}

		/**
		 * @return the operadora
		 */
		public SnCidadeOperadoraBean getSnCidadeOperadoraBean() {
			return cidade;
		}

		/**
		 * @return the controleArquivo
		 */
		public SnControleArquivoBean getSnControleArquivoBean() {
			return controleArquivo;
		}

		/**
		 * @param controleArquivo
		 *            the controleArquivo to set
		 */
		public void setSnControleArquivoBean(
				SnControleArquivoBean controleArquivo) {
			this.controleArquivo = controleArquivo;
		}

		/**
		 * @return the prefixo
		 */
		public String getPrefixo() {
			return prefixo;
		}

		/**
		 * @param prefixo
		 *            the prefixo to set
		 */
		public void setPrefixo(String prefixo) {
			this.prefixo = prefixo;
		}

		/**
		 * @return the quantidadeFaturasEmitidas
		 */
		public Integer getQuantidadeFaturasEmitidas() {
			return quantidadeFaturasEmitidas;
		}

		/**
		 * @param quantidadeFaturasEmitidas
		 *            the quantidadeFaturasEmitidas to set
		 */
		public void setQuantidadeFaturasEmitidas(
				Integer quantidadeFaturasEmitidas) {
			this.quantidadeFaturasEmitidas = quantidadeFaturasEmitidas;
		}

		/**
		 * @return the valorTotalFatura
		 */
		public Double getValorTotalFatura() {
			return valorTotalFatura;
		}

		/**
		 * @param valorTotalFatura
		 *            the valorTotalFatura to set
		 */
		public void setValorTotalFatura(Double valorTotalFatura) {
			this.valorTotalFatura = valorTotalFatura;
		}

	}

	public Date getDtVencimento() {
		return dtVencimento;
	}

	public void setDtVencimento(Date dtVencimento) {
		this.dtVencimento = dtVencimento;
	}

	public String getNomeBase() {
		return nomeBase;
	}

	public void setNomeBase(String nomeBase) {
		this.nomeBase = nomeBase;
	}

	//	
	// public class SumarioPrintHouseArquivo extends SumarioPrintHouseDTO {
	//
	// /**
	// *
	// */
	// public SumarioPrintHouseArquivo(Integer idOperadora,
	// Integer idIntituicaoFinanceira,
	// Integer idModalidadeCobracao,
	// Date dataVencimento,
	// Integer totalPaginasBoleto,
	// JNetDecimal valorFaturaNet,
	// JNetDecimal valorFaturaEbt) {
	// super();
	// super.setIdOperadora(idOperadora);
	// super.setIdIntituicaoFinanceira(idIntituicaoFinanceira);
	// super.setIdModalidadeCobracao(idModalidadeCobracao);
	// super.setDataVencimento(dataVencimento);
	// super.setQuantidadeFatura(new Integer(1));
	// super.setQuantidadeTotalPagina(totalPaginasBoleto);
	// super.setValorTotalNet(valorFaturaNet);
	// super.setValorTotalParceira(valorFaturaEbt);
	//			
	// }
	//		
	// public void addBoleto(Integer totalPaginasBoleto,
	// JNetDecimal valorFaturaNet,
	// JNetDecimal valorFaturaEbt) {
	//			
	// super.setQuantidadeFatura( new
	// Integer(super.getQuantidadeFatura().intValue() + 1 ) );
	// super.setQuantidadeTotalPagina( new
	// Integer(super.getQuantidadeTotalPagina().intValue() +
	// totalPaginasBoleto.intValue()) );
	//			
	// double valorTotalNet = (getValorTotalNet() == null ? 0 :
	// getValorTotalNet().doubleValue() ) + valorFaturaNet.doubleValue();
	// // super.getValorTotalNet().add(new JNetDecimal(valorTotalNet));
	// super.setValorTotalNet(new JNetDecimal(valorTotalNet));
	//			
	// double valorTotalParceira = (getValorTotalParceira() == null ? 0 :
	// getValorTotalParceira().doubleValue() ) + valorFaturaEbt.doubleValue();
	// // super.getValorTotalParceira().add(new
	// JNetDecimal(valorTotalParceira));
	// super.setValorTotalParceira(new JNetDecimal(valorTotalParceira));
	//			
	// }
	//
	//		
	// }

}
