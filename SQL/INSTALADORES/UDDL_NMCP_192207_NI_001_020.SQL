--==============================================================================
-- Projeto       : UNIFICACAO DE SERIES
-- Tag           : NMCP_192207_NI_001
-- Responsavel   : Edgar Rocha Mendonca
-- Solicitante   : Ruhan Bezerra <ruhan.bezerra@innolevels.com.br>
-- Sequencia     : 020
-- Funcao        : Revogacao de privilegios
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
PROMPT :: UDDL_NMCP_192207_NI_001_020.SQL ::
PROMPT Revogacao de privilegios de tabelas - Inicio
PROMPT --========================================================================
PROMPT Revogacao de privilegios da tabela PROD_JD_O.SN_CENTRALIZADOR_NF 
PROMPT ------------------------------------------------------------------------==
REVOKE SELECT, INSERT, UPDATE, DELETE ON PROD_JD_O.SN_CENTRALIZADOR_NF FROM PROD_JD_L;

PROMPT Revogacao de privilegios da tabela PROD_JD_O.SN_CENTRALIZADOR_NF_LOG
PROMPT ------------------------------------------------------------------------==
REVOKE SELECT, INSERT, UPDATE, DELETE ON PROD_JD_O.SN_CENTRALIZADOR_NF_LOG FROM PROD_JD_L;
PROMPT ------------------------------------------------------------------------==
PROMPT Revogacao de privilegios de tabelas - Fim
PROMPT --\\====================================================================//
