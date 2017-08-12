package br.com.collegesmaster.business;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import br.com.collegesmaster.model.impl.RoleImpl;

@Path("/roles")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public interface RoleBusiness extends Business<RoleImpl> {
	
}
