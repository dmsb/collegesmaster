package br.com.collegesmaster.jsf;

import static br.com.collegesmaster.util.JSFUtils.getPrincipalUser;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.collegesmaster.business.IUserBusiness;
import br.com.collegesmaster.model.IUser;

@ManagedBean(name = "userEditMB")
@ViewScoped
public class UserEditMB implements Serializable {

	private static final long serialVersionUID = -7014632562707028131L;
	
	@EJB
	private transient IUserBusiness userBusiness;
	
	private IUser user;
	
	@PostConstruct
	public void init() {
		user = getPrincipalUser();
		
	}
	
	public void editUser() {
		userBusiness.update(user);
	}
	
	public IUser getUser() {
		return user;
	}

	public void setUser(IUser user) {
		this.user = user;
	}
	
}
