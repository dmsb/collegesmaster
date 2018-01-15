package br.com.collegesmaster.model.challengeresponse.business;

import java.util.List;

import br.com.collegesmaster.model.challenge.Challenge;
import br.com.collegesmaster.model.challengeresponse.impl.ChallengeResponseImpl;
import br.com.collegesmaster.model.generics.GenericBusiness;
import br.com.collegesmaster.model.security.User;

public interface ChallengeResponseBusiness extends GenericBusiness<ChallengeResponseImpl> {
	
	ChallengeResponseImpl findById(final Integer id);
	
	List<ChallengeResponseImpl> findAllByUser(User user);
	
	List<ChallengeResponseImpl> findAllByChallenge(Challenge selectedChallenge);

}
