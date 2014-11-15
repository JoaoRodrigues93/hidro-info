package mz.co.hidroinfo.controller;

import java.util.List;

import mz.co.hidroinfo.dao.ReclamacaoDao;
import mz.co.hidroinfo.model.Reclamacao;





import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Textbox;

public class ReclamacaoController extends SelectorComposer<Component>{

	private ListModelList<Reclamacao> modeloReclamacao;

@Wire
private Listbox lst_reclamacao;
@Wire
private Textbox txt_mensagem;
@Wire
private Label lb_assunto;
private ReclamacaoDao dao;
private Reclamacao selectedReclamacao;

public ReclamacaoController(){
	dao=new ReclamacaoDao();
}

public void doAfterCompose(Component comp){
	try {
		super.doAfterCompose(comp);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	inserirReclamacao();
}


public void inserirReclamacao (){
 List <Reclamacao> lt= 	dao.findAll();
 modeloReclamacao = new ListModelList<Reclamacao>(lt);
 lst_reclamacao.setModel(modeloReclamacao);
}
	
@Listen ("onSelect = #lst_reclamacao")
public void selecionarRec(){
	selectedReclamacao=modeloReclamacao.getSelection().iterator().next();
	//Clients.showNotification("Veja os detalhes ao lado");
	lb_assunto.setValue(selectedReclamacao.getAssunto());
	txt_mensagem.setValue(selectedReclamacao.getMensagem());
}


}
