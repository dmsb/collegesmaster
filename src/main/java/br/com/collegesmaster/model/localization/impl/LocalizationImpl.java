package br.com.collegesmaster.model.localization.impl;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import br.com.collegesmaster.model.localization.Localization;

@Embeddable
public class LocalizationImpl implements Localization {
	
	private static final long serialVersionUID = -8555407559997206124L;
	
	@Column(name = "country", nullable = false, length = 50)
	private String country;
	
	@Column(name = "state", nullable = false, length = 30)
	private String state;
	
	@Column(name = "city", nullable = false, length = 50)
	private String city;

	@Override
	public String getCountry() {
		return country;
	}

	@Override
	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String getState() {
		return state;
	}

	@Override
	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String getCity() {
		return city;
	}

	@Override
	public void setCity(String city) {
		this.city = city;
	}
	
	@Override
    public boolean equals(final Object objectToBeComparated) {
    	
		if(this == objectToBeComparated) {
			return true;
		}
		
		if(!(objectToBeComparated instanceof LocalizationImpl)) {
			return false;
		}
		
		final LocalizationImpl objectComparatedInstance = (LocalizationImpl) objectToBeComparated;
		
		return Objects.equals(country, objectComparatedInstance.country) &&
				Objects.equals(state, objectComparatedInstance.state) &&
				Objects.equals(city, objectComparatedInstance.city);
	}
    
	@Override
    public int hashCode() {
        return Objects.hash(country, state, city);
    }
}
