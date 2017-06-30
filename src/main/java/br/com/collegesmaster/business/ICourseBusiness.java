package br.com.collegesmaster.business;

import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import br.com.collegesmaster.model.ICourse;
import br.com.collegesmaster.model.IInstitute;
import br.com.collegesmaster.model.impl.Course;

public interface ICourseBusiness extends IBusiness<ICourse> {
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	List<Course> findNamesByInstitute(final IInstitute institute);
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Course> findAll();
}
