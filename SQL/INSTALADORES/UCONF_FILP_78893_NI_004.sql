--================================================================================================================================
-- PROJETO       : Longo Prazo - Baixa Bancaria
-- TAG           : FILP_78893_NI_004
-- RESPONSÁVEL   : LUIZ A DA COSTA JUNIOR
-- FUNÇÃO        : UCONF
-- CRIAÇÃO       : 19/02/2016
-- ===============================================================================================================================
-- HISTÓRICO DE ALTERAÇÕES
-- ===============================================================================================================================
-- DATA          : 
-- SOLICITANTE   : 
-- ALTERAÇÃO     : 
-- ===============================================================================================================================

DECLARE

  VID_AGENDA FILAS.TBSMS_AGENDA.ID_AGENDA%TYPE;

BEGIN

  FOR X IN (SELECT CLASSE.ID_CLASSE, TAREFA.ID_TAREFA
              FROM (SELECT ID_CLASSE
                      FROM FILAS.TBSMS_CLASSE
                     WHERE NM_CLASSE LIKE '%BAIXA-CRIA FILA%') CLASSE,
                   (SELECT ID_TAREFA, ID_CLASSE
                      FROM FILAS.TBSMS_TAREFA
                     WHERE DS_TAREFA = 'CRIAR FILA BAIXA') TAREFA
             WHERE CLASSE.ID_CLASSE = TAREFA.ID_CLASSE) LOOP
  
    BEGIN
    
      SELECT ID_AGENDA
        INTO VID_AGENDA
        FROM FILAS.TBSMS_AGENDA
       WHERE ID_CLASSE = X.ID_CLASSE
         AND ID_TAREFA = X.ID_TAREFA;
    
      DBMS_OUTPUT.PUT_LINE('Agenda: ' || VID_AGENDA || ' --- ' ||
                           X.ID_CLASSE || ' --- ' || X.ID_TAREFA);
    
    EXCEPTION
      WHEN OTHERS THEN
      
        RAISE_APPLICATION_ERROR(-1, 'Erro' || SQLERRM);
      
    END;
  
    BEGIN
    
	  DELETE FROM FILAS.TBSMS_FILA WHERE ID_AGENDA = VID_AGENDA;
	
      DELETE FROM FILAS.TBSMS_PARAMETRO_AGENDA WHERE ID_AGENDA = VID_AGENDA;
      IF SQL%ROWCOUNT > 0 THEN
      
        DELETE FROM FILAS.TBSMS_AGENDA WHERE ID_AGENDA = VID_AGENDA;
      
      END IF;
    
      DELETE FROM FILAS.TBSMS_HORARIO_EXECUCAO WHERE ID_CLASSE = X.ID_CLASSE;
    
      DELETE FROM FILAS.TBSMS_ATENDIMENTO WHERE ID_CLASSE = X.ID_CLASSE;
    
    END;
  
    DELETE FROM FILAS.TBSMS_TAREFA
     WHERE ID_TAREFA = X.ID_TAREFA
       AND ID_CLASSE = X.ID_CLASSE;
  
    DELETE FROM FILAS.TBSMS_CLASSE WHERE ID_CLASSE = X.ID_CLASSE;
  
    DBMS_OUTPUT.PUT_LINE('ERRO: ' || SQLERRM || ' --- ' || X.ID_CLASSE ||
                         ' --- ' || X.ID_TAREFA || ' --- ' || VID_AGENDA);
  
  END LOOP;

  COMMIT;

END;
/
