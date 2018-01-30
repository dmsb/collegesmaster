package br.com.collegesmaster.model.institute.business;

import java.util.List;

import br.com.collegesmaster.model.generics.GenericCRUD;
import br.com.collegesmaster.model.institute.Course;
import br.com.collegesmaster.model.institute.Discipline;
import br.com.collegesmaster.model.institute.impl.DisciplineImpl;

public interface DisciplineBusiness extends GenericCRUD<DisciplineImpl> {
	
	Discipline findById(final Integer id);
	
	List<DisciplineImpl> findNamesByCourse(final Course course);

	List<DisciplineImpl> findByCourse(final Course course);
	
}
