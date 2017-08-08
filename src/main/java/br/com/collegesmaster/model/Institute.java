package br.com.collegesmaster.model;

import java.util.List;

import br.com.collegesmaster.model.impl.CourseImpl;
import br.com.collegesmaster.model.impl.LocalizationImpl;

public interface Institute extends Model {

	String getName();

	void setName(String name);

	List<CourseImpl> getCourses();

	void setCourses(List<CourseImpl> courses);

	LocalizationImpl getLocalization();

	void setLocalization(LocalizationImpl localization);

	int hashCode();

}