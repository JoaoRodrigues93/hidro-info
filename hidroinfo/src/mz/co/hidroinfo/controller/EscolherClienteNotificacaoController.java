package mz.co.hidroinfo.controller;


import java.util.List;
import java.util.Map;

import mz.co.hidroinfo.dao.ClienteDao;
import mz.co.hidroinfo.model.Cliente;
import mz.co.hidroinfo.model.ClienteColectivo;
import mz.co.hidroinfo.model.ClienteDomestico;

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

public class EscolherClienteNotificacaoController extends SelectorComposer<Component> {
	
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
	private Window winescolher;
	
	@Wire
	private Row rw_dadosContador;
	
	public EscolherClienteNotificacaoController (){
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
		Cliente clienteEscolhido = (Cliente)winescolher.getAttribute("clienteEscolhido");
		Textbox tb_cliente = (Textbox) winescolher.getAttribute("tb_cliente");
		Window wd_notificacao = (Window) winescolher.getAttribute("wd_notificacao");
		clienteEscolhido = cl;
		String nome=null;
		//Messagebox.show("id "+clienteEscolhido.getId());
		if(cl instanceof ClienteDomestico)
			nome = ((ClienteDomestico)cl).getNome();
		else if (cl instanceof ClienteColectivo)
			nome = ((ClienteColectivo)cl).getNome();
		
		tb_cliente.setText(nome);
		winescolher.detach();
		wd_notificacao.setAttribute("clienteEscolhido", clienteEscolhido);
		
	}
}
