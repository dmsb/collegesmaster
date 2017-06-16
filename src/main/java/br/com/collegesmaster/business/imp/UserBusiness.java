package br.com.collegesmaster.business.imp;

import java.util.List;
import java.util.logging.Level;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.google.common.base.Strings;

import br.com.collegesmaster.business.IUserBusiness;
import br.com.collegesmaster.model.IUser;
import br.com.collegesmaster.model.imp.User;
import br.com.collegesmaster.model.imp.User_;
import br.com.collegesmaster.util.CryptoUtils;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class UserBusiness extends GenericBusiness implements IUserBusiness {	
	
	@Override
	public void persist(final IUser user) {
		entityManager.persist(user);

	}

	@Override
	public void merge(final IUser user) {
		entityManager.merge(user);

	}

	@Override
	public void remove(final IUser user) {
		entityManager.remove(user);

	}

	@Override
	public IUser findById(final Integer id, final Class<User> modelClass) {
		return entityManager.find(modelClass, id);
	}

	@Override
	public List<User> findAll() {
		final CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
		criteriaQuery.from(User.class);
		final TypedQuery<User> typedQuery = entityManager.createQuery(criteriaQuery);
		final List<User> result = typedQuery.getResultList();

		return result;
	}

	@Override
	public IUser login(final String username, final String password) {
		if (!(Strings.isNullOrEmpty(username) || Strings.isNullOrEmpty(password))) {

			final String salt = getUserSalt(username);

			if(Strings.isNullOrEmpty(salt) == false) {
				final IUser user = buildLogin(username, password, salt);
				return user;
			}
			return null;
		} else {
			return null;
		}

	}

	private String getUserSalt(final String username) {
		
		queryBuilder = new StringBuilder();
		
		queryBuilder
			.append("SELECT user.salt ")
			.append("FROM   User user where user.username = :username");

		final Query query = entityManager.createQuery(queryBuilder.toString());
		query.setParameter("username", username);
		try {
			final String salt = (String) query.getSingleResult();
			return salt;
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Fail to get user salt", e);
		}
		return null;
	}

	private IUser buildLogin(final String username, final String password, final String salt) {

		final String hashedPassword = CryptoUtils.getHashedPassword(password, salt);

		final CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
		final Root<User> userRoot = criteriaQuery.from(User.class);
		userRoot.fetch(User_.generalInfo);
		
		final Predicate usernamePredicate = criteriaBuilder.equal(userRoot.get(User_.username), username);
		final Predicate passwordPredicate = criteriaBuilder.equal(userRoot.get(User_.password), hashedPassword);
		
		criteriaQuery.select(userRoot).where(usernamePredicate, passwordPredicate);		
		
		final TypedQuery<User> query = entityManager.createQuery(criteriaQuery);
		
		try {
			final IUser user = query.getSingleResult();
			return user;
			
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Fail to login", e);
		}
		return null;
	}

}
