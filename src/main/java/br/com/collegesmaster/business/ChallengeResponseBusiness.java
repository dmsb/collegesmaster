package br.com.collegesmaster.business;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import br.com.collegesmaster.model.Challenge;
import br.com.collegesmaster.model.User;
import br.com.collegesmaster.model.impl.ChallengeResponseImpl;

@Path("/challenges-response")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public interface ChallengeResponseBusiness extends BasicCrudOperation<ChallengeResponseImpl> {
	
	List<ChallengeResponseImpl> findAllByUser(User user);
	
	List<ChallengeResponseImpl> findAllByChallenge(Challenge selectedChallenge);

}
