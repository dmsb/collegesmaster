package br.com.collegesmaster.rest.controllers.impl;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import br.com.collegesmaster.model.security.business.UserBusiness;
import br.com.collegesmaster.model.security.impl.UserImpl;
import br.com.collegesmaster.rest.controllers.UserController;

@RequestScoped
public class UserControllerImpl implements UserController {

	@Inject
	private UserBusiness userBusiness;
	
	@Context
	UriInfo uri;
	
	@Override
	public Response create(final UserImpl user) {
		
		final Boolean wasCreated = userBusiness.create(user);
		if (wasCreated) {
			return Response.ok().entity("User created").build();
		} else {
			return Response.status(Status.BAD_REQUEST).build();
		}
	}

}
