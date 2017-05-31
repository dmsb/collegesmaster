package br.com.collegesmaster.business.imp;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.commons.collections.CollectionUtils;

import br.com.collegesmaster.business.IDisciplineBusiness;
import br.com.collegesmaster.model.IDiscipline;
import br.com.collegesmaster.model.imp.Discipline;

@Stateless
public class DisciplineBusiness extends Business implements IDisciplineBusiness {

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
	public IDiscipline findById(Integer id, Class<IDiscipline> modelClass) {
		return entityManager.find(modelClass, id);
	}

	@Override
	public List<IDiscipline> getList() {
		
		final CriteriaQuery<Discipline> criteriaQuery = criteriaBuilder.createQuery(Discipline.class);
		final TypedQuery<Discipline> typedQuery = entityManager.createQuery(criteriaQuery);		
		final List<Discipline> result = typedQuery.getResultList(); 
		
		if(CollectionUtils.isEmpty(result)) {
			return null;
		} else {
			final List<IDiscipline> disciplines = new ArrayList<IDiscipline>();
			result.forEach(discipline -> disciplines.add(discipline));			
			return disciplines;
		}
	}
}
