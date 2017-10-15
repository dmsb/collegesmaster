package br.com.collegesmaster.rest.controller;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/roles")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public interface RoleController {
	
	@GET
	Response findAllByPredicates(@Context UriInfo requestInfo);

}
