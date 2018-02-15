package br.com.collegesmaster.model.challengeresponse;

import java.util.Collection;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.collegesmaster.model.challenge.Challenge;
import br.com.collegesmaster.model.challenge.impl.AlternativeImpl;
import br.com.collegesmaster.model.challengeresponse.impl.ChallengeResponseImpl;
import br.com.collegesmaster.model.model.Model;
import br.com.collegesmaster.model.security.User;

@JsonDeserialize(as = ChallengeResponseImpl.class)
public interface ChallengeResponse extends Model {

	void setPunctuation(Integer punctuation);

	Integer getPunctuation();

	void setQuestionsResponse(Collection<QuestionResponse> myQuestionsResolution);

	Collection<QuestionResponse> getQuestionsResponse();

	void setOwner(User owner);

	User getOwner();

	void setTargetChallenge(Challenge targetChallenge);

	Challenge getTargetChallenge();

	void addPunctuation(final QuestionResponse response, AlternativeImpl alternative);

	void calculatePunctuation();

}
