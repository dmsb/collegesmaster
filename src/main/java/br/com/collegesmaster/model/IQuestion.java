package br.com.collegesmaster.model;

import java.util.Set;

import br.com.collegesmaster.enums.QuestionLevel;

public interface IQuestion extends IModel {

	void setLevel(QuestionLevel level);

	QuestionLevel getLevel();

	void setAlternatives(Set<IAlternative> alternatives);

	Set<IAlternative> getAlternatives();

	void setPontuation(Integer pontuation);

	Integer getPontuation();

	void setDescription(String description);

	String getDescription();

	void setChallenge(IChallenge challenge);

	IChallenge getChallenge();

}
