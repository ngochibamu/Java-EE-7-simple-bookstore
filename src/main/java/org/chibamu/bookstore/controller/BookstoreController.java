package org.chibamu.bookstore.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import org.chibamu.bookstore.entity.Book;
import org.chibamu.bookstore.entity.BookstoreDto;
import org.chibamu.bookstore.service.BookstoreDataService;
import org.chibamu.bookstore.service.BookstoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named(value = "bookstoreController")
@javax.faces.view.ViewScoped
public class BookstoreController implements Serializable {

	@EJB
	private BookstoreDataService bookstoreDataService;
	
	@EJB
	private BookstoreService bookstoreService;
	
	@Inject
	private FacesContext facesContext;
	
	private static final long serialVersionUID = 6732933809179458078L;
	
	private Part bookstoreData;
	private String content;
	private List<Book> books;
	private boolean edit;
	private Book book = new Book();
	
	private Logger logger = LoggerFactory.getLogger(BookstoreController.class); 
	
	public void importBookstoreData() throws JAXBException, URISyntaxException, XMLStreamException, IOException {
		try(InputStream inputStream = bookstoreData.getInputStream()){
			BookstoreDto dto = bookstoreDataService.parseBookstoreData(inputStream);
			bookstoreService.importBookstoreData(dto);
			final FacesMessage faceMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Done: ", "Successfully Impoted bookstore Data");
			facesContext.addMessage(null, faceMsg);
		}catch (IOException e) {
			final String errorMsg = "Error Importing Bookstore Data";
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMsg, "Error while importing data");
			facesContext.addMessage(null, msg);
			logger.error("Could not import bookstore data: ", e);
		}
		FacesContext.getCurrentInstance().getExternalContext().redirect("/bookstore");
	}
	
	public void saveBook(){
		bookstoreService.saveBook(book);
		book = new Book();
		edit = false;
	}

	public void editBook(Book book) throws IOException{
		this.book = book;
		edit = true;
	}
	
	public void deleteBook(Book book) throws IOException{
		bookstoreService.deleteBook(book);
		FacesContext.getCurrentInstance().getExternalContext().redirect("/bookstore");
	}
	public Part getBookstoreData() {
		return bookstoreData;
	}

	public void setBookstoreData(Part bookstoreData) {
		this.bookstoreData = bookstoreData;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public boolean getEdit(){
		return edit;
	}
	
	public void setEdit(boolean edit){
		this.edit = edit;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}
}
