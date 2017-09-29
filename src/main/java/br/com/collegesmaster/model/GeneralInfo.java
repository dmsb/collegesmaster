package br.com.collegesmaster.model;

import java.time.LocalDate;
import java.util.List;

import br.com.collegesmaster.model.impl.UserImpl;

public interface GeneralInfo extends Model {

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

	void setUsers(List<UserImpl> users);

	List<UserImpl> getUsers();

}