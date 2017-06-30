package br.com.collegesmaster.jsf;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import br.com.collegesmaster.business.IChallengeBusiness;
import br.com.collegesmaster.business.IChallengeResponseBusiness;
import br.com.collegesmaster.business.IDisciplineBusiness;
import br.com.collegesmaster.model.IAlternative;
import br.com.collegesmaster.model.IChallenge;
import br.com.collegesmaster.model.IChallengeResponse;
import br.com.collegesmaster.model.ICourse;
import br.com.collegesmaster.model.IDiscipline;
import br.com.collegesmaster.model.IQuestion;
import br.com.collegesmaster.model.IQuestionResponse;
import br.com.collegesmaster.model.impl.Alternative;
import br.com.collegesmaster.model.impl.ChallengeResponse;
import br.com.collegesmaster.model.impl.Discipline;
import br.com.collegesmaster.model.impl.Question;
import br.com.collegesmaster.model.impl.QuestionResponse;

@ManagedBean(name = "challengeResponseMB")
@ViewScoped
public class ChallengeResponseMB implements Serializable {

	private static final long serialVersionUID = -6656488708325792261L;
	
	@EJB
	private transient IDisciplineBusiness disciplineBusiness;
	
	@EJB
	private transient IChallengeBusiness challengeBusiness;
	
	@EJB
	private transient IChallengeResponseBusiness challengeResponseBusiness;
	
	@ManagedProperty(value="#{userSessionMB}")
	private UserSessionMB userSessionMB;

	private List<Discipline> userDisciplines;
	
	private IDiscipline selectedDiscipline;
	
	private IChallenge selectedChallenge;
	
	private IQuestion selectedQuestion;
	
	private IAlternative selectedAlternative;
	
	private IChallengeResponse challengeResponse;
	
	@PostConstruct
	public void init() {
		resetResponse();
		loadUserDisciplines();
	}
	
	public void resetResponse() {
		challengeResponse = new ChallengeResponse();
		challengeResponse.setOwner(userSessionMB.getUser());
		challengeResponse.setQuestionsResponse(new ArrayList<>());
		selectedQuestion = new Question();
		selectedAlternative = new Alternative();
	}
	
	public void loadUserDisciplines() {		
		final ICourse course = userSessionMB.getUser().getGeneralInfo().getCourse();
		userDisciplines = disciplineBusiness.findByCourse(course);
	}
	
	public void loadChallengeQuestions() {		
		selectedChallenge
			.setQuestions(challengeBusiness.findQuestionsByChallenge(selectedChallenge));
	}
	
	public void selectAlternative() {
		
		final IQuestionResponse questionResponse = buildQuestionResponse();
		
		final Boolean existsAResponse = existsAResponseForThisQuestion(questionResponse);
		
		if(existsAResponse) {
			return;
		} else {
			challengeResponse.getQuestionsResponse().add(questionResponse);			
		}
	}

	private Boolean existsAResponseForThisQuestion(final IQuestionResponse questionResponse) {
		
		final Iterator<IQuestionResponse> iterator = challengeResponse.getQuestionsResponse().iterator();
		
		while(iterator.hasNext()) {
			IQuestionResponse response = iterator.next();
			if(response.getTargetQuestion().equals(questionResponse.getTargetQuestion())) {				
				response = questionResponse;
				return TRUE;
			}
		}

		return FALSE;
	}

	private IQuestionResponse buildQuestionResponse() {
		final IQuestionResponse questionResponse = new QuestionResponse();
		questionResponse.setChallengeResponse(challengeResponse);
		questionResponse.setTargetQuestion(selectedQuestion);
		questionResponse.setLetter(selectedAlternative.getLetter());
		return questionResponse;
	}
	
	public void persistResponse() {
		challengeResponseBusiness.save(challengeResponse);
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

	public IChallengeResponse getChallengeResponse() {
		return challengeResponse;
	}

	public void setChallengeResponse(IChallengeResponse challengeResponse) {
		this.challengeResponse = challengeResponse;
	}

	public IAlternative getSelectedAlternative() {
		return selectedAlternative;
	}

	public void setSelectedAlternative(IAlternative selectedAlternative) {
		this.selectedAlternative = selectedAlternative;
	}

}
