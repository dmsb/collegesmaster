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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotBlank;

import br.com.collegesmaster.enums.Letter;
import br.com.collegesmaster.model.IAlternative;
import br.com.collegesmaster.model.IQuestion;

@Entity
@Table(name = "alternative")
@Access(FIELD)
@Audited
public class Alternative implements IAlternative {

	private static final long serialVersionUID = -9207076283580095871L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@NotNull
	@Enumerated(STRING)
	@Basic(fetch = LAZY, optional = false)
	@Column(name = "letter", unique = false, length = 1)
	private Letter letter;
	
	@NotBlank
    @Lob
    @Column(name = "description", unique= false, length = 150, nullable = false, columnDefinition = "text")
    private String description;
    
    @NotNull
	@Column(name = "definition", unique = false, nullable = false)
	private Boolean definition;
	
    @NotNull
	@ManyToOne(targetEntity = Question.class, optional = false, fetch = LAZY)
	@JoinColumn(name = "questionFK", referencedColumnName = "id")
	private IQuestion question;
	
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
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
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
	public IQuestion getQuestion() {
		return question;
	}

	@Override
	public void setQuestion(IQuestion question) {
		this.question = question;
	}

	@Override
	public boolean equals(final Object objectToBeComparated) {
		
		if(objectToBeComparated == this) {
			return true;
		}
		
		if(!(objectToBeComparated instanceof Alternative)) {
			return false;
		}
		
		final Alternative objectComparatedInstance = (Alternative) objectToBeComparated;
		
		return id == objectComparatedInstance.id && 
			    Objects.equals(description, objectComparatedInstance.description) &&
			    Objects.equals(definition, objectComparatedInstance.definition);
	}
	
	@Override
    public int hashCode() {
        return Objects.hash(id, description, definition);
    }
}
