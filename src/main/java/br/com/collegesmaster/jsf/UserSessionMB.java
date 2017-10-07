package br.com.collegesmaster.jsf;

import static br.com.collegesmaster.jsf.util.JsfUtils.addMessageWithDetails;
import static br.com.collegesmaster.jsf.util.JsfUtils.getHttpServletRequest;
import static javax.faces.application.FacesMessage.SEVERITY_WARN;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.jboss.logging.Logger;

import br.com.collegesmaster.annotation.qualifier.LoggedIn;
import br.com.collegesmaster.business.UserBusiness;
import br.com.collegesmaster.model.impl.UserImpl;
import br.com.collegesmaster.security.model.Credentials;
import br.com.collegesmaster.security.model.impl.CredentialsImpl;

@Named("userSessionMB")
@SessionScoped
public class UserSessionMB implements Serializable {

	private static final long serialVersionUID = 4684060370406574773L;
	
	@Inject
	private transient Logger LOGGER;

	@Inject
	private transient UserBusiness userBusiness;

	private Credentials credentials;

	@Produces
	@LoggedIn
	private UserImpl loggedUser;

	@PostConstruct
	public void init() {
		credentials = new CredentialsImpl();
	}

	public String jaasLogin() {

		final HttpServletRequest loginRequest = getHttpServletRequest();

		try {

			if (loginRequest.getUserPrincipal() != null) {
				loginRequest.logout();
			}

			loginRequest.login(credentials.getUsername(), credentials.getPassword());

			if (loginRequest.getUserPrincipal() != null) {
				
				loggedUser = userBusiness.findByUsername(credentials.getUsername());
				LOGGER.info("Login success!");
				
				if (loginRequest.isUserInRole("PROFESSOR")) {
					return "/pages/users/professor/create_challenge.xhtml?faces-redirect=true";
				} else if (loginRequest.isUserInRole("STUDENT")) {
					return "/pages/users/student/reply_challenges.xhtml?faces-redirect=true";
				}
			}

		} catch (ServletException e) {
			LOGGER.error(e.getMessage());
		}

		addMessageWithDetails(SEVERITY_WARN, "invalid_credentials_mesage", "wrong_login_or_password_message");
		return null;
	}

	public void logout() {
		loggedUser = null;
	}
	
	public UserImpl getLoggedUser() {
		return loggedUser;
	}
	
	public void setLoggedUser(@Observes @LoggedIn UserImpl loggedUser) {
		this.loggedUser = loggedUser;
	}
	
	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

}
