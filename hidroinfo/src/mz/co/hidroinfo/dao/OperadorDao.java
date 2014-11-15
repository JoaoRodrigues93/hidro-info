package mz.co.hidroinfo.dao;

import java.util.Calendar;
import java.util.List;









import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.DistinctRootEntityResultTransformer;

import mz.co.hidroinfo.model.Operador;

public class OperadorDao extends GenericDAO<Operador> {

	public OperadorDao() {
		super(Operador.class);
	}
	
	public List<Operador> obtemPorUsername (String username, String password){
		
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(Operador.class);
		criteria.add(Restrictions.eq("username", username));
		criteria.add(Restrictions.eq("password", password));
		List<Operador> lista = criteria.list();
		tx.commit();
		
		return lista;
	}
	
	public List<Operador> pagamento(Calendar dataPagamento) {
		Session se = getSession();
		Transaction tx = se.beginTransaction();
		Criteria crit = se.createCriteria(Operador.class);

		Criteria pagamento = crit.createCriteria("pagamento");

		pagamento.add(Restrictions.eq("dataPagamento", dataPagamento));
		List<Operador> lista = crit.list();
		DistinctRootEntityResultTransformer dist = DistinctRootEntityResultTransformer.INSTANCE;
		List<Operador> operador = dist.transformList(lista);
		//tx.commit();
		return operador;
	}

}
