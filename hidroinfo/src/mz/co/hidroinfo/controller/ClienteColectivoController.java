package mz.co.hidroinfo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mz.co.hidroinfo.dao.ClienteColectivoDao;
import mz.co.hidroinfo.model.ClienteColectivo;
import mz.co.hidroinfo.model.Contacto;
import mz.co.hidroinfo.model.Endereco;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ClienteColectivoController extends SelectorComposer<Component> {
	
	
	public ClienteColectivoController (){
		dao = new ClienteColectivoDao();
	}
	
	@Wire
	private Button bt_guardar;
	@Wire
	private Button bt_limpar;
	@Wire
	private Button bt_alterar;
	@Wire
	private Textbox tb_nome;
	
	@Wire
	private Textbox tb_nuit;
	@Wire
	private Textbox tb_representante;
	@Wire
	private Textbox tb_email;
	@Wire
	private Textbox tb_telefone;
	@Wire
	private Textbox tb_avenida;
	@Wire
	private Textbox tb_bairro;
	@Wire
	private Textbox tb_casaNumero;
	@Wire
	private Textbox tb_cidade;
	@Wire
	private Textbox tb_quarteirao;
	@Wire
	private Textbox tb_rua;
	@Wire
	private Listbox lb_cliente;
	@Wire
	private ListModelList<ClienteColectivo> modeloColectivo;
	@Wire
	private Grid gd_clienteAlterar;
	@Wire
	private Row rw_dadosCliente;
	@Wire
	private Window winAlterarColectivo;
	private Listitem itemAlterar;
	
	private ClienteColectivoDao dao;
	
	@Listen ("onClick = #bt_guardar")
	
	public void guardar () {
		ClienteColectivo cliente = new ClienteColectivo();
		setValues(cliente);
		dao.create(cliente);
		clearValues();
		Clients.showNotification("Cliente registado");
		Map<String, Object> arguments = (Map)rw_dadosCliente.getValue();
		lb_cliente = (Listbox)arguments.get("tabelaColectivo");
		ListModelList<ClienteColectivo> lista = (ListModelList)lb_cliente.getModel();
		lista.add(0, cliente);
	}
	
	@Listen ("onClienteColectivoDelete = #lb_cliente")
	public void onClickApagar (ForwardEvent event) {
		System.out.println("Apagando um cliente Colectivo");
		Button bt_apagar =(Button) event.getOrigin().getTarget();
		Listcell celula = (Listcell)bt_apagar.getParent().getParent();
		Listitem item = (Listitem)celula.getParent();
		ClienteColectivo clienteApagar = (ClienteColectivo)item.getValue();
		lb_cliente.removeChild(item);
		String nome = clienteApagar.getNome();
		dao.delete(clienteApagar);
		System.out.println("Apagado um cliente Colectivo");
		Clients.showNotification("Os dados do cliente "+nome+" foram apagados");
	}
	
	@Listen ("onClienteColectivoUpdate = #lb_cliente")
	public void onClickAlterar (ForwardEvent event){
		Button bt_alterar =(Button) event.getOrigin().getTarget();
		Listcell celula = (Listcell)bt_alterar.getParent().getParent();
		itemAlterar = (Listitem)celula.getParent();
		ClienteColectivo clienteAlterar = (ClienteColectivo)itemAlterar.getValue();
		Map<String, Object> arguments = new HashMap<String, Object>(); 
		arguments.put("clienteAlterar",clienteAlterar);
		arguments.put("lb_cliente",lb_cliente);
		Window win = (Window)Executions.createComponents("/registos/alteracaoColectivo.zul", null, arguments);
		win.doHighlighted();
	}
	
	@Listen ("onClick = #bt_alterar")
	public void alterarCliente () {

		Map<String, Object> arguments = (Map)rw_dadosCliente.getValue();
		ClienteColectivo cliente =(ClienteColectivo)arguments.get("clienteAlterar");
		lb_cliente = (Listbox) arguments.get("lb_cliente");
		ListModelList<ClienteColectivo> lista = (ListModelList)lb_cliente.getModel();
		lista.remove(cliente);
		setValues(cliente);
		dao.update(cliente);
		lista.add(0, cliente);
		winAlterarColectivo.detach();
		Clients.showNotification("Dados do cliente "+cliente.getNome()+" foram alterados");
	}
	
	public void actualizaTabelaColectivo (){
		List<ClienteColectivo> listaColectivo = dao.findAll();
		ListModelList<ClienteColectivo> modeloColectivo = new ListModelList<ClienteColectivo>(listaColectivo);
		lb_cliente.setModel(modeloColectivo);
	}
	
	public void setValues (ClienteColectivo cliente){
		Endereco endereco = new Endereco();
		Contacto contacto = new Contacto();
		endereco.setAvenida(tb_avenida.getText());
		endereco.setBairro(tb_bairro.getText());
		endereco.setCasaNumero(Integer.valueOf(tb_casaNumero.getText()));
		endereco.setCidade(tb_cidade.getText());
		endereco.setCodigoLocal(1);
		endereco.setQuarteirao(tb_quarteirao.getText());
		endereco.setRua(tb_rua.getText());
		
		contacto.setEmail(tb_email.getText());
		contacto.setTelefone(tb_telefone.getText());
		
		cliente.setRepresentante(tb_representante.getText());
		cliente.setNome(tb_nome.getText());
		cliente.setNuit(Integer.valueOf(tb_nuit.getText()));
		
		cliente.setContacto(contacto);
		cliente.setEndereco(endereco);
	}
	
	public void clearValues(){
		tb_avenida.setText(null);
		tb_bairro.setText(null);
		tb_casaNumero.setText(null);
		tb_cidade.setText(null);
		tb_quarteirao.setText(null);
		tb_rua.setText(null);
		tb_email.setText(null);
		tb_telefone.setText(null);
		tb_representante.setText(null);
		tb_nome.setText(null);
		tb_nuit.setText(null);
	}
}
