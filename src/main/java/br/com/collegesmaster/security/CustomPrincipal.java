package br.com.collegesmaster.security;

import org.jboss.security.SimplePrincipal;

import br.com.collegesmaster.model.IUser;

public class CustomPrincipal extends SimplePrincipal {
	
	private static final long serialVersionUID = -1750576356068755460L;
	
	private IUser user;
	
	public CustomPrincipal(String name, IUser user) {
		super(name);
		this.setUser(user);
	}

	public IUser getUser() {
		return user;
	}

	public void setUser(IUser user) {
		this.user = user;
	}
	
}
