package br.com.collegesmaster.model.business;

import java.util.List;

import br.com.collegesmaster.model.entities.course.Course;
import br.com.collegesmaster.model.entities.discipline.impl.DisciplineImpl;

public interface DisciplineBusiness extends BasicCrudBusiness<DisciplineImpl> {
	
	List<DisciplineImpl> findNamesByCourse(final Course course);

	List<DisciplineImpl> findByCourse(final Course course);
	
}
