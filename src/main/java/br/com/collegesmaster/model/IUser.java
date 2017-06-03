package br.com.collegesmaster.model;

import br.com.collegesmaster.model.imp.IGeneralInfo;

public interface IUser extends IModel {

	String getSalt();

	void setSalt(String salt);

	String getUsername();

	void setUsername(String username);

	String getPassword();

	void setPassword(String password);

	IGeneralInfo getGeneralInfo();

	void setGeneralInfo(IGeneralInfo generalInfo);

}