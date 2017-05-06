package br.com.collegesmaster.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@Table(name = "USER")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "profile_type")
public class User implements Serializable {

    private static final long serialVersionUID = -7809703915845045860L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "username", unique= true)
    @NotNull
    private String username;  

	@Column(name = "password", unique = false, updatable = true, nullable = false)
    @NotEmpty
    private String password;

    @Column(name = "salt")
    @NotBlank
    private String salt;

    @NotEmpty
    @Pattern.List({
//        @Pattern(regexp = "(?=.*[0-9])", message = "Password must contain one digit."),
//        @Pattern(regexp = "(?=.*[a-z])", message = "Password must contain one lowercase letter."),
//        @Pattern(regexp = "(?=.*[A-Z])", message = "Password must contain one uppercase letter."),
//        @Pattern(regexp = "(?=\\S+$)", message = "Password must contain no whitespace.")
        })
    @Transient
    private String rawPassword;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getRawPassword() {
        return rawPassword;
    }

    public void setRawPassword(String rawPassword) {
        this.rawPassword = rawPassword;
    }
}
