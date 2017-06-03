package br.com.collegesmaster.model.imp;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.collegesmaster.annotations.Password;
import br.com.collegesmaster.model.IChallenge;
import br.com.collegesmaster.model.IDiscipline;
import br.com.collegesmaster.model.IGeneralInfo;
import br.com.collegesmaster.model.IProfile;
import br.com.collegesmaster.model.IUser;

@Table(name = "user")
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "profileType")
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

    @ManyToMany(targetEntity = Challenge.class, fetch = FetchType.LAZY)
	@JoinTable(name="user_challenges_created",
	    joinColumns={@JoinColumn(name="userFK", referencedColumnName = "id")},
	    inverseJoinColumns={@JoinColumn(name="challengeFK", referencedColumnName = "id")})
	private List<IChallenge> challengesCreated;
    
    @ManyToMany(targetEntity = Challenge.class, fetch = FetchType.LAZY)
	@JoinTable(name="user_completed_challenges",
	    joinColumns={@JoinColumn(name="userFK", referencedColumnName = "id")},
	    inverseJoinColumns={@JoinColumn(name="challengeFK", referencedColumnName = "id")})
	private List<IChallenge> completedChallenges;
	
	@ManyToMany(targetEntity = Discipline.class, fetch = FetchType.LAZY)
    @JoinTable(name="user_discipline",
             joinColumns={@JoinColumn(name="userFK", referencedColumnName = "id")},
             inverseJoinColumns={@JoinColumn(name="disciplineFK", referencedColumnName = "id")})
    private List<IDiscipline> disciplines;
	
    @OneToOne(targetEntity = GeneralInfo.class, cascade = CascadeType.ALL, 
    		fetch = FetchType.LAZY, optional = false, orphanRemoval = true)
    @JoinTable(name="user_general_info",
	    joinColumns={@JoinColumn(name="userFK", referencedColumnName = "id")},
	    inverseJoinColumns={@JoinColumn(name="generalInfoFK", referencedColumnName = "id")})
	private IGeneralInfo generalInfo;
    
    @OneToOne(targetEntity = Profile.class, optional = false)
    @JoinTable(name="user_profile",
	    joinColumns={@JoinColumn(name="userFK", referencedColumnName = "id")},
	    inverseJoinColumns={@JoinColumn(name="profileFK", referencedColumnName = "id")})
    private IProfile profile;
    
	@Override
	public List<IChallenge> getChallengesCreated() {
		return challengesCreated;
	}

	@Override
	public void setChallengesCreated(List<IChallenge> challengesCreated) {
		this.challengesCreated = challengesCreated;
	}

	@Override
	public List<IChallenge> getCompletedChallenges() {
		return completedChallenges;
	}

	@Override
	public void setCompletedChallenges(List<IChallenge> completedChallenges) {
		this.completedChallenges = completedChallenges;
	}

	@Override
	public List<IDiscipline> getDisciplines() {
		return disciplines;
	}

	@Override
	public void setDisciplines(List<IDiscipline> disciplines) {
		this.disciplines = disciplines;
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
