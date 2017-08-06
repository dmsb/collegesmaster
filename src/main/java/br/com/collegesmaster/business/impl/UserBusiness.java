package br.com.collegesmaster.business.impl;

import static br.com.collegesmaster.model.impl.GeneralInfo_.cpf;
import static br.com.collegesmaster.model.impl.GeneralInfo_.email;
import static br.com.collegesmaster.model.impl.User_.generalInfo;
import static br.com.collegesmaster.model.impl.User_.username;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.jboss.ejb3.annotation.SecurityDomain;
import org.jboss.logging.Logger;

import br.com.collegesmaster.annotation.qualifier.LoggedIn;
import br.com.collegesmaster.annotation.qualifier.UserDatabase;
import br.com.collegesmaster.business.IUserBusiness;
import br.com.collegesmaster.model.IUser;
import br.com.collegesmaster.model.impl.User;

@Stateless
@TransactionManagement(CONTAINER)
@SecurityDomain("collegesmasterSecurityDomain")
public class UserBusiness implements IUserBusiness {

	@Inject
	private Logger LOGGER;

	@Inject
	@UserDatabase
	private EntityManager em;

	@Inject
	private CriteriaBuilder cb;

	@Inject
	@LoggedIn
	private Event<IUser> userUpdateEvent;

	@PermitAll
	@Override
	public void create(final IUser user) {
		em.persist(user);
	}

	@RolesAllowed({ "STUDENT", "PROFESSOR", "ADMINISTATOR" })
	@Override
	public IUser update(final IUser user) {
		final IUser updatedUser = em.merge(user);
		userUpdateEvent.fire(updatedUser);
		return updatedUser;
	}

	@RolesAllowed({ "ADMINISTRATOR" })
	@Override
	public void remove(final IUser user) {
		em.remove(user);
	}

	@RolesAllowed({ "STUDENT", "PROFESSOR", "ADMINISTATOR" })
	@Override
	public IUser findById(final Integer id) {
		return em.find(User.class, id);
	}

	@RolesAllowed({ "ADMINISTRATOR" })
	@Override
	public List<User> findAll() {
		final CriteriaQuery<User> criteriaQuery = cb.createQuery(User.class);
		criteriaQuery.from(User.class);
		final TypedQuery<User> typedQuery = em.createQuery(criteriaQuery);
		final List<User> result = typedQuery.getResultList();

		return result;
	}

	@PermitAll
	@TransactionAttribute(REQUIRED)
	@Override
	public Boolean existsCpf(final String cpfToBeComparated) {

		final CriteriaQuery<Boolean> query = buildBooleanReturnQuery(User.class);

		final Subquery<User> subquery = query.subquery(User.class);
		final Root<User> userRoot = subquery.from(User.class);
		subquery.select(userRoot);

		final String crudeCpfToBeComparated = cpfToBeComparated.replaceAll("[^0-9]", "");

		final Predicate containsCpf = cb.equal(userRoot.join(generalInfo).get(cpf), crudeCpfToBeComparated);
		return executeExists(query, subquery, containsCpf);
	}

	@PermitAll
	@TransactionAttribute(REQUIRED)
	@Override
	public Boolean existsUsername(final String usernameToBeComparated) {

		final CriteriaQuery<Boolean> query = buildBooleanReturnQuery(User.class);

		final Subquery<User> subquery = query.subquery(User.class);
		final Root<User> userRoot = subquery.from(User.class);
		subquery.select(userRoot);

		final Predicate containsUsername = cb.equal(userRoot.get(username), usernameToBeComparated);
		return executeExists(query, subquery, containsUsername);

	}

	@PermitAll
	@TransactionAttribute(REQUIRED)
	@Override
	public Boolean existsEmail(final String emailToBeComparated) {

		final CriteriaQuery<Boolean> query = buildBooleanReturnQuery(User.class);

		final Subquery<User> subquery = query.subquery(User.class);
		final Root<User> userRoot = subquery.from(User.class);
		subquery.select(userRoot);

		final Predicate containsEmail = cb.equal(userRoot.join(generalInfo).get(email), emailToBeComparated);
		return executeExists(query, subquery, containsEmail);
	}

	@PermitAll
	@TransactionAttribute(REQUIRED)
	@Override
	public IUser findByUsername(final String usernameToBeComparated) {

		final CriteriaQuery<User> criteriaQuery = cb.createQuery(User.class);
		final Root<User> userRoot = criteriaQuery.from(User.class);
		userRoot.fetch(generalInfo);

		final Predicate usernamePredicate = cb.equal(userRoot.get(username), usernameToBeComparated);

		criteriaQuery.select(userRoot).where(usernamePredicate);

		final TypedQuery<User> typedQuery = em.createQuery(criteriaQuery);
		return typedQuery.getSingleResult();

	}

	protected Boolean executeExists(final CriteriaQuery<Boolean> query, final Subquery<User> subquery,
			final Predicate... predicates) {

		subquery.where(predicates);
		final Predicate exists = cb.exists(subquery);
		query.where(exists);

		final TypedQuery<Boolean> typedQuery = em.createQuery(query);

		if (TRUE.equals(singleResult(typedQuery))) {
			return TRUE;
		} else {
			return FALSE;
		}
	}

	protected CriteriaQuery<Boolean> buildBooleanReturnQuery(final Class<?> classz) {
		final CriteriaQuery<Boolean> query = cb.createQuery(Boolean.class);
		query.from(classz);
		query.select(cb.literal(TRUE)).distinct(true);
		return query;
	}

	@SuppressWarnings("rawtypes")
	protected Object singleResult(final TypedQuery typedQuery) {
		try {
			return typedQuery.getSingleResult();
		} catch (NoResultException e) {
			LOGGER.info("No results");
		}
		return null;
	}
}
