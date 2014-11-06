package mz.co.hidroinfo.model;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class LeituraContador {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int leituraAnterior;
	private int leituraActual;
	@Temporal (TemporalType.DATE)
	private Calendar dataAnterior;
	@Temporal (TemporalType.DATE)
	private Calendar dataActual;
	@OneToOne
	@JoinColumn
	private Contador contador;
	@ManyToOne
	@JoinColumn
	private Leitor leitor;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getLeituraAnterior() {
		return leituraAnterior;
	}
	public void setLeituraAnterior(int leituraAnterior) {
		this.leituraAnterior = leituraAnterior;
	}
	public int getLeituraActual() {
		return leituraActual;
	}
	public void setLeituraActual(int leituraActual) {
		this.leituraActual = leituraActual;
	}
	public Calendar getDataAnterior() {
		return dataAnterior;
	}
	public void setDataAnterior(Calendar dataAnterior) {
		this.dataAnterior = dataAnterior;
	}
	public Calendar getDataActual() {
		return dataActual;
	}
	public void setDataActual(Calendar dataActual) {
		this.dataActual = dataActual;
	}
	public Contador getContador() {
		return contador;
	}
	public void setContador(Contador contador) {
		this.contador = contador;
	}
	public Leitor getLeitor() {
		return leitor;
	}
	public void setLeitor(Leitor leitor) {
		this.leitor = leitor;
	}
	
}
