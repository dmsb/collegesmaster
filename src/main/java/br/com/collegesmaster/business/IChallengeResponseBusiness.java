package br.com.collegesmaster.business;

import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import br.com.collegesmaster.model.IChallengeResponse;
import br.com.collegesmaster.model.impl.ChallengeResponse;

public interface IChallengeResponseBusiness extends IBusiness<IChallengeResponse> {
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ChallengeResponse> findAll();
}
