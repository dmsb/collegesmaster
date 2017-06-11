package br.com.collegesmaster.model;

import java.util.List;

public interface IUser extends IModel {

	String getSalt();

	void setSalt(String salt);

	String getUsername();

	void setUsername(String username);

	String getPassword();

	void setPassword(String password);

	void setProfiles(List<IProfile> profile);

	List<IProfile> getProfiles();

	void setGeneralInfo(IGeneralInfo person);

	IGeneralInfo getGeneralInfo();

}