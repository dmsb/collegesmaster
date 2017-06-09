package br.com.collegesmaster.model;

import java.util.Date;
import java.util.List;

import br.com.collegesmaster.model.imp.Localization;

public interface IGeneralInfo {

	Integer getId();

	void setId(Integer id);

	List<IUser> getUsers();

	void setUser( List<IUser> user);

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