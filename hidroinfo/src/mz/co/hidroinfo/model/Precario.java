package mz.co.hidroinfo.model;




import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity

public class Precario {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	int id;
	private int taxa_fixa;
	private int taxa_por_metro_cubico; 
	private String tipo_de_tarifa;
	
	
	public int getTaxa_fixa() {
		return taxa_fixa;
	}
	public void setTaxa_fixa(int taxa_fixa) {
		this.taxa_fixa = taxa_fixa;
	}
	
	public int getTaxa_por_metro_cubico() {
		return taxa_por_metro_cubico;
	}
	public void setTaxa_por_metro_cubico(int taxa_por_metro_cubico) {
		this.taxa_por_metro_cubico = taxa_por_metro_cubico;
	}
	
	public String getTipo_de_tarifa() {
		return tipo_de_tarifa;
	}
	public void setTipo_de_tarifa(String tipo_de_tarifa) {
		this.tipo_de_tarifa = tipo_de_tarifa;
	}
	
	

	
	
	

	

}



