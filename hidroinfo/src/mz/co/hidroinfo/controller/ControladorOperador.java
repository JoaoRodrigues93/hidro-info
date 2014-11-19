package mz.co.hidroinfo.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import mz.co.hidroinfo.dao.FuncionarioDao;
import mz.co.hidroinfo.dao.LeitorDao;
import mz.co.hidroinfo.dao.OperadorDao;
import mz.co.hidroinfo.model.ClienteColectivo;
import mz.co.hidroinfo.model.Funcionario;
import mz.co.hidroinfo.model.Leitor;
import mz.co.hidroinfo.model.Operador;

import org.zkoss.zhtml.Button;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ControladorOperador extends SelectorComposer<Component> {
	@Wire
	private Listbox lst_client;
	@Wire
	private Button btn_add, btn_reg;
	@Wire
	private Intbox tb_nuit, tb_telefone;
	@Wire
	private Window winoperador;
	@Wire
	private Intbox nuit, telefone;
	@Wire
	private Row rw_dadosOperador, rw_dadosFuncionario;
	@Wire
	private Textbox tb_nome, tb_bi, tb_email, tb_username, tb_password,
			tb_insira_password, tb_bairro;
	@Wire
	private Listbox lb_operador;
	@Wire
	private Listbox lb_leitor;
	@Wire
	private Window operadorWin = null, winAlterarLeitor = null;
	Operador o;
	OperadorDao operadorDao;
	private ListModelList<Operador> operadorModel;
	private FuncionarioDao daoFuncionario;

	public ControladorOperador(){
		daoFuncionario = new FuncionarioDao();
		operadorDao = new OperadorDao();
	}
	
	@Listen ("onClick=#btn_add")
	public void registarOperador() {
		o = new Operador();
		
		List<Operador> list = operadorDao.obtemPorUsername(tb_username.getText(),
				tb_password.getText());
		if (!list.isEmpty()) {
			Clients.showNotification(
					"Um operador ja foi cadrastado com esse username", "error",
					null, null, 2000);
		} else {
			String pass = tb_insira_password.getText();
			if (!tb_password.getText().equals(pass)) {
				Clients.showNotification("o password deve ser igual");
			} else {
				setValues(o);
				operadorDao.create(o);
clearValues();
Clients.showNotification("Operador registado");
Map<String, Object> arguments = (Map)rw_dadosFuncionario.getValue();
lb_leitor = (Listbox)arguments.get("tabelaOperador");
ListModelList<Operador> lista = (ListModelList)lb_operador.getModel();
lista.add(0, o);
			}
		}
	}




	@Listen("onOperadorDelete = #lb_operador")
	public void onClickApagar(ForwardEvent event) {
		System.out.println("Apagando um funcionario operador");
		Button bt_apagar = (Button) event.getOrigin().getTarget();
		Listcell celula = (Listcell) bt_apagar.getParent().getParent();
		Listitem item = (Listitem) celula.getParent();
		Operador operadorApagar = (Operador) item.getValue();
		lb_operador.removeChild(item);
		String nome = operadorApagar.getNome();
		operadorDao.delete(operadorApagar);
		System.out.println("Apagando um funcionario operador");
		Clients.showNotification("Os dados do funcionario " + nome
				+ " foram apagados");
	}
	@Listen("onOperadorUpdate = #lb_operador")
	public void onClickAlterar(ForwardEvent event) {

		Button btn_confirmar = (Button) event.getOrigin().getTarget();
		Listcell celula = (Listcell) btn_confirmar.getParent().getParent();
		Listitem itemAlterar = (Listitem) celula.getParent();
		Operador op = (Operador) itemAlterar.getValue();
		Map<String, Object> arguments = new HashMap<String, Object>();
		arguments.put("funAlterar", op);
		arguments.put("lb_operador", lb_operador);
		Window win = (Window) Executions.createComponents(
				"/registos/alteracaoOperador.zul", null, arguments);
		win.doHighlighted();
	}
	@Listen("onClick = #btn_confirmar")
	public void alterarOperador() {

		String pass = tb_password.getText();
		String user=tb_username.getText();
		List<Operador> lista = operadorDao.obtemPorUsername(user,pass);
		for (Iterator iterator = lista.iterator(); iterator.hasNext();) {
			Operador operador = (Operador) iterator.next();
			String password = operador.getPassword();
			if (!pass.equals(password)) {
				Clients.showNotification(
						"Nao corresponde ao antigo password, introduza novamente",
						"error", tb_password, null, 5000);
			} else {
			

						Map<String, Object> arguments = (Map) rw_dadosOperador
								.getValue();
						Operador op = (Operador) arguments.get("funAlterar");
						lb_operador = (Listbox) arguments.get("lb_operador");
						ListModelList<Operador> listar = (ListModelList) lb_operador
								.getModel();
						listar.remove(op);
						setValues(op);
						operadorDao.update(op);
						listar.add(0, op);
						operadorWin.detach();
						;
						Clients.showNotification("Dados do operador "
								+ op.getNome() + " foram alterados");
					}

				}
			}
		
	public void actualizaTabelaOperador() {
		List<Operador> operadores = operadorDao.findAll();
		operadorModel = new ListModelList<Operador>(operadores);
		lb_operador.setModel(operadorModel);
	}

	public void setValues(Operador operador) {
		operador.setBi(tb_bi.getText());
		operador.setEmail(tb_email.getText());
		operador.setNome(tb_nome.getText());
		operador.setNuit(Integer.valueOf(tb_nuit.getText()));
		operador.setTelefone(Integer.valueOf(tb_telefone.getText()));
		operador.setUsername(tb_username.getText());
		operador.setPassword(tb_insira_password.getText());

	}

	public void clearValues() {
		tb_username.setText(null);
		tb_telefone.setText(null);
		tb_password.setText(null);
		tb_bi.setText(null);
		tb_email.setRawValue(null);
		tb_nome.setText(null);
		tb_nuit.setText(null);
		tb_insira_password.setText(null);
	}

	@Listen("onClick=#btn_limpar")
	public void limparDadosOperador() {
		clearValues();
	}
}