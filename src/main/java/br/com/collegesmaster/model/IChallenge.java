package br.com.collegesmaster.model;

import java.util.List;

public interface IChallenge extends IModel {

	IUser getOwner();

	void setOwner(IUser user);

	IDiscipline getDiscipline();

	void setDiscipline(IDiscipline discipline);

	void setQuestions(List<IQuestion> questions);

	List<IQuestion> getQuestions();

	void setTitle(String title);

	String getTitle();
	
}