package br.com.collegesmaster.model.entities.course;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.collegesmaster.model.entities.course.impl.CourseImpl;
import br.com.collegesmaster.model.entities.discipline.impl.DisciplineImpl;
import br.com.collegesmaster.model.entities.institute.Institute;
import br.com.collegesmaster.model.entities.model.Model;

@JsonDeserialize(as = CourseImpl.class)
public interface Course extends Model{

	String getName();

	void setName(String name);

	Institute getInstitute();

	void setInstitute(Institute institute);

	List<DisciplineImpl> getDisciplines();

	void setDisciplines(List<DisciplineImpl> disciplines);

}