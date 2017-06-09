package br.com.collegesmaster.model.imp;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.collegesmaster.model.IAlternativeResolution;
import br.com.collegesmaster.model.IChallengeResolution;
import br.com.collegesmaster.model.IQuestion;
import br.com.collegesmaster.model.IQuestionResolution;

@Entity
@Table(name = "question_resolution")
public class QuestionResolution implements IQuestionResolution, Serializable {
	
	private static final long serialVersionUID = 693650150648888820L;

	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@ManyToOne(targetEntity = ChallengeResolution.class, optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "challengeResolutionFK", referencedColumnName = "id")
	private IChallengeResolution challengeResolution;
	
	@OneToMany(targetEntity = AlternativeResolution.class, cascade = CascadeType.ALL,
		fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "questionResolution")
	private List<IAlternativeResolution> alternativesResolution;
	
	@ManyToOne(targetEntity = Question.class, fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "targetQuestionFK", referencedColumnName = "id")
	private IQuestion targetQuestion;
	
	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public IChallengeResolution getChallengeResolution() {
		return challengeResolution;
	}

	@Override
	public void setChallengeResolution(IChallengeResolution challengeResolution) {
		this.challengeResolution = challengeResolution;
	}

	@Override
	public List<IAlternativeResolution> getAlternativesResolution() {
		return alternativesResolution;
	}

	@Override
	public void setAlternativesResolution(List<IAlternativeResolution> alternativesResolution) {
		this.alternativesResolution = alternativesResolution;
	}

	@Override
	public IQuestion getTargetQuestion() {
		return targetQuestion;
	}

	@Override
	public void setTargetQuestion(IQuestion targetQuestion) {
		this.targetQuestion = targetQuestion;
	}
	
	@Override
	public boolean equals(final Object objectToBeComparated) {
		if(objectToBeComparated == null) {
			return false;
		}
		
		if((objectToBeComparated.getClass().isAssignableFrom(Challenge.class)) == false) {
			return false;
		}
		
		final IQuestionResolution objectComparatedInstance = (IQuestionResolution) objectToBeComparated;
		
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
