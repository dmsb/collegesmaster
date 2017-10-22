package br.com.collegesmaster.rest.controller.impl;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.collegesmaster.business.UserBusiness;
import br.com.collegesmaster.model.impl.UserImpl;
import br.com.collegesmaster.rest.controller.UserController;

@RequestScoped
public class UserControllerImpl implements UserController {

	@Inject
	private UserBusiness userBusiness;
	
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
