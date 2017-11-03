package br.com.collegesmaster.model.entities.alternative;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.collegesmaster.model.entities.alternative.impl.AlternativeImpl;
import br.com.collegesmaster.model.entities.enums.Letter;
import br.com.collegesmaster.model.entities.model.Model;
import br.com.collegesmaster.model.entities.question.Question;

@JsonDeserialize(as = AlternativeImpl.class)
public interface Alternative extends Model {

	void setDescription(String description);

	String getDescription();

	void setLetter(Letter letter);

	Letter getLetter();

	void setIsTrue(Boolean isTrue);
	
	Boolean getIsTrue();

	void setQuestion(Question question);

	Question getQuestion();

}
