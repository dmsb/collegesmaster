package br.com.collegesmaster.model.imp;

import static javax.persistence.AccessType.FIELD;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.List;
import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.validator.constraints.NotBlank;

import br.com.collegesmaster.model.IChallenge;
import br.com.collegesmaster.model.IDiscipline;
import br.com.collegesmaster.model.IUser;

@Entity
@Table(name = "challenge")
@Access(FIELD)
@Audited
public class Challenge implements IChallenge {

	private static final long serialVersionUID = 6314730845000580522L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@NotBlank
	@Column(name= "title", unique = false, nullable = false, length = 30)
	private String title;
	
	@NotAudited
	@NotNull
	@ManyToOne(targetEntity = User.class, optional = false, fetch = LAZY)
	@JoinColumn(name = "userFK", referencedColumnName = "id", updatable = false)
	private IUser owner;
	
	@NotNull
	@ManyToOne(targetEntity = Discipline.class, optional = false, fetch = LAZY)
	@JoinColumn(name = "disciplineFK", referencedColumnName = "id")
	private IDiscipline discipline;
	
	@OneToMany(targetEntity = Question.class, cascade = ALL, fetch = LAZY, 
		orphanRemoval = true, mappedBy="challenge")
	private List<Question> questions;
	
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
	public List<Question> getQuestions() {
		return questions;
	}

	@Override
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	@Override
	public boolean equals(final Object objectToBeComparated) {
		
		if(objectToBeComparated == this) {
			return true;
		}
		
		if(!(objectToBeComparated instanceof Challenge)) {
			return false;
		}
		
		final Challenge objectComparatedInstance = (Challenge) objectToBeComparated;
		
		return id == objectComparatedInstance.id && 
			    Objects.equals(title, objectComparatedInstance.title);
	}
	
	@Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}
