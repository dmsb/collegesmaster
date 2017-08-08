package br.com.collegesmaster.model;

import java.util.List;

public interface User extends Model {

	String getSalt();

	void setSalt(String salt);

	String getUsername();

	void setUsername(String username);

	String getPassword();

	void setPassword(String password);

	void setRoles(List<Role> roles);

	List<Role> getRoles();

	void setGeneralInfo(GeneralInfo person);

	GeneralInfo getGeneralInfo();

}