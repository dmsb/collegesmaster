package br.com.collegesmaster.model.security.dataprovider.impl;

import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;

import javax.annotation.security.PermitAll;
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

import org.jboss.ejb3.annotation.SecurityDomain;

import br.com.collegesmaster.model.generics.producers.BooleanReponseFactory;
import br.com.collegesmaster.model.security.dataprovider.UserDataProvider;
import br.com.collegesmaster.model.security.impl.GeneralInfoImpl_;
import br.com.collegesmaster.model.security.impl.UserImpl;
import br.com.collegesmaster.model.security.impl.UserImpl_;
import br.com.collegesmaster.qualifiers.UserDatabase;

@Stateless
@TransactionManagement(CONTAINER)
@PermitAll
@SecurityDomain("collegesmasterSecurityDomain")
public class UserDataProviderImpl implements UserDataProvider {

	@Inject @UserDatabase
	private EntityManager em;
	
	@Inject
	protected CriteriaBuilder cb;
	
	@Inject
	private BooleanReponseFactory<UserImpl> booleanResponseBuilder;

	@TransactionAttribute(REQUIRED)
	@Override
	public Boolean existsCpf(String cpf) {
		final Root<UserImpl> userRoot = booleanResponseBuilder.build(UserImpl.class);
		final Predicate containsCpf = cb.equal(userRoot.join(UserImpl_.generalInfo)
				.get(GeneralInfoImpl_.cpf), cpf);
		
		return booleanResponseBuilder.where(containsCpf).execute();
	}

	@TransactionAttribute(REQUIRED)
	@Override
	public Boolean existsEmail(String email) {
		final Root<UserImpl> userRoot = booleanResponseBuilder.build(UserImpl.class);
		final Predicate containsEmail = cb.equal(userRoot.join(UserImpl_.generalInfo)
				.get(GeneralInfoImpl_.email), email);
		return booleanResponseBuilder.where(containsEmail).execute();
	}

	@TransactionAttribute(REQUIRED)
	@Override
	public Boolean existsUsername(String username) {
		final Root<UserImpl> userRoot = booleanResponseBuilder.build(UserImpl.class);
		final Predicate containsUsername = cb.equal(userRoot.get(UserImpl_.username), username);
		return booleanResponseBuilder.where(containsUsername).execute();
	}

	@TransactionAttribute(REQUIRED)
	@Override
	public UserImpl findByUsername(String username) {
		final CriteriaQuery<UserImpl> criteriaQuery = cb.createQuery(UserImpl.class);
		
		final Root<UserImpl> userRoot = criteriaQuery.from(UserImpl.class);
		userRoot.fetch(UserImpl_.generalInfo);
		
		final Predicate usernamePredicate = cb.equal(userRoot.get(UserImpl_.username), username);
		criteriaQuery.select(userRoot).where(usernamePredicate);
		
		final TypedQuery<UserImpl> typedQuery = em.createQuery(criteriaQuery);
		return typedQuery.getSingleResult();
	}
}
