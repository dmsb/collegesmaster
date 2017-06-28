package br.com.collegesmaster.model;

import java.util.List;

public interface IChallengeResponse extends IModel {

	void setNote(Integer note);

	Integer getNote();

	void setQuestionsResponse(List<IQuestionResponse> myQuestionsResolution);

	List<IQuestionResponse> getQuestionsResponse();

	void setOwner(IUser owner);

	IUser getOwner();

}
