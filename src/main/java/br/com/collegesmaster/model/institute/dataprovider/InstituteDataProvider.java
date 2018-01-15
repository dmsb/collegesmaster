package br.com.collegesmaster.model.institute.dataprovider;

import java.util.List;

import br.com.collegesmaster.model.institute.impl.InstituteImpl;

public interface InstituteDataProvider {
	
	List<InstituteImpl> findNames();
	
	List<InstituteImpl> findFetchingCourses();
	
}
