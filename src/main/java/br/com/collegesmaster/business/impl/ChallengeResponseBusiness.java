package br.com.collegesmaster.business.impl;

import static br.com.collegesmaster.model.impl.ChallengeResponse_.owner;
import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.jboss.ejb3.annotation.SecurityDomain;

import br.com.collegesmaster.business.IChallengeResponseBusiness;
import br.com.collegesmaster.model.IChallengeResponse;
import br.com.collegesmaster.model.IUser;
import br.com.collegesmaster.model.impl.ChallengeResponse;

@Stateless
@TransactionManagement(CONTAINER)
@RolesAllowed({"ADMINISTRATOR"})
@SecurityDomain("collegesmasterSecurityDomain")
public class ChallengeResponseBusiness extends GenericBusiness implements IChallengeResponseBusiness {

	@RolesAllowed({"STUDENT", "ADMINISTRATOR"})
	@Override
	public void save(IChallengeResponse response) {
		entityManager.persist(response);
	}

	@RolesAllowed({"STUDENT", "ADMINISTRATOR"})
	@Override
	public IChallengeResponse update(IChallengeResponse response) {
		return entityManager.merge(response);
	}

	@Override
	public void remove(IChallengeResponse response) {
		entityManager.remove(response);
	}

	@RolesAllowed({"STUDENT", "PROFESSOR", "ADMINISTRATOR"})
	@Override
	public IChallengeResponse findById(Integer id) {
		return entityManager.find(ChallengeResponse.class, id);
	}

	@Override
	public List<ChallengeResponse> findAll() {
		final CriteriaQuery<ChallengeResponse> criteriaQuery = builder.createQuery(ChallengeResponse.class);
		final TypedQuery<ChallengeResponse> typedQuery = entityManager.createQuery(criteriaQuery);		
		return typedQuery.getResultList();
	}
	
	@RolesAllowed({"STUDENT", "PROFESSOR", "ADMINISTRATOR"})
	@Override
	public List<ChallengeResponse> findAllByUser(final IUser user) {
		final CriteriaQuery<ChallengeResponse> criteriaQuery = builder.createQuery(ChallengeResponse.class);
		final Root<ChallengeResponse> challengeResponseRoot = criteriaQuery.from(ChallengeResponse.class);
		final Predicate userPredicate = builder.equal(challengeResponseRoot.get(owner), user);
		
		criteriaQuery
			.select(challengeResponseRoot)
			.where(userPredicate);
		
		final TypedQuery<ChallengeResponse> typedQuery = entityManager.createQuery(criteriaQuery);
		return typedQuery.getResultList();
		
	}

}
