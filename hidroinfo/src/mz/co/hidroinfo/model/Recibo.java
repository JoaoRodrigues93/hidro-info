package mz.co.hidroinfo.model;

import java.util.Calendar;
import java.util.Date;

public class Recibo {

	private int codRecibo;
	private Calendar dataImpressao;
	private Pagamento pagamento;
	
	public Recibo(){}
	
	public Recibo(int codRecibo, Calendar dataImpressao, Pagamento pagamento) {
		super();
		
		this.codRecibo = codRecibo;
		this.dataImpressao = dataImpressao;
		this.pagamento = pagamento;
	}

	public int getCodRecibo() {
		return codRecibo;
	}

	public void setCodRecibo(int codRecibo) {
		this.codRecibo = codRecibo;
	}

	public Calendar getDataImpressao() {
		return dataImpressao;
	}

	public void setDataImpressao(Calendar dataImpressao) {
		this.dataImpressao = dataImpressao;
	}
	
	public Pagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}
	
}
