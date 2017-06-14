package br.com.collegesmaster.business.imp;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import br.com.collegesmaster.business.IInstituteBusiness;
import br.com.collegesmaster.model.IInstitute;
import br.com.collegesmaster.model.imp.Institute;
import br.com.collegesmaster.model.imp.Institute_;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class InstituteBusiness extends GenericBusiness implements IInstituteBusiness {

	@Override
	public void persist(IInstitute institute) {
		entityManager.persist(institute);
		
	}

	@Override
	public void merge(IInstitute institute) {
		entityManager.merge(institute);
		
	}

	@Override
	public void remove(IInstitute institute) {
		entityManager.remove(institute);
		
	}

	@Override
	public IInstitute findById(Integer id, Class<Institute> modelClass) {
		return entityManager.find(modelClass, id);
	}
	
	@Override
	public List<Institute> getList() {
		final CriteriaQuery<Institute> criteriaQuery = criteriaBuilder.createQuery(Institute.class);		
		criteriaQuery.from(Institute.class);
		
		final TypedQuery<Institute> typedQuery = entityManager.createQuery(criteriaQuery);
		final List<Institute> result = typedQuery.getResultList(); 
		
		return result;
	}
	
	@Override
	public List<Institute> getInstituteNames() {
		final CriteriaQuery<Institute> criteriaQuery = criteriaBuilder.createQuery(Institute.class);
		
		final Root<Institute> rootInstitute = criteriaQuery.from(Institute.class);
		
		final List<Selection<?>> idAndNameSelections = new ArrayList<Selection<?>>();
		idAndNameSelections.add(rootInstitute.get(Institute_.id));
		idAndNameSelections.add(rootInstitute.get(Institute_.name));
		
		criteriaQuery.multiselect(idAndNameSelections);

		final TypedQuery<Institute> typedQuery = entityManager.createQuery(criteriaQuery);
		final List<Institute> result = typedQuery.getResultList(); 
		
		return result;
	}
	
	@Override
	public List<Institute> getInstituteFetchingCourses() {
		final CriteriaQuery<Institute> criteriaQuery = criteriaBuilder.createQuery(Institute.class);
		final Root<Institute> instituteRoot = criteriaQuery.from(Institute.class);
				
		instituteRoot.fetch(Institute_.courses);
		criteriaQuery.select(instituteRoot).distinct(true);
		//"If DISTINCT is not specified, duplicate values are not eliminated." (section 4.8 of JPA v2.0)
		
		final TypedQuery<Institute> typedQuery = entityManager.createQuery(criteriaQuery);
		final List<Institute> result = typedQuery.getResultList(); 
		
		return result;
	}
	
}