package br.com.collegesmaster.jsf;

import static br.com.collegesmaster.jsf.util.JsfUtils.addMessage;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import static javax.faces.application.FacesMessage.SEVERITY_INFO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.collegesmaster.annotation.qualifier.LoggedIn;
import br.com.collegesmaster.business.ChallengeBusiness;
import br.com.collegesmaster.business.ChallengeResponseBusiness;
import br.com.collegesmaster.business.DisciplineBusiness;
import br.com.collegesmaster.model.Alternative;
import br.com.collegesmaster.model.Challenge;
import br.com.collegesmaster.model.Course;
import br.com.collegesmaster.model.Discipline;
import br.com.collegesmaster.model.Question;
import br.com.collegesmaster.model.QuestionResponse;
import br.com.collegesmaster.model.User;
import br.com.collegesmaster.model.impl.AlternativeImpl;
import br.com.collegesmaster.model.impl.ChallengeResponseImpl;
import br.com.collegesmaster.model.impl.DisciplineImpl;
import br.com.collegesmaster.model.impl.QuestionImpl;
import br.com.collegesmaster.model.impl.QuestionResponseImpl;

@Named("challengeResponseMB")
@ViewScoped
public class ChallengeResponseMB implements Serializable {

	private static final long serialVersionUID = -6656488708325792261L;
	
	@Inject
	private transient DisciplineBusiness disciplineBusiness;
	
	@Inject
	private transient ChallengeBusiness challengeBusiness;
	
	@Inject
	private transient ChallengeResponseBusiness challengeResponseBusiness;
	
	@Inject @LoggedIn 
	private User loggedUser;
	
	private List<DisciplineImpl> userDisciplines;
	
	private Discipline selectedDiscipline;
	
	private Challenge selectedChallenge;
	
	private Question selectedQuestion;
	
	private Alternative selectedAlternative;
	
	private ChallengeResponseImpl challengeResponse;

	private List<Character> lettersMarked;
	
	@PostConstruct
	public void init() {
		resetResponse();
		loadUserDisciplines();
	}
	
	public void resetResponse() {
		challengeResponse = new ChallengeResponseImpl();
		challengeResponse.setOwner(loggedUser);
		challengeResponse.setQuestionsResponse(new ArrayList<>());
		selectedQuestion = new QuestionImpl();
		selectedAlternative = new AlternativeImpl();
		lettersMarked = new ArrayList<>();
	}
	
	public void loadUserDisciplines() {		
		final Course course = loggedUser.getGeneralInfo().getCourse();
		userDisciplines = disciplineBusiness.findByCourse(course);
	}
	
	public void loadChallengeQuestions() {		
		selectedChallenge
			.setQuestions(challengeBusiness.findQuestionsByChallenge(selectedChallenge));
		
		challengeResponse.setTargetChallenge(selectedChallenge);
		buildQuestionsResponse();
		
	}

	private void buildQuestionsResponse() {
		final List<QuestionImpl> questions = selectedChallenge.getQuestions();
		
		final Iterator<QuestionImpl> iterator = questions.iterator();
		
		while(iterator.hasNext()) {
			lettersMarked.add(null);
			iterator.next();
		}
	}
	
	public void selectAlternative() {
		
		final QuestionResponse questionResponse = buildQuestionResponse();
		
		final Boolean existsAResponse = existsAResponseForThisQuestion(questionResponse);		
		
		addMessage(SEVERITY_INFO, "picked_alternative_message");
		
		if(existsAResponse) {
			return;
		} else {
			challengeResponse.getQuestionsResponse().add(questionResponse);
			setMarkedLetter(questionResponse, questionResponse);
		}

	}

	private void setMarkedLetter(final QuestionResponse questionResponse, final QuestionResponse response) {
		final Integer questionIndex = selectedChallenge.getQuestions().indexOf(questionResponse.getTargetQuestion());
		lettersMarked.set(questionIndex, response.getLetter().getLetter());
	}

	private Boolean existsAResponseForThisQuestion(final QuestionResponse questionResponse) {
		
		final Iterator<QuestionResponse> iterator = challengeResponse.getQuestionsResponse().iterator();
		
		while(iterator.hasNext()) {
			
			QuestionResponse response = iterator.next();
			
			if(response.getTargetQuestion().equals(questionResponse.getTargetQuestion())) {				

				response = questionResponse;
				setMarkedLetter(questionResponse, response);
				
				return TRUE;
			}
		}

		return FALSE;
	}

	private QuestionResponse buildQuestionResponse() {
		final QuestionResponse questionResponse = new QuestionResponseImpl();
		questionResponse.setChallengeResponse(challengeResponse);
		questionResponse.setTargetQuestion(selectedQuestion);
		questionResponse.setLetter(selectedAlternative.getLetter());
		return questionResponse;
	}
	
	public void persistResponse() {
		
		final Boolean created = challengeResponseBusiness.create(challengeResponse);
		
		if(created) {
			addMessage(SEVERITY_INFO, "response_registred_with_success_message");
		} else {
			addMessage(SEVERITY_ERROR, "unexpected_error");
		}
	}

	public List<DisciplineImpl> getUserDisciplines() {
		return userDisciplines;
	}

	public void setUserDisciplines(List<DisciplineImpl> userDisciplines) {
		this.userDisciplines = userDisciplines;
	}

	public Discipline getSelectedDiscipline() {
		return selectedDiscipline;
	}

	public void setSelectedDiscipline(Discipline selectedDiscipline) {
		this.selectedDiscipline = selectedDiscipline;
	}

	public Challenge getSelectedChallenge() {
		return selectedChallenge;
	}

	public void setSelectedChallenge(Challenge selectedChallenge) {
		this.selectedChallenge = selectedChallenge;
	}

	public Question getSelectedQuestion() {
		return selectedQuestion;
	}

	public void setSelectedQuestion(Question selectedQuestion) {
		this.selectedQuestion = selectedQuestion;
	}

	public ChallengeResponseImpl getChallengeResponse() {
		return challengeResponse;
	}

	public void setChallengeResponse(ChallengeResponseImpl challengeResponse) {
		this.challengeResponse = challengeResponse;
	}

	public Alternative getSelectedAlternative() {
		return selectedAlternative;
	}

	public void setSelectedAlternative(Alternative selectedAlternative) {
		this.selectedAlternative = selectedAlternative;
	}

	public List<Character> getLettersMarked() {
		return lettersMarked;
	}

	public void setLettersMarked(List<Character> lettersMarked) {
		this.lettersMarked = lettersMarked;
	}

}
