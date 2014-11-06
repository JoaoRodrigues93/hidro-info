package mz.co.hidroinfo.model;

import javax.persistence.Entity;

@Entity
public class ClienteDomestico extends Cliente {
	private String nome;
	private String bi;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getBi() {
		return bi;
	}
	public void setBi(String bi) {
		this.bi = bi;
	}
	
	@Override
	public String toString() {
		return "ClienteDomestico [nome=" + nome + ", bi=" + bi + ", id=" + id
				+ ", nuit=" + nuit + ", endereco=" + endereco + ", contacto="
				+ contacto + "]";
	}
	
	
	
	
	
	
}
