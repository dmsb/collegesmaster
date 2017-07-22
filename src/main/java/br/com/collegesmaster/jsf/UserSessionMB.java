package br.com.collegesmaster.jsf;

import static br.com.collegesmaster.jsf.util.JSFUtils.addMessageWithDetails;
import static br.com.collegesmaster.jsf.util.JSFUtils.getHttpServletRequest;
import static br.com.collegesmaster.jsf.util.JSFUtils.getUserPrincipal;
import static br.com.collegesmaster.jsf.util.JSFUtils.setUserInUserPrincipal;
import static javax.faces.application.FacesMessage.SEVERITY_WARN;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.jboss.logging.Logger;

import br.com.collegesmaster.business.IUserBusiness;
import br.com.collegesmaster.model.IUser;

@ManagedBean(name = "userSessionMB")
@ViewScoped
public class UserSessionMB implements Serializable {

	private static final long serialVersionUID = 4684060370406574773L;
	
	private static final Logger LOGGER = Logger.getLogger(UserSessionMB.class);
	
	@EJB
	private transient IUserBusiness userBusiness;
	
	private IUser user;
	private String username;
	private String password;

	public String jaasLogin() {

		final HttpServletRequest loginRequest = getHttpServletRequest();
		
		try {

	        if (loginRequest.getUserPrincipal() != null) {
	        	loginRequest.logout();
	        }
			
	        loginRequest.login(username, password);
	        
	        if(loginRequest.getUserPrincipal() != null) {

	        	setUserInUserPrincipal(userBusiness.findByUsername(getUserPrincipal().getName()));
	        	
	        	if(loginRequest.isUserInRole("PROFESSOR")) {
	        		return "/pages/users/professor/create_challenge.xhtml?faces-redirect=true";	
	        	} else if(loginRequest.isUserInRole("STUDENT")) {
	        		return "/pages/users/student/challenge_response.xhtml?faces-redirect=true";	
	        	}
	        }
	        
		} catch (ServletException e) {
			LOGGER.error(e.getMessage());
		}
		
		addMessageWithDetails(SEVERITY_WARN, "invalid_credentials_mesage", "wrong_login_or_password_message");
		return null;
	}
	
	public IUser getUser() {
		return user;
	}

	public void setUser(IUser user) {
		this.user = user;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
