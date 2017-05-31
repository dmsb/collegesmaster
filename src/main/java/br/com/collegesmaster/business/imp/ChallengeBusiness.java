package br.com.collegesmaster.business.imp;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.commons.collections.CollectionUtils;

import br.com.collegesmaster.business.IChallengeBusiness;
import br.com.collegesmaster.model.IChallenge;
import br.com.collegesmaster.model.imp.Challenge;

@Stateless
public class ChallengeBusiness extends Business implements IChallengeBusiness {
	
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
	public IChallenge findById(Integer id, Class<IChallenge> modelClass) {
		return entityManager.find(modelClass, id);
	}

	@Override
	public List<IChallenge> getList() {
		
		final CriteriaQuery<Challenge> criteriaQuery = criteriaBuilder.createQuery(Challenge.class);
		final TypedQuery<Challenge> typedQuery = entityManager.createQuery(criteriaQuery);		
		final List<Challenge> result = typedQuery.getResultList(); 
		
		if(CollectionUtils.isEmpty(result)) {
			return null;
		} else {
			final List<IChallenge> challenges = new ArrayList<IChallenge>();
			result.forEach(challenge -> challenges.add(challenge));
			return challenges;
		}
	}
}
