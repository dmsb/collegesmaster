package br.com.collegesmaster.model;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.collegesmaster.model.impl.ChallengeImpl;
import br.com.collegesmaster.model.impl.QuestionImpl;

@JsonDeserialize(as = ChallengeImpl.class)
public interface Challenge extends Model {

	User getOwner();

	void setOwner(User user);

	Discipline getDiscipline();

	void setDiscipline(Discipline discipline);

	void setQuestions(List<QuestionImpl> questions);

	List<QuestionImpl> getQuestions();

	void setTitle(String title);

	String getTitle();
	
}