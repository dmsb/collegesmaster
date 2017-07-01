package br.com.collegesmaster.model.impl;

import static javax.persistence.AccessType.FIELD;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.List;
import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import br.com.collegesmaster.annotations.Password;
import br.com.collegesmaster.model.IGeneralInfo;
import br.com.collegesmaster.model.IUser;
import br.com.collegesmaster.util.CryptoUtils;

@Entity
@Table(name = "user")
@Access(FIELD)
@Audited
public class User implements IUser {

    private static final long serialVersionUID = -7809703915845045860L;
    
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	private Integer id;
	
    @NotNull
    @Size(min = 2, max = 25)
    @Column(name = "username", unique= true, length = 25, nullable = false)
    private String username;
    
    @NotNull
    @Size(min = 6)
    @Password
	@Column(name = "password", nullable = false, length = 88)
    private String password;
     
    @NotNull
	@Column(name = "salt", nullable = false, length = 88)
    private String salt;
	
    @Valid
    @OneToOne(targetEntity = GeneralInfo.class, fetch = LAZY, optional = false, cascade = ALL)
    @JoinColumn(name = "generalInfoFK", referencedColumnName = "id")
	private IGeneralInfo generalInfo;
    
    @NotAudited
    @NotNull
    @ManyToMany(targetEntity = Profile.class, fetch = LAZY)
    @JoinTable(name="user_profile",
	    joinColumns={@JoinColumn(name="userFK", referencedColumnName = "id")},
	    inverseJoinColumns={@JoinColumn(name="profileFK", referencedColumnName = "id")})
    private List<Profile> profiles;
    
    @Version
	private Long version;
    
    @PrePersist
    @PreUpdate
    private void prePersistUser() {
    	encriptyPassword();
    	buildCpf();
    }
    
    private void encriptyPassword() {
    	final String salt = CryptoUtils.generateSalt();	
		setSalt(salt);
		setPassword(CryptoUtils.getHashedPassword(getPassword(), salt));
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
	public Integer getId() {
		return id;
	}
    
    @Override
	public void setId(Integer id) {
		this.id = id;
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
	public List<Profile> getProfiles() {
		return profiles;
	}

	@Override
	public void setProfiles(List<Profile> profiles) {
		this.profiles = profiles;
	}
	
	@Override
	public Long getVersion() {
		return version;
	}
	
	@Override
	public void setVersion(Long version) {
		this.version = version;
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
