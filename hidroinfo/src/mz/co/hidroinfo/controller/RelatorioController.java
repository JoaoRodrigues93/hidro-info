package mz.co.hidroinfo.controller;

import java.util.Date;
import java.util.List;

import mz.co.hidroinfo.dao.ClienteColectivoDao;
import mz.co.hidroinfo.dao.ClienteDao;
import mz.co.hidroinfo.dao.ClienteDomesticoDao;
import mz.co.hidroinfo.dao.PagamentoDao;
import mz.co.hidroinfo.model.Pagamento;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Calendar;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;

public class RelatorioController extends GenericForwardComposer {
	private final byte MAPUTO = 0;
	private final byte XAI_XAI = 1;
	private final byte INHAMBANE = 2;
	private final byte BEIRA = 3;
	private final byte CHIMOIO = 4;
	private final byte TETE = 5;
	private final byte QUELIMANE = 6;
	private final byte NAMPULA = 7;
	private final byte LICHINGA = 8;
	private final byte PEMBA = 9;
	
	private Radiogroup rdg_relatorios;
	private Combobox cbx_cidades;		
	private Combobox cbx_bairros;
	private Radiogroup rdg_categoria;
	private Combobox cbx_periodo;
	private Label lbl_colectivo, lbl_domestico;
	private Listbox lst_relatorio, lst_clientesDivida;
	private Datebox calendario;
	private Textbox txt_totalClientesColectivo, txt_totalClientesDomestico;
	
	private String[] Mbairros = {"Hulene", "Laulane", "Chamanculo", "Fumento", "Urbanizacao", "Alto-mae", "CMC"
								,"Magoanine", "Guava", "Marraquene"};
	


	public void onSelect(ForwardEvent e){
		int i = cbx_cidades.getSelectedIndex();
		
		switch(i){
			case MAPUTO:
				popularBairros(Mbairros);
				break;
			case XAI_XAI:
				break;
			case INHAMBANE:
				break;
			case BEIRA:
				break;
			case CHIMOIO:
				break;
			case TETE:
				break;
			case QUELIMANE:
				break;
			case NAMPULA:
				break;
			case LICHINGA:
				break;
			case PEMBA:
				break;				
		}
	}
	
	private void popularBairros(String[] bairros){
		cbx_bairros.setVisible(true);
		for(int i = 0; i < bairros.length; i++){
			cbx_bairros.appendItem(bairros[i]);
		}
	}


//	@Override
//	public void doAfterCompose(Component arg0) throws Exception {
//		// TODO Auto-generated method stub
//		
//	}
	
	
//	public void onClick$Pesquisa(ForwardEvent fe){
//		double valor = 0;
//		Date d = calendario.getValue();
//		PagamentoDao pag = new PagamentoDao();
//		java.util.Calendar data = java.util.Calendar.getInstance();
//		data.setTime(d);
//		
//		List<Pagamento> lista_pagamento = pag.pegaPagamento(data);
//		Pagamento pagamento = null;
//		
//		 while (!lista_pagamento.isEmpty()) {
//			 	pagamento = lista_pagamento.remove(0);
//	            Listitem lstit = new Listitem();
//	            lstit.setValue(pagamento);
//	            Listcell lstcl1 = new Listcell();
//	            Listcell lstcl2 = new Listcell();
//	            valor = calcularTotalPagamentoDoDia(data.getTime(), lista_pagamento);
//	            
//	            lstcl1.setLabel(pagamento.getDataPagamento().toString() + "");
//	            lstcl2.setLabel(String.format("%f.2", valor));
//
//
//	            lstit.appendChild(lstcl1);
//	            lstit.appendChild(lstcl2);
//	            lstit.addForward("onClick", "", "onClick$Item");
//
//	            lst_relatorio.appendChild(lstit);
//	        }
//		 
//		 
//	}
	
	public void onClick$Gerar(ForwardEvent fe){
		
	}
	
	public void popularValores(){
		PagamentoDao pag = new PagamentoDao();
		
		switch(rdg_categoria.getSelectedIndex()){
			case 1:
				break;
			case 2:
				break;
			case 3:
				if(cbx_periodo.getSelectedIndex() == 0){
					Date d = calendario.getValue();
					java.util.Calendar c = java.util.Calendar.getInstance();
					c.setTime(d);
					double valor = calcularTotalPagamentoDoDia(pag.pegaPagamento(c));
					Pagamento pagamento =  new Pagamento();
					
					Listitem lstit = new Listitem();
		            lstit.setValue(pagamento);
		            Listcell lstcl1 = new Listcell();
		            Listcell lstcl2 = new Listcell();
		            
		            lstcl1.setLabel(d.getDate() + "/" + d.getMonth() + "/" + d.getYear());
		            lstcl2.setLabel(String.format("%f.2", valor));
		            
		            lstit.appendChild(lstcl1);
		            lstit.appendChild(lstcl2);
		            lstit.addForward("onClick", "", "onClick$Item");
				}
				else if(cbx_periodo.getSelectedIndex() == 1){
					calcularPagamentoSemana();
				} else if(cbx_periodo.getSelectedIndex() == 2){
					calcularPagamentoDoMes();
				}
				break;
		}
		java.util.Calendar data = java.util.Calendar.getInstance();
	}
	
	public double calcularTotalPagamentoDoDia(List<Pagamento> lst){
		double valor = 0;
		int pos = 1;
		Pagamento pag;
		
		while(pos <= lst.size()){
			pag = lst.get(pos++);

			valor += pag.getValor_a_pagar();
			
		}
		return valor;		 
	 }
	
	
	public void calcularPagamentoSemana(){
		tornarComponentesInvisiveis();
		lst_relatorio.setVisible(true);
		
		Date hoje = new Date(System.currentTimeMillis());
		Date tmp = new Date(System.currentTimeMillis());
		java.util.Calendar cal = java.util.Calendar.getInstance();
		PagamentoDao pagdao = new PagamentoDao();
		Pagamento pagamento = new Pagamento();
		int dia = hoje.getDay();
		double valor = 0;
		
		for(int i = 1; i <= dia; i++){
			tmp.setDate(tmp.getDate()-dia+i);
			cal.setTime(tmp);
			List<Pagamento> pag = pagdao.pegaPagamento(cal);
			valor = calcularTotalPagamentoDoDia(pag);
			
			Listitem lstit = new Listitem();
            lstit.setValue(pagamento);
            Listcell lstcl1 = new Listcell();
            Listcell lstcl2 = new Listcell();
            
            lstcl1.setLabel(numDiaParaString(i) + "");
            lstcl2.setLabel(String.format("%f.2", valor));
            
            lstit.appendChild(lstcl1);
            lstit.appendChild(lstcl2);
            lstit.addForward("onClick", "", "onClick$Item");
            
            lst_relatorio.appendChild(lstit);
		}
		
	}
	
	public void calcularPagamentoDoMes(){
		tornarComponentesInvisiveis();
		lst_relatorio.setVisible(true);
		
		Date hoje = new Date(System.currentTimeMillis());
		Date tmp = new Date(System.currentTimeMillis());
		java.util.Calendar cal = java.util.Calendar.getInstance();
		PagamentoDao pagdao = new PagamentoDao();
		Pagamento pagamento = new Pagamento();
		int dia = hoje.getDate();
		double valor = 0;
		
		for(int i = 1; i <= dia; i++){
			tmp.setDate(i);
			cal.setTime(tmp);
			List<Pagamento> pag = pagdao.pegaPagamento(cal);
			valor = calcularTotalPagamentoDoDia(pag);
			
			Listitem lstit = new Listitem();
            lstit.setValue(pagamento);
            Listcell lstcl1 = new Listcell();
            Listcell lstcl2 = new Listcell();
            
            lstcl1.setLabel(tmp.getDate() + "/" + tmp.getMonth() + "/" +tmp.getYear());
            lstcl2.setLabel(String.format("%f.2", valor));
            
            lstit.appendChild(lstcl1);
            lstit.appendChild(lstcl2);
            lstit.addForward("onClick", "", "onClick$Item");
            
            lst_relatorio.appendChild(lstit);
		}
		
	}
	
	
	
	private void mostrarNumeroClientesDomestico(){
		ClienteDomesticoDao cd = new ClienteDomesticoDao();
		long numClientes = cd.findAll().size();
		txt_totalClientesDomestico.setText(numClientes+"");
		
		tornarComponentesInvisiveis();
		
		lbl_domestico.setVisible(true);		
		txt_totalClientesDomestico.setVisible(true);
	}
	
	
	
	private void mostrarNumeroClientesColectivo(){
		ClienteColectivoDao cc = new ClienteColectivoDao();
		long numClientes = cc.findAll().size();
		txt_totalClientesColectivo.setText(numClientes+"");
		
		tornarComponentesInvisiveis();
		
		txt_totalClientesColectivo.setVisible(true);
		lbl_colectivo.setVisible(true);
	}
	
	
	
	
	private void tornarComponentesInvisiveis(){
		txt_totalClientesColectivo.setVisible(false);
		lbl_colectivo.setVisible(false);
		
		txt_totalClientesDomestico.setVisible(false);
		lbl_domestico.setVisible(false);
		
		lst_relatorio.setVisible(false);
		lst_clientesDivida.setVisible(false);
	}
	
	private String numDiaParaString(int dia){
		switch(dia){
			case 1:
				return "Segunda-Feira";
			case 2:
				return "Terca-Feira";
			case 3:
				return "Quarta-Feira";
			case 4:
				return "Quinta-Feira";
			case 5:
				return "Sexta-Feira";
			case 6:
				return "Sabado";
			case 7:
				return "Domingo";
			default:
				return "";
		}
	}
}
