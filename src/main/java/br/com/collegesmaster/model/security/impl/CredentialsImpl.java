package br.com.collegesmaster.model.security.impl;

import br.com.collegesmaster.model.security.Credentials;

public class CredentialsImpl implements Credentials {

	private static final long serialVersionUID = 396055779242305019L;
	
	private String username;
	private String password;
	
	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public void setPassword(String password) {
		this.password = password;
	}

}
