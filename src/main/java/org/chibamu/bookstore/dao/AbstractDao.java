package org.chibamu.bookstore.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

@TransactionAttribute(TransactionAttributeType.REQUIRED)
public abstract class AbstractDao<T extends Serializable> implements Serializable {

	private static final long serialVersionUID = -1019663026997512481L;

	private Class<T> clazz;
	
	@Inject
	protected EntityManager entityManager;
	
	protected AbstractDao(Class<T> clazz) {
		this.clazz = clazz;
	}
	
	public T find(Object id){
		return entityManager.find(clazz, id);
	}
	
	
	public void persist(T entity){
		entityManager.persist(entity);
	}
	
	public void update(T entity){
		entityManager.merge(entity);
	}
	
	public void remove(T entity){
		entityManager.remove(entityManager.merge(entity));
		entityManager.flush();
	}
	
	public List<T> findAll(){
		final CriteriaQuery<T> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(clazz);
		criteriaQuery.select(criteriaQuery.from(clazz));
		return entityManager.createQuery(criteriaQuery).getResultList();
	}
}
