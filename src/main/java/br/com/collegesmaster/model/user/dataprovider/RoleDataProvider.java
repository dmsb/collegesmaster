package br.com.collegesmaster.model.user.dataprovider;

import java.util.List;
import java.util.Map;

import br.com.collegesmaster.model.generics.BasicCrud;
import br.com.collegesmaster.model.user.impl.RoleImpl;

public interface RoleDataProvider extends BasicCrud<RoleImpl>{

	List<RoleImpl> findAllByPredicates(Map<String, Object> predicateMap);
}
