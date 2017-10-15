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
import javax.ws.rs.core.UriInfo;

import org.jboss.ejb3.annotation.SecurityDomain;
import org.jboss.logging.Logger;

import br.com.collegesmaster.annotation.qualifier.UserDatabase;
import br.com.collegesmaster.business.RoleBusiness;
import br.com.collegesmaster.model.impl.RoleImpl;
import br.com.collegesmaster.model.impl.RoleImpl_;

@Stateless
@TransactionManagement(CONTAINER)
@RolesAllowed({"ADMINISTRATOR"})
@SecurityDomain("collegesmasterSecurityDomain")
public class RoleBusinessImpl implements RoleBusiness {
	
	@Inject
	private Logger LOGGER;
	
	@Inject @UserDatabase
	private EntityManager em;
	
	@Inject
	protected CriteriaBuilder cb;
	
	@TransactionAttribute(REQUIRED)
	@Override
	public Boolean create(RoleImpl role) {
		if(role != null && role.getId() == null && role.getVersion() == null) {
			em.persist(role);
			return TRUE;
		} else {
			LOGGER.warn("Entity not persisted, invalid arguments");
			return FALSE;			
		}
	}

	@TransactionAttribute(REQUIRED)
	@Override
	public RoleImpl update(RoleImpl role) {
		if(role != null && role.getId() != null && role.getVersion() != null) {
			return em.merge(role);
		} else {
			LOGGER.warn("Entity not persisted, invalid arguments");
			return null;
		}
	}

	@TransactionAttribute(REQUIRED)
	@Override
	public Boolean remove(RoleImpl role) {
		if(role != null && role.getId() != null && role.getVersion() != null) {
			em.remove(role);				
			return TRUE;
		} else {
			LOGGER.warn("Entity not removed, invalid arguments");
			return FALSE;
		}
	}
	
	@PermitAll
	@TransactionAttribute(REQUIRED)
	@Override
	public RoleImpl findById(Integer id) {
		if(id != null) {
			return em.find(RoleImpl.class, id);			
		} else {
			LOGGER.warn("Cannot find entity, invalid arguments");
			return null;
		}
	}
	
	@PermitAll
	@TransactionAttribute(REQUIRED)
	@Override
	public List<RoleImpl> findAll() {		
		final CriteriaQuery<RoleImpl> criteriaQuery = cb.createQuery(RoleImpl.class);
		Root<RoleImpl> rootRole = criteriaQuery.from(RoleImpl.class);

		final Predicate exceptAdmRole = cb.notEqual(rootRole.get(RoleImpl_.name), "ADMINISTRATOR");
		criteriaQuery.where(exceptAdmRole);
		
		final TypedQuery<RoleImpl> typedQuery = em.createQuery(criteriaQuery);
		final List<RoleImpl> result = typedQuery.getResultList();
		
		return result;
	}
	
	@PermitAll
	@TransactionAttribute(REQUIRED)
	@Override
	public List<RoleImpl> findAllByPredicates(final UriInfo requestInfo) {
		
		final Map<String, Object> equalsPredicate = buildPredicatesFromRequest(requestInfo.getQueryParameters());
		
		final CriteriaQuery<RoleImpl> criteriaQuery = cb.createQuery(RoleImpl.class);		
		final Root<RoleImpl> instituteRoot = criteriaQuery.from(RoleImpl.class);
		
		final List<Predicate> predicates = new ArrayList<>();
		equalsPredicate.forEach((key, value) -> {
			predicates.add(cb.equal(instituteRoot.get(key), value));
		});

		criteriaQuery.where(predicates.toArray(new Predicate[0]));

		final TypedQuery<RoleImpl> typedQuery = em.createQuery(criteriaQuery);		
		
		return typedQuery.getResultList();
	}
}