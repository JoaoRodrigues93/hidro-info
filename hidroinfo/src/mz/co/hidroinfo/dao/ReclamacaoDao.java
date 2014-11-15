package mz.co.hidroinfo.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;

import mz.co.hidroinfo.model.Cliente;
import mz.co.hidroinfo.model.Reclamacao;

public class ReclamacaoDao extends GenericDAO<Reclamacao>{

	public ReclamacaoDao() {
		super(Reclamacao.class);
		// TODO Auto-generated constructor stub
	}
	
	public List<Reclamacao> reclamacao(){
		Session sess =getSession();
		Transaction tx= sess.beginTransaction();
		Criteria crit=sess.createCriteria(Reclamacao.class);
		Criteria rec=crit.createCriteria("cliente");
		
		List<Reclamacao> lista=crit.list();
		tx.commit();
		return lista;
	}

}
