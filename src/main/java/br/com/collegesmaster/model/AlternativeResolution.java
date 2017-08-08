package br.com.collegesmaster.model;

import br.com.collegesmaster.enums.Letter;

public interface AlternativeResolution extends Model {

	void setTargetAlternative(Alternative targetAlternative);

	Alternative getTargetAlternative();

	void setQuestionResolution(QuestionResponse questionResolution);

	QuestionResponse getQuestionResolution();

	void setDefinition(Boolean definition);

	Boolean getDefinition();

	void setLetter(Letter letter);

	Letter getLetter();

}
