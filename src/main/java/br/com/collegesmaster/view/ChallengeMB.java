package br.com.collegesmaster.view;

import static br.com.collegesmaster.view.util.JsfUtils.addMessage;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import static javax.faces.application.FacesMessage.SEVERITY_INFO;
import static javax.faces.application.FacesMessage.SEVERITY_WARN;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import br.com.collegesmaster.model.challenge.Alternative;
import br.com.collegesmaster.model.challenge.Challenge;
import br.com.collegesmaster.model.challenge.business.ChallengeBusiness;
import br.com.collegesmaster.model.challenge.enums.ChallengeType;
import br.com.collegesmaster.model.challenge.enums.Letter;
import br.com.collegesmaster.model.challenge.impl.AlternativeImpl;
import br.com.collegesmaster.model.challenge.impl.ChallengeImpl;
import br.com.collegesmaster.model.challenge.impl.QuestionImpl;
import br.com.collegesmaster.model.institute.Discipline;
import br.com.collegesmaster.model.institute.business.DisciplineBusiness;
import br.com.collegesmaster.model.institute.impl.DisciplineImpl;
import br.com.collegesmaster.model.security.User;
import br.com.collegesmaster.qualifiers.AuthenticatedUser;

@Named("challengeMB")
@ViewScoped
public class ChallengeMB implements Serializable {

	private static final long serialVersionUID = 6075067137564460555L;
	
	@Inject
	private transient ChallengeBusiness challengeBusiness;
	
	@Inject
	private transient DisciplineBusiness disciplineBusiness;
	
	@Inject @AuthenticatedUser 
	private User loggedUser;
	
	private Challenge challenge;
	
	private QuestionImpl oldQuestion;
	
	private QuestionImpl currentQuestion;
	
	private Letter trueAlternative;
	
	private Boolean isANewQuestion;
	
	private Boolean isOnlyView;
	
	@PostConstruct
	public void init() {
		challenge = new ChallengeImpl();
		challenge.setDiscipline(new DisciplineImpl());
		challenge.setOwner(loggedUser);
		challenge.setQuestions(new ArrayList<QuestionImpl>());
		isANewQuestion = true;
		isOnlyView = false;
		initCurrentQuestion();
	}
	
	public List<DisciplineImpl> loadUserDisciplines() {
		return disciplineBusiness.findNamesByCourse(loggedUser.getCourse());
	}
	
	public void initCurrentQuestion() {
		currentQuestion = new QuestionImpl();
		final List<AlternativeImpl> alternatives = new ArrayList<>(4);
		alternatives.add(new AlternativeImpl());
		alternatives.add(new AlternativeImpl());
		alternatives.add(new AlternativeImpl());
		alternatives.add(new AlternativeImpl());
		final Letter[] letters = Letter.values();
		for(int i = 0; i < letters.length; i++) {
			alternatives.get(i).setLetter(letters[i]);
			alternatives.get(i).setQuestion(currentQuestion);
		}
		currentQuestion.setAlternatives(alternatives);
		trueAlternative = null;
		setIsOnlyView(FALSE);
		setIsANewQuestion(TRUE);
	}
	
	public void persistChallenge() {
		final Integer disciplineId = challenge.getDiscipline().getId();
		final Discipline discipline = disciplineBusiness.findById(disciplineId);
		challenge.setDiscipline(discipline);
		challenge.setChallengetType(ChallengeType.NORMAL);
		final Boolean created = challengeBusiness.create(challenge);
		if(created) {
			addMessage(SEVERITY_INFO, "challenge_registred_with_success_message");
		} else {
			addMessage(SEVERITY_ERROR, "unexpected_error");
		}
		init();
	}
	
	public void addQuestionToChallenge() {
		if(trueAlternative != null) {
			currentQuestion.setChallenge(challenge);
			for(final Alternative alternative : currentQuestion.getAlternatives()) {
				if(trueAlternative.equals(alternative.getLetter())) {
					alternative.setIsTrue(TRUE);
				} else {
					alternative.setIsTrue(FALSE);
				}
			}
			challenge.getQuestions().add(currentQuestion);
			addMessage(SEVERITY_INFO, "question_added_message");
		} else {
			addMessage(SEVERITY_WARN, "correct_alternative_required_message");
		}
	}
	
	public void updateQuestionToChallenge() {
		if(trueAlternative != null) {
			currentQuestion.setChallenge(challenge);
			for(final Alternative alternative : currentQuestion.getAlternatives()) {
				alternative.setQuestion(currentQuestion);
				if(trueAlternative.equals(alternative.getLetter())) {
					alternative.setIsTrue(TRUE);
				} else {
					alternative.setIsTrue(FALSE);
				}
			}
			final List<QuestionImpl> updatedQuestions = challenge.getQuestions().stream()
					.map(question -> { return oldQuestion.equals(question) ? currentQuestion : question; })
					.collect(Collectors.toList());
			challenge.setQuestions(updatedQuestions);
			addMessage(SEVERITY_INFO, "question_updated_message");
		} else {
			addMessage(SEVERITY_WARN, "correct_alternative_required_message");
		}
		setIsANewQuestion(true);
	}
	
	public void buildParametersForUpdateQuestion() {
		setOldQuestion(currentQuestion); 
		setIsANewQuestion(FALSE);
		setIsOnlyView(FALSE);
	}
	
	public void removeCurrenQuestionFromQuestionList() {
		challenge.getQuestions().remove(currentQuestion);
	}
	
	public void onChallengeQuestionsDataTableRowSelect(SelectEvent event) {
		if(QuestionImpl.class.isAssignableFrom(event.getObject().getClass())) {
			currentQuestion = (QuestionImpl) event.getObject();			
		}
	}
	
	public Letter[] loadAllLetters() {
		return Letter.values();
	}

	public Challenge getChallenge() {
		return challenge;
	}

	public void setChallenge(Challenge challenge) {
		this.challenge = challenge;
	}

	public QuestionImpl getCurrentQuestion() {
		return currentQuestion;
	}

	public void setCurrentQuestion(QuestionImpl currentQuestion) {
		this.currentQuestion = currentQuestion;
	}

	public Letter getTrueAlternative() {
		return trueAlternative;
	}

	public void setTrueAlternative(Letter trueAlternative) {
		this.trueAlternative = trueAlternative;
	}

	public QuestionImpl getOldQuestion() {
		return oldQuestion;
	}

	public void setOldQuestion(QuestionImpl oldQuestion) {
		this.oldQuestion = oldQuestion;
	}

	public Boolean getIsANewQuestion() {
		return isANewQuestion;
	}

	public void setIsANewQuestion(Boolean isANewQuestion) {
		this.isANewQuestion = isANewQuestion;
	}

	public Boolean getIsOnlyView() {
		return isOnlyView;
	}

	public void setIsOnlyView(Boolean isOnlyView) {
		this.isOnlyView = isOnlyView;
	}
	
}
