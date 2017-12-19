package br.com.collegesmaster.view;

import static br.com.collegesmaster.view.util.JsfUtils.addMessageWithDetails;
import static br.com.collegesmaster.view.util.JsfUtils.getHttpServletRequest;
import static javax.faces.application.FacesMessage.SEVERITY_WARN;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.collegesmaster.model.security.business.AuthenticationBusiness;
import br.com.collegesmaster.model.security.entities.Credentials;
import br.com.collegesmaster.model.security.entities.impl.CredentialsImpl;
import br.com.collegesmaster.model.user.business.UserBusiness;
import br.com.collegesmaster.model.user.impl.UserImpl;
import br.com.collegesmaster.qualifiers.AuthenticatedUser;

@Named("userSessionMB")
@SessionScoped
public class UserSessionMB implements Serializable {

	private static final long serialVersionUID = 4684060370406574773L;

	@Inject
	private transient UserBusiness userBusiness;
	
	@Inject
	private transient AuthenticationBusiness authenticationBusiness;

	private Credentials credentials;
	
	private UserImpl loggedUser;

	@PostConstruct
	public void init() {
		credentials = new CredentialsImpl();
	}

	public String jaasLogin() {
		final String pageToRedirect = authenticationBusiness
				.processLoginServletRequest(credentials, getHttpServletRequest());
		if(pageToRedirect != null) {
			loggedUser = userBusiness.findByUsername(credentials.getUsername());
			return pageToRedirect + "?faces-redirect=true";
		} else {
			addMessageWithDetails(SEVERITY_WARN, "invalid_credentials_mesage", "wrong_login_or_password_message");			
			return null;
		}
	}

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
	
	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}
}