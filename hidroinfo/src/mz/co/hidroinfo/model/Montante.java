package mz.co.hidroinfo.model;

public class Montante {

	private Operador operador;
	private float valorCobrado;
	public Operador getOperador() {
		return operador;
	}
	public void setOperador(Operador operador) {
		this.operador = operador;
	}
	public float getValorCobrado() {
		return valorCobrado;
	}
	public void setValorCobrado(float valorCobrado) {
		this.valorCobrado = valorCobrado;
	}
	
	@Override
	public String toString() {
		return "Montante [operador=" + operador + ", valorCobrado="
				+ valorCobrado + "]";
	}
	
	
}
