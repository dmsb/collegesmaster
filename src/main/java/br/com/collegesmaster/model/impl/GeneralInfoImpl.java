package br.com.collegesmaster.model.impl;

import static javax.persistence.AccessType.FIELD;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.br.CPF;

import br.com.collegesmaster.model.Course;
import br.com.collegesmaster.model.GeneralInfo;

@Entity
@Table(name = "general_info")
@Access(FIELD)
@Audited
public class GeneralInfoImpl extends ModelImpl implements GeneralInfo {
	
	private static final long serialVersionUID = 1137972673979789034L;
	
	@NotNull
	@CPF
	@Column(name = "cpf", unique = true,  nullable = false, length = 11)	
    private String cpf;
		
	@NotNull
	@Email
	@Column(name = "email", unique = true, nullable = false, length = 50)
    private String email;

	@NotNull
	@Size(max = 25)
	@Column(name = "firstName", nullable = false, length = 25)
    private String firstName;

	@NotNull
	@Size(max = 80)
    @Column(name = "lastName", nullable = false, length = 80)
    private String lastName;

    @Column(name = "birthdate")
    private LocalDate birthdate;
    
    @NotNull
    @ManyToOne(targetEntity = CourseImpl.class, fetch = EAGER, optional = false)
    @JoinColumn(name = "courseId", referencedColumnName = "id", updatable = false)
    private Course course;
    
    @OneToMany(fetch = LAZY, mappedBy = "generalInfo", orphanRemoval = true)
    private List<UserImpl> users;
	
	@Override
	public String getCpf() {
        return cpf;
    }

	@Override
	public void setCpf(String cpf) {
        this.cpf = cpf;
    }

	@Override
	public String getEmail() {
		return email;
	}

	@Override
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String getFirstName() {
		return firstName;
	}

	@Override
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Override
	public String getLastName() {
		return lastName;
	}

	@Override
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Override
	public LocalDate getBirthdate() {
		return birthdate;
	}

	@Override
	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}
	
	@Override
	public Course getCourse() {
		return course;
	}

	@Override
	public void setCourse(Course course) {
		this.course = course;
	}
	
	
	@Override
	public List<UserImpl> getUsers() {
		return users;
	}

	@Override
	public void setUsers(List<UserImpl> users) {
		this.users = users;
	}
	
	@Override
	public boolean equals(final Object objectToBeComparated) {

		if(objectToBeComparated == this) {
			return true;
		}
		
		if(!(objectToBeComparated instanceof GeneralInfoImpl)) {
			return false;
		}
		
		final GeneralInfoImpl objectComparatedInstance = (GeneralInfoImpl) objectToBeComparated;
		
		return id == objectComparatedInstance.id && 
				Objects.equals(cpf, objectComparatedInstance.cpf) &&
				Objects.equals(email, objectComparatedInstance.email);
	}
	
	@Override
    public int hashCode() {
        return Objects.hash(id, cpf, email);
    }
}
