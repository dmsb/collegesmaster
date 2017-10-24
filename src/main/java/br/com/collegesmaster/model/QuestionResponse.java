package br.com.collegesmaster.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.collegesmaster.enums.Letter;
import br.com.collegesmaster.model.impl.QuestionResponseImpl;

@JsonDeserialize(as = QuestionResponseImpl.class)
public interface QuestionResponse extends Model {

	void setTargetQuestion(Question targetQuestion);

	Question getTargetQuestion();

	void setChallengeResponse(ChallengeResponse challengeResolution);

	ChallengeResponse getChallengeResponse();

	void setLetter(Letter letter);

	Letter getLetter();

}
