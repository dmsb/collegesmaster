package br.com.collegesmaster.model.impl;

import static br.com.collegesmaster.util.CryptoUtils.generateHashedPassword;
import static br.com.collegesmaster.util.CryptoUtils.generateSalt;
import static javax.persistence.AccessType.FIELD;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

import java.util.List;
import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.envers.Audited;

import br.com.collegesmaster.annotation.Password;
import br.com.collegesmaster.model.Course;
import br.com.collegesmaster.model.GeneralInfo;
import br.com.collegesmaster.model.Role;
import br.com.collegesmaster.model.User;

@Entity
@Table(name = "user")
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
    @JoinColumn(name = "courseFK", referencedColumnName = "id", updatable = false)
    private Course course;
    
    @Valid
    @ManyToOne(targetEntity = GeneralInfoImpl.class, fetch = LAZY, optional = false, cascade = ALL)
    @JoinTable(name="user_general_info",
	    joinColumns={@JoinColumn(name="userFK", referencedColumnName = "id")},
	    inverseJoinColumns={@JoinColumn(name="generalInfoFK", referencedColumnName = "id")})
	private GeneralInfo generalInfo;
    
    @ManyToMany(targetEntity = RoleImpl.class, fetch = LAZY)
    @JoinTable(name="user_role",
	    joinColumns={@JoinColumn(name="userFK", referencedColumnName = "id")},
	    inverseJoinColumns={@JoinColumn(name="roleFK", referencedColumnName = "id")})
    private List<Role> roles;
    
    @PrePersist
    @PreUpdate
    private void prePersistUser() {
    	encriptyPassword();
    	buildCpf();
    }
    
    private void encriptyPassword() {
    	final String salt = generateSalt();	
		setSalt(salt);
		setPassword(generateHashedPassword(getPassword(), salt));
    }
    
    private void buildCpf() {
		final String crudeCpf = getGeneralInfo().getCpf().replaceAll("[^0-9]", "");
		getGeneralInfo().setCpf(crudeCpf);
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
	public List<Role> getRoles() {
		return roles;
	}

	@Override
	public void setRoles(List<Role> roles) {
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
