package br.com.collegesmaster.model.challengeresponse.business;

import java.util.List;

import br.com.collegesmaster.model.challenge.Challenge;
import br.com.collegesmaster.model.challengeresponse.impl.ChallengeResponseImpl;
import br.com.collegesmaster.model.generics.GenericCRUD;
import br.com.collegesmaster.model.security.User;

public interface ChallengeResponseBusiness extends GenericCRUD<ChallengeResponseImpl> {
	
	Boolean alrealdyRepliedByUser(final ChallengeResponseImpl response);
	
	ChallengeResponseImpl findById(final Integer id);
	
	List<ChallengeResponseImpl> findAllByUser(User user);
	
	List<ChallengeResponseImpl> findAllByChallenge(Challenge selectedChallenge);

}
