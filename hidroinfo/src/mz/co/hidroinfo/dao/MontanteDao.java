package mz.co.hidroinfo.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import mz.co.hidroinfo.model.Montante;
import mz.co.hidroinfo.model.Operador;
import mz.co.hidroinfo.model.Pagamento;

public class MontanteDao {

	public List<Montante> pagamentoPorOperador (Calendar data){
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
