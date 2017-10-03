package br.com.collegesmaster.model;

import java.util.Set;

import br.com.collegesmaster.model.impl.RoleImpl;

public interface User extends Model {

	String getSalt();

	void setSalt(String salt);

	String getUsername();

	void setUsername(String username);

	String getPassword();

	void setPassword(String password);

	void setRoles(Set<RoleImpl> roles);

	Set<RoleImpl> getRoles();

	void setGeneralInfo(GeneralInfo person);

	GeneralInfo getGeneralInfo();

	Course getCourse();

	void setCourse(Course course);

}