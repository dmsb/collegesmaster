package br.com.collegesmaster.model.impl;

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
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import br.com.collegesmaster.model.IChallengeResponse;
import br.com.collegesmaster.model.IQuestionResponse;
import br.com.collegesmaster.model.IUser;

@Entity
@Table(name = "challenge_response")
@Access(FIELD)
@Audited
public class ChallengeResponse implements IChallengeResponse {

	private static final long serialVersionUID = -4223636598786128623L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@NotAudited
	@NotNull
	@ManyToOne(targetEntity = User.class, optional = false, fetch = LAZY)
	@JoinColumn(name = "userFK", referencedColumnName = "id", updatable = false, nullable = false)
	private IUser owner;
	
	@NotNull
	@Column(name = "note", nullable = false, length = 11)
	private Integer note;
	
	@NotAudited
	@OneToMany(targetEntity = QuestionResponse.class, cascade = ALL, 
		fetch = LAZY, orphanRemoval = true, mappedBy = "challengeResponse")
	private List<IQuestionResponse> questionsResponse;
	
	@Version
	private Long version;
	
	@PrePersist
	@PreUpdate
	private void calculateNote() {
		note = 0;			
		
		for(final IQuestionResponse response : questionsResponse) {
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
	public List<IQuestionResponse> getQuestionsResponse() {
		return questionsResponse;
	}

	@Override
	public void setQuestionsResponse(List<IQuestionResponse> questionsResponse) {
		this.questionsResponse = questionsResponse;
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
		
		if(!(objectToBeComparated instanceof ChallengeResponse)) {
			return false;
		}
		
		final ChallengeResponse objectComparatedInstance = (ChallengeResponse) objectToBeComparated;
		
		return id == objectComparatedInstance.id && 
				note == objectComparatedInstance.note;
	}
	
	@Override
    public int hashCode() {
        return Objects.hash(id, note);
    }
	
}
