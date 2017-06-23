package br.com.collegesmaster.model.imp;

import static javax.persistence.AccessType.FIELD;
import static javax.persistence.CascadeType.ALL;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import br.com.collegesmaster.model.IChallengeResolution;
import br.com.collegesmaster.model.IQuestionResolution;
import br.com.collegesmaster.model.IUser;

@Entity
@Table(name = "challenge_resolution")
@Access(FIELD)
public class ChallengeResolution implements IChallengeResolution {

	private static final long serialVersionUID = -4223636598786128623L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@ManyToOne(targetEntity = User.class, optional = false, fetch = LAZY)
	@JoinColumn(name = "userFK", referencedColumnName = "id")
	private IUser owner;
	
	@Column(name = "note", nullable = false, length = 11, unique = false)
	private Integer note;
	
	@OneToMany(targetEntity = QuestionResolution.class, cascade = ALL, 
		fetch = LAZY, orphanRemoval = true, mappedBy = "challengeResolution")
	private List<IQuestionResolution> questionsResolution;
	
	@PrePersist
	@PreUpdate
	private void calculateNote() {
		note = 0;			
		
		for(final IQuestionResolution response : questionsResolution) {
			response.getTargetQuestion()
				.getAlternatives()
				.forEach(alternative -> {					
					if(alternative.getDefinition() && 
							alternative.getLetter().equals(response.getLetter())) {
						note = note + response.getTargetQuestion().getPontuation();
					}
				});
		}
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
	public IUser getOwner() {
		return owner;
	}

	@Override
	public void setOwner(IUser owner) {
		this.owner = owner;
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
	public List<IQuestionResolution> getQuestionsResolution() {
		return questionsResolution;
	}

	@Override
	public void setQuestionsResolution(List<IQuestionResolution> questionsResolution) {
		this.questionsResolution = questionsResolution;
	}
	
	@Override
	public boolean equals(final Object objectToBeComparated) {

		if(objectToBeComparated == this) {
			return true;
		}
		
		if(!(objectToBeComparated instanceof ChallengeResolution)) {
			return false;
		}
		
		final ChallengeResolution objectComparatedInstance = (ChallengeResolution) objectToBeComparated;
		
		return id == objectComparatedInstance.id && 
				note == objectComparatedInstance.note;
	}
	
	@Override
    public int hashCode() {
        return Objects.hash(id, note);
    }
	
}
