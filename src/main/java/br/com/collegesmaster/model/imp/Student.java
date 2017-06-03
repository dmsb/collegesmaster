package br.com.collegesmaster.model.imp;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import br.com.collegesmaster.model.IChallenge;
import br.com.collegesmaster.model.IDiscipline;
import br.com.collegesmaster.model.IStudent;

@Entity
@Table(name = "student")
@DiscriminatorValue("student")
public class Student extends User implements Serializable, IStudent {

	private static final long serialVersionUID = 4255404420897428496L;
	
	@ManyToMany(targetEntity = Challenge.class, fetch = FetchType.LAZY)
	@JoinTable(name="challenges_made",
	    joinColumns={@JoinColumn(name="studentFK", referencedColumnName = "id")},
	    inverseJoinColumns={@JoinColumn(name="challengeFK", referencedColumnName = "id")})
	private List<IChallenge> completedChallenges;
	
	@ManyToMany(targetEntity = Discipline.class, fetch = FetchType.LAZY)
    @JoinTable(name="student_discipline",
             joinColumns={@JoinColumn(name="studentFK", referencedColumnName = "id")},
             inverseJoinColumns={@JoinColumn(name="disciplineFK", referencedColumnName = "id")})
    private List<IDiscipline> disciplines;
	
	@Override
	public List<IChallenge> getCompletedChallenges() {
		return completedChallenges;
	}

	@Override
	public void setCompletedChallenges(List<IChallenge> completedChallenges) {
		this.completedChallenges = completedChallenges;
	}

	@Override
	public List<IDiscipline> getDisciplines() {
		return disciplines;
	}

	@Override
	public void setDisciplines(List<IDiscipline> disciplines) {
		this.disciplines = disciplines;
	}

	@Override
	public boolean equals(final Object obj) {
		if ((obj instanceof Student) == false) {
			return false;
		}
		final Student other = (Student) obj;
		return getId() != null && Objects.equals(getId(), other.getId());
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

}
