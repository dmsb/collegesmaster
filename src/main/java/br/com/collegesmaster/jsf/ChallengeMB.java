package br.com.collegesmaster.jsf;

import static br.com.collegesmaster.jsf.util.JSFUtils.addMessage;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
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
import br.com.collegesmaster.business.IChallengeBusiness;
import br.com.collegesmaster.business.IDisciplineBusiness;
import br.com.collegesmaster.enums.Letter;
import br.com.collegesmaster.model.IAlternative;
import br.com.collegesmaster.model.IChallenge;
import br.com.collegesmaster.model.IDiscipline;
import br.com.collegesmaster.model.IUser;
import br.com.collegesmaster.model.impl.Alternative;
import br.com.collegesmaster.model.impl.Challenge;
import br.com.collegesmaster.model.impl.Discipline;
import br.com.collegesmaster.model.impl.Question;

@Named("challengeMB")
@ViewScoped
public class ChallengeMB implements Serializable {

	private static final long serialVersionUID = 6075067137564460555L;
	
	@Inject
	private transient IChallengeBusiness challengeBusiness;
	
	@Inject
	private transient IDisciplineBusiness disciplineBusiness;
	
	@Inject @LoggedIn 
	private IUser loggedUser;
	
	private IChallenge challenge;
	
	private Question currentQuestion;
	
	private List<Alternative> alternatives;
	
	private Letter trueAlternative;
	
	@PostConstruct
	public void init() {
		challenge = new Challenge();
		challenge.setDiscipline(new Discipline());
		challenge.setOwner(loggedUser);
		challenge.setQuestions(new ArrayList<Question>());
		
		initCurrentQuestion();
	}
	
	public List<Discipline> loadUserDisciplines() {
		return disciplineBusiness.findNamesByCourse(loggedUser.getGeneralInfo().getCourse());
	}
	
	public void initCurrentQuestion() {
		currentQuestion = new Question();
		currentQuestion.setAlternatives(new ArrayList<Alternative>());
		initAlternatives();
	}

	private void initAlternatives() {
		alternatives = new ArrayList<>(4);
		alternatives.add(new Alternative());
		alternatives.add(new Alternative());
		alternatives.add(new Alternative());
		alternatives.add(new Alternative());
		
		final Letter[] letters = Letter.values();
		
		for(int i = 0; i < letters.length; i++) {
			alternatives.get(i).setLetter(letters[i]);
		}
		
		trueAlternative = null;
	}
	
	public void persistChallenge() {
		
		final Integer disciplineId = challenge.getDiscipline().getId();		
		
		final IDiscipline discipline = disciplineBusiness.findById(disciplineId);
		
		challenge.setDiscipline(discipline);
		
		challengeBusiness.create(challenge);
		addMessage(SEVERITY_INFO, "challenge_registred_with_success_message");

		init();
	}
	
	public void addQuestionToChallenge() {
		
		if(trueAlternative != null) {
			
			currentQuestion.setChallenge(challenge);
			
			for(final IAlternative alternative : alternatives) {
				alternative.setQuestion(currentQuestion);
				
				if(trueAlternative.equals(alternative.getLetter())) {
					alternative.setDefinition(TRUE);
				} else {
					alternative.setDefinition(FALSE);
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

	public IChallenge getChallenge() {
		return challenge;
	}

	public void setChallenge(IChallenge challenge) {
		this.challenge = challenge;
	}

	public Question getCurrentQuestion() {
		return currentQuestion;
	}

	public void setCurrentQuestion(Question currentQuestion) {
		this.currentQuestion = currentQuestion;
	}
	
	public List<Alternative> getAlternatives() {
		return alternatives;
	}

	public void setAlternatives(List<Alternative> alternatives) {
		this.alternatives = alternatives;
	}

	public Letter getTrueAlternative() {
		return trueAlternative;
	}

	public void setTrueAlternative(Letter trueAlternative) {
		this.trueAlternative = trueAlternative;
	}
	
}
