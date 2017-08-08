package br.com.collegesmaster.model;

import java.io.Serializable;

public interface Model extends Serializable {
	
	Integer getId();

	void setId(Integer id);
	
	Long getVersion();
	
	void setVersion(Long version);
	
}
