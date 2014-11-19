package mz.co.hidroinfo.controller;
import java.util.List;

import mz.co.hidroinfo.dao.FuncionarioDao;
import mz.co.hidroinfo.dao.OperadorDao;
import mz.co.hidroinfo.model.ClienteColectivo;
import mz.co.hidroinfo.model.Funcionario;
import mz.co.hidroinfo.model.Operador;

import org.zkoss.zhtml.Button;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;





public class ControladorOperador extends GenericForwardComposer {
@Wire
	private Listbox lst_client;
@Wire
	private Button btn_add, btn_reg;
@Wire
	private Textbox tb_nome, tb_bi, tb_email, tb_username, tb_password, tb_insira_password;
@Wire
	private Window winoperador;
@Wire
private Intbox tb_nuit, tb_telefone;
	
	Operador o; OperadorDao operadorDao;
	public void onClick$Regista(ForwardEvent e){
		o=new Operador();
		operadorDao=new OperadorDao();
		List <Operador> list= operadorDao.obtemPorUsername(tb_username.getText(), tb_password.getText());
		if(!list.isEmpty())
		{
			Clients.showNotification("Um operador ja foi cadrastado com esse username", "error", null,null,2000);
		}else{
		String pass=tb_insira_password.getText();
		if(!tb_password.getText().equals(pass)){
			Clients.showNotification("o password deve ser igual");
		}else{
		o.setBi(tb_bi.getText());
		o.setEmail(tb_email.getText());
		o.setNome(tb_nome.getText());
		o.setNuit(Integer.parseInt(tb_nuit.getText()));
		o.setTelefone(Integer.parseInt(tb_telefone.getText()));
		o.setUsername(tb_username.getText());
		o.setPassword(tb_password.getText());
		
		
				
		FuncionarioDao fv=new FuncionarioDao();
		fv.create(o);
	
		Listbox lb_operador = (Listbox)winoperador.getAttribute("lb_operador");
		ListModelList<Operador> model = (ListModelList) lb_operador.getModel();
		model.add(o);
		Clients.showNotification("Operador registado com sucesso!");
		onClick$limparCampos();
	}}}
	public void onClick$limparCampos(){
		tb_nome.setText(null);
		tb_bi.setText(null);
		tb_nuit.setText(null);
		tb_telefone.setText(null);
		tb_email.setRawValue(null);
		tb_username.setText(null);
		tb_password.setText(null);
		tb_insira_password.setText(null);
		
		
	
	}
	
	
	

	public void onClick$Apagar(ForwardEvent e){
		o = new Operador();
		
		o.setBi(tb_bi.getText());
		o.setEmail(tb_email.getText());
		o.setNome(tb_nome.getText());
		o.setNuit(tb_nuit.getValue());
		o.setTelefone(tb_telefone.getValue());
		o.setUsername(tb_username.getText());
		o.setPassword(tb_password.getText());
		
		FuncionarioDao cl=new FuncionarioDao();
		cl.delete(o);
		
		Messagebox.show("apagado");
		
	}
	
	public void onClick$Search(ForwardEvent e){
		o = new Operador();
		o.setBi(tb_bi.getText());
		String id=o.getBi();
		FuncionarioDao cl=new FuncionarioDao();		
		//cl.findById(id);
		
		Messagebox.show("apagado");
		
	}
	
	public void onClick$Update(ForwardEvent e){
		o = new Operador();
		
		
		o.setBi(tb_bi.getText());
		o.setEmail(tb_email.getText());
		o.setNome(tb_nome.getValue());
		o.setNuit(tb_nuit.getValue());
		o.setTelefone(tb_telefone.getValue());
		o.setUsername(tb_username.getText());
		o.setPassword(tb_password.getText());
		
		FuncionarioDao cl=new FuncionarioDao();	
		cl.update(o);
		
		Messagebox.show("Actualizado");
		

}
	
	
}