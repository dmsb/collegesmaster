package br.com.collegesmaster.model.security.dataprovider.impl;

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
import javax.persistence.criteria.Root;
import javax.security.auth.login.LoginException;

import org.jboss.ejb3.annotation.SecurityDomain;
import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;

import br.com.collegesmaster.model.security.dataprovider.AuthenticationDataProvider;
import br.com.collegesmaster.model.user.User;
import br.com.collegesmaster.model.user.impl.RoleImpl;
import br.com.collegesmaster.model.user.impl.UserImpl;
import br.com.collegesmaster.model.user.impl.UserImpl_;
import br.com.collegesmaster.qualifiers.UserDatabase;

@Stateless
@TransactionManagement(CONTAINER)
@SecurityDomain("collegesmasterSecurityDomain")
public class AuthenticationDataProviderImpl implements AuthenticationDataProvider {

	@Inject
	private Logger LOGGER;

	@Inject
	@UserDatabase
	private EntityManager em;

	@Inject
	private CriteriaBuilder cb;
	
	private StringBuilder queryBuilder;
	
	@Override
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
	
	@Override
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
	
	@Override
	@PermitAll
	public List<RoleImpl> findUserRoles(final String username) throws LoginException {
		
		final CriteriaQuery<UserImpl> criteriaQuery = cb.createQuery(UserImpl.class);		
		final Root<UserImpl> rootUser = criteriaQuery.from(UserImpl.class);
		
		criteriaQuery
			.where(cb.equal(rootUser.get(UserImpl_.username), username));
		
		final TypedQuery<UserImpl> typedQuery = em.createQuery(criteriaQuery);
		
		try {
			final User user = typedQuery.getSingleResult();
			return user.getRoles();
		} catch (NoResultException e) {
			LOGGER.log(Level.INFO, "No roles foundeds.");
		}
		
		throw new LoginException();
	}
}