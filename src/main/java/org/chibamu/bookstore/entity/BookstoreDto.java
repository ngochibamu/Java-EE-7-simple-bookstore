package org.chibamu.bookstore.entity;

import java.util.ArrayList;
import java.util.List;

public class BookstoreDto {

	private List<BookDto> books = new ArrayList<>();

	public List<BookDto> getBooks() {
		return books;
	}

	public void setBooks(List<BookDto> books) {
		this.books = books;
	}

}