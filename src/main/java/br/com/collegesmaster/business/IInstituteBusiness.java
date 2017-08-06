package br.com.collegesmaster.business;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import br.com.collegesmaster.model.IInstitute;
import br.com.collegesmaster.model.impl.Institute;

@Path("/institute")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public interface IInstituteBusiness extends IBusiness<IInstitute, Institute> {
	
	@GET
	@Path("/list_names")
	List<Institute> findNames();
	
	@GET
	@Path("/list_fetching_courses")
	List<Institute> findFetchingCourses();

}
