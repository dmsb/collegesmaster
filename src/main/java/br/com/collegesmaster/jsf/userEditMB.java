package br.com.collegesmaster.jsf;

import static br.com.collegesmaster.util.JSFUtils.addMessage;
import static br.com.collegesmaster.util.JSFUtils.getPrincipalUser;
import static javax.faces.application.FacesMessage.SEVERITY_INFO;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.collegesmaster.business.IUserBusiness;
import br.com.collegesmaster.model.IUser;

@ManagedBean(name = "userEditMB")
@ViewScoped
public class userEditMB implements Serializable {

	private static final long serialVersionUID = -7014632562707028131L;
	
	@EJB
	private transient IUserBusiness userBusiness;
	
	private IUser user;
	
	@PostConstruct
	public void init() {
		user = getPrincipalUser();
		
	}
	
	public String editUser() {
		userBusiness.update(user);
		addMessage(SEVERITY_INFO, "msg_user_edited_with_success");
		return "/pages/users/edit_user.xhtml?faces-redirect=true";
	}
	
	public IUser getUser() {
		return user;
	}

	public void setUser(IUser user) {
		this.user = user;
	}
	
}
