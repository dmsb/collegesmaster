package br.com.collegesmaster.jsf;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.jboss.logging.Logger;

import br.com.collegesmaster.business.IUserBusiness;
import br.com.collegesmaster.model.IUser;
import br.com.collegesmaster.util.CryptoUtils;

@ManagedBean(name = "userSessionMB")
@SessionScoped
public class UserSessionMB implements Serializable {

	private static final long serialVersionUID = 4684060370406574773L;
	
	private static final Logger LOGGER = Logger.getLogger(UserSessionMB.class);
	
	@EJB
	private transient IUserBusiness userBusiness;
	
	private IUser user;
	private String username;
	private String password;	

	public void jaasLogin() {
		
		
		final HttpServletRequest loginRequest = (HttpServletRequest)         
				FacesContext.getCurrentInstance().getExternalContext().getRequest();
		
		username = loginRequest.getParameterMap().get("j_username")[0];
		final String salt = userBusiness.getUserSalt(loginRequest.getParameterMap().get("j_username")[0]);
		final String hashedPassword = CryptoUtils.getHashedPassword(loginRequest.getParameterMap().get("j_password")[0], salt);
		
		try {
			
	        if (loginRequest.getUserPrincipal() != null) {
	        	loginRequest.logout();
	        }
			
	        loginRequest.login(username, hashedPassword);
	        
		} catch (ServletException e) {
			LOGGER.error("Login failed.", e);
		}
	}
	
	public String login() {
		user = userBusiness.login(username, password);
		password = null;
		if(user == null) {
			FacesContext context = FacesContext.getCurrentInstance();
	        context.addMessage(null, new FacesMessage("Credenciais incorretas",  "login e/ou senha incorretos"));
	        return null;
		} else {			
			return "/pages/challenge_response.xhtml?faces-redirect=true";
		}

	}
	
	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/pages/home.xhtml?faces-redirect=true";
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
