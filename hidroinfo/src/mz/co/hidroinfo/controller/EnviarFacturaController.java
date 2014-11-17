package mz.co.hidroinfo.controller;


import java.awt.Button;
import java.awt.Component;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import lib.SMS.sendsms;
import mz.co.hidroinfo.dao.FacturaDao;
import mz.co.hidroinfo.model.Cliente;
import mz.co.hidroinfo.model.Contador;
import mz.co.hidroinfo.model.Factura;
import mz.co.hidroinfo.model.Leitor;
import mz.co.hidroinfo.model.LeituraContador;
import mz.co.hidroinfo.model.Mensagem;

import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Row;

//import com.lowagie.text.Row;

public class EnviarFacturaController extends SelectorComposer{
	
	
	private FacturaDao facturaDao;
	private Mensagem msg;
	private Cliente cliente;
	private sendsms sender;
	
	@Wire
	private Button btn_enviar;
	@Wire
	private Listbox lb_facturas;
	ListModelList<Factura> facturaModel;
	private Row rw_factura;
	
	public EnviarFacturaController(){
		facturaDao=new FacturaDao();
		sender=new sendsms();
	}
	
	@Override
	public void doAfterCompose(org.zkoss.zk.ui.Component comp) throws Exception{
		super.doAfterCompose(comp);
		pesquisarFacturas();
	}
	
	
	@Listen ("onClick = #btn_enviar")
	public void enviarFacturas(){
		int i=0,erro=0;
		String mensagem="",numero;
		float valorAPagar,leituraActual,leituraAnterior;
		int id;
		List<Factura> lista =facturaModel.getInnerList();
		for (Iterator iterator = lista.iterator(); iterator.hasNext();) {
			Factura factura = (Factura) iterator.next();
			
			numero =factura.getLeituraContador().getContador().getProprietario().getContacto().getTelefone();
			valorAPagar = factura.getValorAPagar();
			id = factura.getId();
			leituraActual=factura.getLeituraContador().getLeituraActual();
			leituraAnterior=factura.getLeituraContador().getLeituraAnterior();
			
			mensagem=escreverFactura(id,valorAPagar,leituraAnterior,leituraActual);
			if(!factura.isEstado()){
				if(sendsms.mandarSMS(numero, mensagem)){
					factura.setEstado(true);
					facturaDao.update(factura);
					i++;
				}
				else{
					++erro;
				}
			}
		}
		if(i!=0)
			Clients.showNotification("Facturas foram enviadas para "+i+" clientes");
		if((i==0) && (erro==0))
			Clients.showNotification("Nenhuma factura foi enviada pois todas as facturas ja tinham sido enviadas");
		if(erro!=0)
			Clients.showNotification(erro+" facturas nao foram enviadas, verifique as configurações do servidor de SMS", "error", null, null, 4000);
		pesquisarFacturas();
	}
	
	public void pesquisarFacturas(){
		List<Factura> facturas=facturaDao.findAll();
		facturaModel = new ListModelList<Factura>(facturas);
		lb_facturas.setModel(facturaModel);
	}
	
	public String escreverFactura(int id,float valor, float LeituraAnterior, float LeituraActual){
		String mensagem="";
		
		mensagem="Codigo da factura: "+id+
				"\nLeitura anterior: "+LeituraAnterior+
				"\nLeitura actual: "+LeituraActual+
				"\nValor a pagar: "+valor+
				"MT";
		return mensagem;
	}
	
	
}