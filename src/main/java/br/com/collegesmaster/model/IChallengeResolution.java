package br.com.collegesmaster.model;

import java.util.List;

public interface IChallengeResolution extends IModel {

	void setTargetChallenge(IChallenge targetChallenge);

	IChallenge getTargetChallenge();

	void setNote(Integer note);

	Integer getNote();

	void setQuestionsResolution(List<IQuestionResolution> myQuestionsResolution);

	List<IQuestionResolution> getQuestionsResolution();

	void setOwner(IUser owner);

	IUser getOwner();

}
