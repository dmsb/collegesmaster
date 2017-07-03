package br.com.collegesmaster.business.imp;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import br.com.collegesmaster.business.IProfileBusiness;
import br.com.collegesmaster.model.IRole;
import br.com.collegesmaster.model.impl.Role;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ProfileBusiness extends GenericBusiness implements IProfileBusiness {
	
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
	public List<Role> findAll() {		
		final CriteriaQuery<Role> criteriaQuery = builder.createQuery(Role.class);
		criteriaQuery.from(Role.class);
		final TypedQuery<Role> typedQuery = entityManager.createQuery(criteriaQuery);		
		final List<Role> result = typedQuery.getResultList(); 
		
		return result;
	}

}
