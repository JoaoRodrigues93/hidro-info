package mz.co.hidroinfo.controller;

import java.util.List;
import java.util.Map;

import mz.co.hidroinfo.dao.ContadorDao;
import mz.co.hidroinfo.model.Contador;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class EscolherContadorController extends SelectorComposer<Component> {

	@Wire
	private Button bt_pesquisa;
	@Wire
	private Textbox tb_pesquisa;
	@Wire
	private Listbox lb_contador;
	private ContadorDao dao;
	ListModelList<Contador> contadorModel;
	private Contador selectedContador;
	@Wire
	private Window winescolher;
	
	@Wire
	private Row rw_dadosLeitura;
	
	public EscolherContadorController (){
		dao = new ContadorDao();
	}
	
	@Override
	public void doAfterCompose(Component comp) throws Exception{
		super.doAfterCompose(comp);
		pesquisaContadors();
	}


	private void pesquisaContadors() {
		List<Contador> contadors = dao.findAll();
		contadorModel = new ListModelList<Contador>(contadors);
		lb_contador.setModel(contadorModel);
	}
	
	@Listen ("onContadorEscolhe = #lb_contador")
	public void escolheContador(ForwardEvent event){
		Map<String,Object> arg =(Map) rw_dadosLeitura.getValue();
		Row rw_contador = (Row) arg.get("rw_contador");
		Textbox tb_escolherContador = (Textbox) arg.get("tb_contador");
		Listitem selectedItem = (Listitem)event.getOrigin().getTarget().getParent().getParent();
		selectedContador = (Contador) selectedItem.getValue();
		
		rw_contador.setValue(selectedContador);
		tb_escolherContador.setText(""+(selectedContador).getNumero());
		
		winescolher.detach();
	}
	
	
}
