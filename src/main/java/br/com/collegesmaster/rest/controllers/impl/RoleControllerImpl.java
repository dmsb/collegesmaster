package br.com.collegesmaster.rest.controllers.impl;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import br.com.collegesmaster.model.user.business.RoleBusiness;
import br.com.collegesmaster.model.user.impl.RoleImpl;
import br.com.collegesmaster.rest.controllers.RoleController;
import br.com.collegesmaster.rest.controllers.util.CustomResponseBuilder;

@RequestScoped
public class RoleControllerImpl implements RoleController {
	
	@Inject
	private RoleBusiness roleBusiness;

	@Override
	public Response findAllByPredicates(UriInfo requestInfo) {
		final List<RoleImpl> roles = roleBusiness.findAllByPredicates(requestInfo);
		return CustomResponseBuilder.entityCollectionResponse(roles);
	}

}
