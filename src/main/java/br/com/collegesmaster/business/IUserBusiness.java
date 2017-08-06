package br.com.collegesmaster.business;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import br.com.collegesmaster.model.IUser;
import br.com.collegesmaster.model.impl.User;

@Path("/user")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public interface IUserBusiness extends IBusiness<IUser, User> {
	
	@GET
	@Path("/exists_cpf/{cpf}")
	Boolean existsCpf(@PathParam("cpf") String cpfToBeComparated);
	
	@GET
	@Path("/exists_email/{email}")
	Boolean existsEmail(@PathParam("email") String emailToBeComparated);
	
	@GET
	@Path("/exists_username/{username}")
	Boolean existsUsername(@PathParam("username") String usernameToBeComparated);
	
	@GET
	@Path("/get/{username}")
	IUser findByUsername(@PathParam("username") String username);
}
