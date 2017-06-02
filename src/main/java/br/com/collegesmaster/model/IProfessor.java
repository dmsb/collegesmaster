package br.com.collegesmaster.model;

import java.util.List;

import br.com.collegesmaster.model.imp.Challenge;
import br.com.collegesmaster.model.imp.Discipline;

public interface IProfessor extends IUser, IModel {

	List<Challenge> getChallenges();

	void setChallenges(List<Challenge> challenges);

	List<Discipline> getDisciplines();

	void setDisciplines(List<Discipline> disciplines);

}