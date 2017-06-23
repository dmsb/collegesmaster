package br.com.collegesmaster.model;

import br.com.collegesmaster.enums.Letter;

public interface IQuestionResolution extends IModel {

	void setTargetQuestion(IQuestion targetQuestion);

	IQuestion getTargetQuestion();

	void setChallengeResolution(IChallengeResolution challengeResolution);

	IChallengeResolution getChallengeResolution();

	void setLetter(Letter letter);

	Letter getLetter();

}
