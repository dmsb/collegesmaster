package br.com.collegesmaster.model.imp;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

import br.com.collegesmaster.model.IUser;

@Entity
@Table(name = "general_info")
public class GeneralInfo implements Serializable, IGeneralInfo {
	
	private static final long serialVersionUID = 1137972673979789034L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "cpf", unique = true, updatable = false, nullable = false)
    @NotBlank
    @CPF
    private String cpf;
	
	@Column(name = "email", unique = true, nullable = false, updatable = true)
    @NotBlank
    @Email
    private String email;

	@Column(name = "firstName", unique = false, nullable = false, updatable = true)
    @NotBlank
    @Size(max = 20)
    private String firstName;

    @Column(name = "lastName", unique = false, nullable = false, updatable = true)
    @NotBlank
    private String lastName;

    @Column(name = "birthdate", updatable = true, nullable = false)
    @Temporal(TemporalType.DATE)
    private Date birthdate;
    
    @OneToOne(targetEntity = User.class, mappedBy = "generalInfo")  
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
	public Date getBirthdate() {
		return birthdate;
	}

	@Override
	public void setBirthdate(Date birthdate) {
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
}
