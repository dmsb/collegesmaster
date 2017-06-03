package br.com.collegesmaster.model;

import java.util.List;

public interface IProfessor extends IUser, IModel {

	List<IChallenge> getChallenges();

	void setChallenges(List<IChallenge> challenges);

	List<IDiscipline> getDisciplines();

	void setDisciplines(List<IDiscipline> disciplines);

}