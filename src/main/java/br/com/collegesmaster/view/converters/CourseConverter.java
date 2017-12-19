package br.com.collegesmaster.view.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.google.common.base.Strings;

import br.com.collegesmaster.model.institute.impl.CourseImpl;

@FacesConverter(value = "courseConverter")
public class CourseConverter implements Converter {
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String course) {
		if(!Strings.isNullOrEmpty(course)) {
			return (CourseImpl)component.getAttributes().get(course);
		} else {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object course) {
		
		if(course instanceof CourseImpl) {
			CourseImpl currentCourse = (CourseImpl) course;
			if(currentCourse.getId() != null) {
				component.getAttributes().put(currentCourse.getId().toString(), currentCourse);
				return currentCourse.getId().toString();
			} else {
				return null;
			}
		} else {
			return null;	
		}	
	}
}
