package br.com.collegesmaster.model.challengeresponse.dataprovider.impl;

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
import br.com.collegesmaster.model.challengeresponse.dataprovider.ChallengeResponseDataProvider;
import br.com.collegesmaster.model.challengeresponse.impl.ChallengeResponseImpl;
import br.com.collegesmaster.model.challengeresponse.impl.ChallengeResponseImpl_;
import br.com.collegesmaster.model.generics.producers.CriteriaBooleanReponse;
import br.com.collegesmaster.model.security.User;
import br.com.collegesmaster.qualifiers.UserDatabase;

@Dependent
public class ChallengeResponseDataProviderImpl implements ChallengeResponseDataProvider {

	@Inject
	private CriteriaBooleanReponse<ChallengeResponseImpl> booleanResponseBuilder;
	
	@Inject @UserDatabase
	private EntityManager em;
	
	@Inject
	protected CriteriaBuilder cb;
	
	@Override
	public Boolean alrealdyRepliedByUser(ChallengeResponseImpl response) {
		final Root<ChallengeResponseImpl> crRoot = booleanResponseBuilder.build(ChallengeResponseImpl.class);
		final Predicate equalsUser = cb.equal(crRoot.get(ChallengeResponseImpl_.owner), response.getOwner());
		final Predicate equalsTargetChallenge = cb.equal(crRoot.get(ChallengeResponseImpl_.targetChallenge), 
				response.getTargetChallenge());
		return booleanResponseBuilder.where(equalsUser, equalsTargetChallenge).execute();
	}

	@Override
	public List<ChallengeResponseImpl> findAllByUser(User user) {
		final CriteriaQuery<ChallengeResponseImpl> criteriaQuery = cb.createQuery(ChallengeResponseImpl.class);
		final Root<ChallengeResponseImpl> challengeResponseRoot = criteriaQuery.from(ChallengeResponseImpl.class);
		final Predicate userPredicate = cb.equal(challengeResponseRoot.get(ChallengeResponseImpl_.owner), user);
		
		criteriaQuery
			.select(challengeResponseRoot)
			.where(userPredicate);
		
		final TypedQuery<ChallengeResponseImpl> typedQuery = em.createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

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
