package br.com.collegesmaster.business;

import static javax.ejb.TransactionAttributeType.REQUIRED;

import java.util.List;

import javax.ejb.TransactionAttribute;

import br.com.collegesmaster.model.Institute;
import br.com.collegesmaster.model.impl.CourseImpl;

public interface CourseBusiness extends Business<CourseImpl> {
	
	@TransactionAttribute(REQUIRED)
	List<CourseImpl> findNamesByInstitute(final Institute institute);

}
