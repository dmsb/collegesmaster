package br.com.collegesmaster.model;

import java.util.List;

import br.com.collegesmaster.model.impl.Discipline;

public interface ICourse extends IModel{

	String getName();

	void setName(String name);

	IInstitute getInstitute();

	void setInstitute(IInstitute institute);

	List<Discipline> getDisciplines();

	void setDisciplines(List<Discipline> disciplines);

}