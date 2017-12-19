package br.com.collegesmaster.model.institute;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.collegesmaster.model.institute.impl.CourseImpl;
import br.com.collegesmaster.model.institute.impl.InstituteImpl;
import br.com.collegesmaster.model.localization.Localization;
import br.com.collegesmaster.model.localization.impl.LocalizationImpl;
import br.com.collegesmaster.model.model.Model;

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