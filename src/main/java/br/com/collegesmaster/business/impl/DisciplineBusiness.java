package br.com.collegesmaster.business.impl;

import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.jboss.ejb3.annotation.SecurityDomain;

import br.com.collegesmaster.annotation.qualifier.UserDatabase;
import br.com.collegesmaster.business.IDisciplineBusiness;
import br.com.collegesmaster.model.ICourse;
import br.com.collegesmaster.model.IDiscipline;
import br.com.collegesmaster.model.impl.Discipline;
import br.com.collegesmaster.model.impl.Discipline_;

@Stateless
@TransactionManagement(CONTAINER)
@RolesAllowed({"ADMINISTRATOR"})
@SecurityDomain("collegesmasterSecurityDomain")
public class DisciplineBusiness implements IDisciplineBusiness {
	
	@Inject @UserDatabase
	private EntityManager em;
	
	@Inject
	protected CriteriaBuilder cb;
	
	@Override
	public void save(IDiscipline discipline) {
		em.persist(discipline);
	}

	@Override
	public IDiscipline update(IDiscipline discipline) {
		return em.merge(discipline);
	}

	@Override
	public void remove(IDiscipline discipline) {
		em.remove(discipline);
	}

	@Override
	@PermitAll
	public IDiscipline findById(Integer id) {
		return em.find(Discipline.class, id);
	}

	@Override
	public List<Discipline> findAllEnabledRolesToClients() {
		
		final CriteriaQuery<Discipline> criteriaQuery = cb.createQuery(Discipline.class);
		criteriaQuery.from(Discipline.class);
		final TypedQuery<Discipline> typedQuery = em.createQuery(criteriaQuery);		
		final List<Discipline> result = typedQuery.getResultList(); 
		
		return result;
	}
	
	@PermitAll
	@Override
	public List<Discipline> findByCourse(final ICourse course) {
		
		final CriteriaQuery<Discipline> criteriaQuery = cb.createQuery(Discipline.class);
		final Root<Discipline> rootDiscipline = criteriaQuery.from(Discipline.class);
		
		final Predicate coursePredicate = cb.equal(rootDiscipline.get(Discipline_.course), course);
		rootDiscipline.fetch(Discipline_.challenges);
		
		criteriaQuery.select(rootDiscipline)
					 .distinct(true)
					 .where(coursePredicate);
		
		final TypedQuery<Discipline> typedQuery = em.createQuery(criteriaQuery);		
		final List<Discipline> result = typedQuery.getResultList();
		
		return result;
	}
	
	@PermitAll
	@Override
	public List<Discipline> findNamesByCourse(final ICourse course) {
		
		final CriteriaQuery<Discipline> criteriaQuery = cb.createQuery(Discipline.class);
		final Root<Discipline> rootDiscipline = criteriaQuery.from(Discipline.class);
		
		final List<Selection<?>> selections = new ArrayList<Selection<?>>();
		selections.add(rootDiscipline.get(Discipline_.id));
		selections.add(rootDiscipline.get(Discipline_.name));
		selections.add(rootDiscipline.get(Discipline_.version));
		
		criteriaQuery.multiselect(selections);
		
		final Predicate coursePredicate = cb.equal(rootDiscipline.get(Discipline_.course), course);
		criteriaQuery.where(coursePredicate);
		
		final TypedQuery<Discipline> typedQuery = em.createQuery(criteriaQuery);		
		final List<Discipline> result = typedQuery.getResultList();
		
		return result;
	}
}
