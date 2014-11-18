package mz.co.hidroinfo.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import mz.co.hidroinfo.dao.MontanteDao;
import mz.co.hidroinfo.dao.OperadorDao;
import mz.co.hidroinfo.dao.PagamentoDao;
import mz.co.hidroinfo.model.*;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listfooter;
import org.zkoss.zul.Messagebox;

public class MontanteFuncionarioController extends SelectorComposer<Component>{

	@Wire
	private Button btn_Montante;
	
	@Wire
	private Datebox dt_dataPagamento;
	
	@Wire
	private Listbox lst_montante;
	
	private OperadorDao dao;
	
	@Wire
	private Button btn_procurar;
	
	@Wire
	private Label lb_total;
	@Wire
	private Listfooter lf_total;
	
	ListModelList<Montante> montanteModel;
	
	public MontanteFuncionarioController (){
		dao=new OperadorDao();
		
	}

	@Listen ("onClick=#btn_procurar")
	public void onClickProcurar(){
		float montante=0;
		Calendar dataPagamento = new GregorianCalendar();
		dataPagamento.setTime(dt_dataPagamento.getValue());
		
		List<Montante> lista=new MontanteDao().pagamentoPorOperador(dataPagamento);
		if(!lista.isEmpty()){
		montanteModel = new ListModelList <Montante> (lista);
		lst_montante.setModel(montanteModel);
		for (Iterator iterator = lista.iterator(); iterator.hasNext();) {
		montante += ((Montante) iterator.next()).getValorCobrado();
		}}else Clients.showNotification("Nao existe dados desse dia");  
			
		
		lf_total.setLabel(String.valueOf(montante));
	}

	
	
}




