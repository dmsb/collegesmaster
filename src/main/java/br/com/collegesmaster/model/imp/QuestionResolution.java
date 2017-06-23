package br.com.collegesmaster.model.imp;

import static javax.persistence.AccessType.FIELD;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.collegesmaster.enums.Letter;
import br.com.collegesmaster.model.IChallengeResolution;
import br.com.collegesmaster.model.IQuestion;
import br.com.collegesmaster.model.IQuestionResolution;

@Entity
@Table(name = "question_resolution")
@Access(FIELD)
public class QuestionResolution implements IQuestionResolution {
	
	private static final long serialVersionUID = 693650150648888820L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@ManyToOne(targetEntity = ChallengeResolution.class, optional = false, fetch = LAZY)
	@JoinColumn(name = "challengeResolutionFK", referencedColumnName = "id")
	private IChallengeResolution challengeResolution;
	
	@ManyToOne(targetEntity = Question.class, fetch = LAZY, optional = false)
	@JoinColumn(name = "targetQuestionFK", referencedColumnName = "id")
	private IQuestion targetQuestion;
	
	@Enumerated(EnumType.STRING)
	@Basic(fetch = FetchType.LAZY, optional = false)
	@Column(name = "letter", unique = false, length = 1)
	private Letter letter;
	
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
	public IQuestion getTargetQuestion() {
		return targetQuestion;
	}

	@Override
	public void setTargetQuestion(IQuestion targetQuestion) {
		this.targetQuestion = targetQuestion;
	}
	
	@Override
	public Letter getLetter() {
		return letter;
	}

	@Override
	public void setLetter(Letter letter) {
		this.letter = letter;
	}

	@Override
	public boolean equals(final Object objectToBeComparated) {
		
		if(objectToBeComparated == this) {
			return true;
		}
		
		if(!(objectToBeComparated instanceof Question)) {
			return false;
		}
		
		final QuestionResolution objectComparatedInstance = (QuestionResolution) objectToBeComparated;
		
		return id == objectComparatedInstance.id;
	}
	
	@Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
