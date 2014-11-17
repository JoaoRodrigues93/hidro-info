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
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

public class PagamentoController extends SelectorComposer<Component> {

	@Wire
	private Textbox itb_idFatura;
	@Wire
	private Textbox tb_nome;
	@Wire
	private Textbox tb_classe;
	@Wire
	private Intbox itb_codCliente;
	@Wire
	private Datebox dt_emissao;
	@Wire
	private Datebox dt_limitePagamento;
	@Wire
	private Textbox tb_faturacao;
	@Wire
	private Textbox dtb_valor;
	@Wire
	private Button bt_factura;
	@Wire
	private Textbox tb_divida;
	@Wire
	private Intbox itb_codFac;
	@Wire
	private Textbox tb_total;
	@Wire
	private Textbox tb_vlrEntregue;
	@Wire
	private Textbox tb_troco;
	@Wire
	private Button btn_pagar;
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

	@WireVariable
	private Session _sess;

	public PagamentoController() {
		daoFac = new FacturaDao();
		daoOp = new OperadorDao();
		pagDao = new PagamentoDao();
		data = new GregorianCalendar().getTime();
		operadoraActual = LoginController.operadorActual;

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
		// Clients.showNotification("Chegou");

		Factura fa = daoFac.findById(itb_codFac.getValue());
		fac = fa;
		if (fa != null) {
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

			itb_codCliente.setValue(fa.getLeituraContador().getContador()
					.getProprietario().getId());
			dt_emissao.setValue(fa.getDataEmissao().getTime());
			dt_limitePagamento.setValue(fa.getDataLimite().getTime());
			tb_faturacao.setValue(fa.getPeriodoFacturacao());
			dtb_valor.setValue(String.valueOf(fa.getValorAPagar()));
		} else
			Clients.showNotification("Nenhuma factura encontrada");
		total();
	}

	public float total() {
		float multa;

		Factura fa = daoFac.findById(itb_codFac.getValue());

		Date dataLim = fa.getDataLimite().getTime();
		float valor = fa.getValorAPagar();
		if (data.after(dataLim)) {
			multa = valor * MULTA / 100;
			tb_divida.setValue(String.valueOf(multa));
		} else {
			multa = 0;
			tb_divida.setValue(String.valueOf(multa));
		}

		float total = multa + valor;
		tb_total.setValue(String.valueOf(total));
		return total;

	}

	@Listen("onClick = #btn_pagar")
	public void efectuarPagamento() {
		Calendar dataPag = new GregorianCalendar();
		dataPag.setTime(data);
		float valorEntr, troco, total, divida;
		valorEntr = Float.valueOf(tb_vlrEntregue.getValue());
		divida = Float.valueOf(tb_divida.getValue());
		total = total();
		int id = Integer.valueOf(itb_idFatura.getValue());
		Factura factura = daoFac.findById(id);
		if (factura == null) {

			if (valorEntr > total) {
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

				imprimirRecibo(pag);
				
			} else
				Messagebox.show("valor insuficiente");
			dt_dataPag.setValue(data);
		}
	}

	private void imprimirRecibo(Pagamento pagamento) {

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

		Executions.createComponents("/gestao/recibo.zul", null, arguments);

	}

	public void actualizaPagamento() {
		List<Pagamento> lista = pagDao.pegaPagamento(new GregorianCalendar());
		modelPagamento = new ListModelList<Pagamento>(lista);
		lst_pagamento.setModel(modelPagamento);
	}

}
