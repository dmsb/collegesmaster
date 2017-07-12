package br.com.collegesmaster.business.imp;

import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.List;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import br.com.collegesmaster.business.IRoleBusiness;
import br.com.collegesmaster.model.IRole;
import br.com.collegesmaster.model.impl.Role;

@Stateless
@TransactionManagement(CONTAINER)
@DeclareRoles({"ADMINISTRATOR"})
@RolesAllowed({"ADMINISTRATOR"})
public class RoleBusiness extends GenericBusiness implements IRoleBusiness {
	
	@Override
	public void save(IRole profile) {
		entityManager.persist(profile);

	}

	@Override
	public IRole update(IRole profile) {
		return entityManager.merge(profile);
	}

	@Override
	public void remove(IRole profile) {
		entityManager.remove(profile);
	}
	
	@Override
	public IRole findById(Integer id) {
		return entityManager.find(Role.class, id);
	}
	
	@Override
	@PermitAll
	public List<Role> findAll() {		
		final CriteriaQuery<Role> criteriaQuery = builder.createQuery(Role.class);
		criteriaQuery.from(Role.class);
		final TypedQuery<Role> typedQuery = entityManager.createQuery(criteriaQuery);		
		final List<Role> result = typedQuery.getResultList(); 
		
		return result;
	}

}
