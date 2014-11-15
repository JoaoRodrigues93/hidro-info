package mz.co.hidroinfo.controller;






import mz.co.hidroinfo.dao.PrecarioDao;
import mz.co.hidroinfo.model.Funcionario;
import mz.co.hidroinfo.model.Precario;

import org.zkoss.zhtml.Button;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;



public class ControladorPrecario extends GenericForwardComposer {

	private Listbox lst_client;
	private Button btn_add, btn_reg;

	private Textbox taxafixac, taxamcubicoc, taxafixad, taxamcubicod;
	
	private Precario p;
	private PrecarioDao dao;
	
	public ControladorPrecario() {
		dao = new PrecarioDao();
	}
	
	public void onClick$RegistaPrecoC(ForwardEvent e){
		
		Precario colectivo = dao.pegaTarifa("Coletivo");
		if(colectivo == null){
		p=new Precario();
		
		p.setTaxa_fixa(Integer.parseInt(taxafixac.getText()));
		p.setTaxa_por_metro_cubico(Integer.parseInt(taxamcubicoc.getText()));
		p.setTipo_de_tarifa("Coletivo");
		
		PrecarioDao pd=new PrecarioDao();
		pd.create(p);
		Clients.showNotification("Tarifa registada com sucesso!");
		limparCampos();
		}
		else {
			colectivo.setTaxa_fixa(Integer.valueOf(taxafixac.getText()));
			colectivo.setTaxa_por_metro_cubico(Integer.valueOf(taxamcubicoc.getText()));
			dao.update(colectivo);
			Clients.showNotification("Tarifa actualizada com sucesso!");
		}
		
		actualizaTarifa();
	}
	public void limparCampos(){
		taxafixac.setText(null);;
		taxamcubicoc.setText(null);
		
		
	
	}
	
	public void onClick$RegistaPrecoD(ForwardEvent e){
		Precario domestico = dao.pegaTarifa("Domestico");
		if(domestico == null){
		p=new Precario();
		p.setTaxa_fixa(Integer.parseInt(taxafixad.getText()));
		p.setTaxa_por_metro_cubico(Integer.parseInt(taxamcubicod.getText()));
		p.setTipo_de_tarifa("Domestico");
		
		PrecarioDao pd=new PrecarioDao();
		pd.create(p);
		Clients.showNotification("Tarifa registada com sucesso!");
		limparCampos();
		}
		else {
			domestico.setTaxa_fixa(Integer.valueOf(taxafixad.getText()));
			domestico.setTaxa_por_metro_cubico(Integer.valueOf(taxamcubicod.getText()));
			dao.update(domestico);
			Clients.showNotification("Tarifa actualizada com sucesso!");
		}
		
		actualizaTarifa();
	}
	

	
	public void doAfterCompose (Component comp){
		try {
			super.doAfterCompose(comp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		actualizaTarifa();
	}
	
	public void actualizaTarifa () {
		Precario colectivo = dao.pegaTarifa("Coletivo");
		Precario domestico = dao.pegaTarifa("Domestico");
		if(colectivo!=null)
		{
			taxafixac.setText(""+colectivo.getTaxa_fixa());
			taxamcubicoc.setText(""+colectivo.getTaxa_por_metro_cubico());
		}
		
		if(domestico!=null)
		{
			taxafixad.setText(""+domestico.getTaxa_fixa());
			taxamcubicod.setText(""+domestico.getTaxa_por_metro_cubico());
		}
	}
	
}
