package br.com.collegesmaster.model.business;

import java.util.List;

import br.com.collegesmaster.model.entities.challenge.Challenge;
import br.com.collegesmaster.model.entities.challenge.impl.ChallengeImpl;
import br.com.collegesmaster.model.entities.question.impl.QuestionImpl;
import br.com.collegesmaster.model.entities.user.User;

public interface ChallengeBusiness extends BasicCrudBusiness<ChallengeImpl> {
	
	List<QuestionImpl> findQuestionsByChallenge(Challenge selectedChallenge);
	
	List<ChallengeImpl> findByUser(User user);
}
