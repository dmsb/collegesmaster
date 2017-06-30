package br.com.collegesmaster.model.impl;

import static javax.persistence.AccessType.FIELD;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.br.CPF;

import br.com.collegesmaster.model.ICourse;
import br.com.collegesmaster.model.IGeneralInfo;

@Entity
@Table(name = "general_info")
@Access(FIELD)
@Audited
public class GeneralInfo implements IGeneralInfo {
	
	private static final long serialVersionUID = 1137972673979789034L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	private Integer id;
	
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
    @ManyToOne(targetEntity = Course.class, fetch = EAGER, optional = false)
    @JoinColumn(name = "courseId", referencedColumnName = "id", updatable = false)
    private ICourse course;
    
	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

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
	public ICourse getCourse() {
		return course;
	}

	@Override
	public void setCourse(ICourse course) {
		this.course = course;
	}

	@Override
	public boolean equals(final Object objectToBeComparated) {

		if(objectToBeComparated == this) {
			return true;
		}
		
		if(!(objectToBeComparated instanceof GeneralInfo)) {
			return false;
		}
		
		final GeneralInfo objectComparatedInstance = (GeneralInfo) objectToBeComparated;
		
		return id == objectComparatedInstance.id && 
				Objects.equals(cpf, objectComparatedInstance.cpf) &&
				Objects.equals(email, objectComparatedInstance.email);
	}
	
	@Override
    public int hashCode() {
        return Objects.hash(id, cpf, email);
    }
}
