package br.com.collegesmaster.model.imp;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

import br.com.collegesmaster.model.IStudent;

@Entity
@Table(name = "student")
@DiscriminatorValue("student")
public class Student extends User implements Serializable, IStudent {

	private static final long serialVersionUID = 4255404420897428496L;

	@Column(name = "registration", unique = true, nullable = false, updatable = false)
	@NotBlank
	private String registration;
	
	@ManyToMany(fetch = FetchType.LAZY)
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

	/* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.IStudent#getRegistration()
	 */
	@Override
	public String getRegistration() {
		return registration;
	}

	/* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.IStudent#setRegistration(java.lang.String)
	 */
	@Override
	public void setRegistration(String registration) {
		this.registration = registration;
	}

	/* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.IStudent#getGeneralInfo()
	 */
	@Override
	public GeneralInfo getGeneralInfo() {
		return generalInfo;
	}

	/* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.IStudent#setGeneralInfo(br.com.collegesmaster.model.imp.GeneralInfo)
	 */
	@Override
	public void setGeneralInfo(GeneralInfo generalInfo) {
		this.generalInfo = generalInfo;
	}

	public int hashCode() {
		return Objects.hashCode(getId());
	}
	
	/* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.IStudent#getCompletedChallenges()
	 */
	@Override
	public List<Challenge> getCompletedChallenges() {
		return completedChallenges;
	}

	/* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.IStudent#setCompletedChallenges(java.util.List)
	 */
	@Override
	public void setCompletedChallenges(List<Challenge> completedChallenges) {
		this.completedChallenges = completedChallenges;
	}

	/* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.IStudent#getDisciplines()
	 */
	@Override
	public List<Discipline> getDisciplines() {
		return disciplines;
	}

	/* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.IStudent#setDisciplines(java.util.List)
	 */
	@Override
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
