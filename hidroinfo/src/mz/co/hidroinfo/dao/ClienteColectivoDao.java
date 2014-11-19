package mz.co.hidroinfo.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import mz.co.hidroinfo.model.ClienteColectivo;
import mz.co.hidroinfo.model.ClienteDomestico;

public class ClienteColectivoDao extends GenericDAO<ClienteColectivo> {

	public ClienteColectivoDao() {
		super(ClienteColectivo.class);
	}

	public List<ClienteColectivo> findAll(String pesquisa) {
		int pesquisaInt=0;
		try{
		pesquisaInt = Integer.valueOf(pesquisa);
		} catch (NumberFormatException ex){
			pesquisaInt =0;
		}
		
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(ClienteColectivo.class);
		Disjunction disj = Restrictions.disjunction();
		Criterion id = Restrictions.eq("id", pesquisaInt);
		Criterion nuit = Restrictions.eq("nuit", pesquisaInt);
		Criterion nome = Restrictions.ilike("nome","%"+pesquisa+"%");
		Criterion representante = Restrictions.ilike("representante","%"+pesquisa+"%");
		Criterion bairro = Restrictions.ilike("endereco.bairro", "%"+pesquisa+"%");
		Criterion telefone = Restrictions.ilike("contacto.telefone", "%"+pesquisa+"%");
		Criterion email = Restrictions.ilike("contacto.email", "%"+pesquisa+"%");
		Criterion cidade = Restrictions.ilike("endereco.cidade", "%"+pesquisa+"%");
		Criterion rua = Restrictions.ilike("endereco.rua", "%"+pesquisa+"%");
		Criterion casaNumero = Restrictions.eq("endereco.casaNumero", pesquisaInt);
		Criterion quarteirao= Restrictions.ilike("endereco.quarteirao", "%"+pesquisa+"%");
		Criterion codigoLocal = Restrictions.eq("endereco.codigoLocal", pesquisaInt);
		Criterion avenida = Restrictions.ilike("endereco.avenida", "%"+pesquisa+"%");
		criteria.setMaxResults(50);
		disj.add(id);
		disj.add(nuit);
		disj.add(nome);
		disj.add(representante);
		disj.add(telefone);
		disj.add(bairro);
		disj.add(avenida);
		disj.add(codigoLocal);
		disj.add(quarteirao);
		disj.add(cidade);
		disj.add(casaNumero);
		disj.add(rua);
		disj.add(email);
		criteria.add(disj);
		@SuppressWarnings ("unchecked")
		List<ClienteColectivo> lista = criteria.list();
		tx.commit();
		return lista;
	}

	public long getNumeroClientes() {
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(ClienteColectivo.class);
		criteria.setProjection(Projections.rowCount());
		long numero = (Long)criteria.uniqueResult();
		tx.commit();
		return numero;
	}

	

}
