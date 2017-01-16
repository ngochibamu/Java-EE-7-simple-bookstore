package org.chibamu.bookstore.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.commons.lang3.StringUtils;
import org.chibamu.bookstore.entity.Author;
import org.chibamu.bookstore.entity.BookDto;
import org.chibamu.bookstore.entity.BookstoreDto;
import org.chibamu.bookstore.util.BookstoreUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class BookstoreDataService {
	
	private Logger logger = LoggerFactory.getLogger(BookstoreDataService.class);
	
	public BookstoreDto parseBookstoreData(InputStream inputStream) throws XMLStreamException {
		BookstoreDto bookstoreDto = new BookstoreDto();
		List<BookDto> bookDtoList = new ArrayList<>();
		Set<Author> authorList = null;
		BookDto bookDto = null;
		boolean title = false;
		boolean year = false;
		boolean price = false;
		boolean author = false;
		
		XMLInputFactory factory =  XMLInputFactory.newInstance();
		factory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, false);
		XMLStreamReader parser = factory.createXMLStreamReader(inputStream);
		while(parser.hasNext()){
			int event = parser.next();
			if(event == XMLStreamConstants.START_ELEMENT){
				if(StringUtils.equalsIgnoreCase(parser.getLocalName(), BookstoreUtil.BOOK)){
					bookDto = new BookDto();
					bookDto.setCategory(parser.getAttributeValue(null, BookstoreUtil.CATEGORY));					
				}else if(StringUtils.equalsIgnoreCase(parser.getLocalName(), BookstoreUtil.TITLE)){
					bookDto.setLanguage(parser.getAttributeValue(null, BookstoreUtil.LANGUAGE));
					title = true;
				}else if(StringUtils.equalsIgnoreCase(parser.getLocalName(), BookstoreUtil.YEAR)){
					year = true;
				}else if(StringUtils.equalsIgnoreCase(parser.getLocalName(), BookstoreUtil.PRICE)){
					price = true;
				}else if(StringUtils.equalsIgnoreCase(parser.getLocalName(), BookstoreUtil.AUTHORS)){
					authorList = new HashSet<>();
				}else if(StringUtils.equalsIgnoreCase(parser.getLocalName(), BookstoreUtil.AUTHOR)){
					author = true;
				}
			}else if(event == XMLStreamConstants.CHARACTERS){
				if(title){
					bookDto.setTitle(parser.getText());
					title = false;
				}else if(year){
					bookDto.setYear(Integer.valueOf(parser.getText()));
					year = false;
				}else if(price){
					bookDto.setPrice(Double.valueOf(parser.getText()));
					price = false;
				}else if(author){
					Author munyori = new Author();
					munyori.setFullName(parser.getText());
					authorList.add(munyori);
					author = false;
				}
				
			}else if(event == XMLStreamConstants.END_ELEMENT){
				if(StringUtils.equalsIgnoreCase(parser.getLocalName(), BookstoreUtil.AUTHORS)){
					bookDto.setAuthors(authorList);
				}else if(StringUtils.equalsIgnoreCase(parser.getLocalName(), BookstoreUtil.BOOK)){
					bookDtoList.add(bookDto);
				}else if(StringUtils.equalsIgnoreCase(parser.getLocalName(), BookstoreUtil.BOOKSTORE)){
					bookstoreDto.setBooks(bookDtoList);
				}
			}
		}
		return bookstoreDto;
	}
}

