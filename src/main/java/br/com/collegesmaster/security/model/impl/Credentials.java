package br.com.collegesmaster.security.model.impl;

import javax.enterprise.inject.Model;

import br.com.collegesmaster.security.model.ICredentials;

@Model
public class Credentials implements ICredentials {

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
