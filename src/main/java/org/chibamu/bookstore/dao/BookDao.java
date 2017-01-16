package org.chibamu.bookstore.dao;

import javax.ejb.Stateless;

import org.chibamu.bookstore.entity.Book;

@Stateless
public class BookDao extends AbstractDao<Book> {

	private static final long serialVersionUID = -5375108059767413755L;

	protected BookDao() {
		super(Book.class);
	}

}
