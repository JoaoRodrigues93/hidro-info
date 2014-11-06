package mz.co.hidroinfo.controller;






import mz.co.hidroinfo.dao.PrecarioDao;
import mz.co.hidroinfo.model.Funcionario;
import mz.co.hidroinfo.model.Precario;

import org.zkoss.zhtml.Button;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.event.ForwardEvent;
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
	
	Precario p;
	public void onClick$RegistaPrecoC(ForwardEvent e){
		p=new Precario();
		
		p.setTaxa_fixa(Integer.parseInt(taxafixac.getText()));
		p.setTaxa_por_metro_cubico(Integer.parseInt(taxamcubicoc.getText()));
		p.setTipo_de_tarifa("Coletivo");
		
		PrecarioDao pd=new PrecarioDao();
		pd.create(p);
	
		

		Messagebox.show("Tarifa registada com sucesso!");
		limparCampos();
	}
	public void limparCampos(){
		taxafixac.setText(null);;
		taxamcubicoc.setText(null);
		
		
	
	}
	
	public void onClick$RegistaPrecoD(ForwardEvent e){
		p=new Precario();
		
		p.setTaxa_fixa(Integer.parseInt(taxafixad.getText()));
		p.setTaxa_por_metro_cubico(Integer.parseInt(taxamcubicod.getText()));
		p.setTipo_de_tarifa("Domestico");
		
		PrecarioDao pd=new PrecarioDao();
		pd.create(p);
	
		

		Messagebox.show("Tarifa registada com sucesso!");
		limparCampos();
	}
	

	
	
	
}
