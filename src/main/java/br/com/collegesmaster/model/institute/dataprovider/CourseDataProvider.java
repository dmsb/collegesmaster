package br.com.collegesmaster.model.institute.dataprovider;

import java.util.List;

import br.com.collegesmaster.model.institute.Institute;
import br.com.collegesmaster.model.institute.impl.CourseImpl;

public interface CourseDataProvider {

	List<CourseImpl> findNamesByInstitute(final Institute institute);
	
}
