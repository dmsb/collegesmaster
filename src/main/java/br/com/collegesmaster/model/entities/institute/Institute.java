package br.com.collegesmaster.model.entities.institute;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.collegesmaster.model.entities.course.impl.CourseImpl;
import br.com.collegesmaster.model.entities.institute.impl.InstituteImpl;
import br.com.collegesmaster.model.entities.localization.Localization;
import br.com.collegesmaster.model.entities.localization.impl.LocalizationImpl;
import br.com.collegesmaster.model.entities.model.Model;

@JsonDeserialize(as = InstituteImpl.class)
public interface Institute extends Model {

	String getName();

	void setName(String name);

	List<CourseImpl> getCourses();

	void setCourses(List<CourseImpl> courses);

	Localization getLocalization();

	void setLocalization(LocalizationImpl localization);

	int hashCode();

}