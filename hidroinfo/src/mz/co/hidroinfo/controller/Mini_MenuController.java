package mz.co.hidroinfo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javassist.SerialVersionUID;
import mz.co.hidroinfo.dao.FuncionarioDao;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Center;
import org.zkoss.zul.Div;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import mz.co.hidroinfo.dao.*;
import mz.co.hidroinfo.model.Leitor;
import mz.co.hidroinfo.model.Operador;

public class Mini_MenuController extends SelectorComposer<Component>{
	
	private static final long SerialVersionUID=1L;
private int tipoMenu;
private final int OPERADOR=0;
private final int LEITOR=1;
private final int MONTANTE=2;

@Wire
private Button btn_operador;
@Wire
private Button btn_leitor;
@Wire 
private Button btn_montante;
@Wire
private Button bt_pesquisa;
@Wire
private Button bt_adiciona;
@Wire
private Textbox tb_pesquisa;
@Wire
private Center pagina;
@Wire
private Window minimenu;
private FuncionarioDao daoFuncionario;
@Wire
private Listbox lb_operador;
@Wire
private Listbox lb_leitor;
private OperadorDao operadorDao;
private LeitorDao leitorDao;

private ListModelList<Operador> operadorModel;
private ListModelList<Leitor> leitorModel;

public Mini_MenuController (){
	tipoMenu=OPERADOR;
	daoFuncionario= new FuncionarioDao();
	operadorDao = new OperadorDao();
	leitorDao = new LeitorDao();
}
@Override 
public void doAfterCompose(Component comp) throws Exception{
	super.doAfterCompose(comp);
	//setMontante();
}

@Listen ("onClick=#btn_operador")
public void setOperador(){
	setOperadorAttributes();
}
@Listen ("onClick=#btn_leitor")
public void SetLeitor(){
	setLeitorAttributes();
}
@Listen ("onClick=#btn_montante")
public void setMontante(){
	setMontanteAttributes();
	montante();
}

public void montante() {
	if(pagina.getFirstChild()!=null)
		pagina.removeChild(pagina.getFirstChild());
	 Div div=(Div)Executions.createComponents("/gestao/montanteFuncionario.zul", pagina, null);}


@Listen ("onClick = #pesquisa")
public void pesquisaCliente () {
	Messagebox.show("Clicou em pesquisa");
}

public void setOperadorAttributes (){
	tipoMenu = OPERADOR;
	btn_operador.setDisabled(true);
	btn_leitor.setDisabled(false);
	btn_montante.setDisabled(false);
	mudaCorBotao(btn_operador, btn_leitor, btn_montante);
	if(pagina.getFirstChild()!=null)
		pagina.removeChild(pagina.getFirstChild());
	lb_operador = (Listbox) Executions.createComponents("/registos/tabelaOperador.zul", pagina, null);
	actualizaTabelaOperador();
	desactivaBusca(false);
}

public void setLeitorAttributes (){
	btn_leitor.setDisabled(true);
	btn_operador.setDisabled(false);
	btn_montante.setDisabled(false);
	tipoMenu = LEITOR;
	mudaCorBotao(btn_leitor, btn_montante, btn_operador);
	if(pagina.getFirstChild()!=null)
		pagina.removeChild(pagina.getFirstChild());
	lb_leitor = (Listbox) Executions.createComponents("/registos/tabelaLeitor.zul", pagina, null);
	actualizaTabelaLeitor();
	desactivaBusca(false);
}
public void setMontanteAttributes (){
	tipoMenu=MONTANTE;
	btn_montante.setDisabled(true);
	btn_leitor.setDisabled(false);
	btn_operador.setDisabled(false);
	mudaCorBotao(btn_montante, btn_leitor, btn_operador);
	desactivaBusca(true);
}

public void actualizaTabelaOperador () {
	List<Operador> operadores = operadorDao.findAll();
	operadorModel = new ListModelList<Operador>(operadores);
	lb_operador.setModel(operadorModel);
}

public void actualizaTabelaLeitor () {
	List<Leitor> leitores = leitorDao.findAll();
	leitorModel = new ListModelList<Leitor>(leitores);
	lb_leitor.setModel(leitorModel);
}

public void mudaCorBotao (Button botaoPrimario, Button botaoSec1, Button botaoSec2) {
	botaoPrimario.setZclass("btn btn-primary");
	botaoSec1.setZclass("btn btn-default");
	botaoSec2.setZclass("btn btn-default");
}

public void desactivaBusca (boolean estado) {
	bt_adiciona.setDisabled(estado);
	bt_pesquisa.setDisabled(estado);
	tb_pesquisa.setReadonly(estado);
}


@Listen("onClick = #bt_adiciona")
public void adiciona () {
	Window win = null;
	if(tipoMenu == OPERADOR){
		win = (Window) Executions.createComponents("/registos/operador.zul", null, null);
		win.setAttribute("lb_operador", lb_operador);
	}
	
	else if(tipoMenu == LEITOR){
		win = (Window) Executions.createComponents("/registos/leitor.zul", null, null);
		win.setAttribute("lb_leitor", lb_leitor);
	}
	
	if(win!=null)
		win.doHighlighted();
}

}
