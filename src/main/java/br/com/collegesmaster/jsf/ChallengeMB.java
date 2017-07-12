package br.com.collegesmaster.jsf;

import static br.com.collegesmaster.util.JSFUtils.addMessage;
import static br.com.collegesmaster.util.JSFUtils.addMessageWithDetails;
import static br.com.collegesmaster.util.JSFUtils.getFullyPrincipalUser;
import static javax.faces.application.FacesMessage.SEVERITY_INFO;

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
import br.com.collegesmaster.model.impl.Alternative;
import br.com.collegesmaster.model.impl.Challenge;
import br.com.collegesmaster.model.impl.Discipline;
import br.com.collegesmaster.model.impl.Question;

@ManagedBean(name = "challengeMB")
@ViewScoped
public class ChallengeMB implements Serializable {

	private static final long serialVersionUID = 6075067137564460555L;
	
	@EJB
	private transient IChallengeBusiness challengeBusiness;
	
	@ManagedProperty(value="#{challengeResponseMB}")
	private ChallengeResponseMB challengeResponseMB;
	
	private IChallenge challenge;
	
	private Question currentQuestion;
	
	private List<Alternative> alternatives;
	
	private Letter trueAlternative;
	
	@PostConstruct
	public void init() {
		challenge = new Challenge();
		challenge.setDiscipline(new Discipline());
		challenge.setOwner(getFullyPrincipalUser());
		challenge.setQuestions(new ArrayList<Question>());
		
		resetCurrentQuestion();
	}

	private void resetCurrentQuestion() {
		currentQuestion = new Question();
		currentQuestion.setAlternatives(new ArrayList<Alternative>());
		resetAlternatives();
	}

	private void resetAlternatives() {
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
		
		final IDiscipline discipline = challengeResponseMB
			.getDisciplineBusiness()
			.findById(disciplineId);
		
		challenge.setDiscipline(discipline);
		
		challengeBusiness.save(challenge);
		
		addMessageWithDetails(SEVERITY_INFO, "msg_success", "msg_challenge_registred_with_success");

		init();
	}
	
	public void addQuestionToChallenge() {
		
		currentQuestion.setChallenge(challenge);
		
		for(final IAlternative alternative : alternatives) {
			alternative.setQuestion(currentQuestion);
			
			if(trueAlternative.equals(alternative.getLetter())) {
				alternative.setDefinition(Boolean.TRUE);
			} else {
				alternative.setDefinition(Boolean.FALSE);
			}
		}

		currentQuestion.setAlternatives(alternatives);
		
		challenge.getQuestions().add(currentQuestion);
		
		resetCurrentQuestion();

		addMessage(SEVERITY_INFO, "msg_questiond_added");
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

	public ChallengeResponseMB getchallengeResponseMB() {
		return challengeResponseMB;
	}

	public void setchallengeResponseMB(ChallengeResponseMB challengeResponseMB) {
		this.challengeResponseMB = challengeResponseMB;
	}
	
}
