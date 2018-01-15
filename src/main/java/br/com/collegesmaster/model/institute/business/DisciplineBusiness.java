package br.com.collegesmaster.model.institute.business;

import java.util.List;

import br.com.collegesmaster.model.generics.GenericBusiness;
import br.com.collegesmaster.model.institute.Course;
import br.com.collegesmaster.model.institute.impl.DisciplineImpl;

public interface DisciplineBusiness extends GenericBusiness<DisciplineImpl> {
	
	DisciplineImpl findById(final Integer id);
	
	List<DisciplineImpl> findNamesByCourse(final Course course);

	List<DisciplineImpl> findByCourse(final Course course);
	
}
