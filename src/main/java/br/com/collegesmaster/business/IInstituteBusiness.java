package br.com.collegesmaster.business;

import java.util.List;

import br.com.collegesmaster.model.IInstitute;
import br.com.collegesmaster.model.imp.Institute;

public interface IInstituteBusiness extends IBusiness<IInstitute, Institute> {

	List<Institute> getInstituteNames();

	List<Institute> getInstitutesFetchingCourses();

}
