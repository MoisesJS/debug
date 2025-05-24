-- ==================================================================================================
-- ÁREA: 			      SISTEMAS
-- PROJETO: 		    PPM 137174 - Migração do VSMS para NETSMS [Billing] - Pacote 4.
-- TAG: 			       SMSP_137174_NI_070_F5A
-- RESPONSÁVEL: 	 Marcus R Pestana
-- EXECUTAR COMO: FILAS
-- BASE: 			      FILASP
-- DATA: 			      18/10/2017
-- FUNÇÃO:			     DESINSTALAÇÃO DA DEMANDA.
-- ==================================================================================================

PROMPT --====================================================================
PROMPT UNINSTALL_FILP_137174_NI_001_F5A.sql
PROMPT INICIANDO UNINSTALL - DEMANDA: FILP_137174_NI_001_F5A
PROMPT --====================================================================

PROMPT **********************************************************************
PROMPT                      **** A T E N C A O ****
PROMPT ESSE PROCEDIMENTO DEVERA SER EXECUTADA EM TODAS AS BASES DE DADOS
PROMPT DO NETSMS.
PROMPT #BASE#  FILASP
PROMPT **********************************************************************


--***************************************************************************
-- INICIO - Executar como usuario FILAS
--***************************************************************************
PROMPT **********************************************************************
PROMPT COMANDOS A SEGUIR DEVEM SER RODADOS COM O OWNER    ** FILAS **
PROMPT NA BASE FILASP
PROMPT #USUARIO# FILAS
PROMPT **********************************************************************

SELECT TO_CHAR(SYSDATE,'DD/MM/RRRR HH24:MI:SS') DATA_HORA_INICIO FROM DUAL;

SET ARRAYSIZE 1;
SET DEFINE OFF;

PROMPT >> UCONF_FILP_137174_NI_001_F5A_001.sql;
@@UCONF_FILP_137174_NI_001_F5A_001.sql;
SHOW ERRORS;

--***************************************************************************
-- FIM - Executar como usuario FILAS
--***************************************************************************

--===========================================================================
PROMPT
PROMPT Recompilar os objetos invalidos
PROMPT 
PROMPT FIM UNINSTALL_FILP_137174_NI_001_F5A.SQL
--===========================================================================

SELECT TO_CHAR(SYSDATE,'DD/MM/RRRR HH24:MI:SS') DATA_HORA_FIM FROM DUAL;
/
