package mz.co.hidroinfo.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

import mz.co.hidroinfo.model.ClienteDomestico;
import mz.co.hidroinfo.model.Leitor;
import mz.co.hidroinfo.model.Operador;

public class LeitorDao extends GenericDAO<Leitor> {

	public LeitorDao() {
		super(Leitor.class);
	}
	public List<Leitor> findAll(String pesquisa) {
		int pesquisaInt=0;
		try{
		pesquisaInt = Integer.valueOf(pesquisa);
		} catch (NumberFormatException ex){
			pesquisaInt =0;
		}
		
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(Leitor.class);
		Disjunction disj = Restrictions.disjunction();
		Criterion id = Restrictions.eq("id", pesquisaInt);
		Criterion nuit = Restrictions.eq("nuit", pesquisaInt);
		Criterion nome = Restrictions.ilike("nome","%"+pesquisa+"%");
		Criterion bi = Restrictions.ilike("bi","%"+pesquisa+"%");
		Criterion telefone = Restrictions.ilike("telefone", "%"+pesquisaInt+"%");
		Criterion email = Restrictions.ilike("email", "%"+pesquisa+"%");
		Criterion bairro = Restrictions.ilike("bairro", "%"+pesquisa+"%");
		disj.add(id);
		disj.add(nuit);
		disj.add(nome);
		disj.add(bi);
		disj.add(telefone);
		disj.add(bairro);
		disj.add(email);
		criteria.add(disj);
		criteria.setMaxResults(50);
		@SuppressWarnings ("unchecked")
		List<Leitor> lista = criteria.list();
		tx.commit();
		//session.close();
		return lista;
	}
}
