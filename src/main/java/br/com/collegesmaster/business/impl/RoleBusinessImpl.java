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
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.jboss.ejb3.annotation.SecurityDomain;

import br.com.collegesmaster.annotation.qualifier.UserDatabase;
import br.com.collegesmaster.business.RoleBusiness;
import br.com.collegesmaster.model.impl.RoleImpl;
import br.com.collegesmaster.model.impl.RoleImpl_;

@Stateless
@TransactionManagement(CONTAINER)
@RolesAllowed({"ADMINISTRATOR"})
@SecurityDomain("collegesmasterSecurityDomain")
public class RoleBusinessImpl implements RoleBusiness {
	
	@Inject @UserDatabase
	private EntityManager em;
	
	@Inject
	protected CriteriaBuilder cb;
	
	@Override
	public void create(RoleImpl profile) {
		em.persist(profile);

	}

	@Override
	public RoleImpl update(RoleImpl profile) {
		return em.merge(profile);
	}

	@Override
	public void remove(RoleImpl profile) {
		em.remove(profile);
	}
	
	@PermitAll
	@Override
	public RoleImpl findById(Integer id) {
		return em.find(RoleImpl.class, id);
	}
	
	@PermitAll
	@Override
	public List<RoleImpl> findAll() {		
		final CriteriaQuery<RoleImpl> criteriaQuery = cb.createQuery(RoleImpl.class);
		Root<RoleImpl> rootRole = criteriaQuery.from(RoleImpl.class);

		final Predicate exceptAdmRole = cb.notEqual(rootRole.get(RoleImpl_.name), "ADMINISTRATOR");
		criteriaQuery.where(exceptAdmRole);
		
		final TypedQuery<RoleImpl> typedQuery = em.createQuery(criteriaQuery);
		final List<RoleImpl> result = typedQuery.getResultList();
		
		return result;
	}

}
