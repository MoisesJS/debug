-- ==================================================================================================
-- ÁREA: 			      SISTEMAS
-- PROJETO:       PPM 137174 - Migração do VSMS para NETSMS [Billing] - Pacote 4
-- TAG: 			       FILP_137174_NI_001_F5A
-- RESPONSÁVEL:   Marcus R Pestana
-- EXECUTAR COMO: FILAS
-- BASE: 			      FILASP
-- DATA: 			      18/10/2017
-- FUNÇÃO:		      INSTALAÇÃO DA DEMANDA.
-- ==================================================================================================

PROMPT --=======================================================================
PROMPT INSTALL_FILP_137174_NI_001_F5A.SQL
PROMPT INICIANDO INSTALL - DEMANDA: FILP_137174_NI_001_F5A
PROMPT --=======================================================================


PROMPT **********************************************************************
PROMPT                      **** A T E N C A O ****
PROMPT ESSE PROCEDIMENTO DEVERA SER EXECUTADO EM TODAS AS BASES DE DADOS
PROMPT DO NETSMS.
PROMPT #BASE# FILASP
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

PROMPT >> CONF_FILP_137174_NI_001_F5A_001.sql;
@@CONF_FILP_137174_NI_001_F5A_001.sql;
SHOW ERRORS;


--===========================================================================
PROMPT
PROMPT Recompilar os objetos invalidos
PROMPT 
PROMPT FIM INSTALL_FILP_137174_NI_001_F5A.SQL
--===========================================================================

SELECT TO_CHAR(SYSDATE,'DD/MM/RRRR HH24:MI:SS') DATA_HORA_FIM FROM DUAL;
/
