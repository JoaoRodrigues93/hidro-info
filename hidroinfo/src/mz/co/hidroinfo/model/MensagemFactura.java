package mz.co.hidroinfo.model;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class MensagemFactura {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String mensagem;
	private boolean confirmacao;
	private Calendar Data_envio;
	@OneToOne
	private Factura factura;
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Factura getFactura() {
		return factura;
	}
	public void setFactura(Factura factura) {
		this.factura = factura;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public boolean isConfirmacao() {
		return confirmacao;
	}
	public void setConfirmacao(boolean confirmacao) {
		this.confirmacao = confirmacao;
	}
	public Calendar getData_envio() {
		return Data_envio;
	}
	public void setData_envio(Calendar data_envio) {
		Data_envio = data_envio;
	}
	
	
	
}
