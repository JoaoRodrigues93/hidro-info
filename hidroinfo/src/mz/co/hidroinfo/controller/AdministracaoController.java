package mz.co.hidroinfo.controller;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Center;
import org.zkoss.zul.Window;

public class AdministracaoController extends SelectorComposer<Component> {
	@Wire
	private Window wd_administracao;
	@Wire
	private Center ct_conteudo;
	@Wire
	private Button bt_precario;
	@Wire
	private Button bt_leituraContador;
	@Wire
	private Button bt_contador;
	
	@Listen ("onClick = #bt_precario")
	public void escolhePrecario(){
		alterAparenciaDeBotoes(bt_precario, bt_leituraContador, bt_contador);
		if(ct_conteudo.getFirstChild()!=null)
			ct_conteudo.removeChild(ct_conteudo.getFirstChild());
		Component comp = Executions.createComponents("/registos/precario.zul", ct_conteudo, null);
	}
	
	@Listen ("onClick = #bt_leituraContador")
	public void escolheLeitura(){
		alterAparenciaDeBotoes(bt_leituraContador, bt_precario, bt_contador);
		if(ct_conteudo.getFirstChild()!=null)
			ct_conteudo.removeChild(ct_conteudo.getFirstChild());
		Component comp = Executions.createComponents("/registos/leituraContador.zul", ct_conteudo, null);
	}
	
	@Listen ("onClick = #bt_contador")
	public void escolheContador(){
		alterAparenciaDeBotoes(bt_contador, bt_leituraContador, bt_precario);
		if(ct_conteudo.getFirstChild()!=null)
			ct_conteudo.removeChild(ct_conteudo.getFirstChild());
		Component comp = Executions.createComponents("/registos/contador.zul", ct_conteudo, null);
	}
	
	public void alterAparenciaDeBotoes(Button botaoPrimario,Button botaoSec1,Button botaoSec2){
		botaoPrimario.setDisabled(true);
		botaoPrimario.setZclass("btn btn-primary");
		botaoSec1.setDisabled(false);
		botaoSec1.setZclass("btn btn-default");
		botaoSec2.setDisabled(false);
		botaoSec2.setZclass("btn btn-default");
	}
}
