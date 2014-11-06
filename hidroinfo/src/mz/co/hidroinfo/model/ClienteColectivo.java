package mz.co.hidroinfo.model;

import javax.persistence.Entity;

@Entity
public class ClienteColectivo extends Cliente {
	private String nome;
	private String representante;
	
	
	public String getNome() {
		return nome;
	}



	public void setNome(String nome) {
		this.nome = nome;
	}



	public String getRepresentante() {
		return representante;
	}



	public void setRepresentante(String representante) {
		this.representante = representante;
	}



	@Override
	public String toString() {
		return "ClienteColectivo [nome=" + nome + ", representante="
				+ representante + ", id=" + id + ", nuit=" + nuit
				+ ", endereco=" + endereco + ", contacto=" + contacto + "]";
	}


}
