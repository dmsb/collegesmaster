package br.com.collegesmaster.business.imp;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import br.com.collegesmaster.business.ICourseBusiness;
import br.com.collegesmaster.model.ICourse;
import br.com.collegesmaster.model.IInstitute;
import br.com.collegesmaster.model.imp.Course;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class CourseBusiness extends GenericBusiness implements ICourseBusiness {
	
	@Override
	public void persist(ICourse course) {
		entityManager.persist(course);
	}

	@Override
	public void merge(ICourse course) {
		entityManager.merge(course);	
	}

	@Override
	public void remove(ICourse course) {
		entityManager.remove(course);
	}

	@Override
	public ICourse findById(Integer id, Class<Course> modelClass) {
		return entityManager.find(modelClass, id);
	}

	@Override
	public List<Course> findAll() {
		
		final CriteriaQuery<Course> criteriaQuery = criteriaBuilder.createQuery(Course.class);		
		criteriaQuery.from(Course.class);
		
		final TypedQuery<Course> typedQuery = entityManager.createQuery(criteriaQuery);
		final List<Course> result = typedQuery.getResultList(); 
		
		return result;
	}
		
	@Override
	public List<Course> findNamesByInstitute(final IInstitute institute) {
		
		final CriteriaQuery<Course> criteriaQuery = criteriaBuilder.createQuery(Course.class);
		final Root<Course> rootCourse = criteriaQuery.from(Course.class);
		
		final List<Selection<?>> idAndNameSelections = new ArrayList<Selection<?>>();
		idAndNameSelections.add(rootCourse.get("id"));
		idAndNameSelections.add(rootCourse.get("name"));
		
		criteriaQuery.multiselect(idAndNameSelections);
		
		final Predicate institutePredicate = criteriaBuilder.equal(rootCourse.get("institute"), institute);
		criteriaQuery.where(institutePredicate);
		
		final TypedQuery<Course> typedQuery = entityManager.createQuery(criteriaQuery);		
		final List<Course> result = typedQuery.getResultList();
		
		return result;
	}
	
	

}
