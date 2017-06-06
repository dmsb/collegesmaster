package br.com.collegesmaster.model;

import br.com.collegesmaster.enums.Letter;

public interface IAlternative extends IModel {

	void setDescription(String description);

	String getDescription();

	void setDefinition(Boolean definition);

	void setLetter(Letter letter);

	Letter getLetter();

	Boolean getDefinition();

	void setQuestion(IQuestion question);

	IQuestion getQuestion();

}
