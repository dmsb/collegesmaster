package br.com.collegesmaster.model.imp;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.collegesmaster.enums.QuestionLevel;
import br.com.collegesmaster.model.IAlternative;
import br.com.collegesmaster.model.IChallenge;
import br.com.collegesmaster.model.IQuestion;

@Entity
@Table(name = "question")
public class Question implements IQuestion, Serializable {

	private static final long serialVersionUID = -8970625810455399880L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "description", nullable = false, unique = false)
	private String description;
	
	@NotNull
	@Column(name = "pontuation", nullable = false, length = 11)
	private Integer pontuation;
	
	@NotNull
	@OneToMany(targetEntity = Alternative.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY,
		orphanRemoval = true, mappedBy = "question")
	private Set<IAlternative> alternatives;
	
	@NotNull
	@Basic(optional = false)
	@Enumerated(EnumType.ORDINAL)
	private QuestionLevel level;
	
	@ManyToOne(targetEntity = Challenge.class, optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "challengeFK", referencedColumnName = "id")
	private IChallenge challenge;
	
	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
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
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public QuestionLevel getLevel() {
		return level;
	}

	@Override
	public void setLevel(QuestionLevel level) {
		this.level = level;
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
	public void setAlternatives(Set<IAlternative> alternatives) {
		this.alternatives = alternatives;
	}

	@Override
	public Set<IAlternative> getAlternatives() {
		return alternatives;
	}

	@Override
	public boolean equals(final Object objectToBeComparated) {
		if(objectToBeComparated == null) {
			return false;
		}
		
		if((objectToBeComparated.getClass().isAssignableFrom(Challenge.class)) == false) {
			return false;
		}
		
		final IQuestion objectComparatedInstance = (IQuestion) objectToBeComparated;
		
		if(getId() != null && objectComparatedInstance.getId() != null) {
			return false;
		}
		
		return Objects.equals(getId(), objectComparatedInstance.getId());
	}
	
	@Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
