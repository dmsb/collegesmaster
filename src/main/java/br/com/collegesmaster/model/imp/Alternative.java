package br.com.collegesmaster.model.imp;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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
    @Column(name = "description", unique= false, length = 1, nullable = false)
    private String description;
	
    @NotNull
    @ManyToOne(targetEntity = Question.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
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
		if((obj instanceof IAlternative) == false) {
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
