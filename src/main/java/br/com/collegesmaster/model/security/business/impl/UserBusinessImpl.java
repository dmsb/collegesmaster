package br.com.collegesmaster.model.security.business.impl;

import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.jboss.ejb3.annotation.SecurityDomain;

import br.com.collegesmaster.model.generics.impl.GenericBusinessImpl;
import br.com.collegesmaster.model.security.business.UserBusiness;
import br.com.collegesmaster.model.security.dataprovider.UserDataProvider;
import br.com.collegesmaster.model.security.impl.UserImpl;
import br.com.collegesmaster.qualifiers.AuthenticatedUser;

@Stateless
@TransactionManagement(CONTAINER)
@SecurityDomain("collegesmasterSecurityDomain")
public class UserBusinessImpl extends GenericBusinessImpl<UserImpl> implements UserBusiness {
	
	@Inject
	private UserDataProvider userDataProvider;
	
	@Inject
	@AuthenticatedUser
	private Event<UserImpl> userUpdateEvent;

	@PermitAll
	@TransactionAttribute(REQUIRED)
	@Override
	public Boolean create(final UserImpl user) {
		return super.genericCreate(user);
	}

	@RolesAllowed({ "STUDENT", "PROFESSOR", "ADMINISTATOR" })
	@TransactionAttribute(REQUIRED)
	@Override
	public UserImpl update(final UserImpl user) {
		final UserImpl savedUser = super.genericUpdate(user);
		if(savedUser != null) {
			userUpdateEvent.fire(savedUser);
			return savedUser;
		} else {
			return user;
		}
		
	}

	@RolesAllowed({ "ADMINISTRATOR" })
	@TransactionAttribute(REQUIRED)
	@Override
	public Boolean remove(final UserImpl user) {
		return super.genericRemove(user);
	}

	@RolesAllowed({ "STUDENT", "PROFESSOR", "ADMINISTATOR" })
	@TransactionAttribute(REQUIRED)
	@Override
	public UserImpl findById(final Integer id) {
		return super.findById(UserImpl.class, id);
	}
	
	@PermitAll
	@TransactionAttribute(REQUIRED)
	@Override
	public Boolean existsCpf(final String cpf) {
		final String crudeCpfToBeComparated = cpf.replaceAll("[^0-9]", "");
		return userDataProvider.existsCpf(crudeCpfToBeComparated);
	}

	@PermitAll
	@TransactionAttribute(REQUIRED)
	@Override
	public Boolean existsUsername(final String username) {
		return userDataProvider.existsUsername(username);
	}

	@PermitAll
	@TransactionAttribute(REQUIRED)
	@Override
	public Boolean existsEmail(final String email) {
		return userDataProvider.existsEmail(email);
	}

	@PermitAll
	@TransactionAttribute(REQUIRED)
	@Override
	public UserImpl findByUsername(final String username) {
		return userDataProvider.findByUsername(username);
	}
}
