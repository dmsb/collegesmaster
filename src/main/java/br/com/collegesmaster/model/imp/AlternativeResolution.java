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
import br.com.collegesmaster.model.IAlternative;
import br.com.collegesmaster.model.IAlternativeResolution;
import br.com.collegesmaster.model.IQuestionResolution;

@Entity
@Table(name = "alternative_resolution")
@Access(FIELD)
public class AlternativeResolution implements IAlternativeResolution {

	private static final long serialVersionUID = -8281078857129961287L;
	
	@Id
	@GeneratedValue(strategy =IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Enumerated(EnumType.STRING)
	@Basic(fetch = FetchType.LAZY, optional = false)
	@Column(name = "letter", unique = false, length = 1)
	private Letter letter;
	
	@Column(name = "definition", unique = false, nullable = false)
	private Boolean definition;
	
	@ManyToOne(targetEntity = QuestionResolution.class, optional = false, fetch = LAZY)
	@JoinColumn(name = "questionResolutionFK", referencedColumnName = "id")
	private IQuestionResolution questionResolution;
	
	@ManyToOne(targetEntity = Alternative.class, fetch = LAZY, optional = false)
	@JoinColumn(name = "targetAlternativeFK", referencedColumnName = "id")
	private IAlternative targetAlternative;
	
	@Override
	public Integer getId() {		
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
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
	public Boolean getDefinition() {
		return definition;
	}

	@Override
	public void setDefinition(Boolean definition) {
		this.definition = definition;
	}

	@Override
	public IQuestionResolution getQuestionResolution() {
		return questionResolution;
	}

	@Override
	public void setQuestionResolution(IQuestionResolution questionResolution) {
		this.questionResolution = questionResolution;
	}

	@Override
	public IAlternative getTargetAlternative() {
		return targetAlternative;
	}

	@Override
	public void setTargetAlternative(IAlternative targetAlternative) {
		this.targetAlternative = targetAlternative;
	}
	
	@Override
	public boolean equals(final Object objectToBeComparated) {

		if(objectToBeComparated == this) {
			return true;
		}
		
		if(!(objectToBeComparated instanceof AlternativeResolution)) {
			return false;
		}
		
		final AlternativeResolution objectComparatedInstance = (AlternativeResolution) objectToBeComparated;
		
		return id == objectComparatedInstance.id && 
			    Objects.equals(letter, objectComparatedInstance.letter) &&
			    Objects.equals(definition, objectComparatedInstance.definition);
	}
	
	@Override
    public int hashCode() {
        return Objects.hash(id, letter, definition);
    }
	
}
