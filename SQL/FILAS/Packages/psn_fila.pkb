create or replace package body psn_fila
/*
========================================================
nome da package: psn_fila
autor..........: Jackson Vieira (WA Informatica)
data criacao...: jul/2007
descricao......: pacote que armazena procedures
                 de controle de fila
--------------------------------------------------------
data alteracao.:
autor..........:
descricao......:
========================================================
*/
 as

	procedure get_error_message(p_id_controle_autosys in TBSMS_CONTROLE_AUTOSYS.ID_CONTROLE_AUTOSYS%type,
															p_message             in out VARCHAR2)
	
	 is
	
		cursor cursorMessages is
			select m.ds_mensagem msg, m.id_fila fila
				from tbsms_mensagem_autosys m
			 where m.id_controle_autosys = p_id_controle_autosys
			 order by m.id_msg_autosys;
	
	begin
	
		for message in cursorMessages loop
		
			p_message := p_message || ' \n ' || 'FILA : ' || message.fila ||
									 ', MSG : ' || message.msg;
		
		end loop;
	
	end get_error_message;

	procedure valida_parametros_nulos(p_id_controle_autosys in TBSMS_PRM_CTRL_AUTOSYS.ID_CONTROLE_AUTOSYS%type,
																		p_nom_parm01          in TBSMS_PRM_CTRL_AUTOSYS.NM_PARAMETRO%type,
																		p_val_parm01          in TBSMS_PRM_CTRL_AUTOSYS.DS_VALOR%type,
																		p_nom_parm02          in TBSMS_PRM_CTRL_AUTOSYS.NM_PARAMETRO%type,
																		p_val_parm02          in TBSMS_PRM_CTRL_AUTOSYS.DS_VALOR%type,
																		p_nom_parm03          in TBSMS_PRM_CTRL_AUTOSYS.NM_PARAMETRO%type,
																		p_val_parm03          in TBSMS_PRM_CTRL_AUTOSYS.DS_VALOR%type,
																		p_nom_parm04          in TBSMS_PRM_CTRL_AUTOSYS.NM_PARAMETRO%type,
																		p_val_parm04          in TBSMS_PRM_CTRL_AUTOSYS.DS_VALOR%type,
																		p_nom_parm05          in TBSMS_PRM_CTRL_AUTOSYS.NM_PARAMETRO%type,
																		p_val_parm05          in TBSMS_PRM_CTRL_AUTOSYS.DS_VALOR%type,
																		p_nom_parm06          in TBSMS_PRM_CTRL_AUTOSYS.NM_PARAMETRO%type,
																		p_val_parm06          in TBSMS_PRM_CTRL_AUTOSYS.DS_VALOR%type,
																		p_nom_parm07          in TBSMS_PRM_CTRL_AUTOSYS.NM_PARAMETRO%type,
																		p_val_parm07          in TBSMS_PRM_CTRL_AUTOSYS.DS_VALOR%type,
																		p_nom_parm08          in TBSMS_PRM_CTRL_AUTOSYS.NM_PARAMETRO%type,
																		p_val_parm08          in TBSMS_PRM_CTRL_AUTOSYS.DS_VALOR%type,
																		p_nom_parm09          in TBSMS_PRM_CTRL_AUTOSYS.NM_PARAMETRO%type,
																		p_val_parm09          in TBSMS_PRM_CTRL_AUTOSYS.DS_VALOR%type,
																		p_nom_parm10          in TBSMS_PRM_CTRL_AUTOSYS.NM_PARAMETRO%type,
																		p_val_parm10          in TBSMS_PRM_CTRL_AUTOSYS.DS_VALOR%type,
																		p_query               out varchar2) is
	
		v_idFila     number(15) := 0;
		v_parametros varchar2(2000) := '';
		--<NMCM_205129_CI_002> -- PPM206088  - Causa Raiz DXC FOP 2022
		v_query      varchar2(5000) := '';
		--<NMCM_205129_CI_002> -- PPM206088  - Causa Raiz DXC FOP 2022
	
	begin
	
		v_parametros := '';
	
		select id_fila
			into v_idFila
			from tbsms_controle_autosys
		 where id_controle_autosys = p_id_controle_autosys;
	
		if trim(p_nom_parm01) is not null and trim(p_val_parm01) is not null then
			v_parametros := '(nm_parametro = ''' || p_nom_parm01 ||
											''' and ds_valor = ''' || p_val_parm01 || ''')';
		end if;
	
		if trim(p_nom_parm02) is not null and trim(p_val_parm02) is not null then
			if trim(v_parametros) is not null then
				v_parametros := v_parametros || 'or';
			end if;
			v_parametros := v_parametros || ' (nm_parametro = ''' || p_nom_parm02 ||
											''' and ds_valor = ''' || p_val_parm02 || ''')';
		
		end if;
	
		if trim(p_nom_parm03) is not null and trim(p_val_parm03) is not null then
			if trim(v_parametros) is not null then
				v_parametros := v_parametros || 'or';
			end if;
			v_parametros := v_parametros || '(nm_parametro = ''' || p_nom_parm03 ||
											''' and ds_valor = ''' || p_val_parm03 || ''')';
		end if;
	
		if trim(p_nom_parm04) is not null and trim(p_val_parm04) is not null then
			if trim(v_parametros) is not null then
				v_parametros := v_parametros || 'or';
			end if;
			v_parametros := v_parametros || '(nm_parametro = ''' || p_nom_parm04 ||
											''' and ds_valor = ''' || p_val_parm04 || ''')';
		end if;
	
		if trim(p_nom_parm05) is not null and trim(p_val_parm05) is not null then
			if trim(v_parametros) is not null then
				v_parametros := v_parametros || 'or';
			end if;
			v_parametros := v_parametros || '(nm_parametro = ''' || p_nom_parm05 ||
											''' and ds_valor = ''' || p_val_parm05 || ''')';
		end if;
	
		if trim(p_nom_parm06) is not null and trim(p_val_parm06) is not null then
			if trim(v_parametros) is not null then
				v_parametros := v_parametros || 'or';
			end if;
			v_parametros := v_parametros || '(nm_parametro = ''' || p_nom_parm06 ||
											''' and ds_valor = ''' || p_val_parm06 || ''')';
		end if;
	
		if trim(p_nom_parm07) is not null and trim(p_val_parm07) is not null then
			if trim(v_parametros) is not null then
				v_parametros := v_parametros || 'or';
			end if;
			v_parametros := v_parametros || '(nm_parametro = ''' || p_nom_parm07 ||
											''' and ds_valor = ''' || p_val_parm07 || ''')';
		end if;
	
		if trim(p_nom_parm08) is not null and trim(p_val_parm08) is not null then
			if trim(v_parametros) is not null then
				v_parametros := v_parametros || 'or';
			end if;
			v_parametros := v_parametros || '(nm_parametro = ''' || p_nom_parm08 ||
											''' and ds_valor = ''' || p_val_parm08 || ''')';
		end if;
	
		if trim(p_nom_parm09) is not null and trim(p_val_parm09) is not null then
			if trim(v_parametros) is not null then
				v_parametros := v_parametros || 'or';
			end if;
			v_parametros := v_parametros || '(nm_parametro = ''' || p_nom_parm09 ||
											''' and ds_valor = ''' || p_val_parm09 || ''')';
		end if;
	
		if trim(p_nom_parm10) is not null and trim(p_val_parm10) is not null then
			if trim(v_parametros) is not null then
				v_parametros := v_parametros || 'or';
			end if;
			v_parametros := v_parametros || '(nm_parametro = ''' || p_nom_parm10 ||
											''' and ds_valor = ''' || p_val_parm10 || ''')';
		end if;
	
		if trim(v_parametros) is not null then
		
			v_query := 'select count(*) from tbsms_parametro_fila
								 where id_fila = ' || v_idFila || ' and (';
			v_query := v_query || v_parametros || ')';
		end if;
	
		p_query := v_query;
	
	end valida_parametros_nulos;

	procedure sp_valida_parm(p_seq        in TBSMS_PRM_CTRL_AUTOSYS.ID_CONTROLE_AUTOSYS%type,
													 p_nom_parm01 in TBSMS_PRM_CTRL_AUTOSYS.NM_PARAMETRO%type,
													 p_val_parm01 in TBSMS_PRM_CTRL_AUTOSYS.DS_VALOR%type,
													 p_nom_parm02 in TBSMS_PRM_CTRL_AUTOSYS.NM_PARAMETRO%type,
													 p_val_parm02 in TBSMS_PRM_CTRL_AUTOSYS.DS_VALOR%type,
													 p_nom_parm03 in TBSMS_PRM_CTRL_AUTOSYS.NM_PARAMETRO%type,
													 p_val_parm03 in TBSMS_PRM_CTRL_AUTOSYS.DS_VALOR%type,
													 p_nom_parm04 in TBSMS_PRM_CTRL_AUTOSYS.NM_PARAMETRO%type,
													 p_val_parm04 in TBSMS_PRM_CTRL_AUTOSYS.DS_VALOR%type,
													 p_nom_parm05 in TBSMS_PRM_CTRL_AUTOSYS.NM_PARAMETRO%type,
													 p_val_parm05 in TBSMS_PRM_CTRL_AUTOSYS.DS_VALOR%type,
													 p_nom_parm06 in TBSMS_PRM_CTRL_AUTOSYS.NM_PARAMETRO%type,
													 p_val_parm06 in TBSMS_PRM_CTRL_AUTOSYS.DS_VALOR%type,
													 p_nom_parm07 in TBSMS_PRM_CTRL_AUTOSYS.NM_PARAMETRO%type,
													 p_val_parm07 in TBSMS_PRM_CTRL_AUTOSYS.DS_VALOR%type,
													 p_nom_parm08 in TBSMS_PRM_CTRL_AUTOSYS.NM_PARAMETRO%type,
													 p_val_parm08 in TBSMS_PRM_CTRL_AUTOSYS.DS_VALOR%type,
													 p_nom_parm09 in TBSMS_PRM_CTRL_AUTOSYS.NM_PARAMETRO%type,
													 p_val_parm09 in TBSMS_PRM_CTRL_AUTOSYS.DS_VALOR%type,
													 p_nom_parm10 in TBSMS_PRM_CTRL_AUTOSYS.NM_PARAMETRO%type,
													 p_val_parm10 in TBSMS_PRM_CTRL_AUTOSYS.DS_VALOR%type) is
	begin
	
		if trim(p_nom_parm01) is not null and trim(p_val_parm01) is not null then
			insert into TBSMS_PRM_CTRL_AUTOSYS
				(ID_CONTROLE_AUTOSYS, NM_PARAMETRO, DS_VALOR)
			values
				(p_seq, p_nom_parm01, p_val_parm01);
		end if;
	
		if trim(p_nom_parm02) is not null and trim(p_val_parm02) is not null then
			insert into TBSMS_PRM_CTRL_AUTOSYS
				(ID_CONTROLE_AUTOSYS, NM_PARAMETRO, DS_VALOR)
			values
				(p_seq, p_nom_parm02, p_val_parm02);
		end if;
	
		if trim(p_nom_parm03) is not null and trim(p_val_parm03) is not null then
			insert into TBSMS_PRM_CTRL_AUTOSYS
				(ID_CONTROLE_AUTOSYS, NM_PARAMETRO, DS_VALOR)
			values
				(p_seq, p_nom_parm03, p_val_parm03);
		end if;
	
		if trim(p_nom_parm04) is not null and trim(p_val_parm04) is not null then
			insert into TBSMS_PRM_CTRL_AUTOSYS
				(ID_CONTROLE_AUTOSYS, NM_PARAMETRO, DS_VALOR)
			values
				(p_seq, p_nom_parm04, p_val_parm04);
		end if;
	
		if trim(p_nom_parm05) is not null and trim(p_val_parm05) is not null then
			insert into TBSMS_PRM_CTRL_AUTOSYS
				(ID_CONTROLE_AUTOSYS, NM_PARAMETRO, DS_VALOR)
			values
				(p_seq, p_nom_parm05, p_val_parm05);
		end if;
	
		if trim(p_nom_parm06) is not null and trim(p_val_parm06) is not null then
			insert into TBSMS_PRM_CTRL_AUTOSYS
				(ID_CONTROLE_AUTOSYS, NM_PARAMETRO, DS_VALOR)
			values
				(p_seq, p_nom_parm06, p_val_parm06);
		end if;
	
		if trim(p_nom_parm07) is not null and trim(p_val_parm07) is not null then
			insert into TBSMS_PRM_CTRL_AUTOSYS
				(ID_CONTROLE_AUTOSYS, NM_PARAMETRO, DS_VALOR)
			values
				(p_seq, p_nom_parm07, p_val_parm07);
		end if;
	
		if trim(p_nom_parm08) is not null and trim(p_val_parm08) is not null then
			insert into TBSMS_PRM_CTRL_AUTOSYS
				(ID_CONTROLE_AUTOSYS, NM_PARAMETRO, DS_VALOR)
			values
				(p_seq, p_nom_parm08, p_val_parm08);
		end if;
	
		if trim(p_nom_parm09) is not null and trim(p_val_parm09) is not null then
			insert into TBSMS_PRM_CTRL_AUTOSYS
				(ID_CONTROLE_AUTOSYS, NM_PARAMETRO, DS_VALOR)
			values
				(p_seq, p_nom_parm09, p_val_parm09);
		end if;
	
		if trim(p_nom_parm10) is not null and trim(p_val_parm10) is not null then
			insert into TBSMS_PRM_CTRL_AUTOSYS
				(ID_CONTROLE_AUTOSYS, NM_PARAMETRO, DS_VALOR)
			values
				(p_seq, p_nom_parm10, p_val_parm10);
		end if;
	
	end sp_valida_parm;

	procedure sp_fila(p_nom_tarefa in TBSMS_TAREFA.NM_TAREFA%type,
										p_nom_parm01 in TBSMS_PRM_CTRL_AUTOSYS.NM_PARAMETRO%type,
										p_val_parm01 in TBSMS_PRM_CTRL_AUTOSYS.DS_VALOR%type,
										p_nom_parm02 in TBSMS_PRM_CTRL_AUTOSYS.NM_PARAMETRO%type,
										p_val_parm02 in TBSMS_PRM_CTRL_AUTOSYS.DS_VALOR%type,
										p_nom_parm03 in TBSMS_PRM_CTRL_AUTOSYS.NM_PARAMETRO%type,
										p_val_parm03 in TBSMS_PRM_CTRL_AUTOSYS.DS_VALOR%type,
										p_nom_parm04 in TBSMS_PRM_CTRL_AUTOSYS.NM_PARAMETRO%type,
										p_val_parm04 in TBSMS_PRM_CTRL_AUTOSYS.DS_VALOR%type,
										p_nom_parm05 in TBSMS_PRM_CTRL_AUTOSYS.NM_PARAMETRO%type,
										p_val_parm05 in TBSMS_PRM_CTRL_AUTOSYS.DS_VALOR%type,
										p_nom_parm06 in TBSMS_PRM_CTRL_AUTOSYS.NM_PARAMETRO%type,
										p_val_parm06 in TBSMS_PRM_CTRL_AUTOSYS.DS_VALOR%type,
										p_nom_parm07 in TBSMS_PRM_CTRL_AUTOSYS.NM_PARAMETRO%type,
										p_val_parm07 in TBSMS_PRM_CTRL_AUTOSYS.DS_VALOR%type,
										p_nom_parm08 in TBSMS_PRM_CTRL_AUTOSYS.NM_PARAMETRO%type,
										p_val_parm08 in TBSMS_PRM_CTRL_AUTOSYS.DS_VALOR%type,
										p_nom_parm09 in TBSMS_PRM_CTRL_AUTOSYS.NM_PARAMETRO%type,
										p_val_parm09 in TBSMS_PRM_CTRL_AUTOSYS.DS_VALOR%type,
										p_nom_parm10 in TBSMS_PRM_CTRL_AUTOSYS.NM_PARAMETRO%type,
										p_val_parm10 in TBSMS_PRM_CTRL_AUTOSYS.DS_VALOR%type) is
	
		type reglista is record(
			id_controle TBSMS_CONTROLE_AUTOSYS.ID_CONTROLE_AUTOSYS%type,
			id_tarefa   TBSMS_TAREFA.ID_TAREFA%type,
			nm_tarefa   TBSMS_TAREFA.NM_TAREFA%type,
			processar   char(1),
			mensagem    TBSMS_CONTROLE_AUTOSYS.DS_MENSAGEM%type);
		type varraylista is table of reglista index by binary_integer;
		type c_crsr is ref cursor;
	
		v_lista_err               varraylista;
		v_crsr                    c_crsr;
		v_seq                     TBSMS_CONTROLE_AUTOSYS.ID_CONTROLE_AUTOSYS%type;
		v_status                  TBSMS_CONTROLE_AUTOSYS.FN_STATUS_CONTROLE%type;
		v_mensagem                varchar2(32767);
		v_qtd_max                 TBSMS_TAREFA.QT_MAX_VEZES_REPROCESSAR%type;
		v_nr_per_repr             TBSMS_TAREFA.FN_PRDD_REPROCESSAR%type;
		v_nr_val_per_repr         TBSMS_TAREFA.NR_VALOR_PRDD_REPROCESSAR%type;
		v_fn_reprocessar_com_nova TBSMS_TAREFA.FN_REPROCESSAR_COM_NOVA%type;
		v_prox_exec               date;
		v_qtd_proc                number(10) := 0;
		v_cont_repr               number(10) := 1;
	
		v_id_controle    TBSMS_CONTROLE_AUTOSYS.ID_CONTROLE_AUTOSYS%type;
		v_id_tarefa      TBSMS_TAREFA.ID_TAREFA%type;
		v_id_tarefa0     TBSMS_TAREFA.ID_TAREFA%type;
		v_retorno        varchar2(4000) := '';
		--<NMCM_205129_CI_002> -- PPM206088  - Causa Raiz DXC FOP 2022
		v_query          varchar2(5000) := '';
		--<NMCM_205129_CI_002> -- PPM206088  - Causa Raiz DXC FOP 2022
		v_qtde_registros number(5) := 0;
		v_controle       number(10) := 0;
		v_todos_proc     char(1) := 'N';
		v_existe         number(10) := 0;
		v_erro           number(1) := 0;
		v_inicio         date := sysdate;
		v_limite         date;
		v_isautosys      TBSMS_TAREFA.FC_AUTOSYS%type;
	
	begin
	
		--data/hora maxima em que a procedure pode ficar rodando (soma 1 dia)
		v_limite := v_inicio + 1;
	
		--verifica o id_tarefa
		begin
			select ID_TAREFA, FC_AUTOSYS
				into v_id_tarefa0, v_isautosys
				from TBSMS_TAREFA
			 where NM_TAREFA = p_nom_tarefa;
		exception
			when others then
				v_retorno := 'Tarefa ' || p_nom_tarefa ||
										 ' nao localizada na tabela TBSMS_TAREFA, processo abortado.';
				v_erro    := 1;
		end;
	
		if (v_isautosys = 'N') then
			v_retorno := 'Tarefa ' || p_nom_tarefa ||
									 ' nao esta um tarefa configurado com a tarefa de AUTOSYS, processo abortado.';
			v_erro    := 1;
		end if;
	
		if v_erro = 0 then
			begin
			
				--verifica tarefas com erro para reprocessa-las
				open v_crsr for
					select ID_CONTROLE_AUTOSYS, ID_TAREFA
						into v_id_controle, v_id_tarefa
						from TBSMS_CONTROLE_AUTOSYS
					 where FN_STATUS_CONTROLE = 4
						 and (FN_EXPIRADO = 0 or FN_EXPIRADO is null)
						 and ID_TAREFA = v_id_tarefa0;
			
				if v_crsr%isopen then
					begin
						v_cont_repr := 0;
						loop
							fetch v_crsr
								into v_id_controle, v_id_tarefa;
							exit when v_crsr%notfound;
						
							valida_parametros_nulos(v_id_controle,
																			p_nom_parm01,
																			p_val_parm01,
																			p_nom_parm02,
																			p_val_parm02,
																			p_nom_parm03,
																			p_val_parm03,
																			p_nom_parm04,
																			p_val_parm04,
																			p_nom_parm05,
																			p_val_parm05,
																			p_nom_parm06,
																			p_val_parm06,
																			p_nom_parm07,
																			p_val_parm07,
																			p_nom_parm08,
																			p_val_parm08,
																			p_nom_parm09,
																			p_val_parm09,
																			p_nom_parm10,
																			p_val_parm10,
																			v_query);
						
							EXECUTE IMMEDIATE v_query
								INTO v_qtde_registros;
						
							if v_qtde_registros > 0 then
							
								v_existe := v_existe + 1;
								v_cont_repr := v_cont_repr + 1;
								v_lista_err(v_cont_repr).id_controle := v_id_controle;
								v_lista_err(v_cont_repr).id_tarefa := v_id_tarefa;
								v_lista_err(v_cont_repr).processar := 'N';
								--verifica o nome da tarefa
								begin
									select NM_TAREFA
										into v_lista_err(v_cont_repr) .nm_tarefa
										from TBSMS_TAREFA
									 where ID_TAREFA = v_id_tarefa;
								exception
									when others then
										v_retorno := 'Tarefa ' || v_id_tarefa ||
																 ' nao localizada na tabela TBSMS_TAREFA, processo abortado.';
										v_erro    := 1;
								end;
							
							end if;
						end loop;
						close v_crsr;
					end;
				end if;
			
				if v_cont_repr = 0 then
					begin
						v_cont_repr := 1;
						--verifica o nome da tarefa
						begin
							select NM_TAREFA
								into v_lista_err(v_cont_repr) .nm_tarefa
								from TBSMS_TAREFA
							 where ID_TAREFA = v_id_tarefa0;
						exception
							when others then
								v_retorno := 'Tarefa ' || v_id_tarefa ||
														 ' nao localizada na tabela TBSMS_TAREFA, processo abortado.';
								v_erro    := 1;
						end;
					end;
				end if;
			
				--verifica qtd maxima permitida, periodicidade, valor periodicidade e se pode processar com nova
				select QT_MAX_VEZES_REPROCESSAR,
							 FN_PRDD_REPROCESSAR,
							 NR_VALOR_PRDD_REPROCESSAR,
							 FN_REPROCESSAR_COM_NOVA
					into v_qtd_max,
							 v_nr_per_repr,
							 v_nr_val_per_repr,
							 v_fn_reprocessar_com_nova
					from TBSMS_TAREFA
				 where ID_TAREFA = v_id_tarefa0;
			
				--verifica se os processos com erro serao reprocessados
				for i in 1 .. v_lista_err.count loop
				
					begin
						--verifica qtd de execucoes e calcula a proxima data/hora de execucao
					
						select QT_EXECUCAO,
									 case
										 when v_nr_per_repr = 0 then --unica
											DH_PRIMEIRA_EXECUCAO
										 when v_nr_per_repr = 1 then --mensal
											DH_PRIMEIRA_EXECUCAO + (v_nr_val_per_repr * 30)
										 when v_nr_per_repr = 2 then --semanal
											DH_PRIMEIRA_EXECUCAO + (v_nr_val_per_repr * 7)
										 when v_nr_per_repr = 3 then --diaria
											DH_PRIMEIRA_EXECUCAO + (v_nr_val_per_repr * 1)
										 when v_nr_per_repr = 4 then --hora
											DH_PRIMEIRA_EXECUCAO + (v_nr_val_per_repr * (1 / 24))
										 when v_nr_per_repr = 5 then --minuto
											DH_PRIMEIRA_EXECUCAO +
											(v_nr_val_per_repr * (1 / (24 * 60)))
									 end proximo_valor
							into v_qtd_proc, v_prox_exec
							from TBSMS_CONTROLE_AUTOSYS
						 where ID_CONTROLE_AUTOSYS = v_lista_err(i).id_controle;
					exception
						when no_data_found then
							v_prox_exec := sysdate;
							v_qtd_proc  := 0;
					end;
				
					if v_qtd_proc < v_qtd_max then
						begin
							if sysdate <= v_prox_exec then
								begin
									v_lista_err(i).processar := 'S';
									update TBSMS_CONTROLE_AUTOSYS
										 set FN_STATUS_CONTROLE = 2
									 where ID_CONTROLE_AUTOSYS = v_lista_err(i).id_controle;
								end;
							else
								begin
									v_erro := 1;
									update TBSMS_CONTROLE_AUTOSYS
										 set FN_EXPIRADO = 1
									 where ID_CONTROLE_AUTOSYS = v_lista_err(i).id_controle;
									v_lista_err(i).mensagem := 'TAREFA EXPIRADA POR TEMPO, EXECUTADA EM ' ||
																						 to_char(sysdate,
																										 'dd/mm/yyyy hh24:mi:ss') ||
																						 ', LIMITE: ' ||
																						 to_char(v_prox_exec,
																										 'dd/mm/yyyy hh24:mi:ss');
								end;
							end if;
						end;
					else
						begin
							v_erro := 1;
							update TBSMS_CONTROLE_AUTOSYS
								 set FN_EXPIRADO = 1
							 where ID_CONTROLE_AUTOSYS = v_lista_err(i).id_controle;
							v_lista_err(i).mensagem := 'TAREFA EXPIRADA POR QTD. EXECUCOES, EXECUTADA ' ||
																				 to_char(v_qtd_proc) ||
																				 ', LIMITE: ' || to_char(v_qtd_max);
						end;
					end if;
				
				end loop;
			
				--somente insere se permitir reprocessar com nova ou se nao houver nada a ser reprocessado
				if v_fn_reprocessar_com_nova = 1 or v_existe = 0 then
					begin
					
						select SQSMS_CONTROLE_AUTOSYS_01.nextval into v_seq from dual;
					
						v_lista_err(v_cont_repr).id_controle := v_seq;
						v_lista_err(v_cont_repr).id_tarefa := v_id_tarefa0;
						v_lista_err(v_cont_repr).processar := 'S';
					
						--insere a tarefa na tabela de controle
						insert into TBSMS_CONTROLE_AUTOSYS
							(ID_CONTROLE_AUTOSYS,
							 ID_TAREFA,
							 DH_SUBMISSAO,
							 DH_INICIO,
							 DH_FIM,
							 FN_STATUS_CONTROLE,
							 ID_FILA,
							 DS_MENSAGEM,
							 QT_EXECUCAO,
							 DH_PRIMEIRA_EXECUCAO,
							 DH_ULTIMA_EXECUCAO,
							 NR_PRIORIDADE,
							 FN_EXPIRADO)
						values
							(v_lista_err(v_cont_repr).id_controle,
							 v_lista_err(v_cont_repr).id_tarefa,
							 sysdate,
							 null,
							 null,
							 0, --novo
							 null,
							 null,
							 null,
							 null,
							 null,
							 null,
							 null);
					
						--valida e insere os parametros
						sp_valida_parm(v_lista_err(v_cont_repr).id_controle,
													 p_nom_parm01,
													 p_val_parm01,
													 p_nom_parm02,
													 p_val_parm02,
													 p_nom_parm03,
													 p_val_parm03,
													 p_nom_parm04,
													 p_val_parm04,
													 p_nom_parm05,
													 p_val_parm05,
													 p_nom_parm06,
													 p_val_parm06,
													 p_nom_parm07,
													 p_val_parm07,
													 p_nom_parm08,
													 p_val_parm08,
													 p_nom_parm09,
													 p_val_parm09,
													 p_nom_parm10,
													 p_val_parm10);
					
					end;
				end if;
			
				commit;
			
				while v_todos_proc = 'N' loop
				
					--no for SOMENTE que nao expirou
					for i in 1 .. v_lista_err.count loop
					
						--verifica status
						sys.sleep(60);
						open v_crsr for
							select FN_STATUS_CONTROLE, DS_MENSAGEM
								from TBSMS_CONTROLE_AUTOSYS
							 where ID_CONTROLE_AUTOSYS = v_lista_err(i)
							.id_controle
								 and v_lista_err(i).processar = 'S';
					
						fetch v_crsr
							into v_status, v_mensagem;
						if v_status in (3, 4) then
							begin
							
								get_error_message(v_lista_err(i).id_controle, v_mensagem);
							
								v_lista_err(i).mensagem := v_mensagem;
								v_lista_err(i).processar := 'N';
							
								if v_status = 4 then
									--finalizou com erro
									begin
										update TBSMS_CONTROLE_AUTOSYS
											 set QT_EXECUCAO = v_qtd_proc + 1
										 where ID_CONTROLE_AUTOSYS = v_lista_err(i).id_controle;
										v_erro := 1;
									end;
								end if;
							
							end;
						end if;
						close v_crsr;
					
					end loop;
				
					--verifica se todos foram processados
					v_todos_proc := 'S';
					for i in 1 .. v_lista_err.count loop
						if v_lista_err(i).processar = 'S' then
							v_todos_proc := 'N';
							exit;
						end if;
					end loop;
				
					--caso ja esteja processando ha mais de um dia eh abortado
					if sysdate >= v_limite then
						begin
							v_retorno    := v_retorno ||
															'<<<--PROCESSANDO HA MAIS DE UM DIA, PROCESSO ABORTADO!-->>>';
							v_todos_proc := 'S';
							v_erro       := 1;
						end;
					end if;
				
				end loop;
				commit;
			
				--retorna as mensagens ocorridas
				for i in 1 .. v_lista_err.count loop
				
					--controle para nao estourar a variavel
					if length(v_retorno || ' / ' || 'SEQUENCE: ' || v_lista_err(i)
										.id_controle || ' ' || 'TAREFA: ' || v_lista_err(i)
										.id_tarefa || ' ' || 'MENSAGEM: ' || v_lista_err(i)
										.mensagem) > 4000 then
						v_retorno := substr(v_retorno || ' / ' || 'SEQUENCE: ' ||
																v_lista_err(i).id_controle || ' ' ||
																'TAREFA: ' || v_lista_err(i)
																.id_tarefa || ' ' || 'MENSAGEM: ' ||
																v_lista_err(i).mensagem,
																1,
																4000);
					else
						begin
							if v_controle = 1 then
								v_retorno := v_retorno || ' / ';
							end if;
						
							v_retorno := v_retorno || 'SEQUENCE: ' || v_lista_err(i)
													.id_controle || ', ';
							v_retorno := v_retorno || 'TAREFA: ' || v_lista_err(i)
													.nm_tarefa || ', ';
							v_retorno := v_retorno || 'MENSAGEM: ' || v_lista_err(i)
													.mensagem;
						
							v_controle := 1;
						end;
					end if;
				
				end loop;
			end;
		end if;
	
		--somente retorna se ocorrer erro
		if v_erro = 1 then
			raise_application_error(-20000, v_retorno);
		end if;
	
	end sp_fila;

  procedure sp_monitorar_fila_recorrente (p_nm_tarefa          in FILAS.TBSMS_TAREFA.NM_TAREFA%TYPE,
                                          p_intervalo_segundos in NUMBER,
                                          p_tempo_maximo       in NUMBER DEFAULT 1800) IS
    --
    vID_TAREFA FILAS.TBSMS_TAREFA.ID_TAREFA%TYPE;
    vDH_INI VARCHAR2(21);
    vDH_FIM VARCHAR2(21);
    vSTATUS_FILA  NUMBER(1) := 0;
    vSTATUS_FILA1 NUMBER(1) := 0;
    vSTATUS_FILA2 NUMBER(1) := 0;
    --
    BEGIN
       --
       IF p_intervalo_segundos > 3600 THEN
          RAISE_APPLICATION_ERROR (-20007, 'Intervalo de Monitoramento deve ser Inferior ou Igual 3600 Segundos!');
       END IF;
       --
       IF p_nm_tarefa IS NULL OR p_intervalo_segundos IS NULL THEN
          RAISE_APPLICATION_ERROR (-20005, 'Nome da Tarefa e Período do Monitoramento são Obrigatórios!');
       END IF;
       --
       BEGIN
          --
          SELECT ID_TAREFA
            INTO vID_TAREFA
            FROM FILAS.TBSMS_TAREFA
           WHERE NM_TAREFA = p_nm_tarefa;
          --
       EXCEPTION
            WHEN NO_DATA_FOUND THEN
                 RAISE_APPLICATION_ERROR (-20006, 'Nome da Tarefa Especificada não Existe!');
       END;
       --
       vDH_INI := TO_CHAR((SYSDATE - p_intervalo_segundos/86400), 'DD/MM/RRRR HH24:MI:SS');
       vDH_FIM := TO_CHAR(SYSDATE, 'DD/MM/RRRR HH24:MI:SS');
       --
       FOR cFILA IN (SELECT F.ID_FILA,
                            F.DH_INICIO,
                            F.FN_STATUS_FILA,
                           (SELECT COUNT(1)
                              FROM FILAS.TBSMS_AGENDA A
                             WHERE A.NR_PERIODICIDADE = 6
                               AND A.ID_TAREFA        = F.ID_TAREFA
                               AND A.ID_CLASSE        = F.ID_CLASSE
                               AND A.ID_AGENDA        = F.ID_AGENDA) TAREFA_RECORRENTE,
                            CASE WHEN F.FN_STATUS_FILA = 0 AND
                                 TRUNC((TO_DATE(vDH_FIM, 'DD/MM/RRRR HH24:MI:SS') - DH_SUBMISSAO) * 1440) > 5 THEN
                                      0
                            ELSE
                                      1
                            END THREAD_STOPPED,
                            CASE WHEN F.DH_INICIO IS NOT NULL AND F.DH_FIM IS NULL THEN
                                      TRUNC((TO_DATE(vDH_FIM, 'DD/MM/RRRR HH24:MI:SS') - F.DH_INICIO) * 86400)
                            ELSE 
                                      0
                            END TEMPO_EXECUCAO
                       FROM FILAS.TBSMS_FILA   F
                      WHERE F.ID_TAREFA = vID_TAREFA
                        AND (F.DH_INICIO IS NULL
                         OR F.DH_INICIO BETWEEN TO_DATE(vDH_INI, 'DD/MM/RRRR HH24:MI:SS')
                                            AND TO_DATE(vDH_FIM, 'DD/MM/RRRR HH24:MI:SS')))
       LOOP
            --
            IF cFILA.THREAD_STOPPED = 0 THEN
               RAISE_APPLICATION_ERROR(-20004, 'Thread do servidor parada há mais de 5 minutos ou não há servidor configurado para processar a fila');
            END IF;
            --
            IF cFILA.TAREFA_RECORRENTE = 0 THEN
               RAISE_APPLICATION_ERROR(-20009, 'Somente tarefas recorrentes podem ser monitoradas!');
            END IF;
            --
            IF cFILA.FN_STATUS_FILA IN (1, 2, 7) AND cFILA.TEMPO_EXECUCAO > P_TEMPO_MAXIMO THEN
               RAISE_APPLICATION_ERROR(-20010, 'Fila Recorrente em execução acima do tempo limite máximo: '|| P_TEMPO_MAXIMO ||' segundos!');
            END IF;
            --
            CASE WHEN cFILA.FN_STATUS_FILA IN (0, 1, 2, 7) THEN
                      vSTATUS_FILA := 1;
                 WHEN cFILA.FN_STATUS_FILA IN (3, 8, 6) THEN
                      vSTATUS_FILA1 := 2;
                 WHEN cFILA.FN_STATUS_FILA IN (4, 5) THEN
                      vSTATUS_FILA2 := 3;
            END CASE;
            --
       END LOOP;
       --
       IF vSTATUS_FILA = 0 AND (vSTATUS_FILA1 = 2 OR vSTATUS_FILA2 = 3) THEN -- FILAS STOPPED
          --
          RAISE_APPLICATION_ERROR(-20001, 'Fila recorrente não está executando!');
          --
       ELSIF vSTATUS_FILA = 1 AND vSTATUS_FILA1 = 0 AND vSTATUS_FILA2 = 3 THEN -- FILAS RUNNING_ERROR
             --
             RAISE_APPLICATION_ERROR(-20002, 'Fila recorrente com ERRO!');
             --
       ELSIF vSTATUS_FILA = 1 AND vSTATUS_FILA1 = 2 AND vSTATUS_FILA2 = 3  THEN --  FILAS RUNNING_WARNING
             --
             RAISE_APPLICATION_ERROR(-20003, 'Alerta: fila recorrente com execuções iniciadas em datas '|| vDH_INI ||' - '|| vDH_FIM ||' com erro!');
             --
       ELSIF vSTATUS_FILA = 0 AND vSTATUS_FILA1 = 0 AND vSTATUS_FILA2 = 0 THEN -- NÃO FORAM LOCALIZADAS FILAS
             --
             RAISE_APPLICATION_ERROR(-20008, 'Não há registros a serem monitorados entre '|| vDH_INI ||' - '|| vDH_FIM ||'!');
             --
       END IF;
       --
   END sp_monitorar_fila_recorrente;
   --
end psn_fila;
/