package br.com.collegesmaster.model;

import br.com.collegesmaster.enums.Letter;

public interface QuestionResponse extends Model {

	void setTargetQuestion(Question targetQuestion);

	Question getTargetQuestion();

	void setChallengeResponse(ChallengeResponse challengeResolution);

	ChallengeResponse getChallengeResponse();

	void setLetter(Letter letter);

	Letter getLetter();

}
