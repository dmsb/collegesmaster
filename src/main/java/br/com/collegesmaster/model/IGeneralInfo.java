package br.com.collegesmaster.model;

import java.time.LocalDate;

public interface IGeneralInfo extends IModel {

	Integer getId();

	void setId(Integer id);

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

	void setCourse(ICourse course);

	ICourse getCourse();

}