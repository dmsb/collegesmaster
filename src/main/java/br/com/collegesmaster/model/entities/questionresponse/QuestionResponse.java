package br.com.collegesmaster.model.entities.questionresponse;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.collegesmaster.model.entities.challengeresponse.ChallengeResponse;
import br.com.collegesmaster.model.entities.enums.Letter;
import br.com.collegesmaster.model.entities.model.Model;
import br.com.collegesmaster.model.entities.question.Question;
import br.com.collegesmaster.model.entities.questionresponse.impl.QuestionResponseImpl;

@JsonDeserialize(as = QuestionResponseImpl.class)
public interface QuestionResponse extends Model {

	void setTargetQuestion(Question targetQuestion);

	Question getTargetQuestion();

	void setChallengeResponse(ChallengeResponse challengeResolution);

	ChallengeResponse getChallengeResponse();

	void setLetter(Letter letter);

	Letter getLetter();

}
