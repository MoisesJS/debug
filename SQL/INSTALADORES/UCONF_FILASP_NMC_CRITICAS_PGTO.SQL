-- ------------------------------------------------------------
-- Script de remoção de configuração de FILAS de integração ATLAS.
-- 
-- Author: Diego C. Amaral - 05/12/2018
-- ------------------------------------------------------------
declare
 vClasseRow filas.tbsms_classe%rowtype;
begin
 
 select * into vClasseRow from filas.tbsms_classe where sg_aplicacao = 'MODCOB' and nm_classe = 'NMC_CRITICA_PGTO';
 
 delete filas.tbsms_controle_autosys where id_fila in (Select id_fila from filas.tbsms_fila where id_Classe = vClasseRow.id_classe);

 delete filas.tbsms_parametro_fila where id_fila in (Select id_fila from filas.tbsms_fila where id_Classe = vClasseRow.id_classe);
 delete filas.tbsms_fila where id_Classe = vClasseRow.id_classe;
 
 
 DELETE filas.TBSMS_HORARIO_EXECUCAO WHERE ID_CLASSE = vClasseRow.id_classe;
 dbms_output.put_line('horario_execucao deleted '||SQL%ROWCOUNT);

 delete from filas.tbsms_atendimento where id_classe = vClasseRow.id_classe;
 dbms_output.put_line('tbsms_atendimento deleted '||SQL%ROWCOUNT);
  
 delete from filas.tbsms_parametro_fila where id_fila in (select id_fila from filas.tbsms_fila where id_classe = vClasseRow.id_classe);
 dbms_output.put_line('tbsms_parametro_fila deleted '||SQL%ROWCOUNT);
 delete from filas.tbsms_fila where id_classe = vClasseRow.id_classe;
 dbms_output.put_line('tbsms_fila deleted '||SQL%ROWCOUNT);
 
 delete from filas.tbsms_parametro_agenda where id_agenda in (select id_agenda from filas.tbsms_agenda where id_classe = vClasseRow.id_classe);
 dbms_output.put_line('tbsms_parametro_agenda deleted '||SQL%ROWCOUNT);
 delete from filas.tbsms_agenda where id_classe = vClasseRow.id_classe;
 dbms_output.put_line('tbsms_agenda deleted '||SQL%ROWCOUNT);
 

 delete from filas.tbsms_tarefa where id_classe = vClasseRow.id_classe;
 dbms_output.put_line('tbsms_tarefa deleted '||SQL%ROWCOUNT);
 delete from filas.tbsms_grupo_tarefa where id_grupo_tarefa in (
        select id_grupo_tarefa from filas.tbsms_tarefa where id_classe = vClasseRow.id_classe);
 dbms_output.put_line('tbsms_grupo_tarefa deleted '||SQL%ROWCOUNT);

 delete from filas.tbsms_classe where id_classe = vClasseRow.id_classe;
 dbms_output.put_line('tbsms_classe deleted '||SQL%ROWCOUNT);
 
 delete filas.tbsms_grupo_tarefa where nm_grupo_tarefa = 'NMC_CRITICA_PGTO';
 dbms_output.put_line('tbsms_grupo_tarefa deleted '||SQL%ROWCOUNT);
 
end;
/