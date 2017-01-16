package org.chibamu.bookstore.dao;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.chibamu.bookstore.entity.Category;
import org.chibamu.bookstore.util.BookstoreUtil;

@Stateless
public class CategoryDao extends AbstractDao<Category> {

	private static final long serialVersionUID = -6398974810412211305L;

	protected CategoryDao() {
		super(Category.class);
	}

	public Category findByTitle(String title){
		try{
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Category> criteriaQuery = criteriaBuilder.createQuery(Category.class);
			Root<Category> root = criteriaQuery.from(Category.class);
			criteriaQuery.select(root);
			criteriaQuery.where(criteriaBuilder.equal(root.get(BookstoreUtil.TITLE), title));
			return entityManager.createQuery(criteriaQuery).getSingleResult();
		}catch(NoResultException no){
			return null;
		}
	}
}
