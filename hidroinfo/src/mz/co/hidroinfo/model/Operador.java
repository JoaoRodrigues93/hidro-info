package mz.co.hidroinfo.model;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Operador extends Funcionario {
	@OneToMany (mappedBy="operador")
	private List <Pagamento> pagamento;
	private String username;
	private String password;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List <Pagamento> getPagamento() {
		return pagamento;
	}
	public void setPagamento(List <Pagamento> pagamento) {
		this.pagamento = pagamento;
	}
	
}
