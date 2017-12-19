package br.com.collegesmaster.model.user.business;

import java.util.List;

import javax.ws.rs.core.UriInfo;

import br.com.collegesmaster.model.generics.BasicCrud;
import br.com.collegesmaster.model.user.impl.RoleImpl;

public interface RoleBusiness extends BasicCrud<RoleImpl> {
	
	List<RoleImpl> findAllByPredicates(UriInfo requestInfo);
	
}
