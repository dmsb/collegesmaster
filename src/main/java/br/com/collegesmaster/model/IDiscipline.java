package br.com.collegesmaster.model;

import java.util.List;

public interface IDiscipline extends IModel {

	List<IStudent> getStudents();

	void setStudents(List<IStudent> students);

	ICourse getCourse();

	void setCourse(ICourse course);

	String getName();

	void setName(String name);

	List<IChallenge> getChallenges();

	void setChallenges(List<IChallenge> challenges);

	List<IProfessor> getProfessors();

	void setProfessors(List<IProfessor> professors);

}