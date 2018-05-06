package br.com.collegesmaster.model.challengeresponse.dataprovider;

import java.util.List;

import br.com.collegesmaster.model.challenge.Challenge;
import br.com.collegesmaster.model.challengeresponse.ChallengeResponse;
import br.com.collegesmaster.model.challengeresponse.impl.ChallengeResponseImpl;
import br.com.collegesmaster.model.security.User;

public interface ChallengeResponseDataProvider {

	Boolean alrealdyRepliedByUser(ChallengeResponse response);
	
	List<ChallengeResponseImpl> findAllByUser(User user);
	
	List<ChallengeResponseImpl> findAllByChallenge(Challenge selectedChallenge);
}
