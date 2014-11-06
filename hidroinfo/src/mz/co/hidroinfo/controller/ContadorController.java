package mz.co.hidroinfo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mz.co.hidroinfo.dao.ContadorDao;
import mz.co.hidroinfo.model.Cliente;
import mz.co.hidroinfo.model.Contador;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ContadorController extends SelectorComposer<Component> {
	
	@Wire
	private Button bt_escolherCliente;
	@Wire
	private Textbox tb_numeroContador;
	@Wire
	private Button bt_guardar;
	@Wire
	private Button bt_limpar;
	@Wire
	private Button bt_eliminar;
	@Wire
	private Button bt_alterar;
	@Wire
	private Textbox tb_escolherCliente;
	@Wire
	private Row rw_proprietario;
	@Wire
	private Listbox lb_contador;
	private ContadorDao dao;
	private ListModelList<Contador> contadorModel;
	
	
	public ContadorController (){
		dao = new ContadorDao();
	}
	
	@Override
	public void doAfterCompose (Component comp) throws Exception{
		super.doAfterCompose(comp);
		pesquisa();
	}
	
	@Listen ("onClick = #bt_escolherCliente")
	public void escolheCliente(){
		Map<String, Object> arguments = new HashMap<String,Object>();
		arguments.put("tb_escolherCliente", tb_escolherCliente);
		arguments.put("rw_proprietario", rw_proprietario);
		Window win = (Window) Executions.createComponents("/registos/escolheCliente.zul", null, arguments);
		win.doHighlighted();
	}
	
	@Listen ("onClick = #bt_guardar")
	public void guardarContador (){
		Cliente selectedCliente = (Cliente) rw_proprietario.getValue();
		Contador contador = new Contador();
		contador.setNumero(Integer.valueOf(tb_numeroContador.getText()));
		contador.setProprietario(selectedCliente);
		dao.create(contador);
		contadorModel.add(0, contador);
		Clients.showNotification("Contador com o número "+contador.getNumero()+" foi registado");
	}
	
	public void pesquisa (){
		List<Contador> contadores = dao.findAll();
		contadorModel = new ListModelList<Contador>(contadores);
		lb_contador.setModel(contadorModel);
	}
	
}
