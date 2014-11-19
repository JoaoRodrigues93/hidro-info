package mz.co.hidroinfo.controller;






import mz.co.hidroinfo.dao.FuncionarioDao;
import mz.co.hidroinfo.model.Leitor;

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
private Intbox tb_nuit, tb_telefone;
@Wire
	private Button btn_add, btn_reg;
@Wire
	private Textbox tb_nome, tb_bi, tb_email, tb_bairro;
@Wire
	private Window winleitor;
	Leitor l;
	public void onClick$Regista(ForwardEvent e){
		l=new Leitor();
		
		
		l.setBi(tb_bi.getText());
		l.setEmail(tb_email.getText());
		l.setNome(tb_nome.getText());
		l.setNuit(Integer.parseInt(tb_nuit.getText()));
		l.setTelefone(Integer.parseInt(tb_telefone.getText()));
		l.setBairro(tb_bairro.getText());
		
				
		FuncionarioDao fv=new FuncionarioDao();
		fv.create(l);
	
		Listbox lb_leitor = (Listbox)winleitor.getAttribute("lb_leitor");
		ListModelList<Leitor> model = (ListModelList) lb_leitor.getModel();
		model.add(l);
		Clients.showNotification("Operador registado com sucesso!");
		onClick$limparCampos();
	}
	public void onClick$limparCampos(){
		tb_nome.setText(null);
		tb_bi.setText(null);
		tb_nuit.setText(null);
		tb_telefone.setText(null);
		tb_email.setRawValue(null);
		tb_bairro.setText(null);
		
		
	
	}
	
	
	

	public void onClick$Apagar(ForwardEvent e){
		l = new Leitor();
		
		l.setBi(tb_bi.getText());
		l.setEmail(tb_email.getText());
		l.setNome(tb_nome.getValue());
		l.setNuit(Integer.valueOf(tb_nuit.getValue()));
		l.setTelefone(Integer.valueOf(tb_telefone.getValue()));
		l.setBairro(tb_bairro.getValue());
		
		FuncionarioDao cl=new FuncionarioDao();
		cl.delete(l);
		
		Messagebox.show("apagado");
		
	}
	
	public void onClick$Search(ForwardEvent e){
		l = new Leitor();
		
		l.setBi(tb_bi.getText());
		String id=l.getBi();
		FuncionarioDao cl=new FuncionarioDao();		
		//cl.findById(id);
		
		Messagebox.show("apagado");
		
	}
	
	public void onClick$Update(ForwardEvent e){
		l = new Leitor();
		
		
		l.setBi(tb_bi.getText());
		l.setEmail(tb_email.getText());
		l.setNome(tb_nome.getValue());
		l.setNuit(Integer.valueOf(tb_nuit.getValue()));
		l.setTelefone(Integer.valueOf(tb_telefone.getValue()));
		l.setBairro(tb_bairro.getValue());
		
		FuncionarioDao cl=new FuncionarioDao();	
		cl.update(l);
		
		Messagebox.show("Actudalizado");
		
	}
}
