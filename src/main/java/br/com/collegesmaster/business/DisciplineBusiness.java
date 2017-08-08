package br.com.collegesmaster.business;

import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import br.com.collegesmaster.model.Course;
import br.com.collegesmaster.model.impl.DisciplineImpl;

public interface DisciplineBusiness extends Business<DisciplineImpl> {
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	List<DisciplineImpl> findNamesByCourse(final Course course);

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	List<DisciplineImpl> findByCourse(final Course course);
	
}
