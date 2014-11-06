package mz.co.hidroinfo.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

import mz.co.hidroinfo.model.ClienteDomestico;

public class ClienteDomesticoDao extends GenericDAO<ClienteDomestico> {

	public ClienteDomesticoDao() {
		super(ClienteDomestico.class);
	}

	public List<ClienteDomestico> findAll(String pesquisa) {
		int pesquisaInt=0;
		try{
		pesquisaInt = Integer.valueOf(pesquisa);
		} catch (NumberFormatException ex){
			pesquisaInt =0;
		}
		
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(ClienteDomestico.class);
		Disjunction disj = Restrictions.disjunction();
		Criterion id = Restrictions.eq("id", pesquisaInt);
		Criterion nuit = Restrictions.eq("nuit", pesquisaInt);
		Criterion nome = Restrictions.ilike("nome","%"+pesquisa+"%");
		Criterion bi = Restrictions.ilike("bi","%"+pesquisa+"%");
		Criterion bairro = Restrictions.ilike("endereco.bairro", "%"+pesquisa+"%");
		Criterion telefone = Restrictions.ilike("contacto.telefone", "%"+pesquisa+"%");
		Criterion email = Restrictions.ilike("contacto.email", "%"+pesquisa+"%");
		Criterion cidade = Restrictions.ilike("endereco.cidade", "%"+pesquisa+"%");
		Criterion rua = Restrictions.ilike("endereco.rua", "%"+pesquisa+"%");
		Criterion casaNumero = Restrictions.eq("endereco.casaNumero", pesquisaInt);
		Criterion quarteirao= Restrictions.ilike("endereco.quarteirao", "%"+pesquisa+"%");
		Criterion codigoLocal = Restrictions.eq("endereco.codigoLocal", pesquisaInt);
		Criterion avenida = Restrictions.ilike("endereco.avenida", "%"+pesquisa+"%");
		disj.add(id);
		disj.add(nuit);
		disj.add(nome);
		disj.add(bi);
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
		criteria.setMaxResults(50);
		@SuppressWarnings ("unchecked")
		List<ClienteDomestico> lista = criteria.list();
		tx.commit();
		return lista;
	}

}
