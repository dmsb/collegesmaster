package br.com.collegesmaster.jsf;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import br.com.collegesmaster.business.IUserBusiness;
import br.com.collegesmaster.model.IUser;

@ManagedBean(name = "userMB")
public class UserMB {
	
	@EJB
	private IUserBusiness userBusiness;
	
	private IUser user;
	private List<IUser> users;
	
	public List<IUser> buildInstituteList() {
		return userBusiness.getList();
	}
	
	public void persistUser() {
		userBusiness.persist(user);
	}

	public IUser getUser() {
		return user;
	}

	public void setUser(IUser user) {
		this.user = user;
	}

	public List<IUser> getUsers() {
		return users;
	}

	public void setUsers(List<IUser> users) {
		this.users = users;
	}
}
