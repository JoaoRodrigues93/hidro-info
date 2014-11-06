package mz.co.hidroinfo.controller;



import java.awt.TextField;
import java.sql.Date;
import java.util.List;
import mz.co.hidroinfo.dao.FuncionarioDao;
import mz.co.hidroinfo.model.Funcionario;

import org.zkoss.zhtml.Button;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;






public class ControladorPesquisa extends GenericForwardComposer{
private Button btn_act;
private Combobox combo_clientePesquisa,combo_vendaSearch;
private Textbox bi;
private Listbox lst_funcionario;
Funcionario f;
FuncionarioDao fDAO;
public void onClick$Insere(ForwardEvent e) {

	
	fDAO=new FuncionarioDao();
	String id=bi.getText();	

	Funcionario func = fDAO.findByBI(id);
	String txtNome=func.getNome();
	String txtBI=func.getBi();
	int txtNuit=func.getNuit();	
	int txtTelefone=func.getTelefone();
	String txtEmail=func.getEmail();
	
	//Messagebox.show(txtNome);
	
	//Date dxt_dataNasc=(Date) clienteDAO.findById(id).getDataNascimento();
	
	Listitem lstit = new Listitem();
	

	Listcell lstcl1 = new Listcell();
	Listcell lstcl2 = new Listcell();
	Listcell lstcl3 = new Listcell();
	Listcell lstcl4 = new Listcell();
	Listcell lstcl5 = new Listcell();
	

	lstcl1.setLabel(txtNome);
	lstcl2.setLabel(txtBI);
	lstcl3.setLabel(String.valueOf(txtNuit));
	lstcl4.setLabel(String.valueOf(txtTelefone));
	lstcl5.setLabel(txtEmail);
	

	lstit.appendChild(lstcl1);
	lstit.appendChild(lstcl2);
	lstit.appendChild(lstcl3);
	lstit.appendChild(lstcl4);
	lstit.appendChild(lstcl5);
	
	
	lst_funcionario.appendChild(lstit);

	
}


public void onClick$BuscaTodosFuncionarios(ForwardEvent e) {

	f=new Funcionario();
	fDAO=new FuncionarioDao();
	String id=bi.getText();	
	
	
	List<Funcionario> l=fDAO.findAll();
	Messagebox.show(l.toString());
	


	Listitem lstit = new Listitem();
	

/*	Listcell lstcl1 = new Listcell();
	Listcell lstcl2 = new Listcell();
	Listcell lstcl3 = new Listcell();
	Listcell lstcl4 = new Listcell();
	Listcell lstcl5 = new Listcell();
	

	lstcl1.setLabel(l.get(0).toString());
	lstcl2.setLabel(l.get(1).toString());
	lstcl3.setLabel(l.get(2).toString());
	lstcl4.setLabel(l.get(3).toString());
	lstcl5.setLabel(l.get(4).toString());


	
	lstit.appendChild(lstcl1);
	lstit.appendChild(lstcl2);
	lstit.appendChild(lstcl3);
	lstit.appendChild(lstcl4);
	lstit.appendChild(lstcl5);
	
	*/
	
	lst_funcionario.appendChild(lstit);

	
}
public void onClick$Regista(ForwardEvent e) {

Executions.sendRedirect("funcionario.zul");
}
}
