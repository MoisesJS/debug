-- =========================================================================================
-- Projeto: DemandasAnatel
-- Tag: NSMSP_73218_NI_004
-- Responsável: Jeferson Solovenco
-- Função : Remover a configuração da Task para a fila de Processamento dos Lotes de historico de atendimento
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
      where  nm_classe = 'PROCESSA_LT_HSTATEND';
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
      and    nm_tarefa = 'PROCESSA_LT_HSTATEND'
      and    ds_tarefa = 'Tarefa para processamento dos lotes de Historico de Atendimento'
      and    nm_classe_java = 'br.com.netservicos.novosms.task.protocolo.ProcessarLoteHistoricoAtendimentoTask';      

	  delete filas.TBSMS_GRUPO_TAREFA 
      where  NM_GRUPO_TAREFA = 'LOTE_HSTATEND';  
	  
      delete filas.tbsms_classe
      where  id_classe = vid_classe;
   end if;  
   commit;
end;
/
