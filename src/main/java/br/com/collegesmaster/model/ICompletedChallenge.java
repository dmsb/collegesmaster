package br.com.collegesmaster.model;

public interface ICompletedChallenge extends IModel {

	public void setChallenge(IChallenge challenge);

	public IChallenge getChallenge();

	public void setNote(Integer note);

	public Integer getNote();

	void setResponse(IChallenge response);

	IChallenge getResponse();

}
