package br.com.collegesmaster.model.institute.business;

import java.util.List;

import br.com.collegesmaster.model.generics.BasicCrud;
import br.com.collegesmaster.model.institute.impl.InstituteImpl;

public interface InstituteBusiness extends BasicCrud<InstituteImpl> {
	
	List<InstituteImpl> findNames();
	
	List<InstituteImpl> findFetchingCourses();
	
}
