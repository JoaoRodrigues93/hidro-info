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
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Center;
import org.zkoss.zul.Div;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import mz.co.hidroinfo.dao.*;
import mz.co.hidroinfo.model.ClienteColectivo;
import mz.co.hidroinfo.model.Contacto;
import mz.co.hidroinfo.model.Endereco;
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

@Wire
private Window operadorWin=null,winAlterarLeitor=null;
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
@Listen ("onOperadorDelete = #lb_operador")
public void onClickApagar (ForwardEvent event) {
	System.out.println("Apagando um funcionario operador");
	Button bt_apagar =(Button) event.getOrigin().getTarget();
	Listcell celula = (Listcell)bt_apagar.getParent().getParent();
	Listitem item = (Listitem)celula.getParent();
	Operador operadorApagar = (Operador)item.getValue();
	lb_operador.removeChild(item);
	String nome = operadorApagar.getNome();
	operadorDao.delete(operadorApagar);
	System.out.println("Apagando um funcionario operador");
	Clients.showNotification("Os dados do funcionario "+nome+" foram apagados");
}	
@Listen ("onLeitorDelete = #lb_leitor")
public void onClick (ForwardEvent event) {
	System.out.println("Apagando um funcionario leitor");
	Button bt_apagar =(Button) event.getOrigin().getTarget();
	Listcell celula = (Listcell)bt_apagar.getParent().getParent();
	Listitem item = (Listitem)celula.getParent();
	Leitor leitorApagar = (Leitor)item.getValue();
	lb_leitor.removeChild(item);
	String nome = leitorApagar.getNome();
	leitorDao.delete(leitorApagar);
	System.out.println("Apagando um funcionario leitor");
	Clients.showNotification("Os dados do funcionario "+nome+" foram apagados");
}

@Listen ("onOperadorUpdate = #lb_operador")
public void onClickAlterar (ForwardEvent event){
	
	Button btn_confirmar =(Button) event.getOrigin().getTarget();
	Listcell celula = (Listcell)btn_confirmar.getParent().getParent();
	Listitem itemAlterar = (Listitem)celula.getParent();
	Operador op = (Operador)itemAlterar.getValue();
	Map<String, Object> arguments = new HashMap<String, Object>(); 
	arguments.put("funAlterar",op);
	arguments.put("lb_operador",lb_operador);
	Window win = (Window)Executions.createComponents("/registos/alteracaoOperador.zul", null, arguments);
	win.doHighlighted();
}

@Listen ("onClick = #btn_confirmar")
public void alterarOperador () {
	String pass=tb_insira_password.getText();
	if(!tb_password.getText().equals(pass)){
		Clients.showNotification("o password deve ser igual");
	}else{
	Map<String, Object> arguments = (Map)rw_dadosOperador.getValue();
	Operador op =(Operador)arguments.get("funAlterar");
	lb_operador = (Listbox) arguments.get("lb_operador");
	ListModelList<Operador> lista = (ListModelList)lb_operador.getModel();
	lista.remove(op);
	setValues(op);
	operadorDao.update(op);
	lista.add(0, op);
	operadorWin.detach();;
	Clients.showNotification("Dados do operador "+op.getNome()+" foram alterados");
}}
@Listen ("onLeitorUpdate = #lb_leitor")
public void onClickAlterarLeitor (ForwardEvent event){
	Button btn_confirmarLeitor =(Button) event.getOrigin().getTarget();
	Listcell celula = (Listcell)btn_confirmarLeitor.getParent().getParent();
	Listitem itemAlterar = (Listitem)celula.getParent();
	Leitor op = (Leitor)itemAlterar.getValue();
	Map<String, Object> arguments = new HashMap<String, Object>(); 
	arguments.put("funAlterar",op);
	arguments.put("lb_leitor",lb_leitor);
	Window win = (Window)Executions.createComponents("/registos/alteracaoLeitor.zul", null, arguments);
	win.doHighlighted();
}

@Listen ("onClick = #btn_confirmarLeitor")
public void alterarleitor () {
	
	Map<String, Object> arguments = (Map)rw_dadosLeitor.getValue();
	Leitor leitor =(Leitor)arguments.get("funAlterar");
	lb_leitor = (Listbox) arguments.get("lb_leitor");
	ListModelList<Leitor> lista = (ListModelList)lb_leitor.getModel();
	lista.remove(leitor);
	setValuesLeitor(leitor);
	leitorDao.update(leitor);
	lista.add(0, leitor);
	winAlterarLeitor.detach();;
	Clients.showNotification("Dados do leitor"+leitor.getNome()+" foram alterados");
}
public void setValues (Operador operador){
operador.setBi(tb_bi.getText());
operador.setEmail(tb_email.getText());
operador.setNome(tb_nome.getText());
operador.setNuit(Integer.valueOf(tb_nuit.getText()));
operador.setTelefone(Integer.valueOf(tb_telefone.getText()));
operador.setUsername(tb_username.getText());
operador.setPassword(tb_password.getText());
}
public void clearValues (){
tb_username.setText(null);
tb_telefone.setText(null);
tb_password.setText(null);
tb_bi.setText(null);
tb_email.setText(null);
tb_nome.setText(null);
tb_nuit.setText(null);
tb_insira_password.setText(null);}

@Listen ("onClick=#btn_limpar")
public void limparDadosOperador (){
	clearValues();
}
public void setValuesLeitor (Leitor leitor){
leitor.setBi(tb_bi.getText());
leitor.setEmail(tb_email.getText());
leitor.setNome(tb_nome.getText());
leitor.setNuit(Integer.valueOf(tb_nuit.getText()));
leitor.setTelefone(Integer.valueOf(tb_telefone.getText()));
leitor.setBairro(tb_bairro.getText());

}
public void clearValuesLeitor (){
tb_bairro.setText(null);
tb_telefone.setText(null);
tb_bi.setText(null);
tb_email.setText(null);
tb_nome.setText(null);
tb_nuit.setText(null);
}

@Listen ("onClick=#btn_limparLeitor")
public void limparDadosLeitor (){
	clearValuesLeitor();
}

}
