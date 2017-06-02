package br.com.collegesmaster.model;

import java.util.List;

import br.com.collegesmaster.model.imp.Challenge;
import br.com.collegesmaster.model.imp.Discipline;

public interface IStudent extends IUser, IModel {

	List<Challenge> getCompletedChallenges();

	void setCompletedChallenges(List<Challenge> completedChallenges);

	List<Discipline> getDisciplines();

	void setDisciplines(List<Discipline> disciplines);

}