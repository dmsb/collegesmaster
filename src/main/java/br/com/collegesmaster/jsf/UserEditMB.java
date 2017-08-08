package br.com.collegesmaster.jsf;

import static br.com.collegesmaster.jsf.util.JSFUtils.addMessage;
import static javax.faces.application.FacesMessage.SEVERITY_INFO;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.collegesmaster.annotation.qualifier.LoggedIn;
import br.com.collegesmaster.business.UserBusiness;
import br.com.collegesmaster.model.impl.UserImpl;

@Named("userEditMB")
@RequestScoped
public class UserEditMB implements Serializable {

	private static final long serialVersionUID = -7014632562707028131L;
	
	@Inject
	private transient UserBusiness userBusiness;
	
	@Inject @LoggedIn 
	private UserImpl loggedUser;
	
	private UserImpl user;
	
	@PostConstruct
	public void init() {
		user = loggedUser;
	}
	
	public void editUser() {
		userBusiness.update(user);
		addMessage(SEVERITY_INFO, "user_edited_with_success_message");
	}
	
	public UserImpl getUser() {
		return user;
	}

	public void setUser(UserImpl user) {
		this.user = user;
	}
	
}
