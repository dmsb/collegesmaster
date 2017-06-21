package br.com.collegesmaster.jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import br.com.collegesmaster.business.IChallengeBusiness;
import br.com.collegesmaster.enums.Letter;
import br.com.collegesmaster.enums.QuestionLevel;
import br.com.collegesmaster.model.IAlternative;
import br.com.collegesmaster.model.IChallenge;
import br.com.collegesmaster.model.IDiscipline;
import br.com.collegesmaster.model.IQuestion;
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
	
	@ManagedProperty(value="#{challengeResponseMB}")
	private ChallengeResponseMB challengeResponseMB;
	
	private IChallenge challenge;
	
	private IQuestion currentQuestion;
	
	private List<IAlternative> alternatives;	
	
	private Letter[] letters;
	
	private QuestionLevel[] levels;
	
	private Letter trueAlternative;
	
	@PostConstruct
	public void init() {
		if(challenge == null) {
			challenge = new Challenge();
			challenge.setDiscipline(new Discipline());
			challenge.setOwner(userSessionMB.getUser());
			challenge.setQuestions(new LinkedHashSet<IQuestion>());			
		}
		
		currentQuestion = new Question();
		currentQuestion.setAlternatives(new LinkedHashSet<IAlternative>());
		
		letters = Letter.values();
		levels = QuestionLevel.values();
		
		alternatives = new ArrayList<>(4);
		alternatives.add(new Alternative());
		alternatives.add(new Alternative());
		alternatives.add(new Alternative());
		alternatives.add(new Alternative());
		
		trueAlternative = Letter.A;
		
	}	
	
	public void persistChallenge() {
		
		final Integer disciplineId = challenge.getDiscipline().getId();		
		final IDiscipline discipline = challengeResponseMB.getDisciplineBusiness().findById(disciplineId, Discipline.class);		
		challenge.setDiscipline(discipline);
		
		challengeBusiness.persist(challenge);
		
		init();
	}
	
	public void addQuestionToChallenge() {
		
		Integer count = 0;
		
		for(IAlternative alternative : alternatives) {
			alternative.setQuestion(currentQuestion);
			alternative.setLetter(letters[count]);
			
			if(trueAlternative.equals(alternative.getLetter())) {
				alternative.setDefinition(Boolean.TRUE);
			} else {
				alternative.setDefinition(Boolean.FALSE);
			}			
			count++;
		}
		
		final Set<IAlternative> alternativeSet = new LinkedHashSet<>();		
		alternatives.forEach(alternativeSet::add);
		
		currentQuestion.setAlternatives(alternativeSet);
		currentQuestion.setChallenge(challenge);
		challenge.getQuestions().add(currentQuestion);
		
		init();
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

	public IQuestion getCurrentQuestion() {
		return currentQuestion;
	}

	public void setCurrentQuestion(IQuestion currentQuestion) {
		this.currentQuestion = currentQuestion;
	}
	
	public List<IAlternative> getAlternatives() {
		return alternatives;
	}

	public void setAlternatives(List<IAlternative> alternatives) {
		this.alternatives = alternatives;
	}

	public Letter[] getLetters() {
		return letters;
	}

	public void setLetters(Letter[] letters) {
		this.letters = letters;
	}

	public QuestionLevel[] getLevels() {
		return levels;
	}

	public void setLevels(QuestionLevel[] levels) {
		this.levels = levels;
	}

	public Letter getTrueAlternative() {
		return trueAlternative;
	}

	public void setTrueAlternative(Letter trueAlternative) {
		this.trueAlternative = trueAlternative;
	}

	public ChallengeResponseMB getChallengeResponseMB() {
		return challengeResponseMB;
	}

	public void setChallengeResponseMB(ChallengeResponseMB challengeResponseMB) {
		this.challengeResponseMB = challengeResponseMB;
	}
	
}
