package br.com.collegesmaster.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

@Embeddable
public class GeneralInfo implements Serializable {
	
	private static final long serialVersionUID = 1137972673979789034L;

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
    
    @Embedded
    @Valid
    private Localization localization;
    
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public Localization getLocalization() {
		return localization;
	}

	public void setLocalization(Localization localization) {
		this.localization = localization;
	}
}
