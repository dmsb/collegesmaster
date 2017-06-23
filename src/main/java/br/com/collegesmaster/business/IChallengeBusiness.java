package br.com.collegesmaster.business;

import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import br.com.collegesmaster.model.IChallenge;
import br.com.collegesmaster.model.imp.Challenge;
import br.com.collegesmaster.model.imp.Question;

public interface IChallengeBusiness extends IBusiness<IChallenge, Challenge> {
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	List<Question> findQuestionsByChallenge(IChallenge selectedChallenge);

}
