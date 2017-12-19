package br.com.collegesmaster.model.challenge;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.collegesmaster.model.challenge.impl.AlternativeImpl;
import br.com.collegesmaster.model.challenge.impl.QuestionImpl;
import br.com.collegesmaster.model.model.Model;

@JsonDeserialize(as = QuestionImpl.class)
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
