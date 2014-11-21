package mz.co.hidroinfo.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.DistinctRootEntityResultTransformer;

import mz.co.hidroinfo.model.Contador;
import mz.co.hidroinfo.model.LeituraContador;
import mz.co.hidroinfo.model.Operador;
import mz.co.hidroinfo.model.Pagamento;

public class LeituraContadorDao extends GenericDAO<LeituraContador> {

	public LeituraContadorDao() {
		super(LeituraContador.class);
	}
	
	public List<LeituraContador> pegaContador(int num) {

		Session session = getSession();
		Transaction tx = session.beginTransaction();
		Criteria crit = session.createCriteria(LeituraContador.class);
		Criteria cont=crit.createCriteria("contador");
		cont.add(Restrictions.eq("numero", num));

		List<LeituraContador> lista = crit.list();
		tx.commit();
		
		return lista;
	}

	
}
