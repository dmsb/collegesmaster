package br.com.collegesmaster.business.impl;

import static br.com.collegesmaster.model.impl.QuestionImpl_.challenge;
import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.List;

import javax.annotation.security.PermitAll;
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
import br.com.collegesmaster.business.ChallengeBusiness;
import br.com.collegesmaster.model.Challenge;
import br.com.collegesmaster.model.User;
import br.com.collegesmaster.model.impl.ChallengeImpl;
import br.com.collegesmaster.model.impl.ChallengeImpl_;
import br.com.collegesmaster.model.impl.QuestionImpl;

@Stateless
@TransactionManagement(CONTAINER)
@SecurityDomain("collegesmasterSecurityDomain")
public class ChallengeBusinessImpl implements ChallengeBusiness {
	
	@Inject @UserDatabase
	private EntityManager em;
	
	@Inject
	protected CriteriaBuilder cb;
	
	@RolesAllowed({"PROFESSOR", "ADMINISTRATOR"})
	@Override
	public void create(final ChallengeImpl challenge) {
		em.persist(challenge);
	}
	
	@RolesAllowed({"PROFESSOR", "ADMINISTRATOR"})
	@Override
	public ChallengeImpl update(final ChallengeImpl challenge) {
		return em.merge(challenge);
	}
	
	@RolesAllowed({"ADMINISTRATOR"})
	@Override
	public void remove(final ChallengeImpl challenge) {
		em.remove(challenge);
	}

	@RolesAllowed({"STUDENT", "PROFESSOR", "ADMINISTRATOR"})
	@Override
	public ChallengeImpl findById(Integer id) {
		return em.find(ChallengeImpl.class, id);
	}
	
	@RolesAllowed({"ADMINISTRATOR"})
	@Override
	public List<ChallengeImpl> findAll() {
		
		final CriteriaQuery<ChallengeImpl> criteriaQuery = cb.createQuery(ChallengeImpl.class);
		final TypedQuery<ChallengeImpl> typedQuery = em.createQuery(criteriaQuery);		
		return typedQuery.getResultList(); 		
	}

	@PermitAll
	@Override
	public List<QuestionImpl> findQuestionsByChallenge(final Challenge selectedChallenge) {

		final CriteriaQuery<QuestionImpl> criteriaQuery = cb.createQuery(QuestionImpl.class);
		final Root<QuestionImpl> questionRoot = criteriaQuery.from(QuestionImpl.class);
		
		final Predicate challengeCondition = 
				cb.equal(questionRoot.get(challenge), selectedChallenge);
		
		criteriaQuery.where(challengeCondition);
		
		final TypedQuery<QuestionImpl> typedQuery = em.createQuery(criteriaQuery);
		
		return typedQuery.getResultList();
	}
	
	@RolesAllowed({"PROFESSOR", "ADMINISTRATOR"})
	@Override
	public List<ChallengeImpl> findByUser(final User user) {
		final CriteriaQuery<ChallengeImpl> criteriaQuery = cb.createQuery(ChallengeImpl.class);
		final Root<ChallengeImpl> challengeRoot = criteriaQuery.from(ChallengeImpl.class);
		
		final Predicate predicate = cb.equal(challengeRoot.get(ChallengeImpl_.owner), user);
		
		criteriaQuery.where(predicate);
		
		final TypedQuery<ChallengeImpl> typedQuery = em.createQuery(criteriaQuery);
		
		return typedQuery.getResultList();
	}	
}
