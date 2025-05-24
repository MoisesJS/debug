--==============================================================================
-- Projeto       : UNIFICACAO DE SERIES
-- Tag           : NMCP_192207_NI_001
-- Responsavel   : Edgar Rocha Mendonca
-- Solicitante   : Ruhan Bezerra <ruhan.bezerra@innolevels.com.br>
-- Sequencia     : 030
-- Funcao        : Exclusao de objetos
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
PROMPT :: UDDL_NMCP_192207_NI_001_030.SQL ::
PROMPT Exclusao de PKs - Inicio
PROMPT --========================================================================
PROMPT Alteracao da tabela PROD_JD_O.SN_CENTRALIZADOR_NF para exclusao da constraint PK_CENTRALIZADOR_NF
PROMPT ------------------------------------------------------------------------==
ALTER TABLE PROD_JD_O.SN_CENTRALIZADOR_NF DROP CONSTRAINT PK_CENTRALIZADOR_NF;
PROMPT ------------------------------------------------------------------------==
PROMPT Exclusao de PKs - Fim
PROMPT --\\====================================================================//
PROMPT 
PROMPT 
PROMPT --//====================================================================\\
PROMPT :: UDDL_NMCP_192207_NI_001_030.SQL ::
PROMPT Exclusao de Indices - Inicio
PROMPT --========================================================================
PROMPT Exclusao do indice IX_CENTRALIZADOR_NF_LOG_01 da tabela PROD_JD_O.SN_CENTRALIZADOR_NF_LOG
PROMPT ------------------------------------------------------------------------==
DROP INDEX PROD_JD_O.IX_CENTRALIZADOR_NF_LOG_01;
PROMPT ------------------------------------------------------------------------==
PROMPT Exclusao de Indices - Fim
PROMPT --\\====================================================================//
PROMPT 
PROMPT 
PROMPT --//====================================================================\\
PROMPT :: UDDL_NMCP_192207_NI_001_030.SQL ::
PROMPT Exclusao de tabelas - Inicio
PROMPT --========================================================================
PROMPT Eliminacao/drop da tabela PROD_JD_O.SN_CENTRALIZADOR_NF
PROMPT ------------------------------------------------------------------------==
DROP TABLE PROD_JD_O.SN_CENTRALIZADOR_NF;

PROMPT Eliminacao/drop da tabela PROD_JD_O.SN_CENTRALIZADOR_NF_LOG
PROMPT ------------------------------------------------------------------------==
DROP TABLE PROD_JD_O.SN_CENTRALIZADOR_NF_LOG;
PROMPT ------------------------------------------------------------------------==
PROMPT Exclusao de tabelas - Fim
PROMPT --\\====================================================================//
