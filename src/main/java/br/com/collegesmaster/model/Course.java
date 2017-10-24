package br.com.collegesmaster.model;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.collegesmaster.model.impl.CourseImpl;
import br.com.collegesmaster.model.impl.DisciplineImpl;

@JsonDeserialize(as = CourseImpl.class)
public interface Course extends Model{

	String getName();

	void setName(String name);

	Institute getInstitute();

	void setInstitute(Institute institute);

	List<DisciplineImpl> getDisciplines();

	void setDisciplines(List<DisciplineImpl> disciplines);

}