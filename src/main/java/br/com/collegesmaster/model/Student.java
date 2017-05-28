package br.com.collegesmaster.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "student")
public class Student extends User implements Serializable {

	private static final long serialVersionUID = 4255404420897428496L;

	@Column(name = "registration", unique = true, nullable = false, updatable = false)
	@NotBlank
	private String registration;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name="challenges_made",
	    joinColumns={@JoinColumn(name="studentFK", referencedColumnName = "id")},
	    inverseJoinColumns={@JoinColumn(name="challengeFK", referencedColumnName = "id")})
	private List<Challenge> completedChallenges;
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="student_discipline",
             joinColumns={@JoinColumn(name="studentFK", referencedColumnName = "id")},
             inverseJoinColumns={@JoinColumn(name="disciplineFK", referencedColumnName = "id")})
    private List<Discipline> disciplines;
	
	@Embedded
	@Valid
	private GeneralInfo generalInfo;

	public String getRegistration() {
		return registration;
	}

	public void setRegistration(String registration) {
		this.registration = registration;
	}

	public GeneralInfo getGeneralInfo() {
		return generalInfo;
	}

	public void setGeneralInfo(GeneralInfo generalInfo) {
		this.generalInfo = generalInfo;
	}

	public int hashCode() {
		return Objects.hashCode(getId());
	}
	
	public List<Challenge> getCompletedChallenges() {
		return completedChallenges;
	}

	public void setCompletedChallenges(List<Challenge> completedChallenges) {
		this.completedChallenges = completedChallenges;
	}

	public List<Discipline> getDisciplines() {
		return disciplines;
	}

	public void setDisciplines(List<Discipline> disciplines) {
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

}
