package mz.co.hidroinfo.model;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;


@Entity
public class Cliente {
	
	@Id @GeneratedValue (strategy = GenerationType.AUTO)
	protected int id;
	protected int nuit;
	@Embedded
	protected Endereco endereco;
	@Embedded
	protected Contacto contacto;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNuit() {
		return nuit;
	}
	public void setNuit(int nuit) {
		this.nuit = nuit;
	}
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	public Contacto getContacto() {
		return contacto;
	}
	public void setContacto(Contacto contacto) {
		this.contacto = contacto;
	}
	
	
}
