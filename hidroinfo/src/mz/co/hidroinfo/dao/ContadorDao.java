package mz.co.hidroinfo.dao;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.DistinctRootEntityResultTransformer;

import mz.co.hidroinfo.model.Contador;
import mz.co.hidroinfo.model.Pagamento;

public class ContadorDao extends GenericDAO<Contador> {

	public ContadorDao() {
		super(Contador.class);
	}
	
	public List<Contador> pegaContador(int num) {

		Session session = getSession();
		Transaction tx = session.beginTransaction();
		Criteria crit = session.createCriteria(Contador.class);
		Criterion contador = Restrictions.eq("numero", num);

		crit.add(contador);

		List<Contador> lista = crit.list();
		tx.commit();
		
		return lista;
	}

}
