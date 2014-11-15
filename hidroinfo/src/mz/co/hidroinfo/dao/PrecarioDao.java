package mz.co.hidroinfo.dao;

import mz.co.hidroinfo.model.Precario;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class PrecarioDao extends GenericDAO<Precario> {

	public PrecarioDao() {
		super(Precario.class);
	}
	
	public Precario pegaTarifa(String tarifa) {
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(Precario.class);
		criteria.add(Restrictions.eq("tipo_de_tarifa", tarifa));
		Precario preco = (Precario) criteria.uniqueResult();
		tx.commit();
		return preco;
	}

	}


