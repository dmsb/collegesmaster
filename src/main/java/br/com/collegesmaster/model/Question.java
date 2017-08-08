package br.com.collegesmaster.model;

import java.util.List;

import br.com.collegesmaster.model.impl.AlternativeImpl;

public interface Question extends Model {

	void setAlternatives(List<AlternativeImpl> alternatives);

	List<AlternativeImpl> getAlternatives();

	void setPontuation(Integer pontuation);

	Integer getPontuation();

	void setDescription(String description);

	String getDescription();

	void setChallenge(Challenge challenge);

	Challenge getChallenge();

}
