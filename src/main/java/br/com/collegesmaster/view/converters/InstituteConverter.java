package br.com.collegesmaster.view.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.google.common.base.Strings;

import br.com.collegesmaster.model.institute.Institute;
import br.com.collegesmaster.model.institute.impl.InstituteImpl;

@FacesConverter(value = "instituteConverter")
public class InstituteConverter implements Converter {
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String institute) {
		if(!Strings.isNullOrEmpty(institute)) {
			return (Institute) component.getAttributes().get(institute);
		} else {
			return "";
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object institute) {
		if(institute instanceof InstituteImpl) {
			Institute currentInstitute = (Institute) institute;
			if(currentInstitute.getId() != null) {
				component.getAttributes().put(currentInstitute.getId().toString(), currentInstitute);
				return currentInstitute.getId().toString();
			} else {
				return "";
			}
		} else {
			return "";	
		}
	}

}
