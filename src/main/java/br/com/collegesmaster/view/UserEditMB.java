package br.com.collegesmaster.view;

import static br.com.collegesmaster.view.util.JsfUtils.addMessage;
import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import static javax.faces.application.FacesMessage.SEVERITY_INFO;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.collegesmaster.model.user.User;
import br.com.collegesmaster.model.user.business.UserBusiness;
import br.com.collegesmaster.model.user.impl.UserImpl;
import br.com.collegesmaster.qualifiers.AuthenticatedUser;

@Named("userEditMB")
@RequestScoped
public class UserEditMB implements Serializable {

	private static final long serialVersionUID = -7014632562707028131L;
	
	@Inject
	private transient UserBusiness userBusiness;
	
	@Inject @AuthenticatedUser 
	private UserImpl loggedUser;
	
	private UserImpl user;
	
	@PostConstruct
	public void init() {
		user = loggedUser;
	}
	
	public void editUser() {
		
		final User created = userBusiness.update(user);
		
		if(created != null) {
			addMessage(SEVERITY_INFO, "user_edited_with_success_message");
		} else {
			addMessage(SEVERITY_ERROR, "unexpected_error");
		}
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(UserImpl user) {
		this.user = user;
	}
	
}
