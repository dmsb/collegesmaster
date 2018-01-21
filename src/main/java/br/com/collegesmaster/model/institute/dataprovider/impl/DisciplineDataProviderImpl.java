package br.com.collegesmaster.model.institute.dataprovider.impl;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import br.com.collegesmaster.model.institute.Course;
import br.com.collegesmaster.model.institute.dataprovider.DisciplineDataProvider;
import br.com.collegesmaster.model.institute.impl.DisciplineImpl;
import br.com.collegesmaster.model.institute.impl.DisciplineImpl_;
import br.com.collegesmaster.qualifiers.UserDatabase;

@Dependent
public class DisciplineDataProviderImpl implements DisciplineDataProvider{

	@Inject @UserDatabase
	private EntityManager em;
	
	@Inject
	protected CriteriaBuilder cb;
	
	@Override
	public List<DisciplineImpl> findNamesByCourse(Course course) {
		final CriteriaQuery<DisciplineImpl> criteriaQuery = cb.createQuery(DisciplineImpl.class);
		final Root<DisciplineImpl> rootDiscipline = criteriaQuery.from(DisciplineImpl.class);
		
		final List<Selection<?>> selections = new ArrayList<Selection<?>>();
		selections.add(rootDiscipline.get(DisciplineImpl_.id));
		selections.add(rootDiscipline.get(DisciplineImpl_.name));
		selections.add(rootDiscipline.get(DisciplineImpl_.version));
		
		criteriaQuery.multiselect(selections);
		
		final Predicate coursePredicate = cb.equal(rootDiscipline.get(DisciplineImpl_.course), course);
		criteriaQuery.where(coursePredicate);
		
		final TypedQuery<DisciplineImpl> typedQuery = em.createQuery(criteriaQuery);		
		final List<DisciplineImpl> result = typedQuery.getResultList();
		
		return result;
	}
	
	@Override
	public List<DisciplineImpl> findByCourse(Course course) {
		final CriteriaQuery<DisciplineImpl> criteriaQuery = cb.createQuery(DisciplineImpl.class);
		final Root<DisciplineImpl> rootDiscipline = criteriaQuery.from(DisciplineImpl.class);
		
		final Predicate coursePredicate = cb.equal(rootDiscipline.get(DisciplineImpl_.course), course);
		rootDiscipline.fetch(DisciplineImpl_.challenges);
		
		criteriaQuery.select(rootDiscipline)
					 .distinct(true)
					 .where(coursePredicate);
		
		final TypedQuery<DisciplineImpl> typedQuery = em.createQuery(criteriaQuery);		
		final List<DisciplineImpl> result = typedQuery.getResultList();
		
		return result;
	}
}
