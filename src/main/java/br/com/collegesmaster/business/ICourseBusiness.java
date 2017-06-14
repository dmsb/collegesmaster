package br.com.collegesmaster.business;

import java.util.List;

import br.com.collegesmaster.model.ICourse;
import br.com.collegesmaster.model.IInstitute;
import br.com.collegesmaster.model.imp.Course;

public interface ICourseBusiness extends IBusiness<ICourse, Course> {

	List<Course> getCoursesNameByInstitute(final IInstitute institute);

}
