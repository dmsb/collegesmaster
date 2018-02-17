package br.com.collegesmaster.view.converters;

import javax.faces.convert.FacesConverter;

import br.com.collegesmaster.model.institute.Course;
import br.com.collegesmaster.view.converters.generics.GenericModelConverter;

@FacesConverter("courseConverter")
public class CourseConverter extends GenericModelConverter<Course> {
	
}
