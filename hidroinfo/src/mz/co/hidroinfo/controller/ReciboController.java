package mz.co.hidroinfo.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mz.co.hidroinfo.model.Recibo;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;

public class ReciboController extends SelectorComposer<Component> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Wire
	private Row rw_recibo;
	@Wire
	private Button bt_imprimir;

	@Listen("onClick = #bt_imprimir")
	public void imprimirRecibo() {
 
	        // database connection
	        Connection connection = null;
	        
	        String jasperFile = "recibo.jasper";
	        String pdfFile = "recibo.pdf";
	        
	        try {
	            // get database connection.
	            
	 
	            JasperPrint print = JasperFillManager.fillReport(jasperFile, null, connection);
	            JRExporter exporter = new net.sf.jasperreports.engine.export.JRPdfExporter();
	            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,pdfFile);
	            exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
	            exporter.exportReport();
	            System.out.println("Created file: " + pdfFile);
	        } catch (JRException e) {
	            e.printStackTrace();
	            System.exit(1);
	        } catch (Exception e) {
	            e.printStackTrace();
	            System.exit(1);
	        }
		
		Recibo recibo = (Recibo) rw_recibo.getValue();
		//Messagebox.show("Operador " + recibo.getPagamento().getOperador().getUsername());
		
		List <Recibo> listRecibo = new ArrayList<Recibo>();
		
		listRecibo.add(recibo);
		
	}
	
	
	

}
