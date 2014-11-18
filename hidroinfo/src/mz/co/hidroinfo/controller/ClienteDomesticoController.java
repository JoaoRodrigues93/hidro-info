package mz.co.hidroinfo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mz.co.hidroinfo.dao.ClienteDomesticoDao;
import mz.co.hidroinfo.model.ClienteColectivo;
import mz.co.hidroinfo.model.ClienteDomestico;
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
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.Window;

public class ClienteDomesticoController extends SelectorComposer<Component> {
	
	
	public ClienteDomesticoController (){
		dao = new ClienteDomesticoDao();
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
	private Textbox tb_bi;
	@Wire
	private Textbox tb_email;
	@Wire
	private Textbox tb_avenida;
	@Wire
	private Textbox tb_bairro;
	@Wire
	private Textbox tb_cidade;
	@Wire
	private Textbox tb_quarteirao;
	@Wire
	private Textbox tb_rua;
	@Wire
	private Listbox lb_cliente;
	@Wire
	private Intbox tb_telefone, tb_nuit, tb_casaNumero;
	@Wire
	private ListModelList<ClienteDomestico> modeloDomestico;
	@Wire
	private Grid gd_clienteAlterar;
	@Wire
	private Row rw_dadosCliente;
	@Wire
	private Window winAlterarDomestico;
	private Listitem itemAlterar;
	
	private ClienteDomesticoDao dao;
	
	@Listen ("onClick = #bt_guardar")
	
	public void guardar () {
System.out.println("Registando um cliente domestico.....\n");
		ClienteDomestico cliente = new ClienteDomestico();
		setValues(cliente);
		dao.create(cliente);
		clearValues();
		Clients.showNotification("Cliente registado");
		Map<String, Object> arguments = (Map)rw_dadosCliente.getValue();
		lb_cliente = (Listbox)arguments.get("tabelaDomestico");
		ListModelList<ClienteDomestico> lista = (ListModelList)lb_cliente.getModel();
		lista.add(0, cliente);
		
	}
	@Listen("onClick=#bt_limpar")
	public void limparCampos (){
		clearValues();
	}
	
	@Listen ("onClienteDomesticoDelete = #lb_cliente")
	public void onClickApagar (ForwardEvent event) {
		Button bt_apagar =(Button) event.getOrigin().getTarget();
		Listcell celula = (Listcell)bt_apagar.getParent().getParent();
		Listitem item = (Listitem)celula.getParent();
		ClienteDomestico clienteApagar = (ClienteDomestico)item.getValue();
		lb_cliente.removeChild(item);
		String nome = clienteApagar.getNome();
		dao.delete(clienteApagar);
		Clients.showNotification("Os dados do cliente "+nome+" foram apagados");
	}
	
	@Listen ("onClienteDomesticoUpdate = #lb_cliente")
	public void onClickAlterar (ForwardEvent event){
		Button bt_alterar =(Button) event.getOrigin().getTarget();
		Listcell celula = (Listcell)bt_alterar.getParent().getParent();
		itemAlterar = (Listitem)celula.getParent();
		ClienteDomestico clienteAlterar = (ClienteDomestico)itemAlterar.getValue();
		Map<String, Object> arguments = new HashMap<String, Object>(); 
		arguments.put("clienteAlterar",clienteAlterar);
		arguments.put("lb_cliente",lb_cliente);
		Window win = (Window)Executions.createComponents("/registos/alteracaoDomestico.zul", null, arguments);
		win.doHighlighted();
	}
	
	@Listen ("onClick = #bt_alterar")
	public void alterarCliente () {

		Map<String, Object> arguments = (Map)rw_dadosCliente.getValue();
		ClienteDomestico cliente =(ClienteDomestico)arguments.get("clienteAlterar");
		lb_cliente = (Listbox) arguments.get("lb_cliente");
		ListModelList<ClienteDomestico> lista = (ListModelList)lb_cliente.getModel();
		lista.remove(cliente);
		setValues(cliente);
		dao.update(cliente);
		lista.add(0, cliente);
		winAlterarDomestico.detach();
		Clients.showNotification("Dados do cliente "+cliente.getNome()+" foram alterados");
	}
	
	
	public void actualizaTabelaDomestico (){
		List<ClienteDomestico> listaDomestico = dao.findAll();
		ListModelList<ClienteDomestico> modeloDomestico = new ListModelList<ClienteDomestico>(listaDomestico);
		lb_cliente.setModel(modeloDomestico);
	}
	
	public void setValues (ClienteDomestico cliente){
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
		
		cliente.setBi(tb_bi.getText());
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
		tb_email.setRawValue(null);
		tb_telefone.setText(null);
		tb_bi.setText(null);
		tb_nome.setText(null);
		tb_nuit.setText(null);
	}
}
