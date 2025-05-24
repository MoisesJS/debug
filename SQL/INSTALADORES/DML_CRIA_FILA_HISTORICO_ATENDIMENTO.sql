-- ---------------------------------------------------------------------------------
-- 1 Correção Sequence - SQSMS_CLASSE_01
-- ---------------------------------------------------------------------------------
declare

  seq        varchar2(50) := 'SQSMS_CLASSE_01';
  tab        varchar2(50) := 'TBSMS_CLASSE';
  campo      varchar2(50) := 'ID_CLASSE';
  retornoSeq number(10);
  retornoIDf number(10);
  passo      number(10);
  inc_by     VARCHAR2(25);

BEGIN

  EXECUTE IMMEDIATE 'SELECT ' || seq || '.NEXTVAL FROM dual'
    into retornoSeq;
  EXECUTE IMMEDIATE 'SELECT max(' || campo || ') FROM ' || tab
    into retornoIDf;

  IF retornoIDf IS NULL THEN
    retornoIDf := 1;
  END IF;

  LOOP
  EXIT WHEN retornoIDf <= retornoSeq;
    EXECUTE IMMEDIATE 'SELECT ' || seq || '.NEXTVAL FROM dual'
        into retornoSeq;
  END LOOP;
 
END;
/

-- ---------------------------------------------------------------------------------
-- 2 Correção Sequence - SQSMS_ATENDIMENTO_01
-- ---------------------------------------------------------------------------------
declare

seq        varchar2(50):= 'SQSMS_ATENDIMENTO_01';
tab        varchar2(50):= 'TBSMS_ATENDIMENTO';
campo      varchar2(50):= 'ID_ATENDE';
retornoSeq number(10);
retornoIDf number(10);
passo      number(10); 
inc_by     VARCHAR2(25);

BEGIN

  EXECUTE IMMEDIATE 'SELECT ' || seq || '.NEXTVAL FROM dual'
    into retornoSeq;
  EXECUTE IMMEDIATE 'SELECT max(' || campo || ') FROM ' || tab
    into retornoIDf;

  IF retornoIDf IS NULL THEN
    retornoIDf := 1;
  END IF;

  LOOP
  EXIT WHEN retornoIDf <= retornoSeq;
    EXECUTE IMMEDIATE 'SELECT ' || seq || '.NEXTVAL FROM dual'
        into retornoSeq;
  END LOOP;
  
END;
/

-- ---------------------------------------------------------------------------------
-- 4 Correção Sequence - SQSMS_TAREFA_01
-- ---------------------------------------------------------------------------------
declare

seq varchar2(50) := 'SQSMS_TAREFA_01';
tab varchar2(50) := 'TBSMS_TAREFA';
campo varchar2(50) := 'ID_TAREFA';
retornoSeq number(10);
retornoIDf number(10);
passo number(10);
inc_by VARCHAR2(25);

BEGIN

  EXECUTE IMMEDIATE 'SELECT ' || seq || '.NEXTVAL FROM dual'
    into retornoSeq;
  EXECUTE IMMEDIATE 'SELECT max(' || campo || ') FROM ' || tab
    into retornoIDf;

  IF retornoIDf IS NULL THEN
    retornoIDf := 1;
  END IF;

  LOOP
  EXIT WHEN retornoIDf <= retornoSeq;
    EXECUTE IMMEDIATE 'SELECT ' || seq || '.NEXTVAL FROM dual'
        into retornoSeq;
  END LOOP;

END;
/


-- ---------------------------------------------------------------------------------
-- 4 Correção Sequence - sqsms_grupo_tarefa_01
-- ---------------------------------------------------------------------------------
declare

seq varchar2(50) := 'sqsms_grupo_tarefa_01';
tab varchar2(50) := 'TBSMS_GRUPO_TAREFA';
campo varchar2(50) := 'ID_GRUPO_TAREFA';
retornoSeq number(10);
retornoIDf number(10);
passo number(10);
inc_by VARCHAR2(25);

BEGIN

  EXECUTE IMMEDIATE 'SELECT ' || seq || '.NEXTVAL FROM dual'
    into retornoSeq;
  EXECUTE IMMEDIATE 'SELECT max(' || campo || ') FROM ' || tab
    into retornoIDf;

  IF retornoIDf IS NULL THEN
    retornoIDf := 1;
  END IF;

  LOOP
  EXIT WHEN retornoIDf <= retornoSeq;
    EXECUTE IMMEDIATE 'SELECT ' || seq || '.NEXTVAL FROM dual'
        into retornoSeq;
  END LOOP;

END;
/


-- Insert Into --------------------------------------------------------------------
-- Table: FILAS.TBSMS_CLASSE -> TBSMS_CLASSE
-- ---------------------------------------------------------------------------------

DECLARE
BEGIN 
    INSERT INTO FILAS.TBSMS_CLASSE(
                      ID_CLASSE,
                      NM_CLASSE,
                      DS_CLASSE, 
                      SG_APLICACAO) 
               VALUES(SQSMS_CLASSE_01.NEXTVAL,
                      'PROCESSA_LT_HSTATEND',
                      'Classe que processa os lotes historico de atend.',
                      'MODCOB');
    
    INSERT INTO FILAS.TBSMS_HORARIO_EXECUCAO(
                      ID_HORARIO_EXECUCAO,
                      ID_CLASSE, 
                      DH_INICIO_FAIXA, 
                      DH_FIM_FAIXA)
                VALUES(SQSMS_HORARIO_EXECUCAO_01.NEXTVAL,
                       SQSMS_CLASSE_01.CURRVAL,
                       TO_DATE('01/01/2015 00:00:01', 'DD/MM/YYYY HH24:MI:SS'),
                       TO_DATE('01/01/2049 23:59:59', 'DD/MM/YYYY HH24:MI:SS'));

END;
/                               
   

   
-- ---------------------------------------------------------------------------------
-- Table: FILAS.TBSMS_ATENDIMENTO -> TBSMS_ATENDIMENTO - 
-- ---------------------------------------------------------------------------------
DECLARE
  CLASSE1 TBSMS_CLASSE.ID_CLASSE%TYPE;
  
  SERVIDOR1 TBSMS_SERVIDOR.ID_SERVIDOR%TYPE;
  
  vATENDIMENTO NUMBER(1);
BEGIN
 
  select ID_CLASSE
    INTO CLASSE1
    from FILAS.TBSMS_CLASSE
   WHERE NM_CLASSE = 'PROCESSA_LT_HSTATEND';


  --
  -- Cadastra a Classe1 nos n servidores.
  --
  INSERT INTO FILAS.TBSMS_ATENDIMENTO
                  (ID_ATENDE,
                   ID_CLASSE, 
                   ID_SERVIDOR, 
                   QT_PROCESSOS)
                VALUES
                  (SQSMS_ATENDIMENTO_01.NEXTVAL, 
                   CLASSE1, 
                   1,
                   1);


END;
/  
-- ---------------------------------------------------------------------------------
-- Table: FILAS.TBSMS_TAREFA -> TBSMS_TAREFA
-- ---------------------------------------------------------------------------------
DECLARE

GRTA1 TBSMS_GRUPO_TAREFA.ID_GRUPO_TAREFA%TYPE;
CLASSE1 TBSMS_CLASSE.ID_CLASSE%TYPE;

BEGIN
        
  INSERT INTO FILAS.TBSMS_GRUPO_TAREFA
    (ID_GRUPO_TAREFA, NM_GRUPO_TAREFA, DS_GRUPO_TAREFA)
  VALUES
    (sqsms_grupo_tarefa_01.NEXTVAL,
     'LOTE_HSTATEND',
     'Processos de Lote de Historico de Atendimento');


  SELECT ID_GRUPO_TAREFA
    INTO GRTA1
    FROM FILAS.TBSMS_GRUPO_TAREFA
   WHERE NM_GRUPO_TAREFA = 'LOTE_HSTATEND';
  --
  select ID_CLASSE
    INTO CLASSE1
    from FILAS.TBSMS_CLASSE
   WHERE NM_CLASSE = 'PROCESSA_LT_HSTATEND';
   
           INSERT INTO FILAS.TBSMS_TAREFA
            (ID_TAREFA,
             ID_GRUPO_TAREFA,
             NM_TAREFA,
             DS_TAREFA,
             NM_CLASSE_JAVA,
             ID_CLASSE,
             NR_PRIORIDADE_PADRAO,
             FN_STATUS_TAREFA,
             FC_AUTOSYS,
             QT_MAX_VEZES_REPROCESSAR,
             FN_PRDD_REPROCESSAR,
             NR_VALOR_PRDD_REPROCESSAR,
             FN_REPROCESSAR_COM_NOVA,
       DS_REGRA_ALERTA_MSG)
          VALUES
            (SQSMS_TAREFA_01.NEXTVAL,
             GRTA1,
             'PROCESSA_LT_HSTATEND',
             'Tarefa para processamento dos lotes de Historico de Atendimento',
             'br.com.netservicos.novosms.task.protocolo.ProcessarLoteHistoricoAtendimentoTask',
             CLASSE1,
             1,
             1,
             'S',
             1,
             1,
             1,
             1,
             null);
      
END;
/
COMMIT;




   
