package br.com.collegesmaster.model;

import java.util.List;

import br.com.collegesmaster.model.impl.Course;
import br.com.collegesmaster.model.impl.Localization;

public interface IInstitute extends IModel {

	String getName();

	void setName(String name);

	List<Course> getCourses();

	void setCourses(List<Course> courses);

	Localization getLocalization();

	void setLocalization(Localization localization);

	int hashCode();

}