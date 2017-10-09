package br.com.collegesmaster.rest.security.business;

import static javax.ejb.TransactionManagementType.CONTAINER;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
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

import br.com.collegesmaster.annotation.qualifier.AuthenticatedUser;
import br.com.collegesmaster.annotation.qualifier.UserDatabase;
import br.com.collegesmaster.model.User;
import br.com.collegesmaster.model.impl.UserImpl;
import br.com.collegesmaster.model.impl.UserImpl_;
import br.com.collegesmaster.security.model.Credentials;
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
	
	@Inject
	@AuthenticatedUser
	private Event<UserImpl> userUpdateEvent;
	
	public User authenticate(final Credentials credentials) throws LoginException {
		
		final String username = credentials.getUsername();
		final String password  = credentials.getPassword();
		
		if (!(Strings.isNullOrEmpty(username) || Strings.isNullOrEmpty(password))) {

			final String salt = findUserSalt(username);

			if(Strings.isNullOrEmpty(salt) == false) {
				final UserImpl user = buildLogin(username, password, salt);
				userUpdateEvent.fire(user);
				return user;
			}
		}
		
		throw new LoginException();

	}

	private String findUserSalt(final String username) {
		
		queryBuilder = new StringBuilder();
		
		queryBuilder
			.append("SELECT user.salt ")
			.append("FROM   User user WHERE user.username = :username");

		final Query query = em.createQuery(queryBuilder.toString());
		query.setParameter("username", username);
		try {
			final String salt = (String) query.getSingleResult();
			return salt;
		} catch (Exception e) {
			LOGGER.log(Level.WARN, "Fail to get user salt", e);
		}
		return null;
	}

	private UserImpl buildLogin(final String usernameSubmited, final String passwordSubmited, final String salt) {

		final String hashedPassword = CryptoUtils.generateHashedPassword(passwordSubmited, salt);

		final CriteriaQuery<UserImpl> criteriaQuery = cb.createQuery(UserImpl.class);
		final Root<UserImpl> userRoot = criteriaQuery.from(UserImpl.class);
		userRoot.fetch(UserImpl_.generalInfo);
		
		final Predicate usernamePredicate = cb.equal(userRoot.get(UserImpl_.username), usernameSubmited);
		final Predicate passwordPredicate = cb.equal(userRoot.get(UserImpl_.password), hashedPassword);
		
		criteriaQuery.select(userRoot).where(usernamePredicate, passwordPredicate);		
		
		final TypedQuery<UserImpl> query = em.createQuery(criteriaQuery);
		
		try {
			final UserImpl user = query.getSingleResult();
			return user;
			
		} catch (Exception e) {
			LOGGER.log(Level.WARN, "Fail to login", e);
		}
		return null;
	}

}
