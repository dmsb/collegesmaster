package br.com.collegesmaster.model;

import br.com.collegesmaster.enums.Letter;

public interface IAlternativeResolution extends IModel {

	void setTargetAlternative(IAlternative targetAlternative);

	IAlternative getTargetAlternative();

	void setQuestionResolution(IQuestionResolution questionResolution);

	IQuestionResolution getQuestionResolution();

	void setDefinition(Boolean definition);

	Boolean getDefinition();

	void setLetter(Letter letter);

	Letter getLetter();

}
