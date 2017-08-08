package br.com.collegesmaster.rest.controller.impl;

import static java.lang.Boolean.TRUE;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import br.com.collegesmaster.business.RoleBusiness;
import br.com.collegesmaster.model.impl.RoleImpl;
import br.com.collegesmaster.rest.controller.RoleController;

@RequestScoped
public class RoleControllerImpl implements RoleController {
	
	@Inject
	private RoleBusiness roleBusiness;
	
	@Override
	public Boolean create(RoleImpl role) {
		roleBusiness.create(role);
		return TRUE;
	}

	@Override
	public RoleImpl update(RoleImpl role) {
		return roleBusiness.update(role);
	}

	@Override
	public Boolean remove(Integer id) {
		roleBusiness.remove(findById(id));
		return TRUE;
	}

	@Override
	public RoleImpl findById(Integer id) {
		return roleBusiness.findById(id);
	}

	@Override
	public List<RoleImpl> findAll(UriInfo info, Request request) {
		return null;
	}
}
