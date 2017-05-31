package br.com.collegesmaster.model;

import java.util.List;

import br.com.collegesmaster.model.imp.Challenge;
import br.com.collegesmaster.model.imp.Professor;
import br.com.collegesmaster.model.imp.Student;

public interface IDiscipline extends IModel {

	List<Student> getStudents();

	void setStudents(List<Student> students);

	ICourse getCourse();

	void setCourse(ICourse course);

	String getName();

	void setName(String name);

	List<Challenge> getChallenges();

	void setChallenges(List<Challenge> challenges);

	List<Professor> getProfessors();

	void setProfessors(List<Professor> professors);

}