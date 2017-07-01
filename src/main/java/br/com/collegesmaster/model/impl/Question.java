package br.com.collegesmaster.model.impl;

import static javax.persistence.AccessType.FIELD;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.List;
import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.validator.constraints.NotBlank;

import br.com.collegesmaster.model.IChallenge;
import br.com.collegesmaster.model.IQuestion;

@Entity
@Table(name = "question")
@Access(FIELD)
@Audited
public class Question implements IQuestion {

	private static final long serialVersionUID = -8970625810455399880L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	private Integer id;
	
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
	@OneToMany(targetEntity = Alternative.class, cascade = ALL, fetch = EAGER,
		orphanRemoval = true, mappedBy = "question")
	private List<Alternative> alternatives;
	
	@NotAudited
	@NotNull
	@ManyToOne(targetEntity = Challenge.class, optional = false, fetch = LAZY)
	@JoinColumn(name = "challengeFK", referencedColumnName = "id", updatable = false)
	private IChallenge challenge;
	
	@Version
	private Long version;
	
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
	public IChallenge getChallenge() {
		return challenge;
	}

	@Override
	public void setChallenge(IChallenge challenge) {
		this.challenge = challenge;
	}
	
	@Override
	public void setAlternatives(List<Alternative> alternatives) {
		this.alternatives = alternatives;
	}

	@Override
	public List<Alternative> getAlternatives() {
		return alternatives;
	}
	
	@Override
	public Long getVersion() {
		return version;
	}
	
	@Override
	public void setVersion(Long version) {
		this.version = version;
	}
	
	@Override
	public boolean equals(final Object objectToBeComparated) {
		
		if(objectToBeComparated == this) {
			return true;
		}
		
		if(!(objectToBeComparated instanceof Question)) {
			return false;
		}
		
		final Question objectComparatedInstance = (Question) objectToBeComparated;
		
		return id == objectComparatedInstance.id && 
				Objects.equals(description, objectComparatedInstance.description) &&
				Objects.equals(pontuation, objectComparatedInstance.pontuation);
	}
	
	@Override
    public int hashCode() {	
        return Objects.hash(id, description, pontuation);
    }
}
