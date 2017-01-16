package org.chibamu.bookstore.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "authors")
public class Author implements Serializable {

	private static final long serialVersionUID = -823644386329239123L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="author_id")
	private long id;
	
	@Column(name = "full_name")
	private String fullName;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
}
