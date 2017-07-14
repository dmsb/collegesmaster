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

import br.com.collegesmaster.model.IChallenge;
import br.com.collegesmaster.model.IChallengeResponse;
import br.com.collegesmaster.model.IQuestionResponse;
import br.com.collegesmaster.model.IUser;

@Entity
@Table(name = "challenge_response")
@Access(FIELD)
@Audited
public class ChallengeResponse extends Model implements IChallengeResponse {

	private static final long serialVersionUID = -4223636598786128623L;
	
	@NotNull
	@ManyToOne(targetEntity = Challenge.class, optional = false, fetch = EAGER)
	@JoinColumn(name = "challengeFK", referencedColumnName = "id", updatable = false, nullable = false)
	private IChallenge targetChallenge;
	
	@NotNull
	@ManyToOne(targetEntity = User.class, optional = false, fetch = LAZY)
	@JoinColumn(name = "userFK", referencedColumnName = "id", updatable = false, nullable = false)
	private IUser owner;
	
	@NotNull
	@Column(name = "pontuation", nullable = false, length = 11)
	private Integer pontuation;
	
	@NotAudited
	@OneToMany(targetEntity = QuestionResponse.class, cascade = ALL, 
		fetch = LAZY, orphanRemoval = true, mappedBy = "challengeResponse")
	private List<IQuestionResponse> questionsResponse;
	
	@PrePersist
	@PreUpdate
	private void calculatePontuation() {
		pontuation = 0;			
		
		for(final IQuestionResponse response : questionsResponse) {
			response.getTargetQuestion()
				.getAlternatives()
				.forEach(alternative -> {					
					buildPontuation(response, alternative);
				});
		}
	}

	private void buildPontuation(final IQuestionResponse response, Alternative alternative) {
		if(alternative.getDefinition() && 
				alternative.getLetter().equals(response.getLetter())) {
			pontuation = pontuation + response.getTargetQuestion().getPontuation();
		}
	}
	
	
	@Override
	public IChallenge getTargetChallenge() {
		return targetChallenge;
	}

	@Override
	public void setTargetChallenge(IChallenge targetChallenge) {
		this.targetChallenge = targetChallenge;
	}

	@Override
	public IUser getOwner() {
		return owner;
	}

	@Override
	public void setOwner(IUser owner) {
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
	public List<IQuestionResponse> getQuestionsResponse() {
		return questionsResponse;
	}

	@Override
	public void setQuestionsResponse(List<IQuestionResponse> questionsResponse) {
		this.questionsResponse = questionsResponse;
	}
	
	@Override
	public boolean equals(final Object objectToBeComparated) {

		if(objectToBeComparated == this) {
			return true;
		}
		
		if(!(objectToBeComparated instanceof ChallengeResponse)) {
			return false;
		}
		
		final ChallengeResponse objectComparatedInstance = (ChallengeResponse) objectToBeComparated;
		
		return id == objectComparatedInstance.id && 
				pontuation == objectComparatedInstance.pontuation;
	}
	
	@Override
    public int hashCode() {
        return Objects.hash(id, pontuation);
    }
	
}
