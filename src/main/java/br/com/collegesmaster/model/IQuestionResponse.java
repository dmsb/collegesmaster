package br.com.collegesmaster.model;

import br.com.collegesmaster.enums.Letter;

public interface IQuestionResponse extends IModel {

	void setTargetQuestion(IQuestion targetQuestion);

	IQuestion getTargetQuestion();

	void setChallengeResponse(IChallengeResponse challengeResolution);

	IChallengeResponse getChallengeResponse();

	void setLetter(Letter letter);

	Letter getLetter();

}
