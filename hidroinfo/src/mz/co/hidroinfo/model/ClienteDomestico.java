package mz.co.hidroinfo.model;

import javax.persistence.Entity;

@Entity
public class ClienteDomestico extends Cliente {
	
	private String bi;
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
