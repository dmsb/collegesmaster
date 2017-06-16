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

import br.com.collegesmaster.business.IDisciplineBusiness;
import br.com.collegesmaster.model.ICourse;
import br.com.collegesmaster.model.IDiscipline;
import br.com.collegesmaster.model.imp.Discipline;
import br.com.collegesmaster.model.imp.Discipline_;

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
	public List<Discipline> findAll() {
		
		final CriteriaQuery<Discipline> criteriaQuery = criteriaBuilder.createQuery(Discipline.class);
		criteriaQuery.from(Discipline.class);
		final TypedQuery<Discipline> typedQuery = entityManager.createQuery(criteriaQuery);		
		final List<Discipline> result = typedQuery.getResultList(); 
		
		return result;
	}
	
	@Override
	public List<Discipline> findByCourse(final ICourse course) {
		
		final CriteriaQuery<Discipline> criteriaQuery = criteriaBuilder.createQuery(Discipline.class);
		final Root<Discipline> rootDiscipline = criteriaQuery.from(Discipline.class);
		
		final Predicate coursePredicate = criteriaBuilder.equal(rootDiscipline.get(Discipline_.course), course);
		rootDiscipline.fetch(Discipline_.challenges);
		
		criteriaQuery.select(rootDiscipline)
					 .distinct(true)
					 .where(coursePredicate);
		
		final TypedQuery<Discipline> typedQuery = entityManager.createQuery(criteriaQuery);		
		final List<Discipline> result = typedQuery.getResultList();
		
		return result;
	}
	
	@Override
	public List<Discipline> findDisciplineNamesByCourse(final ICourse course) {
		
		final CriteriaQuery<Discipline> criteriaQuery = criteriaBuilder.createQuery(Discipline.class);
		final Root<Discipline> rootDiscipline = criteriaQuery.from(Discipline.class);
		
		final List<Selection<?>> idAndNameSelections = new ArrayList<Selection<?>>();
		idAndNameSelections.add(rootDiscipline.get(Discipline_.id));
		idAndNameSelections.add(rootDiscipline.get(Discipline_.name));
		
		criteriaQuery.multiselect(idAndNameSelections);
		
		final Predicate coursePredicate = criteriaBuilder.equal(rootDiscipline.get(Discipline_.course), course);
		criteriaQuery.where(coursePredicate);
		
		final TypedQuery<Discipline> typedQuery = entityManager.createQuery(criteriaQuery);		
		final List<Discipline> result = typedQuery.getResultList();
		
		return result;
	}
}
