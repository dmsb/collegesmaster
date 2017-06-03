package br.com.collegesmaster.model;

import java.util.List;

public interface IStudent extends IUser, IModel {

	List<IChallenge> getCompletedChallenges();

	void setCompletedChallenges(List<IChallenge> completedChallenges);

	List<IDiscipline> getDisciplines();

	void setDisciplines(List<IDiscipline> disciplines);

}