package dto;

import java.io.Serializable;
import java.util.List;

import br.com.collegesmaster.model.Challenge;
import br.com.collegesmaster.model.Course;
import br.com.collegesmaster.model.Discipline;
import br.com.collegesmaster.model.GeneralInfo;
import br.com.collegesmaster.model.Institute;

public class ProfessorDTO extends UserDTO implements Serializable {

	public ProfessorDTO(Integer id, String username) {
		super(id, username);
	}

	public ProfessorDTO(Integer id, String username, String siape, List<Challenge> challenges,
			List<Institute> institutes, List<Course> courses, List<Discipline> disciplines, GeneralInfo generalInfo) {
		super(id, username);
		this.siape = siape;
		this.challenges = challenges;
		this.institutes = institutes;
		this.courses = courses;
		this.disciplines = disciplines;
		this.generalInfo = generalInfo;
	}
	
	public ProfessorDTO(Integer id, String username, String siape, GeneralInfo generalInfo) {
		super(id, username);
		this.siape = siape;
		this.generalInfo = generalInfo;		
	}

	private static final long serialVersionUID = 4776240694092596309L;

	private List<Challenge> challenges;

	private String siape;

	private List<Institute> institutes;

	private List<Course> courses;

	private List<Discipline> disciplines;

	private GeneralInfo generalInfo;

	public List<Challenge> getChallenges() {
		return challenges;
	}

	public void setChallenges(List<Challenge> challenges) {
		this.challenges = challenges;
	}

	public String getSiape() {
		return siape;
	}

	public void setSiape(String siape) {
		this.siape = siape;
	}

	public List<Institute> getInstitutes() {
		return institutes;
	}

	public void setInstitutes(List<Institute> institutes) {
		this.institutes = institutes;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public List<Discipline> getDisciplines() {
		return disciplines;
	}

	public void setDisciplines(List<Discipline> disciplines) {
		this.disciplines = disciplines;
	}

	public GeneralInfo getGeneralInfo() {
		return generalInfo;
	}

	public void setGeneralInfo(GeneralInfo generalInfo) {
		this.generalInfo = generalInfo;
	}

}
