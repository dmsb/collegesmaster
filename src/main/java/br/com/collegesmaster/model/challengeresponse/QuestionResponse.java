package br.com.collegesmaster.model.challengeresponse;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.collegesmaster.model.challenge.Question;
import br.com.collegesmaster.model.challenge.enums.Letter;
import br.com.collegesmaster.model.challengeresponse.impl.QuestionResponseImpl;
import br.com.collegesmaster.model.model.Model;

@JsonDeserialize(as = QuestionResponseImpl.class)
public interface QuestionResponse extends Model {

	void setTargetQuestion(Question targetQuestion);

	Question getTargetQuestion();

	void setChallengeResponse(ChallengeResponse challengeResolution);

	ChallengeResponse getChallengeResponse();

	void setLetter(Letter letter);

	Letter getLetter();

}
