package br.com.collegesmaster.business.imp;

import static br.com.collegesmaster.model.impl.GeneralInfo_.cpf;
import static br.com.collegesmaster.model.impl.GeneralInfo_.email;
import static br.com.collegesmaster.model.impl.User_.generalInfo;
import static br.com.collegesmaster.model.impl.User_.password;
import static br.com.collegesmaster.model.impl.User_.username;
import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.List;
import java.util.logging.Level;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.jboss.ejb3.annotation.SecurityDomain;

import com.google.common.base.Strings;

import br.com.collegesmaster.business.IUserBusiness;
import br.com.collegesmaster.model.IUser;
import br.com.collegesmaster.model.impl.User;
import br.com.collegesmaster.model.impl.User_;
import br.com.collegesmaster.util.CryptoUtils;

@Stateless
@TransactionManagement(CONTAINER)
@DeclareRoles({"STUDENT", "PROFESSOR", "ADMINISTRATOR"})
@RolesAllowed({"STUDENT", "PROFESSOR", "ADMINISTATOR"})
@SecurityDomain("collegesmasterSecurityDomain")
public class UserBusiness extends GenericBusiness implements IUserBusiness {
	
	@Override	
	public void save(final IUser user) {
		entityManager.persist(user);
	}
	
	@Override
	public IUser update(final IUser user) {
		return entityManager.merge(user);
	}
	
	@RolesAllowed({"ADMINISTRATOR"})
	@Override
	public void remove(final IUser user) {
		entityManager.remove(user);
	}
	
	@Override
	public IUser findById(final Integer id) {
		return entityManager.find(User.class, id);
	}
	
	@RolesAllowed({"ADMINISTRATOR"})
	@Override
	public List<User> findAll() {
		final CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
		criteriaQuery.from(User.class);
		final TypedQuery<User> typedQuery = entityManager.createQuery(criteriaQuery);
		final List<User> result = typedQuery.getResultList();

		return result;
	}
	
	@RolesAllowed({"ADMINISTRATOR"})
	@Override
	public IUser login(final String usernameToBeComparated, final String passwordToBeComparated) {
		if (!(Strings.isNullOrEmpty(usernameToBeComparated) || Strings.isNullOrEmpty(passwordToBeComparated))) {

			final String salt = getUserSalt(usernameToBeComparated);

			if(Strings.isNullOrEmpty(salt) == false) {
				final IUser user = buildLogin(usernameToBeComparated, passwordToBeComparated, salt);
				return user;
			}			
			return null;
		} else {			
			return null;
		}

	}
	
	@Override
	@PermitAll
	public String getUserSalt(final String usernameToBeComparated) {
		
		queryBuilder = new StringBuilder();
		
		queryBuilder
			.append("SELECT user.salt ")
			.append("FROM   User user ")
			.append("WHERE  user.username = :username");

		final Query query = entityManager.createQuery(queryBuilder.toString());
		query.setParameter("username", usernameToBeComparated);
		try {
			final String salt = (String) query.getSingleResult();
			return salt;
		} catch (NoResultException e) {
			logger.log(Level.INFO, "No salt founded.");
		}
		return null;
	}

	private IUser buildLogin(final String usernameToBeComparated,
			final String passwordToBeComparated, final String saltToBeComparated) {

		final String hashedPassword = CryptoUtils.getHashedPassword(passwordToBeComparated, saltToBeComparated);

		final CriteriaQuery<User> query = builder.createQuery(User.class);
		final Root<User> userRoot = query.from(User.class);
		userRoot.fetch(generalInfo);
		
		final Predicate usernamePredicate = builder.equal(userRoot.get(username), usernameToBeComparated);
		final Predicate passwordPredicate = builder.equal(userRoot.get(password), hashedPassword);
		
		query.select(userRoot).where(usernamePredicate, passwordPredicate);		
		
		final TypedQuery<User> typedQuery = entityManager.createQuery(query);
		
		try {
			return typedQuery.getSingleResult();			
			
		} catch (NoResultException e) {
			logger.log(Level.SEVERE, "Bad credentials");
		}
		return null;
	}
	
	@Override
	public Boolean existsCpf(final String cpfToBeComparated) {
		
		final CriteriaQuery<Boolean> query = buildBooleanReturnQuery(User.class);
		
		final Subquery<User> subquery = query.subquery(User.class);
		final Root<User> userRoot = subquery.from(User.class);
		subquery.select(userRoot);
		
		final String crudeCpfToBeComparated = cpfToBeComparated.replaceAll("[^0-9]", "");		
		
		final Predicate containsCpf = builder.equal(userRoot.join(generalInfo).get(cpf), crudeCpfToBeComparated);
		return executeExists(query, subquery, containsCpf);
	}

	@Override
	public Boolean existsUsername(final String usernameToBeComparated) {
		
		final CriteriaQuery<Boolean> query = buildBooleanReturnQuery(User.class);
		
		final Subquery<User> subquery = query.subquery(User.class);
		final Root<User> userRoot = subquery.from(User.class);
		subquery.select(userRoot);
		
		final Predicate containsUsername = builder.equal(userRoot.get(username), usernameToBeComparated);
		return executeExists(query, subquery, containsUsername);
	
	}

	@Override
	public Boolean existsEmail(final String emailToBeComparated) {
		
		final CriteriaQuery<Boolean> query = buildBooleanReturnQuery(User.class);
		
		final Subquery<User> subquery = query.subquery(User.class);
		final Root<User> userRoot = subquery.from(User.class);
		subquery.select(userRoot);
		
		final Predicate containsEmail = builder.equal(userRoot.join(generalInfo).get(email), emailToBeComparated);
		return executeExists(query, subquery, containsEmail);
	}
	
	@Override
	@RolesAllowed({"ADMINISTRATOR"})
	public IUser findByUserName(final String username) {
		sessionContext.isCallerInRole("ADMINISTRATOR");
		final CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
		final Root<User> userRoot =  criteriaQuery.from(User.class);
		userRoot.fetch(generalInfo);
		
		final Predicate usernamePredicate = builder.equal(userRoot.get(User_.username), username);
		
		criteriaQuery
			.select(userRoot)
			.where(usernamePredicate);
		
		final TypedQuery<User> typedQuery = entityManager.createQuery(criteriaQuery);
		return typedQuery.getSingleResult();		
		
	}
}
