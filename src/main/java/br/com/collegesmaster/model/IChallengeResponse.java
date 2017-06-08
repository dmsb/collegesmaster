package br.com.collegesmaster.model;

public interface IChallengeResponse extends IModel {

	public void setChallenge(IChallenge challenge);

	public IChallenge getChallenge();

	public void setNote(Integer note);

	public Integer getNote();

	void setMyChallengeResolution(IChallenge myChallengeResolution);

	IChallenge getMyChallengeResolution();

}
