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
import br.com.collegesmaster.business.CourseBusiness;
import br.com.collegesmaster.model.Institute;
import br.com.collegesmaster.model.impl.CourseImpl;

@Stateless
@TransactionManagement(CONTAINER)
@RolesAllowed({"ADMINISTRATOR"})
@SecurityDomain("collegesmasterSecurityDomain")
public class CourseBusinessImpl implements CourseBusiness {
	
	@Inject
	private Logger LOGGER;
	
	@Inject @UserDatabase
	private EntityManager em;
	
	@Inject
	protected CriteriaBuilder cb;
	
	@TransactionAttribute(REQUIRED)
	@Override
	public Boolean create(CourseImpl course) {
		if(course != null && course.getId() == null && course.getVersion() == null) {
			em.persist(course);
			return TRUE;
		} else {
			LOGGER.warn("Entity not persisted, invalid arguments");
			return FALSE;			
		}
	}

	@TransactionAttribute(REQUIRED)
	@Override
	public CourseImpl update(CourseImpl course) {
		if(course != null && course.getId() != null && course.getVersion() != null) {
			return em.merge(course);
		} else {
			LOGGER.warn("Entity not persisted, invalid arguments");
			return null;
		}
	}

	@TransactionAttribute(REQUIRED)
	@Override
	public Boolean remove(CourseImpl course) {
		if(course != null && course.getId() != null && course.getVersion() != null) {
			em.remove(course);				
			return TRUE;
		} else {
			LOGGER.warn("Entity not removed, invalid arguments");
			return FALSE;
		}
	}

	@PermitAll
	@TransactionAttribute(REQUIRED)
	@Override
	public CourseImpl findById(Integer id) {
		if(id != null) {
			return em.find(CourseImpl.class, id);			
		} else {
			LOGGER.warn("Cannot find entity, invalid arguments");
			return null;
		}
	}
	
	@TransactionAttribute(REQUIRED)
	@Override
	public List<CourseImpl> findAll() {
		
		final CriteriaQuery<CourseImpl> criteriaQuery = cb.createQuery(CourseImpl.class);		
		criteriaQuery.from(CourseImpl.class);
		
		final TypedQuery<CourseImpl> typedQuery = em.createQuery(criteriaQuery);
		final List<CourseImpl> result = typedQuery.getResultList();
		
		return result;
	}
	
	@PermitAll
	@TransactionAttribute(REQUIRED)
	@Override
	public List<CourseImpl> findAllByPredicates(final UriInfo requestInfo) {
		
		final Map<String, Object> equalsPredicate = buildPredicatesFromRequest(requestInfo.getQueryParameters());
		
		final CriteriaQuery<CourseImpl> criteriaQuery = cb.createQuery(CourseImpl.class);		
		final Root<CourseImpl> instituteRoot = criteriaQuery.from(CourseImpl.class);
		
		final List<Predicate> predicates = new ArrayList<>();
		equalsPredicate.forEach((key, value) -> {
			predicates.add(cb.equal(instituteRoot.get(key), value));
		});

		criteriaQuery.where(predicates.toArray(new Predicate[0]));

		final TypedQuery<CourseImpl> typedQuery = em.createQuery(criteriaQuery);		
		
		return typedQuery.getResultList();
	}
	
	@PermitAll
	@Override
	public List<CourseImpl> findNamesByInstitute(final Institute institute) {
		
		final CriteriaQuery<CourseImpl> criteriaQuery = cb.createQuery(CourseImpl.class);
		final Root<CourseImpl> rootCourse = criteriaQuery.from(CourseImpl.class);
		
		final List<Selection<?>> selections = new ArrayList<Selection<?>>();
		selections.add(rootCourse.get("id"));
		selections.add(rootCourse.get("name"));
		selections.add(rootCourse.get("version"));
		
		criteriaQuery.multiselect(selections);
		
		final Predicate institutePredicate = cb.equal(rootCourse.get("institute"), institute);
		criteriaQuery.where(institutePredicate);
		
		final TypedQuery<CourseImpl> typedQuery = em.createQuery(criteriaQuery);		
		final List<CourseImpl> result = typedQuery.getResultList();
		
		return result;
	}	
}
