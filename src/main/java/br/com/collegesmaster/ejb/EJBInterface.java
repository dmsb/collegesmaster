package br.com.collegesmaster.ejb;

import java.util.List;

import javax.ejb.Remote;

public interface EJBInterface {
	
	@Remote
	public interface LibrarySessionBeanRemote {
	   public void addBook(String bookName);
	   public List<?> getBooks();
	}
}
