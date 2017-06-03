package br.com.collegesmaster.model;

import java.util.List;

public interface ICourse extends IModel{

	String getName();

	void setName(String name);

	IInstitute getInstitute();

	void setInstitute(IInstitute institute);

	List<IDiscipline> getDisciplines();

	void setDisciplines(List<IDiscipline> disciplines);

}