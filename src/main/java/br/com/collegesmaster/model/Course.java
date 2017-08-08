package br.com.collegesmaster.model;

import java.util.List;

import br.com.collegesmaster.model.impl.DisciplineImpl;

public interface Course extends Model{

	String getName();

	void setName(String name);

	Institute getInstitute();

	void setInstitute(Institute institute);

	List<DisciplineImpl> getDisciplines();

	void setDisciplines(List<DisciplineImpl> disciplines);

}