package br.com.collegesmaster.model.challenge.dataprovider;

import java.util.List;

import br.com.collegesmaster.model.challenge.Challenge;
import br.com.collegesmaster.model.challenge.impl.ChallengeImpl;
import br.com.collegesmaster.model.challenge.impl.QuestionImpl;
import br.com.collegesmaster.model.security.User;

public interface ChallengeDataProvider {

	List<QuestionImpl> findQuestionsByChallenge(Challenge selectedChallenge);
	
	List<ChallengeImpl> findByUser(User user);
	
}
