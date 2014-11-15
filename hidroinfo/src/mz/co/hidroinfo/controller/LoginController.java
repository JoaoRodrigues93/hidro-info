package mz.co.hidroinfo.controller;

import java.util.List;

import mz.co.hidroinfo.dao.OperadorDao;
import mz.co.hidroinfo.model.Operador;









import mz.co.hidroinfo.model.Usuario;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.sys.SessionsCtrl;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.SessionInit;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class LoginController extends SelectorComposer<Component> {
	
	@Wire
	private Button bt_login;
	@Wire
	private Textbox tb_username;
	@Wire
	private Textbox tb_password;
	@Wire
	private Window wdw_login;
	
	private OperadorDao dao;
	public static int usuario=0;
	public static final int OPERADOR=1;
	public static final int ADMINISTRADOR=2;
	public static Operador operadorActual;
	public static Usuario user;
	public static boolean logado=false;
	
	
	public LoginController (){
		dao = new OperadorDao();
		//daoAdmin = new AdministradorDao();
		if(logado)
			Executions.sendRedirect("/menu.zul");
	}
	
	public void doAfterCompose(Component comp){
		try {
			super.doAfterCompose(comp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Listen ("onClick = #bt_login; onOK = #tb_username; onOK = #tb_password")
	public void login (){
		String username,password;
		user = new Usuario();
		username = tb_username.getText();
		password = tb_password.getText();
		
		List<Operador> lista = dao.obtemPorUsername(username, password);
		Operador operador = null;
		//List<Administrador> lista = dao.obtemPorUsername(username, password);
		//AdministradorDao adminstrador;
		if(!lista.isEmpty()){
		operador = lista.get(0);
		if(operador !=null){
		abrePaginaOperador(operador);
		operadorActual = operador;
		username= operador.getNome();
		password = operador.getPassword();
		user.setPassword(password);
		user.setUsername(username);
		logado=true;
		}
		}
		else
		if(username.equalsIgnoreCase("admin") && password.equalsIgnoreCase("1234")){
			user.setPassword(password);
			user.setUsername(username);
			logado =true;
			abreAdministrador();
		}
		else
			Clients.showNotification("Usuario ou palavra-passe invalidos tente novamente",bt_login);
		
	}
	
	private void abreAdministrador() {
		usuario=ADMINISTRADOR;
		Executions.sendRedirect("/menu.zul");
	}

	public void abrePaginaOperador (Operador operador){
		usuario=OPERADOR;
		Executions.sendRedirect("/menu.zul");
	}
}
