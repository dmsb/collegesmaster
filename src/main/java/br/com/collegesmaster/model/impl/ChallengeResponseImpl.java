package br.com.collegesmaster.model.impl;

import static javax.persistence.AccessType.FIELD;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

import java.util.List;
import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import br.com.collegesmaster.model.Challenge;
import br.com.collegesmaster.model.ChallengeResponse;
import br.com.collegesmaster.model.QuestionResponse;
import br.com.collegesmaster.model.User;

@Entity
@Table(name = "challenge_response")
@Access(FIELD)
@Audited
public class ChallengeResponseImpl extends ModelImpl implements ChallengeResponse {

	private static final long serialVersionUID = -4223636598786128623L;
	
	@NotNull
	@ManyToOne(targetEntity = ChallengeImpl.class, optional = false, fetch = EAGER)
	@JoinColumn(name = "challengeFK", referencedColumnName = "id", updatable = false, nullable = false)
	private Challenge targetChallenge;
	
	@NotNull
	@ManyToOne(targetEntity = UserImpl.class, optional = false, fetch = LAZY)
	@JoinColumn(name = "userFK", referencedColumnName = "id", updatable = false, nullable = false)
	private User owner;
	
	@NotNull
	@Column(name = "pontuation", nullable = false, length = 11)
	private Integer pontuation;
	
	@NotAudited
	@OneToMany(targetEntity = QuestionResponseImpl.class, cascade = ALL, 
		fetch = LAZY, orphanRemoval = true, mappedBy = "challengeResponse")
	private List<QuestionResponse> questionsResponse;
	
	@PrePersist
	@PreUpdate
	private void calculatePontuation() {
		pontuation = 0;			
		
		for(final QuestionResponse response : questionsResponse) {
			response.getTargetQuestion()
				.getAlternatives()
				.forEach(alternative -> {					
					buildPontuation(response, alternative);
				});
		}
	}

	private void buildPontuation(final QuestionResponse response, AlternativeImpl alternative) {
		if(alternative.getDefinition() && 
				alternative.getLetter().equals(response.getLetter())) {
			pontuation = pontuation + response.getTargetQuestion().getPontuation();
		}
	}

	@Override
	public Challenge getTargetChallenge() {
		return targetChallenge;
	}

	@Override
	public void setTargetChallenge(Challenge targetChallenge) {
		this.targetChallenge = targetChallenge;
	}

	@Override
	public User getOwner() {
		return owner;
	}

	@Override
	public void setOwner(User owner) {
		this.owner = owner;
	}

	@Override
	public Integer getPontuation() {
		return pontuation;
	}

	@Override
	public void setPontuation(Integer pontuation) {
		this.pontuation = pontuation;
	}

	@Override
	public List<QuestionResponse> getQuestionsResponse() {
		return questionsResponse;
	}

	@Override
	public void setQuestionsResponse(List<QuestionResponse> questionsResponse) {
		this.questionsResponse = questionsResponse;
	}
	
	@Override
	public boolean equals(final Object objectToBeComparated) {

		if(objectToBeComparated == this) {
			return true;
		}
		
		if(!(objectToBeComparated instanceof ChallengeResponseImpl)) {
			return false;
		}
		
		final ChallengeResponseImpl objectComparatedInstance = (ChallengeResponseImpl) objectToBeComparated;
		
		return id == objectComparatedInstance.id && 
				pontuation == objectComparatedInstance.pontuation;
	}
	
	@Override
    public int hashCode() {
        return Objects.hash(id, pontuation);
    }
	
}
