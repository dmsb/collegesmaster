package br.com.collegesmaster.model.challenge.business;

import java.util.List;

import br.com.collegesmaster.model.challenge.Challenge;
import br.com.collegesmaster.model.challenge.impl.ChallengeImpl;
import br.com.collegesmaster.model.challenge.impl.QuestionImpl;
import br.com.collegesmaster.model.generics.BasicCrud;
import br.com.collegesmaster.model.user.User;

public interface ChallengeBusiness extends BasicCrud<ChallengeImpl> {
	
	List<QuestionImpl> findQuestionsByChallenge(Challenge selectedChallenge);
	
	List<ChallengeImpl> findByUser(User user);
}
