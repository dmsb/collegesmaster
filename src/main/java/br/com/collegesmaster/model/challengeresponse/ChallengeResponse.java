package br.com.collegesmaster.model.challengeresponse;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.collegesmaster.model.challenge.Challenge;
import br.com.collegesmaster.model.challenge.impl.AlternativeImpl;
import br.com.collegesmaster.model.challengeresponse.impl.ChallengeResponseImpl;
import br.com.collegesmaster.model.model.Model;
import br.com.collegesmaster.model.user.User;

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

	void addPontuation(final QuestionResponse response, AlternativeImpl alternative);

	void calculatePontuation();

}
