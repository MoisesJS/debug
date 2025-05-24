create or replace package PSN_FILA_HISTORICO is

	/*
    Procedure que efetua a migracao da fila informada para
    as tabelas de historico
    Esta procedure nao possui commit, efetuar a chamada pela procedure
    migrar_filas_historico
  */
	procedure migrar_fila(p_idFila in number);

	/*
   Procedure que efetua a migracao da fila informada para
    as tabelas de historico
    pNumeroDiasManter  = numero de dias que deseja manter na base. Todas as filas
    encontradas com a data inferior serao migradas para as tabelas de historico
    pQtdeFilasMigradas= Mensagem de retorno com o numero total de filas migradas
    pCodErro = retorna 1 caso tenha acontecido erro , retorna 0 quando não aconteceu
    pMsgErro = retorna a mensagem de erro
  */

	procedure migrar_filas_historico(pNumeroDiasManter  in number,
																	 pQtdeFilasMigradas out varchar2,
																	 pCodErro           out number,
																	 pMsgErro           out varchar2);

end PSN_FILA_HISTORICO;
/