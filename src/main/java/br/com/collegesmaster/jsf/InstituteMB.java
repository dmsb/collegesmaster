package br.com.collegesmaster.jsf;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import br.com.collegesmaster.business.IInstituteBusiness;
import br.com.collegesmaster.model.ICourse;
import br.com.collegesmaster.model.IInstitute;
import br.com.collegesmaster.model.imp.Institute;

@ManagedBean(name = "instituteMB")
@ViewScoped
public class InstituteMB {
	
	@EJB
	private IInstituteBusiness instituteBusiness;
	
	@ManagedProperty(value="#{homeMB}")
	private HomeMB homeMB;
	
	private IInstitute institute;
	private List<Institute> institutes;

	@PostConstruct
	public void init() {
		institute = new Institute();
		institutes = new ArrayList<Institute>();
	}
	
	public List<ICourse> allCoursesByInstitute() {
		if(institute != null) {
			return institute.getCourses();			
		} else {
			return null;
		}
	}
	
	public void changeInstitute() {
		
		final Integer currentUserInstituteId = institute.getId();
		
		institutes.forEach(currentInstitute -> {
			if(currentUserInstituteId.equals(currentInstitute.getId())) {				
				institute = currentInstitute;
				final ICourse updatedCourse = currentInstitute.getCourses().get(0);
				homeMB.getUser().getGeneralInfo().setCourse(updatedCourse);
			}
		});
	}
	
	public void changeCourse() {
		final Integer currentUserCourseId = homeMB.getUser().getGeneralInfo().getCourse().getId();
		
		institute.getCourses().forEach(currentCourse -> {
			if(currentUserCourseId.equals(currentCourse.getId())) {
				homeMB.getUser().getGeneralInfo().setCourse(currentCourse);
			}
		});
	}
	
	public IInstitute getInstitute() {
		return institute;
	}

	public void setInstitute(IInstitute institute) {
		this.institute = institute;
	}

	public List<Institute> getInstitutes() {
		institutes = instituteBusiness.getInstitutesFetchingCourses();
		institute = institutes.listIterator().next();
		return institutes;
	}

	public void setInstitutes(List<Institute> institutes) {
		this.institutes = institutes;
	}

	public HomeMB getHomeMB() {
		return homeMB;
	}

	public void setHomeMB(HomeMB homeMB) {
		this.homeMB = homeMB;
	}
	
}
