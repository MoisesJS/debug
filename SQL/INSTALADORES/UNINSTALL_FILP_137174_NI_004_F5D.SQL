-- ==========================================================================
-- ÁREA: 		  SISTEMAS
-- PROJETO:       Migração do VSMS para NETSMS [Billing] - PACOTE 05D - 
--                CONFIGURAÇÃO DOS NOVOS SERVIDORES DO NMC.
-- TAG: 		  FILP_137174_NI_004_F5D
-- RESPONSÁVEL:   GABRIEL GARCIA
-- EXECUTAR COMO: FILAS
-- BASE: 		  FILASP
-- DATA: 		  10/04/2018
-- FUNÇÃO:		  DESINSTALAÇÃO DA DEMANDA.
-- ==========================================================================

PROMPT --====================================================================
PROMPT UNINSTALL_FILP_137174_NI_004_F5D.sql
PROMPT INICIANDO UNINSTALL - DEMANDA: FILP_137174_NI_004_F5D
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

PROMPT >> UCONF_FILP_137174_NI_004_F5D.sql;
@@UCONF_FILP_137174_NI_004_F5D.sql;
SHOW ERRORS;

--***************************************************************************
-- FIM - Executar como usuario FILAS
--***************************************************************************

--===========================================================================
PROMPT
PROMPT Recompilar os objetos invalidos
PROMPT 
PROMPT FIM UNINSTALL_FILP_137174_NI_004_F5D.SQL
--===========================================================================

SELECT TO_CHAR(SYSDATE,'DD/MM/RRRR HH24:MI:SS') DATA_HORA_FIM FROM DUAL;
/
