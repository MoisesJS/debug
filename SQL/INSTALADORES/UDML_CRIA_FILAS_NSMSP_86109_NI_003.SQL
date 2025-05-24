-- =========================================================================================
-- Projeto: NowOnline
-- Tag: NSMSP_84755_NI_001
-- Responsável: Cristiane Sueli
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
      where  nm_classe = 'PROCESSA_CHARGEBACK';
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
      and    nm_tarefa = 'PROCESSA_CHARGEBACK'
      and    ds_tarefa = 'Tarefa para processamento dos lotes de arquivos chargeback.'
      and    nm_classe_java = 'br.com.netservicos.novosms.task.chargeback.ProcessarChargebackTask';      

	delete FILAS.TBSMS_GRUPO_TAREFA
	where NM_GRUPO_TAREFA = 'LOTE_CHARGEBACK';
	  
      delete filas.tbsms_classe
      where  id_classe = vid_classe;
   end if;  
   commit;
end;
/
