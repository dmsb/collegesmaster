package br.com.collegesmaster.jsf;

import static br.com.collegesmaster.util.JSFUtils.addMessage;
import static br.com.collegesmaster.util.JSFUtils.addMessageWithDetails;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static javax.faces.application.FacesMessage.SEVERITY_INFO;
import static javax.faces.application.FacesMessage.SEVERITY_WARN;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.collegesmaster.business.IInstituteBusiness;
import br.com.collegesmaster.business.IProfileBusiness;
import br.com.collegesmaster.business.IUserBusiness;
import br.com.collegesmaster.model.ICourse;
import br.com.collegesmaster.model.IInstitute;
import br.com.collegesmaster.model.IRole;
import br.com.collegesmaster.model.IUser;
import br.com.collegesmaster.model.impl.GeneralInfo;
import br.com.collegesmaster.model.impl.Institute;
import br.com.collegesmaster.model.impl.Role;
import br.com.collegesmaster.model.impl.User;

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
	private IRole selectedRole;
	private IInstitute institute;	
	private List<Institute> institutes;
	
	@PostConstruct
	public void init() {
		user = new User();
		user.setRole(new Role());
		user.setGeneralInfo(new GeneralInfo());		
		
		
		selectedRole = new Role();
		
		institutes = instituteBusiness.findFetchingCourses();
		institute = institutes.get(0);
		user.getGeneralInfo().setCourse(institute.getCourses().get(0));
	}
	
	public List<Role> allRoles() {
		return profileBusiness.findAll();
	}
	
	public void persistUser() {
		
		final Role completeRole = (Role) profileBusiness.findById(selectedRole.getId());
		user.setRole(completeRole);
		
		final Boolean existsUsername = userBusiness.existsUsername(user.getUsername());
		final Boolean existsEmail = userBusiness.existsEmail(user.getGeneralInfo().getEmail());
		final Boolean existsCpf = userBusiness.existsCpf(user.getGeneralInfo().getCpf());
		
		if(TRUE.equals(existsCpf)) {
			addMessage(SEVERITY_WARN, "msg_exists_cpf");
		}
		
		if(TRUE.equals(existsUsername)) {
			addMessage(SEVERITY_WARN, "msg_exists_username");
		}
		
		if(TRUE.equals(existsEmail)) {	
			addMessage(SEVERITY_WARN, "msg_exists_email");
		}

		final Boolean isValid = !(existsCpf || existsEmail || existsUsername);
		
		if(FALSE.equals(isValid)) {
			return;
		} else {
			userBusiness.save(user);
			addMessageWithDetails(SEVERITY_INFO, "msg_success", "msg_user_registred_with_success");
			
			init();			
		}	
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

	public IRole getSelectedRole() {
		return selectedRole;
	}

	public void setSelectedRole(IRole selectedRole) {
		this.selectedRole = selectedRole;
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
