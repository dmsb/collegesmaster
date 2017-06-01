package br.com.collegesmaster.jsf;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

public class LanguageMB implements Serializable {

	private static final long serialVersionUID = -7269752654899410705L;

	private String localeCode;

	private static Map<String, Object> countries;
	
	static {
		countries = new LinkedHashMap<String, Object>();
		countries.put("Brazillian", new Locale("pt", "BR"));
		countries.put("English", Locale.ENGLISH);
	}

	public Map<String, Object> getCountriesInMap() {
		return countries;
	}

	public String getLocaleCode() {
		return localeCode;
	}

	public void setLocaleCode(final String localeCode) {
		this.localeCode = localeCode;
	}

	public void countryLocaleCodeChanged(final ValueChangeEvent e) {
		
		final String newLocaleValue = e.getNewValue().toString();
		
		for (final Map.Entry<String, Object> entry : countries.entrySet()) {
			if (entry.getValue().toString().equals(newLocaleValue)) {
				FacesContext.getCurrentInstance().getViewRoot().setLocale((Locale) entry.getValue());
			}
		}
	}
}
