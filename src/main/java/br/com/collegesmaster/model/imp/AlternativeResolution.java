package br.com.collegesmaster.model.imp;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Basic;
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
import javax.persistence.Table;

import br.com.collegesmaster.enums.Letter;
import br.com.collegesmaster.model.IAlternative;
import br.com.collegesmaster.model.IAlternativeResolution;
import br.com.collegesmaster.model.IQuestionResolution;

@Entity
@Table(name = "alternative_resolution")
public class AlternativeResolution implements IAlternativeResolution, Serializable {

	private static final long serialVersionUID = -8281078857129961287L;
	
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Enumerated(EnumType.STRING)
	@Basic(fetch = FetchType.LAZY, optional = false)
	@Column(name = "letter", unique = false, length = 1)
	private Letter letter;
	
	@Column(name = "definition", unique = false, nullable = false)
	private Boolean definition;
	
	@ManyToOne(targetEntity = QuestionResolution.class, optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "questionResolutionFK", referencedColumnName = "id")
	private IQuestionResolution questionResolution;
	
	@ManyToOne(targetEntity = Alternative.class, fetch = FetchType.LAZY, optional = false)
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
		if(objectToBeComparated == null) {
			return false;
		}
		
		if((objectToBeComparated.getClass().isAssignableFrom(Challenge.class)) == false) {
			return false;
		}
		
		final IAlternativeResolution objectComparatedInstance = (IAlternativeResolution) objectToBeComparated;
		
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
