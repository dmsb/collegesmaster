package br.com.collegesmaster.rest.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface AuthenticationController {
	
	@POST
	@Path("/login")
	public Response jaasAuthentication(@Context HttpServletRequest servletRequest,
		@QueryParam("username")String username, @QueryParam("password")String password);

	@POST
	@Path("/logout")
	public Response jaasLogout(@Context HttpServletRequest servletRequest);
	
}
