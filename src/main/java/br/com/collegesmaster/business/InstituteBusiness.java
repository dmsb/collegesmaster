package br.com.collegesmaster.business;

import java.util.List;

import br.com.collegesmaster.model.impl.InstituteImpl;

public interface InstituteBusiness extends BasicCrudBusiness<InstituteImpl> {
	
	List<InstituteImpl> findNames();
	
	List<InstituteImpl> findFetchingCourses();
	
}
