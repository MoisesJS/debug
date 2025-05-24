--==============================================================================
-- Projeto       : UNIFICACAO DE SERIES
-- Tag           : NMCP_192207_NI_001
-- Responsavel   : Edgar Rocha Mendonca
-- Solicitante   : Ruhan Bezerra <ruhan.bezerra@innolevels.com.br>
-- Sequencia     : 020
-- Funcao        : Concessao de privilegios
-- Criacao       : 20/04/2020
-- =============================================================================
-- Historico de Alteracoes
-- =============================================================================
-- Data          : 
-- Solicitante   : 
-- Alteracao     : 
--==============================================================================
-- Data          : 
-- Solicitante   : 
-- Alteracao     : 
--==============================================================================
-- Data          : 
-- Solicitante   : 
-- Alteracao     : 
--==============================================================================
-- Data          : 
-- Solicitante   : 
-- Alteracao     : 
--==============================================================================
PROMPT --//====================================================================\\
PROMPT #USUARIO# PROD_JD_O
PROMPT #BASE# FILASP
PROMPT --\\====================================================================//
PROMPT 
PROMPT 
PROMPT --//====================================================================\\
PROMPT ::  DDL_NMCP_192207_NI_001_020.SQL ::
PROMPT Concessao de privilegios de tabelas - Inicio
PROMPT --========================================================================
PROMPT Concessao de privilegios da tabela PROD_JD_O.SN_CENTRALIZADOR_NF 
PROMPT ------------------------------------------------------------------------==
GRANT  SELECT, INSERT, UPDATE, DELETE ON PROD_JD_O.SN_CENTRALIZADOR_NF TO   PROD_JD_L;

PROMPT Concessao de privilegios da tabela PROD_JD_O.SN_CENTRALIZADOR_NF_LOG
PROMPT ------------------------------------------------------------------------==
GRANT  SELECT, INSERT, UPDATE, DELETE ON PROD_JD_O.SN_CENTRALIZADOR_NF_LOG TO   PROD_JD_L;
PROMPT ------------------------------------------------------------------------==
PROMPT Concessao de privilegios de tabelas - Fim
PROMPT --\\====================================================================//
