package br.com.collegesmaster.view.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.google.common.base.Strings;

import br.com.collegesmaster.model.institute.Discipline;
import br.com.collegesmaster.model.institute.impl.DisciplineImpl;

@FacesConverter("disciplineConverter")
public class DisciplineConverter implements Converter<Discipline> {
	
	@Override
	public Discipline getAsObject(FacesContext context, UIComponent component, String discipline) {
		if(!Strings.isNullOrEmpty(discipline)) {
			return (Discipline) component.getAttributes().get(discipline);
		} else {
			return new DisciplineImpl();
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Discipline discipline) {
		if(discipline != null && discipline.getId() != null) {
			component.getAttributes().put(discipline.getId().toString(), discipline);
			return discipline.getId().toString();
		} else {
			return "";
		}
	}
}
