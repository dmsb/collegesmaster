package br.com.collegesmaster.model.entities.localization;

import java.io.Serializable;

public interface Localization extends Serializable {

	void setCity(String city);

	String getCity();

	void setState(String state);

	String getState();

	void setCountry(String country);

	String getCountry();

}
