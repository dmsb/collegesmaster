package dto;

import java.io.Serializable;

import br.com.collegesmaster.model.Course;
import br.com.collegesmaster.model.GeneralInfo;
import br.com.collegesmaster.model.Institute;

public class StudentDTO extends UserDTO implements Serializable {
		
	public StudentDTO(Integer id, String username) {
		super(id, username);
	}
	
	public StudentDTO(Integer id, String username, String registration, 
			Institute institute, Course course,	Integer score, GeneralInfo generalInfo) {
		
		super(id, username);
		this.registration = registration;		
		this.institute = institute;
		this.course = course;
		this.score = score;
		this.generalInfo = generalInfo;
	}

	private static final long serialVersionUID = -3316092231330909801L;
	
	private String registration;
	
	private Course course;
	
	private Institute institute;
	
	private Integer score;
	
	private GeneralInfo generalInfo;
	
	public String getRegistration() {
		return registration;
	}

	public void setRegistration(String registration) {
		this.registration = registration;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Institute getInstitute() {
		return institute;
	}

	public void setInstitute(Institute institute) {
		this.institute = institute;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public GeneralInfo getGeneralInfo() {
		return generalInfo;
	}

	public void setGeneralInfo(GeneralInfo generalInfo) {
		this.generalInfo = generalInfo;
	}

}
