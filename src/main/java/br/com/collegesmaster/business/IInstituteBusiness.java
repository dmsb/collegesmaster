package br.com.collegesmaster.business;

import static javax.ejb.TransactionAttributeType.REQUIRED;

import java.util.List;

import javax.ejb.TransactionAttribute;

import br.com.collegesmaster.model.IInstitute;
import br.com.collegesmaster.model.impl.Institute;

public interface IInstituteBusiness extends IBusiness<IInstitute, Institute> {
	
	@TransactionAttribute(REQUIRED)
	List<Institute> findNames();

	@TransactionAttribute(REQUIRED)
	List<Institute> findFetchingCourses();

}
