package br.com.collegesmaster.model;

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

import org.hibernate.validator.constraints.NotEmpty;

import br.com.collegesmaster.annotations.Password;

@Table(name = "USER")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "profile_type")
public class User implements Serializable {

    private static final long serialVersionUID = -7809703915845045860L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "username", unique= true)
    @NotNull
    private String username;  

    @NotEmpty
	@Column(name = "password", unique = false, updatable = true, nullable = false)
    @Password
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getUsername() {
  		return username;
  	}

  	public void setUsername(String username) {
  		this.username = username;
  	}
  	
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public boolean equals(final Object obj) {
		if ((obj instanceof User) == false) {
			return false;
		}
		final User other = (User) obj;
		return getId() != null && Objects.equals(getId(), other.getId());
	}
}
