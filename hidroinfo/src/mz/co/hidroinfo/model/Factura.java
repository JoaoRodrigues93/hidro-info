package mz.co.hidroinfo.model;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



@Entity
public class Factura {
	
	@Id @GeneratedValue (strategy = GenerationType.AUTO)
	private int id;
	@Temporal (TemporalType.DATE)
	private Calendar dataLimite;
	@Temporal (TemporalType.DATE)
	private Calendar dataEmissao;
	private String periodoFacturacao;
	private float valorAPagar;
	@OneToOne
	private LeituraContador leituraContador;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Calendar getDataLimite() {
		return dataLimite;
	}
	public void setDataLimite(Calendar dataLimite) {
		this.dataLimite = dataLimite;
	}
	public Calendar getDataEmissao() {
		return dataEmissao;
	}
	public void setDataEmissao(Calendar dataEmissao) {
		this.dataEmissao = dataEmissao;
	}
	public String getPeriodoFacturacao() {
		return periodoFacturacao;
	}
	public void setPeriodoFacturacao(String periodoFacturacao) {
		this.periodoFacturacao = periodoFacturacao;
	}
	public float getValorAPagar() {
		return valorAPagar;
	}
	public void setValorAPagar(float valorAPagar) {
		this.valorAPagar = valorAPagar;
	}
	public LeituraContador getLeituraContador() {
		return leituraContador;
	}
	public void setLeituraContador(LeituraContador leituraContador) {
		this.leituraContador = leituraContador;
	}
	
	
}
