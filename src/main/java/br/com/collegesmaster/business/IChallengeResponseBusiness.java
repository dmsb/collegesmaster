package br.com.collegesmaster.business;

import java.util.List;

import br.com.collegesmaster.model.IChallengeResponse;
import br.com.collegesmaster.model.IUser;
import br.com.collegesmaster.model.impl.ChallengeResponse;

public interface IChallengeResponseBusiness extends IBusiness<IChallengeResponse, ChallengeResponse> {

	List<ChallengeResponse> findAll(IUser user);

}
