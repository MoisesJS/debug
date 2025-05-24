-- =========================================================================================
-- Projeto: COMBO_MULTI
-- Tag: FILP_58903_NI_001
-- Responsável: COMBO_MULTI
-- Função : Configuração da Task para a fila de Processamento dos Arquivos
-- SEQ : 
-- Executar como : FILAS
-- =========================================================================================

declare
   vid_classe filas.tbsms_classe.id_classe%type;
   vid_grupo_tarefa filas.tbsms_grupo_tarefa.id_grupo_tarefa%type;
   vid_servidor filas.tbsms_servidor.id_servidor%type;
   vid_atende filas.tbsms_atendimento.id_atende%type;
   vid_horario_execucao filas.tbsms_horario_execucao.id_horario_execucao%type;
   vid_tarefa filas.tbsms_tarefa.id_tarefa%type;   
   vid_tarefa_processar filas.tbsms_tarefa.id_tarefa%type;
   vid_tarefa_baixar filas.tbsms_tarefa.id_tarefa%type;   
begin
   begin
      select id_classe
      into   vid_classe
      from   filas.tbsms_classe
      where  nm_classe = 'PARCEIRO - CLARO BAX';
   exception when no_data_found then
      insert into filas.tbsms_classe 
      values (filas.sqsms_classe_01.nextval, 'PARCEIRO - CLARO BAX', 'PROCESSOS DO PARCEIRO CLARO - BAIXAR ARQUIVO', 'MODCOB') 
      returning id_classe into vid_classe;
   end;
   
   begin
      select id_grupo_tarefa
      into   vid_grupo_tarefa
      from   filas.tbsms_grupo_tarefa 
      where  nm_grupo_tarefa = 'CLARO - CARGA';
   exception when no_data_found then
      insert into filas.tbsms_grupo_tarefa 
      values (filas.sqsms_grupo_tarefa_01.nextval, 'CLARO - CARGA', 'CARGA DE ARQUIVOS - CLARO')
      returning id_grupo_tarefa into vid_grupo_tarefa;
   end;   

   begin
      select id_tarefa
      into   vid_tarefa_baixar
      from   filas.tbsms_tarefa
      where  id_grupo_tarefa = vid_grupo_tarefa
      and    id_classe = vid_classe
      and    nm_tarefa = 'BAIXAR_ARQUIVO_CLARO'
      and    ds_tarefa = 'BaixarArquivoClaroTask'
      and    nm_classe_java = 'br.com.netservicos.novosms.task.integracaoparceiros.BaixarArquivoClaroTask';
   exception when no_data_found then
      insert into filas.tbsms_tarefa 
      values (filas.sqsms_tarefa_01.nextval
            , vid_grupo_tarefa
            , 'BAIXAR_ARQUIVO_CLARO'
            , 'BaixarArquivoClaroTask'
            , 'br.com.netservicos.novosms.task.integracaoparceiros.BaixarArquivoClaroTask'
            ,  vid_classe
            ,  1
            ,  1
            ,  'S'
            ,  0
            ,  3
            ,  5
            ,  0
            ,  NULL);
   end; 

   begin
      select id_tarefa
      into   vid_tarefa_processar
      from   filas.tbsms_tarefa
      where  id_grupo_tarefa = vid_grupo_tarefa
      and    id_classe = vid_classe
      and    nm_tarefa = 'PROCESSAR_ARQUIVO_CLARO'
      and    ds_tarefa = 'ProcessarArquivoClaroTask'
      and    nm_classe_java = 'br.com.netservicos.novosms.task.integracaoparceiros.ProcessarArquivoClaroTask';
   exception when no_data_found then
      insert into filas.tbsms_tarefa 
      values (filas.sqsms_tarefa_01.nextval
            , vid_grupo_tarefa
            , 'PROCESSAR_ARQUIVO_CLARO'
            , 'ProcessarArquivoClaroTask'
            , 'br.com.netservicos.novosms.task.integracaoparceiros.ProcessarArquivoClaroTask'
            ,  vid_classe
            ,  1
            ,  1
            ,  'S'
            ,  NULL
            ,  NULL
            ,  NULL
            ,  NULL
            ,  NULL);
   end; 
   
   begin
      select id_tarefa
      into   vid_tarefa
      from   filas.tbsms_tarefa
      where  id_grupo_tarefa = vid_grupo_tarefa
      and    id_classe = vid_classe
      and    nm_tarefa = 'CARGA_EMISSAO_COMMIT_EMLOTE_CLARO'
      and    ds_tarefa = 'CargaEmissaoCommitEmLoteTask'
      and    nm_classe_java = 'br.com.netservicos.novosms.task.integracaoparceiros.CargaEmissaoCommitEmLoteTask';
   exception when no_data_found then
      insert into filas.tbsms_tarefa 
      values (filas.sqsms_tarefa_01.nextval
            , vid_grupo_tarefa
            , 'CARGA_EMISSAO_COMMIT_EMLOTE_CLARO'
            , 'CargaEmissaoCommitEmLoteTask'
            , 'br.com.netservicos.novosms.task.integracaoparceiros.CargaEmissaoCommitEmLoteTask'
            ,  vid_classe
            ,  1
            ,  1
            ,  'S'
            ,  NULL
            ,  NULL
            ,  NULL
            ,  NULL
            ,  NULL);
   end;  

   begin
      select id_tarefa
      into   vid_tarefa
      from   filas.tbsms_tarefa
      where  id_grupo_tarefa = vid_grupo_tarefa
      and    id_classe = vid_classe
      and    nm_tarefa = 'RETORNO_CARGA_CLARO'
      and    ds_tarefa = 'GeraArquivoRetornoCargaFaturasParceiroTask'
      and    nm_classe_java = 'br.com.netservicos.novosms.task.integracaoparceiros.GeraArquivoRetornoCargaFaturasParceiroTask';
   exception when no_data_found then
      insert into filas.tbsms_tarefa 
      values (filas.sqsms_tarefa_01.nextval
            , vid_grupo_tarefa
            , 'RETORNO_CARGA_CLARO'
            , 'GeraArquivoRetornoCargaFaturasParceiroTask'
            , 'br.com.netservicos.novosms.task.integracaoparceiros.GeraArquivoRetornoCargaFaturasParceiroTask'
            ,  vid_classe
            ,  1
            ,  1
            ,  'S'
            ,  NULL
            ,  NULL
            ,  NULL
            ,  NULL
            ,  NULL);
   end; 

    for servidor in (select distinct a.id_servidor
                     from   filas.tbsms_atendimento a
                        ,   filas.tbsms_servidor s
                     where  s.fn_status_servidor = 1
                     and    s.id_servidor = a.id_servidor
                     and    a.id_classe in (13,4)
                     order by 1)
    loop                     
       begin
          select id_atende
          into   vid_atende
          from   filas.tbsms_atendimento 
          where  id_classe = vid_classe
          and    id_servidor = servidor.id_servidor;
       exception when no_data_found then
          insert into filas.tbsms_atendimento 
          values (filas.sqsms_atendimento_01.nextval
               ,  vid_classe
               ,  servidor.id_servidor
               ,  5);
       end;        
    end loop;   
       
   begin
      select max(id_horario_execucao)
      into   vid_horario_execucao
      from   filas.tbsms_horario_execucao
      where  id_classe = vid_classe
      and    dh_inicio_faixa = TO_DATE('01/01/2000 00:00:01','DD/MM/RRRR HH24:MI:SS')
      and    dh_fim_faixa = TO_DATE('30/12/2049 23:59:58','DD/MM/RRRR HH24:MI:SS'); 
      
      if vid_horario_execucao is null then
        insert into filas.tbsms_horario_execucao 
        values (filas.sqsms_horario_execucao_01.nextval
             ,  vid_classe
             ,  TO_DATE('01/01/2000 00:00:01','DD/MM/RRRR HH24:MI:SS')
             ,  TO_DATE('30/12/2049 23:59:58','DD/MM/RRRR HH24:MI:SS'));
      end if;       
   exception when no_data_found then
      insert into filas.tbsms_horario_execucao 
      values (filas.sqsms_horario_execucao_01.nextval
           ,  vid_classe
           ,  TO_DATE('01/01/2000 00:00:01','DD/MM/RRRR HH24:MI:SS')
           ,  TO_DATE('30/12/2049 23:59:58','DD/MM/RRRR HH24:MI:SS'));
   end;           
   commit;
end;
/