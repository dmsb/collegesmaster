package br.com.collegesmaster.model.security.business;

import java.util.List;

import javax.ws.rs.core.UriInfo;

import br.com.collegesmaster.model.generics.GenericCRUD;
import br.com.collegesmaster.model.security.Role;
import br.com.collegesmaster.model.security.impl.RoleImpl;

public interface RoleBusiness extends GenericCRUD<Role> {
	
	Role findById(final Integer id);
	
	List<RoleImpl> findAllByPredicates(UriInfo requestInfo);
	
	List<RoleImpl> findAll();
}
