package br.com.collegesmaster.model;

import java.util.Set;

public interface IChallengeResolution extends IModel {

	void setTargetChallenge(IChallenge targetChallenge);

	IChallenge getTargetChallenge();

	void setNote(Integer note);

	Integer getNote();

	void setQuestionsResolution(Set<IQuestionResolution> myQuestionsResolution);

	Set<IQuestionResolution> getQuestionsResolution();

	void setOwner(IUser owner);

	IUser getOwner();

}
