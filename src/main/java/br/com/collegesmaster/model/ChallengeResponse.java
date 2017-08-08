package br.com.collegesmaster.model;

import java.util.List;

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
