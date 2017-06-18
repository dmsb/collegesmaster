package br.com.collegesmaster.model.imp;

import java.io.Serializable;
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
	private Set<IQuestion> questions;
	
	public Challenge() {
		
	}
	
	public Challenge(Integer id) {
		this.id = id;
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
	public Set<IQuestion> getQuestions() {
		return questions;
	}

	@Override
	public void setQuestions(Set<IQuestion> questions) {
		this.questions = questions;
	}

	@Override
	public boolean equals(final Object objectToBeComparated) {
		if(objectToBeComparated == null) {
			return false;
		}
		
		if((objectToBeComparated.getClass().isAssignableFrom(Challenge.class)) == false) {
			return false;
		}
		
		final IChallenge objectComparatedInstance = (IChallenge) objectToBeComparated;
		
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
