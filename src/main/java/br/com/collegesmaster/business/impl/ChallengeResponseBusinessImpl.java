package br.com.collegesmaster.business.impl;

import static br.com.collegesmaster.model.impl.ChallengeResponseImpl_.owner;
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
import br.com.collegesmaster.business.ChallengeResponseBusiness;
import br.com.collegesmaster.model.Challenge;
import br.com.collegesmaster.model.User;
import br.com.collegesmaster.model.impl.ChallengeResponseImpl;
import br.com.collegesmaster.model.impl.ChallengeResponseImpl_;

@Stateless
@TransactionManagement(CONTAINER)
@SecurityDomain("collegesmasterSecurityDomain")
public class ChallengeResponseBusinessImpl  implements ChallengeResponseBusiness {
	
	@Inject @UserDatabase
	private EntityManager em;
	
	@Inject
	protected CriteriaBuilder cb;
	
	@RolesAllowed({"STUDENT", "ADMINISTRATOR"})
	@Override
	public void create(ChallengeResponseImpl response) {
		em.persist(response);
	}

	@RolesAllowed({"STUDENT", "ADMINISTRATOR"})
	@Override
	public ChallengeResponseImpl update(ChallengeResponseImpl response) {
		return em.merge(response);
	}
	
	@RolesAllowed({"ADMINISTRATOR"})
	@Override
	public void remove(ChallengeResponseImpl response) {
		em.remove(response);
	}

	@RolesAllowed({"STUDENT", "PROFESSOR", "ADMINISTRATOR"})
	@Override
	public ChallengeResponseImpl findById(Integer id) {
		return em.find(ChallengeResponseImpl.class, id);
	}
	
	@RolesAllowed({"ADMINISTRATOR"})
	@Override
	public List<ChallengeResponseImpl> findAll() {
		final CriteriaQuery<ChallengeResponseImpl> criteriaQuery = cb.createQuery(ChallengeResponseImpl.class);
		final TypedQuery<ChallengeResponseImpl> typedQuery = em.createQuery(criteriaQuery);		
		return typedQuery.getResultList();
	}
	
	@RolesAllowed({"STUDENT", "PROFESSOR", "ADMINISTRATOR"})
	@Override
	public List<ChallengeResponseImpl> findAllByUser(final User user) {
		final CriteriaQuery<ChallengeResponseImpl> criteriaQuery = cb.createQuery(ChallengeResponseImpl.class);
		final Root<ChallengeResponseImpl> challengeResponseRoot = criteriaQuery.from(ChallengeResponseImpl.class);
		final Predicate userPredicate = cb.equal(challengeResponseRoot.get(owner), user);
		
		criteriaQuery
			.select(challengeResponseRoot)
			.where(userPredicate);
		
		final TypedQuery<ChallengeResponseImpl> typedQuery = em.createQuery(criteriaQuery);
		return typedQuery.getResultList();
		
	}
	
	@RolesAllowed({"PROFESSOR", "ADMINISTRATOR"})
	@Override
	public List<ChallengeResponseImpl> findAllByChallenge(Challenge selectedChallenge) {
		final CriteriaQuery<ChallengeResponseImpl> criteriaQuery = cb.createQuery(ChallengeResponseImpl.class);
		final Root<ChallengeResponseImpl> challengeResponseRoot = criteriaQuery.from(ChallengeResponseImpl.class);
		challengeResponseRoot.fetch(ChallengeResponseImpl_.owner);
		
		final Predicate predicate = cb
				.equal(challengeResponseRoot.get(ChallengeResponseImpl_.targetChallenge), selectedChallenge);
		
		criteriaQuery
			.select(challengeResponseRoot)
			.where(predicate);
		
		final TypedQuery<ChallengeResponseImpl> typedQuery = em.createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

}
