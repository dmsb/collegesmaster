package br.com.collegesmaster.ejb;

import java.util.List;

import javax.ejb.Remote;

import br.com.collegesmaster.model.Challenge;

@Remote
public interface IChallengeBean {

	public void addChallenge(Challenge challenge);
	
	public List<Challenge> getChallenges();
	
	public void removeChallenge(Challenge challenge);

	public void remover();
}
