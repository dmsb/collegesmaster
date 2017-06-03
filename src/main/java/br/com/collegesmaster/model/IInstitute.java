package br.com.collegesmaster.model;

import java.util.List;

import br.com.collegesmaster.model.imp.Localization;

public interface IInstitute extends IModel {

	String getName();

	void setName(String name);

	List<ICourse> getCourses();

	void setCourses(List<ICourse> courses);

	Localization getLocalization();

	void setLocalization(Localization localization);

	int hashCode();

}