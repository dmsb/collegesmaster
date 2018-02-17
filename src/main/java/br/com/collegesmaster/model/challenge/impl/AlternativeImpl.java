package br.com.collegesmaster.model.challenge.impl;

import static javax.persistence.AccessType.FIELD;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.envers.Audited;

import br.com.collegesmaster.model.challenge.Alternative;
import br.com.collegesmaster.model.challenge.Question;
import br.com.collegesmaster.model.challenge.enums.Letter;
import br.com.collegesmaster.model.model.impl.ModelImpl;

@Entity
@Table(name = "alternative")
@Access(FIELD)
@Audited
public class AlternativeImpl extends ModelImpl implements Alternative {

	private static final long serialVersionUID = -9207076283580095871L;
	
	@NotNull
	@Enumerated(STRING)
	@Basic(fetch = LAZY, optional = false)
	@Column(name = "letter", unique = false, length = 1)
	private Letter letter;
	
	@NotNull
	@Size
    @Lob
    @Column(name = "description", unique= false, length = 150, nullable = false, columnDefinition = "text")
    private String description;
    
    @NotNull
	@Column(name = "isTrue", unique = false, nullable = false)
	private Boolean isTrue;
	
    @NotNull
	@ManyToOne(targetEntity = QuestionImpl.class, optional = false, fetch = LAZY)
	@JoinColumn(name = "questionFK", referencedColumnName = "id", 
		foreignKey = @ForeignKey(name = "ALTERNATIVE_questionFK"))
	private Question question;
	
	@Override
	public Letter getLetter() {
		return letter;
	}

	@Override
	public void setLetter(Letter letter) {
		this.letter = letter;
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
	public Boolean getIsTrue() {
		return isTrue;
	}

	@Override
	public void setIsTrue(Boolean isTrue) {
		this.isTrue = isTrue;
	}
	
	@Override
	public Question getQuestion() {
		return question;
	}

	@Override
	public void setQuestion(Question question) {
		this.question = question;
	}
	
	@Override
	public boolean equals(final Object objectToBeComparated) {
		
		if(this == objectToBeComparated) {
			return true;
		}
		
		if(!(objectToBeComparated instanceof AlternativeImpl)) {
			return false;
		}
		
		final AlternativeImpl objectComparatedInstance = (AlternativeImpl) objectToBeComparated;
		
		return Objects.equals(this.id, objectComparatedInstance.id) && 
			    Objects.equals(description, objectComparatedInstance.description) &&
			    Objects.equals(isTrue, objectComparatedInstance.isTrue);
	}
	
	@Override
    public int hashCode() {
        return Objects.hash(id, description, isTrue);
    }
}
