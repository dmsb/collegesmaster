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
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.enterprise.event.Event;
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

import br.com.collegesmaster.annotation.qualifier.LoggedIn;
import br.com.collegesmaster.annotation.qualifier.UserDatabase;
import br.com.collegesmaster.business.UserBusiness;
import br.com.collegesmaster.business.util.BooleanReponseBuilder;
import br.com.collegesmaster.model.impl.GeneralInfoImpl_;
import br.com.collegesmaster.model.impl.UserImpl;
import br.com.collegesmaster.model.impl.UserImpl_;

@Stateless
@TransactionManagement(CONTAINER)
@SecurityDomain("collegesmasterSecurityDomain")
public class UserBusinessImpl implements UserBusiness {

	@Inject
	private Logger LOGGER;

	@Inject
	@UserDatabase
	private EntityManager em;

	@Inject
	private CriteriaBuilder cb;

	@Inject
	@LoggedIn
	private Event<UserImpl> userUpdateEvent;

	@EJB
	private BooleanReponseBuilder<UserImpl> booleanResponseBuilder;
	@PermitAll
	@Override
	public Boolean create(final UserImpl user) {
		if(user != null && user.getId() == null && user.getVersion() == null) {
			em.persist(user);
			return TRUE;
		} else {
			LOGGER.warn("Entity not persisted, invalid arguments");
			return FALSE;			
		}
	}

	@RolesAllowed({ "STUDENT", "PROFESSOR", "ADMINISTATOR" })
	@Override
	public UserImpl update(final UserImpl user) {
		if(user != null && user.getId() != null && user.getVersion() != null) {
			final UserImpl updatedUser = em.merge(user);
			em.flush();
			userUpdateEvent.fire(updatedUser);
			return updatedUser;
		} else {
			LOGGER.warn("Entity not persisted, invalid arguments");
			return null;
		}
	}

	@RolesAllowed({ "ADMINISTRATOR" })
	@Override
	public Boolean remove(final UserImpl user) {
		if(user != null && user.getId() != null && user.getVersion() != null) {
			em.remove(user);				
			return TRUE;
		} else {
			LOGGER.warn("Entity not removed, invalid arguments");
			return FALSE;
		}
	}

	@RolesAllowed({ "STUDENT", "PROFESSOR", "ADMINISTATOR" })
	@Override
	public UserImpl findById(final Integer id) {
		if(id != null) {
			return em.find(UserImpl.class, id);			
		} else {
			LOGGER.warn("Cannot find entity, invalid arguments");
			return null;
		}
	}

	@RolesAllowed({ "ADMINISTRATOR" })
	@Override
	public List<UserImpl> findAll() {
		final CriteriaQuery<UserImpl> criteriaQuery = cb.createQuery(UserImpl.class);
		criteriaQuery.from(UserImpl.class);
		final TypedQuery<UserImpl> typedQuery = em.createQuery(criteriaQuery);
		final List<UserImpl> result = typedQuery.getResultList();

		return result;
	}
	
	@RolesAllowed({"ADMINISTRATOR"})
	@TransactionAttribute(REQUIRED)
	@Override
	public List<UserImpl> findAll(final UriInfo requestInfo) {
		
		final Map<String, Object> equalsPredicate = buildPredicatesFromRequest(requestInfo, UserImpl.class);
		
		final CriteriaQuery<UserImpl> criteriaQuery = cb.createQuery(UserImpl.class);		
		final Root<UserImpl> instituteRoot = criteriaQuery.from(UserImpl.class);
		
		final List<Predicate> predicates = new ArrayList<>();
		equalsPredicate.forEach((key, value) -> {
			predicates.add(cb.equal(instituteRoot.get(key), value));
		});

		criteriaQuery.where(predicates.toArray(new Predicate[0]));

		final TypedQuery<UserImpl> typedQuery = em.createQuery(criteriaQuery);		
		
		return typedQuery.getResultList();
	}
	
	@PermitAll
	@TransactionAttribute(REQUIRED)
	@Override
	public Boolean existsCpf(final String cpfToBeComparated) {

		final Root<UserImpl> userRoot = booleanResponseBuilder.build(UserImpl.class);
		final String crudeCpfToBeComparated = cpfToBeComparated.replaceAll("[^0-9]", "");
		final Predicate containsCpf = cb.equal(userRoot.join(UserImpl_.generalInfo)
				.get(GeneralInfoImpl_.cpf), crudeCpfToBeComparated);
		
		return booleanResponseBuilder.where(containsCpf).execute();
	}

	@PermitAll
	@TransactionAttribute(REQUIRED)
	@Override
	public Boolean existsUsername(final String usernameToBeComparated) {
		
		final Root<UserImpl> userRoot = booleanResponseBuilder.build(UserImpl.class);
		final Predicate containsUsername = cb.equal(userRoot.get(UserImpl_.username), usernameToBeComparated);

		return booleanResponseBuilder.where(containsUsername).execute();

	}

	@PermitAll
	@TransactionAttribute(REQUIRED)
	@Override
	public Boolean existsEmail(final String emailToBeComparated) {
		
		final Root<UserImpl> userRoot = booleanResponseBuilder.build(UserImpl.class);
		final Predicate containsEmail = cb.equal(userRoot.join(UserImpl_.generalInfo)
				.get(GeneralInfoImpl_.email), emailToBeComparated);

		return booleanResponseBuilder.where(containsEmail).execute();
	}

	@PermitAll
	@TransactionAttribute(REQUIRED)
	@Override
	public UserImpl findByUsername(final String usernameToBeComparated) {

		final CriteriaQuery<UserImpl> criteriaQuery = cb.createQuery(UserImpl.class);
		final Root<UserImpl> userRoot = criteriaQuery.from(UserImpl.class);
		userRoot.fetch(UserImpl_.generalInfo);

		final Predicate usernamePredicate = cb.equal(userRoot.get(UserImpl_.username), usernameToBeComparated);

		criteriaQuery.select(userRoot).where(usernamePredicate);

		final TypedQuery<UserImpl> typedQuery = em.createQuery(criteriaQuery);
		return typedQuery.getSingleResult();

	}
}
