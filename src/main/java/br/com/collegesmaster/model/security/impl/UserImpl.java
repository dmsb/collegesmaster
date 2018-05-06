package br.com.collegesmaster.model.security.impl;

import static javax.persistence.AccessType.FIELD;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.Access;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.collegesmaster.model.institute.Course;
import br.com.collegesmaster.model.institute.impl.CourseImpl;
import br.com.collegesmaster.model.model.impl.ModelImpl;
import br.com.collegesmaster.model.security.User;
import br.com.collegesmaster.model.security.business.impl.Password;
import br.com.collegesmaster.model.security.business.impl.PasswordEncoderWithSalt;

@Entity
@Table(name = "user",
	uniqueConstraints = {
			@UniqueConstraint(columnNames = "username",  name = "UK_USER_username"),
			@UniqueConstraint(columnNames = "cpf",  name = "UK_GI_username"),
			@UniqueConstraint(columnNames = "email",  name = "UK_GI_email")})
@Access(FIELD)
@Audited
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserImpl extends ModelImpl implements User {

    private static final long serialVersionUID = -7809703915845045860L;
	
	@NotNull
    @Size(min = 2, max = 25)
    @Column(name = "username", length = 25, nullable = false)
    private String username;
    
	@NotNull
    @Password
    @Basic(fetch = LAZY)
	@Column(name = "password", nullable = false, length = 88)
    private String password;

	@NotNull
    @Basic(fetch = LAZY)
	@Column(name = "salt", nullable = false, length = 44)
    private String salt;
	
	@NotNull
	@CPF
	@Column(name = "cpf", nullable = false, length = 11)	
    private String cpf;
    
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
    
    @NotNull
    @ManyToOne(targetEntity = CourseImpl.class, fetch = EAGER, optional = false)
    @JoinColumn(name = "courseFK", referencedColumnName = "id", updatable = false,
    	foreignKey = @ForeignKey(name = "USER_courseFK"))
    private Course course;
    
    @NotEmpty
    @ManyToMany(fetch = EAGER)
    @JoinTable(name="user_has_roles",
	    joinColumns = {@JoinColumn(name="userFK", referencedColumnName = "id")},
	    foreignKey = @ForeignKey(name = "UR_userFK"),
	    inverseJoinColumns = {@JoinColumn(name="roleFK", referencedColumnName = "id")},
	    inverseForeignKey = @ForeignKey(name = "UR_roleFK"))
    private Collection<RoleImpl> roles;
    
    @PrePersist
    @Override
	public void encriptyPassword() {
    	final PasswordEncoderWithSalt encoder = new PasswordEncoderWithSalt();
    	final String salt = encoder.generateSalt();	
		setSalt(salt);
		setPassword(encoder.generateHashedPassword(getPassword(), salt));
		parseCpfToCrude();
    }
    
    @PreUpdate
    @Override
	public void parseCpfToCrude() {
		final String crudeCpf = getCpf().replaceAll("[^0-9]", "");
		setCpf(crudeCpf);
    }
	
    @Override
	public Boolean isUserInRole(final String roleName) {
    	final List<String> userRoleNames = getRoleNames();
    	return userRoleNames.stream().anyMatch(roleName::equals);
    }
    
    @Override
	public Boolean isUserInAnyRoles(final List<String> roleNames) {
    	final List<String> userRoleNames = getRoleNames();
    	return roleNames.stream().anyMatch(userRoleNames::contains);
    }

	@Override
	public List<String> getRoleNames() {
		return getRoles().stream().map(role -> {return role.getName();})
    		.collect(Collectors.toList());
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
	public String getSalt() {		
		return salt;
	}

	@Override
	public void setSalt(String salt) {
		this.salt = salt;
	}
    
    @Override
	public String getUsername() {
  		return username;
  	}
    
  	@Override
	public void setUsername(String username) {
  		this.username = username;
  	}
  	
    @Override
	public String getPassword() {
        return password;
    }

    @Override
	public void setPassword(String password) {
        this.password = password;
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
	public Collection<RoleImpl> getRoles() {
		return roles;
	}

	@Override
	public void setRoles(Collection<RoleImpl> roles) {
		this.roles = roles;
	}
	
	@Override
	public boolean equals(final Object objectToBeComparated) {

		if(this == objectToBeComparated) {
			return true;
		}
		
		if(!(objectToBeComparated instanceof UserImpl)) {
			return false;
		}
		
		final UserImpl objectComparatedInstance = (UserImpl) objectToBeComparated;
		
		return Objects.equals(id, objectComparatedInstance.id) &&
				Objects.equals(this.username, objectComparatedInstance.username);
	}
	
	@Override
    public int hashCode() {
        return Objects.hash(id, username);
    }
}
