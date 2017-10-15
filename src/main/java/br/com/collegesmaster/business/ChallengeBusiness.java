package br.com.collegesmaster.business;

import java.util.List;

import br.com.collegesmaster.model.Challenge;
import br.com.collegesmaster.model.User;
import br.com.collegesmaster.model.impl.ChallengeImpl;
import br.com.collegesmaster.model.impl.QuestionImpl;

public interface ChallengeBusiness extends BasicCrudBusiness<ChallengeImpl> {
	
	List<QuestionImpl> findQuestionsByChallenge(Challenge selectedChallenge);
	
	List<ChallengeImpl> findByUser(User user);
}
