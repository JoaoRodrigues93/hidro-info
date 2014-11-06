package mz.co.hidroinfo.controller;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mz.co.hidroinfo.dao.FacturaDao;
import mz.co.hidroinfo.dao.LeituraContadorDao;
import mz.co.hidroinfo.model.Contador;
import mz.co.hidroinfo.model.Factura;
import mz.co.hidroinfo.model.Leitor;
import mz.co.hidroinfo.model.LeituraContador;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class LeituraContadorController extends SelectorComposer<Component> {
	
	@Wire
	private Row rw_contador;
	@Wire
	private Row rw_leitor;
	@Wire
	private Textbox tb_contador;
	@Wire
	private Textbox tb_leitor;
	@Wire
	private Button bt_escolherContador;
	@Wire
	private Button bt_guardar;
	@Wire
	private Button bt_limpar;
	@Wire
	private Button bt_eliminar;
	@Wire
	private Button bt_alterar;
	@Wire
	private Datebox db_dataAnterior;
	@Wire
	private Datebox db_dataActual;
	@Wire
	private Intbox ib_leituraActual;
	@Wire
	private Intbox ib_leituraAnterior;
	@Wire
	private Listbox lb_leitura;
	@Wire
	private ListModelList<LeituraContador> leituraModel;
	
	private LeituraContador selectedLeitura;
	private LeituraContadorDao dao;
	private FacturaDao daoFactura;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception{
		super.doAfterCompose(comp);
		pesquisa();
	}
	
	public LeituraContadorController(){
		dao = new LeituraContadorDao();
		daoFactura = new FacturaDao();
	}
	
	@Listen ("onClick = #bt_escolherContador")
	public void escolheContador(){
		Map<String, Object> arguments = new HashMap<String,Object>();
		arguments.put("rw_contador", rw_contador);
		arguments.put("tb_contador", tb_contador);
		Window win = (Window) Executions.createComponents("/registos/escolheContador.zul", null, arguments);
		win.doHighlighted();
	}
	
	@Listen ("onClick = #bt_escolherLeitor")
	public void escolheLeitor(){
		Map<String, Object> arguments = new HashMap<String,Object>();
		arguments.put("rw_leitor", rw_leitor);
		arguments.put("tb_leitor", tb_leitor);
		Window win = (Window) Executions.createComponents("/registos/escolheLeitor.zul", null, arguments);
		win.doHighlighted();
	}
	
	@Listen ("onClick = #bt_guardar")
	public void guardarLeitura(){
		LeituraContador leitura = new LeituraContador();
		setValues(leitura);
		dao.create(leitura);
		criarFactura(leitura);
		leituraModel.add(0, leitura);
	}
	
	public void setValues(LeituraContador leitura){
		Calendar dataAnterior, dataActual;
		int leituraAnterior,leituraActual;
		Contador contador;
		Leitor leitor;
		dataAnterior = new GregorianCalendar();
		dataAnterior.setTime(db_dataAnterior.getValue());
		dataActual = new GregorianCalendar();
		dataActual.setTime(db_dataActual.getValue());
		leituraActual = ib_leituraActual.getValue();
		leituraAnterior = ib_leituraAnterior.getValue();
		contador = (Contador) rw_contador.getValue();
		leitor = (Leitor)rw_leitor.getValue();
		//Set values
		leitura.setLeituraActual(leituraActual);
		leitura.setLeituraAnterior(leituraAnterior);
		leitura.setDataAnterior(dataAnterior);
		leitura.setDataActual(dataActual);
		leitura.setContador(contador);
		leitura.setLeitor(leitor);
	}
	
	public void pesquisa (){
		List<LeituraContador> leituras = dao.findAll();
		leituraModel = new ListModelList<LeituraContador>(leituras);
		lb_leitura.setModel(leituraModel);
		
	}
	
	public void criarFactura (LeituraContador leitura) {
		Factura factura = new Factura();
		String periodo;
		float vpagar, taxaPorMetro=10,taxaFixa=100;
		int leituraAnterior, leituraActual;
		leituraActual = leitura.getLeituraActual();
		leituraAnterior = leitura.getLeituraAnterior();
		int diaActual, mesActual;
		final float IVA=0.17f;
		Calendar dataEmissao, dataLimite;
		dataEmissao = new GregorianCalendar();
		dataLimite = new GregorianCalendar();
		mesActual = dataEmissao.get(Calendar.DAY_OF_MONTH);
		dataLimite.set(Calendar.MONTH,mesActual+1);
		periodo = leitura.getDataAnterior().getTime().toLocaleString()+" a "+leitura.getDataActual().getTime().toLocaleString();
		vpagar = taxaFixa + taxaPorMetro * (leituraActual-leituraAnterior);
		vpagar +=IVA*vpagar;
		factura.setDataEmissao(dataEmissao);
		factura.setDataLimite(dataLimite);
		factura.setLeituraContador(leitura);
		factura.setPeriodoFacturacao(periodo);
		factura.setValorAPagar(vpagar);
		
		daoFactura.create(factura);
	}
	
	
}
