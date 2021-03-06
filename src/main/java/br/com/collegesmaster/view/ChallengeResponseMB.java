package br.com.collegesmaster.view;

import static br.com.collegesmaster.view.util.JsfUtils.addMessage;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static javax.faces.application.FacesMessage.SEVERITY_INFO;
import static javax.faces.application.FacesMessage.SEVERITY_WARN;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.collegesmaster.model.challenge.Alternative;
import br.com.collegesmaster.model.challenge.Challenge;
import br.com.collegesmaster.model.challenge.Question;
import br.com.collegesmaster.model.challenge.business.ChallengeBusiness;
import br.com.collegesmaster.model.challenge.impl.AlternativeImpl;
import br.com.collegesmaster.model.challenge.impl.QuestionImpl;
import br.com.collegesmaster.model.challengeresponse.ChallengeResponse;
import br.com.collegesmaster.model.challengeresponse.QuestionResponse;
import br.com.collegesmaster.model.challengeresponse.business.ChallengeResponseBusiness;
import br.com.collegesmaster.model.challengeresponse.impl.ChallengeResponseImpl;
import br.com.collegesmaster.model.challengeresponse.impl.QuestionResponseImpl;
import br.com.collegesmaster.model.institute.Course;
import br.com.collegesmaster.model.institute.Discipline;
import br.com.collegesmaster.model.institute.business.DisciplineBusiness;
import br.com.collegesmaster.model.institute.impl.DisciplineImpl;
import br.com.collegesmaster.model.security.User;
import br.com.collegesmaster.qualifiers.AuthenticatedUser;

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
	
	@Inject @AuthenticatedUser 
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
		final Course course = loggedUser.getCourse();
		userDisciplines = disciplineBusiness.findByCourse(course);
	}
	
	public void loadChallengeQuestions() {		
		selectedChallenge
			.setQuestions(challengeBusiness.findQuestionsByChallenge(selectedChallenge));
		challengeResponse.setTargetChallenge(selectedChallenge);
		buildQuestionsResponse();
		
	}

	private void buildQuestionsResponse() {
		final Collection<QuestionImpl> questions = selectedChallenge.getQuestions();
		final Iterator<QuestionImpl> iterator = questions.iterator();
		while(iterator.hasNext()) {
			lettersMarked.add(null);
			iterator.next();
		}
	}
	
	public void selectAlternative() {
		final QuestionResponseImpl questionResponse = buildQuestionResponse();
		final Boolean existsAResponse = existsAResponseForThisQuestion(questionResponse);		
		if(FALSE.equals(existsAResponse)) {
			challengeResponse.getQuestionsResponse().add(questionResponse);
			setMarkedLetter(questionResponse, questionResponse);
		}
		addMessage(SEVERITY_INFO, "picked_alternative_message");
	}

	private void setMarkedLetter(final QuestionResponse questionResponse, final QuestionResponse response) {
		final List<QuestionImpl> list = selectedChallenge.getQuestions().stream().collect(Collectors.toList());
		final Integer questionIndex = list.indexOf(questionResponse.getTargetQuestion());
		lettersMarked.set(questionIndex, response.getLetter().getLetter());
	}

	private Boolean existsAResponseForThisQuestion(final QuestionResponse questionResponse) {
		final Iterator<QuestionResponseImpl> iterator = challengeResponse.getQuestionsResponse().iterator();
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

	private QuestionResponseImpl buildQuestionResponse() {
		final QuestionResponseImpl questionResponse = new QuestionResponseImpl();
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
			addMessage(SEVERITY_WARN, "already_replied_challenge_message");
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

	public ChallengeResponse getChallengeResponse() {
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
