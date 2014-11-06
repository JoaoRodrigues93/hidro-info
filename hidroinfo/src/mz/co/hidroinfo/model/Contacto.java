package mz.co.hidroinfo.model;

import javax.persistence.Embeddable;


@Embeddable
public class Contacto {
	private String telefone;
	private String email;
	
	
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return "Contacto [telefone=" + telefone + ", email=" + email
				+ "]";
	}
	
	
	
}
