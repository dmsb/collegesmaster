package br.com.collegesmaster.model;

import java.util.List;

import br.com.collegesmaster.model.imp.Question;

public interface IChallenge extends IModel {

	IUser getOwner();

	void setOwner(IUser user);

	IDiscipline getDiscipline();

	void setDiscipline(IDiscipline discipline);

	void setQuestions(List<Question> questions);

	List<Question> getQuestions();

	void setTitle(String title);

	String getTitle();
	
}