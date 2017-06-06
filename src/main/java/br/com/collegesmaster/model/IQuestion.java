package br.com.collegesmaster.model;

import java.util.List;

import br.com.collegesmaster.enums.QuestionLevel;

public interface IQuestion extends IModel {

	void setLevel(QuestionLevel level);

	QuestionLevel getLevel();

	void setResponse(List<IAlternative> response);

	List<IAlternative> getResponse();

	void setPontuation(Integer pontuation);

	Integer getPontuation();

	void setDescription(String description);

	String getDescription();

	void setChallenge(IChallenge challenge);

	IChallenge getChallenge();

}
