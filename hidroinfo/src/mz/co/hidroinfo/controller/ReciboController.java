package mz.co.hidroinfo.controller;

import java.awt.Desktop;
import java.io.File;

import mz.co.hidroinfo.model.Recibo;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
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
	      
	        JasperReport jasperReport;
	        JasperPrint jasperPrint;
	        JasperDesign jasperDesign;
	        
	        String jasperFile = "/report/recibo.jrxml";
	        String pdfFile = "/report/recibo";
	        
	        String path = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/");
	        
	        try {
	            
	        	Messagebox.show(path+jasperFile);
	        	
	        	jasperDesign = JRXmlLoader.load(path+jasperFile);
	        	jasperReport = JasperCompileManager.compileReport(jasperDesign);
	        	
	        	ReciboCollection.setRecibo((Recibo)rw_recibo.getValue());
	        	ReciboCollection.getCollectionBean();
	 
	        	Recibo recibo = (Recibo)rw_recibo.getValue();
	        	long horaActual = recibo.getDataImpressao().getTimeInMillis();
	        		        	
	        	jasperPrint = JasperFillManager.fillReport(jasperReport, null, new JRBeanCollectionDataSource(ReciboCollection.getCollectionBean()));
	        	JasperExportManager.exportReportToPdfFile(jasperPrint,path+pdfFile+horaActual+".pdf");
	        	
	        	File myFile = new File(path+pdfFile+horaActual+".pdf");
	            Desktop.getDesktop().open(myFile);
	        	
	        	/*JRExporter exporter = new net.sf.jasperreports.engine.export.JRPdfExporter();
	            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,pdfFile);
	            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	            exporter.exportReport();*/
	            
	        } catch (JRException e) {
	            e.printStackTrace();
	            System.exit(1);
	        } catch (Exception e) {
	            e.printStackTrace();
	            System.exit(1);
	        }
		
	    	        
		/*Recibo recibo = (Recibo) rw_recibo.getValue();
		//Messagebox.show("Operador " + recibo.getPagamento().getOperador().getUsername());
		List <Recibo> listRecibo = new ArrayList<Recibo>();
		listRecibo.add(recibo);*/
	}
	
}
