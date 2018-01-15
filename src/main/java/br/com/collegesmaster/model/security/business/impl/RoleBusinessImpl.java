package br.com.collegesmaster.model.security.business.impl;

import static br.com.collegesmaster.rest.utils.RestUtils.buildPredicatesFromRequest;
import static javax.ejb.TransactionAttributeType.NEVER;
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
import br.com.collegesmaster.model.security.business.RoleBusiness;
import br.com.collegesmaster.model.security.dataprovider.RoleDataProvider;
import br.com.collegesmaster.model.security.impl.RoleImpl;

@Stateless
@TransactionManagement(CONTAINER)
@RolesAllowed({"ADMINISTRATOR"})
@SecurityDomain("collegesmasterSecurityDomain")
public class RoleBusinessImpl extends GenericBusinessImpl<RoleImpl> implements RoleBusiness {
	
	@Inject
	private RoleDataProvider roleDataProvider;
	
	@TransactionAttribute(NEVER)
	@Override
	public Boolean create(RoleImpl role) {
		return super.create(role);
	}

	@TransactionAttribute(NEVER)
	@Override
	public RoleImpl update(RoleImpl role) {
		return super.update(role);
	}

	@TransactionAttribute(NEVER)
	@Override
	public Boolean remove(RoleImpl role) {
		return super.remove(role);
	}
	
	@PermitAll
	@TransactionAttribute(NEVER)
	@Override
	public RoleImpl findById(Integer id) {
		return super.findById(RoleImpl.class, id);
	}
	
	@PermitAll
	@TransactionAttribute(NEVER)	
	@Override
	public List<RoleImpl> findAll() {
		return roleDataProvider.findAllElegibleRoles();
	}
	
	@PermitAll
	@TransactionAttribute(NEVER)
	@Override
	public List<RoleImpl> findAllByPredicates(final UriInfo requestInfo) {
		final Map<String, Object> equalsPredicate = buildPredicatesFromRequest(requestInfo.getQueryParameters());		
		return roleDataProvider.findAllByPredicates(equalsPredicate);
	}
}