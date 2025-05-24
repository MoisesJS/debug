package br.com.netservicos.novosms.emissao.dto;

import java.util.Date;
import java.util.List;

import br.com.netservicos.core.bean.sn.SnCidadeOperadoraBean;
import br.com.netservicos.core.bean.sn.SnDiaVctoBean;
import br.com.netservicos.framework.core.bean.DomainBean;

/**
 * Classe que representa os dados do Detalhe do Ciclo de Emissão parametrizado.
 * @author Luis Andrade - HP
 * @since 20/04/2012
 */
public class DadosDetalheCicloEmissaoDTO extends DomainBean  {

    /** Constante que identifica o nome do atributo que representa a primary key. */
    public static final String PRIMARY_KEY = "pk_fake";

    /** Serial Version UID. */
    private static final long serialVersionUID = -2886094694549759314L;

    
    /** Representa o ID do ciclo. */
    private Long idCiclo;
    
    /** Representa o ID do ciclo no mês. Este campo é reiniciado no inicio de cada mês. */
    private Long idCicloMes;
    
    /** Indica se a cidade está parametrizada por ciclo ou por competência. */
    private boolean cidadePorCiclo;
    
    /** Indica se a CAT79 já foi gerada. */
    private boolean cat79Gerada;
    
    /** Representa a Cidade Operadora. */
    private SnCidadeOperadoraBean operadora;
    
    /** Representa a data em que a geração/emissão foi realizada. */
    private Date dataEmissao;
    
    /** Representa a data em que a CAT79 foi gerada. */
    private Date dataEmissaoCAT79;
    
    /** Lista dos vencimentos que foram gerados no mesmo ciclo. */
    private List<SnDiaVctoBean> vencimentos;
    
    /** Representa o número inicial da faixa de notas processadas no ciclo. */
    private Long numeroNFInicial;
    
    /** Representa o número final da faixa de notas processadas no ciclo. */
    private Long numeroNFFinal;
    
    /** Representa a extensão inicial do arquivo de notas fiscais. Ex.: 001. */
    private String extensaoArquivoInicio;
    
    /** Representa a extensão final do arquivo de notas fiscais. Ex.: 003. */
    private String extensaoArquivoFim;
    
    /** Representa a data de Geração da CAT79 */
    private Date dataGeracaoCAT79;
    
    /** Representa o usuário que efetuou a Geração da CAT79 */
    private String usuarioGeracaoCAT79;
    
    /** Representa o ID de agrupamento de Notas Fiscais. */
    private Long idGrupoNF;
    
    /**
     * Construtor padrão. 
     */
    public DadosDetalheCicloEmissaoDTO() {
        super(PRIMARY_KEY);
    }
    

    /**
     * Construtor utilizando os campos da classe.
     * @param primaryKeyName
     * @param idCiclo
     * @param idCicloMes
     * @param cidadePorCiclo
     * @param cAT79Gerada
     * @param operadora
     * @param dataEmissao
     * @param dataEmissaoCAT79
     * @param vencimentos
     * @param numeroNFInicial
     * @param numeroNFFinal
     * @param dataGeracaoCAT79
     * @param usuarioGeracaoCAT79
     * @param selecionado
     */
    public DadosDetalheCicloEmissaoDTO(String primaryKeyName, Long idCiclo, Long idCicloMes, boolean cidadePorCiclo,
                    boolean cAT79Gerada, SnCidadeOperadoraBean operadora, Date dataEmissao, Date dataEmissaoCAT79,
                    List<SnDiaVctoBean> vencimentos, Long numeroNFInicial, Long numeroNFFinal, Date dataGeracaoCAT79,
                    String usuarioGeracaoCAT79, Long idGrupoNF) {
        super(primaryKeyName);
        this.idCiclo = idCiclo;
        this.idCicloMes = idCicloMes;
        this.cidadePorCiclo = cidadePorCiclo;
        this.cat79Gerada = cAT79Gerada;
        this.operadora = operadora;
        this.dataEmissao = dataEmissao;
        this.dataEmissaoCAT79 = dataEmissaoCAT79;
        this.vencimentos = vencimentos;
        this.numeroNFInicial = numeroNFInicial;
        this.numeroNFFinal = numeroNFFinal;
        this.dataGeracaoCAT79 = dataGeracaoCAT79;
        this.usuarioGeracaoCAT79 = usuarioGeracaoCAT79;
        this.idGrupoNF = idGrupoNF;
    }


    public Long getIdCiclo() {
        return idCiclo;
    }


    public void setIdCiclo(Long idCiclo) {
        this.idCiclo = idCiclo;
    }

    
    public Long getIdCicloMes() {
        return idCicloMes;
    }

    
    public void setIdCicloMes(Long idCicloMes) {
        this.idCicloMes = idCicloMes;
    }

    
    public boolean isCidadePorCiclo() {
        return cidadePorCiclo;
    }


    public void setCidadePorCiclo(boolean cidadePorCiclo) {
        this.cidadePorCiclo = cidadePorCiclo;
    }


    public boolean isCAT79Gerada() {
        return cat79Gerada;
    }

    
    public void setCAT79Gerada(boolean cat79Gerada) {
        this.cat79Gerada = cat79Gerada;
    }

    
    public SnCidadeOperadoraBean getOperadora() {
        return operadora;
    }

    
    public void setOperadora(SnCidadeOperadoraBean operadora) {
        this.operadora = operadora;
    }

    
    public Date getDataEmissao() {
        return dataEmissao;
    }

    
    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    
    public Date getDataEmissaoCAT79() {
        return dataEmissaoCAT79;
    }

   
    public void setDataEmissaoCAT79(Date dataEmissaoCAT79) {
        this.dataEmissaoCAT79 = dataEmissaoCAT79;
    }

    
    public List<SnDiaVctoBean> getVencimentos() {
        return vencimentos;
    }

    
    public void setVencimentos(List<SnDiaVctoBean> vencimentos) {
        this.vencimentos = vencimentos;
    }

    
    public Long getNumeroNFInicial() {
        return numeroNFInicial;
    }

    
    public void setNumeroNFInicial(Long numeroNFInicial) {
        this.numeroNFInicial = numeroNFInicial;
    }

    
    public Long getNumeroNFFinal() {
        return numeroNFFinal;
    }

    
    public void setNumeroNFFinal(Long numeroNFFinal) {
        this.numeroNFFinal = numeroNFFinal;
    }


    public String getExtensaoArquivoInicio() {
        return extensaoArquivoInicio;
    }


    public void setExtensaoArquivoInicio(String extensaoArquivoInicio) {
        this.extensaoArquivoInicio = extensaoArquivoInicio;
    }


    public String getExtensaoArquivoFim() {
        return extensaoArquivoFim;
    }


    public void setExtensaoArquivoFim(String extensaoArquivoFim) {
        this.extensaoArquivoFim = extensaoArquivoFim;
    }


    public Date getDataGeracaoCAT79() {
        return dataGeracaoCAT79;
    }


    public void setDataGeracaoCAT79(Date dataGeracaoCAT79) {
        this.dataGeracaoCAT79 = dataGeracaoCAT79;
    }


    public String getUsuarioGeracaoCAT79() {
        return usuarioGeracaoCAT79;
    }


    public void setUsuarioGeracaoCAT79(String usuarioGeracaoCAT79) {
        this.usuarioGeracaoCAT79 = usuarioGeracaoCAT79;
    }

    
    public Long getIdGrupoNF() {
        return idGrupoNF;
    }


    public void setIdGrupoNF(Long idGrupoNF) {
        this.idGrupoNF = idGrupoNF;
    }


    /**
     * Método responsável por retornar os vencimentos do detalhe do ciclo de emissão.
     * @return String contendo os vencimentos.
     */
    public String getVencimentosToString() {
        StringBuilder sb = new StringBuilder();
        String separador = "";
        for (SnDiaVctoBean vencto : this.vencimentos) {
            sb.append(separador).append(vencto.getDia());
            separador = ", ";
        }
        return sb.toString();
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idCiclo == null) ? 0 : idCiclo.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        DadosDetalheCicloEmissaoDTO other = (DadosDetalheCicloEmissaoDTO) obj;
        if (idCiclo == null) {
            if (other.idCiclo != null) {
                return false;
            }
        } else if (!idCiclo.equals(other.idCiclo)) {
            return false;
        }
        return true;
    }


}
