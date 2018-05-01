package br.com.collegesmaster.view;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import br.com.collegesmaster.model.security.impl.UserImpl;
import br.com.collegesmaster.qualifiers.AuthenticatedUser;

@Named("sessionMB")
@SessionScoped
public class SessionMB implements Serializable {

	private static final long serialVersionUID = 4684060370406574773L;
	
	private UserImpl loggedUser;

	public void logout() {
		loggedUser = null;
	}

	@Produces @AuthenticatedUser
	public UserImpl getLoggedUser() {
		return loggedUser;
	}
	
	public void setLoggedUser(@Observes @AuthenticatedUser UserImpl loggedUser) {
		this.loggedUser = loggedUser;
	}
}