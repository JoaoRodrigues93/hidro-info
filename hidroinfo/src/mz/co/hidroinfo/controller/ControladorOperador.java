package mz.co.hidroinfo.controller;
import mz.co.hidroinfo.dao.FuncionarioDao;
import mz.co.hidroinfo.model.Funcionario;
import mz.co.hidroinfo.model.Operador;

import org.zkoss.zhtml.Button;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;





public class ControladorOperador extends GenericForwardComposer {

	private Listbox lst_client;
	private Button btn_add, btn_reg;

	private Textbox nome, bi, nuit, telefone, email, username, password, insira_password;
	private Window winoperador;
	
	Operador o;
	public void onClick$Regista(ForwardEvent e){
		o=new Operador();
		
		
		o.setBi(bi.getText());
		o.setEmail(email.getText());
		o.setNome(nome.getText());
		o.setNuit(Integer.parseInt(nuit.getText()));
		o.setTelefone(Integer.parseInt(telefone.getText()));
		o.setUsername(username.getText());
		o.setPassword(password.getText());
		
		
				
		FuncionarioDao fv=new FuncionarioDao();
		fv.create(o);
	
		Listbox lb_operador = (Listbox)winoperador.getAttribute("lb_operador");
		ListModelList<Operador> model = (ListModelList) lb_operador.getModel();
		model.add(o);
		Clients.showNotification("Operador registado com sucesso!");
		onClick$limparCampos();
	}
	public void onClick$limparCampos(){
		nome.setText(null);
		bi.setText(null);
		nuit.setText(null);
		telefone.setText(null);
		email.setText(null);
		username.setText(null);
		password.setText(null);
		insira_password.setText(null);
		
		
	
	}
	
	
	

	public void onClick$Apagar(ForwardEvent e){
		o = new Operador();
		
		o.setBi(bi.getText());
		o.setEmail(email.getText());
		o.setNome(nome.getText());
		o.setNuit(Integer.parseInt(nuit.getValue()));
		o.setTelefone(Integer.parseInt(telefone.getValue()));
		o.setUsername(username.getText());
		o.setPassword(password.getText());
		
		FuncionarioDao cl=new FuncionarioDao();
		cl.delete(o);
		
		Messagebox.show("apagado");
		
	}
	
	public void onClick$Search(ForwardEvent e){
		o = new Operador();
		o.setBi(bi.getText());
		String id=o.getBi();
		FuncionarioDao cl=new FuncionarioDao();		
		//cl.findById(id);
		
		Messagebox.show("apagado");
		
	}
	
	public void onClick$Update(ForwardEvent e){
		o = new Operador();
		
		
		o.setBi(bi.getText());
		o.setEmail(email.getText());
		o.setNome(nome.getValue());
		o.setNuit(Integer.parseInt(nuit.getValue()));
		o.setTelefone(Integer.parseInt(telefone.getValue()));
		o.setUsername(username.getText());
		o.setPassword(password.getText());
		
		FuncionarioDao cl=new FuncionarioDao();	
		cl.update(o);
		
		Messagebox.show("Actualizado");
		

}
}