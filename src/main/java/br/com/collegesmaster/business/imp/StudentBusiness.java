package br.com.collegesmaster.business.imp;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.commons.collections.CollectionUtils;

import br.com.collegesmaster.business.IStudentBusiness;
import br.com.collegesmaster.model.IStudent;
import br.com.collegesmaster.model.imp.Student;

@Stateless
public class StudentBusiness extends Business implements IStudentBusiness {

	@Override
	public void persist(IStudent imodel) {
		entityManager.persist(imodel);

	}

	@Override
	public void merge(IStudent imodel) {
		entityManager.merge(imodel);

	}

	@Override
	public void remove(IStudent imodel) {
		entityManager.remove(imodel);

	}

	@Override
	public IStudent findById(Integer id, Class<IStudent> modelClass) {
		return entityManager.find(modelClass, id);
	}

	@Override
	public List<IStudent> getList() {
		final CriteriaQuery<Student> criteriaQuery = criteriaBuilder.createQuery(Student.class);
		final TypedQuery<Student> typedQuery = entityManager.createQuery(criteriaQuery);		
		final List<Student> result = typedQuery.getResultList(); 
		
		if(CollectionUtils.isEmpty(result)) {
			return null;
		} else {
			final List<IStudent> students = new ArrayList<IStudent>();
			result.forEach(student -> students.add(student));
			return students;
		}
	}

}
