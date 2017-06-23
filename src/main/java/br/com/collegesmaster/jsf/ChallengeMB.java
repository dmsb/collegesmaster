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
import br.com.collegesmaster.enums.Letter;
import br.com.collegesmaster.model.IAlternative;
import br.com.collegesmaster.model.IChallenge;
import br.com.collegesmaster.model.IDiscipline;
import br.com.collegesmaster.model.imp.Alternative;
import br.com.collegesmaster.model.imp.Challenge;
import br.com.collegesmaster.model.imp.Discipline;
import br.com.collegesmaster.model.imp.Question;

@ManagedBean(name = "challengeMB")
@ViewScoped
public class ChallengeMB implements Serializable {

	private static final long serialVersionUID = 6075067137564460555L;
	
	@EJB
	private transient IChallengeBusiness challengeBusiness;
	
	@ManagedProperty(value="#{userSessionMB}")
	private UserSessionMB userSessionMB;
	
	@ManagedProperty(value="#{challengeResolutionMB}")
	private ChallengeResolutionMB challengeResolutionMB;
	
	private IChallenge challenge;
	
	private Question currentQuestion;
	
	private List<Alternative> alternatives;
	
	private Letter trueAlternative;
	
	@PostConstruct
	public void init() {
		if(challenge == null) {
			challenge = new Challenge();
			challenge.setDiscipline(new Discipline());
			challenge.setOwner(userSessionMB.getUser());
			challenge.setQuestions(new ArrayList<Question>());			
		}
		
		currentQuestion = new Question();
		currentQuestion.setAlternatives(new ArrayList<Alternative>());
		
		alternatives = new ArrayList<>(4);
		alternatives.add(new Alternative());
		alternatives.add(new Alternative());
		alternatives.add(new Alternative());
		alternatives.add(new Alternative());		
		
		final Letter[] letters = Letter.values();
		
		for(int i = 0; i < letters.length; i++) {
			alternatives.get(i).setLetter(letters[i]);
		}

		trueAlternative = Letter.A;
		
	}	
	
	public void persistChallenge() {
		
		final Integer disciplineId = challenge.getDiscipline().getId();		
		final IDiscipline discipline = challengeResolutionMB.getDisciplineBusiness().findById(disciplineId, Discipline.class);		
		challenge.setDiscipline(discipline);
		
		challengeBusiness.persist(challenge);
		
		init();
	}
	
	public void addQuestionToChallenge() {
		
		for(IAlternative alternative : alternatives) {
			alternative.setQuestion(currentQuestion);
			
			if(trueAlternative.equals(alternative.getLetter())) {
				alternative.setDefinition(Boolean.TRUE);
			} else {
				alternative.setDefinition(Boolean.FALSE);
			}
		}

		currentQuestion.setAlternatives(alternatives);
		currentQuestion.setChallenge(challenge);
		challenge.getQuestions().add(currentQuestion);
		
		init();
	}
	
	public Letter[] loadlAllLetters() {
		return Letter.values();
	}
	
	public void removeQuestion() {
        challenge.getQuestions().remove(currentQuestion);
        currentQuestion = null;
    }
	
	public UserSessionMB getUserSessionMB() {
		return userSessionMB;
	}

	public void setUserSessionMB(UserSessionMB userSessionMB) {
		this.userSessionMB = userSessionMB;
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

	public ChallengeResolutionMB getChallengeResolutionMB() {
		return challengeResolutionMB;
	}

	public void setChallengeResolutionMB(ChallengeResolutionMB challengeResolutionMB) {
		this.challengeResolutionMB = challengeResolutionMB;
	}
	
}
