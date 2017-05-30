package br.com.collegesmaster.business;

import br.com.collegesmaster.model.Challenge;

public interface IChallengeBusiness {

	public void persistChallenge(Challenge challenge);

	public void mergeChallenge(Challenge challenge);

	public void removeChallenge(Challenge challenge);

}