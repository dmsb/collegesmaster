package br.com.collegesmaster.jsf;

import static br.com.collegesmaster.jsf.util.JSFUtils.addMessage;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static javax.faces.application.FacesMessage.SEVERITY_INFO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.collegesmaster.annotations.qualifiers.LoggedIn;
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
import br.com.collegesmaster.model.IUser;
import br.com.collegesmaster.model.impl.Alternative;
import br.com.collegesmaster.model.impl.ChallengeResponse;
import br.com.collegesmaster.model.impl.Discipline;
import br.com.collegesmaster.model.impl.Question;
import br.com.collegesmaster.model.impl.QuestionResponse;

@Named("challengeResponseMB")
@ViewScoped
public class ChallengeResponseMB implements Serializable {

	private static final long serialVersionUID = -6656488708325792261L;
	
	@Inject
	private transient IDisciplineBusiness disciplineBusiness;
	
	@Inject
	private transient IChallengeBusiness challengeBusiness;
	
	@Inject
	private transient IChallengeResponseBusiness challengeResponseBusiness;
	
	@Inject @LoggedIn 
	private IUser loggedUser;
	
	private List<Discipline> userDisciplines;
	
	private IDiscipline selectedDiscipline;
	
	private IChallenge selectedChallenge;
	
	private IQuestion selectedQuestion;
	
	private IAlternative selectedAlternative;
	
	private IChallengeResponse challengeResponse;

	private List<Character> lettersMarked;
	
	@PostConstruct
	public void init() {
		resetResponse();
		loadUserDisciplines();
	}
	
	public void resetResponse() {
		challengeResponse = new ChallengeResponse();
		challengeResponse.setOwner(loggedUser);
		challengeResponse.setQuestionsResponse(new ArrayList<>());
		selectedQuestion = new Question();
		selectedAlternative = new Alternative();
		lettersMarked = new ArrayList<>();
	}
	
	public void loadUserDisciplines() {		
		final ICourse course = loggedUser.getGeneralInfo().getCourse();
		userDisciplines = disciplineBusiness.findByCourse(course);
	}
	
	public void loadChallengeQuestions() {		
		selectedChallenge
			.setQuestions(challengeBusiness.findQuestionsByChallenge(selectedChallenge));
		
		challengeResponse.setTargetChallenge(selectedChallenge);
		buildQuestionsResponse();
		
	}

	private void buildQuestionsResponse() {
		final List<Question> questions = selectedChallenge.getQuestions();
		
		final Iterator<Question> iterator = questions.iterator();
		
		while(iterator.hasNext()) {
			lettersMarked.add(null);
			iterator.next();
		}
	}
	
	public void selectAlternative() {
		
		final IQuestionResponse questionResponse = buildQuestionResponse();
		
		final Boolean existsAResponse = existsAResponseForThisQuestion(questionResponse);		
		
		addMessage(SEVERITY_INFO, "picked_alternative_message");
		
		if(existsAResponse) {
			return;
		} else {
			challengeResponse.getQuestionsResponse().add(questionResponse);
			setMarkedLetter(questionResponse, questionResponse);
		}

	}

	private void setMarkedLetter(final IQuestionResponse questionResponse, final IQuestionResponse response) {
		final Integer questionIndex = selectedChallenge.getQuestions().indexOf(questionResponse.getTargetQuestion());
		lettersMarked.set(questionIndex, response.getLetter().getLetter());
	}

	private Boolean existsAResponseForThisQuestion(final IQuestionResponse questionResponse) {
		
		final Iterator<IQuestionResponse> iterator = challengeResponse.getQuestionsResponse().iterator();
		
		while(iterator.hasNext()) {
			
			IQuestionResponse response = iterator.next();
			
			if(response.getTargetQuestion().equals(questionResponse.getTargetQuestion())) {				

				response = questionResponse;
				setMarkedLetter(questionResponse, response);
				
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
		addMessage(SEVERITY_INFO, "response_registred_with_success_message");
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

	public List<Character> getLettersMarked() {
		return lettersMarked;
	}

	public void setLettersMarked(List<Character> lettersMarked) {
		this.lettersMarked = lettersMarked;
	}

}
