package br.com.collegesmaster.business.impl;

import br.com.collegesmaster.business.IChallengeBusiness;
import br.com.collegesmaster.model.Challenge;

public class ChallengeBusiness extends Business implements IChallengeBusiness {
	
	@Override
	public void persistChallenge(Challenge challenge) {		
		entityManager.persist(challenge);
	}
	
	@Override
	public void mergeChallenge(Challenge challenge) {
		entityManager.merge(challenge);
	}
	
	@Override
	public void removeChallenge(Challenge challenge) {
		entityManager.remove(challenge);
	}
}
