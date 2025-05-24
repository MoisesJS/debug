package br.com.netservicos.novosms.emissao.callBack;

import br.com.netservicos.framework.core.bean.DynamicBean;
import br.com.netservicos.novosms.emissao.dto.SetorDTO;

public class GenericoSetorCallback extends SetorCallback {

	/** Valor do tipo da linha que sera criada para todos os registros. */
	private Integer tipoLinha;

    /**
     * Construtor que recebe o setor aonde deve ser colocado as linhas geradas
     * e o tipo da linha que sera criada.
     * 
     * @version 1.0 - 31/03/2006 - Criacao
     * @author Everton Ken Tomita
     * @number RF015-021
     * 
     * @param Setor que ira armazenar as linhas
     * @param Integer do tipo da linha que sera criada para todos os registros encontrados na consulta
     *
     * @semantics
     *
     * Chamar construtor da superclasse passando o setor
     *
     * Setar a variavel (tipoLinha) com o valor que esta sendo passado como parametro
     * 
     */
	public GenericoSetorCallback(SetorDTO setor, Integer tipoLinha) {
		super(setor);
		this.tipoLinha = tipoLinha;
    }

    /**
     * Recebe o registro atual e adiciona no setor.
     * <br>
     * O tipo da linha a ser colocada no setor eh a que esta sendo passada
     * no construtor.
     *
     * @version 1.0 - 31/03/2006 - Criacao
     * @author Everton Ken Tomita
     * @number RF015-021
     * 
     * @param valor Map que contem os valores dos campos que foi feito a consulta.
     *        <br>Onde:
     *        <br>key - campo do comando SQL executado
     *        <br>value - valor do campo do comando SQL
     * @param rowNum int com o valor da linha atual vindo do callback
     * 
     * @semantics
     *
     * Adicionar o map no setor com o tipo da linha que esta na variavel (tipoLinha)
     */
    public void execute(DynamicBean valor, int rowNum) {
    	this.addLinha(this.tipoLinha.intValue(), valor);
    }
}

