package br.com.collegesmaster.model.imp;

import java.io.Serializable;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.collegesmaster.annotations.Password;
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
	
    @OneToOne(targetEntity = GeneralInfo.class, cascade = CascadeType.ALL, 
    		fetch = FetchType.LAZY, optional = false, orphanRemoval = true)
    @JoinTable(name="user_general_info",
	    joinColumns={@JoinColumn(name="userFK", referencedColumnName = "id")},
	    inverseJoinColumns={@JoinColumn(name="generalInfoFK", referencedColumnName = "id")})
	private IGeneralInfo generalInfo;

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
	public boolean equals(final Object obj) {
		if ((obj instanceof User) == false) {
			return false;
		}
		final User other = (User) obj;
		return getId() != null && Objects.equals(getId(), other.getId());
	}
	
	@Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
