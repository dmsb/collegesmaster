package br.com.collegesmaster.business;

import static javax.ejb.TransactionAttributeType.REQUIRED;

import java.util.List;

import javax.ejb.TransactionAttribute;

import br.com.collegesmaster.model.IChallenge;
import br.com.collegesmaster.model.IChallengeResponse;
import br.com.collegesmaster.model.IUser;
import br.com.collegesmaster.model.impl.ChallengeResponse;

public interface IChallengeResponseBusiness extends IBusiness<IChallengeResponse, ChallengeResponse> {
	
	@TransactionAttribute(REQUIRED)
	List<ChallengeResponse> findAllByUser(IUser user);
	
	@TransactionAttribute(REQUIRED)
	List<ChallengeResponse> findAllByChallenge(IChallenge selectedChallenge);

}
