package br.com.collegesmaster.model;

import br.com.collegesmaster.model.imp.GeneralInfo;

public interface IUser extends IModel {

	String getSalt();

	void setSalt(String salt);

	String getUsername();

	void setUsername(String username);

	String getPassword();

	void setPassword(String password);

	GeneralInfo getGeneralInfo();

	void setGeneralInfo(GeneralInfo generalInfo);

}