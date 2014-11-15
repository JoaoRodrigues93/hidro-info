package mz.co.hidroinfo.controller;

import java.awt.Button;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
//import java.awt.List;












//import org.jfree.text.TextBox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import lib.SMS.sendsms;
import mz.co.hidroinfo.dao.AvisoDeCorteDao;
import mz.co.hidroinfo.dao.ClienteDao;
import mz.co.hidroinfo.dao.NotificacaoDao;
import mz.co.hidroinfo.model.Cliente;
import mz.co.hidroinfo.model.ClienteColectivo;
import mz.co.hidroinfo.model.ClienteDomestico;
import mz.co.hidroinfo.model.Factura;
import mz.co.hidroinfo.model.MensagemAviso;
import mz.co.hidroinfo.model.MensagemAvisoColectiva;
import mz.co.hidroinfo.model.MensagemAvisoIndividual;
import mz.co.hidroinfo.model.NotificacaoIndividual;




public class AvisoDeCorteController extends SelectorComposer{
	private NotificacaoDao notDao;
	private ClienteDao clienteDao;
	private AvisoDeCorteDao avisoDao;
	private MensagemAviso mensagem;
	ListModelList<MensagemAvisoIndividual> destinatarioModel;
	private Cliente clienteEscolhido;
	private MensagemAvisoColectiva avisoColectivo;
	private MensagemAvisoIndividual avisoIndividual;
	private sendsms sms;
	
	@Wire
	private Textbox txt_mensagem;
	@Wire
	private Button btn_enviar;
	@Wire
	private Listbox lb_destinatarios;
	@Wire
	private Textbox tb_cliente;
	@Wire
	private Window wd_aviso;
	@Wire
	private Combobox cbb_destinatario;
	
	
	
	public AvisoDeCorteController(){
		clienteDao=new ClienteDao();
		notDao=new NotificacaoDao();
		avisoDao=new AvisoDeCorteDao();
		sms=new sendsms();
	}
	
	
	
	
	@Listen ("onClick=#btn_enviar")
	public void enviar(){
		int i=0;
		destinatarioModel=new ListModelList<MensagemAvisoIndividual>();
		destinatarioModel.addAll((Collection<? extends MensagemAvisoIndividual>) lb_destinatarios.getListModel());
		List<MensagemAvisoIndividual> avisos=destinatarioModel.getInnerList();
		List<MensagemAvisoIndividual> aux=destinatarioModel.getInnerList();
		for (Iterator iterator = avisos.iterator(); iterator.hasNext();) {
			
			MensagemAvisoIndividual mensagemAvisoIndividual = (MensagemAvisoIndividual) iterator
				.next();
			Cliente ce=mensagemAvisoIndividual.getCliente();
			if (ce instanceof ClienteColectivo) {
				Clients.showNotification("o nome"+ ((ClienteColectivo) ce).getNome());
			}
			if (ce instanceof ClienteDomestico) {
				Clients.showNotification("o nome"+ ((ClienteDomestico) ce).getNome());
			}
			mensagemAvisoIndividual.setMensagem(txt_mensagem.getText());
			mandarMensagem(mensagemAvisoIndividual);
			destinatarioModel.getElementAt(i).setConfirmacao(true);
			lb_destinatarios.setModel(destinatarioModel);
			
			i++;
		}
		lb_destinatarios.setModel(destinatarioModel);
		Clients.showNotification("Mensagens enviadas");
	}
	
	
	@Listen ("onSelect = #cbb_destinatario")
	public void selecionarDestinatarios(){
		if(cbb_destinatario.getSelectedItem().getLabel().compareToIgnoreCase("Individual")==0){
			
			preencherIndividual();
		}
		else
			preencherTodos();
	}
	
	
	public void preencherIndividual(){
		clienteEscolhido=new Cliente();
		
		Window win = (Window) Executions.createComponents("/registos/escolheClienteAviso.zul", null, null);
		win.doHighlighted();
		win.setAttribute("destinatarioModel", destinatarioModel);
		win.setAttribute("tb_cliente", tb_cliente);
		win.setAttribute("clienteEscolhido", clienteEscolhido);
		win.setAttribute("wd_aviso", wd_aviso);
		win.setAttribute("lb_destinatarios", lb_destinatarios);
	}
	
	public void preencherTodos(){
		List<Cliente> clientes = clienteDao.findAll();
		List<MensagemAvisoIndividual> mensagens=new ArrayList<MensagemAvisoIndividual>();
		for (Iterator iterator = clientes.iterator(); iterator.hasNext();) {
			Cliente cliente = (Cliente) iterator.next();
			MensagemAvisoIndividual mensagem = new MensagemAvisoIndividual();
			mensagem.setCliente(cliente);
			mensagens.add(mensagem);
		}
		destinatarioModel=new ListModelList<MensagemAvisoIndividual>(mensagens);
		lb_destinatarios.setModel(destinatarioModel);
	}
	
	
	public void mandarMensagem(MensagemAvisoIndividual mensagemAvisoIndividual){
		sendsms.mandarSMS(mensagemAvisoIndividual.getCliente().getContacto().getTelefone(), mensagemAvisoIndividual.getMensagem());
		avisoDao.create(mensagemAvisoIndividual);
	}
	
}
