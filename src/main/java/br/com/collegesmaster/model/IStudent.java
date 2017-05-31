package br.com.collegesmaster.model;

import java.util.List;

import br.com.collegesmaster.model.imp.Challenge;
import br.com.collegesmaster.model.imp.Discipline;
import br.com.collegesmaster.model.imp.GeneralInfo;

public interface IStudent extends IUser, IModel {

	String getRegistration();

	void setRegistration(String registration);

	GeneralInfo getGeneralInfo();

	void setGeneralInfo(GeneralInfo generalInfo);

	List<Challenge> getCompletedChallenges();

	void setCompletedChallenges(List<Challenge> completedChallenges);

	List<Discipline> getDisciplines();

	void setDisciplines(List<Discipline> disciplines);

}