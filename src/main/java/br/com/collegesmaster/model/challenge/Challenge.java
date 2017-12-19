package br.com.collegesmaster.model.challenge;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.collegesmaster.model.challenge.impl.ChallengeImpl;
import br.com.collegesmaster.model.challenge.impl.QuestionImpl;
import br.com.collegesmaster.model.institute.Discipline;
import br.com.collegesmaster.model.model.Model;
import br.com.collegesmaster.model.user.User;

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