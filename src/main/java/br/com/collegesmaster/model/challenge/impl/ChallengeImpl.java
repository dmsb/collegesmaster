package br.com.collegesmaster.model.challenge.impl;

import static javax.persistence.AccessType.FIELD;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

import java.util.List;
import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.validator.constraints.NotBlank;

import br.com.collegesmaster.model.challenge.Challenge;
import br.com.collegesmaster.model.institute.Discipline;
import br.com.collegesmaster.model.institute.impl.DisciplineImpl;
import br.com.collegesmaster.model.model.impl.ModelImpl;
import br.com.collegesmaster.model.security.User;
import br.com.collegesmaster.model.security.impl.UserImpl;

@Entity
@Table(name = "challenge")
@Access(FIELD)
@Audited
public class ChallengeImpl extends ModelImpl implements Challenge {

	private static final long serialVersionUID = 6314730845000580522L;
	
	@NotBlank
	@Column(name= "title", nullable = false, length = 30)
	private String title;
	
	@NotNull
	@ManyToOne(targetEntity = UserImpl.class, optional = false, fetch = LAZY)
	@JoinColumn(name = "userFK", referencedColumnName = "id", updatable = false,
		foreignKey = @ForeignKey(name = "CHALLENGE_userFK"))
	private User owner;
	
	@NotNull
	@ManyToOne(targetEntity = DisciplineImpl.class, optional = false, fetch = EAGER)
	@JoinColumn(name = "disciplineFK", referencedColumnName = "id", 
		foreignKey = @ForeignKey(name = "CHALLENGE_disciplineFK"))
	private Discipline discipline;
	
	@NotAudited
	@OneToMany(targetEntity = QuestionImpl.class, cascade = ALL, fetch = LAZY, 
		orphanRemoval = true, mappedBy="challenge")
	private List<QuestionImpl> questions;
	
	@Override
	public User getOwner() {
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
	public void setOwner(User owner) {
		this.owner = owner;
	}
	
	@Override
	public Discipline getDiscipline() {
		return discipline;
	}

	@Override
	public void setDiscipline(Discipline discipline) {
		this.discipline = discipline;
	}
	
	@Override
	public List<QuestionImpl> getQuestions() {
		return questions;
	}

	@Override
	public void setQuestions(List<QuestionImpl> questions) {
		this.questions = questions;
	}

	@Override
	public boolean equals(final Object objectToBeComparated) {
		
		if(this == objectToBeComparated) {
			return true;
		}
		
		if(!(objectToBeComparated instanceof ChallengeImpl)) {
			return false;
		}
		
		final ChallengeImpl objectComparatedInstance = (ChallengeImpl) objectToBeComparated;
		
		return Objects.equals(this.id, objectComparatedInstance.id) && 
			    Objects.equals(title, objectComparatedInstance.title);
	}
	
	@Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}
