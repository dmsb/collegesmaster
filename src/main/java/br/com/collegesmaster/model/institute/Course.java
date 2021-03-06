package br.com.collegesmaster.model.institute;

import java.util.Collection;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.collegesmaster.model.institute.impl.CourseImpl;
import br.com.collegesmaster.model.institute.impl.DisciplineImpl;
import br.com.collegesmaster.model.model.Model;

@JsonDeserialize(as = CourseImpl.class)
public interface Course extends Model{

	String getName();

	void setName(String name);

	Institute getInstitute();

	void setInstitute(Institute institute);

	Collection<DisciplineImpl> getDisciplines();

	void setDisciplines(Collection<DisciplineImpl> disciplines);

}