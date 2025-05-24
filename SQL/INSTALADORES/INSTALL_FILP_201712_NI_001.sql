-- ===============================================================================================
-- ÁREA:        SISTEMAS
-- PROJETO:     PPM – 203072 – HUB_PIX 
-- TAG:         FILP_201712_NI_001 
-- BASE:        FILASP
-- ===============================================================================================

PROMPT --=======================================================================
PROMPT INSTALL_FILP_201712_NI_001.SQL
PROMPT INICIANDO INSTALL - DEMANDA: FILP_201712_NI_001
PROMPT --=======================================================================

PROMPT **********************************************************************
PROMPT                      **** A T E N C A O ****
PROMPT ESSE PROCEDIMENTO DEVERA SER EXECUTADO NA BASE FILAS
PROMPT #BASE# FILAS
PROMPT **********************************************************************

SET DEFINE OFF;
PROMPT **********************************************************************
PROMPT COMANDOS A SEGUIR DEVEM SER RODADOS COM O OWNER          ** FILAS **
PROMPT NA BASE FILAS
PROMPT #USUARIO# FILAS
PROMPT **********************************************************************

PROMPT CONF_FILP_201712_NI_001_01.SQL
@@CONF_FILP_201712_NI_001_01.SQL
SHOW ERRORS;

PROMPT --===========================================================================
PROMPT
PROMPT RECOMPILAR OS OBJETOS INVALIDOS
PROMPT 
PROMPT FIM INSTALL_FILP_201712_NI_001.SQL
PROMPT --===========================================================================
