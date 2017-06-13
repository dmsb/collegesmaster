package br.com.collegesmaster.model.imp;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.br.CPF;

import br.com.collegesmaster.model.IGeneralInfo;
import br.com.collegesmaster.model.IUser;

@Entity
@Table(name = "general_info")
public class GeneralInfo implements Serializable, IGeneralInfo {
	
	private static final long serialVersionUID = 1137972673979789034L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "cpf", unique = true, updatable = true, nullable = false, length = 14)
	@Size(min = 14, max = 14)
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
    @Size(max = 25)
    private String firstName;

	@NotNull
    @Column(name = "lastName", unique = false, nullable = false, updatable = true)
    private String lastName;

    @Column(name = "birthdate", updatable = true, nullable = false)
    private LocalDate birthdate;
    
    @OneToOne(targetEntity = User.class, fetch = FetchType.LAZY, mappedBy = "generalInfo")
    private IUser user;
    
    @Embedded
    @Valid
    private Localization localization;
    
	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public IUser getUser() {
		return user;
	}

	@Override
	public void setUser(IUser user) {
		this.user = user;
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
	public Localization getLocalization() {
		return localization;
	}

	@Override
	public void setLocalization(Localization localization) {
		this.localization = localization;
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
