package org.chibamu.bookstore.util;

import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class EntityManagerProducer {

	@PersistenceContext(name = "bookstoreUnit")
	private EntityManager entityManager;
	
	@Produces	
	public EntityManager entityManager(){
		return entityManager;
	}
}
