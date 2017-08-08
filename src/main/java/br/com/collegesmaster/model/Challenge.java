package br.com.collegesmaster.model;

import java.util.List;

import br.com.collegesmaster.model.impl.QuestionImpl;

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