package br.com.collegesmaster.model;

import java.util.List;

import br.com.collegesmaster.model.impl.Alternative;

public interface IQuestion extends IModel {

	void setAlternatives(List<Alternative> alternatives);

	List<Alternative> getAlternatives();

	void setPontuation(Integer pontuation);

	Integer getPontuation();

	void setDescription(String description);

	String getDescription();

	void setChallenge(IChallenge challenge);

	IChallenge getChallenge();

}
