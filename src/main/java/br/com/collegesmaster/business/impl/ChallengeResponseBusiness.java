package br.com.collegesmaster.business.impl;

import static br.com.collegesmaster.model.impl.ChallengeResponse_.owner;
import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.jboss.ejb3.annotation.SecurityDomain;

import br.com.collegesmaster.annotation.qualifier.UserDatabase;
import br.com.collegesmaster.business.IChallengeResponseBusiness;
import br.com.collegesmaster.model.IChallenge;
import br.com.collegesmaster.model.IChallengeResponse;
import br.com.collegesmaster.model.IUser;
import br.com.collegesmaster.model.impl.ChallengeResponse;
import br.com.collegesmaster.model.impl.ChallengeResponse_;

@Stateless
@TransactionManagement(CONTAINER)
@SecurityDomain("collegesmasterSecurityDomain")
public class ChallengeResponseBusiness  implements IChallengeResponseBusiness {
	
	@Inject @UserDatabase
	private EntityManager em;
	
	@Inject
	protected CriteriaBuilder cb;
	
	@RolesAllowed({"STUDENT", "ADMINISTRATOR"})
	@Override
	public void create(IChallengeResponse response) {
		em.persist(response);
	}

	@RolesAllowed({"STUDENT", "ADMINISTRATOR"})
	@Override
	public IChallengeResponse update(IChallengeResponse response) {
		return em.merge(response);
	}
	
	@RolesAllowed({"ADMINISTRATOR"})
	@Override
	public void remove(IChallengeResponse response) {
		em.remove(response);
	}

	@RolesAllowed({"STUDENT", "PROFESSOR", "ADMINISTRATOR"})
	@Override
	public IChallengeResponse findById(Integer id) {
		return em.find(ChallengeResponse.class, id);
	}
	
	@RolesAllowed({"ADMINISTRATOR"})
	@Override
	public List<ChallengeResponse> findAll() {
		final CriteriaQuery<ChallengeResponse> criteriaQuery = cb.createQuery(ChallengeResponse.class);
		final TypedQuery<ChallengeResponse> typedQuery = em.createQuery(criteriaQuery);		
		return typedQuery.getResultList();
	}
	
	@RolesAllowed({"STUDENT", "PROFESSOR", "ADMINISTRATOR"})
	@Override
	public List<ChallengeResponse> findAllByUser(final IUser user) {
		final CriteriaQuery<ChallengeResponse> criteriaQuery = cb.createQuery(ChallengeResponse.class);
		final Root<ChallengeResponse> challengeResponseRoot = criteriaQuery.from(ChallengeResponse.class);
		final Predicate userPredicate = cb.equal(challengeResponseRoot.get(owner), user);
		
		criteriaQuery
			.select(challengeResponseRoot)
			.where(userPredicate);
		
		final TypedQuery<ChallengeResponse> typedQuery = em.createQuery(criteriaQuery);
		return typedQuery.getResultList();
		
	}
	
	@RolesAllowed({"PROFESSOR", "ADMINISTRATOR"})
	@Override
	public List<ChallengeResponse> findAllByChallenge(IChallenge selectedChallenge) {
		final CriteriaQuery<ChallengeResponse> criteriaQuery = cb.createQuery(ChallengeResponse.class);
		final Root<ChallengeResponse> challengeResponseRoot = criteriaQuery.from(ChallengeResponse.class);
		challengeResponseRoot.fetch(ChallengeResponse_.owner);
		
		final Predicate predicate = cb
				.equal(challengeResponseRoot.get(ChallengeResponse_.targetChallenge), selectedChallenge);
		
		criteriaQuery
			.select(challengeResponseRoot)
			.where(predicate);
		
		final TypedQuery<ChallengeResponse> typedQuery = em.createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

}
