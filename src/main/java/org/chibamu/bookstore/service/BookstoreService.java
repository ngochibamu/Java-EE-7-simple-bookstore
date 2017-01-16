package org.chibamu.bookstore.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.xml.bind.JAXBException;

import org.apache.commons.lang3.StringUtils;
import org.chibamu.bookstore.dao.BookDao;
import org.chibamu.bookstore.dao.CategoryDao;
import org.chibamu.bookstore.entity.Book;
import org.chibamu.bookstore.entity.BookDto;
import org.chibamu.bookstore.entity.BookstoreDto;
import org.chibamu.bookstore.entity.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class BookstoreService {

	private Logger logger = LoggerFactory.getLogger(BookstoreService.class); 
	
	private Map<Category, Set<Book>> catBooks = new HashMap<>();
	@Inject
	private CategoryDao categoryDao;
	
	@EJB
	private BookDao bookDao;
	
	@EJB
	private BookstoreDataService service;
	
	public void importBookstoreData(BookstoreDto bookstoreDto ) throws IOException, JAXBException, URISyntaxException {
		
		for(BookDto dto : bookstoreDto.getBooks()){
			Category category = new Category();
			category.setTitle(dto.getCategory());			
			catBooks.put(category, new HashSet<Book>());			
		}
		for(Map.Entry<Category, Set<Book>> entry : catBooks.entrySet()){
			Set<Book> catBookList = new HashSet<>();
			Category category = entry.getKey();
			findBookListByCategory(category, bookstoreDto.getBooks(), catBookList);
			Category oldCategory = categoryDao.findByTitle(category.getTitle());
			if(oldCategory == null){
				categoryDao.persist(category);
				for(Book bk : category.getBooks()){
					bookDao.persist(bk);
				}
			}
			
		}
	}
	
	public void saveBook(Book book){
		bookDao.update(book);
	}
	
	public void deleteBook(Book book){
		bookDao.remove(book);
	}
	private void findBookListByCategory(Category category, List<BookDto> bookList, Set<Book> list){
		for(BookDto bookDto : bookList){
			if(StringUtils.equalsIgnoreCase(category.getTitle(), bookDto.getCategory())){
				Book book = new Book();
				book.setAuthors(bookDto.getAuthors());
				book.setCategory(category);
				book.setPrice(bookDto.getPrice());
				book.setTitle(bookDto.getTitle());
				book.setLanguage(bookDto.getLanguage());
				category.addBook(book);
				list.add(book);
			}
		}
	}
}
