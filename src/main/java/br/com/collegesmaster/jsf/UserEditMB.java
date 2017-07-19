package br.com.collegesmaster.jsf;

import static br.com.collegesmaster.util.JSFUtils.addMessage;
import static br.com.collegesmaster.util.JSFUtils.getUserPrincipal;
import static javax.faces.application.FacesMessage.SEVERITY_INFO;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.collegesmaster.business.IUserBusiness;
import br.com.collegesmaster.model.IUser;

@ManagedBean(name = "userEditMB")
@RequestScoped
public class UserEditMB implements Serializable {

	private static final long serialVersionUID = -7014632562707028131L;
	
	@EJB
	private transient IUserBusiness userBusiness;
	
	private IUser user;
	
	@PostConstruct
	public void init() {
		user = getUserPrincipal().getUser();
	}
	
	public void editUser() {
		userBusiness.update(user);
		addMessage(SEVERITY_INFO, "msg_user_edited_with_success");
	}
	
	public IUser getUser() {
		return user;
	}

	public void setUser(IUser user) {
		this.user = user;
	}
	
}
