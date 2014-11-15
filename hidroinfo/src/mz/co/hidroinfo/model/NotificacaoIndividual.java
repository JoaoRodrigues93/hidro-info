package mz.co.hidroinfo.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;


@Entity
public class NotificacaoIndividual extends Notificacao{
	@OneToOne
	private Cliente cliente;

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
}
