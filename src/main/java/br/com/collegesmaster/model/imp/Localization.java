package br.com.collegesmaster.model.imp;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import br.com.collegesmaster.annotations.State;

@Embeddable
public class Localization implements Serializable {
	
	private static final long serialVersionUID = -8555407559997206124L;
	
	@Column(name = "country")
	private String country;
	
	@State
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
	
	@Override
    public boolean equals(final Object objectToBeComparated) {
    	
    	if(objectToBeComparated == this) {
			return true;
		}
		
		if(!(objectToBeComparated instanceof Localization)) {
			return false;
		}
		
		final Localization objectComparatedInstance = (Localization) objectToBeComparated;
		
		return Objects.equals(country, objectComparatedInstance.country) &&
				Objects.equals(state, objectComparatedInstance.state) &&
				Objects.equals(city, objectComparatedInstance.city);
	}
    
	@Override
    public int hashCode() {
        return Objects.hash(country, state, city);
    }
}
