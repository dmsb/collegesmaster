package br.com.collegesmaster.model.imp;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.collegesmaster.annotations.Password;
import br.com.collegesmaster.model.IGeneralInfo;
import br.com.collegesmaster.model.IProfile;
import br.com.collegesmaster.model.IUser;
import br.com.collegesmaster.util.CryptoUtils;

@Entity
@Table(name = "user")
public class User implements Serializable, IUser {

    private static final long serialVersionUID = -7809703915845045860L;
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
    @NotNull
    @Size(min = 2, max = 25)
    @Column(name = "username", unique= true, length = 25)
    private String username;
    
    @NotNull
	@Column(name = "password", unique = false, nullable = false, length = 88)
    @Size(min = 6)
    @Password
    private String password;

//    private Set<IAlternative> responsedAlternatves;
//    
//     {
//    	 Map<IQuestion, List<IAlternative>> map = responsedAlternatves.stream()
//    			 .collect(groupingBy(IAlternative::getQuestion));
//
//    	 Map<IChallenge, List<Entry<IQuestion, List<IAlternative>>>> responsedChallenges = map.entrySet()
//    			 .stream().collect(groupingBy(entry -> entry.getKey().getChallenge()));
//    }
     
    @NotNull
	@Column(name = "salt", unique = false, nullable = false, length = 88)
    private String salt;
	
    @Valid
    @OneToOne(targetEntity = GeneralInfo.class, fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "generalInfoFK", referencedColumnName = "id")
	private IGeneralInfo generalInfo;
    
    @NotNull
    @ManyToMany(targetEntity = Profile.class, fetch = FetchType.LAZY)
    @JoinTable(name="user_profile",
	    joinColumns={@JoinColumn(name="userFK", referencedColumnName = "id")},
	    inverseJoinColumns={@JoinColumn(name="profileFK", referencedColumnName = "id")})
    private List<IProfile> profiles;
    
    @PrePersist
    private void buildPassword() {
    	final String salt = CryptoUtils.generateSalt();	
		setSalt(salt);
		setPassword(CryptoUtils.getHashedPassword(getPassword(), salt));
				
		final String crudeCpf = getGeneralInfo().getCpf().replace(".", "").replace("-", "");
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
	public List<IProfile> getProfiles() {
		return profiles;
	}

	@Override
	public void setProfiles(List<IProfile> profiles) {
		this.profiles = profiles;
	}

	@Override
	public boolean equals(final Object objectToBeComparated) {
		if(objectToBeComparated == null) {
			return false;
		}
		
		if((objectToBeComparated.getClass().isAssignableFrom(Challenge.class)) == false) {
			return false;
		}
		
		final IUser objectComparatedInstance = (IUser) objectToBeComparated;
		
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
