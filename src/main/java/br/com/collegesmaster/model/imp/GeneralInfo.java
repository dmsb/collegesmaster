package br.com.collegesmaster.model.imp;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.br.CPF;

import br.com.collegesmaster.model.ICourse;
import br.com.collegesmaster.model.IGeneralInfo;

@Entity
@Table(name = "general_info")
public class GeneralInfo implements Serializable, IGeneralInfo {
	
	private static final long serialVersionUID = 1137972673979789034L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "cpf", unique = true,  nullable = false, length = 11)	
	@NotNull
    @CPF
    private String cpf;
	
	@Column(name = "email", unique = true, nullable = false, updatable = true)
	@NotNull
    @Email
    private String email;

	@Column(name = "firstName", unique = false, nullable = false, updatable = true,
			length = 25)
	@NotNull
    @Size(max = 40)
    private String firstName;

	@NotNull
	@Size(max = 80)
    @Column(name = "lastName", unique = false, nullable = false, updatable = true)
    private String lastName;
		
    @Column(name = "birthdate", updatable = true, nullable = false)
    private LocalDate birthdate;
    
    @ManyToOne(targetEntity = Course.class, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "courseId", referencedColumnName = "id")
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
		if(objectToBeComparated == null) {
			return false;
		}
		
		if((objectToBeComparated.getClass().isAssignableFrom(Challenge.class)) == false) {
			return false;
		}
		
		final IGeneralInfo objectComparatedInstance = (IGeneralInfo) objectToBeComparated;
		
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
