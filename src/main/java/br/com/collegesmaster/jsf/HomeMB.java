package br.com.collegesmaster.jsf;

import static br.com.collegesmaster.jsf.util.JsfUtils.addMessage;
import static br.com.collegesmaster.jsf.util.JsfUtils.addMessageWithDetails;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import static javax.faces.application.FacesMessage.SEVERITY_INFO;
import static javax.faces.application.FacesMessage.SEVERITY_WARN;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.collegesmaster.model.business.CourseBusiness;
import br.com.collegesmaster.model.business.InstituteBusiness;
import br.com.collegesmaster.model.business.RoleBusiness;
import br.com.collegesmaster.model.business.UserBusiness;
import br.com.collegesmaster.model.entities.course.Course;
import br.com.collegesmaster.model.entities.course.impl.CourseImpl;
import br.com.collegesmaster.model.entities.generalinfo.impl.GeneralInfoImpl;
import br.com.collegesmaster.model.entities.institute.Institute;
import br.com.collegesmaster.model.entities.institute.impl.InstituteImpl;
import br.com.collegesmaster.model.entities.role.Role;
import br.com.collegesmaster.model.entities.role.impl.RoleImpl;
import br.com.collegesmaster.model.entities.user.User;
import br.com.collegesmaster.model.entities.user.impl.UserImpl;

@Named("homeMB")
@ViewScoped
public class HomeMB implements Serializable {

	private static final long serialVersionUID = 7422462580072371882L;

	@Inject
	private transient UserBusiness userBusiness;
	
	@Inject
	private transient RoleBusiness roleBusiness;
	
	@Inject
	private transient InstituteBusiness instituteBusiness;
	
	@Inject
	private transient CourseBusiness courseBusiness;
	
	private User user;
	private Role selectedRole;
	private Institute selectedInstitute;	
	private List<InstituteImpl> institutes;
	
	@PostConstruct
	public void init() {
		user = new UserImpl();
		user.setRoles(new ArrayList<>());
		user.setGeneralInfo(new GeneralInfoImpl());

		selectedRole = new RoleImpl();

		institutes = instituteBusiness.findNames();
		selectedInstitute = new InstituteImpl();
		selectedInstitute.setCourses(new ArrayList<>());
		user.setCourse(new CourseImpl());
	}
	
	public List<RoleImpl> allRoles() {
		return roleBusiness.findAll();
	}
	
	public void persistUser() {
		final RoleImpl completeRole = roleBusiness.findById(selectedRole.getId());
		user.getRoles().add(completeRole);
		
		final Course completedCourse = courseBusiness.findById(user.getCourse().getId());
		user.setCourse(completedCourse);
		
		final Boolean isValid = checkUniqueConstraints();
		
		if(FALSE.equals(isValid)) {
			return;
		} else {
			
			final Boolean created = userBusiness.create((UserImpl) user);
			if(created) {
				addMessageWithDetails(SEVERITY_INFO, "success_message", "user_registred_with_success_message");
			} else {
				addMessage(SEVERITY_ERROR, "unexpected_error");
			}
			
			init();			
		}	
	}

	private Boolean checkUniqueConstraints() {
		final Boolean existsUsername = userBusiness.existsUsername(user.getUsername());
		final Boolean existsEmail = userBusiness.existsEmail(user.getGeneralInfo().getEmail());
		final Boolean existsCpf = userBusiness.existsCpf(user.getGeneralInfo().getCpf());
		
		if(TRUE.equals(existsCpf)) {
			addMessage(SEVERITY_WARN, "exists_cpf_message");
		}
		
		if(TRUE.equals(existsUsername)) {
			addMessage(SEVERITY_WARN, "exists_username_message");
		}
		
		if(TRUE.equals(existsEmail)) {	
			addMessage(SEVERITY_WARN, "exists_email_message");
		}

		final Boolean isValid = !(existsCpf || existsEmail || existsUsername);
		return isValid;
	}
	
	public void changeInstituteEvent() {
		if(selectedInstitute != null) {			
			final List<CourseImpl> courses = courseBusiness.findNamesByInstitute(selectedInstitute);
			selectedInstitute.setCourses(courses);
		} else {
			user.setCourse(new CourseImpl());
			selectedInstitute = new InstituteImpl();
			selectedInstitute.setCourses(new ArrayList<>());
		}
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getSelectedRole() {
		return selectedRole;
	}

	public void setSelectedRole(Role selectedRole) {
		this.selectedRole = selectedRole;
	}

	public Institute getSelectedInstitute() {
		return selectedInstitute;
	}

	public void setSelectedInstitute(Institute selectedInstitute) {
		this.selectedInstitute = selectedInstitute;
	}

	public List<InstituteImpl> getInstitutes() {
		return institutes;
	}

	public void setInstitutes(List<InstituteImpl> institutes) {
		this.institutes = institutes;
	}
	
}
