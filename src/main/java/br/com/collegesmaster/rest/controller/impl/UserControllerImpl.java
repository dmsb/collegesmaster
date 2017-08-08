package br.com.collegesmaster.rest.controller.impl;

import static java.lang.Boolean.TRUE;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import br.com.collegesmaster.business.UserBusiness;
import br.com.collegesmaster.model.User;
import br.com.collegesmaster.model.impl.UserImpl;
import br.com.collegesmaster.rest.controller.UserController;

@RequestScoped
public class UserControllerImpl implements UserController {

	@Inject
	private UserBusiness userBusiness;

	@Override
	public Boolean create(UserImpl imodel) {
		userBusiness.create(imodel);
		return TRUE;
	}

	@Override
	public UserImpl update(UserImpl imodel) {
		return userBusiness.update(imodel);
	}

	@Override
	public Boolean remove(Integer id) {
		final UserImpl user = userBusiness.findById(id);
		userBusiness.remove(user);

		return TRUE;
	}

	@Override
	public UserImpl findById(Integer id) {
		return userBusiness.findById(id);
	}

	@Override
	public List<UserImpl> findAll(UriInfo info, Request request) {
		return null;
	}

	@Override
	public Boolean existsCpf(String cpf) {
		return userBusiness.existsCpf(cpf);
	}

	@Override
	public Boolean existsEmail(String email) {
		return userBusiness.existsEmail(email);
	}

	@Override
	public Boolean existsUsername(String username) {
		return userBusiness.existsUsername(username);
	}

	@Override
	public User findByUsername(String username) {
		return userBusiness.findByUsername(username);
	}

}
