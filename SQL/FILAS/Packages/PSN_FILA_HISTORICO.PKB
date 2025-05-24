create or replace package body PSN_FILA_HISTORICO as
	/*
  * Procedure que efetua a migracao da fila informada para
    as tabelas de historico
    Esta procedure nao possui commit, efetuar a chamada pela procedure
    migrar_filas_historico
  */
	procedure migrar_Fila(p_idFila in number) is
		v_controle_autosys number(15) := 0;
	begin
		-- inserindo fila historico --
		insert into tbsms_fila_historico
			(id_fila_historico,
			 nm_classe_java,
			 nr_prioridade,
			 ds_url_servidor,
			 dh_submissao,
			 dh_inicio,
			 dh_fim,
			 nm_classe,
			 nm_servidor,
			 nm_status_fila,
			 nm_tarefa,
			 ds_observacao_fila,
			 nm_usuario_criador,
			 nm_usuario_dono)
			select fila.id_fila,
						 tarefa.nm_classe_java,
						 fila.nr_prioridade,
						 servidor.ds_url_servidor,
						 fila.dh_submissao,
						 fila.dh_inicio,
						 fila.dh_fim,
						 classe.nm_classe,
						 servidor.nm_servidor,
						 case fn_status_fila
							 when 0 then
								'NOVA'
							 when 1 then
								'ESPERANDO INICIO'
							 when 2 then
								'PROCESSANDO'
							 when 3 then
								'COMPLETO'
							 when 4 then
								'CANCELADO'
							 when 5 then
								'FALHOU'
							 when 6 then
								'REDISPARO'
							 when 7 then
								'CANCELANDO'
							 when 8 then
								'COMPLETO PARCIAL'
						 end,
						 tarefa.nm_tarefa,
						 decode(fila.id_fila_pai,
										null,
										fila.ds_observacao_fila,
										fila.ds_observacao_fila || ' - idFilaPai = ' ||
										fila.id_fila_pai),
						 fila.nm_usuario_criador,
						 fila.nm_usuario_dono
				from tbsms_fila     fila,
						 tbsms_tarefa   tarefa,
						 tbsms_servidor servidor,
						 tbsms_classe   classe
			 where fila.id_fila = p_idFila
				 and tarefa.id_tarefa = fila.id_tarefa
				 and servidor.id_servidor(+) = fila.id_servidor
				 and classe.id_classe = fila.id_classe;
		-- inserindo anexo historico --
		insert into tbsms_anexo_historico
			(id_anexo_historico, ds_anexo, nm_anexo, id_fila_historico)
			select anexo.id_anexo, anexo.ds_anexo, anexo.nm_anexo, anexo.id_fila
				from tbsms_anexo anexo
			 where anexo.id_fila = p_idFila;
		-- inserindo parametro historico --
		insert into tbsms_parametro_fila_hist
			(id_fila_historico, nm_parametro, ds_valor)
			select parametro.id_fila, parametro.nm_parametro, parametro.ds_valor
				from tbsms_parametro_fila parametro
			 where parametro.id_fila = p_idFila;
		-- inserindo movimento historico --
		insert into tbsms_movimento_fila_hist
			(id_movimento_fila_historico,
			 ds_ocorrencia,
			 nm_tipo_movimento,
			 id_fila_historico,
			 dh_movimento)
			select movimento.id_movimento_fila,
						 case movimento.ds_ocorrencia
							 when 'status.fila.nova' then
								'NOVA'
							 when 'status.fila.espera.inicio' then
								'ESPERANDO INICIO'
							 when 'status.fila.processando' then
								'PROCESSANDO'
							 when 'status.fila.completo' then
								'COMPLETO'
							 when 'status.fila.cancelado' then
								'CANCELADO'
							 when 'status.fila.falhou' then
								'FALHOU'
							 when 'status.fila.redisparo' then
								'REDISPARO'
							 when 'status.fila.cancelando' then
								'CANCELANDO'
							 when 'status.fila.completo.parcial' then
								'COMPLETO PARCIAL'
							 when 'phase.fila.start' then
								'START'
							 when 'phase.fila.pre.action' then
								'PRÉ-ACTION'
							 when 'phase.fila.action' then
								'ACTION'
							 when 'phase.fila.pos.action' then
								'PÓS-ACTION'
							 when 'phase.fila.reports' then
								'REPORTS'
							 when 'phase.fila.processed' then
								'PROCESSED'
						 end,
						 case movimento.fn_tipo_movimento
							 when 0 then
								'NENHUM'
							 when 1 then
								'TROCA STATUS'
							 when 2 then
								'TROCA FASE'
							 when 3 then
								'VISUALIZAÇÃO'
						 end,
						 movimento.id_fila,
						 movimento.dh_movimento
				from tbsms_movimento_fila movimento
			 where movimento.id_fila = p_idFila;
		-- inserindo controle autosys historico --
		begin
			select id_controle_autosys
				into v_controle_autosys
				from tbsms_controle_autosys
			 where id_fila = p_idFila;
		exception
			when no_data_found then
				v_controle_autosys := 0;
		end;
		if (v_controle_autosys <> 0) then
			insert into tbsms_ctrl_autosys_hist
				(id_controle_autosys_hist,
				 nm_tarefa,
				 dh_submissao,
				 dh_inicio,
				 dh_fim,
				 fn_status_controle,
				 id_fila_principal,
				 ds_mensagem,
				 qt_execucao,
				 dh_primeira_execucao,
				 dh_ultima_exclusao)
				select control.id_controle_autosys,
							 to_char(tarefa.id_tarefa),
							 control.dh_submissao,
							 control.dh_inicio,
							 control.dh_fim,
							 control.fn_status_controle,
							 control.id_fila,
							 control.ds_mensagem,
							 control.qt_execucao,
							 control.dh_primeira_execucao,
							 control.dh_ultima_execucao
					from tbsms_controle_autosys control, tbsms_tarefa tarefa
				 where control.id_controle_autosys = v_controle_autosys
					 and tarefa.id_tarefa = control.id_tarefa;
			-- parametros auto sys --
			insert into tbsms_prm_ctrl_autosys_hist
				(id_controle_autosys_hist, nm_parametro, ds_valor)
				select id_controle_autosys, nm_parametro, ds_valor
					from tbsms_prm_ctrl_autosys
				 where id_controle_autosys = v_controle_autosys;
			-- mensagens auto sys --
			insert into tbsms_mensagem_autosys_hist
				(id_msg_autosys_hist,
				 id_controle_autosys_hist,
				 id_fila_historico,
				 ds_mensagem)
				select id_msg_autosys, id_controle_autosys, id_fila, ds_mensagem
					from tbsms_mensagem_autosys
				 where id_controle_autosys = v_controle_autosys;
		end if;
		delete from tbsms_movimento_fila where id_fila = p_idFila;
		delete from tbsms_anexo where id_fila = p_idFila;
		delete from tbsms_parametro_fila where id_fila = p_idFila;
		delete from tbsms_prm_ctrl_autosys
		 where id_controle_autosys = v_controle_autosys;
		 delete from tbsms_mensagem_autosys
		 where id_fila = p_idFila; 
		delete from tbsms_controle_autosys
		 where id_controle_autosys = v_controle_autosys;
		delete from tbsms_fila where id_fila = p_idFila;
	end migrar_Fila;
	procedure migrar_filas_historico(pNumeroDiasManter  in number,
																	 pQtdeFilasMigradas out varchar2,
																	 pCodErro           out number,
																	 pMsgErro           out varchar2) is
		v_idFilaCorte    number(15) := 0;
		v_idFila         number(15) := 0;
		v_contador       number(10) := 0;
		v_contFilhos     number(10) := 0;
		v_contTotal      number(10) := 0;
		v_totRegMigrados number(10) := 0;
		v_codErro        number(1) := 0;
		v_msgErro        varchar2(500) := '';
		v_qtdeDiasCorte  number(10) := pNumeroDiasManter;
		cursor cursorFilas(p_idFilaCorte number) is
			select fila.id_fila
				from tbsms_fila fila
			 where fila.id_fila <= p_idFilaCorte
				 and not exists
			 (select 1
								from tbsms_fila_historico hist
							 where hist.id_fila_historico = fila.id_fila);
		cursor cursorArvoreFila(p_idFila number) is
			select fila.id_fila
				from tbsms_fila fila
			 where not exists
			 (select 1
								from tbsms_fila_historico hist
							 where hist.id_fila_historico = fila.id_fila)
			 start with fila.id_fila = p_idFila
			connect by prior fila.id_fila = fila.id_fila_pai
			 order by fila.id_fila desc;
	begin
		pCodErro := 0;
		pMsgErro := '';
		select max(id_fila)
			into v_idFilaCorte
			from tbsms_fila
		 where dh_submissao < trunc(SYSDATE - v_qtdeDiasCorte);
		for linha in cursorFilas(v_idFilaCorte) loop
			for linha_arvore_fila in cursorArvoreFila(linha.id_fila) loop
				migrar_Fila(linha_arvore_fila.id_fila);
				v_contTotal  := v_contTotal + 1;
				v_contFilhos := v_contFilhos + 1;
				if (mod(v_contFilhos, 100) = 0) then
					commit;
				end if;
			end loop;
			v_contador := v_contador + 1;
			if (mod(v_contador, 100) = 0) then
				commit;
			end if;
		end loop;
		commit;
		pQtdeFilasMigradas := 'Filas Migradas: ' || v_contador ||
													' - Filas Migradas + filhos: ' || v_contTotal;
	exception
		when others then
			pCodErro := 1;
			pMsgErro := sqlerrm;
			rollback;
	end migrar_filas_historico;
end PSN_FILA_HISTORICO;
/
