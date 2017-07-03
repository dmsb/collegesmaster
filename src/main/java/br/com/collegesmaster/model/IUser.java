package br.com.collegesmaster.model;

public interface IUser extends IModel {

	String getSalt();

	void setSalt(String salt);

	String getUsername();

	void setUsername(String username);

	String getPassword();

	void setPassword(String password);

	void setRole(IRole role);

	IRole getRole();

	void setGeneralInfo(IGeneralInfo person);

	IGeneralInfo getGeneralInfo();

}