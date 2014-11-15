package mz.co.hidroinfo.controller;

import java.awt.Button;
import java.util.List;


//import jxl.biff.drawing.ComboBox;
import mz.co.hidroinfo.dao.NotificacaoDao;
import mz.co.hidroinfo.model.Cliente;
import mz.co.hidroinfo.model.Notificacao;








import mz.co.hidroinfo.model.NotificacaoColectiva;
import mz.co.hidroinfo.model.NotificacaoIndividual;

import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;



public class NotificacaoController extends SelectorComposer<Component>{
	@Wire
	private Button btn_enviar;
	@Wire
	private Combobox cbb_destinatario;
	@Wire
	private Textbox txt_mensagem;
	@Wire
	private Textbox txt_assunto;
	@Wire
	private Comboitem cbb_item_individual;
	@Wire
	private  Textbox tb_cliente;
	private Cliente clienteEscolhido;
	@Wire
	private Label lb_item_individual;
	private Notificacao novaNotificacao;
	private NotificacaoDao dao;
	@Wire
	private Window wd_notificacao;
	
	
	
	@Listen ("onClick=#btn_enviar")
	public void enviarMensagem(){
		if(cbb_destinatario.getSelectedItem().getLabel().compareToIgnoreCase("Individual")==0){
			registaIndividual();;
		}
		else
			registaColectiva();
	}
	
	
	@Listen ("onSelect = #cbb_destinatario")
	public void escolherIndividual(){
		if(cbb_destinatario.getSelectedItem().getLabel().compareToIgnoreCase("Individual")==0){
			envioIndividual();
		}
	}
	
	public void envioIndividual() {
		Window win = (Window) Executions.createComponents("/registos/escolheClienteNotificacao.zul", null, null);
		win.doHighlighted();
		win.setAttribute("tb_cliente", tb_cliente);
		win.setAttribute("clienteEscolhido", clienteEscolhido);
		win.setAttribute("wd_notificacao", wd_notificacao);
	}
	
	public void registaIndividual (){
		NotificacaoIndividual  novaNotificacao =new NotificacaoIndividual();
		dao=new NotificacaoDao();
		clienteEscolhido =(Cliente) wd_notificacao.getAttribute("clienteEscolhido");
		novaNotificacao.setAsssunto(txt_assunto.getText());
		novaNotificacao.setMensagem(txt_mensagem.getText());
		novaNotificacao.setCliente(clienteEscolhido);
		try{
			dao.create(novaNotificacao);
		}catch(Exception ex){
			Clients.showNotification("A Notificação nao foi enviada:\nContacte o administrador do Sistema");
		}
		Clients.showNotification("A Notificação foi enviada com sucesso");
	}
	
	public void registaColectiva (){
		NotificacaoColectiva  novaNotificacao =new NotificacaoColectiva();
		dao=new NotificacaoDao();
		novaNotificacao.setAsssunto(txt_assunto.getText());
		novaNotificacao.setMensagem(txt_mensagem.getText());
		try{
			dao.create(novaNotificacao);
		}catch(Exception ex){
			Clients.showNotification("A Notificação nao foi enviada:\nContacte o administrador do Sistema");
		}
		Clients.showNotification("A Notificação foi enviada com sucesso");
	}
}
