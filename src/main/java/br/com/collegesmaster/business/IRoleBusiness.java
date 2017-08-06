package br.com.collegesmaster.business;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import br.com.collegesmaster.model.IRole;
import br.com.collegesmaster.model.impl.Role;

@Path("/role")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public interface IRoleBusiness extends IBusiness<IRole, Role> {
	
}
