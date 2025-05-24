-- ------------------------------------------------------------
-- Script de configuração de FILAS de integração ATLAS.
-- 
-- Author: Diego C. Amaral - 05/12/2018
-- ------------------------------------------------------------
declare

	
	cNmGrupoTarefa varchar2(30) := 'NMC_CRITICA_PGTO';
	cDsGrupoTarefa varchar2(50) := 'Grupo de processos de Criticas de Pagamento';

  
  vClassID number;
  vCount   number;
  idTarefa NUMBER;

  -- ------------------------------------
  -- Fix Sequence
  -- ------------------------------------
  procedure pr_fix_sequence(pSequenceName varchar2, pTableName varchar2, pIdName varchar2) is
    vId  number;
    vSeq number;
  begin
    execute immediate 'select max('||pIdName||') from '||pTableName into vId;
    execute immediate 'select '||pSequenceName||'.nextval from dual' into vSeq;
    while vSeq < vId loop
       execute immediate 'select '||pSequenceName||'.nextval from dual' into vSeq;
    end loop;
  end;

  -- ------------------------------------
  -- Creates GRUPO_TAREFA
  -- Author: Diego Amaral 21/08/17
  -- ------------------------------------	
	function fn_get_grupo_tarefa return number is
		vGrupoTarefa number;
	begin
			begin
				select id_grupo_tarefa
					into vGrupoTarefa
					from filas.tbsms_grupo_tarefa
				 where nm_grupo_tarefa = cNmGrupoTarefa;
			exception
				when no_data_found then
					select filas.SQSMS_GRUPO_TAREFA_01.nextval
						into vGrupoTarefa
						from dual;
					insert into filas.tbsms_grupo_tarefa
						(id_grupo_tarefa, nm_grupo_tarefa, ds_grupo_tarefa)
					values
						(vGrupoTarefa,
						 cNmGrupoTarefa,
						 cDsGrupoTarefa);
			end;
				
			return vGrupoTarefa;
	end;
	
  -- ------------------------------------
  -- Creates CLASSE
  -- Author: Diego Amaral 21/08/17
  -- ------------------------------------
  function fn_create_classe(pNmClasse  varchar2,
                            pDsClasse  varchar2,
                            pAplicacao varchar2) return number is
    vIdClasse number;
  begin
    begin
      select id_classe
        into vIdClasse
        from filas.tbsms_classe
       where nm_classe = pNmClasse;
	   

	   
    exception
      when no_data_found then
        select filas.SQSMS_CLASSE_01.nextval into vIdClasse from dual;
        --dbms_output.put_line('Classe: ['||pNmClasse||':'||vIdClasse||'], Descrição:['||pDsClasse||'], Aplicacao: ['||pAplicacao||']');
        dbms_output.put_line('Creating class '||vClassID||':'||pNmClasse);
        insert into filas.tbsms_classe
          (id_classe, nm_classe, ds_classe, sg_aplicacao)
        values
          (vIdClasse, pNmClasse, pNmClasse, pAplicacao);
		  
		insert into filas.TBSMS_HORARIO_EXECUCAO
		  (ID_HORARIO_EXECUCAO, ID_CLASSE, DH_INICIO_FAIXA, DH_FIM_FAIXA)
		values
		  (filas.SQSMS_HORARIO_EXECUCAO_01.nextval,
		   vIdClasse,
		   to_date('01.01.2017 00:00:01', 'dd.mm.rrrr hh24:mi:ss'),
		   to_date('30.12.2049 23:59:59', 'dd.mm.rrrr hh24:mi:ss'));
    end;
	

  
    return vIdClasse;
  end;

  -- ------------------------------------
  -- Creates TAREFA
  -- Author: Diego Amaral 21/08/17
  -- ------------------------------------
  procedure pr_create_tarefa(pIdClasse     number,
                             nmTarefa      varchar2,
                             dsTarefa      varchar2,
                             nmClasseJava  varchar2,
                             pIsAutoSys varchar2 default 'N') is
    vGrupoTarefa   number;
    vTarefaID      number;
  begin
		-- Gets grupo tarefa
		vGrupoTarefa := fn_get_grupo_tarefa;

  
    select filas.SQSMS_TAREFA_01.nextval into vTarefaID from dual;
  
		insert into filas.tbsms_tarefa (
			ID_TAREFA,
			ID_GRUPO_TAREFA,
			NM_TAREFA,
			DS_TAREFA,
			NM_CLASSE_JAVA,
			ID_CLASSE,
			NR_PRIORIDADE_PADRAO,
			FN_STATUS_TAREFA,
			FC_AUTOSYS,
			FN_PRDD_REPROCESSAR,
			FN_REPROCESSAR_COM_NOVA)
    values
      (vTarefaID, --ID_TAREFA,
       vGrupoTarefa, --ID_GRUPO_TAREFA,
       nmTarefa, --NM_TAREFA,
       dsTarefa, --DS_TAREFA,
       nmClasseJava, --NM_CLASSE_JAVA,
       pIdClasse, --ID_CLASSE,
       1, --NR_PRIORIDADE_PADRAO,
       1, --FN_STATUS_TAREFA,
       pIsAutoSys, --FC_AUTOSYS,
       1, --FN_PRDD_REPROCESSAR,
       1); --FN_REPROCESSAR_COM_NOVA
	   

  
  end;
  
  -- ------------------------------------
  -- Bind SERVER x CLASSE
  -- Author: Diego Amaral 21/08/17
  -- ------------------------------------
  procedure pr_servidor_atendimento(pNmServer   varchar2,
                                    pClassID    number,
                                    pQtProcesso number) is
  
  begin
  
    insert into filas.tbsms_atendimento
      (id_atende, id_classe, id_servidor, qt_processos)
    values
      (filas.SQSMS_ATENDIMENTO_01.nextval,
       pClassID,
       (select id_servidor
          from filas.tbsms_servidor
         where nm_servidor = pNmServer),
       pQtProcesso);
  exception
    when others then
      dbms_output.put_line('Erro ao associar a classe ' || pClassID ||
                           ' ao servidor ' || pNmServer || ' - ' ||
                           sqlerrm);
    
  end;  

begin
	-- fix sequences
  pr_fix_sequence('filas.SQSMS_ATENDIMENTO_01', 'filas.tbsms_atendimento', 'id_atende');
  pr_fix_sequence('filas.SQSMS_TAREFA_01', 'filas.tbsms_tarefa', 'id_tarefa');
  pr_fix_sequence('filas.SQSMS_HORARIO_EXECUCAO_01', 'filas.tbsms_horario_execucao', 'id_horario_execucao');
  pr_fix_sequence('filas.SQSMS_CLASSE_01', 'filas.tbsms_classe', 'id_classe');

	-- Create CLASSE
  vClassID := fn_create_classe('NMC_CRITICA_PGTO',
                               'Processos de Critica de Pagamento Nao Baixado',
                               'MODCOB');

	-- Create TAREFA
  pr_create_tarefa(vClassID,
                   'NMC_CRITICA_PGTO_GEN_ARQUIVO',
                   'Geracao de arquivos de Critica de Pagamento Nao Baixados',
                   'br.com.netservicos.novosms.task.integracaoparceiros.GerarArquivoCriticasPagamentosNaoBaixadosTask',
                   'S');
									 
  pr_create_tarefa(vClassID,
                   'NMC_CRITICA_PGTO_TRANSF_ARQUIVO',
                   'Transferencia de arquivos de Critica de Pagamento Nao Baixados',
                   'br.com.netservicos.novosms.task.integracaoparceiros.TransfereArquivoCriticasPagamentosTask',
                   'N');

  -- bind SERVIDOR_ATENDIMENTO x CLASSE
	pr_servidor_atendimento('novosmsbatch01', vClassID, 5);
	pr_servidor_atendimento('novosmsbatch02', vClassID, 5);
	pr_servidor_atendimento('novosmsbatch03', vClassID, 5);
	pr_servidor_atendimento('novosmsbatch04', vClassID, 5);
	pr_servidor_atendimento('novosmsbatch05', vClassID, 5);
	pr_servidor_atendimento('novosmsbatch06', vClassID, 5);
	pr_servidor_atendimento('novosmsbatch10', vClassID, 5);
	pr_servidor_atendimento('novosmsbatch11', vClassID, 5);
	pr_servidor_atendimento('novosmsbatch12', vClassID, 5);
	pr_servidor_atendimento('novosmsbatch13', vClassID, 5);
	pr_servidor_atendimento('novosmsbatch14', vClassID, 5);
	pr_servidor_atendimento('novosmsbatch15', vClassID, 5);
	pr_servidor_atendimento('novosmsbatch16', vClassID, 5);


end;
/