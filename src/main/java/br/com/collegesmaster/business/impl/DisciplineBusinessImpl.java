package br.com.collegesmaster.business.impl;

import static br.com.collegesmaster.rest.util.RestUtils.buildPredicatesFromRequest;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.ws.rs.core.UriInfo;

import org.jboss.ejb3.annotation.SecurityDomain;
import org.jboss.logging.Logger;

import br.com.collegesmaster.annotation.qualifier.UserDatabase;
import br.com.collegesmaster.business.DisciplineBusiness;
import br.com.collegesmaster.model.Course;
import br.com.collegesmaster.model.impl.DisciplineImpl;
import br.com.collegesmaster.model.impl.DisciplineImpl_;

@Stateless
@TransactionManagement(CONTAINER)
@RolesAllowed({"ADMINISTRATOR"})
@SecurityDomain("collegesmasterSecurityDomain")
public class DisciplineBusinessImpl implements DisciplineBusiness {
	
	@Inject
	private Logger LOGGER;
	
	@Inject @UserDatabase
	private EntityManager em;
	
	@Inject
	protected CriteriaBuilder cb;
	
	@TransactionAttribute(REQUIRED)
	@Override
	public Boolean create(DisciplineImpl discipline) {
		if(discipline != null && discipline.getId() == null && discipline.getVersion() == null) {
			em.persist(discipline);
			return TRUE;
		} else {
			LOGGER.warn("Entity not persisted, invalid arguments");
			return FALSE;			
		}
	}

	@TransactionAttribute(REQUIRED)
	@Override
	public DisciplineImpl update(DisciplineImpl discipline) {
		if(discipline != null && discipline.getId() != null && discipline.getVersion() != null) {
			return em.merge(discipline);
		} else {
			LOGGER.warn("Entity not persisted, invalid arguments");
			return null;
		}
	}

	@TransactionAttribute(REQUIRED)
	@Override
	public Boolean remove(DisciplineImpl discipline) {
		if(discipline != null && discipline.getId() != null && discipline.getVersion() != null) {
			em.remove(discipline);				
			return TRUE;
		} else {
			LOGGER.warn("Entity not removed, invalid arguments");
			return FALSE;
		}
	}
	
	@PermitAll
	@Override
	public DisciplineImpl findById(Integer id) {
		if(id != null) {
			return em.find(DisciplineImpl.class, id);			
		} else {
			LOGGER.warn("Cannot find entity, invalid arguments");
			return null;
		}
	}
	
	@TransactionAttribute(REQUIRED)
	@Override
	public List<DisciplineImpl> findAll() {
		
		final CriteriaQuery<DisciplineImpl> criteriaQuery = cb.createQuery(DisciplineImpl.class);
		criteriaQuery.from(DisciplineImpl.class);
		final TypedQuery<DisciplineImpl> typedQuery = em.createQuery(criteriaQuery);		
		final List<DisciplineImpl> result = typedQuery.getResultList(); 
		
		return result;
	}
	
	@PermitAll
	@TransactionAttribute(REQUIRED)
	@Override
	public List<DisciplineImpl> findAll(final UriInfo requestInfo) {
		
		final Map<String, Object> equalsPredicate = buildPredicatesFromRequest(requestInfo);
		
		final CriteriaQuery<DisciplineImpl> criteriaQuery = cb.createQuery(DisciplineImpl.class);		
		final Root<DisciplineImpl> instituteRoot = criteriaQuery.from(DisciplineImpl.class);
		
		final List<Predicate> predicates = new ArrayList<>();
		equalsPredicate.forEach((key, value) -> {
			predicates.add(cb.equal(instituteRoot.get(key), value));
		});

		criteriaQuery.where(predicates.toArray(new Predicate[0]));

		final TypedQuery<DisciplineImpl> typedQuery = em.createQuery(criteriaQuery);		
		
		return typedQuery.getResultList();
	}
	
	@PermitAll
	@Override
	public List<DisciplineImpl> findByCourse(final Course course) {
		
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
	
	@PermitAll
	@Override
	public List<DisciplineImpl> findNamesByCourse(final Course course) {
		
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
}
