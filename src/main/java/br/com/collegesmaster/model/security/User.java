package br.com.collegesmaster.model.security;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.collegesmaster.model.institute.Course;
import br.com.collegesmaster.model.model.Model;
import br.com.collegesmaster.model.security.impl.RoleImpl;
import br.com.collegesmaster.model.security.impl.UserImpl;

@JsonDeserialize(as = UserImpl.class)
public interface User extends Model {

	String getSalt();

	void setSalt(String salt);

	String getUsername();

	void setUsername(String username);

	String getPassword();

	void setPassword(String password);

	void setRoles(Collection<RoleImpl> roles);

	Collection<RoleImpl> getRoles();

	Course getCourse();

	void setCourse(Course course);
	
	String getCpf();

	void setCpf(String cpf);

	String getEmail();

	void setEmail(String email);

	String getFirstName();

	void setFirstName(String firstName);

	String getLastName();

	void setLastName(String lastName);

	LocalDate getBirthdate();

	void setBirthdate(LocalDate birthdate);

	List<String> getRoleNames();

	Boolean isUserInRole(final String roleName);

	void parseCpfToCrude();

	void encriptyPassword();

	Boolean isUserInAnyRoles(final List<String> roleNames);

}