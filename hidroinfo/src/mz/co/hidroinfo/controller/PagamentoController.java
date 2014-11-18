package mz.co.hidroinfo.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import mz.co.hidroinfo.dao.*;
import mz.co.hidroinfo.model.*;

import org.apache.poi.hssf.record.formula.eval.StringValueEval;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

public class PagamentoController extends SelectorComposer<Component> {

	@Wire
	private Label tb_nome, tb_classe, tb_faturacao, dtb_valor, itb_idFatura, 
	itb_codCliente, tb_divida, tb_total,tb_troco;
	@Wire
	private Datebox dt_emissao;
	@Wire
	private Datebox dt_limitePagamento;
	@Wire
	private Button bt_factura;
	@Wire
	private Intbox itb_codFac;
	@Wire
	private Textbox tb_vlrEntregue;
	@Wire
	private Button btn_pagar;
	@Wire
	private Grid gd_pagamento;
	public static Operador operadoraActual;
	@Wire
	private Datebox dt_dataPag;
	private OperadorDao daoOp;
	private Factura fac;
	private FacturaDao daoFac;
	private ListModelList<Pagamento> modelPagamento;
	@Wire
	private Listbox lst_pagamento;
	private final float MULTA = 20;
	private Date data;
	private PagamentoDao pagDao;
	private Pagamento pag;
	@WireVariable
	private Session _sess;

	public PagamentoController() {
		daoFac = new FacturaDao();
		daoOp = new OperadorDao();
		pagDao = new PagamentoDao();
		data = new GregorianCalendar().getTime();
		operadoraActual = LoginController.operadorActual;
		pag = new Pagamento();

	}

	public void doAfterCompose(Component comp) {
		try {
			super.doAfterCompose(comp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		actualizaPagamento();
	}

	@Listen("onClick = #bt_factura")
	public void vizualizaFactura() {
		tb_troco.setValue(null);
		Factura fa = daoFac.findById(itb_codFac.getValue());
		fac = fa;	
		int id = itb_codFac.getValue();
		if(!pagDao.pegaFactura(id).isEmpty()){
			Clients.showNotification("Pagamento ja foi efectuado com essa factura","error",itb_codFac,null,2000);
		}else{
		if (fa != null) {
			Clients.showNotification("Dados da factura",gd_pagamento);
			Cliente cl = fa.getLeituraContador().getContador()
					.getProprietario();
			String nome = null;

			if (cl instanceof ClienteDomestico) {
				nome = ((ClienteDomestico) cl).getNome();
				tb_classe.setValue("Domestico");
			} else if (cl instanceof ClienteColectivo) {
				nome = ((ClienteColectivo) cl).getNome();
				tb_classe.setValue("Colectivo");

			}
			tb_nome.setValue(nome);
			itb_idFatura.setValue(String.valueOf(fa.getId()));

			itb_codCliente.setValue(String.valueOf(fa.getLeituraContador().getContador().getProprietario().getId()));
			dt_emissao.setValue(fa.getDataEmissao().getTime());
			dt_limitePagamento.setValue(fa.getDataLimite().getTime());
			tb_faturacao.setValue(fa.getPeriodoFacturacao());
			dtb_valor.setValue(String.valueOf(fa.getValorAPagar()));
		} else
			Clients.showNotification("Nenhuma factura encontrada");
		total();
	}}

	public float total() {
		float multa=0, valor=0;
		try{
			

			Factura fa = daoFac.findById(itb_codFac.getValue());

			Date dataLim = fa.getDataLimite().getTime();
			valor = fa.getValorAPagar();
			
			if (data.after(dataLim)) {
				multa = valor * MULTA / 100;
				tb_divida.setValue(String.valueOf(multa));
			} else {
				multa = 0;
				tb_divida.setValue(String.valueOf(multa));
			}	
			
		} catch(Exception ex){
			
			
		}
		float total = multa + valor;
		tb_total.setValue(String.valueOf(total));
		return total;
	}

	@Listen("onClick = #btn_pagar")
	public void efectuarPagamento() {
	//	Clients.showNotification("paguei");
		Calendar dataPag = new GregorianCalendar();
		dataPag.setTime(data);
		float valorEntr=0, troco, total, divida;
		try{
		valorEntr = Float.valueOf(tb_vlrEntregue.getValue());
		}catch (Exception ex) {
			Clients.showNotification("Introduza o valor entre, porfavor!","error",tb_vlrEntregue,null,2000);
		}
		divida = Float.valueOf(tb_divida.getValue());
		total = total();

	
		if (valorEntr < total) {
			Clients.showNotification("valor insuficiente","error",tb_vlrEntregue, null,2000);
		}else{
			troco = valorEntr - total;
			tb_troco.setValue(String.valueOf(troco));
			Pagamento pag = new Pagamento();
			pag.setDivida(divida);
			pag.setDataPagamento(dataPag);
			pag.setOperador(operadoraActual);
			pag.setValor_a_pagar(total);
			pag.setFactura(fac);
			pagDao.create(pag);
			modelPagamento.add(0, pag);
			dt_dataPag.setValue(data);
			// clearDados();
			imprimirRecibo(pag,troco);
		clearDados();
				
			
		}}

	private void imprimirRecibo(Pagamento pagamento, float troco) {

		Recibo recibo = new Recibo();
		ReciboDAO reciboDAO = new ReciboDAO();

		int tamanhoListaParaRediboCodigo = reciboDAO.findAll().size();

		if (tamanhoListaParaRediboCodigo == 0)
			tamanhoListaParaRediboCodigo = 1;

		recibo.setCodRecibo(tamanhoListaParaRediboCodigo);
		recibo.setPagamento(pagamento);
		recibo.setDataImpressao(pagamento.getDataPagamento());

		Map<String, Object> arguments = new HashMap<String, Object>();
		arguments.put("recibo", recibo);
		arguments.put("troco", troco);

		Executions.createComponents("/gestao/recibo.zul", null, arguments);
	

	}

	public void actualizaPagamento() {
		List<Pagamento> lista = pagDao.pegaPagamento(new GregorianCalendar());
		modelPagamento = new ListModelList<Pagamento>(lista);
		lst_pagamento.setModel(modelPagamento);
	}

	public void clearDados() {
		tb_divida.setValue(null);
		tb_total.setValue(null);
		tb_nome.setValue(null);
		tb_classe.setValue(null);
		tb_vlrEntregue.setText(null);
		itb_codCliente.setValue(null);
		itb_codFac.setValue(0);
		itb_idFatura.setValue(null);;
		tb_faturacao.setValue(null);;
		dt_emissao.setValue(null);
		dtb_valor.setValue(null);
		dt_limitePagamento.setValue(null);
		itb_codCliente.setValue(null);
		
		
	}
}
