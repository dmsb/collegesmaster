package br.com.collegesmaster.model.challenge.dataprovider.impl;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.collegesmaster.model.challenge.Challenge;
import br.com.collegesmaster.model.challenge.dataprovider.ChallengeDataProvider;
import br.com.collegesmaster.model.challenge.impl.ChallengeImpl;
import br.com.collegesmaster.model.challenge.impl.ChallengeImpl_;
import br.com.collegesmaster.model.challenge.impl.QuestionImpl;
import br.com.collegesmaster.model.challenge.impl.QuestionImpl_;
import br.com.collegesmaster.model.security.User;
import br.com.collegesmaster.qualifiers.UserDatabase;

@Dependent
public class ChallengeDataProviderImpl implements ChallengeDataProvider {
	
	@Inject @UserDatabase
	private EntityManager em;
	
	@Inject
	protected CriteriaBuilder cb;

	@Override
	public List<QuestionImpl> findQuestionsByChallenge(final Challenge selectedChallenge) {
		final CriteriaQuery<QuestionImpl> criteriaQuery = cb.createQuery(QuestionImpl.class);
		final Root<QuestionImpl> questionRoot = criteriaQuery.from(QuestionImpl.class);
		final Predicate challengeCondition = 
				cb.equal(questionRoot.get(QuestionImpl_.challenge), selectedChallenge);
		criteriaQuery.where(challengeCondition);
		final TypedQuery<QuestionImpl> typedQuery = em.createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

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
