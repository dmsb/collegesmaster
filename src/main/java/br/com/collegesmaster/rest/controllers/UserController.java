package br.com.collegesmaster.rest.controllers;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import br.com.collegesmaster.model.entities.user.impl.UserImpl;

@Path("/users")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public interface UserController {

	@POST
	@Path("/create")
	Response create(final UserImpl user);
}	
