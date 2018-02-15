package br.com.collegesmaster.model.security.dataprovider.impl;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.collegesmaster.model.generics.producers.CriteriaBooleanReponse;
import br.com.collegesmaster.model.security.dataprovider.UserDataProvider;
import br.com.collegesmaster.model.security.impl.UserImpl;
import br.com.collegesmaster.model.security.impl.UserImpl_;
import br.com.collegesmaster.qualifiers.UserDatabase;

@Dependent
public class UserDataProviderImpl implements UserDataProvider {

	@Inject @UserDatabase
	private EntityManager em;
	
	@Inject
	protected CriteriaBuilder cb;
	
	@Inject
	private CriteriaBooleanReponse<UserImpl> booleanResponseBuilder;

	@Override
	public Boolean existsCpf(String cpf) {
		final Root<UserImpl> userRoot = booleanResponseBuilder.build(UserImpl.class);
		final Predicate containsCpf = cb.equal(userRoot.get(UserImpl_.cpf), cpf);
		
		return booleanResponseBuilder.where(containsCpf).execute();
	}

	@Override
	public Boolean existsEmail(String email) {
		final Root<UserImpl> userRoot = booleanResponseBuilder.build(UserImpl.class);
		final Predicate containsEmail = cb.equal(userRoot.get(UserImpl_.email), email);
		return booleanResponseBuilder.where(containsEmail).execute();
	}

	@Override
	public Boolean existsUsername(String username) {
		final Root<UserImpl> userRoot = booleanResponseBuilder.build(UserImpl.class);
		final Predicate containsUsername = cb.equal(userRoot.get(UserImpl_.username), username);
		return booleanResponseBuilder.where(containsUsername).execute();
	}

	@Override
	public UserImpl findByUsername(String username) {
		final CriteriaQuery<UserImpl> criteriaQuery = cb.createQuery(UserImpl.class);
		final Root<UserImpl> userRoot = criteriaQuery.from(UserImpl.class);
		final Predicate usernamePredicate = cb.equal(userRoot.get(UserImpl_.username), username);
		criteriaQuery.select(userRoot).where(usernamePredicate);
		final TypedQuery<UserImpl> typedQuery = em.createQuery(criteriaQuery);
		return typedQuery.getSingleResult();
	}
}
