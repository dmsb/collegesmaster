package br.com.collegesmaster.view.converters;

import static br.com.collegesmaster.view.util.JsfUtils.addMessage;
import static javax.faces.application.FacesMessage.SEVERITY_WARN;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import  org.jboss.logging.Logger;

@FacesConverter("localDateConverter")
public class LocalDateConverter implements Converter {

	private static final Logger LOGGER = Logger.getLogger(LocalDateConverter.class);
	
	@Override
	public LocalDate getAsObject(FacesContext context, UIComponent component, String value) {
		try {
			return LocalDate.parse(value, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		} catch (DateTimeParseException | IllegalArgumentException e) {
			LOGGER.error("Invalid date format: " + e.getMessage());
			addMessage(SEVERITY_WARN, "invalid_date_message");
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		final LocalDate dateValue =  (LocalDate) value;
 		return dateValue.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
	}

}
