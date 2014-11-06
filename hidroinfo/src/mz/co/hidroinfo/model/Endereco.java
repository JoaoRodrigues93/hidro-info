package mz.co.hidroinfo.model;

import javax.persistence.Embeddable;


@Embeddable
public class Endereco {
	

	private String cidade;
	private String bairro;
	private String avenida;
	private String rua;
	private String quarteirao;
	private int codigoLocal;
	private int casaNumero;
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getAvenida() {
		return avenida;
	}
	public void setAvenida(String avenida) {
		this.avenida = avenida;
	}
	public String getRua() {
		return rua;
	}
	public void setRua(String rua) {
		this.rua = rua;
	}
	public String getQuarteirao() {
		return quarteirao;
	}
	public void setQuarteirao(String quarteirao) {
		this.quarteirao = quarteirao;
	}
	public int getCodigoLocal() {
		return codigoLocal;
	}
	public void setCodigoLocal(int codigoLocal) {
		this.codigoLocal = codigoLocal;
	}
	public int getCasaNumero() {
		return casaNumero;
	}
	public void setCasaNumero(int casaNumero) {
		this.casaNumero = casaNumero;
	}
	@Override
	public String toString() {
		return "Endereco [cidade=" + cidade + ", bairro=" + bairro
				+ ", avenida=" + avenida + ", rua=" + rua + ", quarteirao="
				+ quarteirao + ", codigoLocal=" + codigoLocal + ", casaNumero="
				+ casaNumero + "]";
	}
	
	
}
