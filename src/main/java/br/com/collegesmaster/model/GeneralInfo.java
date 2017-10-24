package br.com.collegesmaster.model;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.collegesmaster.model.impl.GeneralInfoImpl;

@JsonDeserialize(as = GeneralInfoImpl.class)
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

}