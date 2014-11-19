package mz.co.hidroinfo.controller;

import java.util.HashMap;
import java.util.Iterator;
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
import mz.co.hidroinfo.model.*;

public class Mini_MenuController extends SelectorComposer<Component> {

	private static final long SerialVersionUID = 1L;
	private int tipoMenu;
	private final int OPERADOR = 0;
	private final int LEITOR = 1;
	private final int MONTANTE = 2;

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
	private Textbox tb_nome, tb_bi, tb_email, tb_username, tb_password,
			tb_insira_password, tb_bairro;
	@Wire
	private Textbox tb_pesquisa;
	@Wire
	private Center pagina;
	@Wire
	private Intbox tb_nuit, tb_telefone;

	@Wire
	private Window operadorWin = null, winAlterarLeitor = null;
	private FuncionarioDao daoFuncionario;
	private OperadorDao operadorDao;
	private LeitorDao leitorDao;
	private Operador op;

	private ListModelList<Operador> operadorModel;
	private ListModelList<Leitor> leitorModel;

	public Mini_MenuController() {
		tipoMenu = LEITOR;
		daoFuncionario = new FuncionarioDao();
		operadorDao = new OperadorDao();
		leitorDao = new LeitorDao();
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		setOperador();
	}

	@Listen("onClick=#btn_operador")
	public void setOperador() {
		setTabelaOperador();
		setOperadorAttributes();
		actualizaOperador("");
	}

	@Listen("onClick=#btn_leitor")
	public void SetLeitor() {
setTabelaLeitor();
		//actualizaLeitor("");
		setLeitorAttributes();
	}

	@Listen("onClick=#btn_montante")
	public void setMontante() {
		setMontanteAttributes();
		montante();
	}

	@Listen("onClick = #bt_pesquisa")
	public void pesquisaFuncionario() {
		String pesquisa = tb_pesquisa.getText();
		if (tipoMenu == OPERADOR)
			actualizaOperador(pesquisa);
		else if (tipoMenu == LEITOR)
			actualizaLeitor(pesquisa);
	}

	public void montante() {
		if (pagina.getFirstChild() != null)
			pagina.removeChild(pagina.getFirstChild());
		Div div = (Div) Executions.createComponents(
				"/gestao/montanteFuncionario.zul", pagina, null);
	}

	public void setTabelaLeitor() {
		Listbox tabelaLeitor;
		Component child = pagina.getFirstChild();
		if (child != null) {
			pagina.removeChild(child);
		}
		tabelaLeitor = (Listbox) Executions.createComponents(
				"/registos/tabelaLeitor.zul", pagina, null);

	}
	public void setTabelaOperador(){
		Listbox tabelaOperador;
		Component child = pagina.getFirstChild();
		if (child != null) {
			pagina.removeChild(child);
		}
		tabelaOperador = (Listbox) Executions.createComponents(
				"/registos/tabelaOperador.zul", pagina, null);
	
	}


	@Listen("onClick = #bt_adiciona")
	public void adiciona() {
		Window win = null;
		Map<String, Object> arguments = new HashMap<String, Object>();
		if (tipoMenu == OPERADOR) {
				arguments.put("tabelaOperador", pagina.getFirstChild());
				win = (Window) Executions.createComponents("/registos/operador.zul", null,arguments);
				}
				else if (tipoMenu == LEITOR){
				arguments.put("tabelaLeitor", pagina.getFirstChild());
				win = (Window) Executions.createComponents("/registos/leitor.zul", null,arguments);
				}
				win.doHighlighted();
	}

	public void setOperadorAttributes() {
		tipoMenu = OPERADOR;
		btn_operador.setDisabled(true);
		btn_leitor.setDisabled(false);
		btn_montante.setDisabled(false);
		mudaCorBotao(btn_operador, btn_leitor, btn_montante);
		desactivaBusca(false);

	}

	public void setLeitorAttributes() {
		btn_leitor.setDisabled(true);
		btn_operador.setDisabled(false);
		btn_montante.setDisabled(false);
		tipoMenu = LEITOR;
		mudaCorBotao(btn_leitor, btn_montante, btn_operador);
		desactivaBusca(false);
	}

	public void setMontanteAttributes() {
		tipoMenu = MONTANTE;
		btn_montante.setDisabled(true);
		btn_leitor.setDisabled(false);
		btn_operador.setDisabled(false);
		mudaCorBotao(btn_montante, btn_leitor, btn_operador);
		desactivaBusca(true);

	}

	public void actualizaOperador(String pesquisa) {
		Listbox tabelaOperador = (Listbox) pagina.getFirstChild();
		List<Operador> operadores = operadorDao.findAll(pesquisa);
		operadorModel = new ListModelList<Operador>(operadores);
		tabelaOperador.setModel(operadorModel);
	}

	public void actualizaLeitor(String pesquisa) {
		Listbox tabelaLeitor = (Listbox) pagina.getFirstChild();
		List<Leitor> leitores = leitorDao.findAll(pesquisa);
		leitorModel = new ListModelList<Leitor>(leitores);
		tabelaLeitor.setModel(leitorModel);
	}

	public void mudaCorBotao(Button botaoPrimario, Button botaoSec1,
			Button botaoSec2) {
		botaoPrimario.setZclass("btn btn-primary");
		botaoSec1.setZclass("btn btn-default");
		botaoSec2.setZclass("btn btn-default");
	}

	public void desactivaBusca(boolean estado) {
		bt_adiciona.setDisabled(estado);
		bt_pesquisa.setDisabled(estado);
		tb_pesquisa.setReadonly(estado);
	}

	

}
