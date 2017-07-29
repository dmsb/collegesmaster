package br.com.collegesmaster.security.model;

import java.io.Serializable;

public interface ICredentials extends Serializable {

	void setPassword(String password);

	String getPassword();

	void setUsername(String username);

	String getUsername();

}
