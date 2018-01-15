package br.com.collegesmaster.model.challenge.business;

import java.util.List;

import br.com.collegesmaster.model.challenge.Challenge;
import br.com.collegesmaster.model.challenge.impl.ChallengeImpl;
import br.com.collegesmaster.model.challenge.impl.QuestionImpl;
import br.com.collegesmaster.model.generics.GenericBusiness;
import br.com.collegesmaster.model.security.User;

public interface ChallengeBusiness extends GenericBusiness<ChallengeImpl> {
	
	ChallengeImpl findById(final Integer id);
	
	List<QuestionImpl> findQuestionsByChallenge(Challenge selectedChallenge);
	
	List<ChallengeImpl> findByUser(User user);
}
