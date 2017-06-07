package br.com.collegesmaster.business.imp;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.commons.collections.CollectionUtils;

import br.com.collegesmaster.business.ICourseBusiness;
import br.com.collegesmaster.model.ICourse;
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
	public ICourse findById(Integer id, Class<ICourse> modelClass) {
		return entityManager.find(modelClass, id);
	}

	@Override
	public List<ICourse> getList() {
		
		final CriteriaQuery<Course> criteriaQuery = criteriaBuilder.createQuery(Course.class);
		final TypedQuery<Course> typedQuery = entityManager.createQuery(criteriaQuery);		
		final List<Course> result = typedQuery.getResultList(); 
		
		if(CollectionUtils.isEmpty(result)) {
			return null;
		} else {
			final List<ICourse> courses = new ArrayList<ICourse>();
			result.forEach(course -> courses.add(course));
			return courses;
		}
	}

}
