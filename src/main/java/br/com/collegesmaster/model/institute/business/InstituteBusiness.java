package br.com.collegesmaster.model.institute.business;

import java.util.List;

import br.com.collegesmaster.model.generics.GenericCRUD;
import br.com.collegesmaster.model.institute.impl.InstituteImpl;

public interface InstituteBusiness extends GenericCRUD<InstituteImpl> {

	InstituteImpl findById(final Integer id);
	
	List<InstituteImpl> findNames();
	
	List<InstituteImpl> findFetchingCourses();
}
