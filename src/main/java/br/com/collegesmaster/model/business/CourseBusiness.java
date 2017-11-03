package br.com.collegesmaster.model.business;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import br.com.collegesmaster.model.entities.course.impl.CourseImpl;
import br.com.collegesmaster.model.entities.institute.Institute;

@Path("/courses")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public interface CourseBusiness extends BasicCrudBusiness<CourseImpl> {
	
	List<CourseImpl> findNamesByInstitute(final Institute institute);

}
