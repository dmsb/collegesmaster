package br.com.collegesmaster.business;

import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import br.com.collegesmaster.model.IChallenge;
import br.com.collegesmaster.model.impl.Challenge;
import br.com.collegesmaster.model.impl.Question;

public interface IChallengeBusiness extends IBusiness<IChallenge> {
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	List<Question> findQuestionsByChallenge(IChallenge selectedChallenge);
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Challenge> findAll();
}
