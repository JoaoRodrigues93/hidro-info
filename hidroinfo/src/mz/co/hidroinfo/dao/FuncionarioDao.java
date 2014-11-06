package mz.co.hidroinfo.dao;

import mz.co.hidroinfo.model.Funcionario;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class FuncionarioDao extends GenericDAO<Funcionario> {

	public FuncionarioDao() {
		super(Funcionario.class);
		
	}

	public Funcionario findByBI(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	

	}


