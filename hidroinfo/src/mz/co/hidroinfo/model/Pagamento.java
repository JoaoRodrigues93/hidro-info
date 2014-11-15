package mz.co.hidroinfo.model;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Pagamento {
	@Id @GeneratedValue (strategy = GenerationType.AUTO)
	private int id;
	private float divida;
	//private float multa;
	private float valor_a_pagar;
@Temporal(TemporalType.DATE)
	private Calendar dataPagamento;
	@ManyToOne
	private Operador operador;
	
	@OneToOne
	private Factura factura;

	
	
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Operador getOperador() {
		return operador;
	}

	public void setOperador(Operador operador) {
		this.operador = operador;
	}

	

	public Calendar getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Calendar dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public float getDivida() {
		return divida;
	}

	public void setDivida(float divida) {
		this.divida = divida;
	}

	

	public float getValor_a_pagar() {
		return valor_a_pagar;
	}

	public void setValor_a_pagar(float valor_a_pagar) {
		this.valor_a_pagar = valor_a_pagar;
	}

	@Override
	public String toString() {
		return "Pagamento [id=" + id + ", divida=" + divida + ", multa="
			 + ", valor_a_pagar=" + valor_a_pagar
				+ ", dataPagamento=" + dataPagamento + ", operador=" + operador
				+ "]";
	}

	public Factura getFactura() {
		return factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}

	
}
