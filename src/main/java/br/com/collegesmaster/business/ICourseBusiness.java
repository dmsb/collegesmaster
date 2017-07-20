package br.com.collegesmaster.business;

import static javax.ejb.TransactionAttributeType.REQUIRED;

import java.util.List;

import javax.ejb.TransactionAttribute;

import br.com.collegesmaster.model.ICourse;
import br.com.collegesmaster.model.IInstitute;
import br.com.collegesmaster.model.impl.Course;

public interface ICourseBusiness extends IBusiness<ICourse, Course> {
	
	@TransactionAttribute(REQUIRED)
	List<Course> findNamesByInstitute(final IInstitute institute);

}
