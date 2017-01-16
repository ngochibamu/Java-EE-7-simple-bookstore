package org.chibamu.bookstore.dao;

import javax.ejb.Stateless;

import org.chibamu.bookstore.entity.Author;

@Stateless
public class AuthorDao extends AbstractDao<Author> {

	private static final long serialVersionUID = -4092833577852426838L;

	protected AuthorDao() {
		super(Author.class);
	}
}
