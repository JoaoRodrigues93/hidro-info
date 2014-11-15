package mz.co.hidroinfo.model;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MensagemAviso {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	protected int id;
	
	protected String mensagem;
	protected boolean confirmacao;
	protected Calendar Data_envio;
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
