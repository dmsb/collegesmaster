package br.com.collegesmaster.business;

import static javax.ejb.TransactionAttributeType.REQUIRED;

import java.util.List;

import javax.ejb.TransactionAttribute;

import br.com.collegesmaster.model.Challenge;
import br.com.collegesmaster.model.User;
import br.com.collegesmaster.model.impl.ChallengeResponseImpl;

public interface ChallengeResponseBusiness extends Business<ChallengeResponseImpl> {
	
	@TransactionAttribute(REQUIRED)
	List<ChallengeResponseImpl> findAllByUser(User user);
	
	@TransactionAttribute(REQUIRED)
	List<ChallengeResponseImpl> findAllByChallenge(Challenge selectedChallenge);

}
