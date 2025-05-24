--===============================================================================
-- Projeto       : Hub/PIX Fase 4: Cartão Recorrente
-- Tag           : FILP_201712_NI_001
-- Solicitante   : Paulo Fogaca Filho - DXC
-- ==============================================================================
-- SEQ : 001
-- Executar como : FILAS
-- ==============================================================================
PROMPT --========================================================================
PROMPT :: UCONF_FILP_201712_NI_001_01.SQL ::
PROMPT --========================================================================


DECLARE
  -- BAIXA CARTAO EMISSAO
  vID_CLASSE_EMISSAO        FILAS.TBSMS_CLASSE.ID_CLASSE%type;
  -- BAIXA CC HUB
  vID_CLASSE_BAIXA        FILAS.TBSMS_CLASSE.ID_CLASSE%type;


BEGIN
    BEGIN
      SELECT ID_CLASSE
        INTO vID_CLASSE_EMISSAO
        FROM FILAS.TBSMS_CLASSE
       WHERE NM_CLASSE = 'BAIXA CARTAO EMISSAO';
    EXCEPTION
        WHEN NO_DATA_FOUND THEN 
            vID_CLASSE_EMISSAO := 0;
    END;

    BEGIN
      SELECT ID_CLASSE
        INTO vID_CLASSE_BAIXA
        FROM FILAS.TBSMS_CLASSE
       WHERE NM_CLASSE = 'BAIXA CC HUB';   
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            vID_CLASSE_BAIXA := 0;
    END;


    IF vID_CLASSE_EMISSAO > 0 THEN 
      UPDATE FILAS.TBSMS_TAREFA
         SET NM_TAREFA = 'EMISSAO_BAIXA_CARTAO_CREDITO_DESAB'
       WHERE NM_TAREFA = 'EMISSAO_BAIXA_CARTAO_CREDITO';
      
      
      UPDATE FILAS.TBSMS_GRUPO_TAREFA
         SET NM_GRUPO_TAREFA = 'DESAB CARTAO EMISSAO'
       WHERE NM_GRUPO_TAREFA = 'BAIXA CARTAO EMISSAO';
      

      DELETE 
        FROM FILAS.TBSMS_ATENDIMENTO
       WHERE ID_CLASSE IN (vID_CLASSE_EMISSAO);

      
      DELETE 
        FROM FILAS.TBSMS_HORARIO_EXECUCAO
       WHERE ID_CLASSE IN (vID_CLASSE_EMISSAO);
       
        
      UPDATE FILAS.TBSMS_CLASSE
         SET NM_CLASSE = 'DESAB CARTAO EMISSAO'
       WHERE ID_CLASSE = vID_CLASSE_EMISSAO;
    END IF;
    
    IF vID_CLASSE_BAIXA > 0 THEN
      UPDATE FILAS.TBSMS_TAREFA
         SET NM_TAREFA = 'EMISSAO_BAIXA_CARTAO_CREDITO_HUB_DESAB'
       WHERE NM_TAREFA = 'EMISSAO_BAIXA_CARTAO_CREDITO_HUB';
      
      
      UPDATE FILAS.TBSMS_GRUPO_TAREFA
         SET NM_GRUPO_TAREFA = 'BAIXA CC HUB DESAB'
       WHERE NM_GRUPO_TAREFA = 'BAIXA CC HUB';
      

      DELETE 
        FROM FILAS.TBSMS_ATENDIMENTO
       WHERE ID_CLASSE IN (vID_CLASSE_BAIXA);

      
      DELETE 
        FROM FILAS.TBSMS_HORARIO_EXECUCAO
       WHERE ID_CLASSE IN (vID_CLASSE_BAIXA);
       
        
      UPDATE FILAS.TBSMS_CLASSE
         SET NM_CLASSE = 'BAIXA CC HUB DESAB'
       WHERE ID_CLASSE = vID_CLASSE_BAIXA;
    END IF;
END;
/