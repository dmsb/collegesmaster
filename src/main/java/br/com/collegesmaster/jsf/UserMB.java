package br.com.collegesmaster.jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.collegesmaster.business.IProfileBusiness;
import br.com.collegesmaster.business.IUserBusiness;
import br.com.collegesmaster.model.IProfile;
import br.com.collegesmaster.model.IUser;
import br.com.collegesmaster.model.imp.GeneralInfo;
import br.com.collegesmaster.model.imp.Localization;
import br.com.collegesmaster.model.imp.Profile;
import br.com.collegesmaster.model.imp.User;
import br.com.collegesmaster.util.CryptoUtils;

@ManagedBean(name = "userMB")
@ViewScoped
public class UserMB implements Serializable {

	private static final long serialVersionUID = 7422462580072371882L;

	@EJB
	private IUserBusiness userBusiness;
	
	@EJB
	private IProfileBusiness profileBusiness;

	private IUser user;
	private IProfile userProfile;
	
	@PostConstruct
	public void init() {
		user = new User();
		user.setGeneralInfo(new GeneralInfo());
		user.getGeneralInfo().setLocalization(new Localization());
		user.setProfiles(new ArrayList<IProfile>());
		
		userProfile = new Profile();
	}
	
	public List<Profile> allProfiles() {
		return profileBusiness.getList();
	}
	
	public void persistUser() {
		final String salt = CryptoUtils.generateSalt();	
		user.setSalt(salt);
		user.setPassword(CryptoUtils.getHashedPassword(user.getPassword(), salt));
		
		final IProfile completeProfile = profileBusiness.findById(userProfile.getId(), Profile.class);
		
		user.getProfiles().add(completeProfile);
		userBusiness.persist(user);
		
		FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Parabéns!",  "Usuário registrado com sucesso"));
	}
		
	public IUser getUser() {
		return user;
	}

	public void setUser(IUser user) {
		this.user = user;
	}

	public IProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(IProfile userProfile) {
		this.userProfile = userProfile;
	}
	
}
