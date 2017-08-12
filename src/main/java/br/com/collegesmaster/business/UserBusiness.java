package br.com.collegesmaster.business;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import br.com.collegesmaster.model.impl.UserImpl;

@Path("/users")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public interface UserBusiness extends Business<UserImpl> {

	@GET
	@Path("/exists_cpf/{cpf}")
	Boolean existsCpf(@PathParam("cpf") String cpf);
	
	@GET
	@Path("/exists_email/{email}")
	Boolean existsEmail(@PathParam("email") String email);
	
	@GET
	@Path("/exists_username/{username}")
	Boolean existsUsername(@PathParam("username") String username);
	
	@GET
	@Path("/find_username/{username}")
	UserImpl findByUsername(@PathParam("username") String username);
}
