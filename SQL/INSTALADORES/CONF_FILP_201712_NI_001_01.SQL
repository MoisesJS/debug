--===============================================================================
-- Projeto       : Hub/PIX Fase 4: Cart�o Recorrente
-- Tag           : FILP_201712_NI_001
-- Solicitante   : Paulo Fogaca Filho - DXC
-- ==============================================================================
-- SEQ : 001
-- Executar como : FILAS
-- ==============================================================================
DECLARE
  -- BAIXA CARTAO EMISSAO
  vID_CLASSE_EMISSAO        FILAS.TBSMS_CLASSE.ID_CLASSE%type;
  vID_GRUPO_TAREFA_EMISSAO  FILAS.TBSMS_GRUPO_TAREFA.ID_GRUPO_TAREFA%type;
  vID_TAREFA_EMISSAO        FILAS.TBSMS_TAREFA.ID_TAREFA%type;
  
  -- BAIXA CC HUB
  vID_CLASSE_BAIXA        FILAS.TBSMS_CLASSE.ID_CLASSE%type;
  vID_GRUPO_TAREFA_BAIXA  FILAS.TBSMS_GRUPO_TAREFA.ID_GRUPO_TAREFA%type;
  vID_TAREFA_BAIXA        FILAS.TBSMS_TAREFA.ID_TAREFA%type;

  VSEQ NUMBER;
  VMAX NUMBER;

BEGIN


    SELECT FILAS.SQSMS_CLASSE_01.NEXTVAL INTO VSEQ FROM DUAL;

    SELECT MAX(ID_CLASSE) INTO VMAX FROM FILAS.TBSMS_CLASSE;

    if VSEQ<=VMAX THEN
      LOOP  
      EXIT WHEN VSEQ=VMAX;
        SELECT FILAS.SQSMS_CLASSE_01.NEXTVAL INTO VSEQ FROM DUAL;  
      END LOOP;
      
      SELECT FILAS.SQSMS_CLASSE_01.NEXTVAL INTO VSEQ FROM DUAL;
    END IF;

  vID_CLASSE_EMISSAO := VSEQ;

  insert into FILAS.TBSMS_CLASSE (ID_CLASSE, NM_CLASSE, DS_CLASSE, SG_APLICACAO)
  values (vID_CLASSE_EMISSAO, 'BAIXA CARTAO EMISSAO', 'BAIXA ONLINE CARTAO CREDITO EMISSAO', 'MODCOB');

  SELECT FILAS.SQSMS_CLASSE_01.NEXTVAL
    INTO vID_CLASSE_BAIXA
    FROM DUAL;
    
  insert into FILAS.TBSMS_CLASSE (ID_CLASSE, NM_CLASSE, DS_CLASSE, SG_APLICACAO)
  values (vID_CLASSE_BAIXA, 'BAIXA CC HUB', 'BAIXA ONLINE CARTAO CREDITO EMISSAO HUB', 'MODCOB');


    SELECT FILAS.SQSMS_GRUPO_TAREFA_01.NEXTVAL INTO VSEQ FROM DUAL;

    SELECT MAX(ID_GRUPO_TAREFA) INTO VMAX FROM FILAS.TBSMS_GRUPO_TAREFA;

    if VSEQ<=VMAX THEN
      LOOP  
      EXIT WHEN VSEQ=VMAX;
        SELECT FILAS.SQSMS_GRUPO_TAREFA_01.NEXTVAL INTO VSEQ FROM DUAL;  
      END LOOP;
      
      SELECT FILAS.SQSMS_GRUPO_TAREFA_01.NEXTVAL INTO VSEQ FROM DUAL;
    END IF;

  vID_GRUPO_TAREFA_EMISSAO := VSEQ;

  insert into FILAS.TBSMS_GRUPO_TAREFA (ID_GRUPO_TAREFA, NM_GRUPO_TAREFA, DS_GRUPO_TAREFA)
  values (vID_GRUPO_TAREFA_EMISSAO, 'BAIXA CARTAO EMISSAO', 'BAIXA ONLINE CARTAO CREDITO EMISSAO');

  SELECT FILAS.SQSMS_GRUPO_TAREFA_01.NEXTVAL
    INTO vID_GRUPO_TAREFA_BAIXA
    FROM DUAL;
    
  insert into FILAS.TBSMS_GRUPO_TAREFA (ID_GRUPO_TAREFA, NM_GRUPO_TAREFA, DS_GRUPO_TAREFA)
  values (vID_GRUPO_TAREFA_BAIXA, 'BAIXA CC HUB', 'BAIXA ONLINE CARTAO CREDITO EMISSAO HUB');

    SELECT FILAS.SQSMS_TAREFA_01.NEXTVAL INTO VSEQ FROM DUAL;
    SELECT MAX(ID_TAREFA) INTO VMAX FROM FILAS.TBSMS_TAREFA;

    if VSEQ<=VMAX THEN
      LOOP  
      EXIT WHEN VSEQ=VMAX;
        SELECT FILAS.SQSMS_TAREFA_01.NEXTVAL INTO VSEQ FROM DUAL;  
      END LOOP;
      
      SELECT FILAS.SQSMS_TAREFA_01.NEXTVAL INTO VSEQ FROM DUAL;
    END IF;

  vID_TAREFA_EMISSAO := VSEQ;

  insert into FILAS.TBSMS_TAREFA (ID_TAREFA, ID_GRUPO_TAREFA, NM_TAREFA, DS_TAREFA, NM_CLASSE_JAVA, ID_CLASSE, NR_PRIORIDADE_PADRAO, FN_STATUS_TAREFA, FC_AUTOSYS, QT_MAX_VEZES_REPROCESSAR, FN_PRDD_REPROCESSAR, NR_VALOR_PRDD_REPROCESSAR, FN_REPROCESSAR_COM_NOVA, DS_REGRA_ALERTA_MSG)
  values (vID_TAREFA_EMISSAO, vID_GRUPO_TAREFA_EMISSAO, 'EMISSAO_BAIXA_CARTAO_CREDITO', 'Processamento de lotes de cart�o de cr�dito online.', 'br.com.netservicos.novosms.task.emissao.EfetuaBaixaOnlineTask', vID_CLASSE_EMISSAO, 1, 1, 'N', null, null, null, null, null);

  SELECT FILAS.SQSMS_TAREFA_01.NEXTVAL
    INTO vID_TAREFA_BAIXA
    FROM DUAL;
    
  insert into FILAS.TBSMS_TAREFA (ID_TAREFA, ID_GRUPO_TAREFA, NM_TAREFA, DS_TAREFA, NM_CLASSE_JAVA, ID_CLASSE, NR_PRIORIDADE_PADRAO, FN_STATUS_TAREFA, FC_AUTOSYS, QT_MAX_VEZES_REPROCESSAR, FN_PRDD_REPROCESSAR, NR_VALOR_PRDD_REPROCESSAR, FN_REPROCESSAR_COM_NOVA, DS_REGRA_ALERTA_MSG)
  values (vID_TAREFA_BAIXA, vID_GRUPO_TAREFA_BAIXA, 'EMISSAO_BAIXA_CARTAO_CREDITO_HUB', 'Envio do boleto para o hub de pagamentos fazer a cobranca de cc.', 'br.com.netservicos.novosms.task.emissao.EnviarBaixaOnlineHUBTask', vID_CLASSE_BAIXA, 1, 1, 'N', null, null, null, null, null);


    SELECT FILAS.SQSMS_ATENDIMENTO_01.NEXTVAL INTO VSEQ FROM DUAL;
    SELECT MAX(ID_ATENDE) INTO VMAX FROM FILAS.TBSMS_ATENDIMENTO;

    if VSEQ<=VMAX THEN
      LOOP  
      EXIT WHEN VSEQ=VMAX;
        SELECT FILAS.SQSMS_ATENDIMENTO_01.NEXTVAL INTO VSEQ FROM DUAL;  
      END LOOP;
      
      SELECT FILAS.SQSMS_ATENDIMENTO_01.NEXTVAL INTO VSEQ FROM DUAL;
    END IF;

  INSERT INTO FILAS.TBSMS_ATENDIMENTO (ID_ATENDE, ID_CLASSE, ID_SERVIDOR, QT_PROCESSOS)
  VALUES (VSEQ, VID_CLASSE_EMISSAO, 1, 10);

  INSERT INTO FILAS.TBSMS_ATENDIMENTO (ID_ATENDE, ID_CLASSE, ID_SERVIDOR, QT_PROCESSOS)
  VALUES (FILAS.SQSMS_ATENDIMENTO_01.NEXTVAL, VID_CLASSE_BAIXA, 1, 10);

  INSERT INTO FILAS.TBSMS_ATENDIMENTO (ID_ATENDE, ID_CLASSE, ID_SERVIDOR, QT_PROCESSOS)
  VALUES (FILAS.SQSMS_ATENDIMENTO_01.NEXTVAL, VID_CLASSE_EMISSAO, 2, 10);

  INSERT INTO FILAS.TBSMS_ATENDIMENTO (ID_ATENDE, ID_CLASSE, ID_SERVIDOR, QT_PROCESSOS)
  VALUES (FILAS.SQSMS_ATENDIMENTO_01.NEXTVAL, VID_CLASSE_BAIXA, 2, 10);
  
  INSERT INTO FILAS.TBSMS_ATENDIMENTO (ID_ATENDE, ID_CLASSE, ID_SERVIDOR, QT_PROCESSOS)
  VALUES (FILAS.SQSMS_ATENDIMENTO_01.NEXTVAL, VID_CLASSE_EMISSAO, 3, 10);

  INSERT INTO FILAS.TBSMS_ATENDIMENTO (ID_ATENDE, ID_CLASSE, ID_SERVIDOR, QT_PROCESSOS)
  VALUES (FILAS.SQSMS_ATENDIMENTO_01.NEXTVAL, VID_CLASSE_BAIXA, 3, 10);
  
  INSERT INTO FILAS.TBSMS_ATENDIMENTO (ID_ATENDE, ID_CLASSE, ID_SERVIDOR, QT_PROCESSOS)
  VALUES (FILAS.SQSMS_ATENDIMENTO_01.NEXTVAL, VID_CLASSE_EMISSAO, 4, 10);

  INSERT INTO FILAS.TBSMS_ATENDIMENTO (ID_ATENDE, ID_CLASSE, ID_SERVIDOR, QT_PROCESSOS)
  VALUES (FILAS.SQSMS_ATENDIMENTO_01.NEXTVAL, VID_CLASSE_BAIXA, 4, 10);
  
  INSERT INTO FILAS.TBSMS_ATENDIMENTO (ID_ATENDE, ID_CLASSE, ID_SERVIDOR, QT_PROCESSOS)
  VALUES (FILAS.SQSMS_ATENDIMENTO_01.NEXTVAL, VID_CLASSE_EMISSAO, 5, 10);

  INSERT INTO FILAS.TBSMS_ATENDIMENTO (ID_ATENDE, ID_CLASSE, ID_SERVIDOR, QT_PROCESSOS)
  VALUES (FILAS.SQSMS_ATENDIMENTO_01.NEXTVAL, VID_CLASSE_BAIXA, 5, 10);
  
  INSERT INTO FILAS.TBSMS_ATENDIMENTO (ID_ATENDE, ID_CLASSE, ID_SERVIDOR, QT_PROCESSOS)
  VALUES (FILAS.SQSMS_ATENDIMENTO_01.NEXTVAL, VID_CLASSE_EMISSAO, 6, 10);

  INSERT INTO FILAS.TBSMS_ATENDIMENTO (ID_ATENDE, ID_CLASSE, ID_SERVIDOR, QT_PROCESSOS)
  VALUES (FILAS.SQSMS_ATENDIMENTO_01.NEXTVAL, VID_CLASSE_BAIXA, 6, 10);
  
  INSERT INTO FILAS.TBSMS_ATENDIMENTO (ID_ATENDE, ID_CLASSE, ID_SERVIDOR, QT_PROCESSOS)
  VALUES (FILAS.SQSMS_ATENDIMENTO_01.NEXTVAL, VID_CLASSE_EMISSAO, 7, 10);

  INSERT INTO FILAS.TBSMS_ATENDIMENTO (ID_ATENDE, ID_CLASSE, ID_SERVIDOR, QT_PROCESSOS)
  VALUES (FILAS.SQSMS_ATENDIMENTO_01.NEXTVAL, VID_CLASSE_BAIXA, 7, 10);
  
  INSERT INTO FILAS.TBSMS_ATENDIMENTO (ID_ATENDE, ID_CLASSE, ID_SERVIDOR, QT_PROCESSOS)
  VALUES (FILAS.SQSMS_ATENDIMENTO_01.NEXTVAL, VID_CLASSE_EMISSAO, 8, 10);

  INSERT INTO FILAS.TBSMS_ATENDIMENTO (ID_ATENDE, ID_CLASSE, ID_SERVIDOR, QT_PROCESSOS)
  VALUES (FILAS.SQSMS_ATENDIMENTO_01.NEXTVAL, VID_CLASSE_BAIXA, 8, 10);
  
  INSERT INTO FILAS.TBSMS_ATENDIMENTO (ID_ATENDE, ID_CLASSE, ID_SERVIDOR, QT_PROCESSOS)
  VALUES (FILAS.SQSMS_ATENDIMENTO_01.NEXTVAL, VID_CLASSE_EMISSAO, 13, 10);

  INSERT INTO FILAS.TBSMS_ATENDIMENTO (ID_ATENDE, ID_CLASSE, ID_SERVIDOR, QT_PROCESSOS)
  VALUES (FILAS.SQSMS_ATENDIMENTO_01.NEXTVAL, VID_CLASSE_BAIXA, 13, 10);
  
  INSERT INTO FILAS.TBSMS_ATENDIMENTO (ID_ATENDE, ID_CLASSE, ID_SERVIDOR, QT_PROCESSOS)
  VALUES (FILAS.SQSMS_ATENDIMENTO_01.NEXTVAL, VID_CLASSE_EMISSAO, 27, 10);

  INSERT INTO FILAS.TBSMS_ATENDIMENTO (ID_ATENDE, ID_CLASSE, ID_SERVIDOR, QT_PROCESSOS)
  VALUES (FILAS.SQSMS_ATENDIMENTO_01.NEXTVAL, VID_CLASSE_BAIXA, 27, 10);
  
  INSERT INTO FILAS.TBSMS_ATENDIMENTO (ID_ATENDE, ID_CLASSE, ID_SERVIDOR, QT_PROCESSOS)
  VALUES (FILAS.SQSMS_ATENDIMENTO_01.NEXTVAL, VID_CLASSE_EMISSAO, 28, 10);

  INSERT INTO FILAS.TBSMS_ATENDIMENTO (ID_ATENDE, ID_CLASSE, ID_SERVIDOR, QT_PROCESSOS)
  VALUES (FILAS.SQSMS_ATENDIMENTO_01.NEXTVAL, VID_CLASSE_BAIXA, 28, 10);
  
  INSERT INTO FILAS.TBSMS_ATENDIMENTO (ID_ATENDE, ID_CLASSE, ID_SERVIDOR, QT_PROCESSOS)
  VALUES (FILAS.SQSMS_ATENDIMENTO_01.NEXTVAL, VID_CLASSE_EMISSAO, 29, 10);

  INSERT INTO FILAS.TBSMS_ATENDIMENTO (ID_ATENDE, ID_CLASSE, ID_SERVIDOR, QT_PROCESSOS)
  VALUES (FILAS.SQSMS_ATENDIMENTO_01.NEXTVAL, VID_CLASSE_BAIXA, 29, 10);
  
  INSERT INTO FILAS.TBSMS_ATENDIMENTO (ID_ATENDE, ID_CLASSE, ID_SERVIDOR, QT_PROCESSOS)
  VALUES (FILAS.SQSMS_ATENDIMENTO_01.NEXTVAL, VID_CLASSE_EMISSAO, 30, 10);

  INSERT INTO FILAS.TBSMS_ATENDIMENTO (ID_ATENDE, ID_CLASSE, ID_SERVIDOR, QT_PROCESSOS)
  VALUES (FILAS.SQSMS_ATENDIMENTO_01.NEXTVAL, VID_CLASSE_BAIXA, 30, 10);
    
    SELECT FILAS.SQSMS_HORARIO_EXECUCAO_01.NEXTVAL INTO VSEQ FROM DUAL;
    SELECT MAX(ID_HORARIO_EXECUCAO) INTO VMAX FROM FILAS.TBSMS_HORARIO_EXECUCAO;

    if VSEQ<=VMAX THEN
      LOOP  
      EXIT WHEN VSEQ=VMAX;
        SELECT FILAS.SQSMS_HORARIO_EXECUCAO_01.NEXTVAL INTO VSEQ FROM DUAL;  
      END LOOP;
      
      SELECT FILAS.SQSMS_HORARIO_EXECUCAO_01.NEXTVAL INTO VSEQ FROM DUAL;
    END IF;

  INSERT INTO FILAS.TBSMS_HORARIO_EXECUCAO (ID_HORARIO_EXECUCAO, ID_CLASSE, DH_INICIO_FAIXA, DH_FIM_FAIXA)
  VALUES (VSEQ, VID_CLASSE_EMISSAO, TO_DATE('01-05-2024 00:01:00', 'dd-mm-yyyy hh24:mi:ss'), TO_DATE('30-12-2049 23:59:59', 'dd-mm-yyyy hh24:mi:ss'));

  INSERT INTO FILAS.TBSMS_HORARIO_EXECUCAO (ID_HORARIO_EXECUCAO, ID_CLASSE, DH_INICIO_FAIXA, DH_FIM_FAIXA)
  VALUES (FILAS.SQSMS_HORARIO_EXECUCAO_01.NEXTVAL, VID_CLASSE_BAIXA, TO_DATE('01-05-2024 00:01:00', 'dd-mm-yyyy hh24:mi:ss'), TO_DATE('30-12-2049 23:59:59', 'dd-mm-yyyy hh24:mi:ss'));

END;
/