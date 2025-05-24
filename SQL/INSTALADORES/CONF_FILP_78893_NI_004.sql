--================================================================================================================================
-- PROJETO       : Longo Prazo - Baixa Bancaria
-- TAG           : FILP_78893_NI_004
-- RESPONSÁVEL   : LUIZ A DA COSTA JUNIOR
-- FUNÇÃO        : CONF
-- CRIAÇÃO       : 19/02/2016
-- ===============================================================================================================================
-- HISTÓRICO DE ALTERAÇÕES
-- ===============================================================================================================================
-- DATA          : 
-- SOLICITANTE   : 
-- ALTERAÇÃO     : 
--===========================================================================

DECLARE

  VID_CLASE            FILAS.TBSMS_CLASSE.ID_CLASSE%TYPE;
  VID_TAREFA           FILAS.TBSMS_TAREFA.ID_TAREFA%TYPE;
  VID_AGENDA           FILAS.TBSMS_AGENDA.ID_AGENDA%TYPE;
  VBASE                VARCHAR2(30);
  VBASE_CONT           NUMBER := 0;
  VID_HORARIO_EXECUCAO FILAS.TBSMS_HORARIO_EXECUCAO.ID_HORARIO_EXECUCAO%TYPE;
  VID_ATENDE           FILAS.TBSMS_ATENDIMENTO.ID_ATENDE%TYPE;

  TYPE R_BASE IS RECORD(
    ID_BASE      INTEGER,
    NM_BASE      VARCHAR2(10),
    NM_BASE_FULL VARCHAR2(15));

  TYPE T_BASE IS TABLE OF R_BASE INDEX BY PLS_INTEGER;

  TB_BASE T_BASE;

BEGIN

  TB_BASE(0).ID_BASE := 1;
  TB_BASE(0).NM_BASE := 'BH';
  TB_BASE(0).NM_BASE_FULL := 'netbh';
  TB_BASE(1).ID_BASE := 2;
  TB_BASE(1).NM_BASE := 'BRA';
  TB_BASE(1).NM_BASE_FULL := 'netbrasil';
  TB_BASE(2).ID_BASE := 3;
  TB_BASE(2).NM_BASE := 'ISP';
  TB_BASE(2).NM_BASE_FULL := 'netisp';
  TB_BASE(3).ID_BASE := 4;
  TB_BASE(3).NM_BASE := 'SOC';
  TB_BASE(3).NM_BASE_FULL := 'netsorocaba';
  TB_BASE(4).ID_BASE := 5;
  TB_BASE(4).NM_BASE := 'SP';
  TB_BASE(4).NM_BASE_FULL := 'netsp';
  TB_BASE(5).ID_BASE := 6;
  TB_BASE(5).NM_BASE := 'SUL';
  TB_BASE(5).NM_BASE_FULL := 'netsul';
  TB_BASE(6).ID_BASE := 7;
  TB_BASE(6).NM_BASE := 'BRI';
  TB_BASE(6).NM_BASE_FULL := 'netabc';

  FOR I IN 0 .. TB_BASE.COUNT LOOP
  
    -- INSERE A CLASSE PARA O PROCESSO DE BAIXA BANCARIA
  
    BEGIN
      SELECT FILAS.SQSMS_CLASSE_01.NEXTVAL INTO VID_CLASE FROM DUAL;
    
      INSERT INTO FILAS.TBSMS_CLASSE
      VALUES
        (VID_CLASE,
         'BAIXA-CRIA FILA ' || TB_BASE(I).NM_BASE,
         'BAIXA - CRIA FILA ' || TB_BASE(I).NM_BASE,
         'MODCOB');
    
      DBMS_OUTPUT.PUT_LINE(VID_CLASE);
    
    EXCEPTION
      WHEN OTHERS THEN
      
        DBMS_OUTPUT.PUT_LINE('ERRO: ' || SQLERRM || ' --- ' || VID_CLASE);
        RETURN;
      
    END;
  
    -- INSERE A TAREFA PARA O PROCESSO DE BAIXA BANCARIA
  
    BEGIN
      SELECT FILAS.SQSMS_TAREFA_01.NEXTVAL INTO VID_TAREFA FROM DUAL;
    
      INSERT INTO FILAS.TBSMS_TAREFA
      VALUES
        (VID_TAREFA,
         4,
         'CRIAR_FILA_' || TB_BASE(I).NM_BASE,
         'CRIAR FILA BAIXA',
         'br.com.netservicos.novosms.task.baixa.CriaFilasBaixaSMSTask',
         VID_CLASE,
         1,
         0,
         'N',
         NULL,
         NULL,
         NULL,
         NULL,
         NULL);
    
    EXCEPTION
      WHEN OTHERS THEN
      
        DBMS_OUTPUT.PUT_LINE('ERRO: ' || SQLERRM || ' --- ' || VID_CLASE ||
                             ' --- ' || VID_TAREFA);
        RETURN;
      
    END;
  
    -- INSERE A AGENDA PARA O PROCESSO DE BAIXA BANCARIA
  
    BEGIN
    
      SELECT FILAS.SQSMS_AGENDA_01.NEXTVAL INTO VID_AGENDA FROM DUAL;
    
      INSERT INTO FILAS.TBSMS_AGENDA
      VALUES
        (VID_AGENDA,
         VID_CLASE,
         VID_TAREFA,
         1,
         TO_DATE('01/01/2016 00:00:01', 'DD/MM/YYYY HH24:MI:SS'),
         TO_DATE('30/12/2049 23:59:58', 'DD/MM/YYYY HH24:MI:SS'),
         5,
         5,
         'AUTOSYS',
         'AUTOSYS',
         NULL,
         'TAREFA QUE CRIAR FILA PARA BAIXA',
         0);
    
    EXCEPTION
      WHEN OTHERS THEN
      
        DBMS_OUTPUT.PUT_LINE('ERRO: ' || SQLERRM || ' --- ' || VID_CLASE ||
                             ' --- ' || VID_TAREFA || ' --- ' ||
                             VID_AGENDA);
        RETURN;
      
    END;
  
    BEGIN
    
      -- INSERE OS PARAMETROS AGENDA PARA O PROCESSO DE BAIXA BANCARIA
    
      INSERT INTO FILAS.TBSMS_PARAMETRO_AGENDA
      VALUES
        (VID_AGENDA, 'j_identity_base', LOWER(TB_BASE(I).NM_BASE_FULL));
    
    END;
  
    -- INSERE OS HORARIOS DE EXECUÇÃO DA AGENDA PARA O PROCESSO DE BAIXA BANCARIA
    BEGIN
    
      SELECT FILAS.SQSMS_HORARIO_EXECUCAO_01.NEXTVAL
        INTO VID_HORARIO_EXECUCAO
        FROM DUAL;
    
      INSERT INTO FILAS.TBSMS_HORARIO_EXECUCAO
      VALUES
        (VID_HORARIO_EXECUCAO,
         VID_CLASE,
         TO_DATE('01/01/2016 00:00:01', 'DD/MM/YYYY HH24:MI:SS'),
         TO_DATE('30/12/2049 23:59:58', 'DD/MM/YYYY HH24:MI:SS'));
    
    END;
  
    -- INSERE O SCHEDULE DE ATENDIMENTO DA FILA PARA O PROCESSO DE BAIXA BANCARIA
  
    BEGIN
	
	for serv in (SELECT DISTINCT id_servidor
					FROM FILAS.TBSMS_ATENDIMENTO
				  WHERE ID_CLASSE IN (SELECT ID_CLASSE
										FROM FILAS.TBSMS_TAREFA
									   WHERE ID_GRUPO_TAREFA = 4
										AND DS_TAREFA LIKE '%BaixaCargaTask%')
										) loop
										
										
	
      SELECT FILAS.SQSMS_ATENDIMENTO_01.NEXTVAL INTO VID_ATENDE FROM DUAL;
    
      INSERT INTO FILAS.TBSMS_ATENDIMENTO VALUES (VID_ATENDE, VID_CLASE, serv.id_servidor, 1);
	  
	  end loop;
    
    END;
  
    DBMS_OUTPUT.PUT_LINE('MSG: ' || SQLERRM || ' --- ' || VID_CLASE ||
                         ' --- ' || VID_TAREFA || ' --- ' || VID_AGENDA);
  
    VBASE_CONT := VBASE_CONT + 1;
    
      COMMIT;
  
  END LOOP;



END;

/
