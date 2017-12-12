package br.com.collegesmaster.model.business;

import java.util.List;

import br.com.collegesmaster.model.entities.institute.impl.InstituteImpl;

public interface InstituteBusiness extends BasicCrudBusiness<InstituteImpl> {
	
	List<InstituteImpl> findNames();
	
	List<InstituteImpl> findFetchingCourses();
	
}