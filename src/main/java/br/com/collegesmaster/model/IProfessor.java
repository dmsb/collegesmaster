package br.com.collegesmaster.model;

import java.util.List;

import br.com.collegesmaster.model.imp.Challenge;
import br.com.collegesmaster.model.imp.Discipline;
import br.com.collegesmaster.model.imp.GeneralInfo;

public interface IProfessor extends IUser, IModel {

	List<Challenge> getChallenges();

	void setChallenges(List<Challenge> challenges);

	String getSiape();

	void setSiape(String siape);

	List<Discipline> getDisciplines();

	void setDisciplines(List<Discipline> disciplines);

	GeneralInfo getGeneralInfo();

	void setGeneralInfo(GeneralInfo generalInfo);

}