package br.com.collegesmaster.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.collegesmaster.enums.Letter;
import br.com.collegesmaster.model.impl.AlternativeImpl;

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
