package br.com.collegesmaster.model;

public interface IModel {
	
	Integer getId();

	void setId(Integer id);
	
	boolean equals(Object obj);

	int hashCode();
	
}
