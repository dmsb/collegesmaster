package br.com.collegesmaster.security.business;

import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.security.auth.login.LoginException;

import org.jboss.ejb3.annotation.SecurityDomain;
import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;

import com.google.common.base.Strings;

import br.com.collegesmaster.model.entities.role.impl.RoleImpl;
import br.com.collegesmaster.model.entities.user.impl.UserImpl;
import br.com.collegesmaster.model.entities.user.impl.UserImpl_;
import br.com.collegesmaster.qualifiers.UserDatabase;
import br.com.collegesmaster.utils.CryptoUtils;

@Stateless
@TransactionManagement(CONTAINER)
@SecurityDomain("collegesmasterSecurityDomain")
public class AuthenticationBusiness {
	
	@Inject
	private Logger LOGGER;

	@Inject
	@UserDatabase
	private EntityManager em;

	@Inject
	private CriteriaBuilder cb;
	
	private StringBuilder queryBuilder;
	
	@PermitAll
	public UserImpl authenticate(final String username, 
			final String password) throws LoginException {
		
		if (!(Strings.isNullOrEmpty(username) || Strings.isNullOrEmpty(password))) {

			final String salt = findUserSalt(username);

			if(Strings.isNullOrEmpty(salt) == false) {
				final UserImpl user = buildLogin(username, password, salt);
				return user;
			}
		}
		throw new LoginException();
	}
	
	@PermitAll
	public String findUserSalt(final String username) throws LoginException {
		
		queryBuilder = new StringBuilder();
		
		queryBuilder
			.append("SELECT user.salt ")
			.append("FROM   UserImpl user ")
			.append("WHERE  user.username = :username");

		final TypedQuery<String> query = em.createQuery(queryBuilder.toString(), String.class);
		query.setParameter("username", username);
		try {
			final String salt = query.getSingleResult();
			return salt;
		} catch (NoResultException e) {
			LOGGER.log(Level.INFO, "No salt founded.");
		}
		
		throw new LoginException();
	}
	
	@PermitAll
	public String findUserPassword(final String username) throws LoginException {
		
		queryBuilder = new StringBuilder();
		
		queryBuilder
			.append("SELECT user.password ")
			.append("FROM   UserImpl user ")
			.append("WHERE  user.username = :username");

		final TypedQuery<String> typedQuery = em
				.createQuery(queryBuilder.toString(), String.class);
		
		typedQuery.setParameter("username", username);
		
		try {
			final String password = typedQuery.getSingleResult();
			return password;
		} catch (NoResultException e) {
			LOGGER.log(Level.INFO, "No password founded.");
		}
		
		throw new LoginException();
	}
	
	@PermitAll
	public List<RoleImpl> findUserRoles(final String username) throws LoginException {
		
		final CriteriaQuery<UserImpl> criteriaQuery = cb.createQuery(UserImpl.class);		
		final Root<UserImpl> rootUser = criteriaQuery.from(UserImpl.class);
		
		criteriaQuery
			.where(cb.equal(rootUser.get(UserImpl_.username), username));
		
		final TypedQuery<UserImpl> typedQuery = em.createQuery(criteriaQuery);
		
		try {
			final UserImpl user = typedQuery.getSingleResult();
			return user.getRoles();
		} catch (NoResultException e) {
			LOGGER.log(Level.INFO, "No roles foundeds.");
		}
		
		throw new LoginException();
	}

	private UserImpl buildLogin(final String username,
			final String password, final String salt) throws LoginException {

		final String hashedPassword = CryptoUtils
				.generateHashedPassword(password, salt);

		final CriteriaQuery<UserImpl> query = cb.createQuery(UserImpl.class);
		final Root<UserImpl> userRoot = query.from(UserImpl.class);
		userRoot.fetch(UserImpl_.generalInfo);
		
		final Predicate usernamePredicate = cb.equal(userRoot.get(UserImpl_.username), username);
		final Predicate passwordPredicate = cb.equal(userRoot.get(UserImpl_.password), hashedPassword);
		
		query.select(userRoot).where(usernamePredicate, passwordPredicate);		

		final TypedQuery<UserImpl> typedQuery = em.createQuery(query);
		
		try {
			return typedQuery.getSingleResult();
		} catch (NoResultException e) {
			LOGGER.warn(e.getMessage());
		}
		throw new LoginException();
	}
	
}
