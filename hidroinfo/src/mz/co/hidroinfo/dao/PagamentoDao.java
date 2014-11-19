package mz.co.hidroinfo.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.DistinctRootEntityResultTransformer;

import mz.co.hidroinfo.model.Operador;
import mz.co.hidroinfo.model.Pagamento;

public class PagamentoDao extends GenericDAO<Pagamento> {

	public PagamentoDao() {
		super(Pagamento.class);
	}

	public List<Pagamento> pegaPagamento (Calendar dataPagamento){
		
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		Criteria crit = session.createCriteria(Pagamento.class);
		Criterion data = Restrictions.eq("dataPagamento", dataPagamento);
	
		crit.add(data);
		
		List<Pagamento> lista = crit.list();
		tx.commit();
		DistinctRootEntityResultTransformer dist = DistinctRootEntityResultTransformer.INSTANCE;
		List<Pagamento> pagamentos = dist.transformList(lista);
		return pagamentos;
	}
	
	public List<Pagamento> pegaPagamento (Calendar primeira, Calendar segunda){
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		Criteria crit = session.createCriteria(Pagamento.class);
		crit.add(Restrictions.ge("dataPagamento", primeira));
		crit.add(Restrictions.le("dataPagamento", segunda));
		
		List<Pagamento> lista = crit.list();
		tx.commit();
		return lista;
	}
	

	
	
	
}
