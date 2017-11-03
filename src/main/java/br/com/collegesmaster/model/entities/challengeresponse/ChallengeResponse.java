package br.com.collegesmaster.model.entities.challengeresponse;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.collegesmaster.model.entities.challenge.Challenge;
import br.com.collegesmaster.model.entities.challengeresponse.impl.ChallengeResponseImpl;
import br.com.collegesmaster.model.entities.model.Model;
import br.com.collegesmaster.model.entities.questionresponse.QuestionResponse;
import br.com.collegesmaster.model.entities.user.User;

@JsonDeserialize(as = ChallengeResponseImpl.class)
public interface ChallengeResponse extends Model {

	void setPontuation(Integer note);

	Integer getPontuation();

	void setQuestionsResponse(List<QuestionResponse> myQuestionsResolution);

	List<QuestionResponse> getQuestionsResponse();

	void setOwner(User owner);

	User getOwner();

	void setTargetChallenge(Challenge targetChallenge);

	Challenge getTargetChallenge();

}
