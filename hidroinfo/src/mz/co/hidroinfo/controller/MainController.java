package mz.co.hidroinfo.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import mz.co.hidroinfo.dao.*;
import mz.co.hidroinfo.model.*;

public class MainController {
	
	
	public static void main (String [] args){
		
		
		
		
		
		
		
		
		
		
		
		ClienteDao  dao = new ClienteDao();
		
		Cliente cliente = dao.findById(1);
		
		
		
		
		
		System.out.println("Registando pag");
		/*OperadorDao dao = new OperadorDao();
		Operador op = dao.findById(2);
		PagamentoDao pagDao = new PagamentoDao();
		Pagamento pag = new Pagamento();
		pag.setDataPagamento(new GregorianCalendar());
		pag.setDivida(1500);
		pag.setMulta(1999);
		pag.setValor_a_pagar(800);
		pag.setOperador(op);
		pagDao.create(pag);
		System.out.println("Concluido registo");
		
		Calendar dataPagamento = new GregorianCalendar();
		dataPagamento.set(2014, 10, 05);
		Date data = new Date();*/
		
		/*ClienteDao da=new ClienteDao();
		Cliente cli= da.findById(1);
		ReclamacaoDao recDao=new ReclamacaoDao();
		Reclamacao recc=new Reclamacao();
		recc.setAssunto("alooooo");
		recc.setData(new GregorianCalendar());
		recc.setMensagem("srtyfughlyhgtrfdskaljsdxvbgfvbhdyesddhuahjdhesfiufubvbdjfhiuweirufghwefskldffweoirfweufhdsfjvkscjjasodhwefsdbcnskjcxewiufbsvc,bsd,cjiwudgwefkewmndlqwjrwehfskjcadjaewufewrgfkeff");
		recc.setCliente(cli);
		recDao.create(recc);
		System.out.println("Concluido registo");*/
		System.out.println("ola");
		/*FacturaDao daoF=new FacturaDao();
		Factura fa=new Factura();
		fa.setDataEmissao(new GregorianCalendar());
		fa.setDataLimite(new GregorianCalendar());
		fa.setPeriodoFacturacao("12");
		fa.setValorAPagar(8000);
		daoF.create(fa);
		System.out.println("concluido");*/
		
		Date data=new GregorianCalendar().getTime();
		SimpleDateFormat form=new SimpleDateFormat("dd-MM-yyy");
		form.format(data);
		System.out.println("data: "+form.format(data));
		
		//Calendar a=Calendar.getInstance();
		
	//	data.setTime(a.getTimeInMillis());
		
		/*Operador op = new OperadorDao().findById(2);
		System.out.println("O operador "+op.getNome()+" "+cobradoPor(a, op)+"  meticais");
		System.out.println(data);
		*/
		
		//List<Operador> operadores = new OperadorDao().pagamento(a);
		//Operador op = operadores.get(0);
		//System.out.println("Pagamento do operador "+op.getNome()+" é "+op.getPagamento().get(0).getValor_a_pagar());\
		/*MontanteDao dao = new MontanteDao();
		List<Montante> montantes =dao.pagamentoPorOperador(a);
		for (Iterator iterator = montantes.iterator(); iterator.hasNext();) {
			Montante montante = (Montante) iterator.next();
			System.out.println("\n"+montante.getValorCobrado()+"Cobrado por "+montante.getOperador().getNome()+"  \n");
		}*/
	}
	
	
	
	
	public static float cobradoPor (Calendar data, Operador operador) {
		List<Pagamento> list = new PagamentoDao().pegaPagamento(data);
		System.out.println("Numero de pagamento "+list.size());
		int id_operador = operador.getId();
		System.out.println("operador"+id_operador);
		float montante=0;
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Pagamento pagamento = (Pagamento) iterator.next();
			
			if(pagamento.getOperador().getId()==id_operador){
				montante +=pagamento.getValor_a_pagar();
			}
			
		}
		
		return montante;
	}
	public static List<Montante> pagamentoPorOperador (Calendar data){
		OperadorDao dao = new OperadorDao();
		List<Operador> operadores = dao.pagamento(data);
		float montanteCobrado=0;

		List<Montante> montantes = new ArrayList<Montante>();
		
		
		for (Iterator iterator = operadores.iterator(); iterator.hasNext();) {
			Operador operador = (Operador) iterator.next();
			List<Pagamento> pagamentos = operador.getPagamento();
			
			
			for (Iterator iterator2 = pagamentos.iterator(); iterator2
					.hasNext();) {
				Pagamento pagamento = (Pagamento) iterator2.next();
				montanteCobrado +=pagamento.getValor_a_pagar();
				
			}
			
			
			Montante montante = new Montante();
			montante.setValorCobrado(montanteCobrado);
			montante.setOperador(operador);
			montantes.add(montante);
			
			montanteCobrado=0;
			
		}
		return montantes;
	}
	
}
