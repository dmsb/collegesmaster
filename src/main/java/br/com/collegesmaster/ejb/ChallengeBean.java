package br.com.collegesmaster.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Remove;
import javax.ejb.Stateful;

import br.com.collegesmaster.model.Challenge;

@Stateful
public class ChallengeBean implements IChallengeBean {

	List<Challenge> challenges;

	public ChallengeBean(){
		challenges = new ArrayList<Challenge>();
	}
	
	@Override
	public void addChallenge(Challenge challenge) {
		challenges.add(challenge);
	}
	
	@Override
	public List<Challenge> getChallenges() {
		return challenges;
	}
	
	@Override
	public void removeChallenge(Challenge challenge) {
		challenges.remove(challenge);
	}

	@Remove
	@Override
	public void remover() {
		challenges = null;
	}
}
