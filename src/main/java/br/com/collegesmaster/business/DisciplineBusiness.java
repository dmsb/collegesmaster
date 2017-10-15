package br.com.collegesmaster.business;

import java.util.List;

import br.com.collegesmaster.model.Course;
import br.com.collegesmaster.model.impl.DisciplineImpl;

public interface DisciplineBusiness extends BasicCrudBusiness<DisciplineImpl> {
	
	List<DisciplineImpl> findNamesByCourse(final Course course);

	List<DisciplineImpl> findByCourse(final Course course);
	
}
