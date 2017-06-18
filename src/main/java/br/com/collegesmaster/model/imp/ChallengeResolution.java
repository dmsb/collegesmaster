package br.com.collegesmaster.model.imp;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.apache.commons.collections.CollectionUtils;

import br.com.collegesmaster.enums.Letter;
import br.com.collegesmaster.model.IAlternative;
import br.com.collegesmaster.model.IAlternativeResolution;
import br.com.collegesmaster.model.IChallenge;
import br.com.collegesmaster.model.IChallengeResolution;
import br.com.collegesmaster.model.IQuestion;
import br.com.collegesmaster.model.IQuestionResolution;
import br.com.collegesmaster.model.IUser;

@Entity
@Table(name = "challenge_resolution")
public class ChallengeResolution implements IChallengeResolution, Serializable {

	private static final long serialVersionUID = -4223636598786128623L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@ManyToOne(targetEntity = User.class, optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "userFK", referencedColumnName = "id")
	private IUser owner;
	
	@Column(name = "note", nullable = false, length = 11, unique = false)
	private Integer note;
	
	@OneToMany(targetEntity = QuestionResolution.class, cascade = CascadeType.ALL, 
		fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "challengeResolution")
	private Set<IQuestionResolution> questionsResolution;
	
	@ManyToOne(targetEntity = Challenge.class, fetch = FetchType.LAZY, optional = false, 
			cascade = {CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name = "targetChallengeFK", referencedColumnName = "id")
	private IChallenge targetChallenge;
	
	@PrePersist
	@PreUpdate
	private void calculateNote() {
		note = 0;			
		
		final Set<IQuestion> targetQuestions = targetChallenge.getQuestions();
		
		if(!(CollectionUtils.isEmpty(targetQuestions) || CollectionUtils.isEmpty(questionsResolution))) {
			
			final Iterator<IQuestion> targetQuestionIterator = targetQuestions.iterator();
			final Iterator<IQuestionResolution> questionResolutionIterator = questionsResolution.iterator();
			
			while(targetQuestionIterator.hasNext() && questionResolutionIterator.hasNext()) {
				
				final Map<Letter, Boolean> challengeResolution = new LinkedHashMap<Letter, Boolean>();
				final Map<Letter, Boolean> myResolution = new LinkedHashMap<Letter, Boolean>();
				
				final IQuestion targetQuestion = targetQuestionIterator.next();
				final Integer pontuation = targetQuestion.getPontuation();
				final IQuestionResolution myQuestion = questionResolutionIterator.next();
				
				buildResponses(pontuation, targetQuestion, challengeResolution, myResolution, myQuestion);
			}						
		}
	}

	private void buildResponses(final Integer pontuation, final IQuestion question, final Map<Letter, Boolean> challengeResolution,
			final Map<Letter, Boolean> myResolution, final IQuestionResolution myQuestion) {
		
		final Set<IAlternative> targetAlternatives = question.getAlternatives();
		final Set<IAlternativeResolution> alternativesResolution = myQuestion.getAlternativesResolution();			
		
		targetAlternatives.forEach(targetAlternative -> challengeResolution.put(targetAlternative.getLetter(), targetAlternative.getDefinition()));		
		
		alternativesResolution.forEach(alternativeResolution ->  {			
			myResolution.put(alternativeResolution.getLetter(), alternativeResolution.getDefinition());
		});			
		
		if(challengeResolution.equals(myResolution)) {
			note = note + pontuation;
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
	public IChallenge getTargetChallenge() {
		return targetChallenge;
	}

	@Override
	public void setTargetChallenge(IChallenge targetChallenge) {
		this.targetChallenge = targetChallenge;
	}

	@Override
	public Set<IQuestionResolution> getQuestionsResolution() {
		return questionsResolution;
	}

	@Override
	public void setQuestionsResolution(Set<IQuestionResolution> questionsResolution) {
		this.questionsResolution = questionsResolution;
	}
	
	@Override
	public boolean equals(final Object objectToBeComparated) {
		if(objectToBeComparated == null) {
			return false;
		}
		
		if((objectToBeComparated.getClass().isAssignableFrom(Challenge.class)) == false) {
			return false;
		}
		
		final IChallengeResolution objectComparatedInstance = (IChallengeResolution) objectToBeComparated;
		
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
