package br.com.collegesmaster.business.imp;

import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import br.com.collegesmaster.business.ICourseBusiness;
import br.com.collegesmaster.model.ICourse;
import br.com.collegesmaster.model.IInstitute;
import br.com.collegesmaster.model.impl.Course;

@Stateless
@TransactionManagement(CONTAINER)
@DeclareRoles({"STUDENT", "PROFESSOR", "ADMINISTRATOR"})
@RolesAllowed({"ADMINISTRATOR"})
public class CourseBusiness extends GenericBusiness implements ICourseBusiness {
	
	@Override
	public void save(ICourse course) {
		entityManager.persist(course);
	}

	@Override
	public ICourse update(ICourse course) {
		return entityManager.merge(course);	
	}

	@Override
	public void remove(ICourse course) {
		entityManager.remove(course);
	}

	@PermitAll
	@Override
	public ICourse findById(Integer id) {
		return entityManager.find(Course.class, id);
	}

	@Override
	public List<Course> findAll() {
		
		final CriteriaQuery<Course> criteriaQuery = builder.createQuery(Course.class);		
		criteriaQuery.from(Course.class);
		
		final TypedQuery<Course> typedQuery = entityManager.createQuery(criteriaQuery);
		final List<Course> result = typedQuery.getResultList(); 
		
		return result;
	}
	
	@PermitAll
	@Override
	public List<Course> findNamesByInstitute(final IInstitute institute) {
		
		final CriteriaQuery<Course> criteriaQuery = builder.createQuery(Course.class);
		final Root<Course> rootCourse = criteriaQuery.from(Course.class);
		
		final List<Selection<?>> idAndNameSelections = new ArrayList<Selection<?>>();
		idAndNameSelections.add(rootCourse.get("id"));
		idAndNameSelections.add(rootCourse.get("name"));
		
		criteriaQuery.multiselect(idAndNameSelections);
		
		final Predicate institutePredicate = builder.equal(rootCourse.get("institute"), institute);
		criteriaQuery.where(institutePredicate);
		
		final TypedQuery<Course> typedQuery = entityManager.createQuery(criteriaQuery);		
		final List<Course> result = typedQuery.getResultList();
		
		return result;
	}	
}
