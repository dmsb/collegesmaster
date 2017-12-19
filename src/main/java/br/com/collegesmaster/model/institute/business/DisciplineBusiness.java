package br.com.collegesmaster.model.institute.business;

import java.util.List;

import br.com.collegesmaster.model.generics.BasicCrud;
import br.com.collegesmaster.model.institute.Course;
import br.com.collegesmaster.model.institute.impl.DisciplineImpl;

public interface DisciplineBusiness extends BasicCrud<DisciplineImpl> {
	
	List<DisciplineImpl> findNamesByCourse(final Course course);

	List<DisciplineImpl> findByCourse(final Course course);
	
}
