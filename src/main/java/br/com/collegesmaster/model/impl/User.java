package br.com.collegesmaster.model.impl;

import static br.com.collegesmaster.util.CryptoUtils.generateSalt;
import static br.com.collegesmaster.util.CryptoUtils.generateHashedPassword;
import static javax.persistence.AccessType.FIELD;
import static javax.persistence.CascadeType.ALL;
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

import br.com.collegesmaster.annotations.Password;
import br.com.collegesmaster.model.IGeneralInfo;
import br.com.collegesmaster.model.IRole;
import br.com.collegesmaster.model.IUser;

@Entity
@Table(name = "user")
@Access(FIELD)
@Audited
public class User extends Model implements IUser {

    private static final long serialVersionUID = -7809703915845045860L;
	
    @NotNull
    @Size(min = 2, max = 25)
    @Column(name = "username", unique= true, length = 25, nullable = false)
    private String username;
    
    @NotNull
    @Size(min = 6)
    @Password
    @Basic(fetch = LAZY)
	@Column(name = "password", nullable = false, length = 88)
    private String password;
     
    @NotNull
    @Basic(fetch = LAZY)
	@Column(name = "salt", nullable = false, length = 44)
    private String salt;
	
    @Valid
    @ManyToOne(targetEntity = GeneralInfo.class, fetch = LAZY, optional = false, cascade = ALL)
    @JoinTable(name="user_general_info",
	    joinColumns={@JoinColumn(name="userFK", referencedColumnName = "id")},
	    inverseJoinColumns={@JoinColumn(name="generalInfoFK", referencedColumnName = "id")})
	private IGeneralInfo generalInfo;
    
    @ManyToMany(targetEntity = Role.class, fetch = LAZY)
    @JoinTable(name="user_role",
	    joinColumns={@JoinColumn(name="userFK", referencedColumnName = "id")},
	    inverseJoinColumns={@JoinColumn(name="roleFK", referencedColumnName = "id")})
    private List<IRole> roles;
    
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
	public IGeneralInfo getGeneralInfo() {
		return generalInfo;
	}

	@Override
	public void setGeneralInfo(IGeneralInfo generalInfo) {
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
	public List<IRole> getRoles() {
		return roles;
	}

	@Override
	public void setRoles(List<IRole> roles) {
		this.roles = roles;
	}
	
	@Override
	public boolean equals(final Object objectToBeComparated) {

		if(objectToBeComparated == this) {
			return true;
		}
		
		if(!(objectToBeComparated instanceof User)) {
			return false;
		}
		
		final User objectComparatedInstance = (User) objectToBeComparated;
		
		return id == objectComparatedInstance.id && 
				Objects.equals(username, objectComparatedInstance.username);
	}
	
	@Override
    public int hashCode() {
        return Objects.hash(id, username);
    }
}
