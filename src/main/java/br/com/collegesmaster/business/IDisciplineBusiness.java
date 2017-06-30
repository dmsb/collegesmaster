package br.com.collegesmaster.business;

import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import br.com.collegesmaster.model.ICourse;
import br.com.collegesmaster.model.IDiscipline;
import br.com.collegesmaster.model.impl.Discipline;

public interface IDisciplineBusiness extends IBusiness<IDiscipline> {
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	List<Discipline> findNamesByCourse(final ICourse course);

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	List<Discipline> findByCourse(final ICourse course);
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Discipline> findAll();
}
