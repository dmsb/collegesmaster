package br.com.collegesmaster.model.security.entities;

import java.io.Serializable;

public interface Credentials extends Serializable {

	void setPassword(String password);

	String getPassword();

	void setUsername(String username);
	
	String getUsername();

}
