--==============================================================================
-- Projeto       : Migra��o do VSMS para NETSMS [Billing] - PACOTE 05D - 
--                 CONFIGURA��O DOS NOVOS SERVIDORES DO NMC.
-- Tag           : FILP_137174_NI_004_F5D
-- Respons�vel   : GABRIEL GARCIA
-- Seq��ncia     : 001
-- Fun��o        : Desconfigura��o de novos servidores de NMC BATCH 
-- Cria��o       : 10/04/2018
-- =============================================================================
-- Historico de Alteracoes
-- =============================================================================
-- Data          : 
-- Solicitante   : 
-- Alteracao     : 
--==============================================================================
PROMPT ***********************************************************************************************;
PROMPT *  DESCONFIG REGISTROS NAS TABELAS FILAS.TBSMS_SERVIDOR E FILAS.TBSMS_ATENDIMENTO BASE FILASP *;
PROMPT ***********************************************************************************************;
BEGIN
	FOR AUX IN (SELECT ID_SERVIDOR
                  FROM FILAS.TBSMS_SERVIDOR
                 WHERE NM_SERVIDOR IN ('novosmsbatch13',
                                       'novosmsbatch14',
                                       'novosmsbatch15',
                                       'novosmsbatch16')) LOOP
       UPDATE FILAS.TBSMS_FILA SET ID_SERVIDOR = 2 WHERE ID_SERVIDOR = AUX.ID_SERVIDOR;
       DELETE FILAS.TBSMS_ATENDIMENTO WHERE  ID_SERVIDOR = AUX.ID_SERVIDOR;
       DELETE FILAS.TBSMS_SERVIDOR WHERE  ID_SERVIDOR = AUX.ID_SERVIDOR;
	END LOOP;

    COMMIT;
END;
/