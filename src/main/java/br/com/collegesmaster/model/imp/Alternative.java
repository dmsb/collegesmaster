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
import javax.validation.constraints.NotNull;

import br.com.collegesmaster.enums.Letter;
import br.com.collegesmaster.model.IAlternative;
import br.com.collegesmaster.model.IQuestion;

@Entity
@Table(name = "alternative")
public class Alternative implements IAlternative, Serializable {

	private static final long serialVersionUID = -9207076283580095871L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Basic(fetch = FetchType.LAZY, optional = false)
	@Column(name = "letter", unique = false, length = 1)
	private Letter letter;
	
    @NotNull
    @Column(name = "description", unique= false, length = 150, nullable = false)
    private String description;
    
	@Column(name = "definition", unique = false, nullable = false)
	private Boolean definition;
	
	@ManyToOne(targetEntity = Question.class, optional = false, fetch = FetchType.LAZY)
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
	public boolean equals(final Object obj) {
		if((obj instanceof Alternative) == false) {
			return false;
		}
		final IAlternative other = (IAlternative) obj;		
		return getId() != null && Objects.equals(getId(), other.getId());
	}
	
	@Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
