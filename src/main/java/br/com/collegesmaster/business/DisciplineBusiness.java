package br.com.collegesmaster.business;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import br.com.collegesmaster.model.Course;
import br.com.collegesmaster.model.impl.DisciplineImpl;

@Path("/disciplines")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public interface DisciplineBusiness extends Business<DisciplineImpl> {
	
	@GET
	@Path("/names/{courseId}")
	List<DisciplineImpl> findNamesByCourse(final Course course);

	@GET
	@Path("/{courseId}")
	List<DisciplineImpl> findByCourse(final Course course);
	
}
