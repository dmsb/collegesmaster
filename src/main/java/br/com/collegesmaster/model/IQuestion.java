package br.com.collegesmaster.model;

import java.util.Set;

public interface IQuestion extends IModel {

	void setAlternatives(Set<IAlternative> alternatives);

	Set<IAlternative> getAlternatives();

	void setPontuation(Integer pontuation);

	Integer getPontuation();

	void setDescription(String description);

	String getDescription();

	void setChallenge(IChallenge challenge);

	IChallenge getChallenge();

}
