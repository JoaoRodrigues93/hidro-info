package mz.co.hidroinfo.dao;


import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;


public class GenericDAO<T> {

	protected Class<T> _clazz;
	
	public GenericDAO(Class<T> clazz) {
		_clazz = clazz;
	}
	
	public Session getSession(){
		return HibernateUtil.getSessionFactory().getCurrentSession();
	}
	
	public Class<T> getClazz(){
		return _clazz;
	}
	

	public void create(T t) {
		Session sess = getSession();
		Transaction tx = sess.beginTransaction();
		sess.save(t);
		tx.commit();
		
	}

	public void update(T t) {
		Session sess = getSession();
		Transaction tx = sess.beginTransaction();
		sess.merge(t);
		tx.commit();

	}
	
	public void delete(T t) {
		Session sess = getSession();
		Transaction tx = sess.beginTransaction();
		sess.delete(t);
		tx.commit();
	}

	@SuppressWarnings("unchecked")
	public T findById(Integer id) {
		Session sess = getSession();
		Transaction tx = sess.beginTransaction();
		T item = (T) sess.get(_clazz, id);
		tx.commit();
		return item; 
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		Session sess = getSession();
		Transaction tx =sess.beginTransaction();
		Criteria criteria = sess.createCriteria(_clazz);
		List<T> items = criteria.list();
		criteria.setMaxResults(100);
		tx.commit();
		return items;
	}
	
	
	

	public void deleteAll() {
		for (T t : findAll()) {
			delete(t);
		}
	}
}
