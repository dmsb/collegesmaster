package br.com.collegesmaster.model;

import br.com.collegesmaster.enums.Profile;
import java.io.Serializable;
import javax.persistence.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CPF;

@Table(name = "TB_USER")
@Entity
public class User implements Serializable {

    private static final long serialVersionUID = -7809703915845045860L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cpf", unique = true, updatable = false, nullable = false)
    @NotBlank
    @CPF
    private String cpf;

    @Column(name = "password", unique = false, updatable = true, nullable = false)
    @NotEmpty
    private String password;

    @Column(name = "salt")
    @NotBlank
    private String salt;

    @OneToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.EAGER, orphanRemoval = true)
    @NotNull
    private Person person;

    @Column(name = "profile")
    @NotNull
    @Enumerated(EnumType.STRING)
    private Profile profile;

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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
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

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
