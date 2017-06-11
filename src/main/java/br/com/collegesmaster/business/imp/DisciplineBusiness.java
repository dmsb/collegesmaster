package br.com.collegesmaster.business.imp;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import br.com.collegesmaster.business.IDisciplineBusiness;
import br.com.collegesmaster.model.IDiscipline;
import br.com.collegesmaster.model.imp.Discipline;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DisciplineBusiness extends GenericBusiness implements IDisciplineBusiness {
	
	@Override
	public void persist(IDiscipline discipline) {
		entityManager.persist(discipline);
	}

	@Override
	public void merge(IDiscipline discipline) {
		entityManager.merge(discipline);
	}

	@Override
	public void remove(IDiscipline discipline) {
		entityManager.remove(discipline);
	}

	@Override
	public IDiscipline findById(Integer id, Class<Discipline> modelClass) {
		return entityManager.find(modelClass, id);
	}

	@Override
	public List<Discipline> getList() {
		
		final CriteriaQuery<Discipline> criteriaQuery = criteriaBuilder.createQuery(Discipline.class);
		final TypedQuery<Discipline> typedQuery = entityManager.createQuery(criteriaQuery);		
		final List<Discipline> result = typedQuery.getResultList(); 
		
		return result;
	}
}
