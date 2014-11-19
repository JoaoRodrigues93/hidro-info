package mz.co.hidroinfo.model;

public class TotalPagamento {
	private String dia;
	private float total;
	
	
	
	public TotalPagamento(String dia, float total) {
		super();
		this.dia = dia;
		this.total = total;
	}
	
	
	public TotalPagamento() {
		
	}


	public String getDia() {
		return dia;
	}
	public void setDia(String dia) {
		this.dia = dia;
	}
	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = total;
	}
	
	
}
