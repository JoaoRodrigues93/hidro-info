package mz.co.hidroinfo.controller;

import java.awt.Button;
import java.util.List;





//import jxl.biff.drawing.ComboBox;
import mz.co.hidroinfo.dao.NotificacaoDao;
import mz.co.hidroinfo.model.Cliente;
import mz.co.hidroinfo.model.Factura;
import mz.co.hidroinfo.model.Notificacao;








import mz.co.hidroinfo.model.NotificacaoColectiva;
import mz.co.hidroinfo.model.NotificacaoIndividual;

import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
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
	ListModelList<Notificacao> notificacaoModel;
	@Wire
	private Window wd_notificacao;
	@Wire
	private Listbox lb_notificacaoes;
	
	
	
	public NotificacaoController(){
		dao=new NotificacaoDao();
	}
	public void doAfterCompose(Component comp){
		try {
			super.doAfterCompose(comp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		tb_cliente.setVisible(false);
		preencherNotificacaoes();
	}
	
	
	
	@Listen ("onClick=#btn_enviar")
	public void enviarMensagem(){
		try{
			if(cbb_destinatario.getSelectedItem().getLabel().compareToIgnoreCase("Individual")==0){
				registaIndividual();;
			}
			else{
				registaColectiva();
			}
			}catch(NullPointerException ex){
				Clients.showNotification("Selecione os destinatarios\nSe sao 'Todos Clientes' ou\n'Individual'", "error", null, null, 2000);
			}
		preencherNotificacaoes();
	}
	
	
	@Listen ("onSelect = #cbb_destinatario")
	public void escolherIndividual(){
		if(cbb_destinatario.getSelectedItem().getLabel().compareToIgnoreCase("Individual")==0){
			tb_cliente.setVisible(true);
			envioIndividual();
		}
		else{
			tb_cliente.setVisible(false);
			tb_cliente.setText(" ");
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
			if(validarString(novaNotificacao.getAsssunto())||validarString(novaNotificacao.getMensagem())){
				dao.create(novaNotificacao);
				txt_assunto.setText("");
				txt_mensagem.setText("");
				tb_cliente.setText("");
				tb_cliente.setVisible(false);
				Clients.showNotification("A Notificação foi enviada com sucesso");
			}else{
				Clients.showNotification("Os campos de 'mensagem' e 'assunto' nao podem estar vazios", "error", null, null, 2000);
			}
		}catch(Exception ex){
			Clients.showNotification("A Notificação nao foi enviada:\nContacte o administrador do Sistema");
		}
		
	}
	
	public void registaColectiva (){
		NotificacaoColectiva  novaNotificacao =new NotificacaoColectiva();
		dao=new NotificacaoDao();
		novaNotificacao.setAsssunto(txt_assunto.getText());
		novaNotificacao.setMensagem(txt_mensagem.getText());
		try{
			if(validarString(novaNotificacao.getAsssunto())||validarString(novaNotificacao.getMensagem())){
				dao.create(novaNotificacao);
				txt_assunto.setText("");
				txt_mensagem.setText("");
				Clients.showNotification("A Notificação foi enviada com sucesso");
			}else{
				Clients.showNotification("Os campos de 'mensagem' e 'assunto' nao podem estar vazios", "error", null, null, 3000);
			}
		}catch(Exception ex){
			Clients.showNotification("A Notificação nao foi enviada:\nContacte o administrador do Sistema");
		}
	}
	
	private boolean validarString(String str){
		if(str.compareToIgnoreCase("")==0){
			return false;
		}
		else return true;
	}

	public void preencherNotificacaoes(){
		List<Notificacao> notificacoes=dao.findAll();
		notificacaoModel = new ListModelList<Notificacao>(notificacoes);
		lb_notificacaoes.setModel(notificacaoModel);
	}
}
