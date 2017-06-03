package br.com.collegesmaster.model.imp;

import java.util.Date;

import br.com.collegesmaster.model.IUser;

public interface IGeneralInfo {

	Integer getId();

	void setId(Integer id);

	IUser getUser();

	void setUser(IUser user);

	String getCpf();

	void setCpf(String cpf);

	String getEmail();

	void setEmail(String email);

	String getFirstName();

	void setFirstName(String firstName);

	String getLastName();

	void setLastName(String lastName);

	Date getBirthdate();

	void setBirthdate(Date birthdate);

	Localization getLocalization();

	void setLocalization(Localization localization);

}