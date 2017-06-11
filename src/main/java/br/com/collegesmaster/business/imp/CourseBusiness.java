package br.com.collegesmaster.business.imp;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

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
	public ICourse findById(Integer id, Class<Course> modelClass) {
		return entityManager.find(modelClass, id);
	}

	@Override
	public List<Course> getList() {
		
		final CriteriaQuery<Course> criteriaQuery = criteriaBuilder.createQuery(Course.class);
		final TypedQuery<Course> typedQuery = entityManager.createQuery(criteriaQuery);		
		final List<Course> result = typedQuery.getResultList(); 
		return result;
	}

}
