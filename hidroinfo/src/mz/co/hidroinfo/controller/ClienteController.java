package mz.co.hidroinfo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mz.co.hidroinfo.dao.ClienteColectivoDao;
import mz.co.hidroinfo.dao.ClienteDomesticoDao;
import mz.co.hidroinfo.model.ClienteColectivo;
import mz.co.hidroinfo.model.ClienteDomestico;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Center;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ClienteController extends SelectorComposer<Component>{

	private static final long serialVersionUID = 1L;
	
	private int tipoCliente;
	private final int COLECTIVO=1;
	private final int DOMESTICO=0;
	
	@Wire
	private Textbox tb_pesquisa;
	@Wire
	private Button bt_colectivo;
	@Wire
	private Button bt_domestico;
	@Wire
	private Button pesquisa;
	@Wire
	private Button adiciona;
	@Wire
	private Center tabela;
	@Wire
	private Window wincliente;
	
	private ClienteDomesticoDao daoDomestico;
	private ClienteColectivoDao daoColectivo;
	
	
	
	public ClienteController () {
		tipoCliente = DOMESTICO;
		daoColectivo = new ClienteColectivoDao();
		daoDomestico = new ClienteDomesticoDao();
	}
	
	
	@Override 
	public void doAfterCompose(Component comp) throws Exception{
		super.doAfterCompose(comp);
		setClienteDomestico();
		actualizaTabelaDomestico("");
	}
	
	@Listen ("onClick = #bt_domestico")
	public void setClienteDomestico (){
		setDomesticoAttributes();
		setTabelaDomestico();
		actualizaTabelaDomestico("");
	}
	
	@Listen ("onClick = #bt_colectivo")
	public void setClienteColectivo (){
		setColectivoAttributes();
		setTabelaColectivo();
		actualizaTabelaColectivo("");
	}
	
	@Listen ("onClick = #pesquisa")
	public void pesquisaCliente () {
		String pesquisa = tb_pesquisa.getText();
		if(tipoCliente == DOMESTICO)
			actualizaTabelaDomestico(pesquisa);
		else
			if(tipoCliente == COLECTIVO)
				actualizaTabelaColectivo(pesquisa);
	}
	
	@Listen ("onClick = #adiciona")
	public void adicionaCliente() {
		Window win =null;
		Map<String, Object> arguments = new HashMap<String, Object>();
		if (tipoCliente == DOMESTICO){
		arguments.put("tabelaDomestico", tabela.getFirstChild());
		win = (Window) Executions.createComponents("clienteDomestico.zul", null,arguments);
		}
		else if (tipoCliente == COLECTIVO){
		arguments.put("tabelaColectivo", tabela.getFirstChild());
		win = (Window) Executions.createComponents("clienteColectivo.zul", null,arguments);
		}
		win.doHighlighted();
	}
	
	public void setTabelaDomestico (){
		Listbox tabelaDomestico;
		Component child = tabela.getFirstChild();
		if(child !=null){
			tabela.removeChild(child);
		}
		tabelaDomestico = (Listbox) Executions.createComponents("tabelaDomestico.zul", tabela, null);
	}
	
	public void setTabelaColectivo (){
		Listbox tabelaColectivo;
		Component child = tabela.getFirstChild();
		if(child !=null){
			tabela.removeChild(child);
		}
		tabelaColectivo = (Listbox) Executions.createComponents("tabelaColectivo.zul", tabela, null);
	}
	
	public void setDomesticoAttributes (){
		tipoCliente = DOMESTICO;
		bt_domestico.setZclass("btn btn-primary");
		bt_colectivo.setZclass("btn btn-default");
		bt_domestico.setDisabled(true);
		bt_colectivo.setDisabled(false);
	}
	
	public void setColectivoAttributes (){
		bt_domestico.setDisabled(false);
		bt_domestico.setZclass("btn btn-default");
		bt_colectivo.setZclass("btn btn-primary");
		bt_colectivo.setDisabled(true);
		tipoCliente = COLECTIVO;
	}
	
	
	public void actualizaTabelaDomestico (String pesquisa){
		Listbox tabelaDomestico = (Listbox)tabela.getFirstChild();
		List<ClienteDomestico> listaDomestico = daoDomestico.findAll(pesquisa);
		ListModelList<ClienteDomestico> modeloDomestico = new ListModelList<ClienteDomestico>(listaDomestico);
		tabelaDomestico.setModel(modeloDomestico);
	}
	
	public void actualizaTabelaColectivo (String pesquisa){
		Listbox tabelaColectivo = (Listbox)tabela.getFirstChild();
		List<ClienteColectivo> listaColectivo = daoColectivo.findAll(pesquisa);
		ListModelList<ClienteColectivo> modeloColectivo = new ListModelList<ClienteColectivo>(listaColectivo);
		tabelaColectivo.setModel(modeloColectivo);
	}
	
}
