package br.com.collegesmaster.model.security.business.impl;

import static br.com.collegesmaster.rest.utils.RestUtils.buildPredicatesFromRequest;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.List;
import java.util.Map;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;
import javax.ws.rs.core.UriInfo;

import org.jboss.ejb3.annotation.SecurityDomain;

import br.com.collegesmaster.model.generics.impl.GenericBusinessImpl;
import br.com.collegesmaster.model.security.Role;
import br.com.collegesmaster.model.security.business.RoleBusiness;
import br.com.collegesmaster.model.security.dataprovider.RoleDataProvider;
import br.com.collegesmaster.model.security.impl.RoleImpl;

@Stateless
@TransactionManagement(CONTAINER)
@RolesAllowed({"ADMINISTRATOR"})
@SecurityDomain("collegesmasterSecurityDomain")
public class RoleBusinessImpl extends GenericBusinessImpl<Role> implements RoleBusiness {
	
	@Inject
	private RoleDataProvider roleDataProvider;
	
	@TransactionAttribute(REQUIRED)
	@Override
	public Boolean create(Role role) {
		return super.genericCreate(role);
	}

	@TransactionAttribute(REQUIRED)
	@Override
	public Role update(Role role) {
		return super.genericUpdate(role);
	}

	@TransactionAttribute(REQUIRED)
	@Override
	public Boolean remove(Role role) {
		return super.genericRemove(role);
	}
	
	@PermitAll
	@TransactionAttribute(REQUIRED)
	@Override
	public Role findById(Integer id) {
		return super.findById(Role.class, id);
	}
	
	@PermitAll
	@TransactionAttribute(REQUIRED)	
	@Override
	public List<RoleImpl> findAll() {
		return roleDataProvider.findAllElegibleRoles();
	}
	
	@PermitAll
	@TransactionAttribute(REQUIRED)
	@Override
	public List<RoleImpl> findAllByPredicates(final UriInfo requestInfo) {
		final Map<String, Object> equalsPredicate = buildPredicatesFromRequest(requestInfo.getQueryParameters());		
		return roleDataProvider.findAllByPredicates(equalsPredicate);
	}
}