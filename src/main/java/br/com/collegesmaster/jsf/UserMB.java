package br.com.collegesmaster.jsf;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import br.com.collegesmaster.business.IUserBusiness;
import br.com.collegesmaster.model.IUser;
import br.com.collegesmaster.model.imp.Person;
import br.com.collegesmaster.model.imp.Localization;
import br.com.collegesmaster.model.imp.User;
import br.com.collegesmaster.util.CryptoUtils;

@ManagedBean(name = "userMB")
public class UserMB implements Serializable {

	private static final long serialVersionUID = 7422462580072371882L;

	@EJB
	private IUserBusiness userBusiness;

	private IUser user;
	private List<IUser> users;
	
	@PostConstruct
	public void init() {
		user = new User();
		user.setGeneralInfo(new Person());
		user.getGeneralInfo().setLocalization(new Localization());;
	}
	
	public String login() {
		System.out.println("Login");
		return "login";
	}
	
	public List<IUser> buildInstituteList() {
		return userBusiness.getList();
	}
	
	public void persistUser() {
		final String salt = CryptoUtils.generateSalt();
		user.setSalt(salt);
		user.setPassword(CryptoUtils.getHashedPassword(user.getPassword(), salt));
		
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
