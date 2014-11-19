package mz.co.hidroinfo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mz.co.hidroinfo.dao.FuncionarioDao;
import mz.co.hidroinfo.dao.LeitorDao;
import mz.co.hidroinfo.dao.OperadorDao;
import mz.co.hidroinfo.model.Leitor;
import mz.co.hidroinfo.model.Operador;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class FuncionarioController extends SelectorComposer<Component> {


	private static final long serialVersionUID = 1L;
	@Wire
	private Row rw_dadosOperador, rw_dadosLeitor;
	@Wire
	private Button btn_leitor, btn_confirmarLeitor;

	@Wire
	private Button btn_confirmar, btn_limparLeitor;

	@Wire
	private Textbox tb_nome,tb_bi,tb_email,tb_username,
	tb_password, tb_insira_password, tb_bairro;

	@Wire
	private Intbox tb_nuit, tb_telefone;
	@Wire
	private Window operadorWin=null,winAlterarLeitor=null;
	@Wire
	private Listbox lb_operador;
	@Wire
	private Listbox lb_leitor;
	private OperadorDao operadorDao;
	private LeitorDao leitorDao;

	public FuncionarioController(){
		new FuncionarioDao();
		operadorDao = new OperadorDao();
		leitorDao = new LeitorDao();
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
	
	@Listen ("onClick = #btn_confirmar")
	public void alterarOperador () {
		Map<String, Object> arguments = (Map)rw_dadosOperador.getValue();
		Operador op =(Operador)arguments.get("funAlterar");
		lb_operador = (Listbox) arguments.get("lb_operador");
		String pass=tb_insira_password.getText();
		List <Operador> list= operadorDao.obtemPorUsername(tb_username.getText(), tb_password.getText());
		if(!list.isEmpty() && op.getId()!=list.get(0).getId())
		{
			Clients.showNotification("Um operador ja foi cadrastado com esse username", "error", null,null,2000);
		}else{
		if(!tb_password.getText().equals(pass)){
			Clients.showNotification("o password deve ser igual", "error", null, null, 2000);
			
		}else{
		ListModelList<Operador> lista = (ListModelList)lb_operador.getModel();
		lista.remove(op);
		setValues(op);
		operadorDao.update(op);
		lista.add(0, op);
		operadorWin.detach();;
		Clients.showNotification("Dados do operador "+op.getNome()+" foram alterados");
	}}}
	
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
		tb_email.setRawValue(null);
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
		tb_email.setRawValue(null);;
		tb_nome.setText(null);
		tb_nuit.setText(null);
		}

		@Listen ("onClick=#btn_limparLeitor")
		public void limparDadosLeitor (){
			clearValuesLeitor();
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
}

