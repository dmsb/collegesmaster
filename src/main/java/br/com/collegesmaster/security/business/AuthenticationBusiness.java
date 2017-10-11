package br.com.collegesmaster.security.business;

import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
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

import br.com.collegesmaster.annotation.qualifier.UserDatabase;
import br.com.collegesmaster.model.impl.RoleImpl;
import br.com.collegesmaster.model.impl.RoleImpl_;
import br.com.collegesmaster.model.impl.UserImpl;
import br.com.collegesmaster.model.impl.UserImpl_;
import br.com.collegesmaster.util.CryptoUtils;

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
	public UserImpl authenticate(final String usernameToBeComparated, 
			final String passwordToBeComparated) throws LoginException {
		
		if (!(Strings.isNullOrEmpty(usernameToBeComparated) || Strings.isNullOrEmpty(passwordToBeComparated))) {

			final String salt = getUserSalt(usernameToBeComparated);

			if(Strings.isNullOrEmpty(salt) == false) {
				final UserImpl user = buildLogin(usernameToBeComparated, passwordToBeComparated, salt);
				return user;
			}
		}
		throw new LoginException();
	}
	
	@PermitAll
	public String getUserSalt(final String usernameToBeComparated) throws LoginException {
		
		queryBuilder = new StringBuilder();
		
		queryBuilder
			.append("SELECT user.salt ")
			.append("FROM   UserImpl user ")
			.append("WHERE  user.username = :username");

		final Query query = em.createQuery(queryBuilder.toString());
		query.setParameter("username", usernameToBeComparated);
		try {
			final String salt = (String) query.getSingleResult();
			return salt;
		} catch (NoResultException e) {
			LOGGER.log(Level.INFO, "No salt founded.");
		}
		
		throw new LoginException();
	}
	
	@PermitAll
	public String getUserPassword(final String username) throws LoginException {
		
		queryBuilder = new StringBuilder();
		
		queryBuilder
			.append("SELECT user.password ")
			.append("FROM   UserImpl user ")
			.append("WHERE  user.username = :username");

		final TypedQuery<String> typedQuery = em
				.createQuery(queryBuilder.toString(), String.class);
		
		typedQuery.setParameter("username", username);
		
		try {
			final String password = (String) typedQuery.getSingleResult();
			return password;
		} catch (NoResultException e) {
			LOGGER.log(Level.INFO, "No password founded.");
		}
		
		throw new LoginException();
	}
	
	@PermitAll
	public List<RoleImpl> getUserRoles(final String username) throws LoginException {
		
		final CriteriaQuery<RoleImpl> criteriaQuery = cb.createQuery(RoleImpl.class);		
		final Root<RoleImpl> rootRole = criteriaQuery.from(RoleImpl.class);
		
		criteriaQuery
			.where(cb.equal(rootRole.join(RoleImpl_.users).get(UserImpl_.username), username));
		
		final TypedQuery<RoleImpl> typedQuery = em.createQuery(criteriaQuery);
		
		try {
			return typedQuery.getResultList();
		} catch (NoResultException e) {
			LOGGER.log(Level.INFO, "No roles foundeds.");
		}
		
		throw new LoginException();
	}

	@PermitAll
	private UserImpl buildLogin(final String usernameToBeComparated,
			final String passwordToBeComparated, final String saltToBeComparated) throws LoginException {

		final String hashedPassword = CryptoUtils
				.generateHashedPassword(passwordToBeComparated, saltToBeComparated);

		final CriteriaQuery<UserImpl> query = cb.createQuery(UserImpl.class);
		final Root<UserImpl> userRoot = query.from(UserImpl.class);
		userRoot.fetch(UserImpl_.generalInfo);
		
		final Predicate usernamePredicate = cb.equal(userRoot.get(UserImpl_.username), usernameToBeComparated);
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
