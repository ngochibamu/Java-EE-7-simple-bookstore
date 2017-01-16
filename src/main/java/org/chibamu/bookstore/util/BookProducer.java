package org.chibamu.bookstore.util;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.chibamu.bookstore.dao.BookDao;
import org.chibamu.bookstore.entity.Book;

@RequestScoped
public class BookProducer implements Serializable{

	private static final long serialVersionUID = -8028756006003873503L;
	
	private List<Book> books;
	
	@Inject
	private BookDao bookDao;
	
	@PostConstruct
	public void retrieveAllBooks(){
		books = bookDao.findAll();
	}
	
	@Produces
	@Named
	public List<Book> getBooks(){
		return books;
	}
	
	public void onMemberListChanged(@Observes (notifyObserver = Reception.IF_EXISTS) final Book member){
		retrieveAllBooks();
	}
	
}
