package br.com.collegesmaster.model;

import br.com.collegesmaster.enums.Letter;

public interface Alternative extends Model {

	void setDescription(String description);

	String getDescription();

	void setDefinition(Boolean definition);

	void setLetter(Letter letter);

	Letter getLetter();

	Boolean getDefinition();

	void setQuestion(Question question);

	Question getQuestion();

}
