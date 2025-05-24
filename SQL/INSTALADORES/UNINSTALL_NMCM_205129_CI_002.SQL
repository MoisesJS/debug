-- ==================================================================================================
-- ÁREA: SISTEMAS
-- PROJETO: Causa Raiz DXC FOP 2022 -- PPM201451
-- TAG: NMCM_205129_CI_002
-- RESPONSÁVEL: Luciana Zanette 
-- EXECUTAR COMO: FILAS
-- BASE: FILAS
-- DATA: 31/03/2023
-- FUNÇÃO: DESINSTALAÇÃO DA DEMANDA.
-- ==================================================================================================

PROMPT --====================================================================
PROMPT UNINSTALL_NMCM_205129_CI_002.SQL
PROMPT INICIANDO UNINSTALL - DEMANDA: NMCM_205129_CI_002
PROMPT --====================================================================

PROMPT **********************************************************************
PROMPT                      **** A T E N C A O ****
PROMPT ESSE PROCEDIMENTO DEVERA SER EXECUTADO NA BASE FILAS
PROMPT PROMPT #BASE# FILAS
PROMPT **********************************************************************

--***************************************************************************
-- INICIO - Executar como usuario FILAS
--***************************************************************************
PROMPT **********************************************************************
PROMPT COMANDOS A SEGUIR DEVEM SER RODADOS COM O OWNER          ** FILAS **
PROMPT NA BASE FILAS
PROMPT #USUARIO# FILAS
PROMPT **********************************************************************
SELECT TO_CHAR(SYSDATE,'DD/MM/RRRR HH24:MI:SS') AS DATA_HORA_INICIO FROM DUAL;
SET ARRAYSIZE 1;
SET DEFINE OFF;

PROMPT >> psn_fila.pkb
@@psn_fila.pkb;
SHOW ERRORS;

--***************************************************************************
-- FIM - Executar como usuario FILAS
--***************************************************************************

SELECT TO_CHAR(SYSDATE,'DD/MM/RRRR HH24:MI:SS') AS DATA_HORA_INICIO FROM DUAL;
SET ARRAYSIZE 1;
SET DEFINE OFF;

--===========================================================================
PROMPT
PROMPT Recompilar os objetos invalidos
PROMPT 
PROMPT FIM UNINSTALL_NMCM_205129_CI_002.SQL
--===========================================================================
/