package br.com.collegesmaster.jsf;

import static br.com.collegesmaster.jsf.util.JsfUtils.addMessage;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import static javax.faces.application.FacesMessage.SEVERITY_INFO;
import static javax.faces.application.FacesMessage.SEVERITY_WARN;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.collegesmaster.annotation.qualifier.LoggedIn;
import br.com.collegesmaster.business.ChallengeBusiness;
import br.com.collegesmaster.business.DisciplineBusiness;
import br.com.collegesmaster.enums.Letter;
import br.com.collegesmaster.model.Alternative;
import br.com.collegesmaster.model.Discipline;
import br.com.collegesmaster.model.User;
import br.com.collegesmaster.model.impl.AlternativeImpl;
import br.com.collegesmaster.model.impl.ChallengeImpl;
import br.com.collegesmaster.model.impl.DisciplineImpl;
import br.com.collegesmaster.model.impl.QuestionImpl;

@Named("challengeMB")
@ViewScoped
public class ChallengeMB implements Serializable {

	private static final long serialVersionUID = 6075067137564460555L;
	
	@Inject
	private transient ChallengeBusiness challengeBusiness;
	
	@Inject
	private transient DisciplineBusiness disciplineBusiness;
	
	@Inject @LoggedIn 
	private User loggedUser;
	
	private ChallengeImpl challenge;
	
	private QuestionImpl currentQuestion;
	
	private List<AlternativeImpl> alternatives;
	
	private Letter trueAlternative;
	
	@PostConstruct
	public void init() {
		challenge = new ChallengeImpl();
		challenge.setDiscipline(new DisciplineImpl());
		challenge.setOwner(loggedUser);
		challenge.setQuestions(new ArrayList<QuestionImpl>());
		
		initCurrentQuestion();
	}
	
	public List<DisciplineImpl> loadUserDisciplines() {
		return disciplineBusiness.findNamesByCourse(loggedUser.getCourse());
	}
	
	public void initCurrentQuestion() {
		currentQuestion = new QuestionImpl();
		currentQuestion.setAlternatives(new ArrayList<AlternativeImpl>());
		initAlternatives();
	}

	private void initAlternatives() {
		
		alternatives = new ArrayList<>(4);
		alternatives.add(new AlternativeImpl());
		alternatives.add(new AlternativeImpl());
		alternatives.add(new AlternativeImpl());
		alternatives.add(new AlternativeImpl());
		
		final Letter[] letters = Letter.values();
		
		for(int i = 0; i < letters.length; i++) {
			alternatives.get(i).setLetter(letters[i]);
		}
		
		trueAlternative = null;
	}
	
	public void persistChallenge() {
		
		final Integer disciplineId = challenge.getDiscipline().getId();		
		
		final Discipline discipline = disciplineBusiness.findById(disciplineId);
		
		challenge.setDiscipline(discipline);
		
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
			
			for(final Alternative alternative : alternatives) {
				alternative.setQuestion(currentQuestion);
				
				if(trueAlternative.equals(alternative.getLetter())) {
					alternative.setIsTrue(TRUE);
				} else {
					alternative.setIsTrue(FALSE);
				}
			}
			
			currentQuestion.setAlternatives(alternatives);
			
			challenge.getQuestions().add(currentQuestion);
			
			addMessage(SEVERITY_INFO, "questiond_added_message");
		} else {
			addMessage(SEVERITY_WARN, "correct_alternative_required_message");
		}
	}
	
	public Letter[] loadAllLetters() {
		return Letter.values();
	}

	public ChallengeImpl getChallenge() {
		return challenge;
	}

	public void setChallenge(ChallengeImpl challenge) {
		this.challenge = challenge;
	}

	public QuestionImpl getCurrentQuestion() {
		return currentQuestion;
	}

	public void setCurrentQuestion(QuestionImpl currentQuestion) {
		this.currentQuestion = currentQuestion;
	}
	
	public List<AlternativeImpl> getAlternatives() {
		return alternatives;
	}

	public void setAlternatives(List<AlternativeImpl> alternatives) {
		this.alternatives = alternatives;
	}

	public Letter getTrueAlternative() {
		return trueAlternative;
	}

	public void setTrueAlternative(Letter trueAlternative) {
		this.trueAlternative = trueAlternative;
	}
	
}
