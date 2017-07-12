package br.com.collegesmaster.business;

import static javax.ejb.TransactionAttributeType.REQUIRED;

import java.util.List;

import javax.ejb.TransactionAttribute;

import br.com.collegesmaster.model.IChallenge;
import br.com.collegesmaster.model.impl.Challenge;
import br.com.collegesmaster.model.impl.Question;

public interface IChallengeBusiness extends IBusiness<IChallenge, Challenge> {
	
	@TransactionAttribute(REQUIRED)
	List<Question> findQuestionsByChallenge(IChallenge selectedChallenge);
}
