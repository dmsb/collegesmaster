package br.com.collegesmaster.business.impl;

import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.jboss.ejb3.annotation.SecurityDomain;

import br.com.collegesmaster.annotation.qualifier.UserDatabase;
import br.com.collegesmaster.business.InstituteBusiness;
import br.com.collegesmaster.model.impl.InstituteImpl;
import br.com.collegesmaster.model.impl.InstituteImpl_;

@Stateless
@TransactionManagement(CONTAINER)
@RolesAllowed({"ADMINISTRATOR"})
@SecurityDomain("collegesmasterSecurityDomain")
public class InstituteBusinessImpl implements InstituteBusiness {
	
	@Inject @UserDatabase
	private EntityManager em;
	
	@Inject
	protected CriteriaBuilder cb;
	
	@Override
	public void create(InstituteImpl institute) {
		em.persist(institute);
		
	}

	@Override
	public InstituteImpl update(InstituteImpl institute) {
		return em.merge(institute);
		
	}

	@Override
	public void remove(InstituteImpl institute) {
		em.remove(institute);
		
	}

	@PermitAll
	@Override
	public InstituteImpl findById(Integer id) {
		return em.find(InstituteImpl.class, id);
	}
	
	@Override
	public List<InstituteImpl> findAll() {
		final CriteriaQuery<InstituteImpl> criteriaQuery = cb.createQuery(InstituteImpl.class);		
		criteriaQuery.from(InstituteImpl.class);
		
		final TypedQuery<InstituteImpl> typedQuery = em.createQuery(criteriaQuery);		
		
		return typedQuery.getResultList();
	}
	
	@PermitAll
	@TransactionAttribute(REQUIRED)
	@Override
	public List<InstituteImpl> findNames() {
		final CriteriaQuery<InstituteImpl> criteriaQuery = cb.createQuery(InstituteImpl.class);
		
		final Root<InstituteImpl> rootInstitute = criteriaQuery.from(InstituteImpl.class);
		
		final List<Selection<?>> selections = new ArrayList<>();
		selections.add(rootInstitute.get(InstituteImpl_.id));		
		selections.add(rootInstitute.get(InstituteImpl_.name));
		selections.add(rootInstitute.get(InstituteImpl_.version));
		criteriaQuery.multiselect(selections);

		final TypedQuery<InstituteImpl> typedQuery = em.createQuery(criteriaQuery); 
		
		return typedQuery.getResultList();
	}
	
	@PermitAll
	@TransactionAttribute(REQUIRED)
	@Override
	public List<InstituteImpl> findFetchingCourses() {
		final CriteriaQuery<InstituteImpl> criteriaQuery = cb.createQuery(InstituteImpl.class);
		final Root<InstituteImpl> instituteRoot = criteriaQuery.from(InstituteImpl.class);
				
		instituteRoot.fetch(InstituteImpl_.courses);
		criteriaQuery.select(instituteRoot).distinct(true);
		
		final TypedQuery<InstituteImpl> typedQuery = em.createQuery(criteriaQuery);		
		
		return typedQuery.getResultList();
	}
	
	@PermitAll
	@TransactionAttribute(REQUIRED)
	@Override
	public List<InstituteImpl> findAll(final Map<String, Object> equalsPredicate) {
		final CriteriaQuery<InstituteImpl> criteriaQuery = cb.createQuery(InstituteImpl.class);		
		final Root<InstituteImpl> instituteRoot = criteriaQuery.from(InstituteImpl.class);
		
		final List<Predicate> predicates = new ArrayList<>();
		equalsPredicate.forEach((key, value) -> {
			predicates.add(cb.equal(instituteRoot.get(key), value));
		});
		
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		
		final TypedQuery<InstituteImpl> typedQuery = em.createQuery(criteriaQuery);		
		
		return typedQuery.getResultList();
	}
}