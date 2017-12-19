package br.com.collegesmaster.model.challengeresponse.business;

import java.util.List;

import br.com.collegesmaster.model.challenge.Challenge;
import br.com.collegesmaster.model.challengeresponse.impl.ChallengeResponseImpl;
import br.com.collegesmaster.model.generics.BasicCrud;
import br.com.collegesmaster.model.user.User;

public interface ChallengeResponseBusiness extends BasicCrud<ChallengeResponseImpl> {
	
	List<ChallengeResponseImpl> findAllByUser(User user);
	
	List<ChallengeResponseImpl> findAllByChallenge(Challenge selectedChallenge);

}
