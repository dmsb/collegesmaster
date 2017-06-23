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

import br.com.collegesmaster.business.IInstituteBusiness;
import br.com.collegesmaster.business.IProfileBusiness;
import br.com.collegesmaster.business.IUserBusiness;
import br.com.collegesmaster.model.ICourse;
import br.com.collegesmaster.model.IInstitute;
import br.com.collegesmaster.model.IProfile;
import br.com.collegesmaster.model.IUser;
import br.com.collegesmaster.model.imp.Course;
import br.com.collegesmaster.model.imp.GeneralInfo;
import br.com.collegesmaster.model.imp.Institute;
import br.com.collegesmaster.model.imp.Profile;
import br.com.collegesmaster.model.imp.User;

@ManagedBean(name = "homeMB")
@ViewScoped
public class HomeMB implements Serializable {

	private static final long serialVersionUID = 7422462580072371882L;

	@EJB
	private transient IUserBusiness userBusiness;
	
	@EJB
	private transient IProfileBusiness profileBusiness;
	
	@EJB
	private transient IInstituteBusiness instituteBusiness;
	
	private IUser user;
	private IProfile userProfile;
	private IInstitute institute;	
	private List<Institute> institutes;
	
	@PostConstruct
	public void init() {
		user = new User();
		user.setProfiles(new ArrayList<Profile>());
		user.setGeneralInfo(new GeneralInfo());		
		user.getGeneralInfo().setCourse(new Course());
		
		userProfile = new Profile();
		
		institutes = instituteBusiness.findFetchingCourses();
		institute = institutes.get(0);
		
	}
	
	public List<Profile> allProfiles() {
		return profileBusiness.findAll();
	}
	
	public void persistUser() {
		
		final Profile completeProfile = (Profile) profileBusiness.findById(userProfile.getId(), Profile.class);
		user.getProfiles().add(completeProfile);
		
		userBusiness.persist(user);
		
		FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Sucesso!",  "Usuário registrado."));
        init();
        
	}
	
	public void findCoursesByInstitute() {
		if(institute != null) {
			final ICourse currentCourse = institute.getCourses().get(0);
			user.getGeneralInfo().setCourse(currentCourse);			
		}
	}
	
	public void changeInstituteEvent() {
		
		final Integer currentUserInstituteId = institute.getId();
		
		institutes.forEach(currentInstitute -> {
			if(currentUserInstituteId.equals(currentInstitute.getId())) {				
				institute = currentInstitute;
				final ICourse updatedCourse = currentInstitute.getCourses().get(0);
				user.getGeneralInfo().setCourse(updatedCourse);
			}
		});
	}
	
	public void changeCourseEvent() {
		
		final Integer currentUserCourseId = user.getGeneralInfo().getCourse().getId();
		
		institute.getCourses().forEach(currentCourse -> {
			if(currentUserCourseId.equals(currentCourse.getId())) {
				user.getGeneralInfo().setCourse(currentCourse);
			}
		});
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

	public IInstitute getInstitute() {
		return institute;
	}

	public void setInstitute(IInstitute institute) {
		this.institute = institute;
	}

	public List<Institute> getInstitutes() {
		return institutes;
	}

	public void setInstitutes(List<Institute> institutes) {
		this.institutes = institutes;
	}
}
