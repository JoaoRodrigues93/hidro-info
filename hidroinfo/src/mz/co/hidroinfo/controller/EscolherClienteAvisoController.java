package mz.co.hidroinfo.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mz.co.hidroinfo.dao.ClienteDao;
import mz.co.hidroinfo.model.Cliente;
import mz.co.hidroinfo.model.ClienteColectivo;
import mz.co.hidroinfo.model.ClienteDomestico;
import mz.co.hidroinfo.model.MensagemAvisoIndividual;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class EscolherClienteAvisoController extends SelectorComposer<Component> {
	
	@Wire
	private Button bt_pesquisa;
	@Wire
	private Textbox tb_pesquisa;
	@Wire
	private Listbox lb_cliente;
	private ClienteDao dao;
	ListModelList<Cliente> clienteModel;
	private Cliente selectedCliente;
	@Wire
	private Window win_escolherAviso;
	
	@Wire
	private Row rw_dadosContador;
	
	public EscolherClienteAvisoController (){
		dao = new ClienteDao();
	}
	
	@Override
	public void doAfterCompose(Component comp) throws Exception{
		super.doAfterCompose(comp);
		pesquisaClientes();
	}


	private void pesquisaClientes() {
		List<Cliente> clientes = dao.findAll();
		clienteModel = new ListModelList<Cliente>(clientes);
		lb_cliente.setModel(clienteModel);
	}
	
	@Listen ("onClienteEscolhe = #lb_cliente")
	public void escolheCliente(ForwardEvent event){
		
		Button bt = (Button) event.getOrigin().getTarget();
		Listitem item =(Listitem) bt.getParent().getParent();
		Cliente cl = (Cliente) item.getValue();
		Cliente clienteEscolhido = (Cliente)win_escolherAviso.getAttribute("clienteEscolhido");
		//Textbox tb_cliente = (Textbox) win_escolherAviso.getAttribute("tb_cliente");
		Window wd_aviso = (Window) win_escolherAviso.getAttribute("wd_aviso");
		Listbox lb_destinatarios=(Listbox) win_escolherAviso.getAttribute("lb_destinatarios");
		ListModelList<MensagemAvisoIndividual> destinatarioModel=(ListModelList<MensagemAvisoIndividual>) win_escolherAviso.getAttribute("destinatarioModel");
		
		clienteEscolhido = cl;
		String nome=null;
		//Messagebox.show("id "+clienteEscolhido.getId());
		if(cl instanceof ClienteDomestico)
			nome = ((ClienteDomestico)cl).getNome();
		else if (cl instanceof ClienteColectivo)
			nome = ((ClienteColectivo)cl).getNome();
		
		//tb_cliente.setText(nome);
		win_escolherAviso.detach();
		
		MensagemAvisoIndividual mensagem=new MensagemAvisoIndividual();
		mensagem.setCliente(clienteEscolhido);
		List<MensagemAvisoIndividual> lista = new ArrayList<MensagemAvisoIndividual>();
		lista.add(mensagem);
		destinatarioModel=new ListModelList<MensagemAvisoIndividual>(lista);
		lb_destinatarios.setModel(destinatarioModel);
		
		
		wd_aviso.setAttribute("clienteEscolhido", clienteEscolhido);
		wd_aviso.setAttribute("destinatarioModel", destinatarioModel);//mudei aqui
		
	}
}
