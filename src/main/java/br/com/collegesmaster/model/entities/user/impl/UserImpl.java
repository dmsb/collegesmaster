package br.com.collegesmaster.model.entities.user.impl;

import static javax.persistence.AccessType.FIELD;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

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
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.envers.Audited;

import br.com.collegesmaster.model.entities.constraints.password.Password;
import br.com.collegesmaster.model.entities.course.Course;
import br.com.collegesmaster.model.entities.course.impl.CourseImpl;
import br.com.collegesmaster.model.entities.generalinfo.GeneralInfo;
import br.com.collegesmaster.model.entities.generalinfo.impl.GeneralInfoImpl;
import br.com.collegesmaster.model.entities.model.impl.ModelImpl;
import br.com.collegesmaster.model.entities.role.impl.RoleImpl;
import br.com.collegesmaster.model.entities.user.User;
import br.com.collegesmaster.utils.PasswordEncoderWithSalt;

@Entity
@Table(name = "user",
	uniqueConstraints = @UniqueConstraint(columnNames = "username",  name = "UK_USER_username"))
@Access(FIELD)
@Audited
public class UserImpl extends ModelImpl implements User {

    private static final long serialVersionUID = -7809703915845045860L;
	
    @NotNull
    @Size(min = 2, max = 25)
    @Column(name = "username", unique= true, length = 25, nullable = false)
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
    @ManyToOne(targetEntity = CourseImpl.class, fetch = EAGER, optional = false)
    @JoinColumn(name = "courseFK", referencedColumnName = "id", updatable = false,
    	foreignKey = @ForeignKey(name = "USER_courseFK"))
    private Course course;
    
    @Valid
    @ManyToOne(targetEntity = GeneralInfoImpl.class, fetch = LAZY, optional = false, cascade = ALL)
    @JoinColumn(name = "generalInfoFK", referencedColumnName = "id", updatable = false,
		foreignKey = @ForeignKey(name = "USER_generalInfoFK"))
	private GeneralInfo generalInfo;
    
    @ManyToMany(fetch = EAGER)
    @JoinTable(name="user_role",
	    joinColumns = {@JoinColumn(name="userFK", referencedColumnName = "id")},
	    foreignKey = @ForeignKey(name = "UR_userFK"),
	    inverseJoinColumns = {@JoinColumn(name="roleFK", referencedColumnName = "id")},
	    inverseForeignKey = @ForeignKey(name = "UR_roleFK"))
    private List<RoleImpl> roles;
    
    @PrePersist
    @PreUpdate
    private void prePersistUser() {
    	encriptyPassword();
    	parseCpfToCrude();
    }
    
    @Override
	public void encriptyPassword() {
    	final PasswordEncoderWithSalt encoder = new PasswordEncoderWithSalt();
    	final String salt = encoder.generateSalt();	
		setSalt(salt);
		setPassword(encoder.generateHashedPassword(getPassword(), salt));
    }
    
    @Override
	public void parseCpfToCrude() {
		final String crudeCpf = getGeneralInfo().getCpf().replaceAll("[^0-9]", "");
		getGeneralInfo().setCpf(crudeCpf);
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
	public GeneralInfo getGeneralInfo() {
		return generalInfo;
	}

	@Override
	public void setGeneralInfo(GeneralInfo generalInfo) {
		this.generalInfo = generalInfo;
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
	public List<RoleImpl> getRoles() {
		return roles;
	}

	@Override
	public void setRoles(List<RoleImpl> roles) {
		this.roles = roles;
	}
	
	@Override
	public boolean equals(final Object objectToBeComparated) {

		if(objectToBeComparated == this) {
			return true;
		}
		
		if(!(objectToBeComparated instanceof UserImpl)) {
			return false;
		}
		
		final UserImpl objectComparatedInstance = (UserImpl) objectToBeComparated;
		
		return id == objectComparatedInstance.id && 
				Objects.equals(username, objectComparatedInstance.username);
	}
	
	@Override
    public int hashCode() {
        return Objects.hash(id, username);
    }
}
