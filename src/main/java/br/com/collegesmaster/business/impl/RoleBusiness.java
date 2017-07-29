package br.com.collegesmaster.business.impl;

import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.jboss.ejb3.annotation.SecurityDomain;

import br.com.collegesmaster.annotations.qualifiers.UserDatabase;
import br.com.collegesmaster.business.IRoleBusiness;
import br.com.collegesmaster.model.IRole;
import br.com.collegesmaster.model.impl.Role;

@Stateless
@TransactionManagement(CONTAINER)
@RolesAllowed({"ADMINISTRATOR"})
@SecurityDomain("collegesmasterSecurityDomain")
public class RoleBusiness implements IRoleBusiness {
	
	@Inject @UserDatabase
	private EntityManager em;
	
	@Inject
	protected CriteriaBuilder cb;
	
	@Override
	public void save(IRole profile) {
		em.persist(profile);

	}

	@Override
	public IRole update(IRole profile) {
		return em.merge(profile);
	}

	@Override
	public void remove(IRole profile) {
		em.remove(profile);
	}
	
	@PermitAll
	@Override
	public IRole findById(Integer id) {
		return em.find(Role.class, id);
	}
	
	@PermitAll
	@Override
	public List<Role> findAll() {		
		final CriteriaQuery<Role> criteriaQuery = cb.createQuery(Role.class);
		criteriaQuery.from(Role.class);
		final TypedQuery<Role> typedQuery = em.createQuery(criteriaQuery);		
		final List<Role> result = typedQuery.getResultList(); 
		
		return result;
	}

}
