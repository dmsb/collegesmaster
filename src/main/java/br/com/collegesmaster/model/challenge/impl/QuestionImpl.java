package br.com.collegesmaster.model.challenge.impl;

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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import br.com.collegesmaster.model.challenge.Challenge;
import br.com.collegesmaster.model.challenge.Question;
import br.com.collegesmaster.model.model.impl.ModelImpl;

@Entity
@Table(name = "question")
@Access(FIELD)
@Audited
public class QuestionImpl extends ModelImpl implements Question {

	private static final long serialVersionUID = -8970625810455399880L;
	
	@NotNull
	@Lob
	@Column(name = "description", nullable = false, unique = false, columnDefinition = "text")
	private String description;

	@NotNull
	@Min(0)
	@Max(100)
	@Column(name = "punctuation", nullable = false, length = 11)
	private Integer punctuation;

	@NotNull
	@NotAudited
	@OneToMany(targetEntity = AlternativeImpl.class, cascade = ALL, fetch = EAGER, 
		orphanRemoval = true, mappedBy = "question")
	private Collection<AlternativeImpl> alternatives;

	@NotNull
	@ManyToOne(targetEntity = ChallengeImpl.class, optional = false, fetch = LAZY)
	@JoinColumn(name = "challengeFK", referencedColumnName = "id", updatable = false,
		foreignKey = @ForeignKey(name = "QUESTION_challengeFK"))
	private Challenge challenge;
	
	@Override
	public Integer getPunctuation() {
		return punctuation;
	}

	@Override
	public void setPunctuation(Integer punctuation) {
		this.punctuation = punctuation;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public Challenge getChallenge() {
		return challenge;
	}

	@Override
	public void setChallenge(Challenge challenge) {
		this.challenge = challenge;
	}

	@Override
	public void setAlternatives(Collection<AlternativeImpl> alternatives) {
		this.alternatives = alternatives;
	}

	@Override
	public Collection<AlternativeImpl> getAlternatives() {
		return alternatives;
	}

	@Override
	public boolean equals(final Object objectToBeComparated) {

		if(this == objectToBeComparated) {
			return true;
		}

		if (!(objectToBeComparated instanceof QuestionImpl)) {
			return false;
		}

		final QuestionImpl objectComparatedInstance = (QuestionImpl) objectToBeComparated;

		return Objects.equals(this.id, objectComparatedInstance.id) && 
				Objects.equals(description, objectComparatedInstance.description) &&
				Objects.equals(punctuation, objectComparatedInstance.punctuation);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, description, punctuation);
	}
}
