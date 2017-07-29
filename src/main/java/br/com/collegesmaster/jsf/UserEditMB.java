package br.com.collegesmaster.jsf;

import static br.com.collegesmaster.jsf.util.JSFUtils.addMessage;
import static javax.faces.application.FacesMessage.SEVERITY_INFO;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.collegesmaster.annotations.qualifiers.LoggedIn;
import br.com.collegesmaster.business.IUserBusiness;
import br.com.collegesmaster.model.IUser;

@Named("userEditMB")
@RequestScoped
public class UserEditMB implements Serializable {

	private static final long serialVersionUID = -7014632562707028131L;
	
	@Inject
	private transient IUserBusiness userBusiness;
	
	@Inject @LoggedIn 
	private IUser loggedUser;
	
	private IUser user;
	
	@PostConstruct
	public void init() {
		user = loggedUser;
	}
	
	public void editUser() {
		userBusiness.update(user);
		addMessage(SEVERITY_INFO, "user_edited_with_success_message");
	}
	
	public IUser getUser() {
		return user;
	}

	public void setUser(IUser user) {
		this.user = user;
	}
	
}
