package br.com.collegesmaster.model;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.collegesmaster.model.impl.RoleImpl;
import br.com.collegesmaster.model.impl.UserImpl;

@JsonDeserialize(as = UserImpl.class)
public interface User extends Model {

	String getSalt();

	void setSalt(String salt);

	String getUsername();

	void setUsername(String username);

	String getPassword();

	void setPassword(String password);

	void setRoles(List<RoleImpl> roles);

	List<RoleImpl> getRoles();

	void setGeneralInfo(GeneralInfo person);

	GeneralInfo getGeneralInfo();

	Course getCourse();

	void setCourse(Course course);

}