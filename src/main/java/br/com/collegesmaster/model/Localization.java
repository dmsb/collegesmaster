package br.com.collegesmaster.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Localization implements Serializable {
	
private static final long serialVersionUID = -8555407559997206124L;
	
	@Column(name = "country")
	private String country;
	
	@Column(name = "state")
	private String state;
	
	@Column(name = "city")
	private String city;

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
}
