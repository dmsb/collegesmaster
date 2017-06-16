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
import br.com.collegesmaster.enums.QuestionLevel;
import br.com.collegesmaster.model.IAlternative;
import br.com.collegesmaster.model.IChallenge;
import br.com.collegesmaster.model.IQuestion;
import br.com.collegesmaster.model.imp.Challenge;
import br.com.collegesmaster.model.imp.Question;

@ManagedBean(name = "challengeMB")
@ViewScoped
public class ChallengeMB implements Serializable {

	private static final long serialVersionUID = 6075067137564460555L;
	
	@EJB
	private IChallengeBusiness challengeBusiness;
	
	@ManagedProperty(value="#{userSessionMB}")
	private UserSessionMB userSessionMB;
	
	private IChallenge challenge;
	
	private IQuestion currentQuestion;
	
	private List<IAlternative> alternatives;	
	
	private Letter[] letters;
	
	private QuestionLevel[] levels;
	
	@PostConstruct
	public void init() {
		challenge = new Challenge();
		challenge.setOwner(userSessionMB.getUser());
		challenge.setQuestions(new ArrayList<IQuestion>());
		
		currentQuestion = new Question();
		currentQuestion.setAlternatives(new ArrayList<IAlternative>());
		
		alternatives = new ArrayList<>(5);		
		letters = Letter.values();
		levels = QuestionLevel.values();
	}
	
	public void addQuestionToChallenge() {
		alternatives.forEach(alternative -> alternative.setQuestion(currentQuestion));
		currentQuestion.setAlternatives(alternatives);
		currentQuestion.setChallenge(challenge);
		challenge.getQuestions().add(currentQuestion);
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
	
}
