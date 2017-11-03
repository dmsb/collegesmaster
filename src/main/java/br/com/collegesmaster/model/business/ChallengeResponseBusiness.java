package br.com.collegesmaster.model.business;

import java.util.List;

import br.com.collegesmaster.model.entities.challenge.Challenge;
import br.com.collegesmaster.model.entities.challengeresponse.impl.ChallengeResponseImpl;
import br.com.collegesmaster.model.entities.user.User;

public interface ChallengeResponseBusiness extends BasicCrudBusiness<ChallengeResponseImpl> {
	
	List<ChallengeResponseImpl> findAllByUser(User user);
	
	List<ChallengeResponseImpl> findAllByChallenge(Challenge selectedChallenge);

}
