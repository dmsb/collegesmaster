package br.com.collegesmaster.business.impl;

import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.jboss.ejb3.annotation.SecurityDomain;

import br.com.collegesmaster.business.IDisciplineBusiness;
import br.com.collegesmaster.model.ICourse;
import br.com.collegesmaster.model.IDiscipline;
import br.com.collegesmaster.model.impl.Discipline;
import br.com.collegesmaster.model.impl.Discipline_;

@Stateless
@TransactionManagement(CONTAINER)
@RolesAllowed({"ADMINISTRATOR"})
@SecurityDomain("collegesmasterSecurityDomain")
public class DisciplineBusiness extends GenericBusiness implements IDisciplineBusiness {
	
	@Override
	public void save(IDiscipline discipline) {
		entityManager.persist(discipline);
	}

	@Override
	public IDiscipline update(IDiscipline discipline) {
		return entityManager.merge(discipline);
	}

	@Override
	public void remove(IDiscipline discipline) {
		entityManager.remove(discipline);
	}

	@Override
	@PermitAll
	public IDiscipline findById(Integer id) {
		return entityManager.find(Discipline.class, id);
	}

	@Override
	public List<Discipline> findAll() {
		
		final CriteriaQuery<Discipline> criteriaQuery = builder.createQuery(Discipline.class);
		criteriaQuery.from(Discipline.class);
		final TypedQuery<Discipline> typedQuery = entityManager.createQuery(criteriaQuery);		
		final List<Discipline> result = typedQuery.getResultList(); 
		
		return result;
	}
	
	@PermitAll
	@Override
	public List<Discipline> findByCourse(final ICourse course) {
		
		final CriteriaQuery<Discipline> criteriaQuery = builder.createQuery(Discipline.class);
		final Root<Discipline> rootDiscipline = criteriaQuery.from(Discipline.class);
		
		final Predicate coursePredicate = builder.equal(rootDiscipline.get(Discipline_.course), course);
		rootDiscipline.fetch(Discipline_.challenges);
		
		criteriaQuery.select(rootDiscipline)
					 .distinct(true)
					 .where(coursePredicate);
		
		final TypedQuery<Discipline> typedQuery = entityManager.createQuery(criteriaQuery);		
		final List<Discipline> result = typedQuery.getResultList();
		
		return result;
	}
	
	@PermitAll
	@Override
	public List<Discipline> findNamesByCourse(final ICourse course) {
		
		final CriteriaQuery<Discipline> criteriaQuery = builder.createQuery(Discipline.class);
		final Root<Discipline> rootDiscipline = criteriaQuery.from(Discipline.class);
		
		final List<Selection<?>> selections = new ArrayList<Selection<?>>();
		selections.add(rootDiscipline.get(Discipline_.id));
		selections.add(rootDiscipline.get(Discipline_.name));
		selections.add(rootDiscipline.get(Discipline_.version));
		
		criteriaQuery.multiselect(selections);
		
		final Predicate coursePredicate = builder.equal(rootDiscipline.get(Discipline_.course), course);
		criteriaQuery.where(coursePredicate);
		
		final TypedQuery<Discipline> typedQuery = entityManager.createQuery(criteriaQuery);		
		final List<Discipline> result = typedQuery.getResultList();
		
		return result;
	}
}
