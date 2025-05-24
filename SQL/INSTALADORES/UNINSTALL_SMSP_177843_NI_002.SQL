--***************************************************************************************
-- Projeto: Integração Novo Atlas x SAP AMX
-- Tag    : SMSP_177843_NI_002
-- Data   : 09/01/2019
-- BASE   : FILAS - FILAS
--
-- Executar como : FILAS
--
-- Autor  : Diego C. Amaral
--***************************************************************************************

PROMPT **********************************************************************
PROMPT **** A T E N C A O ****
PROMPT ESSE PROCEDIMENTO DEVERA SER EXECUTADA NA BASE FILASP.
PROMPT 
PROMPT #BASE# FILASP
PROMPT **********************************************************************
-----------------------------------------------------------------------------
PROMPT **********************************************************************
PROMPT COMANDOS A SEGUIR DEVEM SER RODADOS COM O OWNER ** FILAS **
PROMPT NA BASE FILAS.
PROMPT #USUARIO# FILAS
PROMPT **********************************************************************

PROMPT INICIO do arquivo UNINSTALL_SMSP_177843_NI_002.SQL *********************

set arraysize 1;
set define off;
set serveroutput on
set linesize 1000

PROMPT Configuração de novas filas...
@@UCONF_FILASP_NMC_CRITICAS_PGTO.SQL;

PROMPT COMMIT de alterações.
COMMIT;

PROMPT FIM do arquivo UNINSTALL_SMSP_177843_NI_002.SQL ************************

