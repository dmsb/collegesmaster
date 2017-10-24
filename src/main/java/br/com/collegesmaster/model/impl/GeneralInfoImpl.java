package br.com.collegesmaster.model.impl;

import static javax.persistence.AccessType.FIELD;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import br.com.collegesmaster.model.GeneralInfo;

@Entity
@Table(name = "general_info",
	uniqueConstraints = {
			@UniqueConstraint(columnNames = "cpf",  name = "UK_GI_username"),
			@UniqueConstraint(columnNames = "email",  name = "UK_GI_email")
		})
@Access(FIELD)
@Audited
@JsonIdentityInfo(property = "id",
	generator = ObjectIdGenerators.PropertyGenerator.class)
public class GeneralInfoImpl extends ModelImpl implements GeneralInfo {
	
	private static final long serialVersionUID = 1137972673979789034L;
	
	@NotNull
	@CPF
	@Column(name = "cpf", nullable = false, length = 11)	
    private String cpf;
		
	@NotNull
	@Email
	@Column(name = "email", nullable = false, length = 50)
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
	public boolean equals(final Object objectToBeComparated) {

		if(objectToBeComparated == this) {
			return true;
		}
		
		if(!(objectToBeComparated instanceof GeneralInfoImpl)) {
			return false;
		}
		
		final GeneralInfoImpl objectComparatedInstance = (GeneralInfoImpl) objectToBeComparated;
		
		return id.equals(objectComparatedInstance.id) && 
				Objects.equals(cpf, objectComparatedInstance.cpf) &&
				Objects.equals(email, objectComparatedInstance.email);
	}
	
	@Override
    public int hashCode() {
        return Objects.hash(id, cpf, email);
    }
}
