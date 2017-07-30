package br.com.collegesmaster.business.impl;

import static br.com.collegesmaster.model.impl.Institute_.courses;
import static br.com.collegesmaster.model.impl.Institute_.name;
import static br.com.collegesmaster.model.impl.Model_.id;
import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.ArrayList;
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
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.jboss.ejb3.annotation.SecurityDomain;

import br.com.collegesmaster.annotation.qualifier.UserDatabase;
import br.com.collegesmaster.business.IInstituteBusiness;
import br.com.collegesmaster.model.IInstitute;
import br.com.collegesmaster.model.impl.Institute;
import br.com.collegesmaster.model.impl.Institute_;

@Stateless
@TransactionManagement(CONTAINER)
@RolesAllowed({"ADMINISTRATOR"})
@SecurityDomain("collegesmasterSecurityDomain")
public class InstituteBusiness implements IInstituteBusiness {
	
	@Inject @UserDatabase
	private EntityManager em;
	
	@Inject
	protected CriteriaBuilder cb;
	
	@Override
	public void save(IInstitute institute) {
		em.persist(institute);
		
	}

	@Override
	public IInstitute update(IInstitute institute) {
		return em.merge(institute);
		
	}

	@Override
	public void remove(IInstitute institute) {
		em.remove(institute);
		
	}

	@PermitAll
	@Override
	public IInstitute findById(Integer id) {
		return em.find(Institute.class, id);
	}
	
	@Override
	public List<Institute> findAllEnabledRolesToClients() {
		final CriteriaQuery<Institute> criteriaQuery = cb.createQuery(Institute.class);		
		criteriaQuery.from(Institute.class);
		
		final TypedQuery<Institute> typedQuery = em.createQuery(criteriaQuery);		
		
		return typedQuery.getResultList();
	}
	
	@PermitAll
	@Override
	public List<Institute> findNames() {
		final CriteriaQuery<Institute> criteriaQuery = cb.createQuery(Institute.class);
		
		final Root<Institute> rootInstitute = criteriaQuery.from(Institute.class);
		
		final List<Selection<?>> selections = new ArrayList<>();
		selections.add(rootInstitute.get(id));		
		selections.add(rootInstitute.get(name));
		selections.add(rootInstitute.get(Institute_.version));
		criteriaQuery.multiselect(selections);

		final TypedQuery<Institute> typedQuery = em.createQuery(criteriaQuery); 
		
		return typedQuery.getResultList();
	}
	
	@PermitAll
	@Override
	public List<Institute> findFetchingCourses() {
		final CriteriaQuery<Institute> criteriaQuery = cb.createQuery(Institute.class);
		final Root<Institute> instituteRoot = criteriaQuery.from(Institute.class);
				
		instituteRoot.fetch(courses);
		criteriaQuery.select(instituteRoot).distinct(true);
		
		final TypedQuery<Institute> typedQuery = em.createQuery(criteriaQuery);		
		
		return typedQuery.getResultList();
	}
	
}