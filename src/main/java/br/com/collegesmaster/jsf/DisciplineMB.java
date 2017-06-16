package br.com.collegesmaster.jsf;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import br.com.collegesmaster.business.IDisciplineBusiness;
import br.com.collegesmaster.model.ICourse;
import br.com.collegesmaster.model.IDiscipline;
import br.com.collegesmaster.model.imp.Discipline;

@ManagedBean
@ViewScoped
public class DisciplineMB implements Serializable {

	private static final long serialVersionUID = -6656488708325792261L;
	
	@EJB
	private IDisciplineBusiness disciplineBusiness;
	
	@ManagedProperty(value="#{userSessionMB}")
	private UserSessionMB userSessionMB;
	
	@ManagedProperty(value="#{mainMB}")
	private MainMB mainMB;

	private List<Discipline> userDisciplines;
	
	private IDiscipline selectedDiscipline;
	
	@PostConstruct
	public void init() {
		loadUserDisciplines();
	}
		
	public void loadUserDisciplines() {
		
		final ICourse course = userSessionMB.getUser().getGeneralInfo().getCourse();
		userDisciplines = disciplineBusiness.findByCourse(course);		
		mainMB.setSelectedOption(MenuOption.LOAD_DISCIPLINES);
	}
	
	public void changeSelectedDiscipline() {
		
	}
	
	public IDisciplineBusiness getDisciplineBusiness() {
		return disciplineBusiness;
	}

	public void setDisciplineBusiness(IDisciplineBusiness disciplineBusiness) {
		this.disciplineBusiness = disciplineBusiness;
	}

	public UserSessionMB getUserSessionMB() {
		return userSessionMB;
	}

	public void setUserSessionMB(UserSessionMB userSessionMB) {
		this.userSessionMB = userSessionMB;
	}

	public MainMB getMainMB() {
		return mainMB;
	}

	public void setMainMB(MainMB mainMB) {
		this.mainMB = mainMB;
	}

	public List<Discipline> getUserDisciplines() {
		return userDisciplines;
	}

	public void setUserDisciplines(List<Discipline> userDisciplines) {
		this.userDisciplines = userDisciplines;
	}

	public IDiscipline getSelectedDiscipline() {
		return selectedDiscipline;
	}

	public void setSelectedDiscipline(IDiscipline selectedDiscipline) {
		this.selectedDiscipline = selectedDiscipline;
	}
	

}
