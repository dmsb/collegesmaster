package br.com.collegesmaster.jsf.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.google.common.base.Strings;

import br.com.collegesmaster.model.impl.Institute;

@FacesConverter(value = "instituteConverter")
public class InstituteConverter implements Converter {
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String institute) {
		if(!Strings.isNullOrEmpty(institute)) {
			return (Institute)component.getAttributes().get(institute);
		} else {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object institute) {
		if(institute instanceof Institute) {
			Institute currentInstitute = (Institute) institute;
			if(currentInstitute.getId() != null) {
				component.getAttributes().put(currentInstitute.getId().toString(), currentInstitute);
				return currentInstitute.getId().toString();
			} else {
				return null;
			}
		} else {
			return null;	
		}
	}

}