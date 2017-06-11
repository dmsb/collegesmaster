package br.com.collegesmaster.business.imp;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import br.com.collegesmaster.business.IInstituteBusiness;
import br.com.collegesmaster.model.IInstitute;
import br.com.collegesmaster.model.imp.Institute;

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
		final TypedQuery<Institute> typedQuery = entityManager.createQuery(criteriaQuery);
		final List<Institute> result = typedQuery.getResultList(); 
		
		return result;
	}
}