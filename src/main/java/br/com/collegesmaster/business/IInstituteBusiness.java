package br.com.collegesmaster.business;

import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import br.com.collegesmaster.model.IInstitute;
import br.com.collegesmaster.model.imp.Institute;

public interface IInstituteBusiness extends IBusiness<IInstitute> {
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	List<Institute> findIdsAndNames();

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	List<Institute> findFetchingCourses();
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Institute> findAll();

}
