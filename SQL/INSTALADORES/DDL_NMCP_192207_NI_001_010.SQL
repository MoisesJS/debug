--==============================================================================
-- Projeto       : UNIFICACAO DE SERIES
-- Tag           : NMCP_192207_NI_001
-- Responsavel   : Edgar Rocha Mendonca
-- Solicitante   : Ruhan Bezerra <ruhan.bezerra@innolevels.com.br>
-- Sequencia     : 010
-- Funcao        : Criacao de objetos
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
PROMPT ::  DDL_NMCP_192207_NI_001_010.SQL ::
PROMPT Criacao de tabelas - Inicio
PROMPT --========================================================================
PROMPT (Item 01 de 02) Criacao da tabela PROD_JD_O.SN_CENTRALIZADOR_NF
PROMPT ------------------------------------------------------------------------==
CREATE TABLE PROD_JD_O.SN_CENTRALIZADOR_NF(
  MM_COMPETENCIA DATE NOT NULL
 ,NR_NOTA_FISCAL NUMBER(9) NOT NULL
 ,CID_CONTRATO VARCHAR2(6) NOT NULL
 ,CD_GRUPO_NUM_NF NUMBER(9) NOT NULL
);

PROMPT Comentarios da tabela PROD_JD_O.SN_CENTRALIZADOR_NF
PROMPT ------------------------------------------------------------------------==
COMMENT ON TABLE  PROD_JD_O.SN_CENTRALIZADOR_NF IS 'Tabela para armazenar o sequenciamento do número de série das notas fiscais por mês de competência, operadora e grupo. (TGIN: NMCP_192207_NI_001)';
COMMENT ON COLUMN PROD_JD_O.SN_CENTRALIZADOR_NF.MM_COMPETENCIA IS 'Competência da série de notas fiscais da operadora';
COMMENT ON COLUMN PROD_JD_O.SN_CENTRALIZADOR_NF.NR_NOTA_FISCAL IS 'Número da última nota fiscal emitida dentro da competência, operadora e grupo.';
COMMENT ON COLUMN PROD_JD_O.SN_CENTRALIZADOR_NF.CID_CONTRATO IS 'Código da operadora.';
COMMENT ON COLUMN PROD_JD_O.SN_CENTRALIZADOR_NF.CD_GRUPO_NUM_NF IS 'Campo criado para possibilitar o agrupamento das séries para diferentes operadoras/cidades';

PROMPT (Item 02 de 02) Criacao da tabela PROD_JD_O.SN_CENTRALIZADOR_NF_LOG
PROMPT ------------------------------------------------------------------------==
CREATE TABLE PROD_JD_O.SN_CENTRALIZADOR_NF_LOG(
  NM_BASE_DADOS VARCHAR2(100) NOT NULL
 ,MM_COMPETENCIA DATE NOT NULL
 ,NUM_CONTRATO_INI NUMBER(9) NOT NULL
 ,NUM_CONTRATO_FIM NUMBER(9)
 ,CD_GRUPO_NUM_NF NUMBER(9) NOT NULL
 ,DH_CADASTRO DATE DEFAULT SYSDATE NOT NULL
 ,CID_CONTRATO VARCHAR2(6) NOT NULL
 ,NUM_NOTA_FISCAL_MIN NUMBER
 ,NUM_NOTA_FISCAL_MAX NUMBER
 ,QTD_NOTA_FISCAL INTEGER
 ,NR_NOTA_FISCAL INTEGER
);

COMMENT ON TABLE  PROD_JD_O.SN_CENTRALIZADOR_NF_LOG IS 'Tabela para armazenar o log de pedidos de geração de número das notas fiscais por mês de competência, grupo e base de dados. (TGIN: NMCP_192207_NI_001)';
COMMENT ON COLUMN PROD_JD_O.SN_CENTRALIZADOR_NF_LOG.NM_BASE_DADOS IS 'Base de dados que solicitou o lote';
COMMENT ON COLUMN PROD_JD_O.SN_CENTRALIZADOR_NF_LOG.MM_COMPETENCIA IS 'Competência da série de notas fiscais da operadora';
COMMENT ON COLUMN PROD_JD_O.SN_CENTRALIZADOR_NF_LOG.NUM_CONTRATO_INI IS 'Número do contrato inicial';
COMMENT ON COLUMN PROD_JD_O.SN_CENTRALIZADOR_NF_LOG.NUM_CONTRATO_FIM IS 'Número do contrato final.';
COMMENT ON COLUMN PROD_JD_O.SN_CENTRALIZADOR_NF_LOG.CD_GRUPO_NUM_NF IS 'Campo criado para possibilitar o agrupamento das séries para diferentes operadoras/cidades';
COMMENT ON COLUMN PROD_JD_O.SN_CENTRALIZADOR_NF_LOG.DH_CADASTRO IS 'Data e hora do registro.';
COMMENT ON COLUMN PROD_JD_O.SN_CENTRALIZADOR_NF_LOG.CID_CONTRATO IS 'Código da cidade/operadora.';
COMMENT ON COLUMN PROD_JD_O.SN_CENTRALIZADOR_NF_LOG.NUM_NOTA_FISCAL_MIN IS 'Número da nota inicial.';
COMMENT ON COLUMN PROD_JD_O.SN_CENTRALIZADOR_NF_LOG.NUM_NOTA_FISCAL_MAX IS 'Número da nota final';
COMMENT ON COLUMN PROD_JD_O.SN_CENTRALIZADOR_NF_LOG.QTD_NOTA_FISCAL IS 'Quantidades de notas que serão executadas.';
COMMENT ON COLUMN PROD_JD_O.SN_CENTRALIZADOR_NF_LOG.NR_NOTA_FISCAL IS 'Número da última nota fiscal emitida dentro da competência, operadora e grupo.';
PROMPT ------------------------------------------------------------------------==
PROMPT Criacao de tabelas - Fim
PROMPT --\\====================================================================//
PROMPT 
PROMPT 
PROMPT --//====================================================================\\
PROMPT ::  DDL_NMCP_192207_NI_001_010.SQL ::
PROMPT Criacao de Indices - Inicio
PROMPT --========================================================================
PROMPT Criacao do indice IX_CENTRALIZADOR_NF_LOG_01 da tabela PROD_JD_O.SN_CENTRALIZADOR_NF_LOG
PROMPT ------------------------------------------------------------------------==
CREATE INDEX PROD_JD_O.IX_CENTRALIZADOR_NF_LOG_01 ON PROD_JD_O.SN_CENTRALIZADOR_NF_LOG(CD_GRUPO_NUM_NF, MM_COMPETENCIA);
PROMPT ------------------------------------------------------------------------==
PROMPT Criacao de Indices - Fim
PROMPT --\\====================================================================//
PROMPT 
PROMPT 
PROMPT --//====================================================================\\
PROMPT ::  DDL_NMCP_192207_NI_001_010.SQL ::
PROMPT Criacao de PKs - Inicio
PROMPT --========================================================================
PROMPT Alteracao da tabela PROD_JD_O.SN_CENTRALIZADOR_NF para criacao da constraint PK_CENTRALIZADOR_NF
PROMPT ------------------------------------------------------------------------==
ALTER TABLE PROD_JD_O.SN_CENTRALIZADOR_NF ADD CONSTRAINT PK_CENTRALIZADOR_NF PRIMARY KEY (CD_GRUPO_NUM_NF, MM_COMPETENCIA, CID_CONTRATO);
PROMPT ------------------------------------------------------------------------==
PROMPT Criacao de PKs - Fim
PROMPT --\\====================================================================//
