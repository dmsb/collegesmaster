package br.com.collegesmaster.business.imp;

import static br.com.collegesmaster.model.impl.Question_.challenge;
import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.List;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.collegesmaster.business.IChallengeBusiness;
import br.com.collegesmaster.model.IChallenge;
import br.com.collegesmaster.model.impl.Challenge;
import br.com.collegesmaster.model.impl.Question;

@Stateless
@TransactionManagement(CONTAINER)
@DeclareRoles({"STUDENT", "PROFESSOR", "ADMINISTRATOR"})
@RolesAllowed({"ADMINISTRATOR"})
public class ChallengeBusiness extends GenericBusiness implements IChallengeBusiness {	
	
	@RolesAllowed({"PROFESSOR", "ADMINISTRATOR"})
	@Override
	public void save(final IChallenge challenge) {
		entityManager.persist(challenge);		
	}
	
	@RolesAllowed({"PROFESSOR", "ADMINISTRATOR"})
	@Override
	public IChallenge update(final IChallenge challenge) {
		return entityManager.merge(challenge);
	}

	@Override
	public void remove(final IChallenge challenge) {
		entityManager.remove(challenge);
	}

	@RolesAllowed({"STUDENT", "PROFESSOR", "ADMINISTRATOR"})
	@Override
	public IChallenge findById(Integer id) {
		return entityManager.find(Challenge.class, id);
	}

	@Override
	public List<Challenge> findAll() {
		
		final CriteriaQuery<Challenge> criteriaQuery = builder.createQuery(Challenge.class);
		final TypedQuery<Challenge> typedQuery = entityManager.createQuery(criteriaQuery);		
		return typedQuery.getResultList(); 		
	}

	@PermitAll
	@Override
	public List<Question> findQuestionsByChallenge(final IChallenge selectedChallenge) {
		
		final CriteriaQuery<Question> criteriaQuery = builder.createQuery(Question.class);
		final Root<Question> questionRoot = criteriaQuery.from(Question.class);
		
		final Predicate challengeCondition = 
				builder.equal(questionRoot.get(challenge), selectedChallenge);
		
		criteriaQuery.where(challengeCondition);
		
		final TypedQuery<Question> typedQuery = entityManager.createQuery(criteriaQuery);
		
		return typedQuery.getResultList();
	}	
}
