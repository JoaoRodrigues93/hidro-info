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

	public static void main(String[] args) {
			
		PagamentoDao dao =  new PagamentoDao();
		
		Calendar primeira,segunda;
		primeira = new GregorianCalendar();
		primeira.set(Calendar.DAY_OF_MONTH, 17);
		segunda = new GregorianCalendar();
		segunda.set(Calendar.DAY_OF_MONTH, 17);
		List<Pagamento> pagamentos = dao.pegaPagamento(primeira, segunda);
		
		for (Iterator iterator = pagamentos.iterator(); iterator.hasNext();) {
			Pagamento pagamento = (Pagamento) iterator.next();
			System.out.println("Pagamento :"+pagamento);
		}
	}

}
