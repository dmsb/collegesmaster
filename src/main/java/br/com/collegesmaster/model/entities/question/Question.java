package br.com.collegesmaster.model.entities.question;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.collegesmaster.model.entities.alternative.impl.AlternativeImpl;
import br.com.collegesmaster.model.entities.challenge.Challenge;
import br.com.collegesmaster.model.entities.model.Model;
import br.com.collegesmaster.model.entities.question.impl.QuestionImpl;

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
