package br.com.collegesmaster.business;

import java.util.List;

import br.com.collegesmaster.model.Challenge;
import br.com.collegesmaster.model.User;
import br.com.collegesmaster.model.impl.ChallengeResponseImpl;

public interface ChallengeResponseBusiness extends BasicCrudBusiness<ChallengeResponseImpl> {
	
	List<ChallengeResponseImpl> findAllByUser(User user);
	
	List<ChallengeResponseImpl> findAllByChallenge(Challenge selectedChallenge);

}
