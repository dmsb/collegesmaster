package br.com.collegesmaster.model.imp;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import br.com.collegesmaster.enums.Letter;
import br.com.collegesmaster.model.IAlternative;
import br.com.collegesmaster.model.IChallenge;
import br.com.collegesmaster.model.ICompletedChallenge;
import br.com.collegesmaster.model.IQuestion;

@Entity
@Table(name = "completed_challenge")
public class CompletedChallenge implements ICompletedChallenge, Serializable {

	private static final long serialVersionUID = -4223636598786128623L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "note", nullable = false, length = 11, unique = false)
	private Integer note;
	
	@ManyToOne(targetEntity = Challenge.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "responseChallengeFK", referencedColumnName = "id")
	private IChallenge response;
	
	@ManyToOne(targetEntity = Challenge.class, fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "challengeFK", referencedColumnName = "id")
	private IChallenge challenge;
	
	@PrePersist
	@PreUpdate
	private void buildNote() {
		note = 0;
		challenge.getQuestions().forEach(question -> {
			
			final Integer pontuation = question.getPontuation();
			final Map<Letter, Boolean> challengeResponses = new LinkedHashMap<Letter, Boolean>();
			final Map<Letter, Boolean> myResponses = new LinkedHashMap<Letter, Boolean>();
			
			final Integer indexOfCurrentQuestion = challenge.getQuestions().indexOf(question);
			final IQuestion myQuestion = response.getQuestions().get(indexOfCurrentQuestion);
			
			question.getResponse().forEach(response -> {
				
				final Integer indexOfCurrentResponse = question.getResponse().indexOf(response);
				final IAlternative myResponse = myQuestion.getResponse().get(indexOfCurrentResponse);
				
				myResponses.put(myResponse.getLetter(), myResponse.getDefinition());
				challengeResponses.put(response.getLetter(), response.getDefinition());
				
			});
			
			for(final Map.Entry<Letter, Boolean> responseChecker : challengeResponses.entrySet()) {							
				
				final Boolean responseDefinition = responseChecker.getValue();
				
				if(Boolean.TRUE.equals(responseDefinition)) {
					final Letter responseLetter = responseChecker.getKey();
					if(Boolean.TRUE.equals(myResponses.get(responseLetter))) {
						note = note + pontuation;
					}
				}
			}
		});	
	}
	
	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Override
	public Integer getNote() {
		return note;
	}

	@Override
	public void setNote(Integer note) {
		this.note = note;
	}

	@Override
	public IChallenge getChallenge() {
		return challenge;
	}

	@Override
	public void setChallenge(IChallenge challenge) {
		this.challenge = challenge;
	}

	@Override
	public IChallenge getResponse() {
		return response;
	}

	@Override
	public void setResponse(IChallenge response) {
		this.response = response;
	}
}
