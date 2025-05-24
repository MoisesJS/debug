--==============================================================================
-- Projeto       : Migra��o do VSMS - Gera��o e Emiss�o de Cobran�as Multi Empresa
-- Tag           : FILP_137174_NI_001_F5A
-- Respons�vel   : Marcus Pestana
-- Seq��ncia     : 001
-- Fun��o        : Desconfigura��o de uma nova Fila para geracao de lotes Print 
--                 House Boletos Avulsos
-- Cria��o       : 18/10/2017
-- =============================================================================
-- Historico de Alteracoes
-- =============================================================================
-- Data          : 
-- Solicitante   : 
-- Alteracao     : 
--==============================================================================
PROMPT *********************************************************************************************;
PROMPT *   DESCONFIGURANDO NOVA TAREFA PARA PROCESSAMENTO DE LOTES DE BOLETOS AVULSOS BASE FILASP  *;
PROMPT *********************************************************************************************;
DECLARE
    V_ID_CLASSE           FILAS.TBSMS_CLASSE.ID_CLASSE%TYPE;
BEGIN
    --
    SELECT ID_CLASSE 
      INTO V_ID_CLASSE 
      FROM FILAS.TBSMS_CLASSE 
     WHERE NM_CLASSE = 'EMISSAO-PROC-BOLAV' 
       AND SG_APLICACAO = 'MODCOB';
    --
    DELETE 
      FROM FILAS.TBSMS_ATENDIMENTO 
     WHERE ID_CLASSE = V_ID_CLASSE;
    --
    DELETE 
      FROM FILAS.TBSMS_HORARIO_EXECUCAO
     WHERE ID_CLASSE = V_ID_CLASSE;
    --
    DELETE 
      FROM FILAS.TBSMS_TAREFA
     WHERE ID_CLASSE = V_ID_CLASSE;
    --
    DELETE 
      FROM FILAS.TBSMS_CLASSE 
     WHERE ID_CLASSE = V_ID_CLASSE;
    --
    COMMIT;
END;
/
