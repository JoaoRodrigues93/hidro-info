package mz.co.hidroinfo.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.metamodel.ListAttribute;

import mz.co.hidroinfo.dao.ClienteColectivoDao;
import mz.co.hidroinfo.dao.ClienteDomesticoDao;
import mz.co.hidroinfo.dao.PagamentoDao;
import mz.co.hidroinfo.model.Cliente;
import mz.co.hidroinfo.model.ClienteColectivo;
import mz.co.hidroinfo.model.ClienteDomestico;
import mz.co.hidroinfo.model.Pagamento;
import mz.co.hidroinfo.model.TotalPagamento;

import org.zkoss.zhtml.P;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.util.ThreadLocalListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.Window;

public class RelatoriosController extends SelectorComposer<Component> {

	@Wire
	private Vbox vb_adicional;

	@Wire
	private Button bt_gerar;
	@Wire
	private Radio rd_dividas;
	@Wire
	private Radio rd_pagamentos;
	@Wire
	private Radio rd_clientes;
	@Wire
	private Vlayout vl_navegacao;

	@Wire
	private Radio rd_domestico;
	@Wire
	private Radio rd_colectivo;

	@Wire
	private Radiogroup rg_categoria;
	@Wire
	private Radiogroup rg_relatorio;

	@Wire
	private Combobox cb_bairro;
	@Wire
	private Combobox cb_cidade;
	@Wire
	private Combobox cb_periodo;
	@Wire
	private Div dv_conteudo;
	@Wire
	private Hbox hb_imprimir;
	@Wire
	private Button bt_imprimir;

	@Wire
	private Vbox vb_periodo;
	@Wire
	private Vbox vb_locais;
	@Wire
	private Vlayout vl_dadosRelatorio;
	@Wire
	private Vbox vb_dadosRelatorio;
	@Wire
	private Label lb_tipoRelatorio;
	@Wire
	private Label lb_dataRelatorio;
	@Wire
	private Label lb_breveDescricao;
	@Wire
	private Textbox tb_descricao;
	private Datebox db_primeira;
	private Datebox db_ultima;

	private PagamentoDao dao;
	private ClienteDomesticoDao daoDomestico;
	private ClienteColectivoDao daoColectivo;

	private final int DIVIDA = 1;
	private final int CLIENTES = 2;
	private final int PAGAMENTOS = 3;
	private int tipoRelatorio = 3;

	private final int DOMESTICO = 1;
	private final int COLECTIVO = 2;
	private int categoria = 1;

	private final int DIARIO = 1;
	private final int SEMANAL = 2;
	private final int MENSAL = 3;
	private int periodo = 1;
	private String bairro;
	private String cidade;
	private Date dataEscolhida;

	public RelatoriosController() {
		dao = new PagamentoDao();
		daoDomestico = new ClienteDomesticoDao();
		daoColectivo = new ClienteColectivoDao();
	}

	public void doAfterCompose(Component comp) {
		try {
			super.doAfterCompose(comp);
		} catch (Exception e) {
			e.printStackTrace();
		}

		rd_pagamentos.setSelected(true);
		rd_domestico.setSelected(true);
		visualizaData();
		preencheBairroCidade();
		if (cb_periodo.getItemCount() > 0)
			cb_periodo.setSelectedIndex(0);
	}

	public void preencheBairroCidade() {
		List<Cliente> lista = new ArrayList<Cliente>();
		List<String> bairros = new ArrayList<String>();
		List<String> cidades = new ArrayList<String>();

		String bairroActual = null, cidadeActual = null, bairroProximo, cidadeProxima;
		if (categoria == DOMESTICO) {
			List<ClienteDomestico> clientes = daoDomestico.findAll();
			lista.addAll(clientes);
		} else if (categoria == COLECTIVO) {
			List<ClienteColectivo> clientes = daoColectivo.findAll();
			lista.addAll(clientes);
		}

		if (!lista.isEmpty()) {
			bairroActual = lista.get(0).getEndereco().getBairro();
			cidadeActual = lista.get(0).getEndereco().getCidade();
			bairros.add(bairroActual);
			cidades.add(cidadeActual);
		}
		for (Iterator iterator = lista.iterator(); iterator.hasNext();) {
			Cliente cliente = (Cliente) iterator.next();
			bairroProximo = cliente.getEndereco().getBairro();
			cidadeProxima = cliente.getEndereco().getCidade();
			if (!bairroProximo.equalsIgnoreCase(bairroActual)) {
				bairros.add(bairroProximo);
				bairroActual = bairroProximo;
			}

			if (!cidadeProxima.equalsIgnoreCase(cidadeActual)) {
				cidades.add(cidadeProxima);
				cidadeActual = cidadeProxima;
			}

		}

		if (!bairros.isEmpty() && !cidades.isEmpty()) {
			ListModelList<String> bairroModel = new ListModelList<String>(
					bairros);
			ListModelList<String> cidadeModel = new ListModelList<String>(
					cidades);
			cb_bairro.setModel(bairroModel);
			cb_cidade.setModel(cidadeModel);
		}

	}

	@Listen("onCheck = #rg_relatorio")
	public void escolheRelatorio() {

		Radio escolha = rg_relatorio.getSelectedItem();
		if (escolha.equals(rd_dividas))
			tipoRelatorio = DIVIDA;
		else if (escolha.equals(rd_clientes))
			tipoRelatorio = CLIENTES;
		else if (escolha.equals(rd_pagamentos)) {
			tipoRelatorio = PAGAMENTOS;
			visualizaData();
		}

		habilitaCampos();

		Clients.showNotification("Escolheu este tipo de relatório", escolha);

	}

	@Listen("onCheck = #rg_categoria")
	public void escolheCategoria() {
		Radio escolha = rg_categoria.getSelectedItem();

		if (escolha.equals(rd_colectivo))
			categoria = COLECTIVO;
		else if (escolha.equals(rd_domestico))
			categoria = DOMESTICO;
		Clients.showNotification("Escolha este tipo de categoria de cliente",
				escolha);
	}

	@Listen("onSelect = #cb_bairro")
	public void escolheBairro() {
		Comboitem escolha = cb_bairro.getSelectedItem();
		bairro = (String) escolha.getValue();
	}

	@Listen("onSelect = #cb_cidade")
	public void escolheCidade() {
		Comboitem escolha = cb_cidade.getSelectedItem();
		cidade = (String) escolha.getValue();
	}

	@Listen("onSelect = #cb_periodo")
	public void escolhePeriodo() {
		Comboitem escolha = cb_periodo.getSelectedItem();
		String periodoStr = "Nenhum";

		if (escolha.getLabel().equalsIgnoreCase("Diariamente")) {
			periodo = DIARIO;
			periodoStr = "Diário";
			visualizaData();
		}
		if (escolha.getLabel().equals("Mensalmente")) {
			periodo = MENSAL;
			periodoStr = "Mensal";
			visualizaData();
		}

		if (escolha.getLabel().equalsIgnoreCase("Semanalmente")) {
			periodo = SEMANAL;
			periodoStr = "Semanal";
			visualizaData();
		}

		Clients.showNotification("Escolheu periodo " + periodoStr, cb_periodo);
	}

	@Listen("onClick = #bt_gerar")
	public void gerarRelatorio() {
		boolean gerou = false;
		if (selecaoValida()) {
			if (tipoRelatorio == PAGAMENTOS && selecaoValidaRelatorio()) {
				relatorioPagamentos();
				gerou = true;
			} else if (tipoRelatorio == DIVIDA && selecaoValidaRelatorio()) {
				geraRelatorioDivida();
				gerou = true;
			} else if (tipoRelatorio == CLIENTES) {
				geraRelatorioClientes();
				gerou = true;
			}

		} else if (gerou == false)
			Clients.showNotification(
					"Faça todas as escolhas: cidade,bairro, tipo de relatório e data caso necessário. \n"
							+ "Para poder visualizar o relatório",
					Clients.NOTIFICATION_TYPE_WARNING, vl_navegacao, null,
					2000, true);
		geraDescricao(gerou);
	}

	private boolean selecaoValida() {
		if (rg_categoria.getSelectedIndex() != -1
				&& rg_relatorio.getSelectedIndex() != -1)
			return true;
		else
			return false;
	}

	private boolean selecaoValidaRelatorio() {
		if (cb_bairro.getSelectedIndex() != -1
				&& cb_cidade.getSelectedIndex() != -1
				&& cb_periodo.getSelectedIndex() != -1)
			return true;
		else
			return false;
	}

	public void relatorioPagamentos() {
		geraRelatorioIntervalo();
	}

	public void geraRelatorioIntervalo() {
		Calendar ultimaData = new GregorianCalendar();
		Calendar primeiraData = new GregorianCalendar();
		if (periodo == DIARIO)
			ultimaData.setTime(db_primeira.getValue());
		else
			ultimaData.setTime(db_ultima.getValue());
		primeiraData.setTime(db_primeira.getValue());

		float totalPagamento = 0;
		SimpleDateFormat formato;
		if (periodo == DIARIO)
			formato = new SimpleDateFormat("EEEEEEE - dd/MM/yyyy");
		else if (periodo == SEMANAL)
			formato = new SimpleDateFormat("EEEEEEE");
		else
			formato = new SimpleDateFormat("dd/MM/yyyy");

		int ultimoDia, primeiroDia, diferencaDias;
		primeiroDia = primeiraData.get(Calendar.DAY_OF_YEAR);
		ultimoDia = ultimaData.get(Calendar.DAY_OF_YEAR);
		diferencaDias = ultimoDia - primeiroDia;

		if (periodo == SEMANAL && diferencaDias > 7) {
			Clients.alert("O intervalo que escolheu é maior que uma semana");
		} else if (periodo == MENSAL && diferencaDias > 31) {
			Clients.alert("O intervalo que escolheu é maior que um mês.");
		}

		else {
			Calendar dataActual = null, proximaData;
			List<TotalPagamento> listaPagamentos = new ArrayList<TotalPagamento>();
			List<Pagamento> lista = dao.pegaPagamento(primeiraData, ultimaData);
			Class classe;
			if (categoria == DOMESTICO)
				classe = ClienteDomestico.class;
			else
				classe = ClienteColectivo.class;

			if (!lista.isEmpty())
				dataActual = lista.get(0).getDataPagamento();
			boolean novoTotal = false;

			for (Iterator iterator = lista.iterator(); iterator.hasNext();) {

				Pagamento pagamento = (Pagamento) iterator.next();
				Cliente cliente = pagamento.getFactura().getLeituraContador()
						.getContador().getProprietario();
				if (cliente.getClass() == classe
						&& cliente.getEndereco().getBairro()
								.equalsIgnoreCase(bairro)) {
					if (novoTotal)
						dataActual = pagamento.getDataPagamento();
					proximaData = pagamento.getDataPagamento();
					if (dataActual.equals(proximaData)) {
						totalPagamento += pagamento.getValor_a_pagar();
						novoTotal = false;
						if (!iterator.hasNext()) {
							TotalPagamento pag = new TotalPagamento();
							pag.setDia(formato.format(dataActual.getTime()));
							pag.setTotal(totalPagamento);
							listaPagamentos.add(pag);
						}
					} else {
						TotalPagamento pag = new TotalPagamento();
						pag.setDia(formato.format(dataActual.getTime()));
						pag.setTotal(totalPagamento);
						listaPagamentos.add(pag);
						totalPagamento = pagamento.getValor_a_pagar();
						novoTotal = true;
					}
				}

				if (!listaPagamentos.isEmpty()) {
					insereNaTabela(listaPagamentos);
				} else {
					insereNaTabela(listaPagamentos);
				}

			}
		}
	}

	public void geraRelatorioDivida() {
		Calendar ultimaData = new GregorianCalendar();
		Calendar primeiraData = new GregorianCalendar();
		if (periodo == DIARIO)
			ultimaData.setTime(db_primeira.getValue());
		else
			ultimaData.setTime(db_ultima.getValue());
		primeiraData.setTime(db_primeira.getValue());

		float totalDivida = 0;
		SimpleDateFormat formato;
		if (periodo == DIARIO)
			formato = new SimpleDateFormat("EEEEEEE - dd/MM/yyyy");
		else if (periodo == SEMANAL)
			formato = new SimpleDateFormat("EEEEEEE");
		else
			formato = new SimpleDateFormat("dd/MM/yyyy");

		int ultimoDia, primeiroDia, diferencaDias;
		primeiroDia = primeiraData.get(Calendar.DAY_OF_YEAR);
		ultimoDia = ultimaData.get(Calendar.DAY_OF_YEAR);
		diferencaDias = ultimoDia - primeiroDia;

		if (periodo == SEMANAL && diferencaDias > 7) {
			Clients.alert("O intervalo que escolheu é maior que uma semana");
		} else if (periodo == MENSAL && diferencaDias > 31) {
			Clients.alert("O intervalo que escolheu é maior que um mês.");
		}

		else {
			Calendar dataActual = null, proximaData;
			List<TotalPagamento> listaPagamentos = new ArrayList<TotalPagamento>();
			List<Pagamento> lista = dao.pegaPagamento(primeiraData, ultimaData);
			Class classe;
			if (categoria == DOMESTICO)
				classe = ClienteDomestico.class;
			else
				classe = ClienteColectivo.class;

			if (!lista.isEmpty())
				dataActual = lista.get(0).getDataPagamento();
			boolean novoTotal = false;

			for (Iterator iterator = lista.iterator(); iterator.hasNext();) {

				Pagamento pagamento = (Pagamento) iterator.next();
				Cliente cliente = pagamento.getFactura().getLeituraContador()
						.getContador().getProprietario();
				if (cliente.getClass() == classe
						&& cliente.getEndereco().getBairro()
								.equalsIgnoreCase(bairro)) {
					if (novoTotal)
						dataActual = pagamento.getDataPagamento();
					proximaData = pagamento.getDataPagamento();
					if (dataActual.equals(proximaData)) {
						totalDivida += pagamento.getDivida();
						novoTotal = false;
						if (!iterator.hasNext()) {
							TotalPagamento pag = new TotalPagamento();
							pag.setDia(formato.format(dataActual.getTime()));
							pag.setTotal(totalDivida);
							listaPagamentos.add(pag);
						}
					} else {
						TotalPagamento pag = new TotalPagamento();
						pag.setDia(formato.format(dataActual.getTime()));
						pag.setTotal(totalDivida);
						listaPagamentos.add(pag);
						totalDivida = pagamento.getDivida();
						novoTotal = true;
					}
				}

				if (!listaPagamentos.isEmpty()) {
					insereNaTabela(listaPagamentos);
				} else {
					insereNaTabela(listaPagamentos);
				}

			}
		}
	}

	public void habilitaCampos() {
		if (tipoRelatorio == DIVIDA || tipoRelatorio == PAGAMENTOS)
			desactivaCamposNavegacao(false);
		else if (tipoRelatorio == CLIENTES)
			desactivaCamposNavegacao(true);
	}

	public void geraRelatorioClientes() {
		long numeroCliente = 0;
		String tipoCliente = "";
		if (categoria == DOMESTICO) {
			numeroCliente = daoDomestico.getNumeroClientes();
			tipoCliente = "Domestico";
		} else if (categoria == COLECTIVO) {
			numeroCliente = daoColectivo.getNumeroClientes();
			tipoCliente = "Colectivo";
		}

		if (tipoCliente.length() > 1) {
			TotalPagamento total = new TotalPagamento();
			total.setDia(tipoCliente);
			total.setTotal(numeroCliente);
			List<TotalPagamento> lista = new ArrayList<TotalPagamento>();
			lista.add(total);
			ListModelList<TotalPagamento> modeloPagamento = new ListModelList<TotalPagamento>(
					lista);

			if (dv_conteudo.getFirstChild() != null)
				dv_conteudo.removeChild(dv_conteudo.getFirstChild());
			Listbox lb_clientes = (Listbox) Executions.createComponents(
					"/gestao/totalClientes.zul", dv_conteudo, null);
			lb_clientes.setModel(modeloPagamento);
		}
	}

	public void insereNaTabela(List<TotalPagamento> lista) {
		ListModelList<TotalPagamento> modeloPagamento = new ListModelList<TotalPagamento>(
				lista);
		if (dv_conteudo.getFirstChild() != null)
			dv_conteudo.removeChild(dv_conteudo.getFirstChild());
		Listbox lb_pagamentos = (Listbox) Executions.createComponents(
				"/gestao/totalPagamento.zul", dv_conteudo, null);
		lb_pagamentos.setModel(modeloPagamento);
	}

	public void visualizaData() {
		Label de = new Label();
		if (periodo == DIARIO)
			de.setValue("Escolha o dia:");
		else
			de.setValue("De:");
		db_primeira = new Datebox();
		db_primeira
				.setConstraint("no empty: Escolho o dia que pretende ver o seu respectivo relatório");
		Label ate = new Label(" à ");
		if (!vb_adicional.getChildren().isEmpty())
			vb_adicional.getChildren().clear();
		de.setParent(vb_adicional);
		db_primeira.setParent(vb_adicional);
		if (periodo != DIARIO) {
			db_primeira
					.setConstraint("no empty: Escolho o primeiro apartir do qual pretende visualizar o seu relatório");
			db_ultima = new Datebox(new GregorianCalendar().getTime());
			db_ultima
					.setConstraint("no empty: Escolho o ultimo apartir do qual pretende visualizar o seu relatório");
			ate.setParent(vb_adicional);
			db_ultima.setParent(vb_adicional);
		}
	}

	public void desactivaCamposNavegacao(boolean bool) {
		cb_bairro.setDisabled(bool);
		cb_cidade.setDisabled(bool);
		cb_periodo.setDisabled(bool);
		if (db_primeira != null)
			db_primeira.setDisabled(bool);
		if (db_ultima != null)
			db_ultima.setDisabled(bool);
	}

	public void geraDescricao(boolean bool) {
		if (bool) {
			String relatorio = "", data, breveDescricao, descricao = "";
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			data = formato.format(new GregorianCalendar().getTime());
			breveDescricao = "Descrição";
			if (tipoRelatorio == DIVIDA) {
				relatorio = "Relatório de Dividas";
				descricao = "Este relatório lista todas as dividas dos "
						+ "clientes registados no sistema de acordo com a cidade e bairro ";
			} else if (tipoRelatorio == PAGAMENTOS) {
				relatorio = "Relatório de Pagamentos";
				descricao = "Este relatório lista todos os pagamentos dos "
						+ "clientes registados no sistema de acordo com a cidade e bairro ";
			} else if (tipoRelatorio == CLIENTES) {
				relatorio = "Relatório dos clientes";
				descricao = "Este relatório mostra o total de "
						+ "clientes registados no sistema.";
			}

			lb_tipoRelatorio.setValue(relatorio);
			lb_dataRelatorio.setValue(data);
			lb_breveDescricao.setValue(breveDescricao);
			tb_descricao.setText(descricao);
		}
	}
}
