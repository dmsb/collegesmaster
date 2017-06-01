package br.com.collegesmaster.model.imp;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.collegesmaster.annotations.Password;
import br.com.collegesmaster.model.IUser;

@Table(name = "user")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "profileType")
public class User  implements Serializable, IUser {

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
    
    @Override
	public Integer getId() {
		return id;
	}
    
    @Override
	public void setId(Integer id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.IUser#getSalt()
	 */
	@Override
	public String getSalt() {		
		return salt;
	}

	/* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.IUser#setSalt(java.lang.String)
	 */
	@Override
	public void setSalt(String salt) {
		this.salt = salt;
	}
    
    /* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.IUser#getUsername()
	 */
    @Override
	public String getUsername() {
  		return username;
  	}

  	/* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.IUser#setUsername(java.lang.String)
	 */
  	@Override
	public void setUsername(String username) {
  		this.username = username;
  	}
  	
    /* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.IUser#getPassword()
	 */
    @Override
	public String getPassword() {
        return password;
    }

    /* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.IUser#setPassword(java.lang.String)
	 */
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
