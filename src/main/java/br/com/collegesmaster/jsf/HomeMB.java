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
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

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

@ManagedBean(name = "homeMB")
@ViewScoped
public class HomeMB implements Serializable {

	private static final long serialVersionUID = 7422462580072371882L;

	@EJB
	private transient IUserBusiness userBusiness;
	
	@EJB
	private transient IRoleBusiness roleBusiness;
	
	@EJB
	private transient IInstituteBusiness instituteBusiness;
	
	@EJB
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
	
	public void changeInstituteEvent() {
		if(selectedInstitute != null) {			
			final List<Course> courses = courseBusiness.findNamesByInstitute(selectedInstitute);
			selectedInstitute.setCourses(courses);
			user.getGeneralInfo().setCourse(courses.get(0));
		} else {
			user.getGeneralInfo().setCourse(new Course());
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
