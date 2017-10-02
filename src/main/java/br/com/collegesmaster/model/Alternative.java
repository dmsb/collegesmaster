package br.com.collegesmaster.model;

import br.com.collegesmaster.enums.Letter;

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
