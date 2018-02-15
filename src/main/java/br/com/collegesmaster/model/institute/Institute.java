package br.com.collegesmaster.model.institute;

import java.util.Collection;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.collegesmaster.model.institute.impl.CourseImpl;
import br.com.collegesmaster.model.institute.impl.InstituteImpl;
import br.com.collegesmaster.model.localization.Localization;
import br.com.collegesmaster.model.model.Model;

@JsonDeserialize(as = InstituteImpl.class)
public interface Institute extends Localization, Model {

	String getName();

	void setName(String name);

	Collection<CourseImpl> getCourses();

	void setCourses(Collection<CourseImpl> courses);

	void setSemester(String semester);

	String getSemester();

}