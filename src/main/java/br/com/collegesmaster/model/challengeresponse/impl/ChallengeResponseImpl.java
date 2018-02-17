package br.com.collegesmaster.model.challengeresponse.impl;

import static javax.persistence.AccessType.FIELD;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

import java.util.Collection;
import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import br.com.collegesmaster.model.challenge.Challenge;
import br.com.collegesmaster.model.challenge.impl.AlternativeImpl;
import br.com.collegesmaster.model.challenge.impl.ChallengeImpl;
import br.com.collegesmaster.model.challengeresponse.ChallengeResponse;
import br.com.collegesmaster.model.challengeresponse.QuestionResponse;
import br.com.collegesmaster.model.model.impl.ModelImpl;
import br.com.collegesmaster.model.security.User;
import br.com.collegesmaster.model.security.impl.UserImpl;

@Entity
@Table(name = "challenge_response",
	uniqueConstraints = @UniqueConstraint(columnNames = {"challengeFK", "userFK"},  name = "UK_CHALLENGE_USER"))
@Access(FIELD)
@Audited
public class ChallengeResponseImpl extends ModelImpl implements ChallengeResponse {

	private static final long serialVersionUID = -4223636598786128623L;
	
	@NotNull
	@ManyToOne(targetEntity = ChallengeImpl.class, optional = false, fetch = EAGER)
	@JoinColumn(name = "challengeFK", referencedColumnName = "id", updatable = false, nullable = false,
		foreignKey = @ForeignKey(name = "CR_challengeFK"))
	private Challenge targetChallenge;
	
	@NotNull
	@ManyToOne(targetEntity = UserImpl.class, optional = false, fetch = LAZY)
	@JoinColumn(name = "userFK", referencedColumnName = "id", updatable = false, nullable = false,
		foreignKey = @ForeignKey(name = "CR_userFK"))
	private User owner;
	
	@NotNull
	@Column(name = "punctuation", nullable = false, length = 11)
	private Integer punctuation;
	
	@NotNull
	@NotAudited
	@OneToMany(targetEntity = QuestionResponseImpl.class, cascade = ALL, 
		fetch = LAZY, orphanRemoval = true, mappedBy = "challengeResponse")
	private Collection<QuestionResponse> questionsResponse;
	
	@Override
	@PrePersist
	@PreUpdate
	public void calculatePunctuation() {
		punctuation = 0;
		questionsResponse.stream()
			.forEach(response -> { selectQuestionToProcessPunctuation(response); });
	}

	private void selectQuestionToProcessPunctuation(QuestionResponse response) {
		response.getTargetQuestion()
		.getAlternatives().stream()
			.forEach(alternative -> {					
			addPunctuation(response, alternative);
		});
	}

	@Override
	public void addPunctuation(final QuestionResponse response, AlternativeImpl alternative) {
		if(alternative.getIsTrue() && 
				alternative.getLetter().equals(response.getLetter())) {
			punctuation = punctuation + response.getTargetQuestion().getPunctuation();
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
	public Integer getPunctuation() {
		return punctuation;
	}

	@Override
	public void setPunctuation(Integer punctuation) {
		this.punctuation = punctuation;
	}

	@Override
	public Collection<QuestionResponse> getQuestionsResponse() {
		return questionsResponse;
	}

	@Override
	public void setQuestionsResponse(Collection<QuestionResponse> questionsResponse) {
		this.questionsResponse = questionsResponse;
	}
	
	@Override
	public boolean equals(final Object objectToBeComparated) {

		if(this == objectToBeComparated) {
			return true;
		}
		
		if(!(objectToBeComparated instanceof ChallengeResponseImpl)) {
			return false;
		}
		
		final ChallengeResponseImpl objectComparatedInstance = (ChallengeResponseImpl) objectToBeComparated;
		
		return Objects.equals(this.id, objectComparatedInstance.id) && 
				Objects.equals(this.punctuation, objectComparatedInstance.punctuation);
	}
	
	@Override
    public int hashCode() {
        return Objects.hash(id, punctuation);
    }
	
}
