package br.com.collegesmaster.model;

import java.util.List;

import br.com.collegesmaster.model.impl.Challenge;

public interface IDiscipline extends IModel {

	ICourse getCourse();

	void setCourse(ICourse course);

	String getName();

	void setName(String name);

	List<Challenge> getChallenges();

	void setChallenges(List<Challenge> challenges);

}