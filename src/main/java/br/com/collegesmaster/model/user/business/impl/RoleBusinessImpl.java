package br.com.collegesmaster.model.user.business.impl;

import static br.com.collegesmaster.rest.utils.RestUtils.buildPredicatesFromRequest;
import static java.lang.Boolean.FALSE;
import static javax.ejb.TransactionAttributeType.NOT_SUPPORTED;
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
import org.jboss.logging.Logger;

import br.com.collegesmaster.model.user.business.RoleBusiness;
import br.com.collegesmaster.model.user.dataprovider.RoleDataProvider;
import br.com.collegesmaster.model.user.impl.RoleImpl;

@Stateless
@TransactionManagement(CONTAINER)
@RolesAllowed({"ADMINISTRATOR"})
@SecurityDomain("collegesmasterSecurityDomain")
public class RoleBusinessImpl implements RoleBusiness {
	
	@Inject
	private Logger LOGGER;
	
	@Inject
	private RoleDataProvider roleDataProvider;
	
	@TransactionAttribute(NOT_SUPPORTED)
	@Override
	public Boolean create(RoleImpl role) {
		if(role != null && role.isNew()) {
			return roleDataProvider.create(role);
		} else {
			LOGGER.warn("Entity not persisted, invalid arguments");
			return FALSE;			
		}
	}

	@TransactionAttribute(NOT_SUPPORTED)
	@Override
	public RoleImpl update(RoleImpl role) {
		if(role != null && Boolean.FALSE.equals(role.isNew())) {
			return roleDataProvider.update(role);
		} else {
			LOGGER.warn("Entity not persisted, invalid arguments");
			return null;
		}
	}

	@TransactionAttribute(NOT_SUPPORTED)
	@Override
	public Boolean remove(RoleImpl role) {
		if(role != null && Boolean.FALSE.equals(role.isNew())) {
			return roleDataProvider.remove(role);
		} else {
			LOGGER.warn("Entity not removed, invalid arguments");
			return FALSE;
		}
	}
	
	@PermitAll
	@TransactionAttribute(NOT_SUPPORTED)
	@Override
	public RoleImpl findById(Integer id) {
		if(id != null) {
			return roleDataProvider.findById(id);			
		} else {
			LOGGER.warn("Cannot find entity, invalid arguments");
			return null;
		}
	}
	
	@PermitAll
	@TransactionAttribute(NOT_SUPPORTED)
	@Override
	public List<RoleImpl> findAll() {		
		return roleDataProvider.findAll();
	}
	
	@PermitAll
	@TransactionAttribute(NOT_SUPPORTED)
	@Override
	public List<RoleImpl> findAllByPredicates(final UriInfo requestInfo) {
		final Map<String, Object> equalsPredicate = buildPredicatesFromRequest(requestInfo.getQueryParameters());		
		return roleDataProvider.findAllByPredicates(equalsPredicate);
	}
}