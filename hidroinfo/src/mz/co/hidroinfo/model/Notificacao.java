package mz.co.hidroinfo.model;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Notificacao{
	@Id @GeneratedValue (strategy = GenerationType.AUTO)
	protected int id;
	protected String asssunto;
	protected String mensagem;
	
	public Notificacao(){}
		
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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
