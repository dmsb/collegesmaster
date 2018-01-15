package br.com.collegesmaster.model.security.business;

import java.util.List;

import javax.ws.rs.core.UriInfo;

import br.com.collegesmaster.model.generics.GenericBusiness;
import br.com.collegesmaster.model.security.impl.RoleImpl;

public interface RoleBusiness extends GenericBusiness<RoleImpl> {
	
	RoleImpl findById(final Integer id);
	
	List<RoleImpl> findAllByPredicates(UriInfo requestInfo);
	
	List<RoleImpl> findAll();
}
