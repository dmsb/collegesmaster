package br.com.collegesmaster.model;

import java.util.List;

public interface IChallenge extends IModel {

	IUser getUser();

	void setUser(IUser user);

	IDiscipline getDiscipline();

	void setDiscipline(IDiscipline discipline);

	void setQuestions(List<IQuestion> questions);

	List<IQuestion> getQuestions();
	
}