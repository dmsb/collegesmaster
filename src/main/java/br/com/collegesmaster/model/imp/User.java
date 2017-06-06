package br.com.collegesmaster.model.imp;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.apache.commons.collections.CollectionUtils;

import br.com.collegesmaster.annotations.Password;
import br.com.collegesmaster.model.IChallenge;
import br.com.collegesmaster.model.ICompletedChallenge;
import br.com.collegesmaster.model.IDiscipline;
import br.com.collegesmaster.model.IPerson;
import br.com.collegesmaster.model.IProfile;
import br.com.collegesmaster.model.IUser;

@Entity
@Table(name = "user")
public class User implements Serializable, IUser {

    private static final long serialVersionUID = -7809703915845045860L;
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
    @NotNull
    @Column(name = "username", unique= true, length = 25)
    private String username;

    @NotNull
	@Column(name = "password", unique = false, nullable = false, length = 88)
    @Password    
    private String password;
    
    @NotNull
	@Column(name = "salt", unique = false, nullable = false, length = 88)
    private String salt;

    @OneToMany(targetEntity = Challenge.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "owner")
	private List<IChallenge> challengesCreated;
    
    @OneToMany(targetEntity = CompletedChallenge.class, fetch = FetchType.LAZY, mappedBy = "owner")
	private List<ICompletedChallenge> completedChallenges;
	
    @ManyToOne(targetEntity = Person.class, fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "personFK", referencedColumnName = "id")
	private IPerson person;
    
    @ManyToOne(targetEntity = Profile.class, optional = false)
    @JoinTable(name="user_profile",
	    joinColumns={@JoinColumn(name="userFK", referencedColumnName = "id")},
	    inverseJoinColumns={@JoinColumn(name="profileFK", referencedColumnName = "id")})
    private IProfile profile;
    
    @Transient
    private Map<IDiscipline, Integer> notePerDiscipline;
    
    @PostLoad
    private void buildNotePerDiscipline() {
    	if (CollectionUtils.isEmpty(completedChallenges)) {
			notePerDiscipline = null;
		} else {			
			
			notePerDiscipline = new HashMap<>();
			completedChallenges.forEach(challengesCreated -> {
				
				final Integer note = challengesCreated.getNote();
				final IDiscipline discipline = challengesCreated.getChallenge().getDiscipline();
				
				notePerDiscipline.put(discipline, note);
				
			});			
		}
    }
    
	@Override
	public IPerson getPerson() {
		return person;
	}

	@Override
	public void setPerson(IPerson person) {
		this.person = person;
	}

	@Override
	public Map<IDiscipline, Integer> getNotePerDiscipline() {
		return notePerDiscipline;
	}

	@Override
	public void setNotePerDiscipline(Map<IDiscipline, Integer> notePerDiscipline) {
		this.notePerDiscipline = notePerDiscipline;
	}

	@Override
	public List<IChallenge> getChallengesCreated() {
		return challengesCreated;
	}

	@Override
	public void setChallengesCreated(List<IChallenge> challengesCreated) {
		this.challengesCreated = challengesCreated;
	}

	@Override
	public List<ICompletedChallenge> getCompletedChallenges() {
		return completedChallenges;
	}

	@Override
	public void setCompletedChallenges(List<ICompletedChallenge> completedChallenges) {
		this.completedChallenges = completedChallenges;
	}

	@Override
	public IPerson getGeneralInfo() {
		return person;
	}

	@Override
	public void setGeneralInfo(IPerson person) {
		this.person = person;
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
	public IProfile getProfile() {
		return profile;
	}

	@Override
	public void setProfile(IProfile profile) {
		this.profile = profile;
	}

	@Override
	public boolean equals(final Object obj) {
		if ((obj instanceof User) == false) {
			return false;
		}
		final IUser other = (IUser) obj;
		return getId() != null && Objects.equals(getId(), other.getId());
	}
	
	@Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
