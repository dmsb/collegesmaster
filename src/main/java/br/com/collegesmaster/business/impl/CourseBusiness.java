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

import br.com.collegesmaster.annotations.qualifiers.UserDatabase;
import br.com.collegesmaster.business.ICourseBusiness;
import br.com.collegesmaster.model.ICourse;
import br.com.collegesmaster.model.IInstitute;
import br.com.collegesmaster.model.impl.Course;

@Stateless
@TransactionManagement(CONTAINER)
@RolesAllowed({"ADMINISTRATOR"})
@SecurityDomain("collegesmasterSecurityDomain")
public class CourseBusiness implements ICourseBusiness {
	
	@Inject @UserDatabase
	private EntityManager em;
	
	@Inject
	protected CriteriaBuilder cb;
	
	@Override
	public void save(ICourse course) {
		em.persist(course);
	}

	@Override
	public ICourse update(ICourse course) {
		return em.merge(course);	
	}

	@Override
	public void remove(ICourse course) {
		em.remove(course);
	}

	@PermitAll
	@Override
	public ICourse findById(Integer id) {
		return em.find(Course.class, id);
	}

	@Override
	public List<Course> findAll() {
		
		final CriteriaQuery<Course> criteriaQuery = cb.createQuery(Course.class);		
		criteriaQuery.from(Course.class);
		
		final TypedQuery<Course> typedQuery = em.createQuery(criteriaQuery);
		final List<Course> result = typedQuery.getResultList(); 
		
		return result;
	}
	
	@PermitAll
	@Override
	public List<Course> findNamesByInstitute(final IInstitute institute) {
		
		final CriteriaQuery<Course> criteriaQuery = cb.createQuery(Course.class);
		final Root<Course> rootCourse = criteriaQuery.from(Course.class);
		
		final List<Selection<?>> selections = new ArrayList<Selection<?>>();
		selections.add(rootCourse.get("id"));
		selections.add(rootCourse.get("name"));
		selections.add(rootCourse.get("version"));
		
		criteriaQuery.multiselect(selections);
		
		final Predicate institutePredicate = cb.equal(rootCourse.get("institute"), institute);
		criteriaQuery.where(institutePredicate);
		
		final TypedQuery<Course> typedQuery = em.createQuery(criteriaQuery);		
		final List<Course> result = typedQuery.getResultList();
		
		return result;
	}	
}
