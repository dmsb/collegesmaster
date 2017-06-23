package br.com.collegesmaster.business.imp;

import static br.com.collegesmaster.model.imp.Question_.challenge;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.collegesmaster.business.IChallengeBusiness;
import br.com.collegesmaster.model.IChallenge;
import br.com.collegesmaster.model.imp.Challenge;
import br.com.collegesmaster.model.imp.Question;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ChallengeBusiness extends GenericBusiness implements IChallengeBusiness {	
	
	@Override
	public void persist(final IChallenge challenge) {
		entityManager.persist(challenge);		
	}
	
	@Override
	public void merge(final IChallenge challenge) {
		entityManager.merge(challenge);
	}

	@Override
	public void remove(final IChallenge challenge) {
		entityManager.remove(challenge);
	}

	@Override
	public IChallenge findById(Integer id, Class<Challenge> modelClass) {
		return entityManager.find(modelClass, id);
	}

	@Override
	public List<Challenge> findAll() {
		
		final CriteriaQuery<Challenge> criteriaQuery = criteriaBuilder.createQuery(Challenge.class);
		final TypedQuery<Challenge> typedQuery = entityManager.createQuery(criteriaQuery);		
		return typedQuery.getResultList(); 		
	}

	@Override
	public List<Question> findQuestionsByChallenge(final IChallenge selectedChallenge) {
		
		final CriteriaQuery<Question> criteriaQuery = criteriaBuilder.createQuery(Question.class);
		final Root<Question> questionRoot = criteriaQuery.from(Question.class);
		
		final Predicate challengeCondition = 
				criteriaBuilder.equal(questionRoot.get(challenge), selectedChallenge);
		
		criteriaQuery.where(challengeCondition);
		
		final TypedQuery<Question> typedQuery = entityManager.createQuery(criteriaQuery);
		
		return typedQuery.getResultList();
	}	
}
