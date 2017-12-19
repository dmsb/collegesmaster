package br.com.collegesmaster.model.challenge.impl;

import static javax.persistence.AccessType.FIELD;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

import java.util.List;
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
import org.hibernate.validator.constraints.NotBlank;

import br.com.collegesmaster.model.challenge.Challenge;
import br.com.collegesmaster.model.challenge.Question;
import br.com.collegesmaster.model.model.impl.ModelImpl;

@Entity
@Table(name = "question")
@Access(FIELD)
@Audited
public class QuestionImpl extends ModelImpl implements Question {

	private static final long serialVersionUID = -8970625810455399880L;
	
	@NotBlank
	@Lob
	@Column(name = "description", nullable = false, unique = false, columnDefinition = "text")
	private String description;

	@NotNull
	@Min(0)
	@Max(100)
	@Column(name = "pontuation", nullable = false, length = 11)
	private Integer pontuation;

	@NotAudited
	@OneToMany(targetEntity = AlternativeImpl.class, cascade = ALL, fetch = EAGER, orphanRemoval = true, mappedBy = "question")
	private List<AlternativeImpl> alternatives;

	@NotNull
	@ManyToOne(targetEntity = ChallengeImpl.class, optional = false, fetch = LAZY)
	@JoinColumn(name = "challengeFK", referencedColumnName = "id", updatable = false,
		foreignKey = @ForeignKey(name = "QUESTION_challengeFK"))
	private Challenge challenge;
	
	@Override
	public Integer getPontuation() {
		return pontuation;
	}

	@Override
	public void setPontuation(Integer pontuation) {
		this.pontuation = pontuation;
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
	public void setAlternatives(List<AlternativeImpl> alternatives) {
		this.alternatives = alternatives;
	}

	@Override
	public List<AlternativeImpl> getAlternatives() {
		return alternatives;
	}

	@Override
	public boolean equals(final Object objectToBeComparated) {

		if (objectToBeComparated == this) {
			return true;
		}

		if (!(objectToBeComparated instanceof QuestionImpl)) {
			return false;
		}

		final QuestionImpl objectComparatedInstance = (QuestionImpl) objectToBeComparated;

		return id == objectComparatedInstance.id && Objects.equals(description, objectComparatedInstance.description)
				&& Objects.equals(pontuation, objectComparatedInstance.pontuation);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, description, pontuation);
	}
}
