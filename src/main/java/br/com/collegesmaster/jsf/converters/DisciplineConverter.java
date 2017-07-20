package br.com.collegesmaster.jsf.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.google.common.base.Strings;

import br.com.collegesmaster.model.impl.Discipline;

@FacesConverter("disciplineConverter")
public class DisciplineConverter implements Converter {
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String discipline) {
		if(!Strings.isNullOrEmpty(discipline)) {
			return (Discipline)component.getAttributes().get(discipline);
		} else {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object discipline) {
		
		if(discipline instanceof Discipline) {
			Discipline currentDiscipline = (Discipline) discipline;
			if(currentDiscipline.getId() != null) {
				component.getAttributes().put(currentDiscipline.getId().toString(), currentDiscipline);
				return currentDiscipline.getId().toString();
			} else {
				return null;
			}
		} else {
			return null;	
		}	
	}
}
