package mz.co.hidroinfo.model;

import java.util.Calendar;

public class Mensagem {
	private String mensagem;
	private boolean confirmacao;
	private Calendar Data_envio;
	private Cliente cliente;
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
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	
	
}
