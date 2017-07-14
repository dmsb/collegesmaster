package br.com.collegesmaster.model;

import java.util.List;

public interface IChallengeResponse extends IModel {

	void setPontuation(Integer note);

	Integer getPontuation();

	void setQuestionsResponse(List<IQuestionResponse> myQuestionsResolution);

	List<IQuestionResponse> getQuestionsResponse();

	void setOwner(IUser owner);

	IUser getOwner();

	void setTargetChallenge(IChallenge targetChallenge);

	IChallenge getTargetChallenge();

}
