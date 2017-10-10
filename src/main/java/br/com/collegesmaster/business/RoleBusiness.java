package br.com.collegesmaster.business;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.ejb.Local;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import br.com.collegesmaster.model.impl.RoleImpl;

@Path("/roles")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@Local
public interface RoleBusiness extends BasicCrudOperation<RoleImpl> {
	
}
