package br.com.collegesmaster.business;

import br.com.collegesmaster.model.Challenge;

public interface IChallengeBusiness {

	void persistChallenge(Challenge challenge);

	void mergeChallenge(Challenge challenge);

	void removeChallenge(Challenge challenge);

}