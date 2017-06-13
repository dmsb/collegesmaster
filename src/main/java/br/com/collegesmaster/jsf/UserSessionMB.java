package br.com.collegesmaster.jsf;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.collegesmaster.business.IUserBusiness;
import br.com.collegesmaster.model.IUser;

@ManagedBean(name = "userSessionMB")
@SessionScoped
public class UserSessionMB implements Serializable {

	private static final long serialVersionUID = 4684060370406574773L;

	@EJB
	private IUserBusiness userBusiness;
	
	private IUser user;
	private String username;
	private String password;
	
	public String login() {
		user = userBusiness.login(username, password);
		if(user == null) {
			FacesContext context = FacesContext.getCurrentInstance();
	        context.addMessage(null, new FacesMessage("Credenciais incorretas",  "login e/ou senha incorretos"));
	        return null;
		} else {
			return "login";
		}
		
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
