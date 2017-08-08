package br.com.collegesmaster.business;

import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import br.com.collegesmaster.model.Challenge;
import br.com.collegesmaster.model.User;
import br.com.collegesmaster.model.impl.ChallengeImpl;
import br.com.collegesmaster.model.impl.QuestionImpl;

@Path("/challenges")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public interface ChallengeBusiness extends Business<ChallengeImpl> {
	
	@GET
	@Path("/find_by_challenge")
	@TransactionAttribute(REQUIRED)
	List<QuestionImpl> findQuestionsByChallenge(Challenge selectedChallenge);
	
	@GET
	@Path("/find_by_user")
	@TransactionAttribute(REQUIRED)
	List<ChallengeImpl> findByUser(User user);
}
