package br.com.collegesmaster.model;

import java.util.List;

public interface IChallengeResolution extends IModel {

	public void setTargetChallenge(IChallenge targetChallenge);

	public IChallenge getTargetChallenge();

	public void setNote(Integer note);

	public Integer getNote();

	public void setQuestionsResolution(List<IQuestionResolution> myQuestionsResolution);

	public List<IQuestionResolution> getQuestionsResolution();

	void setOwner(IUser owner);

	IUser getOwner();

}
