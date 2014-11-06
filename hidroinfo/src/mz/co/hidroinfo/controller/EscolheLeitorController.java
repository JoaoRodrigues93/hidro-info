package mz.co.hidroinfo.controller;

import java.util.List;
import java.util.Map;

import mz.co.hidroinfo.dao.ContadorDao;
import mz.co.hidroinfo.dao.FuncionarioDao;
import mz.co.hidroinfo.dao.LeitorDao;
import mz.co.hidroinfo.model.Contador;
import mz.co.hidroinfo.model.Leitor;

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

public class EscolheLeitorController extends SelectorComposer<Component> {
	@Wire
	private Button bt_pesquisa;
	@Wire
	private Textbox tb_pesquisa;
	@Wire
	private Listbox lb_leitor;
	private LeitorDao dao;
	ListModelList<Leitor> leitorModel;
	private Leitor selectedLeitor;
	@Wire
	private Window winescolherLeitor;
	
	@Wire
	private Row rw_dadosLeitura;
	
	public EscolheLeitorController (){
		dao = new LeitorDao();
	}
	
	@Override
	public void doAfterCompose(Component comp) throws Exception{
		super.doAfterCompose(comp);
		pesquisaLeitores();
	}


	private void pesquisaLeitores() {
		List<Leitor> leitores = dao.findAll();
		leitorModel = new ListModelList<Leitor>(leitores);
		lb_leitor.setModel(leitorModel);
	}
	
	@Listen ("onLeitorEscolhe = #lb_leitor")
	public void escolheContador(ForwardEvent event){
		Map<String,Object> arg =(Map) rw_dadosLeitura.getValue();
		Row rw_leitor = (Row) arg.get("rw_leitor");
		Textbox tb_escolherContador = (Textbox) arg.get("tb_leitor");
		Listitem selectedItem = (Listitem)event.getOrigin().getTarget().getParent().getParent();
		selectedLeitor = (Leitor) selectedItem.getValue();
		
		rw_leitor.setValue(selectedLeitor);
		tb_escolherContador.setText(""+(selectedLeitor).getNome());
		
		winescolherLeitor.detach();
	}
	
}
