package br.com.collegesmaster.business.impl;

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
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.jboss.ejb3.annotation.SecurityDomain;

import br.com.collegesmaster.annotation.qualifier.UserDatabase;
import br.com.collegesmaster.business.CourseBusiness;
import br.com.collegesmaster.model.Institute;
import br.com.collegesmaster.model.impl.CourseImpl;

@Stateless
@TransactionManagement(CONTAINER)
@RolesAllowed({"ADMINISTRATOR"})
@SecurityDomain("collegesmasterSecurityDomain")
public class CourseBusinessImpl implements CourseBusiness {
	
	@Inject @UserDatabase
	private EntityManager em;
	
	@Inject
	protected CriteriaBuilder cb;
	
	@Override
	public void create(CourseImpl course) {
		em.persist(course);
	}

	@Override
	public CourseImpl update(CourseImpl course) {
		return em.merge(course);	
	}

	@Override
	public void remove(CourseImpl course) {
		em.remove(course);
	}

	@PermitAll
	@Override
	public CourseImpl findById(Integer id) {
		return em.find(CourseImpl.class, id);
	}

	@Override
	public List<CourseImpl> findAll() {
		
		final CriteriaQuery<CourseImpl> criteriaQuery = cb.createQuery(CourseImpl.class);		
		criteriaQuery.from(CourseImpl.class);
		
		final TypedQuery<CourseImpl> typedQuery = em.createQuery(criteriaQuery);
		final List<CourseImpl> result = typedQuery.getResultList();
		
		return result;
	}
	
	@PermitAll
	@Override
	public List<CourseImpl> findNamesByInstitute(final Institute institute) {
		
		final CriteriaQuery<CourseImpl> criteriaQuery = cb.createQuery(CourseImpl.class);
		final Root<CourseImpl> rootCourse = criteriaQuery.from(CourseImpl.class);
		
		final List<Selection<?>> selections = new ArrayList<Selection<?>>();
		selections.add(rootCourse.get("id"));
		selections.add(rootCourse.get("name"));
		selections.add(rootCourse.get("version"));
		
		criteriaQuery.multiselect(selections);
		
		final Predicate institutePredicate = cb.equal(rootCourse.get("institute"), institute);
		criteriaQuery.where(institutePredicate);
		
		final TypedQuery<CourseImpl> typedQuery = em.createQuery(criteriaQuery);		
		final List<CourseImpl> result = typedQuery.getResultList();
		
		return result;
	}	
}
