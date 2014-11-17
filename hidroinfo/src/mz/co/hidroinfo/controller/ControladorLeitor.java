package mz.co.hidroinfo.controller;






import mz.co.hidroinfo.dao.FuncionarioDao;
import mz.co.hidroinfo.model.Funcionario;
import mz.co.hidroinfo.model.Leitor;
import mz.co.hidroinfo.model.Operador;

import org.zkoss.zhtml.Button;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;





public class ControladorLeitor extends GenericForwardComposer {
@Wire
	private Listbox lst_client;
@Wire
private Intbox nuit, telefone;
@Wire
	private Button btn_add, btn_reg;
@Wire
	private Textbox nome, bi, email, bairro;
@Wire
	private Window winleitor;
	Leitor l;
	public void onClick$Regista(ForwardEvent e){
		l=new Leitor();
		
		
		l.setBi(bi.getText());
		l.setEmail(email.getText());
		l.setNome(nome.getText());
		l.setNuit(Integer.parseInt(nuit.getText()));
		l.setTelefone(Integer.parseInt(telefone.getText()));
		l.setBairro(bairro.getText());
		
				
		FuncionarioDao fv=new FuncionarioDao();
		fv.create(l);
	
		Listbox lb_leitor = (Listbox)winleitor.getAttribute("lb_leitor");
		ListModelList<Leitor> model = (ListModelList) lb_leitor.getModel();
		model.add(l);
		Clients.showNotification("Operador registado com sucesso!");
		onClick$limparCampos();
	}
	public void onClick$limparCampos(){
		nome.setText(null);
		bi.setText(null);
		nuit.setText(null);
		telefone.setText(null);
		email.setText(null);
		bairro.setText(null);
		
		
	
	}
	
	
	

	public void onClick$Apagar(ForwardEvent e){
		l = new Leitor();
		
		l.setBi(bi.getText());
		l.setEmail(email.getText());
		l.setNome(nome.getValue());
		l.setNuit(Integer.valueOf(nuit.getValue()));
		l.setTelefone(Integer.valueOf(telefone.getValue()));
		l.setBairro(bairro.getValue());
		
		FuncionarioDao cl=new FuncionarioDao();
		cl.delete(l);
		
		Messagebox.show("apagado");
		
	}
	
	public void onClick$Search(ForwardEvent e){
		l = new Leitor();
		
		l.setBi(bi.getText());
		String id=l.getBi();
		FuncionarioDao cl=new FuncionarioDao();		
		//cl.findById(id);
		
		Messagebox.show("apagado");
		
	}
	
	public void onClick$Update(ForwardEvent e){
		l = new Leitor();
		
		
		l.setBi(bi.getText());
		l.setEmail(email.getText());
		l.setNome(nome.getValue());
		l.setNuit(Integer.valueOf(nuit.getValue()));
		l.setTelefone(Integer.valueOf(telefone.getValue()));
		l.setBairro(bairro.getValue());
		
		FuncionarioDao cl=new FuncionarioDao();	
		cl.update(l);
		
		Messagebox.show("Actudalizado");
		
	}
}
