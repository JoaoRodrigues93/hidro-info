package mz.co.hidroinfo.controller;

import java.util.List;

import mz.co.hidroinfo.dao.FuncionarioDao;
import mz.co.hidroinfo.dao.LeitorDao;
import mz.co.hidroinfo.dao.OperadorDao;
import mz.co.hidroinfo.model.Leitor;
import mz.co.hidroinfo.model.Operador;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Center;
import org.zkoss.zul.Div;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class Mini_MenuController extends SelectorComposer<Component>{
	
private static final long serialVersionUID=1L;
private int tipoMenu;
private final int OPERADOR=0;
private final int LEITOR=1;
private final int MONTANTE=2;

@Wire
private Button btn_operador;
@Wire
private Row rw_dadosOperador, rw_dadosLeitor;
@Wire
private Button btn_leitor, btn_confirmarLeitor;
@Wire 
private Button btn_montante;
@Wire
private Button bt_pesquisa;
@Wire
private Button bt_adiciona, btn_confirmar, btn_limparLeitor;

@Wire
private Textbox tb_nome,tb_bi,tb_email,tb_username,
tb_password, tb_insira_password, tb_bairro;
@Wire
private Textbox tb_pesquisa;
@Wire
private Center pagina;
@Wire
private Intbox tb_nuit, tb_telefone;

/*@Wire
private Window operadorWin=null,winAlterarLeitor=null;*/
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
	setOperador();
}

@Listen ("onClick=#btn_operador")
public void setOperador(){
	setOperadorAttributes();
	actualizaTabelaOperador();
}
@Listen ("onClick=#btn_leitor")
public void SetLeitor(){
	setLeitorAttributes();
	actualizaTabelaLeitor();
}
@Listen ("onClick=#btn_montante")
public void setMontante(){
	setMontanteAttributes();
	montante();
}
@Listen ("onClick = #bt_pesquisa")
public void pesquisaFuncionario () {
	String pesquisa = tb_pesquisa.getText();
	if(tipoMenu == OPERADOR)
		actualizaOperador(pesquisa);
	else
		if(tipoMenu == LEITOR)
			actualizaLeitor(pesquisa);
}

public void montante() {
	if(pagina.getFirstChild()!=null)
		pagina.removeChild(pagina.getFirstChild());
	 Div div=(Div)Executions.createComponents("/gestao/montanteFuncionario.zul", pagina, null);}


@Listen ("onClick = #bt_pesquisa")
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
//	actualizaTabelaOperador();
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
public void actualizaOperador (String pesquisa) {
	Listbox tabelaOperador = (Listbox)pagina.getFirstChild();
	List<Operador> operadores = operadorDao.findAll(pesquisa);
	operadorModel = new ListModelList<Operador>(operadores);
	lb_operador.setModel(operadorModel);
}
public void actualizaLeitor (String pesquisa) {
	Listbox tabelaLeitor = (Listbox)pagina.getFirstChild();
	List<Leitor> leitores = leitorDao.findAll(pesquisa);
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
