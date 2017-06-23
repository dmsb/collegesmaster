package br.com.collegesmaster.jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import br.com.collegesmaster.business.IChallengeBusiness;
import br.com.collegesmaster.business.IDisciplineBusiness;
import br.com.collegesmaster.model.IChallenge;
import br.com.collegesmaster.model.IChallengeResolution;
import br.com.collegesmaster.model.ICourse;
import br.com.collegesmaster.model.IDiscipline;
import br.com.collegesmaster.model.IQuestion;
import br.com.collegesmaster.model.imp.ChallengeResolution;
import br.com.collegesmaster.model.imp.Discipline;

@ManagedBean(name = "challengeResolutionMB")
@ViewScoped
public class ChallengeResolutionMB implements Serializable {

	private static final long serialVersionUID = -6656488708325792261L;
	
	@EJB
	private transient IDisciplineBusiness disciplineBusiness;
	
	@EJB
	private transient IChallengeBusiness challengeBusiness;
	
	@ManagedProperty(value="#{userSessionMB}")
	private UserSessionMB userSessionMB;

	private List<Discipline> userDisciplines;
	
	private IDiscipline selectedDiscipline;
	
	private IChallenge selectedChallenge;
	
	private IQuestion selectedQuestion;
	
	private IChallengeResolution challengeResolution;
	
	@PostConstruct
	public void init() {
		challengeResolution = new ChallengeResolution();
		challengeResolution.setOwner(userSessionMB.getUser());
		challengeResolution.setQuestionsResolution(new ArrayList<>());
		
		loadUserDisciplines();
	}
		
	public void loadUserDisciplines() {		
		final ICourse course = userSessionMB.getUser().getGeneralInfo().getCourse();
		userDisciplines = disciplineBusiness.findByCourse(course);
	}
	
	public void loadChallengeQuestions() {		
		selectedChallenge
			.setQuestions(challengeBusiness.findQuestionsByChallenge(selectedChallenge));
	}
	
	public UserSessionMB getUserSessionMB() {
		return userSessionMB;
	}

	public void setUserSessionMB(UserSessionMB userSessionMB) {
		this.userSessionMB = userSessionMB;
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

	public IChallenge getSelectedChallenge() {
		return selectedChallenge;
	}

	public void setSelectedChallenge(IChallenge selectedChallenge) {
		this.selectedChallenge = selectedChallenge;
	}

	public IQuestion getSelectedQuestion() {
		return selectedQuestion;
	}

	public void setSelectedQuestion(IQuestion selectedQuestion) {
		this.selectedQuestion = selectedQuestion;
	}
	
	public IDisciplineBusiness getDisciplineBusiness() {
		return disciplineBusiness;
	}

	public IChallengeResolution getChallengeResolution() {
		return challengeResolution;
	}

	public void setChallengeResolution(IChallengeResolution challengeResolution) {
		this.challengeResolution = challengeResolution;
	}
	
}
