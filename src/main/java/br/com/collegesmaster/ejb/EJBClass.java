package br.com.collegesmaster.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

@Stateless
public class EJBClass implements EJBInterface {

	List<String> bookShelf;

	public EJBClass(){
	      bookShelf = new ArrayList<String>();
	   }

	public void addBook(String bookName) {
		bookShelf.add(bookName);
	}

	public List<String> getBooks() {
		return bookShelf;
	}
}
