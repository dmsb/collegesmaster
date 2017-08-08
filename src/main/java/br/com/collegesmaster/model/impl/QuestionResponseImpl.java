package br.com.collegesmaster.model.impl;

import static javax.persistence.AccessType.FIELD;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import br.com.collegesmaster.enums.Letter;
import br.com.collegesmaster.model.ChallengeResponse;
import br.com.collegesmaster.model.Question;
import br.com.collegesmaster.model.QuestionResponse;

@Entity
@Table(name = "question_response")
@Access(FIELD)
@Audited
public class QuestionResponseImpl implements QuestionResponse {
	
	private static final long serialVersionUID = 693650150648888820L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	protected Integer id;
	
	@Version
	protected Long version;
	
	@NotNull
	@NotAudited
	@ManyToOne(targetEntity = ChallengeResponseImpl.class, optional = false, fetch = LAZY)
	@JoinColumn(name = "challengeResponseFK", referencedColumnName = "id", updatable = false)
	private ChallengeResponse challengeResponse;
	
	@NotNull
	@NotAudited
	@ManyToOne(targetEntity = QuestionImpl.class, fetch = LAZY, optional = false)
	@JoinColumn(name = "targetQuestionFK", referencedColumnName = "id", updatable = false)
	private Question targetQuestion;
	
	@NotNull
	@Enumerated(STRING)
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
	public Long getVersion() {
		return version;
	}

	@Override
	public void setVersion(Long version) {
		this.version = version;
	}
	
	@Override
	public ChallengeResponse getChallengeResponse() {
		return challengeResponse;
	}

	@Override
	public void setChallengeResponse(ChallengeResponse challengeResponse) {
		this.challengeResponse = challengeResponse;
	}

	@Override
	public Question getTargetQuestion() {
		return targetQuestion;
	}

	@Override
	public void setTargetQuestion(Question targetQuestion) {
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
		
		if(!(objectToBeComparated instanceof QuestionImpl)) {
			return false;
		}
		
		final QuestionResponseImpl objectComparatedInstance = (QuestionResponseImpl) objectToBeComparated;
		
		return id == objectComparatedInstance.id;
	}
	
	@Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
