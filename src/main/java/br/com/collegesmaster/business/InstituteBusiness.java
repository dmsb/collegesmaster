package br.com.collegesmaster.business;

import java.util.List;
import java.util.Map;

import br.com.collegesmaster.model.impl.InstituteImpl;

public interface InstituteBusiness extends Business<InstituteImpl> {
	
	List<InstituteImpl> findNames();
	
	List<InstituteImpl> findFetchingCourses();

	List<InstituteImpl> findAll(final Map<String, Object> equalsPredicate);
	
}
