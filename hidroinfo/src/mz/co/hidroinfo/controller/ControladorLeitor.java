package mz.co.hidroinfo.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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




public class ControladorLeitor extends SelectorComposer<Component> {
	@Wire
	private Row rw_dadosLeitor, rw_dadosFuncionario;
	@Wire
	private Button bt_adiciona, btn_confirmar, btn_limparLeitor;

	@Wire
	private Textbox tb_nome, tb_bi, tb_email, tb_username, tb_password,
			tb_insira_password, tb_bairro;
	
	@Wire
	private Center pagina;
	@Wire
	private Intbox tb_nuit, tb_telefone;
@Wire
	private Button btn_add, btn_reg;
@Wire
	private Window winAlterarLeitor = null,winleitor;
	private Leitor l;
	private FuncionarioDao daoFuncionario;
	@Wire
	private Listbox lb_operador;
	@Wire
	private Listbox lb_leitor;
	private LeitorDao leitorDao;
	private ListModelList<Leitor> leitorModel;
	
	 public ControladorLeitor() {
		daoFuncionario = new FuncionarioDao();
		leitorDao = new LeitorDao();
	}
	
	@Listen ("onClick=#tbn_add")
	public void Regista(){
		System.out.println("Registando um funcionario....\n");
		l = new Leitor();
		setValuesLeitor(l);
		leitorDao.create(l);
		clearValuesLeitor();
		Clients.showNotification("Leitor registado");
		Map<String, Object> arguments = (Map)rw_dadosFuncionario.getValue();
		lb_leitor = (Listbox)arguments.get("tabelaLeitor");
		ListModelList<Leitor> lista = (ListModelList)lb_leitor.getModel();
		lista.add(0, l);
	}

	public void actualizaTabelaLeitor() {
		List<Leitor> leitores = leitorDao.findAll();
		leitorModel = new ListModelList<Leitor>(leitores);
		lb_leitor.setModel(leitorModel);
	}
	
	@Listen("onLeitorUpdate = #lb_leitor")
	public void onClickAlterarLeitor(ForwardEvent event) {
		Button btn_confirmarLeitor = (Button) event.getOrigin().getTarget();
		Listcell celula = (Listcell) btn_confirmarLeitor.getParent()
				.getParent();
		Listitem itemAlterar = (Listitem) celula.getParent();
		Leitor op = (Leitor) itemAlterar.getValue();
		Map<String, Object> arguments = new HashMap<String, Object>();
		arguments.put("funAlterar", op);
		arguments.put("lb_leitor", lb_leitor);
		Window win = (Window) Executions.createComponents(
				"/registos/alteracaoLeitor.zul", null, arguments);
		win.doHighlighted();
	}

	@Listen("onClick = #btn_confirmarLeitor")
	public void alterarleitor() {

		Map<String, Object> arguments = (Map) rw_dadosLeitor.getValue();
		Leitor leitor = (Leitor) arguments.get("funAlterar");
		lb_leitor = (Listbox) arguments.get("lb_leitor");
		ListModelList<Leitor> lista = (ListModelList) lb_leitor.getModel();
		lista.remove(leitor);
		setValuesLeitor(leitor);
		leitorDao.update(leitor);
		lista.add(0, leitor);
		winAlterarLeitor.detach();
		;
		Clients.showNotification("Dados do leitor" + leitor.getNome()
				+ " foram alterados");
	}

	@Listen("onLeitorDelete = #lb_leitor")
	public void onClick(ForwardEvent event) {
		System.out.println("Apagando um funcionario leitor");
		Button bt_apagar = (Button) event.getOrigin().getTarget();
		Listcell celula = (Listcell) bt_apagar.getParent().getParent();
		Listitem item = (Listitem) celula.getParent();
		Leitor leitorApagar = (Leitor) item.getValue();
		lb_leitor.removeChild(item);
		String nome = leitorApagar.getNome();
		leitorDao.delete(leitorApagar);
		System.out.println("Apagando um funcionario leitor");
		Clients.showNotification("Os dados do funcionario " + nome
				+ " foram apagados");
	}
	@Listen("onClick=#btn_limparLeitor")
	public void limparDadosLeitor() {
		clearValuesLeitor();
	}
	
	public void clearValuesLeitor() {
		tb_bairro.setText(null);
		tb_telefone.setText(null);
		tb_bi.setText(null);
		tb_email.setRawValue(null);

		tb_nome.setText(null);
		tb_nuit.setText(null);
	}

	public void setValuesLeitor(Leitor leitor) {
		leitor.setBi(tb_bi.getText());
		leitor.setEmail(tb_email.getText());
		leitor.setNome(tb_nome.getText());
		leitor.setNuit(Integer.valueOf(tb_nuit.getText()));
		leitor.setTelefone(Integer.valueOf(tb_telefone.getText()));
		leitor.setBairro(tb_bairro.getText());

	}


}
