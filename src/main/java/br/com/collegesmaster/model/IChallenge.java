package br.com.collegesmaster.model;

import java.util.Set;

public interface IChallenge extends IModel {

	IUser getOwner();

	void setOwner(IUser user);

	IDiscipline getDiscipline();

	void setDiscipline(IDiscipline discipline);

	void setQuestions(Set<IQuestion> questions);

	Set<IQuestion> getQuestions();

	void setTitle(String title);

	String getTitle();
	
}