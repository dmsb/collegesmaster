package br.com.collegesmaster.util;

import static br.com.collegesmaster.util.JSFUtils.addMessage;
import static javax.faces.application.FacesMessage.SEVERITY_WARN;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.jboss.logging.Logger;

@FacesConverter(value = "localDateTimeConverter")
public class LocalDateTimeConverter implements Converter {
	
	private static final Logger LOGGER = Logger.getLogger(LocalDateTimeConverter.class);
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		try {
			return LocalDate.parse(value, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		} catch (DateTimeParseException | IllegalArgumentException e) {
			LOGGER.error("Invalid date format: " + e.getMessage());
			addMessage(SEVERITY_WARN, "msg_invalid_date");
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		LocalDate dateValue = (LocalDate) value;
		return dateValue.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

	}

}
