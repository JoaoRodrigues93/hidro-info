package mz.co.hidroinfo.model;



import org.hibernate.Hibernate;
import org.hibernate.ejb.HibernateEntityManager;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class Funcionario {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private String nome;
	private String bi; 
	private int nuit;
	private int telefone;
	private String email;
	
	

	
	@Column(name="nome")
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Column(name="bi")
	public String getBi() {
		return bi;
	}
	public void setBi(String bi) {
		this.bi = bi;
	}
	
	@Column(name="nuit")
	public int getNuit() {
		return nuit;
	}
	public void setNuit(int nuit) {
		this.nuit = nuit;
	}
	
	
	public int getTelefone() {
		return telefone;
	}
	public void setTelefone(int telefone) {
		this.telefone = telefone;
	}
	
	@Column(name="email")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	
	

	

}
