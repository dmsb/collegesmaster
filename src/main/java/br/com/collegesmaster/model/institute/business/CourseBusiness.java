package br.com.collegesmaster.model.institute.business;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import br.com.collegesmaster.model.generics.GenericCRUD;
import br.com.collegesmaster.model.institute.Course;
import br.com.collegesmaster.model.institute.Institute;
import br.com.collegesmaster.model.institute.impl.CourseImpl;

@Path("/courses")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public interface CourseBusiness extends GenericCRUD<Course> {
	
	Course findById(final Integer id);
	
	List<CourseImpl> findNamesByInstitute(final Institute institute);

}
