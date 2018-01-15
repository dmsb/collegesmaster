package br.com.collegesmaster.model.security.dataprovider;

import java.util.List;
import java.util.Map;

import br.com.collegesmaster.model.security.impl.RoleImpl;

public interface RoleDataProvider {

	List<RoleImpl> findAllByPredicates(Map<String, Object> predicateMap);
	
	List<RoleImpl> findAllElegibleRoles();
}
