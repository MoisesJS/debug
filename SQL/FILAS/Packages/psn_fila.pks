create or replace package psn_fila is

  procedure sp_fila (p_nom_tarefa in TBSMS_TAREFA.NM_TAREFA%type,
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
                   p_val_parm10 in TBSMS_PRM_CTRL_AUTOSYS.DS_VALOR%type);

  procedure sp_valida_parm(p_seq in tbsms_prm_ctrl_autosys.ID_CONTROLE_AUTOSYS%type,
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
                   p_val_parm10 in TBSMS_PRM_CTRL_AUTOSYS.DS_VALOR%type);
                   
 procedure sp_monitorar_fila_recorrente (p_nm_tarefa          in FILAS.TBSMS_TAREFA.NM_TAREFA%TYPE,
                                         p_intervalo_segundos in NUMBER,
                                         p_tempo_maximo       in NUMBER DEFAULT 1800);

end psn_fila;
/
