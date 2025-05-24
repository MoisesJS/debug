-- =========================================================================================
-- Projeto: COMBO_MULTI
-- Tag: FILP_58903_NI_001
-- Responsável: COMBO_MULTI
-- Função : Remover a configuração da Task para a fila de Processamento dos Arquivos
-- SEQ : 
-- Executar como : FILAS
-- =========================================================================================
declare
   vid_classe filas.tbsms_classe.id_classe%type;
begin
   begin
      select id_classe
      into   vid_classe
      from   filas.tbsms_classe
      where  nm_classe = 'PARCEIRO - CLARO BAX';
   exception when no_data_found then
      vid_classe := null;
   end;
   
   if vid_classe is not null then
      delete filas.tbsms_horario_execucao
      where  id_classe = vid_classe; 
      
      delete filas.tbsms_atendimento 
      where  id_classe = vid_classe;  

      delete filas.tbsms_tarefa
      where  id_classe = vid_classe
      and    nm_tarefa = 'BAIXAR_ARQUIVO_CLARO'
      and    ds_tarefa = 'BaixarArquivoClaroTask'
      and    nm_classe_java = 'br.com.netservicos.novosms.task.integracaoparceiros.BaixarArquivoClaroTask';      
      
      delete filas.tbsms_tarefa
      where  id_classe = vid_classe
      and    nm_tarefa = 'PROCESSAR_ARQUIVO_CLARO'
      and    ds_tarefa = 'ProcessarArquivoClaroTask'
      and    nm_classe_java = 'br.com.netservicos.novosms.task.integracaoparceiros.ProcessarArquivoClaroTask';

      delete filas.tbsms_tarefa
      where  id_grupo_tarefa IN
             (SELECT G.Id_Grupo_Tarefa
                FROM Filas.Tbsms_Grupo_Tarefa g
               WHERE G.Nm_Grupo_Tarefa = 'CLARO - CARGA')
      and    id_classe = vid_classe
      and    nm_tarefa = 'CARGA_EMISSAO_COMMIT_EMLOTE_CLARO'
      and    ds_tarefa = 'CargaEmissaoCommitEmLoteTask'
      and    nm_classe_java = 'br.com.netservicos.novosms.task.integracaoparceiros.CargaEmissaoCommitEmLoteTask';


      DELETE FROM Filas.Tbsms_Tarefa t
       WHERE T.Id_Grupo_Tarefa IN
             (SELECT G.Id_Grupo_Tarefa
                FROM Filas.Tbsms_Grupo_Tarefa g
               WHERE G.Nm_Grupo_Tarefa = 'CLARO - CARGA');


      delete filas.tbsms_tarefa
      where  id_grupo_tarefa IN
             (SELECT G.Id_Grupo_Tarefa
                FROM Filas.Tbsms_Grupo_Tarefa g
               WHERE G.Nm_Grupo_Tarefa = 'CLARO - CARGA')
      and    id_classe = vid_classe
      and    nm_tarefa = 'RETORNO_CARGA_CLARO'
      and    ds_tarefa = 'GeraArquivoRetornoCargaFaturasParceiroTask'
      and    nm_classe_java = 'br.com.netservicos.novosms.task.integracaoparceiros.GeraArquivoRetornoCargaFaturasParceiroTask';

   
      delete filas.tbsms_grupo_tarefa 
      where  nm_grupo_tarefa = 'CLARO - CARGA';
      
      delete filas.tbsms_classe
      where  id_classe = vid_classe;
   end if;  
   commit;
end;
/
