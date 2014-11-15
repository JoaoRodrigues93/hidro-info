package mz.co.hidroinfo.model;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Notificacao {
	@Id @GeneratedValue (strategy = GenerationType.AUTO)
	private int id;
	
	
	private String asssunto;
	
	private String mensagem;
	
	public Notificacao(){}
	
	public String getAsssunto() {
		return asssunto;
	}
	public void setAsssunto(String asssunto) {
		this.asssunto = asssunto;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
}
