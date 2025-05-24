package br.com.netservicos.novosms.emissao.callBack;

import java.util.Map;

import br.com.netservicos.framework.core.bean.Bean;
import br.com.netservicos.framework.core.bean.DynamicBean;
import br.com.netservicos.framework.core.dao.DAOCallback;
import br.com.netservicos.framework.core.dao.DAOCallbackDecorator;
import br.com.netservicos.novosms.emissao.dto.LinhaDTO;
import br.com.netservicos.novosms.emissao.dto.SetorDTO;

public abstract class SetorCallback implements DAOCallbackDecorator<DynamicBean>{

//  private ParametroDTO parametro;
  private SetorDTO setor;

  public SetorCallback(SetorDTO setor) {
  	this.setor = setor;
  }
 
  /*
  public String getComando() {
      throw new IllegalAccessError("Metodo nao implementado para classe.");
  }
	*/


  /**
   * Adiciona o map no setor.
   * <br>
   * Cria uma nova linha do tipo que eh passado como parametro e adiciona
   * a linha no setor.
   * 
   * @version 1.0 - 14/03/2006 - Criacao
   * @author Everton Ken Tomita
   * @number RF015-021
   *
   * @param tipo int que representa o tipo de linha a ser gerada (ver xls que possue todos os tipos de linha)
   * @param valor Map que contem os valores dos campos para que seja montanda a linha de acordo com o tipo especificado
   * 
   * @semantics
   *
   * Cria um novo objeto do tipo Linha, com os valores que sao passados
   *   como parametro.
   *
   * Adiciona a linha criada no setor.
   * 
   */
  protected void addLinha(int tipo, Map valor) {
  	LinhaDTO linha = new LinhaDTO(new Integer(tipo), valor);
  	this.setor.addLinha(linha);
  }

  /**
   * Trata o objeto gerado pelo callback.
   * <br>
   * Faz o cast de um object para um map que eh gerado pelo mapper generico
   * e executa o metodo implementado pelas classes que implementam essa classe.
   * <br>
   * Metodo implementado da interface de DAOCallback.
   * 
   * @version 1.0 - 14/03/2006 - Criacao
   * @author Everton Ken Tomita
   * @number RF015-021
   *
   * @semantics
   *
   * Faz o cast do (obj) para um tipo Map (map)
   *
   * Executa this.execute(map, rowNum)
   */
  public void execute(Object obj, int rowNum) {
      this.execute((DynamicBean)obj, rowNum);
  }

  /**
   * Antes da chamada do callback, esse metodo sera executado antes para
   * que seja feita alguma execucao especifica.
   * <br>
   * As subclasses deverao sobrescrever o metodo caso seja necessario.
   * 
   * @version 1.0 - 15/03/2006 - Criacao
   * @author Everton Ken Tomita
   * @number RF015-021
   */
  public void initializeDAOCallback() {
  }

  /**
   * Ao final a execucao do callback, esse metodo sera executado para que
   * seja feita alguma coisa especifica.  Ex: colocar uma linha do
   * tipo rodape (trailler).
   * <br>
   * As subclasses deverao sobrescrever o metodo caso seja necessario.
   * 
   * @version 1.0 - 15/03/2006 - Criacao
   * @author Everton Ken Tomita
   * @number RF015-021
   */
  public void finalizeDAOCallback() {
  }

  /**
   * Deve ser implementando para que seja adicionado o map de acordo com
   * a linha especificada pelas subclasses.
   * 
   * @version 1.0 - 14/03/2006 - Criacao
   * @author Everton Ken Tomita
   * @number RF015-021
   * 
   * @param valor Map que contem os valores dos campos que foi feito a consulta.
   *        <br>Onde:
   *        <br>key - campo do comando SQL executado
   *        <br>value - valor do campo do comando SQL
   * @param rowNum int com o valor da linha atual vindo do callback
   */
	public abstract void execute(DynamicBean valor, int rowNum);

	
	public void onRowSucess(DynamicBean valor, int rowNum){
		
	}

  /** @link dependency */
  /*# GerarArquivo lnkGerarArquivo; */

  /** @link dependency */
  /*# LinhaDTO lnkLinha; */
}
