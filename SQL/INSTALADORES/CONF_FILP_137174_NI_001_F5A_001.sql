--==============================================================================
-- Projeto       : Migração do VSMS - Geração e Emissão de Cobranças Multi Empresa
-- Tag           : FILP_137174_NI_001_F5A
-- Responsável   : Marcus Pestana
-- Seqüência     : 001
-- Função        : Configuração de uma nova Fila para geracao de lotes Print 
--                 House Boletos Avulsos
-- Criação       : 18/10/2017
-- =============================================================================
-- Historico de Alteracoes
-- =============================================================================
-- Data          : 
-- Solicitante   : 
-- Alteracao     : 
--==============================================================================
PROMPT ***********************************************************************************************;
PROMPT *     CONFIGURANDO NOVA TAREFA PARA PROCESSAMENTO DE LOTES DE BOLETOS AVULSOS BASE FILASP     *;
PROMPT ***********************************************************************************************;
DECLARE
    V_ID_CLASSE           FILAS.TBSMS_CLASSE.ID_CLASSE%TYPE;
    V_ID_TAREFA           FILAS.TBSMS_TAREFA.ID_TAREFA%TYPE;
    V_ID_AGENDA           FILAS.TBSMS_AGENDA.ID_AGENDA%TYPE;
    V_ID_ATENDE           FILAS.TBSMS_ATENDIMENTO.ID_ATENDE%TYPE;
    V_ID_HORARIO_EXECUCAO FILAS.TBSMS_HORARIO_EXECUCAO.ID_HORARIO_EXECUCAO%TYPE;
BEGIN
    --
    INSERT INTO FILAS.TBSMS_CLASSE
        (ID_CLASSE
        ,NM_CLASSE
        ,DS_CLASSE
        ,SG_APLICACAO)
    VALUES
        (FILAS.SQSMS_CLASSE_01.NEXTVAL
        ,'EMISSAO-PROC-BOLAV'
        ,'GERAÇÃO DE ARQUIVOS DE EMISSÃO DE BOLETO AVULSO'
        ,'MODCOB')
    RETURNING ID_CLASSE INTO V_ID_CLASSE;
    --
    INSERT INTO FILAS.TBSMS_TAREFA
        (ID_TAREFA
        ,ID_GRUPO_TAREFA
        ,NM_TAREFA
        ,DS_TAREFA
        ,NM_CLASSE_JAVA
        ,ID_CLASSE
        ,NR_PRIORIDADE_PADRAO
        ,FN_STATUS_TAREFA
        ,FC_AUTOSYS)
    VALUES
        (FILAS.SQSMS_TAREFA_01.NEXTVAL
        ,1
        ,'EMISSAO_ARQUIVO_PRINT_BOLETO_AVULSO'
        ,'Cria task para gerar arquivo print agrupando lotes de boletos avulsos pendentes'
        ,'br.com.netservicos.novosms.task.emissao.GerarArquivoBoletoAvulsoTask'
        ,V_ID_CLASSE
        ,1
        ,1
        ,'S')
    RETURNING ID_TAREFA INTO V_ID_TAREFA;
    --
    INSERT INTO FILAS.TBSMS_HORARIO_EXECUCAO
        (ID_HORARIO_EXECUCAO
        ,ID_CLASSE
        ,DH_INICIO_FAIXA
        ,DH_FIM_FAIXA)
    VALUES
        (FILAS.SQSMS_HORARIO_EXECUCAO_01.NEXTVAL
        ,V_ID_CLASSE
        ,TO_DATE('30/09/2017 00:00:01', 'DD/MM/RRRR HH24:MI:SS')
        ,TO_DATE('30/12/2049 23:59:59', 'DD/MM/RRRR HH24:MI:SS'))
    RETURNING ID_HORARIO_EXECUCAO INTO V_ID_HORARIO_EXECUCAO;
    --
    FOR REG IN (SELECT *
                  FROM FILAS.TBSMS_ATENDIMENTO A
                 WHERE A.ID_CLASSE =
                       (SELECT ID_CLASSE 
                          FROM FILAS.TBSMS_TAREFA
                         WHERE NM_TAREFA = 'EMISSAO_ARQUIVO_PRINT_HOUSE')) LOOP
        INSERT INTO FILAS.TBSMS_ATENDIMENTO
            (ID_ATENDE
            ,ID_CLASSE
            ,ID_SERVIDOR
            ,QT_PROCESSOS)
        VALUES
            (FILAS.SQSMS_ATENDIMENTO_01.NEXTVAL
            ,V_ID_CLASSE
            ,REG.ID_SERVIDOR
            ,REG.QT_PROCESSOS)
        RETURNING ID_ATENDE INTO V_ID_ATENDE;
    END LOOP;
    -- 
    COMMIT;
END;
/