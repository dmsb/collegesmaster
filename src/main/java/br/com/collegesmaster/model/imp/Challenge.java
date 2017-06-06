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
import javax.validation.constraints.NotNull;

import br.com.collegesmaster.model.IChallenge;
import br.com.collegesmaster.model.IDiscipline;
import br.com.collegesmaster.model.IQuestion;
import br.com.collegesmaster.model.IUser;

@Entity
@Table(name = "challenge")
public class Challenge implements Serializable, IChallenge {

	private static final long serialVersionUID = 6314730845000580522L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@NotNull
	@Column(name= "title", unique = false, nullable = false, length = 30)
	private String title;
	
	@NotNull
	@ManyToOne(targetEntity = User.class, optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "userFK", referencedColumnName = "id")
	private IUser owner;
	
	@ManyToOne(targetEntity = Discipline.class, optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "disciplineFK", referencedColumnName = "id")
	private IDiscipline discipline;
	
	@OneToMany(targetEntity = Question.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, 
		orphanRemoval = true, mappedBy="challenge")
	private List<IQuestion> questions;
	
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
	public String getTitle() {
		return title;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public void setOwner(IUser owner) {
		this.owner = owner;
	}
	
	@Override
	public IDiscipline getDiscipline() {
		return discipline;
	}

	@Override
	public void setDiscipline(IDiscipline discipline) {
		this.discipline = discipline;
	}
	
	@Override
	public List<IQuestion> getQuestions() {
		return questions;
	}

	@Override
	public void setQuestions(List<IQuestion> questions) {
		this.questions = questions;
	}

	@Override
	public boolean equals(final Object obj) {
		if((obj instanceof Challenge) == false) {
			return false;
		}
		final IChallenge other = (IChallenge) obj;		
		return getId() != null && Objects.equals(getId(), other.getId());
	}
	
	@Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
