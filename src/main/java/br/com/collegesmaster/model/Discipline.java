package br.com.collegesmaster.model;

import java.util.List;

import br.com.collegesmaster.model.impl.ChallengeImpl;

public interface Discipline extends Model {

	Course getCourse();

	void setCourse(Course course);

	String getName();

	void setName(String name);

	List<ChallengeImpl> getChallenges();

	void setChallenges(List<ChallengeImpl> challenges);

}