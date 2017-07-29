package br.com.collegesmaster.jsf;

import static br.com.collegesmaster.jsf.util.JSFUtils.addMessage;
import static br.com.collegesmaster.jsf.util.JSFUtils.addMessageWithDetails;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static javax.faces.application.FacesMessage.SEVERITY_INFO;
import static javax.faces.application.FacesMessage.SEVERITY_WARN;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.collegesmaster.business.ICourseBusiness;
import br.com.collegesmaster.business.IInstituteBusiness;
import br.com.collegesmaster.business.IRoleBusiness;
import br.com.collegesmaster.business.IUserBusiness;
import br.com.collegesmaster.model.ICourse;
import br.com.collegesmaster.model.IInstitute;
import br.com.collegesmaster.model.IRole;
import br.com.collegesmaster.model.IUser;
import br.com.collegesmaster.model.impl.Course;
import br.com.collegesmaster.model.impl.GeneralInfo;
import br.com.collegesmaster.model.impl.Institute;
import br.com.collegesmaster.model.impl.Role;
import br.com.collegesmaster.model.impl.User;

@Named("homeMB")
@ViewScoped
public class HomeMB implements Serializable {

	private static final long serialVersionUID = 7422462580072371882L;

	@Inject
	private transient IUserBusiness userBusiness;
	
	@Inject
	private transient IRoleBusiness roleBusiness;
	
	@Inject
	private transient IInstituteBusiness instituteBusiness;
	
	@Inject
	private transient ICourseBusiness courseBusiness;
	
	private IUser user;
	private IRole selectedRole;
	private IInstitute selectedInstitute;	
	private List<Institute> institutes;
	
	@PostConstruct
	public void init() {
		user = new User();
		user.setRoles(new ArrayList<>());
		user.setGeneralInfo(new GeneralInfo());

		selectedRole = new Role();

		institutes = instituteBusiness.findNames();
		selectedInstitute = new Institute();
		selectedInstitute.setCourses(new ArrayList<>());
		user.getGeneralInfo().setCourse(new Course());
	}
	
	public List<Role> allRoles() {
		return roleBusiness.findAll();
	}
	
	public void persistUser() {
		final IRole completeRole = roleBusiness.findById(selectedRole.getId());
		user.getRoles().add(completeRole);
		
		final ICourse completedCourse = courseBusiness.findById(user.getGeneralInfo().getCourse().getId());
		user.getGeneralInfo().setCourse(completedCourse);
		
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
		
		if(FALSE.equals(isValid)) {
			return;
		} else {
			userBusiness.save(user);
			addMessageWithDetails(SEVERITY_INFO, "success_message", "user_registred_with_success_message");
			
			init();			
		}	
	}
	
	public void changeInstituteEvent() {
		if(selectedInstitute != null) {			
			final List<Course> courses = courseBusiness.findNamesByInstitute(selectedInstitute);
			selectedInstitute.setCourses(courses);
		} else {
			user.getGeneralInfo().setCourse(new Course());
			selectedInstitute = new Institute();
			selectedInstitute.setCourses(new ArrayList<>());
		}
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

	public IInstitute getSelectedInstitute() {
		return selectedInstitute;
	}

	public void setSelectedInstitute(IInstitute selectedInstitute) {
		this.selectedInstitute = selectedInstitute;
	}

	public List<Institute> getInstitutes() {
		return institutes;
	}

	public void setInstitutes(List<Institute> institutes) {
		this.institutes = institutes;
	}
	
}
