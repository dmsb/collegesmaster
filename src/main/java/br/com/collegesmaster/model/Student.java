package br.com.collegesmaster.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "Student")
public class Student extends User implements Serializable {

	private static final long serialVersionUID = 4255404420897428496L;

	@Column(name = "registration", unique = true, nullable = false, updatable = false)
	@NotBlank
	private String registration;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)	
	private Course course;
	
	@Column(name = "score")
	private Integer score;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<UserChallenge> userChallenges;
	
	
	@Embedded
	@Valid
	private GeneralInfo generalInfo;

	public String getRegistration() {
		return registration;
	}

	public void setRegistration(String registration) {
		this.registration = registration;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
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

	@Override
	public boolean equals(final Object obj) {
		if ((obj instanceof Student) == false) {
			return false;
		}
		final Student other = (Student) obj;
		return getId() != null && Objects.equals(getId(), other.getId());
	}

}
