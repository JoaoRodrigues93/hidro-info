package mz.co.hidroinfo.controller;

import java.io.IOException;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Center;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.North;
import org.zkoss.zul.Window;

public class MenuController extends SelectorComposer<Component> {
	
	@Wire
	private Button bt_pagamentos;
	@Wire
	private Button bt_clientes;
	@Wire
	private Button bt_funcionarios;
	@Wire
	private Button bt_relatorios;
	@Wire
	private Button bt_notificacoes;
	@Wire
	private Button bt_administracao;
	@Wire
	private Button bt_ajuda;
	@Wire
	private North nt_menu;
	@Wire
	private Center ct_conteudo;
	@Wire
	private Window wd_menu;
	private final int PAGAMENTOS=1;
	private final int CLIENTES=2;
	private final int FUNCIONARIOS=3;
	private final int RELATORIOS=4;
	private final int NOTIFICAOES=5;
	private final int ADMINISTRACAO=6;
	private final int AJUDA=7;
	
	private final String PAGAMENTOSZUL="/gestao/pagamento.zul";
	private final String CLIENTESZUL="/registos/cliente.zul";
	private final String FUNCIONARIOSZUL="/gestao/mini_menu.zul";
	private final String RELATORIOSZUL="/gestao/relatorios.zul";
	private final String NOTIFICAOESZUL="/gestao/menu_reclamacao.zul";
	private final String ADMINISTRACAOZUL="/administracao/administracao.zul";
	private final String AJUDAZUL="/ajuda.zul";
	private int escolhaAnterior=0;
	private int escolha=0;
	private final int ESCOLHA_DEFAULT=2;
	private String escolhaZul="";
	private Button bt_default;
	@Wire
	private Label lb_sair;
	@Wire
	private Label lb_usuario;
	
	public void escolha(){
		switch(escolha){
		case 1: escolhaZul=PAGAMENTOSZUL;
				bt_pagamentos.setZclass("btn  btn-lg btn-primary btn-active");
		break;
		case 2: escolhaZul = CLIENTESZUL; 
		bt_clientes.setZclass("btn  btn-lg btn-primary btn-active");
		break;
		case 3: escolhaZul=FUNCIONARIOSZUL; 
		bt_funcionarios.setZclass("btn  btn-lg btn-primary btn-active");
		break;
		case 4: escolhaZul = RELATORIOSZUL; 
		bt_relatorios.setZclass("btn  btn-lg btn-primary btn-active");
		break;
		case 5: escolhaZul=NOTIFICAOESZUL; 
		bt_notificacoes.setZclass("btn  btn-lg btn-primary btn-active");
		break;
		case 6: escolhaZul = ADMINISTRACAOZUL;
		bt_administracao.setZclass("btn  btn-lg btn-primary btn-active");
		break;
		case 7: escolhaZul=AJUDAZUL;
		bt_ajuda.setZclass("btn  btn-lg btn-primary btn-active");
		break;
		}
		escolheConteudo();
		escolhaAnterior();
	}
	
	public void escolheConteudo(){
		Component filhoActual= ct_conteudo.getFirstChild();
		if(filhoActual!=null)
			ct_conteudo.removeChild(filhoActual);
		Component filhoNovo = Executions.createComponents(escolhaZul, ct_conteudo, null);
	}
	
	@Override
	public void doAfterCompose(Component comp){
		try {
			super.doAfterCompose(comp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(LoginController.logado)
		login();
		else
			
		{	
			Executions.sendRedirect("/index.zul");
		}
	}
	
	@Listen("onClick = #bt_pagamentos")
	public void escolhePagamentos(){
		selecionaBotao(bt_pagamentos, PAGAMENTOS);
	}
	
	@Listen("onClick = #bt_clientes")
	public void escolheCliente(){
		selecionaBotao(bt_clientes, CLIENTES);
	}
	
	@Listen("onClick = #bt_relatorios")
	public void escolheRelatorio(){
		selecionaBotao(bt_relatorios, RELATORIOS);
	}
	
	@Listen("onClick = #bt_notificacoes")
	public void escolheNotificacoes(){
		selecionaBotao(bt_notificacoes, NOTIFICAOES);
	}
	
	@Listen("onClick = #bt_funcionarios")
	public void escolheFuncionarios(){
		selecionaBotao(bt_funcionarios, FUNCIONARIOS);
	}
	
	@Listen("onClick = #bt_administracao")
	public void escolheAdministracao(){
		selecionaBotao(bt_administracao, ADMINISTRACAO);
	}
	
	@Listen("onClick = #bt_ajuda")
	public void escolheAjuda(){
		selecionaBotao(bt_ajuda, AJUDA);
	}
	
	@Listen ("onClick = #lb_sair")
	public void sair(){
		LoginController.logado=false;
		Executions.createComponents("/index.zul", null, null);
		wd_menu.detach();
	}
	
	public void escolhaAnterior(){
		Button botaoAnterior=null;
		
		switch(escolhaAnterior){
		case 1: escolhaZul=PAGAMENTOSZUL;
				botaoAnterior = bt_pagamentos;
		break;
		case 2: escolhaZul = CLIENTESZUL; 
		botaoAnterior = bt_clientes;
		break;
		case 3: escolhaZul=FUNCIONARIOSZUL; 
		botaoAnterior = bt_funcionarios;
		break;
		case 4: escolhaZul = RELATORIOSZUL; 
		botaoAnterior = bt_relatorios;
		break;
		case 5: escolhaZul=NOTIFICAOESZUL; 
		botaoAnterior = bt_notificacoes;
		break;
		case 6: escolhaZul = ADMINISTRACAOZUL;
		botaoAnterior = bt_administracao;
		break;
		case 7: escolhaZul=AJUDAZUL;
		botaoAnterior = bt_ajuda;
		break;
		}
	if(botaoAnterior!=null){
	botaoAnterior.setZclass("btn  btn-lg btn-default");
	botaoAnterior.setDisabled(false);
	}
	}
	
	public void selecionaBotao (Button botaoEscolhido, int opcaoEscolhida){
		escolhaAnterior=escolha;
		escolha = opcaoEscolhida;
		botaoEscolhido.setDisabled(true);
		escolha();
	}
	
	public void login(){
		if(LoginController.usuario==LoginController.OPERADOR)
			escolheMenuOperador();
		else if(LoginController.usuario==LoginController.ADMINISTRADOR)
			escolheMenuAdministrador();
		
		lb_usuario.setValue(LoginController.user.getUsername());
	}
	
	public void escolheMenuOperador(){
		bt_administracao.setDisabled(true);
		bt_relatorios.setDisabled(true);
		bt_funcionarios.setDisabled(true);
	}
	
	public void escolheMenuAdministrador(){
		bt_clientes.setDisabled(false);
		bt_pagamentos.setDisabled(true);
	}
}
