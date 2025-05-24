--==============================================================================
-- Projeto       : Migração do VSMS para NETSMS [Billing] - PACOTE 05D - 
--                 CONFIGURAÇÃO DOS NOVOS SERVIDORES DO NMC.
-- Tag           : FILP_137174_NI_004_F5D
-- Responsável   : GABRIEL GARCIA
-- Seqüência     : 001
-- Função        : Configuração de novo servidor de NMC BATCH 
-- Criação       : 10/04/2018
-- =============================================================================
-- Historico de Alteracoes
-- =============================================================================
-- Data          : 
-- Solicitante   : 
-- Alteracao     : 
--==============================================================================
PROMPT ***********************************************************************************************;
PROMPT *     CONFIG REGISTROS NAS TABELAS FILAS.TBSMS_SERVIDOR E FILAS.TBSMS_ATENDIMENTO BASE FILASP *;
PROMPT ***********************************************************************************************;
DECLARE

  PROCEDURE CRIA_SERVIDOR_FILAS(VID_SERVIDOR_BASE FILAS.TBSMS_SERVIDOR.ID_SERVIDOR%TYPE,
                                V_DS_URL_SERVIDOR FILAS.TBSMS_SERVIDOR.DS_URL_SERVIDOR%TYPE,
                                V_NM_SERVIDOR     FILAS.TBSMS_SERVIDOR.NM_SERVIDOR%TYPE,
                                VPRINT_RESULT     BOOLEAN) is
  
    VID_SERVIDOR_NOVO FILAS.TBSMS_SERVIDOR.ID_SERVIDOR%TYPE;
    VREGISTROS        VARCHAR2(1000);
  
  BEGIN
    select filas.sqsms_servidor_01.nextval
      into VID_SERVIDOR_NOVO
      from dual;
  
    IF (VPRINT_RESULT) THEN
      DBMS_OUTPUT.PUT_LINE('-------> VID_SERVIDOR_NOVO: ' ||
                           VID_SERVIDOR_NOVO);
      DBMS_OUTPUT.PUT_LINE('-------> VID_SERVIDOR_BASE: ' ||
                           VID_SERVIDOR_BASE);
      DBMS_OUTPUT.PUT_LINE(' ');
      DBMS_OUTPUT.PUT_LINE(' ');
      DBMS_OUTPUT.PUT_LINE('-------> TBSMS_SERVIDOR BASE:');
      DBMS_OUTPUT.PUT_LINE('ID_SERVIDOR | NM_SERVIDOR | QT_PROCESSOS | DS_URL_SERVIDOR | DS_CAMINHO_ANEXO | FN_STATUS_SERVIDOR');
      SELECT ID_SERVIDOR || ' | ' || NM_SERVIDOR || ' | ' || QT_PROCESSOS ||
             ' | ' || DS_URL_SERVIDOR || ' | ' || DS_CAMINHO_ANEXO || ' | ' ||
             FN_STATUS_SERVIDOR
        INTO VREGISTROS
        FROM FILAS.TBSMS_SERVIDOR
       WHERE ID_SERVIDOR = VID_SERVIDOR_BASE;
      DBMS_OUTPUT.PUT_LINE(VREGISTROS);
    END IF;
  
    INSERT INTO FILAS.TBSMS_SERVIDOR
      (ID_SERVIDOR,
       NM_SERVIDOR,
       QT_PROCESSOS,
       DS_URL_SERVIDOR,
       DS_CAMINHO_ANEXO,
       FN_STATUS_SERVIDOR)
      SELECT VID_SERVIDOR_NOVO,
             V_NM_SERVIDOR,
             QT_PROCESSOS,
             V_DS_URL_SERVIDOR,
             DS_CAMINHO_ANEXO,
             FN_STATUS_SERVIDOR
        FROM FILAS.TBSMS_SERVIDOR SER
       WHERE SER.ID_SERVIDOR = VID_SERVIDOR_BASE;
  
    IF (VPRINT_RESULT) THEN
      DBMS_OUTPUT.PUT_LINE(' ');
      DBMS_OUTPUT.PUT_LINE(' ');
      DBMS_OUTPUT.PUT_LINE('-------> TBSMS_SERVIDOR NOVO:');
      DBMS_OUTPUT.PUT_LINE('ID_SERVIDOR | NM_SERVIDOR | QT_PROCESSOS | DS_URL_SERVIDOR | DS_CAMINHO_ANEXO | FN_STATUS_SERVIDOR');
      SELECT ID_SERVIDOR || ' | ' || NM_SERVIDOR || ' | ' || QT_PROCESSOS ||
             ' | ' || DS_URL_SERVIDOR || ' | ' || DS_CAMINHO_ANEXO || ' | ' ||
             FN_STATUS_SERVIDOR
        INTO VREGISTROS
        FROM FILAS.TBSMS_SERVIDOR
       WHERE ID_SERVIDOR = VID_SERVIDOR_NOVO;
      DBMS_OUTPUT.PUT_LINE(VREGISTROS);
    END IF;
  
    IF (VPRINT_RESULT) THEN
      DBMS_OUTPUT.PUT_LINE(' ');
      DBMS_OUTPUT.PUT_LINE(' ');
      DBMS_OUTPUT.PUT_LINE('-------------------------------------------||-------------------------------------------');
      DBMS_OUTPUT.PUT_LINE(' ');
      DBMS_OUTPUT.PUT_LINE(' ');
      DBMS_OUTPUT.PUT_LINE('-------> TBSMS_ATENDIMENTO BASE:');
      DBMS_OUTPUT.PUT_LINE(' ');
      DBMS_OUTPUT.PUT_LINE(' ');
      DBMS_OUTPUT.PUT_LINE('ID_ATENDE | ID_CLASSE | ID_SERVIDOR | QT_PROCESSOS');
      FOR AUX IN (SELECT ID_ATENDE || ' | ' || ID_CLASSE || ' | ' ||
                         ID_SERVIDOR || ' | ' || QT_PROCESSOS AS MSG
                    FROM FILAS.TBSMS_ATENDIMENTO SER
                   WHERE SER.ID_SERVIDOR = VID_SERVIDOR_BASE) LOOP
        DBMS_OUTPUT.PUT_LINE(AUX.MSG);
      END LOOP;
    
    END IF;
  
    INSERT INTO FILAS.TBSMS_ATENDIMENTO
      (ID_ATENDE, ID_CLASSE, ID_SERVIDOR, QT_PROCESSOS)
      SELECT filas.sqsms_atendimento_01.nextval,
             ID_CLASSE,
             VID_SERVIDOR_NOVO,
             QT_PROCESSOS
        FROM FILAS.TBSMS_ATENDIMENTO SER
       WHERE SER.ID_SERVIDOR = VID_SERVIDOR_BASE;
  
    IF (VPRINT_RESULT) THEN
      DBMS_OUTPUT.PUT_LINE(' ');
      DBMS_OUTPUT.PUT_LINE(' ');
      DBMS_OUTPUT.PUT_LINE('-------> TBSMS_ATENDIMENTO NOVO:');
      DBMS_OUTPUT.PUT_LINE(' ');
      DBMS_OUTPUT.PUT_LINE(' ');
      DBMS_OUTPUT.PUT_LINE('ID_ATENDE | ID_CLASSE | ID_SERVIDOR | QT_PROCESSOS');
      FOR AUX IN (SELECT ID_ATENDE || ' | ' || ID_CLASSE || ' | ' ||
                         ID_SERVIDOR || ' | ' || QT_PROCESSOS AS MSG
                    FROM FILAS.TBSMS_ATENDIMENTO SER
                   WHERE SER.ID_SERVIDOR = VID_SERVIDOR_NOVO) LOOP
        DBMS_OUTPUT.PUT_LINE(AUX.MSG);
      END LOOP;
    
    END IF;
  
  END CRIA_SERVIDOR_FILAS;

BEGIN
  
/*P1: ID_SERVIDOR BASE, DS_URL_SERVIDOR, NM_SERVIDOR, (TRUE OU FALSE) 
      (TRUE - MOSTRA LOG ANTES E DEPOIS DO INSERT / FALSE - NÃO MOSTRA LOG) 
      OBS.: AUMENTAR O OUTPUT QUANDO TRUE*/
  CRIA_SERVIDOR_FILAS(2, 'https://netsms/app-5', 'novosmsbatch13', FALSE);
  CRIA_SERVIDOR_FILAS(2, 'https://netsms/app-5', 'novosmsbatch14', FALSE);
  CRIA_SERVIDOR_FILAS(2, 'https://netsms/app-5', 'novosmsbatch15', FALSE);
  CRIA_SERVIDOR_FILAS(2, 'https://netsms/app-5', 'novosmsbatch16', FALSE);
  COMMIT;
END;
/