package mz.co.hidroinfo.controller;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Center;
import org.zkoss.zul.Div;

public class Menu_reclamacaoController extends SelectorComposer<Component>{

@Wire
private Button btn_reclamacao;
@Wire
private Button btn_alertas;
@Wire
private Button btn_notificacao;
@Wire
private Button btn_factura;
@Wire
private Center pagina;

private int tipoMenu;
private final int ALERTAS=0;
private final int RECLAMACAO=1;
private final int NOTIFICACAO=2;
private final int FATURA=3;


public Menu_reclamacaoController(){

	tipoMenu=NOTIFICACAO;
	
}

@Override 
public void doAfterCompose(Component comp) throws Exception{
	super.doAfterCompose(comp);
	setNotificacao();
}

@Listen ("onClick=#btn_reclamacao")
public void setReclamacao (){
	setReclamacaoAttributes();
	if(pagina.getFirstChild()!=null)
		pagina.removeChild(pagina.getFirstChild());
	Div div =(Div) Executions.createComponents("/gestao/reclamacao.zul", pagina, null);
}

@Listen ("onClick = #btn_alertas")
public void setAlertas(){
	setAlertasAttributes();
	if(pagina.getFirstChild()!=null)
		pagina.removeChild(pagina.getFirstChild());
	Component comp = Executions.createComponents("/gestao/avisoDeCorte.zul", pagina, null);
}
@Listen ("onClick = #btn_notificacao")
public void setNotificacao(){
	setNotificacaoAttributes();
	if(pagina.getFirstChild()!=null)
		pagina.removeChild(pagina.getFirstChild());
	Component comp = Executions.createComponents("/gestao/notificacao.zul", pagina, null);
}
@Listen ("onClick = #btn_factura")
public void setFactura(){
	setFacturaAttributes();
	if(pagina.getFirstChild()!=null)
		pagina.removeChild(pagina.getFirstChild());
	Component comp = Executions.createComponents("/gestao/enviarFactura.zul", pagina, null);
}

public void setReclamacaoAttributes (){
	tipoMenu = RECLAMACAO;
	btn_reclamacao.setDisabled(true);
	btn_alertas.setDisabled(false);
	btn_notificacao.setDisabled(false);
	btn_factura.setDisabled(false);
	alteraEstilo(btn_reclamacao, btn_notificacao, btn_factura, btn_alertas);
}
public void setFacturaAttributes (){
	tipoMenu = FATURA;
	btn_reclamacao.setDisabled(false);
	btn_alertas.setDisabled(false);
	btn_notificacao.setDisabled(false);
	btn_factura.setDisabled(true);
	alteraEstilo(btn_factura, btn_notificacao,btn_reclamacao, btn_alertas);
}

public void setNotificacaoAttributes (){
	tipoMenu = NOTIFICACAO;
	btn_reclamacao.setDisabled(false);
	btn_alertas.setDisabled(false);
	btn_notificacao.setDisabled(true);
	btn_factura.setDisabled(false);
	alteraEstilo(btn_notificacao, btn_factura,btn_reclamacao, btn_alertas);
}
public void setAlertasAttributes (){
	tipoMenu = ALERTAS;
	btn_reclamacao.setDisabled(false);
	btn_alertas.setDisabled(true);
	btn_notificacao.setDisabled(false);
	btn_notificacao.setDisabled(false);
	alteraEstilo(btn_alertas, btn_notificacao,btn_reclamacao, btn_factura);
}
	
	public void alteraEstilo(Button botaoPrim, Button botaoSec1, Button botaoSec2, Button botaoSec3){
		botaoPrim.setZclass("btn btn-primary");
		botaoSec1.setZclass("btn btn-default");
		botaoSec2.setZclass("btn btn-default");
		botaoSec3.setZclass("btn btn-default");
	}

}